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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * Contains a single {@link Association}. Where more than
 * one gene (or gene product) is present in an association they are written as logical expressions and thereby related
 * to one another using logical ‘and’ and ‘or’ operators.
 *
 * <p>Introduced to FBC in version 2.</p>
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.1
 */
public class GeneProductAssociation extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3080178338645314056L;

  /**
   * 
   */
  private Association association;

  /**
   * Creates a new {@link GeneProductAssociation} instance.
   */
  public GeneProductAssociation() {
    super();
    initDefaults();
  }

  /**
   * Initializes the default values.
   */
  private void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /**
   * Creates a new {@link GeneProductAssociation} instance.
   * 
   * @param gpa the instance to clone.
   */
  public GeneProductAssociation(GeneProductAssociation gpa) {
    super(gpa);
    if (gpa.isSetAssociation()) {
      setAssociation((Association) gpa.getAssociation().clone());
    }
  }

  /**
   * Creates a new {@link GeneProductAssociation} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProductAssociation(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProductAssociation} instance.
   * 
   * @param id the id
   */
  public GeneProductAssociation(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProductAssociation} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProductAssociation(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProductAssociation} instance.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProductAssociation(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public GeneProductAssociation clone() {
    return new GeneProductAssociation(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /**
   * Returns the value of {@link #association}.
   *
   * @return the value of {@link #association}.
   */
  public Association getAssociation() {
    return association;
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
    if (isSetAssociation()) {
      if (pos == index) {
        return getAssociation();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetAssociation()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * Returns whether {@link #association} is set.
   *
   * @return whether {@link #association} is set.
   */
  public boolean isSetAssociation() {
    return association != null;
  }

  /**
   * Sets the value of association
   * @param association the value of association to be set.
   */
  public void setAssociation(Association association) {
    Association oldAssociation = this.association;
    this.association = association;
    registerChild(association);
    firePropertyChange(FBCConstants.association, oldAssociation, this.association);
  }

  /**
   * Unsets the variable association.
   *
   * @return {@code true} if association was set before,
   *         otherwise {@code false}.
   */
  public boolean unsetAssociation() {
    if (isSetAssociation()) {
      Association oldAssociation = association;
      association = null;
      firePropertyChange(FBCConstants.association, oldAssociation, association);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getId());
    }
    return attributes;
  }

}
