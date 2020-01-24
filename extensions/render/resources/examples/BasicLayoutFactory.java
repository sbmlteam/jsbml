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
package examples;

import org.sbml.jsbml.ext.render.director.AssociationNode;
import org.sbml.jsbml.ext.render.director.Catalysis;
import org.sbml.jsbml.ext.render.director.Compartment;
import org.sbml.jsbml.ext.render.director.Consumption;
import org.sbml.jsbml.ext.render.director.DissociationNode;
import org.sbml.jsbml.ext.render.director.Inhibition;
import org.sbml.jsbml.ext.render.director.LayoutFactory;
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

/**
 * Factory-class for creating the various drawing-specialists. This class could 
 * also hold further information like drawing-colours for the Nodes etc
 * 
 * @author David Vetter
 */
public class BasicLayoutFactory implements LayoutFactory<String, String> {

  private double lineWidth = 1;
  private double arrowScale = 2;
  private double reactionNodeSize = 10;  // <- TODO: into constructor.
  
  /**
   * 
   * @param lineWidth the width of lines in the resulting drawing; in pt
   * @param arrowScale the scale of arrow-heads
   */
  public BasicLayoutFactory(double lineWidth, double arrowScale, double reactionNodeSize) {
    this.lineWidth = lineWidth;
    this.arrowScale = arrowScale;
    this.reactionNodeSize = reactionNodeSize;
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
