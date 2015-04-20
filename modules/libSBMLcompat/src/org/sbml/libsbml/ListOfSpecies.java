/*
 * $Id: ListOfSpecies.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/libSBMLcompat/src/org/sbml/libsbml/ListOfSpecies.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.libsbml;

import java.util.LinkedList;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeListener;

public class ListOfSpecies extends ListOf<org.sbml.jsbml.Species> {

	

    /**
     * Generated serial version identifier
     */
    private static final long serialVersionUID = 2272659548920315696L;
    
	private Type currentList;

	/**
	 * Creates a ListOfSpecies instance. By default, the list containing the Species
	 * elements is empty.
	 */
	public ListOfSpecies() {
		super();
		currentList = Type.listOfSpecies;
		listOf = new LinkedList<org.sbml.jsbml.Species>();
	}

	/**
	 * Creates a ListOfSpecies instance from a level and version. By default, the list
	 * containing the Species is empty.
	 */
	public ListOfSpecies(int level, int version) {
		super(level, version);
		currentList = Type.listOfSpecies;
		listOf = new LinkedList<org.sbml.jsbml.Species>();
	}

	/**
	 * creates a ListOfSpecies instance from a given ListOf<Species>.
	 * 
	 * @param listOfSpecies
	 */
	public ListOfSpecies(ListOf<org.sbml.jsbml.Species> listOfSpecies) {
		super(listOfSpecies.getLevel(), listOfSpecies.getVersion());
		for (org.sbml.jsbml.Species species : listOfSpecies) {
			if (species != null) {
				add(new Species(species));
			}
		}
		currentList = Type.listOfSpecies;
	}

	/**
	 * creates a ListOfSpecies instance from a given ListOfSpecies.
	 * 
	 * @param listOfSpecies
	 */
	public ListOfSpecies(ListOfSpecies listOfSpecies) {
		super(listOfSpecies.getLevel(), listOfSpecies.getVersion());
		
		for (org.sbml.jsbml.Species species : listOfSpecies) {
			if (species != null) {
				add(new Species(species));
			}
		}
		currentList = Type.listOfSpecies;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#add(int index, T element)
	 */
	public void add(int index, Species element) {
		setThisAsParentSBMLObject(element);
		listOf.add(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#add(java.lang.Object)
	 */
	public boolean add(Species e) {
		if (e.getLevel() != getLevel()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Level mismatch between {0} in L {1,number,integer} and {2} in L {3,number,integer}",
					getClass().getSimpleName(), getLevel(), e.getClass()
							.getSimpleName(), e.getLevel()));
		} else if (e.getVersion() != getVersion()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Version mismatch between {0} in V {1,number,integer} and {2} in V {3,number,integer}",
					getClass().getSimpleName(), getVersion(), e.getClass()
							.getSimpleName(), e.getVersion()));
		}
		
		// Avoid adding the same thing twice.
		if (e instanceof NamedSBase) {
			NamedSBase nsb = (NamedSBase) e;
			if (nsb.isSetId()) {
				for (SBase element : this) {
					NamedSBase elem = ((NamedSBase) element);
					if (elem.isSetId() && elem.getId().equals(nsb.getId())) {
						return false;
					}
				}
			}
		}
		for (TreeNodeChangeListener l : listOfListeners) {
			e.addTreeNodeChangeListener(l);
		}
		
		registerChild(e);
		return listOf.add(e);
	}

	// TODO: overwrite all of the methods if needed !
	
	// TODO: check and add missing functions from libsbml
	



}
