/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Abstract super class for all kinds of glyphs that graphically represent an
 * instance of {@link NamedSBase}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
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
   * 
   */
  public AbstractReferenceGlyph() {
    super();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public AbstractReferenceGlyph(int level, int version) {
    super(level, version);
  }

  /**
   * 
   * @param glyph
   */
  public AbstractReferenceGlyph(AbstractReferenceGlyph glyph) {
    super(glyph);
    if (glyph.isSetReference()) {
      setReference(glyph.getReference());
    }
  }

  /**
   * @param id
   */
  public AbstractReferenceGlyph(String id) {
    super(id);
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
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
   * 
   * @return
   */
  public String getReference() {
    return isSetReference() ? reference : "";
  }

  /**
   * 
   * @return
   */
  public NamedSBase getReferenceInstance() {
    Model model = getModel();
    return isSetReference() && (model != null) ? model.findNamedSBase(getReference()) : null;
  }

  /**
   * 
   * @return
   * @see #getReferenceInstance()
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
   * 
   * @return
   */
  public boolean isSetReference() {
    return (reference != null) && (reference.length() > 0);
  }

  /**
   * 
   * @param namedSBase
   */
  public void setNamedSBase(NamedSBase namedSBase) {
    setReference(namedSBase.getId());
  }

  /**
   * @param sbase
   */
  public void setReference(String sbase) {
    setReference(sbase, "reference");
  }

  /**
   * Sets the reference object to which the id sbase refers to. The type defines the category
   * that changed for the {@link TreeNodeChangeEvent}.
   * 
   * @param sbase
   * @param type
   */
  void setReference(String sbase, String type) {
    String oldSBase = reference;
    reference = sbase;
    firePropertyChange(type, oldSBase, reference);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getElementName());
    sb.append(" [id=");
    sb.append(isSetId() ? getId() : "null");
    sb.append(", reference=");
    sb.append(isSetReference() ? getReference() : "null");
    sb.append(']');
    return sb.toString();
  }

  /**
   * 
   */
  public void unsetReference() {
    setReference((String) null);
  }

}
