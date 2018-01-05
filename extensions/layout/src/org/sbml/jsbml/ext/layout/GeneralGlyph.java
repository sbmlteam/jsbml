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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;

/**
 * The {@link GeneralGlyph} is used to facilitate the representation of elements
 * other than {@link org.sbml.jsbml.Compartment}, {@link org.sbml.jsbml.Species}
 * and {@link org.sbml.jsbml.Reaction} and thus
 * can be used for the display of relationships of {@link org.sbml.jsbml.Rule}
 * or
 * elements defined by other SBML packages. It closely follows the structure of
 * the {@link ReactionGlyph}. {@link GeneralGlyph} is defined to have an
 * optional attribute reference as well as the elements curve,
 * listOfReferenceGlyphs and listOfSubGlyphs.
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class GeneralGlyph extends AbstractReferenceGlyph {

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
  private ListOf<ReferenceGlyph> listOfReferenceGlyphs;

  /**
   * 
   */
  private ListOf<GraphicalObject> listOfSubGlyphs;

  /**
   * 
   */
  public GeneralGlyph() {
    super();
    initDefaults();
  }

  /**
   * Creates a new instance of {@link GeneralGlyph}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneralGlyph(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new instance of {@link GeneralGlyph} cloned from the given element.
   * 
   * @param generalGlyph the {@link GeneralGlyph} to clone.
   */
  public GeneralGlyph(GeneralGlyph generalGlyph) {
    super(generalGlyph);
    if (generalGlyph.isSetCurve()) {
      setCurve(generalGlyph.getCurve().clone());
    }
    if (generalGlyph.isSetListOfReferenceGlyphs()) {
      setListOfReferenceGlyph(generalGlyph
        .getListOfReferenceGlyphs().clone());
    }
    if (generalGlyph.isSetListOfSubGlyphs()) {
      setListOfSubGlyphs(generalGlyph.getListOfSubGlyphs().clone());
    }
  }

  /**
   * Creates a new instance of {@link GeneralGlyph}.
   * 
   * @param id the id
   */
  public GeneralGlyph(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a new instance of {@link GeneralGlyph}.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneralGlyph(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * Initializes the default values using the namespace.
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;
  }

  /**
   * Returns {@code true}, if listOfSubGlyphs contains at least one element.
   *
   * @return {@code true}, if listOfSubGlyphs contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfSubGlyphs() {
    if ((listOfSubGlyphs == null) || listOfSubGlyphs.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the listOfSubGlyphs. Creates it if it is not already existing.
   *
   * @return the listOfSubGlyphs
   */
  public ListOf<GraphicalObject> getListOfSubGlyphs() {
    if (!isSetListOfSubGlyphs()) {
      listOfSubGlyphs = new ListOf<GraphicalObject>(getLevel(), getVersion());
      listOfSubGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfSubGlyphs.setPackageName(null);
      listOfSubGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfSubGlyphs.setSBaseListType(ListOf.Type.other);
      listOfSubGlyphs.setOtherListName(LayoutConstants.listOfSubGlyphs);
      registerChild(listOfSubGlyphs);
    }
    return listOfSubGlyphs;
  }

  /**
   * The listOfSubGlyphs is an optional list that can contain sub-glyphs of the {@link GeneralGlyph}.
   * One example of its use could be a sub-module containing {@link SpeciesGlyph}s and {@link ReactionGlyph}s
   * that are not necessarily part of the enclosing {@link org.sbml.jsbml.Model}. Another example is an {@link org.sbml.jsbml.Event}, visualized
   * with its {@link org.sbml.jsbml.Trigger} and additional {@link GeneralGlyph}s for its {@link org.sbml.jsbml.EventAssignment}. The
   * listOfSubGlyphs consists of {@link GraphicalObject}s or derived classes. Thus, unlike
   * the listOfAdditionalGraphicalObjects (which may only contain {@link GraphicalObject} or {@link GeneralGlyph}s),
   * the listOfSubGlyphs may contain any derived class, such as for example {@link TextGlyph} elements.
   * 
   * Sets the given ListOf&lt;{@link GraphicalObject}&gt;. If listOfSubGlyphs
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfSubGlyphs the list of SubGlyphs to set.
   */
  public void setListOfSubGlyphs(ListOf<GraphicalObject> listOfSubGlyphs)
  {
    unsetListOfSubGlyphs();

    if (listOfSubGlyphs != null)
    {
      this.listOfSubGlyphs = listOfSubGlyphs;
      listOfSubGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfSubGlyphs.setPackageName(null);
      listOfSubGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfSubGlyphs.setSBaseListType(ListOf.Type.other);
      listOfSubGlyphs.setOtherListName(LayoutConstants.listOfSubGlyphs);
      registerChild(this.listOfSubGlyphs);
    }
  }

  /**
   * Returns {@code true}, if listOfSubGlyphs contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfSubGlyphs contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfSubGlyphs() {
    if (isSetListOfSubGlyphs()) {
      ListOf<GraphicalObject> oldSubGlyphs = listOfSubGlyphs;
      listOfSubGlyphs = null;
      oldSubGlyphs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link GraphicalObject} to the listOfSubGlyphs.
   * <p>The listOfSubGlyphs is initialized if necessary.
   *
   * @param subGlyph the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addSubGlyph(GraphicalObject subGlyph) {
    return getListOfSubGlyphs().add(subGlyph);
  }

  /**
   * Removes an element from the listOfSubGlyphs.
   *
   * @param subGlyph the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeSubGlyph(GraphicalObject subGlyph) {
    if (isSetListOfSubGlyphs()) {
      return getListOfSubGlyphs().remove(subGlyph);
    }
    return false;
  }

  /**
   * Removes an element from the {@link #listOfSubGlyphs} at the given index.
   *
   * @param i the index where to remove the glyph.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeSubGlyph(int i) {
    if (!isSetListOfSubGlyphs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfSubGlyphs().remove(i);
  }

  /**
   * Removes an element from the {@link #listOfSubGlyphs} at the given index.
   * 
   * @param i the index where to remove the glyph.
   */
  public void removeGeneralGlyph(int i) {
    removeSubGlyph(i);
  }

  /**
   * Removes an element from the listOfSubGlyphs.
   * 
   * @param subGlyph the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeGeneralGlyph(GraphicalObject subGlyph) {
    return removeSubGlyph(subGlyph);
  }


  /**
   * Creates a new {@link ReferenceGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return the newly created {@link ReferenceGlyph} instance.
   */
  public ReferenceGlyph createReferenceGlyph() {
    return createReferenceGlyph(null);
  }

  /**
   * Creates a new {@link TextGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return the newly created {@link TextGlyph} instance.
   */
  public TextGlyph createTextGlyph() {
    return createTextGlyph(null);
  }

  /**
   * Creates a new {@link TextGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link TextGlyph} instance.
   * @return a new {@link TextGlyph} instance.
   */
  public TextGlyph createTextGlyph(String id) {
    TextGlyph subGlyph = new TextGlyph(id, getLevel(), getVersion());
    addSubGlyph(subGlyph);
    return subGlyph;
  }

  /**
   * Creates a new {@link ReferenceGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return a new {@link ReferenceGlyph} element.
   */
  public ReferenceGlyph createReferenceGlyphForList() {
    return createReferenceGlyphForList(null);
  }

  /**
   * Creates a new {@link ReferenceGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link ReferenceGlyph} instance.
   * @return a new {@link ReferenceGlyph} element.
   */
  public ReferenceGlyph createReferenceGlyphForList(String id) {
    ReferenceGlyph refGlyph = new ReferenceGlyph(id, getLevel(), getVersion());
    addSubGlyph(refGlyph);
    return refGlyph;
  }


  /**
   * Creates a new {@link SpeciesGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return a new {@link SpeciesGlyph} element.
   */
  public SpeciesGlyph createSpeciesGlyph() {
    return createSpeciesGlyph(null);
  }

  /**
   * Creates a new {@link SpeciesGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link SpeciesGlyph} instance.
   * @return a new {@link SpeciesGlyph} element.
   */
  public SpeciesGlyph createSpeciesGlyph(String id) {
    SpeciesGlyph subGlyph = new SpeciesGlyph(id, getLevel(), getVersion());
    addSubGlyph(subGlyph);
    return subGlyph;
  }

  /**
   * Creates a new {@link SpeciesReferenceGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return a new {@link SpeciesReferenceGlyph} element.
   */
  public SpeciesReferenceGlyph createSpeciesReferenceGlyph() {
    return createSpeciesReferenceGlyph(null);
  }

  /**
   * Creates a new {@link SpeciesReferenceGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link SpeciesReferenceGlyph} instance.
   * @return a new {@link SpeciesReferenceGlyph} element.
   */
  public SpeciesReferenceGlyph createSpeciesReferenceGlyph(String id) {
    SpeciesReferenceGlyph subGlyph = new SpeciesReferenceGlyph(id, getLevel(), getVersion());
    addSubGlyph(subGlyph);
    return subGlyph;
  }
  /**
   * Creates a new {@link CompartmentGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return a new {@link CompartmentGlyph} element.
   */
  public CompartmentGlyph createCompartmentGlyph() {
    return createCompartmentGlyph(null);
  }

  /**
   * Creates a new {@link CompartmentGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link CompartmentGlyph} instance.
   * @return a new {@link CompartmentGlyph} element.
   */
  public CompartmentGlyph createCompartmentGlyph(String id) {
    CompartmentGlyph subGlyph = new CompartmentGlyph(id, getLevel(), getVersion());
    addSubGlyph(subGlyph);
    return subGlyph;
  }

  /**
   * Creates a new {@link GeneralGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return a new {@link GeneralGlyph}.
   */
  public GeneralGlyph createGeneralGlyph() {
    return createGeneralGlyph(null);
  }

  /**
   * Creates a new {@link GeneralGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link GeneralGlyph} instance.
   * @return a new {@link GeneralGlyph} element.
   */
  public GeneralGlyph createGeneralGlyph(String id) {
    GeneralGlyph subGlyph = new GeneralGlyph(id, getLevel(), getVersion());
    addSubGlyph(subGlyph);
    return subGlyph;
  }

  /**
   * Creates a new {@link ReactionGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return a new {@link ReactionGlyph} element.
   */
  public ReactionGlyph createReactionGlyph() {
    return createReactionGlyph(null);
  }

  /**
   * Creates a new {@link ReactionGlyph} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link ReactionGlyph} instance.
   * @return a new {@link ReactionGlyph} element.
   */
  public ReactionGlyph createReactionGlyph(String id) {
    ReactionGlyph subGlyph = new ReactionGlyph(id, getLevel(), getVersion());
    addSubGlyph(subGlyph);
    return subGlyph;
  }

  /**
   * Creates a new {@link GraphicalObject} element and adds it to the ListOfSubGlyphs list.
   * 
   * @return a new {@link GraphicalObject} element.
   */
  public GraphicalObject createGraphicalObject() {
    return createGraphicalObject(null);
  }

  /**
   * Creates a new {@link GraphicalObject} element and adds it to the ListOfSubGlyphs list.
   * 
   * @param id the id of the new {@link GraphicalObject} instance.
   * @return a new {@link GraphicalObject} element.
   */
  public GraphicalObject createGraphicalObject(String id) {
    GraphicalObject subGlyph = new GraphicalObject(id, getLevel(), getVersion());
    addSubGlyph(subGlyph);
    return subGlyph;
  }


  /**
   * Appends the specified element to the end of the
   * {@link #listOfReferenceGlyphs}.
   * 
   * @param glyph the glyph to be added.
   * @return {@code true} (as specified by {@link Collection#add})
   * @throws NullPointerException
   *             if the specified element is null and this list does not
   *             permit null elements
   * @throws IllegalArgumentException
   *             if some property of this element prevents it from being added
   *             to this list
   */
  public boolean addReferenceGlyph(ReferenceGlyph glyph) {
    return getListOfReferenceGlyphs().add(glyph);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
   */
  @Override
  public GeneralGlyph clone() {
    return new GeneralGlyph(this);
  }

  /**
   * Creates and adds a new {@link ReferenceGlyph}.
   * 
   * @param id the identifier for the {@link ReferenceGlyph} to be created.
   * @return a new {@link ReferenceGlyph}.
   */
  public ReferenceGlyph createReferenceGlyph(String id) {
    ReferenceGlyph glyph = new ReferenceGlyph(id, getLevel(), getVersion());
    addReferenceGlyph(glyph);
    return glyph;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      GeneralGlyph reactionGlyph = (GeneralGlyph) object;
      equals &= reactionGlyph.isSetCurve() == isSetCurve();
      if (equals && isSetCurve()) {
        equals &= reactionGlyph.getCurve().equals(getCurve());
      }
      equals &= reactionGlyph.isSetListOfSubGlyphs() == isSetListOfSubGlyphs();
      if (equals && isSetListOfSubGlyphs()) {
        equals &= reactionGlyph.getListOfSubGlyphs().equals(getListOfSubGlyphs());
      }
      equals &= reactionGlyph.isSetListOfReferenceGlyphs() == isSetListOfReferenceGlyphs();
      if (equals && isSetListOfReferenceGlyphs()) {
        equals &= reactionGlyph.getListOfReferenceGlyphs().equals(getListOfReferenceGlyphs());
      }
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
    if (isSetListOfSubGlyphs()) {
      if (pos == index) {
        return getListOfSubGlyphs();
      }
      pos++;
    }
    if (isSetListOfReferenceGlyphs()) {
      if (pos == index) {
        return getListOfReferenceGlyphs();
      }
      pos++;
    }
    if (isSetCurve()) {
      if (pos == index) {
        return getCurve();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
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
    if (isSetListOfReferenceGlyphs()) {
      count++;
    }
    if (isSetListOfSubGlyphs()) {
      count++;
    }

    return count;
  }

  /**
   * Gets the {@link Curve}.
   * 
   * @return the {@link Curve}.
   */
  public Curve getCurve() {
    return curve;
  }

  /**
   * Returns the {@link #listOfReferenceGlyphs}.
   * 
   * <p>If the {@link #listOfReferenceGlyphs} has not yet been initialized, this
   * will be done by this method.</p>
   * 
   * @return the {@link #listOfReferenceGlyphs}
   */
  public ListOf<ReferenceGlyph> getListOfReferenceGlyphs() {
    if (!isSetListOfReferenceGlyphs()) {
      listOfReferenceGlyphs = new ListOf<ReferenceGlyph>();
      listOfReferenceGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfReferenceGlyphs.setPackageName(null);
      listOfReferenceGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfReferenceGlyphs.setSBaseListType(ListOf.Type.other);
      listOfReferenceGlyphs.setOtherListName(LayoutConstants.listOfReferenceGlyphs);
      registerChild(listOfReferenceGlyphs);
    }
    return listOfReferenceGlyphs;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 953;
    int hashCode = super.hashCode();
    if (isSetCurve()) {
      hashCode += prime * getCurve().hashCode();
    }
    if (isSetListOfReferenceGlyphs()) {
      hashCode += prime * getListOfReferenceGlyphs().hashCode();
    }
    if (isSetListOfSubGlyphs()) {
      hashCode += prime * getListOfSubGlyphs().hashCode();
    }

    return hashCode;
  }

  /**
   * Returns {@code true} if the {@link Curve} element is not null.
   * 
   * @return {@code true} if the {@link Curve} element is set.
   */
  public boolean isSetCurve() {
    return curve != null;
  }

  /**
   * Returns {@code true} if the {@link #listOfReferenceGlyphs} element is not null.
   * 
   * @return {@code true} if the {@link #listOfReferenceGlyphs} element is set.
   */
  public boolean isSetListOfReferenceGlyphs() {
    return listOfReferenceGlyphs != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {

      isAttributeRead = true;
      if (attributeName.equals(LayoutConstants.reference)) {
        setReference(value);
      } else {
        return false;
      }
    }

    return isAttributeRead;
  }

  /**
   * Sets the {@link Curve}.
   * 
   * <p>This is an optional attribute of type {@link Curve}. If this is defined, then the
   * {@link BoundingBox} is to be ignored.</p>
   * 
   * @param curve the {@link Curve} instance to set.
   */
  public void setCurve(Curve curve) {
    if (this.curve != null) {
      Curve oldValue = this.curve;
      this.curve = null;
      oldValue.fireNodeRemovedEvent();
    }
    this.curve = curve;
    registerChild(this.curve);
  }

  /**
   * Sets the {@link #listOfReferenceGlyphs}.
   * 
   * <p>The listOfReferenceGlyphs is optional, since conceivable the {@link GeneralGlyph} could
   * just contain a number of subglyphs. When present, it must include at least one {@link ReferenceGlyph}.</p>
   * 
   * @param listOfReferenceGlyph the list of {@link ReferenceGlyph} to set.
   */
  public void setListOfReferenceGlyph(ListOf<ReferenceGlyph> listOfReferenceGlyph)
  {
    unsetListOfReferenceGlyph();
    listOfReferenceGlyphs = listOfReferenceGlyph;

    if (listOfReferenceGlyph != null)
    {
      listOfReferenceGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfReferenceGlyphs.setPackageName(null);
      listOfReferenceGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfReferenceGlyph.setSBaseListType(ListOf.Type.other);
      listOfReferenceGlyphs.setOtherListName(LayoutConstants.listOfReferenceGlyphs);
    }

    registerChild(listOfReferenceGlyphs);
  }


  /**
   * Unsets the {@link #listOfReferenceGlyphs}.
   */
  private void unsetListOfReferenceGlyph() {
    if (listOfReferenceGlyphs != null) {
      ListOf<ReferenceGlyph> oldValue = listOfReferenceGlyphs;
      listOfReferenceGlyphs = null;
      oldValue.fireNodeRemovedEvent();
    }
  }

  /**
   * 
   */
  public void unsetReaction() {
    unsetReference();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetReference()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.reference, getReference());
    }

    return attributes;
  }

}
