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
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class StateFeatureValue extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3920699098046832296L;
  /**
   * 
   */
  private String possibleValue;

  /**
   * 
   */
  public StateFeatureValue() {
    super();
    initDefaults();
  }

  /**
   * @param stateFeatureValue
   */
  public StateFeatureValue(StateFeatureValue stateFeatureValue) {
    this();
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public StateFeatureValue clone() {
    return new StateFeatureValue(this);
  }

  /**
   * Returns the possibleValue
   * 
   * @return the possibleValue
   */
  public String getPossibleValue() {
    return possibleValue;
  }

  /**
   * Sets the possibleValue
   * 
   * @param possibleValue the possibleValue to set
   */
  public void setPossibleValue(String possibleValue) {
    this.possibleValue = possibleValue;
  }

  /**
   * Returns {@code true} if possibleValue is set.
   * 
   * @return {@code true} if possibleValue is set.
   */
  public boolean isSetPossibleValue() {
    return possibleValue != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
  public String toString() {
    // TODO
    return null;
  }

  /**
   * 
   */
  public void initDefaults() {
    setNamespace(MultiConstants.namespaceURI);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {

      if (attributeName.equals(MultiConstants.possibleValue)) {
        setPossibleValue(value);
        isAttributeRead = true;
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

    if (isSetPossibleValue()) {
      attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.possibleValue,
        getPossibleValue());
    }

    return attributes;
  }

}
