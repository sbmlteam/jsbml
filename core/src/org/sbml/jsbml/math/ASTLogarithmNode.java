/*
 * $Id$
 * $URL$
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
 * An Abstract Syntax Tree (AST) node representing the logarithm function
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTLogarithmNode extends ASTBinaryFunctionNode {

  /**
   * 
   */
  private static final long serialVersionUID = 5350043898749220594L;

  /**
   * Creates a new {@link ASTLogarithmNode}.
   */
  public ASTLogarithmNode() {
    super();
  }
  
  /**
   * Creates a new {@link ASTLogarithmNode} with base 10
   * and value {@link ASTNode2}
   * 
   * @param value {@link ASTNode2} - must not be {@code null}
   */
  public ASTLogarithmNode(ASTNode2 value) {
    super();
    setLeftChild(value);
  }
  
  /**
   * Creates a new {@link ASTLogarithmNode} with base {@link ASTNode2}
   * and value {@link ASTNode2}
   * 
   * @param base {@link ASTNode2} - can be {@code null}; then a base of 10 will
   * be assumed.
   * @param value {@link ASTNode2} - must not be {@code null}
   */
  public ASTLogarithmNode(ASTNode2 base, ASTNode2 value) {
    super();
    if (base == null) {
      setLeftChild(value);
    } else {
      setLeftChild(base);
      setRightChild(value);
    }
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTLogarithmNode}.
   * 
   * @param node
   *            the {@link ASTLogarithmNode} to be copied.
   */
  public ASTLogarithmNode(ASTLogarithmNode node) {
    super(node);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTLogarithmNode clone() {
    return new ASTLogarithmNode(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTLogarithmNode [strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
