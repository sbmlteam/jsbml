/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.StringTools;

/**
 * 
 * The {@link Dimension} is used to indicate the parent {@link SBase}
 * is arrayed.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class Dimension extends AbstractNamedSBase implements CallableSBase {

  /**
   * 
   */
  private static final long serialVersionUID = -2930232424549376654L;

  /**
   * Indicates the size of the dimension. Should be a valid id to a Parameter object.
   */
  private String size;

  /**
   * Indicates which array dimension this object refers to.
   */
  private int arrayDimension;

  /**
   * Indicates if the arrayDimension field has a value.
   */
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
   * @param id the id
   */
  public Dimension(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a Dimension instance with a level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Dimension(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a Dimension instance with an id, level, and version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public Dimension(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a Dimension instance with an id, name, level, and version.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
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
   * 
   * @param obj the instance to clone
   */
  public Dimension(Dimension obj) {
    super(obj);
    if (obj.isSetSize()) {
      setSize(obj.size);
    }
    if (obj.isSetArrayDimension()) {
      setArrayDimension(obj.arrayDimension);
    }
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
    setPackageVersion(-1);
    packageName = ArraysConstants.shortLabel;
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
   * 
   * @param size the value of size
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
   * 
   * @param arrayDimension the value of arrayDimension
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

    if (!isAttributeRead) {
      isAttributeRead = true;


      if (attributeName.equals(ArraysConstants.arrayDimension)) {
        setArrayDimension(StringTools.parseSBMLInt(value));
      }

      else if (attributeName.equals(ArraysConstants.size)) {
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

    if (isSetArrayDimension()) {
      attributes.remove("arrayDimension");
      attributes.put(ArraysConstants.shortLabel + ":arrayDimension", ""+getArrayDimension());
    }

    if (isSetSize()) {
      attributes.remove("size");
      attributes.put(ArraysConstants.shortLabel + ":size", getSize());
    }

    return attributes;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2707;
    int result = super.hashCode();
    result = prime * result + arrayDimension;
    result = prime * result + ((size == null) ? 0 : size.hashCode());
    return result;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Dimension other = (Dimension) obj;
    if (arrayDimension != other.arrayDimension) {
      return false;
    }
    if (size == null) {
      if (other.size != null) {
        return false;
      }
    } else if (!size.equals(other.size)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    if (isSetSize()) {
      Parameter param = getModel().getParameter(size);
      return param != null ? param.containsUndeclaredUnits() : false;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    if (isSetSize()) {
      Parameter param = getModel().getParameter(size);
      return param != null ? param.getDerivedUnitDefinition() : null;
    }
    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
   */
  @Override
  public String getDerivedUnits() {
    if (isSetSize()) {
      Parameter param = getModel().getParameter(size);
      return param != null ? param.getDerivedUnits() : null;
    }
    return null;
  }


}
