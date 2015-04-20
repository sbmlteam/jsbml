/*
 * $Id: PerformanceTest.java 2134 2015-03-13 13:02:35Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/core/test/org/sbml/jsbml/test/PerformanceTest.java $
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
import java.io.FileFilter;
import java.io.IOException;
import java.util.Calendar;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;

/**
 * @author Nicolas Rodriguez
 * @version $Rev: 2134 $
 * @since 1.0
 */
public class PerformanceTest {

  /**
   * Test class used to check the jsbml speed to read and write SBML models.
   * 
   * @param args
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException {

    if (args.length < 1) {
      System.out.println("Usage: java org.sbml.jsbml.test.PerformanceTest sbmlFileName|folder");
      System.exit(0);
    }

    // this JOptionPane is added here to be able to start visualVM profiling
    // before the reading or writing is started.
    // JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");

    File argsAsFile = new File(args[0]);
    File[] files = null;

    if (argsAsFile.isDirectory())
    {
      files = argsAsFile.listFiles(new FileFilter() {

        @Override
        public boolean accept(File pathname)
        {
          if (pathname.getName().contains("-jsbml"))
          {
            return false;
          }

          if (pathname.getName().endsWith(".xml"))
          {
            return true;
          }

          return false;
        }
      });
    }
    else
    {
      files = new File[1];
      files[0] = argsAsFile;
    }

    double globalInit = Calendar.getInstance().getTimeInMillis();
    double globalEnd = 0;
    double globalReadingMs = 0;
    
    for (File file : files)
    {

      double init = Calendar.getInstance().getTimeInMillis();
      System.out.println(Calendar.getInstance().getTime());

      String fileName = file.getAbsolutePath();
      String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbml.xml");

      System.out.printf("Reading %s and writing %s (size=%dKb)\n",
        fileName, jsbmlWriteFileName, file.length()/1024);      

      SBMLDocument testDocument;
      long afterRead = 0;
      try {
        testDocument = new SBMLReader().readSBMLFromFile(fileName);
        System.out.printf("Reading done\n");
        System.out.println(Calendar.getInstance().getTime());
        afterRead = Calendar.getInstance().getTimeInMillis();

        System.out.printf("Starting writing\n");

        new SBMLWriter().write(testDocument.clone(), jsbmlWriteFileName);
      }
      catch (XMLStreamException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }

      System.out.println(Calendar.getInstance().getTime());
      double end = Calendar.getInstance().getTimeInMillis();
      globalEnd = end;
      double nbMilliseconds = end - init;
      double nbSeconds = nbMilliseconds / 1000;
      double nbSecondsRead = (afterRead - init)/1000;
      double nbSecondsWrite = (end - afterRead)/1000;
      globalReadingMs += (afterRead - init);
      
      if (nbSeconds > 120) {
        System.out.println("It took " + nbSeconds/60 + " minutes.");
      } else {
        System.out.println("It took " + nbSeconds + " seconds.");
      }
      System.out.println("Reading: " + nbSecondsRead + " seconds.");
      System.out.println("Writing: " + nbSecondsWrite + " seconds.");

      if (files.length == 1)
      {
        System.out.println((int)nbMilliseconds);
      }
    }

    if (files.length > 1)
    {
      double globalNbMilliseconds = globalEnd - globalInit;
      double globalSeconds = globalNbMilliseconds / 1000;

      System.out.println("Reading and writing " + files.length + " models took : " + globalSeconds + 
        " seconds (" + globalReadingMs + " to read, " + (globalNbMilliseconds - globalReadingMs) + " to write).");
      System.out.println("Mean per model = " + globalSeconds / files.length + " seconds (" + globalNbMilliseconds / files.length + " ms).");

      System.out.println((int)globalNbMilliseconds);
    }
  }

}
