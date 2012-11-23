/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.staxmate.SMOutputFactory;
import org.codehaus.staxmate.dom.DOMConverter;
import org.codehaus.staxmate.out.SMNamespace;
import org.codehaus.staxmate.out.SMOutputContainer;
import org.codehaus.staxmate.out.SMOutputContext;
import org.codehaus.staxmate.out.SMOutputDocument;
import org.codehaus.staxmate.out.SMOutputElement;
import org.codehaus.staxmate.out.SMRootFragment;
import org.sbml.jsbml.ASTNode;
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
import org.sbml.jsbml.util.JAXPFacade;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.compilers.MathMLXMLStreamCompiler;
import org.sbml.jsbml.xml.parsers.WritingParser;
import org.sbml.jsbml.xml.parsers.XMLNodeWriter;
import org.w3c.dom.Document;
import org.w3c.util.DateParser;
import org.xml.sax.SAXException;

import com.ctc.wstx.stax.WstxOutputFactory;

/**
 * A SBMLWriter provides the methods to write a SBML file.
 * 
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SBMLWriter {

	/**
	 * @return The default symbol to be used to indent new elements in the XML
	 *         representation of SBML data structures.
	 */
	public static char getDefaultIndentChar() {
		return ' ';
	}
	
	/**
	 * @return The default number of indent symbols to be concatenated at the
	 *         beginning of a new block in an XML representation of SBML data
	 *         structures.
	 */
	public static short getDefaultIndentCount() {
		return (short) 2;
	}

	/**
	 * Tests this class
	 * 
	 * @param args
	 * @throws SBMLException
	 */
	public static void main(String[] args) throws SBMLException {

		if (args.length < 1) {
			System.out.println(
			  "Usage: java org.sbml.jsbml.xml.stax.SBMLWriter sbmlFileName");
			System.exit(0);
		}

		// this JOptionPane is added here to be able to start visualVM profiling
		// before the reading or writing is started.
		// JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
		
		long init = Calendar.getInstance().getTimeInMillis();
		System.out.println(Calendar.getInstance().getTime());
		
		String fileName = args[0];
		String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbml.xml");
		
		System.out.printf("Reading %s and writing %s\n", 
		  fileName, jsbmlWriteFileName);

		SBMLDocument testDocument;
		long afterRead = 0;
		try {
			testDocument = new SBMLReader().readSBMLFile(fileName);
			System.out.printf("Reading done\n");
			System.out.println(Calendar.getInstance().getTime());
			afterRead = Calendar.getInstance().getTimeInMillis();

			// testDocument.checkConsistency(); 

			System.out.printf("Starting writing\n");
			
			new SBMLWriter().write(testDocument.clone(), jsbmlWriteFileName);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Calendar.getInstance().getTime());
		long end = Calendar.getInstance().getTimeInMillis();
		long nbSecondes = (end - init)/1000;
		long nbSecondesRead = (afterRead - init)/1000;
		long nbSecondesWrite = (end - afterRead)/1000;
		
		if (nbSecondes > 120) {
			System.out.println("It took " + nbSecondes/60 + " minutes.");
		} else {
			System.out.println("It took " + nbSecondes + " secondes.");			
		}
		System.out.println("Reading: " + nbSecondesRead + " secondes.");
		System.out.println("Writing: " + nbSecondesWrite + " secondes.");
	}

	/**
	 * The symbol for indentation.
	 */
	private char indentChar;
	
	/**
	 * The number of indentation symbols.
	 */
	private short indentCount;
	/**
	 * contains the WritingParser instances of this class.
	 */
	private HashMap<String, WritingParser> instantiatedSBMLParsers = new HashMap<String, WritingParser>();

	/**
	 * Remember already issued warnings to avoid having multiple lines, saying
	 * the same thing (Warning: Skipping detailed parsing of name space 'XYZ'.
	 * No parser available.)
	 */
	private transient List<String> issuedWarnings = new LinkedList<String>();

	Logger logger = Logger.getLogger(SBMLWriter.class);

	/**
	 * contains all the relationships name space URI <=> WritingParser class.
	 */
	private HashMap<String, Class<? extends WritingParser>> packageParsers = new HashMap<String, Class<? extends WritingParser>>();

	/**
	 * Creates a new {@link SBMLWriter} with default configuration for
	 * {@link #indentChar} and {@link #indentCount}.
	 */
	public SBMLWriter() {
		this(getDefaultIndentChar(), getDefaultIndentCount());
	}

	/**
	 * Creates a new {@link SBMLWriter} with the given configuration for the
	 * {@link #indentChar} and {@link #indentCount}, i.e., the symbol to be used
	 * to indent elements in the XML representation of SBML data objects and the
	 * number of these symbols to be concatenated at the beginning of each new
	 * line for a new element.
	 * 
	 * @param indentChar
	 * @param indentCount
	 */
	public SBMLWriter(char indentChar, short indentCount) {
		setIndentationChar(indentChar);
		setIndentationCount(indentCount);
	}
	
	/**
	 * Adds a {@link WritingParser} to the given list of {@link WritingParser} 
	 * 
	 * @param sbmlParsers
	 * @param sbmlParser
	 * @param namespace
	 */
	private void addWritingParser(ArrayList<WritingParser> sbmlParsers,
			WritingParser sbmlParser, String namespace) 
	{
		if (sbmlParser == null) {
			if (!issuedWarnings.contains(namespace)) {
				logger.debug("Skipping detailed parsing of Namespace '" + namespace
						+ "'. No parser available.");
				issuedWarnings.add(namespace);
			}
		} else {
			sbmlParsers.add(sbmlParser);
		}

	}

	/**
	 * This method creates the necessary number of white spaces at the beginning
	 * of an entry in the SBML file.
	 * 
	 * @param indent
	 * @return
	 */
	private String createIndentationString(int indent) {
	  return StringTools.fill(indent, indentChar);
	}

	/**
	 * Gives the symbol that is used to indent the SBML output for a better
	 * structure and to improve human-readability.
	 * 
	 * @return the character to be used for indentation.
	 */
	public char getIndentationChar() {
		return indentChar;
	}

	/**
	 * Gives the number of indent symbols that are inserted in a line to better structure the SBML output.
	 * 
	 * @return the indentCount
	 */
	public short getIndentationCount() {
		return indentCount;
	}


	/**
	 * Gets all the writing parsers necessary to write the given object.
	 * 
	 * @param object
	 * @param namespace
	 * @return all the writing parsers necessary to write this element.
	 */
	private ArrayList<WritingParser> getWritingParsers(Object object, String namespace) {
		
		Set<String> packageNamespaces = null;

		// logger.debug("getInitializedParsers: name space, object = " + name space + ", " + object);

		if (object instanceof SBase) {
			SBase sbase = (SBase) object;
			packageNamespaces = sbase.getExtensionPackages().keySet();
		} else if (object instanceof Annotation) {
			Annotation annotation = (Annotation) object;
			packageNamespaces = annotation.getNamespaces();
		} else {
			logger.warn("getInitializedParsers: I don't know what to do with " + object);
		}
		
		ArrayList<WritingParser> sbmlParsers = new ArrayList<WritingParser>();
		
		if (packageNamespaces != null) {
			
			
			if (!packageNamespaces.contains(namespace)) {
				
				WritingParser sbmlParser = instantiatedSBMLParsers.get(namespace);
				addWritingParser(sbmlParsers, sbmlParser, namespace);
			}
			
			Iterator<String> iterator = packageNamespaces.iterator();
			
			while (iterator.hasNext()) {
				String packageNamespace = iterator.next();
				WritingParser sbmlParser = instantiatedSBMLParsers.get(packageNamespace);
				addWritingParser(sbmlParsers, sbmlParser, packageNamespace);
			}
			
		} else {
			WritingParser sbmlParser = instantiatedSBMLParsers.get(namespace);
			addWritingParser(sbmlParsers, sbmlParser, namespace);
		}
			
		return sbmlParsers;
	}

	/**
	 * 
	 * @param namespace
	 * @return the WritingParser class associated with 'namespace'. Null if
	 *         there is not matching WritingParser class.
	 */
	public Class<? extends WritingParser> getWritingParsers(String namespace) {
		return packageParsers.get(namespace);
	}

	/**
	 * Initializes the packageParser {@link HasMap} of this class.
	 * 
	 */
	public void initializePackageParserNamespaces() {
		JSBML.loadClasses("org/sbml/jsbml/resources/cfg/PackageParserNamespaces.xml",
				packageParsers);
	}

	/**
	 * Creates the ReadingParser instances and stores them in a HashMap.
	 * 
	 * @return the map containing the ReadingParser instances.
	 */
	private Map<String, WritingParser> initializePackageParsers() {
		if (packageParsers.size() == 0) {
			initializePackageParserNamespaces();
		}

		for (String namespace : packageParsers.keySet()) {
			try {
				instantiatedSBMLParsers.put(namespace, (WritingParser) packageParsers.get(namespace).newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassCastException e) {
				// does nothing, some of the parsers are only Reader and not Writer
				// so would throw a ClassCastException
				logger.debug(e.getMessage());
			}
		}

		return instantiatedSBMLParsers;
	}

	/**
	 * Allows you to set another blank character for indentation. Allowed are
	 * only tabs and white spaces, i.e., <code>'\t'</code> and <code>' '</code>.
	 * 
	 * @param the character to be used for indentation
	 *            the indentSymbol to set
	 */
	public void setIndentationChar(char indentSymbol) {
		if ((indentSymbol != ' ') && (indentSymbol != '\t')) {
			throw new IllegalArgumentException(MessageFormat.format(
									"Invalid argument \"{0}\". Only the blank symbols '\\t' and ' ' are allowed for indentation.",
									indentSymbol));
		}
		this.indentChar = indentSymbol;
	}

	/**
	 * 
	 * @param indentCount
	 * @see SBMLWriter#setIndentationCount(short)
	 */
	public void setIndentationCount(int indentCount) {
		setIndentationCount((short) indentCount);
	}

	/**
	 * @param indentCount the indentCount to set
	 */
	public void setIndentationCount(short indentCount) {
		if (indentCount < 0) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Indent count must be non-negative. Invalid argument {0,number}.",
					indentCount));
		}
		this.indentCount = indentCount;
	}

	/**
	 * 
	 * @param document
	 * @param file
	 * @throws SBMLException
	 * @throws XMLStreamException
	 * @throws IOException
	 *             if it is not possible to write to the given file, e.g., due
	 *             to an invalid file name or missing permissions.
	 */
	public void write(SBMLDocument document, File file)
			throws XMLStreamException, SBMLException, IOException {
		write(document, file, null, null);
	}

	/**
	 * 
	 * @param document
	 * @param file
	 * @param programName
	 * @param programVersion
	 * @return
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @throws IOException
	 *             if it is not possible to write to the given file, e.g., due
	 *             to an invalid file name or missing permissions.
	 */
	public void write(SBMLDocument document, File file, String programName,
			String programVersion) throws XMLStreamException, SBMLException, IOException {
		FileOutputStream stream = new FileOutputStream(file);
		write(document, stream, programName, programVersion);
		stream.close();
	}

	/**
	 * Writes the SBMLDocument in a SBML file.
	 * 
	 * @param sbmlDocument
	 *          the SBMLDocument to write
	 * @param stream
	 *          a stream where to write the content of the model to.
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @throws IOException
	 * 
	 */
	public void write(SBMLDocument sbmlDocument, OutputStream stream)
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
	public void write(SBMLDocument sbmlDocument, OutputStream stream,
			String programName, String programVersion)
			throws XMLStreamException, SBMLException {
		if (!sbmlDocument.isSetLevel() || !sbmlDocument.isSetVersion()) {
			throw new IllegalArgumentException(
					"Unable to write SBML output for documents with undefined SBML Level and Version flag.");
		}

		Logger logger = Logger.getLogger(SBMLWriter.class);

		// Making sure that we use the good XML library
		System.setProperty("javax.xml.stream.XMLOutputFactory", "com.ctc.wstx.stax.WstxOutputFactory");
		System.setProperty("javax.xml.stream.XMLInputFactory", "com.ctc.wstx.stax.WstxInputFactory");
		System.setProperty("javax.xml.stream.XMLEventFactory", "com.ctc.wstx.stax.WstxEventFactory");
		
		initializePackageParsers();

		SMOutputFactory smFactory = new SMOutputFactory(XMLOutputFactory.newInstance());
		XMLStreamWriter2 streamWriter = smFactory.createStax2Writer(stream);
		
		// For this to work, the elements need to be completely empty (no whitespace or line return)
		streamWriter.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, Boolean.TRUE);

		SMOutputDocument outputDocument = SMOutputFactory.createOutputDocument(
				streamWriter, "1.0", "UTF-8", false);
		// to have the automatic indentation working, we should probably only be using StaxMate classes and not directly StAX
		// outputDocument.setIndentation("\n  ", 1, 1);

		String SBMLNamespace = JSBML.getNamespaceFrom(sbmlDocument.getLevel(),
				sbmlDocument.getVersion());
		SMOutputContext context = outputDocument.getContext();
		context.setIndentation("\n" + createIndentationString(indentCount), 1, 2);
		SMNamespace namespace = context.getNamespace(SBMLNamespace);
		namespace.setPreferredPrefix("");
		outputDocument.addCharacters("\n");

		/*
		 * Write a comment to track which program created this SBML file and
		 * which version of JSBML was used for this purpose.
		 */
		if ((programName != null) && (programName.length() > 0)) {
			String date = String.format("%1$tY-%1$tm-%1$td %1$tR", Calendar.getInstance().getTime());
			String msg = " Created by {0} version {1} on {2} with JSBML version {3}. ";
			outputDocument.addComment(
			  MessageFormat.format(msg, programName, (programVersion != null)
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

		// register all the name spaces of the SBMLDocument to the writer
		Iterator<Map.Entry<String, String>> it = sbmlDocument
				.getSBMLDocumentNamespaces().entrySet().iterator();

		logger.debug(" SBML name spaces size = "
				+ sbmlDocument.getSBMLDocumentNamespaces().size());

		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			if (!entry.getKey().equals("xmlns")) {

				logger.debug(" SBML name spaces: " + entry.getKey() + " = "
						+ entry.getValue());

				String namespacePrefix = entry.getKey().substring(
						entry.getKey().indexOf(":") + 1);
				streamWriter.setPrefix(namespacePrefix, entry.getValue());

				logger.debug(" SBML namespaces: " + namespacePrefix + " = "
						+ entry.getValue());

			}
		}

		if (sbmlDocument.getDeclaredNamespaces().size() > 0) {
					
			logger.debug(" SBML declared namespaces size = "
					+ sbmlDocument.getDeclaredNamespaces().size());

			xmlObject.addAttributes(sbmlDocument.getDeclaredNamespaces());
			
			for (String prefix : sbmlDocument.getDeclaredNamespaces().keySet()) {

				if (!prefix.equals("xmlns")) {

					String namespaceURI = sbmlDocument.getDeclaredNamespaces().get(prefix);

					logger.debug(" SBML name spaces: " + prefix + " = " + namespaceURI);

					String namespacePrefix = prefix.substring(prefix.indexOf(":") + 1);

					streamWriter.setPrefix(namespacePrefix, namespaceURI);

					logger.debug(" SBML namespaces: " + namespacePrefix + " = " + namespaceURI);
				}
			}
		}

		it = xmlObject.getAttributes().entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			smOutputElement.addAttribute(entry.getKey(), entry.getValue());
		}

		int indent = indentCount;
		if (sbmlDocument.isSetNotes()) {
			writeNotes(sbmlDocument, smOutputElement, streamWriter,
					SBMLNamespace, indent);
		}
		if (sbmlDocument.isSetAnnotation()) {
			writeAnnotation(sbmlDocument, smOutputElement, streamWriter,
					indent, false);
		}
		smOutputElement.addCharacters("\n");

		writeSBMLElements(xmlObject, smOutputElement, streamWriter,
				sbmlDocument, indent);

		outputDocument.closeRoot();
	}

	/**
	 * Writes the XML representation of an {@link SBMLDocument} in a SBML file.
	 * 
	 * @param sbmlDocument
	 *          the SBMLDocument to write
	 * @param fileName
	 *          the name of the file where to write the {@link SBMLDocument}
	 *            
	 * 
	 * @throws XMLStreamException
	 *             if any error occur while creating the XML document.
	 * @throws FileNotFoundException
	 *             if the file name is invalid
	 * @throws SBMLException
	 * 
	 */
	public void write(SBMLDocument sbmlDocument, String fileName)
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
	public void write(SBMLDocument sbmlDocument, String fileName,
			String programName, String programVersion)
			throws XMLStreamException, FileNotFoundException, SBMLException {
		write(sbmlDocument, new BufferedOutputStream(new FileOutputStream(
				fileName)), programName, programVersion);
	}

	/**
	 * 
	 * @param sbase
	 * @return
	 */
	public String writeAnnotation(SBase sbase) {
		
		String annotationStr = "";

		if (sbase == null || (!sbase.isSetAnnotation())) {
			return annotationStr;
		}
		
		StringWriter stream = new StringWriter();
		
		SMOutputFactory smFactory = new SMOutputFactory(WstxOutputFactory.newInstance());

		try {
			XMLStreamWriter2 writer = smFactory.createStax2Writer(stream);

			// For this to work, the elements need to be completely empty (no whitespace or line return)
			writer.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, Boolean.TRUE);

			// Create an xml fragment to avoid having the xml declaration
			SMRootFragment outputDocument = SMOutputFactory.createOutputFragment(writer);

			// all the sbml element namespaces are registered to the writer in the writeAnnotation method

			// call the writeAnnotation, indicating that we are building an xml fragment
			writeAnnotation(sbase, outputDocument, writer, 0, true);

			writer.writeEndDocument();
			writer.close();

			annotationStr = stream.toString();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (SBMLException e) {
			e.printStackTrace();
		}

		return annotationStr;
	}

	/**
	 * Writes the annotation of this sbase component.
	 * 
	 * @param sbase
	 *          the sbase component
	 * @param element
	 *          the matching SMOutputElement
	 * @param writer
	 *          the XMLStreamWriter2
	 * @param sbmlNamespace
	 *          the SBML namespace.
	 * @param indent
	 *            the number of indent white spaces of this annotation.
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @throws SAXException
	 */
	private void writeAnnotation(SBase sbase, SMOutputContainer element,
			XMLStreamWriter writer, int indent, boolean xmlFragment)
		throws XMLStreamException, SBMLException {
	  
		// create the sbmlNamespace variable
		String sbmlNamespace = JSBML.getNamespaceFrom(sbase.getLevel(), sbase.getVersion());
		SMNamespace namespace = element.getContext().getNamespace(sbmlNamespace);
		namespace.setPreferredPrefix("");

		Annotation annotation = sbase.getAnnotation();
		SMOutputElement annotationElement;
		String whiteSpaces = createIndentationString(indent);
		
		if (xmlFragment) {
			annotationElement = element.addElement("annotation");
		} else {
			element.addCharacters("\n");
			element.setIndentation(whiteSpaces, indent, indentCount);		
			annotationElement = element.addElement(namespace, "annotation");
		}
		annotationElement.setIndentation(whiteSpaces, indent, indentCount);

		if ((annotation.getNonRDFannotation() != null)
				&& (annotation.getNonRDFannotation().length() > 0)) {
			StringBuffer annotationBeginning = StringTools.concat(whiteSpaces,
					"<annotation");

			// Adding the name spaces of the annotation element
			Map<String, String> otherNamespaces = annotation
					.getAnnotationNamespaces();

			for (String namespacePrefix : otherNamespaces.keySet()) {
				StringTools.append(annotationBeginning, " ", namespacePrefix,
						"=\"", otherNamespaces.get(namespacePrefix), "\"");
				annotationElement.addAttribute(namespacePrefix, otherNamespaces.get(namespacePrefix));
			}
						
			boolean allNamespacesDefined = true;
			
			// Adding the name spaces of the sbml element
			if (sbase.getSBMLDocument() != null) {
				Map<String, String> sbmlDocumentNamespaces = sbase.getSBMLDocument().getSBMLDocumentNamespaces();

				for (String namespacePrefix : sbmlDocumentNamespaces.keySet()) {
					
					// Checking if the namespace declaration is not done twice 
					// in the SBMLDocument and the annotation element.
					if (otherNamespaces.get(namespacePrefix) == null) {
						StringTools.append(annotationBeginning, " ", namespacePrefix,
								"=\"", sbmlDocumentNamespaces.get(namespacePrefix), "\"");
					}
				}

			} else {				
				// Can happen when displaying the annotation from an SBase object
				// that is not  yet linked to a SBMLDocument.
				allNamespacesDefined = false;
			}
			// TODO: to be able to write broken annotations where the namespace declaration is missing
			// we need to add dummy namespace here !! Or remove the namespace awareness on the writer !!
				
			StringTools.append(annotationBeginning, Character.valueOf('>'),
					Character.valueOf('\n'), annotation.getNonRDFannotation(),
					whiteSpaces, "</annotation>", Character.valueOf('\n'));

			DOMConverter converter = new DOMConverter();
			
			String annotationString = annotationBeginning.toString()
					.replaceAll("&", "&amp;");
			// here indent gets lost.
			Document domDocument = null;
			boolean domConversionDone = false;

			try {
				domDocument = JAXPFacade.getInstance().create(
						new BufferedReader(new StringReader(annotationString)),	allNamespacesDefined);
				domConversionDone = true;
			} catch (SAXException e) {
				e.printStackTrace();
				// TODO: log error or send SBMLException

				logger.warn("Cannot parse the following XML\n@" + annotationString + "@");
				logger.warn("NonRDFannotation =\n@" + annotation.getNonRDFannotation() + "@");
				
				// trying to read the XML without namespace awareness as the XML can be wrong and we still want to
				// write it back as it is.
				if (allNamespacesDefined) {
					
					try {
						domDocument = JAXPFacade.getInstance().create(
								new BufferedReader(new StringReader(annotationString)),	false);
						domConversionDone = true;
					} catch (SAXException e2) {
						e.printStackTrace();
						// TODO: log error or send SBMLException
					}
				}
			} finally {
				if (domConversionDone) {	
					converter.writeFragment(domDocument.getFirstChild()
							.getChildNodes(), writer);
				}
			}
		} else {
			writer.writeCharacters("\n");
		}

		// if the given SBase is not a model and the level is smaller than 3,
		// no history can be written.
		// Annotation cannot be written without metaid tag.

		if (sbase.getAnnotation().isSetRDFannotation()) {
			if (sbase.isSetMetaId()) {
				if (!annotation.isSetAbout()) {
					// add required missing tag
					annotation.setAbout('#' + sbase.getMetaId());
				}
				writeRDFAnnotation(annotation, annotationElement, writer, indent
						+ indentCount);
			} else {
				/*
				 * This shouldn't actually happen because a metaid will be created
				 * if missing in AbstractSBase. Only if no SBMLDocument is available,
				 * or the SBase is derived from a different SBase implementation, it 
				 * could fail.
				 */
				logger.info(MessageFormat.format(
						"Could not write RDF annotation of {0} due to missing metaid.",
						sbase.getElementName()));
			}
		}
		
		// set the indentation for the closing tag
		element.setIndentation(whiteSpaces, indent + indentCount, indentCount);
	}

	/**
	 * Writes the listOfCVTerms.
	 * 
	 * @param listOfCVTerms
	 *          the list of CVTerms to write
	 * @param rdfNamespaces
	 *          the RDF name spaces and prefixes
	 * @param writer
	 *          the XMLStreamWriter2
	 * @param indent
	 * @throws XMLStreamException
	 */
	private void writeCVTerms(List<CVTerm> listOfCVTerms,
			Map<String, String> rdfNamespaces, XMLStreamWriter writer,
			int indent) throws XMLStreamException 
	{
		if (listOfCVTerms == null || listOfCVTerms.size() == 0) {
			return;
		}
		
		String rdfPrefix = rdfNamespaces.get(Annotation.URI_RDF_SYNTAX_NS);
		String whiteSpace = createIndentationString(indent);

		for (int i = 0; i < listOfCVTerms.size(); i++) 
		{
			CVTerm cvTerm = listOfCVTerms.get(i);
			String namespaceURI = null;
			String prefix = null;
			String elementName = null;
		
			if (cvTerm == null || cvTerm.getResourceCount() == 0) 
			{
				// No need to write a CVTerm without any resources/uris
				continue;
			}
			
			if (cvTerm.getQualifierType().equals(CVTerm.Type.BIOLOGICAL_QUALIFIER)) {
				namespaceURI = CVTerm.Type.BIOLOGICAL_QUALIFIER.getNamespaceURI();
				prefix = rdfNamespaces.get(namespaceURI);
				elementName = cvTerm.getBiologicalQualifierType()
						.getElementNameEquivalent();
			} else if (cvTerm.getQualifierType().equals(CVTerm.Type.MODEL_QUALIFIER)) {
				namespaceURI = cvTerm.getQualifierType().getNamespaceURI();
				prefix = rdfNamespaces.get(namespaceURI);
				elementName = Annotation
						.getElementNameEquivalentToQualifier(cvTerm
								.getModelQualifierType());
			}
			if ((namespaceURI != null) && (elementName != null)
					&& (prefix != null)) 
			{
				writer.writeCharacters(whiteSpace + createIndentationString(indentCount));
				writer.writeStartElement(prefix, elementName, namespaceURI);
				writer.writeCharacters("\n");
				
				if (cvTerm.getResourceCount() > 0) {
					writer.writeCharacters(whiteSpace + createIndentationString(2 * indentCount));
					writer.writeStartElement(rdfPrefix, "Bag",
							Annotation.URI_RDF_SYNTAX_NS);
					writer.writeCharacters("\n");
					
					for (int j = 0; j < cvTerm.getResourceCount(); j++) {
						writer.writeCharacters(whiteSpace + createIndentationString(3 * indentCount));
						writer.writeStartElement(rdfPrefix, "li",
								Annotation.URI_RDF_SYNTAX_NS);
						writer.writeAttribute(rdfPrefix,
								Annotation.URI_RDF_SYNTAX_NS, "resource",
								cvTerm.getResourceURI(j));
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}
					writer.writeCharacters(whiteSpace + createIndentationString(2 * indentCount));
					writer.writeEndElement();
					writer.writeCharacters("\n");
					
				} else { // cvTerm.getReourceCount() == 0
					
					writer.writeCharacters(whiteSpace + createIndentationString(2 * indentCount));
					writer.writeEmptyElement(rdfPrefix, "Bag", Annotation.URI_RDF_SYNTAX_NS);
					writer.writeCharacters("\n");
				}				

				writer.writeCharacters(whiteSpace + createIndentationString(indentCount));
				writer.writeEndElement();
				writer.writeCharacters("\n");
			}
		}
	}


	/**
	 * Writes the history represented by this History instance.
	 * 
	 * @param history
	 *          the history to write
	 * @param rdfNamespaces
	 *          contains the RDF namespaces and their prefixes.
	 * @param writer
	 *          the XMLStreamWriter2
	 * @param indent
	 * @throws XMLStreamException
	 */
	private void writeHistory(History history,
			Map<String, String> rdfNamespaces, XMLStreamWriter writer,
			int indent) throws XMLStreamException {
		// Logger logger = Logger.getLogger(SBMLWriter.class);

		String whiteSpace = createIndentationString(indent);
		String rdfPrefix = rdfNamespaces.get(Annotation.URI_RDF_SYNTAX_NS);
		if (history.getCreatorCount() > 0) {
			String creatorPrefix = rdfNamespaces.get(JSBML.URI_PURL_ELEMENTS);
			writer.writeCharacters(whiteSpace);
			writer.writeStartElement(creatorPrefix, "creator",
					JSBML.URI_PURL_ELEMENTS);
			writer.writeCharacters("\n");
			writer.writeCharacters(whiteSpace + createIndentationString(indentCount));
			writer.writeStartElement(rdfPrefix, "Bag",
					Annotation.URI_RDF_SYNTAX_NS);
			writer.writeCharacters("\n");

			for (int i = 0; i < history.getCreatorCount(); i++) {
				Creator creator = history.getCreator(i);
				writer.writeCharacters(whiteSpace + createIndentationString(2 * indentCount));
				writer.writeStartElement(rdfPrefix, "li",
						Annotation.URI_RDF_SYNTAX_NS);
				writer.writeAttribute(rdfPrefix, Annotation.URI_RDF_SYNTAX_NS,
						"parseType", "Resource");
				String vCardPrefix = rdfNamespaces.get(Creator.URI_RDF_VCARD_NS);

				if (creator.isSetFamilyName()
						|| creator.isSetGivenName()) {
					writer.writeCharacters("\n");
					writer.writeCharacters(whiteSpace + createIndentationString(3 * indentCount));
					writer.writeStartElement(vCardPrefix, "N",
							Creator.URI_RDF_VCARD_NS);
					writer.writeAttribute(Annotation.URI_RDF_SYNTAX_NS,
							"parseType", "Resource");
					writer.writeCharacters("\n");

					if (creator.isSetFamilyName()) {
						writer.writeCharacters(whiteSpace + createIndentationString(4 * indentCount));
						writer.writeStartElement(vCardPrefix, "Family",
								Creator.URI_RDF_VCARD_NS);
						writer.writeCharacters(creator.getFamilyName());
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}
					if (creator.isSetGivenName()) {
						writer.writeCharacters(whiteSpace + createIndentationString(4 * indentCount));
						writer.writeStartElement(vCardPrefix, "Given",
								Creator.URI_RDF_VCARD_NS);
						writer.writeCharacters(creator.getGivenName());
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}
					writer.writeCharacters(whiteSpace + createIndentationString(3 * indentCount));
					writer.writeEndElement();
				} 
				writer.writeCharacters("\n");

				if (creator.isSetEmail()) {
					writer.writeCharacters(whiteSpace + createIndentationString(3 * indentCount));
					writer.writeStartElement(vCardPrefix, "EMAIL",
							Creator.URI_RDF_VCARD_NS);
					writer.writeCharacters(creator.getEmail());
					writer.writeEndElement();
					writer.writeCharacters("\n");
				}
				if (creator.isSetOrganisation()) {
					writer.writeCharacters(whiteSpace + createIndentationString(3 * indentCount));
					writer.writeStartElement(vCardPrefix, "ORG",
							Creator.URI_RDF_VCARD_NS);
					writer.writeAttribute(rdfPrefix,
							Annotation.URI_RDF_SYNTAX_NS, "parseType",
							"Resource");
					writer.writeCharacters("\n");
					writer.writeCharacters(whiteSpace + createIndentationString(4 * indentCount));
					writer.writeStartElement(vCardPrefix, "Orgname",
							Creator.URI_RDF_VCARD_NS);
					writer.writeCharacters(creator.getOrganisation());
					writer.writeEndElement();
					writer.writeCharacters("\n");
					writer.writeCharacters(whiteSpace + createIndentationString(3 * indentCount));
					writer.writeEndElement();
					writer.writeCharacters("\n");
				}
				// adding any additional element/value
				if (creator.isSetOtherAttributes()) {
					for (String elementName : creator.getOtherAttributes().keySet()) {
						String characters = creator.getOtherAttribute(elementName);
						
						writer.writeCharacters(whiteSpace + createIndentationString(3 * indentCount));
						writer.writeStartElement(vCardPrefix, elementName, Creator.URI_RDF_VCARD_NS);
						writer.writeCharacters(characters);
						writer.writeEndElement();
						writer.writeCharacters("\n");
					}
				}
				
				writer.writeCharacters(whiteSpace + createIndentationString(2* indentCount));
				writer.writeEndElement();
				writer.writeCharacters("\n");
			}
			writer.writeCharacters(whiteSpace + createIndentationString(indentCount));
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.writeCharacters(whiteSpace);
			writer.writeEndElement();
			writer.writeCharacters("\n");
		}

		String dctermPrefix = rdfNamespaces.get(JSBML.URI_PURL_TERMS);
		String creationDate = DateParser.getIsoDateNoMillis(new Date());
		String now = creationDate;
		boolean writeCreationDate = false;
		boolean isModelHistory = history.getParent().getParent() instanceof Model;
		
		if (history.isSetCreatedDate()) {
			creationDate = DateParser.getIsoDateNoMillis(history.getCreatedDate());
			writeCreationDate = true;
		} else if (isModelHistory) { // We need to add a creation date
			writeCreationDate = true;
		}
		
		if (writeCreationDate) {
			writeW3CDate(writer, indent, creationDate, "created", dctermPrefix,
					rdfPrefix);
		}

		// Writing the current modified dates.
		if (history.isSetModifiedDate()) {
			for (int i = 0; i < history.getModifiedDateCount(); i++) {
				writeW3CDate(writer, indent,
						DateParser.getIsoDateNoMillis(history
								.getModifiedDate(i)), "modified", dctermPrefix,
						rdfPrefix);
			}
		}
		if (isModelHistory) {
			// We need to add a new modified date
			writeW3CDate(writer, indent, now, "modified", dctermPrefix, rdfPrefix);
		}
	}

	/**
	 * Writes the MathML expression for the math element of this sbase
	 * component.
	 * 
	 * @param m
	 *          the sbase component
	 * @param element
	 *          the matching SMOutputElement
	 * @param writer
	 *          the XMLStreamWriter
	 * @param indent
	 * @throws XMLStreamException
	 * 
	 */
	private void writeMathML(MathContainer m, SMOutputElement element,
			XMLStreamWriter writer, int indent) throws XMLStreamException 
	{
		if (m.isSetMath()) {

			String whitespaces = createIndentationString(indent);
			element.addCharacters("\n");
			// set the indentation for the math opening tag			
			element.setIndentation(whitespaces, indent + indentCount, indentCount);

			// Creating an SMOutputElement to be sure that the previous nested element tag is closed properly.
			SMNamespace mathMLNamespace = element.getNamespace(ASTNode.URI_MATHML_DEFINITION, ASTNode.URI_MATHML_PREFIX);
			SMOutputElement mathElement = element.addElement(mathMLNamespace, "math");
			MathMLXMLStreamCompiler compiler = new MathMLXMLStreamCompiler(
					writer, createIndentationString(indent + indentCount));
			boolean isSBMLNamespaceNeeded = compiler.isSBMLNamespaceNeeded(m.getMath());

			// TODO: add all other namespaces !!

			if (isSBMLNamespaceNeeded) {
				// writing the SBML namespace
				SBMLDocument doc = null;
				SBase sbase = m.getMath().getParentSBMLObject();
				String sbmlNamespace = SBMLDocument.URI_NAMESPACE_L3V1Core;
				
				if (sbase != null) {
					doc = sbase.getSBMLDocument();
					sbmlNamespace = doc.getSBMLDocumentNamespaces().get("xmlns");
					
					if (sbmlNamespace == null) {
						logger.warn("writeMathML: the SBML namespace of this SBMLDocument" +
								" could not be found, using the default namespace (" +
								SBMLDocument.URI_NAMESPACE_L3V1Core + ") instead.");
						sbmlNamespace = SBMLDocument.URI_NAMESPACE_L3V1Core;
					}
				}
				writer.writeNamespace("sbml", sbmlNamespace);
			}
			
			mathElement.setIndentation(createIndentationString(indent + 2), indent + indentCount, indentCount);
			
			writer.writeCharacters(whitespaces);
			writer.writeCharacters("\n");

			compiler.compile(m.getMath());

			writer.writeCharacters(whitespaces);
		}
	}

	/**
	 * Writes the message of this Constraint.
	 * 
	 * @param sbase
	 *          the Constraint component
	 * @param element
	 *          the matching SMOUtputElement
	 * @param writer
	 *          the XMLStreamWriter2
	 * @param sbmlNamespace
	 *          the SBML namespace
	 * @throws XMLStreamException
	 */
	private void writeMessage(Constraint sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace, int indent)
			throws XMLStreamException 
	{
	
		String whitespaces = createIndentationString(indent);
		element.addCharacters("\n");
		// set the indentation for the math opening tag			
		element.setIndentation(whitespaces, indent + indentCount, indentCount);

		// Creating an SMOutputElement to be sure that the previous nested element tag is closed properly.
		SMNamespace sbmlSMNamespace = element.getNamespace();
		SMOutputElement messageElement = element.addElement(sbmlSMNamespace, "message");
		messageElement.setIndentation(createIndentationString(indent + 2), indent + indentCount, indentCount);
		
		writer.writeCharacters(whitespaces);
		writer.writeCharacters("\n");
		
		XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, indent,
				indentCount, indentChar);
		xmlNodeWriter.write(sbase.getMessage());
		
		writer.writeCharacters(whitespaces);
	}

	/**
	 * Writes the notes of this sbase component.
	 * 
	 * @param sbase
	 *          the SBase component
	 * @param element
	 *          the matching SMOUtputElement
	 * @param writer
	 *          the XMLStreamWriter2
	 * @param sbmlNamespace
	 *          the SBML namespace
	 * @param indent
	 * @throws XMLStreamException
	 */
	private void writeNotes(SBase sbase, SMOutputElement element,
			XMLStreamWriter writer, String sbmlNamespace, int indent)
			throws XMLStreamException {
		writer.writeCharacters("\n");
    XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, indent,
      indentCount, indentChar);
		xmlNodeWriter.write(sbase.getNotes());
	}

	/**
	 * Writes the RDF annotation contained in 'annotation'.
	 * 
	 * @param annotation
	 *          the annotation to write
	 * @param annotationElement
	 *          the matching SMOutputElement
	 * @param writer
	 *          the XMLStreamWriter.
	 * @param indent
	 * @throws XMLStreamException
	 */
	private void writeRDFAnnotation(Annotation annotation,
			SMOutputElement annotationElement, XMLStreamWriter writer,
			int indent) throws XMLStreamException {
		// Logger logger = Logger.getLogger(SBMLWriter.class);

		String whiteSpace = createIndentationString(indent);
		SMNamespace namespace = annotationElement.getNamespace(
				Annotation.URI_RDF_SYNTAX_NS, "rdf");
		annotationElement.setIndentation(whiteSpace, indent, indentCount);
		SMOutputElement rdfElement = annotationElement.addElement(namespace,
				"RDF");

		/*
		 * TODO: Check which name spaces are really required and add only those;
		 * particularly, if name spaces are missing and it is known from the
		 * kind of RDF annotation, which name spaces are needed, these should be
		 * added automatically here.
		 */
		Map<String, String> rdfNamespaces = annotation.getRDFAnnotationNamespaces();

		for (String namespaceURI : rdfNamespaces.keySet()) {
			
			if (!namespaceURI.equals(namespace.getURI())) {
				writer.writeNamespace(rdfNamespaces.get(namespaceURI), namespaceURI);
			}
		}

		// Checking if all the necessary namespaces are defined 
		// TODO: In fact, we could remove the rdfNamespaces map ?

		if (rdfNamespaces.get(Annotation.URI_RDF_SYNTAX_NS) == null 
				|| rdfNamespaces.get(Annotation.URI_RDF_SYNTAX_NS).equals("xmlns")) 
		{
			// writer.writeNamespace("rdf", Annotation.URI_RDF_SYNTAX_NS); // already registered previously
			rdfNamespaces.put(Annotation.URI_RDF_SYNTAX_NS, "rdf");
		}

		if (annotation.isSetHistory() && annotation.getHistory().getCreatorCount() > 0) 
		{
			if (rdfNamespaces.get(JSBML.URI_PURL_ELEMENTS) == null) {
				writer.writeNamespace("dc", JSBML.URI_PURL_ELEMENTS);
				rdfNamespaces.put(JSBML.URI_PURL_ELEMENTS, "dc");
			}

			if (rdfNamespaces.get(Creator.URI_RDF_VCARD_NS) == null) {
				writer.writeNamespace("vCard", Creator.URI_RDF_VCARD_NS);
				rdfNamespaces.put(Creator.URI_RDF_VCARD_NS, "vCard");
			}
		}
		
		if (annotation.isSetHistory() && rdfNamespaces.get(JSBML.URI_PURL_TERMS) == null)
		{
			boolean isModelHistory = annotation.getParent() instanceof Model;
			
			if (isModelHistory || (annotation.getHistory().isSetCreatedDate() || annotation.getHistory().isSetModifiedDate())) {
				writer.writeNamespace("dcterms", JSBML.URI_PURL_TERMS);
				rdfNamespaces.put(JSBML.URI_PURL_TERMS, "dcterms");
			}			
		}
		
		if (annotation.getCVTermCount() > 0) 
		{
			if (rdfNamespaces.get(CVTerm.URI_BIOMODELS_NET_BIOLOGY_QUALIFIERS) == null) {
				writer.writeNamespace("bqbiol", CVTerm.URI_BIOMODELS_NET_BIOLOGY_QUALIFIERS);
				rdfNamespaces.put(CVTerm.URI_BIOMODELS_NET_BIOLOGY_QUALIFIERS, "bqbiol");
			}

			if (rdfNamespaces.get(CVTerm.URI_BIOMODELS_NET_MODEL_QUALIFIERS) == null) {
				writer.writeNamespace("bqmodel", CVTerm.URI_BIOMODELS_NET_MODEL_QUALIFIERS);
				rdfNamespaces.put(CVTerm.URI_BIOMODELS_NET_MODEL_QUALIFIERS, "bqmodel");
			}
			
		}
		
		
		rdfElement.addCharacters("\n");
		rdfElement.setIndentation(whiteSpace + createIndentationString(indentCount), indent + indentCount, indentCount);
		SMOutputElement descriptionElement = rdfElement.addElement(namespace,
				"Description");
		descriptionElement.addAttribute(namespace, "about",
				annotation.getAbout());
		descriptionElement.addCharacters("\n");
		if (annotation.isSetHistory()) {
			writeHistory(annotation.getHistory(), rdfNamespaces, writer,
					indent + 4);
		}
		if (annotation.getListOfCVTerms().size() > 0) {
			writeCVTerms(annotation.getListOfCVTerms(), rdfNamespaces, writer,
					indent + indentCount);
		}
		descriptionElement.setIndentation(whiteSpace + createIndentationString(indentCount), indent + indentCount, indentCount);
		descriptionElement.addCharacters(whiteSpace + createIndentationString(indentCount));
		annotationElement.setIndentation(whiteSpace, indent, indentCount);
		rdfElement.addCharacters("\n");
		rdfElement.addCharacters(whiteSpace);
		annotationElement.addCharacters("\n");
	}
	
	/**
	 * Writes the SBML elements.
	 * 
	 * @param parentXmlObject
	 *          contains the XML information of the parentElement.
	 * @param smOutputParentElement
	 *          SMOutputElement of the parentElement.
	 * @param streamWriter
	 * @param objectToWrite
	 *          the Object to write.
	 * @param notesParser
	 *          the WritingParser to parse the notes.
	 * @param MathMLparser
	 *          the WritingParser to parse the MathML expressions.
	 * @param indent
	 *            The number of white spaces to indent this element.
	 * @throws XMLStreamException
	 * @throws SBMLException
	 * @throws SAXException
	 */
	private void writeSBMLElements(SBMLObjectForXML parentXmlObject,
			SMOutputElement smOutputParentElement,
			XMLStreamWriter streamWriter, Object objectToWrite, int indent)
			throws XMLStreamException, SBMLException {
		
		String whiteSpaces = createIndentationString(indent);

		// Get the list of parsers to use.
		List<WritingParser> listOfPackages = getWritingParsers(
				objectToWrite, smOutputParentElement.getNamespace().getURI());

		if (logger.isDebugEnabled()) {
			logger.debug("writeSBMLElements: xmlObject = " + parentXmlObject);
			logger.debug("writeSBMLElements: parentElement = "
					+ smOutputParentElement.getLocalName() + ", "
					+ smOutputParentElement.getNamespace().getURI());
			logger.debug("writeSBMLElements: objectToWrite = "	+ objectToWrite + '\n');
			logger.debug("writeSBMLElements: listOfPackages = " + listOfPackages + '\n');
		}
		
		Iterator<WritingParser> iterator = listOfPackages.iterator();
		while (iterator.hasNext()) {
			WritingParser parser = iterator.next();
			List<Object> sbmlElementsToWrite = parser
					.getListOfSBMLElementsToWrite(objectToWrite);

			if (logger.isDebugEnabled()) {
				logger.debug("writeSBMLElements: parser = " + parser);
				logger.debug("writeSBMLElements: elementsToWrite = " + sbmlElementsToWrite);
			}
			
			if (sbmlElementsToWrite == null) {
				// TODO: test if there are some characters to write ?
				
				// to allow the XML parser to prune empty elements, this indent should not be added.
				// streamWriter.writeCharacters(whiteSpaces.substring(0,
				// 		indent - indentCount));
			} else {
				for (int i = 0; i < sbmlElementsToWrite.size(); i++) {
					Object nextObjectToWrite = sbmlElementsToWrite.get(i);
					boolean elementIsNested = false;

					/*
					 * Skip predefined UnitDefinitions (check depending on Level
					 * and Version).
					 */
					if (nextObjectToWrite instanceof ListOf<?>) {
						ListOf<?> list = (ListOf<?>) nextObjectToWrite;
						if (list.size() > 0) {
							SBase sb = list.getFirst();
							if ((sb instanceof UnitDefinition) && (parser
											.getListOfSBMLElementsToWrite(nextObjectToWrite) == null)) {
								streamWriter.writeCharacters(whiteSpaces.substring(0, indent - indentCount));
								continue;
							}
						} else {
							streamWriter.writeCharacters(whiteSpaces.substring(0, indent - indentCount));
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
						
						if (toTest.size() > 0) {
							elementIsNested = true;
						}
						
						Type listType = toTest.getSBaseListType();
						if (listType == Type.none) {
							// Prevent writing invalid SBML if list types are
							// not set appropriately.
							throw new SBMLException(MessageFormat.format(
									"Unknown ListOf type \"{0}\".",
									toTest.getElementName()));
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
					
					// Writing the element, starting by the indent
					streamWriter.writeCharacters(whiteSpaces);
					parser.writeElement(parentXmlObject, nextObjectToWrite);
					parser.writeNamespaces(parentXmlObject, nextObjectToWrite);
					parser.writeAttributes(parentXmlObject, nextObjectToWrite);
					
					
					SMOutputElement newOutPutElement = null;
					if (parentXmlObject.isSetName()) {
						boolean isClosedMathContainer = false, isClosedAnnotation = false;
						
						// TODO: problem here as a children does not have the same namespace as his parent all the time !!
						// use ((SBase) nextObjectToWrite).getNamespaces(); ??
						
						if (parentXmlObject.isSetNamespace()) {
							SMNamespace namespaceContext = smOutputParentElement
									.getNamespace(
											parentXmlObject.getNamespace(),
											parentXmlObject.getPrefix());
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
							newOutPutElement.addAttribute(entry.getKey(),
									entry.getValue());
						}
						if (nextObjectToWrite instanceof SBase) {
							SBase s = (SBase) nextObjectToWrite;
							if (s.isSetNotes()) {
								writeNotes(s, newOutPutElement, streamWriter,
										newOutPutElement.getNamespace()
												.getURI(), indent + indentCount);
								elementIsNested = true;
							}
							if (s.isSetAnnotation()) {
								writeAnnotation(s, newOutPutElement,
										streamWriter, 
										indent + indentCount, false);
								elementIsNested = isClosedAnnotation = true;
							}
							if (s.getChildCount() > 0) {
								// make sure that we'll have line breaks if an element has any sub elements.
								elementIsNested = true;
							}
						}
						if (nextObjectToWrite instanceof MathContainer) {
							MathContainer mathContainer = (MathContainer) nextObjectToWrite;
							if (mathContainer.getLevel() > 1) {
								writeMathML(mathContainer, newOutPutElement,
										streamWriter, indent + indentCount);
								elementIsNested = true;
							} else {
								elementIsNested = false;
							}
							isClosedMathContainer = true;
						}
						if (nextObjectToWrite instanceof Constraint) {
							Constraint constraint = (Constraint) nextObjectToWrite;
							if (constraint.isSetMessage()) {
								writeMessage(constraint, newOutPutElement,
										streamWriter, newOutPutElement
												.getNamespace().getURI(),
										indent + indentCount);
								elementIsNested = true;
							}
						}
						if (!elementIsNested
								&& ((nextObjectToWrite instanceof Model) || (nextObjectToWrite instanceof UnitDefinition))) {
							elementIsNested = true;
						}
						
						// to allow the XML parser to prune empty element, this line should not be added in all the cases.
						if (elementIsNested) {
							newOutPutElement.addCharacters("\n");
							if (isClosedMathContainer || isClosedAnnotation) {
								newOutPutElement.addCharacters(whiteSpaces);
							}
						}

						writeSBMLElements(parentXmlObject, newOutPutElement,
								streamWriter, nextObjectToWrite, indent + indentCount);
						smOutputParentElement.addCharacters("\n");
					}
				}
				// write the indent before closing the element
				streamWriter.writeCharacters(whiteSpaces.substring(0,
						indent - indentCount));
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
	public String writeSBMLToString(SBMLDocument doc)
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
	public String writeSBMLToString(SBMLDocument d, String programName,
			String programVersion) throws XMLStreamException, SBMLException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		write(d, stream, programName, programVersion);
		return stream.toString();
	}

	/**
	 * 
	 * @param writer
	 * @param indent
	 * @param dateISO
	 * @param dcterm
	 * @param dctermPrefix
	 * @param rdfPrefix
	 * @throws XMLStreamException
	 */
	private void writeW3CDate(XMLStreamWriter writer, int indent,
			String dateISO, String dcterm, String dctermPrefix, String rdfPrefix)
			throws XMLStreamException {
		String whiteSpace = createIndentationString(indent);

		writer.writeCharacters(whiteSpace);
		writer.writeStartElement(dctermPrefix, dcterm, JSBML.URI_PURL_TERMS);
		writer.writeAttribute(rdfPrefix, Annotation.URI_RDF_SYNTAX_NS,
				"parseType", "Resource");
		writer.writeCharacters("\n");
		writer.writeCharacters(whiteSpace + createIndentationString(indentCount));
		writer.writeStartElement(dctermPrefix, "W3CDTF", JSBML.URI_PURL_TERMS);
		writer.writeCharacters(dateISO);
		writer.writeEndElement();
		writer.writeCharacters("\n");
		writer.writeCharacters(whiteSpace);
		writer.writeEndElement();
		writer.writeCharacters("\n");
	}
	
	// TODO: test a bit more Xstream and using Qname to see how it
	// can deal with math or rdf bloc

}
