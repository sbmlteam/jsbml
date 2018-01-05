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
package org.sbml.jsbml.xml.parsers;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * A MathMLParser is used to parse the MathML expressions injected into a SBML
 * file. This class use DOM to parse the mathML. The name space URI of this
 * parser is <a href="http://www.w3.org/1998/Math/MathML">http://www.w3.org/1998/Math/MathML</a>. This parser is able to
 * read and write MathML expressions (implements ReadingParser and
 * WritingParser).
 * 
 * @author Marine Dumousseau
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * 
 * @deprecated this class should not be used anymore, replaced by
 *             {@link MathMLStaxParser}. But we keep it in case somebody want to
 *             use {@link org.w3c.dom.Document}
 * 
 */
@Deprecated
public class MathMLParser implements ReadingParser, WritingParser {

  /**
   * The SBML attribute for the additional namespace.
   */
  private static final String sbmlNS = "xmlns:sbml";

  /**
   * The attribute for SBML units.
   */
  private static final String sbmlUnits = "sbml:units";


  /**
   * Recursively checks whether the given {@link Node} contains any numbers
   * that refer to unit declarations. This check is necessary because this
   * feature has become available in SBML Level 3 and is therefore invalid in
   * earlier levels.
   * 
   * @param node
   *            A node to be checked.
   * @return
   */
  public static boolean checkContainsNumbersReferingToUnits(Node node) {
    boolean containsNumbersReferingToUnits = false;
    int i;
    if (node.getNodeName().equals("cn")) {
      for (i = 0; i < node.getAttributes().getLength(); i++) {
        if (node.getAttributes().item(i).toString().startsWith(
          MathMLParser.getSBMLUnitsAttribute())) {
          return true;
        }
      }
    }
    if (node.hasChildNodes()) {
      for (i = 0; i < node.getChildNodes().getLength(); i++) {
        containsNumbersReferingToUnits |= checkContainsNumbersReferingToUnits(node
          .getChildNodes().item(i));
      }
    }
    return containsNumbersReferingToUnits;
  }

  /**
   * 
   * @param node
   * @param sbmlLevel
   * @return
   * @throws SBMLException
   */
  public static Document createMathMLDocumentFor(Node node, int sbmlLevel)
      throws SBMLException {
    Element math;
    Document document = node.getOwnerDocument();
    if (document.getDocumentElement() == null) {
      math = document.createElementNS(MathMLParser.getNamespaceURI(),
          "math");
      if (checkContainsNumbersReferingToUnits(node)) {
        if ((sbmlLevel < 1) || (sbmlLevel > 2)) {
          math.setAttribute(MathMLParser.getSBMLNSAttribute(),
            MathMLParser.getXMLnamespaceSBML());
        } else {
          // TODO: We could just remove all references to units
          // instead of throwing this exception. What is better? Loss
          // of information or exception?
          throw new SBMLException(
              "math element contains numbers that refer to unit definitions and therefore requires at least SBML Level 3");
        }
      }
      document.appendChild(math);
    } else {
      math = document.getDocumentElement();
      for (int i = math.getChildNodes().getLength() - 1; i >= 0; i--) {
        math.removeChild(math.getChildNodes().item(i));
      }
    }
    math.appendChild(node);
    return document;
  }

  /**
   * 
   * @return
   */
  public static String getDefinitionURIavogadro() {
    return ASTNode.URI_AVOGADRO_DEFINITION;
  }

  /**
   * 
   * @return
   */
  public static String getDefinitionURIdelay() {
    return ASTNode.URI_DELAY_DEFINITION;
  }

  /**
   * 
   * @return
   */
  public static String getDefinitionURItime() {
    return ASTNode.URI_TIME_DEFINITION;
  }

  /**
   * @return the namespaceURI
   */
  public static String getNamespaceURI() {
    return ASTNode.URI_MATHML_DEFINITION;
  }

  /**
   * 
   * @return
   */
  public static String getSBMLNSAttribute() {
    return sbmlNS;
  }

  /**
   * 
   * @return
   */
  public static String getSBMLUnitsAttribute() {
    return sbmlUnits;
  }

