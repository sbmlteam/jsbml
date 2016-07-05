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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.xml.parsers.PackageUtil;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.2
 */
public class OfflineValidatorTests {

  /**
   * @param args
   */
  
//  private static int dirsValidated = 0;
//  private static int dirsMissed = 0;
  
  private static int totalFileTested = 0;
  private static int filesCorrectly = 0;
  
  private static String filter = "";
  
  private static Set<Integer> notDetected =  new HashSet<Integer>();
  private static long readTime = 0;
  
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println(
        "Usage: java org.sbml.jsbml.test.OfflineValidatorTests testDataFolder start[:end] [containsString]");
      System.out.println();
      System.out.println("testDataFolder - root folder which contains a child folder for every error code");
      System.out.println("start          - first error code to be checked. Error codes could be started by package name (layout-20613 == 6020613).");
      System.out.println("end            - last error code to check. Must follow at start sperated by a colon ':'");
      System.out.println("containsString - a String which must be contained in every file name.");
      System.out.println("\n Example: \n java org.sbml.jsbml.test.OfflineValidatorTests ./ 20600:20700 l3v1 \n "+
      "This arguments will start the test for all error codes from 20600 up to 20700, but only uses the test files which contains the String 'l3v1'.");
      System.exit(0);
    }
    
    
    
    
    if (args.length > 2)
    {
      filter = args[2];
    }
    
    String range = args[1];
    
    
    String[] blocks = range.split(":");
    
    final int startCode = parseErrorCode(blocks[0]);
    int endCode = startCode;
      
    if (blocks.length > 1)
    {
      endCode = parseErrorCode(blocks[1]);
    }

    
    // TODO - you can search recursively for all xml files
    // TODO - you could accept several folders as parameter and use the method
    
    // .isDirectory() to distinguish a folder from an error id or error range
    File testDataDir = new File(args[0]);
    
    if (!testDataDir.isDirectory())
    {
      System.out.println("First arg ist not a directory!");
      System.exit(0);
    }
    
    System.out.println("Start tests");
    long init = Calendar.getInstance().getTimeInMillis();
    
    for (int code = startCode; code <= endCode; code++)
    {
      File dir = new File(testDataDir, SBMLPackage.convertErrorCodeToString(code, false));
      
      if (dir.isDirectory())
      {
//        dirsValidated++;
        validateDirectory(dir, code);
      }
      else
      {
//        dirsMissed++;
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
    for (Integer i: notDetected)
    {
      System.out.println(i);
    }
      
    
//    File[] files = null;
//    if (argsAsFile.isDirectory()) {
//      files = argsAsFile.listFiles(new FileFilter() {
//
//        @Override
//        public boolean accept(File pathname) {
//          if (pathname.getName().contains("-jsbml")) {
//            return false;
//          }
//          if (pathname.getName().endsWith(".xml")) {
//            return true;
//          }
//          return false;
//        }
//      });
//    } else {
//      files = new File[1];
//      files[0] = argsAsFile;
//    }
//    List<String> incorrectValidationFileList = new ArrayList<String>();
//    int totalFileTested = 0;
    
//    for (File file : files) {
//      
//      int indexOfDash = file.getName().indexOf('-');
//      int errorCode = -1;
//      if (indexOfDash != -1) {
//        errorCode = Integer.valueOf(file.getName().substring(0, indexOfDash));
//      }
//      System.out.println("Error code: " + errorCode);
//      // TODO - instead of doing the simple prefix test, you can here test if
//      // the errorCode is in a certain range or part of a list of error ids.
//      if (!file.getName().startsWith(prefix)) {
//        continue;
//      }
//      
//      totalFileTested++;
//      long init = Calendar.getInstance().getTimeInMillis();
//      System.out.println(Calendar.getInstance().getTime());
//      
//      String fileName = file.getAbsolutePath();
//      boolean shouldPass = fileName.contains("pass");
//      boolean shouldFail = fileName.contains("fail");
//      
//      System.out.printf("Reading and validating %s\n", fileName);
//      long afterRead = 0;
//      SBMLDocument testDocument = null;
//      try {
//        testDocument = new SBMLReader().readSBMLFile(fileName);
//        System.out.printf("Reading done\n");
//        System.out.println(Calendar.getInstance().getTime());
//        afterRead = Calendar.getInstance().getTimeInMillis();
//        System.out.println(
//          "Going to check package version and namespace for all elements.");
//        PackageUtil.checkPackages(testDocument);
//        System.out.printf("Starting validation\n");
//        int nbErrors = testDocument.checkConsistencyOffline();
//        if (nbErrors > 0 && shouldFail) {
//          System.out.println(
//            "\nFile failed as expected, " + nbErrors + " reported.\n");
//        } else if (nbErrors == 0 && shouldPass) {
//          System.out.println("\nFile passed as expected.\n");
//        } else {
//          System.out.println(
//            "\nWARNING - something is wrong with this file !\n");
//          incorrectValidationFileList.add(file.getName());
//        }
//      } catch (XMLStreamException e) {
//        e.printStackTrace();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//      System.out.println(Calendar.getInstance().getTime());
//      long end = Calendar.getInstance().getTimeInMillis();
//      long nbSecondes = (end - init) / 1000;
//      long nbSecondesRead = (afterRead - init) / 1000;
//      long nbSecondesWrite = (end - afterRead) / 1000;
//      if (nbSecondes > 120) {
//        System.out.println("It took " + nbSecondes / 60 + " minutes.");
//      } else {
//        System.out.println("It took " + nbSecondes + " secondes.");
//      }
//      System.out.println("Reading: " + nbSecondesRead + " secondes.");
//      System.out.println("Validating: " + nbSecondesWrite + " secondes.");
//    }
//    System.out.println("\n\n Number of files incorrectly validated: "
//      + incorrectValidationFileList.size() + " out of " + totalFileTested);
//    if (incorrectValidationFileList.size() > 0) {
//      for (String name : incorrectValidationFileList) {
//        System.out.println(name);
//      }
//    }
  }

  private static int parseErrorCode(String s) {
    String[] blocks = s.split("-");
    
    if (blocks.length == 1) {
      return Integer.parseInt(s);
    } else {
      SBMLPackage pkg = SBMLPackage.getPackageWithName(blocks[0]);
      int code = Integer.parseInt(blocks[1]);
      return pkg.offset + code;
    }
  }
  
  private static void validateDirectory(File dir, int errorCode)
  {
    File[] files = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.getName().endsWith(".xml") && pathname.getName().contains(filter);
      }
    });
    
