/*
 * $Id$
 * $URL$
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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ListOf.Type;

/**
 * 
 * @author Nicolas Rodriguez
 *
 */
public class Model extends org.sbml.jsbml.Model {

    /**
     * Generated serial version identifier
     */
    private static final long serialVersionUID = 8157495350164592563L;
    
	/**
	 * Represents the listOfSpecies subnode of a model element.
	 */
	private ListOfSpecies listOfSpecies;

	
	public Model() {
		super();
	}

	public Model(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a Model instance from a Model.
	 * 
	 * @param model
	 */
	public Model(Model model) {
		super(model); // we would need to make a cloneHelper method that we can overwrite to avoid
		              // to do unnecessary clone on all the list in the super constructor and still 
		              // get the useful stuff from the AbstracSBase constructor.
		              // But I am not sure inheritance is the way to go.
		
		// initDefaults(); TODO: add

		if (model.isSetListOfSpecies()) {
			setListOfSpecies(new ListOfSpecies(model.getListOfSpecies()));
		}
	}

	/**
	 * Creates a Model instance from a Model.
	 * 
	 * @param model
	 */
	public Model(org.sbml.jsbml.Model model) {
		super(model); // call AbstracSBase constructor
		
		// initDefaults(); TODO: add

		if (model.isSetListOfSpecies()) {
			setListOfSpecies(new ListOfSpecies(model.getListOfSpecies()));
		}
	}

	public Model(String id, int level, int version) {
		super(id, level, version);
	}

	/**
	 * Sets the listOfSpecies of this Model to 'listOfSpecies'. Automatically
	 * sets the parentSBML objects of 'listOfSpecies' to this Model.
	 * 
	 * @param listOfSpecies
	 */
	public void setListOfSpecies(ListOfSpecies listOfSpecies) {
		this.listOfSpecies = listOfSpecies;
		setThisAsParentSBMLObject(this.listOfSpecies);
		this.listOfSpecies.setSBaseListType(ListOf.Type.listOfSpecies);
	}

	/**
	 * 
	 */
	private void initListOfSpecies() {
		System.out.println("libsbml.Model: initListOfSpecies called");
		this.listOfSpecies = new ListOfSpecies(getLevel(), getVersion());
		setThisAsParentSBMLObject(this.listOfSpecies);
		this.listOfSpecies.setSBaseListType(Type.listOfSpecies);
	}
	
	/**
	 * Sets the listOfSpecies of this Model to 'listOfSpecies'. Automatically
	 * sets the parentSBML objects of 'listOfSpecies' to this Model.
	 * 
	 * @param listOfSpecies
	 */
	@Override
	public ListOfSpecies getListOfSpecies() {
		if (!isSetListOfSpecies()) {
			initListOfSpecies();
		}
		return listOfSpecies;
	}

	// TODO: check and add missing functions
	
}
