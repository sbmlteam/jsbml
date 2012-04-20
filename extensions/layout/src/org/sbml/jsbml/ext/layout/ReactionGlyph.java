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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class ReactionGlyph extends GraphicalObject {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 8770691813350594995L;

	/**
	 * 
	 */
	private Curve curve;

	/**
	 * 
	 */
	private ListOf<SpeciesReferenceGlyph> listOfSpeciesReferencesGlyph = new ListOf<SpeciesReferenceGlyph>();

	/**
	 * 
	 */
	private String reaction;

	/**
	 * 
	 */
	public ReactionGlyph() {
	  super();
	  addNamespace(LayoutConstant.namespaceURI);
	  
	  listOfSpeciesReferencesGlyph.addNamespace(LayoutConstant.namespaceURI);
	  listOfSpeciesReferencesGlyph.setSBaseListType(ListOf.Type.other);
	  registerChild(listOfSpeciesReferencesGlyph);
	}

	/**
	 * 
	 * @param reactionGlyph
	 */
	public ReactionGlyph(ReactionGlyph reactionGlyph) {
		super(reactionGlyph);
		if (reactionGlyph.isSetCurve()) {
			this.curve = reactionGlyph.getCurve().clone();
		}
		if (reactionGlyph.isSetListOfSpeciesReferencesGlyph()) {
			this.listOfSpeciesReferencesGlyph = reactionGlyph
					.getListOfSpeciesReferenceGlyphs().clone();
		}
		if (reactionGlyph.isSetReaction()) {
			this.reaction = new String(reactionGlyph.getReaction());
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	@Override
	public ReactionGlyph clone() {
		return new ReactionGlyph(this);
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			ReactionGlyph r = (ReactionGlyph) object;
			equals &= r.isSetId() == isSetId();
			if (equals && isSetId()) {
				equals &= r.getId().equals(getId());
			}
			// This can lead to a cyclic check!
			// equals &= r.isSetReaction() == isSetReaction();
			// if (equals && isSetReaction()) {
			// equals &= r.getReaction().equals(getReaction());
			// }
		}
		return equals;
	}
	
	/* (non-Javadoc)
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
		if (isSetCurve()) {
			if (pos == index) {
				return getCurve();
			}
			pos++;
		}
		if (isSetListOfSpeciesReferencesGlyph()) {
			if (pos == index) {
				return getListOfSpeciesReferenceGlyphs();
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
		if (isSetCurve()) {
			count++;
		}
		if (isSetListOfSpeciesReferencesGlyph()) {
			count++;
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public Curve getCurve() {
		return curve;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<SpeciesReferenceGlyph> getListOfSpeciesReferenceGlyphs() {
		return listOfSpeciesReferencesGlyph;
	}

	/**
	 * 
	 * @return
	 */
	public String getReaction() {
		return reaction;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 971;
		int hashCode = super.hashCode();
		if (isSetId()) {
			hashCode += prime * getId().hashCode();
		}
		return hashCode;
	}

	/**
	 * @return
	 */
	public boolean isSetCurve() {
		return curve != null;
	}

	/**
	 * @return
	 */
	public boolean isSetListOfSpeciesReferencesGlyph() {
		return listOfSpeciesReferencesGlyph != null;
	}

	/**
	 * @return
	 */
	public boolean isSetReaction() {
		return reaction != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if(!isAttributeRead) {
		
			isAttributeRead = true;			
			if(attributeName.equals("reaction"))
			{				
				this.reaction = value;
			} 
			else {
				return false;
			}
		}
		
		return isAttributeRead;
	}

	/**
	 * 
	 * @param curve
	 */
	public void setCurve(Curve curve) {
		if(this.curve != null){
			Curve oldValue = this.curve;
			this.curve = null;
			oldValue.fireNodeRemovedEvent();
		}
		this.curve = curve;
		registerChild(this.curve);
	}
	
	/**
	 * 
	 * @param listOfSpeciesReferencesGlyph
	 */
	public void setListOfSpeciesReferencesGlyph(
			ListOf<SpeciesReferenceGlyph> listOfSpeciesReferencesGlyph) {
		unsetListOfSpeciesReferencesGlyph();
		this.listOfSpeciesReferencesGlyph = listOfSpeciesReferencesGlyph;
		registerChild(this.listOfSpeciesReferencesGlyph);
	}
	
	/**
	 * 
	 * @param reaction
	 */
	public void setReaction(String reaction) {
		String oldReaction = this.reaction;
		this.reaction = reaction;
		firePropertyChange(LayoutConstant.reaction, oldReaction, this.reaction);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * 
	 */
	private void unsetListOfSpeciesReferencesGlyph() {
		if(this.listOfSpeciesReferencesGlyph != null){
			ListOf<SpeciesReferenceGlyph> oldValue = this.listOfSpeciesReferencesGlyph;
			this.listOfSpeciesReferencesGlyph = null;
			oldValue.fireNodeRemovedEvent();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetReaction()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.reaction, reaction);
		}

		return attributes;
	}

}
