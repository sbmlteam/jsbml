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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.xml.stream.XMLStreamException;

/**
 * Provides methods for writing SBML to files, text strings or streams.
 * <p>
 * This {@link SBMLWriter} is just a wrapper for the actual implementation in
 * {@link org.sbml.jsbml.xml.stax.SBMLWriter}.
 * <p>
 * This class is provided for compatibility with libSBML and to avoid problems
 * if the internal of jsbml change, so it is preferable to use it instead of
 * directly using {@link org.sbml.jsbml.xml.stax.SBMLWriter}. The
 * {@link SBMLWriter} class is the converse of {@link SBMLReader}, and provides
 * the main interface for serializing SBML models into XML and writing the
 * result to files and text strings. The methods for writing SBML all take an
 * {@link SBMLDocument} object and a destination.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class SBMLWriter implements Cloneable, Serializable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7320725236081166704L;

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
    char indentChar, short indentCount) throws XMLStreamException, SBMLException, IOException {
    new org.sbml.jsbml.xml.stax.SBMLWriter(indentChar, indentCount).write(
      sbmlDocument, file);
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
        throws XMLStreamException, SBMLException, IOException {
    new org.sbml.jsbml.xml.stax.SBMLWriter().write(sbmlDocument, file,
      programName, programVersion);
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
    short indentCount) throws XMLStreamException, SBMLException, IOException {
    new org.sbml.jsbml.xml.stax.SBMLWriter(indentChar, indentCount).write(
      sbmlDocument, file, programName, programVersion);
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
    char indentChar, short indentCount) throws XMLStreamException, SBMLException {
    new org.sbml.jsbml.xml.stax.SBMLWriter(indentChar, indentCount).write(
      sbmlDocument, stream);
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
        throws XMLStreamException, SBMLException {
    new org.sbml.jsbml.xml.stax.SBMLWriter().write(sbmlDocument, stream,
      programName, programVersion);
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
    short indentCount) throws XMLStreamException, SBMLException {
    new org.sbml.jsbml.xml.stax.SBMLWriter(indentChar, indentCount).write(
      sbmlDocument, stream, programName, programVersion);
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
    FileNotFoundException, SBMLException {
    try {
      new org.sbml.jsbml.xml.stax.SBMLWriter(indentChar, indentCount).write(
        sbmlDocument, fileName);
    } catch (IOException e) {
      throw new SBMLException(e);
    }
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
        throws XMLStreamException, FileNotFoundException, SBMLException {
    try {
      new org.sbml.jsbml.xml.stax.SBMLWriter().write(sbmlDocument, fileName,
        programName, programVersion);
    } catch (IOException e) {
      throw new SBMLException(e);
    }
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
        throws XMLStreamException, FileNotFoundException, SBMLException {
    try {
      new org.sbml.jsbml.xml.stax.SBMLWriter(indentChar, indentCount).write(
        sbmlDocument, fileName, programName, programVersion);
    } catch (IOException e) {
      throw new SBMLException(e);
    }
  }

  /**
   * The name of the program that has been used to create an SBML
   * {@link String} representation (possibly in a {@link File}) with the help
   * of JSBML.
   */
  String programName;

  /**
   * The version of the program using JSBML to serialize a model in an SBML
   * {@link String} or {@link File}.
   */
  String programVersion;

  /**
   * The actual writer.
   */
  protected org.sbml.jsbml.xml.stax.SBMLWriter sbmlWriter;

  /**
   * Creates a new {@link SBMLWriter}.
   */
  public SBMLWriter() {
    this(null, null);
  }

  /**
   * Creates a new {@link SBMLWriter} that uses the given character for
   * indentation of the XML representation of SBML data structures (with the
   * given number of such symbols).
   * 
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   * @param indentCount
   *            The number of indentation characters.
   */
  public SBMLWriter(char indentChar, short indentCount) {
    this(null, null, indentChar, indentCount);
  }

  /**
   * Clone constructor.
   * 
   * @param sbmlWriter
   */
  public SBMLWriter(SBMLWriter sbmlWriter) {
    this(sbmlWriter.getProgramName(), sbmlWriter.getProgramVersion(),
      sbmlWriter.getIndentationChar(), sbmlWriter
      .getIndentationCount());
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
  public SBMLWriter(String programName, String programVersion) {
    this(programName, programVersion, org.sbml.jsbml.xml.stax.SBMLWriter
      .getDefaultIndentChar(), org.sbml.jsbml.xml.stax.SBMLWriter
      .getDefaultIndentCount());
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
  public SBMLWriter(String programName, String programVersion,
    char indentChar, short indentCount) {
    this.programName = programName;
    this.programVersion = programVersion;
    sbmlWriter = new org.sbml.jsbml.xml.stax.SBMLWriter(indentChar,
      indentCount);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public SBMLWriter clone() {
    return new SBMLWriter(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof SBMLWriter) {
      SBMLWriter writer = (SBMLWriter) o;
      return (writer.getIndentationChar() == getIndentationChar())
          && (writer.getIndentationCount() == getIndentationCount())
          && writer.getProgramName().equals(getProgramName())
          && writer.getProgramVersion().equals(getProgramVersion());
    }
    return false;
  }


  /**
   * Gives the symbol that is used to indent the SBML output for a better
   * structure and to improve human-readability.
   * 
   * @return the character to be used for indentation.
   */
  public char getIndentationChar() {
    return sbmlWriter.getIndentationChar();
  }

  /**
   * Gives the number of indent symbols that are inserted in a line to better
   * structure the SBML output.
   * 
   * @return the number of characters used for indentation in SBML
   *         serializations.
   */
  public short getIndentationCount() {
    return sbmlWriter.getIndentationCount();
  }

  /**
   * @return the name of the program that uses JSBML for writing SBML data
   *         objects.
   * @see #setProgramName(String)
   */
  public String getProgramName() {
    return (programName != null) ? programName : "";
  }

  /**
   * @return the version of the program that uses JSBML for writing SBML data
   *         objects.
   * @see #setProgramVersion(String)
   */
  public String getProgramVersion() {
    return (programVersion != null) ? programVersion : "";
  }

  /**
   * Check if a program name has been defined for this {@link SBMLWriter}.
   * 
   * @return
   */
  public boolean isSetProgramName() {
    return programName != null;
  }

  /**
   * Check if a program version has been defined for this {@link SBMLWriter}.
   * 
   * @return
   */
  public boolean isSetProgramVersion() {
    return programVersion != null;
  }

  /**
   * Influences the way how SBML data structures are represented in XML.
   * 
   * @param indentChar
   *            The symbol to be used to indent new blocks within an XML
   *            representation of SBML data structures.
   */
  public void setIndentationChar(char indentChar) {
    sbmlWriter.setIndentationChar(indentChar);
  }

  /**
   * Influences the way how SBML data structures are represented in XML.
   * 
   * @param indentCount
   *            The number of indentation characters in the XML representation
   *            of SBML data structures.
   */
  public void setIndentationCount(short indentCount) {
    sbmlWriter.setIndentationCount(indentCount);
  }

  /**
   * Sets the name of this program, i.e., the program that is about to
   * write out the {@link SBMLDocument}.
   * <p>
   * If the program name and version are set (see
   * {@link #setProgramVersion(String version)}), the
   * following XML comment, intended for human consumption, will be written
   * at the beginning of the document:
   * <div class='fragment'>
   * <pre class="brush:xml">
   * &lt;!-- Created by &lt;program name&gt; version &lt;program version&gt; on yyyy-MM-dd HH:mm with JSBML version &lt;JSBML version&gt;. --&gt;
   * </pre>
   * </div>
   * <p>
   * @param name the name of this program (where 'this program' refers to
   * the program in which JSBML is embedded, not JSBML itself!)
   * <p>
   * @return integer value indicating success/failure of the
   * function.  The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link  JSBML#OPERATION_SUCCESS}
   * </ul>
   * <p>
   * @see #setProgramVersion(String version)
   */
  public int setProgramName(String name) {
    programName = name;

    return JSBML.OPERATION_SUCCESS;
  }

  /**
   * Sets the version of this program, i.e., the program that is about to
   * write out the {@link SBMLDocument}.
   * <p>
   * If the program version and name are set (see
   * {@link #setProgramName(String name)}), the
   * following XML comment, intended for human consumption, will be written
   * at the beginning of the document:
   * <div class='fragment'>
   * <pre class="brush:xml">
   * &lt;!-- Created by &lt;program name&gt; version &lt;program version&gt; on yyyy-MM-dd HH:mm with JSBML version &lt;JSBML version&gt;. --&gt;
   * </pre>
   * </div>
   * <p>
   * @param version the version of this program (where 'this program'
   * refers to the program in which JSBML is embedded, not JSBML itself!)
   * <p>
   * @return integer value indicating success/failure of the
   * function.  The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link  JSBML#OPERATION_SUCCESS}
   * </ul>
   * <p>
   * @see #setProgramName(String name)
   */
  public int setProgramVersion(String version) {
    programVersion = version;

    return JSBML.OPERATION_SUCCESS;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format(
      "%s [programName=\"%s\", programVersion=\"%s\", indentationChar='%c', indentationCount=%d]",
      getClass().getSimpleName(), programName,
      programVersion, sbmlWriter.getIndentationChar(),
      sbmlWriter.getIndentationCount());
  }

  /**
   * 
   */
  public void unsetProgramName() {
    setProgramName(null);
  }

  /**
   * 
   */
  public void unsetProgramVersion() {
    setProgramVersion(null);
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
  public void write(SBMLDocument sbmlDocument, File file)
      throws XMLStreamException, SBMLException, IOException {
    sbmlWriter.write(sbmlDocument, file, programName, programVersion);
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
  public void write(SBMLDocument sbmlDocument, OutputStream stream)
      throws XMLStreamException, SBMLException {
    sbmlWriter.write(sbmlDocument, stream, programName, programVersion);
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
  public void write(SBMLDocument sbmlDocument, String fileName)
      throws XMLStreamException, FileNotFoundException, SBMLException {
    try {
      sbmlWriter.write(sbmlDocument, fileName, programName, programVersion);
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
  public void writeSBML(SBMLDocument sbmlDocument, File file)
      throws XMLStreamException, SBMLException, IOException {
    sbmlWriter.write(sbmlDocument, file, programName, programVersion);
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
  public String writeSBMLToString(SBMLDocument sbmlDocument)
      throws XMLStreamException, SBMLException {
    return sbmlWriter.writeSBMLToString(sbmlDocument, programName,
      programVersion);
  }

}
