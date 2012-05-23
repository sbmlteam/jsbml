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

import org.sbml.jsbml.PropertyUndefinedError;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class GraphicalPrimitive2D extends GraphicalPrimitive1D {
	/**
   * 
   */
  private static final long serialVersionUID = -5666416071124784541L;
  protected String fill; 
	protected enum FillRule {
		nonzero,
		evenodd,
	}
	protected FillRule fillRule;
	
	/**
   * Creates an GraphicalPrimitive2D instance 
   */
  public GraphicalPrimitive2D() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public GraphicalPrimitive2D(GraphicalPrimitive2D obj) {
    super(obj);
    fill = obj.fill;
    fillRule = obj.fillRule;
  }

  
  /**
   * clones this class
   */
  @Override
  public GraphicalPrimitive2D clone() {
    return new GraphicalPrimitive2D(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    // TODO: init default values here if necessary, e.g.:
    // bar = null;
  }

  
  /**
   * @return the value of fill
   */
  public String getFill() {
    if (isSetFill()) {
      return fill;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fill, this);
  }


  /**
   * @return whether fill is set 
   */
  public boolean isSetFill() {
    return this.fill != null;
  }


  /**
   * Set the value of fill
   */
  public void setFill(String fill) {
    String oldFill = this.fill;
    this.fill = fill;
    firePropertyChange(RenderConstants.fill, oldFill, this.fill);
  }


  /**
   * Unsets the variable fill 
   * @return <code>true</code>, if fill was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetFill() {
    if (isSetFill()) {
      String oldFill = this.fill;
      this.fill = null;
      firePropertyChange(RenderConstants.fill, oldFill, this.fill);
      return true;
    }
    return false;
  }
  
  
  
  /**
   * @return the value of fillRule
   */
  public FillRule getFillRule() {
    if (isSetFillRule()) {
      return fillRule;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fillRule, this);
  }


  /**
   * @return whether fillRule is set 
   */
  public boolean isSetFillRule() {
    return this.fillRule != null;
  }


  /**
   * Set the value of fillRule
   */
  public void setFillRule(FillRule fillRule) {
    FillRule oldFillRule = this.fillRule;
    this.fillRule = fillRule;
    firePropertyChange(RenderConstants.fillRule, oldFillRule, this.fillRule);
  }


  /**
   * Unsets the variable fillRule 
   * @return <code>true</code>, if fillRule was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetFillRule() {
    if (isSetFillRule()) {
      FillRule oldFillRule = this.fillRule;
      this.fillRule = null;
      firePropertyChange(RenderConstants.fillRule, oldFillRule, this.fillRule);
      return true;
    }
    return false;
  }
  

  
  
  public static final int MIN_SBML_LEVEL = 3;
  public static final int MIN_SBML_VERSION = 1;
}
