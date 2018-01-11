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
 * Extends the SBML Level 3 Version 1 Core {@link Reaction} class with the addition of a
 * new optional element {@link GeneProductAssociation} as well as two optional attributes lowerFluxBound and
 * upperFluxBound.
 *
 * <p>Introduced to FBC in version 2.</p>
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.1
 */
public class FBCReactionPlugin extends AbstractFBCSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8382172559018633373L;

  /**
   * 
   */
  private GeneProductAssociation geneProductAssociation;

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
   * Creates a new {@link FBCReactionPlugin} instance.
   */
  public FBCReactionPlugin() {
    super();
  }


  /**
   * Creates a new {@link FBCReactionPlugin} instance.
   * 
   * @param reactionPlugin the instance to be clone
   */
  public FBCReactionPlugin(FBCReactionPlugin reactionPlugin) {
    super(reactionPlugin);

    if (reactionPlugin.isSetGeneProductAssociation()) {
      setGeneProductAssociation(reactionPlugin.getGeneProductAssociation().clone());
    }
    if (reactionPlugin.isSetLowerFluxBound()) {
      setLowerFluxBound(reactionPlugin.getLowerFluxBound());
    }
    if (reactionPlugin.isSetUpperFluxBound()) {
      setUpperFluxBound(reactionPlugin.getUpperFluxBound());
    }
  }

  /**
   * Creates a new {@link FBCReactionPlugin} instance.
   * 
   * @param extendedSBase the core {@link Reaction} that is extended
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
  public GeneProductAssociation createGeneProductAssociation() {
    return createGeneProductAssociation(null);
  }

  /**
   * 
   * @param id
   * @return
   */
  public GeneProductAssociation createGeneProductAssociation(String id) {
    GeneProductAssociation gpa = new GeneProductAssociation(id, getLevel(), getVersion());
    setGeneProductAssociation(gpa);
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
    if (geneProductAssociation == null) {
      if (other.geneProductAssociation != null) {
        return false;
      }
    } else if (!geneProductAssociation.equals(other.geneProductAssociation)) {
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

    if (isSetGeneProductAssociation()) {
      if (pos == index) {
        return getGeneProductAssociation();
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
    return isSetGeneProductAssociation() ? 1 : 0;
  }

  /**
   * Returns the value of {@link #geneProductAssociation}.
   *
   * @return the value of {@link #geneProductAssociation}.
   */
  public GeneProductAssociation getGeneProductAssociation() {
    return geneProductAssociation;
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
    result = prime * result + ((geneProductAssociation == null) ? 0 : geneProductAssociation.hashCode());
    result = prime * result + ((lowerFluxBound == null) ? 0 : lowerFluxBound.hashCode());
    result = prime * result + ((upperFluxBound == null) ? 0 : upperFluxBound.hashCode());
    return result;
  }

  /**
   * Returns whether {@link #geneProductAssociation} is set.
   *
   * @return whether {@link #geneProductAssociation} is set.
   */
  public boolean isSetGeneProductAssociation() {
    return geneProductAssociation != null;
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
   * Sets the value of {@link #geneProductAssociation}
   * 
   * @param geneProductAssociation
   *        the value of {@link #geneProductAssociation} to be set.
   */
  public void setGeneProductAssociation(GeneProductAssociation geneProductAssociation) {
    GeneProductAssociation oldGeneProductAssociation = geneProductAssociation;
    this.geneProductAssociation = geneProductAssociation;
    if (isSetExtendedSBase()) {
      getExtendedSBase().registerChild(geneProductAssociation);
    }
    firePropertyChange(FBCConstants.geneProductAssociation, oldGeneProductAssociation, geneProductAssociation);
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

  /**
   * Unsets the variable {@link #geneProductAssociation}.
   *
   * @return {@code true} if {@link #geneProductAssociation} was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetGeneProductAssociation() {
    if (isSetGeneProductAssociation()) {
      GeneProductAssociation oldGeneProductAssociation = geneProductAssociation;
      geneProductAssociation = null;
      firePropertyChange(FBCConstants.geneProductAssociation, oldGeneProductAssociation, geneProductAssociation);
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
