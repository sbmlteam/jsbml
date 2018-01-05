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
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * Provides methods for reading SBML from files, text strings or streams.
 * <p>
 * This {@link SBMLReader} is just a wrapper for the actual implementation
 * in {@link org.sbml.jsbml.xml.stax.SBMLReader}.
 * 
 * <p>
 * This class is
 * provided for compatibility with libSBML and to avoid problems if the
 * internal of jsbml change, so it is preferable to use it instead of directly
 * using {@link org.sbml.jsbml.xml.stax.SBMLReader}.
 * 
 * <p>
 * The {@link SBMLReader} class provides the main interface for reading SBML
 * content from files and strings.  The methods for reading SBML all return
 * an {@link SBMLDocument} object representing the results.
 * <p>
 * In the case of failures (such as if the SBML contains errors), the errors
 * will be recorded with the {@link SBMLErrorLog}
 * object kept in the {@link SBMLDocument} returned by {@link SBMLReader}.
 * Consequently, immediately after calling a method on {@link SBMLReader},
 * callers should always check for errors and warnings using the methods for
 * this purpose provided by {@link SBMLDocument}.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class SBMLReader implements Cloneable, Serializable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3313609137341424804L;

  /**
   * The singleton actual reader. This should speed up loading required classes,
   * because the initialization is done only once if using the static read
   * methods.
   */
  private static final SBMLReader reader = new SBMLReader();

  /**
   * String used when reading an SBML file, to store XMLNode as user object
   * when the XMLNode does not represents something known from the 'core'.
   * 
   * What SBML core know is Notes, Annotation and the Constraint message that
   * are all encoded as XMLNode.
   */
  public static String UNKNOWN_XML_NODE = "jsbml.reader.unknown.xmlnode";

  /**
   * Factory method for reading SBML from a given {@link File}.
   * 
   * @param file
   * @return
   * @throws XMLStreamException
   * @throws IOException if the file does not exist or cannot be read.
   */
  public static SBMLDocument read(File file) throws XMLStreamException, IOException {
    return read(file, null);
  }

  /**
   * Factory method for reading SBML from a given {@link File}.
   * 
   * @param file
   * @param listener
   * @return
   * @throws XMLStreamException
   * @throws IOException if the file does not exist or cannot be read.
   */
  public static SBMLDocument read(File file, TreeNodeChangeListener listener) throws XMLStreamException, IOException {
    return reader.readSBML(file, listener);
  }

  /**
   * @param file
   * @param listener
   * @return
   * @throws IOException
   * @throws XMLStreamException
   */
  public SBMLDocument readSBML(File file, TreeNodeChangeListener listener) throws XMLStreamException, IOException {
    return new org.sbml.jsbml.xml.stax.SBMLReader().readSBML(file, listener);
  }

  /**
   * Factory method for reading SBML from a given {@link InputStream}.
   * 
   * @param stream
   * @return
   * @throws XMLStreamException
   */
  public static SBMLDocument read(InputStream stream)
      throws XMLStreamException {
    return reader.readSBMLFromStream(stream);
  }

  /**
   * Factory method for reading SBML from given XML code in its {@link String}
   * representation.
   * 
   * @param xml
   * @return
   * @throws XMLStreamException
   */
  public static SBMLDocument read(String xml) throws XMLStreamException {
    return reader.readSBMLFromString(xml);
  }

  /**
   * Creates a new {@link SBMLReader}.
   */
  public SBMLReader() {
    super();
  }

  /**
   * Clone constructor.
   * 
   * @param sbmlReader
   */
  public SBMLReader(SBMLReader sbmlReader) {
    this();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public SBMLReader clone() {
    return new SBMLReader(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    return o instanceof SBMLReader;
  }

  /**
   * Reads an SBML document from a {@link File}.
   * <p>
   * This method is identical to {@link SBMLReader#readSBMLFromFile(String filename)}
   * or {@link SBMLReader#readSBML(String filename)}.
   * <p>
   * If the {@link File} named {@code file} does not exist or its content is not
   * valid SBML, one {@link Exception} will be thrown.
   * <p>
   * This methods is not part of the libSBML SBMLReader API.
   * <p>
   * @param file the file to be read.
   * <p>
   * @return an {@link SBMLDocument} created from the SBML content.
   * @throws XMLStreamException if any other problems prevent to create a {@link SBMLDocument}
   * @throws IOException if the file does not exist or cannot be read.
   * 
   */
  public SBMLDocument readSBML(File file) throws XMLStreamException, IOException {
    return readSBML(file, new SimpleTreeNodeChangeListener());
  }

  /**
   * Reads an SBML document from a file.
   * <p>
   * This method is identical to {@link SBMLReader#readSBMLFromFile(String filename)}
   * or {@link SBMLReader#readSBML(File file)}.
   * <p>
   * If the file named {@code filename} does not exist or its content is not
   * valid SBML, one {@link Exception} will be thrown.
   * <p>
   * @param fileName  the name or full pathname of the file to be read.
   * <p>
   * @return an {@link SBMLDocument} created from the SBML content.
   * @throws XMLStreamException if any other problems prevent to create a {@link SBMLDocument}
   * @throws IOException if the file does not exist or cannot be read.
   * 
   */
  public SBMLDocument readSBML(String fileName) throws XMLStreamException, IOException {
    return new org.sbml.jsbml.xml.stax.SBMLReader().readSBML(fileName);
  }

  /**
   * Reads an SBML document from a file.
   * <p>
   * This method is identical to {@link SBMLReader#readSBML(String filename)}
   * or {@link SBMLReader#readSBML(File file)}.
   * <p>
   * If the file named {@code filename} does not exist or its content is not
   * valid SBML, one {@link Exception} will be thrown.
   * <p>
   * @param filename  the name or full pathname of the file to be read.
   * <p>
   * @return an {@link SBMLDocument} created from the SBML content.
   * @throws XMLStreamException if any other problems prevent to create a {@link SBMLDocument}
   * @throws IOException if the file does not exist or cannot be read.
   * 
   */
  public SBMLDocument readSBMLFromFile(String filename) throws XMLStreamException,
  IOException {
    return new org.sbml.jsbml.xml.stax.SBMLReader().readSBMLFile(filename);
  }

  /**
   * Reads an SBML document from a data stream.
   * <p>
   * This methods is not part of the libSBML SBMLReader API.
   * <p>
   * @param stream the stream of data to be read.
   * <p>
   * @return an {@link SBMLDocument} created from the SBML content.
   * @throws XMLStreamException if any problems prevent to create a {@link SBMLDocument}
   * 
   */
  public SBMLDocument readSBMLFromStream(InputStream stream)
      throws XMLStreamException {
    return new org.sbml.jsbml.xml.stax.SBMLReader().readSBMLFromStream(stream);
  }

  /**
   * Reads an SBML document from the given XML string.
   * 
   * @param xml a string containing a full SBML model
   * @return an {@link SBMLDocument} created from the SBML content.
   * @throws XMLStreamException if any problems prevent to create a {@link SBMLDocument}
   * 
   */
  public SBMLDocument readSBMLFromString(String xml)
      throws XMLStreamException {
    return new org.sbml.jsbml.xml.stax.SBMLReader().readSBMLFromString(xml);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s[]", getClass().getSimpleName());
  }

}
