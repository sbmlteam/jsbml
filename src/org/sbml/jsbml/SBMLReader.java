/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
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
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

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
 * object kept in the {@link SBMLDocument} returned by {@link SBMLReader}.  Consequently,
 * immediately after calling a method on {@link SBMLReader}, callers should always
 * check for errors and warnings using the methods for this purpose
 * provided by {@link SBMLDocument}.
 * <p>
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @since 0.8
 * @version $Rev$
 */
public class SBMLReader {

	/**
	 * Creates a new {@link SBMLReader}.
	 */
	public SBMLReader() {
	}

	/**
	 * Reads an SBML document from a {@link File}.
	 * <p>
	 * This method is identical to {@link SBMLReader#readSBMLFromFile(String filename)} 
	 * or {@link SBMLReader#readSBML(String filename)}.
	 * <p>
	 * If the {@link File} named <code>file</code> does not exist or its content is not
	 * valid SBML, one {@link Exception} will be thrown.
	 * <p>
	 * This methods is not part of the libSBML SBMLReader API.
	 * <p>
	 * @param file the file to be read.
	 * <p>
	 * @return an {@link SBMLDocument} created from the SBML content.
	 * @throws FileNotFoundException if the file does not exist.
	 * @throws XMLStreamException if any other problems prevent to create a {@link SBMLDocument} 
	 * 
	 */
	public SBMLDocument readSBML(File file) throws FileNotFoundException,
			XMLStreamException {
		return new org.sbml.jsbml.xml.stax.SBMLReader().readSBML(file);
	}

	/**
	 * Reads an SBML document from a file.
	 * <p>
	 * This method is identical to {@link SBMLReader#readSBMLFromFile(String filename)} 
	 * or {@link SBMLReader#readSBML(File file)}.
	 * <p>
	 * If the file named <code>filename</code> does not exist or its content is not
	 * valid SBML, one {@link Exception} will be thrown.
	 * <p>
	 * @param filename  the name or full pathname of the file to be read.
	 * <p>
	 * @return an {@link SBMLDocument} created from the SBML content.
	 * @throws FileNotFoundException if the file does not exist.
	 * @throws XMLStreamException if any other problems prevent to create a {@link SBMLDocument} 
	 * 
	 */
	public SBMLDocument readSBML(String filename) throws FileNotFoundException,
			XMLStreamException {
		return new org.sbml.jsbml.xml.stax.SBMLReader().readSBML(filename);
	}

	/**
	 * Reads an SBML document from a file.
	 * <p>
	 * This method is identical to {@link SBMLReader#readSBML(String filename)} 
	 * or {@link SBMLReader#readSBML(File file)}.
	 * <p>
	 * If the file named <code>filename</code> does not exist or its content is not
	 * valid SBML, one {@link Exception} will be thrown.
	 * <p>
	 * @param filename  the name or full pathname of the file to be read.
	 * <p>
	 * @return an {@link SBMLDocument} created from the SBML content.
	 * @throws FileNotFoundException if the file does not exist.
	 * @throws XMLStreamException if any other problems prevent to create a {@link SBMLDocument} 
	 * 
	 */
	public SBMLDocument readSBMLFromFile(String filename) throws XMLStreamException,
			FileNotFoundException {
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
		return (SBMLDocument) new org.sbml.jsbml.xml.stax.SBMLReader().readSBMLFromStream(stream);
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

}
