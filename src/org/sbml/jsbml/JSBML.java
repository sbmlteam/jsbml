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
 * Wrapper class for global methods and constants defined by JSBML.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * 
 */
public class JSBML {

	/**
	 * The current version number of JSBML.
	 */
	private static final String jsbmlVersion = "0.8.0";

	public static final int OPERATION_SUCCESS = 0;
	public final static int INDEX_EXCEEDS_SIZE = -1;
	public final static int UNEXPECTED_ATTRIBUTE = -2;
	public final static int OPERATION_FAILED = -3;
	public final static int INVALID_ATTRIBUTE_VALUE = -4;
	public final static int INVALID_OBJECT = -5;
	public final static int DUPLICATE_OBJECT_ID = -6;
	public final static int LEVEL_MISMATCH = -7;
	public final static int VERSION_MISMATCH = -8;
	public final static int INVALID_XML_OPERATION = -9;

	/**
	 * Converts a string to its corresponding {@link Unit.Kind} enumeration value.
	 * 
	 * @param name a unit kind string.
	 * @return a Unit.Kind object.
	 * @throws IllegalArgumentException is the unit kind string is not a valid unit kind.
	 */
	public static Unit.Kind forName(String name) {
		return Unit.Kind.valueOf(name.toUpperCase());
	}

	/**
	 * Returns the JSBML version as a string of the form '1.2.3'.
	 * 
	 * @return the JSBML version as a string of the form '1.2.3'.
	 */
	public static String getJSBMLDottedVersion() {
		return jsbmlVersion;
	}

	/**
	 * Returns the JSBML version as an integer: version 1.2.3 becomes 10203.
	 * 
	 * @return the JSBML version as an integer: version 1.2.3 becomes 10203.
	 */
	public static int getJSBMLVersion() {
		return Integer.parseInt(getJSBMLVersionString());
	}

	/**
	 * Returns the JSBML version as a string: version 1.2.3 becomes '10203'.
	 * 
	 * @return the JSBML version as a string: version 1.2.3 becomes '10203'.
	 */
	public static String getJSBMLVersionString() {
		StringBuilder number = new StringBuilder();
		for (String num : getJSBMLDottedVersion().split(".")) {
			number.append(num);
		}
		return number.toString();
	}

	/**
	 * Tests whether a given string corresponds to a predefined
	 * {@link Unit.Kind} enumeration value.
	 * 
	 * @param string the unit string.
	 * @param level the SBML level.
	 * @param version the SBML version.
	 * @return true if the given string is valid for the particular SBML level and version, false otherwise.
	 */
	public static boolean isValidUnitKindString(String string, int level,
			int version) {
		return Unit.Kind.isValidUnitKindString(string, level, version);
	}

	/**
	 * Parses a text-string mathematical formula and returns a representation as
	 * an Abstract Syntax Tree.
	 * 
	 * @param formula a text-string mathematical formula.
	 * @return an <code>ASTNode</code> representing the formula.
	 */
	public static ASTNode parseFormula(String formula) {
		return ASTNode.parseFormula(formula);
	}
	
	/**
	 * Converts an {@link ASTNode} formula to a text string using a specific
	 * syntax for mathematical formulas.
	 * <p>
	 * The text-string form of
	 * mathematical formulas produced by <code><a
	 * href='libsbml.html#formulaToString(org.sbml.libsbml.{@link ASTNode})'>
	 * libsbml.formulaToString()</a></code> and read by
	 * <code><a href='libsbml.html#parseFormula(java.lang.String)'>
	 * libsbml.parseFormula()</a></code> are
	 * simple C-inspired infix notation taken from SBML Level&nbsp;1.  A
	 * formula in this text-string form therefore can be handed to a program
	 * that understands SBML Level&nbsp;1 mathematical expressions, or used as
	 * part of a formula translation system.  The syntax is described in detail
	 * in the libsbml documentation for <a href="http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/ASTNode.html">
	 * ASTNode</a>.
	 * <p>
	 * @param tree the root of the {@link ASTNode} formula expression tree
	 * <p>
	 * @return the formula from the given AST as an SBML Level 1 text-string
	 * mathematical formula.
	 * NULL is returned if the given argument is NULL.
	 * <p>
	 */
	public static String formulaToString(ASTNode node) {
		try {
			return node.toFormula();
		} catch (SBMLException e) {
			// TODO : inform the user of the problem.
		}
		
		return "";
	}

