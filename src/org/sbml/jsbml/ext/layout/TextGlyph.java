/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.SBaseChangeEvent;


/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class TextGlyph extends GraphicalObject {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -2582985174711830815L;
	
	/**
	 * 
	 */
	private GraphicalObject graphicalObject;

	/**
	 * 
	 */
	private String originOfText;


	/**
	 * 
	 */
	private String text;
	/**
	 * 
	 */
	public TextGlyph() {
		super();
	}
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public TextGlyph(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param id
	 */
	public TextGlyph(String id) {
		super(id);
	}
	
	/**
	 * 
	 * @param textGlyph
	 */
	public TextGlyph(TextGlyph textGlyph) {
		super(textGlyph);
		this.graphicalObject = textGlyph.getGraphicalObject().clone();
		this.originOfText = new String(textGlyph.getOriginOfText());
		this.text = new String(textGlyph.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public TextGlyph clone() {
		return new TextGlyph(this);
	}
	
	/*
	 * (non-Javadoc)
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
		}
		return equals;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		int count = super.getChildCount(), pos = 0;
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		if (isSetGraphicalObject()) {
			if (pos == index) {
				return getGraphicalObject();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();
		if (isSetGraphicalObject()) {
			count++;
		}
		return count;
	}
	
	/**
	 * 
	 * @return
	 */
	public GraphicalObject getGraphicalObject() {
		return graphicalObject;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getOriginOfText() {
		return originOfText;
	}
	/**
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 7;
		int hashCode = super.hashCode();
		hashCode += prime * getOriginOfText().hashCode();
		hashCode += prime * getText().hashCode();
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
		return originOfText != null;
	}

	/**
	 * @return
	 */
	public boolean isSetText() {
		return text != null;
	}

	/**
	 * 
	 * @param graphicalObject
	 */
	public void setGraphicalObject(GraphicalObject graphicalObject) {
		this.graphicalObject = graphicalObject;
		setThisAsParentSBMLObject(this.graphicalObject);
	}

	/**
	 * 
	 * @param originOfText
	 */
	public void setOriginOfText(String originOfText) {
		String oldOriginOfText = this.originOfText;
		this.originOfText = originOfText;
		firePropertyChange(SBaseChangeEvent.originOfText, oldOriginOfText, this.originOfText);
	}

	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		String oldText = this.text;
		this.text = text;
		firePropertyChange(SBaseChangeEvent.text, oldText, this.text);
	}
}
