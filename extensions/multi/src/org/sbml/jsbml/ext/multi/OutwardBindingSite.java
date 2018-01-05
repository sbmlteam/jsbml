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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;

/**
 * A binding site not involved in any {@link InSpeciesTypeBond} object in the {@link MultiSpeciesType} referenced by a {@link Species} is
 * an outwardBindingSite.
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class OutwardBindingSite extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2368487517657386175L;

  /**
   * 
   */
  private BindingStatus bindingStatus;

  /**
   * 
   */
  private String component;


  /**
   * Creates an OutwardBindingSite instance
   */
  public OutwardBindingSite() {
    super();
    initDefaults();
  }


  /**
   * Creates a OutwardBindingSite instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public OutwardBindingSite(int level, int version) {
    super(level, version);
    initDefaults();
  }



  /**
   * Clone constructor
   */
  public OutwardBindingSite(OutwardBindingSite obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetBindingStatus()) {
      setBindingStatus(obj.getBindingStatus());
    }
    if (obj.isSetComponent()) {
      setComponent(obj.getComponent());
    }
  }


  /**
   * clones this class
   */
  @Override
  public OutwardBindingSite clone() {
    return new OutwardBindingSite(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6089;
    int result = super.hashCode();
    result = prime * result
        + ((bindingStatus == null) ? 0 : bindingStatus.hashCode());
    result = prime * result + ((component == null) ? 0 : component.hashCode());
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
    OutwardBindingSite other = (OutwardBindingSite) obj;
    if (bindingStatus != other.bindingStatus) {
      return false;
    }
    if (component == null) {
      if (other.component != null) {
        return false;
      }
    } else if (!component.equals(other.component)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #bindingStatus}.
   *
   * @return the value of {@link #bindingStatus}.
   */
  public BindingStatus getBindingStatus() {
    if (isSetBindingStatus()) {
      return bindingStatus;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.bindingStatus, this);
  }


  /**
   * Returns whether {@link #bindingStatus} is set.
   *
   * @return whether {@link #bindingStatus} is set.
   */
  public boolean isSetBindingStatus() {
    return bindingStatus != null;
  }


  /**
   * Sets the value of bindingStatus
   *
   * @param bindingStatus the value of bindingStatus to be set.
   */
  public void setBindingStatus(BindingStatus bindingStatus) {
    BindingStatus oldBindingStatus = this.bindingStatus;
    this.bindingStatus = bindingStatus;
    firePropertyChange(MultiConstants.bindingStatus, oldBindingStatus, this.bindingStatus);
  }


  /**
   * Unsets the variable bindingStatus.
   *
   * @return {@code true} if bindingStatus was set before, otherwise {@code false}.
   */
  public boolean unsetBindingStatus() {
    if (isSetBindingStatus()) {
      BindingStatus oldBindingStatus = bindingStatus;
      bindingStatus = null;
      firePropertyChange(MultiConstants.bindingStatus, oldBindingStatus, bindingStatus);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #component}.
   *
   * @return the value of {@link #component}.
   */
  public String getComponent() {
    if (isSetComponent()) {
      return component;
    }

    return null;
  }


  /**
   * Returns whether {@link #component} is set.
   *
   * @return whether {@link #component} is set.
   */
  public boolean isSetComponent() {
    return component != null;
  }


  /**
   * Sets the value of component
   *
   * @param component the value of component to be set.
   */
  public void setComponent(String component) {
    String oldComponent = this.component;
    this.component = component;
    firePropertyChange(MultiConstants.component, oldComponent, this.component);
  }


  /**
   * Unsets the variable component.
   *
   * @return {@code true} if component was set before, otherwise {@code false}.
   */
  public boolean unsetComponent() {
    if (isSetComponent()) {
      String oldComponent = component;
      component = null;
      firePropertyChange(MultiConstants.component, oldComponent, component);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.component, getComponent());
    }
    if (isSetBindingStatus()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.bindingStatus, getBindingStatus().toString());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.component)) {
        setComponent(value);
      }
      else if (attributeName.equals(MultiConstants.bindingStatus)) {
        try {
          setBindingStatus(BindingStatus.valueOf(value));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + MultiConstants.bindingStatus
            + " on the '" + MultiConstants.outwardBindingSite + "' element.");
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
