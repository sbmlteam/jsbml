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
public class Or extends LogicalOperator {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -4558397941057530943L;

  /**
   * Creates a new {@link Or} instance.
   */
  public Or() {
    super();
  }

  /**
   * Creates a new {@link Or} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Or(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new {@link Or} instance.
   * 
   * @param or the instance to clone
   */
  public Or(Or or) {
    super(or);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.fbc.LogicalOperator#clone()
   */
  @Override
  public Or clone() {
    return new Or(this);
  }

}
