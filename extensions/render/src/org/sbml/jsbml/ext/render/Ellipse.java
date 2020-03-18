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
 * 6. Marquette University, Milwaukee, WI, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render;

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * Encodes an ellipse.
 * 
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @author David Vetter
 * @since 1.0
 */
public class Ellipse extends GraphicalPrimitive2D {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -7679215241987476130L;
  /**
   * 
   */
  protected RelAbsVector cx;
  /**
   * 
   */
  protected RelAbsVector cy;
  /**
   * 
   */
  protected RelAbsVector cz;
  /**
   * 
   */
  protected RelAbsVector rx;
  /**
   * 
   */
  protected RelAbsVector ry;
  /**
   * Optional: Ratio of width to height
   */
  protected Double ratio;
  
  /**
   * Creates a new {@link Ellipse} instance.
   *  
   */
  public Ellipse() {
    super();
  }

  /**
   * Creates a new {@link Ellipse} instance, copied from the given ellipse.
   * 
   * @param obj the Ellipse to be cloned
   */
  public Ellipse(Ellipse obj) {
    super(obj);
    
    cx = obj.cx;
    cy = obj.cy;
    cz = obj.cz;
    rx = obj.rx;
    ry = obj.ry;
    
    ratio = obj.ratio;
  }

  
  /**
   * Creates a new {@link Ellipse} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Ellipse(int level, int version) {
    super(level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#clone()
   */
  @Override
  public Ellipse clone() {
    return new Ellipse(this);
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3137;
    int result = super.hashCode();
    
    result = prime * result + ((cx == null) ? 0 : cx.hashCode());
    result = prime * result + ((cy == null) ? 0 : cy.hashCode());
    result = prime * result + ((cz == null) ? 0 : cz.hashCode());
    result = prime * result + ((rx == null) ? 0 : rx.hashCode());
    result = prime * result + ((ry == null) ? 0 : ry.hashCode());
    result = prime * result + ((ratio == null) ? 0 : ratio.hashCode());
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
    Ellipse other = (Ellipse) obj;
    
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
    if (rx == null) {
      if (other.rx != null) {
        return false;
      }
    } else if (!rx.equals(other.rx)) {
      return false;
    }
    if (ry == null) {
      if (other.ry != null) {
        return false;
      }
    } else if (!ry.equals(other.ry)) {
      return false;
    }
    if (ratio == null) {
      if (other.ratio != null) {
        return false;
      }
    } else if (!ratio.equals(other.ratio)) {
      return false;
    }
    return true;
  }

  /**
   * @return whether cx is set
   */
  public boolean isSetCx() {
    return cx != null;
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
   * @return whether cy is set
   */
  public boolean isSetCy() {
    return cy != null;
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
   * @return whether cz is set
   */
  public boolean isSetCz() {
    return cz != null;
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
   * @return the ratio-attribute (specifies rx/ry, cf. render specification: Ellipse class)
   */
  public double getRatio() {
    return ratio;
  }
  
  /**
   * @return whether the ratio-attribute is set to a number
   */
  public boolean isSetRatio() {
    return ratio != null && !ratio.isNaN();
  }
  
  /**
   * Sets the ratio-attribute to given value and fires appropriate change-events
   * @param newRatio the new value to be taken
   */
  public void setRatio(Double newRatio) {
    Double oldCz = this.ratio;
    this.ratio = newRatio;
    firePropertyChange(RenderConstants.ratio, oldCz, this.cz);
  }
  
  /**
   * Unsets the ratio-attribute (if set)
   * @return Whether the ratio could actually be unset
   */
  public boolean unsetRatio() {
    if (isSetRatio()) {
      Double old = ratio;
      ratio = null;
      firePropertyChange(RenderConstants.ratio, old, ratio);
      return true;
    }
    return false;
  }

  /**
   * @return the value of rx
   */
  public RelAbsVector getRx() {
    if (isSetRx()) {
      return rx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.rx, this);
  }

  /**
   * @return whether rx is set
   */
  public boolean isSetRx() {
    return rx != null;
  }

  /**
   * Set the value of rx
   * @param rx
   */
  public void setRx(RelAbsVector rx) {
    RelAbsVector oldRx = this.rx;
    this.rx = rx;
    firePropertyChange(RenderConstants.rx, oldRx, this.rx);
  }

  /**
   * Unsets the variable rx
   * @return {@code true}, if rx was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRx() {
    if (isSetRx()) {
      RelAbsVector oldRx = rx;
      rx = null;
      firePropertyChange(RenderConstants.rx, oldRx, rx);
      return true;
    }
    return false;
  }

  /**
   * @return the value of ry
   */
  public RelAbsVector getRy() {
    if (isSetRy()) {
      return ry;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.ry, this);
  }

  /**
   * @return whether ry is set
   */
  public boolean isSetRy() {
    return ry != null;
  }

  /**
   * Set the value of ry
   * @param ry
   */
  public void setRy(RelAbsVector ry) {
    RelAbsVector oldRy = this.ry;
    this.ry = ry;
    firePropertyChange(RenderConstants.ry, oldRy, this.ry);
  }

  /**
   * Unsets the variable ry
   * @return {@code true}, if ry was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRy() {
    if (isSetRy()) {
      RelAbsVector oldRy = ry;
      ry = null;
      firePropertyChange(RenderConstants.ry, oldRy, ry);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

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
    if (isSetRx()) {
      attributes.remove(RenderConstants.rx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.rx,
        getRx().getCoordinate());
    }
    if (isSetRy()) {
      attributes.remove(RenderConstants.ry);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.ry,
        getRy().getCoordinate());
    }
    if(isSetRatio()) {
      attributes.remove(RenderConstants.ratio);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.ratio,
        StringTools.toString(Locale.ENGLISH, getRatio()));
    }

    return attributes;
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
      if (attributeName.equals(RenderConstants.cx)) {
        setCx(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.cy)) {
        setCy(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.cz)) {
        setCz(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.rx)) {
        setRx(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.ry)) {
        setRy(new RelAbsVector(value));
      }
      else if(attributeName.equals(RenderConstants.ratio)) {
        try {
          setRatio(Double.parseDouble(value));
        } catch(NumberFormatException e){
          putUserObject(JSBML.INVALID_XML, value);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
