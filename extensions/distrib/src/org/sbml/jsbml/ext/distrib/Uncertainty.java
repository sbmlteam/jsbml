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
 * The {@link Uncertainty} class has two optional children: an {@link UncertStatistics} child and a {@link Distribution} child. Either or both
 * may be used, depending on the information about the parent that the modeler wishes to store in this object. 
 * 
 * <p>If neither is present, this means that no information about the uncertainty of the object is provided by this package.
 * The Uncertainty may be annotated to provide more information about why this is.</p>
 * 
 * <p>Note that the described uncertainty for elements that change in value over time apply only to the element's
 * uncertainty at a snapshot in time, and not the uncertainty in how it changes in time. For typical simulations, this
 * means the element's initial condition. Note too that the description of the uncertainty of a Species should describe
 * the uncertainty of its amount, not the uncertainty of its concentration. The 'primary' mathematical meaning of a
 * Species in SBML is always the amount; the concentration may be used, but is considered to be derived.
 *
 * @author Nicolas Rodriguez
 * @since 1.4
 */
public class Uncertainty extends AbstractDistribSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -904719821379100471L;

  // TODO - remove uncertML and replace by distribution and UncertStatitics
  /**
   * 
   */
  private UncertStatistics uncertStatistics;

  /**
   * 
   */
  private Distribution distribution;

  /**
   * Creates an Uncertainty instance
   */
  public Uncertainty() {
    super();
    initDefaults();
  }


  /**
   * Creates a Uncertainty instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public Uncertainty(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a Uncertainty instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public Uncertainty(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a Uncertainty instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public Uncertainty(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a Uncertainty instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public Uncertainty(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   * @param obj
   */
  public Uncertainty(Uncertainty obj) {
    super(obj);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = DistribConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Uncertainty clone() {
    return new Uncertainty(this);
  }

  
  /**
   * Returns the value of {@link #distribution}.
   *
   * @return the value of {@link #distribution}.
   */
  public Distribution getDistribution() {
    if (isSetDistribution()) {
      return distribution;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.distribution, this);
  }


  /**
   * Returns whether {@link #distribution} is set.
   *
   * @return whether {@link #distribution} is set.
   */
  public boolean isSetDistribution() {
    return this.distribution != null;
  }


  /**
   * Sets the value of distribution
   *
   * @param distribution the value of distribution to be set.
   */
  public void setDistribution(Distribution distribution) {
    Distribution oldDistribution = this.distribution;
    this.distribution = distribution;
    firePropertyChange(DistribConstants.distribution, oldDistribution, this.distribution);
  }


  /**
   * Unsets the variable distribution.
   *
   * @return {@code true} if distribution was set before, otherwise {@code false}.
   */
  public boolean unsetDistribution() {
    if (isSetDistribution()) {
      Distribution oldDistribution = this.distribution;
      this.distribution = null;
      firePropertyChange(DistribConstants.distribution, oldDistribution, this.distribution);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #uncertStatistics}.
   *
   * @return the value of {@link #uncertStatistics}.
   */
  public UncertStatistics getUncertStatistics() {
    if (isSetUncertStatistics()) {
      return uncertStatistics;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.uncertStatistics, this);
  }


  /**
   * Returns whether {@link #uncertStatistics} is set.
   *
   * @return whether {@link #uncertStatistics} is set.
   */
  public boolean isSetUncertStatistics() {
    return this.uncertStatistics != null;
  }


  /**
   * Sets the value of uncertStatistics
   *
   * @param uncertStatistics the value of uncertStatistics to be set.
   */
  public void setUncertStatistics(UncertStatistics uncertStatistics) {
    UncertStatistics oldUncertStatistics = this.uncertStatistics;
    this.uncertStatistics = uncertStatistics;
    firePropertyChange(DistribConstants.uncertStatistics, oldUncertStatistics, this.uncertStatistics);
  }


  /**
   * Unsets the variable uncertStatistics.
   *
   * @return {@code true} if uncertStatistics was set before, otherwise {@code false}.
   */
  public boolean unsetUncertStatistics() {
    if (isSetUncertStatistics()) {
      UncertStatistics oldUncertStatistics = this.uncertStatistics;
      this.uncertStatistics = null;
      firePropertyChange(DistribConstants.uncertStatistics, oldUncertStatistics, this.uncertStatistics);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetUncertStatistics()) {
      count++;
    }
    if (isSetDistribution()) {
      count++;
    }

    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
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

    if (isSetUncertStatistics()) {
      if (pos == index) {
        return getUncertStatistics();
      }
      pos++;
    }

    if (isSetDistribution()) {
      if (pos == index) {
        return getDistribution();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result =
      prime * result + ((distribution == null) ? 0 : distribution.hashCode());
    result = prime * result
      + ((uncertStatistics == null) ? 0 : uncertStatistics.hashCode());
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
    Uncertainty other = (Uncertainty) obj;
    if (distribution == null) {
      if (other.distribution != null) {
        return false;
      }
    } else if (!distribution.equals(other.distribution)) {
      return false;
    }
    if (uncertStatistics == null) {
      if (other.uncertStatistics != null) {
        return false;
      }
    } else if (!uncertStatistics.equals(other.uncertStatistics)) {
      return false;
    }
    return true;
  }

  
}
