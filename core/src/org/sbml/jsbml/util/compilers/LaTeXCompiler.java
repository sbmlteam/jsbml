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

import java.util.List;
import java.util.Locale;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.StringTools;

/**
 * Converts {@link ASTNodeValue} objects into a LaTeX {@link String} to be
 * included into scientific writings or to be displayed in a GUI.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class LaTeXCompiler extends StringTools implements ASTNodeCompiler {

  /**
   * Requires LaTeX package booktabs. Produces a fancy line at the bottom of a
   * table. This variable also includes the <pre>\end{longtable}</pre>
   * command and a new line.
   */
  public static final String bottomrule = "\\bottomrule\\end{longtable}"
      + newLine();

  /**
   * The constant pi
   */
  public static final String CONSTANT_PI = "\\pi";

  /**
   * Surrounded by new line symbols. The begin of a description environment in
   * LaTeX.
   */
  public static final String descriptionBegin = "\\begin{description}"
      + newLine();

  /**
   * Surrounded by new line symbols. The end of a description environment.
   */
  public static final String descriptionEnd = "\\end{description}"
      + newLine();

  /**
   * Surrounded by new line symbols. Begin equation. This type of equation
   * requires the LaTeX package breqn. It will produce equations with
   * automatic line breaks (LaTeX will compute the optimal place for line
   * breaks). Unfortunately, this does not work for very long denominators.
   */
  public static final String eqBegin = newLine() + "\\begin{dmath}"
      + newLine(); // equation

  /**
   * End equation; cf. eqBegin. Surrounded by new line symbols.
   */
  public static final String eqEnd = newLine() + "\\end{dmath}" + newLine(); // equation

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
  public static final String lineBreak = "\\\\" + newLine();

  /**
   * Produces a fancy line in tables. Requires LaTeX package booktabs. Starts
   * and ends with a new line.
   */
  public static final String midrule = newLine() + "\\midrule" + newLine();

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
   * Surrounded by new line symbols.
   */
  public static final String toprule = newLine() + "\\toprule" + newLine();

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
   */
  public static final String implies = "\\rightarrow ";
  

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
    if ((Integer.MIN_VALUE < number) && (number < Integer.MAX_VALUE)) {
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
    StringBuffer heading = new StringBuffer(newLine());
    heading.append("\\");
    heading.append(kind);
    if (!numbering) {
      heading.append('*');
    }
    heading.append('{');
    heading.append(title);
    heading.append('}');
    heading.append(newLine());
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
    StringBuilder masked = new StringBuilder();
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
          if ((i == 0) || (!hyphen)) {
            masked.append('\\');
          } else if (hyphen && (string.charAt(i - 1) != '\\')) {
            masked.append("\\-\\"); // masked.append('\\');
            // } else if ((atI == '[') || (atI == ']')) {
          }
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
   * Creates a new {@link LaTeXCompiler} instance.
   * 
   */
  public LaTeXCompiler() {
    printNameIfAvailable = false;
  }

  /**
   * Creates a new {@link LaTeXCompiler} instance.
   * 
   * @param namesInEquations
   */
  public LaTeXCompiler(boolean namesInEquations) {
    setPrintNameIfAvailable(namesInEquations);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode value) throws SBMLException {
    StringBuffer abs = new StringBuffer("\\left\\lvert");
    abs.append(value.compile(this).toString());
    abs.append("\\right\\rvert");
    return new ASTNodeValue(abs.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(wedge, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode value) throws SBMLException {
    return new ASTNodeValue(command("arccos", value.compile(this))
      .toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccosh(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arccosh", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccot(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arcot", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccoth(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arccoth", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsc(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arccsc", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsch(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arccsch", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsec(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arcsec", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsech(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arcsech", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsin(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arcsin", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsinh(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arcsinh", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctan(ASTNode value) throws SBMLException {
    return new ASTNodeValue(function("arctan", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctanh(ASTNode value) throws SBMLException {
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
    if (formula != null) {
      buffer.append(formula);
    }
    buffer.append("\\right)");
    return buffer;
  }

  /**
   * Creates brackets if needed.
   * 
   * @param node
   * @return
   * @throws SBMLException
   */
  private String checkBrackets(ASTNode node) throws SBMLException {
    String term = node.compile(this).toString();
    // TODO: This should be smarter
    if (node.isSum() || node.isDifference() || node.isUMinus()) {
      term = brackets(term).toString();
    } else if (node.isReal()) {
      if (node.getReal() < 0d) {
        term = brackets(term).toString();
      }
    }

    return term;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ceiling(ASTNode value) throws SBMLException {
    StringBuffer ceiling = new StringBuffer("\\left\\lceil ");
    ceiling.append(value.compile(this).toString());
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
  @Override
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double mantissa, int exponent, String units) {
    StringBuffer sb = concat(format(mantissa), "\\cdot 10^{", exponent, "}");

    // return (mantissa < 0.0) ? new ASTNodeValue(brackets(sb).toString(),
    // this) : new ASTNodeValue(sb.toString(), this);
    return new ASTNodeValue(sb.toString(), this);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double real, String units) {
    // TODO: deal with Units.
    return new ASTNodeValue(format(real).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(int integer, String units) {
    // TODO: deal with Units.
    return new ASTNodeValue(integer, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNodeValue compile(CallableSBase variable) {
    if (variable instanceof Species) {
      Species species = (Species) variable;
      Compartment c = species.getCompartmentInstance();
      boolean concentration = !species.getHasOnlySubstanceUnits() && (c != null)
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
    // TODO: more special cases of names!!! PARAMETER, FUNCTION DEF,
    // REACTION.
    return new ASTNodeValue(mathtt(maskSpecialChars(variable.getId()))
      .toString(), this);
    // else if (variable instanceof Parameter) {
    // return new StringBuffer("parameter");
    // }
    // return new StringBuffer("variable:"+variable);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  public ASTNodeValue compile(String name) {
    return new ASTNodeValue(maskSpecialChars(name), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cos(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\cos", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cosh(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\cosh", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cot(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\cot", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue coth(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\coth", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csc(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\csc", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csch(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("csch", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode, double, java.lang.String)
   */
  @Override
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
    String timeUnits) throws SBMLException {
    // TODO: deal with units.
    return new ASTNodeValue(concat(
      mathrm(maskSpecialChars(delayName)),
      brackets(concat(x.compile(this).toString(), ", ", delay
        .compile(this).toString()))).toString(), this);
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
    itemBuffer.append(newLine());
    return itemBuffer;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#eq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " = ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue exp(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\exp", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue factorial(ASTNode node) throws SBMLException {
    StringBuilder value;
    if (!node.isUnary()) {
      value = brackets(node.compile(this).toString());
    } else {
      value = new StringBuilder(node.compile(this).toString());
    }
    value.append('!');
    return new ASTNodeValue(value.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue floor(ASTNode value) throws SBMLException {
    StringBuilder floor = new StringBuilder("\\left\\lfloor ");
    floor.append(value.compile(this).toString());
    floor.append("\\right\\rfloor ");
    return new ASTNodeValue(floor.toString(), this);
  }

  /**
   * This method returns a {@link StringBuffer} representing a properly
   * LaTeX formatted number.
   * 
   * @param value
   * @return
   */
  public StringBuffer format(double value) {
    StringBuffer sb = new StringBuffer();
    String val = StringTools.toString(Locale.ENGLISH, value);
    if (val.contains("E")) {
      String split[] = val.split("E");
      val = "10^{" + format(Double.parseDouble(split[1])) + "}";
      if (split[0].equals("-1.0")) {
        val = "-" + val;
      } else if (!split[0].equals("1.0")) {
        val = format(Double.parseDouble(split[0])) + "\\cdot " + val;
      }

    } else if (value - ((int) value) == 0) {
      sb.append(((int) value));
    } else {
      sb.append(val);
    }

    return sb;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws SBMLException {
    return new ASTNodeValue(command("frac", numerator.compile(this),
      denominator.compile(this)).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#frac(int, int)
   */
  @Override
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, org.sbml.jsbml.ASTNodeValue[])
   */
  @Override
  public ASTNodeValue function(FunctionDefinition fun, List<ASTNode> args)
      throws SBMLException {
    StringBuffer value = new StringBuffer();
    int length;
    if (fun != null) {
      value.append(mathtt(LaTeXCompiler.maskSpecialChars(
        fun.isSetName() && printNameIfAvailable ? fun.getName() : fun.getId())));
      length = args.size();
    } else if (args.size() == 1) {
      length = 0;
      value.append("\\lambda");
      value.append(brackets(null));
    } else {
      value.append("\\lambda");
      length = args.size() - 1;
    }
    StringBuilder argList = new StringBuilder();

    for (int i = 0; i < length; i++) {
      if (i > 0) {
        argList.append(", ");
      }
      argList.append(args.get(i).compile(this));
    }

    if (length > 0) {
      value.append(brackets(argList));
    }

    if ((0 <= length) && (length < args.size())) {
      value.append(" = ");
      value.append(args.get(args.size() - 1).compile(this));
    }

    return new ASTNodeValue(value.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public ASTNodeValue function(String functionDefinitionName,
    List<ASTNode> args) throws SBMLException
  {
    StringBuffer value = new StringBuffer();
    int length = args.size();

    value.append(mathtt(LaTeXCompiler.maskSpecialChars(functionDefinitionName)));

    StringBuilder argList = new StringBuilder();

    for (int i = 0; i < length; i++) {
      if (i > 0) {
        argList.append(", ");
      }
      argList.append(args.get(i).compile(this));
    }

    if (length > 0) {
      value.append(brackets(argList));
    }

    return new ASTNodeValue(value.toString(), this);
  }


  /**
   * Decides whether to produce brackets.
   * 
   * @param func
   * @param value
   * @return
   * @throws SBMLException
   */
  private StringBuilder function(String func, ASTNode value)
      throws SBMLException {
    return function(func, value.isUnary() ? value.compile(this).toString()
      : brackets(value.compile(this)).toString());
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#greaterEqual(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " \\geq ", right).toString(),
      this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    return new ASTNodeValue(maskSpecialChars(name), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    return new ASTNodeValue(new String(CONSTANT_E), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public ASTNodeValue getConstantFalse() {
    return new ASTNodeValue(new String(CONSTANT_FALSE), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public ASTNodeValue getConstantPi() {
    return new ASTNodeValue(new String(CONSTANT_PI), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantTrue()
   */
  @Override
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getNegativeInfinity()
   */
  @Override
  public ASTNodeValue getNegativeInfinity() {
    return new ASTNodeValue(new String(NEGATIVE_ININITY), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public ASTNodeValue getPositiveInfinity() {
    return new ASTNodeValue(new String(POSITIVE_INFINITY), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#greaterThan(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
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

  /**
   * @param id
   * @return
   */
  public StringBuilder label(String id) {
    return command("label", new StringBuilder(id));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#lambda(org.sbml.jsbml.ASTNode[])
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> nodes) throws SBMLException {
    return function((FunctionDefinition) null, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#lessEqual(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " \\leq ", right).toString(),
      this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\ln", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode node) throws SBMLException {
    return log(null, node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode base, ASTNode value) throws SBMLException {
    StringBuilder v = new StringBuilder("\\log");
    if (base != null) {
      StringTools.append(v, "_{", base.compile(this), "}");
    }
    StringTools.append(v, "{", value.isUnary() ? value.compile(this)
      : brackets(value.compile(this)), "}");
    return new ASTNodeValue(v.toString(), this);
  }

  /**
   * 
   * @param symbol
   * @param values
   * @return
   * @throws SBMLException
   */
  private ASTNodeValue logicalOperation(String symbol, List<ASTNode> values)
      throws SBMLException {
    StringBuffer value = new StringBuffer();
    int i = 0;
    for (ASTNode v : values) {
      if (v.getChildCount() > 0) {
        value.append(leftBrace);
      }
      value.append(v.compile(this).toString());
      if (v.getChildCount() > 0) {
        value.append(rightBrace);
      }
      if (i < values.size() - 1) {
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
    buffer.append(newLine());
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
    // buffer.append(newLine());
    buffer.append(head);
    buffer.append("\\endhead");
    // buffer.append(bottomrule);
    // buffer.append("\\endlastfoot");
    buffer.append(newLine());
    return buffer;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " < ", right).toString(), this);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNode[])
   */
  @Override
  public ASTNodeValue minus(List<ASTNode> nodes) throws SBMLException {
    if (nodes.size() == 0) {
      return new ASTNodeValue("", this);
    }
    StringBuilder value = new StringBuilder();
    value.append(nodes.get(0).compile(this).toString());
    for (int i = 1; i < nodes.size(); i++) {
      value.append('-');
      value.append(checkBrackets(nodes.get(i)));

    }
    return new ASTNodeValue(value.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#notEqual(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(concat(left, " \\neq ", right.compile(this))
      .toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue not(ASTNode node) throws SBMLException {
    return new ASTNodeValue(concat(
      "\\neg ",
      (node.getChildCount() == 0) ? node.compile(this)
        : brackets(node.compile(this))).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNode[])
   */
  @Override
  public ASTNodeValue or(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(or, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#piecewise(org.sbml.jsbml.ASTNode[])
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> nodes) throws SBMLException {
    StringBuilder v = new StringBuilder("\\begin{dcases}");
    v.append(newLine());
    for (int i = 0; i < nodes.size() - 1; i++) {
      v.append(nodes.get(i).compile(this));
      v.append(((i % 2) == 0) ? " & \\text{if\\ } " : lineBreak);
    }
    v.append(nodes.get(nodes.size() - 1).compile(this));
    if ((nodes.size() % 2) == 1) {
      v.append(" & \\text{otherwise}");
      v.append(newLine());
    }
    v.append("\\end{dcases}");
    return new ASTNodeValue(v.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNode[])
   */
  @Override
  public ASTNodeValue plus(List<ASTNode> nodes) throws SBMLException {
    if (nodes.size() > 0) {
      StringBuilder value = new StringBuilder();

      value.append(nodes.get(0).compile(this));

      for (int i = 1; i < nodes.size(); i++) {

        value.append('+');
        value.append(checkBrackets(nodes.get(i)));

      }
      return new ASTNodeValue(value.toString(), this);
    }
    return new ASTNodeValue(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent)
      throws SBMLException {
    StringBuilder value = new StringBuilder();
    value.append(base.compile(this));
    String exp = exponent.compile(this).toString();
    if (!exp.equals("1")) {
      if (!(base.getChildCount() < 2)) {
        value = brackets(value);
      }
      value.append('^');
      value.append('{');
      value.append(exp);
      value.append('}');
    }
    return new ASTNodeValue(value.toString(), this);
  }

  /**
   * Creates a relation between two {@link ASTNode}s.
   * 
   * @param left
   * @param relationSymbol
   * @param right
   * @return
   * @throws SBMLException
   */
  private StringBuilder relation(ASTNode left, String relationSymbol,
    ASTNode right) throws SBMLException {
    StringBuilder value = new StringBuilder();

    value.append(((left.isRelational()) ? brackets(left.compile(this))
      : left.compile(this).toString()));
    value.append(relationSymbol);
    value.append(((right.isRelational()) ? brackets(right.compile(this))
      : right.compile(this).toString()));
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode value)
      throws SBMLException {
    if (rootExponent.isNumber() && (rootExponent.getReal() == 2d)) {
      return sqrt(value);
    }
    return new ASTNodeValue(concat("\\sqrt[", rootExponent, "]{",
      value.compile(this), Character.valueOf('}')).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws SBMLException {
    if (rootExponent == 2d) {
      return sqrt(radiant);
    }
    return new ASTNodeValue(concat("\\sqrt[", rootExponent, "]{",
      radiant.compile(this), Character.valueOf('}')).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sec(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\sec", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sech(ASTNode node) throws SBMLException {
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sin(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\sin", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sinh(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\sinh", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sqrt(ASTNode value) throws SBMLException {
    return new ASTNodeValue(
      command("sqrt", value.compile(this)).toString(), this);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNodeValue symbolTime(String time) {
    return new ASTNodeValue(mathrm(time).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode node) throws SBMLException {
    return new ASTNodeValue(function("\\tan", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tanh(ASTNode node) throws SBMLException {
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
    StringBuilder sb = frac(d, d + symbolTime("t"));
    sb.append(mbox(variable));
    return sb.toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#times(java.util.List)
   */
  @Override
  public ASTNodeValue times(List<ASTNode> values) throws SBMLException {
    if (values.size() == 0) {
      return new ASTNodeValue("", this);
    }
    StringBuilder v = new StringBuilder(checkBrackets(values.get(0)));

    for (int i = 1; i < values.size(); i++) {
      v.append("\\cdot");

      v.append(' ');
      v.append(checkBrackets(values.get(i)).toString());

    }
    return new ASTNodeValue(v.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode value) throws SBMLException {

    StringBuffer v = new StringBuffer();
    v.append('-');
    v.append(checkBrackets(value).toString());
    return new ASTNodeValue(v.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#unknownValue()
   */
  @Override
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
    usepackage.append(newLine());
    return usepackage;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#xor(java.util.List)
   */
  @Override
  public ASTNodeValue xor(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(xor, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public ASTNodeValue selector(List<ASTNode> nodes) throws SBMLException {
    return function("selector", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector(java.util.List)
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
    return logicalOperation(implies, values);
  }

  @Override
  public ASTNodeValue getRateOf(ASTNode nameAST) {

    StringBuffer value = new StringBuffer();

    value.append(mathtt("rateOf"));

      value.append(brackets(nameAST.getName()));

    return new ASTNodeValue(value.toString(), this);
  }

}
