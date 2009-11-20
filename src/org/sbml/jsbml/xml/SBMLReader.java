package org.sbml.jsbml.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;

import org.codehaus.stax2.XMLEventReader2;
import org.codehaus.stax2.evt.XMLEvent2;
import org.sbml.jsbml.element.SBMLDocument;
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

public class SBMLReader {
	
	private static HashMap <String, Class<? extends SBMLParser>> packageParsers = new HashMap<String, Class<? extends SBMLParser>>();
	
	private static void initializePackageParserNamespaces(){
		packageParsers.put("http://www.sbml.org/sbml/level3/version1/multi/version1", MultiParser.class);
		packageParsers.put("http://www.sbml.org/sbml/level3/version1/core", SBMLCoreParser.class);
		packageParsers.put("http://www.w3.org/1999/xhtml", StringParser.class);
		packageParsers.put("http://www.w3.org/1999/02/22-rdf-syntax-ns#", RDFAnnotationParser.class);
		packageParsers.put("http://purl.org/dc/elements/1.1/", CreatorParser.class);
		packageParsers.put("http://purl.org/dc/terms/", DatesParser.class);
		packageParsers.put("http://www.w3.org/2001/vcard-rdf/3.0#", VCardParser.class);
		packageParsers.put("http://biomodels.net/biology-qualifiers/", BiologicalQualifierParser.class);
		packageParsers.put("http://biomodels.net/model-qualifiers/", ModelQualifierParser.class);
		packageParsers.put("http://www.w3.org/1998/Math/MathML", StringParser.class);
	}
	
	private static boolean isPackageRequired(String namespaceURI, StartElement sbml){
		Iterator<Object> att = sbml.getAttributes();
		
		while(att.hasNext()){
			Attribute attribute = (Attribute) att.next();
			if (attribute.getName().getNamespaceURI().equals(namespaceURI)){
				if (attribute.getValue().toLowerCase().equals("true")){
					return true;
				}
				return false;
			}
		}
		return false; // throwing an Excetion would be better.
	}
	
	private static HashMap<String, SBMLParser> getInitializedPackageParsers(StartElement sbml){
		initializePackageParserNamespaces();
		
		HashMap<String, SBMLParser> initializedParsers = new HashMap<String, SBMLParser>();
		
		Iterator<Object> nam = sbml.getNamespaces();
		while(nam.hasNext()){
			Namespace namespace = (Namespace) nam.next();
			String namespaceURI = namespace.getNamespaceURI();

			if (namespace.getPrefix().length() == 0){
				packageParsers.put(namespaceURI, SBMLCoreParser.class);
				initializedParsers.put(namespaceURI, new SBMLCoreParser());
			}
			else if (packageParsers.containsKey(namespaceURI)){
				try {
					initializedParsers.put(namespaceURI, packageParsers.get(namespaceURI).newInstance());
				} catch (InstantiationException e) {
					if (isPackageRequired(namespaceURI, sbml)){
						//throw an Exception : package required for the parsing
					}
				} catch (IllegalAccessException e) {
					if (isPackageRequired(namespaceURI, sbml)){
						//throw an Exception : package required for the parsing
					}
				}
			}
			else {
				if (isPackageRequired(namespaceURI, sbml)){
					//throw an Exception : package required for the parsing
				}
			}
		}
		return initializedParsers;
	}
	
