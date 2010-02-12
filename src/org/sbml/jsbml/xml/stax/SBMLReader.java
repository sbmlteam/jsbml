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

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;

import org.codehaus.stax2.XMLEventReader2;
import org.codehaus.stax2.evt.XMLEvent2;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.sbmlExtensions.groups.GroupsParser;
import org.sbml.jsbml.sbmlExtensions.layout.LayoutParser;
import org.sbml.jsbml.xml.sbmlParsers.AnnotationParser;
import org.sbml.jsbml.xml.sbmlParsers.BiologicalQualifierParser;
import org.sbml.jsbml.xml.sbmlParsers.CreatorParser;
import org.sbml.jsbml.xml.sbmlParsers.DatesParser;
import org.sbml.jsbml.xml.sbmlParsers.ModelQualifierParser;
import org.sbml.jsbml.xml.sbmlParsers.MultiParser;
import org.sbml.jsbml.xml.sbmlParsers.RDFAnnotationParser;
import org.sbml.jsbml.xml.sbmlParsers.SBMLCoreParser;
import org.sbml.jsbml.xml.sbmlParsers.StringParser;
import org.sbml.jsbml.xml.sbmlParsers.VCardParser;

import com.ctc.wstx.stax.WstxInputFactory;

/**
 * A SBMLReader provides all the methods to read a SBML file.
 * @author marine
 *
 */
public class SBMLReader {
	
	/**
	 * contains all the relationships namespace URI <=> ReadingParser class.
	 */
	private static HashMap <String, Class<? extends ReadingParser>> packageParsers = new HashMap<String, Class<? extends ReadingParser>>();
	
	/**
	 * 
	 * @param namespace
	 * @return the ReadingParser class associated with 'namespace'. Null if there is not matching ReadingParser class.
	 */
	public static Class<? extends ReadingParser> getPackageParsers(String namespace){
		return SBMLReader.packageParsers.get(namespace);
	}


	/**
	 * Initialises the packageParser HasMap of this class.
	 */
	public static void initializePackageParserNamespaces(){
		//TODO Load the map from a configuration file
		packageParsers.put("http://www.sbml.org/sbml/level3/version1/multi/version1", MultiParser.class);
		packageParsers.put("http://www.sbml.org/sbml/level3/version1/groups/version1", GroupsParser.class);
		packageParsers.put("http://www.sbml.org/sbml/level3/version1/layout/version1", LayoutParser.class);
		packageParsers.put("http://www.sbml.org/sbml/level3/version1/core", SBMLCoreParser.class);
		packageParsers.put("http://www.sbml.org/sbml/level2", SBMLCoreParser.class);
		packageParsers.put("http://www.w3.org/1999/xhtml", StringParser.class);
		packageParsers.put("http://www.w3.org/1999/02/22-rdf-syntax-ns#", RDFAnnotationParser.class);
		packageParsers.put("http://purl.org/dc/elements/1.1/", CreatorParser.class);
		packageParsers.put("http://purl.org/dc/terms/", DatesParser.class);
		packageParsers.put("http://www.w3.org/2001/vcard-rdf/3.0#", VCardParser.class);
		packageParsers.put("http://biomodels.net/biology-qualifiers/", BiologicalQualifierParser.class);
		packageParsers.put("http://biomodels.net/model-qualifiers/", ModelQualifierParser.class);
		packageParsers.put("http://www.w3.org/1998/Math/MathML", StringParser.class);
	}
	
	/**
	 * 
	 * @param namespaceURI
	 * @param sbml : the StartElement instance
	 * @return true if the package represented by the namespace URI is required to read the SBML file.
	 * If there is no 'required' attribute for this namespace URI, return false.
	 */
	@SuppressWarnings("unchecked")
	private static boolean isPackageRequired(String namespaceURI, StartElement sbml){
		Iterator att = sbml.getAttributes();
		
		while(att.hasNext()){
			Attribute attribute = (Attribute) att.next();

			if (attribute.getName().getNamespaceURI().equals(namespaceURI)){
				if (attribute.getValue().toLowerCase().equals("true")){
					return true;
				}
				return false;
			}
		}
		return false; // By default, a package is not required?
	}
	
