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

import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.ASTCnRealNode;
import org.sbml.jsbml.math.ASTNode2;

/**
 * 
 * 
 * @author Andreas Dr&auml;ger
 * @author Victor Kofia
 * 
 * @since 1.0
 */
public class LibSBMLFormulaCompiler extends FormulaCompiler {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccos(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arccos(ASTNode2 node) throws SBMLException {
    return function("acos", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccosh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arccosh(ASTNode2 node) throws SBMLException {
    return function("arccosh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccot(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arccot(ASTNode2 node) throws SBMLException {
    return function("arccot", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccoth(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arccoth(ASTNode2 node) throws SBMLException {
    return function("arccoth", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccsc(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arccsc(ASTNode2 node) throws SBMLException {
    return function("arccsc", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccsch(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arccsch(ASTNode2 node) throws SBMLException {
    return function("arccsch", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsec(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arcsec(ASTNode2 node) throws SBMLException {
    return function("arcsec", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsech(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arcsech(ASTNode2 node) throws SBMLException {
    return function("arcsech", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsin(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arcsin(ASTNode2 node) throws SBMLException {
    return function("asin", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsinh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arcsinh(ASTNode2 node) throws SBMLException {
    return function("arcsinh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arctan(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arctan(ASTNode2 node) throws SBMLException {
    return function("atan", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arctanh(org.sbml.jsbml. ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  arctanh(ASTNode2 node) throws SBMLException {
    return function("arctanh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#and(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String>  and(List<ASTNode2> nodes) throws SBMLException {
    return function("and", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#or(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String>  or(List<ASTNode2> nodes) throws SBMLException {
    return function("or", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#xor(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String>  xor(List<ASTNode2> nodes) throws SBMLException {
    return function("xor", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#eq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  eq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("eq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#neq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  neq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("neq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#geq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  geq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("geq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#eq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  gt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("gt", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#eq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  leq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("leq", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#eq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  lt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("lt", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getNegativeInfinity()
   */
  public <T> ASTNode2Value<String>  getNegativeInfinity() {
    return new ASTNode2Value<String>("-INF", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getPositiveInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getPositiveInfinity() {
    return new ASTNode2Value<String>("INF", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#pow(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  pow(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("pow", left, right);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#ln(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  ln(ASTNode2 node) throws SBMLException {
    return function("log", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  log(ASTNode2 node) throws SBMLException {
    return function("log10", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  log(ASTNode2 left, ASTNode2 right) throws SBMLException {
    if (left instanceof ASTCnRealNode && ((ASTCnRealNode)left).getReal() == 10) {
      return function("log10", right);
    } else {
      return function("log", left, right);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getConstantE()
   */
  @Override
  public <T> ASTNode2Value<String>  getConstantE() {
    return new ASTNode2Value<String>("exponentiale", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#factorial(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  factorial(ASTNode2 node) {
    try {
      return function("factorial", node);
    } catch (SBMLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#root(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  root(ASTNode2 rootExponent, ASTNode2 radiant)
      throws SBMLException
      {
    return function("root", rootExponent, radiant);
      }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#root(double, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  root(double rootExponent, ASTNode2 radiant)
      throws SBMLException
      {
    return function("root", new ASTCnRealNode(rootExponent), radiant);
      }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#sqrt(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String>  sqrt(ASTNode2 node) throws SBMLException {
    return function("sqrt", node);
  }

}
