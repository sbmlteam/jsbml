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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 0.8
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
	private String id;

	/**
	 * 
	 */
	private ListOf<SpeciesReferencesGlyph> listOfSpeciesReferencesGlyph = new ListOf<SpeciesReferencesGlyph>();

	/**
	 * 
	 */
	private String reaction;

	/**
	 * 
	 */
	public ReactionGlyph() {

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
		if (reactionGlyph.isSetId()) {
			this.id = new String(reactionGlyph.getId());
		}
		if (reactionGlyph.isSetListOfSpeciesReferencesGlyph()) {
			this.listOfSpeciesReferencesGlyph = reactionGlyph
					.getListOfSpeciesReferencesGlyph().clone();
		}
		if (reactionGlyph.isSetReaction()) {
			this.reaction = new String(reactionGlyph.getReaction());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	@Override
	public ReactionGlyph clone() {
		return new ReactionGlyph(this);
	}


	/*
	 * (non-Javadoc)
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
		if (isSetCurve()) {
			if (pos == index) {
				return getCurve();
			}
			pos++;
		}
		if (isSetListOfSpeciesReferencesGlyph()) {
			if (pos == index) {
				return getListOfSpeciesReferencesGlyph();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}

	/*
	 * (non-Javadoc)
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
	public ListOf<SpeciesReferencesGlyph> getListOfSpeciesReferencesGlyph() {
		return listOfSpeciesReferencesGlyph;
	}

	/**
	 * 
	 * @return
	 */
	public String getReaction() {
		return reaction;
	}

	/*
	 * (non-Javadoc)
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

	/**
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = false; //super.readAttribute(attributeName, prefix,
		//value);
		System.out.println("readAttribute in ReactionGlyph.class");
		if(!isAttributeRead)
		
			isAttributeRead = true;			
			if(attributeName.equals("reaction"))
			{				
				this.reaction = value;
				System.out.println("ReactionGlyph: reaction: "+reaction);
			}
			else if(attributeName.equals("id"))
			{
				this.id = value;
				System.out.println("ReactionGlyph: id: "+id);
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
		setThisAsParentSBMLObject(this.curve);
	}
	
	/**
	 * 
	 * @param listOfSpeciesReferencesGlyph
	 */
	public void setListOfSpeciesReferencesGlyph(
			ListOf<SpeciesReferencesGlyph> listOfSpeciesReferencesGlyph) {
		unsetListOfSpeciesReferencesGlyph();
		this.listOfSpeciesReferencesGlyph = listOfSpeciesReferencesGlyph;
		setThisAsParentSBMLObject(this.listOfSpeciesReferencesGlyph);
	}
	
	/**
	 * 
	 * @param reaction
	 */
	public void setReaction(String reaction) {
		String oldReaction = this.reaction;
		this.reaction = reaction;
		firePropertyChange(TreeNodeChangeEvent.reaction, oldReaction, this.reaction);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	private void unsetListOfSpeciesReferencesGlyph() {
		if(this.listOfSpeciesReferencesGlyph != null){
			ListOf<SpeciesReferencesGlyph> oldValue = this.listOfSpeciesReferencesGlyph;
			this.listOfSpeciesReferencesGlyph = null;
			oldValue.fireNodeRemovedEvent();
		}
	}

}