//    System.out.println("Files: " + files.length);
    for (File f: files)
    {
      totalFileTested++;
      validateFile(f, errorCode);
    }
  }
  
  private static void validateFile(File file, int errorCode)
  {
    String name = file.getName();
    
   
    
    boolean shouldPass = name.contains("pass");
    
    System.out.println("Start validating " + name + ". Should pass: " + shouldPass);
    try {
      long startRead = Calendar.getInstance().getTimeInMillis();
      SBMLDocument doc = new SBMLReader().readSBML(file);
      readTime += (Calendar.getInstance().getTimeInMillis() - startRead);
      int errors = doc.checkConsistencyOffline();
      if (errors > 0)
      {
        System.out.println(errors + " constraints broken.");
        if (shouldPass)
        {
          System.out.println("Did not pass as expected!");
        }
        else
        {
          SBMLErrorLog log = doc.getErrorLog();
          
          for (SBMLError e: log.getValidationErrors())
          {
            if (e.getCode() == errorCode)
            {
              filesCorrectly++;
              System.out.println("Constraint " + errorCode + " was broken as expected.");
              return;
            }
          }
          
          notDetected.add(errorCode);
          System.out.println("Didn't detected broken Constraint " + errorCode + "!");
        }
       
      }
      else
      {
        if (shouldPass)
        {
          filesCorrectly++;
          System.out.println("Passed as expected");
        }
        else
        {
          notDetected.add(errorCode);
          System.out.println("Didn't detected broken Constraint " + errorCode + "!");
        }
      }
      
      
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
