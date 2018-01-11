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
package org.sbml.jsbml.ext.render;

import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class RadialGradient extends GradientBase {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -6976786676644704255L;
  /**
   * 
   */
  private Boolean absoluteCx;
  /**
   * 
   */
  private Boolean absoluteCy;
  /**
   * 
   */
  private Boolean absoluteCz;
  /**
   * 
   */
  private Boolean absoluteR;

  /**
   * 
   */
  private Boolean absoluteFx;
  /**
   * 
   */
  private Boolean absoluteFy;
  /**
   * 
   */
  private Boolean absoluteFz;

  /**
   * 
   */
  private Double cx;
  /**
   * 
   */
  private Double cy;
  /**
   * 
   */
  private Double cz;
  /**
   * 
   */
  private Double r;
  /**
   * 
   */
  private Double fx;
  /**
   * 
   */
  private Double fy;
  /**
   * 
   */
  private Double fz;


  /**
   * Creates an RadialGradient instance
   */
  public RadialGradient() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
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
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
    
//    cx = 0.5d;
//    cy = 0.5d;
//    cz = 0.5d;
//    r = 0.5d;
//    fx = cx;
//    fy = cy;
//    fz = cz;
//    absoluteCx = false;
//    absoluteCy = false;
//    absoluteCz = false;
//    absoluteR = false;
//    absoluteFx = false;
//    absoluteFy = false;
//    absoluteFz = false;
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
    return absoluteCx != null;
  }

  /**
   * @return whether absoluteCy is set
   */
  public boolean isSetAbsoluteCy() {
    return absoluteCy != null;
  }

  /**
   * @return whether absoluteCz is set
   */
  public boolean isSetAbsoluteCz() {
    return absoluteCz != null;
  }

  /**
   * @return whether absoluteFx is set
   */
  public boolean isSetAbsoluteFx() {
    return absoluteFx != null;
  }

  /**
   * @return whether absoluteFy is set
   */
  public boolean isSetAbsoluteFy() {
    return absoluteFy != null;
  }

  /**
   * @return whether absoluteFz is set
   */
  public boolean isSetAbsoluteFz() {
    return absoluteFz != null;
  }

  /**
   * @return whether absoluteR is set
   */
  public boolean isSetAbsoluteR() {
    return absoluteR != null;
  }

  /**
   * @return whether cx is set
   */
  public boolean isSetCx() {
    return cx != null;
  }

  /**
   * @return whether cy is set
   */
  public boolean isSetCy() {
    return cy != null;
  }

  /**
   * @return whether cz is set
   */
  public boolean isSetCz() {
    return cz != null;
  }

  /**
   * @return whether fx is set
   */
  public boolean isSetFx() {
    return fx != null;
  }

  /**
   * @return whether fy is set
   */
  public boolean isSetFy() {
    return fy != null;
  }

  /**
   * @return whether fz is set
   */
  public boolean isSetFz() {
    return fz != null;
  }

  /**
   * @return whether r is set
   */
  public boolean isSetR() {
    return r != null;
  }

  /**
   * Set the value of absoluteCx
   * @param absoluteCx
   */
  public void setAbsoluteCx(boolean absoluteCx) {
    Boolean oldAbsoluteCx = this.absoluteCx;
    this.absoluteCx = absoluteCx;
    firePropertyChange(RenderConstants.absoluteCx, oldAbsoluteCx, this.absoluteCx);
  }

  /**
   * Set the value of absoluteCy
   * @param absoluteCy
   */
  public void setAbsoluteCy(boolean absoluteCy) {
    Boolean oldAbsoluteCy = this.absoluteCy;
    this.absoluteCy = absoluteCy;
    firePropertyChange(RenderConstants.absoluteCy, oldAbsoluteCy, this.absoluteCy);
  }

  /**
   * Set the value of absoluteCz
   * @param absoluteCz
   */
  public void setAbsoluteCz(boolean absoluteCz) {
    Boolean oldAbsoluteCz = this.absoluteCz;
    this.absoluteCz = absoluteCz;
    firePropertyChange(RenderConstants.absoluteCz, oldAbsoluteCz, this.absoluteCz);
  }

  /**
   * Set the value of absoluteFx
   * @param absoluteFx
   */
  public void setAbsoluteFx(boolean absoluteFx) {
    Boolean oldAbsoluteFx = this.absoluteFx;
    this.absoluteFx = absoluteFx;
    firePropertyChange(RenderConstants.absoluteFx, oldAbsoluteFx, this.absoluteFx);
  }

  /**
   * Set the value of absoluteFy
   * @param absoluteFy
   */
  public void setAbsoluteFy(boolean absoluteFy) {
    Boolean oldAbsoluteFy = this.absoluteFy;
    this.absoluteFy = absoluteFy;
    firePropertyChange(RenderConstants.absoluteFy, oldAbsoluteFy, this.absoluteFy);
  }

  /**
   * Set the value of absoluteFz
   * @param absoluteFz
   */
  public void setAbsoluteFz(boolean absoluteFz) {
    Boolean oldAbsoluteFz = this.absoluteFz;
    this.absoluteFz = absoluteFz;
    firePropertyChange(RenderConstants.absoluteFz, oldAbsoluteFz, this.absoluteFz);
  }

  /**
   * Set the value of absoluteR
   * @param absoluteR
   */
  public void setAbsoluteR(boolean absoluteR) {
    Boolean oldAbsoluteR = this.absoluteR;
    this.absoluteR = absoluteR;
    firePropertyChange(RenderConstants.absoluteR, oldAbsoluteR, this.absoluteR);
  }

  /**
   * Set the value of cx
   * @param cx
   */
  public void setCx(double cx) {
    Double oldCx = this.cx;
    this.cx = cx;
    firePropertyChange(RenderConstants.cx, oldCx, this.cx);
  }

  /**
   * Set the value of cy
   * @param cy
   */
  public void setCy(double cy) {
    Double oldCy = this.cy;
    this.cy = cy;
    firePropertyChange(RenderConstants.cy, oldCy, this.cy);
  }

  /**
   * Set the value of cz
   * @param cz
   */
  public void setCz(double cz) {
    Double oldCz = this.cz;
    this.cz = cz;
    firePropertyChange(RenderConstants.cz, oldCz, this.cz);
  }

  /**
   * Set the value of fx
   * @param fx
   */
  public void setFx(double fx) {
    Double oldFx = this.fx;
    this.fx = fx;
    firePropertyChange(RenderConstants.fx, oldFx, this.fx);
  }

  /**
   * Set the value of fy
   * @param fy
   */
  public void setFy(double fy) {
    Double oldFy = this.fy;
    this.fy = fy;
    firePropertyChange(RenderConstants.fy, oldFy, this.fy);
  }

  /**
   * Set the value of fz
   * @param fz
   */
  public void setFz(double fz) {
    Double oldFz = this.fz;
    this.fz = fz;
    firePropertyChange(RenderConstants.fz, oldFz, this.fz);
  }

  /**
   * Set the value of r
   * @param r
   */
  public void setR(double r) {
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
      Boolean oldAbsoluteCx = absoluteCx;
      absoluteCx = null;
      firePropertyChange(RenderConstants.absoluteCx, oldAbsoluteCx, absoluteCx);
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
      Boolean oldAbsoluteCy = absoluteCy;
      absoluteCy = null;
      firePropertyChange(RenderConstants.absoluteCy, oldAbsoluteCy, absoluteCy);
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
      Boolean oldAbsoluteCz = absoluteCz;
      absoluteCz = null;
      firePropertyChange(RenderConstants.absoluteCz, oldAbsoluteCz, absoluteCz);
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
      Boolean oldAbsoluteFx = absoluteFx;
      absoluteFx = null;
      firePropertyChange(RenderConstants.absoluteFx, oldAbsoluteFx, absoluteFx);
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
      Boolean oldAbsoluteFy = absoluteFy;
      absoluteFy = null;
      firePropertyChange(RenderConstants.absoluteFy, oldAbsoluteFy, absoluteFy);
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
      Boolean oldAbsoluteFz = absoluteFz;
      absoluteFz = null;
      firePropertyChange(RenderConstants.absoluteFz, oldAbsoluteFz, absoluteFz);
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
      Boolean oldAbsoluteR = absoluteR;
      absoluteR = null;
      firePropertyChange(RenderConstants.absoluteR, oldAbsoluteR, absoluteR);
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
      Double oldCx = cx;
      cx = null;
      firePropertyChange(RenderConstants.cx, oldCx, cx);
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
      Double oldCy = cy;
      cy = null;
      firePropertyChange(RenderConstants.cy, oldCy, cy);
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
      Double oldCz = cz;
      cz = null;
      firePropertyChange(RenderConstants.cz, oldCz, cz);
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
      Double oldFx = fx;
      fx = null;
      firePropertyChange(RenderConstants.fx, oldFx, fx);
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
      Double oldFy = fy;
      fy = null;
      firePropertyChange(RenderConstants.fy, oldFy, fy);
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
      Double oldFz = fz;
      fz = null;
      firePropertyChange(RenderConstants.fz, oldFz, fz);
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
      Double oldR = r;
      r = null;
      firePropertyChange(RenderConstants.r, oldR, r);
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3169;
    int result = super.hashCode();
    result = prime * result
        + ((absoluteCx == null) ? 0 : absoluteCx.hashCode());
    result = prime * result
        + ((absoluteCy == null) ? 0 : absoluteCy.hashCode());
    result = prime * result
        + ((absoluteCz == null) ? 0 : absoluteCz.hashCode());
    result = prime * result
        + ((absoluteFx == null) ? 0 : absoluteFx.hashCode());
    result = prime * result
        + ((absoluteFy == null) ? 0 : absoluteFy.hashCode());
    result = prime * result
        + ((absoluteFz == null) ? 0 : absoluteFz.hashCode());
    result = prime * result + ((absoluteR == null) ? 0 : absoluteR.hashCode());
    result = prime * result + ((cx == null) ? 0 : cx.hashCode());
    result = prime * result + ((cy == null) ? 0 : cy.hashCode());
    result = prime * result + ((cz == null) ? 0 : cz.hashCode());
    result = prime * result + ((fx == null) ? 0 : fx.hashCode());
    result = prime * result + ((fy == null) ? 0 : fy.hashCode());
    result = prime * result + ((fz == null) ? 0 : fz.hashCode());
    result = prime * result + ((r == null) ? 0 : r.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    RadialGradient other = (RadialGradient) obj;
    if (absoluteCx == null) {
      if (other.absoluteCx != null) {
        return false;
      }
    } else if (!absoluteCx.equals(other.absoluteCx)) {
      return false;
    }
    if (absoluteCy == null) {
      if (other.absoluteCy != null) {
        return false;
      }
    } else if (!absoluteCy.equals(other.absoluteCy)) {
      return false;
    }
    if (absoluteCz == null) {
      if (other.absoluteCz != null) {
        return false;
      }
    } else if (!absoluteCz.equals(other.absoluteCz)) {
      return false;
    }
    if (absoluteFx == null) {
      if (other.absoluteFx != null) {
        return false;
      }
    } else if (!absoluteFx.equals(other.absoluteFx)) {
      return false;
    }
    if (absoluteFy == null) {
      if (other.absoluteFy != null) {
        return false;
      }
    } else if (!absoluteFy.equals(other.absoluteFy)) {
      return false;
    }
    if (absoluteFz == null) {
      if (other.absoluteFz != null) {
        return false;
      }
    } else if (!absoluteFz.equals(other.absoluteFz)) {
      return false;
    }
    if (absoluteR == null) {
      if (other.absoluteR != null) {
        return false;
      }
    } else if (!absoluteR.equals(other.absoluteR)) {
      return false;
    }
    if (cx == null) {
      if (other.cx != null) {
        return false;
      }
    } else if (!cx.equals(other.cx)) {
      return false;
    }
    if (cy == null) {
      if (other.cy != null) {
        return false;
      }
    } else if (!cy.equals(other.cy)) {
      return false;
    }
    if (cz == null) {
      if (other.cz != null) {
        return false;
      }
    } else if (!cz.equals(other.cz)) {
      return false;
    }
    if (fx == null) {
      if (other.fx != null) {
        return false;
      }
    } else if (!fx.equals(other.fx)) {
      return false;
    }
    if (fy == null) {
      if (other.fy != null) {
        return false;
      }
    } else if (!fy.equals(other.fy)) {
      return false;
    }
    if (fz == null) {
      if (other.fz != null) {
        return false;
      }
    } else if (!fz.equals(other.fz)) {
      return false;
    }
    if (r == null) {
      if (other.r != null) {
        return false;
      }
    } else if (!r.equals(other.r)) {
      return false;
    }
    return true;
  }

  
}
