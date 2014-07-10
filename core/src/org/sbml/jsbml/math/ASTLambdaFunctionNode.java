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

import org.apache.log4j.Logger;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * An Abstract Syntax Tree (AST) node representing a lambda function
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTLambdaFunctionNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = 3189146748998908918L;


  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTLambdaFunctionNode.class);


  /**
   * The number of MathML bvar elements inside this
   * lambda function
   */
  private Integer numBvars;

  /**
   * Creates a new {@link ASTLambdaFunctionNode}.
   */
  public ASTLambdaFunctionNode() {
    super();
    numBvars = null;
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTLambdaFunctionNode}.
   * 
   * @param node
   *            the {@link ASTLambdaFunctionNode} to be copied.
   */
  public ASTLambdaFunctionNode(ASTLambdaFunctionNode node) {
    super(node);
    setNumBvars(node.getNumBvars());
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTLambdaFunctionNode clone() {
    return new ASTLambdaFunctionNode(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ASTLambdaFunctionNode other = (ASTLambdaFunctionNode) obj;
    if (numBvars == null) {
      if (other.numBvars != null)
        return false;
    } else if (!numBvars.equals(other.numBvars))
      return false;
    return true;
  }

  /**
   * Get the number of bvar elements
   * 
   * @return Integer numBvars
   */
  public int getNumBvars() {
    if (isSetNumBVars()) {
      return numBvars;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("lambda", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1213;
    int result = super.hashCode();
    result = prime * result + ((numBvars == null) ? 0 : numBvars.hashCode());
    return result;
  }

  /**
   * Returns True iff numBvars is set
   * 
   * @return boolean
   */
  private boolean isSetNumBVars() {
    return numBvars != null;
  }

  /**
   * Set the number of bvar elements
   * 
   * @param Integer numBvars
   */
  protected void setNumBvars(int numBvars) {
    Integer old = this.numBvars;
    this.numBvars = numBvars;
    firePropertyChange(TreeNodeChangeEvent.numBvars, old, this.numBvars);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTLambdaFunctionNode [numBvars=");
    builder.append(numBvars);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
