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
package org.sbml.jsbml.ext.qual;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBasePlugin;

/**
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class QualitativeModel implements SBasePlugin {

	private ListOf<QualitativeSpecies> listOfQualitativeSpecies = new ListOf<QualitativeSpecies>();
	private ListOf<Transition> listOfTransitions = new ListOf<Transition>();

	private Model model;

	
	public QualitativeModel(Model model) {
		this.model = model;
	}


	public boolean isSetListOfQualitativeSpecies() {
		if ((listOfQualitativeSpecies == null) || listOfQualitativeSpecies.isEmpty()) {
			return false;			
		}		
		return true;
	}

	
	/**
	 * @return the listOfQualitativeSpecies
	 */
	public ListOf<QualitativeSpecies> getListOfQualitativeSpecies() {
		return listOfQualitativeSpecies;
	}

	public QualitativeSpecies getQualitativeSpecies(int i) {
		if ((i >= 0) && (i < listOfQualitativeSpecies.size())) {
			return listOfQualitativeSpecies.get(i);
		}

		return null;
	}

	/**
	 * @param listOfQualitativeSpecies the listOfQualitativeSpecies to set
	 */
	public void addQualitativeSpecies(QualitativeSpecies qualitativeSpecies) {
		this.listOfQualitativeSpecies.add(qualitativeSpecies);
	}
	
	
	public boolean isSetListOfTransitions() {
		if ((listOfTransitions == null) || listOfTransitions.isEmpty()) {
			return false;			
		}		
		return true;
	}



	/**
	 * @return the listOTransitions
	 */
	public ListOf<Transition> getListOfTransitions() {
		return listOfTransitions;
	}

	public Transition getTransition(int i) {
		if ((i >= 0) && (i < listOfTransitions.size())) {
			return listOfTransitions.get(i);
		}

		return null;
	}

	public void addTransition(Transition transition) {
		this.listOfTransitions.add(transition);
	}


	public QualitativeModel clone() {
		// TODO
		return null;
	}
	
	
	public boolean readAttribute(String attributeName, String prefix, String value) {
		return false;
	}
	
	public Map<String, String> writeXMLAttributes() {
		return null;
	}
	
	public TreeNode getChildAt(int childIndex) {
		// TODO
		return null;
	}
	
	public int getChildCount() {
		// TODO
		return 0;
	}
	
	public TreeNode getParent() {
		return model;
	}
}
