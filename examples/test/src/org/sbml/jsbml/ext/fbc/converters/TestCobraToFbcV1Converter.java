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
package org.sbml.jsbml.ext.fbc.converters;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;

/**
 * tests CobraToFbcV1Converter
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.3
 */
public class TestCobraToFbcV1Converter {
  
  /**
   * Converts old COBRA SBML files to SBML FBC_V1.
   * 
   * @param args the arguments, expect two files path, input and output files.
   * @throws XMLStreamException if an error occurs
   * @throws IOException if an error occurs
   */
  public static void main(String[] args) throws XMLStreamException, IOException {
    // read document
    SBMLReader sbmlReader = new SBMLReader();
    SBMLDocument doc = sbmlReader.readSBMLFromFile(args[0]);
    // convert and write document
    CobraToFbcV1Converter cobraToFbcV1Converter = new CobraToFbcV1Converter();
    TidySBMLWriter tidySBMLWriter = new TidySBMLWriter();
    tidySBMLWriter.writeSBMLToFile(cobraToFbcV1Converter.convert(doc),args[1]);
  }  
}
