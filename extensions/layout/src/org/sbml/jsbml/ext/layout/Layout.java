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

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
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
	private Dimensions dimensions;
	/**
	 * 
	 */
	private ListOf<GraphicalObject> listOfAdditionalGraphicalObjects;
	/**
	 * 
	 */
	private ListOf<CompartmentGlyph> listOfCompartmentGlyphs;
	/**
	 * 
	 */
	private ListOf<ReactionGlyph> listOfReactionGlyphs;
	/**
	 * 
	 */
	private ListOf<SpeciesGlyph> listOfSpeciesGlyphs;
	/**
	 * 
	 */
	private ListOf<TextGlyph> listOfTextGlyphs;
	
	/**
	 * 
	 */
	public Layout() {
		super();
		initDefault();
	}
	 
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Layout(int level, int version) {
		super(level, version);
		initDefault();
	}
	
	 
  /**
	 * @param layout 
	 * 
	 */
	public Layout(Layout layout) {
		super(layout);
    
    if (layout.listOfAdditionalGraphicalObjects != null) {
      listOfAdditionalGraphicalObjects = layout.listOfAdditionalGraphicalObjects.clone();
    }
    if (layout.dimensions != null) {
      dimensions = layout.dimensions.clone();
    }
    if (layout.listOfCompartmentGlyphs != null) {
      listOfCompartmentGlyphs = layout.listOfCompartmentGlyphs.clone();
    }
    if (layout.listOfReactionGlyphs != null) {
      listOfReactionGlyphs = layout.listOfReactionGlyphs.clone();
    }
    if (layout.listOfSpeciesGlyphs != null) {
      listOfSpeciesGlyphs = layout.listOfSpeciesGlyphs.clone();
    }
    if (layout.listOfTextGlyphs != null) {
      listOfTextGlyphs = layout.listOfTextGlyphs.clone();
    }

	}
	
	/**
   * 
   * @param id
   * @param level
   * @param version
   */
  public Layout(String id, int level, int version) {
    super(id, level, version);
    initDefault();
  }

  /**
   * 
   * @param reactionGlyph
   */
  public void add(ReactionGlyph reactionGlyph) {
    addReactionGlyph(reactionGlyph);
  }

	/**
	 * 
	 * @param speciesGlyph
	 */
	public void add(SpeciesGlyph speciesGlyph) {
		addSpeciesGlyph(speciesGlyph);
	}
	
	 /**
	   * Add an arbitrary additional graphical object.
	   * @param object
	   */
	  public void addAdditionalGraphical(GraphicalObject object) {
	    if (object != null) {
	      registerChild(object);
	      listOfAdditionalGraphicalObjects.add(object);
	    }
	  }

  /**
   * 
   * @param compartmentGlyph
   */
  public void addCompartmentGlyph(CompartmentGlyph compartmentGlyph) {
    if (compartmentGlyph != null) {
      registerChild(compartmentGlyph);
      getListOfCompartmentGlyphs().add(compartmentGlyph);
    }
  }
	
  /**
   * 
   * @param reactionGlyph
   */
  public void addReactionGlyph(ReactionGlyph reactionGlyph) {
	  if (reactionGlyph != null) {
		  registerChild(reactionGlyph);
		  listOfReactionGlyphs.add(reactionGlyph);
	  }
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
	
  /**
   * 
   * @param TextGlyph
   */
  public void addTextGlyph(TextGlyph TextGlyph) {
    if (TextGlyph != null) {
      registerChild(TextGlyph);
      listOfTextGlyphs.add(TextGlyph);
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
   * @param compartment
   * @return
   */
  public boolean containsGlyph(Compartment compartment) {
	  return containsGlyph(listOfCompartmentGlyphs, compartment);
  }

  /**
   * 
   * @param listOfGlyphs
   * @param nsb
   * @return
   */
  private <T extends NamedSBaseGlyph> boolean containsGlyph(ListOf<T> listOfGlyphs,
		  NamedSBase nsb) {
	  if ((nsb != null) && (listOfGlyphs != null) && !listOfGlyphs.isEmpty()) {
		  NamedSBaseReferenceFilter filter = new NamedSBaseReferenceFilter(nsb.getId());
		  filter.setFilterForReference(true);
		  return listOfGlyphs.firstHit(filter) != null;
	  }
	  return false;
  }

  /**
   * 
   * @param reaction
   * @return
   */
  public boolean containsGlyph(Reaction reaction) {
	  return containsGlyph(listOfReactionGlyphs, reaction);
  }

  /**
   * 
   * @param species
   * @return
   */
  public boolean containsGlyph(Species species) {
	  return containsGlyph(listOfSpeciesGlyphs, species);
  }

  /**
   * Creates and adds a new {@link CompartmentGlyph}.
   * 
   * @param id
   *        the identifier of the {@link CompartmentGlyph} to be created.
   * @param compartment
   *        {@link Compartment} ID.
   * @return a new {@link CompartmentGlyph}.
   */
  public CompartmentGlyph createCompartmentGlyph(String id, String compartment) {
	  CompartmentGlyph glyph = new CompartmentGlyph(id, getLevel(), getVersion());
	  glyph.setCompartment(compartment);
	  addCompartmentGlyph(glyph);
	  return glyph;
  }

  /**
   * Creates and adds a new {@link CompartmentGlyph}.
   * 
   * @param id
   *        the identifier of the {@link CompartmentGlyph} to be created.
   * @return a new {@link CompartmentGlyph}.
   * @see #createCompartmentGlyph(String, String)
   */
  public CompartmentGlyph createCompartmentGlyph(String id) {
    return createCompartmentGlyph(id, null);
  }

  /**
   * Creates, sets and returns {@link Dimensions} based on the
   * given values.
   * 
   * @param width
   * @param height
   * @param depth
   * @return a new {@link Dimensions} object.
   * @see #createDimensions(String, double, double, double)
   */
  public Dimensions createDimensions(double width, double height, double depth) {
    return createDimensions(null, width, height, depth);
  }

  /**
   * Creates, sets and returns {@link Dimensions} based on the
   * given values.
   * 
   * @param id
   *        the identifier of the {@link Dimensions} to be created.
   * @param width
   * @param height
   * @param depth
   * @return a new {@link Dimensions} object.
   */
  public Dimensions createDimensions(String id, double width,
    double height, double depth) {
    Dimensions d = new Dimensions(id, getLevel(), getVersion());
    d.setWidth(width);
    d.setHeight(height);
    d.setDepth(depth);
    setDimensions(d);
    return d;
  }
  
  /**
   * Creates and adds a new {@link ReactionGlyph}.
   * 
   * @param id
   *        the identifier of the {@link ReactionGlyph} to be created.
   * @param reaction
   *        {@link Reaction} ID.
   * @return a new {@link ReactionGlyph}.
   */
  public ReactionGlyph createReactionGlyph(String id, String reaction) {
	  ReactionGlyph glyph = new ReactionGlyph(id, getLevel(), getVersion());
	  glyph.setReaction(reaction);
	  addReactionGlyph(glyph);
	  return glyph;
  }

  /**
   * Creates and adds a new {@link ReactionGlyph}.
   * 
   * @param id
   *        the identifier of the {@link ReactionGlyph} to be created.
   * @return a new {@link ReactionGlyph}.
   * @see #createReactionGlyph(String, String)
   */
  public ReactionGlyph createReactionGlyph(String id) {
    return createReactionGlyph(id, null);
  }
  
  /**
   * Creates and adds a new {@link SpeciesGlyph}.
   * 
   * @param id
   *        the identifier for the {@link SpeciesGlyph} to be created.
   * @param species
   *        {@link Species} ID.
   * @return a new {@link SpeciesGlyph}.
   */
  public SpeciesGlyph createSpeciesGlyph(String id, String species) {
	  SpeciesGlyph glyph = new SpeciesGlyph(id, getLevel(), getVersion());
	  glyph.setSpecies(species);
	  addSpeciesGlyph(glyph);
	  return glyph;
  }

  /**
   * Creates and adds a new {@link SpeciesGlyph} with the given identifier.
   * 
   * @param id
   *        the identifier for the {@link SpeciesGlyph} to be created.
   * @return a new {@link SpeciesGlyph}.
   * @see #createSpeciesGlyph(String, String)
   */
  public SpeciesGlyph createSpeciesGlyph(String id) {
    return createSpeciesGlyph(id, null);
  }
  
  /**
   * Creates and adds a new {@link TextGlyph}.
   * 
   * @param id
   *        the identifier for the {@link TextGlyph} to be created.
   * @param text
   *        the text for the new glyph
   * @return a new {@link TextGlyph}.
   */
  public TextGlyph createTextGlyph(String id, String text) {
	  TextGlyph glyph = new TextGlyph(id, getLevel(), getVersion());
	  glyph.setText(text);
	  addTextGlyph(glyph);
	  return glyph;
  }

  /**
   * Creates and adds a new {@link TextGlyph}.
   * 
   * @param id
   *        the identifier for the {@link TextGlyph} to be created.
   * @return a new {@link TextGlyph}.
   * @see #createTextGlyph(String, String)
   */
  public TextGlyph createTextGlyph(String id) {
    return createTextGlyph(id, null);
  }

  /**
   * Searches all instances of {@link CompartmentGlyph} within this {@link Layout} that
   * refer to the {@link Compartment} with the given id.
   * 
   * @param compartmentID
   * @return a {@link List} containing all identified elements. It can be
   *         empty but not <code>null</code>.
   */
  public List<CompartmentGlyph> findCompartmentGlyphs(String compartmentID) {
	  return findGlyphs(listOfCompartmentGlyphs, compartmentID);
  }

  /**
   * 
   * @param listOfGlyphs
   * @param id
   * @return
   */
  @SuppressWarnings("unchecked")
  private <T> List<T> findGlyphs(ListOf<? extends T> listOfGlyphs, String id) {
	  if (isSetListOfReactionGlyphs()) {
		  NamedSBaseReferenceFilter filter = new NamedSBaseReferenceFilter(id);
		  filter.setFilterForReference(true);
		  return (List<T>) listOfReactionGlyphs.filter(filter);
	  }
	  return new ArrayList<T>(0);
  }
	
  /**
   * Searches all instances of {@link ReactionGlyph} within this {@link Layout} that
   * refer to the {@link Reaction} with the given id.
   * 
   * @param reactionID
   * @return a {@link List} containing all identified elements. It can be
   *         empty but not <code>null</code>.
   */
  public List<ReactionGlyph> findReactionGlyphs(String reactionID) {
	  return findGlyphs(listOfReactionGlyphs, reactionID);
  }

  /**
   * Searches all instances of {@link SpeciesGlyph} within this {@link Layout} that
   * refer to the {@link Species} with the given id.
   * 
   * @param speciesID
   * @return a {@link List} containing all identified elements. It can be
   *         empty but not <code>null</code>.
   */
  public List<SpeciesGlyph> findSpeciesGlyphs(String speciesID) {
	 return findGlyphs(listOfSpeciesGlyphs, speciesID);
  }
  
  /**
   * 
   * @param i
   * @return
   */
  public GraphicalObject getAdditionalGraphicalObject(int i) {
	  if (isSetListOfAdditionalGraphicalObjects()) {
		  // Do not check the range of indices here! Better throw IndexOutOfBoundsException!
		  return listOfAdditionalGraphicalObjects.get(i);
	  }
	  return null;
  }
	
  /* (non-Javadoc)
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
			  return getListOfAdditionalGraphicalObjects();
		  }
	  }
	  throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
			  index, +((int) Math.min(pos, 0))));
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
	  int count = super.getChildCount();
	  if (isSetDimensions()) {
		  count++;
	  }
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
    return getListOfCompartmentGlyphs().get(i);
  }
	
  /**
   * 
   * @param id
   * @return
   */
  public CompartmentGlyph getCompartmentGlyph(String id) {
	  if (isSetListOfCompartmentGlyphs()) {
		  return getListOfCompartmentGlyphs().firstHit(new NameFilter(id));
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
  public ListOf<GraphicalObject> getListOfAdditionalGraphicalObjects() {
    if (!isSetListOfAdditionalGraphicalObjects()) {
      listOfAdditionalGraphicalObjects = ListOf.newInstance(this, GraphicalObject.class);
      listOfAdditionalGraphicalObjects.addNamespace(LayoutConstant.namespaceURI);
    }
	  return listOfAdditionalGraphicalObjects;
  }
  
  /**
   * 
   * @return
   */
  public ListOf<CompartmentGlyph> getListOfCompartmentGlyphs() {
	  if (!isSetListOfCompartmentGlyphs()) {
		  listOfCompartmentGlyphs = ListOf.newInstance(this, CompartmentGlyph.class);
		  listOfCompartmentGlyphs.addNamespace(LayoutConstant.namespaceURI);
	  }
	  return listOfCompartmentGlyphs;
  }
 

  /**
   * 
   * @return
   */
  public ListOf<ReactionGlyph> getListOfReactionGlyphs() {
	  if (!isSetListOfReactionGlyphs()) {
		  listOfReactionGlyphs = ListOf.newInstance(this, ReactionGlyph.class);
		  listOfReactionGlyphs.addNamespace(LayoutConstant.namespaceURI);
	  }
	  return listOfReactionGlyphs;
  }
	
  /**
   * 
   * @return
   */
  public ListOf<SpeciesGlyph> getListOfSpeciesGlyphs() {
	  if (!isSetListOfSpeciesGlyphs()) {
		  listOfSpeciesGlyphs = ListOf.newInstance(this, SpeciesGlyph.class);
		  listOfSpeciesGlyphs.addNamespace(LayoutConstant.namespaceURI);
	  }
	  return listOfSpeciesGlyphs;
  }

  /**
   * 
   * @return
   */
  public ListOf<TextGlyph> getListOfTextGlyphs() {
	  if (!isSetListOfTextGlyphs()) {
		  listOfTextGlyphs = ListOf.newInstance(this, TextGlyph.class);
		  listOfTextGlyphs.addNamespace(LayoutConstant.namespaceURI);
	  }
	  return listOfTextGlyphs;
  }

  /**
   * 
   * @param i
   * @return
   */
  public ReactionGlyph getReactionGlyph(int i) {
    return getListOfReactionGlyphs().get(i);
  }

  /**
   * 
   * @param id
   * @return
   */
  public ReactionGlyph getReactionGlyph(String id) {
	  if (isSetListOfReactionGlyphs()) {
		  return listOfReactionGlyphs.firstHit(new NameFilter(id));
	  }
	  return null;
  }

  /**
   * 
   * @param i
   * @return
   */
  public SpeciesGlyph getSpeciesGlyph(int i) {
    return getListOfSpeciesGlyphs().get(i);
  }

  /**
   * 
   * @param id
   * @return
   */
  public SpeciesGlyph getSpeciesGlyph(String id) {
	  if (isSetListOfSpeciesGlyphs()) {
		  return listOfSpeciesGlyphs.firstHit(new NameFilter(id));
	  }
	  return null;
  }
  
  /**
   * 
   */
  private void initDefault() {
    addNamespace(LayoutConstant.namespaceURI);
  }
	
  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
	  // See "Including Layout Information in SBML Files" Version 2.2, p. 6
    return true;
  }

  /**
   * @return
   */
  public boolean isSetAddGraphicalObjects() {
	  return (listOfAdditionalGraphicalObjects != null) && (listOfAdditionalGraphicalObjects.size() > 0);
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
  public boolean isSetListOfAdditionalGraphicalObjects() {
	  return (listOfAdditionalGraphicalObjects != null) && (listOfAdditionalGraphicalObjects.size() > 0);
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
   * @param listOfAdditionalGraphicalObjects
   */
  public void setAddGraphicalObjects(ListOf<GraphicalObject> addGraphicalObjects) {
	  if (this.listOfAdditionalGraphicalObjects != null) {
		  this.listOfAdditionalGraphicalObjects.fireNodeRemovedEvent();
	  }
	  this.listOfAdditionalGraphicalObjects = addGraphicalObjects;
	  registerChild(this.listOfAdditionalGraphicalObjects);
  }

  /**
   * 
   * @param dimensions
   */
  public void setDimensions(Dimensions dimensions) {
	  if (this.dimensions != null) {
		  this.dimensions.fireNodeRemovedEvent();
	  }
	  this.dimensions = dimensions;
	  registerChild(this.dimensions);
  }
  
  /**
   * 
   * @param AdditionalGraphicalObjects
   */
  public void setListOfAdditionalGraphicalObjects(ListOf<GraphicalObject> additionalGraphicalObjects) {
	  unsetListOfAdditionalGraphicalObjects();
	  this.listOfAdditionalGraphicalObjects = additionalGraphicalObjects;
	  if (this.listOfAdditionalGraphicalObjects != null) {
		  this.listOfAdditionalGraphicalObjects.setSBaseListType(ListOf.Type.other);
		  registerChild(this.listOfAdditionalGraphicalObjects);
	  }
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
	  registerChild(this.listOfCompartmentGlyphs);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#toString()
   */
  @Override
  public String toString() {
	  return getElementName();
  }
  
  /**
   * Removes the {@link #listOfAdditionalGraphicalObjects} from this {@link Model} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return <code>true</code> if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfAdditionalGraphicalObjects() {
	  if (this.listOfAdditionalGraphicalObjects != null) {
		  ListOf<GraphicalObject> oldListOfAdditionalGraphicalObjects = this.listOfAdditionalGraphicalObjects;
		  this.listOfAdditionalGraphicalObjects = null;
		  oldListOfAdditionalGraphicalObjects.fireNodeRemovedEvent();
		  return true;
	  }
	  return false;
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
