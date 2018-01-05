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
 * 6. Boston University, Boston, MA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.dyn;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ontology.Term;


/**
 * @author Harold G&oacute;mez
 * @since 1.0
 */
public class DynSBasePlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 3120251908117414039L;

  /**
   * Cell Behavior Ontology Term associated with SBase object
   * */
  private Term cboTerm;

  /**
   * Empty constructor
   * */
  public DynSBasePlugin() {
    super();
  }

  /**
   * Constructor
   * 
   * @param plugin
   */
  public DynSBasePlugin(DynSBasePlugin plugin) {
    super(plugin);

    if (plugin.isSetCBOTerm()) {
      setCBOTerm(plugin.getCBOTerm());
    }
  }

  /**
   * Constructor
   * 
   * @param extendedSBase
   */
  public DynSBasePlugin(SBase extendedSBase) {
    super(extendedSBase);
  }

  /**
   * Initializes custom Class attributes
   * */
  public void initDefaults() {
    setPackageVersion(-1);
  }

  /**
   * Returns the value of CBO
   * 
   * @return the value of CBO
   */
  public Term getCBOTerm() {
    if (isSetCBOTerm()) {
      return cboTerm;
    }
    return null;
  }

  /**
   * Returns whether CBO is set
   * 
   * @return whether CBO is set
   */
  public boolean isSetCBOTerm() {
    return cboTerm != null;
  }

  /**
   * Sets the value of CBO
   * 
   * @param cboTerm
   */
  public void setCBOTerm(Term cboTerm) {
    Term oldCboTerm = this.cboTerm;
    this.cboTerm = cboTerm;
    firePropertyChange(DynConstants.cboTerm, oldCboTerm, this.cboTerm);
  }

  /**
   * Unsets the cboTerm field
   * 
   * @return {@code true}, if cboTerm was set before, otherwise {@code false}
   */
  public boolean unsetCBOTerm() {
    if (isSetCBOTerm()) {
      Term oldCboTerm = cboTerm;
      cboTerm = null;
      firePropertyChange(DynConstants.cboTerm, oldCboTerm, cboTerm);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public SBase getParent() {
    if (isSetExtendedSBase()) {
      return (SBase) getExtendedSBase().getParent();
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBase getParentSBMLObject() {
    return getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return DynConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return DynConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!super.equals(object)) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    DynSBasePlugin other = (DynSBasePlugin) object;
    if (!cboTerm.getId().equals(other.cboTerm)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 4289;
    int hashCode = super.hashCode();
    if (isSetCBOTerm()) {
      hashCode += prime * cboTerm.hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = false;

    if (attributeName.equals(DynConstants.cboTerm)) {
      try {
        if (value.startsWith("http://")) {
          value = value.substring(value.lastIndexOf("#") + 1);
        }
        setCBOTerm(CBO.getTerm(value));
        isAttributeRead = true;
      } catch (Exception e) {
        MessageFormat.format(
          DynConstants.bundle.getString("COULD_NOT_READ_CBO"), value,
          DynConstants.cboTerm);
        e.printStackTrace();
      }

    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public DynSBasePlugin clone() {
    return new DynSBasePlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetCBOTerm()) {
      attributes.put(DynConstants.shortLabel + ":cboTerm", cboTerm.getId());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int arg0) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

}
