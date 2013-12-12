/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev: 1473 $
 */
public class SpatialParameterPlugin extends AbstractSBasePlugin {


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return SpatialConstants.getNamespaceURI(getLevel(), getVersion());
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return SpatialConstants.packageName;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return SpatialConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Parameter> getParent() {
    return (ListOf<Parameter>) getExtendedSBase().getParent();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public ListOf<Parameter> getParentSBMLObject() {
    return getParent();
  }
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3766260342134204275L;

  /**
   * 
   */
  private SpatialParameterQualifier qualifier;

  /**
   * 
   */
  public SpatialParameterPlugin() {
    super();
  }



  /**
   * @param sb
   */
  public SpatialParameterPlugin(SpatialParameterPlugin sb) {
    super(sb);
    if (sb.isSetQualifier()) {
      qualifier = (SpatialParameterQualifier) sb.getQualifier().clone();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public SpatialParameterPlugin clone() {
    return new SpatialParameterPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      SpatialParameterPlugin sp = (SpatialParameterPlugin) object;
      equals &= sp.isSetQualifier() == isSetQualifier();
      if (equals && isSetQualifier()) {
        equals &= sp.getQualifier().equals(getQualifier());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    if (isSetQualifier())  {
      if (childIndex == pos) {
        return getQualifier();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(isLeaf() ? MessageFormat.format(
      "Node {0} has no children.", getExtendedSBase().getElementName()) : MessageFormat.format(
        "Index {0,number,integer} >= {1,number,integer}", childIndex, +Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return (isSetQualifier() ? 1 : 0);
  }

  /**
   * @return the qualifier
   */
  public SpatialParameterQualifier getQualifier() {
    return qualifier;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 991;
    int hashCode = super.hashCode();
    if (isSetQualifier()) {
      hashCode += prime * getQualifier().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   * @return
   */
  public boolean isSetQualifier() {
    return qualifier != null;
  }

  /**
   * @param qualifier the qualifier to set
   */
  public void setQualifier(SpatialParameterQualifier qualifier) {
    this.qualifier = qualifier;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    // TODO Auto-generated method stub
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    // TODO Auto-generated method stub
    return null;
  }


  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    // TODO Auto-generated method stub
    return false;
  }

}
