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

import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;

/**
 * RoleToSBO provides a method to get the SBO term for a species reference role
 * constant by using a pre-defined reference map.
 * 
 * @author Jakob Matthes
 * @version $Rev$
 */
public class RoleToSBO {

	private static HashMap<SpeciesReferenceRole, Integer> referenceMap = filledReferenceMap();
			
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
	 * Get the SBO term for the given species reference role by look-up in the
	 * reference map.
	 * 
	 * @param role the {@link SpeciesReferenceRole} for which to look up
	 * @return corresponding SBO term
	 */
	public static Integer getSBO(SpeciesReferenceRole role) {
		return referenceMap.get(role);
	}

}
