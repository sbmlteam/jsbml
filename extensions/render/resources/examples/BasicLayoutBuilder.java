package examples;

import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.director.AbstractLayoutBuilder;
import org.sbml.jsbml.ext.render.director.AssociationNode;
import org.sbml.jsbml.ext.render.director.Catalysis;
import org.sbml.jsbml.ext.render.director.Compartment;
import org.sbml.jsbml.ext.render.director.Consumption;
import org.sbml.jsbml.ext.render.director.DissociationNode;
import org.sbml.jsbml.ext.render.director.Inhibition;
import org.sbml.jsbml.ext.render.director.Macromolecule;
import org.sbml.jsbml.ext.render.director.Modulation;
import org.sbml.jsbml.ext.render.director.NecessaryStimulation;
import org.sbml.jsbml.ext.render.director.NucleicAcidFeature;
import org.sbml.jsbml.ext.render.director.OmittedProcessNode;
import org.sbml.jsbml.ext.render.director.PerturbingAgent;
import org.sbml.jsbml.ext.render.director.ProcessNode;
import org.sbml.jsbml.ext.render.director.Production;
import org.sbml.jsbml.ext.render.director.ReversibleConsumption;
import org.sbml.jsbml.ext.render.director.SBGNArc;
import org.sbml.jsbml.ext.render.director.SBGNNode;
import org.sbml.jsbml.ext.render.director.SBGNNodeWithCloneMarker;
import org.sbml.jsbml.ext.render.director.SBGNProcessNode;
import org.sbml.jsbml.ext.render.director.SimpleChemical;
import org.sbml.jsbml.ext.render.director.SourceSink;
import org.sbml.jsbml.ext.render.director.Stimulation;
import org.sbml.jsbml.ext.render.director.UncertainProcessNode;
import org.sbml.jsbml.ext.render.director.UnspecifiedNode;

public class BasicLayoutBuilder extends AbstractLayoutBuilder<String, String, String> {

  private BasicLayoutFactory factory;
  private StringBuffer product;
  private boolean ready;
  
  public BasicLayoutBuilder(double lineWidth, double arrowScale, double reactionNodeSize) {
    product = new StringBuffer();
    factory = new BasicLayoutFactory(lineWidth, arrowScale, reactionNodeSize);
    ready = false;
  }

  @Override
  public void builderStart(Layout layout) {
    // Standalone document is the drawn picture and only the drawn picture
    addLine("\\documentclass{standalone}");  
    addLine("\\usepackage{tikz}");
    addLine("\\usetikzlibrary{arrows.meta}");
    addLine("\\usetikzlibrary{shapes.misc}"); 
    addLine("\\usepackage{mathabx}");
    addLine("");
    addLine("\\begin{document}");
    // Layout-coordinates are in pt with rightward x-Axis and downward y-Axis
    // (Layout specification 3.2, p. 6) 
    addLine("\\begin{tikzpicture}[yscale=-1]");
    
    product.append("\\draw[dotted] (0pt,0pt) rectangle (");
    product.append(layout.getDimensions().getWidth());
    product.append("pt, ");
    product.append(layout.getDimensions().getHeight());
    addLine("pt); % Canvas");
  }

  @Override
  public void buildCompartment(CompartmentGlyph compartmentGlyph) {
    // Can assume: compartmentGlyph is laid-out
    product.append(drawBoundingBox(factory.createCompartment(), compartmentGlyph.getBoundingBox()));
    addLine(compartmentGlyph.getId());
  }

  @Override
  public void buildConnectingArc(SpeciesReferenceGlyph srg, ReactionGlyph rg,
    double curveWidth) {
    SBGNArc<String> process = createArc(srg, rg); // getSBGNArc(srg.getSBOTerm()); 
    addLine(String.format("\t%% Connecting arc: %s and %s", srg.getId(), rg.getId()));
    product.append(process.draw(srg.getCurve()));
  }

  @Override
  public void buildCubicBezier(CubicBezier cubicBezier, double lineWidth) {
    // TODO Auto-generated method stub
    // what is this method for?
  }

  @Override
  public void buildEntityPoolNode(SpeciesGlyph speciesGlyph,
    boolean cloneMarker) {
    // Use AbstractLayoutBuilder.getSBGNNode to parse the SBOTerm (which has
    // been set by the LayoutDirector) to get the appropriate SBGN-class:
    SBGNNode<String> species = getSBGNNode(speciesGlyph.getSBOTerm());
    
    // Note: SourceSink does not carry a clone-marker, making this check
    // necessary
    if(species instanceof SBGNNodeWithCloneMarker) {
      ((SBGNNodeWithCloneMarker<String>) species).setCloneMarker(cloneMarker);
    }
    
    if(species instanceof LaTeXSourceSink) {
      ((LaTeXSourceSink) species).setNodeId(speciesGlyph.getId());
    }
    
    product.append(drawBoundingBox(species, speciesGlyph.getBoundingBox()));
    // Adding some annotation to the LaTeX-document for readability
    product.append(" % Species: ");
    addLine(speciesGlyph.getId());
  }

