package examples.render;

import java.awt.Color;

import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
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
import org.sbml.jsbml.ext.render.director.SimpleChemical;
import org.sbml.jsbml.ext.render.director.SourceSink;
import org.sbml.jsbml.ext.render.director.Stimulation;
import org.sbml.jsbml.ext.render.director.UncertainProcessNode;
import org.sbml.jsbml.ext.render.director.UnspecifiedNode;


public class RenderLayoutBuilder
  extends AbstractLayoutBuilder<LocalRenderInformation, LocalStyle, LocalStyle> {

  private LocalRenderInformation product;
  private Layout layout;
  
  @Override
  public void builderStart(Layout layout) {
    this.layout = layout;
    terminated = false;
    product = new LocalRenderInformation(layout.getId() + "_render",
      layout.getLevel(), layout.getVersion());
    product.addColorDefinition(new ColorDefinition("red", Color.RED));
    product.addColorDefinition(new ColorDefinition("black", Color.BLACK));
    product.addColorDefinition(new ColorDefinition("white", Color.WHITE));
    
    buildLineEndings();
  }

  @Override
  public void buildCompartment(CompartmentGlyph compartmentGlyph) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildConnectingArc(SpeciesReferenceGlyph srg, ReactionGlyph rg,
    double curveWidth) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildCubicBezier(CubicBezier cubicBezier, double lineWidth) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildEntityPoolNode(SpeciesGlyph speciesGlyph,
    boolean cloneMarker) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildProcessNode(ReactionGlyph reactionGlyph,
    double rotationAngle, double curveWidth) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildTextGlyph(TextGlyph textGlyph) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void builderEnd() {
    terminated = true;
    // Probably do not need to do much here.
  }

  @Override
  public LocalRenderInformation getProduct() {
    return product;
  }

  @Override
  public AssociationNode<LocalStyle> createAssociationNode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Compartment<LocalStyle> createCompartment() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DissociationNode<LocalStyle> createDissociationNode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Macromolecule<LocalStyle> createMacromolecule() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public NucleicAcidFeature<LocalStyle> createNucleicAcidFeature() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OmittedProcessNode<LocalStyle> createOmittedProcessNode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PerturbingAgent<LocalStyle> createPerturbingAgent() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProcessNode<LocalStyle> createProcessNode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SimpleChemical<LocalStyle> createSimpleChemical() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SourceSink<LocalStyle> createSourceSink() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UncertainProcessNode<LocalStyle> createUncertainProcessNode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UnspecifiedNode<LocalStyle> createUnspecifiedNode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Catalysis<LocalStyle> createCatalysis() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Consumption<LocalStyle> createConsumption() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReversibleConsumption<LocalStyle> createReversibleConsumption() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Inhibition<LocalStyle> createInhibition() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Modulation<LocalStyle> createModulation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public NecessaryStimulation<LocalStyle> createNecessaryStimulation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Production<LocalStyle> createProduction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Stimulation<LocalStyle> createStimulation() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  /**
   * Method to create all the needed line-endings in the product (so the
   * individual arcs can just reference them)
   */
  private void buildLineEndings() {
    // TODO: implement the line-endings!
  }
}
