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

import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;

/**
 * Encodes an ellipse.
 * 
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
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
  protected Double cx;
  /**
   * 
   */
  protected Double cy;
  /**
   * 
   */
  protected Double cz;
  /**
   * 
   */
  protected Double rx;
  /**
   * 
   */
  protected Double ry;
  /**
   * 
   */
  protected Boolean absoluteCx;
  /**
   * 
   */
  protected Boolean absoluteCy;
  /**
   * 
   */
  protected Boolean absoluteCz;
  /**
   * 
   */
  protected Boolean absoluteRx;
  /**
   * 
   */
  protected Boolean absoluteRy;

  // TODO - missing attribute ratio
  
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
    
    absoluteCx = obj.absoluteCx;
    absoluteCy = obj.absoluteCy;
    absoluteCz = obj.absoluteCz;
    absoluteRx = obj.absoluteRx;
    absoluteRy = obj.absoluteRy;
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
  public double getCx() {
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
    result = prime * result
        + ((absoluteCx == null) ? 0 : absoluteCx.hashCode());
    result = prime * result
        + ((absoluteCy == null) ? 0 : absoluteCy.hashCode());
    result = prime * result
        + ((absoluteCz == null) ? 0 : absoluteCz.hashCode());
    result = prime * result
        + ((absoluteRx == null) ? 0 : absoluteRx.hashCode());
    result = prime * result
        + ((absoluteRy == null) ? 0 : absoluteRy.hashCode());
    result = prime * result + ((cx == null) ? 0 : cx.hashCode());
    result = prime * result + ((cy == null) ? 0 : cy.hashCode());
    result = prime * result + ((cz == null) ? 0 : cz.hashCode());
    result = prime * result + ((rx == null) ? 0 : rx.hashCode());
    result = prime * result + ((ry == null) ? 0 : ry.hashCode());
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
    if (absoluteRx == null) {
      if (other.absoluteRx != null) {
        return false;
      }
    } else if (!absoluteRx.equals(other.absoluteRx)) {
      return false;
    }
    if (absoluteRy == null) {
      if (other.absoluteRy != null) {
        return false;
      }
    } else if (!absoluteRy.equals(other.absoluteRy)) {
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
  public void setCx(double cx) {
    Double oldCx = this.cx;
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
      Double oldCx = cx;
      cx = null;
      firePropertyChange(RenderConstants.cx, oldCx, cx);
      return true;
    }
    return false;
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
   * @return whether cy is set
   */
  public boolean isSetCy() {
    return cy != null;
  }

  /**
   * Set the value of cy
   * @param cy
   */
  public void setCy(Double cy) {
    Double oldCy = this.cy;
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
      Double oldCy = cy;
      cy = null;
      firePropertyChange(RenderConstants.cy, oldCy, cy);
      return true;
    }
    return false;
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
   * @return whether cz is set
   */
  public boolean isSetCz() {
    return cz != null;
  }

  /**
   * Set the value of cz
   * @param cz
   */
  public void setCz(Double cz) {
    Double oldCz = this.cz;
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
      Double oldCz = cz;
      cz = null;
      firePropertyChange(RenderConstants.cz, oldCz, cz);
      return true;
    }
    return false;
  }

  /**
   * @return the value of rx
   */
  public double getRx() {
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
  public void setRx(Double rx) {
    Double oldRx = this.rx;
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
      Double oldRx = rx;
      rx = null;
      firePropertyChange(RenderConstants.rx, oldRx, rx);
      return true;
    }
    return false;
  }

  /**
   * @return the value of ry
   */
  public double getRy() {
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
  public void setRy(Double ry) {
    Double oldRy = this.ry;
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
      Double oldRy = ry;
      ry = null;
      firePropertyChange(RenderConstants.ry, oldRy, ry);
      return true;
    }
    return false;
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
   * @return whether absoluteCx is set
   */
  public boolean isSetAbsoluteCx() {
    return absoluteCx != null;
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
   * @return whether absoluteCy is set
   */
  public boolean isSetAbsoluteCy() {
    return absoluteCy != null;
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
   * @return whether absoluteCz is set
   */
  public boolean isSetAbsoluteCz() {
    return absoluteCz != null;
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
   * @return the value of absoluteRx
   */
  public boolean isAbsoluteRx() {
    if (isSetAbsoluteRx()) {
      return absoluteRx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteRx, this);
  }

  /**
   * @return whether absoluteRx is set
   */
  public boolean isSetAbsoluteRx() {
    return absoluteRx != null;
  }

  /**
   * Set the value of absoluteRx
   * @param absoluteRx
   */
  public void setAbsoluteRx(boolean absoluteRx) {
    Boolean oldAbsoluteRx = this.absoluteRx;
    this.absoluteRx = absoluteRx;
    firePropertyChange(RenderConstants.absoluteRx, oldAbsoluteRx, this.absoluteRx);
  }

  /**
   * Unsets the variable absoluteRx
   * @return {@code true}, if absoluteRx was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteRx() {
    if (isSetAbsoluteRx()) {
      Boolean oldAbsoluteRx = absoluteRx;
      absoluteRx = null;
      firePropertyChange(RenderConstants.absoluteRx, oldAbsoluteRx, absoluteRx);
      return true;
    }
    return false;
  }


  /**
   * @return the value of absoluteRy
   */
  public boolean isAbsoluteRy() {
    if (isSetAbsoluteRy()) {
      return absoluteRy;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteRy, this);
  }

  /**
   * @return whether absoluteRy is set
   */
  public boolean isSetAbsoluteRy() {
    return absoluteRy != null;
  }

  /**
   * Set the value of absoluteRy
   * @param absoluteRy
   */
  public void setAbsoluteRy(boolean absoluteRy) {
    Boolean oldAbsoluteRy = this.absoluteRy;
    this.absoluteRy = absoluteRy;
    firePropertyChange(RenderConstants.absoluteRy, oldAbsoluteRy, this.absoluteRy);
  }

  /**
   * Unsets the variable absoluteRy
   * @return {@code true}, if absoluteRy was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteRy() {
    if (isSetAbsoluteRy()) {
      Boolean oldAbsoluteRy = absoluteRy;
      absoluteRy = null;
      firePropertyChange(RenderConstants.absoluteRy, oldAbsoluteRy, absoluteRy);
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
    if (isSetRx()) {
      attributes.remove(RenderConstants.rx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.rx,
        XMLTools.positioningToString(getRx(), isAbsoluteRx()));
    }
    if (isSetRy()) {
      attributes.remove(RenderConstants.ry);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.ry,
        XMLTools.positioningToString(getRy(), isAbsoluteRy()));
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
      else if (attributeName.equals(RenderConstants.rx)) {
        setRx(XMLTools.parsePosition(value));
        setAbsoluteRx(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.ry)) {
        setRy(XMLTools.parsePosition(value));
        setAbsoluteRy(XMLTools.isAbsolutePosition(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
