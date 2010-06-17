/*
 * $Id: LaTeX.java 97 2009-12-10 09:08:54Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/util/LaTeX.java $
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

import org.sbml.jsbml.ASTNodeCompiler;
import org.sbml.jsbml.ASTNodeValue;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.NamedSBaseWithDerivedUnit;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.util.StringTools;

/**
 * Converts {@link ASTNodeValue} objects into a LaTeX {@link String} to be
 * included into scientific writings or to be displayed by {@link sHotEqn} in a
 * GUI.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2009-01-03
 */
public class LaTeX extends StringTools implements ASTNodeCompiler {

	/**
	 * Requires LaTeX package booktabs. Produces a fancy line at the bottom of a
	 * table. This variable also includes the <code>end{longtable}</code>
	 * command and a new line.
	 */
	public static final String bottomrule = "\\bottomrule\\end{longtable}"
			+ newLine;

	/**
	 * The constant pi
	 */
	public static final String CONSTANT_PI = "\\pi";

	/**
	 * Surrounded by new line symbols. The begin of a description environment in
	 * LaTeX.
	 */
	public static final String descriptionBegin = "\\begin{description}"
			+ newLine;

	/**
	 * Surrounded by new line symbols. The end of a description environment.
	 */
	public static final String descriptionEnd = "\\end{description}" + newLine;

	/**
	 * Surrounded by new line symbols. Begin equation. This type of equation
	 * requires the LaTeX package breqn. It will produce equations with
	 * automatic line breaks (LaTeX will compute the optimal place for line
	 * breaks). Unfortunately, this does not work for very long denominators.
	 */
	public static final String eqBegin = newLine + "\\begin{dmath}" + newLine; // equation

	/**
	 * End equation; cf. eqBegin. Surrounded by new line symbols.
	 */
	public static final String eqEnd = newLine + "\\end{dmath}" + newLine; // equation

	/**
	 * Left parenthesis.
	 */
	public static final String leftBrace = "\\left(";

	/**
	 * An opening quotation mark.
	 */
	public static final String leftQuotationMark = "``";

	/**
	 * This is a LaTeX line break. The line break symbol double backslash
	 * followed by a new line symbol of the operating system.
	 */
	public static final String lineBreak = "\\\\" + newLine;

	/**
	 * Produces a fancy line in tables. Requires LaTeX package booktabs. Starts
	 * and ends with a new line.
	 */
	public static final String midrule = newLine + "\\midrule" + newLine;

	/**
	 * 
	 */
	public static final String NEGATIVE_ININITY = "-\\infty";

	/**
	 * 
	 */
	public static final String or = "\\lor ";

	/**
	 * 
	 */
	public static final String POSITIVE_INFINITY = "\\infty";

	/**
	 * 
	 */
	public static final String rightBrace = "\\right)";

	/**
	 * An closing quotation mark.
	 */
	public static final String rightQuotationMark = "\"";

	/**
	 * Needed for the beginning of a table. Requires LaTeX package booktabs.
	 * Surounded by new line symbols.
	 */
	public static final String toprule = newLine + "\\toprule" + newLine;

	/**
	 * 
	 */
	public static final String wedge = "\\wedge ";

	/**
	 * 
	 */
	public static final String xor = "\\oplus ";

	/**
	 * 
	 * @param command
	 * @param what
	 * @return
	 */
	private static StringBuilder command(String command, Object what) {
		StringBuilder sb = new StringBuilder("\\");
		sb.append(command);
		sb.append('{');
		sb.append(what);
		sb.append('}');
		return sb;
	}

