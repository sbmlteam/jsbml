/*
 * $Id: JSBML.java 181 2010-04-20 07:28:21Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/JSBML.java $
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * Wrapper class for global methods and constants defined by libSBML.
 * 
 * @author Andreas Dr&auml;ger
 * 
 */
public class JSBML {

	private static final String jsbmlVersion = "0.5.0";

	/**
	 * Converts a string to its corresponding UnitKind_t enumeration value.
	 * 
	 * @param name
	 * @return
	 */
	public static Unit.Kind forName(String name) {
		return Unit.Kind.valueOf(name);
	}

	/**
	 * Returns the libSBML version as a string of the form '1.2.3'.
	 * 
	 * @return
	 */
	public static String getJSBMLDottedVersion() {
		return jsbmlVersion;
	}

	/**
	 * Returns the libSBML version as an integer: version 1.2.3 becomes 10203.
	 * 
	 * @return
	 */
	public static int getJSBMLVersion() {
		return Integer.parseInt(getJSBMLVersionString());
	}

	/**
	 * Returns the libSBML version as a string: version 1.2.3 becomes '10203'.
	 * 
	 * @return
	 */
	public static String getJSBMLVersionString() {
		StringBuilder number = new StringBuilder();
		for (String num : getJSBMLDottedVersion().split(".")) {
			number.append(num);
		}
		return number.toString();
	}

	/**
	 * Predicate for testing whether a given string corresponds to a predefined
	 * UnitKind_t enumeration value.
	 * 
	 * @param string
	 * @param level
	 * @param version
	 * @return
	 */
	public static boolean isValidUnitKindString(String string, int level,
			int version) {
		return Unit.Kind.isValidUnitKindString(string, level, version);
	}

	/**
	 * Parses a text-string mathematical formula and returns a representation as
	 * an Abstract Syntax Tree.
	 * 
	 * @param formula
	 * @return
	 */
	public static ASTNode parseFormula(String formula) {
		return ASTNode.parseFormula(formula);
	}

	/**
	 * Reads the MathML from the given XML string, constructs a corresponding
	 * abstract syntax tree, and returns a pointer to the root of the tree.
	 * 
	 * @param xml
	 * @return
	 */
	public static ASTNode readMathMLFromString(String xml) {
		return ASTNode.readMathMLFromString(xml);
	}

	/**
	 * Reads an SBML document from the given file filename.
	 * 
	 * @param filename
	 * @return
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 */
	public static SBMLDocument readSBML(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return SBMLReader.readSBML(fileName);
	}

	/**
	 * Reads an SBML document from the given file filename.
	 * 
	 * @param fileName
	 * @return
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 */
	public static SBMLDocument readSBMLFromFile(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return SBMLReader.readSBML(fileName);
	}

	/**
	 * Reads an SBML document from a string assumed to be in XML format.
	 * 
	 * @param xml
	 * @return
	 * @throws XMLStreamException
	 */
	public static SBMLDocument readSBMLFromString(String xml)
			throws XMLStreamException {
		return SBMLReader.readSBMLFromString(xml);
	}

	/**
	 * Converts a UnitKind_t enumeration value to a text string equivalent.
	 * 
	 * @param uk
	 * @return
	 */
	public static String toString(Unit.Kind uk) {
		return uk.toString();
	}

	/**
	 * Tests for logical equality between two given UnitKind_t values.
	 * 
	 * @param uk1
	 * @param uk2
	 * @return
	 */
	public static boolean UnitKind_equals(Unit.Kind uk1, Unit.Kind uk2) {
		return uk1.equals(uk2);
	}

	/**
	 * Writes the given ASTNode (and its children) to a string as MathML, and
	 * returns the string.
	 * 
	 * @param node
	 * @return
	 */
	public static String writeMathMLToString(ASTNode node) {
		return node.toMathML();
	}

	/**
	 * Writes the given SBML document to filename.
	 * 
	 * @param d
	 * @param filename
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 */
	public static void writeSBML(SBMLDocument d, String filename)
			throws FileNotFoundException, XMLStreamException,
			InstantiationException, IllegalAccessException {
		SBMLWriter.write(d, filename);
	}

	/**
	 * Writes the given SBML document to an in-memory string and returns a
	 * pointer to it.
	 * 
	 * @param d
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws XMLStreamException
	 */
	public static String writeSBMLToString(SBMLDocument d)
			throws XMLStreamException, InstantiationException,
			IllegalAccessException {
		return SBMLWriter.writeSBMLToString(d);
	}

}
