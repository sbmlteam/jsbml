/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  joIntegerly by the following organizations:
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

import org.apache.log4j.Logger;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * An Abstract Syntax Tree (AST) node representing a rational number
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCnRationalNode extends ASTCnNumberNode<ValuePair<Double,Double>> {

  /**
   * 
   */
  private static final long serialVersionUID = -6844137576018089451L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnRationalNode.class);

  /**
   * Creates a new {@link ASTCnRationalNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnRationalNode() {
    super();
    setType(Type.RATIONAL);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnRationalNode}.
   * 
   * @param node
   *            the {@link ASTCnRationalNode} to be copied.
   */
  public ASTCnRationalNode(ASTCnRationalNode node) {
    super(node);
    setType(Type.RATIONAL);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCnNumberNode#clone()
   */
  @Override
  public ASTCnRationalNode clone() {
    return new ASTCnRationalNode(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNodeValue compile(ASTNode2Compiler compiler) {
    ASTNodeValue value = compiler.frac(getNumerator(), getDenominator());
    value.setType(getType());
    MathContainer parent = getParentSBMLObject();
    if (parent != null) {
      value.setLevel(parent.getLevel());
      value.setVersion(parent.getVersion());
    }
    return value;
  }

  /**
   * Get the denominator value of this node.
   * 
   * @return double denominator
   */
  public double getDenominator() {
    return isSetDenominator() ? this.number.getV() : Double.NaN;
  }

  /**
   * Get the numerator value of this node.
   * 
   * @return double numerator
   */
  public double getNumerator() {
    return isSetNumerator() ? this.number.getL() : Double.NaN;
  }

  /**
   * Returns True iff denominator has been set
   * 
   * @param null
   * @return boolean
   */
  public boolean isSetDenominator() {
    return this.number.getV() != null;
  }

  /**
   * Returns True iff numerator has been set
   * 
   * @param null
   * @return boolean
   */
  public boolean isSetNumerator() {
    return this.number.getL() != null;
  }

  /**
   * Set the value of the denominator
   * 
   * @param double denominator
   */
  public void setDenominator(double denominator) {
    if (! isSetNumber()) {
      setNumber(new ValuePair<Double,Double>());
    }
    Double old = this.number.getV();
    this.number.setV(denominator);
    firePropertyChange(TreeNodeChangeEvent.denominator, old, this.number.getV());
  }

  /**
   * Set the value of the numerator
   * 
   * @param double numerator
   */
  public void setNumerator(double numerator) {
    if (! isSetNumber()) {
      setNumber(new ValuePair<Double,Double>());
    }
    Double old = this.number.getL();
    this.number.setL(numerator);
    firePropertyChange(TreeNodeChangeEvent.numerator, old, this.number.getL());
  }

}