	/**
	 * Reads the MathML from the given XML string, constructs a corresponding
	 * abstract syntax tree, and returns a pointer to the root of the tree.
	 * 
	 * @param xml the MathML XML string.
	 * @return an <code>ASTNode</code>
	 */
	public static ASTNode readMathMLFromString(String xml) {
		return ASTNode.readMathMLFromString(xml);
	}

	/**
	 * Reads an SBML document from the given file.
	 * 
	 * @param filename the file name.
	 * @return an <code>SBMLDocument</code> object.
	 * @throws XMLStreamException if any error occur while creating the XML document.
	 * @throws FileNotFoundException if the file name is invalid
	 */
	public static SBMLDocument readSBML(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return SBMLReader.readSBML(fileName);
	}

	/**
	 * Reads an SBML document from the given file.
	 * 
	 * @param fileName the file name.
	 * @return an <code>SBMLDocument</code> object.
	 * @throws XMLStreamException if any error occur while creating the XML document.
	 * @throws FileNotFoundException if the file name is invalid
	 */
	public static SBMLDocument readSBMLFromFile(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return SBMLReader.readSBML(fileName);
	}

	/**
	 * Reads an SBML document from a string assumed to be in XML format.
	 * 
	 * @param xml the SBMLDocument as XML.
	 * @return an <code>SBMLDocument</code> object.
	 * @throws XMLStreamException if any error occur while creating the XML document.
	 */
	public static SBMLDocument readSBMLFromString(String xml)
			throws XMLStreamException {
		return SBMLReader.readSBMLFromString(xml);
	}

	/**
	 * Converts a {@link Unit.Kind} enumeration value to a text string equivalent.
	 * 
	 * @param uk the Unit.Kind
	 * @return a string representation of the given Unit.Kind
	 */
	public static String toString(Unit.Kind uk) {
		return uk.toString();
	}

	/**
	 * Tests for logical equality between two given {@link Unit.Kind} values.
	 * 
	 * @param uk1 the first Unit.Kind
	 * @param uk2 the second Unit.Kind
	 * @return true if the two <code>Unit.Kind</code> are equals, false otherwise.
	 */
	public static boolean UnitKind_equals(Unit.Kind uk1, Unit.Kind uk2) {
		return uk1.equals(uk2);
	}

	/**
	 * Writes the given ASTNode (and its children) to a string as MathML, and
	 * returns the string.
	 * 
     * @param node the <code>ASTNode</code>
     * @return the MathML string representing the given <code>ASTNode</code>
	 * @throws XMLStreamException
	 * @throws SBMLException
	 */
	public static String writeMathMLToString(ASTNode node)
			throws XMLStreamException, SBMLException {
		return node.toMathML();
	}

	/**
     * Writes the XML representation of an SBML document to a file.
     *
     * @param d the <code>SBMLdocument</code>
     * @param filename the file name
     * @throws XMLStreamException if any error occur while creating the XML document.
     * @throws FileNotFoundException if the file name is invalid
	 * @throws SBMLException 
	 */
	public static void writeSBML(SBMLDocument d, String filename)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		SBMLWriter.write(d, filename);
	}

	/**
     * Writes the given SBML document to an in-memory string.
     *
     * @param d the  <code>SBMLdocument</code>
     * @return the XML representation of the <code>SBMLdocument</code> as a String.
     * @throws XMLStreamException if any error occur while creating the XML document.
	 * @throws SBMLException 
	 */
	public static String writeSBMLToString(SBMLDocument d)
			throws XMLStreamException, SBMLException {
		return SBMLWriter.writeSBMLToString(d);
	}

	public static void main(String[] args) {
		System.out.println(JSBML.forName("METER"));
		System.out.println(JSBML.forName("test invalid"));
	}
	
}
