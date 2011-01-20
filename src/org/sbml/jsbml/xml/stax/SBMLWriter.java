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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.codehaus.stax2.XMLOutputFactory2;
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
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.resources.Resource;
import org.sbml.jsbml.util.JAXPFacade;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.compilers.MathMLXMLStreamCompiler;
import org.sbml.jsbml.xml.parsers.XMLNodeWriter;
import org.w3c.dom.Document;
import org.w3c.util.DateParser;
import org.xml.sax.SAXException;

import com.ctc.wstx.stax.WstxOutputFactory;

/**
 * A SBMLWriter provides the methods to write a SBML file.
 * 
 * @author marine
 * @author rodrigue
 * @author Andreas Dr&auml;ger
 * 
 */
public class SBMLWriter {

	/**
	 * contains the WritingParser instances of this class.
	 */
	private static HashMap<String, WritingParser> instantiatedSBMLParsers = new HashMap<String, WritingParser>();

	/**
	 * contains all the relationships name space URI <=> ReadingParser class.
	 */
	private static HashMap<String, Class<? extends WritingParser>> packageParsers = new HashMap<String, Class<? extends WritingParser>>();

  /**
   * Remember already issued warnings to avoid having
   * multiple lines, saying the same thing
   * (Warning: Skipping detailed parsing of name space 'XYZ'. No parser available.)
   */
	private static transient List<String> issuedWarnings = new LinkedList<String>();
  
