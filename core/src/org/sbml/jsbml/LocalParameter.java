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
package org.sbml.jsbml;

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * A local parameter can only be used to specify a constant within a
 * {@link KineticLaw}.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class LocalParameter extends QuantityWithUnit {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 57994535283502018L;

  /**
   * This field memorizes whether this {@link LocalParameter} has been
   * explicitly set to be constant. All instances of {@link LocalParameter}
   * are constant by definition, however, in earlier versions of SBML (before
   * Level 3) it was possible to explicitly state that a
   * {@link LocalParameter} is constant. Therefore, a special field has become
   * necessary to reflect this property.
   */
  private boolean isExplicitlySetConstant = false;

  /**
   * Creates a new instance of {@link LocalParameter}.
   */
  public LocalParameter() {
    super();
  }

  /**
   * @param level
   * @param version
   */
  public LocalParameter(int level, int version) {
    super(level, version);
  }

  /**
   * @param lp
   */
  public LocalParameter(LocalParameter lp) {
    super(lp);
  }

  /**
   * Creates a new local parameter that will have the same properties than the
   * given global parameter. However, the value of the constant attribute will
   * be ignored because a local parameter can only be a constant quantity.
   * 
   * @param parameter
   */
  public LocalParameter(Parameter parameter) {
    super(parameter);
  }

  /**
   * Creates a new instance of {@link LocalParameter} with the given
   * identifier.
   * 
   * @param id
   */
  public LocalParameter(String id) {
    super(id);
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public LocalParameter(String id, int level, int version) {
    super(id, level, version);
  }

  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public LocalParameter(String id, String name, int level, int version) {
    super(id, name, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.QuantityWithUnit#clone()
   */
  @Override
  public LocalParameter clone() {
    return new LocalParameter(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.QuantityWithUnit#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      LocalParameter lp = (LocalParameter) object;
      equals &= isExplicitlySetConstant() == lp.isExplicitlySetConstant();
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return (getLevel() < 3) ? "parameter" : super.getElementName();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<LocalParameter> getParent() {
    return (ListOf<LocalParameter>) super.getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParentSBMLObject()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<LocalParameter> getParentSBMLObject() {
    return (ListOf<LocalParameter>) super.getParentSBMLObject();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getPredefinedUnitID()
   */
  @Override
  public String getPredefinedUnitID() {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.QuantityWithUnit#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int hashCode = super.hashCode();
    hashCode += prime * Boolean.valueOf(isExplicitlySetConstant()).hashCode();
    return hashCode;
  }

  /**
   * In SBML prior to Level 3 it was possible to explicitly state that a local
   * parameter represents a constant {@link Quantity}. However, per
   * definition, each local parameter has always been constant no matter if
   * its XML representation contains the {@code constant} attribute or
   * not. Hence, this attribute could set to {@code true} only. This
   * method checks if for this {@link LocalParameter} an explicit
   * {@code constant} flag has been set.
   * 
   * @return the isExplicitlySetConstant {@code true} if this
   *         {@link LocalParameter} contains an explicit {@code constant}
   *         flag, {@code false} otherwise.
   */
  public boolean isExplicitlySetConstant() {
    return isExplicitlySetConstant;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Symbol#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (attributeName.equals("value")) {
      setValue(StringTools.parseSBMLDouble(value));
      return true;
    } else if (attributeName.equals("units")) {
      this.setUnits(value);
      return true;
    } else if ((getLevel() < 3) && attributeName.equals("constant")
        && value.equals("true"))
    {
      isExplicitlySetConstant = true;
      // do nothing because this is always constant.
      return true;
    }

    return isAttributeRead;
  }

  /**
   * This method allows users to explicitly manipulate the
   * {@code constant} attribute of this {@link LocalParameter}. This
   * attribute can set to {@code true} only. Therefore, this method does
   * just decide whether or not the {@code constant} attribute should
   * occur in generated SBML code when serializing this {@link LocalParameter}
   * . Since this object always represents a constant {@link Quantity} this
   * method does only decide whether or not the resulting SBML code should
   * contain the attribute/value pair {@code constant = true}, it does
   * not decide on whether or not this object should be constant.
   * 
   * @param isExplicitlySetConstant
   *            the isExplicitlySetConstant to set
   * @deprecated Since SBML Level 3 it is no longer possible to explicitly set
   *             a {@link LocalParameter} to {@code constant = true}
   *             because {@link LocalParameter} instances always represent
   *             constant a {@link Quantity}.
   */
  @Deprecated
  public void setExplicitlyConstant(boolean isExplicitlySetConstant) {
    if (this.isExplicitlySetConstant != isExplicitlySetConstant) {
      Boolean oldValue = this.isExplicitlySetConstant;
      this.isExplicitlySetConstant = Boolean.valueOf(isExplicitlySetConstant);
      firePropertyChange(TreeNodeChangeEvent.isExplicitlySetConstant,
        oldValue, this.isExplicitlySetConstant);
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.Symbol#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetValue()) {
      attributes.put("value", StringTools.toString(Locale.ENGLISH,
        getValue()));
    }
    if (isSetUnits()) {
      attributes.put("units", getUnits());
    }
    // Put back the constant attribute if it was set in the original file.
    // The methods #readAttribute take care of doing all the needed tests.
    if (isExplicitlySetConstant) {
      attributes.put("constant", "true");
    }

    return attributes;
  }

}
