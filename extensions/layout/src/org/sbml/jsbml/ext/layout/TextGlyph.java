/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.ext.layout;

import java.util.Map;

import org.sbml.jsbml.NamedSBase;



/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class TextGlyph extends NamedSBaseGlyph {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -2582985174711830815L;
	
	/**
	 * 
	 */
	private String graphicalObject;

	/**
	 * 
	 */
	private String text;
	
	/**
	 * 
	 */
	public TextGlyph() {
		super();
		addNamespace(LayoutConstants.namespaceURI);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public TextGlyph(int level, int version) {
	  super(level, version);
	  addNamespace(LayoutConstants.namespaceURI);
	}
	
	/**
	 * 
	 * @param id
	 */
	public TextGlyph(String id) {
		super(id);
		addNamespace(LayoutConstants.namespaceURI);
	}
	
	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public TextGlyph(String id, int level, int version) {
	  super(id, level, version);
	  addNamespace(LayoutConstants.namespaceURI);
	}

	/**
	 * 
	 * @param textGlyph
	 */
	public TextGlyph(TextGlyph textGlyph) {
		super(textGlyph);
		if (textGlyph.isSetGraphicalObject()) {
		  this.graphicalObject = new String(textGlyph.getGraphicalObject());
		}
		if (textGlyph.isSetText()) {
		  this.text = new String(textGlyph.getText());
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public TextGlyph clone() {
		return new TextGlyph(this);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			TextGlyph t = (TextGlyph) object;
			equals &= t.isSetOriginOfText() == isSetOriginOfText();
			if (equals && isSetOriginOfText()) {
				equals &= t.getOriginOfText().equals(getOriginOfText());
			}
			equals &= t.isSetText() == isSetText();
			if (equals && isSetText()) {
				equals &= t.getText().equals(getText());
			}
			equals &= t.isSetGraphicalObject() == isSetGraphicalObject();
			if (equals && isSetGraphicalObject()) {
				equals &= t.getGraphicalObject().equals(getGraphicalObject());
			}
		}
		return equals;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getGraphicalObject() {
		return graphicalObject;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getOriginOfText() {
		return getNamedSBase();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 967;
		int hashCode = super.hashCode();
		if (isSetOriginOfText()) {
			hashCode += prime * getOriginOfText().hashCode();
		}
		if (isSetText()) {
			hashCode += prime * getText().hashCode();
		}
		if (isSetGraphicalObject()) {
			hashCode += prime * getGraphicalObject().hashCode();
		}
		
		return hashCode;
	}

	/**
	 * @return
	 */
	public boolean isSetGraphicalObject() {
		return graphicalObject != null;
	}

	/**
	 * @return
	 */
	public boolean isSetOriginOfText() {
		return isSetNamedSBase();
	}

	/**
	 * @return
	 */
	public boolean isSetText() {
		return text != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if(!isAttributeRead)
		{
		
			if(attributeName.equals(LayoutConstants.graphicalObject))
			{	
				setGraphicalObject(value);
			}
			else if(attributeName.equals(LayoutConstants.text))
			{	
				setText(value);
			}
			else if(attributeName.equals(LayoutConstants.originOfText))
			{	
				setOriginOfText(value);
			}
			else
			{
				return false;
			}
		
			return true;
		}
		
		return isAttributeRead;
	}

	/**
	 * 
	 * @param graphicalObject
	 */
	public void setGraphicalObject(String graphicalObject) {
		String oldValue = this.graphicalObject;
		this.graphicalObject = graphicalObject;
		firePropertyChange(LayoutConstants.graphicalObject, oldValue, this.graphicalObject);
	}

	/**
	 * 
	 * @param originOfText
	 */
	public void setOriginOfText(NamedSBase originOfText) {
	  setNamedSBase(originOfText);
	}
	
	/**
	 * 
	 * @param originOfText
	 */
	public void setOriginOfText(String originOfText) {
		setNamedSBase(originOfText);
	}

	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		String oldText = this.text;
		this.text = text;
		firePropertyChange(LayoutConstants.text, oldText, this.text);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetGraphicalObject()) {
			attributes.put(LayoutConstants.shortLabel + ":"
					+ LayoutConstants.graphicalObject, graphicalObject);
		}
		if (isSetText()) {
			attributes.put(LayoutConstants.shortLabel + ":"
					+ LayoutConstants.text, text);
		}
		if (isSetOriginOfText()) {
			attributes.put(LayoutConstants.shortLabel + ":"
					+ LayoutConstants.originOfText, getOriginOfText());
		}

		return attributes;
	}

}
