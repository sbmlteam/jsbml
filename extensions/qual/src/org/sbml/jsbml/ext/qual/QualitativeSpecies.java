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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.QualParser;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class QualitativeSpecies extends AbstractNamedSBase implements UniqueNamedSBase{

  // TODO : QualitativeSpecies id can be used in AssigmentRule or
  // EventAssignment variable attribute.
  // here we have a potential problem with striping the package stuff as we
  // would have dangling ids
  // If the package continue like this, we have a problem for example with
  // method like Model.findVariable(variable); that
  // need to be updated/replaced/modified by extension packages ??
  // Potentially, the QualitativeSpecies would have to be a Variable and a
  // CallableSBase to be able to be used in math expression and/or
  // EventAssignment/Rules
  /**
   * Generated serial version identifier.
   */
  private static final long     serialVersionUID = -6048861420699176889L;
  private String                compartment;
  private Boolean               boundaryCondition;
  private Boolean               constant;             // TODO: extends/implements the jsbml interface
                                                      // that has the constant attribute.
  private Integer               initialLevel;
  private Integer               maxLevel;
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
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  public QualitativeSpecies(String id, boolean boundaryCondition,
    String compartment, boolean constant) {
    this(id);
    setBoundaryCondition(boundaryCondition);
    setCompartment(compartment);
    setConstant(constant);
  }

  public void initDefaults() {
    addNamespace(QualParser.getNamespaceURI());
    boundaryCondition = null;
    compartment = null;
    constant = null;
  }

  @Override
  public AbstractSBase clone() {
    return null;
  }


  /**
   * @return true
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
    firePropertyChange(QualChangeEvent.compartment, oldCompartment,
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
    throw new PropertyUndefinedError(QualChangeEvent.boundaryCondition, this);
  }


  /**
   * @param boundaryCondition
   *        the boundaryCondition to set
   */
  public void setBoundaryCondition(boolean boundaryCondition) {    
    Boolean oldBoundaryCondition = this.boundaryCondition;
    this.boundaryCondition = boundaryCondition;
    firePropertyChange(QualChangeEvent.boundaryCondition, oldBoundaryCondition,
      this.boundaryCondition);
  }


  /**
   * @return true if the unset of the boundaryCondition attribute was successful
   */
  public boolean unsetBoundaryCondition() {
    if (isSetBoundaryCondition()) {
      boolean oldBoundaryCondition = boundaryCondition;
      boundaryCondition = null;
      firePropertyChange(QualChangeEvent.boundaryCondition,
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
    throw new PropertyUndefinedError(QualChangeEvent.constant, this);
  }


  /**
   * @param constant
   *        the constant to set
   */
  public void setConstant(boolean constant) {
    Boolean oldConstant = this.constant;
    this.constant = constant;
    firePropertyChange(QualChangeEvent.constant, oldConstant, this.constant);
  }


  /**
   * @return true if the unset of the constant attribute was successful
   */
  public boolean unsetConstant() {
    if (isSetConstant()) {
      boolean oldConstant = this.constant;
      this.constant = null;
      firePropertyChange(QualChangeEvent.constant, oldConstant, this.constant);
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
    throw new PropertyUndefinedError(QualChangeEvent.initialLevel, this);
  }


  /**
   * @param initialLevel
   *        the initialLevel to set
   */
  public void setInitialLevel(int initialLevel) {
    Integer oldInitialLevel = this.initialLevel;
    this.initialLevel = initialLevel;
    firePropertyChange(QualChangeEvent.initialLevel, oldInitialLevel,
      this.initialLevel);
  }


  /**
   * @return true if unset initialLevel attribute was successful
   */
  public boolean unsetInitialLevel() {
    if (isSetInitialLevel()) {
      Integer oldInitialLevel = this.initialLevel;
      this.initialLevel = null;
      firePropertyChange(QualChangeEvent.initialLevel, oldInitialLevel,
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
    throw new PropertyUndefinedError(QualChangeEvent.maxLevel, this);
  }


  /**
   * @param maxLevel
   *        the maxLevel to set
   */
  public void setMaxLevel(int maxLevel) {
    Integer oldMaxLevel = this.maxLevel;
    this.maxLevel = maxLevel;
    firePropertyChange(QualChangeEvent.maxLevel, oldMaxLevel, this.maxLevel);
  }


  /**
   * @return true if unset maxLevel attribute was successful
   */
  public boolean unsetMaxLevel() {
    if (isSetMaxLevel()) {
      Integer oldMaxLevel = this.maxLevel;
      this.maxLevel = null;
      firePropertyChange(QualChangeEvent.maxLevel, oldMaxLevel, this.maxLevel);
      return true;
    } else {
      return false;
    }
  }


  // TODO : add all the necessary methods to manipulate the list
  // did I miss something?
  public boolean isSetListOfSymbolicValues() {
    return listOfSymbolicValues != null;
  }


  public void setListOfSymbolicValues(ListOf<SymbolicValue> los) {
    unsetListOfSymbolicValues();
    this.listOfSymbolicValues = los;
    setThisAsParentSBMLObject(this.listOfSymbolicValues);
  }


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
      this.listOfSymbolicValues = new ListOf<SymbolicValue>();
      listOfSymbolicValues.setSBaseListType(ListOf.Type.other);
      listOfSymbolicValues.addNamespace(QualParser.getNamespaceURI());
      setThisAsParentSBMLObject(listOfSymbolicValues);

    }
    return this.listOfSymbolicValues;
  }


  /**
   * @param listOfSymbolicValues
   *        the listOfSymbolicValues to set
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
   * @param position in the listOfSymbolicValues which should be deleted
   * @return true if the operation was successful
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
  
  /*
   * (non-Javadoc)
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

  @Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
	  
	  boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

	  if (!isAttributeRead) {
		  isAttributeRead = true;
		  
		  if (attributeName.equals(QualChangeEvent.boundaryCondition)) {
			  setBoundaryCondition(StringTools.parseSBMLBoolean(value));
		  }	else if (attributeName.equals(QualChangeEvent.constant)) {
			  setConstant(StringTools.parseSBMLBoolean(value));
		  } else if (attributeName.equals(QualChangeEvent.compartment)) {
			  setCompartment(value);
		  } else if (attributeName.equals(QualChangeEvent.initialLevel)) {
			  setInitialLevel(StringTools.parseSBMLInt(value));
		  } else if (attributeName.equals(QualChangeEvent.maxLevel)) {
			  setMaxLevel(StringTools.parseSBMLInt(value));
		  } else {
			  isAttributeRead = false;
		  }
	  }
	  
	  return isAttributeRead;
	}
  
  @Override
	public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();

	  if (isSetId()) {
		  attributes.remove("id");
		  attributes.put(QualParser.shortLabel+ ":id", getId());
	  }
	  if (isSetName()) {
		  attributes.remove("name");
		  attributes.put(QualParser.shortLabel+ ":name", getName());
	  }
	  if (isSetBoundaryCondition()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.boundaryCondition, Boolean.toString(getBoundaryCondition()));
	  }
	  if (isSetConstant()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.constant, Boolean.toString(getConstant()));
	  }
	  if (isSetCompartment()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.compartment, getCompartment());
	  }
	  if (isSetInitialLevel()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.initialLevel, Integer.toString(getInitialLevel()));
	  }	  
	  if (isSetMaxLevel()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.maxLevel, Integer.toString(getMaxLevel()));
	  }
	  
	  return attributes;
	}
  
}
