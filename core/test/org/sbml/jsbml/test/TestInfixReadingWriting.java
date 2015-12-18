/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
import java.nio.file.Files;
import java.util.Calendar;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.xml.XMLNode;


public class TestInfixReadingWriting {


  public static void main(String[] args) {
    
    if (args.length < 1) {
      System.out.println(
          "Usage: java org.sbml.jsbml.test.TestInfixReadingWriting folder|file");
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

    long init = Calendar.getInstance().getTimeInMillis();
    int nbEquals = 0;
    
    for (File file : files)
    {
      String fileName = file.getAbsolutePath();

      // read the mathml, infix math input and expected output.
      
      byte[] encoded = null;
      String mathMLString =  null;
      String infixInput = null;
      String infixExpectedOutput = null;
      
      try 
      {
        encoded = Files.readAllBytes(file.toPath());
        mathMLString =  new String(encoded, "UTF-8");

        ASTNode astNode = ASTNode.readMathMLFromString(mathMLString);
        String infixOutput = astNode.toFormula();

        if (astNode.getNumSemanticsAnnotations() != 2) {
          System.out.println("!!!!!!!!!! ERROR: for file '" + fileName + "' we found " + astNode.getNumSemanticsAnnotations() + " semantics annotations !");
          continue;
        }
        
        for (int i = 0; i < 2; i++) 
        {
          XMLNode xmlNode = astNode.getSemanticsAnnotation(i);
          String encoding = "";
          
          if (xmlNode.getName().equals("annotation") && xmlNode.hasAttr("encoding")) 
          {
            encoding = xmlNode.getAttrValue("encoding");
            
            if (encoding.equals("infix-input")) 
            {
              infixInput = xmlNode.getChild(0).getCharacters();
            }
            else if (encoding.equals("infix-output"))
            {
              infixExpectedOutput = xmlNode.getChild(0).getCharacters();
            }
          }
        }
        
        // compare the different output
        
        System.out.println("Infix input = '" + infixInput + "'");
        System.out.println("Infix output = '" + infixOutput + "'");
        System.out.println("Infix expected output = '" + infixExpectedOutput + "'");
       
        if (infixOutput != null && infixOutput.equals(infixExpectedOutput)) {
          nbEquals++;
          System.out.println("#########################################################");
        }
        
      }
      catch (Exception e) 
      {
        System.out.println(fileName);
        e.printStackTrace();
      }
      
    }
    
    System.out.println("Nb test where we have the same output = " + nbEquals);
    
    long end = Calendar.getInstance().getTimeInMillis();
    long nbSecondes = (end - init)/1000;

    if (nbSecondes > 120) {
      System.out.println("It took " + nbSecondes/60 + " minutes.");
    } else {
      System.out.println("It took " + nbSecondes + " secondes.");
    }
  }
}
