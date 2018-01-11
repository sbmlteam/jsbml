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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Abstract super class for all kinds of glyphs that graphically represent an
 * instance of {@link NamedSBase}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public abstract class AbstractReferenceGlyph extends GraphicalObject {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3542638384361924654L;

  /**
   * The identifier of the {@link NamedSBase} represented by this
   * {@link GraphicalObject}.
   */
  private String reference;

  /**
   * Creates a new instance of {@link AbstractReferenceGlyph}.
   */
  public AbstractReferenceGlyph() {
    super();
  }

  /**
   * Creates a new instance of {@link AbstractReferenceGlyph}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractReferenceGlyph(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new instance of {@link AbstractReferenceGlyph} from the given glyph.
   * 
   * @param glyph the glyph to clone.
   */
  public AbstractReferenceGlyph(AbstractReferenceGlyph glyph) {
    super(glyph);
    if (glyph.isSetReference()) {
      setReference(glyph.getReference());
    }
  }

  /**
   * Creates a new instance of {@link AbstractReferenceGlyph}.
   * 
   * @param id the id
   */
  public AbstractReferenceGlyph(String id) {
    super(id);
  }

  /**
   * Creates a new instance of {@link AbstractReferenceGlyph}.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractReferenceGlyph(String id, int level, int version) {
    super(id, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
   */
  @Override
  public abstract AbstractReferenceGlyph clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      AbstractReferenceGlyph nsg = (AbstractReferenceGlyph) object;
      equal &= isSetReference() == nsg.isSetReference();
      if (equal && isSetReference()) {
        equal &= getReference().equals(nsg.getReference());
      }
    }
    return equal;
  }

  /**
   * Gets the reference.
   * 
   * <p>This attribute is used to store all the references from the different glyphs, meaning the 'compartment' for {@link CompartmentGlyph},
   * the 'species' for {@link SpeciesGlyph}, the 'reaction' for {@link ReactionGlyph} amd the 'reference' for {@link GeneralGlyph}.</p>
   * 
   * <p>The optional reference attribute of type SIdRef that can be used to specify the id of the corresponding element
   * in the model that is represented.<br/>
   * If the reference attribute is used together with the metaidRef, they need to refer to the same object in the Model.</p>

   * @return the reference.
   */
  public String getReference() {
    return isSetReference() ? reference : "";
  }

  /**
   * Gets the {@link NamedSBase} instance corresponding to the reference id.
   * 
   * <p>Careful if you want to support SBML L3V2, you should use {@link #getSBaseInstance()}.
   * This method might be deprecated in future JSBML releases.</p>
   * 
   * @return the {@link NamedSBase} instance corresponding to the reference id.
   */
  public NamedSBase getReferenceInstance() {
    if (isSetReference()) {
      Model model = getModel();
      return (model != null) ? model.findNamedSBase(getReference()) : null;
    }
    return null;
  }

  /**
   * Gets the {@link SBase} instance corresponding to the reference id.
   * 
   * @return the {@link SBase} instance corresponding to the reference id.
   */
  public SBase getSBaseInstance() {
    Model model = getModel();
    return isSetReference() && (model != null) ? model.findUniqueSBase(getReference()) : null;
  }

  /**
   * Gets the {@link NamedSBase} instance corresponding to the reference id.
   * 
   * <p>Careful if you want to support SBML L3V2, you should use {@link #getSBaseInstance()}.
   * This method might be deprecated in future JSBML releases.</p>
   * 
   * @return the {@link NamedSBase} instance corresponding to the reference id.
   * @see #getSBaseInstance()
   */
  public NamedSBase getNamedSBaseInstance() {
    return getReferenceInstance();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;
    int hashCode = super.hashCode();
    hashCode += prime * (isSetReference() ? reference.hashCode() : 0);
    return hashCode;
  }

  /**
   * Returns {@code true} if the reference attribute is not null and not empty.
   * 
   * @return {@code true} if the reference attribute is set.
   */
  public boolean isSetReference() {
    return (reference != null) && (reference.length() > 0);
  }

  /**
   * Sets the reference based on the id of the given {@link NamedSBase}.
   * 
   * @param namedSBase the {@link NamedSBase} to set as reference for this glyph.
   * @see #setReference(String)
   */
  public void setNamedSBase(NamedSBase namedSBase) {
    setReference(namedSBase.getId());
  }

  /**
   * Sets the reference based on the id of the given {@link SBase}.
   * 
   * @param sbase the {@link SBase} to set as reference for this glyph.
   * @see #setReference(String)
   */
  public void setSBase(SBase sbase) {
    setReference(sbase.getId());
  }

  /**
   * Sets the reference.
   * 
   * @param sbase the id of an {@link SBase}.
   * @return {@code true} if this operation caused any change.
   */
  public boolean setReference(String sbase) {
    return setReference(sbase, "reference");
  }

  /**
   * Sets the reference object to which the id sbase refers to. The type defines the category
   * that changed for the {@link TreeNodeChangeEvent}.
   * 
   * @param sbase the id of an {@link SBase}.
   * @param type the type of reference,can be 'compartment', 'species', 'reaction' or 'reference'.
   * @return {@code true} if this operation caused any change.
   */
  boolean setReference(String sbase, String type) {
    if (sbase != reference) {
      String oldSBase = reference;
      reference = sbase;
      firePropertyChange(type, oldSBase, reference);
      return true;
    }
    return false;
  }

  /**
   * Unsets the reference.
   * 
   * @return {@code true} if this operation caused any change.
   */
  public boolean unsetReference() {
    return setReference((String) null);
  }

}
