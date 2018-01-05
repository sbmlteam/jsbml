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
package org.sbml.jsbml;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Calendar;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.jsbml.xml.stax.SBMLWriter;
import org.w3c.tidy.Tidy;

/**
 * Provides methods for writing SBML to files, text strings or streams.
 * <p>
 * This class is just a wrapper for the actual implementation. It does use <a href="http://jtidy.sourceforge.net/">JTidy</a>,
 *  a HTML/XML syntax checker and pretty printer, in order to have a proper XML indentation when writing the SBML
 *  document.
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class TidySBMLWriter extends org.sbml.jsbml.SBMLWriter implements Cloneable, Serializable {

  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The default Tidy instance 
   */
  private static final transient Tidy tidy = new Tidy();  // obtain a new Tidy instance

  static {
    // set desired configuration options using tidy setters
    tidy.setDropEmptyParas(false);
    tidy.setHideComments(false);
    tidy.setIndentContent(true);
    tidy.setInputEncoding("UTF-8");
    tidy.setOutputEncoding("UTF-8");
    tidy.setQuiet(true);
    tidy.setSmartIndent(true);
    tidy.setTrimEmptyElements(true);
    // preventing line wrapping by default as it can cause problems in old COBRA style sbml by cutting the notes into several lines
    tidy.setWraplen(0);
    tidy.setWrapAttVals(false);
    tidy.setWrapScriptlets(true);
    tidy.setLiteralAttribs(true);
    tidy.setXmlOut(true);
    tidy.setXmlSpace(true);
    tidy.setXmlTags(true);
  }
  
  /**
   * A user tidy instance if they want to modify the default settings
   */
  private Tidy userTidy = null;

  /**
   * Writes the given SBML document to a {@link File}.
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param file
   *            the file where the SBML document is to be written.
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * @throws IOException
   *             if it is not possible to write to the given file, e.g., due
   *             to an invalid file name or missing permissions.
   */
  public static void write(SBMLDocument sbmlDocument, File file,
    char indentChar, short indentCount) throws XMLStreamException, SBMLException, IOException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(indentChar, indentCount);
    sbmlWriter.write(sbmlDocument, file);
  }

  /**
   * @param indentChar
   * @param indentCount
   */
  public static void setIndentation(char indentChar, short indentCount) {
    if (indentChar == ' ') {
      tidy.setSpaces(indentCount);
    } else {
      tidy.setTabsize(indentCount);
    }
  }

  /**
   * Writes the given SBML document to a {@link File}.
   * <p>
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param file
   *            the file where the SBML document is to be written.
   * @param programName
   *            the name of this program (where 'this program' refers to the
   *            program in which JSBML is embedded, not JSBML itself!)
   * @param programVersion
   *            the version of this program (where 'this program' refers to
   *            the program in which JSBML is embedded, not JSBML itself!)
   * 
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * @throws IOException
   *             if it is not possible to write to the given file, e.g., due
   *             to an invalid file name or missing permissions.
   */
  public static void write(SBMLDocument sbmlDocument, File file, String programName,
    String programVersion)
        throws XMLStreamException, SBMLException, IOException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(programName, programVersion);
    sbmlWriter.write(sbmlDocument, file);
  }

  /**
   * Writes the given SBML document to a {@link File}.
   * <p>
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param file
   *            the file where the SBML document is to be written.
   * @param programName
   *            the name of this program (where 'this program' refers to the
   *            program in which JSBML is embedded, not JSBML itself!)
   * @param programVersion
   *            the version of this program (where 'this program' refers to
   *            the program in which JSBML is embedded, not JSBML itself!)
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   * 
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * @throws IOException
   *             if it is not possible to write to the given file, e.g., due
   *             to an invalid file name or missing permissions.
   * @see #write(SBMLDocument, File, String, String)
   */
  public static void write(SBMLDocument sbmlDocument, File file,
    String programName, String programVersion, char indentChar,
    short indentCount) throws XMLStreamException, SBMLException, IOException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(programName, programVersion, indentChar, indentCount);
    sbmlWriter.write(sbmlDocument, file);
  }

  /**
   * Writes the given {@link SBMLDocument} to the {@link OutputStream}.
   * 
   * @param sbmlDocument the SBML document to be written
   * @param stream the stream object where the SBML is to be written.
   * @param indentChar The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount The number of indentation characters.
   * @throws XMLStreamException  if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException  if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   */
  public static void write(SBMLDocument sbmlDocument, OutputStream stream,
    char indentChar, short indentCount) throws XMLStreamException, SBMLException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(indentChar, indentCount);
    sbmlWriter.write(sbmlDocument, stream);
  }

  /**
   * Writes the given {@link SBMLDocument} to the {@link OutputStream}.
   * 
   * @param sbmlDocument
   *            the SBML document to be written
   * @param stream
   *            the stream object where the SBML is to be written.
   * @param programName
   *            the name of this program (where 'this program' refers to the
   *            program in which JSBML is embedded, not JSBML itself!)
   * @param programVersion
   *            the version of this program (where 'this program' refers to
   *            the program in which JSBML is embedded, not JSBML itself!)
   * 
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   */
  public static void write(SBMLDocument sbmlDocument, OutputStream stream,
    String programName, String programVersion)
        throws XMLStreamException, SBMLException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(programName, programVersion);
    sbmlWriter.write(sbmlDocument, stream);
  }

  /**
   * Writes the given {@link SBMLDocument} to the {@link OutputStream}.
   * 
   * @param sbmlDocument
   *            the SBML document to be written
   * @param stream
   *            the stream object where the SBML is to be written.
   * @param programName
   *            the name of this program (where 'this program' refers to the
   *            program in which JSBML is embedded, not JSBML itself!)
   * @param programVersion
   *            the version of this program (where 'this program' refers to
   *            the program in which JSBML is embedded, not JSBML itself!)
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   * 
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * @see #write(SBMLDocument, OutputStream, String, String)
   */
  public static void write(SBMLDocument sbmlDocument, OutputStream stream,
    String programName, String programVersion, char indentChar,
    short indentCount) throws XMLStreamException, SBMLException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(programName, programVersion, indentChar, indentCount);
    sbmlWriter.write(sbmlDocument, stream);
  }

  /**
   * Writes the given {@link SBMLDocument} to file name.
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param fileName
   *            the name or full pathname of the file where the SBML document
   *            is to be written.
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws FileNotFoundException
   *             if the file does not exist or cannot be created.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   */
  public static void write(SBMLDocument sbmlDocument, String fileName,
    char indentChar, short indentCount) throws XMLStreamException,
    FileNotFoundException, SBMLException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(indentChar, indentCount);
    sbmlWriter.writeSBMLToFile(sbmlDocument, fileName);
  }

  /**
   * Writes the given {@link SBMLDocument} to file name.
   * <p>
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param fileName
   *            the name or full pathname of the file where the SBML document
   *            is to be written.
   * @param programName
   *            the name of this program (where 'this program' refers to the
   *            program in which JSBML is embedded, not JSBML itself!)
   * @param programVersion
   *            the version of this program (where 'this program' refers to
   *            the program in which JSBML is embedded, not JSBML itself!)
   * 
   * @throws FileNotFoundException
   *             if the file does not exist or cannot be created.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   */
  public static void write(SBMLDocument sbmlDocument, String fileName,
    String programName, String programVersion)
        throws XMLStreamException, FileNotFoundException, SBMLException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(programName, programVersion);
    sbmlWriter.writeSBMLToFile(sbmlDocument, fileName);
  }

  /**
   * Writes the given {@link SBMLDocument} to file name.
   * <p>
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param fileName
   *            the name or full pathname of the file where the SBML document
   *            is to be written.
   * @param programName
   *            the name of this program (where 'this program' refers to the
   *            program in which JSBML is embedded, not JSBML itself!)
   * @param programVersion
   *            the version of this program (where 'this program' refers to
   *            the program in which JSBML is embedded, not JSBML itself!)
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   * 
   * @throws FileNotFoundException
   *             if the file does not exist or cannot be created.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * @see #write(SBMLDocument, String, String, String)
   */
  public static void write(SBMLDocument sbmlDocument, String fileName,
    String programName, String programVersion, char indentChar,
    short indentCount)
        throws XMLStreamException, FileNotFoundException, SBMLException
  {
    TidySBMLWriter sbmlWriter = new TidySBMLWriter(programName, programVersion, indentChar, indentCount);
    sbmlWriter.writeSBMLToFile(sbmlDocument, fileName);
  }

  /**
   * Creates a new {@link TidySBMLWriter}.
   */
  public TidySBMLWriter() {
    this(null, null);
  }

  /**
   * Creates a new {@link TidySBMLWriter} that uses the given character for
   * indentation of the XML representation of SBML data structures (with the
   * given number of such symbols).
   * 
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   */
  public TidySBMLWriter(char indentChar, short indentCount) {
    this(null, null, indentChar, indentCount);
  }

  /**
   * Clone constructor.
   * 
   * @param sbmlWriter
   */
  public TidySBMLWriter(TidySBMLWriter sbmlWriter) {
    // TODO - not good, correct
    this(sbmlWriter.getProgramName(), sbmlWriter.getProgramVersion(),
      sbmlWriter.getIndentationChar(), sbmlWriter.getIndentationCount());
  }

  /**
   * Creates a new {@link SBMLWriter} for the program with the given name and
   * version.
   * 
   * @param programName
   *            The name of the program that has been used to create an SBML
   *            {@link String} representation (possibly in a {@link File})
   *            with the help of JSBML.
   * @param programVersion
   *            The version of the program using JSBML to serialize a model in
   *            an SBML {@link String} or {@link File}.
   */
  public TidySBMLWriter(String programName, String programVersion) {
    this(programName, programVersion, SBMLWriter.getDefaultIndentChar(), SBMLWriter.getDefaultIndentCount());
  }

  /**
   * Creates a new {@link SBMLWriter} for the program with the given name and
   * version that uses the given character for indentation of the XML
   * representation of SBML data structures (with the given number of such
   * symbols).
   * 
   * @param programName
   *            The name of the program that has been used to create an SBML
   *            {@link String} representation (possibly in a {@link File})
   *            with the help of JSBML.
   * @param programVersion
   *            The version of the program using JSBML to serialize a model in
   *            an SBML {@link String} or {@link File}.
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   */
  public TidySBMLWriter(String programName, String programVersion,
    char indentChar, short indentCount) {
    super(programName, programVersion, indentChar, indentCount);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public TidySBMLWriter clone() {
    return new TidySBMLWriter(this);
  }

  /**
   * Writes the given SBML document to a {@link File}. If specified in the
   * constructor of this {@link SBMLWriter}, the {@link #programName} and
   * {@link #programVersion} of the calling program will be made persistent in
   * the resulting SBML {@link File}.
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param file
   *            the file where the SBML document is to be written.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * @throws IOException
   *             if it is not possible to write to the given file, e.g., due
   *             to an invalid file name or missing permissions.
   */
  @Override
  public void write(SBMLDocument sbmlDocument, File file)
      throws XMLStreamException, SBMLException, IOException
  {
    writeSBML(sbmlDocument, file);
  }

  /**
   * Writes the given SBML document to the {@link OutputStream}. If specified
   * in the constructor of this {@link SBMLWriter}, the {@link #programName}
   * and {@link #programVersion} of the calling program will be made
   * persistent in the resulting SBML representation.
   * 
   * @param sbmlDocument
   *            the SBML document to be written
   * @param stream
   *            the stream object where the SBML is to be written.
   * 
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * 
   */
  @Override
  public void write(SBMLDocument sbmlDocument, OutputStream stream)
      throws XMLStreamException, SBMLException
  {
    String sbmlDocString = sbmlWriter.writeSBMLToString(sbmlDocument, getProgramName(), getProgramVersion());

    try {
      // make sure that encoding is UTF-8 
      Writer out = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
      InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(sbmlDocString.getBytes("UTF-8")), "UTF-8");

      setIndentation(getIndentationChar(), getIndentationCount());
      
      if (userTidy == null) {
        tidy.parse(in, out); // run tidy, providing an input and output stream
      } else {
        userTidy.parse(in, out); // run tidy, providing an input and output stream
      }
      
    } catch (IOException e) {
      throw new SBMLException(e);
    }
  }

  /**
   * Writes the given {@link SBMLDocument} to file name. If specified in the
   * constructor of this {@link SBMLWriter}, the {@link #programName} and
   * {@link #programVersion} of the calling program will be made persistent in
   * the resulting SBML {@link File}.
   * <p>
   * 
   * @param sbmlDocument
   *            the {@link SBMLDocument} to be written
   * @param fileName
   *            the name or full pathname of the file where the SBML document
   *            is to be written.
   *            <p>
   * @throws FileNotFoundException
   *             if the file does not exist or cannot be created.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * 
   */
  @Override
  public void write(SBMLDocument sbmlDocument, String fileName)
      throws XMLStreamException, FileNotFoundException, SBMLException 
  {
    try {
      writeSBML(sbmlDocument, new File(fileName));
    } catch (IOException e) {
      throw new SBMLException(e);
    }
  }

  /**
   * Writes the given SBML document to a {@link File}. If specified in the
   * constructor of this {@link SBMLWriter}, the {@link #programName} and
   * {@link #programVersion} of the calling program will be made persistent in
   * the resulting SBML {@link File}.
   * 
   * @param sbmlDocument
   *            the SBML document to be written
   * @param file
   *            the file where the SBML document is to be written.
   * 
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   * @throws IOException
   *             if it is not possible to write to the given file, e.g., due
   *             to an invalid file name or missing permissions.
   */
  @Override
  public void writeSBML(SBMLDocument sbmlDocument, File file)
      throws XMLStreamException, SBMLException, IOException {

    write(sbmlDocument, new FileOutputStream(file));
      
  }

  /**
   * Writes the given SBML document to file name. If specified in the
   * constructor of this {@link SBMLWriter}, the {@link #programName} and
   * {@link #programVersion} of the calling program will be made persistent in
   * the resulting SBML {@link File}.
   * <p>
   * 
   * @param sbmlDocument
   *            the SBML document to be written
   * @param fileName
   *            the name or full pathname of the file where the SBML document
   *            is to be written.
   * 
   * @throws FileNotFoundException
   *             if the file does not exist or cannot be created.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   */
  @Override
  public void writeSBMLToFile(SBMLDocument sbmlDocument, String fileName)
      throws FileNotFoundException, XMLStreamException, SBMLException {
    write(sbmlDocument, fileName);
  }

  /**
   * Writes the given SBML document to an in-memory {@link String} and returns
   * it. If specified in the constructor of this {@link SBMLWriter}, the
   * {@link #programName} and {@link #programVersion} of the calling program
   * will be made persistent in the resulting SBML {@link String}.
   * <p>
   * 
   * @param sbmlDocument
   *            the SBML document to be written
   *            <p>
   * @return the string representing the SBML document as XML.
   * @throws XMLStreamException
   *             if any problems prevent to write the {@link SBMLDocument} as
   *             XML.
   * @throws SBMLException
   *             if any SBML problems prevent to write the
   *             {@link SBMLDocument}.
   */
  @Override
  public String writeSBMLToString(SBMLDocument sbmlDocument)
      throws XMLStreamException, SBMLException {

    String sbmlDocString = sbmlWriter.writeSBMLToString(sbmlDocument, getProgramName(), getProgramVersion());

    // make sure that encoding is UTF-8 
    try {
      ByteArrayOutputStream outputSbml = new ByteArrayOutputStream();
      Writer out = new OutputStreamWriter(outputSbml, "UTF-8");
      InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(sbmlDocString.getBytes("UTF-8")), "UTF-8");

      setIndentation(getIndentationChar(), getIndentationCount());
      
      if (userTidy == null) {
        tidy.parse(in, out); // run tidy, providing an input and output stream
      } else {
        userTidy.parse(in, out); // run tidy, providing an input and output stream
      }

      String outputSBMLString = outputSbml.toString("UTF-8");
      
      return outputSBMLString;
    } catch (UnsupportedEncodingException e) {
      throw new SBMLException(e);
    }
  }
  
  

  /**
   * Returns the instance of {@link Tidy} that is used to pretty print the SBML.
   * 
   * @return the userTidy
   */
  public Tidy getUserTidy() {
    return userTidy;
  }

  /**
   * Sets the instance of {@link Tidy} that will be used to pretty print the SBML.
   * 
   * @param userTidy the {@link Tidy} instance to set
   */
  public void setUserTidy(Tidy userTidy) {
    this.userTidy = userTidy;
  }

  /**
   * 
   * @param args
   * @throws SBMLException
   * @throws XMLStreamException
   * @throws IOException
   */
  public static void main(String[] args) throws SBMLException, XMLStreamException, IOException {
    // TODO: move this to the examples folder.

    if (args.length < 1) {
      System.out.println(
          "Usage: java org.sbml.jsbml.TidySBMLWriter sbmlFileName");
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
          if (pathname.getName().contains("-jsbmlTidy"))
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

    for (File file : files)
    {
      long init = Calendar.getInstance().getTimeInMillis();
      System.out.println(Calendar.getInstance().getTime());

      String fileName = file.getAbsolutePath();
      String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbmlTidy.xml");

      System.out.printf("Reading %s and writing %s\n",
        fileName, jsbmlWriteFileName);

      long afterRead = 0;
      SBMLDocument testDocument = null;
      try {
        testDocument = new SBMLReader().readSBMLFile(fileName);
        System.out.printf("Reading done\n");
        System.out.println(Calendar.getInstance().getTime());
        afterRead = Calendar.getInstance().getTimeInMillis();

        // testDocument.checkConsistency();
        // System.out.println(XMLNode.convertXMLNodeToString(testDocument.getModel().getAnnotation().getNonRDFannotation()));

        System.out.printf("Starting writing\n");

        TidySBMLWriter.write(testDocument, jsbmlWriteFileName, ' ', (short) 2);
        
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
      System.out.println("Writing: " + nbSecondesWrite + " secondes.");
    }
  }

  //    SBMLDocument doc = new SBMLReader().readSBMLFromFile("/home/rodrigue/data/BIOMD0000000477.xml");
  //
  //    TidySBMLWriter.write(doc, new File("/home/rodrigue/data/BIOMD0000000477-tidyFile.xml"), ' ', (short) 2);
  //  }
}
