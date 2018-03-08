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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;

/**
 * A MathMLStaxParser is used to parse the MathML expressions injected into a SBML
 * file. The name space URI of this parser is
 * "http://www.w3.org/1998/Math/MathML". This parser is able to read and write
 * MathML expressions (implements ReadingParser and WritingParser).
 *
 * @author Nicolas Rodriguez
 * @since 0.8
 */
@ProviderFor(ReadingParser.class)
public class MathMLStaxParser implements ReadingParser {


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
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(MathMLStaxParser.class);

  /**
   * 
   */
  public static final String JSBML_MATH_COUNT = "jsbml.math.element.count";
  
  /**
   * 
   */
  public static final String JSBML_SEMANTICS_COUNT = "jsbml.semantics.element.count";

  /**
   *
   */
  private boolean lastElementWasApply;

  /**
   *
   */
  private boolean isFunctionDefinition;
  
  /**
   * integer used to count the number of piecewise elements open
   */
  private int piecewiseCount;
  
  /**
   * list used to count the number of piece elements open related to a piecewise element.
   * <p>The size of the list should be equals to piecewiseCount.
   */
  private ArrayList<Integer> piecewisePieceCount = new ArrayList<Integer>();
  
  /**
   * list used to count the number of otherwise elements open related to a piecewise element.
   * <p>The size of the list should be equals to piecewiseCount.
   */
  private ArrayList<Integer> piecewiseOtherwiseCount = new ArrayList<Integer>();
  
  /**
   * Returns the indent
   * 
   * @return the indent
   */
  public int getIndent() {
    return indent;
  }

  /**
   * Returns true if indenting
   * 
   * @return true if indenting
   */
  public boolean getIndenting() {
    return indenting;
  }


