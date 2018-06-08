/*
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2018 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render.director;


/**
 * Interface that defines methods to create and return the different types of
 * nodes and arcs.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.4
 * @param <NodeT>
 * @param <ArcT>
 */
public interface LayoutFactory<NodeT,ArcT> {
  
  /**
   * @return an {@link AssociationNode}
   */
  public AssociationNode<NodeT> createAssociationNode();
  
  /**
   * @return a {@link Compartment}
   */
  public Compartment<NodeT> createCompartment();
  
  /**
   * @return a {@link DissociationNode}
   */
  public DissociationNode<NodeT> createDissociationNode();
  
  /**
   * @return a {@link Macromolecule}
   */
  public Macromolecule<NodeT> createMacromolecule();
  
  /**
   * @return a {@link NucleicAcidFeature}
   */
  public NucleicAcidFeature<NodeT> createNucleicAcidFeature();
  
  /**
   * @return a {@link OmittedProcessNode}
   */
  public OmittedProcessNode<NodeT> createOmittedProcessNode();
  
  /**
   * @return a {@link PerturbingAgent}
   */
  public PerturbingAgent<NodeT> createPerturbingAgent();
  
  /**
   * @return a {@link ProcessNode}
   */
  public ProcessNode<NodeT> createProcessNode();
  
  /**
   * @return a {@link SimpleChemical}
   */
  public SimpleChemical<NodeT> createSimpleChemical();
  
  /**
   * @return a {@link SourceSink}
   */
  public SourceSink<NodeT> createSourceSink();
  
  /**
   * @return an {@link UncertainProcessNode}
   */
  public UncertainProcessNode<NodeT> createUncertainProcessNode();
  
  /**
   * @return a {@link UnspecifiedNode}
   */
  public UnspecifiedNode<NodeT> createUnspecifiedNode();
  
  /**
   * @return a {@link Catalysis}
   */
  public Catalysis<ArcT> createCatalysis();
  
  /**
   * @return a {@link Consumption}
   */
  public Consumption<ArcT> createConsumption();
  
  /**
   * 
   * @return a {@link Consumption}
   */
  public ReversibleConsumption<ArcT> createReversibleConsumption();
  
  /**
   * @return a {@link Inhibition}
   */
  public Inhibition<ArcT> createInhibition();
  
  /**
   * @return a {@link Modulation}
   */
  public Modulation<ArcT> createModulation();
  
  /**
   * @return a {@link NecessaryStimulation}
   */
  public NecessaryStimulation<ArcT> createNecessaryStimulation();
  
  /**
   * @return a {@link Production}
   */
  public Production<ArcT> createProduction();
  
  /**
   * @return a {@link Stimulation}
   */
  public Stimulation<ArcT> createStimulation();
  
}
