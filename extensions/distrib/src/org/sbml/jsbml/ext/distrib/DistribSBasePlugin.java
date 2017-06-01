/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class DistribSBasePlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1732369832900356125L;
  /**
   * 
   */
  private Uncertainty uncertainty;

  /**
   * Creates a new {@link DistribSBasePlugin} instance.
   * 
   * @param sbase the {@link SBase} to be extended.
   */
  public DistribSBasePlugin(SBase sbase) {
    super(sbase);
    setPackageVersion(-1);
  }

  /**
   * Creates a new {@link DistribSBasePlugin} instance, cloned from
   * the input parameter.
   * 
   * @param obj the {@link DistribSBasePlugin} to clone
   */
  public DistribSBasePlugin(DistribSBasePlugin obj) {
    super(obj);

    if (obj.isSetUncertainty()) {
      setUncertainty(obj.getUncertainty().clone());
    }
  }


  /**
   * Creates and Sets a new {@link Uncertainty} instance.
   * 
   * @return the newly created {@link Uncertainty} instance.
   */
  public Uncertainty createUncertainty() {
    Uncertainty uncert = new Uncertainty();
    setUncertainty(uncert);
    return uncert;
  }


  /**
   * Returns the value of {@link #uncertainty}.
   *
   * @return the value of {@link #uncertainty}.
   */
  public Uncertainty getUncertainty() {
    if (isSetUncertainty()) {
      return uncertainty;
    }

    return null;
  }


  /**
   * Returns whether {@link #uncertainty} is set.
   *
   * @return whether {@link #uncertainty} is set.
   */
  public boolean isSetUncertainty() {
    return uncertainty != null;
  }


  /**
   * Sets the value of uncertainty
   *
   * @param uncertainty the value of uncertainty to be set.
   */
  public void setUncertainty(Uncertainty uncertainty) {
    Uncertainty oldUncertainty = this.uncertainty;
    this.uncertainty = uncertainty;
    if (getExtendedSBase() != null) {
      getExtendedSBase().registerChild(uncertainty);
    }
    firePropertyChange(DistribConstants.uncertainty, oldUncertainty, this.uncertainty);
  }


  /**
   * Unsets the variable uncertainty.
   *
   * @return {@code true} if uncertainty was set before, otherwise {@code false}.
   */
  public boolean unsetUncertainty() {
    if (isSetUncertainty()) {
      Uncertainty oldUncertainty = uncertainty;
      uncertainty = null;
      firePropertyChange(DistribConstants.uncertainty, oldUncertainty, uncertainty);
      return true;
    }
    return false;
  }


  @Override
  public String getPackageName() {
    return DistribConstants.shortLabel;
  }

  @Override
  public String getPrefix() {
    return DistribConstants.shortLabel;
  }

  @Override
  public String getURI() {
    return getElementNamespace();
  }


  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetUncertainty()) {
      count++;
    }

    return count;
  }


  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int pos = 0;

    if (isSetUncertainty()) {
      if (pos == index) {
        return getUncertainty();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public AbstractSBasePlugin clone() {
    return new DistribSBasePlugin(this);
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    return false;
  }

}
