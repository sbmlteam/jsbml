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
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.resources.Resource;
import org.sbml.jsbml.text.parser.ParseException;
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
	private static final String jsbmlVersion = "0.8.10";

	public static final int OPERATION_SUCCESS = 0;
	public static final int INDEX_EXCEEDS_SIZE = -1;
	public static final int UNEXPECTED_ATTRIBUTE = -2;
	public static final int OPERATION_FAILED = -3;
	public static final int INVALID_ATTRIBUTE_VALUE = -4;
	public static final int INVALID_OBJECT = -5;
	public static final int DUPLICATE_OBJECT_ID = -6;
	public static final int LEVEL_MISMATCH = -7;
	public static final int VERSION_MISMATCH = -8;
	public static final int INVALID_XML_OPERATION = -9;

	/**
	 * Message to display in cases that two objects do not have identical level
	 * attributes. Requires the following replacement arguments: Class name of
	 * first element, version in first element, class name of second element and
	 * level in second argument.
	 */
	public static final String LEVEL_MISMATCH_MSG = "Level mismatch between %s in L %d and %s in L %d";

	/**
	 * Message to display in cases that two objects do not have identical
	 * version attributes. Requires the following replacement arguments: Class
	 * name of first element, version in first element, class name of second
	 * element and version in second argument.
	 */
	public static final String VERSION_MISMATCH_MSG = "Version mismatch between %s in V %d and %s in V %d.";
	/**
	 * Message to indicate that a certain property cannot be set for the current level/version combination.
	 */
	public static final String PROPERTY_UNDEFINED_EXCEPTION_MSG = "Property %s is not defined for Level %s and Version %s";
	/**
	 * Message to indicate that an invalid combination of the level and version
	 * attribute has been set.
	 */
	public static final String UNDEFINED_LEVEL_VERSION_COMBINATION_MSG = "Undefined combination of Level %d and Version %d.";
	/**
	 * This indicates that the {@link Model} has not been set properly or that an element tries to access its containing model but this is not possible.
	 */
	public static final String UNDEFINED_MODEL_MSG = "Cannot access containing model.";
	/**
	 * This message indicates that a problem occurred but the current class
	 * cannot give any more precise information about the reasons.
	 */
	public static final String UNDEFINED_PARSE_ERROR_MSG = "An error occur while creating a parser: %s.";

	/**
	 * Helper method that takes as argument the owner of a {@link ListOf}
	 * object, the currently set list in this owner, the desired new list and
	 * the {@link Type} that is to be set. It then looks if old elements have to
	 * be deleted or new elements are to be added to the original list. Finally,
	 * it sets a pointer from the original list to the parent. To make sure
	 * everything is correct, a pointer to the modified list is returned, which
	 * should then in turn be assigned to the variable storing the original
	 * list. Otherwise the modification might get lost.
	 * 
	 * @param <T>
	 * @param owner
	 * @param origList
	 * @param newList
	 * @param type
	 * @return a pointer to the list that has been changed.
	 */
	public static <T extends SBase> ListOf<T> addAllOrReplace(SBase owner,
			ListOf<T> origList, ListOf<T> newList, ListOf.Type type) {
		if (newList != null) {
			if (owner.getLevel() != newList.getLevel()) {
				throw new IllegalArgumentException(levelMismatchMessage(owner,
						newList));
			}
			if (owner.getVersion() != newList.getVersion()) {
				throw new IllegalArgumentException(versionMismatchMessage(
						owner, newList));
			}
		}
		if ((newList == null) || (origList != null)) {
			if (origList != null) {
				origList.clear();
			}
			if (newList == null) {
				origList = null;
			} else {
				origList.addAll(newList);
			}
		} else {
			origList = newList;
			origList.setSBaseListType(type);
			owner.setThisAsParentSBMLObject(origList);
		}
		return origList;
	}
	
	/**
	 * Tests for logical equality between two given {@link Unit.Kind} values.
	 * 
	 * @param uk1
	 *            the first Unit.Kind
	 * @param uk2
	 *            the second Unit.Kind
	 * @return true if the two <code>Unit.Kind</code> are equals, false
	 *         otherwise.
	 */
	public static boolean equals(Unit.Kind uk1, Unit.Kind uk2) {
		return uk1.equals(uk2);
	}

	/**
	 * Converts an {@link ASTNode} formula to a text string using a specific
	 * syntax for mathematical formulas.
	 * <p>
	 * The text-string form of mathematical formulas produced by <code><a
	 * href='libsbml.html#formulaToString(org.sbml.libsbml.{@link ASTNode})'>
	 * libsbml.formulaToString()</a></code> and read by <code><a href='libsbml.html#parseFormula(java.lang.String)'>
	 * libsbml.parseFormula()</a></code> are simple C-inspired infix notation taken from SBML
	 * Level&nbsp;1. A formula in this text-string form therefore can be handed
	 * to a program that understands SBML Level&nbsp;1 mathematical expressions,
	 * or used as part of a formula translation system. The syntax is described
	 * in detail in the libsbml documentation for <a href=
	 * "http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/ASTNode.html"
	 * > ASTNode</a>.
	 * <p>
	 * 
	 * @param tree
	 *            the root of the {@link ASTNode} formula expression tree
	 *            <p>
	 * @return the formula from the given AST as an SBML Level 1 text-string
	 *         mathematical formula. NULL is returned if the given argument is
	 *         NULL.
	 *         <p>
	 * @throws SBMLException
	 *             In case the given {@link ASTNode} tree contains invalid
	 *             nodes.
	 */
	public static String formulaToString(ASTNode node) throws SBMLException {
		return node.toFormula();
	}

	/**
	 * Converts a string to its corresponding {@link Unit.Kind} enumeration
	 * value.
	 * 
	 * @param name
	 *            a unit kind string.
	 * @return a Unit.Kind object.
	 * @throws IllegalArgumentException
	 *             is the unit kind string is not a valid unit kind.
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
	 * @param string
	 *            the unit string.
	 * @param level
	 *            the SBML level.
	 * @param version
	 *            the SBML version.
	 * @return true if the given string is valid for the particular SBML level
	 *         and version, false otherwise.
	 */
	public static boolean isValidUnitKindString(String string, int level,
			int version) {
		return Unit.Kind.isValidUnitKindString(string, level, version);
	}

	/**
	 * Creates an error message if the level fields of both elements are not
	 * identical, or an empty {@link String} otherwise.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public static String levelMismatchMessage(SBase element1, SBase element2) {
		if (element1.getLevel() != element2.getLevel()) {
			return String.format(VERSION_MISMATCH_MSG, element1
					.getElementName(), element1.getLevel(), element2
					.getElementName(), element2.getLevel());
		}
		return "";
	}

	/**
	 * Loads {@link Properties} from a configuration file with the given path
	 * assuming that all values represent class names.
	 * @param <T>
	 * @param path
	 * @param whereToPutProperties
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Class<?>> void loadClasses(String path,
			Map<String, T> whereToPutProperties) {
		Properties p = new Properties();
		try {
			p.loadFromXML(Resource.getInstance().getStreamFromResourceLocation(
					path));
			for (Map.Entry<Object, Object> entry : p.entrySet()) {
				whereToPutProperties.put(entry.getKey().toString(), (T) Class
						.forName(entry.getValue().toString()));
			}
		} catch (InvalidPropertiesFormatException e) {
			throw new IllegalArgumentException(String.format(
					"The format of the file %s is incorrect.", path));
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format(
					"There was a problem opening the file %s.", path));
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format(
									"There was a problem loading the file %s: %s. Please make sure the resources directory is included in the Java class path.",
									path, e.getMessage()));
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(JSBML.forName("METER"));
		System.out.println(JSBML.forName("test invalid"));
	}

	/**
	 * Parses a text-string mathematical formula and returns a representation as
	 * an Abstract Syntax Tree.
	 * 
	 * @param formula
	 *            a text-string mathematical formula.
	 * @return an <code>ASTNode</code> representing the formula.
	 * @throws ParseException
	 *             If the given formula is not of valid format or cannot be
	 *             parsed for other reasons.
	 */
	public static ASTNode parseFormula(String formula) throws ParseException {
		return ASTNode.parseFormula(formula);
	}

	/**
	 * Reads the MathML from the given XML string, constructs a corresponding
	 * abstract syntax tree, and returns a pointer to the root of the tree.
	 * 
	 * @param xml
	 *            the MathML XML string.
	 * @return an <code>ASTNode</code>
	 */
	public static ASTNode readMathMLFromString(String xml) {
		return ASTNode.readMathMLFromString(xml);
	}

	/**
	 * Reads an SBML document from the given file.
	 * 
	 * @param filename
	 *            the file name.
	 * @return an <code>SBMLDocument</code> object.
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 * @throws FileNotFoundException
	 *             if the file name is invalid
	 */
	public static SBMLDocument readSBML(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return SBMLReader.readSBML(fileName);
	}

	/**
	 * Reads an SBML document from the given file.
	 * 
	 * @param fileName
	 *            the file name.
	 * @return an <code>SBMLDocument</code> object.
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 * @throws FileNotFoundException
	 *             if the file name is invalid
	 */
	public static SBMLDocument readSBMLFromFile(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return SBMLReader.readSBML(fileName);
	}

	/**
	 * Reads an SBML document from a string assumed to be in XML format.
	 * 
	 * @param xml
	 *            the SBMLDocument as XML.
	 * @return an <code>SBMLDocument</code> object.
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 */
	public static SBMLDocument readSBMLFromString(String xml)
			throws XMLStreamException {
		return SBMLReader.readSBMLFromString(xml);
	}

	/**
	 * Converts a {@link Unit.Kind} enumeration value to a text string
	 * equivalent.
	 * 
	 * @param uk
	 *            the Unit.Kind
	 * @return a string representation of the given Unit.Kind
	 */
	public static String toString(Unit.Kind uk) {
		return uk.toString();
	}

	/**
	 * Creates an error message if the version fields of both elements are not
	 * identical, or an empty {@link String} otherwise.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public static String versionMismatchMessage(SBase element1, SBase element2) {
		if (element1.getVersion() != element2.getVersion()) {
			return String.format(VERSION_MISMATCH_MSG, element1
					.getElementName(), element1.getVersion(), element2
					.getElementName(), element2.getVersion());
		}
		return "";
	}
	
	/**
	 * Creates an error message pointing out that the property of the given name is not defined
	 * in the Level/Version combination of the given {@link SBase}.
	 * 
	 * @param property
	 * @param sbase
	 * @return
	 */
	public static String propertyUndefinedMessage(String property, SBase sbase) {
		return String.format(PROPERTY_UNDEFINED_EXCEPTION_MSG, property,
				Integer.valueOf(sbase.getLevel()), Integer.valueOf(sbase.getVersion()));
	}

	/**
	 * Writes the given ASTNode (and its children) to a string as MathML, and
	 * returns the string.
	 * 
	 * @param node
	 *            the <code>ASTNode</code>
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
	 * @param d
	 *            the <code>SBMLdocument</code>
	 * @param filename
	 *            the file name
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 * @throws FileNotFoundException
	 *             if the file name is invalid
	 * @throws SBMLException
	 */
	public static void writeSBML(SBMLDocument d, String filename)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		SBMLWriter.write(d, filename);
	}

	/**
	 * Writes the given SBML document to an in-memory string.
	 * 
	 * @param d
	 *            the <code>SBMLdocument</code>
	 * @return the XML representation of the <code>SBMLdocument</code> as a
	 *         String.
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 * @throws SBMLException
	 */
	public static String writeSBMLToString(SBMLDocument d)
			throws XMLStreamException, SBMLException {
		return SBMLWriter.writeSBMLToString(d);
	}

}
