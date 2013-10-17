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
package org.sbml.jsbml.ext.render;

import java.util.Map;

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
public class RadialGradient extends GradientBase {
	/**
   * 
   */
  private static final long serialVersionUID = -6976786676644704255L;
	private Boolean absoluteCx, absoluteCy, absoluteCz, absoluteR;
	
	private Boolean absoluteFx, absoluteFy, absoluteFz;

  private Double cx, cy, cz, r, fx, fy, fz;


  /**
   * Creates an RadialGradient instance 
   */
  public RadialGradient() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public RadialGradient(RadialGradient obj) {
    super(obj);
    cx = obj.cx;
    cy = obj.cy;
    cz = obj.cz;
    fx = obj.fx;
    fy = obj.fy;
    fz = obj.fz;
    r = obj.r;
    absoluteCx = obj.absoluteCx;
    absoluteCy = obj.absoluteCy;
    absoluteCz = obj.absoluteCz;
    absoluteR = obj.absoluteR;
    absoluteFx = obj.absoluteFx;
    absoluteFy = obj.absoluteFy;
    absoluteFz = obj.absoluteFz;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GradientBase#clone()
   */
  @Override
  public RadialGradient clone() {
    return new RadialGradient(this);
  }

  /**
   * @return the value of cx
   */
  public double getCx() {
    if (isSetCx()) {
      return cx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.cx, this);
  }

  /**
   * @return the value of cy
   */
  public double getCy() {
    if (isSetCy()) {
      return cy;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.cy, this);
  }

  /**
   * @return the value of cz
   */
  public double getCz() {
    if (isSetCz()) {
      return cz;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.cz, this);
  }
  
  /**
   * @return the value of fx
   */
  public double getFx() {
    if (isSetFx()) {
      return fx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fx, this);
  }

  /**
   * @return the value of fy
   */
  public double getFy() {
    if (isSetFy()) {
      return fy;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fy, this);
  }

  /**
   * @return the value of fz
   */
  public double getFz() {
    if (isSetFz()) {
      return fz;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fz, this);
  }

  /**
   * @return the value of r
   */
  public double getR() {
    if (isSetR()) {
      return r;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.r, this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GradientBase#initDefaults()
   */
  @Override
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    cx = 0.5d;
    cy = 0.5d;
    cz = 0.5d;
    r = 0.5d;
    fx = cx;
    fy = cy;
    fz = cz;
    absoluteCx = false;
    absoluteCy = false;
    absoluteCz = false;
    absoluteR = false;
    absoluteFx = false;
    absoluteFy = false;
    absoluteFz = false;
  }

  /**
   * @return the value of absoluteCx
   */
  public boolean isAbsoluteCx() {
    if (isSetAbsoluteCx()) {
      return absoluteCx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteCx, this);
  }

  /**
   * @return the value of absoluteCy
   */
  public boolean isAbsoluteCy() {
    if (isSetAbsoluteCy()) {
      return absoluteCy;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteCy, this);
  }

  /**
   * @return the value of absoluteCz
   */
  public boolean isAbsoluteCz() {
    if (isSetAbsoluteCz()) {
      return absoluteCz;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteCz, this);
  }
  
  /**
   * @return the value of absoluteFx
   */
  public boolean isAbsoluteFx() {
    if (isSetAbsoluteFx()) {
      return absoluteFx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteFx, this);
  }

  /**
   * @return the value of absoluteFy
   */
  public boolean isAbsoluteFy() {
    if (isSetAbsoluteFy()) {
      return absoluteFy;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteFy, this);
  }

  /**
   * @return the value of absoluteFz
   */
  public boolean isAbsoluteFz() {
    if (isSetAbsoluteFz()) {
      return absoluteFz;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteFz, this);
  }

  /**
   * @return the value of absoluteR
   */
  public boolean isAbsoluteR() {
    if (isSetAbsoluteR()) {
      return absoluteR;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteR, this);
  }
  
  /**
   * @return whether absoluteCx is set 
   */
  public boolean isSetAbsoluteCx() {
    return this.absoluteCx != null;
  }

  /**
   * @return whether absoluteCy is set 
   */
  public boolean isSetAbsoluteCy() {
    return this.absoluteCy != null;
  }

  /**
   * @return whether absoluteCz is set 
   */
  public boolean isSetAbsoluteCz() {
    return this.absoluteCz != null;
  }

  /**
   * @return whether absoluteFx is set 
   */
  public boolean isSetAbsoluteFx() {
    return this.absoluteFx != null;
  }
  
  /**
   * @return whether absoluteFy is set 
   */
  public boolean isSetAbsoluteFy() {
    return this.absoluteFy != null;
  }

  /**
   * @return whether absoluteFz is set 
   */
  public boolean isSetAbsoluteFz() {
    return this.absoluteFz != null;
  }

  /**
   * @return whether absoluteR is set 
   */
  public boolean isSetAbsoluteR() {
    return this.absoluteR != null;
  }

  /**
   * @return whether cx is set 
   */
  public boolean isSetCx() {
    return this.cx != null;
  }

  /**
   * @return whether cy is set 
   */
  public boolean isSetCy() {
    return this.cy != null;
  }

  /**
   * @return whether cz is set 
   */
  public boolean isSetCz() {
    return this.cz != null;
  }

  /**
   * @return whether fx is set 
   */
  public boolean isSetFx() {
    return this.fx != null;
  }

  /**
   * @return whether fy is set 
   */
  public boolean isSetFy() {
    return this.fy != null;
  }
  
  /**
   * @return whether fz is set 
   */
  public boolean isSetFz() {
    return this.fz != null;
  }

  /**
   * @return whether r is set 
   */
  public boolean isSetR() {
    return this.r != null;
  }

  /**
   * Set the value of absoluteCx
   */
  public void setAbsoluteCx(Boolean absoluteCx) {
    Boolean oldAbsoluteCx = this.absoluteCx;
    this.absoluteCx = absoluteCx;
    firePropertyChange(RenderConstants.absoluteCx, oldAbsoluteCx, this.absoluteCx);
  }

  /**
   * Set the value of absoluteCy
   */
  public void setAbsoluteCy(Boolean absoluteCy) {
    Boolean oldAbsoluteCy = this.absoluteCy;
    this.absoluteCy = absoluteCy;
    firePropertyChange(RenderConstants.absoluteCy, oldAbsoluteCy, this.absoluteCy);
  }

  /**
   * Set the value of absoluteCz
   */
  public void setAbsoluteCz(Boolean absoluteCz) {
    Boolean oldAbsoluteCz = this.absoluteCz;
    this.absoluteCz = absoluteCz;
    firePropertyChange(RenderConstants.absoluteCz, oldAbsoluteCz, this.absoluteCz);
  }

  /**
   * Set the value of absoluteFx
   */
  public void setAbsoluteFx(Boolean absoluteFx) {
    Boolean oldAbsoluteFx = this.absoluteFx;
    this.absoluteFx = absoluteFx;
    firePropertyChange(RenderConstants.absoluteFx, oldAbsoluteFx, this.absoluteFx);
  }

  /**
   * Set the value of absoluteFy
   */
  public void setAbsoluteFy(Boolean absoluteFy) {
    Boolean oldAbsoluteFy = this.absoluteFy;
    this.absoluteFy = absoluteFy;
    firePropertyChange(RenderConstants.absoluteFy, oldAbsoluteFy, this.absoluteFy);
  }

  /**
   * Set the value of absoluteFz
   */
  public void setAbsoluteFz(Boolean absoluteFz) {
    Boolean oldAbsoluteFz = this.absoluteFz;
    this.absoluteFz = absoluteFz;
    firePropertyChange(RenderConstants.absoluteFz, oldAbsoluteFz, this.absoluteFz);
  }

  /**
   * Set the value of absoluteR
   */
  public void setAbsoluteR(Boolean absoluteR) {
    Boolean oldAbsoluteR = this.absoluteR;
    this.absoluteR = absoluteR;
    firePropertyChange(RenderConstants.absoluteR, oldAbsoluteR, this.absoluteR);
  }

  /**
   * Set the value of cx
   */
  public void setCx(Double cx) {
    Double oldCx = this.cx;
    this.cx = cx;
    firePropertyChange(RenderConstants.cx, oldCx, this.cx);
  }

  /**
   * Set the value of cy
   */
  public void setCy(Double cy) {
    Double oldCy = this.cy;
    this.cy = cy;
    firePropertyChange(RenderConstants.cy, oldCy, this.cy);
  }

  /**
   * Set the value of cz
   */
  public void setCz(Double cz) {
    Double oldCz = this.cz;
    this.cz = cz;
    firePropertyChange(RenderConstants.cz, oldCz, this.cz);
  }
  
  /**
   * Set the value of fx
   */
  public void setFx(Double fx) {
    Double oldFx = this.fx;
    this.fx = fx;
    firePropertyChange(RenderConstants.fx, oldFx, this.fx);
  }

  /**
   * Set the value of fy
   */
  public void setFy(Double fy) {
    Double oldFy = this.fy;
    this.fy = fy;
    firePropertyChange(RenderConstants.fy, oldFy, this.fy);
  }

  /**
   * Set the value of fz
   */
  public void setFz(Double fz) {
    Double oldFz = this.fz;
    this.fz = fz;
    firePropertyChange(RenderConstants.fz, oldFz, this.fz);
  }

  /**
   * Set the value of r
   */
  public void setR(Double r) {
    Double oldR = this.r;
    this.r = r;
    firePropertyChange(RenderConstants.r, oldR, this.r);
  }
  
  /**
   * Unsets the variable absoluteCx 
   * @return {@code true}, if absoluteCx was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteCx() {
    if (isSetAbsoluteCx()) {
      Boolean oldAbsoluteCx = this.absoluteCx;
      this.absoluteCx = null;
      firePropertyChange(RenderConstants.absoluteCx, oldAbsoluteCx, this.absoluteCx);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteCy 
   * @return {@code true}, if absoluteCy was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteCy() {
    if (isSetAbsoluteCy()) {
      Boolean oldAbsoluteCy = this.absoluteCy;
      this.absoluteCy = null;
      firePropertyChange(RenderConstants.absoluteCy, oldAbsoluteCy, this.absoluteCy);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteCz 
   * @return {@code true}, if absoluteCz was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteCz() {
    if (isSetAbsoluteCz()) {
      Boolean oldAbsoluteCz = this.absoluteCz;
      this.absoluteCz = null;
      firePropertyChange(RenderConstants.absoluteCz, oldAbsoluteCz, this.absoluteCz);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteFx 
   * @return {@code true}, if absoluteFx was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteFx() {
    if (isSetAbsoluteFx()) {
      Boolean oldAbsoluteFx = this.absoluteFx;
      this.absoluteFx = null;
      firePropertyChange(RenderConstants.absoluteFx, oldAbsoluteFx, this.absoluteFx);
      return true;
    }
    return false;
  }
  
  /**
   * Unsets the variable absoluteFy 
   * @return {@code true}, if absoluteFy was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteFy() {
    if (isSetAbsoluteFy()) {
      Boolean oldAbsoluteFy = this.absoluteFy;
      this.absoluteFy = null;
      firePropertyChange(RenderConstants.absoluteFy, oldAbsoluteFy, this.absoluteFy);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteFz 
   * @return {@code true}, if absoluteFz was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteFz() {
    if (isSetAbsoluteFz()) {
      Boolean oldAbsoluteFz = this.absoluteFz;
      this.absoluteFz = null;
      firePropertyChange(RenderConstants.absoluteFz, oldAbsoluteFz, this.absoluteFz);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteR 
   * @return {@code true}, if absoluteR was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteR() {
    if (isSetAbsoluteR()) {
      Boolean oldAbsoluteR = this.absoluteR;
      this.absoluteR = null;
      firePropertyChange(RenderConstants.absoluteR, oldAbsoluteR, this.absoluteR);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable cx 
   * @return {@code true}, if cx was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetCx() {
    if (isSetCx()) {
      Double oldCx = this.cx;
      this.cx = null;
      firePropertyChange(RenderConstants.cx, oldCx, this.cx);
      return true;
    }
    return false;
  }
  
  /**
   * Unsets the variable cy 
   * @return {@code true}, if cy was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetCy() {
    if (isSetCy()) {
      Double oldCy = this.cy;
      this.cy = null;
      firePropertyChange(RenderConstants.cy, oldCy, this.cy);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable cz 
   * @return {@code true}, if cz was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetCz() {
    if (isSetCz()) {
      Double oldCz = this.cz;
      this.cz = null;
      firePropertyChange(RenderConstants.cz, oldCz, this.cz);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable fx 
   * @return {@code true}, if fx was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetFx() {
    if (isSetFx()) {
      Double oldFx = this.fx;
      this.fx = null;
      firePropertyChange(RenderConstants.fx, oldFx, this.fx);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable fy 
   * @return {@code true}, if fy was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetFy() {
    if (isSetFy()) {
      Double oldFy = this.fy;
      this.fy = null;
      firePropertyChange(RenderConstants.fy, oldFy, this.fy);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable fz 
   * @return {@code true}, if fz was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetFz() {
    if (isSetFz()) {
      Double oldFz = this.fz;
      this.fz = null;
      firePropertyChange(RenderConstants.fz, oldFz, this.fz);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable r 
   * @return {@code true}, if r was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetR() {
    if (isSetR()) {
      Double oldR = this.r;
      this.r = null;
      firePropertyChange(RenderConstants.r, oldR, this.r);
      return true;
    }
    return false;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GradientBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetR()) {
      attributes.remove(RenderConstants.r);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.r,
        XMLTools.positioningToString(getR(), isAbsoluteR()));
    }
    if (isSetCx()) {
      attributes.remove(RenderConstants.cx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.cx,
        XMLTools.positioningToString(getCx(), isAbsoluteCx()));
    }
    if (isSetCy()) {
      attributes.remove(RenderConstants.cy);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.cy,
        XMLTools.positioningToString(getCy(), isAbsoluteCy()));
    }
    if (isSetCz()) {
      attributes.remove(RenderConstants.cz);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.cz,
        XMLTools.positioningToString(getCz(), isAbsoluteCz()));
    }
    if (isSetFx()) {
      attributes.remove(RenderConstants.fx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fx,
        XMLTools.positioningToString(getFx(), isAbsoluteFx()));
    }
    if (isSetFy()) {
      attributes.remove(RenderConstants.fy);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fy,
        XMLTools.positioningToString(getFy(), isAbsoluteFy()));
    }
    if (isSetFz()) {
      attributes.remove(RenderConstants.fz);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fz,
        XMLTools.positioningToString(getFz(), isAbsoluteFz()));
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GradientBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.r)) {
        setR(XMLTools.parsePosition(value));
        setAbsoluteR(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.cx)) {
        setCx(XMLTools.parsePosition(value));
        setAbsoluteCx(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.cy)) {
        setCy(XMLTools.parsePosition(value));
        setAbsoluteCy(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.cz)) {
        setCz(XMLTools.parsePosition(value));
        setAbsoluteCz(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.fx)) {
        setFx(XMLTools.parsePosition(value));
        setAbsoluteFx(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.fy)) {
        setFy(XMLTools.parsePosition(value));
        setAbsoluteFy(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.fz)) {
        setFz(XMLTools.parsePosition(value));
        setAbsoluteFz(XMLTools.isAbsolutePosition(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