	/**
	 * 
	 * @param command
	 * @param first
	 * @param second
	 * @return
	 */
	private static StringBuilder command(String command, Object first,
			Object second) {
		StringBuilder sb = command(command, first);
		sb.append('{');
		sb.append(second);
		sb.append('}');
		return sb;
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String getNumbering(long number) {
		if ((Integer.MIN_VALUE < number) && (number < Integer.MAX_VALUE))
			switch ((int) number) {
			case 1:
				return "first";
			case 2:
				return "second";
			case 3:
				return "third";
			case 5:
				return "fifth";
			case 13:
				return "thirteenth";
			default:
				if (number < 13) {
					String word = StringTools.getWordForNumber(number);
					return word.endsWith("t") ? word + 'h' : word + "th";
				}
				break;
			}
		String numberWord = Long.toString(number);
		switch (numberWord.charAt(numberWord.length() - 1)) {
		case '1':
			return StringTools.getWordForNumber(number)
					+ "\\textsuperscript{st}";
		case '2':
			return StringTools.getWordForNumber(number)
					+ "\\textsuperscript{nd}";
		case '3':
			return StringTools.getWordForNumber(number)
					+ "\\textsuperscript{rd}";
		default:
			return StringTools.getWordForNumber(number)
					+ "\\textsuperscript{th}";
		}
	}

	/**
	 * Creates head lines.
	 * 
	 * @param kind
	 *            E.g., section, subsection, subsubsection, paragraph etc.
	 * @param title
	 *            The title of the heading.
	 * @param numbering
	 *            If true a number will be placed in front of the title.
	 * @return
	 */
	private static StringBuffer heading(String kind, String title,
			boolean numbering) {
		StringBuffer heading = new StringBuffer(newLine);
		heading.append("\\");
		heading.append(kind);
		if (!numbering)
			heading.append('*');
		heading.append('{');
		heading.append(title);
		heading.append('}');
		heading.append(newLine);
		return heading;
	}

	/**
	 * Masks all special characters used by LaTeX with a backslash including
	 * hyphen symbols.
	 * 
	 * @param string
	 * @return
	 */
	public static String maskSpecialChars(String string) {
		return maskSpecialChars(string, true);
	}

	/**
	 * 
	 * @param string
	 * @param hyphen
	 *            if true a hyphen symbol is introduced at each position where a
	 *            special character has to be masked anyway.
	 * @return
	 */
	public static String maskSpecialChars(String string, boolean hyphen) {
		StringBuffer masked = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char atI = string.charAt(i);
			if (atI == '<') {
				masked.append("$<$");
			} else if (atI == '>') {
				masked.append("$>$");
			} else {
				if ((atI == '_') || (atI == '\\') || (atI == '$')
						|| (atI == '&') || (atI == '#') || (atI == '{')
						|| (atI == '}') || (atI == '~') || (atI == '%')
						|| (atI == '^')) {
					if ((i == 0) || (!hyphen))
						masked.append('\\');
					else if (hyphen && (string.charAt(i - 1) != '\\'))
						masked.append("\\-\\"); // masked.append('\\');
					// } else if ((atI == '[') || (atI == ']')) {
				}
				masked.append(atI);
			}
		}
		return masked.toString().trim();
	}

	/**
	 * 
	 */
	public final String CONSTANT_E = mathrm("e").toString();
	/**
	 * 
	 */
	public final String CONSTANT_FALSE = mathrm("false").toString();
	/**
	 * 
	 */
	public final String CONSTANT_TRUE = mathrm("true").toString();

	/**
	 * Important for LaTeX export to decide whether the name or the id of a
	 * NamedSBase should be printed.
	 */
	private boolean printNameIfAvailable;

	/**
	 * 
	 * 
	 */
	public LaTeX() {
		printNameIfAvailable = false;
	}

