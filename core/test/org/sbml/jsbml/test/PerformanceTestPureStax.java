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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class PerformanceTestPureStax {

  /**
   * A {@link Logger} for this class.
   */
  private static Logger logger = Logger.getLogger(PerformanceTestPureStax.class);

  /**
   * @param xmlEventReader
   * @param listener
   * @return
   * @throws XMLStreamException
   */
  @SuppressWarnings("unchecked")
  private static StringBuilder readXMLFromXMLEventReader(XMLEventReader xmlEventReader, TreeNodeChangeListener listener)  throws XMLStreamException
  {
    XMLEvent event;
    StartElement startElement = null;
    QName currentNode = null;
    StringBuilder stringWriter = new StringBuilder();

    // Read all the elements of the file
    while (xmlEventReader.hasNext()) {
      event = xmlEventReader.nextEvent();

      // StartDocument
      if (event.isStartDocument())
      {
        @SuppressWarnings("unused")
        StartDocument startDocument = (StartDocument) event;
        logger.info("Start document");
        // nothing to do
      }
      // EndDocument
      else if (event.isEndDocument())
      {
        @SuppressWarnings("unused")
        EndDocument endDocument = (EndDocument) event;
        logger.info("End document");
        // nothing to do?
      }
      // StartElement
      else if (event.isStartElement())
      {

        startElement = event.asStartElement();
        currentNode = startElement.getName();

        stringWriter.append(currentNode.getLocalPart()).append("\t[");

        for (Iterator<Attribute> iterator = startElement.getAttributes(); iterator.hasNext();)
        {
          Attribute attr = iterator.next();
          stringWriter.append(attr.getName().getLocalPart()).append(" = ").append(attr.getValue()).append(", ");
        }

        if (startElement.getAttributes().hasNext())
        {
          stringWriter.delete(stringWriter.lastIndexOf(","), stringWriter.length());
        }
        stringWriter.append("]\n");

      }
      // Characters
      else if (event.isCharacters())
      {
        Characters characters = event.asCharacters();
        String text = characters.getData();

        if (text.trim().length() > 0)
        {
          stringWriter.append(text).append("\n");
        }
      }
      // EndElement
      else if (event.isEndElement()) {
        // nothing to do
      }
    }

    return stringWriter;
  }


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
          if (pathname.getName().contains("-jsbml.xml"))
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
    XMLInputFactory inputFactory = XMLInputFactory.newFactory();
    
    System.out.println("XMLInputFactory class = " + inputFactory.getClass().getName());    

    for (File file : files)
    {

      double init = Calendar.getInstance().getTimeInMillis();
      System.out.println(Calendar.getInstance().getTime());

      String fileName = file.getAbsolutePath();
      String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-pureStax.txt");

      System.out.printf("Reading %s and writing %s\n",
        fileName, jsbmlWriteFileName);

      long afterRead = 0;
      try {
        XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(new FileInputStream(file));
        StringBuilder stringBuilder = readXMLFromXMLEventReader(xmlEventReader, null);

        System.out.printf("Reading done\n");
        System.out.println(Calendar.getInstance().getTime());
        afterRead = Calendar.getInstance().getTimeInMillis();

        System.out.printf("Starting writing\n");

        PrintStream out = new PrintStream(new File(jsbmlWriteFileName));
        out.print(stringBuilder.toString());
        out.flush();out.close();

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

      System.out.println("Reading and writing " + files.length + " models took : " + globalSeconds + " seconds.");
      System.out.println("Mean per model = " + globalSeconds / files.length + " seconds (" + globalNbMilliseconds / files.length + " ms).");

      System.out.println((int)globalNbMilliseconds);
    }
  }

}
