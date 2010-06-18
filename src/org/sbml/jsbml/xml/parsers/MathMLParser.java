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

package org.sbml.jsbml.xml.parsers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.stax.ReadingParser;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;
import org.sbml.jsbml.xml.stax.WritingParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * A MathMLParser is used to parse the MathML expressions injected into a SBML
 * file. The name space URI of this parser is
 * "http://www.w3.org/1998/Math/MathML". This parser is able to read and write
 * MathML expressions (implements ReadingParser and WritingParser).
 * 
 * @author marine
 * 
 */
public class MathMLParser implements ReadingParser, WritingParser {

	/**
	 * The URI for the definition of the csymbol for avogadro.
	 */
	private static final String definitionURIavogadro = "http://www.sbml.org/sbml/symbols/avogadro";

	/**
	 * The URI for the definition of the csymbol for delay.
	 */
	private static final String definitionURIdelay = "http://www.sbml.org/sbml/symbols/delay";

	/**
	 * The URI for the definition of the csymbol for time.
	 */
	private static final String definitionURItime = "http://www.sbml.org/sbml/symbols/time";

	/**
	 * The name space URI of MathML.
	 */
	private static final String namespaceURI = "http://www.w3.org/1998/Math/MathML";

	/**
	 * The additional MathML name space for units to numbers.
	 */
	private static final String namespaceURISBML = "http://www.sbml.org/sbml/level3/version1/core";
	/**
	 * The SBML attribute for the additional namespace.
	 */
	private static final String sbmlNS = "xmlns:sbml";

	/**
	 * The attribute for SBML units.
	 */
	private static final String sbmlUnits = "sbml:units";

	/**
	 * Recursively checks whether the given {@link Node} contains any numbers
	 * that refer to unit declarations. This check is necessary because this
	 * feature has become available in SBML Level 3 and is therefore invalid in
	 * earlier levels.
	 * 
	 * @param node
	 *            A node to be checked.
	 * @return
	 */
	public static boolean checkContainsNumbersReferingToUnits(Node node) {
		boolean containsNumbersReferingToUnits = false;
		int i;
		if (node.getNodeName().equals("cn")) {
			for (i = 0; i < node.getAttributes().getLength(); i++) {
				if (node.getAttributes().item(i).toString().startsWith(
						MathMLParser.getSBMLUnitsAttribute()))
					return true;
			}
		}
		if (node.hasChildNodes()) {
			for (i = 0; i < node.getChildNodes().getLength(); i++) {
				containsNumbersReferingToUnits |= checkContainsNumbersReferingToUnits(node
						.getChildNodes().item(i));
			}
		}
		return containsNumbersReferingToUnits;
	}

	/**
	 * 
	 * @param node
	 * @param sbmlLevel
	 * @return
	 * @throws SBMLException
	 */
	public static Document createMathMLDocumentFor(Node node, int sbmlLevel)
			throws SBMLException {
		Element math;
		Document document = node.getOwnerDocument();
		if (document.getDocumentElement() == null) {
			math = document.createElementNS(MathMLParser.getNamespaceURI(),
					"math");
			if (checkContainsNumbersReferingToUnits(node)) {
				if ((sbmlLevel < 1) || (sbmlLevel > 2)) {
					math.setAttribute(MathMLParser.getSBMLNSAttribute(),
							MathMLParser.getXMLnamespaceSBML());
				} else {
					// TODO: We could just remove all references to units
					// instead of throwing this exception. What is better? Loss
					// of information or exception?
					throw new SBMLException(
							"math element contains numbers that refer to unit definitions and therefore requires at least SBML Level 3");
				}
			}
			document.appendChild(math);
		} else {
			math = document.getDocumentElement();
			for (int i = math.getChildNodes().getLength() - 1; i >= 0; i--) {
				math.removeChild(math.getChildNodes().item(i));
			}
		}
		math.appendChild(node);
		return document;
	}

	/**
	 * 
	 * @return
	 */
	public static String getDefinitionURIavogadro() {
		return definitionURIavogadro;
	}

	/**
	 * 
	 * @return
	 */
	public static String getDefinitionURIdelay() {
		return definitionURIdelay;
	}

