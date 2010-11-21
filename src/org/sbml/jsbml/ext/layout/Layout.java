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
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBaseChangedListener;

/**
 * 
 * @author 
 * @author Andreas Dr&auml;ger
 */
public class Layout extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 8866612784809904674L;
	/**
	 * 
	 */
	private ListOf<GraphicalObject> addGraphicalObjects = new ListOf<GraphicalObject>();
	/**
	 * 
	 */
	private Dimensions dimensions;
	/**
	 * 
	 */
	private ListOf<CompartmentGlyph> listOfCompartmentGlyphs = new ListOf<CompartmentGlyph>();
	/**
	 * 
	 */
	private ListOf<ReactionGlyph> listOfReactionGlyphs = new ListOf<ReactionGlyph>();
	/**
	 * 
	 */
	private ListOf<SpeciesGlyph> listOfSpeciesGlyphs = new ListOf<SpeciesGlyph>();
	/**
	 * 
	 */
	private ListOf<TextGlyph> listOfTextGlyphs = new ListOf<TextGlyph>();
	
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
		if (i >= 0 && i < listOfCompartmentGlyphs.size()) {
			return listOfCompartmentGlyphs.get(i);
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
		return listOfCompartmentGlyphs;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<ReactionGlyph> getListOfReactionGlyphs() {
		return listOfReactionGlyphs;
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
		return listOfTextGlyphs;
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
		if ((listOfSpeciesGlyphs == null) || listOfSpeciesGlyphs.isEmpty()) {
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
		unsetListOfCompartmentGlyphs();
		this.listOfCompartmentGlyphs = compartmentGlyphs;
		if ((this.listOfCompartmentGlyphs != null) && (this.listOfCompartmentGlyphs.getSBaseListType() != ListOf.Type.other)) {
			this.listOfCompartmentGlyphs.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(this.listOfSpeciesGlyphs);
	}
	
	/**
	 * 
	 * @param reactionGlyphs
	 */
	public void setListOfReactionGlyphs(ListOf<ReactionGlyph> reactionGlyphs) {
		unsetListOfReactionGlyphs();
		this.listOfReactionGlyphs = reactionGlyphs;
		if (this.listOfReactionGlyphs != null) {
			this.listOfReactionGlyphs.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(this.listOfReactionGlyphs);
	}

	/**
	 * 
	 * @param speciesGlyphs
	 */
	public void setListOfSpeciesGlyphs(ListOf<SpeciesGlyph> speciesGlyphs) {
		unsetListOfSpeciesGlyphs();
		if (speciesGlyphs == null) {
			this.listOfSpeciesGlyphs = new ListOf<SpeciesGlyph>();
		} else {
			this.listOfSpeciesGlyphs = speciesGlyphs;
		}
		if (this.listOfSpeciesGlyphs != null) {
			this.listOfSpeciesGlyphs.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(this.listOfSpeciesGlyphs);
	}
	
	/**
	 * 
	 * @param textGlyphs
	 */
	public void setListOfTextGlyphs(ListOf<TextGlyph> textGlyphs) {
		unsetListOfTextGlyphs();
		this.listOfTextGlyphs = textGlyphs;
		if (this.listOfTextGlyphs != null) {
			this.listOfTextGlyphs.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(this.listOfTextGlyphs);
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
	
	/**
	 * Removes the {@link #listOfCompartmentGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfCompartmentGlyphs() {
		if (this.listOfCompartmentGlyphs != null) {
			ListOf<CompartmentGlyph> oldListOfCompartmentGlyphs = this.listOfCompartmentGlyphs;
			this.listOfCompartmentGlyphs = null;
			oldListOfCompartmentGlyphs.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Removes the {@link #listOfReactionGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfReactionGlyphs() {
		if (this.listOfReactionGlyphs != null) {
			ListOf<ReactionGlyph> oldListOfReactionGlyphs = this.listOfReactionGlyphs;
			this.listOfReactionGlyphs = null;
			oldListOfReactionGlyphs.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the {@link #listOfSpeciesGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfSpeciesGlyphs() {
		if (this.listOfSpeciesGlyphs != null) {
			ListOf<SpeciesGlyph> oldListOfSpeciesGlyphs = this.listOfSpeciesGlyphs;
			this.listOfSpeciesGlyphs = null;
			oldListOfSpeciesGlyphs.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Removes the {@link #listOfTextGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfTextGlyphs() {
		if (this.listOfTextGlyphs != null) {
			ListOf<TextGlyph> oldListOfTextGlyphs = this.listOfTextGlyphs;
			this.listOfTextGlyphs = null;
			oldListOfTextGlyphs.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}
}
