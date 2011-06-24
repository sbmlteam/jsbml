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

import org.sbml.jsbml.ListOf;

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
		// TODO Auto-generated constructor stub
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
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ReactionGlyph) {
			ReactionGlyph r = (ReactionGlyph) o;
			boolean equals = super.equals(r);
			equals &= r.isSetCurve() == isSetCurve();
			if (equals && isSetCurve()) {
				equals &= r.getCurve().equals(getCurve());
			}
			equals &= r.isSetId() == isSetId();
			if (equals && isSetId()) {
				equals &= r.getId().equals(getId());
			}
			equals &= r.isSetListOfSpeciesReferencesGlyph() == isSetListOfSpeciesReferencesGlyph();
			if (equals && isSetListOfSpeciesReferencesGlyph()) {
				equals &= r.getListOfSpeciesReferencesGlyph().equals(getListOfSpeciesReferencesGlyph());
			}
			equals &= r.isSetReaction() == isSetReaction();
			if (equals && isSetReaction()) {
				equals &= r.getReaction().equals(getReaction());
			}
			return equals;
		}
		return false;
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
		this.curve = curve;
	}

	/**
	 * 
	 * @param listOfSpeciesReferencesGlyph
	 */
	public void setListOfSpeciesReferencesGlyph(
			ListOf<SpeciesReferencesGlyph> listOfSpeciesReferencesGlyph) {
		this.listOfSpeciesReferencesGlyph = listOfSpeciesReferencesGlyph;
	}

	/**
	 * 
	 * @param reaction
	 */
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
