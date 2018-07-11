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

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.SBMLException;

/**
 * This class creates formula {@link String}s that represent the content of {@link ASTNode}s and try to reproduce an output similar to old COBRA SBML files.
 *
 *<p> "and" and "or" are configurable. This is the only difference between this compiler and the {@link FormulaCompilerLibSBML}. </p> 
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.4
 */
public class ConfigurableLogicalFormulaCompiler extends FormulaCompilerLibSBML{
  
  /**
   * 
   */
  private String andReplacement = " and ";
  /**
   * 
   */
  private String orReplacement = " or ";

  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(andReplacement, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#or(java.util.List)
   */
  @Override
  public ASTNodeValue or(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(orReplacement, nodes);
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
