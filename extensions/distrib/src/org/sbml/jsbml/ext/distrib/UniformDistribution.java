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
 * The {@link UniformDistribution} is a {@link ContinuousUnivariateDistribution} that defines a minimum (a),
 * maximum (b) and an optional numberOfClasses. The minimum value must be less than the maximum value. If
 * numberOfClasses is defined, its value must be an integer greater than or equal to two.
 * A random variable x follows a uniform distribution if the probability density function (pdf) is of the form:
 * 1 / (b - a)
 * 
 * <p>The distribution assigns equal probability to all events within the chosen domain between (and including) the
 * minimum (a) and the maximum (b).</p>
 * 
 * <p>If numberOfClasses is included, the uniform range is divided into numberOfClasses - 1 sections, and each of the
 * borders of those sections are equally likely to be returned. If numberOfClasses is 2 (the minimum), the range just
 * has 2 - 1 = 1 section, and the borders of that section (the minimum and maximum) are the two possible return values.
 * If numberofClasses is 3, the range is broken into 3 - 1 = 2 sections, leaving the minimum, maximum, and mean as the
 * three possible return values, etc.</p>
 * 
 * @author rodrigue
 * @since 1.4
 */
public class UniformDistribution extends ContinuousUnivariateDistribution {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private UncertValue minimum;
  
  /**
   * 
   */
  private UncertValue maximum;
  
  /**
   * 
   */
  private UncertValue numberOfClasses;
  
  /**
   * Creates a new instance of {@link UniformDistribution}
   * 
   */
  public UniformDistribution() {
  }


  /**
   * Creates a new instance of {@link UniformDistribution}
   * 
   * @param level
   * @param version
   */
  public UniformDistribution(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link UniformDistribution}
   * 
   * @param sb
   */
  public UniformDistribution(UniformDistribution sb) {
    super(sb);
    
    if (sb.isSetMinimum()) {
      setMinimum(sb.getMinimum().clone());
    }
    if (sb.isSetMaximum()) {
      setMaximum(sb.getMaximum().clone());
    }
    if (sb.isSetNumberOfClasses()) {
      setNumberOfClasses(sb.getNumberOfClasses().clone());
    }
  }


  /**
   * Creates a new instance of {@link UniformDistribution}
   * 
   * @param id
   * @param level
   * @param version
   */
  public UniformDistribution(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link UniformDistribution}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UniformDistribution(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /**
   * Creates a new instance of {@link UniformDistribution}
   * 
   * @param id
   */
  public UniformDistribution(String id) {
    super(id);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public UniformDistribution clone() {
    return new UniformDistribution(this);
  }
  
  
  /**
   * Returns the value of {@link #minimum}.
   *
   * @return the value of {@link #minimum}.
   */
  public UncertValue getMinimum() {
    if (isSetMinimum()) {
      return minimum;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.minimum, this);
  }


  /**
   * Returns whether {@link #minimum} is set.
   *
   * @return whether {@link #minimum} is set.
   */
  public boolean isSetMinimum() {
    return this.minimum != null;
  }


  /**
   * Sets the value of minimum
   *
   * @param minimum the value of minimum to be set.
   */
  public void setMinimum(UncertValue minimum) {
    UncertValue oldMinimum = this.minimum;
    this.minimum = minimum;
    firePropertyChange(DistribConstants.minimum, oldMinimum, this.minimum);
  }


  /**
   * Unsets the variable minimum.
   *
   * @return {@code true} if minimum was set before, otherwise {@code false}.
   */
  public boolean unsetMinimum() {
    if (isSetMinimum()) {
      UncertValue oldMinimum = this.minimum;
      this.minimum = null;
      firePropertyChange(DistribConstants.minimum, oldMinimum, this.minimum);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #maximum}.
   *
   * @return the value of {@link #maximum}.
   */
  public UncertValue getMaximum() {
    if (isSetMaximum()) {
      return maximum;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.maximum, this);
  }


  /**
   * Returns whether {@link #maximum} is set.
   *
   * @return whether {@link #maximum} is set.
   */
  public boolean isSetMaximum() {
    return this.maximum != null;
  }


  /**
   * Sets the value of maximum
   *
   * @param maximum the value of maximum to be set.
   */
  public void setMaximum(UncertValue maximum) {
    UncertValue oldMaximum = this.maximum;
    this.maximum = maximum;
    firePropertyChange(DistribConstants.maximum, oldMaximum, this.maximum);
  }


  /**
   * Unsets the variable maximum.
   *
   * @return {@code true} if maximum was set before, otherwise {@code false}.
   */
  public boolean unsetMaximum() {
    if (isSetMaximum()) {
      UncertValue oldMaximum = this.maximum;
      this.maximum = null;
      firePropertyChange(DistribConstants.maximum, oldMaximum, this.maximum);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #numberOfClasses}.
   *
   * @return the value of {@link #numberOfClasses}.
   */
  public UncertValue getNumberOfClasses() {
    if (isSetNumberOfClasses()) {
      return numberOfClasses;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.numberOfClasses, this);
  }


  /**
   * Returns whether {@link #numberOfClasses} is set.
   *
   * @return whether {@link #numberOfClasses} is set.
   */
  public boolean isSetNumberOfClasses() {
    return this.numberOfClasses != null;
  }


  /**
   * Sets the value of numberOfClasses
   *
   * @param numberOfClasses the value of numberOfClasses to be set.
   */
  public void setNumberOfClasses(UncertValue numberOfClasses) {
    UncertValue oldNumberOfClasses = this.numberOfClasses;
    this.numberOfClasses = numberOfClasses;
    firePropertyChange(DistribConstants.numberOfClasses, oldNumberOfClasses, this.numberOfClasses);
  }


  /**
   * Unsets the variable numberOfClasses.
   *
   * @return {@code true} if numberOfClasses was set before, otherwise {@code false}.
   */
  public boolean unsetNumberOfClasses() {
    if (isSetNumberOfClasses()) {
      UncertValue oldNumberOfClasses = this.numberOfClasses;
      this.numberOfClasses = null;
      firePropertyChange(DistribConstants.numberOfClasses, oldNumberOfClasses, this.numberOfClasses);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3137;
    int result = super.hashCode();
    result = prime * result + ((maximum == null) ? 0 : maximum.hashCode());
    result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
    result = prime * result
      + ((numberOfClasses == null) ? 0 : numberOfClasses.hashCode());
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
    UniformDistribution other = (UniformDistribution) obj;
    if (maximum == null) {
      if (other.maximum != null) {
        return false;
      }
    } else if (!maximum.equals(other.maximum)) {
      return false;
    }
    if (minimum == null) {
      if (other.minimum != null) {
        return false;
      }
    } else if (!minimum.equals(other.minimum)) {
      return false;
    }
    if (numberOfClasses == null) {
      if (other.numberOfClasses != null) {
        return false;
      }
    } else if (!numberOfClasses.equals(other.numberOfClasses)) {
      return false;
    }
    return true;
  }
  
  
  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetMinimum()) {
      count++;
    }
    if (isSetMaximum()) {
      count++;
    }
    if (isSetNumberOfClasses()) {
      count++;
    }
    
    return count;
  }


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

    if (isSetMinimum()) {
      if (pos == index) {
        return getMinimum();
      }
      pos++;
    }
    if (isSetMaximum()) {
      if (pos == index) {
        return getMaximum();
      }
      pos++;
    }
    if (isSetNumberOfClasses()) {
      if (pos == index) {
        return getNumberOfClasses();
      }
      pos++;
    }
    
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  
}
