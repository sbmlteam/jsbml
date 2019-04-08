/*
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2019 jointly by the following organizations: 
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
import org.sbml.jsbml.SBMLError.SEVERITY;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;

/**
 * tests NotesToAnnotation.java
 * 
 * @author Thomas Hamm
 * @since 1.5
 */

public class TestNotesToAnnotation {

  /**
   * Tests the transfer of information from the notes to the annotations (tests NotesToAnnotation.java).
   * 
   * @param args the arguments, expect two files path, input and output files
   * @throws SBMLException if an error occurs
   * @throws XMLStreamException if an error occurs
   * @throws IOException if an error occurs
   */ 
  public static void main(String[] args) throws SBMLException, XMLStreamException, IOException {
    // read document
    SBMLReader sbmlReader = new SBMLReader();
    SBMLDocument doc = sbmlReader.readSBMLFromFile(args[0]);

    // test SBML document with offline validator 
    int docLevel = doc.getLevel();
    int docVersion = doc.getVersion();
    int docIsValidOffl = doc.checkConsistencyOffline();

    System.out.println("The original SBML document is SBML Level " + docLevel + " Version " + docVersion + ".");
    System.out.println("Results of the JSBML offline validator for the original SBML document:");

    int numOfErrorsOffl = doc.getErrorLog().getErrorsBySeverity(SEVERITY.ERROR).size();
    int numOfFatalsOffl = doc.getErrorLog().getErrorsBySeverity(SEVERITY.FATAL).size();
    int numOfWarningsOffl = doc.getErrorLog().getErrorsBySeverity(SEVERITY.WARNING).size();
    int numOfInfosOffl = doc.getErrorLog().getErrorsBySeverity(SEVERITY.INFO).size();
    System.out.println("  Number of errors (" + numOfErrorsOffl + ") + fatals (" + numOfFatalsOffl + ") + warnings (" + numOfWarningsOffl + ") + infos (" + numOfInfosOffl + ") is: " +  docIsValidOffl + ".");
    System.out.println();
    System.out.println();
    // doc.printErrors(System.out);


    // transfers the information from the notes to the annotations
    NotesToAnnotation notesToAnnotation = new NotesToAnnotation();
    String[] keysInNotes = { "KEGG Compound", "SEED Compound", "BioCyc", "UniPathway Compound", "MetaNetX (MNX) Chemical", "Reactome", "Human Metabolome Database", "UM-BBD", "EC Number", "MetaNetX (MNX) Equation", "KEGG Reaction", "RHEA", "UniPathway Reaction"};
    SBMLDocument newDoc = notesToAnnotation.transfer(doc, keysInNotes); 

    // write document
    TidySBMLWriter tidySBMLWriter = new TidySBMLWriter();
    tidySBMLWriter.writeSBMLToFile(newDoc, args[1]);


    // test new SBML document with offline validator
    int newdocLevel = newDoc.getLevel();
    int newdocVersion = newDoc.getVersion();
    int newdocIsValidOffl = newDoc.checkConsistencyOffline();

    System.out.println("The new SBML document is SBML Level " + newdocLevel + " Version " + newdocVersion + ".");
    System.out.println("Results of the JSBML offline validator for the new SBML document:");

    int newNumOfErrorsOffl = newDoc.getErrorLog().getErrorsBySeverity(SEVERITY.ERROR).size();
    int newNumOfFatalsOffl = newDoc.getErrorLog().getErrorsBySeverity(SEVERITY.FATAL).size();
    int newNumOfWarningsOffl = newDoc.getErrorLog().getErrorsBySeverity(SEVERITY.WARNING).size();
    int newNumOfInfosOffl = newDoc.getErrorLog().getErrorsBySeverity(SEVERITY.INFO).size();
    System.out.println("  Number of errors (" + newNumOfErrorsOffl + ") + fatals (" + newNumOfFatalsOffl + ") + warnings (" + newNumOfWarningsOffl + ") + infos (" + newNumOfInfosOffl + ") is: " +  newdocIsValidOffl + ".");
    System.out.println();
    System.out.println();
    // newDoc.printErrors(System.out);

  }
}
