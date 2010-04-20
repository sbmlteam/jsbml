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

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

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
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.sbmlExtensions.groups.GroupsParser;
import org.sbml.jsbml.util.JAXPFacade;
import org.sbml.jsbml.xml.sbmlParsers.MultiParser;
import org.sbml.jsbml.xml.sbmlParsers.SBMLCoreParser;
import org.w3c.dom.Document;

import com.ctc.wstx.stax.WstxOutputFactory;

/**
 * A SBMLWriter provides the methods to write a SBML file.
 * 
 * @author marine
 * @author rodrigue
 * 
 */
public class SBMLWriter {

	/**
	 * contains the WritingParser instances of this class.
	 */
	private static HashMap<String, WritingParser> instantiatedSBMLParsers = new HashMap<String, WritingParser>();

	/**
	 * contains all the relationships namespace URI <=> ReadingParser class.
	 */
	private static HashMap<String, Class<? extends WritingParser>> packageParsers = new HashMap<String, Class<? extends WritingParser>>();

	/**
	 * Initialises the packageParser HasMap of this class.
	 */
	public static void initializePackageParserNamespaces() {
		// TODO Load the map from a configuration file
		packageParsers.put(
				"http://www.sbml.org/sbml/level3/version1/multi/version1",
				MultiParser.class);
		packageParsers.put(
				"http://www.sbml.org/sbml/level3/version1/groups/version1",
				GroupsParser.class);
		packageParsers.put("http://www.sbml.org/sbml/level3/version1/core",
				SBMLCoreParser.class);
		packageParsers.put("http://www.sbml.org/sbml/level2",
				SBMLCoreParser.class);
	}

