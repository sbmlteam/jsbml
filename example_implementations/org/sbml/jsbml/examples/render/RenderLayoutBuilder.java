/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.examples.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.LineEnding;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.RenderCubicBezier;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderPoint;
import org.sbml.jsbml.ext.render.VTextAnchor;
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

/**
 * Class for building a LocalRenderInformation object for rendering the given
 * layout. A key method is {@link RenderLayoutBuilder#buildLineEndings()}.<br>
 * 
 * Notice the different classes used for drawing Nodes (LocalStyle) vs. Arcs
 * (String), which both differ from the final product's type
 * (LocalRenderInformation). Compare this to the <String, String, String> typing
 * in the LaTeX-example
 * 
 * @author David Vetter
 */
public class RenderLayoutBuilder
  extends AbstractLayoutBuilder<LocalRenderInformation, LocalStyle, String> {

  private LocalRenderInformation product;
  private Layout                 layout;
  
  /** Ids for the various Color-definitions */
  private static String          STROKE    = "black";
  private static String          HIGHLIGHT = "red";
  private static String          FILL      = "white";
  private static String COMPARTMENT_FILL   = "compartmentFill";
  private static String COMPARTMENT_STROKE = "compartmentStroke";
  private static String          GENE_FILL = "geneFill";
  private static String MACROMOLECULE_FILL = "macromoleculeFill";
  private static String SIMPLE_CHEMICAL_FILL = "simpleChemicalFill";
  private static String SOURCE_SINK_FILL   = "sourceSinkFill";
  private static String PERTURBING_AGENT_FILL = "perturbingAgentFill";
  
  private double arrowScale = 6;
  
  public static final String          STYLE_CATALYSIS              =
    "catalysisStyle";
  public static final String          STYLE_CONSUMPTION            =
    "consumptionStyle";
  public static final String          STYLE_REVERSIBLE_CONSUMPTION =
    "reversibleConsumptionStyle";
  public static final String          STYLE_INHIBITION             =
    "inhibitionStyle";
  public static final String          STYLE_MODULATION             =
    "modulationStyle";
  public static final String          STYLE_NECESSARY_STIMULATION  =
    "necessaryStimulationStyle";
  public static final String          STYLE_PRODUCTION             =
    "productionStyle";
  public static final String          STYLE_STIMULATION            =
    "stimulationStyle";
  
  /**
   * Since the arcs specified by SpeciesReferenceGlyphs can easily be styled by
   * just adding them to the ID-list of a general style (their layout is not the
   * business of the render-plugin), we here hold a mapping from style-names to
   * the style-objects, which is built in the
   * {@link RenderLayoutBuilder#buildLineEndings}-method, and used by the
   * {@link RenderLayoutBuilder#buildConnectingArc}-method
   */
  private HashMap<String, LocalStyle> arcStyles;
  
  public RenderLayoutBuilder() {
    super();
    arcStyles = new HashMap<String, LocalStyle>();
  }
  
  @Override
  public void builderStart(Layout layout) {
    this.layout = layout;
    terminated = false;
    product = new LocalRenderInformation(layout.getId() + "_render",
      layout.getLevel(), layout.getVersion());
    product.addColorDefinition(new ColorDefinition(HIGHLIGHT, Color.RED));
    product.addColorDefinition(new ColorDefinition(STROKE, Color.BLACK));
    product.addColorDefinition(new ColorDefinition(FILL, Color.WHITE));
    
    /**
     * Fill colors for different SBGN-glyphs: Compare
     * github.com/draeger-lab/SysBio/blob/master/src/de/zbit/graph/io/def/SBGNVisualizationProperties.java
     */
    product.addColorDefinition(
      new ColorDefinition(COMPARTMENT_FILL, new Color(243, 243, 191)));
    product.addColorDefinition(
      new ColorDefinition(COMPARTMENT_STROKE, new Color(204, 204, 0)));
    product.addColorDefinition(
      new ColorDefinition(GENE_FILL, new Color(255, 255, 0)));
    product.addColorDefinition(
      new ColorDefinition(MACROMOLECULE_FILL, new Color(0, 205, 0)));
    product.addColorDefinition(
      new ColorDefinition(SIMPLE_CHEMICAL_FILL, new Color(176, 226, 255)));
    product.addColorDefinition(
      new ColorDefinition(SOURCE_SINK_FILL, new Color(255, 204, 204)));
    product.addColorDefinition(
      new ColorDefinition(PERTURBING_AGENT_FILL, new Color(255, 0, 255)));
    
    buildLineEndings();
    
    RenderGroup group = new RenderGroup(layout.getLevel(), layout.getVersion());
    group.setFontSize((short) 10);
    group.setFontFamily("monospace");
    group.setTextAnchor(HTextAnchor.MIDDLE);
    group.setVTextAnchor(VTextAnchor.MIDDLE);
    
    LocalStyle style = new LocalStyle(layout.getLevel(), layout.getVersion(), group);
    style.setId("TextGylphStyle");
    style.setTypeList(new ArrayList<LocalStyle.Type>());
    style.getTypeList().add(LocalStyle.Type.TEXTGLYPH);
    product.addLocalStyle(style); 
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
    /**
     * Since the drawing-expert has no access to the concrete
     * CompartmentGlyph-instance, the built style is linked to the styled within
     * this RenderLayoutBuilder (similar for other graphical objects)
     */
    compartmentStyle.setIDList(new ArrayList<String>());
    compartmentStyle.getIDList().add(cg.getId());
    compartmentStyle.setId("styleOf_" + cg.getId());
    product.addLocalStyle(compartmentStyle);
  }

  @Override
  public void buildConnectingArc(SpeciesReferenceGlyph srg, ReactionGlyph rg,
    double curveWidth) {
    /**
     * Design decision: To avoid some redundancy, just register the
     * speciesReferenceGlyph at the correct Style.
     */
    
    // See LaTeX-example
    if (srg.isSetSpeciesReference()
      && srg.getSpeciesReferenceInstance().isSetSBOTerm()) {
      srg.setSBOTerm(srg.getSpeciesReferenceInstance().getSBOTerm());
      srg.unsetRole();
    }
    SBGNArc<String> process = createArc(srg, rg); 
    
    /**
     * The various SBGNArc-implementations will here always simply return the id
     * (a String) of the style to which the speciesReferenceGlyph should be
     * subscribed. This makes the resulting file less redundant (and is the
     * reason for the near pointless respective implementations here)
     */
    arcStyles.get(process.draw(srg.getCurve())).getIDList().add(srg.getId());
  }

  @Override
  public void buildCubicBezier(CubicBezier cubicBezier, double lineWidth) { }
  // Unnecessary ^

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
    speciesStyle.setId("styleOf_" + sg.getId());
    product.addLocalStyle(speciesStyle);
  }

  @Override
  public void buildProcessNode(ReactionGlyph reactionGlyph,
    double rotationAngle, double curveWidth) {
    SBGNProcessNode<LocalStyle> process = getSBGNReactionNode(reactionGlyph.getReactionInstance().getSBOTerm());  
    
    BoundingBox bb = reactionGlyph.getBoundingBox();
    Point rotationCentre = bb.getPosition().clone();
    rotationCentre.setX(bb.getDimensions().getWidth()/2 + rotationCentre.getX());
    rotationCentre.setY(bb.getDimensions().getHeight()/2 + rotationCentre.getY());
    
    /**
     * Note: The layout-specification states that the boundingbox is to be
     * ignored if a reactionGlyph's Curve is set. However, the render-plugin
     * will always use the boundingbox (as a reference coordinate system), so it
     * is here used too (out of necessity).
     */
    LocalStyle style = process.draw(bb.getPosition().getX(),
      bb.getPosition().getY(), bb.getPosition().getZ(),
      bb.getDimensions().getWidth(), bb.getDimensions().getHeight(),
      bb.getDimensions().getDepth(), rotationAngle, rotationCentre);
    style.setIDList(new ArrayList<String>());
    style.getIDList().add(reactionGlyph.getId());
    product.addLocalStyle(style);
  }

  @Override
  public void buildTextGlyph(TextGlyph textGlyph) {
    // This is handled by the one general textglyph-style built in builderStart
  }

  @Override
  public void builderEnd() {
    terminated = true;
  }

  @Override
  public LocalRenderInformation getProduct() {
    return product;
  }

  
  /*
   * The following methods implement the LayoutFactory-Interface:
   * Instantiate Drawing-experts for the various SBGN-elements, and equip them
   * with the necessary drawing-rules (like line width)
   */
  @Override
  public AssociationNode<LocalStyle> createAssociationNode() {
    return new RenderAssociationNode(1, STROKE, FILL, 10);
  }

  @Override
  public Compartment<LocalStyle> createCompartment() {
    return new RenderCompartment(0.3, COMPARTMENT_STROKE, COMPARTMENT_FILL);
  }

  @Override
  public DissociationNode<LocalStyle> createDissociationNode() {
    return new RenderDissociationNode(1, STROKE, FILL, 10);
  }

  @Override
  public Macromolecule<LocalStyle> createMacromolecule() {
    return new RenderMacromolecule(1, STROKE, MACROMOLECULE_FILL, HIGHLIGHT, 4);
  }

  @Override
  public NucleicAcidFeature<LocalStyle> createNucleicAcidFeature() {
    return new RenderNucleicAcidFeature(1, STROKE, GENE_FILL, HIGHLIGHT, 4);
  }

  @Override
  public OmittedProcessNode<LocalStyle> createOmittedProcessNode() {
    return new RenderOmittedProcessNode(1, STROKE, FILL, 10);
  }

  @Override
  public PerturbingAgent<LocalStyle> createPerturbingAgent() {
    return new RenderPerturbingAgent(1, STROKE, PERTURBING_AGENT_FILL, HIGHLIGHT);
  }

  @Override
  public ProcessNode<LocalStyle> createProcessNode() {
    return new RenderProcessNode(1, STROKE, FILL, 10);
  }

  @Override
  public SimpleChemical<LocalStyle> createSimpleChemical() {
    return new RenderSimpleChemical(1, STROKE, SIMPLE_CHEMICAL_FILL, HIGHLIGHT);
  }

  @Override
  public SourceSink<LocalStyle> createSourceSink() {
    return new RenderSourceSink(1, STROKE, SOURCE_SINK_FILL);
  }

  @Override
  public UncertainProcessNode<LocalStyle> createUncertainProcessNode() {
    return new RenderUncertainProcessNode(1, STROKE, FILL, 10);
  }

  @Override
  public UnspecifiedNode<LocalStyle> createUnspecifiedNode() {
    return new RenderUnspecifiedNode(1, STROKE, FILL, HIGHLIGHT);
  }

  @Override
  public Catalysis<String> createCatalysis() {
    return new RenderCatalysis();
  }

  @Override
  public Consumption<String> createConsumption() {
    return new RenderConsumption();
  }

  @Override
  public ReversibleConsumption<String> createReversibleConsumption() {
    return new RenderReversibleConsumption();
  }

  @Override
  public Inhibition<String> createInhibition() {
    return new RenderInhibition();
  }

  @Override
  public Modulation<String> createModulation() {
    return new RenderModulation();
  }

  @Override
  public NecessaryStimulation<String> createNecessaryStimulation() {
    return new RenderNecessaryStimulation();
  }

  @Override
  public Production<String> createProduction() {
    return new RenderProduction();
  }

  @Override
  public Stimulation<String> createStimulation() {
    return new RenderStimulation();
  }
  
  
  /**
   * Method to create all the needed line-endings in the product (so the
   * individual arcs can just reference them, cf. render-specification on
   * LineEndings).
   */
  private void buildLineEndings() {
    /**
     * The consumption-arcs will not have any particularly interesting features. 
     * Importantly, it lacks a LineEnding
     */
    RenderGroup consumptionStyleGroup = new RenderGroup();
    consumptionStyleGroup.setStroke(STROKE);
    consumptionStyleGroup.setStrokeWidth(1);
    LocalStyle consumptionStyle = new LocalStyle(layout.getLevel(), layout.getVersion(), consumptionStyleGroup);
    consumptionStyle.setId(STYLE_CONSUMPTION);
    consumptionStyle.setIDList(new ArrayList<String>());
    this.product.addLocalStyle(consumptionStyle);
    arcStyles.put(consumptionStyle.getId(), consumptionStyle);
    
    
    LineEnding production = createLineEnding("productionHead", -0.8*arrowScale, -arrowScale/2, 0.9*arrowScale, arrowScale);
    // Build the actual arrow-head
    RenderGroup productionGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon productionArrowHead = productionGroup.createPolygon();
    addRenderPoint(productionArrowHead, 0, 0);
    addRenderPoint(productionArrowHead, 0.9*arrowScale, 0.5*arrowScale);
    addRenderPoint(productionArrowHead, 0, arrowScale);
    setGraphicalProperties(productionArrowHead, 0.3, STROKE, STROKE);
    
    production.setGroup(productionGroup);
    product.addLineEnding(production);
    
    // Building the corresponding line-styles:
    addArcStyle(STYLE_PRODUCTION, production.getId());
    addArcStyle(STYLE_REVERSIBLE_CONSUMPTION, production.getId());
    
    
    // Stimulation and Production-heads just so happen to be near identical
    LineEnding stimulation = createLineEnding("stimulationHead", -0.8*arrowScale, -0.5*arrowScale, 0.9*arrowScale, arrowScale);
    
    RenderGroup stimulationGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon stimulationArrowHead = stimulationGroup.createPolygon();
    addRenderPoint(stimulationArrowHead, 0, 0);
    addRenderPoint(stimulationArrowHead, 0.9*arrowScale, 0.5*arrowScale);
    addRenderPoint(stimulationArrowHead, 0, arrowScale);
    setGraphicalProperties(stimulationArrowHead, 0.3, STROKE, FILL);
    
    stimulation.setGroup(stimulationGroup);
    product.addLineEnding(stimulation);
    
    addArcStyle(STYLE_STIMULATION, stimulation.getId());
    
    
    // Catalysis uses a circular ending
    LineEnding catalysis = createLineEnding("catalysisHead", -0.9*arrowScale, -0.5*arrowScale, arrowScale, arrowScale); 
    
    RenderGroup catalysisGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Ellipse catalysisArrowHead = catalysisGroup.createEllipse();
    catalysisArrowHead.setCx(new RelAbsVector(0.6d*arrowScale));
    catalysisArrowHead.setCy(new RelAbsVector(0.5*arrowScale));
    catalysisArrowHead.setRx(new RelAbsVector(0.5*arrowScale));
    setGraphicalProperties(catalysisArrowHead, 0.3, STROKE, FILL);
    
    catalysis.setGroup(catalysisGroup);
    product.addLineEnding(catalysis);
    
    addArcStyle(STYLE_CATALYSIS, catalysis.getId());
    
    
    // Inhibition:
    LineEnding inhibition = createLineEnding("inhibitionHead", -0.1*arrowScale, -0.5*arrowScale, 0.2*arrowScale, arrowScale);
    
    RenderGroup inhibitionGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon inhibitionArrowHead = inhibitionGroup.createPolygon();
    addRenderPoint(inhibitionArrowHead, 0.1*arrowScale, 0);
    addRenderPoint(inhibitionArrowHead, 0.1*arrowScale, arrowScale);
    setGraphicalProperties(inhibitionArrowHead, 0.3, STROKE, FILL);
    
    inhibition.setGroup(inhibitionGroup);
    product.addLineEnding(inhibition);
    
    addArcStyle(STYLE_INHIBITION, inhibition.getId());
    
    
    // Necessary Stimulation:
    LineEnding necessaryStimulation = createLineEnding("necessaryStimulationHead", -1.2*arrowScale, -0.5*arrowScale, 1.3*arrowScale, arrowScale);
    
    RenderGroup necessaryStimulationGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon necessaryStimulationArrowHead = necessaryStimulationGroup.createPolygon();
    addRenderPoint(necessaryStimulationArrowHead, 0.1*arrowScale, 0);
    addRenderPoint(necessaryStimulationArrowHead, 0.1*arrowScale, arrowScale);
    setGraphicalProperties(necessaryStimulationArrowHead, 0.3, STROKE, FILL);
    
    necessaryStimulationArrowHead = necessaryStimulationGroup.createPolygon();
    addRenderPoint(necessaryStimulationArrowHead, 0.4*arrowScale, 0);
    addRenderPoint(necessaryStimulationArrowHead, 1.3*arrowScale, 0.5*arrowScale);
    addRenderPoint(necessaryStimulationArrowHead, 0.4*arrowScale, arrowScale);
    setGraphicalProperties(necessaryStimulationArrowHead, 0.3, STROKE, FILL);
    
    necessaryStimulation.setGroup(necessaryStimulationGroup);
    product.addLineEnding(necessaryStimulation);
    
    addArcStyle(STYLE_NECESSARY_STIMULATION, necessaryStimulation.getId());
    
    
    // Modulation:
    LineEnding modulation = createLineEnding("modulationHead", -0.9*arrowScale, -0.5*arrowScale, arrowScale, arrowScale);
    
    RenderGroup modulationGroup = new RenderGroup(layout.getLevel(), layout.getVersion());
    Polygon modulationArrowHead = modulationGroup.createPolygon();
    addRenderPoint(modulationArrowHead, 0, 0.5*arrowScale);
    addRenderPoint(modulationArrowHead, 0.5*arrowScale, 0);
    addRenderPoint(modulationArrowHead, arrowScale, 0.5*arrowScale);
    addRenderPoint(modulationArrowHead, 0.5*arrowScale, arrowScale);
    setGraphicalProperties(modulationArrowHead, 0.3, STROKE, FILL);
    
    modulation.setGroup(modulationGroup);
    product.addLineEnding(modulation);
    
    addArcStyle(STYLE_MODULATION, modulation.getId());
  }
  

  /**
   * @param id the style's full id (e.g. "catalysisStyle")
   * @param headId the head's full id (e.g. "catalysisHead")
   */
  private void addArcStyle(String id, String headId) {
    RenderGroup styleGroup = new RenderGroup();
    styleGroup.setStroke(STROKE);
    styleGroup.setStrokeWidth(1);
    styleGroup.setEndHead(headId);
    LocalStyle style = new LocalStyle(layout.getLevel(), layout.getVersion(), styleGroup);
    style.setId(id);
    style.setIDList(new ArrayList<String>());
    this.product.addLocalStyle(style);
    arcStyles.put(style.getId(), style);
  }
  
  /**
   * 
   * @param gp
   * @param strokeWidth
   * @param stroke
   * @param fill (if null: no fill is set)
   */
  private void setGraphicalProperties(GraphicalPrimitive2D gp,
    double strokeWidth, String stroke, String fill) {
    gp.setStrokeWidth(strokeWidth);
    if(fill != null) {
      gp.setFill(fill);
    }
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
    currentRenderPoint.setX(new RelAbsVector(x));
    currentRenderPoint.setY(new RelAbsVector(y));
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
    bezier.setX(new RelAbsVector(endX));
    bezier.setY(new RelAbsVector(endY));
    bezier.setX1(new RelAbsVector(baseX1));
    bezier.setY1(new RelAbsVector(baseY1));
    bezier.setX2(new RelAbsVector(baseX2)); 
    bezier.setY2(new RelAbsVector(baseY2));
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
