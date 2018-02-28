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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.parsers.AbstractReaderWriter;
import org.sbml.jsbml.xml.parsers.AnnotationReader;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;
import org.sbml.jsbml.xml.parsers.ParserManager;
import org.sbml.jsbml.xml.parsers.ReadingParser;
import org.sbml.jsbml.xml.parsers.SBMLCoreParser;
import org.sbml.jsbml.xml.parsers.XMLNodeReader;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.stax.WstxInputFactory;


/**
 * Provides all the methods to read a SBML file.
 *
 * @author Marine Dumousseau
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 0.8
 */
public class SBMLReader {

  // Commenting out this static block as setting those system properties has some unwanted side
  // effect, for example in OSGi where the properties are global
  // The fact to use directly WstxOutputFactory and WstxInputFactory when creating the parser
  // should prevent the problem that setting those properties was fixing.
  //  static {
  //    // Making sure that we use the good XML library
  //    System.setProperty("javax.xml.stream.XMLOutputFactory", "com.ctc.wstx.stax.WstxOutputFactory");
  //    System.setProperty("javax.xml.stream.XMLInputFactory", "com.ctc.wstx.stax.WstxInputFactory");
  //    System.setProperty("javax.xml.stream.XMLEventFactory", "com.ctc.wstx.stax.WstxEventFactory");
  //  }

  /**
   * Contains all the initialized parsers.
   */
  private Map<String, ReadingParser> initializedParsers = new HashMap<String, ReadingParser>();

  /**
   * The parent of the mathML we are parsing through the readMathML methods.
   * It allow to parse properly the FunctionDefinition contained in the mathML.
   *
   */
  private MathContainer astNodeParent;

  /**
   *
   */
  private List<AnnotationReader> annotationParsers = new ArrayList<AnnotationReader>();

