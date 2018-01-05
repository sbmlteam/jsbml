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

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class InteriorPoint extends AbstractSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(InteriorPoint.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5525511951071345435L;

  /**
   * 
   */
  private Double coord1;

  /**
   * 
   */
  private Double coord2;

  /**
   * 
   */
  private Double coord3;


  /**
   * 
   */
  public InteriorPoint() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param interiorPoint
   */
  public InteriorPoint(InteriorPoint interiorPoint) {
    super(interiorPoint);

    if (interiorPoint.isSetCoord1()) {
      setCoord1(interiorPoint.getCoord1());
    }
    if (interiorPoint.isSetCoord2()) {
      setCoord2(interiorPoint.getCoord2());
    }
    if (interiorPoint.isSetCoord3()) {
      setCoord3(interiorPoint.getCoord3());
    }
  }

  /**
   * @param level
   * @param version
   */
  public InteriorPoint(int level, int version) {
    super(level,version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public InteriorPoint clone() {
    return new InteriorPoint(this);
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
    boolean equal =  super.equals(object);
    if (equal) {
      InteriorPoint ip = (InteriorPoint) object;

      equal &= ip.isSetCoord1() == isSetCoord1();
      if (equal && isSetCoord1()) {
        equal &= ip.getCoord1() == getCoord1();
      }
      equal &= ip.isSetCoord2() == isSetCoord2();
      if (equal && isSetCoord2()) {
        equal &= ip.getCoord2() == getCoord2();
      }
      equal &= ip.isSetCoord3() == isSetCoord3();
      if (equal && isSetCoord3()) {
        equal &= ip.getCoord3() == getCoord3();
      }
    }

    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /**
   * Returns the value of coord1
   *
   * @return the value of coord1
   */
  public double getCoord1() {
    if (isSetCoord1()) {
      return coord1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.coord1, this);
  }


  /**
   * Returns whether coord1 is set
   *
   * @return whether coord1 is set
   */
  public boolean isSetCoord1() {
    return coord1 != null;
  }

  /**
   * Sets the value of coord1
   * @param coord1
   */
  public void setCoord1(double coord1) {
    Double oldCoord1 = this.coord1;
    this.coord1 = coord1;
    firePropertyChange(SpatialConstants.coord1, oldCoord1, this.coord1);
  }


  /**
   * Unsets the variable coord1
   *
   * @return {@code true}, if coord1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoord1() {
    if (isSetCoord1()) {
      Double oldCoord1 = coord1;
      coord1 = null;
      firePropertyChange(SpatialConstants.coord1, oldCoord1, coord1);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of coord2
   *
   * @return the value of coord2
   */
  public double getCoord2() {
    if (isSetCoord2()) {
      return coord2;
    }
    throw new PropertyUndefinedError(SpatialConstants.coord2, this);
  }

  /**
   * Returns whether coord2 is set
   *
   * @return whether coord2 is set
   */
  public boolean isSetCoord2() {
    return coord2 != null;
  }


  /**
   * Sets the value of coord2
   * @param coord2
   */
  public void setCoord2(double coord2) {
    Double oldCoord2 = this.coord2;
    this.coord2 = coord2;
    firePropertyChange(SpatialConstants.coord2, oldCoord2, this.coord2);
  }


  /**
   * Unsets the variable coord2
   *
   * @return {@code true}, if coord2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoord2() {
    if (isSetCoord2()) {
      Double oldCoord2 = coord2;
      coord2 = null;
      firePropertyChange(SpatialConstants.coord2, oldCoord2, coord2);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of coord3
   *
   * @return the value of coord3
   */
  public double getCoord3() {
    if (isSetCoord3()) {
      return coord3;
    }
    throw new PropertyUndefinedError(SpatialConstants.coord3, this);
  }


  /**
   * Returns whether coord3 is set
   *
   * @return whether coord3 is set
   */
  public boolean isSetCoord3() {
    return coord3 != null;
  }


  /**
   * Sets the value of coord3
   * @param coord3
   */
  public void setCoord3(double coord3) {
    Double oldCoord3 = coord1;
    this.coord3 = coord3;
    firePropertyChange(SpatialConstants.coord3, oldCoord3, this.coord3);
  }


  /**
   * Unsets the variable coord3
   *
   * @return {@code true}, if coord3 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoord3() {
    if (isSetCoord3()) {
      Double oldCoord3 = coord3;
      coord3 = null;
      firePropertyChange(SpatialConstants.coord3, oldCoord3, coord3);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1627;
    int hashCode = super.hashCode();
    if (isSetCoord1()) {
      hashCode += prime * getCoord1();
    }
    if (isSetCoord2()) {
      hashCode += prime * getCoord2();
    }
    if (isSetCoord3()) {
      hashCode += prime * getCoord3();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetCoord1()) {
      attributes.put(SpatialConstants.shortLabel + ":coord1", String.valueOf(getCoord1()));
    }
    if (isSetCoord2()) {
      attributes.put(SpatialConstants.shortLabel + ":coord2", String.valueOf(getCoord2()));
    }
    if (isSetCoord3()) {
      attributes.put(SpatialConstants.shortLabel + ":coord3", String.valueOf(getCoord3()));
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
      if (attributeName.equals(SpatialConstants.coord1)) {
        try {
          setCoord1(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coord1, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.coord2)) {
        try {
          setCoord2(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coord2, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.coord3)) {
        try {
          setCoord3(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.coord3, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
