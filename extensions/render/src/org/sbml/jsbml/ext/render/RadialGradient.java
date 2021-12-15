/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
 * @author David Emanuel Vetter
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
  private RelAbsVector cx, cy, cz, r, fx, fy, fz;


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
  public RelAbsVector getCx() {
    if (isSetCx()) {
      return cx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.cx, this);
  }

  /**
   * @return the value of cy
   */
  public RelAbsVector getCy() {
    if (isSetCy()) {
      return cy;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.cy, this);
  }

  /**
   * @return the value of cz
   */
  public RelAbsVector getCz() {
    if (isSetCz()) {
      return cz;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.cz, this);
  }

  /**
   * @return the value of fx
   */
  public RelAbsVector getFx() {
    if (isSetFx()) {
      return fx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fx, this);
  }

  /**
   * @return the value of fy
   */
  public RelAbsVector getFy() {
    if (isSetFy()) {
      return fy;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fy, this);
  }

  /**
   * @return the value of fz
   */
  public RelAbsVector getFz() {
    if (isSetFz()) {
      return fz;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fz, this);
  }

  /**
   * @return the value of r
   */
  public RelAbsVector getR() {
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
   * Set the value of cx
   * @param cx
   */
  public void setCx(RelAbsVector cx) {
    RelAbsVector oldCx = this.cx;
    this.cx = cx;
    firePropertyChange(RenderConstants.cx, oldCx, this.cx);
  }

  /**
   * Set the value of cy
   * @param cy
   */
  public void setCy(RelAbsVector cy) {
    RelAbsVector oldCy = this.cy;
    this.cy = cy;
    firePropertyChange(RenderConstants.cy, oldCy, this.cy);
  }

  /**
   * Set the value of cz
   * @param cz
   */
  public void setCz(RelAbsVector cz) {
    RelAbsVector oldCz = this.cz;
    this.cz = cz;
    firePropertyChange(RenderConstants.cz, oldCz, this.cz);
  }

  /**
   * Set the value of fx
   * @param fx
   */
  public void setFx(RelAbsVector fx) {
    RelAbsVector oldFx = this.fx;
    this.fx = fx;
    firePropertyChange(RenderConstants.fx, oldFx, this.fx);
  }

  /**
   * Set the value of fy
   * @param fy
   */
  public void setFy(RelAbsVector fy) {
    RelAbsVector oldFy = this.fy;
    this.fy = fy;
    firePropertyChange(RenderConstants.fy, oldFy, this.fy);
  }

  /**
   * Set the value of fz
   * @param fz
   */
  public void setFz(RelAbsVector fz) {
    RelAbsVector oldFz = this.fz;
    this.fz = fz;
    firePropertyChange(RenderConstants.fz, oldFz, this.fz);
  }

  /**
   * Set the value of r
   * @param r
   */
  public void setR(RelAbsVector r) {
    RelAbsVector oldR = this.r;
    this.r = r;
    firePropertyChange(RenderConstants.r, oldR, this.r);
  }
  
  /**
   * Unsets the variable cx
   * @return {@code true}, if cx was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCx() {
    if (isSetCx()) {
      RelAbsVector oldCx = cx;
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
      RelAbsVector oldCy = cy;
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
      RelAbsVector oldCz = cz;
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
      RelAbsVector oldFx = fx;
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
      RelAbsVector oldFy = fy;
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
      RelAbsVector oldFz = fz;
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
      RelAbsVector oldR = r;
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
        getR().getCoordinate());
    }
    if (isSetCx()) {
      attributes.remove(RenderConstants.cx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.cx,
        getCx().getCoordinate());
    }
    if (isSetCy()) {
      attributes.remove(RenderConstants.cy);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.cy,
        getCy().getCoordinate());
    }
    if (isSetCz()) {
      attributes.remove(RenderConstants.cz);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.cz,
        getCz().getCoordinate());
    }
    if (isSetFx()) {
      attributes.remove(RenderConstants.fx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fx,
        getFx().getCoordinate());
    }
    if (isSetFy()) {
      attributes.remove(RenderConstants.fy);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fy,
        getFy().getCoordinate());
    }
    if (isSetFz()) {
      attributes.remove(RenderConstants.fz);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fz,
        getFz().getCoordinate());
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
        setR(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.cx)) {
        setCx(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.cy)) {
        setCy(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.cz)) {
        setCz(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.fx)) {
        setFx(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.fy)) {
        setFy(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.fz)) {
        setFz(new RelAbsVector(value));
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