	/**
	 * This method creates the necessary number of white spaces at the beginning
	 * of an entry in the SBML file.
	 * 
	 * @param indent
	 * @return
	 */
	private static String createIndent(int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		return sb.toString();
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
    
		Logger logger = Logger.getLogger(SBMLWriter.class);
		// logger.debug("getInitializedParsers : name space, object = " + name space + ", " + object);

		if (object instanceof SBase) {
			SBase sbase = (SBase) object;
			packageNamespaces = sbase.getNamespaces();
		} else if (object instanceof Annotation) {
			Annotation annotation = (Annotation) object;
			packageNamespaces = annotation.getNamespaces();
		}
		ArrayList<WritingParser> sbmlParsers = new ArrayList<WritingParser>();

		if (packageNamespaces != null) {

			// logger.debug("getInitializedParsers : name spaces = " + packageNamespaces);
			
			if (!packageNamespaces.contains(namespace)) {
				try {

					if (SBMLWriter.instantiatedSBMLParsers
							.containsKey(namespace)) {
						WritingParser sbmlParser = SBMLWriter.instantiatedSBMLParsers
								.get(namespace);
						sbmlParsers.add(sbmlParser);
					} else {

						ReadingParser sbmlParser = SBMLReader
								.getPackageParsers(namespace).newInstance();

						if (sbmlParser instanceof WritingParser) {
							SBMLWriter.instantiatedSBMLParsers.put(namespace,
									(WritingParser) sbmlParser);
							sbmlParsers.add((WritingParser) sbmlParser);
						}
					}
				} catch (InstantiationException e) {
					throw new IllegalArgumentException(String.format(
							JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException(String.format(
							JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
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
						sbmlParsers.add(parser);
					} else if (!SBMLWriter.instantiatedSBMLParsers
							.containsKey(packageNamespace)) {

						// This check allows to write e.g. CellDesigner
						// Namespaces
						// manually to an XML file, without implement the whole
					  // parser.
					  // (e.g. http://www.sbml.org/2001/ns/celldesigner)
					  if (SBMLReader.getPackageParsers(packageNamespace) == null) {
					    if (!issuedWarnings.contains(packageNamespace)) {
					      logger.warn("Skipping detailed parsing of Namespace '" + packageNamespace
					          + "'. No parser available.");
					      issuedWarnings.add(packageNamespace);
					    }
					    continue;
					  }

						ReadingParser sbmlParser = SBMLReader
								.getPackageParsers(packageNamespace)
								.newInstance();

						if (sbmlParser instanceof WritingParser) {
							SBMLWriter.instantiatedSBMLParsers.put(
									packageNamespace,
									(WritingParser) sbmlParser);
							sbmlParsers.add((WritingParser) sbmlParser);
						}
					}
				} catch (InstantiationException e) {
					throw new IllegalArgumentException(String.format(
							JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException(String.format(
							JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
				}
			}
		}

		// logger.debug("getInitializedParsers : SBMLparsers = " + sbmlParsers);

		return sbmlParsers;
	}

	/**
	 * 
	 * @param level
	 * @param version
	 * @return the name space matching the level and version.
	 */
	private static String getNamespaceFrom(int level, int version) {
		if (level == 3) {
			if (version == 1) {
				return "http://www.sbml.org/sbml/level3/version1/core";
			}
		} else if (level == 2) {
			if (version == 4) {
				return "http://www.sbml.org/sbml/level2/version4";
			} else if (version == 3) {
				return "http://www.sbml.org/sbml/level2/version3";
			} else if (version == 2) {
				return "http://www.sbml.org/sbml/level2/version2";
			} else if (version == 1) {
				return "http://www.sbml.org/sbml/level2";
			}
		} else if (level == 1) {
			if ((version == 1) || (version == 2)) {
				return "http://www.sbml.org/sbml/level1";
			}
		}
		return null;
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
	 * Initializes the packageParser {@link HasMap} of this class.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void initializePackageParserNamespaces() {
		Properties p = new Properties();
		try {
			p.loadFromXML(Resource.getInstance().getStreamFromResourceLocation(
									"org/sbml/jsbml/resources/cfg/PackageParserNamespaces.xml"));
			for (Map.Entry<Object, Object> entry : p.entrySet()) {
				packageParsers.put(entry.getKey().toString(),
						(Class<? extends WritingParser>) Class.forName(entry
								.getValue().toString()));
			}
		} catch (InvalidPropertiesFormatException e) {
			throw new IllegalArgumentException(
					"The format of the PackageParserNamespaces.xml file is incorrect.");
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"There was a problem opening the file PackageParserNamespaces.xml.");
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format(
									"There was a problem loading the file PackageParserNamespaces.xml: %s. " +
									"Please make sure the resource directory is included in the Java class path.",
									e.getMessage()));
		}
	}

	/**
	 * 
	 * @param args
	 * @throws SBMLException
	 */
	public static void main(String[] args) throws SBMLException {

		if (args.length < 1) {
			System.out
					.println("Usage : java org.sbml.jsbml.xml.stax.SBMLWriter sbmlFileName");
			System.exit(0);
		}

		String fileName = args[0];
		String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbml.xml");

		System.out.println("Reading " + fileName + " and writing "
				+ jsbmlWriteFileName);

		SBMLDocument testDocument;
		try {
			testDocument = SBMLReader.readSBMLFile(fileName);
			write(testDocument, jsbmlWriteFileName);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the SBMLDocument in a SBML file.
	 * 
	 * @param sbmlDocument
	 *            : the SBMLDocument to write
	 * @param stream
	 *            : a stream where to write the content of the model to.
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @throws IOException
	 * 
	 */
	public static void write(SBMLDocument sbmlDocument, OutputStream stream)
			throws XMLStreamException, SBMLException {
		write(sbmlDocument, stream, null, null);
	}

	/**
	 * 
	 * @param sbmlDocument
	 * @param stream
	 * @param programName
	 *            can be null
	 * @param programVersion
	 *            can be null
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * 
	 */
	public static void write(SBMLDocument sbmlDocument, OutputStream stream,
			String programName, String programVersion)
			throws XMLStreamException, SBMLException 
	{
		if (!sbmlDocument.isSetLevel() || !sbmlDocument.isSetVersion()) {
			throw new IllegalArgumentException(
					"Unable to write SBML output for documents with undefined SBML Level and Version flag.");
		}
		
		Logger logger = Logger.getLogger(SBMLWriter.class);
		
		SBMLReader.initializePackageParserNamespaces();
		SBMLWriter.initializePackageParserNamespaces();

		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory
				.newInstance());
		XMLStreamWriter2 streamWriter = smFactory.createStax2Writer(stream);		
		streamWriter.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, Boolean.FALSE);

		SMOutputDocument outputDocument = SMOutputFactory.createOutputDocument(
				streamWriter, "1.0", "UTF-8", false);
		// outputDocument.setIndentation(newLine + " ", 1, 2);

		String SBMLNamespace = getNamespaceFrom(sbmlDocument.getLevel(),
				sbmlDocument.getVersion());
		SMOutputContext context = outputDocument.getContext();
		SMNamespace namespace = context.getNamespace(SBMLNamespace);
		namespace.setPreferredPrefix("");
		outputDocument.addCharacters("\n");

		/*
		 * Write a comment to track which program created this SBML file and
		 * which version of JSBML was used for this purpose.
		 */
		if ((programName != null) && (programName.length() > 0)) {
			String date = String.format("%1$tY-%1$tm-%1$td %1$tR", Calendar
					.getInstance().getTime());
			String msg = " Created by %s version %s on %s with jsbml version %s. ";
			outputDocument.addComment(String
					.format(msg, programName, (programVersion != null)
							&& (programVersion.length() > 0) ? programVersion
							: "?", date, JSBML.getJSBMLDottedVersion()));
			outputDocument.addCharacters("\n");
		}

		SMOutputElement smOutputElement = outputDocument.addElement(namespace,
				sbmlDocument.getElementName());

		SBMLObjectForXML xmlObject = new SBMLObjectForXML();
		xmlObject.setName(sbmlDocument.getElementName());
		xmlObject.setNamespace(SBMLNamespace);
		xmlObject.addAttributes(sbmlDocument.writeXMLAttributes());

		// register all the namespaces of the SBMLDocument to the writer
		Iterator<Map.Entry<String, String>> it = sbmlDocument.getSBMLDocumentNamespaces().entrySet().iterator();
		
		logger.debug(" SBML namespaces size = " + sbmlDocument.getSBMLDocumentNamespaces().size());
		
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			if (!entry.getKey().equals("xmlns")) {
				
				logger.debug(" SBML namespaces : " + entry.getKey() + " = " + entry.getValue());
				
				String namespacePrefix = entry.getKey().substring(entry.getKey().indexOf(":") + 1);
				streamWriter.setPrefix(namespacePrefix, entry.getValue());

				logger.debug(" SBML namespaces : " + namespacePrefix + " = " + entry.getValue());

			}
		}
		
		it = xmlObject.getAttributes().entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			smOutputElement.addAttribute(entry.getKey(), entry.getValue());
		}
		ReadingParser notesParser = null;
		ReadingParser mathMLParser = null;
		try {
			notesParser = SBMLReader.getPackageParsers(
					JSBML.URI_XHTML_DEFINITION).newInstance();
			mathMLParser = SBMLReader.getPackageParsers(
					JSBML.URI_MATHML_DEFINITION).newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format(
					JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format(
					JSBML.UNDEFINED_PARSE_ERROR_MSG, e.getMessage()));
		}

		int indent = 2;
		if (sbmlDocument.isSetNotes()) {
			writeNotes(sbmlDocument, smOutputElement, streamWriter,
					SBMLNamespace, indent);
		}
		if (sbmlDocument.isSetAnnotation()) {
			writeAnnotation(sbmlDocument, smOutputElement, streamWriter,
					SBMLNamespace, indent);
		}
		smOutputElement.addCharacters("\n");

		writeSBMLElements(xmlObject, smOutputElement, streamWriter,
				sbmlDocument, notesParser, mathMLParser, indent);

		outputDocument.closeRoot();
	}

	/**
	 * Writes the XML representation of an {@link SBMLDocument} in a SBML file.
	 * 
	 * @param sbmlDocument
	 *            : the SBMLDocument to write
	 * @param fileName
	 *            : the name of the file where to write the {@link SBMLDocument}
	 *            .
	 * 
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 * @throws FileNotFoundException
	 *             if the file name is invalid
	 * @throws SBMLException
	 * 
	 */
	public static void write(SBMLDocument sbmlDocument, String fileName)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		write(sbmlDocument, fileName, null, null);
	}

	/**
	 * 
	 * @param sbmlDocument
	 * @param fileName
	 * @param programName
	 * @param programVersion
	 * 
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 * @throws SBMLException
	 * 
	 */
	public static void write(SBMLDocument sbmlDocument, String fileName,
			String programName, String programVersion)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		write(sbmlDocument, new BufferedOutputStream(new FileOutputStream(
				fileName)), programName, programVersion);
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
	 * @param indent
	 *            the number of indent white spaces of this annotation.
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @throws SAXException
	 */
	private static void writeAnnotation(SBase sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace, int indent)
			throws XMLStreamException, SBMLException 
	{
		SMNamespace namespace = element.getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");
		Annotation annotation = sbase.getAnnotation();
		SMOutputElement annotationElement;
		String whiteSpaces = createIndent(indent);
		element.addCharacters("\n");
		element.setIndentation(whiteSpaces, indent, 2);
		annotationElement = element.addElement(namespace, "annotation");
		annotationElement.setIndentation(whiteSpaces, indent, 2);

		if (annotation.getNonRDFannotation() != null) {
			StringBuffer annotationBeginning = StringTools.concat(whiteSpaces,
					"<annotation");

			// Adding the namespaces of the annotation element
			Map<String, String> otherNamespaces = annotation
					.getAnnotationNamespaces();

			Iterator<Entry<String, String>> it = otherNamespaces.entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				StringTools.append(annotationBeginning, " ", entry.getKey(),
						"=\"", entry.getValue(), "\"");
				if (entry.getKey().contains(":")) {
					String[] key = entry.getKey().split(":");
					annotationElement.getNamespace(key[1], entry.getValue());
				} else {
					annotationElement.getNamespace("", entry.getValue());
				}
			}
			
			// Adding the namespaces of the sbml element
			otherNamespaces = sbase.getSBMLDocument().getSBMLDocumentNamespaces();
			it = otherNamespaces.entrySet().iterator();
			
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				StringTools.append(annotationBeginning, " ", entry.getKey(),
						"=\"", entry.getValue(), "\"");
				if (entry.getKey().contains(":")) {
					String[] key = entry.getKey().split(":");
					annotationElement.getNamespace(key[1], entry.getValue());
				} else {
					// does nothing, we don't need the sbml namespace here
				}
			}

			StringTools.append(annotationBeginning, Character.valueOf('>'),
					Character.valueOf('\n'), annotation.getNonRDFannotation(),
					whiteSpaces, "</annotation>", Character.valueOf('\n'));

			DOMConverter converter = new DOMConverter();
			String annotationString = annotationBeginning.toString()
					.replaceAll("&", "&amp;");
			// here indent gets lost.
			Document domDocument = null;
			try {
				domDocument = JAXPFacade.getInstance().create(
						new BufferedReader(new StringReader(annotationString)),
						true);
				converter.writeFragment(domDocument.getFirstChild().getChildNodes(), writer);
			} catch (SAXException e) {
				e.printStackTrace();
				// TODO : log error or send SBMLException
			}
		} else {
			writer.writeCharacters("\n");
		}

		// if the given SBase is not a model and the level is smaller than 3,
		// no history can be written.
		// Annotation cannot be written without metaid tag.
		if (sbase.isSetMetaId()
				&& ((annotation.isSetHistory() && ((sbase.getLevel() >= 3) || (sbase instanceof Model))) || annotation
						.getListOfCVTerms().size() > 0)) {
			if (!annotation.isSetAbout()) {
				// add required missing tag
				annotation.setAbout("#" + sbase.getMetaId());
			}
			writeRDFAnnotation(annotation, annotationElement, writer,
					indent + 2);
		}
		SBMLObjectForXML xmlObject = new SBMLObjectForXML();
		writeSBMLElements(xmlObject, annotationElement, writer, annotation,
				null, null, indent + 2);
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
	 * @param indent
	 * @throws XMLStreamException
	 */
	private static void writeCVTerms(List<CVTerm> listOfCVTerms,
			Map<String, String> rdfNamespaces, XMLStreamWriter writer,
			int indent) throws XMLStreamException {
		String rdfPrefix = rdfNamespaces.get(Annotation.URI_RDF_SYNTAX_NS);
		String whiteSpace = createIndent(indent);
		if (listOfCVTerms.size() > 0) {
			for (int i = 0; i < listOfCVTerms.size(); i++) {
				CVTerm cvTerm = listOfCVTerms.get(i);
				String namespaceURI = null;
				String prefix = null;
				String elementName = null;
				if (cvTerm.getQualifierType().equals(
						CVTerm.Type.BIOLOGICAL_QUALIFIER)) {
					namespaceURI = CVTerm.Type.BIOLOGICAL_QUALIFIER.getNamespaceURI();
					prefix = rdfNamespaces.get(namespaceURI);
					elementName = cvTerm.getBiologicalQualifierType()
							.getElementNameEquivalent();
				} else if (cvTerm.getQualifierType().equals(
						CVTerm.Type.MODEL_QUALIFIER)) {
					namespaceURI = cvTerm.getQualifierType().getNamespaceURI();
					prefix = rdfNamespaces.get(namespaceURI);
					elementName = Annotation
							.getElementNameEquivalentToQualifier(cvTerm
									.getModelQualifierType());
				}
				if ((namespaceURI != null) && (elementName != null)
						&& (prefix != null)) {
					writer.writeCharacters(whiteSpace + "  ");
					writer.writeStartElement(prefix, elementName, namespaceURI);
					writer.writeCharacters("\n");
					if (cvTerm.getNumResources() > 0) {
						writer.writeCharacters(whiteSpace + "    ");
						writer.writeStartElement(rdfPrefix, "Bag",
								Annotation.URI_RDF_SYNTAX_NS);
						writer.writeCharacters("\n");
						for (int j = 0; j < cvTerm.getNumResources(); j++) {
							writer.writeCharacters(whiteSpace + "      ");
							writer.writeStartElement(rdfPrefix, "li",
									Annotation.URI_RDF_SYNTAX_NS);
							writer.writeAttribute(rdfPrefix,
									Annotation.URI_RDF_SYNTAX_NS, "resource",
									cvTerm.getResourceURI(j));
							writer.writeEndElement();
							writer.writeCharacters("\n");
						}
						writer.writeCharacters(whiteSpace + "    ");
						writer.writeEndElement();
						writer.writeCharacters("\n");
						writer.writeCharacters(whiteSpace + "  ");
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}
				}
			}
		}
	}

	/**
	 * Writes the history represented by this History instance.
	 * 
	 * @param history
	 *            : the model history to write
	 * @param rdfNamespaces
	 *            : contains the RDF namespaces and their prefixes.
	 * @param writer
	 *            : the XMLStreamWriter2
	 * @param indent
	 * @throws XMLStreamException
	 */
	private static void writeHistory(History history,
			Map<String, String> rdfNamespaces, XMLStreamWriter writer,
			int indent) throws XMLStreamException 
	{
		// Logger logger = Logger.getLogger(SBMLWriter.class);	
		
		String whiteSpace = createIndent(indent);
		String rdfPrefix = rdfNamespaces.get(Annotation.URI_RDF_SYNTAX_NS);
		if (history.getNumCreators() > 0) {
			String creatorPrefix = rdfNamespaces.get(JSBML.URI_PURL_ELEMENTS);
			writer.writeCharacters(whiteSpace);
			writer.writeStartElement(creatorPrefix, "creator",
					JSBML.URI_PURL_ELEMENTS);
			writer.writeCharacters("\n");
			writer.writeCharacters(whiteSpace + "  ");
			writer.writeStartElement(rdfPrefix, "Bag",
					Annotation.URI_RDF_SYNTAX_NS);
			writer.writeCharacters("\n");

			for (int i = 0; i < history.getNumCreators(); i++) {
				Creator modelCreator = history.getCreator(i);
				writer.writeCharacters(whiteSpace + "    ");
				writer.writeStartElement(rdfPrefix, "li",
						Annotation.URI_RDF_SYNTAX_NS);
				writer.writeAttribute(rdfPrefix,
						Annotation.URI_RDF_SYNTAX_NS,
						"parseType", "Resource");
				String vCardPrefix = rdfNamespaces
						.get(JSBML.URI_RDF_VCARD_NS);

				if (modelCreator.isSetFamilyName()
						|| modelCreator.isSetGivenName()) {
					writer.writeCharacters("\n");
					writer.writeCharacters(whiteSpace + "      ");
					writer.writeStartElement(vCardPrefix, "N",
							JSBML.URI_RDF_VCARD_NS);
					writer.writeAttribute(
							Annotation.URI_RDF_SYNTAX_NS,
							"parseType", "Resource");
					writer.writeCharacters("\n");

					if (modelCreator.isSetFamilyName()) {
						writer.writeCharacters(whiteSpace + "        ");
						writer.writeStartElement(vCardPrefix, "Family",
								JSBML.URI_RDF_VCARD_NS);
						writer.writeCharacters(modelCreator.getFamilyName());
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}
					if (modelCreator.isSetGivenName()) {
						writer.writeCharacters(whiteSpace + "        ");
						writer.writeStartElement(vCardPrefix, "Given",
								JSBML.URI_RDF_VCARD_NS);
						writer.writeCharacters(modelCreator.getGivenName());
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}
					writer.writeCharacters(whiteSpace + "      ");
					writer.writeEndElement();
					writer.writeCharacters("\n");
				}

				if (modelCreator.isSetEmail()) {
					writer.writeCharacters(whiteSpace + "      ");
					writer.writeStartElement(vCardPrefix, "EMAIL",
							JSBML.URI_RDF_VCARD_NS);
					writer.writeCharacters(modelCreator.getEmail());
					writer.writeEndElement();
					writer.writeCharacters("\n");
				}
				if (modelCreator.isSetOrganisation()) {
					writer.writeCharacters(whiteSpace + "      ");
					writer.writeStartElement(vCardPrefix, "ORG",
							JSBML.URI_RDF_VCARD_NS);
					writer.writeAttribute(rdfPrefix,
							Annotation.URI_RDF_SYNTAX_NS,
							"parseType", "Resource");
					writer.writeCharacters("\n");
					writer.writeCharacters(whiteSpace + "        ");
					writer.writeStartElement(vCardPrefix, "Orgname",
							JSBML.URI_RDF_VCARD_NS);
					writer.writeCharacters(modelCreator.getOrganisation());
					writer.writeEndElement();
					writer.writeCharacters("\n");
					writer.writeCharacters(whiteSpace + "      ");
					writer.writeEndElement();
					writer.writeCharacters("\n");
				}
				writer.writeCharacters(whiteSpace + "    ");
				writer.writeEndElement();
				writer.writeCharacters("\n");
			}
			writer.writeCharacters(whiteSpace + "  ");
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.writeCharacters(whiteSpace);
			writer.writeEndElement();
			writer.writeCharacters("\n");
		}

		String dctermPrefix = rdfNamespaces.get(JSBML.URI_PURL_TERMS);

		String creationDate;
		String now = creationDate = DateParser.getIsoDateNoMillis(new Date());
		
		if (history.isSetCreatedDate()) {
			creationDate = DateParser.getIsoDateNoMillis(history.getCreatedDate());
		} else { // We need to add a creation date
			creationDate = now;
		}
		writeW3CDate(writer, indent, creationDate, "created", dctermPrefix, rdfPrefix);

		// Writing the current modified dates.
		if (history.isSetModifiedDate()) {
			for (int i = 0; i < history.getNumModifiedDates(); i++) {
				writeW3CDate(writer, indent, DateParser.getIsoDateNoMillis(history.getModifiedDate(i)),
						"modified", dctermPrefix, rdfPrefix);
			}
		}
		// We need to add a new modified date
		writeW3CDate(writer, indent, now, "modified", dctermPrefix, rdfPrefix);
		
	}

	private static void writeW3CDate(XMLStreamWriter writer,
			int indent, String dateISO, String dcterm, 
			String dctermPrefix, String rdfPrefix) 
	throws XMLStreamException 
 {
		String whiteSpace = createIndent(indent);

		writer.writeCharacters(whiteSpace);
		writer.writeStartElement(dctermPrefix, dcterm, JSBML.URI_PURL_TERMS);
		writer.writeAttribute(rdfPrefix, Annotation.URI_RDF_SYNTAX_NS, "parseType",
				"Resource");
		writer.writeCharacters("\n");
		writer.writeCharacters(whiteSpace + "  ");
		writer.writeStartElement(dctermPrefix, "W3CDTF", JSBML.URI_PURL_TERMS);
		writer.writeCharacters(dateISO);
		writer.writeEndElement();
		writer.writeCharacters("\n");
		writer.writeCharacters(whiteSpace);
		writer.writeEndElement();
		writer.writeCharacters("\n");
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
	 *            : the XMLStreamWriter
	 * @param indent
	 * @throws XMLStreamException
	 * 
	 */
	private static void writeMathML(MathContainer m, SMOutputElement element,
			XMLStreamWriter writer, int indent) throws XMLStreamException {
		if (m.isSetMath()) {

			writer.writeCharacters("\n");
			writer.writeCharacters(createIndent(indent));
			// writer.setPrefix("math", URI_MATHML_DEFINITION);
			// writer.writeStartElement(URI_MATHML_DEFINITION,
			// "math");
			 
			writer.writeStartElement("math"); // URI_MATHML_DEFINITION, 
			writer.writeNamespace(null, JSBML.URI_MATHML_DEFINITION);
			writer.setPrefix("math", JSBML.URI_MATHML_DEFINITION);
			writer.setDefaultNamespace(JSBML.URI_MATHML_DEFINITION);
			
			// writer.writeAttribute("xmlns:math",	URI_MATHML_DEFINITION);
			
			writer.writeCharacters("\n");

			MathMLXMLStreamCompiler compiler = new MathMLXMLStreamCompiler(
					writer, createIndent(indent + 2));
			compiler.compile(m.getMath());

			writer.writeCharacters(createIndent(indent));
			writer.writeEndElement();
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
	 * @throws XMLStreamException 
	 */
	private static void writeMessage(Constraint sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace, int indent) throws XMLStreamException 
	{

		writer.writeCharacters("\n");

		XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, createIndent(indent));
		
		xmlNodeWriter.write(sbase.getMessage());
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
	 * @param indent
	 * @throws XMLStreamException
	 */
	private static void writeNotes(SBase sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace, int indent)
			throws XMLStreamException {

		writer.writeCharacters("\n");

		XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, createIndent(indent));
		
		xmlNodeWriter.write(sbase.getNotes());
		
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
	 * @param indent
	 * @throws XMLStreamException
	 */
	private static void writeRDFAnnotation(Annotation annotation,
			SMOutputElement annotationElement, XMLStreamWriter writer,
			int indent) throws XMLStreamException 
	{
		//Logger logger = Logger.getLogger(SBMLWriter.class);
		
		String whiteSpace = createIndent(indent);
		SMNamespace namespace = annotationElement.getNamespace(
				Annotation.URI_RDF_SYNTAX_NS, "rdf");
		annotationElement.setIndentation(whiteSpace, indent, 2);
		SMOutputElement rdfElement = annotationElement.addElement(namespace,
				"RDF");

		/*
		 * TODO: Check which name spaces are really required and add only those;
		 * particularly, if name spaces are missing and it is known from the
		 * kind of RDF annotation, which name spaces are needed, these should be
		 * added automatically here.
		 */
		Map<String, String> rdfNamespaces = annotation
				.getRDFAnnotationNamespaces();
		Iterator<Entry<String, String>> it = rdfNamespaces.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			if (!entry.getKey().equals(namespace.getURI())) {
				writer.writeNamespace(entry.getValue(), entry.getKey());
			}
		}
		rdfElement.addCharacters("\n");
		rdfElement.setIndentation(whiteSpace + "  ", indent + 2, 2);
		SMOutputElement descriptionElement = rdfElement.addElement(namespace,
				"Description");
		descriptionElement.addAttribute(namespace, "about", annotation
				.getAbout());
		descriptionElement.addCharacters("\n");
		if (annotation.isSetHistory()) {
			writeHistory(annotation.getHistory(), rdfNamespaces, writer,
					indent + 4);
		}
		if (annotation.getListOfCVTerms().size() > 0) {
			writeCVTerms(annotation.getListOfCVTerms(), rdfNamespaces, writer,
					indent + 2);
		}
		descriptionElement.setIndentation(whiteSpace + "  ", indent + 2, 2);
		descriptionElement.addCharacters(whiteSpace + "  ");
		annotationElement.setIndentation(whiteSpace, indent, 2);
		rdfElement.addCharacters("\n");
		rdfElement.addCharacters(whiteSpace);
		annotationElement.addCharacters("\n");
	}

	/**
	 * Writes the SBML elements.
	 * 
	 * @param parentXmlObject
	 *            : contains the XML information of the parentElement.
	 * @param smOutputParentElement
	 *            : SMOutputElement of the parentElement.
	 * @param streamWriter
	 * @param objectToWrite
	 *            : the Object to write.
	 * @param notesParser
	 *            : the WritingParser to parse the notes.
	 * @param MathMLparser
	 *            : the WritingParser to parse the MathML expressions.
	 * @param indent
	 *            The number of white spaces to indent this element.
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @throws SAXException
	 */
	private static void writeSBMLElements(SBMLObjectForXML parentXmlObject,
			SMOutputElement smOutputParentElement,
			XMLStreamWriter streamWriter, Object objectToWrite,
			ReadingParser notesParser, ReadingParser MathMLparser, int indent)
			throws XMLStreamException, SBMLException {

		String whiteSpaces = createIndent(indent);

		// Get the list of parsers to use.
		// TODO : check this, it should probably be a loop, one element could be
		// written by several parser
		ArrayList<WritingParser> listOfPackages = getInitializedParsers(
				objectToWrite, smOutputParentElement.getNamespace().getURI());

		// System.out.println("SBMLWriter : writeSBMLElements : xmlObject = " +
		// xmlObject);
		// System.out.println("SBMLWriter : writeSBMLElements : parentElement = "
		// + parentElement.getLocalName() + ", "
		// + parentElement.getNamespace().getURI());
		// System.out.println("SBMLWriter : writeSBMLElements : objectToWrite = "
		// + objectToWrite + '\n');
		// System.out.println("SBMLWriter : writeSBMLElements : listOfPackages = "
		// + listOfPackages + '\n');

		Iterator<WritingParser> iterator = listOfPackages.iterator();
		while (iterator.hasNext()) {
			WritingParser parser = iterator.next();
			List<Object> sbmlElementsToWrite = parser
					.getListOfSBMLElementsToWrite(objectToWrite);

			// System.out.println("SBMLWriter : writeSBMLElements : parser = "
			// + parser);
			// System.out
			// .println("SBMLWriter : writeSBMLElements : elementsToWrite = "
			// + sbmlElementsToWrite);

			if (sbmlElementsToWrite == null) {
				// TODO test if there are some characters to write.
				streamWriter.writeCharacters(whiteSpaces.substring(0,
						indent - 2));
			} else {
				for (int i = 0; i < sbmlElementsToWrite.size(); i++) {
					Object nextObjectToWrite = sbmlElementsToWrite.get(i);

					/*
					 * Skip predefined UnitDefinitions (check depending on Level
					 * and Version).
					 */
					if (nextObjectToWrite instanceof ListOf<?>) {
						ListOf<?> list = (ListOf<?>) nextObjectToWrite;
						if (list.size() > 0) {
							SBase sb = list.getFirst();
							if ((sb instanceof UnitDefinition)
									&& (parser
											.getListOfSBMLElementsToWrite(nextObjectToWrite) == null)) {
								streamWriter.writeCharacters(whiteSpaces
										.substring(0, indent - 2));
								continue;
							}
						} else {
							streamWriter.writeCharacters(whiteSpaces.substring(
									0, indent - 2));
							continue;
						}
					}

					parentXmlObject.clear();

					/*
					 * The following containers are all optional in a
					 * <reaction>, but if any is present, it must not be empty:
					 * <listOfReactants>, <listOfProducts>, <listOfModifiers>,
					 * <kineticLaw>. (References: L2V2 Section 4.13; L2V3
					 * Section 4.13; L2V4 Section 4.13)
					 */
					if (nextObjectToWrite instanceof ListOf<?>) {
						ListOf<?> toTest = (ListOf<?>) nextObjectToWrite;
						Type listType = toTest.getSBaseListType();
						if (listType == Type.none) {
							// Prevent writing invalid SBML if list types are
							// not set appropriately.
							throw new SBMLException(String.format(
									"Unknown ListOf type \"%s\".", toTest
											.getElementName()));
						}
						if (listType.equals(ListOf.Type.listOfReactants)
								|| listType.equals(ListOf.Type.listOfProducts)
								|| listType.equals(ListOf.Type.listOfModifiers)) {
							if (toTest.size() < 1) {
								continue; // Skip these, see reference in
								// comment above.
							}
						}
					} else if (nextObjectToWrite instanceof KineticLaw) {
						// TODO: Is there any chance, that an KineticLaw get's
						// an empty XML entity?
					}
					streamWriter.writeCharacters(whiteSpaces);
					parser.writeElement(parentXmlObject, nextObjectToWrite);
					parser.writeNamespaces(parentXmlObject, nextObjectToWrite);
					parser.writeAttributes(parentXmlObject, nextObjectToWrite);
					SMOutputElement newOutPutElement = null;
					if (parentXmlObject.isSetName()) {
						if (parentXmlObject.isSetNamespace()) {
							SMNamespace namespaceContext = smOutputParentElement
									.getNamespace(parentXmlObject
											.getNamespace(), parentXmlObject
											.getPrefix());
							newOutPutElement = smOutputParentElement
									.addElement(namespaceContext,
											parentXmlObject.getName());
						} else {
							newOutPutElement = smOutputParentElement
									.addElement(smOutputParentElement
											.getNamespace(), parentXmlObject
											.getName());
						}

						Iterator<Entry<String, String>> it = parentXmlObject
								.getAttributes().entrySet().iterator();
						while (it.hasNext()) {
							Entry<String, String> entry = it.next();
							newOutPutElement.addAttribute(entry.getKey(), entry
									.getValue());
						}
						if (nextObjectToWrite instanceof SBase) {
							SBase s = (SBase) nextObjectToWrite;
							if (s.isSetNotes() && (notesParser != null)) {
								writeNotes(s, newOutPutElement, streamWriter,
										newOutPutElement.getNamespace()
												.getURI(), indent + 2);
							}
							if (s.isSetAnnotation()) {
								writeAnnotation(s, newOutPutElement,
										streamWriter, newOutPutElement
												.getNamespace().getURI(),
										indent + 2);
							}
						}
						if ((nextObjectToWrite instanceof Constraint)
								&& (notesParser != null)) {
							Constraint constraint = (Constraint) nextObjectToWrite;
							if (constraint.isSetMessage()) {
								writeMessage(constraint, newOutPutElement, streamWriter,
										newOutPutElement.getNamespace()
												.getURI(), indent + 2);
							}
						}
						if ((nextObjectToWrite instanceof MathContainer)
								&& (MathMLparser != null)) {
							MathContainer mathContainer = (MathContainer) nextObjectToWrite;
							writeMathML(mathContainer, newOutPutElement,
									streamWriter, indent + 2);
						}
						newOutPutElement.addCharacters("\n");

						writeSBMLElements(parentXmlObject, newOutPutElement,
								streamWriter, nextObjectToWrite, notesParser,
								MathMLparser, indent + 2);
						smOutputParentElement.addCharacters("\n");
					}
				}
				streamWriter.writeCharacters(whiteSpaces.substring(0,
						indent - 2));
			}
		}
	}

	/**
	 * Writes the given SBML document to an in-memory string.
	 * 
	 * @param doc
	 *            the <code>SBMLdocument</code>
	 * @return the XML representation of the <code>SBMLdocument</code> as a
	 *         String.
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 * @throws SBMLException
	 */
	public static String writeSBMLToString(SBMLDocument doc)
			throws XMLStreamException, SBMLException {
		return writeSBMLToString(doc, null, null);
	}

	/***
	 * 
	 * @param d
	 * @param programName
	 * @param programVersion
	 * @return
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * 
	 */
	public static String writeSBMLToString(SBMLDocument d, String programName,
			String programVersion) throws XMLStreamException, SBMLException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		write(d, stream, programName, programVersion);
		return stream.toString();
	}


	/**
	 * 
	 * @param document
	 * @param file
	 * @param programName
	 * @param programVersion
	 * @return
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 * @throws SBMLException
	 */
	public static void write(SBMLDocument document, File file,
			String programName, String programVersion)
			throws FileNotFoundException, XMLStreamException, SBMLException {
		FileOutputStream stream = new FileOutputStream(file);
		write(document, stream, programName, programVersion);
	}

	/**
	 * 
	 * @param document
	 * @param file
	 * @throws SBMLException 
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	 */
	public static void write(SBMLDocument document, File file) throws FileNotFoundException, XMLStreamException, SBMLException {
		write(document, file, null, null);
	}

	// ToCHECK : writing of X should not include unset fields.

	// TODO : dcterms:created, dcterms:modified are not saved !

	// TODO : when there are some custom annotations that do not declare their
	// namespace in the annotation but only on
	// the sbml element, the whole annotation failed to be written out.

	// TODO : put all of that as tracker item on sourceforge as it will probably
	// take some time to be resolved.

	// TODO : test a bit more Xstream and using Qname to see how it
	// can deal with math or rdf bloc

}