  /**
   * The additional MathML name space for units to numbers.
   * @return
   */
  public static String getXMLnamespaceSBML() {
    return SBMLDocument.URI_NAMESPACE_L3V1Core;
  }

  /**
   * Write output as {@link String}.
   * 
   * @param doc
   * @param omitXMLDeclaration
   * @param indenting
   * @param indent
   * @return
   * @throws IOException
   * @throws SBMLException
   *             If illegal references to units are made for numbers and the
   *             SBML Level has been set to values before level 3.
   */
  public static String toMathML(Document doc, boolean omitXMLDeclaration,
    boolean indenting, int indent) throws IOException, SBMLException {
    StringWriter sw = new StringWriter();
    toMathML(sw, doc, omitXMLDeclaration, indenting, indent);
    return sw.toString();
  }

  /**
   * 
   * @param out
   * @param doc
   * @param omitXMLDeclaration
   * @param indenting
   * @param indent
   * @throws IOException
   * @throws SBMLException
   */
  public static void toMathML(Writer out, Document doc,
    boolean omitXMLDeclaration, boolean indenting, int indent)
        throws IOException, SBMLException
  {
    TransformerFactory tfactory = TransformerFactory.newInstance();
    Transformer serializer;
    try {
      serializer = tfactory.newTransformer();
      //Setup indenting to "pretty print"
      serializer.setOutputProperty(OutputKeys.INDENT, "yes");
      serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      serializer.transform(new DOMSource(doc), new StreamResult(out));
    } catch (TransformerException e) {
      // this is fatal, just dump the stack and throw a runtime exception
      e.printStackTrace();

      throw new RuntimeException(e);
    }
  }

  /**
   * The number of white spaces for the indent if this is to be used.
   */
  private short indent;

  /**
   * Decides whether or not the content of the MathML string should be
   * indented.
   */
  private boolean indenting;

  /**
   * If true no XML declaration will be written into the MathML output,
   * otherwise the XML version will appear at the beginning of the output.
   */
  private boolean omitXMLDeclaration;

  /**
   * 
   * @return
   */
  public int getIndent() {
    return indent;
  }

  /**
   * 
   * @return
   */
  public boolean getIndenting() {
    return indenting;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 
   * @return
   */
  public boolean getOmitXMLDeclaration() {
    return omitXMLDeclaration;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String ElementName, String AttributeName, String value, String prefix, boolean isLastAttribute, Object contextObject)
   */
  @Override
  public boolean processAttribute(String ElementName, String AttributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject) {
    // TODO Auto-generated method stub

    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters, Object contextObject)
   */
  @Override
  public void processCharactersOf(String elementName, String characters,
    Object contextObject) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String ElementName, String prefix, boolean isNested, Object contextObject)
   */
  @Override
  public boolean processEndElement(String ElementName, String prefix,
    boolean isNested, Object contextObject) {
    // TODO Auto-generated method stub
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix, String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject)
   */
  @Override
  public void processNamespace(String elementName, String URI, String prefix,
    String localName, boolean hasAttributes, boolean isLastNamespace,
    Object contextObject) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String ElementName, String prefix, boolean hasAttributes, boolean hasNamespaces, Object contextObject)
   */
  @Override
  public Object processStartElement(String ElementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @param indent
   *            the indent to set
   */
  public void setIndent(int indent) {
    if ((indent < 0) || (Short.MAX_VALUE < indent)) {
      throw new IllegalArgumentException(MessageFormat.format(
        "indent {0,number,integer} is out of the range [0, {1,number,integer}].",
        indent, Short.toString(Short.MAX_VALUE)));
    }
    this.indent = (short) indent;
  }

  /**
   * @param indenting
   *            the indenting to set
   */
  public void setIndenting(boolean indenting) {
    this.indenting = indenting;
  }

  /**
   * @param omitXMLDeclaration
   *            the omitXMLDeclaration to set
   */
  public void setOmitXMLDeclaration(boolean omitXMLDeclaration) {
    this.omitXMLDeclaration = omitXMLDeclaration;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.writeAttributes(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeAttributes(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#writeCharacters(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeCharacters(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#writeNamespaces(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
   */
  @Override
  public void writeNamespaces(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    // TODO Auto-generated method stub
    return null;
  }

}
