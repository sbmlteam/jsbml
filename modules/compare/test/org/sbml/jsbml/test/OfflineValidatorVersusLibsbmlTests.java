/*
 * 
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.LoggingValidationContext;
import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.libsbml.libsbmlConstants;

/**
 * Utility class that is used to test the jsbml offline validator. It compare the results of the libsbml validator
 * with the one of the jsbml offline validator, making sure that the same errors are reported the same number of times.
 * 
 * <p>You need a local installation of libSBML to run this class.</p>
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public class OfflineValidatorVersusLibsbmlTests {

  /**
   * This value should be set to true after the static block is executed
   * otherwise, there is not need to run this test !! You will need
   * to configure libSBML properly before being able to make use of
   * this class.
   * 
   */
  private static boolean isLibSBMLAvailable = false;

  static {
    try {
      System.loadLibrary("sbmlj");

      Class.forName("org.sbml.libsbml.libsbml");

      isLibSBMLAvailable = true;

    } catch (SecurityException e) {
      e.printStackTrace();

      System.out
      .println("SecurityException exception catched: Could not load libsbml library.");
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      System.out
      .println("UnsatisfiedLinkError exception catched: Could not load libsbml library.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

      System.out
      .println("ClassNotFoundException exception catched: Could not load libsbml class file.");

    } catch (RuntimeException e) {
      e.printStackTrace();
      System.out
      .println("Could not load libsbml.\n "
          + "Control that the libsbmlj.jar that you are using is synchronized with your current libSBML installation.");

    }
  }


  private static int nbFileValidated = 0;

  private static int totalFileTested  = 0;
  private static int filesCorrectly   = 0;

  private static String filter = null;

  private static Map<String, Exception> exceptions = new HashMap<String, Exception>();
  private static Set<Integer> notDetected = new TreeSet<Integer>();
  private static Map<Integer, String>                  notDetectedFiles =
      new HashMap<Integer, String>();
  private static long                                  readTime         = 0;

  private static Map<String, LoggingValidationContext> contextCache     =
      new HashMap<String, LoggingValidationContext>();

  private static Set<Integer> notImplementedConstraints = new HashSet<Integer>();  

  /**
   * @param args
   */
  public static void main(String[] args) {

    if (args.length < 1) {
      System.out.println(
        "Usage: java org.sbml.jsbml.test.OfflineValidatorTests testDataFolder ");
      System.out.println();
      System.out.println(
        "testDataFolder - root folder which contains some xml files");
      System.out.println(
        "\n Example: \n java org.sbml.jsbml.test.OfflineValidatorTests . \n "
          + "This arguments will start the test for all SBML files in the current folder.");
      System.exit(0);
    }

    if (args.length >= 2) {
      filter = args[1];
      System.out.println("Filter = " + filter);
    }    
    
    final File testDataDir = new File(args[0]);

    if (!testDataDir.isDirectory()) {
      System.out.println("First argument is not a directory!");
      System.exit(0);
    }
    
    if (!isLibSBMLAvailable) {
      System.out.println("LibSBML does not sems to be configured properly on your system!");
      System.exit(0);      
    }

    notImplementedConstraints.addAll(Arrays.asList(10201, 10202, 10203, 10204, 10205, 10206, 10207,
      10309, 10310, 10311, 10401, 10402, 10403, 10801, 10804, 21006, 
      10501, 10503, 10512, 10513, 10522, 10523, 10531, 10532, 10533, 10534, 10541, 10542, 10551, 10562, 10565,
      10101, 10102, 10103, 20101, 20102, 20103, 20301));
    
    long init = Calendar.getInstance().getTimeInMillis();

    validateDirectory(testDataDir);

    if (exceptions.size() > 0) {
      System.out.println();
      printStrongHLine();
      System.out.println("Unexpected EXCEPTIONS that happened during validation or reading of an SBML file:");
      printStrongHLine();
      for (String key : exceptions.keySet()){
        System.out.println("There was a exception in " + key + ":");
        Exception e = exceptions.get(key);
        e.printStackTrace(System.out);
        System.out.println();
        System.out.println();
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

    System.out.println("\n\nNumber of files correctly validated: "
        + (nbFileValidated - notDetected.size()) + " out of " + nbFileValidated);
    System.out.println("\nIncorrect constraints list: ");
    
    Integer previous = 0;
    Integer[] notDetectedA = notDetected.toArray(new Integer[notDetected.size()]);
    
    for (int i = 0; i < notDetectedA.length; i++) {
      Integer errorCode = notDetectedA[i];
      
      if (i == 0) {
        previous = errorCode;
      }

      Integer errorBase = previous - (previous % 100);
      
      if ((errorCode - errorBase) > 100) {
        System.out.println();
      }
      System.out.print(errorCode + ", ");
      previous = errorCode;
    }
    
    System.out.println("\n\nNumber of files correctly validated: "
      + filesCorrectly + " out of " + totalFileTested);
    System.out.println("Didn't detect the following broken constraints:");

    for (Integer i : notDetected) {

      String out = i + " in " + notDetectedFiles.get(i);
      System.out.println(out);
    }
    
  }


  /**
   * 
   * 
   * @param dir the directory
   */
  private static void validateDirectory(File dir) {
    File[] files = dir.listFiles(new FileFilter() {

      @Override
      public boolean accept(File pathname) {
        boolean accept = pathname.getName().endsWith(".xml");
        
        if (accept && filter != null) {
          accept = pathname.getName().matches(filter);
        }
        return accept;
      }
    });

    // System.out.println("Files: " + files.length);
    for (File f : files) {
      totalFileTested++;
      validateFile(f);
    }
  }


  /**
   * 
   * 
   * @param file the file to validate
   */
  private static void validateFile(File file) {
    String name = file.getName();

    printStrongHLine();
    System.out.println("File: " + name);

    try {
      long startRead = Calendar.getInstance().getTimeInMillis();
      SBMLDocument doc = new SBMLReader().readSBML(file);
      readTime += (Calendar.getInstance().getTimeInMillis() - startRead);

      LoggingValidationContext ctx = getContext(doc);

      ctx.validate(doc);
      SBMLErrorLog log = ctx.getErrorLog();

      int errors = log.getNumErrors();

      boolean constraintBroken = false;

      System.out.println(errors + " constraints broken with the JSBML offline validator.");
      for (SBMLError e : log.getValidationErrors()) {
        int errorCode = e.getCode();
        
        // TODO - count each errorCode
      }

      // TODO - do the validation with libsbml and count each errorCode
      org.sbml.libsbml.SBMLDocument ldoc = new org.sbml.libsbml.SBMLReader().readSBML(file.getAbsolutePath());
      ldoc.setConsistencyChecks(libsbmlConstants.LIBSBML_CAT_UNITS_CONSISTENCY, true);
      long lerrors = ldoc.checkConsistency();

      System.out.println(lerrors + " constraints broken with the libSBML validator.");

      // TODO - compare both results and report error code where the numbers differ between jsbml and libsbml
      
      // TODO - reomve for now the error code in the 
      
      if (!constraintBroken) {
        filesCorrectly++;
        System.out.println("PASSED");
      } else {
        // TODO - go through the list of problematic error code notImplementedConstraints set or above 22000.
        int errorCode = -1;
        didNotDetect(errorCode, file.getName());
        System.out.println("FAILED!!!");
      }
    } catch (Exception e) {
      exceptions.put(name, e);
//      e.printStackTrace(); 
    }

    System.out.println();
    System.out.println();
  }


  /**
   * 
   * 
   * @param errorCode
   * @param fileName
   */
  private static void didNotDetect(int errorCode, String fileName) {
    if (notDetected.add(errorCode)) {
      notDetectedFiles.put(errorCode, fileName);
    } else {
      String files = notDetectedFiles.get(errorCode);

      files += ", " + fileName;
      notDetectedFiles.put(errorCode, files);
    }
  }


  private static LoggingValidationContext getContext(SBMLDocument doc) {
    String key = "l" + doc.getLevel() + "v" + doc.getVersion();
    LoggingValidationContext ctx = contextCache.get(key);

    // If no context was in cache create new one
    if (ctx == null) {
      ctx = new LoggingValidationContext(doc.getLevel(), doc.getVersion());
      ctx.enableCheckCategories(CHECK_CATEGORY.values(), true);
      ctx.loadConstraints(SBMLDocument.class);
      contextCache.put(key, ctx);
    } else {
      
      // Reset context if necessary
      ctx.clearErrorLog();
      
      if (ctx.getConstraintType() != SBMLDocument.class) {
        ctx.loadConstraints(SBMLDocument.class);
      }
    }
   
    return ctx;
  }


  private static void printStrongHLine() {

    System.out.println("==============================");
  }
}
