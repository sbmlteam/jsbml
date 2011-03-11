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
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

/**
 * Provides methods for writing SBML to files, text strings or streams.
 * <p>
 * This {@link SBMLWriter} is just a wrapper for the actual implementation
 * in {@link org.sbml.jsbml.xml.stax.SBMLWriter}. 
 * <p>
 * This class is
 * provided for compatibility with libSBML and to avoid problems if the 
 * internal of jsbml change, so it is preferable to use it instead of directly 
 * using {@link org.sbml.jsbml.xml.stax.SBMLWriter}.
 * 
 * The {@link SBMLWriter} class is the converse of {@link SBMLReader}, and provides the
 * main interface for serializing SBML models into XML and writing the
 * result to files and text strings.  The methods for writing SBML all take
 * an {@link SBMLDocument} object and a destination.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @since 0.8
 * @version $Rev$
 */
public class SBMLWriter {

	private String programName;
	private String programVersion;
	
	/**
	 * Creates a new {@link SBMLwriter}.
	 */
	public SBMLWriter() {
	}

	
	/**
	 * Sets the name of this program, i.e., the program that is about to
	 * write out the {@link SBMLDocument}.
	 * <p>
	 * If the program name and version are set (see
	 * {@link #setProgramVersion(String version)}), the
	 * following XML comment, intended for human consumption, will be written
	 * at the beginning of the document:
	 * <div class='fragment'><pre>
	   &lt;!-- Created by &lt;program name&gt; version &lt;program version&gt;
	   on yyyy-MM-dd HH:mm with {@link JSBML} version &lt;{@link JSBML} version&gt;. --&gt;
	</pre></div>
	 * <p>
	 * @param name the name of this program (where 'this program' refers to
	 * the program in which JSBML is embedded, not JSBML itself!)
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.  The possible values
	 * returned by this function are:
	 * <li> {@link  JSBML.OPERATION_SUCCESS}
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
	 * {@link setProgramName(String name)}), the
	 * following XML comment, intended for human consumption, will be written
	 * at the beginning of the document: <div class='fragment'><pre>&lt;!-- Created by &lt;program
	 * name&gt; version &lt;program version&gt; on yyyy-MM-dd HH:mm with {@link JSBML}
	 * version &lt;{@link JSBML} version&gt;. --&gt; </pre></div>
	 * <p>
	 * @param version the version of this program (where 'this program'
	 * refers to the program in which JSBML is embedded, not JSBML itself!)
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.  The possible values
	 * returned by this function are:
	 * <li> {@link  JSBML.OPERATION_SUCCESS}
	 * <p>
	 * @see #setProgramName(String name)
	 */
	public int setProgramVersion(String version) {
		programVersion = version;
		
		return JSBML.OPERATION_SUCCESS;
	}
	 
	 
	/**
	 * Writes the given SBML document to a {@link File}.
     * <p>
	 * @param sbmlDocument the {@link SBMLDocument} to be written
	 * @param file the file where the SBML document is to be written.
	 * @param programName the name of this program (where 'this program' refers to
	 * the program in which JSBML is embedded, not JSBML itself!)
	 * @param programVersion the version of this program (where 'this program'
	 * refers to the program in which JSBML is embedded, not JSBML itself!)
	 * 
	 * @throws FileNotFoundException if the file does not exist or cannot be created.
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 * 
	 */
	public void write(SBMLDocument document, File file, String programName,
			String programVersion) 
		throws FileNotFoundException, XMLStreamException, SBMLException 
	{
		new org.sbml.jsbml.xml.stax.SBMLWriter().write(document, file,
				programName, programVersion);
	}

	/**
	 * Writes the given SBML document to the output stream.
	 *  
	 * @param sbmlDocument the SBML document to be written
	 * @param stream the stream object where the SBML is to be written.
	 * 
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 * 
	 */
	public void write(SBMLDocument sbmlDocument, OutputStream stream)
			throws XMLStreamException, SBMLException {
		new org.sbml.jsbml.xml.stax.SBMLWriter().write(sbmlDocument, stream, programName, programVersion);
	}

