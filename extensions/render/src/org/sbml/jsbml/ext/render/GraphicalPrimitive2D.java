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
import org.sbml.jsbml.SBMLException;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class GraphicalPrimitive2D extends GraphicalPrimitive1D {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -5666416071124784541L;
  /**
   * 
   */
  protected String fill;
  
  /**
   * The type FillRule enumeration describes how a surface created by connecting points on a Polygon are to be filled when rendered.
   * 
   * <p>For a detailed description on how these values should be applied, we would like to refer you to the corresponding
   * documentation in the <a href="http://www.w3.org/TR/SVG/painting.html#FillRuleProperty">SVG specification</a>.</p>
   * 
   * @author Eugen Netz
   * @author Alexander Diamantikos
   * @author Jakob Matthes
   * @author Jan Rudolph
   * @since 1.0
   */
  public enum FillRule {
    /**
     * 
     */
    NONZERO,
    /**
     * 
     */
    EVENODD,
  }

  /**
   * 
   */
  protected FillRule fillRule;

  /**
   * Creates an {@link GraphicalPrimitive2D} instance
   */
  public GraphicalPrimitive2D() {
    super();
    initDefaults();
  }

  /**
   * Creates an {@link GraphicalPrimitive2D} instance
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public GraphicalPrimitive2D(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj
   */
  public GraphicalPrimitive2D(GraphicalPrimitive2D obj) {
    super(obj);
    
    if (obj.isSetFill()) {
      setFill(obj.fill);
    }
    if (obj.isSetFillRule()) {
      setFillRule(obj.fillRule);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#clone()
   */
  @Override
  public GraphicalPrimitive2D clone() {
    return new GraphicalPrimitive2D(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;    
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3169;
    int result = super.hashCode();
    result = prime * result + ((fill == null) ? 0 : fill.hashCode());
    result = prime * result + ((fillRule == null) ? 0 : fillRule.hashCode());
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
    GraphicalPrimitive2D other = (GraphicalPrimitive2D) obj;
    if (fill == null) {
      if (other.fill != null) {
        return false;
      }
    } else if (!fill.equals(other.fill)) {
      return false;
    }
    if (fillRule != other.fillRule) {
      return false;
    }
    return true;
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
    return fill != null;
  }

  /**
   * Set the value of fill
   * @param fill
   */
  public void setFill(String fill) {
    String oldFill = this.fill;
    this.fill = fill;
    firePropertyChange(RenderConstants.fill, oldFill, this.fill);
  }

  /**
   * Unsets the variable fill
   * @return {@code true}, if fill was set before,
   *         otherwise {@code false}
   */
  public boolean unsetFill() {
    if (isSetFill()) {
      String oldFill = fill;
      fill = null;
      firePropertyChange(RenderConstants.fill, oldFill, fill);
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
    return fillRule != null;
  }

  /**
   * Set the value of fillRule
   * @param fillRule
   */
  public void setFillRule(FillRule fillRule) {
    FillRule oldFillRule = this.fillRule;
    this.fillRule = fillRule;
    firePropertyChange(RenderConstants.fillRule, oldFillRule, this.fillRule);
  }

  /**
   * Unsets the variable fillRule
   * @return {@code true}, if fillRule was set before,
   *         otherwise {@code false}
   */
  public boolean unsetFillRule() {
    if (isSetFillRule()) {
      FillRule oldFillRule = fillRule;
      fillRule = null;
      firePropertyChange(RenderConstants.fillRule, oldFillRule, fillRule);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    
    if (isSetFill()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fill,
        getFill());
    }
    if (isSetFillRule()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fillRule,
        getFillRule().toString().toLowerCase());
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;
      
      if (attributeName.equals(RenderConstants.fill)) {
        setFill(value);
      }
      else if (attributeName.equals(RenderConstants.fillRule)) {
        try {
          setFillRule(FillRule.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.fillRule
              + " on the '" + getElementName() + "' element.");
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    
    return isAttributeRead;
  }

}
