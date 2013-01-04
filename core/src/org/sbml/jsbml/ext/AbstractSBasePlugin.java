/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2013 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */ 
package org.sbml.jsbml.ext;

import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.SBase;


/**
 * @author Andreas Dr&auml;ger
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 28.10.2011
 */
public abstract class AbstractSBasePlugin extends AbstractTreeNode implements SBasePlugin {

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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getExtendedSBase()
   */
  //@Override
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
    boolean equal = super.equals(object);
    
    // Check all child nodes recursively:
    if (equal && (object instanceof SBasePlugin)) {
      SBasePlugin stn = (SBasePlugin) object;
      equal &= stn.isSetExtendedSBase() == isSetExtendedSBase();
      if (equal && isSetExtendedSBase()) {
        equal &= stn.getExtendedSBase().equals(getExtendedSBase());
      }
    }
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#isSetExtendedSBase()
   */
  //@Override
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

}
