/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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
 * @author Nicolas Rodriguez
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
	private ListOf<SpeciesTypeInstance> listOfSpeciesTypeInstances;

	private Species species; // Do we need this anymore ?
	
	/**
	 * 
	 * @param species
	 */
	public MultiSpecies(Species species) {
		this.species = species;
	}


	public MultiSpecies clone() {
		// TODO
		return null;
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

	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param speciesTypeInstance
	 */
	public void addSpeciesTypeInstance(SpeciesTypeInstance speciesTypeInstance) {
		getListOfSpeciesTypeInstances().add(speciesTypeInstance);		
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public SpeciesTypeInstance getSpeciesTypeInstance(int n) {
		return getListOfSpeciesTypeInstances().get(n);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesTypeInstance getInitialSpeciesInstance(String id) {
		if (isSetListOfSpeciesInstances()) {
			for (SpeciesTypeInstance comp : listOfSpeciesTypeInstances) {
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
	public ListOf<SpeciesTypeInstance> getListOfSpeciesTypeInstances() {
		if (listOfSpeciesTypeInstances == null) {
			this.listOfSpeciesTypeInstances = new ListOf<SpeciesTypeInstance>();
		}

		return listOfSpeciesTypeInstances;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfSpeciesInstances() {
		return (listOfSpeciesTypeInstances != null)
				&& (listOfSpeciesTypeInstances.size() > 0); // TODO : should we do that or not (and in general for other listOf that should not be empty) ?
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		// TODO 
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		return getClass().getSimpleName();
	}

	/**
	 * Removes the {@link #listOfSpeciesTypeInstances} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return {@code true} if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfInitialSpeciesInstances() {
		// TODO : check if we need to do any additional call to have everything properly unregistered
		if (this.listOfSpeciesTypeInstances != null) {
			ListOf<SpeciesTypeInstance> oldListOfInitialSpeciesInstances = this.listOfSpeciesTypeInstances;
			this.listOfSpeciesTypeInstances = null;
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


  public SpeciesTypeInstance createSpeciesTypeInstance(String id) {
	SpeciesTypeInstance sti = new SpeciesTypeInstance();
	sti.setId(id);
	addSpeciesTypeInstance(sti);
	
	return sti;
}

}
