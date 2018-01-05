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
 * Stores the options sent by the SBML online validator.
 * See the <a href="http://sbml.org/Facilities/Documentation/Validator_Web_API">SBML Online Validator web API</a> page.
 * 
 * @since 0.8
 */
public class Option {

  /**
   * 
   */
  private String name;
  /**
   * 
   */
  private String status;

  /**
   * 
   */
  public Option() {
  }

  /**
   * 
   * @param name
   * @param status
   */
  public Option(String name, String status) {
    this.name = name;
    this.status = status;
  }

  /**
   * 
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * 
   * @return
   */
  public String getStatus() {
    return status;
  }

  /**
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 
   * @param status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Option [name=" + name + ", status=" + status + "]";
  }

}
