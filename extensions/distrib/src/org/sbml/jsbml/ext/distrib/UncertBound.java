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

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;


/**
 * @author rodrigue
 * @since 1.2
 */
public class UncertBound extends UncertValue {

  // TODO - implements XML attributes, equals and hashcode

  /**
   * 
   */
  private Boolean inclusive;
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * 
   */
  public UncertBound() {
    // TODO Auto-generated constructor stub
  }


  /**
   * @param level
   * @param version
   */
  public UncertBound(int level, int version) {
    super(level, version);
  }


  /**
   * @param sb
   */
  public UncertBound(SBase sb) {
    super(sb);
  }


  /**
   * @param id
   */
  public UncertBound(String id) {
    super(id);
  }


  /**
   * @param id
   * @param level
   * @param version
   */
  public UncertBound(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UncertBound(String id, String name, int level, int version) {
    super(id, name, level, version);
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
  
}
