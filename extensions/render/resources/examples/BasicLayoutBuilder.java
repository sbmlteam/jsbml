package examples;

import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.director.LayoutBuilder;
import org.sbml.jsbml.ext.render.director.Macromolecule;
import org.sbml.jsbml.ext.render.director.SBGNArc;
import org.sbml.jsbml.ext.render.director.SBGNNode;
import org.sbml.jsbml.ext.render.director.SBGNProcessNode;
import org.sbml.jsbml.ext.render.director.SimpleChemical;

// TODO: is this intended usage?
public class BasicLayoutBuilder implements LayoutBuilder<String> {

  private BasicLayoutFactory factory;
  private StringBuffer product;
  private boolean ready;
  private Layout layout;
  
  public BasicLayoutBuilder() {
    product = new StringBuffer();
    factory = new BasicLayoutFactory(1, 2);
    ready = false;
  }

  @Override
  public void builderStart(Layout layout) {
    this.layout = layout;
    addLine("\\documentclass{article}");
    addLine("\\usepackage{tikz}");
    addLine("\\usetikzlibrary{arrows.meta}");
    addLine("\\usetikzlibrary{shapes.misc}"); 
    addLine("\\usepackage{mathabx}");
    addLine("");
    addLine("\\begin{document}");
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
    SBGNArc<String> process;
    switch(srg.getRole()) {
      case PRODUCT:
      case SIDEPRODUCT:
        process = factory.createProduction();
        break;
      case ACTIVATOR:
        process = factory.createStimulation();
        break;
      case INHIBITOR:
        process = factory.createInhibition();
        break;
      case MODIFIER:
        process = factory.createCatalysis();
        break;
      default:
        process = factory.createConsumption();
    }
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
    // speciesGlyph.getSpeciesInstance() TODO: how to best decide whether simpleChemical?
    // For now: all equal, graphically SBGN-macromolecules.
    Macromolecule<String> species = factory.createMacromolecule();
    species.setCloneMarker(cloneMarker);
    product.append(drawBoundingBox(species, speciesGlyph.getBoundingBox()));
    product.append(" % Species: ");
    addLine(speciesGlyph.getId());
  }

  @Override
  public void buildProcessNode(ReactionGlyph reactionGlyph,
    double rotationAngle, double curveWidth) {
    SBGNProcessNode<String> process = factory.createProcessNode();
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
}
