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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SimpleSpeciesReference;

/**
 * The {@link SpeciesReferenceGlyph} class describes the graphical connection
 * between a {@link SpeciesGlyph} and a {@link ReactionGlyph} (which would be
 * an arrow or some curve in most cases).
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class SpeciesReferenceGlyph extends AbstractReferenceGlyph {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8810905237933499989L;

  /**
   * 
   */
  private Curve curve;

  /**
   * 
   */
  private SpeciesReferenceRole role;

  /**
   * 
   */
  private String speciesGlyph;

  /**
   * 
   */
  public SpeciesReferenceGlyph() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public SpeciesReferenceGlyph(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * 
   * @param speciesReferenceGlyph
   */
  public SpeciesReferenceGlyph(SpeciesReferenceGlyph speciesReferenceGlyph) {
    super(speciesReferenceGlyph);
    if (speciesReferenceGlyph.isSetCurve()) {
      setCurve(speciesReferenceGlyph.getCurve().clone());
    }
    if (speciesReferenceGlyph.isSetSpeciesReferenceRole()) {
      setRole(SpeciesReferenceRole.valueOf(speciesReferenceGlyph.getSpeciesReferenceRole().toString()));
    }
    if (speciesReferenceGlyph.isSetSpeciesGlyph()) {
      setSpeciesGlyph(new String(speciesReferenceGlyph.getSpeciesGlyph()));
    }
    initDefaults();
  }

  /**
   * 
   * @return
   */
  public ReactionGlyph getReactionGlyph() {
    TreeNode parent = getParent();
    do {
      if ((parent != null) && (parent instanceof ReactionGlyph)) {
        return (ReactionGlyph) parent;
      }
      parent = parent.getParent();
    } while (parent != null);

    return null;
  }

  /**
   * 
   * @param id
   */
  public SpeciesReferenceGlyph(String id) {
    super(id);
    initDefaults();
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public SpeciesReferenceGlyph(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
   */
  @Override
  public SpeciesReferenceGlyph clone() {
    return new SpeciesReferenceGlyph(this);
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
   * 
   * @return
   */
  public Curve createCurve() {
    if (isSetCurve()) {
      unsetCurve();
    }
    setCurve(new Curve(getLevel(), getVersion()));
    return getCurve();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      SpeciesReferenceGlyph s = (SpeciesReferenceGlyph) object;
      equals &= s.isSetSpeciesReferenceRole() && isSetSpeciesReferenceRole();
      if (equals && isSetSpeciesReferenceRole()) {
        equals &= s.getSpeciesReferenceRole().equals(getSpeciesReferenceRole());
      }
      equals &= s.isSetSpeciesGlyph() == isSetSpeciesGlyph();
      if (equals && isSetSpeciesGlyph()) {
        equals &= s.getSpeciesGlyph().equals(getSpeciesGlyph());
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
   * 
   * @return String id of the speciesGlyph
   */
  public String getSpeciesGlyph() {
    return speciesGlyph;
  }

  /**
   * 
   * @return
   */
  public SpeciesGlyph getSpeciesGlyphInstance() {
    if (!isSetSpeciesGlyph()) {
      return null;
    }
    Model model = getModel();
    return (model != null) ? (SpeciesGlyph) model.findNamedSBase(getSpeciesGlyph()) : null;
  }

  /**
   * 
   * @return
   */
  public String getSpeciesReference() {
    return getReference();
  }

  /**
   * Note that the return type of this method is {@link NamedSBase} because it
   * could be possible to link some element from other packages to this glyph.
   * 
   * @return
   */
  public NamedSBase getSpeciesReferenceInstance() {
    return getNamedSBaseInstance();
  }

  /**
   * @return
   */
  public SpeciesReferenceRole getSpeciesReferenceRole() {
    return getRole();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 953;
    int hashCode = super.hashCode();
    if (isSetSpeciesReferenceRole()) {
      hashCode += prime * getSpeciesReferenceRole().hashCode();
    }
    if (isSetSpeciesGlyph()) {
      hashCode += prime * getSpeciesGlyph().hashCode();
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
   * @return
   */
  public boolean isSetSpeciesGlyph() {
    return speciesGlyph != null;
  }

  /**
   * @return
   */
  public boolean isSetSpeciesReference() {
    return isSetReference();
  }

  public boolean isSetSpeciesReferenceInstance() {
    return getSpeciesReferenceInstance() != null;
  }

  /**
   * @return
   */
  public boolean isSetSpeciesReferenceRole() {
    return role != null;
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

      if (attributeName.equals(LayoutConstants.speciesReference))
      {
        setSpeciesReference(value);
      }
      else if (attributeName.equals(LayoutConstants.speciesGlyph))
      {
        setSpeciesGlyph(value);
      }
      else if (attributeName.equals(LayoutConstants.role))
      {
        try {
          setRole(SpeciesReferenceRole.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + LayoutConstants.role
            + " on the 'SpeciesReferenceGlyph' element.");
        }
      }
      else
      {
        return false;
      }
    }

    return isAttributeRead;
  }

  /**
   * The curve is an optional element of type {@link Curve}. When present, the glyphs
   * bounding box (as inherited from the {@link GraphicalObject}) is to be disregarded.
   * 
   * So as to make the drawing of these curves as easy as possible, the line segments
   * should be ordered depending on the role of the {@link SpeciesReferenceGlyph}. If
   * no role attribute is defined, the role to be assumed is taken from the role that
   * the {@link SpeciesReference} referenced via the attribute speciesReference has,
   * otherwise it is undefined.
   * 
   * @param curve
   */
  public void setCurve(Curve curve) {
    Curve oldCurve = this.curve;
    this.curve = curve;
    firePropertyChange(LayoutConstants.curve, oldCurve, role);
    registerChild(this.curve);
  }

  /**
   * The role attribute is of type {@link SpeciesReferenceRole} and is used to specify
   * how the species reference should be displayed. This attribute is optional and should
   * only be necessary if the optional speciesReference attribute is not given or if the
   * respective information from the model needs to be overridden.
   * 
   * To define more specific interactions, the recommended practice is to use the sboTerm
   * attribute on the {@link SpeciesReference}. If both role and sboTerm are specified and
   * they conflict, it is the role that takes precedence.
   * 
   * @param role
   */
  public void setRole(SpeciesReferenceRole role) {
    SpeciesReferenceRole oldRole = this.role;
    this.role = role;
    firePropertyChange(LayoutConstants.role, oldRole, role);
  }

  /**
   * 
   * @param role
   * @see #setRole(SpeciesReferenceRole)
   */
  public void setSpeciesReferenceRole(SpeciesReferenceRole role) {
    setRole(role);
  }

  /**
   * @return
   * @see #getSpeciesReferenceRole()
   */
  public SpeciesReferenceRole getRole() {
    return role;
  }

  /**
   * The speciesGlyph attribute contains a reference to the id of a {@link SpeciesGlyph}
   * object that is to be connected to the {@link ReactionGlyph}. This attribute is
   * mandatory so as to ensure unambiguity about which {@link SpeciesGlyph} has to be
   * connected with this {@link ReactionGlyph}.
   * 
   * @param speciesGlyph
   */
  public void setSpeciesGlyph(String speciesGlyph) {
    String oldValue = this.speciesGlyph;
    this.speciesGlyph = speciesGlyph;
    firePropertyChange(LayoutConstants.speciesGlyph, oldValue, this.speciesGlyph);
  }

  /**
   * See setSpeciesReference(String).
   * 
   * @param speciesReference
   */
  public void setSpeciesReference(SimpleSpeciesReference speciesReference) {
    setSpeciesReference(speciesReference.getId());
  }

  /**
   * The speciesReference is an optional attribute that allows modelers to
   * connect the {@link SpeciesReferenceGlyph} with a particular {@link SpeciesReference}
   * of the containing {@link Model}.
   * 
   * @param speciesReference
   */
  public void setSpeciesReference(String speciesReference) {
    setReference(speciesReference, LayoutConstants.speciesReference);
  }

  /**
   * 
   * @return
   */
  public boolean unsetCurve() {
    if (isSetCurve()) {
      Curve oldCurve = getCurve();
      curve = null;
      oldCurve.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * 
   */
  public void unsetSpeciesReference() {
    unsetReference();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetSpeciesGlyph()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.speciesGlyph, speciesGlyph);
    }
    if (isSetSpeciesReference()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.speciesReference, getSpeciesReference());
    }
    if (isSetSpeciesReferenceRole()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.role, role.toString().toLowerCase());
    }

    return attributes;
  }

}
