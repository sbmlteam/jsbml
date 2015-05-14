/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015  jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import java.text.MessageFormat;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;

/**
 * Introduced to FBC in version 2.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.1
 * @date 06.03.2015
 */
public class FBCReactionPlugin extends AbstractFBCSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8382172559018633373L;

  /**
   * 
   */
  private GeneProteinAssociation geneProteinAssociation;

  /**
   * A reference to a global parameter in the model for the lower bound of
   * the reaction.
   */
  private String lowerFluxBound;
  /**
   * A reference to a global parameter in the model for the upper bound of
   * the reaction.
   */
  private String upperFluxBound;

  /**
   * 
   */
  public FBCReactionPlugin() {
    super();
  }


  /**
   * @param reactionPlugin
   */
  public FBCReactionPlugin(FBCReactionPlugin reactionPlugin) {
    super(reactionPlugin);

    if (reactionPlugin.isSetGeneProteinAssociation()) {
      setGeneProteinAssociation(reactionPlugin.getGeneProteinAssociation().clone());
    }
    if (reactionPlugin.isSetLowerFluxBound()) {
      setLowerFluxBound(reactionPlugin.getLowerFluxBound());
    }
    if (reactionPlugin.isSetUpperFluxBound()) {
      setUpperFluxBound(reactionPlugin.getUpperFluxBound());
    }
  }

  /**
   * @param extendedSBase
   */
  public FBCReactionPlugin(Reaction extendedSBase) {
    super(extendedSBase);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public FBCReactionPlugin clone() {
    return new FBCReactionPlugin(this);
  }

  /**
   * 
   * @return
   */
  public GeneProteinAssociation createGeneProteinAssociation() {
    return createGeneProteinAssociation(null);
  }

  /**
   * 
   * @param id
   * @return
   */
  public GeneProteinAssociation createGeneProteinAssociation(String id) {
    GeneProteinAssociation gpa = new GeneProteinAssociation(id, getLevel(), getVersion());
    setGeneProteinAssociation(gpa);
    return gpa;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    FBCReactionPlugin other = (FBCReactionPlugin) obj;
    if (geneProteinAssociation == null) {
      if (other.geneProteinAssociation != null) {
        return false;
      }
    } else if (!geneProteinAssociation.equals(other.geneProteinAssociation)) {
      return false;
    }
    if (lowerFluxBound == null) {
      if (other.lowerFluxBound != null) {
        return false;
      }
    } else if (!lowerFluxBound.equals(other.lowerFluxBound)) {
      return false;
    }
    if (upperFluxBound == null) {
      if (other.upperFluxBound != null) {
        return false;
      }
    } else if (!upperFluxBound.equals(other.upperFluxBound)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int pos = 0;

    if (isSetGeneProteinAssociation()) {
      if (pos == index) {
        return getGeneProteinAssociation();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetGeneProteinAssociation() ? 1 : 0;
  }

  /**
   * Returns the value of {@link #geneProteinAssociation}.
   *
   * @return the value of {@link #geneProteinAssociation}.
   */
  public GeneProteinAssociation getGeneProteinAssociation() {
    return geneProteinAssociation;
  }

  /**
   * Returns the value of {@link #lowerFluxBound}.
   *
   * @return the value of {@link #lowerFluxBound}.
   */
  public String getLowerFluxBound() {
    if (isSetLowerFluxBound()) {
      return lowerFluxBound;
    }
    return "";
  }

  /**
   * 
   * @return
   */
  public Parameter getLowerFluxBoundInstance() {
    if (getModel() == null) {
      return null;
    }
    return getModel().getParameter(getLowerFluxBound());
  }

  /**
   * 
   * @return
   */
  private Model getModel() {
    if (isSetExtendedSBase()) {
      return extendedSBase.getModel();
    }
    return null;
  }

  /**
   * Returns the value of {@link #upperFluxBound}.
   *
   * @return the value of {@link #upperFluxBound}.
   */
  public String getUpperFluxBound() {
    if (isSetUpperFluxBound()) {
      return upperFluxBound;
    }
    return "";
  }

  /**
   * 
   * @return
   */
  public Parameter getUpperFluxBoundInstance() {
    if (getModel() == null) {
      return null;
    }
    return getModel().getParameter(getUpperFluxBound());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((geneProteinAssociation == null) ? 0 : geneProteinAssociation.hashCode());
    result = prime * result + ((lowerFluxBound == null) ? 0 : lowerFluxBound.hashCode());
    result = prime * result + ((upperFluxBound == null) ? 0 : upperFluxBound.hashCode());
    return result;
  }

  /**
   * Returns whether {@link #geneProteinAssociation} is set.
   *
   * @return whether {@link #geneProteinAssociation} is set.
   */
  public boolean isSetGeneProteinAssociation() {
    return geneProteinAssociation != null;
  }

  /**
   * Returns whether {@link #lowerFluxBound} is set.
   *
   * @return whether {@link #lowerFluxBound} is set.
   */
  public boolean isSetLowerFluxBound() {
    return lowerFluxBound != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetLowerFluxBoundInstance() {
    return getLowerFluxBoundInstance() != null;
  }

  /**
   * Returns whether {@link #upperFluxBound} is set.
   *
   * @return whether {@link #upperFluxBound} is set.
   */
  public boolean isSetUpperFluxBound() {
    return upperFluxBound != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetUpperFluxBoundInstance() {
    return getUpperFluxBoundInstance() != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {

    if (attributeName.equals(FBCConstants.upperFluxBound)) {
      setUpperFluxBound(value);
      return true;
    } else if (attributeName.equals(FBCConstants.lowerFluxBound)) {
      setLowerFluxBound(value);
      return true;
    }

    return false;
  }

  /**
   * Sets the value of geneProteinAssociation
   * @param geneProteinAssociation the value of geneProteinAssociation to be set.
   */
  public void setGeneProteinAssociation(GeneProteinAssociation geneProteinAssociation) {
    GeneProteinAssociation oldGeneProteinAssociation = this.geneProteinAssociation;
    this.geneProteinAssociation = geneProteinAssociation;
    if (isSetExtendedSBase()) {
      getExtendedSBase().registerChild(geneProteinAssociation);
    }
    firePropertyChange(FBCConstants.geneProteinAssociation, oldGeneProteinAssociation, this.geneProteinAssociation);
  }

  /**
   * 
   * @param boundParameter
   */
  public void setLowerFluxBound(Parameter boundParameter) {
    if (!boundParameter.isSetId()) {
      throw new IllegalArgumentException("Flux bound parameter must have an id.");
    }
    setLowerFluxBound(boundParameter.getId());
  }

  /**
   * Sets the value of lowerFluxBound
   * @param lowerFluxBound
   */
  public void setLowerFluxBound(String lowerFluxBound) {
    String oldLowerFluxBound = this.lowerFluxBound;
    this.lowerFluxBound = lowerFluxBound;
    firePropertyChange(FBCConstants.lowerFluxBound, oldLowerFluxBound, this.lowerFluxBound);
  }

  /**
   * 
   * @param boundParameter
   */
  public void setUpperFluxBound(Parameter boundParameter) {
    if (!boundParameter.isSetId()) {
      throw new IllegalArgumentException("Flux bound parameter must have an id.");
    }
    setUpperFluxBound(boundParameter.getId());
  }

  /**
   * Sets the value of upperFluxBound
   * @param upperFluxBound
   */
  public void setUpperFluxBound(String upperFluxBound) {
    String oldUpperFluxBound = this.upperFluxBound;
    this.upperFluxBound = upperFluxBound;
    firePropertyChange(FBCConstants.upperFluxBound, oldUpperFluxBound, this.upperFluxBound);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName());
    builder.append(" [geneProteinAssociation=");
    builder.append(geneProteinAssociation);
    builder.append(", lowerFluxBound=");
    builder.append(lowerFluxBound);
    builder.append(", upperFluxBound=");
    builder.append(upperFluxBound);
    builder.append("]");
    return builder.toString();
  }

  /**
   * Unsets the variable geneProteinAssociation.
   *
   * @return {@code true} if geneProteinAssociation was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetGeneProteinAssociation() {
    if (isSetGeneProteinAssociation()) {
      GeneProteinAssociation oldGeneProteinAssociation = geneProteinAssociation;
      geneProteinAssociation = null;
      firePropertyChange(FBCConstants.geneProteinAssociation, oldGeneProteinAssociation, geneProteinAssociation);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable lowerFluxBound.
   *
   * @return {@code true} if lowerFluxBound was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetLowerFluxBound() {
    if (isSetLowerFluxBound()) {
      String oldLowerFluxBound = lowerFluxBound;
      lowerFluxBound = null;
      firePropertyChange(FBCConstants.lowerFluxBound, oldLowerFluxBound, lowerFluxBound);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable upperFluxBound.
   *
   * @return {@code true} if upperFluxBound was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetUpperFluxBound() {
    if (isSetUpperFluxBound()) {
      String oldUpperFluxBound = upperFluxBound;
      upperFluxBound = null;
      firePropertyChange(FBCConstants.upperFluxBound, oldUpperFluxBound, upperFluxBound);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = new TreeMap<String, String>();

    if (isSetLowerFluxBound()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.lowerFluxBound, getLowerFluxBound());
    }
    if (isSetUpperFluxBound()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.upperFluxBound, getUpperFluxBound());
    }

    return attributes;
  }

}
