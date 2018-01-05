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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;

/**
 * Analogous to how a {@link Reaction} object has to at least have one reactant
 * or product, the {@link ReactionGlyph} has to at least have one
 * {@link SpeciesReferenceGlyph} stored in the
 * {@link #listOfSpeciesReferenceGlyphs}. The {@link ReactionGlyph} inherits
 * from {@link GraphicalObject}.
 * In addition to the attributes inherited from {@link GraphicalObject}, the
 * {@link ReactionGlyph} is described by an attribute reaction, a {@link Curve}
 * element and a {@link #listOfSpeciesReferenceGlyphs} element.
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class ReactionGlyph extends AbstractReferenceGlyph {

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
  private ListOf<SpeciesReferenceGlyph> listOfSpeciesReferenceGlyphs;

  /**
   * 
   */
  public ReactionGlyph() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public ReactionGlyph(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * 
   * @param reactionGlyph
   */
  public ReactionGlyph(ReactionGlyph reactionGlyph) {
    super(reactionGlyph);
    if (reactionGlyph.isSetCurve()) {
      setCurve(reactionGlyph.getCurve().clone());
    }
    if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
      setListOfSpeciesReferenceGlyphs(reactionGlyph
        .getListOfSpeciesReferenceGlyphs().clone());
    }
  }

  /**
   * 
   * @param id
   */
  public ReactionGlyph(String id) {
    super(id);
    initDefaults();
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public ReactionGlyph(String id, int level, int version) {
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
   * Appends the specified element to the end of the
   * {@link #listOfSpeciesReferenceGlyphs}.
   * 
   * @param glyph
   * @return {@code true} (as specified by {@link Collection#add})
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
  @Override
  public ReactionGlyph clone() {
    return new ReactionGlyph(this);
  }

  /**
   * Creates and adds a new {@link Curve} to this ReactionGlyph.
   * 
   * @return a new {@link Curve} instance.
   */
  public Curve createCurve() {
    Curve newcurve = new Curve(getLevel(), getVersion());
    setCurve(newcurve);
    return newcurve;
  }


  /**
   * Creates and adds a new {@link SpeciesReferenceGlyph}
   * 
   * @param id the identifier for the {@link SpeciesReferenceGlyph} to be created.
   * @return a new {@link SpeciesReferenceGlyph}.
   */
  public SpeciesReferenceGlyph createSpeciesReferenceGlyph(String id) {
    SpeciesReferenceGlyph glyph = new SpeciesReferenceGlyph(id, getLevel(), getVersion());
    addSpeciesReferenceGlyph(glyph);
    return glyph;
  }

  /**
   * Creates and adds a new {@link SpeciesReferenceGlyph}
   * 
   * @param id
   *        the identifier for the {@link SpeciesReferenceGlyph} to be created.
   * @param speciesGlyph
   *        corresponding {@link SpeciesGlyph} ID.
   * @return a new {@link SpeciesReferenceGlyph} that points to the given
   *         {@code speciesGlyph}.
   */
  public SpeciesReferenceGlyph createSpeciesReferenceGlyph(String id, String speciesGlyph) {
    SpeciesReferenceGlyph glyph = createSpeciesReferenceGlyph(id);
    glyph.setSpeciesGlyph(speciesGlyph);
    return glyph;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      ReactionGlyph reactionGlyph = (ReactionGlyph) object;
      equals &= reactionGlyph.isSetCurve() == isSetCurve();
      if (equals && isSetCurve()) {
        equals &= reactionGlyph.getCurve().equals(getCurve());
      }
      equals &= reactionGlyph.isSetListOfSpeciesReferenceGlyphs() == isSetListOfSpeciesReferenceGlyphs();
      if (equals && isSetListOfSpeciesReferenceGlyphs()) {
        equals &= reactionGlyph.getListOfSpeciesReferenceGlyphs().equals(getListOfSpeciesReferenceGlyphs());
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
    if (isSetCurve()) {
      if (pos == index) {
        return getCurve();
      }
      pos++;
    }
    if (isSetListOfSpeciesReferenceGlyphs()) {
      if (pos == index) {
        return getListOfSpeciesReferenceGlyphs();
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
    if (isSetListOfSpeciesReferenceGlyphs()) {
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
   * Returns the list of {@link SpeciesReferenceGlyph}s.
   * 
   * <p>If the {@link #listOfSpeciesReferenceGlyphs} has not yet been initialized, this
   * will be done by this method.</p>
   * 
   * @return the {@link #listOfSpeciesReferenceGlyphs}
   */
  public ListOf<SpeciesReferenceGlyph> getListOfSpeciesReferenceGlyphs() {
    if (!isSetListOfSpeciesReferenceGlyphs()) {
      listOfSpeciesReferenceGlyphs = new ListOf<SpeciesReferenceGlyph>();
      listOfSpeciesReferenceGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfSpeciesReferenceGlyphs.setPackageName(null);
      listOfSpeciesReferenceGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfSpeciesReferenceGlyphs.setSBaseListType(ListOf.Type.other);
      listOfSpeciesReferenceGlyphs.setOtherListName(LayoutConstants.listOfSpeciesReferenceGlyphs);

      registerChild(listOfSpeciesReferenceGlyphs);
    }
    return listOfSpeciesReferenceGlyphs;
  }

  /**
   * 
   * @return
   */
  public String getReaction() {
    return getReference();
  }

  /**
   * Note that the return type of this method is {@link NamedSBase} because it
   * could be possible to link some element from other packages to this glyph.
   * 
   * @return
   */
  public NamedSBase getReactionInstance() {
    return getNamedSBaseInstance();
  }

  /**
   * 
   * @param i
   * @return
   */
  public SpeciesReferenceGlyph getSpeciesReferenceGlyph(int i) {
    return getListOfSpeciesReferenceGlyphs().get(i);
  }

  /**
   * 
   * @param srGlyphId
   * @return
   */
  public SpeciesReferenceGlyph getSpeciesReferenceGlyph(String srGlyphId) {
    return getListOfSpeciesReferenceGlyphs().firstHit(new NamedSBaseReferenceFilter(srGlyphId));
  }

  /**
   * 
   * @return
   */
  public int getSpeciesReferenceGlyphCount() {
    return isSetListOfSpeciesReferenceGlyphs() ? listOfSpeciesReferenceGlyphs.size() : 0;
  }

  /**
   * @return
   * @deprecated use {@link #getSpeciesReferenceGlyphCount()}
   */
  @Deprecated
  public int getNumSpeciesReferenceGlyphs() {
    return getSpeciesReferenceGlyphCount();
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
    if (isSetListOfSpeciesReferenceGlyphs()) {
      hashCode += prime * getListOfSpeciesReferenceGlyphs().hashCode();
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
   * Returns {@code true} is the list of {@link SpeciesReferenceGlyph} is set.
   * 
   * @return {@code true} is the list of {@link SpeciesReferenceGlyph} is set.
   */
  public boolean isSetListOfSpeciesReferenceGlyphs() {
    return listOfSpeciesReferenceGlyphs != null;
  }

  /**
   * Returns {@code true} is the list of {@link SpeciesReferenceGlyph} is set.
   * 
   * @return {@code true} is the list of {@link SpeciesReferenceGlyph} is set.
   * @deprecated use {@link #isSetListOfSpeciesReferenceGlyphs()}
   */
  @Deprecated
  public boolean isSetListOfSpeciesReferencesGlyphs() {
    return  isSetListOfSpeciesReferenceGlyphs();
  }

  /**
   * @return
   */
  public boolean isSetReaction() {
    return isSetReference();
  }

  /**
   * 
   * @return
   */
  public boolean isSetReactionInstance() {
    return getReactionInstance() != null;
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
      if (attributeName.equals("reaction")) {
        setReaction(value);
      } else {
        return false;
      }
    }

    return isAttributeRead;
  }

  /**
   * The curve describes the center section of a {@link ReactionGlyph}. The center
   * section is frequently used by tools to separate the point where substrates arcs come together
   * from the point where product arcs split off. The {@link Curve} is optional, and when not present
   * the dimensions of the inherited {@link BoundingBox} describes the center section, by storing its
   * position and dimension.
   * 
   * @param curve
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
   * Sets the list of {@link SpeciesReferenceGlyph}s.
   * 
   * <p>Since the {@link Species} element can have several graphical representations
   * in the layout there must be a way to specify which {@link SpeciesGlyph} should
   * be connected to the {@link ReactionGlyph}. This is done using the
   * listOfSpeciesReferenceGlyphs.</p>
   * 
   * @param listOfSpeciesReferencesGlyph the list of {@link SpeciesReferenceGlyph}s to set.
   */
  public void setListOfSpeciesReferenceGlyphs(ListOf<SpeciesReferenceGlyph> listOfSpeciesReferencesGlyph) {
    unsetListOfSpeciesReferencesGlyph();
    listOfSpeciesReferenceGlyphs = listOfSpeciesReferencesGlyph;

    if (listOfSpeciesReferenceGlyphs != null) {
      listOfSpeciesReferenceGlyphs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'layout'
      listOfSpeciesReferenceGlyphs.setPackageName(null);
      listOfSpeciesReferenceGlyphs.setPackageName(LayoutConstants.shortLabel);
      listOfSpeciesReferenceGlyphs.setSBaseListType(ListOf.Type.other);

      registerChild(listOfSpeciesReferenceGlyphs);
    }
  }


  /**
   * Sets the list of {@link SpeciesReferenceGlyph}s.
   * 
   * @param listOfSpeciesReferencesGlyph the list of {@link SpeciesReferenceGlyph}s to set.
   * @deprecated use {@link #setListOfSpeciesReferenceGlyphs(ListOf)}
   */
  @Deprecated
  public void setListOfSpeciesReferencesGlyph(ListOf<SpeciesReferenceGlyph> listOfSpeciesReferencesGlyph) {
    setListOfSpeciesReferenceGlyphs(listOfSpeciesReferencesGlyph);
  }

  /**
   * See setReaction(String)
   * 
   * @param reaction
   */
  public void setReaction(Reaction reaction) {
    setReaction(reaction.getId());
  }

  /**
   * The reaction attribute is used to specify the id of the corresponding
   * {@link Reaction} in the {@link Model}. This reference is optional.
   * 
   * @param reaction
   */
  public void setReaction(String reaction) {
    setReference(reaction, LayoutConstants.reaction);
  }

  /**
   * Unsets the list of {@link SpeciesReferenceGlyph}s.
   * 
   * @deprecated use {@link #unsetListOfSpeciesReferenceGlyphs()}
   */
  @Deprecated
  public void unsetListOfSpeciesReferencesGlyph() {
    unsetListOfSpeciesReferenceGlyphs();
  }

  /**
   * Unsets the list of {@link SpeciesReferenceGlyph}s.
   * 
   */
  public void unsetListOfSpeciesReferenceGlyphs() {
    if (listOfSpeciesReferenceGlyphs != null) {
      ListOf<SpeciesReferenceGlyph> oldValue = listOfSpeciesReferenceGlyphs;
      listOfSpeciesReferenceGlyphs = null;
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

    if (isSetReaction()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.reaction, getReaction());
    }

    return attributes;
  }

}
