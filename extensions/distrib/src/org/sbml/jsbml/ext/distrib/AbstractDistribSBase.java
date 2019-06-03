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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBase;


/**
 * 
 * @author rodrigue
 * @since 1.4
 */
public abstract class AbstractDistribSBase extends AbstractSBase implements NamedSBase {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Creates a new instance of {@link AbstractDistribSBase}
   * 
   */
  public AbstractDistribSBase() {
    super();
  }


  /**
   * Creates a new instance of {@link AbstractDistribSBase}
   * 
   * @param level
   * @param version
   */
  public AbstractDistribSBase(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link AbstractDistribSBase}
   * 
   * @param sb
   */
  public AbstractDistribSBase(AbstractDistribSBase sb) {
    super(sb);
  }

  
  /**
   * Creates a new instance of {@link AbstractDistribSBase}
   * 
   * @param id
   * @param level
   * @param version
   */
  public AbstractDistribSBase(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link AbstractDistribSBase}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public AbstractDistribSBase(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /**
   * Creates a new instance of {@link AbstractDistribSBase}
   * 
   * @param id
   */
  public AbstractDistribSBase(String id) {
    super(id);
  }


  @Override
  public String getPackageName() {
    return DistribConstants.shortLabel;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

      if (isSetId()) {
        attributes.remove("id");
        attributes.put(DistribConstants.shortLabel + ":id", getId());
      }
      if (isSetName()) {
        attributes.remove("name");
        attributes.put(DistribConstants.shortLabel + ":name", getName());
      }

      return attributes;
  }
  
  

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    return count;
  }

  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }

    int count = super.getChildCount(), pos = 0;

    if (index < count) {
      return super.getChildAt(index);
    }
    
    throw new IndexOutOfBoundsException(
        MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",

            index, Math.min(pos, 0)));
  }


}
