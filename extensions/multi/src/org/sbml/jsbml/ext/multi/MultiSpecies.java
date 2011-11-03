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

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * 
 * @author Marine Dumousseau
 * @since 1.0
 * @version $Rev$
 */
public class MultiSpecies extends AbstractSBasePlugin {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5396837209115412420L;
	/**
	 * 
	 */
	private ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstances;

	private Species species;
	
	/**
	 * 
	 */
	public MultiSpecies() {
		super();
		this.setListOfInitialSpeciesInstance(null);
	}
	
	/**
	 * 
	 * @param species
	 */
	public MultiSpecies(MultiSpecies species) {
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
			initialSpecies.setParentSBML(species);
			listOfInitialSpeciesInstances.add(initialSpecies);
		}
	}


	public MultiSpecies clone() {
		return new MultiSpecies(this);
	}

	/* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  public boolean getAllowsChildren() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
   */
	public SBase getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
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

	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfSpeciesInstances() {
		return (listOfInitialSpeciesInstances != null)
				&& (listOfInitialSpeciesInstances.size() > 0);
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		// TODO Auto-generated method stub
		return false;
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
		species.setThisAsParentSBMLObject(this.listOfInitialSpeciesInstances);
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
	 */
  public Map<String, String> writeXMLAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
