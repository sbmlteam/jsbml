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
package org.sbml.jsbml.xml.parsers;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.XMLNode;

/**
 * The interface to implement for the SBML parsers writing SBML annotations.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public interface AnnotationWriter {

  /**
   * Writes the annotation of the given {@link SBase} object into the given
   * {@link XMLNode}.
   * <p>This method can change the content of the {@link XMLNode} by
   * removing or adding nodes.
   * 
   * @param contextObject
   * @param xmlNode
   * @return
   */
  public XMLNode writeAnnotation(SBase contextObject, XMLNode xmlNode);

}
