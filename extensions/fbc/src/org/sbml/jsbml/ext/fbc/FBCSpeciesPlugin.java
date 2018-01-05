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
package org.sbml.jsbml.ext.fbc;

import java.text.MessageFormat;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.xml.parsers.AbstractReaderWriter;

/**
 * Extends the SBML core {@link Species} class with the additional
 * attributes charge and chemicalFormula.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class FBCSpeciesPlugin extends AbstractFBCSBasePlugin {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(FBCSpeciesPlugin.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Species> getParent() {

    if (isSetExtendedSBase()) {
      return (ListOf<Species>) getExtendedSBase().getParent();
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public ListOf<Species> getParentSBMLObject() {
    return getParent();
  }

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 923773407400143272L;

  /**
   * 
   */
  private int charge;

  /**
   * 
   */
  private String chemicalFormula;

  /**
   * 
   */
  private boolean isSetCharge;

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public FBCSpeciesPlugin(FBCSpeciesPlugin obj) {
    super(obj);

    if (obj.isSetChemicalFormula()) {
      setChemicalFormula(new String(obj.getChemicalFormula()));
    }

    if (obj.isSetCharge()) {
      setCharge(obj.getCharge());
    }

  }

  /**
   * Creates an {@link FBCSpeciesPlugin} instance
   * 
   * @param species the core {@link Species} that is extended.
   */
  public FBCSpeciesPlugin(Species species) {
    super(species);

    if (species == null) {
      throw new IllegalArgumentException("The value of the species argument must not be null.");
    }
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public FBCSpeciesPlugin clone() {
    return new FBCSpeciesPlugin(this);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /**
   * Returns the value of {@link #charge}.
   *
   * @return the value of {@link #charge}.
   */
  public int getCharge() {
    if (isSetCharge()) {
      return charge;
    }
    throw new PropertyUndefinedError(FBCConstants.charge, this);
  }

  /**
   * Returns the value of {@link #chemicalFormula}.
   *
   * @return the value of {@link #chemicalFormula}.
   */
  public String getChemicalFormula() {
    if (isSetChemicalFormula()) {
      return chemicalFormula;
    }
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int pos = 0;
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, + Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /**
   * Initializes the default values.
   */
  public void initDefaults() {
  }

  /**
   * Returns whether {@link #charge} is set.
   *
   * @return whether {@link #charge} is set.
   */
  public boolean isSetCharge() {
    return isSetCharge;
  }

  /**
   * Returns whether {@link #chemicalFormula} is set.
   *
   * @return whether {@link #chemicalFormula} is set.
   */
  public boolean isSetChemicalFormula() {
    return (chemicalFormula != null) && (chemicalFormula.length() > 0);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {

    if (attributeName.equals(FBCConstants.charge)) {
      setCharge(StringTools.parseSBMLInt(value));
      return true;
    } else if (attributeName.equals(FBCConstants.chemicalFormula)) {
      try {
        setChemicalFormula(value);
      } catch (IllegalArgumentException exc) {
        logger.error("Skipped invalid chemical formula: " + value);
        AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
      }
      return true;
    }

    return false;
  }

  /**
   * Sets the value of charge. 
   * 
   * <p>The optional field charge takes an integer indicating
   * the charge on the species (in terms of electrons, not the SI unit coulombs).
   * <br/>This attribute can be used for charge balancing.</p>
   * 
   * @param charge the value of charge. 
   */
  public void setCharge(int charge) {
    int oldCharge = this.charge;
    this.charge = charge;
    isSetCharge = true;
    firePropertyChange(FBCConstants.charge, oldCharge, this.charge);
  }

  /**
   * Sets the value of chemicalFormula.
   * 
   * <p>The format of chemical formula must consist only of atomic names (as  in
   * the  Periodic  Table) or user defined compounds either of which take the
   * form of a single capital letter followed by zero or more lower-case
   * letters. Where there is more than a single atom  present, this is indicated
   * with an integer. With regards to order (and enhance interoperability)
   * it is recommended to use the Hill system order (Hill 1900, 2012).</p>
   * 
   * @param chemicalFormula the chemical formula
   */
  public void setChemicalFormula(String chemicalFormula) {
    if ((chemicalFormula != null) && (!isReadingInProgress()) &&
        !SyntaxChecker.isValidChemicalFormula(chemicalFormula)) {
      throw new IllegalArgumentException(chemicalFormula);
    }
    String oldChemicalFormula = this.chemicalFormula;
    this.chemicalFormula = chemicalFormula;
    firePropertyChange(FBCConstants.chemicalFormula, oldChemicalFormula, this.chemicalFormula);
  }

  /**
   * Unsets the variable charge
   *
   * @return {@code true}, if charge was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCharge() {
    if (isSetCharge()) {
      int oldCharge = charge;
      charge = 0;
      isSetCharge = false;
      firePropertyChange(FBCConstants.charge, oldCharge, charge);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable chemicalFormula
   *
   * @return {@code true} if chemicalFormula was set before,
   *         otherwise {@code false}
   */
  public boolean unsetChemicalFormula() {
    if (isSetChemicalFormula()) {
      String oldChemicalFormula = chemicalFormula;
      chemicalFormula = null;
      firePropertyChange(FBCConstants.chemicalFormula, oldChemicalFormula, chemicalFormula);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = new TreeMap<String, String>();

    if (isSetCharge) {
      attributes.put(FBCConstants.shortLabel + ':' + FBCConstants.charge, Integer.toString(getCharge()));
    }
    if (isSetChemicalFormula()) {
      attributes.put(FBCConstants.shortLabel + ':' + FBCConstants.chemicalFormula, getChemicalFormula());
    }

    return attributes;
  }

}
