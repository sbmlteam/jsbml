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
 * @author Onur &Oumlzel
 * @author David Vetter
 * @since 1.0
 */
public class Text extends GraphicalPrimitive1D implements FontRenderStyle, Point3D {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -7468181076596795203L;
  /**
   * 
   */
  private String fontFamily;
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
  private HTextAnchor textAnchor;
  /**
   * 
   */
  private VTextAnchor vTextAnchor;
  /**
   * 
   */
  private RelAbsVector x, y, z;
  /**
   * The actual text to be displayed
   */
  private String text;
  
  
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
    
    // copy all attributes
    
    fontFamily = obj.fontFamily;
    fontSize = obj.fontSize;
    fontStyleItalic = obj.fontStyleItalic;
    fontWeightBold = obj.fontWeightBold;
    
    textAnchor = obj.textAnchor;
    vTextAnchor = obj.vTextAnchor;
    
    x = obj.x;
    y = obj.y;
    z = obj.z;
    text = obj.text;
  }

  @Override
  public Text clone() {
    return new Text(this);
  }
  
  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#getFontFamily()
   */
  @Override
  public String getFontFamily() {
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

  /**
   * @return the text to be displayed by this
   */
  public String getText() {
    if(isSetText()) {
      return text;
    }
    throw new PropertyUndefinedError(RenderConstants.text, this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#getTextAnchor()
   */
  @Override
  public HTextAnchor getTextAnchor() {
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
  public RelAbsVector getX() {
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
  public RelAbsVector getY() {
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
  public RelAbsVector getZ() {
    if (isSetZ()) {
      return z;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.z, this);
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

  
  /**
   * Checks whether the text-field has been set. <br>
   * <b>Note:</b> Deviating from libSBML, the empty string "" is a valid, set
   * value of {@link Text#text}
   * 
   * @return Whether the {@link Text#text}-field has been set
   */
  public boolean isSetText() {
    return text != null;
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
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setFontFamily(org.sbml.jsbml.ext.render.FontFamily)
   */
  @Override
  public void setFontFamily(String fontFamily) {
    String oldFontFamily = this.fontFamily;
    this.fontFamily = fontFamily;
    firePropertyChange(RenderConstants.fontFamily, oldFontFamily, this.fontFamily);
  }
 
  
  @Override
  public void setFontFamily(FontFamily fontFamily) {
    String fontFam = fontFamily.toString();
    setFontFamily(fontFam);
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
  
  /**
   * Set the value of the {@link Text#text} field (and fire appropriate property-change event)
   * @param text the new text
   */
  public void setText(String text) {
    String oldText = this.text;
    this.text = text;
    firePropertyChange(RenderConstants.text, oldText, this.text);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#setTextAnchor(org.sbml.jsbml.ext.render.TextAnchor)
   */
  @Override
  public void setTextAnchor(HTextAnchor textAnchor) {
    HTextAnchor oldTextAnchor = this.textAnchor;
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
  public void setX(RelAbsVector x) {
    RelAbsVector oldX = this.x;
    this.x = x;
    firePropertyChange(RenderConstants.x, oldX, this.x);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setY(java.lang.Double)
   */
  @Override
  public void setY(RelAbsVector y) {
    RelAbsVector oldY = this.y;
    this.y = y;
    firePropertyChange(RenderConstants.y, oldY, this.y);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setZ(java.lang.Double)
   */
  @Override
  public void setZ(RelAbsVector z) {
    RelAbsVector oldZ = this.z;
    this.z = z;
    firePropertyChange(RenderConstants.z, oldZ, this.z);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.FontRenderStyle#unsetFontFamily()
   */
  @Override
  public boolean unsetFontFamily() {
    if (isSetFontFamily()) {
      String oldFontFamily = fontFamily;
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

  /**
   * Unsets the {@link Text#text} and fires appropriate change event
   * 
   * @return whether the text could be unset
   */
  public boolean unsetText() {
    if(isSetText()) {
      String oldText = text;
      text = null;
      firePropertyChange(RenderConstants.text, oldText, text);
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
      HTextAnchor oldTextAnchor = textAnchor;
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
      RelAbsVector oldX = x;
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
      RelAbsVector oldY = y;
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
      RelAbsVector oldZ = z;
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
      String fontFamily = getFontFamily(); 
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fontFamily, fontFamily);
    }
    if (isSetTextAnchor()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.textAnchor,
        getTextAnchor().toString().toLowerCase());
    }
    if (isSetVTextAnchor()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.vTextAnchor,
        getVTextAnchor().toString().toLowerCase());
    }
    if (isSetFontSize()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.fontSize,
        Short.toString(getFontSize()));
    }
    if (isSetX()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.x,
        getX().getCoordinate());
    }
    if (isSetY()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.y,
        getY().getCoordinate());
    }
    if (isSetZ()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.z,
        getZ().getCoordinate());
    }
    if (isSetFontStyleItalic()) {
      attributes.put(RenderConstants.fontStyleItalic,
        XMLTools.fontStyleItalicToString(isFontStyleItalic()));
    }
    if (isSetFontWeightBold()) {
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

      if (attributeName.equals(RenderConstants.fontFamily)) {
        try {
          setFontFamily(value);
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.fontFamily
              + " on the 'text' element.");
        }
      }    
      else if (attributeName.equals(RenderConstants.textAnchor)) {
        try {
          setTextAnchor(HTextAnchor.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.textAnchor
              + " on the 'text' element.");
        }
      }
      else if (attributeName.equals(RenderConstants.vTextAnchor)) {
        try {
          setVTextAnchor(VTextAnchor.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.vTextAnchor
              + " on the 'text' element.");
        }
      }
      else if (attributeName.equals(RenderConstants.fontSize)) {
        setFontSize(Short.valueOf(value));
      }
      else if (attributeName.equals(RenderConstants.x)) {
        setX(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.y)) {
        setY(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.z)) {
        setZ(new RelAbsVector(value));
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3163;
    int result = super.hashCode();
    result = prime * result
        + ((fontFamily == null) ? 0 : fontFamily.hashCode());
    result = prime * result + ((fontSize == null) ? 0 : fontSize.hashCode());
    result = prime * result
        + ((fontStyleItalic == null) ? 0 : fontStyleItalic.hashCode());
    result = prime * result
        + ((fontWeightBold == null) ? 0 : fontWeightBold.hashCode());
    result = prime * result
        + ((textAnchor == null) ? 0 : textAnchor.hashCode());
    result = prime * result
        + ((vTextAnchor == null) ? 0 : vTextAnchor.hashCode());
    result = prime * result + ((x == null) ? 0 : x.hashCode());
    result = prime * result + ((y == null) ? 0 : y.hashCode());
    result = prime * result + ((z == null) ? 0 : z.hashCode());
    result = prime * result + ((text == null) ? 0 : text.hashCode());
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
    Text other = (Text) obj;
    
    if (!fontFamily.equals(other.fontFamily)) {
      return false;
    }
    if (fontSize == null) {
      if (other.fontSize != null) {
        return false;
      }
    } else if (!fontSize.equals(other.fontSize)) {
      return false;
    }
    if (fontStyleItalic == null) {
      if (other.fontStyleItalic != null) {
        return false;
      }
    } else if (!fontStyleItalic.equals(other.fontStyleItalic)) {
      return false;
    }
    if (fontWeightBold == null) {
      if (other.fontWeightBold != null) {
        return false;
      }
    } else if (!fontWeightBold.equals(other.fontWeightBold)) {
      return false;
    }
    if (textAnchor != other.textAnchor) {
      return false;
    }
    if (vTextAnchor != other.vTextAnchor) {
      return false;
    }
    if (x == null) {
      if (other.x != null) {
        return false;
      }
    } else if (!x.equals(other.x)) {
      return false;
    }
    if (y == null) {
      if (other.y != null) {
        return false;
      }
    } else if (!y.equals(other.y)) {
      return false;
    }
    if (z == null) {
      if (other.z != null) {
        return false;
      }
    } else if (!z.equals(other.z)) {
      return false;
    }
    if (text == null) {
      if (other.text != null) {
        return false;
      }
    } else if (!text.equals(other.text)) {
      return false;
    }
    return true;
  }

  
  /**
   * @return An informative string about this Text-object. Contains the text
   *         (which is <b>not</b> an XML-attribute)
   */
  @Override
  public String toString() {
    String result = super.toString();
    if(isSetText()) {
      result = result.substring(0, result.length() - 1);
      result += " text=" + getText() + "]";
    }
    
    return result;
  }
}
