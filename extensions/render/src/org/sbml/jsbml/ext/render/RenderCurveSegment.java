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

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Parent class to {@link RenderPoint} and {@link RenderCubicBezier}.
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public abstract class RenderCurveSegment extends AbstractSBase implements Point3D {

  /**
   * 
   * @author Nicolas Rodriguez
   * @since 1.2
   */
  public enum Type
  {
    /**
     * 
     */
    RENDER_CUBIC_BEZIER("RenderCubicBezier"),
    /**
     * 
     */
    RENDER_POINT("RenderPoint");

    /**
     * Returns the Type corresponding to the given String.
     * 
     * @param value a String
     * @return the Type corresponding to the given String.
     */
    public static Type fromString(String value)
    {
      if (value == null)
      {
        throw new IllegalArgumentException("Enumeration values cannot be NULL.");
      }

      for (Type v : values())
      {
        if (value.equalsIgnoreCase(v.getXmlString()))
        {
          return v;
        }
      }
      
      return valueOf(value);
    }

    /**
     * 
     */
    private final String xmlString;

    /**
     * Creates a new {@link Type} instance with the given XML String.
     * 
     * @param xmlString the XML String
     */
    private Type(String xmlString) {
      this.xmlString = xmlString;
    }

    /**
     * Returns the String that will be written to XML.
     *
     * @return the xmlString
     */
    public String getXmlString() {
      return xmlString;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
      return xmlString;
    }
  }

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5085246314333062152L;

  /**
   * 
   */
  private static final transient Logger logger = Logger.getLogger(RenderCurveSegment.class);

  /**
   * 
   */
  private Type type;



  /**
   * Creates a new {@link RenderCurveSegment} instance.
   * 
   */
  public RenderCurveSegment() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link RenderCurveSegment} instance cloned from the given {@link RenderCurveSegment}.
   * 
   * @param curveSegment the {@link RenderCurveSegment} to clone
   */
  public RenderCurveSegment(RenderCurveSegment curveSegment) {
    super(curveSegment);

    if (curveSegment.isSetType()) {
      setType(curveSegment.getType());
    }
  }

  /**
   * Creates a new {@link RenderCurveSegment} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public RenderCurveSegment(int level, int version) {
    super(level, version);
    initDefaults();
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      RenderCurveSegment curveSegment = (RenderCurveSegment) object;
      equals &= curveSegment.isSetType() == isSetType();
      if (equals && isSetType()) {
        equals &= curveSegment.getType().equals(getType());
      }
    }
    return equals;
  }

  /**
   * Returns the type.
   * 
   * @return the type.
   */
  public Type getType() {
    return type;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3049;
    int hashCode = super.hashCode();
    if (isSetType()) {
      hashCode += prime * getType().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   */
  private void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }

  /**
   * Returns {@code true} if the type is equals to {@link Type#RENDER_CUBIC_BEZIER}.
   * 
   * @return {@code true} if the type is equals to {@link Type#RENDER_CUBIC_BEZIER}.
   */
  public boolean isRenderCubicBezier() {
    return type != null && type.equals(Type.RENDER_CUBIC_BEZIER);
  }

  /**
   * Returns {@code true} if the type is equals to {@link Type#RENDER_POINT}.
   * 
   * @return {@code true} if the type is equals to {@link Type#RENDER_POINT}.
   */
  public boolean isRenderPoint() {
    return type != null && type.equals(Type.RENDER_POINT);
  }

  /**
   * Returns {@code true} if the type is set.
   * 
   * @return {@code true} if the type is set.
   */
  public boolean isSetType() {
    return type != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) 
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (logger.isDebugEnabled()) {
      logger.debug("reading RenderCurveSegment attribute " + prefix + ":" + attributeName);
    }
    
    if (!isAttributeRead) {

      if (attributeName.equals("type")) {
        try
        {
          setType(Type.fromString(value));
        }
        catch (Exception e)
        {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + RenderConstants.type
            + " on a 'RenderPoint' or 'RenderCubicBezier' element.");
        }
        return true;
      }
    }

    return isAttributeRead;
  }


  /**
   * Sets the {@link Type} of this {@link RenderCurveSegment} to {@link Type#RENDER_CUBIC_BEZIER}
   * or {@link Type#RENDER_POINT}.
   * 
   * @param type the type
   */
  void setType(Type type)
  {
    Type oldType = this.type;
    this.type = type;
    firePropertyChange(TreeNodeChangeEvent.type, oldType, this.type);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (logger.isDebugEnabled())
    {
      logger.debug("write XML attributes of RenderCurveSegment");
      logger.debug("isSetType: " + isSetType());
      logger.debug("Type = " + type);
    }

    if (isSetType()) {
      attributes.put(RenderConstants.xsiShortLabel + ":type", getType().toString());
    }

    return attributes;
  }

}
