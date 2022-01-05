/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.validator;

/**
 * This class represents an exception that is thrown when the model to be
 * simulated is overdetermined
 * 
 * @author Alexander D&ouml;rr
 * @since 0.8
 */
public class ModelOverdeterminedException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -5288546434951201722L;

  /**
   * 
   */
  public ModelOverdeterminedException() {
    super();
  }

  /**
   * @param message
   */
  public ModelOverdeterminedException(String message) {
    super(message);
  }

  /**
   * @param message
   * @param cause
   */
  public ModelOverdeterminedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param cause
   */
  public ModelOverdeterminedException(Throwable cause) {
    super(cause);
  }

}
