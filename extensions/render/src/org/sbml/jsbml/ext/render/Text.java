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
public class Text extends GraphicalPrimitive1D implements FontRenderStyle, Point3D {
  /**
   * 
   */
  private static final long serialVersionUID = -7468181076596795203L;
  private Boolean absoluteX, absoluteY, absoluteZ;
  private FontFamily fontFamily;
  private Short fontSize;
  private Boolean fontWeightBold, fontStyleItalic;
  private TextAnchor textAnchor;
  private VTextAnchor vTextAnchor;
  private Double x, y, z;


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#getFontFamily()
   */
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
  public boolean isSetAbsoluteX() {
    return this.absoluteX != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetAbsoluteY()
   */
  public boolean isSetAbsoluteY() {
    return this.absoluteY != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetAbsoluteZ()
   */
  public boolean isSetAbsoluteZ() {
    return this.absoluteZ != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontFamily()
   */
  public boolean isSetFontFamily() {
    return this.fontFamily != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontSize()
   */
  public boolean isSetFontSize() {
    return this.fontSize != null;
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontStyleItalic()
   */
  public boolean isSetFontStyleItalic() {
    return this.fontStyleItalic != null;
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetFontWeightBold()
   */
  public boolean isSetFontWeightBold() {
    return this.fontWeightBold != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetTextAnchor()
   */
  public boolean isSetTextAnchor() {
    return this.textAnchor != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#isSetVTextAnchor()
   */
  public boolean isSetVTextAnchor() {
    return this.vTextAnchor != null;
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetX()
   */
  public boolean isSetX() {
    return this.x != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetY()
   */
  public boolean isSetY() {
    return this.y != null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isSetZ()
   */
  public boolean isSetZ() {
    return this.z != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteX(java.lang.Boolean)
   */
  public void setAbsoluteX(Boolean absoluteX) {
    Boolean oldAbsoluteX = this.absoluteX;
    this.absoluteX = absoluteX;
    firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, this.absoluteX);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteY(java.lang.Boolean)
   */
  public void setAbsoluteY(Boolean absoluteY) {
    Boolean oldAbsoluteY = this.absoluteY;
    this.absoluteY = absoluteY;
    firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, this.absoluteY);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteZ(java.lang.Boolean)
   */
  public void setAbsoluteZ(Boolean absoluteZ) {
    Boolean oldAbsoluteZ = this.absoluteZ;
    this.absoluteZ = absoluteZ;
    firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, this.absoluteZ);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontFamily(org.sbml.jsbml.ext.render.FontFamily)
   */
  public void setFontFamily(FontFamily fontFamily) {
    FontFamily oldFontFamily = this.fontFamily;
    this.fontFamily = fontFamily;
    firePropertyChange(RenderConstants.fontFamily, oldFontFamily, this.fontFamily);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontSize(short)
   */
  public void setFontSize(short fontSize) {
    Short oldFontSize = this.fontSize;
    this.fontSize = fontSize;
    firePropertyChange(RenderConstants.fontSize, oldFontSize, this.fontSize);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontStyleItalic(boolean)
   */
  public void setFontStyleItalic(boolean fontStyleItalic) {
    Boolean oldFontStyleItalic = this.fontStyleItalic;
    this.fontStyleItalic = fontStyleItalic;
    firePropertyChange(RenderConstants.fontStyleItalic, oldFontStyleItalic, this.fontStyleItalic);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontWeightBold(boolean)
   */
  public void setFontWeightBold(boolean fontWeightBold) {
    Boolean oldFontWeightBold = this.fontWeightBold;
    this.fontWeightBold = fontWeightBold;
    firePropertyChange(RenderConstants.fontWeightBold, oldFontWeightBold, this.fontWeightBold);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setTextAnchor(org.sbml.jsbml.ext.render.TextAnchor)
   */
  public void setTextAnchor(TextAnchor textAnchor) {
    TextAnchor oldTextAnchor = this.textAnchor;
    this.textAnchor = textAnchor;
    firePropertyChange(RenderConstants.textAnchor, oldTextAnchor, this.textAnchor);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setVTextAnchor(org.sbml.jsbml.ext.render.VTextAnchor)
   */
  public void setVTextAnchor(VTextAnchor vTextAnchor) {
    VTextAnchor oldVTextAnchor = this.vTextAnchor;
    this.vTextAnchor = vTextAnchor;
    firePropertyChange(RenderConstants.vTextAnchor, oldVTextAnchor, this.vTextAnchor);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setX(java.lang.Double)
   */
  public void setX(Double x) {
    Double oldX = this.x;
    this.x = x;
    firePropertyChange(RenderConstants.x, oldX, this.x);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setY(java.lang.Double)
   */
  public void setY(Double y) {
    Double oldY = this.y;
    this.y = y;
    firePropertyChange(RenderConstants.y, oldY, this.y);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setZ(java.lang.Double)
   */
  public void setZ(Double z) {
    Double oldZ = this.z;
    this.z = z;
    firePropertyChange(RenderConstants.z, oldZ, this.z);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetAbsoluteX()
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
   * @see org.sbml.jsbml.ext.render.Point3D#unsetAbsoluteY()
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
   * @see org.sbml.jsbml.ext.render.Point3D#unsetAbsoluteZ()
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
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontFamily()
   */
  public boolean unsetFontFamily() {
    if (isSetFontFamily()) {
      FontFamily oldFontFamily = this.fontFamily;
      this.fontFamily = null;
      firePropertyChange(RenderConstants.fontFamily, oldFontFamily, this.fontFamily);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontSize()
   */
  public boolean unsetFontSize() {
    if (isSetFontSize()) {
      Short oldFontSize = this.fontSize;
      this.fontSize = null;
      firePropertyChange(RenderConstants.fontSize, oldFontSize, this.fontSize);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontStyleItalic()
   */
  public boolean unsetFontStyleItalic() {
    if (isSetFontStyleItalic()) {
      Boolean oldFontStyleItalic = this.fontStyleItalic;
      this.fontStyleItalic = null;
      firePropertyChange(RenderConstants.fontStyleItalic, oldFontStyleItalic, this.fontStyleItalic);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontWeightBold()
   */
  public boolean unsetFontWeightBold() {
    if (isSetFontWeightBold()) {
      Boolean oldFontWeightBold = this.fontWeightBold;
      this.fontWeightBold = null;
      firePropertyChange(RenderConstants.fontWeightBold, oldFontWeightBold, this.fontWeightBold);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetTextAnchor()
   */
  public boolean unsetTextAnchor() {
    if (isSetTextAnchor()) {
      TextAnchor oldTextAnchor = this.textAnchor;
      this.textAnchor = null;
      firePropertyChange(RenderConstants.textAnchor, oldTextAnchor, this.textAnchor);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetVTextAnchor()
   */
  public boolean unsetVTextAnchor() {
    if (isSetVTextAnchor()) {
      VTextAnchor oldVTextAnchor = this.vTextAnchor;
      this.vTextAnchor = null;
      firePropertyChange(RenderConstants.vTextAnchor, oldVTextAnchor, this.vTextAnchor);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#unsetX()
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
   * @see org.sbml.jsbml.ext.render.Point3D#unsetY()
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
   * @see org.sbml.jsbml.ext.render.Point3D#unsetZ()
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
