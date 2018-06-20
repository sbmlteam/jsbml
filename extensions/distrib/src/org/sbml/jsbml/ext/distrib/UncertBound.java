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
package org.sbml.jsbml.ext.distrib;

import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;


/**
 * The {@link UncertBound} class inherits from {@link UncertValue} and adds a single required Boolean attribute 'inclusive'. This
 * attribute indicates whether the value the bound represents is to be included in that range ('true') or not ('false').
 * 
 * This allows the creation of either 'open' or 'closed' boundaries of the ranges it is used to define.
 *
 * @author rodrigue
 * @since 1.4
 */
public class UncertBound extends UncertValue {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private Boolean inclusive;
  
  
  /**
   * Creates a new instance of {@link UncertBound}
   * 
   */
  public UncertBound() {
    super();
  }


  /**
   * Creates a new instance of {@link UncertBound}
   * 
   * @param level
   * @param version
   */
  public UncertBound(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link UncertBound}
   * 
   * @param sb
   */
  public UncertBound(UncertBound sb) {
    super(sb);

    if (sb.isSetInclusive()) {
      setInclusive(sb.getInclusive());
    }
  }


  /**
   * Creates a new instance of {@link UncertBound}
   * 
   * @param id
   * @param level
   * @param version
   */
  public UncertBound(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link UncertBound}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UncertBound(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /**
   * Creates a new instance of {@link UncertBound}
   * 
   * @param id
   */
  public UncertBound(String id) {
    super(id);
  }

  @Override
  public UncertBound clone() {
    return new UncertBound(this);
  }

  /**
   * Returns the value of {@link #inclusive}.
   *
   * @return the value of {@link #inclusive}.
   */
  public boolean getInclusive() {

    if (isSetInclusive()) {
      return inclusive;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.inclusive, this);
  }


  /**
   * Returns whether {@link #inclusive} is set.
   *
   * @return whether {@link #inclusive} is set.
   */
  public boolean isSetInclusive() {
    return this.inclusive != null;
  }


  /**
   * Sets the value of inclusive
   *
   * @param inclusive the value of inclusive to be set.
   */
  public void setInclusive(boolean inclusive) {
    boolean oldInclusive = this.inclusive;
    this.inclusive = inclusive;
    firePropertyChange(DistribConstants.inclusive, oldInclusive, this.inclusive);
  }


  /**
   * Unsets the variable inclusive.
   *
   * @return {@code true} if inclusive was set before, otherwise {@code false}.
   */
  public boolean unsetInclusive() {
    if (isSetInclusive()) {
      boolean oldInclusive = this.inclusive;
      this.inclusive = null;
      firePropertyChange(DistribConstants.inclusive, oldInclusive, this.inclusive);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3229;
    int result = super.hashCode();
    result = prime * result + ((inclusive == null) ? 0 : inclusive.hashCode());
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
    UncertBound other = (UncertBound) obj;
    if (inclusive == null) {
      if (other.inclusive != null) {
        return false;
      }
    } else if (!inclusive.equals(other.inclusive)) {
      return false;
    }
    return true;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.distrib.UncertValue#writeXMLAttributes()
   */
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetInclusive()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.inclusive, inclusive.toString());
    }
    
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.distrib.UncertValue#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(DistribConstants.inclusive)) {
        setInclusive(StringTools.parseSBMLBoolean(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
