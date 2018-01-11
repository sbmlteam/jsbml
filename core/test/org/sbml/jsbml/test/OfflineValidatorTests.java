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
import java.io.FileFilter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.util.converters.ExpandFunctionDefinitionConverter;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.LoggingValidationContext;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * Utility class that is used to test the jsbml offline validator over the libsbml validator test suite files.
 * 
 * @author Nicolas Rodriguez and Roman Schulte
 * @since 1.2
 */
public class OfflineValidatorTests {

  /**
   * Enables or not unit validation.
   */
  private static boolean ENABLE_UNITS_VALIDATION = true;

  private static int nbDirValidated = 0;
  // private static int dirsMissed = 0;

  private static int                                   totalFileTested  = 0;
  private static int                                   filesCorrectly   = 0;

  private static String                                filter           = "";

  private static Map<String, Exception>                exceptions       =
      new HashMap<String, Exception>();
  private static Set<Integer>                          notDetected      =
      new TreeSet<Integer>();
  private static Map<Integer, String>                  notDetectedFiles =
      new HashMap<Integer, String>();
  private static long                                  readTime         = 0;

  private static Map<String, LoggingValidationContext> contextCache     =
      new HashMap<String, LoggingValidationContext>();

  private static Logger logger = Logger.getLogger(OfflineValidatorTests.class);

  /**
   * @param args
   */
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
          "end            - last error code to check. Must follow at start seperated by a colon ':'");
      System.out.println(
          "containsString - a String which must be contained in every file name.");
      System.out.println(
        "\n Example: \n java org.sbml.jsbml.test.OfflineValidatorTests ./ 20600:20700 l3v1 \n "
            + "This arguments will start the test for all error codes from 20600 up to 20700, but only uses the test files which contains the String 'l3v1'.");
      System.exit(0);
    }

    logger.info("Starting tests...");

    if (args.length > 2) {
      filter = args[2];
    }

    final File testDataDir = new File(args[0]);
    final String range = args[1];
    final String[] blocks = range.split(":");

    if (blocks[0].contains("-")) {
      blocks[0] = correctPackageErrorCode(blocks[0]);
    }

    final int startCode = Integer.parseInt(blocks[0]);
    int endCode = startCode;

    if (blocks.length > 1) {
      if (blocks[1].contains("-")) {
        blocks[1] = correctPackageErrorCode(blocks[1]);
      }
      endCode = Integer.parseInt(blocks[1]);
    }

    if (!testDataDir.isDirectory()) {
      System.out.println("First arg ist not a directory!");
      System.exit(0);
    }

    System.out.println(
      "Start tests (Range from " + startCode + " to " + endCode + ")");
    System.out.println();
    System.out.println();

    long init = Calendar.getInstance().getTimeInMillis();

    for (int code = startCode; code <= endCode; code++) {
      File dir = new File(testDataDir, "" + code);

      if (dir.isDirectory()) {
        nbDirValidated++;

        validateDirectory(dir, code);
      } else if (code > 1000000) {
        String codeStr = Integer.toString(code);
        String packageShortLabel = getPackageLabel(codeStr);

        // Just taking the last 5 digit from the error code to build the directory name
        dir = new File(testDataDir, packageShortLabel + "-" + codeStr.substring(codeStr.length() - 5));

        if (dir.exists()) {
          nbDirValidated++;

          validateDirectory(dir, code);
        }
        // dirsMissed++;
        // System.out.println("No directory found for error code " + code);
      }
    }

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

    System.out.println("\n\nNumber of constraints correctly validated: "
        + (nbDirValidated - notDetected.size()) + " out of " + nbDirValidated);
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
   * @param code
   * @return
   */
  private static String getPackageLabel(String codeStr)
  {
    switch(codeStr.charAt(0)) {
    case '1':
      return "comp";
    case '2':
      return "fbc";
    case '3':
      return "qual";
    case '4':
      return "groups";
    case '6':
      return "layout";
    case '7':
      return "multi";
    case '8':
      return "arrays";

    }

    return "";
  }


  /**
   * Corrects an error code that contain 'packageLabel-' and transforms it
   * into a valid integer.
   * 
   * <p>For example 'layout-20301' will be transformed into '6020301'.</p>
   * 
   * @param errorCode an error code that contain '-'
   * @return a corrected error code if the prefix contain a known SBML L3 package, the
   * unmodified argument otherwise.
   */
  private static String correctPackageErrorCode(String errorCode)
  {
    if (! errorCode.contains("-"))
    {
      return errorCode;
    }

    String[] x = errorCode.split("-");

    if (x.length == 2)
    {
      String prefix = "";

      switch(x[0]) {

      case "layout": {
        prefix = "60";
        break;
      }
      case "comp": {
        prefix = "10";
        break;
      }
      case "fbc": {
        prefix = "20";
        break;
      }
      case "qual": {
        prefix = "30";
        break;
      }
      case "groups": {
        prefix = "40";
        break;
      }
      case "arrays": {
        prefix = "80";
        break;
      }
      case "multi": {
        prefix = "70";
        break;
      }

      }

      return prefix + x[1];
    }

    return errorCode;
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

    System.out.println("Constraint " + errorCode);
    printStrongHLine();
    System.out.println("File: " + name);
    System.out.println("Should pass: " + shouldPass);

    try {
      long startRead = Calendar.getInstance().getTimeInMillis();
      SBMLDocument doc = new SBMLReader().readSBML(file);
      readTime += (Calendar.getInstance().getTimeInMillis() - startRead);

      LoggingValidationContext ctx = getContext(doc);

      SBMLDocument docToValidate = doc;
      
      if (doc.isSetModel() && doc.getModel().getFunctionDefinitionCount() > 0) {
        ExpandFunctionDefinitionConverter converter = new ExpandFunctionDefinitionConverter();
        docToValidate = converter.convert(doc);
      }
      ctx.validate(docToValidate);

      SBMLErrorLog log = ctx.getErrorLog();

      int errors = log.getNumErrors();

      boolean constraintBroken = false;

      System.out.println(errors + " constraints broken.");
      for (SBMLError e : log.getValidationErrors()) {
        if (e.getCode() == errorCode) {

          System.out.println("Constraint " + errorCode + " was broken.");

          constraintBroken = true;
          break;
        }
      }

      if (constraintBroken == !shouldPass) {
        filesCorrectly++;
        System.out.println("PASSED");
      } else {
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
    int l = doc.getLevel(), v = doc.getVersion();
    String key = "l" + l + "v" + v;
    LoggingValidationContext ctx = contextCache.get(key);

    // If no context was in cache create new one
    if (ctx == null) {
      ctx = new LoggingValidationContext(l, v);
      ctx.enableCheckCategories(CHECK_CATEGORY.values(), true);
      // TODO: check also if other categories should be disabled
      if (!ENABLE_UNITS_VALIDATION) {
        ctx.enableCheckCategory(CHECK_CATEGORY.UNITS_CONSISTENCY, false);
      }

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