	/**
	 * Writes the given SBML document to the output stream.
	 * 
	 * @param sbmlDocument the SBML document to be written
	 * @param stream the stream object where the SBML is to be written.
	 * @param programName the name of this program (where 'this program' refers to
	 * the program in which JSBML is embedded, not JSBML itself!)
	 * @param programVersion the version of this program (where 'this program'
	 * refers to the program in which JSBML is embedded, not JSBML itself!)
	 * 
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 * 
	 */
	public void write(SBMLDocument sbmlDocument, OutputStream stream,
			String programName, String programVersion)
			throws XMLStreamException, SBMLException {
		new org.sbml.jsbml.xml.stax.SBMLWriter().write(sbmlDocument, stream,
				programName, programVersion);
	}

	/**
	 * Writes the given SBML document to filename.
     * <p>
	 * @param sbmlDocument the {@link SBMLDocument} to be written
	 * @param fileName the name or full pathname of the file where the SBML
     * document is to be written.
     * <p> 
	 * @throws FileNotFoundException if the file does not exist or cannot be created.
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 * 
	 */
	public void write(SBMLDocument sbmlDocument, String fileName)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		new org.sbml.jsbml.xml.stax.SBMLWriter().write(sbmlDocument, fileName, programName, programVersion);
	}

	/**
	 * Writes the given SBML document to filename.
     * <p>
	 * @param sbmlDocument the {@link SBMLDocument} to be written
	 * @param fileName the name or full pathname of the file where the SBML
     * document is to be written.
	 * @param programName the name of this program (where 'this program' refers to
	 * the program in which JSBML is embedded, not JSBML itself!)
	 * @param programVersion the version of this program (where 'this program'
	 * refers to the program in which JSBML is embedded, not JSBML itself!)
	 * 
	 * @throws FileNotFoundException if the file does not exist or cannot be created.
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 * 
	 */
	public void write(SBMLDocument sbmlDocument, String fileName,
			String programName, String programVersion)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		new org.sbml.jsbml.xml.stax.SBMLWriter().write(sbmlDocument, fileName,
				programName, programVersion);
	}

	/**
	 * Writes the given SBML document to a {@link File}.
	 * 
	 * @param d the SBML document to be written
	 * @param file the file where the SBML document is to be written. 
	 * 
	 * @throws FileNotFoundException if the file does not exist or cannot be created.
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 * 
	 */
	public void writeSBML(SBMLDocument document, File file)
			throws FileNotFoundException, XMLStreamException, SBMLException {
		new org.sbml.jsbml.xml.stax.SBMLWriter().write(document, file);
	}
	
	/**
	 * Writes the given SBML document to an in-memory string and returns it.
	 * <p>
	 * @param d the SBML document to be written
	 * <p>
	 * @return the string representing the SBML document as XML.
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 */
	public String writeSBMLToString(SBMLDocument d) throws XMLStreamException, SBMLException {
		return new org.sbml.jsbml.xml.stax.SBMLWriter().writeSBMLToString(d, programName, programVersion);
	}

	/**
	 * Writes the given SBML document to filename.
	 * <p>
	 * @param d the SBML document to be written
	 * @param filename the name or full pathname of the file where the SBML
	 * document is to be written. 
	 * 
	 * @throws FileNotFoundException if the file does not exist or cannot be created.
	 * @throws XMLStreamException if any problems prevent to write the {@link SBMLDocument} as XML. 
	 * @throws SBMLException if any SBML problems prevent to write the {@link SBMLDocument}. 
	 */
	public void writeSBMLToFile(SBMLDocument d, String filename) 
		throws FileNotFoundException, XMLStreamException, SBMLException 
	{
		write(d, filename);
	}

}
