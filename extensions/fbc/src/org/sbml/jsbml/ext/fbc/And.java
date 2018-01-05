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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc;

/**
 * This class represents a set of two or more associations that are related in an order independent ‘and’
 * relationship.
 * 
 * <p>Introduced to FBC in version 2.</p>
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.1
 */
public class And extends LogicalOperator {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5964208695186298385L;

  /**
   * Creates a new {@link And} instance.
   */
  public And() {
    super();
  }

  /**
   * Creates a new {@link And} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public And(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new {@link And} instance.
   * 
   * @param and the instance to clone
   */
  public And(And and) {
    super(and);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.fbc.LogicalOperator#clone()
   */
  @Override
  public And clone() {
    return new And(this);
  }

}
