/*
 * $Id:  SBMLWriter.java 09:43:26 draeger $
 * $URL: SBMLWriter.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

/**
 * This class is simply a wrapper around the actual
 * {@link org.sbml.jsbml.xml.stax.SBMLWriter} in JSBML provided here for
 * compatibility with libSBML.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-10-21
 * @deprecated directly use the static methods in
 *             {@link org.sbml.jsbml.xml.stax.SBMLWriter}.
 */
@Deprecated
public class SBMLWriter {

	/**
	 * Constructor provided for compatibility with libSBML.
	 */
	@Deprecated
	public SBMLWriter() {
	}

	/**
	 * 
	 * @param document
	 * @param file
	 * @param programName
	 * @param programVersion
	 * @return
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @deprecated use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLWriter.#write(SBMLDocument, File,
	 *             String, String))}
	 */
	@Deprecated
	public String write(SBMLDocument document, File file, String programName,
			String programVersion) throws FileNotFoundException,
			XMLStreamException, SBMLException {
		return org.sbml.jsbml.xml.stax.SBMLWriter.write(document, file,
				programName, programVersion);
	}

	/**
	 * 
	 * @param sbmlDocument
	 * @param stream
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @deprecated use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLWriter.#write(SBMLDocument,
	 *             OutputStream))}
	 */
	@Deprecated
	public void write(SBMLDocument sbmlDocument, OutputStream stream)
			throws XMLStreamException, SBMLException {
		org.sbml.jsbml.xml.stax.SBMLWriter.write(sbmlDocument, stream);
	}

	/**
	 * 
	 * @param sbmlDocument
	 * @param stream
	 * @param programName
	 * @param programVersion
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @deprecated use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLWriter.#write(SBMLDocument,
	 *             OutputStream, String, String))}
	 */
	@Deprecated
	public void write(SBMLDocument sbmlDocument, OutputStream stream,
			String programName, String programVersion)
			throws XMLStreamException, SBMLException {
		org.sbml.jsbml.xml.stax.SBMLWriter.write(sbmlDocument, stream,
				programName, programVersion);
	}

	/**
	 * 
	 * @param sbmlDocument
	 * @param fileName
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 * @throws SBMLException
	 * @deprecated use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLWriter.#write(SBMLDocument,
	 *             String)}
	 */
	@Deprecated
	public void write(SBMLDocument sbmlDocument, String fileName)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		org.sbml.jsbml.xml.stax.SBMLWriter.write(sbmlDocument, fileName);
	}

	/**
	 * 
	 * @param sbmlDocument
	 * @param fileName
	 * @param programName
	 * @param programVersion
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 * @throws SBMLException
	 * @deprecated use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLWriter.#write(SBMLDocument,
	 *             String, String, String)}
	 */
	@Deprecated
	public void write(SBMLDocument sbmlDocument, String fileName,
			String programName, String programVersion)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		org.sbml.jsbml.xml.stax.SBMLWriter.write(sbmlDocument, fileName,
				programName, programVersion);
	}

	/**
	 * 
	 * @param document
	 * @param file
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @deprecated use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLWriter.#write(SBMLDocument,
	 *             File)}
	 */
	@Deprecated
	public void writeSBML(SBMLDocument document, File file)
			throws FileNotFoundException, XMLStreamException, SBMLException {
		org.sbml.jsbml.xml.stax.SBMLWriter.write(document, file);
	}
}
