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

import java.text.MessageFormat;
import java.util.logging.Logger;

import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;

/**
 * This abstract class combines the interfaces {@link LayoutBuilder} and
 * {@link LayoutFactory}. It implements the methods {@link #getSBGNNode},
 * {@link #getSBGNArc} and {@link #getSBGNReactionNode(int)} to get the node,
 * arc or process node for a specific SBO term or a specific species reference
 * role.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.4
 * @param <P>
 * @param <NodeT>
 * @param <ArcT>
 */
public abstract class AbstractLayoutBuilder<P, NodeT, ArcT> implements LayoutBuilder<P>, LayoutFactory<NodeT,ArcT> {
  
  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(AbstractLayoutBuilder.class.getName());
  
  /**
   * Indicates whether graph generation has finished or not.
   */
  protected boolean terminated = false;
  
  /**
   * Create a {@link SBGNArc} for the given {@link SpeciesReferenceGlyph} and
   * {@link ReactionGlyph}.
   * 
   * The decision which arc to produce is based on the role (priority) or the
   * SBO term of the {@link SpeciesReferenceGlyph}.
   * 
   * @param srg the {@link SpeciesReferenceGlyph} of the arc
   * @param reactionGlyph the {@link ReactionGlyph} of the arc
   * @return a {@link SBGNArc} for the two given objects
   */
  public SBGNArc<ArcT> createArc(SpeciesReferenceGlyph srg,
    ReactionGlyph reactionGlyph) {
    logger.fine(MessageFormat.format("building arc from srgId=''{0}'' to rgId=''{1}''", srg.getId(), reactionGlyph.getId()));
    int sboTerm = -1;
    SpeciesReferenceRole speciesReferenceRole = null;
    SBGNArc<ArcT> arc = null;
    if (srg.isSetSpeciesReferenceRole()) {
      speciesReferenceRole = srg.getSpeciesReferenceRole();
      sboTerm = speciesReferenceRole.toSBOterm();
      if (srg.isSetSpeciesReference()) {
        NamedSBase specRef = srg.getSpeciesReferenceInstance();
        if (specRef == null) {
          logger.fine(MessageFormat.format(
            "Encountered undefined identifier ''{0}'' in speciesReferenceGlyph ''{1}''.",
            srg.getReference(), srg.getId()));
        } else if (specRef.isSetSBOTerm() && SBO.isChildOf(specRef.getSBOTerm(), sboTerm)) {
          sboTerm = specRef.getSBOTerm();
        }
      }
      logger.fine("SRG role " + speciesReferenceRole);
    } else {
      sboTerm = srg.getSBOTerm();
      logger.fine("SRG sbo term " + sboTerm);
    }
    
    Reaction reaction = (Reaction) reactionGlyph.getReactionInstance();
    boolean reactionIsReversible = (reaction != null) &&
        reaction.isSetReversible() && reaction.isReversible();
    
    if (reactionIsReversible &&
        (((sboTerm != -1) && SBO.isChildOf(sboTerm, SBO.getConsumption())) ||
            ((speciesReferenceRole != null) &&
                ((speciesReferenceRole == SpeciesReferenceRole.SUBSTRATE) ||
                    (speciesReferenceRole == SpeciesReferenceRole.SIDESUBSTRATE))))) {
      // consumption in reversible reaction needs an arrow
      arc = createReversibleConsumption();
    } else {
      arc = getSBGNArc(sboTerm);
    }
    
    return arc;
  }
  
  /**
   * Create the correct {@link SBGNNode} according to the given SBO term.
   * 
   * @param sboTerm of the corresponding species
   * @return correct {@link SBGNNode} according to the SBO term
   */
  public SBGNNode<NodeT> getSBGNNode(int sboTerm) {
    
    // simple chemical, 247
    if (SBO.isChildOf(sboTerm, SBO.getSimpleMolecule())) {
      return createSimpleChemical();
    }
    
    // ion
    if (SBO.isChildOf(sboTerm, SBO.getIon())) {
      return createSimpleChemical();
    }
    
    // macromolecule, 245
    if (SBO.isChildOf(sboTerm, SBO.getMacromolecule())) {
      return createMacromolecule();
    }
    
    // nucleic acid features
    // TODO not only genes are nucleic acid features
    if (SBO.isChildOf(sboTerm, SBO.getGene())) {
      return createNucleicAcidFeature();
    }
    
    // source or sink, 291
    if (SBO.isChildOf(sboTerm, SBO.getEmptySet())) {
      return createSourceSink();
    }
    
    // unspecified entity, 285
    if (SBO.isChildOf(sboTerm, SBO.getUnknownMolecule())) {
      return createUnspecifiedNode();
    }
    
    // physical compartment, 290
    if (SBO.isChildOf(sboTerm, SBO.getCompartment())) {
      return createCompartment();
    }
    
    if (SBO.isChildOf(sboTerm, SBO.getPertubingAgent())) {
      return createPerturbingAgent();
    }
    
    //default type of the entity pool node is an unspecified node
    //if the SBO term does not match any of the above numbers
    return createUnspecifiedNode();
    
  }
  
