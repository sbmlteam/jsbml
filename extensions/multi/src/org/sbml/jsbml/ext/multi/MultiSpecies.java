/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * 
 * @author Marine Dumousseau
 * @since 0.8
 * @version $Rev$
 */
public class MultiSpecies extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5396837209115412420L;
	/**
	 * 
	 */
	private ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstances;

	/**
	 * 
	 * @param species
	 */
	public MultiSpecies(MultiSpecies species) {
		super(species);
		this.setListOfInitialSpeciesInstance(null);
	}
	
	/**
	 * 
	 */
	public MultiSpecies() {
		super();
		this.setListOfInitialSpeciesInstance(null);
	}

	/**
	 * 
	 * @param initialSpecies
	 */
	public void addInitialSpeciesInstance(InitialSpeciesInstance initialSpecies) {
		if (listOfInitialSpeciesInstances == null) {
			this.listOfInitialSpeciesInstances = new ListOf<InitialSpeciesInstance>();
		}
		if (!listOfInitialSpeciesInstances.contains(initialSpecies)) {
			initialSpecies.setParentSBML(this);
			listOfInitialSpeciesInstances.add(initialSpecies);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public AbstractSBase clone() {
		return new MultiSpecies(this);
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public InitialSpeciesInstance getInitialSpeciesInstance(int n) {
		if (isSetListOfSpeciesInstances()) {
			return listOfInitialSpeciesInstances.get(n);
		}
		throw new IndexOutOfBoundsException(Integer.toString(n));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public InitialSpeciesInstance getInitialSpeciesInstance(String id) {
		if (isSetListOfSpeciesInstances()) {
			for (InitialSpeciesInstance comp : listOfInitialSpeciesInstances) {
				if (comp.getId().equals(id)) {
					return comp;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<InitialSpeciesInstance> getListOfInitialSpeciesInstance() {
		return listOfInitialSpeciesInstances;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfSpeciesInstances() {
		return (listOfInitialSpeciesInstances != null)
				&& (listOfInitialSpeciesInstances.size() > 0);
	}
	
	/**
	 * 
	 * @param listOfInitialSpeciesInstance
	 */
	public void setListOfInitialSpeciesInstance(
			ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstance) {
		unsetListOfInitialSpeciesInstances();
		this.listOfInitialSpeciesInstances = listOfInitialSpeciesInstance;
		if ((this.listOfInitialSpeciesInstances != null) && (this.listOfInitialSpeciesInstances.getSBaseListType() != ListOf.Type.other)) {
			this.listOfInitialSpeciesInstances.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(this.listOfInitialSpeciesInstances);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		return getClass().getSimpleName();
	}

	/**
	 * Removes the {@link #listOfInitialSpeciesInstances} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfInitialSpeciesInstances() {
		if (this.listOfInitialSpeciesInstances != null) {
			ListOf<InitialSpeciesInstance> oldListOfInitialSpeciesInstances = this.listOfInitialSpeciesInstances;
			this.listOfInitialSpeciesInstances = null;
			oldListOfInitialSpeciesInstances.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

}
