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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
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
			registerChild(speciesGlyph);
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
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
		if (isSetDimensions()) {
			if (index == pos) {
				return getDimensions();
			}
			pos++;
		}
		if (isSetListOfCompartmentGlyphs()) {
			if (index == pos) {
				return getListOfCompartmentGlyphs();
			}
			pos++;
		}
		if (isSetListOfSpeciesGlyphs()) {
			if (index == pos) {
				return getListOfSpeciesGlyphs();
			}
			pos++;
		}
		if (isSetListOfReactionGlyphs()) {
			if (index == pos) {
				return getListOfReactionGlyphs();
			}
			pos++;
		}
		if (isSetListOfTextGlyphs()) {
			if (index == pos) {
				return getListOfTextGlyphs();
			}
			pos++;
		}
		if (isSetAddGraphicalObjects()) {
			if (index == pos) {
				return getAddGraphicalObjects();
			}
		}
		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = 0;
		if (isSetListOfCompartmentGlyphs()) {
			count++;
		}
		if (isSetListOfSpeciesGlyphs()) {
			count++;
		}
		if (isSetListOfReactionGlyphs()) {
			count++;
		}
		if (isSetListOfTextGlyphs()) {
			count++;
		}
		if (isSetAddGraphicalObjects()) {
			count++;
		}
		return count;
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

	/* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    // TODO Auto-generated method stub
    return false;
  }
	
	/**
	 * @return
	 */
	public boolean isSetAddGraphicalObjects() {
		return (addGraphicalObjects != null) && (addGraphicalObjects.size() > 0);
	}

	/**
	 * @return
	 */
	public boolean isSetDimensions() {
		return dimensions != null;
	}

	/**
	 * @return
	 */
	public boolean isSetListOfCompartmentGlyphs() {
		return (listOfCompartmentGlyphs != null) && (listOfCompartmentGlyphs.size() > 0);
	}

	/**
	 * @return
	 */
	public boolean isSetListOfReactionGlyphs() {
		return (listOfReactionGlyphs != null) && (listOfReactionGlyphs.size() > 0);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfSpeciesGlyphs() {
		return (listOfSpeciesGlyphs != null) && (listOfSpeciesGlyphs.size() > 0);
	}

	/**
	 * @return
	 */
	public boolean isSetListOfTextGlyphs() {
		return (listOfTextGlyphs != null) && (listOfTextGlyphs.size() > 0);
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
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		
			return isAttributeRead;
	}

	/**
	 * 
	 * @param addGraphicalObjects
	 */
	public void setAddGraphicalObjects(ListOf<GraphicalObject> addGraphicalObjects) {
		if(this.addGraphicalObjects != null){
			this.addGraphicalObjects.fireNodeRemovedEvent();
		}
		this.addGraphicalObjects = addGraphicalObjects;
		registerChild(this.addGraphicalObjects);
	}
	
	/**
	 * 
	 * @param dimensions
	 */
	public void setDimensions(Dimensions dimensions) {
		if(this.dimensions != null){
			this.dimensions.fireNodeRemovedEvent();
		}
		this.dimensions = dimensions;
		registerChild(this.dimensions);
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
		registerChild(this.listOfSpeciesGlyphs);
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
			registerChild(this.listOfReactionGlyphs);
		}
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
			registerChild(this.listOfSpeciesGlyphs);
		}
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
			registerChild(this.listOfTextGlyphs);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		return getElementName();
	}
	
	/**
	 * Removes the {@link #listOfCompartmentGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfCompartmentGlyphs() {
		if (this.listOfCompartmentGlyphs != null) {
			ListOf<CompartmentGlyph> oldListOfCompartmentGlyphs = this.listOfCompartmentGlyphs;
			this.listOfCompartmentGlyphs = null;
			oldListOfCompartmentGlyphs.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the {@link #listOfReactionGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfReactionGlyphs() {
		if (this.listOfReactionGlyphs != null) {
			ListOf<ReactionGlyph> oldListOfReactionGlyphs = this.listOfReactionGlyphs;
			this.listOfReactionGlyphs = null;
			oldListOfReactionGlyphs.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the {@link #listOfSpeciesGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfSpeciesGlyphs() {
		if (this.listOfSpeciesGlyphs != null) {
			ListOf<SpeciesGlyph> oldListOfSpeciesGlyphs = this.listOfSpeciesGlyphs;
			this.listOfSpeciesGlyphs = null;
			oldListOfSpeciesGlyphs.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

  /**
	 * Removes the {@link #listOfTextGlyphs} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfTextGlyphs() {
		if (this.listOfTextGlyphs != null) {
			ListOf<TextGlyph> oldListOfTextGlyphs = this.listOfTextGlyphs;
			this.listOfTextGlyphs = null;
			oldListOfTextGlyphs.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

}
