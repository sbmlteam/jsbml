/*
 * $Id:  Dimension.java 1:25:57 PM lwatanabe $
 * $URL: Dimension.java $
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.ext.arrays;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date May 11, 2014
 */
public class Dimension extends AbstractNamedSBase {
  
  private String size;
  
  private int arrayDimension;
  
  private boolean isSetArrayDimension;
  
  /**
   * Creates an Dimension instance 
   */
  public Dimension() {
    super();
    initDefaults();
    
    
  }


  /**
   * Creates a Dimension instance with an id. 
   * 
   * @param id
   */
  public Dimension(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a Dimension instance with a level and version. 
   * 
   * @param level
   * @param version
   */
  public Dimension(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a Dimension instance with an id, level, and version. 
   * 
   * @param id
   * @param level
   * @param version
   */
  public Dimension(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a Dimension instance with an id, name, level, and version. 
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Dimension(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(ArraysConstants.MIN_SBML_LEVEL),
      Integer.valueOf(ArraysConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public Dimension(Dimension obj) {
    super(obj);
    size = obj.size;
    arrayDimension = obj.arrayDimension;
  }


  /**
   * clones this class
   */
  @Override
  public Dimension clone() {
    return new Dimension(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(ArraysConstants.namespaceURI);
    arrayDimension = -1;
    isSetArrayDimension = false;
    size = null;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  
  /**
   * Returns the value of size
   *
   * @return the value of size
   */
  public String getSize() {
    if (isSetSize()) {
      return size;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(ArraysConstants.size, this);
  }


  /**
   * Returns whether size is set 
   *
   * @return whether size is set 
   */
  public boolean isSetSize() {
    return size != null;
  }


  /**
   * Sets the value of size
   */
  public void setSize(String size) {
    String oldSize = this.size;
    this.size = size;
    firePropertyChange(ArraysConstants.size, oldSize, this.size);
  }


  /**
   * Unsets the variable size 
   *
   * @return {@code true}, if size was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetSize() {
    if (isSetSize()) {
      String oldSize = size;
      size = null;
      firePropertyChange(ArraysConstants.size, oldSize, size);
      return true;
    }
    return false;
  }
  
  
  
  /**
   * Returns the value of arrayDimension
   *
   * @return the value of arrayDimension
   */
  public int getArrayDimension() {
    if (isSetArrayDimension()) {
      return arrayDimension;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(ArraysConstants.arrayDimension, this);
  }


  /**
   * Returns whether arrayDimension is set 
   *
   * @return whether arrayDimension is set 
   */
  public boolean isSetArrayDimension() {
    return isSetArrayDimension;
  }


  /**
   * Sets the value of arrayDimension
   */
  public void setArrayDimension(int arrayDimension) {
    int oldArrayDimension = this.arrayDimension;
    this.arrayDimension = arrayDimension;
    isSetArrayDimension = true;
    firePropertyChange(ArraysConstants.arrayDimension, oldArrayDimension, this.arrayDimension);
  }


  /**
   * Unsets the variable arrayDimension 
   *
   * @return {@code true}, if arrayDimension was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetArrayDimension() {
    if (isSetArrayDimension()) {
      int oldArrayDimension = arrayDimension;
      arrayDimension = -1;
      isSetArrayDimension = false;
      firePropertyChange(ArraysConstants.arrayDimension, oldArrayDimension, arrayDimension);
      return true;
    }
    return false;
  }
  
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if(!isAttributeRead) {
      isAttributeRead = true;
      
      
      if(attributeName.equals(ArraysConstants.arrayDimension)) {
        setArrayDimension(StringTools.parseSBMLInt(value));
      }
      
      else if(attributeName.equals(ArraysConstants.size)) {
        setSize(value);
      }
      
      else {
        isAttributeRead = false;
      }
      
    }
    
    return isAttributeRead;
  }
  
  @Override
  public Map<String, String> writeXMLAttributes()
  {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(ArraysConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(ArraysConstants.shortLabel + ":name", getName());
    }
    
    if(isSetArrayDimension()) {
      attributes.remove("arrayDimension");
      attributes.put(ArraysConstants.shortLabel + ":getArrayDimension", ""+getArrayDimension());
    }
    
    if(isSetSize()) {
      attributes.remove("size");
      attributes.put(ArraysConstants.shortLabel + ":size", getSize());
    }
    
    return attributes;
  }
  
}
