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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.xml.parsers.QualParser;

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
	private Model model;
	
	public QualitativeModel(Model model) {
	  super();
		this.model = model;
	}


	/**
	 * @param listOfQualitativeSpecies the listOfQualitativeSpecies to set
	 */
	public void addQualitativeSpecies(QualitativeSpecies qualitativeSpecies) {
		getListOfQualitativeSpecies().add(qualitativeSpecies);
		
	}
	
	
  public void addTransition(Transition transition) {
		getListOfTransitions().add(transition);
	}


  
  public QualitativeModel clone() {
		// TODO
		return null;
	}


  
  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  public boolean getAllowsChildren() {
    return true;
  }


  /* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public SBase getChildAt(int childIndex) {
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
	
	/**
   * Creates a new {@link Transition} inside this {@link QualitativeModel} and returns it.
   * <p>
   * 
   * @return the {@link Transition} object created
   *         <p>
   * @see #addTransition(Transition r)
   */
  public Transition createTransition() {
    return createTransition(null);
  }
	
	/**
   * Creates a new {@link Transition} inside this {@link QualitativeModel} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link Transition} object created
   */
  public Transition createTransition(String id) {
    Transition transition = new Transition(id, getModel().getLevel(), getModel().getVersion());
    addTransition(transition);
    
    return transition;
  }
  
  public Transition createTransition(String id, Input in, Output out) {
    Transition transition = createTransition(id);
    transition.addInput(in);
    transition.addOutput(out);
    
    return transition;
  }


  /**
   * 
   * @param id
   * @param sign
   * @param in
   * @param out
   * @return
   */
  public Transition createTransition(String id, Sign sign, Input in,
    Output out) {
    Transition transition = new Transition(id, getModel().getLevel(),
      getModel().getVersion());
    transition.addInput(in);
    transition.addOutput(out);
    addTransition(transition);    
    return transition;
  }

  /**
   * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
   * <p>
   * 
   * @return the {@link QualitativeSpecies} object created
   *         <p>
   * @see #addSpecies(QualitativeSpecies s)
   */
  public QualitativeSpecies createSpecies() {
    return createSpecies(null);
  }
  
  /**
   * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link QualitativeSpecies} object created
   */
  public QualitativeSpecies createSpecies(String id) {
    QualitativeSpecies species = new QualitativeSpecies(id);
    addSpecies(species);
    return species;
  }
  
  /**
   * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link QualitativeSpecies} object created
   */
  public QualitativeSpecies createSpecies(String id, boolean boundaryCondition, String compartment, boolean constant) {
    QualitativeSpecies species = new QualitativeSpecies(id,
      getModel().getLevel(), getModel().getVersion());
    species.setBoundaryCondition(boundaryCondition);
    species.setCompartment(compartment);
    species.setConstant(constant);
    addSpecies(species);
    return species;
  }
  
  /**
   * Adds a Species instance to the listOfSpecies of this Model.
   * 
   * @param spec
   * @return <code>true</code> if the {@link #listOfSpecies} was changed as a
   *         result of this call.
   */
  public boolean addSpecies(QualitativeSpecies spec) {
    return getListOfQualitativeSpecies().add(spec);
  }
  
  /**
   * Creates a new {@link Species} inside this {@link Model} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @param c
   *        the Compartment of the new {@link Species}
   * @return the {@link Species} object created
   */
  public QualitativeSpecies createSpecies(String id, Compartment c) {
    QualitativeSpecies s = createSpecies(id);
    s.setCompartment(c);
    return s;
  }
  
	/**
	 * @return the listOfQualitativeSpecies
	 */
	public ListOf<QualitativeSpecies> getListOfQualitativeSpecies() {
	  if (!isSetListOfQualitativeSpecies()) {
      listOfQualitativeSpecies = new ListOf<QualitativeSpecies>(
          getModel().getLevel(), getModel().getVersion());
		listOfQualitativeSpecies.addNamespace(QualParser.getNamespaceURI());
		model.registerChild(listOfQualitativeSpecies);
		listOfQualitativeSpecies.setSBaseListType(ListOf.Type.other);
	  }
		return listOfQualitativeSpecies;
	}
	
	
	/**
	 * @return the listOTransitions
	 */
	public ListOf<Transition> getListOfTransitions() {
	  if (!isSetListOfTransitions()) {
      listOfTransitions = new ListOf<Transition>(getModel().getLevel(),
          getModel().getVersion());
	    listOfTransitions.addNamespace(QualParser.getNamespaceURI());
		model.registerChild(listOfTransitions);
		listOfTransitions.setSBaseListType(ListOf.Type.other);

	  }
		return listOfTransitions;
	}



	public Model getModel() {
    return model;
  }

	public QualitativeSpecies getQualitativeSpecies(int i) {
		if ((i >= 0) && (i < listOfQualitativeSpecies.size())) {
			return listOfQualitativeSpecies.get(i);
		}

		return null;
	}

	public Transition getTransition(int i) {
		if ((i >= 0) && (i < listOfTransitions.size())) {
			return listOfTransitions.get(i);
		}

		return null;
	}


	public boolean isSetListOfQualitativeSpecies() {
		if ((listOfQualitativeSpecies == null) || listOfQualitativeSpecies.isEmpty()) {
			return false;			
		}		
		return true;
	}
	
	
	public boolean isSetListOfTransitions() {
		if ((listOfTransitions == null) || listOfTransitions.isEmpty()) {
			return false;			
		}		
		return true;
	}
	
	public boolean readAttribute(String attributeName, String prefix, String value) {
	  // no attribute to read
		return false;
	}
	
	public void setListOfQualitativeSpecies(
    ListOf<QualitativeSpecies> listOfQualitativeSpecies) {
    this.listOfQualitativeSpecies = listOfQualitativeSpecies;
    model.registerChild(this.listOfQualitativeSpecies);
  }
	
	public void setListOfTransitions(ListOf<Transition> listOfTransitions) {
    this.listOfTransitions = listOfTransitions;
    model.registerChild(this.listOfTransitions);
  }


  public Map<String, String> writeXMLAttributes() {
	  // no attribute to read
	  return null;
	}
  
  public boolean unsetListOfTransitions(){
    if(isSetListOfTransitions()) {
      this.listOfTransitions = null;
      model.registerChild(this.listOfTransitions);
      return true;
    }
    return false;
  }
  
  public boolean unsetListOfQualitativeSpecies() {
    if(isSetListOfQualitativeSpecies()) {
      this.listOfQualitativeSpecies = null;
      model.registerChild(this.listOfQualitativeSpecies);
      return true;
    }
    return false;
  }

}
