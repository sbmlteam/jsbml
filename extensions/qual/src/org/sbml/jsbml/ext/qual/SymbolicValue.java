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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date $Date$
 */
public class SymbolicValue extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -214835834453944834L;
  /**
   * 
   */
  private Integer rank;

  /**
   * 
   */
  public SymbolicValue() {
  	super();
  	initDefaults();
  }
  
  /**
   * @param id
   */
  public SymbolicValue(String id) {
    super(id);
    initDefaults();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public SymbolicValue(int level, int version){
	  this(null, null, level, version);
  }
 
 
  /**
   * @param id
   * @param level
   * @param version
   */
  public SymbolicValue(String id, int level, int version) {
	  this(id, null, level, version);
  }

  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public SymbolicValue(String id, String name, int level, int version) {
	  super(id, name, level, version);
	  // TODO: replace level/version check with call to helper method
	  if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
		  throw new LevelVersionError(getElementName(), level, version);
	  }
	  initDefaults();
  }

  public SymbolicValue(SymbolicValue sv) {
    super(sv);
    
    rank = sv.rank;
  }
  
  /**
   * 
   */
  public void initDefaults() {
     addNamespace(QualConstant.namespaceURI);
     rank = null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  public SymbolicValue clone() {
    return new SymbolicValue(this);
  }


  /**
   * @return false
   */
  public boolean isRankMandatory() {
    return false;
  }


  /**
   * @return the rank
   */
  public int getRank() {
	  if (isSetRank()) {
		  return rank.intValue();
	  }
	  throw new PropertyUndefinedError(QualConstant.rank, this);
  }


  /**
   * 
   * @return
   */
  public boolean isSetRank() {
    return this.rank != null;
  }


  /**
   * @param rank
   *        the rank to set
   */
  public void setRank(int rank) {
    Integer oldRank = this.rank;
    this.rank = rank;
    firePropertyChange(QualConstant.rank, oldRank, this.rank);
  }


  /**
   * @return true if unset rank attribute was successful
   */
  public boolean unsetRank() {
    if (isSetRank()) {
      Integer oldRank = this.rank;
      this.rank = null;
      firePropertyChange(QualConstant.rank, oldRank, this.rank);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      SymbolicValue sv = (SymbolicValue) object;
      equals &= sv.isSetRank() == isSetRank();
      if (equals && isSetRank()) {
        equals &= (sv.getRank() == (getRank()));
      }
    }
    return equals;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;
    int hashCode = super.hashCode();
    if (isSetRank()) {
      hashCode += prime * getRank();
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

		  if (attributeName.equals(QualConstant.rank)) {
			  setRank(StringTools.parseSBMLInt(value));
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
		  attributes.put(QualConstant.shortLabel + ":id", getId());
	  }
	  if (isSetName()) {
		  attributes.remove("name");
		  attributes.put(QualConstant.shortLabel + ":name", getName());
	  }
	  if (isSetRank()) {
		  attributes.put(QualConstant.shortLabel + ":" + QualConstant.rank, Integer.toString(getRank()));
	  }	  

	  return attributes;
  }
  
}
