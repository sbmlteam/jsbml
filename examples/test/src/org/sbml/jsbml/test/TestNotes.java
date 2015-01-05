/*
 * $Id$
 * $URL$
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

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.xml.XMLNode;

/**
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class TestNotes
{

  /**
   * @param args
   * @throws XMLStreamException
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException, XMLStreamException
  {
    //    String chebiURL = "http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&chebiId=15511&dimensions=300";
    String chebiURLXML =	"http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage&#61;true&#38;chebiId&#61;15511&#38;dimensions=300";
    //    String chebiURLHTML = "http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&amp;chebiId=15511&amp;dimensions=300";


    SBMLDocument doc = new SBMLDocument(2, 4);

    Model model = doc.createModel("m");

    model.appendNotes(chebiURLXML);

    System.out.println(chebiURLXML);

    System.out.println(model.getNotesString());

    String docXML = JSBML.writeSBMLToString(doc);

    System.out.println(docXML);

    SBMLDocument doc2 = JSBML.readSBMLFromString(docXML);

    System.out.println(doc2.getModel().getNotesString());

    // TODO: fix this, add more tests like creating the XMLNode by hand to see if it is better when we add the chebi URL as an attribute

    String chebiAsImage = "<img xmlns=\"http://www.w3.org/1999/xhtml\" src=\"http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage&#61;true&#38;chebiId&#61;15511&#38;dimensions=300\" alt=\"Smiley face\" height=\"42\" width=\"42\" />";

    model.getNotes().getChildAt(0).addChild(XMLNode.convertStringToXMLNode(chebiAsImage));

    System.out.println("\n\n" + model.getNotesString());

    docXML = JSBML.writeSBMLToString(doc);

    System.out.println(docXML);
  }

}
