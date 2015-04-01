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
package org.sbml.jsbml.ext.multi.deprecated;

import org.sbml.jsbml.AbstractSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class SelectorReference extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8313057744716585955L;

  /**
   * 
   */
  private boolean negation;

  /**
   * 
   */
  private String selector;

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public AbstractSBase clone() {
    return new SelectorReference(this);
  }

  /**
   * 
   */
  public SelectorReference() {
    super();
  }

  /**
   * @param selectorRef
   */
  public SelectorReference(SelectorReference selectorRef) {
    this();
    //TODO: clone!
  }

  /**
   * Returns the negation.
   * 
   * @return the negation
   */
  public boolean isNegation() {
    return negation;
  }


  /**
   * Sets the negation.
   * 
   * @param negation the negation to set
   */
  public void setNegation(boolean negation) {
    this.negation = negation;
  }


  /**
   * Returns the selector.
   * 
   * @return the selector
   */
  public String getSelector() {
    return selector;
  }


  /**
   * Sets the selector.
   * 
   * @param selector the selector to set
   */
  public void setSelector(String selector) {
    this.selector = selector;
  }


  @Override
  public String toString() {
    // TODO
    return null;
  }

}
