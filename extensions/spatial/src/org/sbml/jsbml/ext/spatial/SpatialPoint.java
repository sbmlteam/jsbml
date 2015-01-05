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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpatialPoint extends AbstractSpatialNamedSBase {

  /**
   * 
   */
  private String domain;

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
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1171421071571874086L;

  /**
   * 
   */
  public SpatialPoint() {
    super();
  }

  /**
   * @param sp
   */
  public SpatialPoint(SpatialPoint sp) {
    super(sp);


    if (sp.isSetDomain()) {
      domain = new String(sp.getDomain());
    }

    if (sp.isSetCoord1()) {
      coord1 = new Double(sp.getCoord1());
    }

    if (sp.isSetCoord2()) {
      coord2 = new Double(sp.getCoord2());
    }

    if (sp.isSetCoord3()) {
      coord3 = new Double(sp.getCoord3());
    }
  }


  /**
   * @param level
   * @param version
   */
  public SpatialPoint(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public SpatialPoint(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public SpatialPoint clone() {
    return new SpatialPoint(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SpatialPoint sp = (SpatialPoint) object;

      equal &= sp.isSetDomain() == isSetDomain();
      if (equal && isSetDomain()) {
        equal &= sp.getDomain().equals(getDomain());
      }

      equal &= sp.isSetCoord1() == isSetCoord1();
      if (equal && isSetCoord1()) {
        equal &= sp.getCoord1() == getCoord1();
      }

      equal &= sp.isSetCoord2() == isSetCoord2();
      if (equal && isSetCoord2()) {
        equal &= sp.getCoord2() == getCoord2();
      }

      equal &= sp.isSetCoord3() == isSetCoord3();
      if (equal && isSetCoord3()) {
        equal &= sp.getCoord3() == getCoord3();
      }
      //TODO: add equal statements for each attribute and class
    }
    return equal;
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
    //TODO: This is necessary if we cannot return null here.
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
    double oldCoord1 = this.coord1;
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
      double oldCoord1 = coord1;
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
    //TODO: This is necessary if we cannot return null here.
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
    double oldCoord2 = this.coord2;
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
      double oldCoord2 = coord2;
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
    // TODO:This is necessary if we cannot return null here.
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
    double oldCoord3 = this.coord3;
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
      double oldCoord3 = coord3;
      coord3 = null;
      firePropertyChange(SpatialConstants.coord3, oldCoord3, coord3);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of domain
   *
   * @return the value of domain
   */
  public String getDomain() {
    if (isSetDomain()) {
      return domain;
    }
    // TODO: This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.domain, this);
  }


  /**
   * Returns whether domain is set
   *
   * @return whether domain is set
   */
  public boolean isSetDomain() {
    return domain != null;
  }

  /**
   * Sets the value of domain
   * @param domain
   */
  public void setDomain(String domain) {
    String oldDomain = this.domain;
    this.domain = domain;
    firePropertyChange(SpatialConstants.domain, oldDomain, this.domain);
  }

  /**
   * Unsets the variable domain
   *
   * @return {@code true}, if domain was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDomain() {
    if (isSetDomain()) {
      String oldDomain = domain;
      domain = null;
      firePropertyChange(SpatialConstants.domain, oldDomain, domain);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetDomain()) {
      hashCode += prime * getDomain().hashCode();
    }

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


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetDomain()) {
      attributes.remove("domain");
      attributes.put(SpatialConstants.shortLabel + ":domain", getDomain());
    }

    if (isSetCoord1()) {
      attributes.remove("coord1");
      attributes.put(SpatialConstants.shortLabel + ":coord1",
        String.valueOf(getCoord1()));
    }

    if (isSetCoord2()) {
      attributes.remove("coord1");
      attributes.put(SpatialConstants.shortLabel + ":coord1",
        String.valueOf(getCoord2()));
    }

    if (isSetCoord3()) {
      attributes.remove("coord3");
      attributes.put(SpatialConstants.shortLabel + ":coord3",
        String.valueOf(getCoord3()));
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.domain)) {
        try {
          setDomain(value);
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.domain);
        }
      }

      else if (attributeName.equals(SpatialConstants.coord1)) {
        try {
          setCoord1(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.coord1);
        }
      }

      else if (attributeName.equals(SpatialConstants.coord2)) {
        try {
          setCoord2(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.coord2);
        }
      }

      else if (attributeName.equals(SpatialConstants.coord3)) {
        try {
          setCoord3(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.coord3);
        }
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
    StringBuilder builder = new StringBuilder();
    builder.append("SpatialPoint [domain=");
    builder.append(domain);
    builder.append(", coord1=");
    builder.append(coord1);
    builder.append(", coord2=");
    builder.append(coord2);
    builder.append(", coord3=");
    builder.append(coord3);
    builder.append("]");
    return builder.toString();
  }



}
