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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.xml.XMLNode;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class Uncertainty extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -904719821379100471L;
  /**
   * 
   */
  private XMLNode uncertML;

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
   * Returns the value of {@link #uncertML}.
   *
   * @return the value of {@link #uncertML}.
   */
  public XMLNode getUncertML() {
    if (isSetUncertML()) {
      return uncertML;
    }

    return null;
  }


  /**
   * Returns whether {@link #uncertML} is set.
   *
   * @return whether {@link #uncertML} is set.
   */
  public boolean isSetUncertML() {
    return uncertML != null;
  }


  /**
   * Sets the value of uncertML
   *
   * @param uncertML the value of uncertML to be set.
   */
  public void setUncertML(XMLNode uncertML) {
    XMLNode oldUncertML = this.uncertML;
    this.uncertML = uncertML;
    this.uncertML.setParent(this);
    firePropertyChange(DistribConstants.uncertML, oldUncertML, this.uncertML);
  }


  /**
   * Unsets the variable uncertML.
   *
   * @return {@code true} if uncertML was set before, otherwise {@code false}.
   */
  public boolean unsetUncertML() {
    if (isSetUncertML()) {
      XMLNode oldUncertML = uncertML;
      uncertML = null;
      firePropertyChange(DistribConstants.uncertML, oldUncertML, uncertML);
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

    if (isSetUncertML()) {
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

    if (isSetUncertML()) {
      if (pos == index) {
        return getUncertML();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(DistribConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(DistribConstants.shortLabel + ":name", getId());
    }

    return attributes;
  }

}