	/**
	 * 
	 * @param settings
	 */
	public LaTeX(boolean namesInEquations) {
		setPrintNameIfAvailable(namesInEquations);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue abs(ASTNodeValue value) {
		StringBuffer abs = new StringBuffer("\\left\\lvert");
		abs.append(value.toString());
		abs.append("\\right\\rvert");
		return new ASTNodeValue(abs.toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#and(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue and(ASTNodeValue... nodes) {
		return logicalOperation(wedge, nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccos(ASTNodeValue value) {
		return new ASTNodeValue(command("arccos", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccosh(ASTNodeValue value) {
		return new ASTNodeValue(function("arccosh", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccot(ASTNodeValue value) {
		return new ASTNodeValue(function("arcot", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccoth(ASTNodeValue value) {
		return new ASTNodeValue(function("arccoth", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsc(ASTNodeValue value) {
		return new ASTNodeValue(function("arccsc", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsch(ASTNodeValue value) {
		return new ASTNodeValue(function("arccsch", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsec(ASTNodeValue value) {
		return new ASTNodeValue(function("arcsec", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsech(ASTNodeValue value) {
		return new ASTNodeValue(function("arcsech", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsin(ASTNodeValue value) {
		return new ASTNodeValue(function("arcsin", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsinh(ASTNodeValue value) {
		return new ASTNodeValue(function("arcsinh", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctan(ASTNodeValue value) {
		return new ASTNodeValue(function("arctan", value).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctanh(ASTNodeValue value) {
		return new ASTNodeValue(function("\\arctanh", value).toString(), this);
	}

	/**
	 * Encloses the given formula in brackets.
	 * 
	 * @param formula
	 * @return
	 */
	public StringBuilder brackets(Object formula) {
		StringBuilder buffer = new StringBuilder("\\left(");
		buffer.append(formula);
		buffer.append("\\right)");
		return buffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ceiling(ASTNodeValue value) {
		StringBuffer ceiling = new StringBuffer("\\left\\lceil ");
		ceiling.append(value.toString());
		ceiling.append("\\right\\rceil ");
		return new ASTNodeValue(ceiling.toString(), this);
	}

	/**
	 * 
	 * @param color
	 * @param what
	 * @return
	 */
	public StringBuilder colorbox(String color, Object what) {
		return command("colorbox", color, what);
	}

	/**
	 * This method returns the correct LaTeX expression for a function which
	 * returns the size of a compartment. This can be a volume, an area, a
	 * length or a point.
	 */
	public ASTNodeValue compile(Compartment c) {
		StringBuffer value = new StringBuffer();
		switch ((int) c.getSpatialDimensions()) {
		case 3:
			value.append("vol");
			break;
		case 2:
			value.append("area");
			break;
		case 1:
			value.append("length");
			break;
		default:
			value.append("point");
			break;
		}
		return new ASTNodeValue(mathrm(value.toString()).append(
				brackets(getNameOrID(c))).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, java.lang.String)
	 */
	public ASTNodeValue compile(double real, String units) {
		// TODO: deal with Units.
		return new ASTNodeValue(format(real).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(int, java.lang.String)
	 */
	public ASTNodeValue compile(int integer, String units) {
		// TODO: deal with Units.
		return new ASTNodeValue(integer, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.
	 * NamedSBaseWithDerivedUnit)
	 */
	public ASTNodeValue compile(NamedSBaseWithDerivedUnit variable) {
		if (variable instanceof Species) {
			Species species = (Species) variable;
			Compartment c = species.getCompartmentInstance();
			boolean concentration = !species.getHasOnlySubstanceUnits()
					&& (0 < c.getSpatialDimensions());
			StringBuffer value = new StringBuffer();
			if (concentration) {
				value.append('[');
			}
			value.append(getNameOrID(species));
			if (concentration) {
				value.append(']');
			}
			return new ASTNodeValue(value.toString(), this);

		} else if (variable instanceof Compartment) {
			Compartment c = (Compartment) variable;
			return compile(c);
		}
		// TODO: weitere spezialfälle von Namen!!! PARAMETER, FUNCTION DEF,
		// REACTION.
		return new ASTNodeValue(mathtt(maskSpecialChars(variable.getId()))
				.toString(), this);
		// else if (variable instanceof Parameter) {
		// return new StringBuffer("parameter");
		// }
		// return new StringBuffer("variable:"+variable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(java.lang.String)
	 */
	public ASTNodeValue compile(String name) {
		return new ASTNodeValue(maskSpecialChars(name), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cos(ASTNodeValue node) {
		return new ASTNodeValue(function("\\cos", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cosh(ASTNodeValue node) {
		return new ASTNodeValue(function("\\cosh", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cot(ASTNodeValue node) {
		return new ASTNodeValue(function("\\cot", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue coth(ASTNodeValue node) {
		return new ASTNodeValue(function("\\coth", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csc(ASTNodeValue node) {
		return new ASTNodeValue(function("\\csc", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csch(ASTNodeValue node) {
		return new ASTNodeValue(function("csch", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#delay(org.sbml.jsbml.ASTNodeValue,
	 * double)
	 */
	public ASTNodeValue delay(String delayName, ASTNodeValue x, double d,
			String timeUnits) {
		// TODO: deal with units.
		return new ASTNodeValue(concat(mathrm(maskSpecialChars(delayName)),
				brackets(concat(x.toString(), ", ", compile(d, timeUnits))))
				.toString(), this);
	}

	/**
	 * This method simplifies the process of creating descriptions. There is an
	 * item entry together with a description. No new line or space is needed
	 * for separation.
	 * 
	 * @param item
	 *            e.g., "my item"
	 * @param description
	 *            e.g., "my description"
	 * @return
	 */
	public StringBuffer descriptionItem(String item, Object description) {
		StringBuffer itemBuffer = new StringBuffer("\\item[");
		itemBuffer.append(item);
		itemBuffer.append("] ");
		itemBuffer.append(description);
		itemBuffer.append(newLine);
		return itemBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#equal(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue eq(ASTNodeValue left, ASTNodeValue right) {
		return new ASTNodeValue(relation(left, " = ", right).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue exp(ASTNodeValue node) {
		return new ASTNodeValue(function("\\exp", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue factorial(ASTNodeValue node) {
		StringBuilder value;
		if (!node.isUnary()) {
			value = brackets(node.toString());
		} else {
			value = new StringBuilder(node.toString());
		}
		value.append('!');
		return new ASTNodeValue(value.toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue floor(ASTNodeValue value) {
		StringBuilder floor = new StringBuilder("\\left\\lfloor ");
		floor.append(value.toString());
		floor.append("\\right\\rfloor ");
		return new ASTNodeValue(floor.toString(), this);
	}

	/**
	 * This method returns a <code>StringBuffer</code> representing a properly
	 * LaTeX formatted number.
	 * 
	 * @param value
	 * @return
	 */
	public StringBuffer format(double value) {
		StringBuffer sb = new StringBuffer();
		String val = Double.toString(value);
		if (val.contains("E")) {
			String split[] = val.split("E");
			val = "10^{" + format(Double.parseDouble(split[1])) + "}";
			if (split[0].equals("-1.0")) {
				val = "-" + val;
			} else if (!split[0].equals("1.0")) {
				val = format(Double.parseDouble(split[0])) + "\\cdot " + val;
			}
			sb.append(math(val));
		} else if (value - ((int) value) == 0) {
			sb.append(((int) value));
		} else {
			sb.append(val);
		}
		return sb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue frac(ASTNodeValue numerator, ASTNodeValue denominator) {
		return new ASTNodeValue(command("frac", numerator, denominator)
				.toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(int, int)
	 */
	public ASTNodeValue frac(int numerator, int denominator) {
		return new ASTNodeValue(frac(Integer.valueOf(numerator),
				Integer.valueOf(denominator)).toString(), this);
	}

	/**
	 * 
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public StringBuilder frac(Object numerator, Object denominator) {
		return command("frac", numerator, denominator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition
	 * , org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue function(FunctionDefinition fun, ASTNodeValue... args) {
		StringBuffer value = new StringBuffer();
		int length;
		if (fun != null) {
			value.append(mathtt(LaTeX.maskSpecialChars(fun.getId())));
			length = args.length;
		} else {
			length = args.length - 1;
		}
		StringBuffer argList = new StringBuffer();

		for (int i = 0; i < length; i++) {
			if (i > 0) {
				argList.append(", ");
			}
			argList.append(args[i]);
		}
		value.append(brackets(argList));
		if (length < args.length) {
			value.append(" = ");
			value.append(args[args.length - 1]);
		}
		return new ASTNodeValue(value.toString(), this);
	}

	/**
	 * Decides whether to produce brackets.
	 * 
	 * @param func
	 * @param value
	 * @return
	 */
	private StringBuilder function(String func, ASTNodeValue value) {
		return function(func, value.isUnary() ? value : brackets(value));
	}

	/**
	 * Without brackets.
	 * 
	 * @param func
	 * @param value
	 * @return
	 */
	private StringBuilder function(String func, Object value) {
		boolean command = func.startsWith("\\");
		StringBuilder fun = command ? new StringBuilder(func) : mathrm(func);
		if (command) {
			fun.append('{');
		}
		fun.append(value);
		if (command) {
			fun.append('}');
		}
		return fun;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue geq(ASTNodeValue left, ASTNodeValue right) {
		return new ASTNodeValue(relation(left, " \\geq ", right).toString(),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantE()
	 */
	public ASTNodeValue getConstantE() {
		return new ASTNodeValue(new String(CONSTANT_E), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantFalse()
	 */
	public ASTNodeValue getConstantFalse() {
		return new ASTNodeValue(new String(CONSTANT_FALSE), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantPi()
	 */
	public ASTNodeValue getConstantPi() {
		return new ASTNodeValue(new String(CONSTANT_PI), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantTrue()
	 */
	public ASTNodeValue getConstantTrue() {
		return new ASTNodeValue(new String(CONSTANT_TRUE), this);
	}

	/**
	 * If the field printNameIfAvailable is false this method returns a the id
	 * of the given SBase. If printNameIfAvailable is true this method looks for
	 * the name of the given SBase and will return it.
	 * 
	 * @param sbase
	 *            the SBase, whose name or id is to be returned.
	 * @param mathMode
	 *            if true this method returns the name typesetted in mathmode,
	 *            i.e., mathrm for names and mathtt for ids, otherwise texttt
	 *            will be used for ids and normalfont (nothing) will be used for
	 *            names.
	 * @return The name or the ID of the SBase (according to the field
	 *         printNameIfAvailable), whose LaTeX special symbols are masked and
	 *         which is type set in typewriter font if it is an id. The mathmode
	 *         argument decides if mathtt or mathrm has to be used.
	 */
	private StringBuilder getNameOrID(NamedSBase sbase) {
		String name = "";
		if (sbase.isSetName() && printNameIfAvailable) {
			name = sbase.getName();
		} else if (sbase.isSetId()) {
			name = sbase.getId();
		} else {
			name = "Undefinded";
		}
		name = maskSpecialChars(name);
		return printNameIfAvailable ? mathrm(name) : mathtt(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getNegativeInfinity()
	 */
	public ASTNodeValue getNegativeInfinity() {
		return new ASTNodeValue(new String(NEGATIVE_ININITY), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getPositiveInfinity()
	 */
	public ASTNodeValue getPositiveInfinity() {
		return new ASTNodeValue(new String(POSITIVE_INFINITY), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue gt(ASTNodeValue left, ASTNodeValue right) {
		return new ASTNodeValue(relation(left, " > ", right).toString(), this);
	}

	/**
	 * Creates a hyper link to the given target and the text to be visible in
	 * the document.
	 * 
	 * @param target
	 *            The target to which this link points to.
	 * @param text
	 *            The text to be written in the link.
	 * @return
	 */
	public StringBuilder href(String target, Object text) {
		return command("href", target, text);
	}

	/**
	 * 
	 * @param target
	 * @param text
	 * @return
	 */
	public StringBuilder hyperref(String target, Object text) {
		StringBuilder sb = new StringBuilder("\\hyperref[");
		sb.append(target);
		sb.append("]{");
		sb.append(text);
		sb.append('}');
		return sb;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPrintNameIfAvailable() {
		return printNameIfAvailable;
	}

	public StringBuilder label(String id) {
		return command("label", new StringBuilder(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lambda(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue lambda(ASTNodeValue... nodes) {
		return function((FunctionDefinition) null, nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#lessEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue leq(ASTNodeValue left, ASTNodeValue right) {
		return new ASTNodeValue(concat(left, " \\leq ", right).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ln(ASTNodeValue node) {
		return new ASTNodeValue(function("\\ln", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue node) {
		return log(null, node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue base, ASTNodeValue value) {
		StringBuilder v = new StringBuilder("\\log");
		if (base != null) {
			StringTools.append(v, "_{", base, "}");
		}
		StringTools.append(v, "{", value.isUnary() ? value : brackets(value),
				"}");
		return new ASTNodeValue(value.toString(), this);
	}

	/**
	 * 
	 * @param symbol
	 * @param values
	 * @return
	 */
	private ASTNodeValue logicalOperation(String symbol, ASTNodeValue... values) {
		StringBuffer value = new StringBuffer();
		int i = 0;
		for (ASTNodeValue v : values) {
			if (!v.isUnary()) {
				value.append(leftBrace);
			}
			value.append(v);
			if (!v.isUnary()) {
				value.append(rightBrace);
			}
			if (i < values.length - 1) {
				value.append(symbol);
			}
			i++;
		}
		return new ASTNodeValue(value.toString(), this);
	}

	/**
	 * Creates a head for a longtable in LaTeX.
	 * 
	 * @param columnDef
	 *            without leading and ending brackets, e.g., "lrrc",
	 * @param caption
	 *            caption of this table without leading and ending brackets
	 * @param headLine
	 *            table head without leading and ending brackets and without
	 *            double backslashes at the end
	 * @return
	 */
	public StringBuffer longtableHead(String columnDef, String caption,
			String headLine) {
		StringBuffer buffer = new StringBuffer("\\begin{longtable}[h!]{");
		buffer.append(columnDef);
		buffer.append('}');
		buffer.append(newLine);
		buffer.append("\\caption{");
		buffer.append(caption);
		buffer.append('}');
		buffer.append("\\\\");
		StringBuffer head = new StringBuffer(toprule);
		head.append(headLine);
		head.append("\\\\");
		head.append(midrule);
		buffer.append(head);
		buffer.append("\\endfirsthead");
		// buffer.append(newLine);
		buffer.append(head);
		buffer.append("\\endhead");
		// buffer.append(bottomrule);
		// buffer.append("\\endlastfoot");
		buffer.append(newLine);
		return buffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue lt(ASTNodeValue left, ASTNodeValue right) {
		return new ASTNodeValue(concat(left, " < ", right).toString(), this);
	}

	/**
	 * Encloses the given formula in dollar symbols (inline math mode).
	 * 
	 * @param formula
	 * @return
	 */
	public StringBuffer math(Object formula) {
		StringBuffer math = new StringBuffer();
		String f = String.valueOf(formula);
		if (f.length() == 0) {
			return math;
		}
		if (f.charAt(0) != '$') {
			math.append('$');
		}
		math.append(f);
		if (f.charAt(f.length() - 1) != '$') {
			math.append('$');
		}
		return math;
	}

	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public StringBuilder mathrm(char symbol) {
		return command("mathrm", Character.valueOf(symbol));
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	public StringBuilder mathrm(String text) {
		return command("mathrm", text);
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	public StringBuilder mathtext(String text) {
		return command("text", text);
	}

	/**
	 * Returns the LaTeX code to set the given String in type writer font within
	 * a math environment.
	 * 
	 * @param id
	 * @return
	 */
	public StringBuilder mathtt(String id) {
		return command("mathtt", new StringBuffer(id));
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public StringBuilder mbox(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(" \\mbox{");
		sb.append(s);
		sb.append("} ");
		return sb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue minus(ASTNodeValue... nodes) {
		if (nodes.length == 0)
			return new ASTNodeValue("", this);
		StringBuilder value = new StringBuilder();
		value.append(nodes[0].toString());
		for (int i = 1; i < nodes.length; i++) {
			value.append('-');
			if (nodes[i].getType() == Type.PLUS) {
				value.append(brackets(nodes[i]));
			} else {
				value.append(nodes[i]);
			}
		}
		return new ASTNodeValue(value.toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#notEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue neq(ASTNodeValue left, ASTNodeValue right) {
		return new ASTNodeValue(concat(left, " \\neq ", right).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#not(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue not(ASTNodeValue node) {
		return new ASTNodeValue(concat("\\neg ",
				node.isUnary() ? node : brackets(node)).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue or(ASTNodeValue... nodes) {
		return logicalOperation(or, nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#piecewise(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue piecewise(ASTNodeValue... nodes) {
		StringBuilder v = new StringBuilder("\\begin{dcases}");
		v.append(newLine);
		for (int i = 0; i < nodes.length - 1; i++) {
			v.append(nodes[i]);
			v.append(((i % 2) == 0) ? " & \\text{if\\ } " : lineBreak);
		}
		v.append(nodes[nodes.length - 1]);
		if ((nodes.length % 2) == 1) {
			v.append(" & \\text{otherwise}");
			v.append(newLine);
		}
		v.append("\\end{dcases}");
		return new ASTNodeValue(v.toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue plus(ASTNodeValue... nodes) {
		if (nodes.length > 0) {
			StringBuilder value = new StringBuilder();
			value.append(nodes[0]);
			for (int i = 1; i < nodes.length; i++) {
				if (nodes[i].isUMinus()) {
					value.append(nodes[i]);
				} else {
					value.append('+');
					if (nodes[i].getType() == Type.MINUS) {
						value.append(brackets(nodes[i]));
					} else {
						value.append(nodes[i]);
					}
				}
			}
			return new ASTNodeValue(value.toString(), this);
		}
		return new ASTNodeValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent) {
		StringBuilder value = new StringBuilder();
		value.append(base);
		if (!base.isUnary()) {
			value = brackets(value);
		}
		value.append('^');
		value.append('{');
		value.append(exponent);
		value.append('}');
		return new ASTNodeValue(value.toString(), this);
	}

	/**
	 * Creates a relation between two astnodes.
	 * 
	 * @param left
	 * @param relationSymbol
	 * @param right
	 * @return
	 */
	private StringBuilder relation(ASTNodeValue left, String relationSymbol,
			ASTNodeValue right) {
		StringBuilder value = new StringBuilder();
		value.append(left);
		value.append(" = ");
		value.append(right);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue root(ASTNodeValue rootExponent, ASTNodeValue value) {
		if (rootExponent.isNumber()
				&& (((Number) rootExponent.getValue()).doubleValue() == 2d)) {
			return sqrt(value);
		}
		return new ASTNodeValue(concat("\\sqrt[", rootExponent, "]{", value,
				Character.valueOf('}')).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(double,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue root(double rootExponent, ASTNodeValue radiant) {
		if (rootExponent == 2d) {
			return sqrt(radiant);
		}
		return new ASTNodeValue(concat("\\sqrt[", rootExponent, "]{", radiant,
				Character.valueOf('}')).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sec(ASTNodeValue node) {
		return new ASTNodeValue(function("\\sec", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sech(ASTNodeValue node) {
		return new ASTNodeValue(function("sech", node).toString(), this);
	}

	/**
	 * 
	 * @param title
	 * @param numbering
	 * @return
	 */
	public StringBuffer section(String title, boolean numbering) {
		return heading("section", title, numbering);
	}

	/**
	 * 
	 * @param printNameIfAvailable
	 */
	public void setPrintNameIfAvailable(boolean printNameIfAvailable) {
		this.printNameIfAvailable = printNameIfAvailable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sin(ASTNodeValue node) {
		return new ASTNodeValue(function("\\sin", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sinh(ASTNodeValue node) {
		return new ASTNodeValue(function("\\sinh", node).toString(), this);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public ASTNodeValue sqrt(ASTNodeValue value) {
		return new ASTNodeValue(command("sqrt", value).toString(), this);
	}

	/**
	 * 
	 * @param title
	 * @param numbering
	 * @return
	 */
	public StringBuffer subsection(String title, boolean numbering) {
		return heading("subsection", title, numbering);
	}

	/**
	 * 
	 * @param title
	 * @param numbering
	 * @return
	 */
	public StringBuffer subsubsection(String title, boolean numbering) {
		return heading("subsubsection", title, numbering);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#symbolTime(java.lang.String)
	 */
	public ASTNodeValue symbolTime(String time) {
		return new ASTNodeValue(mathrm(time).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tan(ASTNodeValue node) {
		return new ASTNodeValue(function("\\tan", node).toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tanh(ASTNodeValue node) {
		return new ASTNodeValue(function("\\tanh", node).toString(), this);
	}

	/**
	 * 
	 * @param color
	 * @param text
	 * @return
	 */
	public StringBuilder textcolor(String color, Object text) {
		return command("textcolor", color, text);
	}

	/**
	 * Returns the LaTeX code to set the given String in type writer font.
	 * 
	 * @param id
	 * @return
	 */
	public StringBuilder texttt(String id) {
		return command("texttt", new StringBuffer(id));
	}

	/**
	 * 
	 * @param variable
	 * @return
	 */
	public String timeDerivative(String variable) {
		String d = mbox("d").toString();
		String dt = d + symbolTime("t");
		StringBuilder sb = frac(d, dt);
		sb.append(mbox(variable));
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#times(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue times(ASTNodeValue... values) {
		if (values.length == 0) {
			return new ASTNodeValue("", this);
		}
		StringBuilder v = new StringBuilder(values[0].toString());
		if (values[0].isSum()
				|| (values[0].isDifference() && !values[0].isUMinus())
				|| (values[0].isNumber() && values[0].toDouble() < 0)) {
			v = brackets(v);
		}
		for (int i = 1; i < values.length; i++) {
			v.append("\\cdot");
			if ((values[i].isDifference() || values[i].isSum())
					&& !values[i].isUMinus()) {
				v.append(brackets(values[i].toString()));
			} else {
				v.append(' ');
				v.append(values[i].toString());
			}
		}
		return new ASTNodeValue(v.toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#toString(org.sbml.jsbml.ASTNodeValue)
	 */
	public String toString(ASTNodeValue value) {
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#uiMinus(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue uMinus(ASTNodeValue value) {
		StringBuffer v = new StringBuffer();
		v.append('-');
		v.append(value.isSum() || value.isDifference() ? brackets(value)
				: value);
		return new ASTNodeValue(v.toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#unknownValue()
	 */
	public ASTNodeValue unknownValue() {
		return new ASTNodeValue(mathtext(" unknown ").toString(), this);
	}

	/**
	 * Creates a usepackage command for the given package with the optional
	 * options.
	 * 
	 * @param latexPackage
	 *            the name of the latex package
	 * @param options
	 *            options without commas
	 * @return usepackage command including system-dependent new line character.
	 */
	public StringBuffer usepackage(String latexPackage, String... options) {
		StringBuffer usepackage = new StringBuffer("\\usepackage");
		if (options.length > 0) {
			usepackage.append('[');
			boolean first = true;
			for (String option : options) {
				if (!first) {
					usepackage.append(',');
				} else {
					first = false;
				}
				usepackage.append(option);
			}
			usepackage.append(']');
		}
		usepackage.append('{');
		usepackage.append(latexPackage);
		usepackage.append('}');
		usepackage.append(newLine);
		return usepackage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#xor(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue xor(ASTNodeValue... nodes) {
		return logicalOperation(xor, nodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, int,
	 * java.lang.String)
	 */
	public ASTNodeValue compile(double mantissa, int exponent, String units) {
		return new ASTNodeValue(concat(format(mantissa), "\\cdot 10^{",
				exponent, "}").toString(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
	 */
	public ASTNodeValue getConstantAvogadro(String name) {
		return new ASTNodeValue(maskSpecialChars(name), this);
	}
}
