/*
 * $Id:  SBMLReader.java 09:37:00 draeger $
 * $URL: SBMLReader.java $
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
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

/**
 * This {@link SBMLReader} is just a wrapper for the actual
 * {@link org.sbml.jsbml.xml.stax.SBMLReader} for SBML files. This class is
 * provided for compatibility with libSBML.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-10-21
 * @deprecated directly use the static methods in
 *             {@link org.sbml.jsbml.xml.stax.SBMLReader}
 */
@Deprecated
public class SBMLReader {

	/**
	 * Constructor needed for compatibility with libSBML.
	 */
	@Deprecated
	public SBMLReader() {
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 * @deprecated directly use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLReader.#readSBML(File)}
	 */
	@Deprecated
	public SBMLDocument readSBML(File file) throws FileNotFoundException,
			XMLStreamException {
		return org.sbml.jsbml.xml.stax.SBMLReader.readSBML(file);
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 * @deprecated directly use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLReader.#readSBML(String)}
	 */
	@Deprecated
	public SBMLDocument readSBML(String file) throws FileNotFoundException,
			XMLStreamException {
		return org.sbml.jsbml.xml.stax.SBMLReader.readSBML(file);
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 * @deprecated directly use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLReader.#readSBMLFile(String)}
	 */
	@Deprecated
	public SBMLDocument readSBMLFile(String file) throws XMLStreamException,
			FileNotFoundException {
		return org.sbml.jsbml.xml.stax.SBMLReader.readSBMLFile(file);
	}

	/**
	 * 
	 * @param stream
	 * @return
	 * @throws XMLStreamException
	 * @deprecated directly use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLReader.#readSBMLFromStream(InputStream
	 *             )}
	 */
	@Deprecated
	public SBMLDocument readSBMLFromStream(InputStream stream)
			throws XMLStreamException {
		return org.sbml.jsbml.xml.stax.SBMLReader.readSBMLFromStream(stream);
	}

	/**
	 * 
	 * @param xml
	 * @return
	 * @throws XMLStreamException
	 * @deprecated directly use {@link
	 *             org.sbml.jsbml.xml.stax.SBMLReader.#readSBMLFromString(String)
	 *             }
	 */
	@Deprecated
	public SBMLDocument readSBMLFromString(String xml)
			throws XMLStreamException {
		return org.sbml.jsbml.xml.stax.SBMLReader.readSBMLFromString(xml);
	}

}
