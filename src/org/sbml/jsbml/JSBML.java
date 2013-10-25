/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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
import java.text.MessageFormat;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.resources.Resource;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * Wrapper class for global methods and constants defined by JSBML.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class JSBML {

	public static final int DUPLICATE_OBJECT_ID = -6;

	/**
	 * Error message for the case that an invalid unit identifier is to be added
	 * to this object.
	 */
	public static final String ILLEGAL_UNIT_EXCEPTION_MSG = "Cannot identify unit {0} in the model. Only a valid unit kind or the identifier of an existing unit definition are allowed.";
	public static final int INDEX_EXCEEDS_SIZE = -1;
	public static final int INVALID_ATTRIBUTE_VALUE = -4;
	public static final int INVALID_OBJECT = -5;
	public static final int INVALID_XML_OPERATION = -9;
	/**
	 * The current version number of JSBML.
	 */
	private static final String jsbmlVersion = "1.0-rc1"; // TODO : replace automatically this version number with [BUILD.NUMBER]
	
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
	 * URI for purl terms.
	 */
	public static final transient String URI_PURL_TERMS = "http://purl.org/dc/terms/";
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
	 * this method will also be {@code null}.
	 * 
	 * @param model
	 *            The model where to add the given {@link UnitDefinition}
	 * @param units
	 *            The {@link UnitDefinition} to be checked and possibly added to
	 *            the given {@link Model}
	 * @return The identifier of the given {@link UnitDefinition} if this one
	 *         has been added to the given model, the identifier of an
	 *         equivalent {@link UnitDefinition} that is already part of the
	 *         given model, or {@code null}.
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
   * The text-string form of mathematical formulas produced by
   * {@link ASTNode#formulaToString(ASTNode)} and read by
   * {@link ASTNode#parseFormula(String)} are simple C-inspired infix notation
   * taken from SBML Level&nbsp;1. A formula in this text-string form therefore
   * can be handed to a program that understands SBML Level&nbsp;1 mathematical
   * expressions, or used as part of a formula translation system. The syntax is
   * described in detail in the libsbml documentation for <a href=
   * "http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/ASTNode.html"
   * >ASTNode</a>.
   * <p>
   * 
   * @param tree
   *        the root of the {@link ASTNode} formula expression tree
   * @return the formula from the given AST as an SBML Level 1 text-string
   *         mathematical formula. {@code null} is returned if the given
   *         argument is {@code null}.
   * @throws SBMLException
   *         In case the given {@link ASTNode} tree contains invalid nodes.
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
	 * Returns the JSBML version as an integer: version 1.2.3 becomes 123.
	 * 
	 * @return the JSBML version as an integer: version 1.2.3 becomes 123.
	 */
	public static int getJSBMLVersion() {
		String jsbmlVersionString = getJSBMLVersionString(); 
		int indexOfDash = jsbmlVersionString.indexOf("-");
		
		if (indexOfDash != -1) {
			jsbmlVersionString = jsbmlVersionString.substring(0, indexOfDash);
		}
		
		return Integer.parseInt(jsbmlVersionString);
	}
	
	
	/**
	 * Returns the JSBML version as a string: version 1.2.3 becomes '123'.
	 * 
	 * @return the JSBML version as a string: version 1.2.3 becomes '123'.
	 */
	public static String getJSBMLVersionString() {
		StringBuilder number = new StringBuilder();
		for (String num : jsbmlVersion.split("\\.")) {
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
      Logger logger = Logger.getLogger(JSBML.class);
    	Properties p = new Properties();
    	try {
    		p.loadFromXML(Resource.getInstance().getStreamFromResourceLocation(path));
    		for (Map.Entry<Object, Object> entry : p.entrySet()) {
    			try {
            whereToPutProperties.put(entry.getKey().toString(),
            		(Class<T>) Class.forName(entry.getValue().toString()));
          } catch (ClassNotFoundException e) {
            logger.debug(MessageFormat.format("Could not load class {0}.", e.getLocalizedMessage()));
          }
    		}
    	} catch (InvalidPropertiesFormatException e) {
    		throw new IllegalArgumentException(MessageFormat.format(
    				"The format of the file {0} is incorrect.", path));
    	} catch (IOException e) {
    		throw new IllegalArgumentException(MessageFormat.format(
    				"There was a problem opening the file {0}. Please make sure the resources directory is included in the Java class path.", 
    				path));
    	}
    }	 
 
    /**	 
     * Parses a text-string mathematical formula and returns a representation as	 
     * an Abstract Syntax Tree.	 
     *	 
     * @param formula	 
     *            a text-string mathematical formula.	 
     * @return an {@code ASTNode} representing the formula.	 
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
     * @return an {@code ASTNode}	 
     */	 
    public static ASTNode readMathMLFromString(String xml) {	 
            return ASTNode.readMathMLFromString(xml);	 
    }	 
 
    /**	 
     * Reads an SBML document from the given file.	 
     *	 
     * @param filename	 
     *            the file name.	 
     * @return an {@code SBMLDocument} object.	 
     * @throws XMLStreamException	 
     *             if any error occur while creating the XML document.	 
     * @throws IOException if the file does not exist or cannot be read.
     */	 
    public static SBMLDocument readSBML(String fileName)	 
                    throws XMLStreamException, IOException {
      return SBMLReader.read(fileName);
    }
 
    /**	 
     * Reads an SBML document from the given file.	 
     *	 
     * @param fileName	 
     *            the file name.	 
     * @return an {@code SBMLDocument} object.	 
     * @throws XMLStreamException	 
     *             if any error occur while creating the XML document.	 
     * @throws IOException if the file does not exist or cannot be read.
     */	 
    public static SBMLDocument readSBMLFromFile(String fileName)	 
        throws XMLStreamException, IOException {	 
      return SBMLReader.read(new File(fileName));	 
    }
    
    
    /**	 
     * Reads an SBML document from a string assumed to be in XML format.	 
     *	 
     * @param xml	 
     *            the SBMLDocument as XML.	 
     * @return an {@code SBMLDocument} object.	 
     * @throws XMLStreamException	 
     *             if any error occur while creating the XML document.	 
     */	 
    public static SBMLDocument readSBMLFromString(String xml)	 
        throws XMLStreamException {	 
      return SBMLReader.read(xml);	 
    } 
 
    /**	 
     * Writes the given ASTNode (and its children) to a string as MathML, and	 
     * returns the string.	 
     *	 
     * @param node	 
     *            the {@code ASTNode}	 
     * @return the MathML string representing the given {@code ASTNode}	 
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
     *            the {@code SBMLdocument}	 
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
      SBMLWriter writer = new SBMLWriter();
      writer.write(d, filename);	 
    }

	/**	 
	 * Writes the given SBML document to an in-memory string.	 
	 *	 
	 * @param d	 
	 *            the {@link SBMLDocument}	 
	 * @return the XML representation of the {@link SBMLDocument} as a	 
	 *         {@link String}.
	 * @throws XMLStreamException	 
	 *             if any error occur while creating the XML document.	 
	 * @throws SBMLException	 
	 */	 
    public static String writeSBMLToString(SBMLDocument d)	 
        throws XMLStreamException, SBMLException {
      SBMLWriter writer = new SBMLWriter();
      return writer.writeSBMLToString(d);
    }
	
	/**
	 * 
	 * @param level
	 * @param version
	 * @return the name space matching the level and version.
	 */
	public static String getNamespaceFrom(int level, int version) {
		if (level == 3) {
			if (version == 1) {
				return SBMLDocument.URI_NAMESPACE_L3V1Core;
			}
		} else if (level == 2) {
			if (version == 4) {
				return SBMLDocument.URI_NAMESPACE_L2V4;
			} else if (version == 3) {
				return SBMLDocument.URI_NAMESPACE_L2V3;
			} else if (version == 2) {
				return SBMLDocument.URI_NAMESPACE_L2V2;
			} else if (version == 1) {
				return SBMLDocument.URI_NAMESPACE_L2V1;
			}
		} else if (level == 1) {
			if ((version == 1) || (version == 2)) {
				return SBMLDocument.URI_NAMESPACE_L1;
			}
		}
		return null;
	}

}
