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
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.PropertyUndefinedError;


/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class DiffusionCoefficient extends ParameterType {

  /**
   * 
   */
  private DiffusionKind diffusionKind;
  /**
   * 
   */
  private CoordinateKind coordinateReference1;
  /**
   * 
   */
  private CoordinateKind coordinateReference2;
  
  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(DiffusionCoefficient.class);


  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1233063375698575897L;



  /**
   * 
   */
  public DiffusionCoefficient() {
    super();
  }


  /**
   * @param diffCoeff
   */
  public DiffusionCoefficient(DiffusionCoefficient diffCoeff) {
    super(diffCoeff);

    if (diffCoeff.isSetDiffusionKind()) {
      setDiffusionKind(diffCoeff.getDiffusionKind());
    }

    if (diffCoeff.isSetCoordinateReference1()) {
      setCoordinateReference1(diffCoeff.getCoordinateReference1());
    }

    if (diffCoeff.isSetCoordinateReference2()) {
      setCoordinateReference2(diffCoeff.getCoordinateReference2());
    }
  }


  /**
   * @param level
   * @param version
   */
  public DiffusionCoefficient(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public DiffusionCoefficient(String id, int level, int version) {
    super(level, version);
  }


  @Override
  public DiffusionCoefficient clone() {
    return new DiffusionCoefficient(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      DiffusionCoefficient diffCoeff = (DiffusionCoefficient) object;

      equal &= diffCoeff.isSetDiffusionKind() == isSetDiffusionKind();
      if (equal && isSetDiffusionKind()) {
        equal &= diffCoeff.getDiffusionKind() == getDiffusionKind();
      }

      equal &= diffCoeff.isSetCoordinateReference1() == isSetCoordinateReference1();
      if (equal && isSetCoordinateReference1()) {
        equal &= diffCoeff.getCoordinateReference1() == getCoordinateReference1();
      }

      equal &= diffCoeff.isSetCoordinateReference2() == isSetCoordinateReference2();
      if (equal && isSetCoordinateReference2()) {
        equal &= diffCoeff.getCoordinateReference2() == getCoordinateReference2();
      }
    }
    return equal;
  }


  /**
   * Returns the value of coordinateReference1
   *
   * @return the value of coordinateReference1
   */
  public CoordinateKind getCoordinateReference1() {
    if (isSetCoordinateReference1()) {
      return coordinateReference1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.coordinateReference1, this);
  }


  /**
   * Returns whether coordinateReference1 is set
   *
   * @return whether coordinateReference1 is set
   */
  public boolean isSetCoordinateReference1() {
    return coordinateReference1 != null;
  }


  /**
   * Sets the value of coordinateReference1
   * @param coordinateReference1
   */
  public void setCoordinateReference1(CoordinateKind coordinateReference1) {
    CoordinateKind oldCoordinateReference1 = this.coordinateReference1;
    this.coordinateReference1 = coordinateReference1;
    firePropertyChange(SpatialConstants.coordinateReference1, oldCoordinateReference1, this.coordinateReference1);
  }


  /**
   * Unsets the variable coordinateReference1
   *
   * @return {@code true}, if coordinateReference1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoordinateReference1() {
    if (isSetCoordinateReference1()) {
      CoordinateKind oldCoordinateReference1 = coordinateReference1;
      coordinateReference1 = null;
      firePropertyChange(SpatialConstants.coordinateReference1, oldCoordinateReference1, coordinateReference1);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of coordinateReference2
   *
   * @return the value of coordinateReference2
   */
  public CoordinateKind getCoordinateReference2() {
    if (isSetCoordinateReference2()) {
      return coordinateReference2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.coordinateReference2, this);
  }


  /**
   * Returns whether coordinateReference2 is set
   *
   * @return whether coordinateReference2 is set
   */
  public boolean isSetCoordinateReference2() {
    return coordinateReference2 != null;
  }


  /**
   * Sets the value of coordinateReference2
   * @param coordinateReference2
   */
  public void setCoordinateReference2(CoordinateKind coordinateReference2) {
    CoordinateKind oldCoordinateReference2 = this.coordinateReference2;
    this.coordinateReference2 = coordinateReference2;
    firePropertyChange(SpatialConstants.coordinateReference2, oldCoordinateReference2, this.coordinateReference2);
  }


  /**
   * Unsets the variable coordinateReference2
   *
   * @return {@code true}, if coordinateReference2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoordinateReference2() {
    if (isSetCoordinateReference2()) {
      CoordinateKind oldCoordinateReference2 = coordinateReference2;
      coordinateReference2 = null;
      firePropertyChange(SpatialConstants.coordinateReference2, oldCoordinateReference2, coordinateReference2);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of diffusionKind
   *
   * @return the value of diffusionKind
   */
  public DiffusionKind getDiffusionKind() {
    if (isSetDiffusionKind()) {
      return diffusionKind;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.diffusionKind, this);
  }


  /**
   * Returns whether diffusionKind is set
   *
   * @return whether diffusionKind is set
   */
  public boolean isSetDiffusionKind() {
    return diffusionKind != null;
  }


  /**
   * Sets the value of diffusionKind
   * @param diffusionKind
   */
  public void setDiffusionKind(DiffusionKind diffusionKind) {
    DiffusionKind oldDiffusionKind = this.diffusionKind;
    this.diffusionKind = diffusionKind;
    firePropertyChange(SpatialConstants.diffusionKind, oldDiffusionKind, this.diffusionKind);
  }


  /**
   * Unsets the variable diffusionKind
   *
   * @return {@code true}, if diffusionKind was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDiffusionKind() {
    if (isSetDiffusionKind()) {
      DiffusionKind oldDiffusionKind = diffusionKind;
      diffusionKind = null;
      firePropertyChange(SpatialConstants.diffusionKind, oldDiffusionKind, diffusionKind);
      return true;
    }
    return false;
  }


  @Override
  public int hashCode() {
    final int prime = 1531;
    int hashCode = super.hashCode();

    if (isSetDiffusionKind()) {
      hashCode += prime * getDiffusionKind().hashCode();
    }

    if (isSetCoordinateReference1()) {
      hashCode += prime * getCoordinateReference1().hashCode();
    }


    if (isSetCoordinateReference2()) {
      hashCode += prime * getCoordinateReference2().hashCode();
    }

    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetDiffusionKind()) {
      attributes.remove("type");
      attributes.put(SpatialConstants.shortLabel + ":type",
        String.valueOf(getDiffusionKind()).toLowerCase());
    } 

    if (isSetCoordinateReference1()) {
      attributes.remove("coordinateReference1");
      attributes.put(SpatialConstants.shortLabel + ":coordinateReference1",
        String.valueOf(getCoordinateReference1()));
    }

    if (isSetCoordinateReference2()) {
      attributes.remove("coordinateReference2");
      attributes.put(SpatialConstants.shortLabel + ":coordinateReference2",
        String.valueOf(getCoordinateReference2()));
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      
      if (attributeName.equals(SpatialConstants.type)) {
        try {
          setDiffusionKind(DiffusionKind.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.type, getElementName()));
        }
      }

      else if (attributeName.equals(SpatialConstants.coordinateReference1)) {
        try {
          setCoordinateReference1(CoordinateKind.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coordinateReference1, getElementName()));
        }
      }

      else if (attributeName.equals(SpatialConstants.coordinateReference2)) {
        try {
          setCoordinateReference2(CoordinateKind.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coordinateReference2, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


}
