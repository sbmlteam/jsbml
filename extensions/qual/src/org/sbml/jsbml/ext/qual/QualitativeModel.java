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
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @author Clemens Wrzodek
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class QualitativeModel extends AbstractSBasePlugin {

  private static final long serialVersionUID = 1861588578911387944L;
  
  private ListOf<QualitativeSpecies> listOfQualitativeSpecies;
	private ListOf<Transition> listOfTransitions;
	
	public QualitativeModel(Model model) {
	  super(model);
	}


	/**
	 * @param listOfQualitativeSpecies the listOfQualitativeSpecies to set
	 */
	public void addQualitativeSpecies(QualitativeSpecies qualitativeSpecies) {
		getListOfQualitativeSpecies().add(qualitativeSpecies);		
	}
	
	
  public void addTransition(Transition transition) {
    if(!getListOfTransitions().contains(transition)){
      getListOfTransitions().add(transition);
    }
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
   * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
   * <p>
   * 
   * @return the {@link QualitativeSpecies} object created
   *         <p>
   * @see #addSpecies(QualitativeSpecies s)
   */
  public QualitativeSpecies createQualitativeSpecies() {
    return createQualitativeSpecies(null);
  }
  
  /**
   * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link QualitativeSpecies} object created
   */
  public QualitativeSpecies createQualitativeSpecies(String id) {
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
  public QualitativeSpecies createQualitativeSpecies(String id, boolean boundaryCondition, String compartment, boolean constant) {
    QualitativeSpecies species = new QualitativeSpecies(id,
      getModel().getLevel(), getModel().getVersion());
    species.setBoundaryCondition(boundaryCondition);
    species.setCompartment(compartment);
    species.setConstant(constant);
    addSpecies(species);
    return species;
  }
  
  /**
   * Creates a new {@link QualitativeSpecies}, based on an existing {@link Species},
   * adds it to this {@link QualitativeModel} and returns it.
   * @param id the id of the new element to create
   * @param metaId the metaId of the new element to create
   * @param species a template to copy fields from
   * @return the {@link QualitativeSpecies} object created
   */
  public QualitativeSpecies createQualitativeSpecies(String id, String metaId, Species species) {
    QualitativeSpecies qualSpecies = new QualitativeSpecies(species);
    qualSpecies.setId(id);
    qualSpecies.setMetaId(metaId);
    qualSpecies.setLevel(getModel().getLevel());
    qualSpecies.setVersion(getModel().getVersion());
    addSpecies(qualSpecies);
    return qualSpecies;
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
  public QualitativeSpecies createQualitativeSpecies(String id, Compartment c) {
    QualitativeSpecies s = createQualitativeSpecies(id);
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
		listOfQualitativeSpecies.addNamespace(QualConstant.namespaceURI);
		getModel().registerChild(listOfQualitativeSpecies);
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
	    listOfTransitions.addNamespace(QualConstant.namespaceURI);
		getModel().registerChild(listOfTransitions);
		listOfTransitions.setSBaseListType(ListOf.Type.other);

	  }
		return listOfTransitions;
	}

  /**
   * 
   * @return
   */
	public Model getModel() {
    return (Model) extendedSBase;
  }

  /**
   * 
   * @param i
   * @return
   */
  public QualitativeSpecies getQualitativeSpecies(int i) {
    return getListOfQualitativeSpecies().get(i);
  }
	
  /**
   * 
   * @param id
   * @return
   */
	public QualitativeSpecies getQualitativeSpecies(String id){
	  if(isSetListOfQualitativeSpecies()) {
  	  return listOfQualitativeSpecies.firstHit(new NameFilter(id));	    
	  } 
	  return null;
	}
	
	/**
	 * 
	 * @param qs
	 * @return
	 */
	public boolean containsQualitativeSpecies(QualitativeSpecies qs){
    return isSetListOfQualitativeSpecies()
      && listOfQualitativeSpecies.contains(qs);
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
  public Transition getTransition(int i) {
    return getListOfTransitions().get(i);
  }
  
  /**
   * @param id
   * @return
   */
  public SBase getTransition(String id) {
    if(isSetListOfTransitions()) {
      return listOfTransitions.firstHit(new NameFilter(id));     
    } 
    return null;
  }

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfQualitativeSpecies() {
		if ((listOfQualitativeSpecies == null) || listOfQualitativeSpecies.isEmpty()) {
			return false;			
		}		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfTransitions() {
		if ((listOfTransitions == null) || listOfTransitions.isEmpty()) {
			return false;			
		}		
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean readAttribute(String attributeName, String prefix, String value) {
	  // no attribute to read
		return false;
	}
	
	/**
	 * 
	 * @param listOfQualitativeSpecies
	 */
	public void setListOfQualitativeSpecies(
    ListOf<QualitativeSpecies> listOfQualitativeSpecies) {
    this.listOfQualitativeSpecies = listOfQualitativeSpecies;
    getModel().registerChild(this.listOfQualitativeSpecies);
  }
	
	/**
	 * 
	 * @param listOfTransitions
	 */
	public void setListOfTransitions(ListOf<Transition> listOfTransitions) {
    this.listOfTransitions = listOfTransitions;
    getModel().registerChild(this.listOfTransitions);
  }


	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
	 */
  public Map<String, String> writeXMLAttributes() {
	  // no attribute to read
	  return null;
	}
  
  /**
   * 
   * @return
   */
  public boolean unsetListOfTransitions(){
    if(isSetListOfTransitions()) {
      this.listOfTransitions = null;
      getModel().registerChild(this.listOfTransitions);
      return true;
    }
    return false;
  }
  
  /**
   * 
   * @return
   */
  public boolean unsetListOfQualitativeSpecies() {
    if(isSetListOfQualitativeSpecies()) {
      this.listOfQualitativeSpecies = null;
      getModel().registerChild(this.listOfQualitativeSpecies);
      return true;
    }
    return false;
  }

}
