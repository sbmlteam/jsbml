/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
package examples.latex;

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

/**
 * Class for actually rendering a full layout: The {@link AbstractLayoutBuilder}
 * itself already implements a LayoutFactory, and thus can generate
 * drawing-experts.
 * Methods of this class are called by the {@link LayoutDirector} to draw each
 * element of the layout
 * 
 * @author David Vetter
 */
public class BasicLayoutBuilder extends AbstractLayoutBuilder<String, String, String> {

  private StringBuffer product;
  private boolean ready;
  
  /**
   * These fields are for the LayoutFactory-functionality required by the
   * AbstractLayoutBuilder. They are used in instantiating the drawing-experts
   * for the graphical elements
   */
  private double lineWidth = 1;
  private double arrowScale = 2;
  private double reactionNodeSize = 10;
  
  public BasicLayoutBuilder(double lineWidth, double arrowScale, double reactionNodeSize) {
    product = new StringBuffer();
    ready = false;
    this.lineWidth = lineWidth;
    this.arrowScale = arrowScale;
    this.reactionNodeSize = reactionNodeSize;
  }

  @Override
  public void builderStart(Layout layout) {
    /** 
     * Standalone document is the drawn picture and only the drawn picture
     * -> Use tikz with library arrow.meta (for arrow-heads) and mathabx 
     * for the source/sink-symbol 
     */
    addLine("\\documentclass{standalone}");  
    addLine("\\usepackage{tikz}");
    addLine("\\usetikzlibrary{arrows.meta}");
    addLine("\\usetikzlibrary{calc}");
    addLine("\\usepackage{mathabx}");
    addLine("");
    addLine("\\begin{document}");
    /** 
     * Layout-coordinates are in pt with rightward x-Axis and downward y-Axis
     * (Layout specification 3.2, p. 6)
     */ 
    addLine("\\begin{tikzpicture}[yscale=-1]");
    
    /**
     * Add a dotted canvas-rectangle (could also make this white) to get the
     * full layout-dimensionality
     */
    product.append("\\draw[dotted] (0pt,0pt) rectangle (");
    product.append(layout.getDimensions().getWidth());
    product.append("pt, ");
    product.append(layout.getDimensions().getHeight());
    addLine("pt); % Canvas");
  }

  @Override
  public void buildCompartment(CompartmentGlyph compartmentGlyph) {
    /** Can assume: compartmentGlyph is laid-out */
    product.append(drawBoundingBox(createCompartment(), compartmentGlyph.getBoundingBox()));
    /**
     * Coupling: The LaTeXCompartment will end its line with a comment "%
     * Compartment: ", to which the builder here adds the compartmentGlyph's id
     * (to make the LaTeX-document more readable)
     * 
     * Similar comments are added for the other drawings.
     */
    addLine(compartmentGlyph.getId());
  }

  @Override
  public void buildConnectingArc(SpeciesReferenceGlyph srg, ReactionGlyph rg,
    double curveWidth) {
    // Rather dirty fix:
    // Deviating from the policy of the LayoutDirector, the SBOTerm here gets
    // priority over the role specified in the layout
    if(srg.getSpeciesReferenceInstance().isSetSBOTerm()) {
      srg.setSBOTerm(srg.getSpeciesReferenceInstance().getSBOTerm());
      srg.unsetRole();
    }
    /**
     * Like for EntityPoolNodes, use the AbstractLayoutBuilder's method to 
     * decide which type of arc should be built
     */
    SBGNArc<String> process = createArc(srg, rg); 
    addLine(String.format("\t%% Connecting arc: %s and %s", srg.getId(), rg.getId()));
    product.append(process.draw(srg.getCurve()));
  }

  @Override
  public void buildCubicBezier(CubicBezier cubicBezier, double lineWidth) {
    // This method is not needed (but can be used of course)
  }

