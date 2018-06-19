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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;


/**
 * @author rodrigue
 * @since 1.4
 */
public class NormalDistribution extends ContinuousUnivariateDistribution {

  // TODO - implements XML attributes, equals and hashcode

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private UncertValue mean;
  
  /**
   * 
   */
  private UncertValue stddev;
  
  /**
   * 
   */
  private UncertValue variance;
  
  

  /**
   * Creates a new instance of {@link NormalDistribution}
   * 
   */
  public NormalDistribution() {
  }


  /**
   * Creates a new instance of {@link NormalDistribution}
   * 
   * @param level
   * @param version
   */
  public NormalDistribution(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link NormalDistribution}
   * 
   * @param sb
   */
  public NormalDistribution(NormalDistribution sb) {
    super(sb);
    
    if (sb.isSetMean()) {
      setMean(sb.getMean().clone());
    }
    if (sb.isSetStddev()) {
      setStddev(sb.getStddev().clone());
    }
    if (sb.isSetVariance()) {
      setVariance(sb.getVariance().clone());
    }
  }


  /**
   * Creates a new instance of {@link NormalDistribution}
   * 
   * @param id
   * @param level
   * @param version
   */
  public NormalDistribution(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link NormalDistribution}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public NormalDistribution(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /**
   * Creates a new instance of {@link NormalDistribution}
   * 
   * @param id
   */
  public NormalDistribution(String id) {
    super(id);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public AbstractSBase clone() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  /**
   * Returns the value of {@link #mean}.
   *
   * @return the value of {@link #mean}.
   */
  public UncertValue getMean() {
    if (isSetMean()) {
      return mean;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.mean, this);
  }


  /**
   * Returns whether {@link #mean} is set.
   *
   * @return whether {@link #mean} is set.
   */
  public boolean isSetMean() {
    return this.mean != null;
  }


  /**
   * Sets the value of mean
   *
   * @param mean the value of mean to be set.
   */
  public void setMean(UncertValue mean) {
    UncertValue oldMean = this.mean;
    this.mean = mean;
    firePropertyChange(DistribConstants.mean, oldMean, this.mean);
  }


  /**
   * Unsets the variable mean.
   *
   * @return {@code true} if mean was set before, otherwise {@code false}.
   */
  public boolean unsetMean() {
    if (isSetMean()) {
      UncertValue oldMean = this.mean;
      this.mean = null;
      firePropertyChange(DistribConstants.mean, oldMean, this.mean);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #stddev}.
   *
   * @return the value of {@link #stddev}.
   */
  public UncertValue getStddev() {
    if (isSetStddev()) {
      return stddev;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.stddev, this);
  }


  /**
   * Returns whether {@link #stddev} is set.
   *
   * @return whether {@link #stddev} is set.
   */
  public boolean isSetStddev() {
    return this.stddev != null;
  }


  /**
   * Sets the value of stddev
   *
   * @param stddev the value of stddev to be set.
   */
  public void setStddev(UncertValue stddev) {
    UncertValue oldStddev = this.stddev;
    this.stddev = stddev;
    firePropertyChange(DistribConstants.stddev, oldStddev, this.stddev);
  }


  /**
   * Unsets the variable stddev.
   *
   * @return {@code true} if stddev was set before, otherwise {@code false}.
   */
  public boolean unsetStddev() {
    if (isSetStddev()) {
      UncertValue oldStddev = this.stddev;
      this.stddev = null;
      firePropertyChange(DistribConstants.stddev, oldStddev, this.stddev);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #variance}.
   *
   * @return the value of {@link #variance}.
   */
  public UncertValue getVariance() {
    if (isSetVariance()) {
      return variance;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.variance, this);
  }


  /**
   * Returns whether {@link #variance} is set.
   *
   * @return whether {@link #variance} is set.
   */
  public boolean isSetVariance() {
    return this.variance != null;
  }


  /**
   * Sets the value of variance
   *
   * @param variance the value of variance to be set.
   */
  public void setVariance(UncertValue variance) {
    UncertValue oldVariance = this.variance;
    this.variance = variance;
    firePropertyChange(DistribConstants.variance, oldVariance, this.variance);
  }


  /**
   * Unsets the variable variance.
   *
   * @return {@code true} if variance was set before, otherwise {@code false}.
   */
  public boolean unsetVariance() {
    if (isSetVariance()) {
      UncertValue oldVariance = this.variance;
      this.variance = null;
      firePropertyChange(DistribConstants.variance, oldVariance, this.variance);
      return true;
    }
    return false;
  }
  
  
}
