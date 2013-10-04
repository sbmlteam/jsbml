/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.StringTools;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 02.10.2013
 */
public class FBCSpeciesPlugin extends AbstractSBasePlugin {

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
   */
  public FBCSpeciesPlugin(FBCSpeciesPlugin obj) {
    super(obj.getExtendedSBase());

    if (obj.isSetChemicalFormula()) {
      setChemicalFormula(obj.getChemicalFormula());
    }

    if (obj.isSetCharge()) {
      setCharge(obj.getCharge());
    }

  }


  /**
   * Creates an FBCSpeciesPlugin instance 
   */
  public FBCSpeciesPlugin(Species species) {
    super(species);		
    initDefaults();

    if (species == null) {
      throw new IllegalArgumentException("The value of the species argument must not be null.");
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  public FBCSpeciesPlugin clone() {
    return new FBCSpeciesPlugin(this);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  public boolean getAllowsChildren() {
    return false;
  }



  /**
   * Returns the value of charge
   *
   * @return the value of charge
   */
  public int getCharge() {
    if (isSetCharge()) {
      return charge;
    }

    throw new PropertyUndefinedError(FBCConstants.charge, this);
  }

  /**
   * Returns the value of chemicalFormula
   *
   * @return the value of chemicalFormula
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
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  public int getChildCount() {
    return 0;
  }


  /**
   * Initializes the default values.
   */
  public void initDefaults() {}

  /**
   * Returns whether charge is set 
   *
   * @return whether charge is set 
   */
  public boolean isSetCharge() {
    return isSetCharge;
  }

  /**
   * Returns whether chemicalFormula is set 
   *
   * @return whether chemicalFormula is set 
   */
  public boolean isSetChemicalFormula() {
    return this.chemicalFormula != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  public boolean readAttribute(String attributeName, String prefix, String value) {

    if (attributeName.equals(FBCConstants.charge)) {
      setCharge(StringTools.parseSBMLInt(value));
      return true;
    } else if (attributeName.equals(FBCConstants.chemicalFormula)) {
      setChemicalFormula(value);
      return true;		
    }

    return false;
  }

  /**
   * Sets the value of charge
   */
  public void setCharge(int charge) {
    int oldCharge = this.charge;
    this.charge = charge;
    isSetCharge = true;
    firePropertyChange(FBCConstants.charge, oldCharge, this.charge);
  }

  /**
   * Sets the value of chemicalFormula
   */
  public void setChemicalFormula(String chemicalFormula) {
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
      int oldCharge = this.charge;
      this.charge = 0;
      isSetCharge = false;
      firePropertyChange(FBCConstants.charge, oldCharge, this.charge);
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
      String oldChemicalFormula = this.chemicalFormula;
      this.chemicalFormula = null;
      firePropertyChange(FBCConstants.chemicalFormula, oldChemicalFormula, this.chemicalFormula);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = new HashMap<String, String>();

    if (isSetCharge) {
      attributes.put(FBCConstants.shortLabel + ':' + FBCConstants.charge, Integer.toString(getCharge()));
    }
    if (isSetChemicalFormula()) {
      attributes.put(FBCConstants.shortLabel + ':' + FBCConstants.chemicalFormula, getChemicalFormula());
    }

    return attributes;
  }

}
