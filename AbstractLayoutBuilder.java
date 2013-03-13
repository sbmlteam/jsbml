/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2013 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */

package de.zbit.sbml.layout;

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
 * {@link LayoutFactory}. It implements the methods {@link #getSBGNNode(int)} and {@link #getSBGNArc(int)}
 * to get the node/arc for a specific SBO term or a specific species reference
 * role.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
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
	 * 
	 * @param srg
	 * @param reactionGlyph
	 * @return
	 */
	public SBGNArc<ArcT> createArc(SpeciesReferenceGlyph srg,
		ReactionGlyph reactionGlyph) {
		logger.fine(MessageFormat.format("building arc from srgId={0} to rgId={1}", srg.getId(), reactionGlyph.getId()));
		int sboTerm = -1;
		SpeciesReferenceRole speciesReferenceRole = null;
		SBGNArc<ArcT> arc = null;
		if (srg.isSetSpeciesReferenceRole()) {
			speciesReferenceRole = srg.getSpeciesReferenceRole();
			sboTerm = speciesReferenceRole.toSBOterm();
			if (srg.isSetSpeciesReference()) {
				NamedSBase specRef = srg.getSpeciesReferenceInstance();
				if (specRef.isSetSBOTerm() && SBO.isChildOf(specRef.getSBOTerm(), sboTerm)) {
					sboTerm = specRef.getSBOTerm();
				}
			}
			logger.fine("SRG role " + speciesReferenceRole);
		} else {
			sboTerm = srg.getSBOTerm();
			logger.fine("SRG sbo term " + sboTerm);
		}
		arc = getSBGNArc(sboTerm);
		
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
		}

		return arc;
	}
	
	/**
	 * helping method that creates the correct node depending on the sbo term of
	 * the corresponding species
	 * 
	 * @param sboTerm of the corresponding species
	 * @return correct SBGNNode
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
	 * helping method that creates the correct arc depending on the sbo term of
	 * the connection
	 * 
	 * @param sboTerm of the corresponding connecting arc
	 * @return correct arc
	 */
	public SBGNArc<ArcT> getSBGNArc(int sboTerm) {
		if (SBO.isChildOf(sboTerm, SBO.getConsumption()) || SBO.isChildOf(sboTerm, SBO.getReactant())) {
			return createConsumption();
		} else if (SBO.isChildOf(sboTerm, SBO.getProduction()) || SBO.isChildOf(sboTerm, SBO.getProduct())) {
			return createProduction();
		} else if (SBO.isChildOf(sboTerm, SBO.getCatalysis()) || SBO.isChildOf(sboTerm, SBO.getCatalyst())) {
			return createCatalysis();
		} else if (SBO.isChildOf(sboTerm, SBO.getInhibition()) || SBO.isChildOf(sboTerm, SBO.getInhibitor())) {
			return createInhibition();
		} else if (SBO.isChildOf(sboTerm, SBO.getModulation()) || SBO.isChildOf(sboTerm, SBO.getModifier())) {
			return createModulation();
		} else if (SBO.isChildOf(sboTerm, SBO.getNecessaryStimulation()) || SBO.isChildOf(sboTerm, SBO.getTrigger())) {
			return createNecessaryStimulation();
		} else if (SBO.isChildOf(sboTerm, SBO.getStimulation()) || SBO.isChildOf(sboTerm, SBO.getStimulator()) || SBO.isChildOf(sboTerm, SpeciesReferenceRole.ACTIVATOR.toSBOterm())) {
			return createStimulation();
		}
		//default type of the connecting arc is a consumption, a line without special line ending
		//if the SBO term does not match any of the above numbers
		return createConsumption();
	}
	
	/**
	 * 
	 * @param referenceRole
	 * @return
	 */
	public SBGNArc<ArcT> getSBGNArc(SpeciesReferenceRole referenceRole) {
		return getSBGNArc(referenceRole.toSBOterm());		
	}
	
	/**
	 * helping method that creates the correct node depending on the sbo term of
	 * the corresponding reaction
	 * 
	 * @param sboTerm of the reaction
	 * @return SBGNNode
	 */
	public SBGNReactionNode<NodeT> getSBGNReactionNode(int sboTerm) {
		// reaction is an omitted process
		if (SBO.isChildOf(sboTerm, 397)){
			return createOmittedProcessNode();
		}
		
		// reaction is an uncertain process
		if (SBO.isChildOf(sboTerm, 396)) {
			return createUncertainProcessNode();
		}
		
		// reaction is a dissociation
		if (SBO.isChildOf(sboTerm, 180)) {
			return createDissociationNode();
		}
		
		//reaction is an association / non-covalent binding
		if (SBO.isChildOf(sboTerm, 177)) {
			return createAssociationNode();
		}
		
		// default is the process node
		return createProcessNode();
	}

	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.LayoutBuilder#isProductReady()
	 */
	public boolean isProductReady() {
		return terminated;
	}
	
}
