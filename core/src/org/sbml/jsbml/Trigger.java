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
package org.sbml.jsbml;

import java.util.Map;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Represents the trigger XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * 
 */
public class Trigger extends AbstractMathContainer implements UniqueSId {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6964409168614117235L;

  /**
   * 
   */
  private Boolean initialValue;

  /**
   * 
   */
  private Boolean persistent;

  /**
   * Creates a {@link Trigger} instance.
   */
  public Trigger() {
    super();
    initDefaults();
  }

  /**
   * Creates a {@link Trigger} instance from a level and version.
   * 
   * @param level
   * @param version
   */
  public Trigger(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a {@link Trigger} instance from a given {@link Trigger}.
   * 
   * @param trigger
   */
  public Trigger(Trigger trigger) {
    super(trigger);

    if (trigger.isSetInitialValue()) {
      initialValue = trigger.getInitialValue();
    }
    if (trigger.isSetPersistent()) {
      persistent = trigger.getPersistent();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public Trigger clone() {
    return new Trigger(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      Trigger t = (Trigger) object;
      equal &= t.isSetInitialValue() == isSetInitialValue();
      if (equal && isSetInitialValue()) {
        equal &= getInitialValue() == t.getInitialValue();
      }
      equal &= t.isSetPersistent() == isSetPersistent();
      if (equal && isSetPersistent()) {
        equal &= getPersistent() == t.getPersistent();
      }
    }
    return equal;
  }

  /**
   * @return the initialValue
   */
  public boolean getInitialValue() {
    if (getLevel() < 3) {
      return true;
    }
    if (!isSetInitialValue()) {
      throw new PropertyUndefinedError("initialValue", this);
    }
    return initialValue.booleanValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @Override
  public Event getParent() {
    return (Event) super.getParent();
  }

  /**
   * @return the persistent
   */
  public boolean getPersistent() {
    if (getLevel() < 3) {
      return true;
    }
    if (!isSetPersistent()) {
      throw new PropertyUndefinedError("persistent", this);
    }
    return persistent.booleanValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 863;
    int hashCode = super.hashCode();
    if (isSetInitialValue()) {
      hashCode += prime * initialValue.hashCode();
    }
    if (isSetPersistent()) {
      hashCode += prime * persistent.hashCode();
    }
    return hashCode;
  }

  /**
   * Sets the properties {@link #initialValue} and {@link #persistent} to null, i.e., undefined.
   */
  public void initDefaults() {
    initialValue = persistent = null;
  }

  /**
   * 
   * @return whether or not this {@link Trigger} is initially set to true.
   */
  public boolean isInitialValue() {
    return getInitialValue();
  }

  /**
   * 
   * @return whether or not this is a persistent {@link Trigger}
   */
  public boolean isPersistent() {
    return getPersistent();
  }

  /**
   * 
   * @return
   */
  public boolean isSetInitialValue() {
    return initialValue != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetPersistent() {
    return persistent != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      if (attributeName.equals("initialValue")) {
        setInitialValue(StringTools.parseSBMLBoolean(value));
        return true;
      } else if (attributeName.equals("persistent")) {
        setPersistent(StringTools.parseSBMLBoolean(value));
        return true;
      }
    }
    return isAttributeRead;
  }

  /**
   * Can only be set if Level &gt;= 3.
   * 
   * @param initialValue
   *            the initialValue to set
   * @throws PropertyNotAvailableException
   *             if Level &lt; 3.
   */
  public void setInitialValue(boolean initialValue) {
    if (getLevel() < 3) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.initialValue, this);
    }
    Boolean oldInitialValue = this.initialValue;
    this.initialValue = Boolean.valueOf(initialValue);
    firePropertyChange(TreeNodeChangeEvent.initialValue, oldInitialValue, this.initialValue);
  }

  /**
   * Can only be set if Level &gt;= 3.
   * 
   * @param persistent
   *            the persistent to set
   * @throws PropertyNotAvailableException
   *             if Level &lt; 3.
   */
  public void setPersistent(boolean persistent) {
    if (getLevel() < 3) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.persistent, this);
    }
    Boolean oldPersistent = this.persistent;
    this.persistent = Boolean.valueOf(persistent);
    firePropertyChange(TreeNodeChangeEvent.persistent, oldPersistent, this.persistent);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetLevel() && (2 < getLevel())) {
      if (isSetInitialValue()) {
        attributes.put("initialValue", Boolean
          .toString(getInitialValue()));
      }
      if (isSetPersistent()) {
        attributes
        .put("persistent", Boolean.toString(getPersistent()));
      }
    }
    return attributes;
  }

}
