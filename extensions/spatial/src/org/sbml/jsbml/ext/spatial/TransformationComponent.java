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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @since 1.0
 */
public class TransformationComponent extends AbstractSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(TransformationComponent.class);

  /**
   * 
   */
  private static final long serialVersionUID = -2905551100912455549L;

  /**
   * 
   */
  private Double[] components;
  /**
   * 
   */
  private Integer componentsLength;

  /**
   * 
   */
  public TransformationComponent() {
    super();
    initDefaults();
  }

  /**
   * @param tc
   */
  public TransformationComponent(TransformationComponent tc) {
    super(tc);

    if (tc.isSetComponents()) {
      setComponents(tc.getComponents().clone());
    }

  }

  /**
   * @param level
   * @param version
   */
  public TransformationComponent(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public TransformationComponent clone() {
    return new TransformationComponent(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      TransformationComponent tc = (TransformationComponent) object;
      equal &= tc.isSetComponents() == isSetComponents();
      if (equal && isSetComponents()) {
        equal &= tc.getComponents().equals(getComponents());
      }
      if (equal && isSetComponentsLength()) {
        equal &= tc.getComponentsLength() == getComponentsLength();
      }
    }
    return equal;
  }

  /**
   * Returns the value of components
   *
   * @return the value of components
   */
  public Double[] getComponents() {
    if (isSetComponents()) {
      return components;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.components, this);
  }


  /**
   * Returns whether components is set
   *
   * @return whether components is set
   */
  public boolean isSetComponents() {
    return components != null;
  }

  /**
   * Sets the value of components
   * @param components
   */
  public void setComponents(Double... components) {
    Double[] oldComponents = this.components;
    this.components = components;
    componentsLength = components.length;
    firePropertyChange(SpatialConstants.components, oldComponents, this.components);
    firePropertyChange(SpatialConstants.componentsLength, oldComponents.length, componentsLength);
  }


  /**
   * Unsets the variable components
   *
   * @return {@code true}, if components was set before,
   *         otherwise {@code false}
   */
  public boolean unsetComponents() {
    if (isSetComponents()) {
      Double[] oldComponents = components;
      components = null;
      componentsLength = null;
      firePropertyChange(SpatialConstants.components, oldComponents, components);
      firePropertyChange(SpatialConstants.componentsLength, oldComponents.length, componentsLength);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of componentsLength
   *
   * @return the value of componentsLength
   */
  public int getComponentsLength() {
    if (isSetComponentsLength()) {
      return componentsLength.intValue();
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.componentsLength, this);
  }


  /**
   * Returns whether componentsLength is set
   *
   * @return whether componentsLength is set
   */
  public boolean isSetComponentsLength() {
    return componentsLength != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetComponents()) {
      hashCode += prime * getComponents().hashCode();
    }
    if (isSetComponentsLength()) {
      hashCode += prime * getComponentsLength();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetComponents()) {
      attributes.remove("components");
      attributes.put(SpatialConstants.shortLabel + ":components", Arrays.toString(getComponents()));
    }

    if (isSetComponentsLength()) {
      attributes.remove("componentsLength");
      attributes.put(SpatialConstants.shortLabel + ":componentsLength",
        String.valueOf(getComponentsLength()));
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.components)) {
        StringTokenizer test = new StringTokenizer(value);
        Double[] componentsTemp = new Double[test.countTokens()];
        int i = 0;
        while(test.hasMoreTokens()) {
          try {
            componentsTemp[i] = StringTools.parseSBMLDouble(test.nextToken());
          } catch (Exception e) {
            logger.warn(MessageFormat.format(
              SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.components, getElementName()));
          }
          i++;
        }
        if (componentsTemp.length > 0) {
          unsetComponents();
          setComponents(componentsTemp);
        }
      }
      else {
        isAttributeRead = false; // TODO - need to read componentsLength attribute as well.
      }
    }
    return isAttributeRead;
  }

}
