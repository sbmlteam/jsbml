/*
 * $Id: T1.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/examples/test/src/org/sbml/jsbml/test/T1.java $
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
package org.sbml.jsbml.test;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev: 2109 $
 */
public class T1 {

  /**
   * @param args
   * @throws XMLStreamException
   * @throws IOException
   */
  public static void main(String[] args) throws XMLStreamException, IOException {
    System.setProperty("log4j.configuration", System.getProperty("user.dir") + "/log4j.properties");
    SBMLWriter.write(SBMLReader.read(new File(args[0]),  new SimpleTreeNodeChangeListener()), System.out, ' ', (short) 2);
  }

}
