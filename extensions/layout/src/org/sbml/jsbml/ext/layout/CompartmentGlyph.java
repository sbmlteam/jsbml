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

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentalizedSBase;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * The {@link CompartmentGlyph} class is derived from {@link GraphicalObject}
 * and inherits its attributes. Additionally it has tow optional attributes:
 * compartment and order.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class CompartmentGlyph extends AbstractReferenceGlyph implements CompartmentalizedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -831178362695634919L;

  /**
   * 
   */
  private Double order;

  /**
   * Creates a new instance of {@link CompartmentGlyph}.
   */
  public CompartmentGlyph() {
    super();
    initDefaults();
  }

  /**
   * Creates a new instance of {@link CompartmentGlyph}.
   * 
   * @param compartmentGlyph the {@link CompartmentGlyph} to clone.
   */
  public CompartmentGlyph(CompartmentGlyph compartmentGlyph) {
    super(compartmentGlyph);
    initDefaults();
  }

  /**
   * Creates a new instance of {@link CompartmentGlyph}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public CompartmentGlyph(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new instance of {@link CompartmentGlyph}.
   * 
   * @param id the id
   */
  public CompartmentGlyph(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a new instance of {@link CompartmentGlyph}.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public CompartmentGlyph(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
   */
  @Override
  public CompartmentGlyph clone() {
    return new CompartmentGlyph(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartment()
   */
  @Override
  public String getCompartment() {
    return getReference();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartmentInstance()
   */
  @Override
  public NamedSBase getCompartmentInstance() {
    return getNamedSBaseInstance();
  }

  /**
   * Returns the value of order
   *
   * @return the value of order
   */
  public double getOrder()
  {
    if (isSetOrder()) {
      return order;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(LayoutConstants.order, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isCompartmentMandatory()
   */
  @Override
  public boolean isCompartmentMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartment()
   */
  @Override
  public boolean isSetCompartment() {
    return isSetReference();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartmentInstance()
   */
  @Override
  public boolean isSetCompartmentInstance() {
    return getCompartmentInstance() != null;
  }

  /**
   * Returns whether order is set
   *
   * @return whether order is set
   */
  public boolean isSetOrder() {
    return order != null;
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

      if (attributeName.equals(LayoutConstants.compartment))
      {
        setCompartment(value);
      }
      else if (attributeName.equals(LayoutConstants.order))
      {
        Double valueDbl = StringTools.parseSBMLDouble(value);
        
        // If the value is NaN, we could have encountered a NumberFormatExpection
        // So we parse it again to be sure as StringTools.parseSBMLDouble does not propagate the exception.
        if (valueDbl.isNaN()) {
          try {
            Double.parseDouble(value);
          } catch (NumberFormatException e) {
            // The value is a wrong double so don't set it and return false so that it is put in the unknown attributes
            return false;
          }    
        }
        
        setOrder(valueDbl);
      }
      else
      {
        return false;
      }

      return true;
    }

    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(org.sbml.jsbml.Compartment)
   */
  @Override
  public boolean setCompartment(Compartment compartment) {
    return setCompartment(compartment.getId());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(java.lang.String)
   */
  @Override
  public boolean setCompartment(String compartment) {
    return setReference(compartment, TreeNodeChangeEvent.compartment);
  }

  /**
   * Sets the oder.
   * 
   * <p>The order attribute is an optional attribute of type {@link Double}. It is there
   * to handle the case where compartments in a layout overlap, and tools want to
   * clearly disambiguate which {@link CompartmentGlyph} is on top of the other.
   * The order attribute follows the coordinate system. There are z dimension points
   * into a screen; thus, an element with a <b>lower</b> order value will be <b>in front</b>
   * of an elements with a <b>higher</b> value. If not specified, the order is undefined
   * and tools are free to display the compartment glyphs in the order that best fits their
   * needs.</p>
   * 
   * <p>Note: if z coordinates are used (and this order is seemingly redundant), the order
   * can still be used to disambiguate drawing order.</p>
   * 
   * @param order the oder
   */
  public void setOrder(double order) {
    Double oldOrder = this.order;
    this.order = order;
    firePropertyChange(LayoutConstants.order, oldOrder, this.order);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#unsetCompartment()
   */
  @Override
  public boolean unsetCompartment() {
    return unsetReference();
  }

  /**
   * Unsets the variable order
   *
   * @return {@code true}, if order was set before,
   *         otherwise {@code false}
   */
  public boolean unsetOrder() {
    if (isSetOrder()) {
      Double oldOrder = order;
      order = null;
      firePropertyChange(LayoutConstants.order, oldOrder, order);
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

    if (isSetCompartment()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.compartment, getCompartment());
    }
    if (isSetOrder()) {
      attributes.put(LayoutConstants.shortLabel + ':' + LayoutConstants.order,
        StringTools.toString(Locale.ENGLISH, order.doubleValue()));
    }

    return attributes;
  }

}
