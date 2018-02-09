/*
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The Babraham Institute, Cambridge, UK
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
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;
import org.sbml.jsbml.util.converters.SBMLConverter;

/**
 * converts old COBRA SBML files to SBML FBCV2
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.3
 */
public class CobraToFbcV2Converter implements SBMLConverter {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#convert(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public SBMLDocument convert(SBMLDocument sbmlDocument) throws SBMLException {
   // convert old COBRA SBML file to SBML FBCV1
    CobraToFbcV1Converter cobraToFbcV1Converter = new CobraToFbcV1Converter();
    sbmlDocument = cobraToFbcV1Converter.convert(sbmlDocument);
   // convert SBML FBCV1 file to SBML FBCV2
    FcbV1ToFbcV2Converter fbcV1ToFbcV2Converter = new FcbV1ToFbcV2Converter();
    sbmlDocument = fbcV1ToFbcV2Converter.convert(sbmlDocument);
    
    return sbmlDocument;
  }
  
  public static void main(String[] args) throws XMLStreamException, IOException {
    // read document 
    SBMLReader sbmlReader = new SBMLReader();
    SBMLDocument doc = sbmlReader.readSBMLFromFile(args[0]);
   // convert and write document
    CobraToFbcV2Converter cobraToFbcV2Converter = new CobraToFbcV2Converter();
    TidySBMLWriter tidySBMLWriter = new TidySBMLWriter();
    tidySBMLWriter.writeSBMLToFile(cobraToFbcV2Converter.convert(doc),args[1]);    
  }
}