	/**
	 * 
	 * @param namespaceURI
	 * @param sbml : the StartElement instance
	 * @return true if there is no 'required' attribute whith this namespace URI.
	 */
	@SuppressWarnings("unchecked")
	private static boolean hasNoRequiredAttributeFor(String namespaceURI, StartElement sbml){
		Iterator att = sbml.getAttributes();
		
		while(att.hasNext()){
			Attribute attribute = (Attribute) att.next();

			if (attribute.getName().getNamespaceURI().equals(namespaceURI)){
				return false;
			}
		}
		return true; 
	}
	
	/**
	 * Creates the necessary ReadingParser instances and stores them in a HashMap.
	 * @param sbml : the StartElement instance
	 * @return the map containing the ReadingParser instances for this StartElement.
	 */
	@SuppressWarnings("unchecked")
	private static HashMap<String, ReadingParser> getInitializedPackageParsers(StartElement sbml){
		initializePackageParserNamespaces();
		
		HashMap<String, ReadingParser> initializedParsers = new HashMap<String, ReadingParser>();
		
		Iterator<Namespace> nam = sbml.getNamespaces();
		while(nam.hasNext()){
			Namespace namespace = nam.next();
			String namespaceURI = namespace.getNamespaceURI();

			// If the prefix is an empty String, it means that the namespace is the namespace of the current element (which is the sbml element normally).
			if (namespace.getPrefix().length() == 0){
				packageParsers.put(namespaceURI, SBMLCoreParser.class);
				initializedParsers.put(namespaceURI, new SBMLCoreParser());
			}
			// If there is a ReadingParser associated with this namespace URI.
			else if (packageParsers.containsKey(namespaceURI)){
				try {
					initializedParsers.put(namespaceURI, packageParsers.get(namespaceURI).newInstance());
				} catch (InstantiationException e) {
					if (isPackageRequired(namespaceURI, sbml)){
						// TODO throw an Exception : package required for the parsing?
					}
				} catch (IllegalAccessException e) {
					if (isPackageRequired(namespaceURI, sbml)){
						// TODO throw an Exception : package required for the parsing?
					}
				}
			}
			// If there is no ReadingParser associated with this namespaceURI and there is no 'required' attribute with this namespace, the declared namespace
			// can be for the annotation and it associates this namespace URI to an AnnotationParser instance.
			else {
				if (hasNoRequiredAttributeFor(namespaceURI, sbml)){
					packageParsers.put(namespaceURI, AnnotationParser.class);
					initializedParsers.put(namespaceURI, new AnnotationParser());
				}
				else {
					if (isPackageRequired(namespaceURI, sbml)){
						// TODO throw an Exception : package required for the parsing?
					}
				}
			}
		}
		return initializedParsers;
	}
	
