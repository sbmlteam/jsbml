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
 * An Abstract Syntax Tree (AST) node representing a MathML ci element
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCiFunctionNode extends ASTFunction implements
ASTCSymbolBaseNode {

  /**
   * 
   */
  private static final long serialVersionUID = -3672567229521583280L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCiFunctionNode.class);

  /**
   * definitionURL attribute for MathML element
   */
  private String definitionURL;

  /**
   * Creates a new {@link ASTCiFunctionNode}.
   */
  public ASTCiFunctionNode() {
    super();
    setDefinitionURL(null);
    setName(null);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCiFunctionNode}.
   * 
   * @param node
   *            the {@link ASTCiFunctionNode} to be copied.
   */
  public ASTCiFunctionNode(ASTCiFunctionNode node) {
    super(node);
    setDefinitionURL(node.getDefinitionURL());
    setName(node.getName());
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTCiFunctionNode clone() {
    return new ASTCiFunctionNode(this);
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
    ASTCiFunctionNode other = (ASTCiFunctionNode) obj;
    if (definitionURL == null) {
      if (other.definitionURL != null)
        return false;
    } else if (!definitionURL.equals(other.definitionURL))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  /**
   * Returns the definitionURL of the MathML element represented by this ASTCiFunctionNode
   * 
   * @return String definitionURL
   */
  @Override
  public String getDefinitionURL() {
    if (isSetDefinitionURL()) {
      return definitionURL;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("definitionURL", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result
      + ((definitionURL == null) ? 0 : definitionURL.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#isSetDefinitionURL()
   */
  @Override
  public boolean isSetDefinitionURL() {
    return definitionURL != null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#refersTo(java.lang.String)
   */
  @Override
  public boolean refersTo(String id) {
    if (getName().equals(id)) {
      return true;
    }
    boolean childContains = false;
    for (ASTNode2 child : listOfNodes) {
      if (child instanceof ASTCSymbolBaseNode) {
        childContains |= ((ASTCSymbolBaseNode) child).refersTo(id);
      }
    }
    return childContains;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#setDefinitionURL(java.lang.String)
   */
  @Override
  public void setDefinitionURL(String definitionURL) {
    String old = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, old, this.definitionURL);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCiFunctionNode [definitionURL=");
    builder.append(definitionURL);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