  @Override
  public void buildProcessNode(ReactionGlyph reactionGlyph,
    double rotationAngle, double curveWidth) {
    SBGNProcessNode<String> process = getSBGNReactionNode(reactionGlyph.getReactionInstance().getSBOTerm());
    product.append("% Reaction: ");
    addLine(reactionGlyph.getId());
    
    BoundingBox bb = reactionGlyph.getBoundingBox();
    Point rotationCentre = bb.getPosition().clone();
    rotationCentre.setX(bb.getDimensions().getWidth()/2 + rotationCentre.getX());
    rotationCentre.setY(bb.getDimensions().getHeight()/2 + rotationCentre.getY());
    if(reactionGlyph.isSetCurve()) {
      product.append(process.draw(reactionGlyph.getCurve(), rotationAngle, rotationCentre));
    } else {
      product.append(process.draw(bb.getPosition().getX(),
        bb.getPosition().getY(), bb.getPosition().getZ(),
        bb.getDimensions().getWidth(), bb.getDimensions().getHeight(),
        bb.getDimensions().getDepth(), rotationAngle, rotationCentre));
    }
  }

  @Override
  public void buildTextGlyph(TextGlyph textGlyph) {
    // TODO LayoutFatory does not know of this?
    String label = textGlyph.isSetText() ? textGlyph.getText() : "?";
    if(textGlyph.isSetText()) {
      label = textGlyph.getText();
    }
    // Hierarchical overriding:
    if(textGlyph.isSetOriginOfText()) {
      label = textGlyph.getOriginOfTextInstance().getName();
    }
    
    addLine(
      String.format("\\node[] (%s) at (%spt, %spt) {%s};", textGlyph.getId(),
        textGlyph.getBoundingBox().getPosition().getX()
          + textGlyph.getBoundingBox().getDimensions().getWidth() / 2,
          textGlyph.getBoundingBox().getPosition().getY()
          + textGlyph.getBoundingBox().getDimensions().getHeight() / 2,
          label));
  }

  @Override
  public void builderEnd() {
    addLine("\\end{tikzpicture}");
    addLine("\\end{document}");
    addLine("");
    ready = true;
  }

  @Override
  public String getProduct() {
    // TODO: should this check for isProductReady?
    return product.toString();
  }

  @Override
  public boolean isProductReady() {
    return ready;
  }
  
  
  /**
   * Helper method to add a String as a new line (newline is added behind given
   * string, like {@link System.out.println}) to the product
   * 
   * @param line
   *        to be added
   */
  private void addLine(String line) {
    product.append(line);
    product.append(System.getProperty("line.separator")); 
  }
  
  private String drawBoundingBox(SBGNNode<String> node, BoundingBox bbox) {
    return node.draw(bbox.getPosition().getX(), bbox.getPosition().getY(),
      bbox.getPosition().getZ(), bbox.getDimensions().getWidth(),
      bbox.getDimensions().getHeight(), bbox.getDimensions().getDepth());
  }

  @Override
  public AssociationNode<String> createAssociationNode() {
    return factory.createAssociationNode();
  }

  @Override
  public Compartment<String> createCompartment() {
    return factory.createCompartment();
  }

  @Override
  public DissociationNode<String> createDissociationNode() {
    return factory.createDissociationNode();
  }

  @Override
  public Macromolecule<String> createMacromolecule() {
    return factory.createMacromolecule();
  }

  @Override
  public NucleicAcidFeature<String> createNucleicAcidFeature() {
    return factory.createNucleicAcidFeature();
  }

  @Override
  public OmittedProcessNode<String> createOmittedProcessNode() {
    return factory.createOmittedProcessNode();
  }

  @Override
  public PerturbingAgent<String> createPerturbingAgent() {
    return factory.createPerturbingAgent();
  }

  @Override
  public ProcessNode<String> createProcessNode() {
    return factory.createProcessNode();
  }

  @Override
  public SimpleChemical<String> createSimpleChemical() {
    return factory.createSimpleChemical();
  }

  @Override
  public SourceSink<String> createSourceSink() {
    return factory.createSourceSink();
  }

  @Override
  public UncertainProcessNode<String> createUncertainProcessNode() {
    return factory.createUncertainProcessNode();
  }

  @Override
  public UnspecifiedNode<String> createUnspecifiedNode() {
    return factory.createUnspecifiedNode();
  }

  @Override
  public Catalysis<String> createCatalysis() {
    return factory.createCatalysis();
  }

  @Override
  public Consumption<String> createConsumption() {
    return factory.createConsumption();
  }

  @Override
  public ReversibleConsumption<String> createReversibleConsumption() {
    return factory.createReversibleConsumption();
  }

  @Override
  public Inhibition<String> createInhibition() {
    return factory.createInhibition();
  }

  @Override
  public Modulation<String> createModulation() {
    return factory.createModulation();
  }

  @Override
  public NecessaryStimulation<String> createNecessaryStimulation() {
    return factory.createNecessaryStimulation();
  }

  @Override
  public Production<String> createProduction() {
    return factory.createProduction();
  }

  @Override
  public Stimulation<String> createStimulation() {
    return factory.createStimulation();
  }
}
