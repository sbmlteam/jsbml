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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.StringTools;

/**
 * Produces an infix formula like {@link FormulaCompiler} but removes all the
 * piecewise functions. They are replaced by an id that is unique if you are
 * using the same {@link FormulaCompilerNoPiecewise} instance. The content of
 * the piecewise function is put in a {@link Map} and is transformed to use
 * if/then/else.
 * 
 * This class is used for example to create an SBML2XPP converter where (in XPP)
 * the piecewise operator is not supported.
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class FormulaCompilerNoPiecewise extends FormulaCompiler {

  /**
   * 
   */
  private Map<String, String> piecewiseMap = new LinkedHashMap<String, String>();
  /**
   * 
   */
  private String andReplacement = " & ";
  /**
   * 
   */
  private String orReplacement = " | ";

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> nodes) throws SBMLException {

    // create the piecewise output with if/then/else
    // We need to compile each nodes, in case they contain some other piecewise
    String piecewiseStr = "";

    int nbChildren = nodes.size();
    int nbIfThen = nbChildren / 2;
    boolean otherwise = (nbChildren % 2) == 1;

    for (int i = 0; i < nbIfThen; i++) {
      int index = i * 2;
      if (i > 0) {
        piecewiseStr += "(";
      }
      piecewiseStr = StringTools.concat(piecewiseStr, "if (", nodes.get(
        index + 1).compile(this).toString(), ") then (", nodes.get(
          index).compile(this).toString(), ") else ").toString();
    }

    if (otherwise) {
      piecewiseStr += "(" + nodes.get(nbChildren - 1).compile(this).toString() + ")";
    }

    // closing the opened parenthesis
    if (nbIfThen > 1) {
      for (int i = 1; i < nbIfThen; i++) {
        piecewiseStr += ")";
      }
    }

    if (andReplacement != null) {
      piecewiseStr = piecewiseStr.replaceAll(" and ", andReplacement);
    }
    if (orReplacement != null) {
      piecewiseStr = piecewiseStr.replaceAll(" or ", orReplacement);
    }

    // get a unique identifier for the piecewise expression in this compiler.
    int id = piecewiseMap.size() + 1;
    String piecewiseId = "piecew" + id;

    // Adding the piecewise to the list of piecewise
    piecewiseMap.put(piecewiseId, piecewiseStr);

    return new ASTNodeValue(" " + piecewiseId + " ", this);
  }


  /**
   * Gets a {@link Map} of the piecewise expressions that have been
   * transformed.
   * 
   * @return a {@link Map} of the piecewise expressions that have been
   *         transformed.
   */
  public Map<String, String> getPiecewiseMap() {
    return piecewiseMap;
  }


  /**
   * Gets the String that will be used to replace ' and ' (the mathML
   * &#60;and&#62; element) in the boolean expressions.
   * 
   * @return the {@link String} that will be used to replace ' and ' (the
   *         mathML &#60;and&#62; element) in the boolean expressions.
   */
  public String getAndReplacement() {
    return andReplacement;
  }


  /**
   * Sets the {@link String} that will be used to replace ' and ' (the mathML
   * &#60;and&#62; element) in the boolean expressions. The default value used
   * is ' &amp; '. If null is given, no replacement will be performed.
   * 
   * @param andReplacement
   */
  public void setAndReplacement(String andReplacement) {
    this.andReplacement = andReplacement;
  }


  /**
   * Gets the String that will be used to replace ' or ' (the mathML
   * &#60;or&#62; element) in the boolean expressions.
   * 
   * @return the {@link String} that will be used to replace ' or ' (the
   *         mathML &#60;or&#62; element) in the boolean expressions.
   */
  public String getOrReplacement() {
    return orReplacement;
  }


  /**
   * Sets the {@link String} that will be used to replace ' or ' (the mathML
   * &#60;or&#62; element) in the boolean expressions. The default value is '
   * | '. If null is given, no replacement will be performed.
   * 
   * @param orReplacement
   */
  public void setOrReplacement(String orReplacement) {
    this.orReplacement = orReplacement;
  }

}
