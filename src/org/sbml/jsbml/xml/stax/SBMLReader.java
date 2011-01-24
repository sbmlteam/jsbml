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
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.SimpleSBaseChangeListener;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.parsers.AnnotationParser;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;
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
	private HashMap<String, Class<? extends ReadingParser>> packageParsers = new HashMap<String, Class<? extends ReadingParser>>();

	
	/**
	 * Contains all the initialized parsers.
	 */
	private HashMap<String, ReadingParser> initializedParsers = new HashMap<String, ReadingParser>();


	
	/**
	 * Creates the ReadingParser instances and stores them in a
	 * HashMap.
	 * 
	 * @return the map containing the ReadingParser instances.
	 */
	private HashMap<String, ReadingParser> initializePackageParsers() 
	{
		if (packageParsers.size() == 0) {
			initializePackageParserNamespaces();
		}
		
		for (String namespace : packageParsers.keySet()) {
			try {
				initializedParsers.put(namespace, packageParsers.get(namespace).newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return initializedParsers;
	}
	
	/**
	 * Associates any unknown namespaces with the {@link AnnotationParser}.
	 * 
	 */
	private void addAnnotationParsers(StartElement startElement) 
	{
		@SuppressWarnings("unchecked")
		Iterator<Namespace> namespacesIterator = startElement.getNamespaces();
		
		while (namespacesIterator.hasNext()) {
			String namespaceURI = namespacesIterator.next().getNamespaceURI();
			
			if (initializedParsers.get(namespaceURI) == null) {
				initializedParsers.put(namespaceURI, new AnnotationParser());
			}
		}
	}
	

	/**
	 * Gets the ReadingParser class associated with 'namespace'.
	 * 
	 * @param namespace
	 * @return the ReadingParser class associated with 'namespace'. Null if
	 *         there is not matching ReadingParser class.
	 */
	public Class<? extends ReadingParser> getReadingParsers(String namespace) {
		return packageParsers.get(namespace);
	}


	/**
	 * Initializes the packageParser {@link HashMap} of this class.
	 * 
	 */
	public void initializePackageParserNamespaces() {
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
	private boolean isPackageRequired(String namespaceURI,
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

		/*
		if (args.length < 1) {
			System.out
					.println("Usage: java org.sbml.jsbml.xml.stax.SBMLReader sbmlFileName");
			System.exit(0);
		}

		String fileName = args[0];

		SBMLDocument testDocument = readSBML(fileName);
		SBMLWriter.write(testDocument, System.out);
		
		*/
		String mathMLString1 = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n"
			+ "  <apply>\n"
            + "    <times/>\n"
            + "    <ci> uVol </ci>\n"
            + "    <ci> MKP3 </ci>\n"
            + "  </apply>\n"
            + "</math>\n";
		
		String mathMLString2 = "<math:math xmlns:math=\"http://www.w3.org/1998/Math/MathML\">\n"
			+ "  <math:apply>\n"
            + "    <math:times/>\n"
            + "    <math:ci> uVol </math:ci>\n"
            + "    <math:ci> MKP3 </math:ci>\n"
            + "  </math:apply>\n"
            + "</math:math>\n";
		
		String notesHTMLString = "<notes>\n" +
			"  <body xmlns=\"http://www.w3.org/1999/xhtml\">\n " +
			"    <p>The model describes the double phosphorylation of MAP kinase by an ordered mechanism using the Michaelis-Menten formalism. " +
			"Two enzymes successively phosphorylate the MAP kinase, but one phosphatase dephosphorylates both sites.</p>\n" +
			"  </body>\n" +
			"</notes>";
		
		SBMLReader reader = new SBMLReader();
		
		Object astNodeObject1 = reader.readXMLFromString(mathMLString1);
		Object astNodeObject2 = reader.readXMLFromString(mathMLString2);
		Object xmlNodeObject = reader.readXMLFromString(notesHTMLString);
		
		System.out.println("MathML object = " + astNodeObject1);
		System.out.println("MathML object = " + ((AssignmentRule) astNodeObject2).getMath());
		System.out.println("Notes object = " + ((SBase) xmlNodeObject).getNotes().toXMLString());
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
	public SBMLDocument readSBML(File file) throws XMLStreamException,
			FileNotFoundException 
	{
		Object readObject = readXMLFromStream(new FileInputStream(file));
		
		if (readObject instanceof SBMLDocument) {
			return (SBMLDocument) readObject;
		}
		
		throw new XMLStreamException("Your did not gave a correct SBMl file !");
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
	public SBMLDocument readSBML(String file) throws XMLStreamException,
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
	public SBMLDocument readSBMLFile(String fileName)
			throws XMLStreamException, FileNotFoundException {
		return readSBML(new File(fileName));
	}

	
	/**
	 * Reads an {@link SBMLDocument} from the given {@link XMLEventReader}
	 * 
	 * @param xmlEventReader
	 * @return
	 * @throws XMLStreamException
	 */
	public SBMLDocument readSBML(XMLEventReader xmlEventReader)
		throws XMLStreamException 
	{
		return (SBMLDocument) readXMLFromXMLEventReader(xmlEventReader);		
	}
	
	/**
	 * Reads a mathML String into an {@link ASTNode}.
	 * 
	 * @param mathML
	 * @return an {@link ASTNode} representing the given mathML String.
	 * @throws XMLStreamException
	 */
	public ASTNode readMathML(String mathML)
		throws XMLStreamException 
	{
		Object object = readXMLFromString(mathML);
		
		if (object != null && object instanceof Rule) {
			ASTNode math = ((Rule) object).getMath();
			
			if (math != null) {
				return math;
			}
		}
		
		return null;
	}

	/**
	 * Reads a notes XML String into an {@link XMLNode}.
	 * 
	 * @param notesXHTML
	 * @return an {@link XMLNode} representing the given notes String.
	 * @throws XMLStreamException
	 */
	public XMLNode readNotes(String notesXHTML)
		throws XMLStreamException 
	{
		Object object = readXMLFromString(notesXHTML);

		if (object != null && object instanceof Rule) {
			XMLNode notes = ((Rule) object).getNotes();

			if (notes != null) {
				return notes;
			}
		}

		return null;
	}

	/**
	 * Reads a SBML document from the given <code>stream</code>. 
	 * 
	 * @param stream
	 * @return
	 * @throws XMLStreamException
	 */
	public SBMLDocument readSBMLFromStream(InputStream stream)
			throws XMLStreamException 
	{
		WstxInputFactory inputFactory = new WstxInputFactory();

		XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(stream);
		
		return (SBMLDocument) readXMLFromXMLEventReader(xmlEventReader);		
	}

	/**
	 * Reads a XML document from the given <code>stream</code>. It need to be a self contain part of
	 * an SBML document. 
	 * 
	 * @param stream
	 * @return
	 * @throws XMLStreamException
	 */
	private Object readXMLFromStream(InputStream stream)
			throws XMLStreamException 
	{
		WstxInputFactory inputFactory = new WstxInputFactory();

		XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(stream);
		
		return readXMLFromXMLEventReader(xmlEventReader);		
	}
	
		
	/**
	 * Reads an XML document from the given {@link XMLEventReader}. It need to represent a self contain part of
	 * an SBML document. It can be either a math element, a notes element or the whole SBML model. If math or notes are given, 
	 * a Rule containing the math or notes will be returned, otherwise an SBMLDocument is returned.
	 * 
	 * 
	 * @param xmlEventReader
	 * @return an <code>Object</code> representing the given XML.
	 * @throws XMLStreamException
	 */
	private Object readXMLFromXMLEventReader(XMLEventReader xmlEventReader)  throws XMLStreamException {

		initializePackageParsers();

		XMLEvent event;
		StartElement startElement = null;
		ReadingParser parser = null;
		Stack<Object> sbmlElements = new Stack<Object>();
		QName currentNode = null;
		Boolean isNested = false;
		Boolean isText = false;
		Boolean isHTML = false;
		Integer level = -1, version = -1;
		Object lastElement = null;
		
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
				
				addAnnotationParsers(startElement);
				
				// If the XML element is the sbml element, creates the
				// necessary ReadingParser instances.
				// Creates an empty SBMLDocument instance and pushes it on
				// the SBMLElements stack.
				if (currentNode.getLocalPart().equals("sbml")) {

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
				} else if (lastElement == null) {
					// We put a fake element that can take either math or notes

					if (currentNode.getLocalPart().equals("notes")) {
						
						initializedParsers.put("", new SBMLCoreParser());
						
					} else if (currentNode.getLocalPart().equals("math")) {
						
						initializedParsers.put("", new MathMLStaxParser());
						initializedParsers.put(JSBML.URI_MATHML_DEFINITION, new MathMLStaxParser());
						currentNode = new QName(JSBML.URI_MATHML_DEFINITION, "math");
						
					}
					
					// TODO : will not work with arbitrary SBML part
					// TODO : we need to be able, somehow, to set the Model element in the AssignmentRule
					// to be able to have a fully functional parsing. Without it the functionDefinition, for examples, are
					// not properly recognized.
					AssignmentRule assignmentRule = new AssignmentRule();
					sbmlElements.push(assignmentRule);
					
				}

				parser = processStartElement(startElement, currentNode, isHTML,	sbmlElements);
				lastElement = sbmlElements.peek();

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
						
						// logger.debug("isCharacter : elementName = " + currentNode.getLocalPart());
						
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

				// the method  processEndElement will return null until we arrive at the end of the 'sbml' element.
				lastElement = sbmlElements.peek();
				
				SBMLDocument sbmlDocument = processEndElement(startElement, currentNode, isNested, isText, isHTML, event,
						level, version, parser,	sbmlElements);
				
				if (sbmlDocument != null) {
					return sbmlDocument;
				}
				
				currentNode = null;
				isNested = false;
				isText = false;
			} 
		}
		
		// We reach the end of the XML fragment and no 'sbml' have been found
		// so we are probably parsing some math or notes String.
		
		logger.debug("no more XMLEvent : stack.size = " + sbmlElements.size());
		
		logger.debug("no more XMLEvent : stack = " + sbmlElements);
		
		initializedParsers.remove("");
		
		if (sbmlElements.size() > 0) {
			return sbmlElements.peek();
		}
		
		return null;
	}

	/**
	 * Reads a SBML model from the given XML String.
	 * 
	 * @param xml
	 * @return
	 */
	public SBMLDocument readSBMLFromString(String xml)
		throws XMLStreamException 
	{
		Object readObject = readXMLFromStream(new ByteArrayInputStream(xml.getBytes()));
		
		if (readObject instanceof SBMLDocument) {
			return (SBMLDocument) readObject;
		}
		
		throw new XMLStreamException("Your did not gave a correct SBMl file !");
	}

	/**
	 * Reads an XML String that should the part of a SBML model.
	 * 
	 * @param xml
	 * @return
	 */
	private Object readXMLFromString(String xml)
		throws XMLStreamException 
	{
		return readXMLFromStream(new ByteArrayInputStream(xml.getBytes()));
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
	private ReadingParser processStartElement(StartElement startElement, QName currentNode, 
			Boolean isHTML, Stack<Object> sbmlElements) 
	{		
		Logger logger = Logger.getLogger(SBMLReader.class);		
		ReadingParser parser = null;

		String elementNamespace = currentNode.getNamespaceURI();

		logger.debug("processStartElement : " + currentNode.getLocalPart() + ", " + elementNamespace);
		
		// To be able to parse all the SBML file, the sbml node
		// should have been read first.
		if (!sbmlElements.isEmpty() && (initializedParsers != null)) {

			// All the element should have a namespace.
			if (elementNamespace != null) {
				
				parser = initializedParsers.get(elementNamespace);
				// if the current node is a notes or message element
				// and the matching ReadingParser is a StringParser,
				// we need to set the typeOfNotes variable of the
				// StringParser instance.
				if (currentNode.getLocalPart().equals("notes")
						|| currentNode.getLocalPart().equals("message")) 
				{
					ReadingParser sbmlparser = initializedParsers.get(JSBML.URI_XHTML_DEFINITION);

					if (sbmlparser instanceof StringParser) {
						StringParser notesParser = (StringParser) sbmlparser;
						notesParser.setTypeOfNotes(currentNode.getLocalPart());
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
					processNamespaces(nam, currentNode,sbmlElements, hasAttributes);
					
					// Process the attributes
					processAttributes(att, currentNode, sbmlElements, parser, hasAttributes);

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
	private void processNamespaces(Iterator<Namespace> nam, QName currentNode, 
			Stack<Object> sbmlElements,	boolean hasAttributes) 
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
	private void processAttributes(Iterator<Attribute> att, QName currentNode, 
			Stack<Object> sbmlElements, ReadingParser parser, boolean hasAttributes) 
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
						attribute.getValue(), attributeName.getPrefix(),
						isLastAttribute, sbmlElements.peek());
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
	private SBMLDocument processEndElement(StartElement element, QName currentNode, Boolean isNested, Boolean isText, 
			Boolean isHTML, XMLEvent event,	int level, int version, ReadingParser parser, 			
			Stack<Object> sbmlElements) 
	{
		Logger logger = Logger.getLogger(SBMLReader.class);
		
		EndElement endElement = event.asEndElement();

		logger.debug("event.isEndElement : stack.size = " + sbmlElements.size());
		logger.debug("event.isEndElement : element name = " + endElement.getName().getLocalPart());
		if (endElement.getName().getLocalPart().equals("kineticLaw") || endElement.getName().getLocalPart().startsWith("listOf")
				|| endElement.getName().getLocalPart().equals("math")) 
		{
			logger.debug("event.isEndElement : stack = " + sbmlElements);
		}
		// TODO : check that the stack did not increase before and after an element ?
		
		// If this element contains no text and doesn't have any
		// subNodes, this element is nested.
		if (!isText && currentNode != null) {
			if (currentNode.getLocalPart().equals(
					endElement.getName().getLocalPart())) 
			{
				isNested = true;
			}
		}

		if (initializedParsers != null) {
			parser = initializedParsers.get(endElement.getName().getNamespaceURI());

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
				logger.debug("event.isEndElement : calling parser.processEndElement ");

				boolean popElementFromTheStack = parser
						.processEndElement(endElement.getName().getLocalPart(),
								endElement.getName().getPrefix(), isNested,
								sbmlElements.peek());
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
					
					logger.debug("event.isEndElement : sbml element found");
					
					// process the end of the document and return
					// the final SBMLDocument
					if (sbmlElements.peek() instanceof SBMLDocument) {
						SBMLDocument sbmlDocument = (SBMLDocument) sbmlElements
								.peek();
						
						Iterator<Entry<String, ReadingParser>> iterator = initializedParsers
								.entrySet().iterator();

						while (iterator.hasNext()) {
							Entry<String, ReadingParser> entry = iterator.next();

							ReadingParser sbmlParser = entry.getValue();
							// TODO : calling endDocument for all parsers, can it be a problem ?
							// sbmlParser.processEndDocument(sbmlDocument);
							
							// TODO : call endDocument only on the parser associated with the namespaces 
							// declared on the sbml element.
							// logger.debug("event.isEndElement : parser = " + sbmlParser.getClass());
						}
						
						return sbmlDocument;
						
					} else {
						// TODO at the end of a sbml node, the
						// SBMLElements stack must contain only a
						// SBMLDocument instance.
						// Otherwise, there is a syntax error in the
						// SBML document, Throw an error?
						logger.warn("!!! event.isEndElement : there is a problem in your SBML file !!!!");
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
