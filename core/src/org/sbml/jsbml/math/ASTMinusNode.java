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
 * An Abstract Syntax Tree (AST) node representing the minus operator
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jul 2, 2014
 */
public class ASTMinusNode extends ASTBinaryFunctionNode {
  
  /**
   * 
   */
  private static final long serialVersionUID = 652890680815928656L;

  /**
   * Creates a new {@link ASTMinusNode}.
   */
  public ASTMinusNode() {
    super();
  }
  
  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTMinusNode}.
   * 
   * @param node
   *            the {@link ASTMinusNode} to be copied.
   */
  public ASTMinusNode(ASTMinusNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTMinusNode} with two children
   * 
   * @param leftChild {@link ASTNode2}
   * 
   * @param rightChild {@link ASTNode2}
   * 
   */
  public ASTMinusNode(ASTNode2 leftChild, ASTNode2 rightChild) {
    super(leftChild, rightChild);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTMinusNode clone() {
    return new ASTMinusNode(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTMinusNode [type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }
  
}