	/**
	 * 
	 * @param namespace
	 * @return the WritingParser class associated with 'namespace'. Null if
	 *         there is not matching WritingParser class.
	 */
	public static Class<? extends WritingParser> getWritingPackageParsers(
			String namespace) {
		return SBMLWriter.packageParsers.get(namespace);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 * @return the namespace matching the level and version.
	 */
	private static String getNamespaceFrom(int level, int version) {

		if (level == 3 && version == 1) {
			return "http://www.sbml.org/sbml/level3/version1/core";
		} else if (level == 2 && version == 4) {
			return "http://www.sbml.org/sbml/level2/version4";
		} else if (level == 2 && version == 3) {
			return "http://www.sbml.org/sbml/level2/version3";
		} else if (level == 2 && version == 2) {
			return "http://www.sbml.org/sbml/level2/version2";
		} else if (level == 2 && version == 1) {
			return "http://www.sbml.org/sbml/level2";
		} else if (level == 1 && version == 1) {
			return "http://www.sbml.org/sbml/level1";
		}
		return null;
	}

	/**
	 * Writes the notes of this sbase component.
	 * 
	 * @param sbase
	 *            : the SBase component
	 * @param element
	 *            : the matching SMOUtputElement
	 * @param writer
	 *            : the XMLStreamWriter2
	 * @param sbmlNamespace
	 *            : the SBML namespace
	 */
	private static void writeNotes(SBase sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace) {

		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		try {
			element.addCharacters(" \n");

			DOMConverter converter = new DOMConverter();
			SMOutputElement note = element.addElementWithCharacters(namespace,
					"notes", " \n");
			String notes = sbase.getNotesString();
			/*
			 * This can lead to problems if utf8 characters are encoded in the
			 * notes using the designated HTML codes, i.e., &#8820; for left
			 * double quotes.
			 */
			// .replaceAll("&", "&amp;");
			Document domDocument = JAXPFacade.getInstance().create(
					new BufferedReader(new StringReader(notes)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			note.addCharacters("\n");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {			
			e.printStackTrace();
		}
	}

	/**
	 * Writes the message of this Constraint.
	 * 
	 * @param sbase
	 *            : the Constraint component
	 * @param element
	 *            : the matching SMOUtputElement
	 * @param writer
	 *            : the XMLStreamWriter2
	 * @param sbmlNamespace
	 *            : the SBML namespace
	 */
	private static void writeMessage(Constraint sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace) {

		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		try {
			element.addCharacters(" \n");

			DOMConverter converter = new DOMConverter();
			SMOutputElement note = element.addElementWithCharacters(namespace,
					"message", " \n");
			String messageString = sbase.getMessage().replaceAll("&", "&amp;");
			Document domDocument = JAXPFacade.getInstance().create(
					new BufferedReader(new StringReader(messageString)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			note.addCharacters("\n");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the MathML expression for the math element of this sbase
	 * component.
	 * 
	 * @param m
	 *            : the sbase component
	 * @param element
	 *            : the matching SMOutputElement
	 * @param writer
	 *            : the XMLStreamWriter2
	 */
	private static void writeMathML(MathContainer m, SMOutputElement element,
			XMLStreamWriter writer) {

		try {
			DOMConverter converter = new DOMConverter();
			
			element.addCharacters("\n");
			
			String math = m.getMathBufferToString().replaceAll("&", "&amp;");
			
			Document domDocument = JAXPFacade.getInstance().create(
					new BufferedReader(new StringReader(math)), true);
			converter.writeFragment(domDocument.getChildNodes(), writer);
			element.addCharacters("\n");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the model history represented by this ModelHistory instance.
	 * 
	 * @param modelHistory
	 *            : the model history to write
	 * @param rdfNamespaces
	 *            : contains the RDF namespaces and their prefixes.
	 * @param writer
	 *            : the XMLStreamWriter2
	 */
	private static void writeModelHistory(History modelHistory,
			HashMap<String, String> rdfNamespaces, XMLStreamWriter writer) {
		try {
			String rdfPrefix = rdfNamespaces
					.get("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			if (modelHistory.getNumCreators() > 0) {
				String creatorPrefix = rdfNamespaces
						.get("http://purl.org/dc/elements/1.1/");

				writer.writeStartElement(creatorPrefix, "creator",
						"http://purl.org/dc/elements/1.1/");
				writer.writeCharacters(" \n");
				writer.writeStartElement(rdfPrefix, "Bag",
						"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
				writer.writeCharacters(" \n");

				for (int i = 0; i < modelHistory.getNumCreators(); i++) {

					Creator modelCreator = modelHistory.getCreator(i);
					writer.writeStartElement(rdfPrefix, "li",
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
					writer.writeAttribute(rdfPrefix,
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
							"parseType", "Resource");
					writer.writeCharacters(" \n");
					String vCardPrefix = rdfNamespaces
							.get("http://www.w3.org/2001/vcard-rdf/3.0#");

					if (modelCreator.isSetFamilyName()
							|| modelCreator.isSetGivenName()) {
						writer.writeCharacters(" \n");
						writer.writeStartElement(vCardPrefix, "N",
								"http://www.w3.org/2001/vcard-rdf/3.0#");
						writer.writeAttribute(
								"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
								"parseType", "Resource");
						writer.writeCharacters(" \n");

						if (modelCreator.isSetFamilyName()) {
							writer.writeStartElement(vCardPrefix, "Family",
									"http://www.w3.org/2001/vcard-rdf/3.0#");
							writer
									.writeCharacters(modelCreator
											.getFamilyName());
							writer.writeEndElement();
							writer.writeCharacters(" \n");
						}
						if (modelCreator.isSetGivenName()) {
							writer.writeStartElement(vCardPrefix, "Given",
									"http://www.w3.org/2001/vcard-rdf/3.0#");
							writer.writeCharacters(modelCreator.getGivenName());
							writer.writeEndElement();
							writer.writeCharacters(" \n");
						}
						writer.writeEndElement();
						writer.writeCharacters(" \n");
					}

					if (modelCreator.isSetEmail()) {
						writer.writeStartElement(vCardPrefix, "EMAIL",
								"http://www.w3.org/2001/vcard-rdf/3.0#");
						writer.writeCharacters(modelCreator.getEmail());
						writer.writeEndElement();
						writer.writeCharacters(" \n");
					}
					if (modelCreator.isSetOrganisation()) {
						writer.writeStartElement(vCardPrefix, "ORG",
								"http://www.w3.org/2001/vcard-rdf/3.0#");
						writer.writeAttribute(rdfPrefix,
								"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
								"parseType", "Resource");

						writer.writeStartElement(vCardPrefix, "Orgname",
								"http://www.w3.org/2001/vcard-rdf/3.0#");
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

			// System.out.println("isSetCreatedDate = " + modelHistory.isSetCreatedDate());
			// System.out.println("isSetModifiedDate = " + modelHistory.isSetModifiedDate());
			
			if (modelHistory.isSetCreatedDate()) {
				writer.writeStartElement(datePrefix, "created",
						"http://purl.org/dc/terms/");
				writer.writeAttribute(rdfPrefix,
						"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
						"parseType", "Resource");
				writer.writeCharacters(" \n");

				writer.writeStartElement(datePrefix, "W3CDTF",
						"http://purl.org/dc/terms/");
				writer
						.writeCharacters(modelHistory.getCreatedDate()
								.toString());
				writer.writeEndElement();
				writer.writeCharacters(" \n");

				writer.writeEndElement();
				writer.writeCharacters(" \n");
			}
			if (modelHistory.isSetModifiedDate()) {
				for (int i = 0; i < modelHistory.getNumModifiedDates(); i++) {
					writer.writeStartElement(datePrefix, "modified",
							"http://purl.org/dc/terms/");
					writer.writeAttribute(rdfPrefix,
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
							"parseType", "Resource");
					writer.writeCharacters(" \n");

					writer.writeStartElement(datePrefix, "W3CDTF",
							"http://purl.org/dc/terms/");
					writer.writeCharacters(modelHistory.getModifiedDate(i)
							.toString());
					writer.writeEndElement();
					writer.writeCharacters(" \n");

					writer.writeEndElement();
					writer.writeCharacters(" \n");
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the listOfCVTerms.
	 * 
	 * @param listOfCVTerms
	 *            : the list of CVTerms to write
	 * @param rdfNamespaces
	 *            : the RDF namespaces and prefixes
	 * @param writer
	 *            : the XMLStreamWriter2
	 */
	private static void writeCVTerms(List<CVTerm> listOfCVTerms,
			HashMap<String, String> rdfNamespaces, XMLStreamWriter writer) {
		try {
			String rdfPrefix = rdfNamespaces
					.get("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			if (listOfCVTerms.size() > 0) {

				for (int i = 0; i < listOfCVTerms.size(); i++) {
					CVTerm cvTerm = listOfCVTerms.get(i);
					String namespaceURI = null;
					String prefix = null;
					String elementName = null;
					if (cvTerm.getQualifierType().equals(
							CVTerm.Type.BIOLOGICAL_QUALIFIER)) {
						namespaceURI = "http://biomodels.net/biology-qualifiers/";
						prefix = rdfNamespaces
								.get("http://biomodels.net/biology-qualifiers/");
						elementName = Annotation
								.getElementNameEquivalentToQualifier(cvTerm
										.getBiologicalQualifierType());
					} else if (cvTerm.getQualifierType().equals(
							CVTerm.Type.MODEL_QUALIFIER)) {
						namespaceURI = "http://biomodels.net/model-qualifiers/";
						prefix = rdfNamespaces
								.get("http://biomodels.net/model-qualifiers/");
						elementName = Annotation
								.getElementNameEquivalentToQualifier(cvTerm
										.getModelQualifierType());
					}

					if (namespaceURI != null && elementName != null
							&& prefix != null) {
						writer.writeStartElement(prefix, elementName,
								namespaceURI);
						writer.writeCharacters(" \n");
						if (cvTerm.getNumResources() > 0) {
							writer
									.writeStartElement(rdfPrefix, "Bag",
											"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
							writer.writeCharacters(" \n");
							for (int j = 0; j < cvTerm.getNumResources(); j++) {
								writer
										.writeStartElement(rdfPrefix, "li",
												"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
								writer
										.writeAttribute(
												rdfPrefix,
												"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
												"resource", cvTerm
														.getResourceURI(j));
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
			e.printStackTrace();
		}
	}

	/**
	 * Writes the RDF annotation contained in 'annotation'.
	 * 
	 * @param annotation
	 *            : the annotation to write
	 * @param annotationElement
	 *            : the matching SMOutputElement
	 * @param writer
	 *            : the XMLStreamWriter.
	 */
	private static void writeRDFAnnotation(Annotation annotation,
			SMOutputElement annotationElement, XMLStreamWriter writer) {
		try {
			SMNamespace namespace = annotationElement.getNamespace(
					"http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
			SMOutputElement rdfElement = annotationElement.addElement(
					namespace, "RDF");

			HashMap<String, String> rdfNamespaces = annotation
					.getRdfAnnotationNamespaces();
			Iterator<Entry<String, String>> it = rdfNamespaces.entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();

				if (!entry.getKey().equals(namespace.getURI())) {
					writer.writeNamespace(entry.getValue(), entry.getKey());
				}
			}
			rdfElement.addCharacters(" \n");

			SMOutputElement descriptionElement = rdfElement.addElement(
					namespace, "Description");
			descriptionElement.addAttribute(namespace, "about", annotation
					.getAbout());
			descriptionElement.addCharacters(" \n");
			if (annotation.isSetHistory()) {
				writeModelHistory(annotation.getHistory(), rdfNamespaces,
						writer);
			}
			if (annotation.getListOfCVTerms().size() > 0) {
				writeCVTerms(annotation.getListOfCVTerms(), rdfNamespaces,
						writer);
			}
			descriptionElement.addCharacters("\n");
			rdfElement.addCharacters(" \n");
			annotationElement.addCharacters(" \n");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the annotation of this sbase component.
	 * 
	 * @param sbase
	 *            : the sbase component
	 * @param element
	 *            : the matching SMOutputElement
	 * @param writer
	 *            : the XMLStreamWriter2
	 * @param sbmlNamespace
	 *            : the SBML namespace.
	 */
	private static void writeAnnotation(SBase sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace) {
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		Annotation annotation = sbase.getAnnotation();
		SMOutputElement annotationElement;
		try {
			element.addCharacters(" \n");
			annotationElement = element.addElement(namespace, "annotation");

			if (annotation.getNoRDFAnnotation() != null) {
				StringBuffer annotationBeginning = new StringBuffer(
						"<annotation");

				HashMap<String, String> otherNamespaces = annotation
						.getAnnotationNamespaces();
				Iterator<Entry<String, String>> it = otherNamespaces.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					annotationBeginning.append(" " + entry.getKey() + "=\""
							+ entry.getValue() + "\"");
					if (entry.getKey().contains(":")) {
						String[] key = entry.getKey().split(":");
						annotationElement
								.getNamespace(key[1], entry.getValue());
					} else {
						annotationElement.getNamespace("", entry.getValue());
					}
				}
				annotationBeginning.append("> \n").append(
						annotation.getNoRDFAnnotation()).append(
						"</annotation> \n");

				DOMConverter converter = new DOMConverter();
				String annotationString = annotationBeginning.toString()
						.replaceAll("&", "&amp;");
				Document domDocument = JAXPFacade.getInstance().create(
						new BufferedReader(new StringReader(annotationString)),
						true);
				converter.writeFragment(domDocument.getFirstChild().getChildNodes(), writer);
			}

			if (annotation.isSetHistory()
					|| annotation.getListOfCVTerms().size() > 0) {
				writeRDFAnnotation(annotation, annotationElement, writer);
			}
			SBMLObjectForXML xmlObject = new SBMLObjectForXML();
			writeSBMLElements(xmlObject, annotationElement, writer, annotation,
					null, null);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Writes the SBML components.
	 * 
	 * @param xmlObject
	 *            : contains the XML information of the parentElement.
	 * @param parentElement
	 *            : SMOutputElement of the parentElement.
	 * @param streamWriter
	 * @param objectToWrite
	 *            : the Object to write.
	 * @param notesParser
	 *            : the WritingParser to parse the notes.
	 * @param MathMLParser
	 *            : the WritingParser to parse the MathML expressions.
	 */
	private static void writeSBMLElements(SBMLObjectForXML xmlObject,
			SMOutputElement parentElement, XMLStreamWriter streamWriter,
			Object objectToWrite, ReadingParser notesParser,
			ReadingParser MathMLParser) {

		ArrayList<WritingParser> listOfPackages = getInitializedParsers(
				objectToWrite, parentElement.getNamespace().getURI());

		// System.out.println("SBMLWriter : writeSBMLElements : xmlObject = " + xmlObject);
//		System.out.println("SBMLWriter : writeSBMLElements : parentElement = " 
//				+ parentElement.getLocalName() + ", "
//				+ parentElement.getNamespace().getURI());
//		System.out.println("SBMLWriter : writeSBMLElements : objectToWrite = "
//				+ objectToWrite + "\n");
//		System.out.println("SBMLWriter : writeSBMLElements : listOfPackages = "
//				+ listOfPackages + "\n");

		Iterator<WritingParser> iterator = listOfPackages.iterator();
		while (iterator.hasNext()) {
			WritingParser parser = iterator.next();
			ArrayList<Object> sbmlElementsToWrite = parser
					.getListOfSBMLElementsToWrite(objectToWrite);

//			System.out.println("SBMLWriter : writeSBMLElements : parser = "
//					+ parser);
//			System.out
//					.println("SBMLWriter : writeSBMLElements : elementsToWrite = "
//							+ sbmlElementsToWrite);

			if (sbmlElementsToWrite == null) {
				// TODO test if there are some characters to write.
			} else {
				for (int i = 0; i < sbmlElementsToWrite.size(); i++) {
					Object nextObjectToWrite = sbmlElementsToWrite.get(i);

					xmlObject.clear();
					
					/*
					 *  The following containers are all optional in a <reaction>,
					 *  but if any is present, it must not be empty: <listOfReactants>,
					 *  <listOfProducts>, <listOfModifiers>, <kineticLaw>.
					 *  (References: L2V2 Section 4.13; L2V3 Section 4.13; L2V4 Section 4.13) 
					 */
					if (nextObjectToWrite instanceof ListOf) {
					  ListOf<?> toTest = (ListOf<?>) nextObjectToWrite;
					  Type listType = toTest.getSBaseListType();
					  if (listType.equals(ListOf.Type.listOfReactants) || listType.equals(ListOf.Type.listOfProducts)
					      || listType.equals(ListOf.Type.listOfModifiers)) {
					    if (toTest.size()<1) {
					      continue; // Skip these, see reference in comment above.
					    }
					  }
					} else if (nextObjectToWrite instanceof KineticLaw) {
					  // TODO: Is there any chance, that an KineticLaw get's an empty XML entity?
					}
					parser.writeElement(xmlObject, nextObjectToWrite);
					
					parser.writeNamespaces(xmlObject, nextObjectToWrite);
					parser.writeAttributes(xmlObject, nextObjectToWrite);
					SMOutputElement newOutPutElement = null;
					if (xmlObject.isSetName()) {
						try {

							if (xmlObject.isSetNamespace()) {
								SMNamespace namespaceContext = parentElement
										.getNamespace(xmlObject.getNamespace(),
												xmlObject.getPrefix());
								newOutPutElement = parentElement.addElement(
										namespaceContext, xmlObject.getName());
							} else {

								newOutPutElement = parentElement.addElement(
										parentElement.getNamespace(), xmlObject
												.getName());
							}

							Iterator<Entry<String, String>> it = xmlObject
									.getAttributes().entrySet().iterator();
							while (it.hasNext()) {
								Entry<String, String> entry = it.next();
								newOutPutElement.addAttribute(entry.getKey(),
										entry.getValue());
							}
							if (nextObjectToWrite instanceof SBase) {
								SBase s = (SBase) nextObjectToWrite;
								if (s.isSetNotes() && notesParser != null) {
									writeNotes(s, newOutPutElement,
											streamWriter, newOutPutElement
													.getNamespace().getURI());
								}
								if (s.isSetAnnotation()) {
									writeAnnotation(s, newOutPutElement,
											streamWriter, newOutPutElement
													.getNamespace().getURI());
								}
							}
							if (nextObjectToWrite instanceof Constraint
									&& notesParser != null) {
								Constraint constraint = (Constraint) nextObjectToWrite;
								if (!constraint.isSetMessage()) {
									writeMathML(constraint, newOutPutElement,
											streamWriter);
								}
							}
							if (nextObjectToWrite instanceof MathContainer
									&& MathMLParser != null) {
								MathContainer mathContainer = (MathContainer) nextObjectToWrite;
								if (!mathContainer.isSetMath()
										&& mathContainer.isSetMathBuffer()) {
									writeMathML(mathContainer,
											newOutPutElement, streamWriter);
								}
							}
							newOutPutElement.addCharacters(" \n");
							writeSBMLElements(xmlObject, newOutPutElement,
									streamWriter, nextObjectToWrite,
									notesParser, MathMLParser);
							parentElement.addCharacters(" \n");
						} catch (XMLStreamException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param object
	 *            : object to write
	 * @param namespace
	 * @return the list of the WritingParser instances necessary to write this
	 *         object.
	 */
	private static ArrayList<WritingParser> getInitializedParsers(
			Object object, String namespace) {
		Set<String> packageNamespaces = null;

//		System.out.println("SBMLWriter : getInitializedParsers : namespace, object = "
//						+ namespace + ", " + object);

		if (object instanceof SBase) {
			SBase sbase = (SBase) object;
			packageNamespaces = sbase.getNamespaces();
		} else if (object instanceof Annotation) {
			Annotation annotation = (Annotation) object;
			packageNamespaces = annotation.getNamespaces();
		}
		ArrayList<WritingParser> SBMLParsers = new ArrayList<WritingParser>();

		if (packageNamespaces != null) {

//			System.out
//					.println("SBMLWriter : getInitializedParsers : namespaces = "
//							+ packageNamespaces);
			if (!packageNamespaces.contains(namespace)) {
				try {

					if (SBMLWriter.instantiatedSBMLParsers
							.containsKey(namespace)) {
						WritingParser sbmlParser = SBMLWriter.instantiatedSBMLParsers
								.get(namespace);
						SBMLParsers.add(sbmlParser);
					} else {

						ReadingParser sbmlParser = SBMLReader
								.getPackageParsers(namespace).newInstance();

						if (sbmlParser instanceof WritingParser) {
							SBMLWriter.instantiatedSBMLParsers.put(namespace,
									(WritingParser) sbmlParser);
							SBMLParsers.add((WritingParser) sbmlParser);
						}
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			Iterator<String> iterator = packageNamespaces.iterator();
			while (iterator.hasNext()) {
				String packageNamespace = iterator.next();
				try {
					if (SBMLWriter.instantiatedSBMLParsers
							.containsKey(packageNamespace)) {
						WritingParser parser = SBMLWriter.instantiatedSBMLParsers
								.get(packageNamespace);
						SBMLParsers.add(parser);
					} else if (!SBMLWriter.instantiatedSBMLParsers
							.containsKey(packageNamespace)) {

					  // This check allows to write e.g. CellDesigner Namespaces
					  // manually to an XML file, without implement the whole parser.
					  // (e.g. http://www.sbml.org/2001/ns/celldesigner)
					  if (SBMLReader
                .getPackageParsers(packageNamespace)==null) {
					    System.out.println("Warning: Skipping detailed parsing of Namespace '" + packageNamespace + "'. No parser available.");
					    continue;
					  }
					  
						ReadingParser sbmlParser = SBMLReader
								.getPackageParsers(packageNamespace)
								.newInstance();

						if (sbmlParser instanceof WritingParser) {

							SBMLWriter.instantiatedSBMLParsers.put(
									packageNamespace,
									(WritingParser) sbmlParser);
							SBMLParsers.add((WritingParser) sbmlParser);
						}
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		// System.out.println("SBMLWriter : getInitializedParsers : SBMLparsers = "
		// + SBMLParsers);

		return SBMLParsers;
	}

	/**
	 * Writes the SBMLDocument in a SBML file.
	 * 
	 * @param sbmlDocument
	 *            : the SBMLDocument to write
	 * @param fileName
	 *            : the name of the file where to write the SBMLDocument.
	 */
	public static void write(SBMLDocument sbmlDocument, String fileName) {
		SBMLReader.initializePackageParserNamespaces();
		SBMLWriter.initializePackageParserNamespaces();
		sbmlDocument.getSBMLDocumentAttributes();

		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory
				.newInstance());
		SMOutputDocument outputDocument;
		try {
			XMLStreamWriter2 streamWriter = smFactory
					.createStax2Writer(new File(fileName));
			
			outputDocument = SMOutputFactory.createOutputDocument(streamWriter,
					"1.0", "UTF-8", false);
			//outputDocument.setIndentation("\n ", 1, 2);

			String SBMLNamespace = getNamespaceFrom(sbmlDocument.getLevel(),
					sbmlDocument.getVersion());
			SMOutputContext context = outputDocument.getContext();
			SMNamespace namespace = context.getNamespace(SBMLNamespace);
			namespace.setPreferredPrefix("");
			SMOutputElement sbmlElement = outputDocument.addElement(namespace,
					"sbml");

			SBMLObjectForXML xmlObject = new SBMLObjectForXML();
			xmlObject.setName("sbml");
			xmlObject.setNamespace(SBMLNamespace);
			xmlObject.addAttributes(sbmlDocument.writeXMLAttributes());

			Iterator<Entry<String, String>> it = xmlObject.getAttributes()
					.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				sbmlElement.addAttribute(entry.getKey(), entry.getValue());
			}
			ReadingParser notesParser = SBMLReader.getPackageParsers(
					"http://www.w3.org/1999/xhtml").newInstance();
			ReadingParser MathMLParser = SBMLReader.getPackageParsers(
					"http://www.w3.org/1998/Math/MathML").newInstance();
			if (sbmlDocument.isSetNotes()) {
				writeNotes(sbmlDocument, sbmlElement, streamWriter,
						SBMLNamespace);
			}
			if (sbmlDocument.isSetAnnotation()) {
				writeAnnotation(sbmlDocument, sbmlElement, streamWriter,
						SBMLNamespace);
			}

			writeSBMLElements(xmlObject, sbmlElement, streamWriter,
					sbmlDocument, notesParser, MathMLParser);

			outputDocument.closeRoot();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Usage : java org.sbml.jsbml.xml.stax.SBMLWriter sbmlFileName");
			System.exit(0);
		}
		
		String fileName = args[0];
		String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbml.xml");
		
		SBMLDocument testDocument = SBMLReader.readSBMLFile(fileName);

		write(testDocument, jsbmlWriteFileName);
	}

	// ToCHECK : writing of X should not include unset fields.

	// TODO : dcterms:created, dcterms:modified are not saved !
	
	// TODO : notes where an & is present are not written out (so almost all the notes from the models in Biomodels DB)
	// When java read some notes with &amp;, it convert it to simply & in UTF-8 and when trying to write out the notes, 
	// there is then an exception as the character is not allowed.
	
	// TODO : when there are some custom annotations that do not declare their namespace in the annotation but only on 
	// the sbml element, the whole annotation failed to be written out.
	
	// TODO : put all of that as tracker item on sourceforge as it will probably take some time to be resolved.
	// TODO : put some logging system in place
}
