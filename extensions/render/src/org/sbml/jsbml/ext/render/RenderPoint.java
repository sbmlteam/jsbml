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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
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
public class RenderPoint extends AbstractSBase implements Point3D {
  public static final int MIN_SBML_LEVEL = 3;
  public static final int MIN_SBML_VERSION = 1;
	/**
   * 
   */
  private static final long serialVersionUID = 6792387139122188270L;
	
	private Boolean absoluteX, absoluteY, absoluteZ;

  private Double x, y, z;

  /**
   * Creates an RenderPoint instance 
   */
  public RenderPoint() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public RenderPoint(RenderPoint obj) {
    super(obj);
    // TODO: copy all class attributes, e.g.:
    // bar = obj.bar;
  }


  public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}


  @Override
  public boolean getAllowsChildren() {
    return false;
  }


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
  
  @Override
  public int getChildCount() {
    return 0;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#getX()
   */
  public double getX() {
    if (isSetX()) {
      return x;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.x, this);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#getY()
   */
  public double getY() {
    if (isSetY()) {
      return y;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.y, this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#getZ()
   */
  public double getZ() {
    if (isSetZ()) {
      return z;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.z, this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    z = 0d;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isAbsoluteX()
   */
  public boolean isAbsoluteX() {
    if (isSetAbsoluteX()) {
      return absoluteX;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteX, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isAbsoluteY()
   */
  public boolean isAbsoluteY() {
    if (isSetAbsoluteY()) {
      return absoluteY;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteY, this);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isAbsoluteZ()
   */
  public boolean isAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      return absoluteZ;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteZ, this);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetAbsoluteX()
   */
  public boolean isSetAbsoluteX() {
    return this.absoluteX != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetAbsoluteY()
   */
  public boolean isSetAbsoluteY() {
    return this.absoluteY != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetAbsoluteZ()
   */
  public boolean isSetAbsoluteZ() {
    return this.absoluteZ != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetX()
   */
  public boolean isSetX() {
    return this.x != null;
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetY()
   */
  public boolean isSetY() {
    return this.y != null;
  }
	
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetZ()
   */
  public boolean isSetZ() {
    return this.z != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setAbsoluteX(java.lang.Boolean)
   */
  public void setAbsoluteX(Boolean absoluteX) {
    Boolean oldAbsoluteX = this.absoluteX;
    this.absoluteX = absoluteX;
    firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, this.absoluteX);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setAbsoluteY(java.lang.Boolean)
   */
  public void setAbsoluteY(Boolean absoluteY) {
    Boolean oldAbsoluteY = this.absoluteY;
    this.absoluteY = absoluteY;
    firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, this.absoluteY);
  }
	
	/* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setAbsoluteZ(java.lang.Boolean)
   */
  public void setAbsoluteZ(Boolean absoluteZ) {
    Boolean oldAbsoluteZ = this.absoluteZ;
    this.absoluteZ = absoluteZ;
    firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, this.absoluteZ);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setX(java.lang.Double)
   */
  public void setX(Double x) {
    Double oldX = this.x;
    this.x = x;
    firePropertyChange(RenderConstants.x, oldX, this.x);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setY(java.lang.Double)
   */
  public void setY(Double y) {
    Double oldY = this.y;
    this.y = y;
    firePropertyChange(RenderConstants.y, oldY, this.y);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setZ(java.lang.Double)
   */
  public void setZ(Double z) {
    Double oldZ = this.z;
    this.z = z;
    firePropertyChange(RenderConstants.z, oldZ, this.z);
  }


  public String toString() {
		// TODO Auto-generated method stub
		return null;
	}


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetAbsoluteX()
   */
  public boolean unsetAbsoluteX() {
    if (isSetAbsoluteX()) {
      Boolean oldAbsoluteX = this.absoluteX;
      this.absoluteX = null;
      firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, this.absoluteX);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetAbsoluteY()
   */
  public boolean unsetAbsoluteY() {
    if (isSetAbsoluteY()) {
      Boolean oldAbsoluteY = this.absoluteY;
      this.absoluteY = null;
      firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, this.absoluteY);
      return true;
    }
    return false;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetAbsoluteZ()
   */
  public boolean unsetAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      Boolean oldAbsoluteZ = this.absoluteZ;
      this.absoluteZ = null;
      firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, this.absoluteZ);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetX()
   */
  public boolean unsetX() {
    if (isSetX()) {
      Double oldX = this.x;
      this.x = null;
      firePropertyChange(RenderConstants.x, oldX, this.x);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetY()
   */
  public boolean unsetY() {
    if (isSetY()) {
      Double oldY = this.y;
      this.y = null;
      firePropertyChange(RenderConstants.y, oldY, this.y);
      return true;
    }
    return false;
  }
	
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetZ()
   */
  public boolean unsetZ() {
    if (isSetZ()) {
      Double oldZ = this.z;
      this.z = null;
      firePropertyChange(RenderConstants.z, oldZ, this.z);
      return true;
    }
    return false;
  }
}
