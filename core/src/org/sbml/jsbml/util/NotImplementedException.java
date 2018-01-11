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
package org.sbml.jsbml.util;

/**
 * This kind of {@link Exception} indicates currently missing functionality in
 * JSBML. This class will be removed as soon JSBML has implemented all features.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class NotImplementedException extends Error {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 8871684506701754580L;

  /**
   * 
   */
  public NotImplementedException() {
    super();
  }

  /**
   * @param message
   */
  public NotImplementedException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public NotImplementedException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public NotImplementedException(String message, Throwable cause) {
    super(message, cause);
  }

}
