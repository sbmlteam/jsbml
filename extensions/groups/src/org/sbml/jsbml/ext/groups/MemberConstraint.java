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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.groups;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @deprecated This class was removed from the specifications as of version 0.7
 *             (2015-11-24) as no software wanted to implement support for it.
 *             It might be added back in a future version of the specifications
 *             if somebody want to implement it.
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@Deprecated
public class MemberConstraint extends AbstractNamedSBase  implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5785868406244137379L;

  /**
   * 
   */
  private String identicalAttribute;
  /**
   * 
   */
  private String distinctAttribute;

  /**
   * Creates an MemberConstraint instance
   */
  public MemberConstraint() {
    super();
    initDefaults();
  }

  /**
   * Creates a MemberConstraint instance with an id.
   * 
   * @param id the id
   */
  public MemberConstraint(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a MemberConstraint instance with a level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public MemberConstraint(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a MemberConstraint instance with an id, level, and version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public MemberConstraint(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a MemberConstraint instance with an id, name, level, and version.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public MemberConstraint(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(GroupsConstants.MIN_SBML_LEVEL),
      Integer.valueOf(GroupsConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public MemberConstraint(MemberConstraint obj) {
    super(obj);

    if (obj.isSetIdenticalAttribute()) {
      setIdenticalAttribute(obj.getIdenticalAttribute());
    }
    if (obj.isSetDistinctAttribute()) {
      setDistinctAttribute(obj.getDistinctAttribute());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public MemberConstraint clone() {
    return new MemberConstraint(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2729;
    int result = super.hashCode();
    result = prime * result
        + ((distinctAttribute == null) ? 0 : distinctAttribute.hashCode());
    result = prime * result
        + ((identicalAttribute == null) ? 0 : identicalAttribute.hashCode());
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
    MemberConstraint other = (MemberConstraint) obj;
    if (distinctAttribute == null) {
      if (other.distinctAttribute != null) {
        return false;
      }
    } else if (!distinctAttribute.equals(other.distinctAttribute)) {
      return false;
    }
    if (identicalAttribute == null) {
      if (other.identicalAttribute != null) {
        return false;
      }
    } else if (!identicalAttribute.equals(other.identicalAttribute)) {
      return false;
    }
    return true;
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = GroupsConstants.shortLabel;
    setPackageVersion(-1);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * Returns the value of identicalAttribute
   *
   * @return the value of identicalAttribute
   */
  public String getIdenticalAttribute() {
    if (isSetIdenticalAttribute()) {
      return identicalAttribute;
    }

    return null;
  }

  /**
   * Returns whether identicalAttribute is set
   *
   * @return whether identicalAttribute is set
   */
  public boolean isSetIdenticalAttribute() {
    return identicalAttribute != null;
  }

  /**
   * Sets the value of identicalAttribute
   * 
   * @param identicalAttribute the value of identicalAttribute
   */
  public void setIdenticalAttribute(String identicalAttribute) {
    String oldIdenticalAttribute = this.identicalAttribute;
    this.identicalAttribute = identicalAttribute;
    firePropertyChange(GroupsConstants.identicalAttribute, oldIdenticalAttribute, this.identicalAttribute);
  }

  /**
   * Unsets the variable identicalAttribute
   *
   * @return {@code true}, if identicalAttribute was set before,
   *         otherwise {@code false}
   */
  public boolean unsetIdenticalAttribute() {
    if (isSetIdenticalAttribute()) {
      String oldIdenticalAttribute = identicalAttribute;
      identicalAttribute = null;
      firePropertyChange(GroupsConstants.identicalAttribute, oldIdenticalAttribute, identicalAttribute);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of distinctAttribute
   *
   * @return the value of distinctAttribute
   */
  public String getDistinctAttribute() {
    if (isSetDistinctAttribute()) {
      return distinctAttribute;
    }

    return null;
  }

  /**
   * Returns whether distinctAttribute is set
   *
   * @return whether distinctAttribute is set
   */
  public boolean isSetDistinctAttribute() {
    return distinctAttribute != null;
  }

  /**
   * Sets the value of distinctAttribute
   * 
   * @param distinctAttribute the value of distinctAttribute
   */
  public void setDistinctAttribute(String distinctAttribute) {
    String oldDistinctAttribute = this.distinctAttribute;
    this.distinctAttribute = distinctAttribute;
    firePropertyChange(GroupsConstants.distinctAttribute, oldDistinctAttribute, this.distinctAttribute);
  }

  /**
   * Unsets the variable distinctAttribute
   *
   * @return {@code true}, if distinctAttribute was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDistinctAttribute() {
    if (isSetDistinctAttribute()) {
      String oldDistinctAttribute = distinctAttribute;
      distinctAttribute = null;
      firePropertyChange(GroupsConstants.distinctAttribute, oldDistinctAttribute, distinctAttribute);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(GroupsConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(GroupsConstants.shortLabel + ":name", getName());
    }
    if (isSetIdenticalAttribute()) {
      attributes.put(GroupsConstants.shortLabel + ":" + GroupsConstants.identicalAttribute, getIdenticalAttribute());
    }
    if (isSetDistinctAttribute()) {
      attributes.put(GroupsConstants.shortLabel + ":" + GroupsConstants.distinctAttribute, getDistinctAttribute());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(GroupsConstants.identicalAttribute)) {
        setIdenticalAttribute(value);
      } else if (attributeName.equals(GroupsConstants.distinctAttribute)) {
        setDistinctAttribute(value);
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

}
