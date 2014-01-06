/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class SpeciesTypeInstance extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1775590492963078468L;

  private double initialAmount;

  private double initialConcentration;

  private ListOf<SelectorReference> listOfSelectorReferences;

  @Override
  public boolean isIdMandatory() {
    return true;
  }

  @Override
  public AbstractSBase clone() {
    // TODO
    return null;
  }

  /**
   * Returns the initialAmount.
   * 
   * @return the initialAmount
   */
  public double getInitialAmount() {
    return initialAmount;
  }

  /**
   * Sets the initialAmount.
   * 
   * @param initialAmount the initialAmount to set
   */
  public void setInitialAmount(double initialAmount) {
    this.initialAmount = initialAmount;
  }

  /**
   * Returns the initialConcentration.
   * 
   * @return the initialConcentration
   */
  public double getInitialConcentration() {
    return initialConcentration;
  }

  /**
   * Sets the initialConcentration.
   * 
   * @param initialConcentration the initialConcentration to set
   */
  public void setInitialConcentration(double initialConcentration) {
    this.initialConcentration = initialConcentration;
  }

  /**
   * @return the listOfSelectorReferences
   */
  public ListOf<SelectorReference> getListOfSelectorReferences() {
    return listOfSelectorReferences;
  }

  /**
   * @param listOfSelectorReferences the listOfSelectorReferences to set
   */
  public void addSelectorReference(SelectorReference selectorReference) {
    getListOfSelectorReferences().add(selectorReference);
  }
}
