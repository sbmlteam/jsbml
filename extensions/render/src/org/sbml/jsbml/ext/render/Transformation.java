/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
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
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBase;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class Transformation extends AbstractSBase {
  /**
   * 
   */
  private static final long serialVersionUID = 1845276761656867150L;
  protected Double[] transform = new Double[12];

  /**
   * Creates an Transformation instance 
   */
  public Transformation() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public Transformation(Transformation obj) {
    super(obj);
    transform = obj.transform;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  //@Override
  public Transformation clone() {
    return new Transformation(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", childIndex,
      +((int) Math.min(pos, 0))));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  //@Override
  public String toString() {
    // TODO Auto-generated method stub
    return null;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetTransform()) {
    	attributes.remove(RenderConstants.transform);
        attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.transform,
        	XMLTools.encodeArrayDoubleToString(transform)); 
    }
    return attributes;
  }
  
  private boolean isSetTransform() {
	  return this.transform != null;
}

/* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      // TODO: catch Exception if Enum.valueOf fails, generate logger output
      if (attributeName.equals(RenderConstants.transform)) {
    	  setTransform(XMLTools.decodeStringToArrayDouble(value));
      }
    }
    return isAttributeRead;
  }

  private void setTransform(Double[] transform) {
	Double[] oldTransform = this.transform;
    this.transform = transform;
    firePropertyChange(RenderConstants.transform, oldTransform, this.transform);
  }

}
