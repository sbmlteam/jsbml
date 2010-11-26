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

package org.sbml.jsbml.xml.stax;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;
import org.codehaus.stax2.evt.XMLEvent2;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.Message;
import org.sbml.jsbml.util.SimpleSBaseChangeListener;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.parsers.AnnotationParser;
import org.sbml.jsbml.xml.parsers.SBMLCoreParser;
import org.sbml.jsbml.xml.parsers.StringParser;

import com.ctc.wstx.stax.WstxInputFactory;

/**
 * Provides all the methods to read a SBML file.
 * 
 * @author marine
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * 
 */
public class SBMLReader {

	/**
	 * Contains all the relationships namespace URI <=> {@link ReadingParser} implementation classes.
	 */
	private static HashMap<String, Class<? extends ReadingParser>> packageParsers = new HashMap<String, Class<? extends ReadingParser>>();

	/**
	 * Adds a new ReadingParser instance to the initializedParsers if it doesn't
	 * contain a ReadingParser instance for this name space URI.
	 * 
	 * @param elementName
	 *            : localName of the XML element
	 * @param startElement
	 *            : StartElement instance
	 * @param initializedParsers
	 *            : the map containing all the ReadingParser instance.
	 */
	private static void addNamespaceToInitializedPackages(String elementName,
			StartElement startElement,
			HashMap<String, ReadingParser> initializedParsers) 
	{
		@SuppressWarnings("unchecked")
		Iterator<Namespace> nam = startElement.getNamespaces();

		while (nam.hasNext()) {
			Namespace namespace = nam.next();

			if (elementName.equals("annotation")
					&& !packageParsers.containsKey(namespace.getNamespaceURI())) 
			{
				packageParsers.put(namespace.getNamespaceURI(),
						AnnotationParser.class);
			}
			if (packageParsers.containsKey(namespace.getNamespaceURI())
					&& !initializedParsers.containsKey(namespace
							.getNamespaceURI())) 
			{

				ReadingParser newParser;
				try {
					newParser = packageParsers.get(namespace.getNamespaceURI())
							.newInstance();
					initializedParsers.put(namespace.getNamespaceURI(),
							newParser);
				} catch (InstantiationException e) {
					throw new IllegalArgumentException(String.format(
							JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException(String.format(
							JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
				}
			} else if (!packageParsers.containsKey(namespace.getNamespaceURI())
					&& !initializedParsers.containsKey(namespace
							.getNamespaceURI())) 
			{
				// TODO : check that this message does not appear each time that the namespace is encountered
				
				Logger logger = Logger.getLogger(SBMLReader.class);
				logger.warn("Cannot find a parser for the " + namespace.getNamespaceURI() + " namespace");
			}
		}
	}

	/**
	 * Creates the necessary ReadingParser instances and stores them in a
	 * HashMap.
	 * 
	 * @param startElement
	 *            : the StartElement instance
	 * @return the map containing the ReadingParser instances for this
	 *         StartElement.
	 */
	@SuppressWarnings("unchecked")
	private static HashMap<String, ReadingParser> getInitializedPackageParsers(
			StartElement startElement) {
		initializePackageParserNamespaces();

		HashMap<String, ReadingParser> initializedParsers = new HashMap<String, ReadingParser>();

		Iterator<Namespace> nam = startElement.getNamespaces();
		while (nam.hasNext()) {
			Namespace namespace = nam.next();
			String namespaceURI = namespace.getNamespaceURI();

			// If the prefix is an empty String, it means that the namespace is
			// the namespace of the current element (which is the sbml element
			// normally).
			// TODO : wrong assumption, I think - Nico.
			if (namespace.getPrefix().length() == 0) {
				packageParsers.put(namespaceURI, SBMLCoreParser.class);
				initializedParsers.put(namespaceURI, new SBMLCoreParser());
			}
			// If there is a ReadingParser associated with this namespace URI.
			else if (packageParsers.containsKey(namespaceURI)) {
				try {
					initializedParsers.put(namespaceURI, packageParsers.get(
							namespaceURI).newInstance());
				} catch (InstantiationException e) {
					if (isPackageRequired(namespaceURI, startElement)) {
						// throwing an Exception : the package is required for the parsing
						// We could just log the error and try anyway to read the file ??
						SBMLError error = new SBMLError();
						Message message = new Message();
						// TODO : Use something like : String.format(JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage())
						message.setMessage("The package parser for " + namespaceURI + 
								" cannot be created. It is required to understand this model.\n" + e.getMessage());
						error.setMessage(message);
						throw error;
					}
				} catch (IllegalAccessException e) {
					if (isPackageRequired(namespaceURI, startElement)) {
						// throwing an Exception : the package is required for the parsing
						// We could just log the error and try anyway to read the file ??
						SBMLError error = new SBMLError();
						Message message = new Message();
						message.setMessage("The package parser for " + namespaceURI + 
								" cannot be created. It is required to understand this model.\n" + e.getMessage());
						error.setMessage(message);
						throw error;
					}
				}
			}
			// If there is no ReadingParser associated with this namespaceURI
			// and there is no 'required' attribute with this namespace, the
			// declared namespace
			// can be for the annotation and it associates this namespace URI to
			// an AnnotationParser instance.
			// TODO : this is wrong as it is possible that we have a model with a level 3 package that jsbml do not know about
			else {
				if (hasNoRequiredAttributeFor(namespaceURI, startElement)) {
					packageParsers.put(namespaceURI, AnnotationParser.class);
					initializedParsers.put(namespaceURI, new AnnotationParser());
				} else {
					if (isPackageRequired(namespaceURI, startElement)) {
						// TODO throw an Exception : package required for the
						// parsing?
					}
				}
			}
		}
		return initializedParsers;
	}

	/**
	 * Gets the ReadingParser class associated with 'namespace'.
	 * 
	 * @param namespace
	 * @return the ReadingParser class associated with 'namespace'. Null if
	 *         there is not matching ReadingParser class.
	 */
	public static Class<? extends ReadingParser> getPackageParsers(
			String namespace) {
		return SBMLReader.packageParsers.get(namespace);
	}

	/**
	 * Returns true if there is no 'required' attribute for this namespace
	 *         URI, false otherwise.
	 * 
	 * @param namespaceURI
	 * @param startElement
	 *            : the StartElement instance
	 * @return true if there is no 'required' attribute with this namespace
	 *         URI.
	 */
	private static boolean hasNoRequiredAttributeFor(String namespaceURI,
			StartElement startElement) 
	{
		@SuppressWarnings("unchecked")
		Iterator<Attribute> att = startElement.getAttributes();

		while (att.hasNext()) {
			Attribute attribute = (Attribute) att.next();

			if (attribute.getName().getNamespaceURI().equals(namespaceURI)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Initializes the packageParser {@link HashMap} of this class.
	 * 
	 */
	public static void initializePackageParserNamespaces() {
		JSBML.loadClasses("org/sbml/jsbml/resources/cfg/PackageParserNamespaces.xml", packageParsers);
	}

	/**
	 * Returns true if there is no 'required' attribute for this namespace
	 *         URI, false otherwise.
	 * 
	 * @param namespaceURI
	 * @param startElement
	 *            : the StartElement instance representing the SBMLDocument element.
	 * @return true if the package represented by the namespace URI is required
	 *         to read the SBML file. If there is no 'required' attribute for
	 *         this namespace URI, return false.
	 */
	// TODO : Duplicate method with hasNoRequiredAttributeFor ???
	private static boolean isPackageRequired(String namespaceURI,
			StartElement startElement) 
	{
		@SuppressWarnings("unchecked")
		Iterator<Attribute> att = startElement.getAttributes();

		while (att.hasNext()) {
			Attribute attribute = (Attribute) att.next();

			if (attribute.getName().getNamespaceURI().equals(namespaceURI)) {
				
				// TODO : we have to check that the attribute name is really required !!!! :-)
				
				if (attribute.getValue().toLowerCase().equals("true")) {
					return true;
				}
				return false;
			}
		}
		return false; // By default, a package is not required?
	}

	/**
	 * Reads the file that is passed as argument and write it to the console, 
	 * using the method {@link SBMLWriter.write}.
	 * 
	 * @param args the command line arguments, we are taking the first one as 
	 * the file name to read.
	 * 
	 * @throws IOException if the file name is not valid.
	 * @throws SBMLException if there are any problems reading or writing the SBML model.
	 * @throws XMLStreamException if there are any problems reading or writing the XML file.
	 */
	public static void main(String[] args) throws IOException, XMLStreamException, SBMLException  {

		if (args.length < 1) {
			System.out
					.println("Usage: java org.sbml.jsbml.xml.stax.SBMLReader sbmlFileName");
			System.exit(0);
		}

		String fileName = args[0];

		SBMLDocument testDocument = readSBML(fileName);
		SBMLWriter.write(testDocument, System.out);
	}

	/**
	 * Reads a SBML String from the given file.
	 * 
	 * @param file
	 *            A file containing SBML content.
	 * @return the matching SBMLDocument instance.
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 */
	public static SBMLDocument readSBML(File file) throws XMLStreamException,
			FileNotFoundException {
		return readSBMLFromStream(new FileInputStream(file));
	}

	/**
	 * Reads SBML from a given {@link File}.
	 * 
	 * @param file
	 *            The path to an SBML {@link File}.
	 * @return the matching SBMLDocument instance.
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 */
	public static SBMLDocument readSBML(String file) throws XMLStreamException,
			FileNotFoundException {
		return readSBMLFile(file);
	}

	/**
	 * Reads the SBML file 'fileName' and creates/initialises a SBMLDocument
	 * instance.
	 * 
	 * @param fileName
	 *            : name of the SBML file to read.
	 * @return the initialised SBMLDocument.
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 */
	public static SBMLDocument readSBMLFile(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return readSBML(new File(fileName));
	}

	/**
	 * Reads a SBML document from the given <code>stream</code>.
	 * 
	 * @param stream
	 * @return
	 * @throws XMLStreamException
	 */
	public static SBMLDocument readSBMLFromStream(InputStream stream)
			throws XMLStreamException 
	{
		WstxInputFactory inputFactory = new WstxInputFactory();
		HashMap<String, ReadingParser> initializedParsers = null;

		XMLEventReader xmlEventReader = inputFactory
				.createXMLEventReader(stream);
		XMLEvent event;
		StartElement startElement = null;
		ReadingParser parser = null;
		Stack<Object> sbmlElements = new Stack<Object>();
		QName currentNode = null;
		Boolean isNested = false;
		Boolean isText = false;
		Boolean isHTML = false;
		Integer level = -1, version = -1;

		Logger logger = Logger.getLogger(SBMLReader.class);	
		
		// Read all the elements of the file
		while (xmlEventReader.hasNext()) {
			event = (XMLEvent2) xmlEventReader.nextEvent();

			// StartDocument
			if (event.isStartDocument()) {
				@SuppressWarnings("unused")
				StartDocument startDocument = (StartDocument) event;
				// nothing to do
			}
			// EndDocument
			else if (event.isEndDocument()) {
				@SuppressWarnings("unused")
				EndDocument endDocument = (EndDocument) event;
				// nothing to do?
			}
			// StartElement
			else if (event.isStartElement()) {
				
				startElement = event.asStartElement();
				currentNode = startElement.getName();
				isNested = false;
				isText = false;
				
				// If the XML element is the sbml element, creates the
				// necessary ReadingParser instances.
				// Creates an empty SBMLDocument instance and pushes it on
				// the SBMLElements stack.
				if (currentNode.getLocalPart().equals("sbml")) {
					initializedParsers = getInitializedPackageParsers(startElement);
					SBMLDocument sbmlDocument = new SBMLDocument();

					// the output of the change listener is activated or not via log4j.properties
					sbmlDocument.addChangeListener(new SimpleSBaseChangeListener());

					for (@SuppressWarnings("unchecked")
							Iterator<Attribute> iterator = startElement.getAttributes(); iterator.hasNext();) 
					{
						Attribute attr = iterator.next();
						if (attr.getName().toString().equals("level")) {
							level = StringTools.parseSBMLInt(attr.getValue());
							sbmlDocument.setLevel(level);
						} else if (attr.getName().toString().equals("version")) {
							version = StringTools.parseSBMLInt(attr.getValue());
							sbmlDocument.setVersion(version);
						}
					}
					sbmlElements.push(sbmlDocument);
				} 

				parser = processStartElement(startElement, currentNode, isHTML,
						initializedParsers, sbmlElements);

			}
			// Characters
			else if (event.isCharacters()) {
				Characters characters = event.asCharacters();

				if (!characters.isWhiteSpace()) {
					isText = true; // the characters are not only 'white spaces'
				}
				if (sbmlElements.peek() instanceof XMLNode) {
					isText = true; // We want to keep the whitespace/formatting when reading html block
				}

				// process the text of a XML element.
				if ((parser != null) && !sbmlElements.isEmpty()	&& isText) {
					if (currentNode != null) {
						parser.processCharactersOf(currentNode.getLocalPart(),
								characters.getData(), sbmlElements.peek());
					} else {
						parser.processCharactersOf(null, characters.getData(),
								sbmlElements.peek());
					}
				} else if (isText) {
					logger.warn("Some characters cannot be read : " + characters.getData());
					if (logger.isDebugEnabled()) {
						logger.debug("Parser = " + parser);
						if (sbmlElements.isEmpty()) {
							logger.debug("The Object Stack is empty !!!");
						} else {
							logger.debug("The current Object in the stack is : " + sbmlElements.peek());
						}
					}

					
				}
			}
			// EndElement
			else if (event.isEndElement()) {
				SBMLDocument sbmlDocument = processEndElement(startElement, currentNode, isNested, isText, isHTML, event,
						level, version, parser,	initializedParsers, sbmlElements);
				
				if (sbmlDocument != null) {
					return sbmlDocument;
				}
				
				currentNode = null;
				isNested = false;
				isText = false;
			}
		}
		
		return null;
	}

	/**
	 * Reads a SBML model from the given XML String.
	 * 
	 * @param xml
	 * @return
	 */
	public static SBMLDocument readSBMLFromString(String xml)
	throws XMLStreamException {
		return readSBMLFromStream(new ByteArrayInputStream(xml.getBytes()));
	}


	/**
	 * Process a {@link StartElement} event.
	 * 
	 * @param startElement
	 * @param currentNode
	 * @param isHTML
	 * @param initializedParsers
	 * @param sbmlElements
	 * @return
	 */
	private static ReadingParser processStartElement(StartElement startElement, QName currentNode, 
			Boolean isHTML, HashMap<String, ReadingParser> initializedParsers, Stack<Object> sbmlElements) 
	{		
		Logger logger = Logger.getLogger(SBMLReader.class);		
		ReadingParser parser = null;

		String elementNamespace = currentNode.getNamespaceURI();
		
		// To be able to parse all the SBML file, the sbml node
		// should have been read first.
		if (!sbmlElements.isEmpty() && (initializedParsers != null)) {

			// All the element should have a namespace.
			if (elementNamespace != null) {
				addNamespaceToInitializedPackages(currentNode.getLocalPart(), 
						startElement, initializedParsers);
				
				parser = initializedParsers.get(elementNamespace);
				// if the current node is a notes or message element
				// and the matching ReadingParser is a StringParser,
				// we need to set the typeOfNotes variable of the
				// StringParser instance.
				if (currentNode.getLocalPart().equals("notes")
						|| currentNode.getLocalPart().equals("message")) {
					ReadingParser sbmlparser = initializedParsers
						.get("http://www.w3.org/1999/xhtml");

					if (sbmlparser instanceof StringParser) {
						StringParser notesParser = (StringParser) sbmlparser;
						notesParser.setTypeOfNotes(currentNode
								.getLocalPart());
					}
				}

				if (parser != null) {

					@SuppressWarnings("unchecked")
					Iterator<Namespace> nam = startElement.getNamespaces();
					@SuppressWarnings("unchecked")
					Iterator<Attribute> att = startElement.getAttributes();
					boolean hasAttributes = att.hasNext();
					boolean hasNamespace = nam.hasNext();

					// All the subNodes of SBML are processed.
					if (!currentNode.getLocalPart().equals("sbml")) {
						Object processedElement = parser.processStartElement(currentNode
								.getLocalPart(), currentNode
								.getPrefix(), hasAttributes,
								hasNamespace, sbmlElements
								.peek());
						if (processedElement != null) {
							sbmlElements.push(processedElement);
						} else {
							// It is normal to have sometimes null returned as some of the 
							// XML elements are ignore or do not produce a new java object (like 'apply' in mathML).
						}
					}
					
					// process the namespaces
					processNamespaces(nam, currentNode, initializedParsers, sbmlElements, hasAttributes);
					
					// Process the attributes
					processAttributes(att, currentNode, initializedParsers, sbmlElements, parser, hasAttributes);

				} else {
					logger.warn("Cannot find a parser for the " + elementNamespace + " namespace");				}
			} else {
				logger.warn("Cannot find a parser for the " + elementNamespace + " namespace");			}
		}
		
		return parser;
	}

	
	/**
	 * Process Namespaces of the current element on the stack.
	 * 
	 * @param nam
	 * @param currentNode
	 * @param initializedParsers
	 * @param sbmlElements
	 * @param hasAttributes
	 */
	private static void processNamespaces(Iterator<Namespace> nam, QName currentNode, 
			HashMap<String, ReadingParser> initializedParsers, Stack<Object> sbmlElements,
			boolean hasAttributes) 
	{
		Logger logger = Logger.getLogger(SBMLReader.class);
		ReadingParser namespaceParser = null;

		while (nam.hasNext()) {
			Namespace namespace = (Namespace) nam.next();
			boolean isLastNamespace = !nam.hasNext();
			namespaceParser = initializedParsers.get(namespace.getNamespaceURI());
			
			if (namespaceParser != null) {
				namespaceParser.processNamespace(
						currentNode.getLocalPart(),
						namespace.getNamespaceURI(),
						namespace.getName().getPrefix(),
						namespace.getName().getLocalPart(),
						hasAttributes, isLastNamespace,
						sbmlElements.peek());
			} else {
				logger.warn("Cannot find a parser for the " + namespace.getNamespaceURI() + " namespace");
			}
		}

	}
	
	/**
	 * Process Attributes of the current element on the stack.
	 * 
	 * @param att
	 * @param currentNode
	 * @param initializedParsers
	 * @param sbmlElements
	 * @param parser
	 * @param hasAttributes
	 */
	private static void processAttributes(Iterator<Attribute> att, QName currentNode, 
			HashMap<String, ReadingParser> initializedParsers, Stack<Object> sbmlElements,
			ReadingParser parser, boolean hasAttributes) 
	{
		Logger logger = Logger.getLogger(SBMLReader.class);
		ReadingParser attributeParser = null;

		while (att.hasNext()) {

			Attribute attribute = (Attribute) att.next();
			boolean isLastAttribute = !att.hasNext();
			QName attributeName = attribute.getName();

			if (attribute.getName().getNamespaceURI().length() > 0) {
				attributeParser = initializedParsers.get(attribute.getName()
						.getNamespaceURI());
			} else {
				attributeParser = parser;
			}

			if (attributeParser != null) {
				attributeParser.processAttribute(
						currentNode.getLocalPart(),
						attributeName.getLocalPart(),
						attribute.getValue(), attributeName
						.getPrefix(),
						isLastAttribute, sbmlElements
						.peek());
			} else {
				logger.warn("Cannot find a parser for the " + attribute.getName().getNamespaceURI() + " namespace");
			}
		}
	}
	
	
	/**
	 * Process the end of an element.
	 * 
	 * @param element
	 * @param currentNode
	 * @param isNested
	 * @param isText
	 * @param isHTML
	 * @param event
	 * @param level
	 * @param version
	 * @param parser
	 * @param initializedParsers
	 * @param sbmlElements
	 * @return
	 */
	private static SBMLDocument processEndElement(StartElement element, QName currentNode, Boolean isNested, Boolean isText, 
			Boolean isHTML, XMLEvent event,	int level, int version, ReadingParser parser, 			
			HashMap<String, ReadingParser> initializedParsers, Stack<Object> sbmlElements) 
	{
		
		EndElement endElement = event.asEndElement();

		// TODO : create a log system to avoid having to comment/uncomment sysout to debug
//		System.out.println("SBMLReader : event.isEndElement : stack.size = " + SBMLElements.size());
//		System.out.println("SBMLReader : event.isEndElement : element name = " + endElement.getName().getLocalPart());
//		if (endElement.getName().getLocalPart().equals("kineticLaw") || endElement.getName().getLocalPart().startsWith("listOf")) {
//			System.out.println("SBMLReader : event.isEndElement : stack = " + SBMLElements);
//		}
		// TODO : check that the stack did not increase before and after an element ?
		
		// If this element contains no text and doesn't have any
		// subNodes, this element is nested.
		if (!isText && currentNode != null) {
			if (currentNode.getLocalPart().equals(
					endElement.getName().getLocalPart())) {
				isNested = true;
			}
		}

		if (initializedParsers != null) {
			parser = initializedParsers.get(endElement.getName()
					.getNamespaceURI());

			// if the current node is a notes or message element and
			// the matching ReadingParser is a StringParser, we need
			// to set the typeOfNotes variable of the
			// StringParser instance.
			if (endElement.getName().getLocalPart().equals("notes")
					|| endElement.getName().getLocalPart().equals(
							"message")) {
				ReadingParser sbmlparser = initializedParsers
						.get("http://www.w3.org/1999/xhtml");
				if (sbmlparser instanceof StringParser) {
					StringParser notesParser = (StringParser) sbmlparser;
					notesParser.setTypeOfNotes(endElement.getName()
							.getLocalPart());
				}
			}
			// process the end of the element.
			if (!sbmlElements.isEmpty() && parser != null) {
				// System.out.println("SBMLReader : event.isEndElement : calling end element");

				boolean popElementFromTheStack = parser
						.processEndElement(endElement.getName()
								.getLocalPart(), endElement.getName()
								.getPrefix(), isNested, sbmlElements
								.peek());
				// remove the top of the SBMLElements stack at the
				// end of an element if this element is not the sbml
				// element.
				if (!endElement.getName().getLocalPart().equals("sbml")) {
					if (popElementFromTheStack) {
						sbmlElements.pop();
					}

					// System.out.println("SBMLReader : event.isEndElement : new stack.size = "
					// + SBMLElements.size());

				} else {
					// process the end of the document and return
					// the final SBMLDocument
					if (sbmlElements.peek() instanceof SBMLDocument) {
						SBMLDocument sbmlDocument = (SBMLDocument) sbmlElements
								.peek();
						Iterator<Entry<String, ReadingParser>> iterator = initializedParsers
								.entrySet().iterator();

						while (iterator.hasNext()) {
							Entry<String, ReadingParser> entry = iterator
									.next();

							ReadingParser sbmlParser = entry.getValue();
							sbmlParser.processEndDocument(sbmlDocument);
						}
						
						return (SBMLDocument) sbmlElements.peek();
						
					} else {
						// TODO at the end of a sbml node, the
						// SBMLElements stack must contain only a
						// SBMLDocument instance.
						// Otherwise, there is a syntax error in the
						// SBML document, Throw an error?
					}
				}
			} else {
				// TODO if SBMLElements.isEmpty => there is a syntax
				// error in the SBMLDocument, throw an error?
				// TODO if parser == null => there is no parser for
				// the namespace of this element, throw an error?
			}
		} else {
			// TODO The initialized parsers map should be
			// initialized as soon as there is a sbml node.
			// if it is null, there is an syntax error in the SBML
			// file. Throw an error?
		}
		
		// We return null as long as we did not find the SBMLDocument closing tag
		return null;
	}
	
	
}
