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
package org.sbml.jsbml.xml;

/**
 * This kind of {@link Error} indicates that an XML file could not be parsed
 * correctly.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class XMLException extends RuntimeException {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7347204499480036729L;

  /**
   * 
   */
  public XMLException() {
    super();
  }


  /**
   * @param message
   */
  public XMLException(String message) {
    super(message);
  }

  /**
   * @param message
   * @param cause
   */
  public XMLException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param cause
   */
  public XMLException(Throwable cause) {
    super(cause);
  }

  /**
   * 
   * @return
   */
  public int getErrorId() {
    return 0;
  }

}
