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
import java.util.Vector;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class HTMLFormula extends MathMLCompiler {

  /**
   * HTML code for the empty set symbol &ldquo;&#8709;&rdquo;.
   */
  public static final String EMPTY_SET = "&#8709;";

  /**
   * HTML code for the reversible reaction arrow whose upper side is directed
   * to the right &ldquo;&#8652;&rdquo;.
   */
  public static final String REVERSIBLE_REACTION_ARROW = "&#x21cc;";

  /**
   * HTML code for the right arrow &ldquo;&#8594;&rdquo;.
   */
  public static final String RIGHT_ARROW = "&#8594;";

  /**
   * Multiplication symbol &ldquo;&#8901;&rdquo;.
   */
  public static final String C_DOT = "&#8901;";

  /**
   * Basic method which links several elements with a mathematical operator.
   * All empty StringBuffer object are excluded.
   * 
   * @param operator
   * @param elements
   * @return
   */
  private static final StringBuffer arith(Object operator, Object... elements) {
    List<Object> vsb = new Vector<Object>();
    for (Object sb : elements) {
      if (sb != null && sb.toString().length() > 0) {
        vsb.add(sb);
      }
    }
    StringBuffer equation = new StringBuffer();
    if (vsb.size() > 0) {
      equation.append(vsb.get(0));
    }
    String op = operator.toString();
    for (int count = 1; count < vsb.size(); count++) {
      StringTools.append(equation, op, vsb.get(count));
    }
    return equation;
  }

  /**
   * 
   * @param arith
   * @return
   */
  private static StringBuffer brackets(Object arith) {
    return StringTools.concat("(", arith, ")");
  }

  /**
   * Returns the basis to the power of the exponent as StringBuffer. Several
   * special cases are treated.
   * 
   * @param basis
   * @param exponent
   * @return
   */
  public static final StringBuffer pow(Object basis, Object exponent) {
    try {
      if (Double.parseDouble(exponent.toString()) == 0d) {
        return new StringBuffer("1");
      }
      if (Double.parseDouble(exponent.toString()) == 1d) {
        return basis instanceof StringBuffer ? (StringBuffer) basis
          : new StringBuffer(basis.toString());
      }
    } catch (NumberFormatException exc) {
    }
    String b = basis.toString();
    if (b.contains(C_DOT) || b.contains("-") || b.contains("+")
        || b.contains("/") || b.contains("<sup>")) {
      basis = brackets(basis);
    }
    String e = exponent.toString();
    if (e.contains(C_DOT) || e.substring(1).contains("-")
        || e.contains("+") || e.contains("/") || e.contains("<sup>")) {
      exponent = brackets(e);
    }
    return StringTools.concat(basis, "<sup>", exponent, "</sup>");
  }

  /**
   * Returns the sum of the given elements as StringBuffer.
   * 
   * @param summands
   * @return
   */
  public static final StringBuffer sum(Object... summands) {
    return brackets(arith(Character.valueOf('+'), summands));
  }

  /**
   * Returns the product of the given elements as StringBuffer.
   * 
   * @param factors
   * @return
   */
  public static final StringBuffer times(Object... factors) {
    return arith(C_DOT, factors);
  }

  /**
   * 
   * @param u
   * @return
   */
  public static String toHTML(Unit u) {
    StringBuffer times = new StringBuffer();
    if (u.getMultiplier() != 0) {
      if (u.getMultiplier() != 1) {
        times.append(StringTools.toString(u.getMultiplier()));
      }
      StringBuffer pow = new StringBuffer();
      pow.append(u.getKind().getSymbol());
      String prefix = u.getPrefix();
      if (prefix.length() > 0 && !prefix.startsWith("10")) {
        pow.insert(0, prefix);
      } else if (u.getScale() != 0) {
        pow.insert(0, ' ');
        pow = HTMLFormula.times(HTMLFormula.pow(Integer.valueOf(10), u
          .getScale()), pow);
      }
      times = HTMLFormula.times(times, pow);
    }
    if (u.getOffset() != 0) {
      times = HTMLFormula.sum(StringTools.toString(u.getOffset()), times);
    }
    return HTMLFormula.pow(times, StringTools.toString(u.getExponent()))
        .toString();
  }

  /**
   * Creates an HTML string representation of this UnitDefinition.
   * @param ud
   * 
   * @return
   */
  public static String toHTML(UnitDefinition ud) {
    StringBuilder sb = new StringBuilder();
    if (ud != null) {
      for (int i = 0; i < ud.getUnitCount(); i++) {
        Unit unit = ud.getUnit(i);
        if (i > 0) {
          sb.append(' ');
          sb.append(C_DOT);
          sb.append(' ');
        }
        sb.append(toHTML(unit));
      }
    }
    return sb.toString();
  }

  /**
   * @throws XMLStreamException
   */
  public HTMLFormula() throws XMLStreamException {
    super();
  }

  /**
   * @param reaction
   * @return
   * @throws SBMLException
   */
  public String reactionEquation(Reaction reaction) throws SBMLException {
    StringBuilder reactionEqn = new StringBuilder();
    int count = 0;
    for (SpeciesReference reactant : reaction.getListOfReactants()) {
      if (count > 0) {
        reactionEqn.append(" + ");
      }
      if (reactant.isSetStoichiometryMath()) {
        reactionEqn.append(reactant.getStoichiometryMath().getMath()
          .compile(this));
      } else if (reactant.getStoichiometry() != 1d) {
        reactionEqn.append(reactant.getStoichiometry());
      }
      reactionEqn.append(' ');
      reactionEqn
      .append(StringTools.encodeForHTML(reactant.getSpecies()));
      count++;
    }
    if (reaction.getReactantCount() == 0) {
      reactionEqn.append(EMPTY_SET);
    }
    reactionEqn.append(' ');
    reactionEqn.append(reaction.getReversible() ? RIGHT_ARROW
      : REVERSIBLE_REACTION_ARROW);
    reactionEqn.append(' ');
    count = 0;
    for (SpeciesReference product : reaction.getListOfProducts()) {
      if (count > 0) {
        reactionEqn.append(" + ");
      }
      if (product.isSetStoichiometryMath()) {
        reactionEqn.append(product.getStoichiometryMath().getMath()
          .compile(this));
      } else if (product.getStoichiometry() != 1d) {
        reactionEqn.append(product.getStoichiometry());
      }
      reactionEqn.append(' ');
      reactionEqn.append(StringTools.encodeForHTML(product.getSpecies()));
      count++;
    }
    if (reaction.getProductCount() == 0) {
      reactionEqn.append(EMPTY_SET);
    }
    return StringTools.toHTML(reactionEqn.toString());
  }

}
