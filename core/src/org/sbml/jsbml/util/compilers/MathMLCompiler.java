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
package org.sbml.jsbml.util.compilers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.MathMLParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * With this compiler, an {@link ASTNode} can be transformed into a MathML
 * string.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @deprecated use {@link MathMLXMLStreamCompiler} instead.
 */
@Deprecated
public class MathMLCompiler implements ASTNodeCompiler {

  /**
   * XML document for the output.
   */
  private Document document;

  /**
   * The last top {@link Element} that has been created in the previous step.
   */
  private Element lastElementCreated;

  /**
   * The SBML level to be used in this class.
   */
  private int level;

  /**
   * 
   * @throws XMLStreamException
   */
  public MathMLCompiler() throws XMLStreamException {
    init();
  }

  /**
   * 
   * @param ast
   * @throws XMLStreamException
   * @throws SBMLException
   */
  public MathMLCompiler(ASTNode ast) throws XMLStreamException, SBMLException {
    this();
    ast.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("abs", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> values) throws DOMException,
  SBMLException {
    if (values.size() > 0) {
      return createApplyNode("and", values);
    }
    throw new IllegalArgumentException(
        "Cannot create a node for empty element list");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arccos", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccosh(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arccosh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccot(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arccot", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccoth(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arccoth", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsc(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arccsc", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsch(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arccsch", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsec(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsec", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsech(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsech", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsin(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsin", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsinh(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsinh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctan(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arctan", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctanh(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("arctanh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ceiling(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("ceiling", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public ASTNodeValue compile(Compartment c) {
    return compile((CallableSBase) c);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double mantissa, int exponent, String units) {
    return new ASTNodeValue(createCnElement(mantissa, exponent, units),
      this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double real, String units) {
    return new ASTNodeValue(createCnElement(Double.valueOf(real), units),
      this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(int integer, String units) {
    return new ASTNodeValue(
      createCnElement(Integer.valueOf(integer), units), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNodeValue compile(CallableSBase variable) {
    setLevel(variable.getLevel());
    return compile(variable.getId());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  public ASTNodeValue compile(String name) {
    return createCiElement(name);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cos(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("cos", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cosh(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("cosh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cot(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("cot", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue coth(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("coth", value);
  }

  /**
   * 
   * @param tag
   * @param childNodes
   * @return
   * @throws DOMException
   * @throws SBMLException
   */
  private ASTNodeValue createApplyNode(Node tag, ASTNodeValue... childNodes)
      throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(tag);
    for (ASTNodeValue n : childNodes) {
      lastElementCreated.appendChild(n.toNode());
    }
    if (childNodes.length > 0) {
      setLevel(childNodes[0].getLevel());
    }
    return new ASTNodeValue(lastElementCreated, this);
  }

  /**
   * Creates an apply {@link Node} with the given tag {@link Node} as its
   * child and {@link ASTNodeValue}s on the same level as the tag {@link Node}
   * .
   * 
   * @param tag
   * @param childNodes
   * @return
   * @throws SBMLException
   * @throws DOMException
   */
  private ASTNodeValue createApplyNode(Node tag, List<ASTNode> childNodes)
      throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(tag);
    for (ASTNode n : childNodes) {
      lastElementCreated.appendChild(n.compile(this).toNode());
    }
    if (childNodes.size() > 0) {
      setLevel(childNodes.get(0).getParentSBMLObject().getLevel());
    }
    return new ASTNodeValue(lastElementCreated, this);
  }

  /**
   * 
   * @param tagName
   * @param childNodes
   * @return
   * @throws DOMException
   * @throws SBMLException
   */
  private ASTNodeValue createApplyNode(String tagName, ASTNode... childNodes)
      throws DOMException, SBMLException {
    List<ASTNode> l = new ArrayList<ASTNode>();
    for (ASTNode ast : childNodes) {
      l.add(ast);
    }
    return createApplyNode(tagName, l);
  }

  /**
   * 
   * @param tagName
   * @param childNodes
   * @return
   * @throws DOMException
   * @throws SBMLException
   */
  private ASTNodeValue createApplyNode(String tagName,
    ASTNodeValue... childNodes) throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(document.createElement(tagName));
    for (ASTNodeValue value : childNodes) {
      lastElementCreated.appendChild(value.toNode());
    }
    if (childNodes.length > 0) {
      setLevel(childNodes[0].getLevel());
    }
    return new ASTNodeValue(lastElementCreated, this);
  }

  /**
   * Creates a {@link Node} with the tag "apply" and adds the given
   * {@link Node} as its child. Then it creates a new {@link ASTNodeValue}
   * that wraps the apply {@link Node}.
   * 
   * @param tagName
   *            The name of the node to be created, e.g., "abs" or "and".
   * @param childNodes
   *            at least one child should be passed to this method.
   * @return
   * @throws SBMLException
   * @throws DOMException
   */
  private ASTNodeValue createApplyNode(String tagName,
    List<ASTNode> childNodes) throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(document.createElement(tagName));
    for (ASTNode value : childNodes) {
      lastElementCreated.appendChild(value.compile(this).toNode());
    }
    if (childNodes.size() > 0) {
      setLevel(childNodes.get(0).getParentSBMLObject().getLevel());
    }
    return new ASTNodeValue(lastElementCreated, this);
  }

  /**
   * 
   * @param name
   * @return
   */
  private ASTNodeValue createCiElement(String name) {
    lastElementCreated = document.createElement("ci");
    lastElementCreated.setNodeValue(writeName(name.trim()));
    //lastElementCreated.setTextContent(writeName(name.trim()));
    return new ASTNodeValue(lastElementCreated, this);
  }

  /**
   * 
   * @param mantissa
   * @param exponent
   * @param units
   *            Can be {@code null}.
   * @return
   */
  private Element createCnElement(double mantissa, int exponent, String units) {
    lastElementCreated = document.createElement("cn");
    lastElementCreated.setAttribute("type", "e-notation");
    if ((units != null) && (level > 2)) {
      lastElementCreated.setAttribute(MathMLParser
        .getSBMLUnitsAttribute(), units);
    }
    lastElementCreated.appendChild(document.createTextNode(writeName(Double
      .valueOf(mantissa))));
    lastElementCreated.appendChild(document.createElement("sep"));
    lastElementCreated.appendChild(document
      .createTextNode(writeName(Integer.valueOf(exponent))));
    return lastElementCreated;
  }

  /**
   * 
   * @param value
   * @param units
   *            Can be {@code null}.
   * @return
   */
  private Element createCnElement(Number value, String units) {
    lastElementCreated = document.createElement("cn");
    if ((units != null) && (level > 2)) {
      lastElementCreated.setAttribute(MathMLParser
        .getSBMLUnitsAttribute(), units);
    }
    String type = value instanceof Integer ? "integer" : "real";
    lastElementCreated.setAttribute("type", type);
    lastElementCreated.setNodeValue(writeName(value));
    //lastElementCreated.setTextContent(writeName(value));
    return lastElementCreated;
  }

  /**
   * Creates an {@link ASTNodeValue} with the single node as defined by the
   * tagName.
   * 
   * @param tagName
   * @return
   */
  private ASTNodeValue createConstant(String tagName) {
    lastElementCreated = document.createElement(tagName);
    return new ASTNodeValue(lastElementCreated, this);
  }

  /**
   * 
   * @param name
   * @param definitionURI
   * @return
   */
  private Element createCSymbol(String name, String definitionURI) {
    lastElementCreated = document.createElement("csymbol");
    lastElementCreated.setAttribute("encoding", "text");
    lastElementCreated.setAttribute("definitionURL", definitionURI);
    lastElementCreated.setNodeValue(writeName(name));
    //lastElementCreated.setTextContent(writeName(name));
    return lastElementCreated;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csc(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("csc", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csch(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("csch", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode, double, java.lang.String)
   */
  @Override
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
    String timeUnit) throws DOMException, SBMLException {
    setLevel(x.getParentSBMLObject().getLevel());
    return createApplyNode(createCSymbol(delayName, MathMLParser
      .getDefinitionURIdelay()), compile(x.toString()), compile(delay.toString()));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#eq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws DOMException,
  SBMLException {
    return createApplyNode("eq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue exp(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("exp", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue factorial(ASTNode value) throws DOMException,
  SBMLException {
    return createApplyNode("factorial", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue floor(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("floor", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws DOMException, SBMLException {
    return createApplyNode("divide", numerator, denominator);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(int, int)
   */
  @Override
  public ASTNodeValue frac(int numerator, int denominator)
      throws DOMException, SBMLException {
    return createApplyNode("divide", compile(numerator, null), compile(
      denominator, null));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public ASTNodeValue function(FunctionDefinition functionDefinition,
    List<ASTNode> args) throws DOMException, SBMLException {
    return createApplyNode(compile(functionDefinition).toNode(), args);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public ASTNodeValue function(String functionDefinitionName,
    List<ASTNode> args) throws SBMLException {
    return createApplyNode(compile(functionDefinitionName).toNode(), args);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#geq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws DOMException,
  SBMLException {
    return createApplyNode("geq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    return new ASTNodeValue(createCSymbol(name, MathMLParser
      .getDefinitionURIavogadro()), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    return createConstant("exponentiale");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public ASTNodeValue getConstantFalse() {
    return createConstant(Boolean.FALSE.toString());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public ASTNodeValue getConstantPi() {
    return createConstant("pi");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantTrue()
   */
  @Override
  public ASTNodeValue getConstantTrue() {
    return createConstant(Boolean.TRUE.toString());
  }

  /**
   * @return the document
   * @throws SBMLException
   */
  public Document getDocument() throws SBMLException {
    return MathMLParser.createMathMLDocumentFor(lastElementCreated, level);
  }

  /**
   * @return the level
   */
  public int getLevel() {
    return level;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getNegativeInfinity()
   */
  @Override
  public ASTNodeValue getNegativeInfinity() throws DOMException,
  SBMLException {
    return uMinus(getPositiveInfinity());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public ASTNodeValue getPositiveInfinity() {
    return createConstant("infinity");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#gt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws DOMException,
  SBMLException {
    return createApplyNode("gt", left, right);
  }

  /**
   * Re-initializes the {@link Document} object belonging to this class. Only
   * in this way a new {@link ASTNode} tree can be compiled. To this end, also
   * the level attribute is reset (because maybe next time we want to compile
   * some {@link ASTNode} with from a different source.
   * 
   * @throws XMLStreamException
   */
  private void init() throws XMLStreamException {
    level = -1;
    try {
      // create document
      DocumentBuilderFactory factory = DocumentBuilderFactory
          .newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder builder;
      builder = factory.newDocumentBuilder();
      document = builder.newDocument();
    } catch (ParserConfigurationException e) {
      throw new XMLStreamException(e);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lambda(java.util.List)
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> values) throws DOMException,
  SBMLException {
    lastElementCreated = document.createElement("lambda");
    if (values.size() > 0) {
      setLevel(values.get(0).getParentSBMLObject().getLevel());
      if (values.size() > 1) {
        for (int i = 0; i < values.size() - 1; i++) {
          Element bvar = document.createElement("bvar");
          lastElementCreated.appendChild(bvar);
          bvar.appendChild(values.get(i).compile(this).toNode());
        }
      }
      lastElementCreated.appendChild(values.get(values.size() - 1)
        .compile(this).toNode());
    }
    return new ASTNodeValue(lastElementCreated, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#leq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws DOMException,
  SBMLException {
    return createApplyNode("leq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("ln", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode value) throws DOMException, SBMLException {
    return log(null, value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode base, ASTNode value) throws DOMException,
  SBMLException {
    ASTNodeValue v = createApplyNode("log", value);
    if (base != null) {
      Element logbase = document.createElement("logbase");
      logbase.appendChild(base.compile(this).toNode());
      v.toNode().insertBefore(logbase,
        v.toNode().getFirstChild().getNextSibling());
    }
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws DOMException,
  SBMLException {
    return createApplyNode("lt", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#minus(java.util.List)
   */
  @Override
  public ASTNodeValue minus(List<ASTNode> values) throws DOMException,
  SBMLException {
    return createApplyNode("minus", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#neq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws DOMException,
  SBMLException {
    return createApplyNode("neq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue not(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("not", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#or(java.util.List)
   */
  @Override
  public ASTNodeValue or(List<ASTNode> values) throws DOMException,
  SBMLException {
    return createApplyNode("or", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> values) throws DOMException,
  SBMLException {
    if (values.size() > 0) {
      setLevel(values.get(0).getParentSBMLObject().getLevel());
      lastElementCreated = document.createElement("apply");
      Element tag = document.createElement("piecewise");
      lastElementCreated.appendChild(tag);
      Element piece = null;
      for (int i = 0; i < values.size(); i++) {
        if ((i % 2) == 0) {
          piece = document.createElement("piece");
          tag.appendChild(piece);
        } else if (i == values.size() - 1) {
          piece = document.createElement("otherwise");
          tag.appendChild(piece);
        }
        if (piece != null) {
          piece.appendChild(values.get(i).compile(this).toNode());
        }
      }
      return new ASTNodeValue(lastElementCreated, this);
    }
    throw new IllegalArgumentException(
        "Cannot create piecewise function with empty argument list");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#plus(java.util.List)
   */
  @Override
  public ASTNodeValue plus(List<ASTNode> values) throws DOMException,
  SBMLException {
    return createApplyNode("plus", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent)
      throws DOMException, SBMLException {
    return createApplyNode("pow", base, exponent);
  }

  /**
   * Allows to re-use this object to compile another {@link ASTNode}. Without
   * reseting, this compiler can only be used one time. Otherwise it cannot be
   * guaranteed that the results will be correct.
   * 
   * @throws XMLStreamException
   */
  public void reset() throws XMLStreamException {
    init();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
      throws DOMException, SBMLException {
    if (rootExponent != null) {
      return root(rootExponent.compile(this), radiant);
    }
    return createApplyNode("root", radiant);
  }

  /**
   * 
   * @param rootExponent
   * @param radiant
   * @return
   * @throws DOMException
   * @throws SBMLException
   */
  private ASTNodeValue root(ASTNodeValue rootExponent, ASTNode radiant)
      throws DOMException, SBMLException {
    Element logbase = document.createElement("degree");
    logbase.appendChild(rootExponent.toNode());
    ASTNodeValue v = createApplyNode("root", radiant);
    Node v_node = v.toNode();

    // TODO : Not working - not correcting it as the mathMl output is wrong anyway and will be re-implemented.
    v_node.insertBefore(logbase, v_node.getFirstChild().getNextSibling());

    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws DOMException, SBMLException {
    ASTNodeValue exponent;
    String dimensionless = (0 < level) && (level < 3) ? null
      : Unit.Kind.DIMENSIONLESS.toString().toLowerCase();
    if (rootExponent - ((int) rootExponent) == 0) {
      exponent = compile((int) rootExponent, dimensionless);
    } else {
      exponent = compile(rootExponent, dimensionless);
    }
    return root(exponent, radiant);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sec(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("sec", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sech(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("sech", value);
  }

  /**
   * @param level
   *            the level to set
   */
  private void setLevel(int level) {
    if ((0 < this.level) && (this.level <= 3)
        && ((level < 0) || (3 < level))) {
      throw new IllegalArgumentException(StringTools.concat(
        "Cannot set level from the valid value ",
        Integer.toString(this.level), " to invalid value ",
        Integer.toString(level)).toString());
    }
    this.level = level;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sin(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("sin", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sinh(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("sinh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sqrt(ASTNode value) throws DOMException, SBMLException {
    return root(2, value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNodeValue symbolTime(String time) {
    return new ASTNodeValue(createCSymbol(time, MathMLParser
      .getDefinitionURItime()), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("tan", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tanh(ASTNode value) throws DOMException, SBMLException {
    return createApplyNode("tanh", value);
  }

  /**
   * Convenient method to perform internal changes.
   * 
   * @param values
   * @return
   * @throws DOMException
   * @throws SBMLException
   */
  private ASTNodeValue times(ASTNodeValue... values) throws DOMException,
  SBMLException {
    return createApplyNode("times", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#times(java.util.List)
   */
  @Override
  public ASTNodeValue times(List<ASTNode> values) throws DOMException,
  SBMLException {
    return createApplyNode("times", values);
  }

  /**
   * 
   * @param omitXMLDeclaration
   * @param indenting
   * @param indent
   * @return
   * @throws IOException
   * @throws SBMLException
   */
  public String toMathML(boolean omitXMLDeclaration, boolean indenting,
    int indent) throws IOException, SBMLException {
    return MathMLParser.toMathML(getDocument(), omitXMLDeclaration,
      indenting, indent);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode value) throws DOMException,
  SBMLException {
    ASTNodeValue v = value.compile(this);
    v.setLevel(value.getParentSBMLObject().getLevel());
    return uMinus(v);
  }

  /**
   * 
   * @param value
   * @return
   * @throws DOMException
   * @throws SBMLException
   */
  private ASTNodeValue uMinus(ASTNodeValue value) throws DOMException,
  SBMLException {
    setLevel(value.getLevel());
    return times(compile(-1, (0 < level) && (level < 3) ? null
      : Unit.Kind.DIMENSIONLESS.toString().toLowerCase()), value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#unknownValue()
   */
  @Override
  public ASTNodeValue unknownValue() throws SBMLException {
    throw new SBMLException(
        "cannot write unknown syntax tree nodes to a MathML document");
  }

  /**
   * Converts the given {@link Object} into a {@link String}. Then it removes
   * leading and tailing white spaces from the given {@link String} and then
   * inserts exactly one white space at the beginning and the end of the given
   * {@link String}.
   * 
   * @param name
   * @return
   */
  private String writeName(Object name) {
    return StringTools.concat(Character.toString(' '),
      name.toString().trim(), Character.toString(' ')).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#xor(java.util.List)
   */
  @Override
  public ASTNodeValue xor(List<ASTNode> values) throws DOMException,
  SBMLException {
    return createApplyNode("xor", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public ASTNodeValue selector(List<ASTNode> nodes) throws SBMLException {
    return function("selector", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector()
   */
  @Override
  public ASTNodeValue vector(List<ASTNode> nodes) throws SBMLException {
    return function("vector", nodes);
  }

  @Override
  public ASTNodeValue max(List<ASTNode> values) {
    return function("max", values);
  }

  @Override
  public ASTNodeValue min(List<ASTNode> values) {
    return function("min", values);
  }

  @Override
  public ASTNodeValue quotient(List<ASTNode> values) {
    return function("quotient", values);
  }

  @Override
  public ASTNodeValue rem(List<ASTNode> values) {
    return function("rem", values);
  }

  @Override
  public ASTNodeValue implies(List<ASTNode> values) {
    return function("implies", values);
  }

  @Override
  public ASTNodeValue getRateOf(ASTNode nameAST) {
    // TODO Auto-generated method stub
    return null;
  }

}
