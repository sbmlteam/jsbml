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
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class QualitativeModel extends AbstractSBasePlugin {

  private static final long serialVersionUID = 1861588578911387944L;
  
  private ListOf<QualitativeSpecies> listOfQualitativeSpecies;
	private ListOf<Transition> listOfTransitions;

	
	public QualitativeModel(Model model) {
	  super();
		setParent(model);
		addAllChangeListeners(model.getListOfTreeNodeChangeListeners());
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
	  if (!isSetListOfQualitativeSpecies()) {
	    // TODO: initialize the ListOf correctly
	    listOfQualitativeSpecies = new ListOf<QualitativeSpecies>();
	  }
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
		getListOfQualitativeSpecies().add(qualitativeSpecies);
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
	  if (!isSetListOfTransitions()) {
      // TODO: initialize the ListOf correctly
	    listOfTransitions = new ListOf<Transition>();
	  }
		return listOfTransitions;
	}

	public Transition getTransition(int i) {
		if ((i >= 0) && (i < listOfTransitions.size())) {
			return listOfTransitions.get(i);
		}

		return null;
	}

	public void addTransition(Transition transition) {
		getListOfTransitions().add(transition);
	}


	public QualitativeModel clone() {
		// TODO
		return null;
	}
	
	
	public boolean readAttribute(String attributeName, String prefix, String value) {
	  // TODO:
		return false;
	}
	
	public Map<String, String> writeXMLAttributes() {
	  // TODO
		return null;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
      
    int pos = 0;
    if (isSetListOfQualitativeSpecies()) {
      if (pos == childIndex) {
        return getListOfQualitativeSpecies();
      }
      pos++;
    }
    if (isSetListOfTransitions()) {
      if (pos == childIndex) {
        return getListOfTransitions();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
        childIndex, +((int) Math.min(pos, 0))));	  
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount() {
    int count = 0;
    if (isSetListOfQualitativeSpecies()) {
      count++;
    }
    if (isSetListOfTransitions()) {
      count++;
    }
    return count;
  }
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#getParent()
	 */
	public Model getParent() {
		return (Model)super.getParent();
	}


  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  public boolean getAllowsChildren() {
    return true;
  }
}
