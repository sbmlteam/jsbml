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
package org.sbml.jsbml.test;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * This class is just for temporarily testing cases and needs several
 * modifications of source code to work probably. Don't use this class on your
 * own.
 * 
 * @author Roman
 * @since 1.2
 */
public final class OfflineValidatorPerformanceTest {

  public static void main(String[] args) throws IOException, XMLStreamException {
    // TODO Auto-generated method stub
    if (args.length == 0)
    {
      System.out.println("No file path given as argument");
      return;
    }
    
    final File testFile = new File(args[0]);
    System.out.println("Read file...");
    long startTime = System.currentTimeMillis();
    
    SBMLDocument doc = new SBMLReader().readSBML(testFile);
    long time =  (System.currentTimeMillis() - startTime);
    
    long secTime = time / 1_000l;
    
    long min = secTime / 60;
    long sec = secTime % 60;
    System.out.println("Read time: " + min + ":" + sec + " (" + time + ")");
    
    startTime = System.currentTimeMillis();
    int numErrors = doc.checkConsistencyOffline();
    time =  (System.currentTimeMillis() - startTime);
    
    secTime = time / 1_000l;
    
    min = secTime / 60;
    sec = secTime % 60;
    System.out.println("Validation time: " + min + ":" + sec + " (" + time + ")");
    System.out.println("Found " + numErrors + " Errors:");
    
    SBMLErrorLog log = doc.getErrorLog();
    for (int i = 0; i < log.getNumErrors(); i++)
    {
      SBMLError e = log.getError(i);
      System.out.println("--------------------------");
      System.out.println(e);
      System.out.println("--------------------------");
      System.out.println();
    }
  }

}
