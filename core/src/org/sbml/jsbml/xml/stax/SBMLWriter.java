/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
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
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
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
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.parsers.PackageUtil;
import org.sbml.jsbml.xml.parsers.ParserManager;
import org.sbml.jsbml.xml.parsers.WritingParser;
import org.sbml.jsbml.xml.parsers.XMLNodeWriter;

import com.ctc.wstx.stax.WstxOutputFactory;

/**
 * A SBMLWriter provides the methods to write a SBML file.
 * 
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class SBMLWriter {

  // Commenting out this static block as setting those system properties has some unwanted side
  // effect, for example in OSGi where the properties are global
  // The fact to use directly WstxOutputFactory and WstxInputFactory when creating the parser
  // should prevent the problem that setting those properties was fixing.
  //static {
  // Making sure that we use the good XML library
  // System.setProperty("javax.xml.stream.XMLOutputFactory", "com.ctc.wstx.stax.WstxOutputFactory");
  // System.setProperty("javax.xml.stream.XMLInputFactory", "com.ctc.wstx.stax.WstxInputFactory");
  // System.setProperty("javax.xml.stream.XMLEventFactory", "com.ctc.wstx.stax.WstxEventFactory");
  //}

  /**
   * Returns the default symbol to be used to indent new elements in the XML
   *         representation of SBML data structures.
   * 
   * @return the default symbol to be used to indent new elements in the XML
   *         representation of SBML data structures.
   */
  public static char getDefaultIndentChar() {
    return ' ';
  }

  /**
   * Returns the default number of indent symbols to be concatenated at the
   *         beginning of a new block in an XML representation of SBML data
   *         structures.
   * 
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
      String jsbmlWriteFileName = fileName.replaceFirst("\\.xml", "-jsbml.xml");

      System.out.printf("Reading %s and writing %s\n",
        fileName, jsbmlWriteFileName);

      long afterRead = 0;
      SBMLDocument testDocument = null;
      try {
        testDocument = new SBMLReader().readSBMLFile(fileName);
        System.out.printf("Reading done\n");
        System.out.println(Calendar.getInstance().getTime());
        afterRead = Calendar.getInstance().getTimeInMillis();

        int nbProblem = testDocument.checkConsistencyOffline();

        System.out.println("Found " + nbProblem + " constraint errors.");
        testDocument.printErrors(System.out);

        //        System.out.println("Model Notes = " + XMLNode.convertXMLNodeToString(testDocument.getModel().getNotes()));
        //        System.out.println("MathML = " + testDocument.getModel().getReaction(0).getKineticLaw().getMath().toMathML());

        System.out.println("Going to check package version and namespace for all elements.");
        PackageUtil.checkPackages(testDocument);

        System.out.printf("Starting writing\n");

        new org.sbml.jsbml.SBMLWriter("MyApp", "1.0").write(testDocument, jsbmlWriteFileName);

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
				System.out.println('\t' + i + " uris found. \n");
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
  private Map<String, WritingParser> instantiatedSBMLParsers = new HashMap<String, WritingParser>();

  /**
   * Remember already issued warnings to avoid having multiple lines, saying
   * the same thing (Warning: Skipping detailed parsing of name space 'XYZ'.
   * No parser available.)
   */
  private transient List<String> issuedWarnings = new ArrayList<String>();

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SBMLWriter.class);


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
   * @param indentChar the symbol to be used to indent elements in the XML representation
   * @param indentCount the number of these symbols to be concatenated
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
   * Creates the necessary number of white spaces at the beginning
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
  // TODO - remove/update this method, one object can only be written by one package
  private List<WritingParser> getWritingParsers(Object object, String parentNamespace) {

    Set<String> packageNamespaces = null;

    // logger.debug("getInitializedParsers: name space, object = " + name space + ", " + object);

    if (object instanceof SBase) {
      SBase sbase = (SBase) object;
      packageNamespaces = new TreeSet<String>();
      String sbaseNamespace = sbase.getNamespace();

      if (sbaseNamespace != null && (!packageNamespaces.contains(sbaseNamespace))) {
        packageNamespaces.add(sbaseNamespace);
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
   * Creates the ReadingParser instances and stores them in a HashMap.
   * 
   * @return the map containing the ReadingParser instances.
   */
  private Map<String, WritingParser> initializePackageParsers()
  {
    if (instantiatedSBMLParsers == null || instantiatedSBMLParsers.size() == 0) {
      instantiatedSBMLParsers = ParserManager.getManager().getWritingParsers();
    }

    return instantiatedSBMLParsers;
  }


  /**
   * Sets other blank character(s) for indentation.
   * <p> Allowed are
   * only tabs and white spaces, i.e., {@code '\t'} and {@code ' '}.
   * 
   * @param indentSymbol the character to be used for indentation
   */
  public void setIndentationChar(char indentSymbol) {
    if ((indentSymbol != ' ') && (indentSymbol != '\t')) {
      throw new IllegalArgumentException(MessageFormat.format(
        "Invalid argument \"{0}\". Only the blank symbols '\\t' and ' ' are allowed for indentation.",
        indentSymbol));
    }
    indentChar = indentSymbol;
  }

  /**
   * Sets the indent for this {@link SBMLWriter}.
   * 
   * @param indentCount the indent count to set
   * @see SBMLWriter#setIndentationCount(short)
   */
  public void setIndentationCount(int indentCount) {
    setIndentationCount((short) indentCount);
  }

  /**
   * Sets the indent for this {@link SBMLWriter}.
   * 
   * @param indentCount the indent count to set
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
   * Writes the {@link SBMLDocument} into a {@link File}.
   * 
   * @param document the {@link SBMLDocument} to write.
   * @param file the {@link File} to write to.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   * @throws XMLStreamException if any error occur while creating the XML document.
   * @throws IOException
   *             if it is not possible to write to the given file, e.g., due
   *             to an invalid file name or missing permissions.
   */
  public void write(SBMLDocument document, File file)
      throws XMLStreamException, SBMLException, IOException {
    write(document, file, null, null);
  }

  /**
   * Writes the {@link SBMLDocument} into a {@link File}.
   * 
   * @param document the {@link SBMLDocument} to write.
   * @param file the {@link File} to write to.
   * @param programName the program name.
   * @param programVersion the program version.
   * @throws XMLStreamException if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   * @throws IOException
   *             if it is not possible to write to the given file, e.g., due
   *             to an invalid file name or missing permissions.
   */
  public void write(SBMLDocument document, File file, String programName,
    String programVersion) throws XMLStreamException, SBMLException, IOException {
    FileOutputStream stream = new FileOutputStream(file);
    BufferedOutputStream buffer = new BufferedOutputStream(stream);
    XMLStreamException exc1 = null;
    try {
      write(document, buffer, programName, programVersion);
    } catch (XMLStreamException exc) {
      /*
       * Catching this exception makes sure that we have still the chance to
       * close the streams. Otherwise they will stay opened although the
       * execution of this method is over.
       */
      exc1 = exc;
    } finally {
      try {
        try {
          stream.close();
        } finally {
          buffer.close();
        }
      } catch (IOException exc2) {
        // Ok, we lost. No chance to really close these streams. Heavy error.
        if (exc1 != null) {
          exc2.initCause(exc1);
        }
        throw exc2;
      } finally {
        if (exc1 != null) {
          throw exc1;
        }
      }
    }
  }

  /**
   * Writes the {@link SBMLDocument} into an {@link OutputStream}.
   * 
   * @param sbmlDocument
   *          the {@link SBMLDocument} to write.
   * @param stream
   *          a {@link OutputStream} where to write the content of the model to.
   * @throws XMLStreamException if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   * 
   */
  public void write(SBMLDocument sbmlDocument, OutputStream stream)
      throws XMLStreamException, SBMLException {
    write(sbmlDocument, stream, null, null);
  }

  /**
   * Writes the XML representation of an {@link SBMLDocument} into an {@link OutputStream}.
   * 
   * @param sbmlDocument the {@link SBMLDocument}
   * @param stream the {@link OutputStream} to write to.
   * @param programName
   *            the program name (can be null).
   * @param programVersion
   *            the program version (can be null).
   * @throws XMLStreamException if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   * 
   */
  public void write(SBMLDocument sbmlDocument, OutputStream stream,
    String programName, String programVersion)
        throws XMLStreamException, SBMLException {
    if ((sbmlDocument == null) || !sbmlDocument.isSetLevel() || !sbmlDocument.isSetVersion()) {
      throw new IllegalArgumentException(
          "Unable to write SBML output for documents with undefined SBML Level and Version flag.");
    }

    Logger logger = Logger.getLogger(SBMLWriter.class);

    // check package version and namespace in general and register packages if needed.
    PackageUtil.checkPackages(sbmlDocument, true, true);

    initializePackageParsers();

    // Explicitly creating WstxOutputFactory as it is needed by staxmate and it is then easier for
    // OSGi to find the needed dependencies
    WstxOutputFactory factory = new WstxOutputFactory();
    SMOutputFactory smFactory = new SMOutputFactory(factory);
    XMLStreamWriter2 streamWriter = smFactory.createStax2Writer(stream);

    SMOutputDocument outputDocument = SMOutputFactory.createOutputDocument(
      streamWriter, "1.0", "UTF-8", false);
    // to have the automatic indentation working, we should probably only be using StaxMate classes and not directly StAX
    // outputDocument.setIndentation("\n  ", 1, 1);

    String SBMLNamespace = JSBML.getNamespaceFrom(sbmlDocument.getLevel(),
      sbmlDocument.getVersion());
    SMOutputContext context = outputDocument.getContext();
    context.setIndentation('\n' + createIndentationString(indentCount), 1, 2);
    SMNamespace namespace = context.getNamespace(SBMLNamespace);
    namespace.setPreferredPrefix("");
    outputDocument.addCharacters("\n");

    /*
     * Write a comment to track which program created this SBML file and
     * which version of JSBML was used for this purpose.
     */
    if ((programName != null) && (programName.length() > 0)) {
      outputDocument.addComment(
        MessageFormat.format(
          " Created by {0} version {1} on {2,date,yyyy-MM-dd} at {2,time,kk:mm:ss z} with JSBML version {3}. ",
          (programName != null) && (programName.length() > 0) ? programName : "?",
            (programVersion != null)  && (programVersion.length() > 0) ? programVersion : "?",
              Calendar.getInstance().getTime(), JSBML.getJSBMLDottedVersion()));
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

          String namespacePrefix = prefix.substring(prefix.indexOf(':') + 1);

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
   *          the {@link SBMLDocument} to write.
   * @param fileName
   *          the name of the file where to write the {@link SBMLDocument}
   * @throws XMLStreamException
   *             if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   * @throws IOException if an I/O error occurs.
   * 
   */
  public void write(SBMLDocument sbmlDocument, String fileName)
      throws XMLStreamException, SBMLException, IOException {
    write(sbmlDocument, fileName, null, null);
  }

  /**
   * Writes the XML representation of an {@link SBMLDocument} in a SBML file.
   * 
   * @param sbmlDocument the {@link SBMLDocument} to write.
   * @param fileName the name of the file where to write the {@link SBMLDocument}
   * @param programName the program name that created the {@link SBMLDocument}
   * @param programVersion the program version that created the {@link SBMLDocument}
   * @throws XMLStreamException if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   * @throws IOException if an I/O error occurs.
   * 
   */
  public void write(SBMLDocument sbmlDocument, String fileName,
    String programName, String programVersion)
        throws XMLStreamException, SBMLException, IOException {
    write(sbmlDocument, new File(fileName), programName, programVersion);
  }

  /**
   * Writes the {@link Annotation} of an {@link SBase} element to an XML {@link String}.
   * 
   * @param sbase the {@link SBase} element.
   * @return the {@link Annotation} of the given {@link SBase} element as an XML {@link String}.
   * @throws XMLStreamException if any error occur while creating the XML document.
   */
  public String writeAnnotation(SBase sbase) throws XMLStreamException {

    String annotationStr = "";

    if ((sbase == null) || !sbase.isSetAnnotation()) {
      return annotationStr;
    }

    StringWriter stream = new StringWriter();
    WstxOutputFactory outputFactory = new WstxOutputFactory();
    SMOutputFactory smFactory = new SMOutputFactory(outputFactory);
    XMLStreamWriter2 writer = smFactory.createStax2Writer(stream);

    // Create an xml fragment to avoid having the xml declaration
    SMRootFragment outputDocument = SMOutputFactory.createOutputFragment(writer);

    // all the sbml element namespaces are registered to the writer in the writeAnnotation method

    // call the writeAnnotation, indicating that we are building an xml fragment
    writeAnnotation(sbase, outputDocument, writer, 0, true);

    writer.writeEndDocument();
    writer.close();

    annotationStr = stream.toString();

    return annotationStr;
  }

  /**
   * Writes the {@link Annotation} of an {@link SBase} element.
   * 
   * @param sbase
   *          the {@link SBase} element.
   * @param element
   *          the matching {@link SMOutputElement}
   * @param writer
   *          the {@link XMLStreamWriter} to write to.
   * @param indent
   *            the number of indent white spaces of this annotation.
   * @param xmlFragment
   * @throws XMLStreamException if any error occur while creating the XML document.
   */
  private void writeAnnotation(SBase sbase, SMOutputContainer element,
    XMLStreamWriter writer, int indent, boolean xmlFragment)
        throws XMLStreamException
  {
    XMLNode fullAnnotationXMLNode = sbase.getAnnotation().getFullAnnotation();

    writer.writeCharacters("\n");
    XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, indent, indentCount, indentChar);
    xmlNodeWriter.write(fullAnnotationXMLNode);

  }

  /**
   * @param xmlNode
   * @param smOutputParentElement
   * @param writer
   * @param indent
   * @throws XMLStreamException
   */
  private void writeXMLNode(XMLNode xmlNode,
    SMOutputElement smOutputParentElement, XMLStreamWriter writer,
    int indent) throws XMLStreamException
  {

    XMLNodeWriter xmlNodeWriter = new XMLNodeWriter(writer, indent, indentCount, indentChar);
    xmlNodeWriter.write(xmlNode);
    writer.writeCharacters("\n");

  }



  /**
   * Writes the MathML expression of a {@link MathContainer} element.
   * 
   * @param m
   *          the {@link MathContainer} element.
   * @param element
   *          the matching {@link SMOutputElement}.
   * @param writer
   *          the {@link XMLStreamWriter} to write to.
   * @param indent the number of white spaces to indent this element.
   * @throws XMLStreamException if any error occur while creating the XML document.
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

      ASTNode astNode = m.getMath();

      // if an ASTNode.isSemantics we need to write the enclosing 'semantics' element !!
      if (astNode.isSemantics()) {
        writer.writeCharacters(whitespaces);
        writer.writeStartElement("semantics");
        writer.writeCharacters("\n");
      }

      compiler.compile(m.getMath());

      // writing the semantics annotation elements here to write them only for the top level element.
      if (astNode.isSemantics()) {

        compiler.compileSemanticAnnotations(astNode);

        writer.writeCharacters(whitespaces);
        writer.writeEndElement();
        writer.writeCharacters("\n");
      }

      writer.writeCharacters(whitespaces);
    }
  }

  /**
   * Writes the message of a {@link Constraint} to the given {@link XMLStreamWriter}.
   * 
   * @param sbase
   *          the {@link Constraint} element.
   * @param element
   *          the matching {@link SMOutputElement}.
   * @param writer
   *          the {@link XMLStreamWriter} to write to.
   * @param sbmlNamespace
   *          the SBML namespace.
   * @param indent the number of white spaces to indent this element.
   * @throws XMLStreamException if any error occur while creating the XML document.
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
   * Writes the notes of this {@link SBase} element to the given {@link XMLStreamWriter}.
   * 
   * @param sbase
   *          the {@link SBase} element.
   * @param element
   *          the matching {@link SMOutputElement}
   * @param writer
   *          the {@link XMLStreamWriter} to write to.
   * @param sbmlNamespace
   *          the SBML namespace.
   * @param indent the number of white spaces to indent this element.
   * @throws XMLStreamException if any error occur while creating the XML document.
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
   * Writes the SBML elements to an {@link XMLStreamWriter}.
   * 
   * @param parentXmlObject
   *          contains the XML information of the parentElement.
   * @param smOutputParentElement
   *          {@link SMOutputElement} of the parentElement.
   * @param streamWriter the {@link XMLStreamWriter} to write to.
   * @param parentObject
   *          the {@link Object} to write.
   * @param indent
   *            the number of white spaces to indent this element.
   * @throws XMLStreamException if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
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
          if (parentObject instanceof SBase) {
            SBase parentSBase = (SBase) parentObject;

            // making several if block to make things easier to read and like this it is
            // ready in case we decide to write them here, when encountered in the tree.

            // Notes XMLNode is written later in this method
            if (nextObjectToWrite == parentSBase.getNotes()) {
              continue;
            }
            // MathML and Annotation are written later
            if (nextObjectToWrite instanceof ASTNode || nextObjectToWrite instanceof Annotation) {
              continue;
            }
            // Constraint Message is written later
            if ((parentObject instanceof Constraint) && (nextObjectToWrite == ((Constraint) parentObject).getMessage())){
              continue;
            }
          }

          // additional XMLNode that could come from an L3 package or from unknown XML elements
          if (nextObjectToWrite instanceof XMLNode) {
            writeXMLNode((XMLNode) nextObjectToWrite, smOutputParentElement, streamWriter, indent);
            continue;
          }

          logger.warn("Element '" + nextObjectToWrite.getClass().getSimpleName() +
              "' ignored, we are not sure what to do with it !!");
          continue;
        }

        SBase s = (SBase) nextObjectToWrite;

        // test if this element is part of a disabled package. Do not write the element if it is the case
        if (s.getNamespace() != null) {
          SBMLDocument doc = s.getSBMLDocument();
          Boolean isPackageEnabled = null;

          if (doc != null) {
            isPackageEnabled = doc.isPackageEnabledOrDisabled(s.getNamespace());
          } else {
            // Something is wrong - should not happen
            isPackageEnabled = s.isPackageEnabled(s.getNamespace());
            logger.warn("The SBMLDocument could not be found!");
          }

          if ((isPackageEnabled != null) && isPackageEnabled.equals(Boolean.FALSE)) {
            continue;
          }
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

        if (isEmptyListOf(nextObjectToWrite))
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
        childParser.writeCharacters(childXmlObject, nextObjectToWrite);

        if (!childXmlObject.isSetName()) {
          logger.error("XML name not set, element ignored! (" + nextObjectToWrite.getClass().getName() + ")");
          continue;
        }

        SMOutputElement newOutPutElement = null;
        boolean isClosedMathContainer = false, isClosedAnnotation = false;

        SMNamespace namespace = null;

        if (childXmlObject.isSetNamespace()) {
          namespace = smOutputParentElement.getContext().getNamespace(childXmlObject.getNamespace(), childXmlObject.getPrefix());
        } else {
          namespace = smOutputParentElement.getNamespace();
        }

        newOutPutElement = smOutputParentElement.addElement(namespace, childXmlObject.getName());

        // adding the attributes to the {@link SMOutputElement}
        for (String attributeName : childXmlObject.getAttributes().keySet())
        {
          newOutPutElement.addAttribute(attributeName, childXmlObject.getAttributes().get(attributeName));
        }

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

        if (childXmlObject.getCharacters() != null && childXmlObject.getCharacters().trim().length() != 0) {
          newOutPutElement.addCharacters(childXmlObject.getCharacters());
        }

        if (s.getChildCount() > 0) {
          // make sure that we'll have line breaks if an element has any sub elements.
          elementIsNested = true;
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
   * @param object the {@link Object} to test
   * @return {@code true} if the given {@link Object} is an empty {@link ListOf}, {@code false} otherwise.
   */
  private boolean isEmptyListOf(Object object)
  {
    if (object instanceof ListOf<?>)
    {
      ListOf<?> list = (ListOf<?>) object;

      // from L3V2 empty ListOf are allowed
      if ((list.getLevelAndVersion().compareTo(3, 2) < 0) && list.isEmpty())
      {
        return true;
      }
    }

    return false;
  }


  /**
   * Writes the given {@link SBMLDocument} to an in-memory XML {@link String}.
   * 
   * @param doc
   *            the {@code SBMLdocument}
   * @return the XML representation of the {@code SBMLdocument} as a
   *         String.
   * @throws XMLStreamException
   *             if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   */
  public String writeSBMLToString(SBMLDocument doc)
      throws XMLStreamException, SBMLException {
    return writeSBMLToString(doc, null, null);
  }

  /***
   * Writes the given {@link SBMLDocument} to an in-memory XML {@link String}.
   * 
   * @param d
   * @param programName
   * @param programVersion
   * @return the XML representation of the {@code SBMLdocument} as a {@link String}.
   * @throws XMLStreamException if any error occur while creating the XML document.
   * @throws SBMLException if any error is detected in the {@link SBMLDocument}.
   * 
   */
  public String writeSBMLToString(SBMLDocument d, String programName,
    String programVersion) throws XMLStreamException, SBMLException {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    write(d, stream, programName, programVersion);
    try {
      return stream.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      return stream.toString();
    }
  }

}
