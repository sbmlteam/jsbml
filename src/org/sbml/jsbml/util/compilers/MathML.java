/*
 * $Id: MathMLComnpiler.java 97 2009-12-10 09:08:54Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/util/MathMLComnpiler.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
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
package org.sbml.jsbml.util.compilers;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNodeCompiler;
import org.sbml.jsbml.ASTNodeValue;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.NamedSBaseWithDerivedUnit;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.MathMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * With this compiler, an {@link ASTNode} can be transformed into a MathML
 * string.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-18
 * 
 */
public class MathML implements ASTNodeCompiler {

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
	 * @throws ParserConfigurationException
	 */
	public MathML() throws XMLStreamException {
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue abs(ASTNodeValue value) {
		return createApplyNode("abs", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#and(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue and(ASTNodeValue... values) {
		if (values.length > 0) {
			return createApplyNode("and", values);
		}
		throw new IllegalArgumentException(
				"cannot create and node for empty element list");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccos(ASTNodeValue value) {
		return createApplyNode("arccos", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccosh(ASTNodeValue value) {
		return createApplyNode("arccosh", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccot(ASTNodeValue value) {
		return createApplyNode("arccot", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccoth(ASTNodeValue value) {
		return createApplyNode("arccoth", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsc(ASTNodeValue value) {
		return createApplyNode("arccsc", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsch(ASTNodeValue value) {
		return createApplyNode("arccsch", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsec(ASTNodeValue value) {
		return createApplyNode("arcsec", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsech(ASTNodeValue value) {
		return createApplyNode("arcsech", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsin(ASTNodeValue value) {
		return createApplyNode("arcsin", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsinh(ASTNodeValue value) {
		return createApplyNode("arcsinh", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctan(ASTNodeValue value) {
		return createApplyNode("arctan", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctanh(ASTNodeValue value) {
		return createApplyNode("arctanh", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ceiling(ASTNodeValue value) {
		return createApplyNode("ceiling", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
	 */
	public ASTNodeValue compile(Compartment c) {
		return compile((NamedSBaseWithDerivedUnit) c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, int,
	 * java.lang.String)
	 */
	public ASTNodeValue compile(double mantissa, int exponent, String units) {
		return new ASTNodeValue(createCnElement(mantissa, exponent, units),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, java.lang.String)
	 */
	public ASTNodeValue compile(double real, String units) {
		return new ASTNodeValue(createCnElement(Double.valueOf(real), units),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(int, java.lang.String)
	 */
	public ASTNodeValue compile(int integer, String units) {
		return new ASTNodeValue(
				createCnElement(Integer.valueOf(integer), units), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.
	 * NamedSBaseWithDerivedUnit)
	 */
	public ASTNodeValue compile(NamedSBaseWithDerivedUnit variable) {
		setLevel(variable.getLevel());
		return compile(variable.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(java.lang.String)
	 */
	public ASTNodeValue compile(String name) {
		return createCiElement(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cos(ASTNodeValue value) {
		return createApplyNode("cos", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cosh(ASTNodeValue value) {
		return createApplyNode("cosh", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cot(ASTNodeValue value) {
		return createApplyNode("cot", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue coth(ASTNodeValue value) {
		return createApplyNode("coth", value);
	}

	/**
	 * Creates an apply {@link Node} with the given tag {@link Node} as its
	 * child and {@link ASTNodeValue}s on the same level as the tag {@link Node}
	 * .
	 * 
	 * @param tag
	 * @param childNodes
	 * @return
	 */
	private ASTNodeValue createApplyNode(Node tag, ASTNodeValue... childNodes) {
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
	 * Creates a {@link Node} with the tag "apply" and adds the given
	 * {@link Node} as its child. Then it creates a new {@link ASTNodeValue}
	 * that wraps the apply {@link Node}.
	 * 
	 * @param tagName
	 *            The name of the node to be created, e.g., "abs" or "and".
	 * @param childNodes
	 *            at least one child should be passed to this method.
	 * @return
	 */
	private ASTNodeValue createApplyNode(String tagName,
			ASTNodeValue... childNodes) {
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
	 * 
	 * @param name
	 * @return
	 */
	private ASTNodeValue createCiElement(String name) {
		lastElementCreated = document.createElement("ci");
		lastElementCreated.setTextContent(writeName(name.trim()));
		return new ASTNodeValue(lastElementCreated, this);
	}

	/**
	 * 
	 * @param mantissa
	 * @param exponent
	 * @param units
	 *            Can be null.
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
	 *            Can be null.
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
		lastElementCreated.setTextContent(writeName(name));
		return lastElementCreated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csc(ASTNodeValue value) {
		return createApplyNode("csc", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csch(ASTNodeValue value) {
		return createApplyNode("csch", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#delay(java.lang.String,
	 * org.sbml.jsbml.ASTNodeValue, double, java.lang.String)
	 */
	public ASTNodeValue delay(String delayName, ASTNodeValue x, double d,
			String timeUnit) {
		setLevel(x.getLevel());
		return createApplyNode(createCSymbol(delayName, MathMLParser
				.getDefinitionURIdelay()), compile(x.toString()), compile(d,
				timeUnit));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#equal(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue eq(ASTNodeValue left, ASTNodeValue right) {
		return createApplyNode("eq", left, right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue exp(ASTNodeValue value) {
		return createApplyNode("exp", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue factorial(ASTNodeValue value) {
		return createApplyNode("factorial", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue floor(ASTNodeValue value) {
		return createApplyNode("floor", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue frac(ASTNodeValue numerator, ASTNodeValue denominator) {
		return createApplyNode("divide", numerator, denominator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(int, int)
	 */
	public ASTNodeValue frac(int numerator, int denominator) {
		return createApplyNode("divide", compile(numerator, null), compile(
				denominator, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition
	 * , org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue function(FunctionDefinition functionDefinition,
			ASTNodeValue... args) {
		return createApplyNode(compile(functionDefinition).toNode(), args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue geq(ASTNodeValue left, ASTNodeValue right) {
		return createApplyNode("geq", left, right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
	 */
	public ASTNodeValue getConstantAvogadro(String name) {
		return new ASTNodeValue(createCSymbol(name, MathMLParser
				.getDefinitionURIavogadro()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantE()
	 */
	public ASTNodeValue getConstantE() {
		return createConstant("exponentiale");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantFalse()
	 */
	public ASTNodeValue getConstantFalse() {
		return createConstant(Boolean.FALSE.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantPi()
	 */
	public ASTNodeValue getConstantPi() {
		return createConstant("pi");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantTrue()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getNegativeInfinity()
	 */
	public ASTNodeValue getNegativeInfinity() {
		return uMinus(getPositiveInfinity());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getPositiveInfinity()
	 */
	public ASTNodeValue getPositiveInfinity() {
		return createConstant("infinity");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue gt(ASTNodeValue left, ASTNodeValue right) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lambda(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue lambda(ASTNodeValue... values) {
		lastElementCreated = document.createElement("lambda");
		if (values.length > 0) {
			setLevel(values[0].getLevel());
			if (values.length > 1) {
				for (int i = 0; i < values.length - 1; i++) {
					Element bvar = document.createElement("bvar");
					bvar.appendChild(values[i].toNode());
					lastElementCreated.appendChild(bvar);
				}
			}
			lastElementCreated.appendChild(values[values.length - 1].toNode());
		}
		return new ASTNodeValue(lastElementCreated, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#lessEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue leq(ASTNodeValue left, ASTNodeValue right) {
		return createApplyNode("leq", left, right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ln(ASTNodeValue value) {
		return createApplyNode("ln", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue value) {
		return log(null, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue base, ASTNodeValue value) {
		ASTNodeValue v = createApplyNode("log", value);
		if (base != null) {
			Element logbase = document.createElement("logbase");
			logbase.appendChild(base.toNode());
			v.toNode().insertBefore(logbase,
					v.toNode().getFirstChild().getNextSibling());
		}
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue lt(ASTNodeValue left, ASTNodeValue right) {
		return createApplyNode("lt", left, right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue minus(ASTNodeValue... values) {
		return createApplyNode("minus", values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#notEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue neq(ASTNodeValue left, ASTNodeValue right) {
		return createApplyNode("neq", left, right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#not(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue not(ASTNodeValue value) {
		return createApplyNode("not", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue or(ASTNodeValue... values) {
		return createApplyNode("or", values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#piecewise(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue piecewise(ASTNodeValue... values) {
		if (values.length > 0) {
			setLevel(values[0].getLevel());
			lastElementCreated = document.createElement("apply");
			Element tag = document.createElement("piecewise");
			Element piece = null;
			for (int i = 0; i < values.length; i++) {
				if ((i % 2) == 0) {
					piece = document.createElement("piece");
					tag.appendChild(piece);
				} else if (i == values.length - 1) {
					piece = document.createElement("otherwise");
					tag.appendChild(piece);
				}
				if (piece != null) {
					piece.appendChild(values[i].toNode());
				}
			}
			lastElementCreated.appendChild(tag);
			return new ASTNodeValue(lastElementCreated, this);
		}
		throw new IllegalArgumentException(
				"cannot create piecewise function with empty argument list");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue plus(ASTNodeValue... values) {
		return createApplyNode("plus", values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue root(ASTNodeValue rootExponent, ASTNodeValue radiant) {
		ASTNodeValue v = createApplyNode("root", radiant);
		if (rootExponent != null) {
			Element logbase = document.createElement("degree");
			logbase.appendChild(rootExponent.toNode());
			v.toNode().insertBefore(logbase,
					v.toNode().getFirstChild().getNextSibling());
		}
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(double,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue root(double rootExponent, ASTNodeValue radiant) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sec(ASTNodeValue value) {
		return createApplyNode("sec", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sech(ASTNodeValue value) {
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
					"cannot set level from the valid value ",
					Integer.toString(this.level), " to invalid value ",
					Integer.toString(level)).toString());
		}
		this.level = level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sin(ASTNodeValue value) {
		return createApplyNode("sin", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sinh(ASTNodeValue value) {
		return createApplyNode("sinh", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sqrt(ASTNodeValue value) {
		return root(2, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#symbolTime(java.lang.String)
	 */
	public ASTNodeValue symbolTime(String time) {
		return new ASTNodeValue(createCSymbol(time, MathMLParser
				.getDefinitionURItime()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tan(ASTNodeValue value) {
		return createApplyNode("tan", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tanh(ASTNodeValue value) {
		return createApplyNode("tanh", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#times(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue times(ASTNodeValue... values) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#uiMinus(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue uMinus(ASTNodeValue value) {
		setLevel(value.getLevel());
		return times(compile(-1, (0 < level) && (level < 3) ? null
				: Unit.Kind.DIMENSIONLESS.toString().toLowerCase()), value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#unknownValue()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#xor(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue xor(ASTNodeValue... values) {
		return createApplyNode("xor", values);
	}
}
