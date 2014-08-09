/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.math.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.MathMLParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Aug 7, 2014
 */
public class MathMLCompiler extends StringTools implements ASTNode2Compiler {

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
  public MathMLCompiler(ASTNode2 ast) throws XMLStreamException, SBMLException {
    this();
    ast.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#abs(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> abs(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("abs", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#and(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> and(List<ASTNode2> values) throws DOMException,
  SBMLException {
    if (values.size() > 0) {
      return createApplyNode("and", values);
    }
    throw new IllegalArgumentException(
        "Cannot create a node for empty element list");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccos(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccos(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arccos", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccosh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccosh(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arccosh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccot(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccot(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arccot", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccoth(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccoth(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arccoth", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccsc(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsc(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arccsc", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccsch(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsch(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arccsch", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsec(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsec(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsec", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsech(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsech(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsech", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsin(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsin(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsin", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsinh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsinh(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arcsinh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arctan(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctan(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arctan", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arctanh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctanh(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("arctanh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#ceiling(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ceiling(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("ceiling", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public <T> ASTNode2Value<String> compile(Compartment c) {
    return compile((CallableSBase) c);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(double, int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double mantissa, int exponent, String units) {
    return new ASTNode2Value(createCnElement(mantissa, exponent, units),
      this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(double, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double real, String units) {
    return new ASTNode2Value(createCnElement(Double.valueOf(real), units),
      this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(int integer, String units) {
    return new ASTNode2Value(
      createCnElement(Integer.valueOf(integer), units), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public <T> ASTNode2Value<String> compile(CallableSBase variable) {
    setLevel(variable.getLevel());
    return compile(variable.getId());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(String name) {
    return createCiElement(name);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#cos(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cos(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("cos", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#cosh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cosh(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("cosh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#cot(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cot(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("cot", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#coth(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> coth(ASTNode2 value) throws DOMException, SBMLException {
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
  private <T> ASTNode2Value<String> createApplyNode(Node tag, ASTNode2Value... childNodes)
      throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(tag);
    for (ASTNode2Value n : childNodes) {
      lastElementCreated.appendChild(n.toNode());
    }
    if (childNodes.length > 0) {
      setLevel(childNodes[0].getLevel());
    }
    return new ASTNode2Value(lastElementCreated, this);
  }

  /**
   * Creates an apply {@link Node} with the given tag {@link Node} as its
   * child and {@link ASTNode2Value}s on the same level as the tag {@link Node}
   * .
   * 
   * @param tag
   * @param childNodes
   * @return
   * @throws SBMLException
   * @throws DOMException
   */
  private <T> ASTNode2Value<String> createApplyNode(Node tag, List<ASTNode2> childNodes)
      throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(tag);
    for (ASTNode2 n : childNodes) {
      lastElementCreated.appendChild(n.compile(this).toNode());
    }
    if (childNodes.size() > 0) {
      setLevel(childNodes.get(0).getParentSBMLObject().getLevel());
    }
    return new ASTNode2Value(lastElementCreated, this);
  }

  /**
   * 
   * @param tagName
   * @param childNodes
   * @return
   * @throws DOMException
   * @throws SBMLException
   */
  private <T> ASTNode2Value<String> createApplyNode(String tagName, ASTNode2... childNodes)
      throws DOMException, SBMLException {
    List<ASTNode2> l = new ArrayList<ASTNode2>();
    for (ASTNode2 ast : childNodes) {
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
  private <T> ASTNode2Value<String> createApplyNode(String tagName,
    ASTNode2Value... childNodes) throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(document.createElement(tagName));
    for (ASTNode2Value value : childNodes) {
      lastElementCreated.appendChild(value.toNode());
    }
    if (childNodes.length > 0) {
      setLevel(childNodes[0].getLevel());
    }
    return new ASTNode2Value(lastElementCreated, this);
  }

  /**
   * Creates a {@link Node} with the tag "apply" and adds the given
   * {@link Node} as its child. Then it creates a new {@link ASTNode2Value}
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
  private <T> ASTNode2Value<String> createApplyNode(String tagName,
    List<ASTNode2> childNodes) throws DOMException, SBMLException {
    lastElementCreated = document.createElement("apply");
    lastElementCreated.appendChild(document.createElement(tagName));
    for (ASTNode2 value : childNodes) {
      lastElementCreated.appendChild(value.compile(this).toNode());
    }
    if (childNodes.size() > 0) {
      setLevel(childNodes.get(0).getParentSBMLObject().getLevel());
    }
    return new ASTNode2Value(lastElementCreated, this);
  }

  /**
   * 
   * @param name
   * @return
   */
  private <T> ASTNode2Value<String> createCiElement(String name) {
    lastElementCreated = document.createElement("ci");
    lastElementCreated.setTextContent(writeName(name.trim()));
    return new ASTNode2Value(lastElementCreated, this);
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
    lastElementCreated.setTextContent(writeName(value));
    return lastElementCreated;
  }

  /**
   * Creates an {@link ASTNode2Value} with the single node as defined by the
   * tagName.
   * 
   * @param tagName
   * @return
   */
  private <T> ASTNode2Value<String> createConstant(String tagName) {
    lastElementCreated = document.createElement(tagName);
    return new ASTNode2Value(lastElementCreated, this);
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
    lastElementCreated.setTextContent(writeName(name));
    return lastElementCreated;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#csc(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csc(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("csc", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#csch(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csch(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("csch", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#delay(java.lang.String, org.sbml.jsbml.ASTNode2, double, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> delay(String delayName, ASTNode2 x, ASTNode2 delay) throws DOMException, SBMLException {
    setLevel(x.getParentSBMLObject().getLevel());
    return createApplyNode(createCSymbol(delayName, MathMLParser
      .getDefinitionURIdelay()), compile(x.toString()), compile(delay.toString()));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#eq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> eq(ASTNode2 left, ASTNode2 right) throws DOMException,
  SBMLException {
    return createApplyNode("eq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#exp(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> exp(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("exp", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#factorial(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> factorial(ASTNode2 value) throws DOMException,
  SBMLException {
    return createApplyNode("factorial", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#floor(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> floor(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("floor", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#frac(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> frac(ASTNode2 numerator, ASTNode2 denominator)
      throws DOMException, SBMLException {
    return createApplyNode("divide", numerator, denominator);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#frac(int, int)
   */
  @Override
  public <T> ASTNode2Value<String> frac(int numerator, int denominator)
      throws DOMException, SBMLException {
    return createApplyNode("divide", compile(numerator, null), compile(
      denominator, null));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(FunctionDefinition functionDefinition,
    List<ASTNode2> args) throws DOMException, SBMLException {
    return createApplyNode(compile(functionDefinition).toNode(), args);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#function(java.lang.String, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(String functionDefinitionName,
    List<ASTNode2> args) throws SBMLException {
    return createApplyNode(compile(functionDefinitionName).toNode(), args);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#geq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> geq(ASTNode2 left, ASTNode2 right) throws DOMException,
  SBMLException {
    return createApplyNode("geq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> getConstantAvogadro(String name) {
    return new ASTNode2Value(createCSymbol(name, MathMLParser
      .getDefinitionURIavogadro()), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getConstantE()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantE() {
    return createConstant("exponentiale");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getConstantFalse()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantFalse() {
    return createConstant(Boolean.FALSE.toString());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getConstantPi()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantPi() {
    return createConstant("pi");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getConstantTrue()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantTrue() {
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getNegativeInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getNegativeInfinity() throws DOMException,
  SBMLException {
    return uMinus(getPositiveInfinity());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getPositiveInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getPositiveInfinity() {
    return createConstant("infinity");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#gt(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> gt(ASTNode2 left, ASTNode2 right) throws DOMException,
  SBMLException {
    return createApplyNode("gt", left, right);
  }

  /**
   * Re-initializes the {@link Document} object belonging to this class. Only
   * in this way a new {@link ASTNode2} tree can be compiled. To this end, also
   * the level attribute is reset (because maybe next time we want to compile
   * some {@link ASTNode2} with from a different source.
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#lambda(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> lambda(List<ASTNode2> values) throws DOMException,
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
    return new ASTNode2Value(lastElementCreated, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#leq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> leq(ASTNode2 left, ASTNode2 right) throws DOMException,
  SBMLException {
    return createApplyNode("leq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#ln(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ln(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("ln", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 value) throws DOMException, SBMLException {
    return log(null, value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 base, ASTNode2 value) throws DOMException,
  SBMLException {
    ASTNode2Value<String> v = createApplyNode("log", value);
    if (base != null) {
      Element logbase = document.createElement("logbase");
      logbase.appendChild(base.compile(this).toNode());
      v.toNode().insertBefore(logbase,
        v.toNode().getFirstChild().getNextSibling());
    }
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#lt(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> lt(ASTNode2 left, ASTNode2 right) throws DOMException,
  SBMLException {
    return createApplyNode("lt", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#minus(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> minus(List<ASTNode2> values) throws DOMException,
  SBMLException {
    return createApplyNode("minus", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#neq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> neq(ASTNode2 left, ASTNode2 right) throws DOMException,
  SBMLException {
    return createApplyNode("neq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#not(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> not(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("not", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#or(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> or(List<ASTNode2> values) throws DOMException,
  SBMLException {
    return createApplyNode("or", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#piecewise(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> piecewise(List<ASTNode2> values) throws DOMException,
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
      return new ASTNode2Value(lastElementCreated, this);
    }
    throw new IllegalArgumentException(
        "Cannot create piecewise function with empty argument list");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#plus(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> plus(List<ASTNode2> values) throws DOMException,
  SBMLException {
    return createApplyNode("plus", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#pow(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> pow(ASTNode2 base, ASTNode2 exponent)
      throws DOMException, SBMLException {
    return createApplyNode("pow", base, exponent);
  }

  /**
   * Allows to re-use this object to compile another {@link ASTNode2}. Without
   * reseting, this compiler can only be used one time. Otherwise it cannot be
   * guaranteed that the results will be correct.
   * 
   * @throws XMLStreamException
   */
  public void reset() throws XMLStreamException {
    init();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#root(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(ASTNode2 rootExponent, ASTNode2 radiant)
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
  private <T> ASTNode2Value<String> root(ASTNode2Value rootExponent, ASTNode2 radiant)
      throws DOMException, SBMLException {
    Element logbase = document.createElement("degree");
    logbase.appendChild(rootExponent.toNode());
    ASTNode2Value<String> v = createApplyNode("root", radiant);
    Node v_node = v.toNode();

    // TODO : Not working - not correcting it as the mathMl output is wrong anyway and will be re-implemented.
    v_node.insertBefore(logbase, v_node.getFirstChild().getNextSibling());

    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#root(double, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(double rootExponent, ASTNode2 radiant)
      throws DOMException, SBMLException {
    ASTNode2Value<String> exponent;
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sec(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sec(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("sec", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sech(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sech(ASTNode2 value) throws DOMException, SBMLException {
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sin(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sin(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("sin", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sinh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sinh(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("sinh", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sqrt(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sqrt(ASTNode2 value) throws DOMException, SBMLException {
    return root(2, value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#symbolTime(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> symbolTime(String time) {
    return new ASTNode2Value(createCSymbol(time, MathMLParser
      .getDefinitionURItime()), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#tan(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tan(ASTNode2 value) throws DOMException, SBMLException {
    return createApplyNode("tan", value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#tanh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tanh(ASTNode2 value) throws DOMException, SBMLException {
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
  private <T> ASTNode2Value<String> times(ASTNode2Value... values) throws DOMException,
  SBMLException {
    return createApplyNode("times", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#times(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> times(List<ASTNode2> values) throws DOMException,
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#uMinus(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> uMinus(ASTNode2 value) throws DOMException,
  SBMLException {
    ASTNode2Value<?> v = value.compile(this);
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
  private <T> ASTNode2Value<String> uMinus(ASTNode2Value value) throws DOMException,
  SBMLException {
    setLevel(value.getLevel());
    return times(compile(-1, (0 < level) && (level < 3) ? null
      : Unit.Kind.DIMENSIONLESS.toString().toLowerCase()), value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#unknownValue()
   */
  @Override
  public <T> ASTNode2Value<String> unknownValue() throws SBMLException {
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#xor(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> xor(List<ASTNode2> values) throws DOMException,
  SBMLException {
    return createApplyNode("xor", values);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#selector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> selector(List<ASTNode2> nodes) throws SBMLException {
    return function("selector", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#vector()
   */
  @Override
  public <T> ASTNode2Value<String> vector(List<ASTNode2> nodes) throws SBMLException {
    return function("vector", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#function(java.lang.Object, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<?> function(T functionDefinitionName,
    List<ASTNode2> args) throws SBMLException {
    // TODO Auto-generated method stub
    return null;
  }  
}
