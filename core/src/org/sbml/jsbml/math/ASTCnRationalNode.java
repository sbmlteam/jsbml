/*
 * $Id:  ASTCnRationalNode.java 1:56:01 PM victor-kofia $
 * $URL: https://svn.code.sf.net/p/jsbml/code/trunk/core/src/org/sbml/jsbml/math/ASTCnRationalNode.java $
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
package org.sbml.jsbml.math;


/**
 * An Abstract Syntax Tree (AST) node representing a rational number
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCnRationalNode extends ASTCnNumberNode {
  
  /**
   * The numerator associated with this rational number
   */
  private int numerator;
  
  /**
   * The denominator associated with this rational number
   */
  private int denominator;

  /**
   * Get the value of the numerator
   * 
   * @return int numerator
   */
  public int getNumerator() {
    return numerator;
  }

  /**
   * Set the value of the numerator
   * 
   * @param int numerator
   */
  public void setNumerator(int numerator) {
    this.numerator = numerator;
  }

  /**
   * Get the value of the denominator
   * 
   * @return int denominator
   */
  public int getDenominator() {
    return denominator;
  }

  /**
   * Set the value of the denominator
   * 
   * @param int denominator
   */
  public void setDenominator(int denominator) {
    this.denominator = denominator;
  }
  
}
