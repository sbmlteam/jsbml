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
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentalizedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class CompartmentReference extends AbstractNamedSBase implements CompartmentalizedSBase, UniqueNamedSBase {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 6455311889725292763L;

  /**
   * 
   */
  private String compartment;

  /**
   * Creates a new {@link CompartmentReference} instance.
   */
  public CompartmentReference() {
    super();
    initDefaults();
  }


  /**
   * Creates a new {@link CompartmentReference} instance, cloned from the given object.
   * 
   * @param obj the {@link CompartmentReference} to clone
   */
  public CompartmentReference(CompartmentReference obj) {
    super(obj);

    // compartment
    if (obj.isSetCompartment()) {
      setCompartment(obj.getCompartment());
    }
  }


  /**
   * Creates a CompartmentReference instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public CompartmentReference(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a CompartmentReference instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public CompartmentReference(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a CompartmentReference instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public CompartmentReference(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a CompartmentReference instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public CompartmentReference(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(MultiConstants.MIN_SBML_LEVEL),
      Integer.valueOf(MultiConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public CompartmentReference clone() {
    return new CompartmentReference(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartment()
   */
  @Override
  public String getCompartment() {
    if (isSetCompartment()) {
      return compartment;
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartmentInstance()
   */
  @Override
  public Compartment getCompartmentInstance() {
    if (isSetCompartment()) {
      Model model = getModel();
      if (model != null) {
        return model.getCompartment(getCompartment());
      }
    }
    return null;
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isCompartmentMandatory()
   */
  @Override
  public boolean isCompartmentMandatory() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * Returns whether {@link #compartment} is set.
   *
   * @return whether {@link #compartment} is set.
   */
  @Override
  public boolean isSetCompartment() {
    return compartment != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartmentInstance()
   */
  @Override
  public boolean isSetCompartmentInstance() {
    return getCompartmentInstance() != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.compartment)) {
        setCompartment(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(org.sbml.jsbml.Compartment)
   */
  @Override
  public boolean setCompartment(Compartment compartment) {
    return setCompartment(compartment.getId());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(java.lang.String)
   */
  @Override
  public boolean setCompartment(String compartment) {
    if (compartment != this.compartment) {
      String oldCompartment = this.compartment;
      this.compartment = compartment;
      firePropertyChange(MultiConstants.compartment, oldCompartment, this.compartment);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#unsetCompartment()
   */
  @Override
  public boolean unsetCompartment() {
    return setCompartment((String) null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(MultiConstants.shortLabel + ":name", getName());
    }

    if (isSetCompartment()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.compartment, getCompartment());
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6197;
    int result = super.hashCode();
    result = prime * result
        + ((compartment == null) ? 0 : compartment.hashCode());
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
    CompartmentReference other = (CompartmentReference) obj;
    if (compartment == null) {
      if (other.compartment != null) {
        return false;
      }
    } else if (!compartment.equals(other.compartment)) {
      return false;
    }
    return true;
  }

  
}
