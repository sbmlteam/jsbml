/*
 * $Id: DynSBasePlugin.java 2180 2015-04-08 15:48:28Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/dyn/src/org/sbml/jsbml/ext/dyn/DynSBasePlugin.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
 * @version $Rev: 2180 $
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
    elementNamespace = DynConstants.namespaceURI;  // TODO - removed once the mechanism are in place to set package version and namespace
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
    elementNamespace = DynConstants.namespaceURI;  // TODO - removed once the mechanism are in place to set package version and namespace
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

  @Override
  public SBase getParent() {
    if (isSetExtendedSBase()) {
      return (SBase) getExtendedSBase().getParent();
    }

    return null;
  }

  @Override
  public SBase getParentSBMLObject() {
    return getParent();
  }

  @Override
  public String getPackageName() {
    return DynConstants.shortLabel;
  }

  @Override
  public String getPrefix() {
    return DynConstants.shortLabel;
  }

  @Override
  public String getURI() {
    return getElementNamespace();
  }

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

  @Override
  public int hashCode() {
    final int prime = 4289;
    int hashCode = super.hashCode();
    if (isSetCBOTerm()) {
      hashCode += prime * cboTerm.hashCode();
    }
    return hashCode;
  }

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

  @Override
  public DynSBasePlugin clone() {
    return new DynSBasePlugin(this);
  }

  @Override
  public String toString() {
    return "DynSBasePlugin [cboTerm=" + cboTerm + "]";
  }

  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetCBOTerm()) {
      attributes.put(DynConstants.shortLabel + ":cboTerm", cboTerm.getId());
    }

    return attributes;
  }

  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  @Override
  public TreeNode getChildAt(int arg0) {
    return null;
  }

  @Override
  public int getChildCount() {
    return 0;
  }

}
