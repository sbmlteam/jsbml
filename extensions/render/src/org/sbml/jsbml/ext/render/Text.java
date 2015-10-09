/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class Text extends GraphicalPrimitive1D implements FontRenderStyle, Point3D {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -7468181076596795203L;
  /**
   * 
   */
  private Boolean absoluteX;
  /**
   * 
   */
  private Boolean absoluteY;
  /**
   * 
   */
  private Boolean absoluteZ;
  /**
   * 
   */
  private FontFamily fontFamily;
  /**
   * 
   */
  private Short fontSize;
  /**
   * 
   */
  private Boolean fontWeightBold;
  /**
   * 
   */
  private Boolean fontStyleItalic;
  /**
   * 
   */
  private TextAnchor textAnchor;
  /**
   * 
   */
  private VTextAnchor vTextAnchor;
  /**
   * 
   */
  private Double x;
  /**
   * 
   */
  private Double y;
  /**
   * 
   */
  private Double z;

  
  
  
  public Text() {
    super();
    initDefaults();
  }

  public Text(int level, int version) {
    super(level, version);
    initDefaults();
  }

  public Text(Text obj) {
    super(obj);
    
    // TODO copy all attributes
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#getFontFamily()
   */
  @Override
  public FontFamily getFontFamily() {
    if (isSetFontFamily()) {
      return fontFamily;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontFamily, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#getFontSize()
   */
  @Override
  public short getFontSize() {
    if (isSetFontSize()) {
      return fontSize;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontSize, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#getTextAnchor()
   */
  @Override
  public TextAnchor getTextAnchor() {
    if (isSetTextAnchor()) {
      return textAnchor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.textAnchor, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#getVTextAnchor()
   */
  @Override
  public VTextAnchor getVTextAnchor() {
    if (isSetVTextAnchor()) {
      return vTextAnchor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.vTextAnchor, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#getX()
   */
  @Override
  public double getX() {
    if (isSetX()) {
      return x;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.x, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#getY()
   */
  @Override
  public double getY() {
    if (isSetY()) {
      return y;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.y, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#getZ()
   */
  @Override
  public double getZ() {
    if (isSetZ()) {
      return z;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.z, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isAbsoluteX()
   */
  @Override
  public boolean isAbsoluteX() {
    if (isSetAbsoluteX()) {
      return absoluteX;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteX, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isAbsoluteY()
   */
  @Override
  public boolean isAbsoluteY() {
    if (isSetAbsoluteY()) {
      return absoluteY;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteY, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isAbsoluteZ()
   */
  @Override
  public boolean isAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      return absoluteZ;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteZ, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isFontStyleItalic()
   */
  @Override
  public boolean isFontStyleItalic() {
    if (isSetFontStyleItalic()) {
      return fontStyleItalic;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontStyleItalic, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isFontWeightBold()
   */
  @Override
  public boolean isFontWeightBold() {
    if (isSetFontWeightBold()) {
      return fontWeightBold;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.fontWeightBold, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetAbsoluteX()
   */
  @Override
  public boolean isSetAbsoluteX() {
    return absoluteX != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetAbsoluteY()
   */
  @Override
  public boolean isSetAbsoluteY() {
    return absoluteY != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetAbsoluteZ()
   */
  @Override
  public boolean isSetAbsoluteZ() {
    return absoluteZ != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontFamily()
   */
  @Override
  public boolean isSetFontFamily() {
    return fontFamily != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontSize()
   */
  @Override
  public boolean isSetFontSize() {
    return fontSize != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontStyleItalic()
   */
  @Override
  public boolean isSetFontStyleItalic() {
    return fontStyleItalic != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontWeightBold()
   */
  @Override
  public boolean isSetFontWeightBold() {
    return fontWeightBold != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetTextAnchor()
   */
  @Override
  public boolean isSetTextAnchor() {
    return textAnchor != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetVTextAnchor()
   */
  @Override
  public boolean isSetVTextAnchor() {
    return vTextAnchor != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetX()
   */
  @Override
  public boolean isSetX() {
    return x != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetY()
   */
  @Override
  public boolean isSetY() {
    return y != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetZ()
   */
  @Override
  public boolean isSetZ() {
    return z != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteX(java.lang.Boolean)
   */
  @Override
  public void setAbsoluteX(boolean absoluteX) {
    Boolean oldAbsoluteX = this.absoluteX;
    this.absoluteX = absoluteX;
    firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, this.absoluteX);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteY(java.lang.Boolean)
   */
  @Override
  public void setAbsoluteY(boolean absoluteY) {
    Boolean oldAbsoluteY = this.absoluteY;
    this.absoluteY = absoluteY;
    firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, this.absoluteY);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteZ(java.lang.Boolean)
   */
  @Override
  public void setAbsoluteZ(boolean absoluteZ) {
    Boolean oldAbsoluteZ = this.absoluteZ;
    this.absoluteZ = absoluteZ;
    firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, this.absoluteZ);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontFamily(org.sbml.jsbml.ext.render.FontFamily)
   */
  @Override
  public void setFontFamily(FontFamily fontFamily) {
    FontFamily oldFontFamily = this.fontFamily;
    this.fontFamily = fontFamily;
    firePropertyChange(RenderConstants.fontFamily, oldFontFamily, this.fontFamily);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontSize(short)
   */
  @Override
  public void setFontSize(short fontSize) {
    Short oldFontSize = this.fontSize;
    this.fontSize = fontSize;
    firePropertyChange(RenderConstants.fontSize, oldFontSize, this.fontSize);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontStyleItalic(boolean)
   */
  @Override
  public void setFontStyleItalic(boolean fontStyleItalic) {
    Boolean oldFontStyleItalic = this.fontStyleItalic;
    this.fontStyleItalic = fontStyleItalic;
    firePropertyChange(RenderConstants.fontStyleItalic, oldFontStyleItalic, this.fontStyleItalic);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontWeightBold(boolean)
   */
  @Override
  public void setFontWeightBold(boolean fontWeightBold) {
    Boolean oldFontWeightBold = this.fontWeightBold;
    this.fontWeightBold = fontWeightBold;
    firePropertyChange(RenderConstants.fontWeightBold, oldFontWeightBold, this.fontWeightBold);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setTextAnchor(org.sbml.jsbml.ext.render.TextAnchor)
   */
  @Override
  public void setTextAnchor(TextAnchor textAnchor) {
    TextAnchor oldTextAnchor = this.textAnchor;
    this.textAnchor = textAnchor;
    firePropertyChange(RenderConstants.textAnchor, oldTextAnchor, this.textAnchor);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setVTextAnchor(org.sbml.jsbml.ext.render.VTextAnchor)
   */
  @Override
  public void setVTextAnchor(VTextAnchor vTextAnchor) {
    VTextAnchor oldVTextAnchor = this.vTextAnchor;
    this.vTextAnchor = vTextAnchor;
    firePropertyChange(RenderConstants.vTextAnchor, oldVTextAnchor, this.vTextAnchor);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setX(java.lang.Double)
   */
  @Override
  public void setX(double x) {
    Double oldX = this.x;
    this.x = x;
    firePropertyChange(RenderConstants.x, oldX, this.x);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setY(java.lang.Double)
   */
  @Override
  public void setY(double y) {
    Double oldY = this.y;
    this.y = y;
    firePropertyChange(RenderConstants.y, oldY, this.y);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setZ(java.lang.Double)
   */
  @Override
  public void setZ(double z) {
    Double oldZ = this.z;
    this.z = z;
    firePropertyChange(RenderConstants.z, oldZ, this.z);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetAbsoluteX()
   */
  @Override
  public boolean unsetAbsoluteX() {
    if (isSetAbsoluteX()) {
      Boolean oldAbsoluteX = absoluteX;
      absoluteX = null;
      firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, absoluteX);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetAbsoluteY()
   */
  @Override
  public boolean unsetAbsoluteY() {
    if (isSetAbsoluteY()) {
      Boolean oldAbsoluteY = absoluteY;
      absoluteY = null;
      firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, absoluteY);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetAbsoluteZ()
   */
  @Override
  public boolean unsetAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      Boolean oldAbsoluteZ = absoluteZ;
      absoluteZ = null;
      firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, absoluteZ);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontFamily()
   */
  @Override
  public boolean unsetFontFamily() {
    if (isSetFontFamily()) {
      FontFamily oldFontFamily = fontFamily;
      fontFamily = null;
      firePropertyChange(RenderConstants.fontFamily, oldFontFamily, fontFamily);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontSize()
   */
  @Override
  public boolean unsetFontSize() {
    if (isSetFontSize()) {
      Short oldFontSize = fontSize;
      fontSize = null;
      firePropertyChange(RenderConstants.fontSize, oldFontSize, fontSize);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontStyleItalic()
   */
  @Override
  public boolean unsetFontStyleItalic() {
    if (isSetFontStyleItalic()) {
      Boolean oldFontStyleItalic = fontStyleItalic;
      fontStyleItalic = null;
      firePropertyChange(RenderConstants.fontStyleItalic, oldFontStyleItalic, fontStyleItalic);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontWeightBold()
   */
  @Override
  public boolean unsetFontWeightBold() {
    if (isSetFontWeightBold()) {
      Boolean oldFontWeightBold = fontWeightBold;
      fontWeightBold = null;
      firePropertyChange(RenderConstants.fontWeightBold, oldFontWeightBold, fontWeightBold);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetTextAnchor()
   */
  @Override
  public boolean unsetTextAnchor() {
    if (isSetTextAnchor()) {
      TextAnchor oldTextAnchor = textAnchor;
      textAnchor = null;
      firePropertyChange(RenderConstants.textAnchor, oldTextAnchor, textAnchor);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetVTextAnchor()
   */
  @Override
  public boolean unsetVTextAnchor() {
    if (isSetVTextAnchor()) {
      VTextAnchor oldVTextAnchor = vTextAnchor;
      vTextAnchor = null;
      firePropertyChange(RenderConstants.vTextAnchor, oldVTextAnchor, vTextAnchor);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetX()
   */
  @Override
  public boolean unsetX() {
    if (isSetX()) {
      Double oldX = x;
      x = null;
      firePropertyChange(RenderConstants.x, oldX, x);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetY()
   */
  @Override
  public boolean unsetY() {
    if (isSetY()) {
      Double oldY = y;
      y = null;
      firePropertyChange(RenderConstants.y, oldY, y);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetZ()
   */
  @Override
  public boolean unsetZ() {
    if (isSetZ()) {
      Double oldZ = z;
      z = null;
      firePropertyChange(RenderConstants.z, oldZ, z);
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
    if (isSetFontFamily()) {
      attributes.remove(RenderConstants.fontFamily);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fontFamily,
        getFontFamily().toString().toLowerCase());
    }
    if (isSetTextAnchor()) {
      attributes.remove(RenderConstants.textAnchor);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.textAnchor,
        getTextAnchor().toString().toLowerCase());
    }
    if (isSetVTextAnchor()) {
      attributes.remove(RenderConstants.vTextAnchor);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.vTextAnchor,
        getVTextAnchor().toString().toLowerCase());
    }
    if (isSetFontSize()) {
      attributes.remove(RenderConstants.fontSize);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fontSize,
        Short.toString(getFontSize()));
    }
    if (isSetX()) {
      attributes.remove(RenderConstants.x);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.x,
        XMLTools.positioningToString(getX(), isAbsoluteX()));
    }
    if (isSetY()) {
      attributes.remove(RenderConstants.y);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.y,
        XMLTools.positioningToString(getY(), isAbsoluteY()));
    }
    if (isSetZ()) {
      attributes.remove(RenderConstants.z);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.z,
        XMLTools.positioningToString(getZ(), isAbsoluteZ()));
    }
    if (isSetFontStyleItalic()) {
      attributes.remove(RenderConstants.fontStyleItalic);
      attributes.put(RenderConstants.fontStyleItalic,
        XMLTools.fontStyleItalicToString(isFontStyleItalic()));
    }
    if (isSetFontWeightBold()) {
      attributes.remove(RenderConstants.fontWeightBold);
      attributes.put(RenderConstants.fontWeightBold,
        XMLTools.fontWeightBoldToString(isFontWeightBold()));
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
      if (attributeName.equals(RenderConstants.fontFamily)) {
        setFontFamily(FontFamily.valueOf(value.toUpperCase()));
      }
      else if (attributeName.equals(RenderConstants.textAnchor)) {
        setTextAnchor(TextAnchor.valueOf(value.toUpperCase()));
      }
      else if (attributeName.equals(RenderConstants.vTextAnchor)) {
        setVTextAnchor(VTextAnchor.valueOf(value.toUpperCase()));
      }
      else if (attributeName.equals(RenderConstants.fontSize)) {
        setFontSize(Short.valueOf(value));
      }
      else if (attributeName.equals(RenderConstants.x)) {
        setX(XMLTools.parsePosition(value));
        setAbsoluteX(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.y)) {
        setY(XMLTools.parsePosition(value));
        setAbsoluteY(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.z)) {
        setZ(XMLTools.parsePosition(value));
        setAbsoluteZ(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.fontStyleItalic)) {
        setFontStyleItalic(XMLTools.parseFontStyleItalic(value));
      }
      else if (attributeName.equals(RenderConstants.fontWeightBold)) {
        setFontWeightBold(XMLTools.parseFontWeightBold(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