	public static SBMLDocument readSBMLFile(String fileName){
		WstxInputFactory inputFactory = new WstxInputFactory();
		HashMap<String, SBMLParser> initializedParsers = null;
				
		try {

			XMLEventReader2 xmlEventReader = inputFactory.createXMLEventReader(new File(fileName));
			XMLEvent2 event;
			StartElement element = null;
			SBMLParser parser = null;
			SBMLParser attributeParser = null;
			Stack<Object> SBMLElements = new Stack<Object>();
			QName currentNode = null;

			while (xmlEventReader.hasNext()){
				event = (XMLEvent2) xmlEventReader.nextEvent();
				if (event.isStartElement()){
					element = event.asStartElement();
					currentNode = element.getName();
					String elementNamespace = currentNode.getNamespaceURI();

					if (currentNode.getLocalPart().equals("sbml")){
						initializedParsers = getInitializedPackageParsers(element);
						if (elementNamespace != null){
							SBMLCoreParser defaultParser = (SBMLCoreParser) initializedParsers.get(elementNamespace);
							parser = defaultParser;
							
							if (defaultParser != null){
								SBMLDocument sbmlDocument = defaultParser.createSBMLDocumentFrom(element);
								SBMLElements.push(sbmlDocument);
							}
							else{
								// throw an error : the default parser doesn't match the SBML namespace URI of this file?
							}
						}
						else{
							// throw an error : a SBML document must have a namespace URI
						}
					}
					else if (!SBMLElements.isEmpty() && initializedParsers != null){
						Iterator<Object> nam = element.getNamespaces();
						
						if (currentNode.getLocalPart().equals("annotation")){
							
							AnnotationParser annotationParser = new AnnotationParser();
							while (nam.hasNext()){
								Namespace namespace = (Namespace) nam.next();
								
								initializedParsers.put(namespace.getNamespaceURI(), annotationParser);
							}
						}
						if (elementNamespace != null){
							
							while (nam.hasNext()){
								Namespace namespace = (Namespace) nam.next();
								
								if (packageParsers.containsKey(namespace.getNamespaceURI()) && !initializedParsers.containsKey(namespace.getNamespaceURI())){
									SBMLParser newParser = packageParsers.get(namespace.getNamespaceURI()).newInstance();
									initializedParsers.put(namespace.getNamespaceURI(), newParser);
								}
							}
							
							parser = initializedParsers.get(elementNamespace);
							
							if (currentNode.getLocalPart().equals("notes") || currentNode.getLocalPart().equals("message")){
								SBMLParser sbmlparser = initializedParsers.get("http://www.w3.org/1999/xhtml");
								
								if (sbmlparser instanceof StringParser){
									StringParser notesParser = (StringParser) sbmlparser;
									notesParser.setTypeOfNotes(currentNode.getLocalPart());
								}
							}
							
							if (parser != null){
								Iterator<Object> att = element.getAttributes();
								boolean hasAttributes = att.hasNext();
								
								if (event.isEndElement()){
									hasAttributes = true;
								}

								Object processedElement = parser.processStartElement(currentNode.getLocalPart(), currentNode.getPrefix(), hasAttributes, SBMLElements.peek());
								SBMLElements.push(processedElement);
					
								while(att.hasNext()){

									Attribute attribute = (Attribute) att.next();
									boolean isLastAttribute = !att.hasNext();
									QName attributeName = attribute.getName();
									
									if (!attribute.getName().getNamespaceURI().equals("")){
										attributeParser = initializedParsers.get(attribute.getName().getNamespaceURI());
										
										if (currentNode.getLocalPart().equals("notes") || currentNode.getLocalPart().equals("message")){
											SBMLParser sbmlparser = initializedParsers.get("http://www.w3.org/1999/xhtml");
											
											if (sbmlparser instanceof StringParser){
												StringParser notesParser = (StringParser) sbmlparser;
												notesParser.setTypeOfNotes(currentNode.getLocalPart());
											}
										}
									}
									
									if (attributeParser != null){
										attributeParser.processAttribute(currentNode.getLocalPart(), attributeName.getLocalPart(), attribute.getValue(), attributeName.getPrefix(), isLastAttribute, SBMLElements.peek());
									}
									else {
										// error or not depending on the required attribute
									}
								}
							}
							else{
								// throw an error : the default parser doesn't match the namespace URI of this element?
							}
						}
						else{
							// throw an error : a SBML element must have a namespace URI
						}
					}
				}
				else if (event.isCharacters()){
					Characters characters = event.asCharacters();

					if (parser != null && !SBMLElements.isEmpty() && currentNode != null){
						parser.processCharactersOf(currentNode.getLocalPart(), characters.getData(), SBMLElements.peek());
					}
				}
				else if (event.isEndElement()){
					EndElement endElement = event.asEndElement();
					boolean isNested = false;
					
					if (event.isStartElement()){
						isNested = true;
					}
					
					if (initializedParsers != null){
						parser = initializedParsers.get(endElement.getName().getNamespaceURI());
						
						if (endElement.getName().getLocalPart().equals("notes") || endElement.getName().getLocalPart().equals("message")){
							SBMLParser sbmlparser = initializedParsers.get("http://www.w3.org/1999/xhtml");
							if (sbmlparser instanceof StringParser){
								StringParser notesParser = (StringParser) sbmlparser;
								notesParser.setTypeOfNotes(endElement.getName().getLocalPart());
							}
						}
						
						if (!SBMLElements.isEmpty() && parser != null){
							parser.processEndElement(endElement.getName().getLocalPart(), endElement.getName().getPrefix(), isNested, SBMLElements.peek());
							if (!endElement.getName().getLocalPart().equals("sbml")){
								
								SBMLElements.pop();
							}
							else {
								if (SBMLElements.peek() instanceof SBMLDocument){
									SBMLDocument sbmlDocument = (SBMLDocument) SBMLElements.peek();
									Iterator<Entry<String, SBMLParser>> iterator = initializedParsers.entrySet().iterator();
									
									while (iterator.hasNext()){
										Entry<String, SBMLParser> entry = iterator.next();
										
										SBMLParser sbmlParser = entry.getValue();
										sbmlParser.processEndDocument(sbmlDocument);
									}
									return (SBMLDocument) SBMLElements.peek();
								}
							}
						}
					}
					element = null;
				}
			}
			return null;
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){		
		SBMLDocument testDocument = readSBMLFile("/home/compneur/Desktop/LibSBML-Project/BIOMD0000000001.xml");
		
	}

}
