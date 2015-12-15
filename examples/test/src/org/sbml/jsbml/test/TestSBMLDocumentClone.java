/*
 * $Id$
 * $URL$
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.filters.Filter;



/**
 * Test class used to check that jsbml SBMLDocument equals method return true when passed it's clone.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.2
 */
public class TestSBMLDocumentClone {

  public TestSBMLDocumentClone() {
  }
  
  /**
   * Checks that jsbml {@link SBMLDocument#equals(Object)} method return true against it's clone.
   * 
   * <p>If passed a folder, it will look recursively for all xml files. Particularly setup to go through
   * the sbml-test-suite folders and test all valid models there. 
   * 
   * @param args the file or folder to test
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException {

    if (args.length < 1) {
      System.out.println("Usage: java org.sbml.jsbml.test.TestSBMLDocumentClone sbmlFileName|folder");
      System.exit(0);
    }

    // this JOptionPane is added here to be able to start visualVM profiling
    // before the reading or writing is started.
    // JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");

    File argsAsFile = new File(args[0]);
    List<File> files = null;

    if (argsAsFile.isDirectory())
    {
      files = findAllXMLFiles(argsAsFile, new ArrayList<File>());      
    }
    else
    {
      files = new ArrayList<File>();
      files.add(argsAsFile);
    }

    double globalInit = Calendar.getInstance().getTimeInMillis();
    double globalEnd = 0;
    int nbNotEquals = 0;
    List<File> fileNotEquals = new ArrayList<File>();
    
    System.out.println("Going to test '" + files.size() + "' files !");    

    for (File file : files)
    {

      double init = Calendar.getInstance().getTimeInMillis();
      // System.out.println(Calendar.getInstance().getTime());

      String fileName = file.getAbsolutePath();

      System.out.printf("Reading %s\n", fileName);

      try {
        
        SBMLDocument doc = new SBMLReader().readSBML(file, null);

        boolean equals = doc.equals(doc.clone());

        if (!equals) {
          nbNotEquals++;
          fileNotEquals.add(file);
          System.out.printf("Reading %s\n", fileName);
          System.out.println(file.getAbsolutePath() + " is not equals to it's clone !!!!!!!!!!!!!!!!!!!!!!!!!!");
          
          @SuppressWarnings("unchecked")
          List<SBase> notEqualSBases = (List<SBase>) doc.filter(new Filter() {
            
            @Override
            public boolean accepts(Object o) {
              if (o instanceof SBase) {
                SBase s = (SBase) o;
                
                return ! s.equals(s.clone());
              }
              return false;
            }
          });
          
          System.out.println(notEqualSBases);
        }

        
        String docString = toSerializedString(doc);
        SBMLDocument serializedDoc = (SBMLDocument) fromSerializedString(docString);

        boolean equalsSerialized = doc.equals(serializedDoc);
        
        // System.out.println("Equals serialized = " + equalsSerialized);
        
        if (!equalsSerialized) {
          nbNotEquals++;
          fileNotEquals.add(file);
          
          List<SBase> notEqualSBases = checkEquals(doc, serializedDoc, new ArrayList<SBase>());
          
          System.out.println("SBases not equals after serialization: " + notEqualSBases);
        }
        
        // TODO - write to String with SBMLWriter and compare again the result with equals !!
        
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
        System.out.printf("Reading %s\n", fileName);
        nbNotEquals++;
        fileNotEquals.add(file);
        e.printStackTrace();
      }

      // System.out.println(Calendar.getInstance().getTime());
      double end = Calendar.getInstance().getTimeInMillis();
      globalEnd = end;
      double nbMilliseconds = end - init;

      if (files.size() == 1)
      {
        System.out.println((int)nbMilliseconds);
      }
    }

    if (nbNotEquals > 0) {
      System.out.println("Found " + nbNotEquals + " documents not equals to their clone or failing to clone.");
      
      for (File file : fileNotEquals) {
        System.out.println(file.getAbsolutePath());
        
      }
    }
    
    if (files.size() > 1)
    {
      double globalNbMilliseconds = globalEnd - globalInit;
      double globalSeconds = globalNbMilliseconds / 1000;

      System.out.println("Reading and writing " + files.size() + " models took : " + globalSeconds + " seconds.");
      System.out.println("Mean per model = " + globalSeconds / files.size() + " seconds (" + globalNbMilliseconds / files.size() + " ms).");

      System.out.println((int)globalNbMilliseconds);
    }
  }

  /**
   * @param sbase
   * @param serializedSBase
   * @param notEqualsList
   * @return
   */
  private static List<SBase> checkEquals(SBase sbase, SBase serializedSBase, ArrayList<SBase> notEqualsList) 
  {
    if (!sbase.equals(serializedSBase)) {
      notEqualsList.add(sbase);
    }
    
    if (sbase.getChildCount() > 0) {

      for (int i = 0; i < sbase.getChildCount(); i++) {
        TreeNode child = sbase.getChildAt(i);
        
        if (child instanceof SBase) {
          checkEquals((SBase) child, (SBase) serializedSBase.getChildAt(i), notEqualsList);
        }
      }
    }
    
    
    return notEqualsList;
  }

  /**
   * @param argsAsFile
   * @param filesList
   * @return
   */
  private static List<File> findAllXMLFiles(File argsAsFile, List<File> filesList) 
  {
    // System.out.println("Analyzing directory '" + argsAsFile.getAbsolutePath() + "'");
    
    File[] xmlFiles = argsAsFile.listFiles(new FileFilter() {

      @Override
      public boolean accept(File pathname)
      {
        if (pathname.getName().contains("-jsbml.xml") || pathname.getName().contains("fail")
            || pathname.getName().contains("sedml"))
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
    
    File[] directories = argsAsFile.listFiles(new FileFilter() {

      @Override
      public boolean accept(File pathname)
      {
        return pathname.isDirectory();
      }
    });

    if (xmlFiles.length > 0) {
      filesList.addAll(Arrays.asList(xmlFiles));
    }

    if (directories.length > 0) {
      for(File directory : directories) {
        findAllXMLFiles(directory, filesList);
      }
    }
    
    return filesList;
  }

  /** Read the object from Base64 string. */
 private static Object fromSerializedString(String s) throws IOException , ClassNotFoundException {
      byte [] data = Base64.getDecoder().decode(s);
      ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
      Object o  = ois.readObject();
      ois.close();
      return o;
 }

  /** Write the object to a Base64 string. */
  private static String toSerializedString(Serializable o) throws IOException {
      
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);

    try {
      oos.writeObject(o);
      oos.close();
      return Base64.getEncoder().encodeToString(baos.toByteArray());
      
    } catch (NotSerializableException e) {
      System.out.println("ERROR !! current content of the serialization : \n" + new String(baos.toByteArray()));
      throw e;
    }
  }
}
