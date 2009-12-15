/*
 * $Id: SBMLWriter.java 38 2009-12-14 15:50:38Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/xml/SBMLWriter.java $
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

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.staxmate.SMOutputFactory;
import org.codehaus.staxmate.dom.DOMConverter;
import org.codehaus.staxmate.out.SMNamespace;
import org.codehaus.staxmate.out.SMOutputContext;
import org.codehaus.staxmate.out.SMOutputDocument;
import org.codehaus.staxmate.out.SMOutputElement;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.ModelCreator;
import org.sbml.jsbml.ModelHistory;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.util.JAXPFacade;
import org.w3c.dom.Document;

import com.ctc.wstx.stax.WstxOutputFactory;

/**
 * A SBMLWriter provides the methods to write a SBML file.
 * @author marine
 *
 */
public class SBMLWriter {
	
	/**
	 * contains the WritingParser instances of this class.
	 */
	private static HashMap<String, WritingParser> instantiatedSBMLParsers = new HashMap<String, WritingParser>();
	
	/**
	 * 
	 * @param level
	 * @param version
	 * @return the namespace matching the level and version.
	 */
	private static String getNamespaceFrom(int level, int version){
		
		if (level == 3 && version == 1){
			return "http://www.sbml.org/sbml/level3/version1/core";
		}
		else if (level == 2 && version == 4){
			return "http://www.sbml.org/sbml/level2/version4";
		}
		else if (level == 2 && version == 3){
			return "http://www.sbml.org/sbml/level2/version3";
		}
		else if (level == 2 && version == 2){
			return "http://www.sbml.org/sbml/level2/version2";
		}
		else if (level == 2 && version == 1){
			return "http://www.sbml.org/sbml/level2";
		}
		else if (level == 1 && version == 1){
			return "http://www.sbml.org/sbml/level1";
		}
		return null;
	}
	