	/**
	 * Adds a new ReadingParser instance to the initializedParsers if it doesn't contain a ReadingParser instance for this namespace URI.
	 * @param elementName : localName of the XML element
	 * @param element : StartElement instance
	 * @param initializedParsers : the map containing all the ReadingParser instance.
	 */
	private static void addNamespaceToInitializedPackages(String elementName, StartElement element, HashMap<String, ReadingParser> initializedParsers){
		Iterator<Namespace> nam = element.getNamespaces();

		while (nam.hasNext()){
			Namespace namespace = (Namespace) nam.next();
			
			if (elementName.equals("annotation") && !packageParsers.containsKey(namespace.getNamespaceURI())){
				packageParsers.put(namespace.getNamespaceURI(), AnnotationParser.class);
			}
			if (packageParsers.containsKey(namespace.getNamespaceURI()) && !initializedParsers.containsKey(namespace.getNamespaceURI())){

				ReadingParser newParser;
				try {
					newParser = packageParsers.get(namespace.getNamespaceURI()).newInstance();
					initializedParsers.put(namespace.getNamespaceURI(), newParser);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (!packageParsers.containsKey(namespace.getNamespaceURI()) && !initializedParsers.containsKey(namespace.getNamespaceURI())){
				// TODO what to do if we have namespaces but no parsers for this namespaces?
			}
		}
	}
	
	/**
	 * Reads the SBML file 'fileName' and creates/initialises a SBMLDocument instance.
	 * @param fileName : name of the SBML file to read.
	 * @return the initialised SBMLDocument.
	 */
	public static SBMLDocument readSBMLFile(String fileName){
		WstxInputFactory inputFactory = new WstxInputFactory();
		HashMap<String, ReadingParser> initializedParsers = null;
				
		try {

			XMLEventReader2 xmlEventReader = inputFactory.createXMLEventReader(new File(fileName));
			XMLEvent2 event;
			StartElement element = null;
			ReadingParser parser = null;
			ReadingParser attributeParser = null;
			ReadingParser namespaceParser = null;
			Stack<Object> SBMLElements = new Stack<Object>();
			QName currentNode = null;
			boolean isNested = false;
			boolean isText = false;

			// Read all the elements of the file
			while (xmlEventReader.hasNext()){
				event = (XMLEvent2) xmlEventReader.nextEvent();
				
				// StartDocument
				if (event.isStartDocument()){
					StartDocument startDocument = (StartDocument) event;
					// TODO check/store the XML version, etc?
				}
				// EndDocument
				else if (event.isEndDocument()){
					EndDocument endDocument = (EndDocument) event;
					// TODO End of the document, something to do?
				}
				// StartElement
				else if (event.isStartElement()){
					element = event.asStartElement();
					currentNode = element.getName();
					isNested = false;
					isText = false;

					String elementNamespace = currentNode.getNamespaceURI();
					// If the XML element is a sbml element, creates the necessary ReadingParser instances.
					// Creates an empty SBMLDocument instance and pushes it on the SBMLElements stack.
					if (currentNode.getLocalPart().equals("sbml")){
						initializedParsers = getInitializedPackageParsers(element);
						SBMLDocument sbmlDocument = new SBMLDocument();
						SBMLElements.push(sbmlDocument);
					}
						
					// To be able to parse all the SBML file, the sbml node should have been read first.
					if (!SBMLElements.isEmpty() && initializedParsers != null){

						// All the element should have a namespace.
						if (elementNamespace != null){
							addNamespaceToInitializedPackages(currentNode.getLocalPart(), element, initializedParsers);
							parser = initializedParsers.get(elementNamespace);
							// if the current node is a notes or message element and the matching ReadingParser is a StringParser, we need to set the typeOfNotes variable of the
							// StringParser instance.
							if (currentNode.getLocalPart().equals("notes") || currentNode.getLocalPart().equals("message")){
								ReadingParser sbmlparser = initializedParsers.get("http://www.w3.org/1999/xhtml");
								
								if (sbmlparser instanceof StringParser){
									StringParser notesParser = (StringParser) sbmlparser;
									notesParser.setTypeOfNotes(currentNode.getLocalPart());
								}
							}
							
							if (parser != null){
								
								Iterator<Namespace> nam = element.getNamespaces();
								Iterator<Attribute> att = element.getAttributes();
								boolean hasAttributes = att.hasNext();
								boolean hasNamespace = nam.hasNext();
	
								// All the subNodes of SBML are processed.
								if (!currentNode.getLocalPart().equals("sbml")){
									Object processedElement = parser.processStartElement(currentNode.getLocalPart(), currentNode.getPrefix(), hasAttributes, hasNamespace, SBMLElements.peek());
									SBMLElements.push(processedElement);
								}
								
								// process the namespaces
								while (nam.hasNext()){
									Namespace namespace = (Namespace) nam.next();
									boolean isLastNamespace = !nam.hasNext();
									namespaceParser = initializedParsers.get(namespace.getNamespaceURI());
									if (namespaceParser != null){
										namespaceParser.processNamespace(currentNode.getLocalPart(), namespace.getNamespaceURI(), namespace.getName().getPrefix(), namespace.getName().getLocalPart(), hasAttributes, isLastNamespace, SBMLElements.peek());
									}
									else {
										// TODO what to do if we have namespaces but no parsers for this namespaces?
									}
								}
								// Process the attributes
								while(att.hasNext()){
									
									Attribute attribute = (Attribute) att.next();
									boolean isLastAttribute = !att.hasNext();
									QName attributeName = attribute.getName();
									
									if (!attribute.getName().getNamespaceURI().equals("")){
										attributeParser = initializedParsers.get(attribute.getName().getNamespaceURI());
									}
									else {
										attributeParser = parser;
									}
									
									if (attributeParser != null){
										attributeParser.processAttribute(currentNode.getLocalPart(), attributeName.getLocalPart(), attribute.getValue(), attributeName.getPrefix(), isLastAttribute, SBMLElements.peek());
									}
									else {
										// TODO there is no parser for the namespace of this attribute, what to do?
									}
								}
							}
							else{
								// TODO throw an error : There is no parser for this namespace URI, what to do?
							}
						}
						else{
							// TODO throw an error? Each element must have a namespace URI.
						}
					}
				}
				// Characters
				else if (event.isCharacters()){
					Characters characters = event.asCharacters();
					
					// If there is some white space after, before or into an element, the element contains text.
					if (!characters.isWhiteSpace()){
						isText = true;	
					}

					// process the text of a XML element.
					if (parser != null && !SBMLElements.isEmpty() && !characters.isWhiteSpace()){
						if (currentNode != null){
							parser.processCharactersOf(currentNode.getLocalPart(), characters.getData(), SBMLElements.peek());
						}
						else {
							parser.processCharactersOf(null, characters.getData(), SBMLElements.peek());
						}
					}
					else {
						// TODO if parser == null => the text of this node can be read, there is no parser for the namespace URI of the element. Throw an exception?
						// TODO if SBMLElement.isEmpty() => syntax error in the SBML document. There is no node sbml? Throw an exception?
						// TODO if currentNode == null => syntax error in the SBML document. The nodes with child nodes can't have text elements. Throw an exception?
					}
				}
				// EndElement
				else if (event.isEndElement()){
					EndElement endElement = event.asEndElement();

					// If this element contains no text and doesn't have any subNodes, this element is nested.
					if (!isText && currentNode != null){
						if (currentNode.getLocalPart().equals(endElement.getName().getLocalPart())){
							isNested = true;
						}
					}
					
					if (initializedParsers != null){
						parser = initializedParsers.get(endElement.getName().getNamespaceURI());
						
						// if the current node is a notes or message element and the matching ReadingParser is a StringParser, we need to set the typeOfNotes variable of the
						// StringParser instance.
						if (endElement.getName().getLocalPart().equals("notes") || endElement.getName().getLocalPart().equals("message")){
							ReadingParser sbmlparser = initializedParsers.get("http://www.w3.org/1999/xhtml");
							if (sbmlparser instanceof StringParser){
								StringParser notesParser = (StringParser) sbmlparser;
								notesParser.setTypeOfNotes(endElement.getName().getLocalPart());
							}
						}
						// process the end of the element.
						if (!SBMLElements.isEmpty() && parser != null){
							parser.processEndElement(endElement.getName().getLocalPart(), endElement.getName().getPrefix(), isNested, SBMLElements.peek());
							// remove the top of the SBMLEements stack at the end of an element if this element is not the sbml element.
							if (!endElement.getName().getLocalPart().equals("sbml")){
								
								SBMLElements.pop();
							}
							else {
								// process the end of the document and return the final SBMLDocument
								if (SBMLElements.peek() instanceof SBMLDocument){
									SBMLDocument sbmlDocument = (SBMLDocument) SBMLElements.peek();
									Iterator<Entry<String, ReadingParser>> iterator = initializedParsers.entrySet().iterator();
									
									while (iterator.hasNext()){
										Entry<String, ReadingParser> entry = iterator.next();
										
										ReadingParser sbmlParser = entry.getValue();
										sbmlParser.processEndDocument(sbmlDocument);
									}
									return (SBMLDocument) SBMLElements.peek();
								}
								else {
									// TODO at the end of a sbml node, the SBMLElements stack must contains only a SBMLDocument instance.
									// Otherwise, there is a syntax error in the SBML document, Throw an error?
								}
							}
						}
						else {
							// TODO if SBMLElements.isEmpty => there is a syntax error in the SBMLDocument, throw an error?
							// TODO if parser == null => there is no parser for the namespace of this element, throw an error?
						}
					}
					else {
						// TODO The initialized parsers map should be initialized as soon as there is a sbml node.
						// if it is null, there is an syntax error in the SBML file. Throw an error?
					}
					currentNode = null;
					isNested = false;
					isText = false;
				}
			}
			return null;
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){		
		//SBMLDocument testDocument = readSBMLFile("/home/compneur/Desktop/LibSBML-Project/MultiExamples/glutamateReceptor.xml");
		//SBMLDocument testDocument = readSBMLFile("/home/compneur/Desktop/LibSBML-Project/BIOMD0000000002.xml");	
		//SBMLDocument testDocument = readSBMLFile("/home/compneur/workspace/jsbmlStax/src/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000227.xml");
		SBMLDocument testDocument = readSBMLFile("/home/compneur/workspace/jsbmlStax/src/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000228.xml");
	}

	/**
	 * Reads a SBML String 'fileName' 
	 * @param fileName
	 * @return the matching SBMLDocument instance.
	 */
	public static SBMLDocument readSBML(String fileName) {
		return SBMLReader.readSBMLFile(fileName);
	}

}
