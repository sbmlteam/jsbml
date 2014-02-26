/*
 * $Id$
 * $URL$
 *
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
package org.sbml.jsbml.ext;

import java.util.Map;
import java.util.TreeMap;

import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;

/**
 * @author Andreas Dr&auml;ger
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 28.10.2011
 */
public abstract class AbstractSBasePlugin extends AbstractTreeNode implements SBasePlugin {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getLevel()
   */
  @Override
  public int getLevel() {
    return isSetExtendedSBase() ? getExtendedSBase().getLevel() : -1;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getVersion()
   */
  @Override
  public int getVersion() {
    return isSetExtendedSBase() ? getExtendedSBase().getVersion() : -1;
  }

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3741496965840142920L;

  /**
   * 
   */
  protected SBase extendedSBase;

  /**
   * 
   */
  public AbstractSBasePlugin() {
    super();
  }

  /**
   * @param extendedSBase
   */
  public AbstractSBasePlugin(SBase extendedSBase) {
    this();
    this.extendedSBase = extendedSBase;
  }

  /**
   * This method will need to be further tested
   * @param plugin
   */
  public AbstractSBasePlugin(AbstractSBasePlugin plugin) {
    super(plugin);
    extendedSBase = plugin.extendedSBase;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getExtendedSBase()
   */
  @Override
  public SBase getExtendedSBase() {
    return extendedSBase;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public abstract AbstractSBasePlugin clone();

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    // Check all child nodes recursively:
    boolean equal = super.equals(object);
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#isSetExtendedSBase()
   */
  @Override
  public boolean isSetExtendedSBase() {
    return extendedSBase != null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    // A constant and arbitrary, sufficiently large prime number:
    final int prime = 769;
    int hashCode = super.hashCode();
    if (isSetExtendedSBase()) {
      hashCode += prime * getExtendedSBase().hashCode();
    }
    return hashCode;
  }

  protected int packageVersion;

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageVersion()
   */
  @Override
  public int getPackageVersion() {
    return packageVersion;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBase getParentSBMLObject() {
    return (SBase) getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getSBMLDocument()
   */
  @Override
  public SBMLDocument getSBMLDocument() {
    return isSetExtendedSBase() ? getExtendedSBase().getSBMLDocument() : null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    return new TreeMap<String, String>();
  }

}
