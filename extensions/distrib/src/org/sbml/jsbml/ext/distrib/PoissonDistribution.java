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

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.PropertyUndefinedError;


/**
 * The {@link PoissonDistribution} is a {@link DiscreteUnivariateDistribution} that defines the {@link UncertValue} child 'rate' (λ).
 * 
 * <p>The 'rate' value must be positive.
 * A random variable x follows a Poisson distribution if the probability mass function (pmf) is of the form:</p>
 * (λ^x / x!) . exp(-λ)
 *  
 * <p>The Poisson distribution can be used to model the number of events occurring within fixed time period of time.</p>
 *
 * @author rodrigue
 * @since 1.4
 */
public class PoissonDistribution extends DiscreteUnivariateDistribution {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private UncertValue rate;
  
  /**
   * Creates a new instance of {@link PoissonDistribution}
   * 
   */
  public PoissonDistribution() {
  }


  /**
   * Creates a new instance of {@link PoissonDistribution}
   * 
   * @param level
   * @param version
   */
  public PoissonDistribution(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link PoissonDistribution}
   * 
   * @param sb
   */
  public PoissonDistribution(PoissonDistribution sb) {
    super(sb);
    
    if (sb.isSetRate()) {
      setRate(sb.getRate().clone());
    }
  }


  /**
   * Creates a new instance of {@link PoissonDistribution}
   * 
   * @param id
   */
  public PoissonDistribution(String id) {
    super(id);
  }


  /**
   * Creates a new instance of {@link PoissonDistribution}
   * 
   * @param id
   * @param level
   * @param version
   */
  public PoissonDistribution(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link PoissonDistribution}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public PoissonDistribution(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public PoissonDistribution clone() {
    return new PoissonDistribution(this);
  }
  
  
  /**
   * Returns the value of {@link #rate}.
   *
   * @return the value of {@link #rate}.
   */
  public UncertValue getRate() {
    if (isSetRate()) {
      return rate;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.rate, this);
  }


  /**
   * Returns whether {@link #rate} is set.
   *
   * @return whether {@link #rate} is set.
   */
  public boolean isSetRate() {
    return this.rate != null;
  }


  /**
   * Sets the value of rate
   *
   * @param rate the value of rate to be set.
   */
  public void setRate(UncertValue rate) {
    UncertValue oldRate = this.rate;
    this.rate = rate;
    firePropertyChange(DistribConstants.rate, oldRate, this.rate);
  }


  /**
   * Unsets the variable rate.
   *
   * @return {@code true} if rate was set before, otherwise {@code false}.
   */
  public boolean unsetRate() {
    if (isSetRate()) {
      UncertValue oldRate = this.rate;
      this.rate = null;
      firePropertyChange(DistribConstants.rate, oldRate, this.rate);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3271;
    int result = super.hashCode();
    result = prime * result + ((rate == null) ? 0 : rate.hashCode());
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
    PoissonDistribution other = (PoissonDistribution) obj;
    if (rate == null) {
      if (other.rate != null) {
        return false;
      }
    } else if (!rate.equals(other.rate)) {
      return false;
    }
    return true;
  }
  
  
  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.distrib.DiscreteUnivariateDistribution#getChildCount()
   */
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetRate()) {
      count++;
    }
    
    return count;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.distrib.DiscreteUnivariateDistribution#getChildAt(int)
   */
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }

    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetRate()) {
      if (pos == index) {
        return getRate();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  
}