  /**
   * Returns true if we need to omit the XML declaration.
   * 
   * @return true if we need to omit the XML declaration.
   */
  public boolean getOmitXMLDeclaration() {
    return omitXMLDeclaration;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String ElementName, String AttributeName, String value, String prefix, boolean isLastAttribute, Object contextObject)
   */
  @Override
  public boolean processAttribute(String elementName, String attributeName,
    String value, String uri, String prefix, boolean isLastAttribute,
    Object contextObject) {
    // Process the possible attributes.
    // the sbml:units attribute is handle by the SBMLCoreParser.

    // We are inside a mathML 'semantics' element, everything is read into an XMLNode
    if (contextObject instanceof XMLNode) {
      XMLNode xmlNode = (XMLNode) contextObject;
      xmlNode.addAttr(attributeName, value, uri, prefix);
      return true;
    }
    
    if (! (contextObject instanceof ASTNode)) {
      logger.debug("processAttribute : !!!!!!!!! context is not an ASTNode (" +
          contextObject.getClass());
      return false;
    }

    ASTNode astNode = (ASTNode) contextObject;

    // System.out.println("MathMLStaxParser : processAttribute called");
    // System.out.println("MathMLStaxParser : processAttribute : element name = " + elementName + ", attribute name = " + attributeName +
    // ", value = " + value + ", prefix = " + prefix + ", " + isLastAttribute + ", " + contextObject);

    // Possible value : type, id, style, class, encoding, definitionURL ...
    if (attributeName.equals("definitionURL")) {
      // astNode.setType(value);  // Done in SBMLReader
      astNode.setDefinitionURL(value);
    }

    // if attributeName.equals("type") // Done in SBMLReader - astNode.setType(value);
    
    if (attributeName.equals("id")) {
      astNode.setId(value);
    } else if (attributeName.equals("style")) {
      astNode.setStyle(value);
    } else if (attributeName.equals("class")) {
      astNode.setClassName(value);
    } else if (attributeName.equals("encoding")) {
      astNode.setEncoding(value);
    }

    // TODO - need to process all attributes even if we don't know them !!
    // TODO - or we just return false and the SBMLReader will take care of it ?

    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processCharactersOf(String elementName, String characters, Object contextObject)
   */
  @Override
  public void processCharactersOf(String elementName, String characters, Object contextObject) {
    // process the text content of the mathMl node, mainly ci, cn and csymbol should have content

    if (logger.isDebugEnabled()) {
      logger.debug("processCharactersOf called");
      logger.debug("processCharactersOf : element name = " + elementName + ", characters = " + characters);
    }

    // We are inside a mathML 'semantics' element, everything is read into an XMLNode
    if (contextObject instanceof XMLNode) {
      XMLNode xmlNode = (XMLNode) contextObject;
      
      XMLNode textNode = new XMLNode(characters);
      xmlNode.addChild(textNode);
      return;
    }
    
    // Depending of the type of ASTNode, we need to do different things
    if (! (contextObject instanceof ASTNode)) {
      logger.debug("processCharactersOf : !!!!!!!!! context is not an ASTNode (" +
          contextObject.getClass() + ")!!!!!!!!!!");
      return;
    }

    ASTNode astNode = (ASTNode) contextObject;

    if (isFunctionDefinition)
    {
      FunctionDefinition functionDef = null;

      try {
        functionDef = astNode.getParentSBMLObject().getModel().getFunctionDefinition(characters.trim());
      } catch(NullPointerException e) {

        logger.debug("WARNING : cannot recognize properly functionDefinition in mathML block !!!");
      }

      if (logger.isDebugEnabled()) {
        logger.debug("MathMLStaxParser : processCharactersOf : function found !!");
        logger.debug("Model : " + astNode.getParentSBMLObject().getModel() + ", functionDef = " + functionDef);
      }

      if (astNode.getParentSBMLObject().getModel() != null && functionDef == null)
      {
        logger.warn("Cannot recognize functionDefinition with id '" + characters.trim() + "'");
      }

      // astNode.setType(Type.FUNCTION); // Done in processStartElement already.
    }

    if (astNode.isName() || astNode.isFunction()) {
      astNode.setName(characters.trim());
    } else if (astNode.isInteger()) {
      astNode.setValue(StringTools.parseSBMLInt(characters.trim()));
    } else if (astNode.isRational()) {
      if (elementName == null) {
        astNode.setValue(astNode.getNumerator(), StringTools.parseSBMLInt(characters.trim()));
      } else {
        astNode.setValue(StringTools.parseSBMLInt(characters.trim()), 0);
      }
    } else if (astNode.getType().equals(Type.REAL_E)) {
      if (elementName == null) {
        astNode.setValue(astNode.getMantissa(), StringTools.parseSBMLInt(characters.trim()));
      } else {
        astNode.setValue(StringTools.parseSBMLDouble(characters.trim()), 0);
      }
    } else if (astNode.isReal()) {
      astNode.setValue(Double.valueOf(characters.trim()));
    } else if (astNode.getType().equals(Type.FUNCTION_DELAY)) {
      astNode.setName(characters.trim());
    } else {
      logger.warn("processCharactersOf : !!!!!!!!! I don't know what to do with that : " +
          elementName + " !!!!!!!!!!");
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
   */
  @Override
  public void processEndDocument(SBMLDocument sbmlDocument) {

    // System.out.println("MathMLStaxParser : processEndDocument called");

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String ElementName, String prefix, boolean isNested, Object contextObject)
   */
  @Override
  public boolean processEndElement(String elementName, String prefix,
    boolean isNested, Object contextObject) {

    if (logger.isDebugEnabled()) {
      logger.debug("processEndElement called");
      logger.debug("processEndElement : element name = " + elementName);
    }

    // We are inside a mathML 'semantics' element, everything is read into an XMLNode
    if (contextObject instanceof XMLNode) {
      XMLNode xmlNode = (XMLNode) contextObject;

      if (xmlNode.getChildCount() == 0) {
        xmlNode.setEnd();
      }
      
      // if elementName.equals(semantics) add all child elements as semanticsAnnotation to the parent ASTNode !
      if (elementName.equals("annotation") || elementName.equals("annotation-xml")) 
      {
        TreeNode parentNode = xmlNode.getParent();
        
        if (parentNode instanceof ASTNode) 
        {
          // we are at the end of the annotation or annotation-xml elements.
          ((ASTNode) parentNode).addSemanticsAnnotation(xmlNode);
          
          // System.out.println("MathMLStaxParser - processEndElement - num semantic annotation = " + ((ASTNode) parentNode).getNumSemanticsAnnotations());
        }
        
      }
      
      // always remove the XMLNode from the stack.
      return true;
    }
    
    // we don't change pieceCount or otherwiseCount here as the id might not be different.
    if (elementName.equals("piecewise")){
      piecewiseCount--;
      if (piecewisePieceCount.size() > piecewiseCount) {
        piecewisePieceCount.remove(piecewiseCount);
      }
      if (piecewiseOtherwiseCount.size() > piecewiseCount) {
        piecewiseOtherwiseCount.remove(piecewiseCount);
      }
    }

    if (elementName.equals("sep")) {
      return false;
    } else if (contextObject instanceof MathContainer) {
      //      try {
      //        logger.debug("processEndElement : formula = " + ((MathContainer) contextObject).getMath());
      //      } catch (Exception e) {
      //        logger.debug("Exception while reading mathML: " + e.getLocalizedMessage());
      //      }
      return false;

    } else if (contextObject instanceof ASTNode) {
      ASTNode astNode = (ASTNode) contextObject;

      // add other type of ASTNode in the test ??
      if ((astNode.isFunction() || astNode.isOperator() || astNode.isRelational() ||
          astNode.isLogical()) && !elementName.equals("apply") && !elementName.equals("piecewise")
          && !elementName.equals("lamdba") && !astNode.getType().equals(ASTNode.Type.LAMBDA)
          && !elementName.equals("semantics"))
      {
        if (logger.isDebugEnabled()) {
          logger.debug("processEndElement : stack stay the same. ASTNode type = " + astNode.getType());
        }
        return false;

      }
    }

    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix, String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject)
   */
  @Override
  public void processNamespace(String elementName, String uri, String prefix,
    String localName, boolean hasAttributes, boolean isLastNamespace,
    Object contextObject)
  {
    
    if (contextObject instanceof XMLNode) {

      XMLNode xmlNode = (XMLNode) contextObject;
      
      if (!xmlNode.isStart()) {
        logger.debug(MessageFormat.format(
          "processNamespace: context Object is not a start node! {0}",
          contextObject));
      }
      if (localName == null || localName.trim().length() == 0) {
        localName = "xmlns";
      }

      xmlNode.addNamespace(uri, localName);
    }
    
    // TODO - store namespaces on mathML elements !

    logger.debug("processNamespace called");

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String ElementName, String prefix, boolean hasAttributes, boolean hasNamespaces, Object contextObject)
   */
  @Override
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

    if (logger.isDebugEnabled()) {
      logger.debug("processStartElement called");
      logger.debug("processStartElement: element name = " + elementName); // +
      // ", prefix = " + prefix + ", hasAttributes = " + hasAttributes + ", hasNamespace = " + hasNamespaces);
      // + ", " + contextObject);
    }

    if (elementName.equals("math") || elementName.equals("apply") || elementName.equals("sep")
        || elementName.equals("piece") || elementName.equals("otherwise")
        || elementName.equals("bvar") || elementName.equals("degree") || elementName.equals("logbase")
        || elementName.equals("semantics"))
    {
      if (elementName.equals("apply"))
      {
        lastElementWasApply = true; // TODO - write the booleans inside a user object to make the parser multi-thread safe ?
      }
      else
      {
        lastElementWasApply = false;
      }
      
      if (elementName.equals("math")) {
        processMathElement(contextObject, JSBML_MATH_COUNT, elementName);
      }
      if (elementName.equals("semantics")) {
        processMathElement(contextObject, JSBML_SEMANTICS_COUNT, elementName);
      }

      // trying to count the piecewise, piece and otherwise open elements to annotate the ASTNode with the counter,
      //  then later we can check that each piece block has 2 and only 2 child.      
      if (elementName.equals("piece")) {
        int pieceCount = piecewisePieceCount.get(piecewiseCount - 1);
        pieceCount++;
        piecewisePieceCount.set(piecewiseCount - 1, pieceCount);
      }
      if (elementName.equals("otherwise")) {
        int otherwiseCount = piecewiseOtherwiseCount.get(piecewiseCount - 1);
        otherwiseCount++;
        piecewiseOtherwiseCount.set(piecewiseCount - 1, otherwiseCount);
      }

      // we do nothing
      return null;
    }

    if (elementName.equals("piecewise")) {
      piecewiseCount++;
      piecewisePieceCount.add(0);
      piecewiseOtherwiseCount.add(0);
    }
    
    if (lastElementWasApply && elementName.equals("ci"))
    {
      isFunctionDefinition = true;
    }
    else
    {
      isFunctionDefinition = false;
    }
    lastElementWasApply = false;

    MathContainer mathContainer = null;
    ASTNode parentASTNode = null;
    boolean setMath = false;

    if (contextObject instanceof MathContainer) {
      mathContainer = (MathContainer) contextObject;
      if (mathContainer.getMath() == null) {
        setMath = true;
      } else {
        // because normal operator are written <operator/> in mathML and so the parent ASTNode is not any more the contextObject
        parentASTNode = mathContainer.getMath();
        // System.out.println("MathMLStaxParser: processStartElement parent type: " + parentASTNode.getType());
      }
      
      if (elementName.equals("annotation") || elementName.equals("annotation-xml")) 
      {
        // Creating a StartElement XMLNode !!
        XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, uri, prefix), new XMLAttributes(), new XMLNamespaces());
        xmlNode.setParent(parentASTNode);
        
        return xmlNode;
      }

    } else if (contextObject instanceof ASTNode) {

      parentASTNode = ((ASTNode) contextObject);
      mathContainer = parentASTNode.getParentSBMLObject();
      // System.out.println("MathMLStaxParser: processStartElement parent type: " + parentASTNode.getType());
      
      // if semantics, create a new XMLNode and return it. Set the ASTNode as the parent so that
      // we can add the semanticsAnnotation element in the #processEndElement method.
      if (elementName.equals("annotation") || elementName.equals("annotation-xml")) 
      {
        // Creating a StartElement XMLNode !!
        XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, uri, prefix), new XMLAttributes(), new XMLNamespaces());
        xmlNode.setParent(parentASTNode);
        
        return xmlNode;
      }
    }    
    // We are inside a mathML 'semantics' element, everything is read into an XMLNode    
    else if (contextObject instanceof XMLNode) 
    {
      // Creating a StartElement XMLNode !!
      XMLNode xmlNode = new XMLNode(new XMLTriple(elementName, uri, prefix), new XMLAttributes(), new XMLNamespaces());
      XMLNode parentNode = (XMLNode) contextObject;

      parentNode.addChild(xmlNode);
      
      return xmlNode;
    }
    else 
    {
      // Should never happen
      logger.debug("processStartElement: !!!!!!!!!!! Should not have been here !!!!!!!!!!!");
      logger.debug("processStartElement: contextObject.classname = " + contextObject.getClass().getName());
      return null;
    }

    ASTNode astNode = new ASTNode();

    if (isFunctionDefinition) {
      astNode.setType(Type.FUNCTION);
    } else {
      astNode.setType(elementName);
    }
    
    if (piecewiseCount > 0) {
      // add piecewiseCount.pieceCount or/and piecewiseCount.otherwiseCount to the ASTNode
      int otherwiseCount = piecewiseOtherwiseCount.get(piecewiseCount - 1);
      int pieceCount = piecewisePieceCount.get(piecewiseCount - 1);
      int localPiecewiseCount = piecewiseCount;
      
      if (elementName.equals("piecewise") && piecewiseCount > 1) {
        // This is to take into account the special case where piecewise is a direct child of piece or otherwise
        // in this case, piecewiseCount and the list have been increase already but we want to tag the piecewise 
        // element with the previous values
        otherwiseCount = piecewiseOtherwiseCount.get(piecewiseCount - 2);
        pieceCount = piecewisePieceCount.get(piecewiseCount - 2);
        localPiecewiseCount--;
      }
      
      if (otherwiseCount > 0) {
        astNode.putUserObject(JSBML.PIECEWISE_ID, "otherwise." + localPiecewiseCount + "." + otherwiseCount);
      } else {
        astNode.putUserObject(JSBML.PIECEWISE_ID, "piece." + localPiecewiseCount + "." + pieceCount);
      }
    }
    
    if (setMath) {
      mathContainer.setMath(astNode);
    } else {
      parentASTNode.addChild(astNode);
    }

    return astNode;
  }

  /**
   * Process a {@link MathContainer} to add one to the number of element encountered.
   * 
   * @param contextObject a {@link MathContainer} instance
   */
  private void processMathElement(Object contextObject, String userObjectKey, String elementName) {
    
    MathContainer mathContainer = null;

    if (contextObject instanceof MathContainer) {
      mathContainer = (MathContainer) contextObject;
    } else if (contextObject instanceof ASTNode) {

      mathContainer = ((ASTNode) contextObject).getParentSBMLObject();
    }
    
    if (mathContainer != null 
        && (mathContainer instanceof InitialAssignment || mathContainer instanceof Rule 
            || mathContainer instanceof Constraint || mathContainer instanceof KineticLaw
            || mathContainer instanceof Trigger || mathContainer instanceof Delay
            || mathContainer instanceof EventAssignment || mathContainer instanceof Priority
            || mathContainer instanceof FunctionDefinition || !mathContainer.getPackageName().equals("core"))) 
    {
      int nbElement = (int) ((mathContainer.isSetUserObjects() && mathContainer.getUserObject(userObjectKey) != null) ? ((Number) mathContainer.getUserObject(userObjectKey)).intValue() : 0);
      nbElement++;
      
      mathContainer.putUserObject(userObjectKey, nbElement);
      
      if (mathContainer instanceof Constraint || mathContainer instanceof KineticLaw) {
        AbstractReaderWriter.storeElementsOrder(elementName, mathContainer);
      }
    }
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
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return namespaces;
  }

  /**
   *
   */
  private static final List<String> namespaces = new ArrayList<String>();

  static {
    namespaces.add(ASTNode.URI_MATHML_DEFINITION);
  }

}
