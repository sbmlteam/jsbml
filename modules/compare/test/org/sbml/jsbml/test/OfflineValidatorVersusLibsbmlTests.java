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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.LoggingValidationContext;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;
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
   * Enables or not unit validation.
   */
  private static boolean ENABLE_UNITS_VALIDATION = true;

  /**
   * The file size limit in kilobytes above which we don't do the validation
   */
  private static int FILE_SIZE_LIMIT = 5000;

  /**
   * Tells if we should print {@link #PRINT_ERROR_LIMIT} error messages per error code
   */
  private static boolean PRINT_ERROR_SAMPLE = true;

  /**
   * Indicates the maximum of error we should print for each error code, if {@link #PRINT_ERROR_SAMPLE} is {@code true}.
   */
  private static final int PRINT_ERROR_LIMIT = 3;


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
      System.out.println("SecurityException exception catched: Could not load libsbml library.");
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      System.out.println("UnsatisfiedLinkError exception catched: Could not load libsbml library.");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println("ClassNotFoundException exception catched: Could not load libsbml class file.");
    } catch (RuntimeException e) {
      e.printStackTrace();
      System.out.println("Could not load libsbml.\n "
          + "Control that the libsbmlj.jar that you are using is synchronized with your current libSBML installation.");
    }
  }


  private static int nbFileValidated = 0;

  private static int totalFileTested  = 0;
  private static int filesCorrectly   = 0;
  private static int nbFilesCorrectForImplementedConstraints   = 0;

  private static String filter = null;

  private static Map<String, Exception> exceptions = new HashMap<String, Exception>();
  private static Set<Integer> notDetected = new TreeSet<Integer>();
  private static Map<String, String> notDetectedFiles = new TreeMap<String, String>();
  private static Map<Integer, Integer> differencesMap = new TreeMap<Integer, Integer>();
  private static long globalJSBMLReadTime = 0;
  private static long globalJSBMLValidationTime = 0;
  private static long globalLibSBMLReadTime = 0;
  private static long globalLibSBMLValidationTime = 0;

  private static Map<String, LoggingValidationContext> contextCache = new HashMap<String, LoggingValidationContext>();

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

    // this JOptionPane is added here to be able to start visualVM profiling
    // before the reading or writing is started.
    // JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");

    notImplementedConstraints.addAll(Arrays.asList(10201, 10202, 10203, 10204, 10205, 10206, 10207,
      10309, 10310, 10311, 10401, 10402, 10403, 10801, 10804, 21006,
      10501, 10503, 10511, 10512, 10513, 10521, 10522, 10523, 10531, 10532, 10533, 10534, 10541, 10542, 10551, 10562, 10563, 10565,
      10101, 10102, 10103, 20101, 20102, 20103, 20301));

    // long init = Calendar.getInstance().getTimeInMillis();

    validateDirectory(testDataDir);

    double nbSecondesRead = globalJSBMLReadTime / 1000.0;
    double nbSecondesValidating = globalJSBMLValidationTime / 1000.0;
    double nbSecondes = nbSecondesRead + nbSecondesValidating;

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

    System.out.println("Units validation was " + (ENABLE_UNITS_VALIDATION ? "on" : "off") + "\n\n");

    if (nbSecondes > 120) {
      System.out.println("It took " + nbSecondes / 60 + " minutes for JSBML.");
    } else {
      System.out.println("It took " + nbSecondes + " secondes for JSBML.");
    }

    System.out.println("Reading: " + nbSecondesRead + " seconds for JSBML (" + nbSecondesRead/totalFileTested + " mean per file).");
    System.out.println("Validating: " + nbSecondesValidating + " seconds for JSBML (" + nbSecondesValidating/totalFileTested + " mean per file).\n");

    nbSecondesRead = globalLibSBMLReadTime / 1000.0;
    nbSecondesValidating = globalLibSBMLValidationTime / 1000.0;
    nbSecondes = nbSecondesRead + nbSecondesValidating;

    if (nbSecondes > 120) {
      System.out.println("It took " + nbSecondes / 60 + " minutes for LibSBML.");
    } else {
      System.out.println("It took " + nbSecondes + " secondes for LibSBML.");
    }

    System.out.println("Reading: " + nbSecondesRead + " seconds for LibSBML (" + nbSecondesRead/totalFileTested + " mean per file).");
    System.out.println("Validating: " + nbSecondesValidating + " seconds for LibSBML (" + nbSecondesValidating/totalFileTested + " mean per file).");

    if (notDetectedFiles.size() > 0) {
      System.out.println("\nList of incorrectly detected constraints in the following files :\n");

      for (String fileName : notDetectedFiles.keySet()) {

        String out = fileName + ": " + notDetectedFiles.get(fileName);
        System.out.println(out);
      }
    }

    System.out.println("\n\nNumber of files correctly validated: "
        + filesCorrectly + " out of " + totalFileTested);
    System.out.println("\nNumber of files correctly validating the implemented constraints: "
        + (filesCorrectly + nbFilesCorrectForImplementedConstraints) + " out of " + totalFileTested);

    System.out.println("\nIncorrect constraints list (errors followed by a '!' are errors that we know are not implemented): ");

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

      if (!notImplementedConstraints.contains(errorCode)) {
        System.out.print(errorCode + " (" + differencesMap.get(errorCode) + "), ");
      } else {
        System.out.print("" + errorCode + " (" + differencesMap.get(errorCode) + ")!, ");
      }

      previous = errorCode;
    }

  }


  /**
   * Validates an entire directory, taking "*.xml" and files not bigger than {@link #FILE_SIZE_LIMIT}kb.
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

        double fileSizeKB = pathname.length()/1024;

        if (fileSizeKB > FILE_SIZE_LIMIT) {
          return false;
        }

        return accept;
      }
    });

    for (File f : files) {
      totalFileTested++;
      validateFile(f);
    }
  }


  /**
   * Validates a given file with jsbml and libsbml and compares the results.
   * 
   * @param file the file to validate
   */
  private static void validateFile(File file) {
    String name = file.getName();

    printStrongHLine();

    double fileSizeKB = file.length()/1024;

    try {
      long startRead = Calendar.getInstance().getTimeInMillis();
      SBMLDocument doc = new SBMLReader().readSBML(file);

      System.out.println("File: " + name + " (size= " + fileSizeKB + "kb, SBML L" + doc.getLevel() + "V" + doc.getVersion() + ")");

      long endRead = Calendar.getInstance().getTimeInMillis();

      LoggingValidationContext ctx = getContext(doc);

      ctx.validate(doc);

      long endValidation = Calendar.getInstance().getTimeInMillis();

      SBMLErrorLog log = ctx.getErrorLog();

      int errors = log.getNumErrors();

      double readTime = endRead - startRead;
      globalJSBMLReadTime += readTime;
      double validationTime = endValidation - endRead;
      globalJSBMLValidationTime += validationTime;

      boolean constraintBroken = false;
      Map<Integer, Integer> jsbmlErrorCount = new TreeMap<Integer, Integer>();
      Map<Integer, Integer> libsbmlErrorCount = new TreeMap<Integer, Integer>();
      HashSet<Integer> wronglyValidatedConstraintSet = new HashSet<Integer>();

      System.out.println(errors + " constraints broken with the JSBML offline validator (" + readTime/1000 + "s to read, " + validationTime/1000 + "s to validate).");
      for (SBMLError e : log.getValidationErrors()) {
        int errorCode = e.getCode();

        // System.out.println("JSBML - error " + errorCode);

        // count each errorCode
        if (jsbmlErrorCount.get(errorCode) == null) { // TODO - check that the JSBML SBMLerrors have the same 'severity' than the libsbml SBMLerrors
          jsbmlErrorCount.put(errorCode, 1);
        } else {
          jsbmlErrorCount.put(errorCode, jsbmlErrorCount.get(errorCode) + 1);
        }

        if (PRINT_ERROR_SAMPLE && jsbmlErrorCount.get(errorCode) < PRINT_ERROR_LIMIT) {
          System.out.println('\n' + e.toString());
        }
      }

      if (PRINT_ERROR_SAMPLE) {
        System.out.println();
      }

      // check if there are any empty ListOf in the SBMLDocument and report them as a new Error
      List<? extends TreeNode> emptyListOfs = doc.filter(new Filter() {

        @Override
        public boolean accepts(Object o) {

          if (o instanceof ListOf<?>) {
            return ((ListOf<?>) o).size() == 0;
          }

          return false;
        }
      });
      if (emptyListOfs.size() > 0) {
        jsbmlErrorCount.put(26000, emptyListOfs.size());

        for (TreeNode emptyListOf : emptyListOfs) {
          System.out.println("Empty ListOf found - " + ((ListOf<?>) emptyListOf).getSBaseListType() + ", element name = " + ((ListOf<?>) emptyListOf).getElementName());
        }
      }

      // do the validation with libsbml and count each errorCode
      startRead = Calendar.getInstance().getTimeInMillis();
      org.sbml.libsbml.SBMLDocument ldoc = new org.sbml.libsbml.SBMLReader().readSBML(file.getAbsolutePath());
      endRead = Calendar.getInstance().getTimeInMillis();

      if (ENABLE_UNITS_VALIDATION) {
        ldoc.setConsistencyChecks(libsbmlConstants.LIBSBML_CAT_UNITS_CONSISTENCY, true);
      } else {
        ldoc.setConsistencyChecks(libsbmlConstants.LIBSBML_CAT_UNITS_CONSISTENCY, false);
      }
      long lerrors = ldoc.checkConsistency();
      endValidation = Calendar.getInstance().getTimeInMillis();

      readTime = endRead - startRead;
      globalLibSBMLReadTime += readTime;
      validationTime = endValidation - endRead;
      globalLibSBMLValidationTime += validationTime;

      System.out.println(lerrors + " constraints broken with the libSBML validator (" + readTime/1000 + "s to read, " + validationTime/1000 + "s to validate).\n");

      for (int i = 0; i < lerrors; i++) {
        org.sbml.libsbml.SBMLError error = ldoc.getError(i);
        int errorCode = (int) error.getErrorId();

        // System.out.println("libSBML - error " + errorCode);

        // count each errorCode
        if (libsbmlErrorCount.get(errorCode) == null) {
          libsbmlErrorCount.put(errorCode, 1);
        } else {
          libsbmlErrorCount.put(errorCode, libsbmlErrorCount.get(errorCode) + 1);
        }

        if (PRINT_ERROR_SAMPLE && libsbmlErrorCount.get(errorCode) < PRINT_ERROR_LIMIT) {
          System.out.println('\n' + toString(error));
        }

      }

      if (PRINT_ERROR_SAMPLE) {
        System.out.println();
      }

      // compare both results and report error code where the numbers differ between jsbml and libsbml
      Set<Integer> wrongConstraintCodes = new HashSet<Integer>();
      wrongConstraintCodes.addAll(jsbmlErrorCount.keySet());
      wrongConstraintCodes.addAll(libsbmlErrorCount.keySet());
      boolean fileCorrectForNow = true;

      for (Integer errorCode : wrongConstraintCodes)
      {
        int jsbmlErrorNb = jsbmlErrorCount.get(errorCode) == null ? 0 : jsbmlErrorCount.get(errorCode);
        int libsbmlErrorNb = libsbmlErrorCount.get(errorCode) == null ? 0 : libsbmlErrorCount.get(errorCode);

        System.out.println("For validation '" + errorCode + "' libsbml = " + libsbmlErrorNb + ", jsbml = " + jsbmlErrorNb);

        if (differencesMap.get(errorCode) == null) {
          differencesMap.put(errorCode, 0);
        }

        if (libsbmlErrorNb == 0)
        {
          System.out.println("ERROR: libSBML didn't detect at all constraint '" + errorCode + "'");
          constraintBroken = true;
          wronglyValidatedConstraintSet.add(errorCode);
          differencesMap.put(errorCode, differencesMap.get(errorCode) + jsbmlErrorNb);

          if (!(notImplementedConstraints.contains(errorCode) || errorCode > 80000)) {
            fileCorrectForNow = false;
          }
        }
        else if (jsbmlErrorNb != libsbmlErrorNb)
        {
          if (errorCode == SBMLErrorCodes.CORE_99505 && doc.getModel().getEventCount() > 0) {
            if (libsbmlErrorNb == (jsbmlErrorNb - doc.getModel().getEventCount())) {
              System.out.println("error '" + errorCode + "' is properly detected by jsbml (problem with trigger ignored)");
              System.out.println();
              continue;
            }
          }

          System.out.println("ERROR: libSBML didn't detect the same number of SBMLError for constraint '" + errorCode); //  + "' libsbml = " + libsbmlErrorNb + ", jsbml = " + jsbmlErrorNb
          constraintBroken = true;
          wronglyValidatedConstraintSet.add(errorCode);
          differencesMap.put(errorCode, differencesMap.get(errorCode) + Math.abs(jsbmlErrorNb - libsbmlErrorNb));

          if (!(notImplementedConstraints.contains(errorCode) || errorCode > 80000)) {
            fileCorrectForNow = false;
          }
        }
        else
        {
          System.out.println("error '" + errorCode + "' is properly detected by jsbml");
        }
        System.out.println();
      }


      if (!constraintBroken) {
        filesCorrectly++;
        System.out.println("PASSED");
      } else {
        if (fileCorrectForNow) {
          nbFilesCorrectForImplementedConstraints++;
        }
        for (Integer errorCode : wronglyValidatedConstraintSet) {
          didNotDetect(errorCode, file.getName());
        }
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
   * @param validationErrors
   * @return
   */
  private static Set<CHECK_CATEGORY> analyseSBMLerrorLog2(List<SBMLError> validationErrors)
  {
    Set<CHECK_CATEGORY> ignoredCategories = new HashSet<CHECK_CATEGORY>();
    CHECK_CATEGORY latest = CHECK_CATEGORY.MODELING_PRACTICE;

    /* order is:
    identifier consistency
    general consistency
    sbo
    math
    units
    overdetermined
    modelling practice
     */

    for (SBMLError e : validationErrors) {
      String category = e.getCategory();
      String severity = e.getSeverity();

      if (severity != null && (severity.equalsIgnoreCase("error") || severity.equalsIgnoreCase("fatal"))) {
        // the category we are in will be the latest one.

        // System.out.println("DEBUG - SBMLError.category = '" + category + "'");

        switch(category) {

        case "SBML identifier consistency":
          ignoredCategories.add(CHECK_CATEGORY.GENERAL_CONSISTENCY);

        case "General SBML conformance":
        case "SBML component consistency":
          ignoredCategories.add(CHECK_CATEGORY.SBO_CONSISTENCY);

        case "SBO term consistency":
          ignoredCategories.add(CHECK_CATEGORY.MATHML_CONSISTENCY);

        case "MathML consistency":
          ignoredCategories.add(CHECK_CATEGORY.UNITS_CONSISTENCY);

        case "SBML unit consistency":
          ignoredCategories.add(CHECK_CATEGORY.OVERDETERMINED_MODEL);

        case "Overdetermined model":
          ignoredCategories.add(CHECK_CATEGORY.MODELING_PRACTICE);
        }
      }

    }

    return ignoredCategories;
  }


  /**
   * Registers in several list or map the error code and the file name where it was not detected properly.
   * 
   * @param errorCode the error code that was not detected properly
   * @param fileName the file name where it was not detected properly.
   */
  private static void didNotDetect(int errorCode, String fileName) {

    notDetected.add(errorCode);

    String constraints = notDetectedFiles.get(fileName);

    if (constraints == null) {
      constraints = "" + errorCode;
    } else {
      constraints += ", " + errorCode;
    }

    notDetectedFiles.put(fileName, constraints);
  }


  /**
   * Gets a new {@link LoggingValidationContext} for the given {@link SBMLDocument}.
   * 
   * @param doc the SBML document
   * @return a new {@link LoggingValidationContext} initialised for the given {@link SBMLDocument}.
   */
  private static LoggingValidationContext getContext(SBMLDocument doc) {
    String key = "l" + doc.getLevel() + "v" + doc.getVersion();
    LoggingValidationContext ctx = contextCache.get(key);

    // If no context was in cache create new one
    if (ctx == null) {
      ctx = new LoggingValidationContext(doc.getLevel(), doc.getVersion());
      ctx.enableCheckCategories(CHECK_CATEGORY.values(), true);
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


  /**
   * Inserts a kind of line separator in the console.
   */
  private static void printStrongHLine() {

    System.out.println("==============================");
  }


  /**
   * Prints a libsbml SBMLerror in a similar way as the jsbml SBMLError.
   * 
   * @param error the error to print
   * @return a String representing the error
   */
  public static String toString(org.sbml.libsbml.SBMLError error) {
    return StringTools.concat("SBMLError ", error.getErrorId(), " [", error.getSeverityAsString(), "] [", error.getCategoryAsString(), "] ",
      "\n  Line = ", error.getLine(), ",  Column = ", error.getColumn(), "\n  package = ", error.getPackage(), "\n  short message = ",
      (error.getShortMessage() != null ? error.getShortMessage() : ""),
      "\n  message = ", (error.getMessage() != null ? error.getMessage() : ""), "\n").toString();

    //  "\n  excerpt = ", error.,
  }

}
