/*
 * $Id$
 * $URL$
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

import org.sbml.jsbml.resources.Resource;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * Wrapper class for global methods and constants defined by JSBML.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * 
 */
public class JSBML {

	public static final int DUPLICATE_OBJECT_ID = -6;

	/**
	 * Error message for the case that an invalid unit identifier is to be added
	 * to this object.
	 */
	public static final String ILLEGAL_UNIT_EXCEPTION_MSG = "Cannot identify unit %s in the model. Only a valid unit kind or the identifier of an existing unit definition are allowed.";
	public static final int INDEX_EXCEEDS_SIZE = -1;
	public static final int INVALID_ATTRIBUTE_VALUE = -4;
	public static final int INVALID_OBJECT = -5;
	public static final int INVALID_XML_OPERATION = -9;
	/**
	 * The current version number of JSBML.
	 */
	private static final String jsbmlVersion = "0.8.15";
	public static final int LEVEL_MISMATCH = -7;
	public static final int OPERATION_FAILED = -3;
	public static final int OPERATION_SUCCESS = 0;
	/**
	 * This indicates that the {@link Model} has not been set properly or that
	 * an element tries to access its containing model but this is not possible.
	 */
	public static final String UNDEFINED_MODEL_MSG = "Cannot access containing model.";

	/**
	 * This message indicates that a problem occurred but the current class
	 * cannot give any more precise information about the reasons.
	 */
	public static final String UNDEFINED_PARSE_ERROR_MSG = "An error occur while creating a parser: %s.";
	/**
	 * This message indicates that a problem occurred but the current class
	 * cannot give any more precise information about the reasons.
	 */
	public static final String UNDEFINED_PARSING_ERROR_MSG = "An error occur while parsing the file : %s.";
	public static final int UNEXPECTED_ATTRIBUTE = -2;

	public static final int VERSION_MISMATCH = -8;
	
	/**
	 * URI for the RDF syntax name space definition for VCards.
	 */
	public static final transient String URI_RDF_VCARD_NS = "http://www.w3.org/2001/vcard-rdf/3.0#";
	/**
	 * URI for purl terms.
	 */
	public static final transient String URI_PURL_TERMS = "http://purl.org/dc/terms/";
	/**
	 * URI for the definition of MathML.
	 */
	public static final transient String URI_MATHML_DEFINITION = "http://www.w3.org/1998/Math/MathML";
	/**
	 * URI for the definition of purl elements
	 */
	public static final transient String URI_PURL_ELEMENTS = "http://purl.org/dc/elements/1.1/";
	/**
	 * URI for the definition of XHTML.
	 */
	public static final transient String URI_XHTML_DEFINITION = "http://www.w3.org/1999/xhtml";

	
	/**
	 * Adds the given {@link UnitDefinition} to the given model or returns the
	 * identifier of an equivalent {@link UnitDefinition} that is already part
	 * of the model. In case that the given model is null, the return value of
	 * this method will also be null.
	 * 
	 * @param model
	 *            The model where to add the given {@link UnitDefinition}
	 * @param units
	 *            The {@link UnitDefinition} to be checked and possibly added to
	 *            the given {@link Model}
	 * @return The identifier of the given {@link UnitDefinition} if this one
	 *         has been added to the given model, the identifier of an
	 *         equivalent {@link UnitDefinition} that is already part of the
	 *         given model, or null.
	 */
	public static String addUnitDefinitionIfNotContained(Model model,
			UnitDefinition units) {
		if (model != null) {
			return model.addUnitDefinitionOrReturnIdenticalUnit(units);
		}
		return null;
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
     * Loads {@link Properties} from a configuration file with the given path
     * assuming that all values represent class names.
     * @param <T>
     * @param path
     * @param whereToPutProperties
     */
    @SuppressWarnings("unchecked")
    public static <T> void loadClasses(String path,
    		Map<String, Class<? extends T>> whereToPutProperties) {
    	Properties p = new Properties();
    	try {
    		p.loadFromXML(Resource.getInstance().getStreamFromResourceLocation(
    				path));
    		for (Map.Entry<Object, Object> entry : p.entrySet()) {
    			whereToPutProperties.put(entry.getKey().toString(),
    					(Class<T>) Class.forName(entry.getValue().toString()));
    		}
    	} catch (InvalidPropertiesFormatException e) {
    		throw new IllegalArgumentException(String.format(
    				"The format of the file %s is incorrect.", path));
    	} catch (IOException e) {
    		throw new IllegalArgumentException(String.format(
    				"There was a problem opening the file %s.", path));
    	} catch (ClassNotFoundException e) {
    		throw new IllegalArgumentException(
    				String.format(
    								"There was a problem loading the file %s: %s. Please make sure the resources directory is included in the Java class path.",
    								path, e.getMessage()));
    	}
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
    	SBMLReader reader = new SBMLReader();
    	
    	return reader.readSBML(fileName);	 
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
    	SBMLReader reader = new SBMLReader();
    	
    	return reader.readSBML(fileName);	 
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
    	SBMLReader reader = new SBMLReader();
    	
    	return reader.readSBMLFromString(xml);	 
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
                    throws XMLStreamException, FileNotFoundException, SBMLException 
    {	 
    	SBMLWriter writer = new SBMLWriter();
    	writer.write(d, filename);	 
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
	                throws XMLStreamException, SBMLException 
	{	 
		SBMLWriter writer = new SBMLWriter();
		return writer.writeSBMLToString(d);	 
	}
}
