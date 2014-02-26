/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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

import java.util.Map;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class ReplacedElement extends SBaseRef {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1222530387127803035L;

  private String submodelRef;

  private String deletion;

  private String conversionFactor;

  /**
   * Creates an ReplacedElement instance
   */
  public ReplacedElement() {
    super();
    initDefaults();
  }

  /**
   * Creates a ReplacedElement instance with a level and version. -
   * 
   * @param level
   * @param version
   */
  public ReplacedElement(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public ReplacedElement(ReplacedElement obj) {
    super(obj);

    if (obj.isSetSubmodelRef()) {
      setSubmodelRef(new String(obj.getSubmodelRef()));
    }
    if (obj.isSetDeletion()) {
      setDeletion(new String(obj.getDeletion()));
    }
    if (obj.isSetConversionFactor()) {
      setConversionFactor(new String(obj.getConversionFactor()));
    }
  }

  /**
   * clones this class
   */
  @Override
  public ReplacedElement clone() {
    return new ReplacedElement(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(CompConstants.namespaceURI);
  }



  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ReplacedElement [submodelRef=" + submodelRef + ", deletion="
        + deletion + ", conversionFactor=" + conversionFactor + "]";
  }

  /**
   * Returns the value of submodelRef
   * 
   * @return the value of submodelRef
   */
  public String getSubmodelRef() {

    if (isSetSubmodelRef()) {
      return submodelRef;
    }

    return "";
  }

  /**
   * Returns whether submodelRef is set
   * 
   * @return whether submodelRef is set
   */
  public boolean isSetSubmodelRef() {
    return submodelRef != null;
  }

  /**
   * Sets the value of submodelRef
   */
  public void setSubmodelRef(String submodelRef) {
    String oldSubmodelRef = this.submodelRef;
    this.submodelRef = submodelRef;
    firePropertyChange(CompConstants.submodelRef, oldSubmodelRef, this.submodelRef);
  }

  /**
   * Unsets the variable submodelRef
   * @return {@code true}, if submodelRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSubmodelRef() {
    if (isSetSubmodelRef()) {
      String oldSubmodelRef = submodelRef;
      submodelRef = null;
      firePropertyChange(CompConstants.submodelRef, oldSubmodelRef, submodelRef);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of deletion
   *
   * @return the value of deletion
   */
  public String getDeletion() {

    if (isSetDeletion()) {
      return deletion;
    }

    return "";
  }

  /**
   * Returns whether deletion is set
   *
   * @return whether deletion is set
   */
  public boolean isSetDeletion() {
    return deletion != null;
  }

  /**
   * Sets the value of deletion
   */
  public void setDeletion(String deletion) {
    String oldDeletion = this.deletion;
    this.deletion = deletion;
    firePropertyChange(CompConstants.deletion, oldDeletion, this.deletion);
  }

  /**
   * Unsets the variable deletion
   *
   * @return {@code true}, if deletion was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDeletion() {
    if (isSetDeletion()) {
      String oldDeletion = deletion;
      deletion = null;
      firePropertyChange(CompConstants.deletion, oldDeletion, deletion);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of conversionFactor
   *
   * @return the value of conversionFactor
   */
  public String getConversionFactor() {

    if (isSetConversionFactor()) {
      return conversionFactor;
    }

    return "";
  }

  /**
   * Returns whether conversionFactor is set
   *
   * @return whether conversionFactor is set
   */
  public boolean isSetConversionFactor() {
    return conversionFactor != null;
  }

  /**
   * Sets the value of conversionFactor
   */
  public void setConversionFactor(String conversionFactor) {
    String oldConversionFactor = this.conversionFactor;
    this.conversionFactor = conversionFactor;
    firePropertyChange(CompConstants.conversionFactor, oldConversionFactor, this.conversionFactor);
  }

  /**
   * Unsets the variable conversionFactor
   *
   * @return {@code true}, if conversionFactor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetConversionFactor() {
    if (isSetConversionFactor()) {
      String oldConversionFactor = conversionFactor;
      conversionFactor = null;
      firePropertyChange(CompConstants.conversionFactor, oldConversionFactor, conversionFactor);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.SBaseRef#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetSubmodelRef()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.submodelRef, getSubmodelRef());
    }
    if (isSetDeletion()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.deletion, getDeletion());
    }
    if (isSetConversionFactor()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.conversionFactor, getConversionFactor());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.SBaseRef#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(CompConstants.deletion)) {
        setDeletion(value);
      }
      else if (attributeName.equals(CompConstants.submodelRef)) {
        setSubmodelRef(value);
      }
      else if (attributeName.equals(CompConstants.conversionFactor)) {
        setConversionFactor(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }
}
