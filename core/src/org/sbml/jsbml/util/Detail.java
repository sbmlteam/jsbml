/*
 * $Id: Detail.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/core/src/org/sbml/jsbml/util/Detail.java $
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
package org.sbml.jsbml.util;

/**
 * @author Nicolas Rodriguez
 * @version $Rev: 2109 $
 * @since 1.0
 */
public class Detail {

  // TODO: Where is this used? What is its purpose?

  /**
   * 
   */
  private int category;
  /**
   * 
   */
  private int severity;

  /**
   * 
   * @return
   */
  public int getCategory() {
    return category;
  }

  /**
   * 
   * @param category
   */
  public void setCategory(int category) {
    this.category = category;
  }

  /**
   * 
   * @return
   */
  public int getSeverity() {
    return severity;
  }

  /**
   * 
   * @param severity
   */
  public void setSeverity(int severity) {
    this.severity = severity;
  }

}
