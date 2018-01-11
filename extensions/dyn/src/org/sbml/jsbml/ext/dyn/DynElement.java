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
 * 6. Boston University, Boston, MA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.dyn;

import java.text.MessageFormat;
import java.util.Map;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @author Harold G&oacute;mez
 * @since 1.0
 */
public class DynElement extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 2706393074014198836L;

  /**
   * Id reference of model component
   */
  private String idRef;

  /**
   * metaId reference of model component
   */
  private String metaIdRef;

  /**
   * Empty constructor
   */
  public DynElement() {
    super();
    initDefaults();
  }

  /**
   * Initializes custom Class attributes
   * */
  private void initDefaults() {
    setPackageVersion(-1);
    packageName = DynConstants.shortLabel;
    idRef = null;
    metaIdRef = null;
  }

  /**
   * Constructor
   * 
   * @param level
   * @param version
   */
  public DynElement(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Constructor
   * @param id
   * @param name
   * 
   * @param level
   * @param version
   */
  public DynElement(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /**
   * Constructor
   * 
   * @param dynElement
   */
  public DynElement(DynElement dynElement) {
    super(dynElement);

    if (dynElement.isSetIdRef()) {
      setIdRef(dynElement.getIdRef());
    }
    if (dynElement.isSetMetaIdRef()) {
      setIdRef(dynElement.getMetaIdRef());
    }
  }

  @Override
  public boolean isIdMandatory() {
    return false;
  }

  @Override
  public AbstractSBase clone() {
    return new DynElement(this);
  }

  /**
   * Returns the value of idRef
   * 
   * @return the value of idRef
   */
  public String getIdRef() {
    if (isSetIdRef()) {
      return idRef;
    }
    return null;
  }

  /**
   * Returns whether idRef is set
   * 
   * @return whether idRef is set
   */
  public boolean isSetIdRef() {
    return idRef != null;
  }

  /**
   * Sets the value of idRef
   * 
   * @param idRef
   */
  public void setIdRef(String idRef) {
    String oldIdRef = this.idRef;
    this.idRef = idRef;
    firePropertyChange(DynConstants.idRef, oldIdRef, this.idRef);
  }

  /**
   * Unsets the variable idRef
   * 
   * @return {@code true}, if idRef was set before, otherwise {@code false}
   */
  public boolean unsetIdRef() {
    if (isSetIdRef()) {
      String oldIdRef = idRef;
      idRef = null;
      firePropertyChange(DynConstants.idRef, oldIdRef, idRef);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of metaIdRef
   * 
   * @return the value of metaIdRef
   */
  public String getMetaIdRef() {
    if (isSetMetaIdRef()) {
      return metaIdRef;
    }
    return null;
  }

  /**
   * Returns whether metaIdRef is set
   * 
   * @return whether metaIdRef is set
   */
  public boolean isSetMetaIdRef() {
    return metaIdRef != null;
  }

  /**
   * Sets the value of metaIdRef
   * 
   * @param metaIdRef
   */
  public void setMetaIdRef(String metaIdRef) {
    String oldMetaIdRef = this.metaIdRef;
    this.metaIdRef = metaIdRef;
    firePropertyChange(DynConstants.metaIdRef, oldMetaIdRef, this.metaIdRef);
  }

  /**
   * Unsets the variable metaIdRef
   * 
   * @return {@code true}, if metaIdRef was set before, otherwise
   *         {@code false}
   */
  public boolean unsetMetaIdRef() {
    if (isSetMetaIdRef()) {
      String oldMetaIdRef = metaIdRef;
      metaIdRef = null;
      firePropertyChange(DynConstants.metaIdRef, oldMetaIdRef,
        metaIdRef);
      return true;
    }
    return false;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!super.equals(object)) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    DynElement other = (DynElement) object;
    if (idRef == null) {
      if (other.idRef != null) {
        return false;
      }
    } else if (!idRef.equals(other.idRef)) {
      return false;
    }
    if (metaIdRef == null) {
      if (other.metaIdRef != null) {
        return false;
      }
    } else if (!metaIdRef.equals(other.metaIdRef)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3709;
    int result = super.hashCode();
    result = prime * result + ((idRef == null) ? 0 : idRef.hashCode());
    result = prime * result
        + ((metaIdRef == null) ? 0 : metaIdRef.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetIdRef()) {
      attributes.put(DynConstants.shortLabel + ":" + DynConstants.idRef,
        idRef);
    }
    if (isSetMetaIdRef()) {
      attributes.put(DynConstants.shortLabel + ":"
          + DynConstants.metaIdRef, metaIdRef);
    }
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(DynConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(DynConstants.shortLabel + ":name", getName());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    try {
      if (!isAttributeRead) {
        if (attributeName.equals(DynConstants.idRef)) {
          setIdRef(value);
          return true;
        }
        if (attributeName.equals(DynConstants.metaIdRef)) {
          setMetaIdRef(value);
          return true;
        }
      }
    } catch (Exception e) {
      MessageFormat.format(
        DynConstants.bundle.getString("COULD_NOT_READ_DYN_ELEMENT"), value,
        attributeName.equals(DynConstants.idRef)?DynConstants.idRef:DynConstants.metaIdRef);
    }

    return isAttributeRead;
  }

}
