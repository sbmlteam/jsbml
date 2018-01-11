/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.layout;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.ListOfWithName;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * The {@link Layout} class stores layout information for some or all elements of the
 * {@link org.sbml.jsbml.Model} as well as additional objects that need not be connected to the {@link org.sbml.jsbml.Model}.
 * The {@link Layout} has two attributes: id and name. Additionally, a {@link Dimensions}
 * element specifies the size of the {@link Layout}. The actual layout elements are contained
 * in several lists, namely: a ListOf{@link CompartmentGlyph}s, a ListOf{@link SpeciesGlyph}s,
 * a ListOf{@link ReactionGlyph}s, a ListOf{@link TextGlyph}s, and a
 * ListOfAdditional{@link GraphicalObject}s. Each of these lists can only occur once, and, if present,
 * are not allowed to be empty.
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 */
public class Layout extends AbstractNamedSBase implements UniqueNamedSBase {

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
      setListOfAdditionalGraphicalObjects(layout.getListOfAdditionalGraphicalObjects().clone());
    }
    if (layout.dimensions != null) {
      setDimensions(layout.getDimensions().clone());
    }
    if (layout.listOfCompartmentGlyphs != null) {
      setListOfCompartmentGlyphs(layout.getListOfCompartmentGlyphs().clone());
    }
    if (layout.listOfReactionGlyphs != null) {
      setListOfReactionGlyphs(layout.getListOfReactionGlyphs().clone());
    }
    if (layout.listOfSpeciesGlyphs != null) {
      setListOfSpeciesGlyphs(layout.getListOfSpeciesGlyphs().clone());
    }
    if (layout.listOfTextGlyphs != null) {
      setListOfTextGlyphs(layout.getListOfTextGlyphs().clone());
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
   * Adds an arbitrary additional {@link GraphicalObject}.
   * @param object
   * @libsbml.deprecated same as {@link #addGraphicalObject(GraphicalObject)}
   */
  public void addAdditionalGraphicalObject(GraphicalObject object) {
    addGraphicalObject(object);
  }

  /**
   * Adds a new {@link CompartmentGlyph} to the {@link #listOfCompartmentGlyphs}.
   * <p>The {@link #listOfCompartmentGlyphs} is initialized if necessary.
   *
   * @param compartmentGlyph the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   */
  public boolean addCompartmentGlyph(CompartmentGlyph compartmentGlyph) {
    return getListOfCompartmentGlyphs().add(compartmentGlyph);
  }

  /**
   * Adds a {@link GeneralGlyph} object.
   * 
   * @param object
   */
  public void addGeneralGlyph(GeneralGlyph object) {
    if (object != null) {
      getListOfAdditionalGraphicalObjects().add(object);
    }
  }

  /**
   * Adds an arbitrary additional {@link GraphicalObject}.
   * @param object
   */
  public void addGraphicalObject(GraphicalObject object) {
    if (object != null) {
      getListOfAdditionalGraphicalObjects().add(object);
    }
  }

  /**
   * Adds a {@link ReactionGlyph} to this layout
   * 
   * @param reactionGlyph
   */
  public void addReactionGlyph(ReactionGlyph reactionGlyph) {
    if (reactionGlyph != null) {
      getListOfReactionGlyphs().add(reactionGlyph);
    }
  }

  /**
   * 
   * @param speciesGlyph
   */
  public void addSpeciesGlyph(SpeciesGlyph speciesGlyph) {
    if (speciesGlyph != null) {
      getListOfSpeciesGlyphs().add(speciesGlyph);
    }
  }

  /**
   * 
   * @param TextGlyph
   */
  public void addTextGlyph(TextGlyph TextGlyph) {
    if (TextGlyph != null) {
      getListOfTextGlyphs().add(TextGlyph);
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
  private <T extends AbstractReferenceGlyph> boolean containsGlyph(ListOf<T> listOfGlyphs,
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


  // TODO - add methods to create GraphicalObject
  // TODO - check the libsbml Layout java API to see if we could add some methods

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
   * Creates and adds a new {@link GeneralGlyph}.
   * 
   * @param id
   *        the identifier of the {@link GeneralGlyph} to be created.
   * @return a new {@link GeneralGlyph}.
   * @see #createGeneralGlyph(String, String)
   */
  public GeneralGlyph createGeneralGlyph(String id) {
    return createGeneralGlyph(id, null);
  }

  /**
   * Creates and adds a new {@link GeneralGlyph}.
   * 
   * @param id
   *        the identifier of the {@link GeneralGlyph} to be created.
   * @param reference
   *        the identifier of an element in the model that this GeneralGlyph will represent.
   * @return a new {@link GeneralGlyph}.
   */
  public GeneralGlyph createGeneralGlyph(String id, String reference) {
    GeneralGlyph glyph = new GeneralGlyph(id, getLevel(), getVersion());
    glyph.setReference(reference);
    addGeneralGlyph(glyph);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Layout layout = (Layout) object;
      equals &= layout.isSetDimensions() == isSetDimensions();
      if (equals && isSetDimensions()) {
        equals &= layout.getDimensions().equals(getDimensions());
      }
      equals &= layout.isSetListOfAdditionalGraphicalObjects() == isSetListOfAdditionalGraphicalObjects();
      if (equals && isSetListOfAdditionalGraphicalObjects()) {
        equals &= layout.getListOfAdditionalGraphicalObjects().equals(getListOfAdditionalGraphicalObjects());
      }
      if (equals && isSetListOfCompartmentGlyphs()) {
        equals &= layout.getListOfCompartmentGlyphs().equals(getListOfCompartmentGlyphs());
      }
      if (equals && isSetListOfReactionGlyphs()) {
        equals &= layout.getListOfReactionGlyphs().equals(getListOfReactionGlyphs());
      }
      if (equals && isSetListOfSpeciesGlyphs()) {
        equals &= layout.getListOfSpeciesGlyphs().equals(getListOfSpeciesGlyphs());
      }
      if (equals && isSetListOfTextGlyphs()) {
        equals &= layout.getListOfTextGlyphs().equals(getListOfTextGlyphs());
      }
    }
    return equals;
  }

  /**
   * Searches all instances of {@link CompartmentGlyph} within this {@link Layout} that
   * refer to the {@link Compartment} with the given id.
   * 
   * @param compartmentID
   * @return a {@link List} containing all identified elements. It can be
   *         empty but not {@code null}.
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
    // TODO - use the Model findUniqueNamedSBase method if model is not null ?
    if (isSetListOfReactionGlyphs() && (listOfGlyphs != null) && (!listOfGlyphs.isEmpty())) {
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
   *         empty but not {@code null}.
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
   *         empty but not {@code null}.
   */
  public List<SpeciesGlyph> findSpeciesGlyphs(String speciesID) {
    return findGlyphs(listOfSpeciesGlyphs, speciesID);
  }

  /**
   * Searches within the {@link #listOfTextGlyphs} for {@link TextGlyph}s whose
   * {@link TextGlyph#getOriginOfText()} points to the given id.
   * 
   * @param id
   *        the identifier of the element for which {@link TextGlyph}s are to be
   *        found.
   * @return all {@link TextGlyph}s associated to an element with the given id.
   */
  public List<TextGlyph> findTextGlyphs(String id) {
    return findGlyphs(listOfTextGlyphs, id);
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

  /**
   * 
   * @return
   */
  public int getAdditionalGraphicalObjectCount() {
    return isSetListOfAdditionalGraphicalObjects() ? listOfAdditionalGraphicalObjects.size() : 0;
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
    if (isSetAddGraphicalObjects()) {
      if (index == pos) {
        return getListOfAdditionalGraphicalObjects();
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
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
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
   * Gets an element from the listOfCompartmentGlyphs at the given index.
   *
   * @param i the index of the {@link CompartmentGlyph} element to get.
   * @return an element from the listOfCompartmentGlyphs at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public CompartmentGlyph getCompartmentGlyph(int i) {
    if (!isSetListOfCompartmentGlyphs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfCompartmentGlyphs().get(i);
  }

  /**
   * Gets an element from the listOfCompartmentGlyphs, with the given id.
   *
   * @param id the id of the {@link CompartmentGlyph} element to get.
   * @return an element from the listOfCompartmentGlyphs with the given id or null.
   */
  public CompartmentGlyph getCompartmentGlyph(String id) {
    if (isSetListOfCompartmentGlyphs()) {
      return getListOfCompartmentGlyphs().firstHit(new NameFilter(id));
    }
    return null;
  }

  /**
   * Returns the number of {@link CompartmentGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link CompartmentGlyph}s of this {@link Layout}.
   */
  public int getCompartmentGlyphCount() {
    return isSetListOfCompartmentGlyphs() ? listOfCompartmentGlyphs.size() : 0;
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
      listOfAdditionalGraphicalObjects = new ListOf<GraphicalObject>();
      listOfAdditionalGraphicalObjects.setSBaseListType(ListOf.Type.other);
      listOfAdditionalGraphicalObjects.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfAdditionalGraphicalObjects.setPackageName(null);
      listOfAdditionalGraphicalObjects.setPackageName(LayoutConstants.shortLabel);
      listOfAdditionalGraphicalObjects.setOtherListName(LayoutConstants.listOfAdditionalGraphicalObjects);
      
      registerChild(listOfAdditionalGraphicalObjects);
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
      listOfCompartmentGlyphs.setSBaseListType(ListOf.Type.other);
      listOfCompartmentGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfCompartmentGlyphs.setPackageName(null);
      listOfCompartmentGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfCompartmentGlyphs.setOtherListName(LayoutConstants.listOfCompartmentGlyphs);
      
      registerChild(listOfCompartmentGlyphs);
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
      listOfReactionGlyphs.setSBaseListType(ListOf.Type.other);
      listOfReactionGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfReactionGlyphs.setPackageName(null);
      listOfReactionGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfReactionGlyphs.setOtherListName(LayoutConstants.listOfReactionGlyphs);
      
      registerChild(listOfReactionGlyphs);
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
      listOfSpeciesGlyphs.setSBaseListType(ListOf.Type.other);
      listOfSpeciesGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfSpeciesGlyphs.setPackageName(null);
      listOfSpeciesGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfSpeciesGlyphs.setOtherListName(LayoutConstants.listOfSpeciesGlyphs);
      
      registerChild(listOfSpeciesGlyphs);
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
      listOfTextGlyphs.setSBaseListType(ListOf.Type.other);
      listOfTextGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfTextGlyphs.setPackageName(null);
      listOfTextGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfTextGlyphs.setOtherListName(LayoutConstants.listOfTextGlyphs);
      
      registerChild(listOfTextGlyphs);
    }
    return listOfTextGlyphs;
  }

  /**
   * Returns the number of {@link CompartmentGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link CompartmentGlyph}s of this {@link Layout}.
   * @libsbml.deprecated same as {@link #getCompartmentGlyphCount()}
   */
  public int getNumCompartmentGlyphs() {
    return getCompartmentGlyphCount();
  }

  /**
   * Returns the number of {@link ReactionGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link ReactionGlyph}s of this {@link Layout}.
   * @libsbml.deprecated same as {@link #getReactionGlyphCount()}
   */
  public int getNumReactionGlyphs() {
    return getReactionGlyphCount();
  }

  /**
   * Returns the number of {@link SpeciesGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link SpeciesGlyph}s of this {@link Layout}.
   * @libsbml.deprecated same as {@link #getSpeciesGlyphCount()}
   */
  public int getNumSpeciesGlyphs() {
    return getSpeciesGlyphCount();
  }

  /**
   * Returns the number of {@link TextGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link TextGlyph}s of this {@link Layout}.
   * @libsbml.deprecated same as {@link #getTextGlyphCount()}
   */
  public int getNumTextGlyphs() {
    return getTextGlyphCount();
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
   * Returns the number of {@link ReactionGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link ReactionGlyph}s of this {@link Layout}.
   */
  public int getReactionGlyphCount() {
    return isSetListOfReactionGlyphs() ? listOfReactionGlyphs.size() : 0;
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
   * Returns the number of {@link SpeciesGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link SpeciesGlyph}s of this {@link Layout}.
   */
  public int getSpeciesGlyphCount() {
    return isSetListOfSpeciesGlyphs() ? listOfSpeciesGlyphs.size() : 0;
  }

  /**
   * 
   * @param i
   * @return
   */
  public TextGlyph getTextGlyph(int i) {
    return getListOfTextGlyphs().get(i);
  }

  /**
   * 
   * @param id
   * @return
   */
  public TextGlyph getTextGlyph(String id) {
    if (isSetListOfTextGlyphs()) {
      return listOfTextGlyphs.firstHit(new NameFilter(id));
    }
    return null;
  }

  /**
   * Returns the number of {@link TextGlyph}s of this {@link Layout}.
   * 
   * @return the number of {@link TextGlyph}s of this {@link Layout}.
   */
  public int getTextGlyphCount() {
    return isSetListOfTextGlyphs() ? listOfTextGlyphs.size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 947;
    int hashCode = super.hashCode();
    if (isSetDimensions()) {
      hashCode += prime * getDimensions().hashCode();
    }
    if (isSetListOfAdditionalGraphicalObjects()) {
      hashCode += prime * getListOfAdditionalGraphicalObjects().hashCode();
    }
    if (isSetListOfCompartmentGlyphs()) {
      hashCode += prime * getListOfCompartmentGlyphs().hashCode();
    }
    if (isSetListOfReactionGlyphs()) {
      hashCode += prime * getListOfReactionGlyphs().hashCode();
    }
    if (isSetListOfSpeciesGlyphs()) {
      hashCode += prime * getListOfSpeciesGlyphs().hashCode();
    }
    if (isSetListOfTextGlyphs()) {
      hashCode += prime * getListOfTextGlyphs().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   */
  private void initDefault() {
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
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
   * Removes an element from the listOfCompartmentGlyphs.
   *
   * @param compartmentGlyph the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeCompartmentGlyph(CompartmentGlyph compartmentGlyph) {
    if (isSetListOfCompartmentGlyphs()) {
      return getListOfCompartmentGlyphs().remove(compartmentGlyph);
    }
    return false;
  }

  /**
   * Removes an element from the listOfCompartmentGlyphs at the given index.
   *
   * @param i the index where to remove the {@link CompartmentGlyph}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public CompartmentGlyph removeCompartmentGlyph(int i) {
    if (!isSetListOfCompartmentGlyphs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfCompartmentGlyphs().remove(i);
  }

  /**
   * Removes an element from the listOfCompartmentGlyphs.
   *
   * @param compartmentGlyphId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or {@code null}.
   */
  public CompartmentGlyph removeCompartmentGlyph(String compartmentGlyphId) {
    if (isSetListOfCompartmentGlyphs()) {
      return getListOfCompartmentGlyphs().remove(compartmentGlyphId);
    }
    return null;
  }

  /**
   * @param toBeRemoved
   * @return the given element in case of a successful operation, {@code null}
   *         otherwise.
   */
  public GeneralGlyph removeGeneralGlyph(GeneralGlyph toBeRemoved) {
    if (isSetListOfAdditionalGraphicalObjects()
        && getListOfAdditionalGraphicalObjects().contains(toBeRemoved)
        && toBeRemoved.removeFromParent()) {
      return toBeRemoved;
    }
    return null;
  }

  /**
   * Removes an element from the {@link #listOfAdditionalGraphicalObjects} at
   * the given index.
   * 
   * @param i the index where to remove the {@link GeneralGlyph}.
   * @return the removed object
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index
   * is out of bound (index &lt; 0 || index &gt; list.size).
   * @throws ClassCastException if the additional graphical object at the given
   * index is not an instance of {@link GeneralGlyph}. Note that the element is
   * still removed, but an exception will be thrown. The element will not be
   * re-inserted.
   */
  public GeneralGlyph removeGeneralGlyph(int i) {
    if (!isSetListOfAdditionalGraphicalObjects()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return (GeneralGlyph) getListOfAdditionalGraphicalObjects().remove(i);
  }

  /**
   * 
   * @param id
   * @return
   * @throws ClassCastException if the additional graphical object with the
   * given id is not an instance of {@link GeneralGlyph}. Note that the element
   * is still removed, but an exception will be thrown. The element will not be
   * re-inserted.
   */
  public GeneralGlyph removeGeneralGlyph(String id) {
    if (isSetListOfAdditionalGraphicalObjects()) {
      return (GeneralGlyph) getListOfAdditionalGraphicalObjects().remove(id);
    }
    return null;
  }

  /**
   * Removes an element from the {@link #listOfReactionGlyphs} at the given index.
   *
   * @param i the index where to remove the {@link ReactionGlyph}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public ReactionGlyph removeReactionGlyph(int i) {
    if (!isSetListOfReactionGlyphs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfReactionGlyphs().remove(i);
  }

  /**
   * Removes an element from the listOfReactionGlyphs.
   *
   * @param reactionGlyph the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeReactionGlyph(ReactionGlyph reactionGlyph) {
    if (isSetListOfReactionGlyphs()) {
      return getListOfReactionGlyphs().remove(reactionGlyph);
    }
    return false;
  }

  /**
   * Removes an element from the listOfReactionGlyphs.
   *
   * @param reactionGlyphId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or {@code null}.
   */
  public ReactionGlyph removeReactionGlyph(String reactionGlyphId) {
    if (isSetListOfReactionGlyphs()) {
      return getListOfReactionGlyphs().remove(reactionGlyphId);
    }
    return null;
  }

  /**
   * Removes all singleton {@link SpeciesGlyph}s (no {@link SpeciesReferenceGlyph}
   * in {@link ReactionGlyph}) and also removes their associated {@link TextGlyph}s
   * @return
   */
  public boolean removeSingletons() {
    // Collect species glyphs that are used first
    Set<String> speciesGlyphsToKeep = new HashSet<String>();
    if (isSetListOfReactionGlyphs()) {
      for (ReactionGlyph rxnGlyph : getListOfReactionGlyphs()) {
        for (SpeciesReferenceGlyph srg : rxnGlyph.getListOfSpeciesReferenceGlyphs()) {
          speciesGlyphsToKeep.add(srg.getSpeciesGlyph());
        }
      }

      Iterator<SpeciesGlyph> it = getListOfSpeciesGlyphs().iterator();
      while(it.hasNext()) {
        if (!speciesGlyphsToKeep.contains(it.next().getId())) {
          it.remove();
        }
      }

      Iterator<TextGlyph> it2 = getListOfTextGlyphs().iterator();
      while(it2.hasNext()) {
        GraphicalObject go = it2.next().getGraphicalObjectInstance();
        if ((go instanceof SpeciesGlyph) &&
            (!speciesGlyphsToKeep.contains(go.getId()))) {
          it2.remove();
        }
      }

      return true;

    }

    return false;


  }

  /**
   * Removes an element from the listOfSpeciesGlyphs at the given index.
   *
   * @param i the index where to remove the {@link SpeciesGlyph}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public SpeciesGlyph removeSpeciesGlyph(int i) {
    if (!isSetListOfSpeciesGlyphs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesGlyphs().remove(i);
  }

  /**
   * Removes an element from the listOfSpeciesGlyphs.
   *
   * @param speciesGlyph the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeSpeciesGlyph(SpeciesGlyph speciesGlyph) {
    if (isSetListOfSpeciesGlyphs()) {
      return getListOfSpeciesGlyphs().remove(speciesGlyph);
    }
    return false;
  }

  /**
   * Removes an element from the listOfSpeciesGlyphs.
   *
   * @param speciesGlyphId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or {@code null}.
   */
  public SpeciesGlyph removeSpeciesGlyph(String speciesGlyphId) {
    if (isSetListOfSpeciesGlyphs()) {
      return getListOfSpeciesGlyphs().remove(speciesGlyphId);
    }
    return null;
  }

  /**
   * Removes an element from the listOfTextGlyphs at the given index.
   *
   * @param i the index where to remove the {@link TextGlyph}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public TextGlyph removeTextGlyph(int i) {
    if (!isSetListOfTextGlyphs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfTextGlyphs().remove(i);
  }

  /**
   * Removes an element from the listOfTextGlyphs.
   *
   * @param textGlyphId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or {@code null}.
   */
  public TextGlyph removeTextGlyph(String textGlyphId) {
    if (isSetListOfTextGlyphs()) {
      return getListOfTextGlyphs().remove(textGlyphId);
    }
    return null;
  }

  /**
   * Removes an element from the listOfTextGlyphs.
   *
   * @param textGlyph the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeTextGlyph(TextGlyph textGlyph) {
    if (isSetListOfTextGlyphs()) {
      return getListOfTextGlyphs().remove(textGlyph);
    }
    return false;
  }


  /**
   * This element is optional. If set, this list cannot be empty.
   * 
   * @param addGraphicalObjects
   */
  public void setAddGraphicalObjects(ListOf<GraphicalObject> addGraphicalObjects) {
    setListOfAdditionalGraphicalObjects(addGraphicalObjects);
  }


  /**
   * The dimensions element of type {@link Dimensions} specifies the dimensions of this
   * layout. This element is required. It holds the dimensions of all layout elements
   * (care should be taken when using {@link CubicBezier}s, that the described curve also
   * lies within the given dimensions).
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
   * Sets the {@link #listOfAdditionalGraphicalObjects}.
   * 
   * <p>This element is optional. If set, this list cannot be empty. Most objects for
   * which layout information is to be included in an SBML file have a corresponding
   * object in {@link org.sbml.jsbml.Model}. As there might be cases where the user wants to
   * include object types in the layout that do fall in any of the other categories
   * described below, we include a listOfAdditionalGraphicalObjects in each {@link Layout}
   * object. This list holds an arbitrary number of {@link GraphicalObject} elements.
   * The {@link GraphicalObject} only defines a bounding box in a specific place in the
   * {@link Layout} without giving additional information about its contents.</p>
   * 
   * <p>The listOfAdditionalGraphicalObjects, when present, must contain one or more of the
   * following elements: {@link GraphicalObject} or {@link GeneralGlyph}. When using a
   * {@link GraphicalObject} it is recommended that some form of meta-information is provided.
   * For additional relationships such as SBML events or rules, the {@link GeneralGlyph} can
   * be used.</p>
   * 
   * @param additionalGraphicalObjects
   */
  public void setListOfAdditionalGraphicalObjects(ListOf<GraphicalObject> additionalGraphicalObjects)
  {
    unsetListOfAdditionalGraphicalObjects();

    if (additionalGraphicalObjects != null) {
      listOfAdditionalGraphicalObjects = additionalGraphicalObjects;

      listOfAdditionalGraphicalObjects.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfAdditionalGraphicalObjects.setPackageName(null);
      listOfAdditionalGraphicalObjects.setPackageName(LayoutConstants.shortLabel);
      listOfAdditionalGraphicalObjects.setSBaseListType(ListOf.Type.other);
      listOfAdditionalGraphicalObjects.setOtherListName(LayoutConstants.listOfAdditionalGraphicalObjects);

      registerChild(listOfAdditionalGraphicalObjects);
    }
  }


  /**
   * This element is optional. If set, this list cannot be empty. Also, not all
   * {@link Compartment}s of a {@link org.sbml.jsbml.Model} will need a corresponding {@link CompartmentGlyph}.
   * 
   * @param compartmentGlyphs
   */
  public void setListOfCompartmentGlyphs(ListOf<CompartmentGlyph> compartmentGlyphs) {
    unsetListOfCompartmentGlyphs();
    listOfCompartmentGlyphs = compartmentGlyphs;

    if (listOfCompartmentGlyphs != null) {
      listOfCompartmentGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfCompartmentGlyphs.setPackageName(null);
      listOfCompartmentGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfCompartmentGlyphs.setSBaseListType(ListOf.Type.other);
      listOfCompartmentGlyphs.setOtherListName(LayoutConstants.listOfCompartmentGlyphs);
      registerChild(listOfCompartmentGlyphs);
    }

  }

  /**
   * This element is optional. If set, this list cannot be empty. Also, not all {@link Reaction}s
   * of a {@link org.sbml.jsbml.Model} need a corresponding {@link ReactionGlyph}.
   * 
   * @param reactionGlyphs
   */
  public void setListOfReactionGlyphs(ListOf<ReactionGlyph> reactionGlyphs) {
    unsetListOfReactionGlyphs();
    listOfReactionGlyphs = reactionGlyphs;

    if (listOfReactionGlyphs != null) {
      listOfReactionGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfReactionGlyphs.setPackageName(null);
      listOfReactionGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfReactionGlyphs.setSBaseListType(ListOf.Type.other);
      listOfReactionGlyphs.setOtherListName(LayoutConstants.listOfReactionGlyphs);
      registerChild(listOfReactionGlyphs);
    }
  }


  /**
   * This element is optional. If set, this list cannot be empty. Also, not all {@link Species}
   * of a {@link org.sbml.jsbml.Model} need a corresponding {@link SpeciesGlyph}.
   * 
   * @param speciesGlyphs
   */
  public void setListOfSpeciesGlyphs(ListOf<SpeciesGlyph> speciesGlyphs) {
    unsetListOfSpeciesGlyphs();
    listOfSpeciesGlyphs = speciesGlyphs;

    if (listOfSpeciesGlyphs != null) {
      listOfSpeciesGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfSpeciesGlyphs.setPackageName(null);
      listOfSpeciesGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfSpeciesGlyphs.setSBaseListType(ListOf.Type.other);
      listOfSpeciesGlyphs.setOtherListName(LayoutConstants.listOfSpeciesGlyphs);
      registerChild(listOfSpeciesGlyphs);
    }
  }


  /**
   * This element is optional. If set, this list cannot be empty.
   * 
   * @param textGlyphs
   */
  public void setListOfTextGlyphs(ListOf<TextGlyph> textGlyphs) {
    unsetListOfTextGlyphs();
    listOfTextGlyphs = textGlyphs;

    if (listOfTextGlyphs != null) {
      listOfTextGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfTextGlyphs.setPackageName(null);
      listOfTextGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfTextGlyphs.setSBaseListType(ListOf.Type.other);
      listOfTextGlyphs.setOtherListName(LayoutConstants.listOfTextGlyphs);
      registerChild(listOfTextGlyphs);
    }
  }

  /**
   * Removes the {@link #listOfAdditionalGraphicalObjects} from this {@link org.sbml.jsbml.Model} and notifies
   * all registered instances of {@link org.sbml.jsbml.util.TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfAdditionalGraphicalObjects() {
    if (listOfAdditionalGraphicalObjects != null) {
      ListOf<GraphicalObject> oldListOfAdditionalGraphicalObjects = listOfAdditionalGraphicalObjects;
      listOfAdditionalGraphicalObjects = null;
      oldListOfAdditionalGraphicalObjects.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfCompartmentGlyphs} from this {@link org.sbml.jsbml.Model} and notifies
   * all registered instances of {@link org.sbml.jsbml.util.TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfCompartmentGlyphs() {
    if (listOfCompartmentGlyphs != null) {
      ListOf<CompartmentGlyph> oldListOfCompartmentGlyphs = listOfCompartmentGlyphs;
      listOfCompartmentGlyphs = null;
      oldListOfCompartmentGlyphs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfReactionGlyphs} from this {@link org.sbml.jsbml.Model} and notifies
   * all registered instances of {@link org.sbml.jsbml.util.TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfReactionGlyphs() {
    if (listOfReactionGlyphs != null) {
      ListOf<ReactionGlyph> oldListOfReactionGlyphs = listOfReactionGlyphs;
      listOfReactionGlyphs = null;
      oldListOfReactionGlyphs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfSpeciesGlyphs} from this {@link org.sbml.jsbml.Model} and notifies
   * all registered instances of {@link org.sbml.jsbml.util.TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfSpeciesGlyphs() {
    if (listOfSpeciesGlyphs != null) {
      ListOf<SpeciesGlyph> oldListOfSpeciesGlyphs = listOfSpeciesGlyphs;
      listOfSpeciesGlyphs = null;
      oldListOfSpeciesGlyphs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfTextGlyphs} from this {@link org.sbml.jsbml.Model} and notifies
   * all registered instances of {@link org.sbml.jsbml.util.TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfTextGlyphs() {
    if (listOfTextGlyphs != null) {
      ListOf<TextGlyph> oldListOfTextGlyphs = listOfTextGlyphs;
      listOfTextGlyphs = null;
      oldListOfTextGlyphs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(LayoutConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(LayoutConstants.shortLabel + ":name", getName());
    }

    return attributes;
  }

}