  /**
   * Initialize a static instance of the core parser.
   */
  private static SBMLCoreParser sbmlCoreParser = new SBMLCoreParser();

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SBMLReader.class);

  /**
   * Creates the ReadingParser instances and stores them in a
   * HashMap.
   *
   * @return the map containing the ReadingParser instances.
   */
  private Map<String, ReadingParser> initializePackageParsers() {
    Logger logger = Logger.getLogger(SBMLReader.class);

    if (logger.isDebugEnabled()) {
      logger.debug("initializePackageParsers called for " + this);
    }

    if ((initializedParsers == null) || (initializedParsers.size() == 0)) {
      initializedParsers = ParserManager.getManager().getReadingParsers();
      initializeAnnotationParsers();
    }

    return initializedParsers;
  }

  /**
   * Associates any unknown namespaces with the {@link AnnotationReader}.
   * @param startElement
   */
  private void addAnnotationParsers(StartElement startElement)
  {
    @SuppressWarnings("unchecked")
    Iterator<Namespace> namespacesIterator = startElement.getNamespaces();

    while (namespacesIterator.hasNext()) {
      String namespaceURI = namespacesIterator.next().getNamespaceURI();

      if (initializedParsers.get(namespaceURI) == null) {
        initializedParsers.put(namespaceURI, initializedParsers.get("anyXML"));
      }
    }
  }


  /**
   * Initializes the packageParser {@link HashMap} of this class.
   *
   */
  public void initializeAnnotationParsers() {

    // TODO - make use of the java6 annotation to know which annotationParsers to initialize

    // to prevent several call to this method
    annotationParsers.clear();

    Map<String, Class<? extends AnnotationReader>> annotationParserClasses = new HashMap<String, Class<? extends AnnotationReader>>();

    JSBML.loadClasses(
      "org/sbml/jsbml/resources/cfg/annotationParsers.xml",
      annotationParserClasses);

    for (Class<? extends AnnotationReader> annotationReaderClass : annotationParserClasses.values()) {
      try {
        annotationParsers.add(annotationReaderClass.newInstance());
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Reads the file that is passed as argument and write it to the console,
   * using the method {@link SBMLWriter#write(SBMLDocument, java.io.OutputStream)}.
   *
   * @param args the command line arguments, we are taking the first one as
   * the file name to read.
   *
   * @throws IOException if the file name is not valid.
   * @throws SBMLException if there are any problems reading or writing the SBML model.
   * @throws XMLStreamException if there are any problems reading or writing the XML file.
   */
  public static void main(String[] args) throws IOException, XMLStreamException, SBMLException  {
    // TODO: This class should not contain a main method; move to examples/.
    if (args.length < 1) {
      System.out
      .println("Usage: java org.sbml.jsbml.xml.stax.SBMLReader sbmlFileName");
      System.exit(0);
    }

    String fileName = args[0];

    SBMLDocument testDocument = new org.sbml.jsbml.SBMLReader().readSBMLFromFile(fileName);

    System.out.println("Number of namespaces: " + testDocument.getDeclaredNamespaces().size());

    for (String prefix : testDocument.getDeclaredNamespaces().keySet()) {
      System.out.println("PREFIX = "+prefix);
      String uri = testDocument.getDeclaredNamespaces().get(prefix);
      System.out.println("URI = "+uri);
    }

    System.out.println("Model NoRDFAnnotation String = \n@" + testDocument.getModel().getAnnotation().getNonRDFannotation() + "@");

    System.out.println("Model Annotation String = \n@" + testDocument.getModel().getAnnotationString() + "@");

    for (Species species : testDocument.getModel().getListOfSpecies()) {
      species.getAnnotationString();
    }

    new SBMLWriter().write(testDocument, System.out);

    /*
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
			"  <body xmlns=\"" + JSBML.URI_XHTML_DEFINITION + "\">\n " +
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
     */
  }

  /**
   *
   * @param file
   * @return
   * @throws XMLStreamException
   * @throws IOException
   */
  public SBMLDocument readSBML(File file) throws IOException, XMLStreamException {
    return readSBML(file, null);
  }

  /**
   * Reads a SBML String from the given file.
   *
   * @param file
   *            A file containing SBML content.
   * @param listener
   * @return the matching SBMLDocument instance.
   * @throws IOException
   * @throws XMLStreamException
   */
  public SBMLDocument readSBML(File file, TreeNodeChangeListener listener) throws IOException, XMLStreamException {
    FileInputStream stream = new FileInputStream(file);
    XMLStreamException exc1 = null;
    Object readObject = null;
    try {
      readObject = readXMLFromStream(stream, listener);
    } catch (XMLStreamException exc) {
      /*
       * Catching this exception makes sure that we have still the chance to
       * close the stream. Otherwise it will stay opened although the execution
       * of this method is over.
       */
      exc1 = exc;
    } finally {
      try {
        stream.close();
      } catch (IOException exc2) {
        // Ok, we lost. No chance to really close this stream. Heavy error.
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
    if (readObject instanceof SBMLDocument) {
      return (SBMLDocument) readObject;
    }
    throw new XMLStreamException(MessageFormat.format(
      "JSBML could not properly read file {0}. Please check if it contains valid SBML. If you think it is valid, please submit a bug report to the bug tracker of JSBML.",
      (file.getPath() == null) ? "null" : file.getAbsolutePath()));
  }

  /**
   * Reads SBML from a given file.
   *
   * @param file
   *            The path to an SBML file.
   * @return the matching SBMLDocument instance.
   * @throws XMLStreamException
   * @throws IOException
   */
  public SBMLDocument readSBML(String file) throws XMLStreamException,
  IOException {
    return readSBMLFile(file);
  }

  /**
   * Reads the SBML file 'fileName' and creates/initialises a SBMLDocument
   * instance.
   *
   * @param fileName
   *         name of the SBML file to read.
   * @return the initialized SBMLDocument.
   * @throws XMLStreamException
   * @throws IOException
   */
  public SBMLDocument readSBMLFile(String fileName)
      throws XMLStreamException, IOException {
    return readSBML(new File(fileName));
  }


  /**
   * Reads an {@link SBMLDocument} from the given {@link XMLEventReader}
   *
   * @param xmlEventReader
   * @param listener
   * @return
   * @throws XMLStreamException
   */
  public SBMLDocument readSBML(XMLEventReader xmlEventReader, TreeNodeChangeListener listener)
      throws XMLStreamException {
    return (SBMLDocument) readXMLFromXMLEventReader(xmlEventReader, listener);
  }

  /**
   *
   * @param xmlEventReader
   * @return
   * @throws XMLStreamException
   */
  public SBMLDocument readSBML(XMLEventReader xmlEventReader) throws XMLStreamException {
    return readSBML(xmlEventReader, new SimpleTreeNodeChangeListener());
  }

  /**
   * Reads a mathML String into an {@link ASTNode}.
   *
   * @param mathML
   * @param listener
   * @return an {@link ASTNode} representing the given mathML String.
   * @throws XMLStreamException
   */
  public ASTNode readMathML(String mathML, TreeNodeChangeListener listener)
      throws XMLStreamException
  {
    if (logger.isDebugEnabled()) {
      logger.debug("SBMLReader.readMathML called");
    }

    Object object = readXMLFromString(mathML, listener);
    if (object != null && object instanceof Constraint) {
      ASTNode math = ((Constraint) object).getMath();
      if (math != null) {
        cleanTreeNode(math);
        return math;
      }
    }
    return null;
  }

  /**
   * Cleans the given node by removing user object(s) set during reading/parsing.
   * 
   * @param treeNode the node to be cleaned
   */
  private void cleanTreeNode(AbstractTreeNode treeNode)
  {
    // Go through the whole treeNode (using a fake filter!) to remove the variable that says that we were in the process of reading an xml stream.
    treeNode.filter(new Filter() {

      @Override
      public boolean accepts(Object o) {
        if (o instanceof TreeNodeWithChangeSupport) {
          if (((TreeNodeWithChangeSupport) o).isSetUserObjects()) {
            ((TreeNodeWithChangeSupport) o).userObjectKeySet().remove(JSBML.READING_IN_PROGRESS);
          } // else if (! ((o instanceof TreeNodeAdapter) || (o instanceof XMLNode))) {
          //	            System.out.println("######### user objects not set !!!!!!!! " + o + " class name = " + o.getClass().getSimpleName());
          //	          }
        }
        return false;
      }
    });

  }

  /**
   * Reads a mathML {@link String} into an {@link ASTNode}.
   *
   * @param mathML
   * @param listener
   * @param parent the parent {@link MathContainer} of the mathML to parse
   * @return an {@link ASTNode} representing the given mathML {@link String}.
   * @throws XMLStreamException
   */
  public ASTNode readMathML(String mathML, TreeNodeChangeListener listener, MathContainer parent)
      throws XMLStreamException
  {
    astNodeParent = parent;

    if (logger.isDebugEnabled()) {
      logger.debug("SBMLReader.readMathML with parent called");
    }

    Object object = readXMLFromString(mathML, listener);
    if (object != null && object instanceof Constraint) {
      ASTNode math = ((Constraint) object).getMath();
      if (math != null) {
        cleanTreeNode(math);
        return math;
      }
    }
    return null;
  }

  /**
   *
   * @param mathML
   * @return
   * @throws XMLStreamException
   */
  public ASTNode readMathML(String mathML) throws XMLStreamException {
    return readMathML(mathML, new SimpleTreeNodeChangeListener());
  }

  /**
   * Reads a notes XML {@link String} into an {@link XMLNode}.
   *
   * @param notesXHTML
   * @param listener
   * @return an {@link XMLNode} representing the given notes {@link String}.
   * @throws XMLStreamException
   */
  public XMLNode readNotes(String notesXHTML, TreeNodeChangeListener listener)
      throws XMLStreamException {
    Object object = readXMLFromString(notesXHTML, listener);

    if ((object != null) && (object instanceof Constraint)) {
      Constraint constraint = ((Constraint) object);
      cleanTreeNode(constraint);

      if (constraint.isSetNotes()) {
        XMLNode notes = constraint.getNotes();
        if (notes != null) {
          return notes;
        }
      } else if (constraint.isSetMessage()) {
        XMLNode message = constraint.getMessage();
        if (message != null) {
          return message;
        }
      } else if (constraint.isSetAnnotation()) {
        XMLNode annotation = constraint.getAnnotation().getNonRDFannotation();
        if (annotation != null) {
          return annotation;
        }
      } else if (constraint.getUserObject(org.sbml.jsbml.SBMLReader.UNKNOWN_XML_NODE) != null) {
        return (XMLNode) constraint.getUserObject(org.sbml.jsbml.SBMLReader.UNKNOWN_XML_NODE);
      }
    }
    else if ((object != null) && (object instanceof XMLNode))
    {
      // Should not happen at the moment but could if readXMLFromString returned directly
      // the XMLNode instead of a Constraint object.
      return (XMLNode) object;
    }

    logger.warn("Tried to read @" + notesXHTML + "@ as XMLNode without success ! ");

    return null;
  }

  /**
   *
   * @param notesXHTML
   * @return
   * @throws XMLStreamException
   */
  public XMLNode readNotes(String notesXHTML) throws XMLStreamException {
    return readNotes(notesXHTML, new SimpleTreeNodeChangeListener());
  }

  /**
   * Reads a SBML document from the given {@code stream}.
   *
   * @param stream
   * @param listener
   * @return
   * @throws XMLStreamException
   */
  public SBMLDocument readSBMLFromStream(InputStream stream, TreeNodeChangeListener listener)
      throws XMLStreamException {
    WstxInputFactory inputFactory = new WstxInputFactory();
    XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(stream);
    return (SBMLDocument) readXMLFromXMLEventReader(xmlEventReader, listener);
  }

  /**
   *
   * @param stream
   * @return
   * @throws XMLStreamException
   */
  public SBMLDocument readSBMLFromStream(InputStream stream) throws XMLStreamException {
    return readSBMLFromStream(stream, new SimpleTreeNodeChangeListener());
  }

  /**
   * Reads a XML document from the given {@code stream}. It need to be a self contain part of
   * an SBML document.
   *
   * @param stream
   * @param listener
   * @return
   * @throws XMLStreamException
   */
  private Object readXMLFromStream(InputStream stream, TreeNodeChangeListener listener)
      throws XMLStreamException {
    WstxInputFactory inputFactory = new WstxInputFactory();

    // see https://groups.google.com/d/msg/jsbml-development/cckEJPYNzQY/5ynmIbqNCAAJ for why we did set this value
    try {
      inputFactory.setProperty(WstxInputProperties.P_MAX_ELEMENT_DEPTH, 5000);
    } catch(IllegalArgumentException e) {
      // do nothing - the XML libraries used do not support this property for some reason
    }

    XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(stream);
    return readXMLFromXMLEventReader(xmlEventReader, listener);
  }


  /**
   * Reads an XML document from the given {@link XMLEventReader}. It need to represent a self contain part of
   * an SBML document. It can be either a math element, a notes element or the whole SBML model. If math or notes are given,
   * a Rule containing the math or notes will be returned, otherwise an SBMLDocument is returned.
   *
   *
   * @param xmlEventReader
   * @param listener
   * @return an {@code Object} representing the given XML.
   * @throws XMLStreamException
   */
  private Object readXMLFromXMLEventReader(XMLEventReader xmlEventReader, TreeNodeChangeListener listener)  throws XMLStreamException {

    initializePackageParsers();

    XMLEvent event;
    StartElement startElement = null;
    ReadingParser parser = null;
    Stack<Object> sbmlElements = new Stack<Object>();
    QName currentNode = null;
    boolean isNested = false;
    boolean isText = false;
    boolean isHTML = false;
    boolean isInsideAnnotation = false;
    boolean isInsideNotes = false;
    int annotationDeepness = -1;
    int level = -1, version = -1;
    Object lastElement = null;

    // Read all the elements of the file
    while (xmlEventReader.hasNext()) {
      event = xmlEventReader.nextEvent();

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
          sbmlDocument.putUserObject(JSBML.READING_IN_PROGRESS, Boolean.TRUE);

          if (currentNode.getPrefix() != null && currentNode.getPrefix().trim().length() > 0) {
            sbmlDocument.putUserObject(JSBML.ELEMENT_XML_PREFIX, currentNode.getPrefix());
          }

          // the output of the change listener is activated or not via log4j.properties
          sbmlDocument.addTreeNodeChangeListener(listener == null
              ? new SimpleTreeNodeChangeListener() : listener);

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
        else if (lastElement == null) // We are probably reading some 'free' XML, mathML or HTML
        {
          // We put a fake Constraint element in the stack that can take either math, notes or message.
          // This a hack to be able to read some mathMl or notes by themselves.
          // If the parent container is set in this SBMLReader, we use it instead.

          // TODO: will not work with arbitrary SBML part
          // TODO: we need to be able, somehow, to set the Model element in the Constraint
          // to be able to have a fully functional parsing. Without it the functionDefinition, for examples, are
          // not properly recognized.
          if (astNodeParent != null)
          {
            sbmlElements.push(astNodeParent);
          }
          else
          {
            Constraint constraint = new Constraint(3,1);
            sbmlElements.push(constraint);
          }

          if (currentNode.getLocalPart().equals("notes") || currentNode.getLocalPart().equals("message")
              || currentNode.getLocalPart().equals("annotation"))
          {
            initializedParsers.put("", sbmlCoreParser);

            // get the sbml namespace to set it on the first element to parse
            SBase sbase = (SBase) sbmlElements.firstElement();
            String sbmlNamespace = JSBML.getNamespaceFrom(sbase.getLevel(), sbase.getVersion());
            currentNode = new QName(sbmlNamespace, currentNode.getLocalPart());
          }
          else if (currentNode.getLocalPart().equals("math"))
          {
            initializedParsers.put("", new MathMLStaxParser());
            initializedParsers.put(ASTNode.URI_MATHML_DEFINITION, new MathMLStaxParser());
            currentNode = new QName(ASTNode.URI_MATHML_DEFINITION, "math");
          }
          // TODO - add something generic for the L3 packages or change all the parsers to work if the contextObject is 'null' ??

        } else if (currentNode.getLocalPart().equals("annotation")) {

          // get the sbml namespace as some element can have similar names in different namespaces
          SBase sbmlDoc = (SBase) sbmlElements.firstElement();
          String sbmlNamespace = JSBML.getNamespaceFrom(sbmlDoc.getLevel(), sbmlDoc.getVersion());

          if (currentNode.getNamespaceURI().equals(sbmlNamespace)) {
            if (isInsideAnnotation) {
              logger.warn("Starting to read a new annotation element while the previous annotation element is not finished.");
            }
            isInsideAnnotation = true;
          }
        }
        else if (isInsideAnnotation) {
          // Count the number of open elements to know how deep we are in the annotation
          annotationDeepness++;
        }
        else if (currentNode.getLocalPart().equals("notes"))
        {
          // get the sbml namespace as some element can have similar names in different namespaces
          SBase firstElement = (SBase) sbmlElements.firstElement();

          if (firstElement instanceof SBMLDocument) {
            SBase sbmlDoc = (SBase) sbmlElements.firstElement();
            String sbmlNamespace = JSBML.getNamespaceFrom(sbmlDoc.getLevel(), sbmlDoc.getVersion());

            if (currentNode.getNamespaceURI().equals(sbmlNamespace)) {
              isInsideNotes = true;
            }
          } else if (firstElement instanceof Constraint) { // we are reading a partial document from SBMLReader#readNotes for example
            isInsideNotes = true;
          }
        }

        if (isInsideAnnotation && logger.isDebugEnabled()) {
          logger.debug("startElement: local part = " + currentNode.getLocalPart());
          // logger.debug("startElement: annotation deepness = " + annotationDeepness);
        }

        // annotationDeepness = 0 is the annotation element and we want to pass everything inside it to the anyXML parser
        parser = processStartElement(startElement, currentNode, isHTML,	sbmlElements, (annotationDeepness > 0));
        lastElement = sbmlElements.peek();

      }
      // Characters
      else if (event.isCharacters()) {
        Characters characters = event.asCharacters();

        if (!characters.isWhiteSpace()) {
          isText = true; // the characters are not only 'white spaces'
        }
        if ((!sbmlElements.isEmpty() && (sbmlElements.peek() instanceof XMLNode)) || isInsideNotes || isInsideAnnotation) {
          isText = true; // We want to keep the whitespace/formatting when reading html block
        }

        // process the text of a XML element.
        if ((parser != null) && !sbmlElements.isEmpty()	&& (isText || isInsideAnnotation)) {

          if (isInsideNotes) {
            parser = initializedParsers.get(JSBML.URI_XHTML_DEFINITION); // TODO : this is probably not needed
          }
          else if (isInsideAnnotation) {
            parser = initializedParsers.get("anyXML");
          }

          if (logger.isDebugEnabled()) {
            logger.debug(" PackageParser = " + parser.getClass().getName());
            logger.debug(" Characters = @" + characters.getData() + "@");
          }

          if (currentNode != null) {

            // logger.debug("isCharacter: elementName = " + currentNode.getLocalPart());

            parser.processCharactersOf(currentNode.getLocalPart(),
              characters.getData(), sbmlElements.peek());
          } else {
            parser.processCharactersOf(null, characters.getData(),
              sbmlElements.peek());
          }
        } else if (isText) {
          logger.warn(MessageFormat.format("Some characters cannot be read: {0}", characters.getData()));
          if (logger.isDebugEnabled()) {
            logger.debug("PackageParser = " + parser);
            if (sbmlElements.isEmpty()) {
              logger.debug("The Object Stack is empty!");
            } else {
              logger.debug("The current Object in the stack is: " + sbmlElements.peek());
            }
          }


        }
      }
      // EndElement
      else if (event.isEndElement()) {

        // the method  processEndElement will return null until we arrive at the end of the 'sbml' element.
        lastElement = sbmlElements.peek();

        currentNode = event.asEndElement().getName();

        if (currentNode != null) {

          boolean isSBMLelement = true;

          // get the sbml namespace as some element can have similar names in different namespaces
          if (sbmlElements.firstElement() instanceof SBase)
          {
            SBase sbmlDoc = (SBase) sbmlElements.firstElement();
            String sbmlNamespace = JSBML.getNamespaceFrom(sbmlDoc.getLevel(), sbmlDoc.getVersion());

            if (!currentNode.getNamespaceURI().equals(sbmlNamespace)) {
              isSBMLelement = false;
            }
          }

          if (currentNode.getLocalPart().equals("annotation") && isSBMLelement)
          {
            isInsideAnnotation = false;
            annotationDeepness = -1;

            // calling the annotation parsers
            for (AnnotationReader annoReader : annotationParsers) {
              annoReader.processAnnotation((SBase) ((Annotation) lastElement).getParent()); // or take the second element in the stack ??
            }

          } else if (isInsideAnnotation) {
            annotationDeepness--;
          }
          else if (currentNode.getLocalPart().equals("notes") && isSBMLelement)
          {
            isInsideNotes = false;
          }
        }

        SBMLDocument sbmlDocument = processEndElement(currentNode, isNested, isText, isHTML,
          level, version, parser, sbmlElements, (annotationDeepness >= 0));

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

    if (logger.isDebugEnabled()) {
      logger.debug("no more XMLEvent: stack.size = " + sbmlElements.size());

      logger.debug("no more XMLEvent: stack = " + sbmlElements);
    }

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
   * @param listener
   * @return
   * @throws XMLStreamException
   */
  public SBMLDocument readSBMLFromString(String xml, TreeNodeChangeListener listener) throws XMLStreamException {
    Object readObject = readXMLFromStream(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), listener);
    if (readObject instanceof SBMLDocument) {
      return (SBMLDocument) readObject;
    }
    throw new XMLStreamException("The given file seems not to be a valid SBMl file. Please check it using the SBML online validator.");
  }

  /**
   *
   * @param xml
   * @return
   * @throws XMLStreamException
   */
  public SBMLDocument readSBMLFromString(String xml) throws XMLStreamException {
    return readSBMLFromString(xml, new SimpleTreeNodeChangeListener());
  }

  /**
   * Reads an XML {@link String} that should the part of a SBML model.
   *
   * @param xml
   * @param listener
   * @return
   * @throws XMLStreamException
   */
  private Object readXMLFromString(String xml, TreeNodeChangeListener listener)
      throws XMLStreamException {
    return readXMLFromStream(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), listener);
  }


  /**
   * Process a {@link StartElement} event.
   *
   * @param startElement
   * @param currentNode
   * @param isHTML
   * @param sbmlElements
   * @param isInsideAnnotation
   * @return
   */
  private ReadingParser processStartElement(StartElement startElement, QName currentNode,
    Boolean isHTML, Stack<Object> sbmlElements, boolean isInsideAnnotation)
  {
    ReadingParser parser = null;

    String elementNamespace = currentNode.getNamespaceURI();

    if (logger.isDebugEnabled()) {
      logger.debug("processStartElement: " + currentNode.getLocalPart() + ", " + elementNamespace);
    }

    // To be able to parse all the SBML file, the sbml node
    // should have been read first.
    if (!sbmlElements.isEmpty() && (initializedParsers != null)) {

      // All the element should have a namespace.
      if (elementNamespace != null) {

        // TODO - change the way we deal with notes, message and annotation and just use the context object ! If XMLNode, we use the 'anyXML' parser
        // it will allow us to deal easily with unknowns XML elements.

        parser = initializedParsers.get(elementNamespace);
        // if the current node is a notes or message element
        // and the matching ReadingParser is a XMLNodeReader,
        // we need to set the typeOfNotes variable of the
        // XMLNodeReader instance.
        if (currentNode.getLocalPart().equals("notes")
            || currentNode.getLocalPart().equals("message")
            || currentNode.getLocalPart().equals("annotation"))
        {
          ReadingParser sbmlparser = initializedParsers.get("anyXML");
          SBase sbmlDoc = (SBase) sbmlElements.firstElement();
          String sbmlNamespace = JSBML.getNamespaceFrom(sbmlDoc.getLevel(), sbmlDoc.getVersion());

          if (sbmlparser instanceof XMLNodeReader && elementNamespace.equals(sbmlNamespace)) {
            // TODO - update only when the top level element from the stack is an SBase ??
            XMLNodeReader notesParser = (XMLNodeReader) sbmlparser;
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

          // if the object on the top of the stack is an XMLNode, we always use the XMLNodeReader
          if (isInsideAnnotation || (sbmlElements.peek() instanceof XMLNode))
          {
            parser = initializedParsers.get("anyXML");
          }

          // All the subNodes of SBML are processed.
          if (!currentNode.getLocalPart().equals("sbml"))
          {
            Object processedElement = parser.processStartElement(currentNode.getLocalPart(),
              currentNode.getNamespaceURI(),
              currentNode.getPrefix(), hasAttributes,
              hasNamespace, sbmlElements.peek());

            if (processedElement != null) {
              // TODO - we won't need this code any more if the list of child is stored directly in the ASTNode facade
              // TODO - try to remove this code and check if the ASTNode2 can still pass the sbml-test-suite
              if (processedElement instanceof ASTNode) {
                ASTNode astNode = (ASTNode) processedElement;
                if (currentNode.getLocalPart().equals("cn") && hasAttributes) {
                  Object object = sbmlElements.peek();

                  while (att.hasNext()) {

                    Attribute attribute = att.next();
                    String attributeName = attribute.getName().getLocalPart();

                    if (attributeName.equals("type")) {
                      String type = attribute.getValue();

                      if (type.equalsIgnoreCase("integer")) {
                        astNode.setType(Type.INTEGER);
                      } else if(type.equalsIgnoreCase("e-notation")) {
                        astNode.setType(Type.REAL_E);
                      } else if(type.equalsIgnoreCase("rational")) {
                        astNode.setType(Type.RATIONAL);
                      }

                      if (object != null && object instanceof ASTNode) {
                        ASTNode parent = (ASTNode) object;

                        // we need to remove the last child as the hierarchy of children are stored in the ASTNode2 and not directly in the ASTNode
                        parent.removeChild(parent.getChildCount() - 1);
                        parent.addChild(astNode);
                      } // else the parent can be directly a MathContainer - nothing to do in this case.
                    }
                  }
                }
                if (currentNode.getLocalPart().equals("csymbol") && hasAttributes) {
                  Object object = sbmlElements.peek();

                  while (att.hasNext()) {

                    Attribute attribute = att.next();
                    String attributeName = attribute.getName().getLocalPart();

                    if (attributeName.equals("definitionURL")) {
                      String type = attribute.getValue();

                      if (type.equalsIgnoreCase(ASTNode.URI_TIME_DEFINITION)) {
                        astNode.setType(Type.NAME_TIME);
                      } else if(type.equalsIgnoreCase(ASTNode.URI_DELAY_DEFINITION)) {
                        astNode.setType(Type.FUNCTION_DELAY);
                      } else if(type.equalsIgnoreCase(ASTNode.URI_AVOGADRO_DEFINITION)) {
                        astNode.setType(Type.NAME_AVOGADRO);
                      } else if(type.equalsIgnoreCase(ASTNode.URI_RATE_OF_DEFINITION)) {
                        astNode.setType(Type.FUNCTION_RATE_OF);
                      }

                      if (object != null && object instanceof ASTNode) {
                        ASTNode parent = (ASTNode) object;

                        // we need to remove the last child as the hierarchy of children are stored in the ASTNode2 and not directly in the ASTNode
                        parent.removeChild(parent.getChildCount() - 1);
                        parent.addChild(astNode);
                      } // else the parent can be directly a MathContainer - nothing to do in this case.
                    }
                  }
                }

                // reset the Iterator of attributes so that they can be processed correctly in #processAttributes(...)
                att = startElement.getAttributes();
              }

              sbmlElements.push(processedElement);
              if (processedElement instanceof TreeNodeWithChangeSupport) {
                ((TreeNodeWithChangeSupport) processedElement).putUserObject(JSBML.READING_IN_PROGRESS, Boolean.TRUE);
              }
            } else {
              // It is normal to have sometimes null returned as some of the
              // XML elements are ignored or do not produce a new java object (like 'apply' in mathML).
            }
          }

          // process the namespaces
          processNamespaces(nam, currentNode,sbmlElements, parser, hasAttributes);

          // Process the attributes
          processAttributes(att, currentNode, sbmlElements, parser, hasAttributes, isInsideAnnotation);

        } else {
          logger.warn(MessageFormat.format("Cannot find a parser for the {0} namespace", elementNamespace));
        }
      } else {
        logger.warn(MessageFormat.format("Cannot find a parser for the {0} namespace", elementNamespace));
      }
    }

    return parser;
  }

  // TODO: the attributes hasAttributes, hasNamespace, isLastAttribute and  isLastNamespace are probably not needed for XML reading.

  /**
   * Process Namespaces of the current element on the stack.
   *
   * @param nam
   * @param currentNode
   * @param sbmlElements
   * @param parser
   * @param hasAttributes
   */
  private void processNamespaces(Iterator<Namespace> nam, QName currentNode,
    Stack<Object> sbmlElements,	ReadingParser parser, boolean hasAttributes)
  {
    ReadingParser namespaceParser = null;

    while (nam.hasNext()) {
      Namespace namespace = nam.next();
      boolean isLastNamespace = !nam.hasNext();
      namespaceParser = initializedParsers.get(namespace.getNamespaceURI());

      logger.debug("processNamespaces: " + namespace.getNamespaceURI());

      // Calling the currentNode parser to store all the declared namespaces
      parser.processNamespace(currentNode.getLocalPart(),
        namespace.getNamespaceURI(),
        namespace.getName().getPrefix(),
        namespace.getName().getLocalPart(),
        hasAttributes, isLastNamespace,
        sbmlElements.peek());

      // Calling each corresponding parser, in case they want to initialize things for the currentNode
      if ((namespaceParser != null) && !namespaceParser.getClass().equals(parser.getClass())) {

        logger.debug("processNamespaces 2e parser: " + namespaceParser);

        namespaceParser.processNamespace(currentNode.getLocalPart(),
          namespace.getNamespaceURI(),
          namespace.getName().getPrefix(),
          namespace.getName().getLocalPart(),
          hasAttributes, isLastNamespace,
          sbmlElements.peek());
      } else if (namespaceParser == null) {
        // These namespaces would be treated by the anyXML parser
        logger.warn(MessageFormat.format("Cannot find a parser for the {0} namespace", namespace.getNamespaceURI()));
      }
    }

  }

  /**
   * Process Attributes of the current element on the stack.
   *
   * @param att
   * @param currentNode
   * @param sbmlElements
   * @param parser
   * @param hasAttributes
   * @param isInsideAnnotation
   */
  private void processAttributes(Iterator<Attribute> att, QName currentNode,
    Stack<Object> sbmlElements, ReadingParser parser, boolean hasAttributes,
    boolean isInsideAnnotation)
  {
    ReadingParser attributeParser = null;

    while (att.hasNext()) {

      Attribute attribute = att.next();
      boolean isLastAttribute = !att.hasNext();
      QName attributeName = attribute.getName();

      if (attribute.getName().getNamespaceURI().length() > 0) {
        String attributeNamespaceURI = attribute.getName().getNamespaceURI();

        if (isInsideAnnotation)
        {
          attributeParser = initializedParsers.get("anyXML");
        }
        else
        {
          attributeParser = initializedParsers.get(attributeNamespaceURI);
        }

      } else {
        attributeParser = parser;
      }

      if (attributeParser != null) {
        boolean isAttributeRead = attributeParser.processAttribute(
          currentNode.getLocalPart(),
          attributeName.getLocalPart(),
          attribute.getValue(),
          attributeName.getNamespaceURI(),
          attributeName.getPrefix(),
          isLastAttribute, sbmlElements.peek());

        if (!isAttributeRead) {
          // store the unknownAttribute
          AbstractReaderWriter.processUnknownAttribute(attributeName.getLocalPart(), attributeName.getNamespaceURI(),
            attribute.getValue(), attributeName.getPrefix(), sbmlElements.peek());
        }

      } else {
        logger.warn("Cannot find a parser for the " + attribute.getName().getNamespaceURI() + " namespace");
      }
    }
  }


  /**
   * Process the end of an element.
   *
   * @param currentNode
   * @param isNested
   * @param isText
   * @param isHTML
   * @param level
   * @param version
   * @param parser
   * @param sbmlElements
   * @param isInsideAnnotation
   * @return
   */
  private SBMLDocument processEndElement(QName currentNode, Boolean isNested, Boolean isText,
    Boolean isHTML, int level, int version, ReadingParser parser,
    Stack<Object> sbmlElements, boolean isInsideAnnotation)
  {
    if (logger.isDebugEnabled()) {
      logger.debug("event.isEndElement: stack.size = " + sbmlElements.size());
      logger.debug("event.isEndElement: element name = " + currentNode.getLocalPart());

      if (currentNode.getLocalPart().equals("kineticLaw") || currentNode.getLocalPart().startsWith("listOf")
          || currentNode.getLocalPart().equals("math")) {
        logger.debug("event.isEndElement: stack = " + sbmlElements);
      }
    }
    // check that the stack did not increase before and after an element ?

    if (initializedParsers != null) {
      String elementNamespaceURI = currentNode.getNamespaceURI();
      parser = initializedParsers.get(elementNamespaceURI);

      if (isInsideAnnotation)
      {
        parser = initializedParsers.get("anyXML");
      }

      // process the end of the element.
      if (!sbmlElements.isEmpty() && (parser != null)) {

        if (logger.isDebugEnabled()) {
          logger.debug("event.isEndElement: calling parser.processEndElement " + parser.getClass());
        }

        boolean popElementFromTheStack = parser.processEndElement(currentNode.getLocalPart(),
          currentNode.getPrefix(), isNested, sbmlElements.peek());
        // remove the top of the SBMLElements stack at the
        // end of an element if this element is not the sbml
        // element.
        if (!currentNode.getLocalPart().equals("sbml")) {
          if (popElementFromTheStack) {
            sbmlElements.pop();
          }

          // System.out.println("SBMLReader: event.isEndElement: new stack.size = "
          // + SBMLElements.size());

        } else {

          logger.debug("event.isEndElement: sbml element found");

          // process the end of the document and return
          // the final SBMLDocument
          if (sbmlElements.peek() instanceof SBMLDocument) {
            SBMLDocument sbmlDocument = (SBMLDocument) sbmlElements.peek();

            Iterator<Entry<String, ReadingParser>> iterator = initializedParsers.entrySet().iterator();
            List<String> readingParserClasses = new ArrayList<String>();

            // Calling endDocument for all parsers
            while (iterator.hasNext()) {
              Entry<String, ReadingParser> entry = iterator.next();
              ReadingParser sbmlParser = entry.getValue();

              if (!readingParserClasses.contains(sbmlParser.getClass().getCanonicalName())) {

                readingParserClasses.add(sbmlParser.getClass().getCanonicalName());

                logger.debug("event.isEndElement: EndDocument found: parser = " + sbmlParser.getClass());

                sbmlParser.processEndDocument(sbmlDocument);

                // call endDocument only on the parser associated with the namespaces
                // declared on the sbml document ??.
              }
            }

            logger.debug("event.isEndElement: EndDocument returned.");

            return sbmlDocument;

          } else {
            // At the end of a sbml node, the
            // SBMLElements stack must contain only a
            // SBMLDocument instance.
            // Otherwise, there is a syntax error in the
            // SBML document
            logger.warn("!!! event.isEndElement: there is a problem in your SBML file !!!!");
            logger.warn("Found an element '" + sbmlElements.peek().getClass().getCanonicalName() +
              "', expected " + SBMLDocument.class.getCanonicalName());
          }
        }
      } else {
        // If SBMLElements.isEmpty => there is a syntax
        // error in the SBMLDocument
        // If parser == null => there is no parser for
        // the namespace of this element
        logger.warn("!!! event.isEndElement: there is a problem in your SBML file !!!!");
        logger.warn("This should never happen, there is probably a problem with the parsers used." +
            "\n Try to check if one needed parser is missing or if you are using a parser in development.");
      }
    } else {
      // The initialized parsers map should be
      // initialized as soon as there is a sbml node.
      // If it is null, there is an syntax error in the SBML
      // file.
      logger.warn("The parsers are not initialized, this should not happen !!!");
    }

    // We return null as long as we did not find the SBMLDocument closing tag
    return null;
  }

}
