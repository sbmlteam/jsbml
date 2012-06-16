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

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
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
public class GradientStop extends AbstractSBase { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7400974339251884133L;


	private Double offset;
  private String stopColor;


  /**
	 * Creates a GradientStop instance with an offset and a color. 
	 * 
	 * @param offset
	 * @param color
	 */
	public GradientStop(Double offset, String stopColor) {
		this.offset = offset;
		this.stopColor = stopColor;
	}

  /**
	 * Creates a GradientStop instance with an offset, color, level, and version. 
	 * 
	 * @param offset
	 * @param color
	 * @param level
	 * @param version
	 */
	public GradientStop(Double offset, String stopColor, int level, int version) {
		super(level, version);
		if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
				Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
			throw new LevelVersionError(getElementName(), level, version);
		}
		this.offset = offset;
		this.stopColor = stopColor;
	}

	/**
	 * Clone constructor
	 */
	public GradientStop(GradientStop obj) {
		super(obj);
		this.offset = obj.offset;
		this.stopColor = obj.stopColor;
	}

	public GradientStop() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
  //@Override
	public GradientStop clone() {
		return new GradientStop(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
  public boolean getAllowsChildren() {
    return false;
  }

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
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

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
  public int getChildCount() {
    return 0;
  }

	/**
	 * @return the value of offset
	 */
	public double getOffset() {
		if (isSetOffset()) {
			return offset;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.offset, this);
	}

	/**
	 * @return the value of stopColor
	 */
	public String getStopColor() {
		if (isSetStopColor()) {
			return stopColor;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.stopColor, this);
	}
	
	/**
	 * @return whether offset is set 
	 */
	public boolean isSetOffset() {
		return this.offset != null;
	}

	/**
	 * @return whether stopColor is set 
	 */
	public boolean isSetStopColor() {
		return this.stopColor != null;
	}

	/**
	 * Set the value of offset
	 */
	public void setOffset(Double offset) {
		Double oldOffset = this.offset;
		this.offset = offset;
		firePropertyChange(RenderConstants.offset, oldOffset, this.offset);
	}

	/**
	 * Set the value of stopColor
	 */
	public void setStopColor(String stopColor) {
		String oldStopColor = this.stopColor;
		this.stopColor = stopColor;
		firePropertyChange(RenderConstants.stopColor, oldStopColor, this.stopColor);
	}

	/**
	 * Unsets the variable offset 
	 * @return <code>true</code>, if offset was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetOffset() {
		if (isSetOffset()) {
			Double oldOffset = this.offset;
			this.offset = null;
			firePropertyChange(RenderConstants.offset, oldOffset, this.offset);
			return true;
		}
		return false;
	}

	/**
	 * Unsets the variable stopColor 
	 * @return <code>true</code>, if stopColor was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetStopColor() {
		if (isSetStopColor()) {
			String oldStopColor = this.stopColor;
			this.stopColor = null;
			firePropertyChange(RenderConstants.stopColor, oldStopColor, this.stopColor);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	//@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
	@Override
  public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();
	  if (isSetOffset()) {
	    attributes.remove(RenderConstants.offset);
	    attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.offset,
	      XMLTools.positioningToString(getOffset(), false));
	  }
	  if (isSetStopColor()) {
	    attributes.remove(RenderConstants.stopColor);
	    attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.stopColor,
	      getStopColor());
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
	    if (attributeName.equals(RenderConstants.offset)) {
	      setOffset(XMLTools.parsePosition(value));
	    }
	    else if (attributeName.equals(RenderConstants.stopColor)) {
	      setStopColor(value);
	    }
	    else {
	      isAttributeRead = false;
	    }
	  }
	  return isAttributeRead;
	}

}
