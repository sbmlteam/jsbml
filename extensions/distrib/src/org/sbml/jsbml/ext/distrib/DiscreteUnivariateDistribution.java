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
 * @since 1.4
 */
public abstract class DiscreteUnivariateDistribution extends UnivariateDistribution {

  // TODO - implements XML attributes, equals and hashcode

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private UncertBound truncationLowerBound;
  
  /**
   * 
   */
  private UncertBound truncationUpperBound;
  
  
  
  /**
   * Creates a new instance of {@link DiscreteUnivariateDistribution}
   * 
   */
  public DiscreteUnivariateDistribution() {
    super();
  }


  /**
   * Creates a new instance of {@link DiscreteUnivariateDistribution}
   * 
   * @param level
   * @param version
   */
  public DiscreteUnivariateDistribution(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link DiscreteUnivariateDistribution}
   * 
   * @param sb
   */
  public DiscreteUnivariateDistribution(SBase sb) {
    super(sb);
  }


  /**
   * Creates a new instance of {@link DiscreteUnivariateDistribution}
   * 
   * @param id
   * @param level
   * @param version
   */
  public DiscreteUnivariateDistribution(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link DiscreteUnivariateDistribution}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public DiscreteUnivariateDistribution(String id, String name, int level,
    int version) {
    super(id, name, level, version);
  }


  /**
   * Creates a new instance of {@link DiscreteUnivariateDistribution}
   * 
   * @param id
   */
  public DiscreteUnivariateDistribution(String id) {
    super(id);
  }


  /**
   * Returns the value of {@link #truncationLowerBound}.
   *
   * @return the value of {@link #truncationLowerBound}.
   */
  public UncertBound getTruncationLowerBound() {
    if (isSetTruncationLowerBound()) {
      return truncationLowerBound;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.truncationLowerBound, this);
  }


  /**
   * Returns whether {@link #truncationLowerBound} is set.
   *
   * @return whether {@link #truncationLowerBound} is set.
   */
  public boolean isSetTruncationLowerBound() {
    return this.truncationLowerBound != null;
  }


  /**
   * Sets the value of truncationLowerBound
   *
   * @param truncationLowerBound the value of truncationLowerBound to be set.
   */
  public void setTruncationLowerBound(UncertBound truncationLowerBound) {
    UncertBound oldTruncationLowerBound = this.truncationLowerBound;
    this.truncationLowerBound = truncationLowerBound;
    firePropertyChange(DistribConstants.truncationLowerBound, oldTruncationLowerBound, this.truncationLowerBound);
  }


  /**
   * Unsets the variable truncationLowerBound.
   *
   * @return {@code true} if truncationLowerBound was set before, otherwise {@code false}.
   */
  public boolean unsetTruncationLowerBound() {
    if (isSetTruncationLowerBound()) {
      UncertBound oldTruncationLowerBound = this.truncationLowerBound;
      this.truncationLowerBound = null;
      firePropertyChange(DistribConstants.truncationLowerBound, oldTruncationLowerBound, this.truncationLowerBound);
      return true;
    }
    return false;
  }

  
  /**
   * Returns the value of {@link #truncationUpperBound}.
   *
   * @return the value of {@link #truncationUpperBound}.
   */
  public UncertBound getTruncationUpperBound() {
    if (isSetTruncationUpperBound()) {
      return truncationUpperBound;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.truncationUpperBound, this);
  }


  /**
   * Returns whether {@link #truncationUpperBound} is set.
   *
   * @return whether {@link #truncationUpperBound} is set.
   */
  public boolean isSetTruncationUpperBound() {
    return this.truncationUpperBound != null;
  }


  /**
   * Sets the value of truncationUpperBound
   *
   * @param truncationUpperBound the value of truncationUpperBound to be set.
   */
  public void setTruncationUpperBound(UncertBound truncationUpperBound) {
    UncertBound oldTruncationUpperBound = this.truncationUpperBound;
    this.truncationUpperBound = truncationUpperBound;
    firePropertyChange(DistribConstants.truncationUpperBound, oldTruncationUpperBound, this.truncationUpperBound);
  }


  /**
   * Unsets the variable truncationUpperBound.
   *
   * @return {@code true} if truncationUpperBound was set before, otherwise {@code false}.
   */
  public boolean unsetTruncationUpperBound() {
    if (isSetTruncationUpperBound()) {
      UncertBound oldTruncationUpperBound = this.truncationUpperBound;
      this.truncationUpperBound = null;
      firePropertyChange(DistribConstants.truncationUpperBound, oldTruncationUpperBound, this.truncationUpperBound);
      return true;
    }
    return false;
  }

}
