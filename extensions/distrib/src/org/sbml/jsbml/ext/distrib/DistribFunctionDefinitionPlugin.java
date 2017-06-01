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

import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class DistribFunctionDefinitionPlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8759855933212983388L;
  /**
   * 
   */
  private DrawFromDistribution drawFromDistribution;

  /**
   * Creates a new {@link DistribFunctionDefinitionPlugin} instance.
   * 
   * @param fd the {@link FunctionDefinition} to be extended.
   */
  public DistribFunctionDefinitionPlugin(FunctionDefinition fd) {
    super(fd);
    setPackageVersion(-1);
  }

  /**
   * Creates a new {@link DistribFunctionDefinitionPlugin} instance, cloned from
   * the input parameter.
   * 
   * @param obj the {@link DistribFunctionDefinitionPlugin} to clone
   */
  public DistribFunctionDefinitionPlugin(DistribFunctionDefinitionPlugin obj) {
    super(obj);

    if (obj.isSetDrawFromDistribution()) {
      setDrawFromDistribution(obj.getDrawFromDistribution().clone());
    }
  }

  /**
   * Returns the value of drawFromDistribution
   *
   * @return the value of drawFromDistribution
   */
  public DrawFromDistribution getDrawFromDistribution() {
    if (isSetDrawFromDistribution()) {
      return drawFromDistribution;
    }

    return null;
  }

  /**
   * Returns whether drawFromDistribution is set
   *
   * @return whether drawFromDistribution is set
   */
  public boolean isSetDrawFromDistribution() {
    return drawFromDistribution != null;
  }

  /**
   * Sets the value of drawFromDistribution
   * @param drawFromDistribution
   */
  public void setDrawFromDistribution(DrawFromDistribution drawFromDistribution) {
    DrawFromDistribution oldDrawFromDistribution = this.drawFromDistribution;
    this.drawFromDistribution = drawFromDistribution;
    if (getExtendedSBase() != null) {
      getExtendedSBase().registerChild(drawFromDistribution);
    }
    firePropertyChange(DistribConstants.drawFromDistribution, oldDrawFromDistribution, this.drawFromDistribution); // TODO - check that the old drawFromDistribution is properly un-registered
  }

  /**
   * Creates and Sets a new drawFromDistribution instance.
   * @return
   */
  public DrawFromDistribution createDrawFromDistribution() {
    DrawFromDistribution drawFromDistribution = new DrawFromDistribution();
    setDrawFromDistribution(drawFromDistribution);
    return drawFromDistribution;
  }


  /**
   * Unsets the variable drawFromDistribution
   *
   * @return {@code true}, if drawFromDistribution was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDrawFromDistribution() {
    if (isSetDrawFromDistribution()) {
      DrawFromDistribution oldDrawFromDistribution = drawFromDistribution;
      drawFromDistribution = null;
      firePropertyChange(DistribConstants.drawFromDistribution, oldDrawFromDistribution, drawFromDistribution);
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
  public int getChildCount() {
    int count = 0;

    if (isSetDrawFromDistribution()) {
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

    if (isSetDrawFromDistribution()) {
      if (pos == index) {
        return getDrawFromDistribution();
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
    return new DistribFunctionDefinitionPlugin(this);
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    return false;
  }

  @Override
  public String getURI() {
    // TODO Auto-generated method stub
    return null;
  }


}
