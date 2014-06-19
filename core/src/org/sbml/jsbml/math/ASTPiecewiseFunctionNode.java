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
 * An Abstract Syntax Tree (AST) node representing a MathML piecewise
 * element in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTPiecewiseFunctionNode extends ASTFunction {

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTPiecewiseFunctionNode.class);

  /**
   * The number of piece elements associated with this MathML
   * piecewise element
   */
  private Integer numPiece;

  /**
   * True iff piecewise element contains an otherwise element
   */
  private boolean hasOtherwise;

  /**
   * Creates a new {@link ASTPiecewiseFunctionNode}.
   */
  public ASTPiecewiseFunctionNode() {
    super();
    hasOtherwise = false;
    numPiece = null;
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTPiecewiseFunctionNode}.
   * 
   * @param node
   *            the {@link ASTPiecewiseFunctionNode} to be copied.
   */
  public ASTPiecewiseFunctionNode(ASTPiecewiseFunctionNode node) {
    super(node);
    hasOtherwise = node.hasOtherwise();
    setNumPiece(node.getNumPiece());
  }

  /**
   * Get the number of piece elements in this {@link ASTPiecewiseFunctionNode}
   * 
   * @return Integer numPiece
   */
  public int getNumPiece() {
    if (isSetNumpiece()) {
      return numPiece;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("piecewise", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /**
   * Returns True iff piecewise element contains an otherwise element
   * 
   * @return boolean hasOtherwise
   */
  public boolean hasOtherwise() {
    return hasOtherwise;
  }

  /**
   * Returns true iff numPiece has been set
   * 
   * @return boolean
   */
  private boolean isSetNumpiece() {
    return numPiece != null;
  }

  /**
   * Set the number of piece elements in this {@link ASTPiecewiseFunctionNode}
   * 
   * @param Integer numPiece
   */
  protected void setNumPiece(int numPiece) {
    Integer old = this.numPiece;
    this.numPiece = numPiece;
    firePropertyChange(TreeNodeChangeEvent.numPiece, old, this.numPiece);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTPiecewiseFunctionNode [numPiece=");
    builder.append(numPiece);
    builder.append(", hasOtherwise=");
    builder.append(hasOtherwise);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
