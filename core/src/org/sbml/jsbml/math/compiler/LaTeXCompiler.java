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

import java.util.List;
import java.util.Locale;

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.math.ASTCnRealNode;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.util.StringTools;

/**
 * Converts {@link ASTNode2Value} objects into a LaTeX {@link String} to be
 * included into scientific writings or to be displayed in a GUI.
 * 
 * @author Andreas Dr&auml;ger
 * @author Victor Kofia
 * @since 1.0
 */
public class LaTeXCompiler extends StringTools implements ASTNode2Compiler {

  /**
   * Requires LaTeX package booktabs. Produces a fancy line at the bottom of a
   * table. This variable also includes the <code>end{longtable}</code>
   * command and a new line.
   */
  public static final String bottomrule = "\\bottomrule\\end{longtable}" + newLine();

  /**
   * The constant pi
   */
  public static final String CONSTANT_PI = "\\pi";

  /**
   * Surrounded by new line symbols. The begin of a description environment in
   * LaTeX.
   */
  public static final String descriptionBegin = "\\begin{description}" + newLine();

  /**
   * Surrounded by new line symbols. The end of a description environment.
   */
  public static final String descriptionEnd = "\\end{description}" + newLine();

  /**
   * Surrounded by new line symbols. Begin equation. This type of equation
   * requires the LaTeX package breqn. It will produce equations with
   * automatic line breaks (LaTeX will compute the optimal place for line
   * breaks). Unfortunately, this does not work for very long denominators.
   */
  public static final String eqBegin = newLine() + "\\begin{dmath}" + newLine(); // equation

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
   * 
   * 
   */
  public LaTeXCompiler() {
    printNameIfAvailable = false;
  }