	/**
	 * Writes the notes of this sbase component.
	 * @param sbase : the SBase component
	 * @param element : the matching SMOUtputElement
	 * @param writer : the XMLStreamWriter2
	 * @param sbmlNamespace : the SBML namespace
	 */
	private static void writeNotes(SBase sbase, SMOutputElement element, XMLStreamWriter2 writer, String sbmlNamespace){
		
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		try {
			element.addCharacters(" \n");

			DOMConverter converter = new DOMConverter();
			SMOutputElement note = element.addElementWithCharacters(namespace, "notes", " \n");
			String notes = sbase.getNotesString().replaceAll("&", "&amp;");
			Document domDocument = JAXPFacade.getInstance().create(new BufferedReader(new StringReader(notes)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			note.addCharacters("\n");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Writes the message of this Constraint.
	 * @param sbase : the Constraint component
	 * @param element : the matching SMOUtputElement
	 * @param writer : the XMLStreamWriter2
	 * @param sbmlNamespace : the SBML namespace
	 */
	private static void writeMessage(Constraint sbase, SMOutputElement element, XMLStreamWriter2 writer, String sbmlNamespace){
		
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		try {
			element.addCharacters(" \n");

			DOMConverter converter = new DOMConverter();
			SMOutputElement note = element.addElementWithCharacters(namespace, "message", " \n");
			String messageString = sbase.getMessage().replaceAll("&", "&amp;");
			Document domDocument = JAXPFacade.getInstance().create(new BufferedReader(new StringReader(messageString)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			note.addCharacters("\n");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Writes the MathML expression for the math element of this sbase component.
	 * @param m : the sbase component
	 * @param element : the matching SMOutputElement
	 * @param writer : the XMLStreamWriter2
	 */
	private static void writeMathML(MathContainer m, SMOutputElement element, XMLStreamWriter2 writer){
		
		try {
			DOMConverter converter = new DOMConverter();
			SMOutputElement mathElement = element.addElement("math");
			
			SMNamespace namespace = mathElement.getNamespace("http://www.w3.org/1998/Math/MathML");
			namespace.setPreferredPrefix("");
			mathElement.addCharacters("\n");
			
			String math = m.getMathBufferToString().replaceAll("&", "&amp;");
			Document domDocument = JAXPFacade.getInstance().create(new BufferedReader(new StringReader(math)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			mathElement.addCharacters("\n");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Writes the model history represented by this ModelHistory instance.
	 * @param modelHistory : the model history to write
	 * @param rdfNamespaces : contains the RDF namespaces and their prefixes.
	 * @param writer : the XMLStreamWriter2
	 */
	private static void writeModelHistory(ModelHistory modelHistory, HashMap<String, String> rdfNamespaces, XMLStreamWriter2 writer){
		try {
			String rdfPrefix = rdfNamespaces.get("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			if (modelHistory.getNumCreators() > 0){
				String creatorPrefix = rdfNamespaces.get("http://purl.org/dc/elements/1.1/");
				
				writer.writeStartElement(creatorPrefix, "creator", "http://purl.org/dc/elements/1.1/");
				writer.writeCharacters(" \n");
				writer.writeStartElement(rdfPrefix, "Bag", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
				writer.writeCharacters(" \n");
				
				for (int i = 0; i < modelHistory.getNumCreators(); i++){

					ModelCreator modelCreator = modelHistory.getCreator(i);
					writer.writeStartElement(rdfPrefix, "li", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
					writer.writeAttribute(rdfPrefix, "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType", "Resource");
					writer.writeCharacters(" \n");
					String vCardPrefix = rdfNamespaces.get("http://www.w3.org/2001/vcard-rdf/3.0#");

					if (modelCreator.isSetFamilyName() || modelCreator.isSetGivenName()){
						writer.writeCharacters(" \n");
						writer.writeStartElement(vCardPrefix, "N", "http://www.w3.org/2001/vcard-rdf/3.0#");
						writer.writeAttribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType", "Resource");
						writer.writeCharacters(" \n");
						
						if (modelCreator.isSetFamilyName()){
							writer.writeStartElement(vCardPrefix, "Family", "http://www.w3.org/2001/vcard-rdf/3.0#");
							writer.writeCharacters(modelCreator.getFamilyName());
							writer.writeEndElement();
							writer.writeCharacters(" \n");
						}
						if (modelCreator.isSetGivenName()){
							writer.writeStartElement(vCardPrefix, "Given", "http://www.w3.org/2001/vcard-rdf/3.0#");
							writer.writeCharacters(modelCreator.getGivenName());
							writer.writeEndElement();
							writer.writeCharacters(" \n");
						}
						writer.writeEndElement();
						writer.writeCharacters(" \n");
					}
					
					if (modelCreator.isSetEmail()){
						writer.writeStartElement(vCardPrefix, "EMAIL", "http://www.w3.org/2001/vcard-rdf/3.0#");
						writer.writeCharacters(modelCreator.getEmail());
						writer.writeEndElement();
						writer.writeCharacters(" \n");
					}
					if (modelCreator.isSetOrganisation()){
						writer.writeStartElement(vCardPrefix, "ORG", "http://www.w3.org/2001/vcard-rdf/3.0#");
						writer.writeAttribute(rdfPrefix, "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType", "Resource");
						
						writer.writeStartElement(vCardPrefix, "Orgname", "http://www.w3.org/2001/vcard-rdf/3.0#");
						writer.writeCharacters(modelCreator.getOrganisation());
						writer.writeEndElement();
						writer.writeCharacters(" \n");
						
						writer.writeEndElement();
						writer.writeCharacters(" \n");
					}
					writer.writeEndElement();
					writer.writeCharacters(" \n");
				}
				writer.writeEndElement();
				writer.writeCharacters(" \n");
				writer.writeEndElement();
				writer.writeCharacters(" \n");
			}
			String datePrefix = rdfNamespaces.get("http://purl.org/dc/terms/");

			if (modelHistory.isSetCreatedDate()){
				writer.writeStartElement(datePrefix, "created", "http://purl.org/dc/terms/");
				writer.writeAttribute(rdfPrefix, "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType", "Resource");
				writer.writeCharacters(" \n");
				
				writer.writeStartElement(datePrefix, "W3CDTF", "http://purl.org/dc/terms/");
				writer.writeCharacters(modelHistory.getCreatedDate().toString());
				writer.writeEndElement();
				writer.writeCharacters(" \n");
				
				writer.writeEndElement();
				writer.writeCharacters(" \n");
			}
			if (modelHistory.isSetModifiedDate()){
				for (int i = 0; i < modelHistory.getNumModifiedDates(); i++){
					writer.writeStartElement(datePrefix, "modified", "http://purl.org/dc/terms/");
					writer.writeAttribute(rdfPrefix, "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType", "Resource");
					writer.writeCharacters(" \n");
					
					writer.writeStartElement(datePrefix, "W3CDTF", "http://purl.org/dc/terms/");
					writer.writeCharacters(modelHistory.getModifiedDate(i).toString());
					writer.writeEndElement();
					writer.writeCharacters(" \n");
					
					writer.writeEndElement();
					writer.writeCharacters(" \n");
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	/**
	 * Writes the listOfCVTerms.
	 * @param listOfCVTerms : the list of CVTerms to write
	 * @param rdfNamespaces : the RDF namespaces and prefixes
	 * @param writer : the XMLStreamWriter2
	 */
	private static void writeCVTerms(List<CVTerm> listOfCVTerms,  HashMap<String, String> rdfNamespaces, XMLStreamWriter2 writer){
		try {
			String rdfPrefix = rdfNamespaces.get("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			if (listOfCVTerms.size() > 0){
				
				for (int i = 0; i < listOfCVTerms.size(); i++){
					CVTerm cvTerm = listOfCVTerms.get(i);
					String namespaceURI = null;
					String prefix = null;
					String elementName = null;
					if (cvTerm.getQualifierType().equals(Qualifier.BIOLOGICAL_QUALIFIER)){
						namespaceURI = "http://biomodels.net/biology-qualifiers/";
						prefix = rdfNamespaces.get("http://biomodels.net/biology-qualifiers/");
						elementName = Annotation.getElementNameEquivalentToQualifier(cvTerm.getBiologicalQualifierType());
					}
					else if (cvTerm.getQualifierType().equals(Qualifier.MODEL_QUALIFIER)){
						namespaceURI = "http://biomodels.net/model-qualifiers/";
						prefix = rdfNamespaces.get("http://biomodels.net/model-qualifiers/");
						elementName = Annotation.getElementNameEquivalentToQualifier(cvTerm.getModelQualifierType());
					}
					
					if (namespaceURI != null && elementName != null && prefix != null){
						writer.writeStartElement(prefix, elementName, namespaceURI);
						writer.writeCharacters(" \n");
						if (cvTerm.getNumResources() > 0){
							writer.writeStartElement(rdfPrefix, "Bag", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
							writer.writeCharacters(" \n");
							for (int j = 0; j < cvTerm.getNumResources(); j++){
								writer.writeStartElement(rdfPrefix, "li", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
								writer.writeAttribute(rdfPrefix, "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "resource", cvTerm.getResourceURI(j));
								writer.writeEndElement();
								writer.writeCharacters(" \n");
							}
							writer.writeEndElement();
							writer.writeCharacters(" \n");
							writer.writeEndElement();
							writer.writeCharacters(" \n");
						}
					}
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	/**
	 * Writes the RDF annotation contained in 'annotation'.
	 * @param annotation : the annotation to write
	 * @param annotationElement : the matching SMOutputElement
	 * @param writer : the XMLStreamWriter.
	 */
	private static void writeRDFAnnotation(Annotation annotation, SMOutputElement annotationElement, XMLStreamWriter2 writer){
		try {
			SMNamespace namespace = annotationElement.getNamespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
			SMOutputElement rdfElement = annotationElement.addElement(namespace, "RDF");
			
			HashMap<String, String> rdfNamespaces = annotation.getRdfAnnotationNamespaces();
			Iterator<Entry<String, String>> it = rdfNamespaces.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				
				if (!entry.getKey().equals(namespace.getURI())){
						writer.writeNamespace(entry.getValue(), entry.getKey());					
				}
			}
			rdfElement.addCharacters(" \n");
			
			SMOutputElement descriptionElement = rdfElement.addElement(namespace, "Description");
			descriptionElement.addAttribute(namespace, "about", annotation.getAbout());
			descriptionElement.addCharacters(" \n");
			if (annotation.isSetModelHistory()){
				writeModelHistory(annotation.getModelHistory(), rdfNamespaces, writer);
			}
			if (annotation.getListOfCVTerms().size() > 0){
				writeCVTerms(annotation.getListOfCVTerms(), rdfNamespaces, writer);
			}
			descriptionElement.addCharacters("\n");
			rdfElement.addCharacters(" \n");
			annotationElement.addCharacters(" \n");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the annotation of this sbase component.
	 * @param sbase : the sbase component
	 * @param element : the matching SMOutputElement
	 * @param writer : the XMLStreamWriter2
	 * @param sbmlNamespace : the SBML namespace.
	 */
	private static void writeAnnotation(SBase sbase, SMOutputElement element, XMLStreamWriter2 writer, String sbmlNamespace){
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		Annotation annotation = sbase.getAnnotation();
		SMOutputElement annotationElement;
		try {
			element.addCharacters(" \n");
			annotationElement = element.addElement(namespace, "annotation");

			if (annotation.getNoRDFAnnotation() != null){
				StringBuffer annotationBeginning = new StringBuffer("<annotation");
				
				HashMap<String, String> otherNamespaces = annotation.getAnnotationNamespaces();
				Iterator<Entry<String, String>> it = otherNamespaces.entrySet().iterator();
				while(it.hasNext()){
					Entry<String, String> entry = it.next();
					annotationBeginning.append(" "+entry.getKey() + "=\"" + entry.getValue() + "\"");
					if (entry.getKey().contains(":")){
						String [] key = entry.getKey().split(":");
						annotationElement.getNamespace(key[1], entry.getValue());
					}
					else {
						annotationElement.getNamespace("", entry.getValue());
					}
				}
				annotationBeginning.append("> \n").append(annotation.getNoRDFAnnotation()).append("</annotation> \n");
				
				DOMConverter converter = new DOMConverter();
				String annotationString = annotationBeginning.toString().replaceAll("&", "&amp;");
				Document domDocument = JAXPFacade.getInstance().create(new BufferedReader(new StringReader(annotationString)), true);
				converter.writeFragment(domDocument.getFirstChild().getChildNodes(), writer);
			}
			
			if (annotation.isSetModelHistory() || annotation.getListOfCVTerms().size() > 0){
				writeRDFAnnotation(annotation, annotationElement, writer);
			}
			SBMLObjectForXML xmlObject = new SBMLObjectForXML();
			writeSBMLElements(xmlObject, annotationElement, writer, annotation, null, null);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the SBML components.
	 * @param xmlObject : contains the XML information of the parentElement.
	 * @param parentElement : SMOutputElement of the parentElement.
	 * @param streamWriter
	 * @param objectToWrite : the Object to write.
	 * @param notesParser : the WritingParser to parse the notes.
	 * @param MathMLParser : the WritingParser to parse the MathML expressions.
	 */
	private static void writeSBMLElements(SBMLObjectForXML xmlObject, SMOutputElement parentElement, XMLStreamWriter2 streamWriter, Object objectToWrite, ReadingParser notesParser, ReadingParser MathMLParser){
		ArrayList<WritingParser> listOfPackages = getInitializedParsers(objectToWrite, parentElement.getNamespace().getURI());

		Iterator<WritingParser> iterator = listOfPackages.iterator();
		while(iterator.hasNext()){
			WritingParser parser = iterator.next();
			ArrayList<Object> SBMLElementsToWrite = parser.getListOfSBMLElementsToWrite(objectToWrite);
			if (SBMLElementsToWrite == null){
				// TODO test if there are some characters to write.
			}
			else {
				for (int i = 0; i < SBMLElementsToWrite.size(); i++){
					Object nextObjectToWrite = SBMLElementsToWrite.get(i);

					xmlObject.clear();
					parser.writeElement(xmlObject, nextObjectToWrite);

					parser.writeNamespaces(xmlObject, nextObjectToWrite);
					parser.writeAttributes(xmlObject, nextObjectToWrite);
					SMOutputElement newOutPutElement = null;
					if (xmlObject.isSetName()){
						try {

							if (xmlObject.isSetNamespace()){
								SMNamespace namespaceContext = parentElement.getNamespace(xmlObject.getNamespace(), xmlObject.getPrefix());
								newOutPutElement = parentElement.addElement(namespaceContext, xmlObject.getName());
							}
							else {
								newOutPutElement = parentElement.addElement(parentElement.getNamespace(), xmlObject.getName());
							}
							
							Iterator<Entry<String, String>> it = xmlObject.getAttributes().entrySet().iterator();
							while (it.hasNext()){
								Entry<String, String> entry = it.next();
								newOutPutElement.addAttribute(entry.getKey(), entry.getValue());
							}
							if (nextObjectToWrite instanceof SBase){
								SBase s = (SBase) nextObjectToWrite;
								if (s.isSetNotes() && notesParser != null){
									writeNotes(s, newOutPutElement, streamWriter, newOutPutElement.getNamespace().getURI());
								}
								if (s.isSetAnnotation()){
									writeAnnotation(s, newOutPutElement, streamWriter, newOutPutElement.getNamespace().getURI());
								}
							}
							if (nextObjectToWrite instanceof Constraint && notesParser != null){
								Constraint constraint = (Constraint) nextObjectToWrite;
								if (!constraint.isSetMessage()){
									writeMathML(constraint, newOutPutElement, streamWriter);
								}
							}
							if (nextObjectToWrite instanceof MathContainer && MathMLParser != null){
								MathContainer mathContainer = (MathContainer) nextObjectToWrite;
								if (!mathContainer.isSetMath() && mathContainer.isSetMathBuffer()){
									writeMathML(mathContainer, newOutPutElement, streamWriter);
								}
							}
							newOutPutElement.addCharacters(" \n");
							writeSBMLElements(xmlObject, newOutPutElement, streamWriter, nextObjectToWrite, notesParser, MathMLParser);
							parentElement.addCharacters(" \n");
						} catch (XMLStreamException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param object : object to write
	 * @param SBMLNamespace
	 * @return the list of the WritingParser instances necessary to write this object.
	 */
	private static ArrayList<WritingParser> getInitializedParsers(Object object, String SBMLNamespace){
		Collection<String> packageNamespaces = null;

		if (object instanceof SBase){
			SBase sbase = (SBase) object;
			packageNamespaces = sbase.getNamespaces();
		}
		else if (object instanceof Annotation){
			Annotation annotation = (Annotation) object;
			packageNamespaces = annotation.getNamespaces();
		}
		ArrayList<WritingParser> SBMLParsers = new ArrayList<WritingParser>();
		
		if (packageNamespaces != null){
			if (!packageNamespaces.contains(SBMLNamespace)){
				try {
					if (SBMLWriter.instantiatedSBMLParsers.containsKey(SBMLNamespace)){
						WritingParser sbmlParser = SBMLWriter.instantiatedSBMLParsers.get(SBMLNamespace);
						SBMLParsers.add(sbmlParser);
					}
					else {
						ReadingParser sbmlParser = SBMLReader.getPackageParsers(SBMLNamespace).newInstance();
						
						if (sbmlParser instanceof WritingParser){
							SBMLWriter.instantiatedSBMLParsers.put(SBMLNamespace, (WritingParser) sbmlParser);
							SBMLParsers.add((WritingParser) sbmlParser);
						}
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Iterator<String> iterator = packageNamespaces.iterator();
			while(iterator.hasNext()){
				String namespace = iterator.next();
				try {
					if (SBMLWriter.instantiatedSBMLParsers.containsKey(namespace)){
						WritingParser parser = SBMLWriter.instantiatedSBMLParsers.get(namespace);
						SBMLParsers.add(parser);
					}
					else if (!SBMLWriter.instantiatedSBMLParsers.containsKey(namespace) && SBMLWriter.instantiatedSBMLParsers.get(namespace) instanceof WritingParser){
						WritingParser parser = (WritingParser) SBMLReader.getPackageParsers(namespace).newInstance();
						SBMLWriter.instantiatedSBMLParsers.put(SBMLNamespace, parser);
						SBMLParsers.add(parser);
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return SBMLParsers;
	}
	
	/**
	 * Writes the SBMLDocument in a SBML file.
	 * @param sbmlDocument : the SBMLDocument to write
	 * @param fileName : the name of the file where to write the SBMLDocument.
	 */
	public static void write(SBMLDocument sbmlDocument, String fileName){
		SBMLReader.initializePackageParserNamespaces();
		sbmlDocument.getSBMLDocumentAttributes();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory.newInstance());
		SMOutputDocument outputDocument;
		try {
			XMLStreamWriter2 streamWriter = smFactory.createStax2Writer(new File(fileName));
			outputDocument = SMOutputFactory.createOutputDocument(streamWriter, "1.0", "UTF-8", false);
			outputDocument.setIndentation("\n ", 1, 4);

			String SBMLNamespace = getNamespaceFrom(sbmlDocument.getLevel(), sbmlDocument.getVersion());
			SMOutputContext context = outputDocument.getContext();
			SMNamespace namespace = context.getNamespace(SBMLNamespace);
			namespace.setPreferredPrefix("");
			SMOutputElement sbmlElement = outputDocument.addElement(namespace, "sbml");
			
			SBMLObjectForXML xmlObject = new SBMLObjectForXML();
			xmlObject.setName("sbml");
			xmlObject.setNamespace(SBMLNamespace);
			
			Iterator<Entry<String, String>> it = xmlObject.getAttributes().entrySet().iterator();
			while (it.hasNext()){
				Entry<String, String> entry = it.next();
				sbmlElement.addAttribute(entry.getKey(), entry.getValue());
			}
			ReadingParser notesParser = SBMLReader.getPackageParsers("http://www.w3.org/1999/xhtml").newInstance();
			ReadingParser MathMLParser = SBMLReader.getPackageParsers("http://www.w3.org/1998/Math/MathML").newInstance();
			if (sbmlDocument.isSetNotes()){
				writeNotes(sbmlDocument, sbmlElement, streamWriter, SBMLNamespace);
			}
			if (sbmlDocument.isSetAnnotation()){
				writeAnnotation(sbmlDocument, sbmlElement, streamWriter, SBMLNamespace);
			}
			
			writeSBMLElements(xmlObject, sbmlElement, streamWriter, sbmlDocument, notesParser, MathMLParser);
			
			outputDocument.closeRoot();
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){		
		//SBMLDocument testDocument = readSBMLFile("/home/compneur/Desktop/LibSBML-Project/MultiExamples/glutamateReceptor.xml");
		SBMLDocument testDocument = SBMLReader.readSBMLFile("/home/compneur/Desktop/LibSBML-Project/BIOMD0000000002.xml");		
		//System.out.println(testDocument.getModel().getNotesString());
		write(testDocument, "test.xml");
	}


}
