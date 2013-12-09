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

package org.sbml.jsbml.xml.stax;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.staxmate.SMOutputFactory;
import org.codehaus.staxmate.out.SMNamespace;
import org.codehaus.staxmate.out.SMOutputContainer;
import org.codehaus.staxmate.out.SMOutputContext;
import org.codehaus.staxmate.out.SMOutputDocument;
import org.codehaus.staxmate.out.SMOutputElement;
import org.codehaus.staxmate.out.SMRootFragment;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.compilers.MathMLXMLStreamCompiler;
import org.sbml.jsbml.xml.parsers.AnnotationWriter;
import org.sbml.jsbml.xml.parsers.WritingParser;
import org.sbml.jsbml.xml.parsers.XMLNodeWriter;
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

		File argsAsFile = new File(args[0]);
		File[] files = null;

		if (argsAsFile.isDirectory())
		{
			files = argsAsFile.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) 
				{
					if (pathname.getName().contains("-jsbml"))
					{
						return false;
					}
					
					if (pathname.getName().endsWith(".xml"))
					{
						return true;
					}
					
					return false;
				}
			});
		}
		else
		{
			files = new File[1];
			files[0] = argsAsFile;
		}

		for (File file : files) 
		{
			long init = Calendar.getInstance().getTimeInMillis();
			System.out.println(Calendar.getInstance().getTime());

			String fileName = file.getAbsolutePath();
			String jsbmlWriteFileName = fileName.replaceFirst(".xml", "-jsbml.xml");

			System.out.printf("Reading %s and writing %s\n", 
					fileName, jsbmlWriteFileName);

			long afterRead = 0;
			SBMLDocument testDocument = null;
			try {
				testDocument = new SBMLReader().readSBMLFile(fileName);
				System.out.printf("Reading done\n");
				System.out.println(Calendar.getInstance().getTime());
				afterRead = Calendar.getInstance().getTimeInMillis();

				// testDocument.checkConsistency(); 
				// System.out.println(XMLNode.convertXMLNodeToString(testDocument.getModel().getAnnotation().getNonRDFannotation()));			

				System.out.printf("Starting writing\n");

				new SBMLWriter().write(testDocument, jsbmlWriteFileName);
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
		/*
		for (Species species : testDocument.getModel().getListOfSpecies())
		{
			if (species.getCVTermCount() > 0)
			{
				System.out.println("Species '" + species.getId() + "' has " + species.getCVTermCount() + " annotations");
				int i = 0;
				for (CVTerm cvTerm : species.getCVTerms())
				{
					i += cvTerm.getResourceCount();
				}
				System.out.println("\t" + i + " uris found. \n");
			}
		}
		*/
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

	private List<AnnotationWriter> annotationParsers = new ArrayList<AnnotationWriter>();

	/**
	 * Remember already issued warnings to avoid having multiple lines, saying
	 * the same thing (Warning: Skipping detailed parsing of name space 'XYZ'.
	 * No parser available.)
	 */
	private transient List<String> issuedWarnings = new ArrayList<String>();

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
	private void addWritingParser(List<WritingParser> sbmlParsers,
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
	 * @param parentNamespace
	 * @return all the writing parsers necessary to write this element.
	 */
	private List<WritingParser> getWritingParsers(Object object, String parentNamespace) {
		
		Set<String> packageNamespaces = null;

		// logger.debug("getInitializedParsers: name space, object = " + name space + ", " + object);

		if (object instanceof SBase) {
			SBase sbase = (SBase) object;
			packageNamespaces = new TreeSet<String>();

			for (String sbaseNamespace : sbase.getNamespaces()) {
				if (!packageNamespaces.contains(sbaseNamespace)) {
					packageNamespaces.add(sbaseNamespace);
				}
			}

		} else if (object instanceof Annotation) {
			packageNamespaces = new TreeSet<String>();
		} else {
			logger.warn("getInitializedParsers: I don't know what to do with " + object);
		}
		
		List<WritingParser> sbmlParsers = new ArrayList<WritingParser>();
		
		if (packageNamespaces != null && packageNamespaces.size() > 0) {
			
			Iterator<String> iterator = packageNamespaces.iterator();
			
			while (iterator.hasNext()) {
				String packageNamespace = iterator.next();
				WritingParser sbmlParser = instantiatedSBMLParsers.get(packageNamespace);
				addWritingParser(sbmlParsers, sbmlParser, packageNamespace);
			}
			
		} 
		else // if an object as no namespaces associated, we use the parent namespace 
		{
			WritingParser sbmlParser = instantiatedSBMLParsers.get(parentNamespace);
			addWritingParser(sbmlParsers, sbmlParser, parentNamespace);
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
	 * Initializes the AnnotationWriter {@link HashMap} of this class.
	 * 
	 */
	public void initializeAnnotationParsers() {
		
		// TODO - make use of the java6 annotation to know which annotationParsers to initialize
		
		// to prevent several call to this method
		annotationParsers.clear();
		
		Map<String, Class<? extends AnnotationWriter>> annotationParserClasses = new HashMap<String, Class<? extends AnnotationWriter>>();

		JSBML.loadClasses("org/sbml/jsbml/resources/cfg/annotationParsers.xml", annotationParserClasses);
		
		for (Class<? extends AnnotationWriter> annotationWriterClass : annotationParserClasses.values()) {
			try {
				annotationParsers.add(annotationWriterClass.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Allows you to set another blank character for indentation. Allowed are
	 * only tabs and white spaces, i.e., {@code '\t'} and {@code ' '}.
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
		initializeAnnotationParsers();

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
		if (sbmlDocument.getDeclaredNamespaces().size() > 0) {
					
			logger.debug(" SBML declared namespaces size = "
					+ sbmlDocument.getDeclaredNamespaces().size());

			for (String prefix : sbmlDocument.getDeclaredNamespaces().keySet()) {

				if (!prefix.equals("xmlns")) {

					String namespaceURI = sbmlDocument.getDeclaredNamespaces().get(prefix);

					logger.debug(" SBML name spaces: " + prefix + " = " + namespaceURI);

					String namespacePrefix = prefix.substring(prefix.indexOf(":") + 1);

					streamWriter.setPrefix(namespacePrefix, namespaceURI);
					xmlObject.getAttributes().put(prefix, namespaceURI);
					
					logger.debug(" SBML namespaces: " + namespacePrefix + " = " + namespaceURI);
				}
			}
		}

		Iterator<Map.Entry<String, String>> it = xmlObject.getAttributes().entrySet().iterator();
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
		throws XMLStreamException, SBMLException 
	{	  
		// calling the annotation parsers so that they update the XMLNode before writing it
		// TODO - should be done in the Annotation class on methods that return the whole XMLNode
		for (AnnotationWriter annoWriter : annotationParsers) {
			annoWriter.writeAnnotation(sbase);
		}
		
		writer.writeCharacters("\n");
		XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, indent,
				indentCount, indentChar);
		xmlNodeWriter.write(sbase.getAnnotation().getNonRDFannotation());

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
					sbmlNamespace = doc.getDeclaredNamespaces().get("xmlns");
					
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
			throws XMLStreamException 
	{
		writer.writeCharacters("\n");
    XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, indent,
      indentCount, indentChar);
		xmlNodeWriter.write(sbase.getNotes());
	}


	/**
	 * Writes the SBML elements.
	 * 
	 * @param parentXmlObject
	 *          contains the XML information of the parentElement.
	 * @param smOutputParentElement
	 *          {@link SMOutputElement} of the parentElement.
	 * @param streamWriter
	 * @param parentObject
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
			XMLStreamWriter streamWriter, Object parentObject, int indent)
			throws XMLStreamException, SBMLException 
	{		
		String whiteSpaces = createIndentationString(indent);

		// Get the list of parsers to use.
		List<WritingParser> listOfPackages = getWritingParsers(
				parentObject, smOutputParentElement.getNamespace().getURI());

		if (listOfPackages.size() > 1) {
			logger.warn("An SBML element should only be associated with one package!");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("\nwriteSBMLElements: parentXmlObject = " + parentXmlObject);
			logger.debug("writeSBMLElements: parentElement = "
					+ smOutputParentElement.getLocalName() + ", "
					+ smOutputParentElement.getNamespace().getURI());
			logger.debug("writeSBMLElements: parentObject = "	+ parentObject + '\n');
			logger.debug("writeSBMLElements: listOfPackages = " + listOfPackages + '\n');
		}
		
		for (WritingParser parser : listOfPackages) {
			List<Object> sbmlElementsToWrite = parser.getListOfSBMLElementsToWrite(parentObject);

			if (logger.isDebugEnabled()) {
				logger.debug("writeSBMLElements: parser = " + parser);
				logger.debug("writeSBMLElements: elementsToWrite = " + sbmlElementsToWrite + "\n");
			}
			
			if (sbmlElementsToWrite == null) {
				continue;
			}

			for (Object nextObjectToWrite : sbmlElementsToWrite) 
			{
				if (! (nextObjectToWrite instanceof SBase)) 
				{
					logger.debug("Element '" + nextObjectToWrite.getClass().getSimpleName() +
					  "' ignored because it is supposed to be written elsewhere (ASTNode, XMLNode, ..) ");
					// ASTNode, Annotation, Notes, Math, ... are written directly below, at the same time as SBase at the moment
					continue;
				}
				
				// this new element might need a different writer than it's parent !!
				List<WritingParser> listOfChildPackages = getWritingParsers(nextObjectToWrite, smOutputParentElement.getNamespace().getURI());
				SBMLObjectForXML childXmlObject = new SBMLObjectForXML();
				
				boolean elementIsNested = false;

				if (listOfChildPackages.size() > 1) {
					logger.warn("An SBML element should only be associated with one package!");
				
					if (logger.isDebugEnabled()) {
						logger.debug("List of associated namespace: " + listOfChildPackages);
					}
				}
				WritingParser childParser = listOfChildPackages.get(0);
				
				if (logger.isDebugEnabled()) {
					logger.debug("writeSBMLElements: childParser = " + childParser);
					logger.debug("writeSBMLElements: element to Write = " + nextObjectToWrite.getClass().getSimpleName() + "\n");
				}

				if (isEmptyListOf(nextObjectToWrite, childParser))
				{
					streamWriter.writeCharacters(whiteSpaces.substring(0, indent));
					continue;
				}

				if (nextObjectToWrite instanceof TreeNode && ((TreeNode) nextObjectToWrite).getChildCount() > 0) 
				{
					elementIsNested = true;
				}

				// Writing the element, starting by the indent
				streamWriter.writeCharacters(whiteSpaces);
				childParser.writeElement(childXmlObject, nextObjectToWrite);
				childParser.writeNamespaces(childXmlObject, nextObjectToWrite);
				childParser.writeAttributes(childXmlObject, nextObjectToWrite);

				if (!childXmlObject.isSetName()) {
					// TODO: add a log message that this is ignored ??
					logger.debug("XML name not set, element ignored!");
					continue;
				}
				
				SMOutputElement newOutPutElement = null;
				boolean isClosedMathContainer = false, isClosedAnnotation = false;

				SMNamespace namespace = null;

				if (childXmlObject.isSetNamespace()) {
					namespace = smOutputParentElement.getNamespace(childXmlObject.getNamespace(), childXmlObject.getPrefix());
				} else {
					namespace = smOutputParentElement.getNamespace();
				}

				newOutPutElement = smOutputParentElement.addElement(namespace, childXmlObject.getName());

				// adding the attributes to the {@link SMOutputElement}
				for (String attributeName : childXmlObject.getAttributes().keySet()) 
				{
					newOutPutElement.addAttribute(attributeName, childXmlObject.getAttributes().get(attributeName));					
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

				writeSBMLElements(childXmlObject, newOutPutElement,
						streamWriter, nextObjectToWrite, indent + indentCount);
				smOutputParentElement.addCharacters("\n");
			}

			// write the indent before closing the element
			streamWriter.writeCharacters(whiteSpaces.substring(0, indent - indentCount));
		}
	}


	/**
	 * Returns {@code true} if the given {@link Object} is an empty {@link ListOf}, {@code false} otherwise.
	 * 
	 * @param object
	 * @param parser
	 * @return {@code true} if the given {@link Object} is an empty {@link ListOf}, {@code false} otherwise.
	 */
	private boolean isEmptyListOf(Object object, WritingParser parser)
	{		
		if (object instanceof ListOf<?>) 
		{
			ListOf<?> list = (ListOf<?>) object;

			if (list.isEmpty()) 
			{
				return true;
			}
		}
	
		return false;
	}

		
	/**
	 * Writes the given SBML document to an in-memory string.
	 * 
	 * @param doc
	 *            the {@code SBMLdocument}
	 * @return the XML representation of the {@code SBMLdocument} as a
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
	
}