  /**
   * @param namesInEquations
   */
  public LaTeXCompiler(boolean namesInEquations) {
    setPrintNameIfAvailable(namesInEquations);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#abs(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> abs(ASTNode2 value) throws SBMLException {
    StringBuffer abs = new StringBuffer("\\left\\lvert");
    abs.append(value.compile(this).toString());
    abs.append("\\right\\rvert");
    return new ASTNode2Value<String>(abs.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#and(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> and(List<ASTNode2> nodes) throws SBMLException {
    return logicalOperation(wedge, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccos(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccos(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(command("arccos", value.compile(this))
        .toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccosh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccosh(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arccosh", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccot(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccot(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arcot", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccoth(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccoth(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arccoth", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccsc(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsc(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arccsc", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arccsch(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsch(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arccsch", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsec(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsec(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arcsec", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsech(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsech(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arcsech", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsin(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsin(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arcsin", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arcsinh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsinh(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arcsinh", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arctan(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctan(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("arctan", value).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#arctanh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctanh(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(function("\\arctanh", value).toString(), this);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#ceiling(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ceiling(ASTNode2 value) throws SBMLException {
    StringBuffer ceiling = new StringBuffer("\\left\\lceil ");
    ceiling.append(value.compile(this).toString());
    ceiling.append("\\right\\rceil ");
    return new ASTNode2Value<String>(ceiling.toString(), this);
  }

  /**
   * Creates brackets if needed.
   * @param node
   * 
   * @return
   * @throws SBMLException
   */
  private String checkBrackets(ASTNode2 node) throws SBMLException {
    String term = node.compile(this).toString();
    switch(node.getType()) {
    case SUM:
    case MINUS:
      term = brackets(term).toString();
      break;
    case REAL:
      if (((ASTCnRealNode)node).getReal() < 0d) {
        term = brackets(term).toString();
      }
      break;
    default:
      break;
    }
    return term;
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public <T> ASTNode2Value<String> compile(CallableSBase variable) {
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
      return new ASTNode2Value<String>(value.toString(), this);

    } else if (variable instanceof Compartment) {
      Compartment c = (Compartment) variable;
      return compile(c);
    }
    // TODO: more special cases of names!!! PARAMETER, FUNCTION DEF,
    // REACTION.
    return new ASTNode2Value<String>(mathtt(maskSpecialChars(variable.getId()))
        .toString(), this);
    // else if (variable instanceof Parameter) {
    // return new StringBuffer("parameter");
    // }
    // return new StringBuffer("variable:"+variable);
  }

  /**
   * This method returns the correct LaTeX expression for a function which
   * returns the size of a compartment. This can be a volume, an area, a
   * length or a point.
   */
  @Override
  public <T> ASTNode2Value<String> compile(Compartment c) {
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
    return new ASTNode2Value<String>(mathrm(value.toString()).append(
      brackets(getNameOrID(c))).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(double, int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double mantissa, int exponent, String units) {
    StringBuffer sb = concat(format(mantissa), "\\cdot 10^{", exponent, "}");

    // return (mantissa < 0.0) ? new ASTNode2Value<String>(brackets(sb).toString(),
    // this) : new ASTNode2Value<String>(sb.toString(), this);
    return new ASTNode2Value<String>(sb.toString(), this);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(double, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double real, String units) {
    // TODO: deal with Units.
    return new ASTNode2Value<String>(format(real).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<Integer> compile(int integer, String units) {
    ASTNode2Value<Integer> value = new ASTNode2Value<Integer>(integer, this);
    // TODO: value.setUnits(units);
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#compile(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(String name) {
    return new ASTNode2Value<String>(maskSpecialChars(name), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#cos(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cos(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\cos", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#cosh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cosh(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\cosh", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#cot(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cot(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\cot", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#coth(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> coth(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\coth", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#csc(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csc(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\csc", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#csch(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csch(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("csch", node).toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#delay(java.lang.String, org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> delay(String delayName, ASTNode2 x, ASTNode2 delay) throws SBMLException {
    delayName = delayName == null ? "delay" : delayName;
    return new ASTNode2Value<String>(concat(
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#eq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> eq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " = ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#exp(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> exp(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\exp", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#factorial(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> factorial(ASTNode2 node) throws SBMLException {
    StringBuilder value;
    if (!(node.getChildCount() == 1)) {
      value = brackets(node.compile(this).toString());
    } else {
      value = new StringBuilder(node.compile(this).toString());
    }
    value.append('!');
    return new ASTNode2Value<String>(value.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#floor(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> floor(ASTNode2 value) throws SBMLException {
    StringBuilder floor = new StringBuilder("\\left\\lfloor ");
    floor.append(value.compile(this).toString());
    floor.append("\\right\\rfloor ");
    return new ASTNode2Value<String>(floor.toString(), this);
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
   * @see org.sbml.jsbml.ASTNode2Compiler#frac(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> frac(ASTNode2 numerator, ASTNode2 denominator)
      throws SBMLException {
    return new ASTNode2Value<String>(command("frac", numerator.compile(this),
      denominator.compile(this)).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#frac(int, int)
   */
  @Override
  public <T> ASTNode2Value<String> frac(int numerator, int denominator) {
    return new ASTNode2Value<String>(frac(Integer.valueOf(numerator),
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
   * @see org.sbml.jsbml.ASTNode2Compiler#function(org.sbml.jsbml.FunctionDefinition, org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<String> function(FunctionDefinition fun, List<ASTNode2> args)
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

    return new ASTNode2Value<String>(value.toString(), this);
  }

  /**
   * Decides whether to produce brackets.
   * 
   * @param func
   * @param value
   * @return
   * @throws SBMLException
   */
  private StringBuilder function(String func, ASTNode2 value)
      throws SBMLException {
    return function(func, value.getChildCount() == 1 ? value.compile(this).toString()
      : brackets(value.compile(this)).toString());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#function(java.lang.String, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(String functionDefinitionName,
    List<ASTNode2> args) throws SBMLException
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

    return new ASTNode2Value<String>(value.toString(), this);
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
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#function(java.lang.Object, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(T functionDefinitionName,
    List<ASTNode2> args) throws SBMLException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#greaterEqual(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> geq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " \\geq ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> getConstantAvogadro(String name) {
    return new ASTNode2Value<String>(maskSpecialChars(name == null ? "avogadro" : name), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#getConstantE()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantE() {
    return new ASTNode2Value<String>(new String(CONSTANT_E), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#getConstantFalse()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantFalse() {
    return new ASTNode2Value<String>(new String(CONSTANT_FALSE), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#getConstantPi()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantPi() {
    return new ASTNode2Value<String>(new String(CONSTANT_PI), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#getConstantTrue()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantTrue() {
    return new ASTNode2Value<String>(new String(CONSTANT_TRUE), this);
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
   * @see org.sbml.jsbml.ASTNode2Compiler#getNegativeInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getNegativeInfinity() {
    return new ASTNode2Value<String>(new String(NEGATIVE_ININITY), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#getPositiveInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getPositiveInfinity() {
    return new ASTNode2Value<String>(new String(POSITIVE_INFINITY), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#greaterThan(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> gt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " > ", right).toString(), this);
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
   * @see org.sbml.jsbml.ASTNode2Compiler#lambda(org.sbml.jsbml.ASTNode2[])
   */
  @Override
  public <T> ASTNode2Value<String> lambda(List<ASTNode2> nodes) throws SBMLException {
    return function((FunctionDefinition) null, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#lessEqual(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> leq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " \\leq ", right).toString(),
        this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#ln(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ln(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\ln", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 node) throws SBMLException {
    return log(null, node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 base, ASTNode2 value) throws SBMLException {
    StringBuilder v = new StringBuilder("\\log");
    if (base != null) {
      StringTools.append(v, "_{", base.compile(this), "}");
    }
    StringTools.append(v, "{", value.getChildCount() == 1 ? value.compile(this)
      : brackets(value.compile(this)), "}");
    return new ASTNode2Value<String>(v.toString(), this);
  }

  /**
   * 
   * @param symbol
   * @param values
   * @return
   * @throws SBMLException
   */
  private <T> ASTNode2Value<String> logicalOperation(String symbol, List<ASTNode2> values)
      throws SBMLException {
    StringBuffer value = new StringBuffer();
    int i = 0;
    for (ASTNode2 v : values) {
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
    return new ASTNode2Value<String>(value.toString(), this);
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
   * @see org.sbml.jsbml.ASTNode2Compiler#lessThan(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> lt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " < ", right).toString(), this);
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
   * @see org.sbml.jsbml.ASTNode2Compiler#minus(org.sbml.jsbml.ASTNode2[])
   */
  @Override
  public <T> ASTNode2Value<String> minus(List<ASTNode2> nodes) throws SBMLException {
    if (nodes.size() == 0) {
      return new ASTNode2Value<String>("", this);
    }
    StringBuilder value = new StringBuilder();
    value.append(nodes.get(0).compile(this).toString());
    for (int i = 1; i < nodes.size(); i++) {
      value.append('-');
      value.append(checkBrackets(nodes.get(i)));

    }
    return new ASTNode2Value<String>(value.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#notEqual(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> neq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(concat(left.toLaTeX(), " \\neq ", right.compile(this))
        .toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#not(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> not(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(concat(
      "\\neg ",
      (node.getChildCount() == 0) ? node.compile(this)
        : brackets(node.compile(this))).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#or(org.sbml.jsbml.ASTNode2[])
   */
  @Override
  public <T> ASTNode2Value<String> or(List<ASTNode2> nodes) throws SBMLException {
    return logicalOperation(or, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#piecewise(org.sbml.jsbml.ASTNode2[])
   */
  @Override
  public <T> ASTNode2Value<String> piecewise(List<ASTNode2> nodes) throws SBMLException {
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
    return new ASTNode2Value<String>(v.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNode2Compiler#plus(org.sbml.jsbml.ASTNode2[])
   */
  @Override
  public <T> ASTNode2Value<String> plus(List<ASTNode2> nodes) throws SBMLException {
    if (nodes.size() > 0) {
      StringBuilder value = new StringBuilder();

      value.append(nodes.get(0).compile(this));

      for (int i = 1; i < nodes.size(); i++) {

        value.append('+');
        value.append(checkBrackets(nodes.get(i)));

      }
      return new ASTNode2Value<String>(value.toString(), this);
    }
    return new ASTNode2Value<String>(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#pow(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> pow(ASTNode2 base, ASTNode2 exponent)
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
    return new ASTNode2Value<String>(value.toString(), this);
  }

  /**
   * Creates a relation between two {@link ASTNode2}s.
   * 
   * @param left
   * @param relationSymbol
   * @param right
   * @return
   * @throws SBMLException
   */
  private StringBuilder relation(ASTNode2 left, String relationSymbol,
    ASTNode2 right) throws SBMLException {
    StringBuilder value = new StringBuilder();

    value.append(((left.getType().name().startsWith("RELATIONAL")) ? brackets(left.compile(this))
      : left.compile(this).toString()));
    value.append(relationSymbol);
    value.append(((right.getType().name().startsWith("RELATIONAL")) ? brackets(right.compile(this))
      : right.compile(this).toString()));
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#root(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(ASTNode2 rootExponent, ASTNode2 value)
      throws SBMLException {
    if (rootExponent.getType() == Type.REAL && (((ASTCnRealNode)rootExponent).getReal() == 2d)) {
      return sqrt(value);
    }
    return new ASTNode2Value<String>(concat("\\sqrt[", rootExponent, "]{",
      value.compile(this), Character.valueOf('}')).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#root(double, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(double rootExponent, ASTNode2 radiant)
      throws SBMLException {
    if (rootExponent == 2d) {
      return sqrt(radiant);
    }
    return new ASTNode2Value<String>(concat("\\sqrt[", rootExponent, "]{",
      radiant.compile(this), Character.valueOf('}')).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sec(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sec(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\sec", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sech(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sech(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("sech", node).toString(), this);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#selector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> selector(List<ASTNode2> nodes) throws SBMLException {
    return function("selector", nodes);
  }

  /**
   * 
   * @param printNameIfAvailable
   */
  public void setPrintNameIfAvailable(boolean printNameIfAvailable) {
    this.printNameIfAvailable = printNameIfAvailable;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sin(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sin(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\sin", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sinh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sinh(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\sinh", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#sqrt(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sqrt(ASTNode2 value) throws SBMLException {
    return new ASTNode2Value<String>(
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#symbolTime(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> symbolTime(String time) {
    return new ASTNode2Value<String>(mathrm(time).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#tan(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tan(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\tan", node).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#tanh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tanh(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(function("\\tanh", node).toString(), this);
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#times(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> times(List<ASTNode2> values) throws SBMLException {
    if (values.size() == 0) {
      return new ASTNode2Value<String>("", this);
    }
    StringBuilder v = new StringBuilder(checkBrackets(values.get(0)));

    for (int i = 1; i < values.size(); i++) {
      v.append("\\cdot");

      v.append(' ');
      v.append(checkBrackets(values.get(i)).toString());

    }
    return new ASTNode2Value<String>(v.toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#uMinus(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> uMinus(ASTNode2 value) throws SBMLException {

    StringBuffer v = new StringBuffer();
    v.append('-');
    v.append(checkBrackets(value).toString());
    return new ASTNode2Value<String>(v.toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#unknownValue()
   */
  @Override
  public <T> ASTNode2Value<String> unknownValue() {
    return new ASTNode2Value<String>(mathtext(" unknown ").toString(), this);
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
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#vector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> vector(List<ASTNode2> nodes) throws SBMLException {
    return function("vector", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#xor(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> xor(List<ASTNode2> nodes) throws SBMLException {
    return logicalOperation(xor, nodes);
  }

}
