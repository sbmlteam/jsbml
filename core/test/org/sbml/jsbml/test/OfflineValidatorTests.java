/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2016 jointly by the following organizations:
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.parsers.PackageUtil;
import org.sbml.jsbml.xml.stax.SBMLReader;


/**
 * 
 *
 *
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.2
 */
public class OfflineValidatorTests 
{


  /**
   * @param args
   */
  public static void main(String[] args) 
  {
    if (args.length < 1) {
      System.out.println(
          "Usage: java org.sbml.jsbml.xml.stax.SBMLWriter sbmlFileFolder [file-prefix]");
      System.exit(0);
    }
    
    String prefix = null;
    
    if (args.length > 1) 
    {
      prefix = args[1];
    }

    // this JOptionPane is added here to be able to start visualVM profiling
    // before the reading or writing is started.
    // JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");

    
    // TODO - you can search recursively for all xml files
    
    // TODO - you could accept several folders as parameter and use the method .isDirectory() to distinguish a folder from an error id or error range
    
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

    List<String> incorrectValidationFileList = new ArrayList<String>();
    int totalFileTested = 0;
    
    for (File file : files)
    {
      int indexOfDash = file.getName().indexOf('-');
      int errorCode = -1;
      
      if (indexOfDash != -1) {
        errorCode = Integer.valueOf(file.getName().substring(0, indexOfDash));
      }
      
      System.out.println("Error code: " + errorCode);
      
      // TODO - instead of doing the simple prefix test, you can here test if the errorCode is in a certain range or part of a list of error ids.
      
      if (!file.getName().startsWith(prefix))
      {
        continue;
      }
      totalFileTested++;
      
      long init = Calendar.getInstance().getTimeInMillis();
      System.out.println(Calendar.getInstance().getTime());

      String fileName = file.getAbsolutePath();
      boolean shouldPass = fileName.contains("pass");
      boolean shouldFail = fileName.contains("fail");

      System.out.printf("Reading and validating %s\n", fileName);

      long afterRead = 0;
      SBMLDocument testDocument = null;
      try {
        
        testDocument = new SBMLReader().readSBMLFile(fileName);
        System.out.printf("Reading done\n");
        System.out.println(Calendar.getInstance().getTime());
        afterRead = Calendar.getInstance().getTimeInMillis();

        System.out.println("Going to check package version and namespace for all elements.");
        PackageUtil.checkPackages(testDocument);

        System.out.printf("Starting validation\n");

        int nbErrors = testDocument.checkConsistencyOffline();
        
        if (nbErrors > 0 && shouldFail) 
        {
          System.out.println("\nFile failed as expected, " + nbErrors + " reported.\n");
        }
        else if (nbErrors == 0 && shouldPass)
        {
          System.out.println("\nFile passed as expected.\n");
        }
        else 
        {
          System.out.println("\nWARNING - something is wrong with this file !\n");
          incorrectValidationFileList.add(file.getName());
        }
        
      } catch (XMLStreamException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      System.out.println(Calendar.getInstance().getTime());
      long end = Calendar.getInstance().getTimeInMillis();
      long nbSecondes = (end - init)/1000;
      long nbSecondesRead = (afterRead - init)/1000;
      long nbSecondesWrite = (end - afterRead)/1000;

      if (nbSecondes > 120) {
        System.out.println("It took " + nbSecondes/60 + " minutes.");
      } else {
        System.out.println("It took " + nbSecondes + " secondes.");
      }
      System.out.println("Reading: " + nbSecondesRead + " secondes.");
      System.out.println("Validating: " + nbSecondesWrite + " secondes.");
    }
    
    System.out.println("\n\n Number of files incorrectly validated: " + incorrectValidationFileList.size() + " out of " + totalFileTested);
    
    if (incorrectValidationFileList.size() > 0) {
      for (String name : incorrectValidationFileList) {
        System.out.println(name);
      }
    }
  }
}
