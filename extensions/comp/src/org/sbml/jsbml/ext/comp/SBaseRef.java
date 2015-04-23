/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.ext.comp;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.UnitDefinition;

/**
 * Contains the machinery for constructing references to specific components
 * within enclosed models or even within external models located in other files.
 * <p>
 * The four different attributes on SBaseRef are mutually exclusive: only one of
 * the attributes can have a value at any given time, and exactly one must have
 * a value in a given SBaseRef object instance.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class SBaseRef extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -4434500961448381830L;

  /**
   * This attribute is used to refer
   * to a port identifier, in the case when the reference being constructed with the SBaseRef is intended to refer to a
   * port on a submodel. The namespace of the PortSIdRef value is the set of identifiers of type PortSId defined in the
   * submodel, not the parent model.
   */
  private String portRef;

  /**
   * This attribute is used to refer to
   * a regular identifier (i.e., the value of an id attribute on some other object), in the case when the reference being
   * constructed with the SBaseRef is intended to refer to an object that does not have a port identifier. The namespace
   * of the SIdRef value is the set of identifiers of type SId defined in the submodel, not the parent model.
   */
  private String idRef;

  /**
   * This attribute is used to refer to the identifier of a
   * UnitDefinition object. The namespace of the UnitSIdRef value is the set of unit identifiers defined in the submodel,
   * not the parent model.
   */
  private String unitRef;

  /**
   * This attribute is used to refer to a metaid attribute
   * value on some other object, in the case when the reference being constructed with the SBaseRef is intended to refer
   * to an object that does not have a port identifier. The namespace of the metaIdRef value is the entire document in
   * which the referenced model resides
   */
  private String metaIdRef;

  /**
   * An SBaseRef object may have up to one subcomponent named sBaseRef.
   * This permits recursive structures to be constructed so that objects inside submodels can be referenced.
   */
  private SBaseRef sBaseRef;


  /**
   * Creates a {@link SBaseRef} instance.
   */
  public SBaseRef() {
    super();
    
    initDefaults();
  }

  /**
   * Creates a SBaseRef instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public SBaseRef(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public SBaseRef(SBaseRef obj) {
    super(obj);

    if (obj.isSetPortRef()) {
      setPortRef(obj.getPortRef());
    }
    if (obj.isSetIdRef()) {
      setIdRef(obj.getIdRef());
    }
    if (obj.isSetUnitRef()) {
      setUnitRef(obj.getUnitRef());
    }
    if (obj.isSetMetaIdRef()) {
      setMetaIdRef(obj.getMetaIdRef());
    }
    if (obj.isSetSBaseRef()) {
      setSBaseRef(obj.getSBaseRef());
    }
  }

  @Override
  public SBaseRef clone() {
    return new SBaseRef(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(CompConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = CompConstants.shortLabel;
  }



  /**
   * Returns the value of portRef
   * 
   * @return the value of portRef
   */
  public String getPortRef() {

    if (isSetPortRef()) {
      return portRef;
    }

    return "";
  }

  /**
   * Returns whether portRef is set
   * 
   * @return whether portRef is set
   */
  public boolean isSetPortRef() {
    return portRef != null;
  }

  /**
   * Sets the value of the optional portRef attribute.
   * 
   * As its name implies, this attribute is used to refer
   * to a port identifier, in the case when the reference
   * being constructed with the {@link SBaseRef} is
   * intended to refer to a port on a submodel.
   * @param portRef
   * 
   */
  public void setPortRef(String portRef) {
    String oldPortRef = this.portRef;
    this.portRef = portRef;
    firePropertyChange(CompConstants.portRef, oldPortRef, this.portRef);
  }

  /**
   * Unsets the variable portRef
   * 
   * @return {@code true}, if portRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetPortRef() {
    if (isSetPortRef()) {
      String oldPortRef = portRef;
      portRef = null;
      firePropertyChange(CompConstants.portRef, oldPortRef, portRef);
      return true;
    }
    return false;
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

    return "";
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
   * Sets the value of the optional idRef attribute.
   * 
   * As its name implies, this attribute is used to
   * refer to a regular identifier (i.e., the value of
   * an id attribute on some other object), in the case
   * when the reference being constructed with the
   * {@link SBaseRef} is intended to refer to an object
   * that does not have a port identifier.
   * @param idRef
   * 
   */
  public void setIdRef(String idRef) {
    String oldIdRef = this.idRef;
    this.idRef = idRef;
    firePropertyChange(CompConstants.idRef, oldIdRef, this.idRef);
  }

  /**
   * Unsets the variable idRef
   * 
   * @return {@code true}, if idRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetIdRef() {
    if (isSetIdRef()) {
      String oldIdRef = idRef;
      idRef = null;
      firePropertyChange(CompConstants.idRef, oldIdRef, idRef);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of unitRef
   * 
   * @return the value of unitRef
   */
  public String getUnitRef() {

    if (isSetUnitRef()) {
      return unitRef;
    }

    return "";
  }

  /**
   * Returns whether unitRef is set
   * 
   * @return whether unitRef is set
   */
  public boolean isSetUnitRef() {
    return unitRef != null;
  }

  /**
   * Sets the value of the optional unitRef attribute
   * 
   * This attribute is used to refer to the identifier of a
   * {@link UnitDefinition} object. The namespace of the
   * UnitSIdRef value is the set of unit identifiers defined
   * in the submodel, not the parent model.
   * 
   * Note that even though this attribute is of type UnitSIdRef,
   * the reserved unit identifiers that are defined by SBML Level 3
   * are not permitted as values of unitRef. Reserved unit
   * identifiers may not be replaced or deleted.
   * @param unitRef
   * 
   */
  public void setUnitRef(String unitRef) {
    String oldUnitRef = this.unitRef;
    this.unitRef = unitRef;
    firePropertyChange(CompConstants.unitRef, oldUnitRef, this.unitRef);
  }

  /**
   * Unsets the variable unitRef
   * 
   * @return {@code true}, if unitRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetUnitRef() {
    if (isSetUnitRef()) {
      String oldUnitRef = unitRef;
      unitRef = null;
      firePropertyChange(CompConstants.unitRef, oldUnitRef, unitRef);
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

    return "";
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
   * Sets the value of the optional metaIdRef attribute.
   * 
   * This attribute is used to refer to a metaid attribute value on some
   * other object, in the case when the reference being constructed with
   * the {@link SBaseRef} is intended to refer to an object that does not
   * have a port identifier. The namespace of the metaIdRef value is the
   * entire document in which the referenced model resides, but must refer
   * to a subelement of the referenced model. Since meta identifiers are
   * optional attributes of SBase, all SBML objects have the potential to
   * have a meta identifier value.
   * @param metaIdRef
   */
  public void setMetaIdRef(String metaIdRef) {
    String oldIdRef = this.metaIdRef;
    this.metaIdRef = metaIdRef;
    firePropertyChange(CompConstants.metaIdRef, oldIdRef, this.metaIdRef);
  }

  /**
   * Unsets the variable metaIdRef
   * @return {@code true}, if metaIdRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMetaIdRef() {
    if (isSetMetaIdRef()) {
      String oldIdRef = metaIdRef;
      metaIdRef = null;
      firePropertyChange(CompConstants.metaIdRef, oldIdRef, metaIdRef);
      return true;
    }
    return false;
  }

  /**
   * Creates a new {@link SBaseRef} instance and add it to this {@link SBaseRef}
   * 
   * @return a new {@link SBaseRef} instance (added to this {@link SBaseRef})
   */
  public SBaseRef createSBaseRef()
  {
    setSBaseRef(new SBaseRef());

    return sBaseRef;
  }


  /**
   * Returns the value of sBaseRef
   * 
   * @return the value of sBaseRef
   */
  public SBaseRef getSBaseRef() {

    if (isSetSBaseRef()) {
      return sBaseRef;
    }

    return null;
  }

  /**
   * Returns whether sBaseRef is set
   * 
   * @return whether sBaseRef is set
   */
  public boolean isSetSBaseRef() {
    return sBaseRef != null;
  }

  /**
   * Sets the value of the optional sBaseRef element.
   * 
   * An {@link SBaseRef} object may have up to one subcomponent named
   * sBaseRef, of type {@link SBaseRef}. This permits recursive structures
   * to be constructed so that objects inside submodels can be referenced.
   * @param sBaseRef
   * 
   */
  public void setSBaseRef(SBaseRef sBaseRef) {
    SBaseRef oldSBaseRef = this.sBaseRef;
    this.sBaseRef = sBaseRef;
    firePropertyChange(CompConstants.sBaseRef, oldSBaseRef, this.sBaseRef);
    // registering the new sBaseRef in the model/document // Could be done in org.sbml.jsbml.AbstractSBase.firePropertyChange(String, Object, Object)
    registerChild(sBaseRef);
  }

  /**
   * Unsets the variable sBaseRef
   * 
   * @return {@code true}, if sBaseRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSBaseRef() {
    if (isSetSBaseRef()) {
      SBaseRef oldSBaseRef = sBaseRef;
      sBaseRef = null;
      firePropertyChange(CompConstants.sBaseRef, oldSBaseRef, sBaseRef);
      return true;
    }
    return false;
  }



  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetSBaseRef()) {
      count++;
    }

    return count;
  }

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

    if (isSetSBaseRef()) {
      if (pos == index) {
        return getSBaseRef();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetPortRef()) {
      attributes.put(CompConstants.shortLabel + ':' + CompConstants.portRef, getPortRef());
    }
    if (isSetIdRef()) {
      attributes.put(CompConstants.shortLabel + ':' + CompConstants.idRef, getIdRef());
    }
    if (isSetUnitRef()) {
      attributes.put(CompConstants.shortLabel + ':' + CompConstants.unitRef, getUnitRef());
    }
    if (isSetMetaIdRef()) {
      attributes.put(CompConstants.shortLabel + ':' + CompConstants.metaIdRef, getMetaIdRef());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(CompConstants.portRef)) {
        setPortRef(value);
      }
      else if (attributeName.equals(CompConstants.idRef)) {
        setIdRef(value);
      } else if (attributeName.equals(CompConstants.unitRef)) {
        setUnitRef(value);
      } else if (attributeName.equals(CompConstants.metaIdRef)) {
        setMetaIdRef(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SBaseRef [portRef=" + portRef + ", idRef=" + idRef
        + ", unitRef=" + unitRef + ", metaIdRef=" + metaIdRef
        + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((idRef == null) ? 0 : idRef.hashCode());
    result = prime * result
        + ((metaIdRef == null) ? 0 : metaIdRef.hashCode());
    result = prime * result + ((portRef == null) ? 0 : portRef.hashCode());
    result = prime * result
        + ((sBaseRef == null) ? 0 : sBaseRef.hashCode());
    result = prime * result + ((unitRef == null) ? 0 : unitRef.hashCode());
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
    SBaseRef other = (SBaseRef) obj;
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
    if (portRef == null) {
      if (other.portRef != null) {
        return false;
      }
    } else if (!portRef.equals(other.portRef)) {
      return false;
    }
    if (sBaseRef == null) {
      if (other.sBaseRef != null) {
        return false;
      }
    } else if (!sBaseRef.equals(other.sBaseRef)) {
      return false;
    }
    if (unitRef == null) {
      if (other.unitRef != null) {
        return false;
      }
    } else if (!unitRef.equals(other.unitRef)) {
      return false;
    }
    return true;
  }


}
