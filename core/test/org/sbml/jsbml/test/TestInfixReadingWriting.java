/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.xml.XMLNode;


/**
 * 
 * 
 * @author rodrigue
 *
 */
public class TestInfixReadingWriting {

  /**
   * 
   */
  public static String[] TESTS_TO_IGNORE = {
      // trigonometric operators (output could be optional?)
      "0019", "0020", "0035", "0036", "0039", "0040",
      "0110", "0111", "0126", "0127", "0130", "0131", 
      
      // additional/missing parenthesis in JSBML, not harmful
      "0072", "0076", "0164", "0168", "0200", "0201", "0202", "0203",
      "0249",
      //? the parenthesis might be better for readability ?
      "0301",
      
      // different output for multiple arguments to relational operators
      "0086", "0087", "0088", "0089", "0090", "0091",
      
      // number like '1e3' written wrongly with many zeros by libsbml 
      "0196", "0197", "0211", "0212", "0213", "0214",
      
      // very large numbers written with an exponent by JSBML/java
      "0224", "0225"
  };
  
  /*
   
    output for log/ln (not wrong but might be better the way libsbml does it) => "0056", "0057", "0058", "0147", "0149", "0150"
    
    output for sqrt/root different ==> "0063", "0064", "0155", "0156", "0178"
   
   
    arithmetic operator removed from the output when there is only one argument or less ? ==> "0084", "0085", "0176", "0177", "0180", "0181"
    
    units in formula not supported ==> "0209", "0210", "0211", "0212", "0213", "0214", "0215", "0216", "0217"
                                       "0221", "0222"  
    
    '1e' written '1' by JSBML, wrong or same ? ==> "0218", "0219", "0220"
    
    modulo => need to restore the original String ? ==> "0233", "0254", "0255", "0279", "0280", "0302"-"0361"
    
    
    // Stopped to check them at nb 0363
   */

  /**
   * 
   * @param args path to the folder containing all the test files.
   */
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
    
    Arrays.sort(files);

    long init = Calendar.getInstance().getTimeInMillis();
    List<String> differences = new ArrayList<String>();
    List<String> difference2s = new ArrayList<String>();
    int nbEquals = 0;
    int nbEqualsIgnoreSpace = 0;
    int nbTests = 0;
    
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
        
        nbTests++;
        
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
        System.out.print("Infix expected output = '" + infixExpectedOutput + "'");
       
        boolean different = true;
        
        if (infixOutput != null && infixOutput.equals(infixExpectedOutput)) {
          nbEquals++;
          System.out.print("  ###");
          different = false;
        }
        if (infixOutput != null && infixOutput.replace(" ", "").equals(infixExpectedOutput.replace(" ", ""))) {
          nbEqualsIgnoreSpace++;
          System.out.println("  @@@");
          different = false;
        }
        System.out.println("\n");

        if (different) {
          differences.add("input= '" + infixInput + "', output = '" + infixOutput + "' (expected output = '" + infixExpectedOutput + "') (" + fileName + ")");
        }
        if (different && (infixInput.indexOf("%") == -1) && (infixOutput.indexOf("arc") == -1)) {
          difference2s.add("input= '" + infixInput + "', output = '" + infixOutput + "' (expected output = '" + infixExpectedOutput + "')");
        }
      }
      catch (Exception e) 
      {
        System.out.println(fileName);
        e.printStackTrace();
      }
      
    }
    
    System.out.println("Nb tests where we have the same output without considering spaces  = " + nbEqualsIgnoreSpace + " / " + nbTests
      + " (" + (nbTests - nbEqualsIgnoreSpace) + " failed tests)");
    System.out.println("Nb tests where we have the same output, including spaces = " + nbEquals + " (nb tests = " + nbTests + ")");
    
    long end = Calendar.getInstance().getTimeInMillis();
    long nbSecondes = (end - init)/1000;

    if (nbSecondes > 120) {
      System.out.println("It took " + nbSecondes/60 + " minutes.");
    } else {
      System.out.println("It took " + nbSecondes + " secondes.");
    }
    
    if (differences.size() > 0) {
      System.out.println("\n\n List of differences: \n");

      for (String difference : differences) {
        System.out.println(difference);
      }
    }
    
    if (difference2s.size() > 0) {
      System.out.println("\n\nNb tests where we don't have the same output (excluding modulo and trigonometric operators) = " + difference2s.size()
        + " (nb tests = " + nbTests + ")");
      
      System.out.println("\n\n");
      for (String difference : difference2s) {
        System.out.println(difference);
      }
      
    }
  }
}
