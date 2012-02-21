/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Clemens Wrzodek
 * @version $Rev$
 * @since 1.0
 * @date $Date$
 */
public class QualitativeSpecies extends AbstractNamedSBase implements UniqueNamedSBase{

  /**
   * Generated serial version identifier.
   */
  private static final long     serialVersionUID = -6048861420699176889L;
  /**
   * 
   */
  private String                compartment;
  /**
   * 
   */
  private Boolean               boundaryCondition;
  /**
   * 
   */
  private Boolean               constant;             // TODO: extends/implements the jsbml interface
                                                      // that has the constant attribute.
  /**
   * 
   */
  private Integer               initialLevel;
  /**
   * 
   */
  private Integer               maxLevel;
  /**
   * 
   */
  private ListOf<SymbolicValue> listOfSymbolicValues;

  /**
   * 
   */
  public QualitativeSpecies() {
    super();
    initDefaults();
  }
  
  /**
   * 
   * @param id
   */
  public QualitativeSpecies(String id) {
    super(id);
    initDefaults();
  }
  
  /**
   * 
   * @param level
   * @param version
   */
  public QualitativeSpecies(int level, int version){
    this(null, null, level, version);
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public QualitativeSpecies(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public QualitativeSpecies(String id, String name, int level, int version) {
    super(id, name, level, version);
    // TODO: replace level/version check with call to helper method
    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }
  
  /**
   * Copy constructor that clones all values from the input
   * <code>species</code> that are also available in
   * {@link QualitativeSpecies}.
   * <p>You should consider setting a new id and meta_id afterwards
   * to avoid duplicate identifiers.
   * @param species
   */
  public QualitativeSpecies(Species species) {
    super(species);
    initDefaults();
    
    if (species.isSetCompartment()) {
      compartment = species.getCompartment();
    }
    if (species.isSetBoundaryCondition()) {
      boundaryCondition = species.getBoundaryCondition();
    }
    if (species.isSetConstant()) {
      constant = species.getConstant();
    }
    
    /* initialLevel, maxLevel and listOfSymbolicValues
     * are only for qual species.
     */
  }
  
  /**
   * Copy constructor that clones all variables of
   * <code>qualSpecies</code>.
   * @param qualSpecies
   */
  public QualitativeSpecies(QualitativeSpecies qualSpecies) {
    super(qualSpecies);


    compartment = qualSpecies.compartment;
    boundaryCondition = qualSpecies.boundaryCondition;
    constant = qualSpecies.constant;
    initialLevel = qualSpecies.initialLevel;
    maxLevel = qualSpecies.maxLevel;
    if (qualSpecies.isSetListOfSymbolicValues()) {
      listOfSymbolicValues = qualSpecies.listOfSymbolicValues.clone();
    }
  }
  
  /**
   * 
   * @param id
   * @param boundaryCondition
   * @param compartment
   * @param constant
   */
  public QualitativeSpecies(String id, boolean boundaryCondition,
    String compartment, boolean constant) {
    this(id);
    setBoundaryCondition(boundaryCondition);
    setCompartment(compartment);
    setConstant(constant);
  }

  /**
   * 
   */
  public void initDefaults() {
    addNamespace(QualConstant.namespaceURI);
    boundaryCondition = null;
    compartment = null;
    constant = null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  public AbstractSBase clone() {
    return new QualitativeSpecies(this);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * 
   * @return true
   */
  public boolean isCompartmentMandatory(){
    return true;
  }
  
  /**
   * @return if boundaryCondition attribute is set
   */
  public boolean isSetCompartment() {
    return compartment != null;
  }


  /**
   * @return the compartment
   */
  public String getCompartment() {
    return isSetCompartment() ? compartment : "";
  }


  /**
   * @param compartment
   *        the compartment to set
   */
  public void setCompartment(String compartment) {
    String oldCompartment = this.compartment;
    if ((compartment == null) || (compartment.length() == 0)) {
      this.compartment = null;
    } else {
      this.compartment = compartment;
    }
    firePropertyChange(QualConstant.compartment, oldCompartment,
      this.compartment);
  }
  
  /**
   * Sets the compartmentID of this {@link Species} to the id of
   * 'compartment'.
   * 
   * @param compartment
   */
  public void setCompartment(Compartment compartment) {
    if (compartment != null) {
      setCompartment(compartment.getId());
    } else {
      unsetCompartment();
    }
  }


  /**
   * @return true if the unset of the compartment attribute was successful
   */
  public boolean unsetCompartment() {
    if (isSetCompartment()) {
      this.compartment = null;
      return true;
    }
    return false;
  }


  /**
   * @return true
   */
  public boolean isBoundaryConditionMandatory() {
    return true;
  }


  /**
   * @return if boundaryCondition attribute is set
   */
  public boolean isSetBoundaryCondition() {
    return boundaryCondition != null;
  }


  /**
   * @return the boundaryCondition
   */
  public boolean getBoundaryCondition() {
    if (isSetBoundaryCondition()) {
      return boundaryCondition.booleanValue();
    }
    throw new PropertyUndefinedError(QualConstant.boundaryCondition, this);
  }


  /**
   * @param boundaryCondition
   *        the boundaryCondition to set
   */
  public void setBoundaryCondition(boolean boundaryCondition) {    
    Boolean oldBoundaryCondition = this.boundaryCondition;
    this.boundaryCondition = boundaryCondition;
    firePropertyChange(QualConstant.boundaryCondition, oldBoundaryCondition,
      this.boundaryCondition);
  }


  /**
   * @return true if the unset of the boundaryCondition attribute was successful
   */
  public boolean unsetBoundaryCondition() {
    if (isSetBoundaryCondition()) {
      boolean oldBoundaryCondition = boundaryCondition;
      boundaryCondition = null;
      firePropertyChange(QualConstant.boundaryCondition,
        oldBoundaryCondition, this.boundaryCondition);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return true
   */
  public boolean isSetConstantMandatory() {
    return true;
  }

  /**
   * 
   * @return
   */
  public boolean isSetConstant() {
    return constant != null;
  }


  /**
   * @return the constant
   */
  public boolean getConstant() {
    if (isSetConstant()) {
      return constant.booleanValue();
    }
    throw new PropertyUndefinedError(QualConstant.constant, this);
  }


  /**
   * @param constant
   *        the constant to set
   */
  public void setConstant(boolean constant) {
    Boolean oldConstant = this.constant;
    this.constant = constant;
    firePropertyChange(QualConstant.constant, oldConstant, this.constant);
  }


  /**
   * @return true if the unset of the constant attribute was successful
   */
  public boolean unsetConstant() {
    if (isSetConstant()) {
      boolean oldConstant = this.constant;
      this.constant = null;
      firePropertyChange(QualConstant.constant, oldConstant, this.constant);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return false
   */
  public boolean isInitialLevelMandatory() {
    return false;
  }

  
  /**
   * 
   * @return
   */
  public boolean isSetInitialLevel() {
    return initialLevel != null;
  }


  /**
   * @return the initialLevel
   */
  public int getInitialLevel() {
    if (isSetInitialLevel()) {
      return initialLevel.intValue();
    }
    throw new PropertyUndefinedError(QualConstant.initialLevel, this);
  }


  /**
   * @param initialLevel
   *        the initialLevel to set
   */
  public void setInitialLevel(int initialLevel) {
    Integer oldInitialLevel = this.initialLevel;
    this.initialLevel = initialLevel;
    firePropertyChange(QualConstant.initialLevel, oldInitialLevel,
      this.initialLevel);
  }


  /**
   * @return true if unset initialLevel attribute was successful
   */
  public boolean unsetInitialLevel() {
    if (isSetInitialLevel()) {
      Integer oldInitialLevel = this.initialLevel;
      this.initialLevel = null;
      firePropertyChange(QualConstant.initialLevel, oldInitialLevel,
        this.initialLevel);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return false
   */
  public boolean isMaxLevelMandatory() {
    return false;
  }


  /**
   * 
   * @return
   */
  public boolean isSetMaxLevel() {
    return this.maxLevel != null;
  }


  /**
   * @return the maxLevel
   */
  public int getMaxLevel() {
    if (isSetMaxLevel()) {
      return maxLevel.intValue();
    }
    throw new PropertyUndefinedError(QualConstant.maxLevel, this);
  }


  /**
   * @param maxLevel
   *        the maxLevel to set
   */
  public void setMaxLevel(int maxLevel) {
    Integer oldMaxLevel = this.maxLevel;
    this.maxLevel = maxLevel;
    firePropertyChange(QualConstant.maxLevel, oldMaxLevel, this.maxLevel);
  }


  /**
   * @return true if unset maxLevel attribute was successful
   */
  public boolean unsetMaxLevel() {
    if (isSetMaxLevel()) {
      Integer oldMaxLevel = this.maxLevel;
      this.maxLevel = null;
      firePropertyChange(QualConstant.maxLevel, oldMaxLevel, this.maxLevel);
      return true;
    } else {
      return false;
    }
  }


  /**
   * 
   * @return
   */
  // TODO : add all the necessary methods to manipulate the list
  // did I miss something?
  public boolean isSetListOfSymbolicValues() {
    return listOfSymbolicValues != null;
  }


  /**
   * 
   * @param los
   */
  public void setListOfSymbolicValues(ListOf<SymbolicValue> los) {
    unsetListOfSymbolicValues();
    this.listOfSymbolicValues = los;
    registerChild(this.listOfSymbolicValues);
  }


  /**
   * 
   * @return
   */
  public boolean unsetListOfSymbolicValues() {
    if (isSetListOfSymbolicValues()) {
      ListOf<SymbolicValue> oldLos = this.listOfSymbolicValues;
      this.listOfSymbolicValues = null;
      oldLos.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @return the listOfSymbolicValues
   */
  public ListOf<SymbolicValue> getListOfSymbolicValues() {
    if (!isSetListOfSymbolicValues()) {
      this.listOfSymbolicValues = new ListOf<SymbolicValue>(getLevel(), getVersion());
      listOfSymbolicValues.setSBaseListType(ListOf.Type.other);
      listOfSymbolicValues.addNamespace(QualConstant.namespaceURI);
      registerChild(listOfSymbolicValues);

    }
    return this.listOfSymbolicValues;
  }


  /**
   * @param symbolicValue
   *        the symbolicValue to add
   */
  public boolean addSymbolicValue(SymbolicValue symbolicValue) {
    if (getListOfSymbolicValues().add(symbolicValue)) {
      return true;
    }
    return false;
  }


  /**
   * 
   * @param symbolicValue to remove from the listOfSymbolicValues
   * @return true if the operation was successful
   */
  public boolean removeSymbolicValue(SymbolicValue symbolicValue) {
    if (isSetListOfSymbolicValues()) {
      return listOfSymbolicValues.remove(symbolicValue);
    }
    return false;
  }

  /**
   * 
   * @param i position in the listOfSymbolicValues which should be deleted
   * @throws IndexOutOfBoundsException if the index is invalid.
   */
  public void removeSymbolicValue(int i) {
    if (!isSetListOfSymbolicValues()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    listOfSymbolicValues.remove(i);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }
      
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetListOfSymbolicValues()) {
      if (pos == index) {
        return getListOfSymbolicValues();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
        index, +((int) Math.min(pos, 0))));
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfSymbolicValues()) {
      count++;
    }
    return count;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      QualitativeSpecies qs = (QualitativeSpecies) object;
      equals &= qs.isSetBoundaryCondition() == isSetBoundaryCondition();
      if (equals && isSetBoundaryCondition()) {
        equals &= (qs.getBoundaryCondition()==getBoundaryCondition());
      }
      equals &= qs.isSetConstant() == isSetConstant();
      if (equals && isSetConstant()) {
        equals &= (qs.getConstant()==getConstant());
      }
      equals &= qs.isSetCompartment() == isSetCompartment();
      if (equals && isSetCompartment()) {
        equals &= qs.getCompartment().equals(getCompartment());
      }
      equals &= qs.isSetInitialLevel() == isSetInitialLevel();
      if (equals && isSetInitialLevel()) {
        equals &= qs.getInitialLevel()==getInitialLevel();
      }
      equals &= qs.isSetMaxLevel() == isSetMaxLevel();
      if (equals && isSetMaxLevel()) {
        equals &= qs.getMaxLevel() == getMaxLevel();
      }
    }
    return equals;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 971;
    int hashCode = super.hashCode();
    if (isSetBoundaryCondition()) {
      hashCode += prime + (getBoundaryCondition() ? 1 : -1);
    }
    if (isSetConstant()) {
      hashCode += prime + (getConstant() ? 1 : -1);
    }
    if (isSetCompartment()) {
      hashCode += prime * getCompartment().hashCode();
    }
    if (isSetInitialLevel()) {
      hashCode += prime * getInitialLevel();
    }
    if (isSetMaxLevel()) {
      hashCode += prime * getMaxLevel();
    }
    
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {

	  boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

	  if (!isAttributeRead) {
		  isAttributeRead = true;

		  if (attributeName.equals(QualConstant.boundaryCondition)) {
			  setBoundaryCondition(StringTools.parseSBMLBoolean(value));
		  }	else if (attributeName.equals(QualConstant.constant)) {
			  setConstant(StringTools.parseSBMLBoolean(value));
		  } else if (attributeName.equals(QualConstant.compartment)) {
			  setCompartment(value);
		  } else if (attributeName.equals(QualConstant.initialLevel)) {
			  setInitialLevel(StringTools.parseSBMLInt(value));
		  } else if (attributeName.equals(QualConstant.maxLevel)) {
			  setMaxLevel(StringTools.parseSBMLInt(value));
		  } else {
			  isAttributeRead = false;
		  }
	  }

	  return isAttributeRead;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();

	  if (isSetId()) {
		  attributes.remove("id");
		  attributes.put(QualConstant.shortLabel+ ":id", getId());
	  }
	  if (isSetName()) {
		  attributes.remove("name");
		  attributes.put(QualConstant.shortLabel+ ":name", getName());
	  }
	  if (isSetBoundaryCondition()) {
		  attributes.put(QualConstant.shortLabel+ ":"+QualConstant.boundaryCondition, Boolean.toString(getBoundaryCondition()));
	  }
	  if (isSetConstant()) {
		  attributes.put(QualConstant.shortLabel+ ":"+QualConstant.constant, Boolean.toString(getConstant()));
	  }
	  if (isSetCompartment()) {
		  attributes.put(QualConstant.shortLabel+ ":"+QualConstant.compartment, getCompartment());
	  }
	  if (isSetInitialLevel()) {
		  attributes.put(QualConstant.shortLabel+ ":"+QualConstant.initialLevel, Integer.toString(getInitialLevel()));
	  }	  
	  if (isSetMaxLevel()) {
		  attributes.put(QualConstant.shortLabel+ ":"+QualConstant.maxLevel, Integer.toString(getMaxLevel()));
	  }

	  return attributes;
  }
  
}