  /**
   * Creates the correct {@link SBGNArc} according to the given SBO term.
   * 
   * @param sboTerm of the corresponding connecting arc
   * @return correct {@link SBGNArc} according to the SBO term
   */
  public SBGNArc<ArcT> getSBGNArc(int sboTerm) {
    if (SBO.isChildOf(sboTerm, SBO.getConsumption()) ||
        SBO.isChildOf(sboTerm, SBO.getReactant())) {
      return createConsumption();
    } else if (SBO.isChildOf(sboTerm, SBO.getProduction()) ||
        SBO.isChildOf(sboTerm, SBO.getProduct())) {
      return createProduction();
    } else if (SBO.isChildOf(sboTerm, SBO.getCatalysis()) ||
        SBO.isChildOf(sboTerm, SBO.getCatalyst())) {
      return createCatalysis();
    } else if (SBO.isChildOf(sboTerm, SBO.getInhibition()) ||
        SBO.isChildOf(sboTerm, SBO.getInhibitor())) {
      return createInhibition();
    } else if (SBO.isChildOf(sboTerm, SBO.getNecessaryStimulation()) ||
        SBO.isChildOf(sboTerm, SBO.getTrigger())) {
      return createNecessaryStimulation();
    } else if (SBO.isChildOf(sboTerm, SBO.getStimulation()) ||
        SBO.isChildOf(sboTerm, SBO.getStimulator()) ||
        SBO.isChildOf(sboTerm, SpeciesReferenceRole.ACTIVATOR.toSBOterm())) {
      return createStimulation();
    } else if (SBO.isChildOf(sboTerm, SBO.getModulation()) ||
        SBO.isChildOf(sboTerm, SBO.getModifier())) {
      return createModulation();
    }
    
    // default type of the connecting arc is a consumption, a line without
    // special line ending
    return createConsumption();
  }
  
  /**
   * Creates the correct {@link SBGNArc} according to the given
   * {@link SpeciesReferenceRole}. The conversion of
   * {@link SpeciesReferenceRole} to SBO term is performed by the class
   * {@link SpeciesReferenceRole}.
   * 
   * @param speciesReferenceRole of the corresponding connecting arc
   * @return correct {@link SBGNArc} according to the SBO term
   */
  public SBGNArc<ArcT> getSBGNArc(SpeciesReferenceRole speciesReferenceRole) {
    return getSBGNArc(speciesReferenceRole.toSBOterm());
  }
  
  /**
   * Create the correct process node according to the SBO term of the
   * reaction.
   * 
   * @param sboTerm of the reaction
   * @return correct {@link SBGNProcessNode} for the process
   */
  public SBGNProcessNode<NodeT> getSBGNReactionNode(int sboTerm) {
    // reaction is an omitted process
    if (SBO.isChildOf(sboTerm, SBO.getTransitionOmitted())) {
      return createOmittedProcessNode();
    }
    
    // reaction is an uncertain process
    if (SBO.isChildOf(sboTerm, SBO.getUnknownTransition())) {
      return createUncertainProcessNode();
    }
    
    // reaction is a dissociation
    if (SBO.isChildOf(sboTerm, SBO.getDissociation())) {
      return createDissociationNode();
    }
    
    // reaction is an association / non-covalent binding
    if (SBO.isChildOf(sboTerm, SBO.getAssociation())) {
      return createAssociationNode();
    }
    
    // default is the process node
    return createProcessNode();
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.LayoutBuilder#isProductReady()
   */
  @Override
  public boolean isProductReady() {
    return terminated;
  }
  
}
