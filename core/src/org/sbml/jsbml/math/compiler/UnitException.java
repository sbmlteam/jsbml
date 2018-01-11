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
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math.compiler;

import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.ASTNode2;

/**
 * Exception to be thrown if inconsistent units are used in mathematical
 * expressions, i.e., instances of {@link ASTNode2}.
 * 
 * @author Andreas Dr&auml;ger
 * @author Victor Kofia
 * @since 0.8
 */
public class UnitException extends SBMLException {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5630929507888356877L;

  /**
   * 
   */
  public UnitException() {
    super();
  }

  /**
   * @param message
   */
  public UnitException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public UnitException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public UnitException(String message, Throwable cause) {
    super(message, cause);
  }

}
