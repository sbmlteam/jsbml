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

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class Group extends GraphicalPrimitive2D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2302368129571619877L;
	private String id;
	private FontFamily fontFamily;
	private Short fontSize;
	private Boolean fontWeightBold, fontStyleItalic;
	private TextAnchor textAnchor;
	private VTextAnchor vTextAnchor;
	private String startHead;
	private String endHead;
	
	/**
	 * Creates an Group instance 
	 */
	public Group() {
		super();
		initDefaults();
	}

	/**
	 * Creates a Group instance with an id. 
	 * 
	 * @param id
	 */
	public Group(String id) {
		super();
		this.id = id;
		initDefaults();
	}

	/**
	 * Creates a Group instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public Group(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Creates a Group instance with an id, level, and version. 
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Group(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a Group instance with an id, name, level, and version. 
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public Group(String id, String name, int level, int version) {
		super();
		this.id = id;
		if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
				Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
			throw new LevelVersionError(getElementName(), level, version);
		}
		initDefaults();
	}

	/**
	 * Clone constructor
	 */
	public Group(Group obj) {
		super(obj);
		this.id = obj.id;
		this.fontFamily = obj.fontFamily;
		this.fontSize = obj.fontSize;
		this.fontStyleItalic = obj.fontStyleItalic;
		this.fontWeightBold = obj.fontWeightBold;
		this.textAnchor = obj.textAnchor;
		this.vTextAnchor = obj.vTextAnchor;
		this.startHead = obj.startHead;
		this.endHead = obj.endHead;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#clone()
	 */
	@Override
	public Group clone() {
		return new Group(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#initDefaults()
	 */
	@Override
	public void initDefaults() {
		addNamespace(RenderConstants.namespaceURI);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = 0;
		return count;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildAt(int)
	 */
	@Override
	public SBase getChildAt(int childIndex) {
		if (childIndex < 0) {
			throw new IndexOutOfBoundsException(childIndex + " < 0");
		}
		int pos = 0;
		throw new IndexOutOfBoundsException(MessageFormat.format(
				"Index {0,number,integer} >= {1,number,integer}", childIndex,
				+((int) Math.min(pos, 0))));
	}
	
	/**
	 * @return the value of id
	 */
	public String getId() {
		if (isSetId()) {
			return id;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.id, this);
	}

	/**
	 * @return whether id is set 
	 */
	public boolean isSetId() {
		return this.id != null;
	}

	/**
	 * Set the value of id
	 */
	public void setId(String id) {
		String oldId = this.id;
		this.id = id;
		firePropertyChange(RenderConstants.id, oldId, this.id);
	}

	/**
	 * Unsets the variable id 
	 * @return {@code true}, if id was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetId() {
		if (isSetId()) {
			String oldId = this.id;
			this.id = null;
			firePropertyChange(RenderConstants.id, oldId, this.id);
			return true;
		}
		return false;
	}

	/**
	 * @return the value of fontFamily
	 */
	public FontFamily getFontFamily() {
		if (isSetFontFamily()) {
			return fontFamily;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.fontFamily, this);
	}

	/**
	 * @return whether fontFamily is set 
	 */
	public boolean isSetFontFamily() {
		return this.fontFamily != null;
	}

	/**
	 * Set the value of fontFamily
	 */
	public void setFontFamily(FontFamily fontFamily) {
		FontFamily oldFontFamily = this.fontFamily;
		this.fontFamily = fontFamily;
		firePropertyChange(RenderConstants.fontFamily, oldFontFamily, this.fontFamily);
	}

	/**
	 * Unsets the variable fontFamily 
	 * @return {@code true}, if fontFamily was set before, 
	 *         otherwise {@code false}
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

	/**
	 * @return the value of fontSize
	 */
	public Short getFontSize() {
		if (isSetFontSize()) {
			return fontSize;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.fontSize, this);
	}

	/**
	 * @return whether fontSize is set 
	 */
	public boolean isSetFontSize() {
		return this.fontSize != null;
	}

	/**
	 * Set the value of fontSize
	 */
	public void setFontSize(Short fontSize) {
		Short oldFontSize = this.fontSize;
		this.fontSize = fontSize;
		firePropertyChange(RenderConstants.fontSize, oldFontSize, this.fontSize);
	}

	/**
	 * Unsets the variable fontSize 
	 * @return {@code true}, if fontSize was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetFontSize() {
		if (isSetFontSize()) {
			short oldFontSize = this.fontSize;
			this.fontSize = null;
			firePropertyChange(RenderConstants.fontSize, oldFontSize, this.fontSize);
			return true;
		}
		return false;
	}

	/**
	 * @return the value of fontWeightBold
	 */
	public boolean isFontWeightBold() {
		if (isSetFontWeightBold()) {
			return fontWeightBold;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.fontWeightBold, this);
	}

	/**
	 * @return whether fontWeightBold is set 
	 */
	public boolean isSetFontWeightBold() {
		return this.fontWeightBold != null;
	}

	/**
	 * Set the value of fontWeightBold
	 */
	public void setFontWeightBold(Boolean fontWeightBold) {
		Boolean oldFontWeightBold = this.fontWeightBold;
		this.fontWeightBold = fontWeightBold;
		firePropertyChange(RenderConstants.fontWeightBold, oldFontWeightBold, this.fontWeightBold);
	}

	/**
	 * Unsets the variable fontWeightBold 
	 * @return {@code true}, if fontWeightBold was set before, 
	 *         otherwise {@code false}
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

	/**
	 * @return the value of fontStyleItalic
	 */
	public boolean isFontStyleItalic() {
		if (isSetFontStyleItalic()) {
			return fontStyleItalic;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.fontStyleItalic, this);
	}

	/**
	 * @return whether fontStyleItalic is set 
	 */
	public boolean isSetFontStyleItalic() {
		return this.fontStyleItalic != null;
	}

	/**
	 * Set the value of fontStyleItalic
	 */
	public void setFontStyleItalic(Boolean fontStyleItalic) {
		Boolean oldFontStyleItalic = this.fontStyleItalic;
		this.fontStyleItalic = fontStyleItalic;
		firePropertyChange(RenderConstants.fontStyleItalic, oldFontStyleItalic, this.fontStyleItalic);
	}

	/**
	 * Unsets the variable fontStyleItalic 
	 * @return {@code true}, if fontStyleItalic was set before, 
	 *         otherwise {@code false}
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

	/**
	 * @return the value of startHead
	 */
	public String getStartHead() {
		if (isSetStartHead()) {
			return startHead;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.startHead, this);
	}

	/**
	 * @return whether startHead is set 
	 */
	public boolean isSetStartHead() {
		return this.startHead != null;
	}

	/**
	 * Set the value of startHead
	 */
	public void setStartHead(String startHead) {
		String oldStartHead = this.startHead;
		this.startHead = startHead;
		firePropertyChange(RenderConstants.startHead, oldStartHead, this.startHead);
	}

	/**
	 * Unsets the variable startHead 
	 * @return {@code true}, if startHead was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetStartHead() {
		if (isSetStartHead()) {
			String oldStartHead = this.startHead;
			this.startHead = null;
			firePropertyChange(RenderConstants.startHead, oldStartHead, this.startHead);
			return true;
		}
		return false;
	}

	/**
	 * @return the value of endHead
	 */
	public String getEndHead() {
		if (isSetEndHead()) {
			return endHead;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.endHead, this);
	}

	/**
	 * @return whether endHead is set 
	 */
	public boolean isSetEndHead() {
		return this.endHead != null;
	}

	/**
	 * Set the value of endHead
	 */
	public void setEndHead(String endHead) {
		String oldEndHead = this.endHead;
		this.endHead = endHead;
		firePropertyChange(RenderConstants.endHead, oldEndHead, this.endHead);
	}

	/**
	 * Unsets the variable endHead 
	 * @return {@code true}, if endHead was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetEndHead() {
		if (isSetEndHead()) {
			String oldEndHead = this.endHead;
			this.endHead = null;
			firePropertyChange(RenderConstants.endHead, oldEndHead, this.endHead);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the value of textAnchor
	 */
	public TextAnchor getTextAnchor() {
		if (isSetTextAnchor()) {
			return textAnchor;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.textAnchor, this);
	}

	/**
	 * @return whether textAnchor is set 
	 */
	public boolean isSetTextAnchor() {
		return this.textAnchor != null;
	}

	/**
	 * Set the value of textAnchor
	 */
	public void setTextAnchor(TextAnchor textAnchor) {
		TextAnchor oldTextAnchor = this.textAnchor;
		this.textAnchor = textAnchor;
		firePropertyChange(RenderConstants.textAnchor, oldTextAnchor, this.textAnchor);
	}

	/**
	 * Unsets the variable textAnchor 
	 * @return {@code true}, if textAnchor was set before, 
	 *         otherwise {@code false}
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

	/**
	 * @return the value of vTextAnchor
	 */
	public VTextAnchor getVTextAnchor() {
		if (isSetVTextAnchor()) {
			return vTextAnchor;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.vTextAnchor, this);
	}

	/**
	 * @return whether vTextAnchor is set 
	 */
	public boolean isSetVTextAnchor() {
		return this.vTextAnchor != null;
	}

	/**
	 * Set the value of vTextAnchor
	 */
	public void setVTextAnchor(VTextAnchor vTextAnchor) {
		VTextAnchor oldVTextAnchor = this.vTextAnchor;
		this.vTextAnchor = vTextAnchor;
		firePropertyChange(RenderConstants.vTextAnchor, oldVTextAnchor, this.vTextAnchor);
	}

	/**
	 * Unsets the variable vTextAnchor 
	 * @return {@code true}, if vTextAnchor was set before, 
	 *         otherwise {@code false}
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
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetId()) {
      attributes.remove(RenderConstants.id);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.id,
        getId());
    }
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
    if (isSetStartHead()) {
      attributes.remove(RenderConstants.startHead);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.startHead,
        getStartHead());
    }
    if (isSetEndHead()) {
      attributes.remove(RenderConstants.endHead);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.endHead,
        getEndHead());
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
      if (attributeName.equals(RenderConstants.id)) {
        setId(value);
      }
      else if (attributeName.equals(RenderConstants.fontFamily)) {
        setFontFamily(FontFamily.valueOf(value.toUpperCase()));
      }
      else if (attributeName.equals(RenderConstants.fontSize)) {
        setFontSize(Short.valueOf(value));
      }
      else if (attributeName.equals(RenderConstants.fontWeightBold)) {
        setFontWeightBold(XMLTools.parseFontWeightBold(value));
      }
      else if (attributeName.equals(RenderConstants.fontStyleItalic)) {
        setFontStyleItalic(XMLTools.parseFontStyleItalic(value));
      }
      else if (attributeName.equals(RenderConstants.textAnchor)) {
        setTextAnchor(TextAnchor.valueOf(value.toUpperCase()));
      }
      else if (attributeName.equals(RenderConstants.vTextAnchor)) {
        setVTextAnchor(VTextAnchor.valueOf(value.toUpperCase()));
      }
      else if (attributeName.equals(RenderConstants.startHead)) {
        setStartHead(value);
      }
      else if (attributeName.equals(RenderConstants.endHead)) {
        setEndHead(value);
      }     
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
