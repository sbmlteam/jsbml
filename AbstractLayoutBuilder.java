/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2012 by the University of Tuebingen, Germany.
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

import java.util.HashMap;

import org.sbml.jsbml.SBO;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;

/**
 * abstract class combines the interfaces LayoutBuilder and LayoutFactory and
 * implements two methods for getting node or arc object of a specific type
 * corresponding to a given sbo term
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev: 135 $
 */
public abstract class AbstractLayoutBuilder<P> implements LayoutBuilder<P>, LayoutFactory {
	
	private static HashMap<SpeciesReferenceRole, Integer> RoleToSBOTerm = filledReferenceMap();
			
	private static HashMap<SpeciesReferenceRole, Integer> filledReferenceMap(){
		HashMap<SpeciesReferenceRole, Integer> referenceMap = new HashMap<SpeciesReferenceRole, Integer>();
		referenceMap.put(SpeciesReferenceRole.SUBSTRATE, 394);
		referenceMap.put(SpeciesReferenceRole.SIDESUBSTRATE, 394);
		referenceMap.put(SpeciesReferenceRole.UNDEFINED, 394);
		referenceMap.put(SpeciesReferenceRole.PRODUCT, 393);
		referenceMap.put(SpeciesReferenceRole.SIDEPRODUCT, 393);
		referenceMap.put(SpeciesReferenceRole.ACTIVATOR, 172);
		referenceMap.put(SpeciesReferenceRole.MODIFIER, 172);
		referenceMap.put(SpeciesReferenceRole.INHIBITOR, 169);
		return referenceMap;
	}

	/**
	 * helping method that creates the correct node depending on the sbo term of
	 * the corresponding species
	 * 
	 * @param sboTerm of the corresponding species
	 * @return correct SBGNNode
	 */
	public SBGNNode getSBGNNode(int sboTerm) {
		
		// simple chemical, 247
		if (SBO.isChildOf(sboTerm, SBO.getSimpleMolecule())) {
			return createSimpleChemical();
		}
		
		// macromolecule, 245
		if (SBO.isChildOf(sboTerm, SBO.getMacromolecule())) {
			return createMacromolecule();
		}
		
		// source or sink, 291
		if (SBO.isChildOf(sboTerm, SBO.getEmptySet())) {
			return createSourceSink();
		}
		
		// unspecified entity, 285
		if (SBO.isChildOf(sboTerm, SBO.getUnknownMolecule())) {
			return createUnspecifiedNode();
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
	public SBGNArc getSBGNArc(int sboTerm) {
		
		// consumption, 394
		if (SBO.isChildOf(sboTerm, 394)) {
			return createConsumption();
		}
		
		// production, 393
		if (SBO.isChildOf(sboTerm, 393)) {
			return createProduction();
		}
		
		// catalysis, 172
		if (SBO.isChildOf(sboTerm, 172)) {
			return createCatalysis();
		}
		
		// inhibition, 169
		if (SBO.isChildOf(sboTerm, 169)) {
			return createInhibition();
		}
		
		//default type of the connecting arc is a consumption, a line without special line ending
		//if the SBO term does not match any of the above numbers
		return createConsumption();
		
	}
	
	public SBGNArc getSBGNArc(SpeciesReferenceRole referenceRole){
		return getSBGNArc(RoleToSBOTerm.get(referenceRole));		
	}
	
}
