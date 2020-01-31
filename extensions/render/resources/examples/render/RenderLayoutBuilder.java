package examples.render;

import java.awt.Color;
import java.util.ArrayList;

import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.GraphicalPrimitive2D;
import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderCubicBezier;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderPoint;
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
import org.sbml.jsbml.ext.render.director.SBGNNode;
import org.sbml.jsbml.ext.render.director.SBGNNodeWithCloneMarker;
import org.sbml.jsbml.ext.render.director.SimpleChemical;
import org.sbml.jsbml.ext.render.director.SourceSink;
import org.sbml.jsbml.ext.render.director.Stimulation;
import org.sbml.jsbml.ext.render.director.UncertainProcessNode;
import org.sbml.jsbml.ext.render.director.UnspecifiedNode;


public class RenderLayoutBuilder
  extends AbstractLayoutBuilder<LocalRenderInformation, LocalStyle, LocalStyle> {

  private LocalRenderInformation product;
  private Layout layout;
  private static String STROKE = "black";
  private static String HIGHLIGHT = "red";
  private static String FILL = "white";
  
  @Override
  public void builderStart(Layout layout) {
    this.layout = layout;
    terminated = false;
    product = new LocalRenderInformation(layout.getId() + "_render",
      layout.getLevel(), layout.getVersion());
    product.addColorDefinition(new ColorDefinition(HIGHLIGHT, Color.RED));
    product.addColorDefinition(new ColorDefinition(STROKE, Color.BLACK));
    product.addColorDefinition(new ColorDefinition(FILL, Color.WHITE));
    
    buildLineEndings();
  }

  @Override
  public void buildCompartment(CompartmentGlyph cg) {
    LocalStyle compartmentStyle =
      createCompartment().draw(cg.getBoundingBox().getPosition().getX(),
        cg.getBoundingBox().getPosition().getY(),
        cg.getBoundingBox().getPosition().getZ(),
        cg.getBoundingBox().getDimensions().getWidth(),
        cg.getBoundingBox().getDimensions().getHeight(),
        cg.getBoundingBox().getDimensions().getDepth());
    compartmentStyle.setIDList(new ArrayList<String>());
    compartmentStyle.getIDList().add(cg.getId());
    product.addLocalStyle(compartmentStyle);
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
  public void buildEntityPoolNode(SpeciesGlyph sg,
    boolean cloneMarker) {
    SBGNNode<LocalStyle> species = getSBGNNode(sg.getSBOTerm());
    
    /**
     * Note: SourceSink does not carry a clone-marker, making this check
     * necessary
     */
    if(species instanceof SBGNNodeWithCloneMarker) {
      ((SBGNNodeWithCloneMarker<LocalStyle>) species).setCloneMarker(cloneMarker);
    }
    BoundingBox bb = sg.getBoundingBox();
    LocalStyle speciesStyle =
      species.draw(bb.getPosition().getX(), bb.getPosition().getY(),
        bb.getPosition().getZ(), bb.getDimensions().getWidth(),
        bb.getDimensions().getHeight(), bb.getDimensions().getDepth());
    speciesStyle.setIDList(new ArrayList<String>());
    speciesStyle.getIDList().add(sg.getId());
    product.addLocalStyle(speciesStyle);
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
    System.out.println("Unimplemented: Tried to create AssociationNode");
    return null;
  }

  @Override
  public Compartment<LocalStyle> createCompartment() {
    return new RenderCompartment(0.3, STROKE, FILL);
  }

  @Override
  public DissociationNode<LocalStyle> createDissociationNode() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create DissociationNode");
    return null;
  }

  @Override
  public Macromolecule<LocalStyle> createMacromolecule() {
    return new RenderMacromolecule(1, STROKE, FILL, HIGHLIGHT, 10);
  }

  @Override
  public NucleicAcidFeature<LocalStyle> createNucleicAcidFeature() {
    return new RenderNucleicAcidFeature(1, STROKE, FILL, HIGHLIGHT, 10);
  }

  @Override
  public OmittedProcessNode<LocalStyle> createOmittedProcessNode() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create OmittedProcessNode");
    return null;
  }

  @Override
  public PerturbingAgent<LocalStyle> createPerturbingAgent() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create PerturbingAgent");
    return null;
  }

  @Override
  public ProcessNode<LocalStyle> createProcessNode() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create ProcessNode");
    return null;
  }

  @Override
  public SimpleChemical<LocalStyle> createSimpleChemical() {
    return new RenderSimpleChemical(1, STROKE, FILL, HIGHLIGHT);
  }

  @Override
  public SourceSink<LocalStyle> createSourceSink() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Source/Sink");
    return null;
  }

  @Override
  public UncertainProcessNode<LocalStyle> createUncertainProcessNode() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create UncertainProcessNode");
    return null;
  }

  @Override
  public UnspecifiedNode<LocalStyle> createUnspecifiedNode() {
    return new RenderUnspecifiedNode(1, STROKE, FILL, HIGHLIGHT);
  }

  @Override
  public Catalysis<LocalStyle> createCatalysis() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Catalysis");
    return null;
  }

  @Override
  public Consumption<LocalStyle> createConsumption() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Consumption");
    return null;
  }

  @Override
  public ReversibleConsumption<LocalStyle> createReversibleConsumption() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create reversibleConsumption");
    return null;
  }

  @Override
  public Inhibition<LocalStyle> createInhibition() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Inhibition");
    return null;
  }

  @Override
  public Modulation<LocalStyle> createModulation() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Modulation");
    return null;
  }

  @Override
  public NecessaryStimulation<LocalStyle> createNecessaryStimulation() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Necessary Stimulation");
    return null;
  }

  @Override
  public Production<LocalStyle> createProduction() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Production");
    return null;
  }

  @Override
  public Stimulation<LocalStyle> createStimulation() {
    // TODO Auto-generated method stub
    System.out.println("Unimplemented: Tried to create Stimulation");
    return null;
  }
  
  
  /**
   * Method to create all the needed line-endings in the product (so the
   * individual arcs can just reference them)
   */
  private void buildLineEndings() {
    LineEnding production = createLineEnding("productionHead", -8, -5, 9, 10);
    // Build the actual arrow-head
    RenderGroup productionGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon productionArrowHead = productionGroup.createPolygon();
    addRenderPoint(productionArrowHead, 0, 0);
    addRenderPoint(productionArrowHead, 9, 5);
    addRenderPoint(productionArrowHead, 0, 10);
    setGraphicalProperties(productionArrowHead, 0.3, STROKE, STROKE);
    
    production.setGroup(productionGroup);
    product.addLineEnding(production);
    
    // Stimulation and Production-heads just so happen to be near identical
    LineEnding stimulation = createLineEnding("stimulationHead", -8, -5, 9, 10);
    
    RenderGroup stimulationGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon stimulationArrowHead = stimulationGroup.createPolygon();
    addRenderPoint(stimulationArrowHead, 0, 0);
    addRenderPoint(stimulationArrowHead, 9, 5);
    addRenderPoint(stimulationArrowHead, 0, 10);
    setGraphicalProperties(stimulationArrowHead, 0.3, STROKE, FILL);
    
    stimulation.setGroup(stimulationGroup);
    product.addLineEnding(stimulation);
    
    // Catalysis uses a circular ending
    LineEnding catalysis = createLineEnding("catalysisHead", -9, -5, 10, 10); 
    
    RenderGroup catalysisGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Ellipse catalysisArrowHead = catalysisGroup.createEllipse();
    catalysisArrowHead.setCx(6d);
    catalysisArrowHead.setAbsoluteCx(true);
    catalysisArrowHead.setCy(5d);
    catalysisArrowHead.setAbsoluteCy(true);
    catalysisArrowHead.setRx(5d);
    catalysisArrowHead.setAbsoluteRx(true);
    setGraphicalProperties(catalysisArrowHead, 0.3, STROKE, FILL);
    
    catalysis.setGroup(catalysisGroup);
    product.addLineEnding(catalysis);
    
    // Inhibition:
    LineEnding inhibition = createLineEnding("inhibitionHead", -1, -5, 2, 10);
    
    RenderGroup inhibitionGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon inhibitionArrowHead = inhibitionGroup.createPolygon();
    addRenderPoint(inhibitionArrowHead, 1, 0);
    addRenderPoint(inhibitionArrowHead, 1, 10);
    setGraphicalProperties(inhibitionArrowHead, 0.3, STROKE, FILL);
    
    inhibition.setGroup(inhibitionGroup);
    product.addLineEnding(inhibition);
    
    // Necessary Stimulation:
    LineEnding necessaryStimulation = createLineEnding("necessaryStimulationHead", -12, -5, 13, 10);
    
    RenderGroup necessaryStimulationGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon necessaryStimulationArrowHead = necessaryStimulationGroup.createPolygon();
    addRenderPoint(necessaryStimulationArrowHead, 1, 0);
    addRenderPoint(necessaryStimulationArrowHead, 1, 10);
    setGraphicalProperties(necessaryStimulationArrowHead, 0.3, STROKE, FILL);
    
    necessaryStimulationArrowHead = necessaryStimulationGroup.createPolygon();
    addRenderPoint(necessaryStimulationArrowHead, 4, 0);
    addRenderPoint(necessaryStimulationArrowHead, 13, 5);
    addRenderPoint(necessaryStimulationArrowHead, 4, 10);
    setGraphicalProperties(necessaryStimulationArrowHead, 0.3, STROKE, FILL);
    
    necessaryStimulation.setGroup(necessaryStimulationGroup);
    product.addLineEnding(necessaryStimulation);
    
    // Modulation:
    LineEnding modulation = createLineEnding("modulationHead", -9, -5, 10, 10);
    
    RenderGroup modulationGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon modulationArrowHead = modulationGroup.createPolygon();
    addRenderPoint(modulationArrowHead, 0, 5);
    addRenderPoint(modulationArrowHead, 5, 0);
    addRenderPoint(modulationArrowHead, 10, 5);
    addRenderPoint(modulationArrowHead, 5, 10);
    setGraphicalProperties(modulationArrowHead, 0.3, STROKE, FILL);
    
    modulation.setGroup(modulationGroup);
    product.addLineEnding(modulation);
  }
  

  /**
   * 
   * @param gp
   * @param strokeWidth
   * @param stroke
   * @param fill
   */
  private void setGraphicalProperties(GraphicalPrimitive2D gp,
    double strokeWidth, String stroke, String fill) {
    gp.setStrokeWidth(strokeWidth);
    gp.setFill(fill);
    gp.setStroke(stroke);
  }
  
  /**
   * Adds an absolutely positioned {@link RenderPoint} to the polygon
   * @param polygon the polygon to which to add a renderPoint at (x,y)
   * @param x
   * @param y
   */
  public static void addRenderPoint(Polygon polygon, double x, double y) {
    RenderPoint currentRenderPoint = polygon.createRenderPoint();
    currentRenderPoint.setAbsoluteX(true);
    currentRenderPoint.setAbsoluteY(true);
    currentRenderPoint.setX(x);
    currentRenderPoint.setY(y);
  }
  
  /**
   * Adds a {@link RenderCubicBezier} to given {@link Polygon}. All coordinates are 
   * taken (and set) to be absolute.
   * @param polygon to which to add the Bezier-curve
   * @param baseX1 x of the control-point for the curve-start
   * @param baseY1 y of the control-point for the curve-start
   * @param baseX2 x of the control-point for the curve-end
   * @param baseY2 y of the control-point for the curve-end
   * @param endX where the curve ends
   * @param endY where the curve ends
   */
  public static void addRenderCubicBezier(Polygon polygon, double baseX1,
    double baseY1, double baseX2, double baseY2, double endX, double endY) {
    RenderCubicBezier bezier = new RenderCubicBezier();
    bezier.setX(endX); bezier.setAbsoluteX(true);
    bezier.setY(endY); bezier.setAbsoluteY(true);
    bezier.setX1(baseX1); bezier.setAbsoluteX1(true);
    bezier.setY1(baseY1); bezier.setAbsoluteY1(true);
    bezier.setX2(baseX2); bezier.setAbsoluteX2(true);
    bezier.setY2(baseY2); bezier.setAbsoluteY2(true);
    polygon.addElement(bezier);
  }
  

  private LineEnding createLineEnding(String id, double xOffset, double yOffset,
    double width, double height) {
    LineEnding result = new LineEnding();
    result.setId(id);
    result.setLevel(layout.getLevel());
    result.setVersion(layout.getVersion());
    result.setEnableRotationMapping(new Boolean(true));
    BoundingBox bbox = result.createBoundingBox();
    bbox.setPosition(new Point(xOffset, yOffset, 0));
    bbox.setDimensions(
      new Dimensions(width, height, 0, layout.getLevel(), layout.getVersion()));
    return result;
  }
  
}
