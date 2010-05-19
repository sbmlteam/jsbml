/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;

/**
 * 
 * 
 *
 */
public class Layout extends AbstractNamedSBase {

	/**
	 * 
	 */
	private Dimensions dimensions;
	/**
	 * 
	 */
	private ListOf<CompartmentGlyph> compartmentGlyphs = new ListOf<CompartmentGlyph>();
	/**
	 * 
	 */
	private ListOf<SpeciesGlyph> listOfSpeciesGlyphs = new ListOf<SpeciesGlyph>();
	/**
	 * 
	 */
	private ListOf<ReactionGlyph> reactionGlyphs = new ListOf<ReactionGlyph>();
	/**
	 * 
	 */
	private ListOf<TextGlyph> textGlyphs = new ListOf<TextGlyph>();
	/**
	 * 
	 */
	private ListOf<GraphicalObject> addGraphicalObjects = new ListOf<GraphicalObject>();
	
	/**
	 * 
	 */
	public Layout() {
		super();
	}
	 
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Layout(int level, int version) {
		super(level, version);
	}
	
	/**
	 * @param layout 
	 * 
	 */
	public Layout(Layout layout) {
		super(layout);
		// TODO: not fully implemented!
	}

	/**
	 * 
	 * @param speciesGlyph
	 */
	public void add(SpeciesGlyph speciesGlyph) {
		addSpeciesGlyph(speciesGlyph);
	}

	/**
	 * 
	 * @param speciesGlyph
	 */
	public void addSpeciesGlyph(SpeciesGlyph speciesGlyph) {
		if (speciesGlyph != null) {
			setThisAsParentSBMLObject(speciesGlyph);
			listOfSpeciesGlyphs.add(speciesGlyph);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Layout clone() {
		return new Layout(this);
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<GraphicalObject> getAddGraphicalObjects() {
		return addGraphicalObjects;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public CompartmentGlyph getCompartmentGlyph(int i) {
		if (i >= 0 && i < compartmentGlyphs.size()) {
			return compartmentGlyphs.get(i);
		}
		
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Dimensions getDimensions() {
		return dimensions;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<CompartmentGlyph> getListOfCompartmentGlyphs() {
		return compartmentGlyphs;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<ReactionGlyph> getListOfReactionGlyphs() {
		return reactionGlyphs;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<SpeciesGlyph> getListOfSpeciesGlyphs() {
		return listOfSpeciesGlyphs;
	}
	
	/**
	 * 
	 * @return
	 */
	public ListOf<TextGlyph> getListOfTextGlyphs() {
		return textGlyphs;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SpeciesGlyph getSpeciesGlyph(int i) {
		if (i >= 0 && i < listOfSpeciesGlyphs.size()) {
			return listOfSpeciesGlyphs.get(i);
		}
		
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfSpeciesGlyphs() {
		if (listOfSpeciesGlyphs == null || listOfSpeciesGlyphs.isEmpty()) {
			return false;			
		}
		
		return true;
	}

	/**
	 * 
	 * @param addGraphicalObjects
	 */
	public void setAddGraphicalObjects(ListOf<GraphicalObject> addGraphicalObjects) {
		this.addGraphicalObjects = addGraphicalObjects;
	}

	/**
	 * 
	 * @param dimensions
	 */
	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * 
	 * @param compartmentGlyphs
	 */
	public void setListOfCompartmentGlyphs(ListOf<CompartmentGlyph> compartmentGlyphs) {
		this.compartmentGlyphs = compartmentGlyphs;
	}

	/**
	 * 
	 * @param reactionGlyphs
	 */
	public void setListOfReactionGlyphs(ListOf<ReactionGlyph> reactionGlyphs) {
		this.reactionGlyphs = reactionGlyphs;
	}

	/**
	 * 
	 * @param speciesGlyphs
	 */
	public void setListOfSpeciesGlyphs(ListOf<SpeciesGlyph> speciesGlyphs) {
		if (speciesGlyphs == null) {
			this.listOfSpeciesGlyphs = new ListOf<SpeciesGlyph>();
		} else {
			this.listOfSpeciesGlyphs = speciesGlyphs;
		}
		
		setThisAsParentSBMLObject(this.listOfSpeciesGlyphs);		
	}

	/**
	 * 
	 * @param textGlyphs
	 */
	public void setListOfTextGlyphs(ListOf<TextGlyph> textGlyphs) {
		this.textGlyphs = textGlyphs;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
