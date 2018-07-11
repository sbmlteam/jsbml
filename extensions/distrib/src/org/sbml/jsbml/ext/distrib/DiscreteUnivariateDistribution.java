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
 * The abstract {@link DiscreteUnivariateDistribution} class is the base class for a wide variety of distributions, all of which
 * describe a potentially-bounded range of probabilities of discrete values. The most commonly-used distributions in
 * this class is probably the {@link PoissonDistribution}. Distributions that always return integers fall in this category, which
 * often involve events happening at particular frequencies.
 * 
 * <p>All {@link DiscreteUnivariateDistribution} elements (like {@link ContinuousUnivariateDistribution} elements) may have two optional
 * children: 'lowerTruncationBound' and 'upperTruncationBound', both of the class UncertBound (defined
 * below). Either element, if present, limit the range of possible sampled values from the distribution. The
 * 'lowerTruncationBound' defines the value below which no sampling may take place (inclusive or not, as defined by
 * that element's inclusive attribute), and the 'upperTruncationBound' defines the value above which no sampling
 * may take place. These bounds may fall between the possible discrete values being returned: as an example, for
 * a distribution that returned an integer in the series [0, 1, 2, ...], if it was given a 'lowerTruncationBound' of 1.5,
 * the lowest value it could return would be 2. In this case, the value of the inclusive attribute on the {@link UncertBound}
 * would be immaterial, as '1.5' could never be returned.</p>
 * 
 * @author rodrigue
 * @since 1.4
 */
public abstract class DiscreteUnivariateDistribution extends UnivariateDistribution {

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
  public DiscreteUnivariateDistribution(DiscreteUnivariateDistribution sb) {
    super(sb);
    
    if (sb.isSetTruncationLowerBound()) {
      setTruncationLowerBound(sb.getTruncationLowerBound().clone());
    }
    if (sb.isSetTruncationUpperBound()) {
      setTruncationUpperBound(sb.getTruncationUpperBound().clone());
    }
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


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result
      + ((truncationLowerBound == null) ? 0 : truncationLowerBound.hashCode());
    result = prime * result
      + ((truncationUpperBound == null) ? 0 : truncationUpperBound.hashCode());
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
    DiscreteUnivariateDistribution other = (DiscreteUnivariateDistribution) obj;
    if (truncationLowerBound == null) {
      if (other.truncationLowerBound != null) {
        return false;
      }
    } else if (!truncationLowerBound.equals(other.truncationLowerBound)) {
      return false;
    }
    if (truncationUpperBound == null) {
      if (other.truncationUpperBound != null) {
        return false;
      }
    } else if (!truncationUpperBound.equals(other.truncationUpperBound)) {
      return false;
    }
    return true;
  }

  
  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  public int getChildCount() {
    int count = super.getChildCount();

     if (isSetTruncationLowerBound()) {
      count++;
     }
     if (isSetTruncationUpperBound()) {
       count++;
      }
     
    return count;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
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

    if (isSetTruncationLowerBound()) {
      if (pos == index) {
        return getTruncationLowerBound();
      }
      pos++;
    }

    if (isSetTruncationUpperBound()) {
      if (pos == index) {
        return getTruncationUpperBound();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

}