  @Override
  public void buildEntityPoolNode(SpeciesGlyph speciesGlyph,
    boolean cloneMarker) {
    /**
     * Use AbstractLayoutBuilder.getSBGNNode to parse the SBOTerm (which has
     * been set by the LayoutDirector) to get the appropriate SBGN-class:
     */ 
    SBGNNode<String> species = getSBGNNode(speciesGlyph.getSBOTerm());
    
    /**
     * Note: SourceSink does not carry a clone-marker, making this check
     * necessary
     */
    if(species instanceof SBGNNodeWithCloneMarker) {
      ((SBGNNodeWithCloneMarker<String>) species).setCloneMarker(cloneMarker);
    }
    
    if(species instanceof LaTeXSourceSink) {
      /**
       * Coupling: because LaTeXSourceSink uses its id for the tikz-node it draws, 
       * its id is set to a sensible value 
       */
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
    /**
     * As by the layout-specification, a ReactionGlyph's BoundingBox is to be
     * ignored if its Curve is set: The Curve specifies the central part
     * connecting the substrate and product-SpeciesReferenceGlyph-curves.
     * Here, it is understood that it does NOT specify the rectangle/bullet
     * used in SBGN, but only the "whiskers" extending from that center in
     * either direction
     * Note also, that the BoundingBox is not fully ignored here, as it is still
     * used to compute the rotationCentre
     */
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
    /** Start with dummy-text */
    String label = textGlyph.isSetText() ? textGlyph.getText() : "?";
    
    /** then hierarchically override the text based on the set attributes */
    if(textGlyph.isSetText()) {
      label = textGlyph.getText();
    }
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
  
  /**
   * Local helper method: The call "node.draw(bb.x, bb.y, bb.z, bb.width, bb.height, bb.depth)" 
   * occurs more than once
   * @param node the {@link SBGNNode} whose draw-method is to be called
   * @param bbox the {@link BoundingBox} to be drawn
   * @return the drawing (a LaTeX-String)
   */
  private String drawBoundingBox(SBGNNode<String> node, BoundingBox bbox) {
    return node.draw(bbox.getPosition().getX(), bbox.getPosition().getY(),
      bbox.getPosition().getZ(), bbox.getDimensions().getWidth(),
      bbox.getDimensions().getHeight(), bbox.getDimensions().getDepth());
  }

  @Override
  public AssociationNode<String> createAssociationNode() {
    return new LaTeXAssociationNode(lineWidth, reactionNodeSize);
  }

  @Override
  public Compartment<String> createCompartment() {
    return new LaTeXCompartment(lineWidth);
  }

  @Override
  public DissociationNode<String> createDissociationNode() {
    return new LaTeXDissociationNode(lineWidth, reactionNodeSize);
  }

  @Override
  public Macromolecule<String> createMacromolecule() {
    return new LaTeXMacromolecule(lineWidth);
  }

  @Override
  public NucleicAcidFeature<String> createNucleicAcidFeature() {
    return new LaTeXNucleicAcidFeature(lineWidth);
  }

  @Override
  public OmittedProcessNode<String> createOmittedProcessNode() {
    return new LaTeXOmittedProcessNode(lineWidth, reactionNodeSize);
  }

  @Override
  public PerturbingAgent<String> createPerturbingAgent() {
    return new LaTeXPerturbingAgent(lineWidth);
  }

  @Override
  public ProcessNode<String> createProcessNode() {
    return new LaTeXProcessNode(lineWidth, reactionNodeSize);
  }

  @Override
  public SimpleChemical<String> createSimpleChemical() {
    return new LaTeXSimpleChemical(lineWidth);
  }

  @Override
  public SourceSink<String> createSourceSink() {
    return new LaTeXSourceSink();
  }

  @Override
  public UncertainProcessNode<String> createUncertainProcessNode() {
    return new LaTeXUncertainProcessNode(lineWidth, reactionNodeSize);
  }

  @Override
  public UnspecifiedNode<String> createUnspecifiedNode() {
    return new LaTeXUnspecifiedNode(lineWidth);
  }

  @Override
  public Catalysis<String> createCatalysis() {
    return new LaTeXCatalysis(arrowScale);
  }

  @Override
  public Consumption<String> createConsumption() {
    return new LaTeXConsumption();
  }

  @Override
  public ReversibleConsumption<String> createReversibleConsumption() {
    return new LaTeXReversibleConsumption(arrowScale);
  }

  @Override
  public Inhibition<String> createInhibition() {
    return new LaTeXInhibition(arrowScale);
  }

  @Override
  public Modulation<String> createModulation() {
    return new LaTeXModulation(arrowScale);
  }

  @Override
  public NecessaryStimulation<String> createNecessaryStimulation() {
    return new LaTeXNecessaryStimulation(arrowScale);
  }

  @Override
  public Production<String> createProduction() {
    return new LaTeXProduction(arrowScale);
  }

  @Override
  public Stimulation<String> createStimulation() {
    return new LaTeXStimulation(arrowScale);
  }
}