	/**
	 * 
	 * @return
	 */
	public static String getDefinitionURItime() {
		return definitionURItime;
	}

	/**
	 * @return the namespaceURI
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * 
	 * @return
	 */
	public static String getSBMLNSAttribute() {
		return sbmlNS;
	}

	/**
	 * 
	 * @return
	 */
	public static String getSBMLUnitsAttribute() {
		return sbmlUnits;
	}

	/**
	 * 
	 * @return
	 */
	public static String getXMLnamespaceSBML() {
		return namespaceURISBML;
	}

	/**
	 * Write output as {@link String}.
	 * 
	 * @param omitXMLDeclaration
	 * @param indenting
	 * @param indent
	 * @return
	 * @throws IOException
	 * @throws SBMLException
	 *             If illegal references to units are made for numbers and the
	 *             SBML Level has been set to values before level 3.
	 */
	public static String toMathML(Document doc, boolean omitXMLDeclaration,
			boolean indenting, int indent) throws IOException, SBMLException {
		StringWriter sw = new StringWriter();
		toMathML(sw, doc, omitXMLDeclaration, indenting, indent);
		return sw.toString();
	}

	/**
	 * 
	 * @param out
	 * @param doc
	 * @param omitXMLDeclaration
	 * @param indenting
	 * @param indent
	 * @throws IOException
	 * @throws SBMLException
	 */
	public static void toMathML(Writer out, Document doc,
			boolean omitXMLDeclaration, boolean indenting, int indent)
			throws IOException, SBMLException {
		OutputFormat format = new OutputFormat(doc);
		format.setOmitXMLDeclaration(omitXMLDeclaration);
		format.setStandalone(true);
		format.setMethod("xml");
		format.setMediaType("text/xml");
		format.setEncoding("UTF-8");
		format.setAllowJavaNames(true);
		format.setIndenting(indenting);
		format.setIndent(indent);
		XMLSerializer serializer = new XMLSerializer(new BufferedWriter(out),
				format);
		serializer.setNamespaces(true);
		serializer.serialize(doc);
	}

	/**
	 * The number of white spaces for the indent if this is to be used.
	 */
	private short indent;

	/**
	 * Decides whether or not the content of the MathML string should be
	 * indented.
	 */
	private boolean indenting;

	/**
	 * If true no XML declaration will be written into the MathML output,
	 * otherwise the XML version will appear at the beginning of the output.
	 */
	private boolean omitXMLDeclaration;

	/**
	 * 
	 * @return
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getIndenting() {
		return indenting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object
	 * sbase)
	 */
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getOmitXMLDeclaration() {
		return omitXMLDeclaration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String
	 * ElementName, String AttributeName, String value, String prefix, boolean
	 * isLastAttribute, Object contextObject)
	 */
	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String
	 * elementName, String characters, Object contextObject)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument
	 * sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String
	 * ElementName, String prefix, boolean isNested, Object contextObject)
	 */
	public void processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String
	 * elementName, String URI, String prefix, String localName, boolean
	 * hasAttributes, boolean isLastNamespace, Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
	 * ElementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
	 * Object contextObject)
	 */
	public Object processStartElement(String ElementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param indent
	 *            the indent to set
	 */
	public void setIndent(int indent) {
		if ((indent < 0) || (Short.MAX_VALUE < indent)) {
			throw new IllegalArgumentException(StringTools.concat("indent ",
					indent, " is out of the range [0, ",
					Short.toString(Short.MAX_VALUE) + "]").toString());
		}
		this.indent = (short) indent;
	}

	/**
	 * @param indenting
	 *            the indenting to set
	 */
	public void setIndenting(boolean indenting) {
		this.indenting = indenting;
	}

	/**
	 * @param omitXMLDeclaration
	 *            the omitXMLDeclaration to set
	 */
	public void setOmitXMLDeclaration(boolean omitXMLDeclaration) {
		this.omitXMLDeclaration = omitXMLDeclaration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.writeAttributes(SBMLObjectForXML xmlObject,
	 * Object sbmlElementToWrite)
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeCharacters(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeElement(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeNamespaces(SBMLObjectForXML
	 * xmlObject, Object sbmlElementToWrite)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub

	}

}
