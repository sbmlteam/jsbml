/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * @author Nicolas Rodriguez and Roman Schulte
 * @version $Rev$
 * @since 1.2
 */
public class OfflineValidatorTests {

  /**
   * @param args
   */

  // private static int dirsValidated = 0;
  // private static int dirsMissed = 0;

  private static int          totalFileTested = 0;
  private static int          filesCorrectly  = 0;

  private static String       filter          = "";

  private static Set<Integer> notDetected     = new HashSet<Integer>();
  private static long         readTime        = 0;


  public static void main(String[] args) {
    
    if (args.length < 1) {
      System.out.println(
        "Usage: java org.sbml.jsbml.test.OfflineValidatorTests testDataFolder start[:end] [containsString]");
      System.out.println();
      System.out.println(
        "testDataFolder - root folder which contains a child folder for every error code");
      System.out.println(
        "start          - first error code to be checked. Error codes could be started by package name (layout-20613 == 6020613).");
      System.out.println(
        "end            - last error code to check. Must follow at start sperated by a colon ':'");
      System.out.println(
        "containsString - a String which must be contained in every file name.");
      System.out.println(
        "\n Example: \n java org.sbml.jsbml.test.OfflineValidatorTests ./ 20600:20700 l3v1 \n "
          + "This arguments will start the test for all error codes from 20600 up to 20700, but only uses the test files which contains the String 'l3v1'.");
      System.exit(0);
    }

    if (args.length > 2) {
      filter = args[2];
    }

    final File testDataDir = new File(args[0]);
    final String range = args[1];
    final String[] blocks = range.split(":");

    final int startCode = SBMLPackage.convertStringToErrorCode(blocks[0]);
    int endCode = startCode;

    if (blocks.length > 1) {
      endCode = SBMLPackage.convertStringToErrorCode(blocks[1]);
    }

    if (!testDataDir.isDirectory()) {
      System.out.println("First arg ist not a directory!");
      System.exit(0);
    }

    System.out.println("Start tests");
    long init = Calendar.getInstance().getTimeInMillis();

    for (int code = startCode; code <= endCode; code++) {
      File dir = new File(testDataDir,
        SBMLPackage.convertErrorCodeToString(code, false));

      if (dir.isDirectory()) {
        // dirsValidated++;
        validateDirectory(dir, code);
      } else {
        // dirsMissed++;
        System.out.println("No directory found for error code " + code);
      }
    }

    long end = Calendar.getInstance().getTimeInMillis();
    double nbSecondes = (end - init) / 1000.0;
    double nbSecondesRead = readTime / 1000.0;
    double nbSecondesValidating = ((end - init) - readTime) / 1000.0;

    if (nbSecondes > 120) {
      System.out.println("It took " + nbSecondes / 60 + " minutes.");
    } else {
      System.out.println("It took " + nbSecondes + " secondes.");
    }

    System.out.println("Reading: " + nbSecondesRead + " secondes.");
    System.out.println("Validating: " + nbSecondesValidating + " secondes.");

    System.out.println("\n\n Number of files correctly validated: "
      + filesCorrectly + " out of " + totalFileTested);
    System.out.println("Didn't detect the following broken constraints:");

    for (Integer i : notDetected) {
      System.out.println(i);
    }
  }


  private static void validateDirectory(File dir, int errorCode) {
    File[] files = dir.listFiles(new FileFilter() {

      @Override
      public boolean accept(File pathname) {
        return pathname.getName().endsWith(".xml")
          && pathname.getName().contains(filter);
      }
    });

    // System.out.println("Files: " + files.length);
    for (File f : files) {
      totalFileTested++;
      validateFile(f, errorCode);
    }
  }


  private static void validateFile(File file, int errorCode) {
    String name = file.getName();

    boolean shouldPass = name.contains("pass");

    System.out.println(
      "Start validating " + name + ". Should pass: " + shouldPass);
    try {
      long startRead = Calendar.getInstance().getTimeInMillis();
      SBMLDocument doc = new SBMLReader().readSBML(file);
      readTime += (Calendar.getInstance().getTimeInMillis() - startRead);
      
      int errors = doc.checkConsistencyOffline();
      
      if (errors > 0) {
        System.out.println(errors + " constraints broken.");
        if (shouldPass) {
          System.out.println("Did not pass as expected!");
        } else {
          SBMLErrorLog log = doc.getErrorLog();

          for (SBMLError e : log.getValidationErrors()) {
            if (e.getCode() == errorCode) {
              filesCorrectly++;
              System.out.println(
                "Constraint " + errorCode + " was broken as expected.");
              return;
            }
          }

          notDetected.add(errorCode);
          System.out.println(
            "Didn't detected broken Constraint " + errorCode + "!");
        }

      } else {
        if (shouldPass) {
          filesCorrectly++;
          System.out.println("Passed as expected");
        } else {
          notDetected.add(errorCode);
          System.out.println(
            "Didn't detected broken Constraint " + errorCode + "!");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
