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

import java.util.Collection;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class ReactionGlyph extends NamedSBaseGlyph {

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
	private ListOf<SpeciesReferenceGlyph> listOfSpeciesReferencesGlyph;

	/**
	 * 
	 */
	public ReactionGlyph() {
	  super();
	  addNamespace(LayoutConstant.namespaceURI);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public ReactionGlyph(int level, int version) {
		super(level, version);
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
	}
	
	/**
	 * 
	 * @param id
	 */
	public ReactionGlyph(String id) {
		super(id);
	}
	
	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public ReactionGlyph(String id, int level, int version) {
		super(id, level, version);
	}

	/**
	 * Appends the specified element to the end of the
	 * {@link #listOfSpeciesReferencesGlyph}.
	 * 
	 * @param glyph
	 * @return <code>true</code> (as specified by {@link Collection#add(E)})
	 * @throws NullPointerException
	 *             if the specified element is null and this list does not
	 *             permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of this element prevents it from being added
	 *             to this list
	 */
	public boolean addSpeciesReferenceGlyph(SpeciesReferenceGlyph glyph) {
		return getListOfSpeciesReferenceGlyphs().add(glyph);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	public ReactionGlyph clone() {
		return new ReactionGlyph(this);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesReferenceGlyph createSpeciesReferenceGlyph(String id) {
		SpeciesReferenceGlyph glyph = new SpeciesReferenceGlyph(id, getLevel(), getVersion());
		addSpeciesReferenceGlyph(glyph);
		return glyph;
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
	 * If the {@link #listOfSpeciesReferencesGlyph} has not yet been initialized, this
	 * will be done by this method.
	 * 
	 * @return the {@link #listOfSpeciesReferencesGlyph}
	 */
	public ListOf<SpeciesReferenceGlyph> getListOfSpeciesReferenceGlyphs() {
		if (!isSetListOfSpeciesReferencesGlyph()) {
			listOfSpeciesReferencesGlyph = new ListOf<SpeciesReferenceGlyph>();
			listOfSpeciesReferencesGlyph.addNamespace(LayoutConstant.namespaceURI);
			listOfSpeciesReferencesGlyph.setSBaseListType(ListOf.Type.other);
			registerChild(listOfSpeciesReferencesGlyph);
		}
		return listOfSpeciesReferencesGlyph;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.NamedSBaseGlyph#getNamedSBaseInstance()
	 */
	@Override
	public Reaction getNamedSBaseInstance() {
		return (Reaction) super.getNamedSBaseInstance();
	}

	/**
	 * 
	 * @return
	 */
	public String getReaction() {
		return getNamedSBase();
	}
	
	/**
	 * 
	 * @return
	 */
	public Reaction getReactionInstance() {
		return getNamedSBaseInstance();
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
		return isSetNamedSBase();
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
			if (attributeName.equals("reaction")) {				
				setReaction(value);
			} else {
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
		if(this.curve != null) {
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
	public void setReaction(Reaction reaction) {
		setReaction(reaction.getId());
	}
	
	/**
	 * 
	 * @param reaction
	 */
	public void setReaction(String reaction) {
		setNamedSBase(reaction, LayoutConstant.reaction);
	}
	
	/**
	 * 
	 */
	private void unsetListOfSpeciesReferencesGlyph() {
		if (this.listOfSpeciesReferencesGlyph != null) {
			ListOf<SpeciesReferenceGlyph> oldValue = this.listOfSpeciesReferencesGlyph;
			this.listOfSpeciesReferencesGlyph = null;
			oldValue.fireNodeRemovedEvent();
		}
	}

	/**
	 * 
	 */
	public void unsetReaction() {
		unsetNamedSBase();
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetReaction()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.reaction, getReaction());
		}

		return attributes;
	}

}
