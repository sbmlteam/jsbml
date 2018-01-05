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
package org.sbml.jsbml.math;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * An Abstract Syntax Tree (AST) node representing the delay function
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCSymbolDelayNode extends ASTBinaryFunctionNode implements
ASTCSymbolNode {

  /**
   * 
   */
  private static final long serialVersionUID = -5565842362242854303L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCSymbolDelayNode.class);

  /**
   * name attribute for MathML element
   */
  protected String name;

  /**
   * definitionURL attribute for MathML element
   */
  protected String definitionURL;

  /**
   * encoding attribute for MathML element
   */
  private String encoding;

  /**
   * Creates a new {@link ASTCSymbolDelayNode}.
   */
  public ASTCSymbolDelayNode() {
    super();
    setName(null);
    setDefinitionURL(ASTNode.URI_DELAY_DEFINITION);
    setEncoding("text");
    setType(Type.FUNCTION_DELAY);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCSymbolDelayNode}.
   * 
   * @param node the {@link ASTCSymbolDelayNode} to be copied.
   */
  public ASTCSymbolDelayNode(ASTCSymbolDelayNode node) {
    super(node);
    if (node.isSetDefinitionURL()) {
      setDefinitionURL(node.getDefinitionURL());
    }
    if (node.isSetEncoding()) {
      setEncoding(node.getEncoding());
    }
    if (node.isSetName()) {
      setName(node.getName());
    }
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTCSymbolDelayNode clone() {
    return new ASTCSymbolDelayNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    value = isSetName() ? compiler.delay(getName(), getLeftChild(), getRightChild())
      : compiler.delay("delay", getLeftChild(), getRightChild());
    return processValue(value);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ASTCSymbolDelayNode other = (ASTCSymbolDelayNode) obj;
    if (definitionURL == null) {
      if (other.definitionURL != null) {
        return false;
      }
    } else if (!definitionURL.equals(other.definitionURL)) {
      return false;
    }
    if (encoding == null) {
      if (other.encoding != null) {
        return false;
      }
    } else if (!encoding.equals(other.encoding)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolNode#getDefinitionURL()
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
   * @see org.sbml.jsbml.math.ASTCSymbolNode#getEncodingURL()
   */
  @Override
  public String getEncoding() {
    if (isSetEncoding()) {
      return encoding;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("encodingURL", this);
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
    final int prime = 1291;
    int result = super.hashCode();
    result = prime * result
        + ((definitionURL == null) ? 0 : definitionURL.hashCode());
    result = prime * result
        + ((encoding == null) ? 0 : encoding.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.FUNCTION_DELAY;
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
   * @see org.sbml.jsbml.math.ASTCSymbolNode#isSetEncodingURL()
   */
  @Override
  public boolean isSetEncoding() {
    return encoding != null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#refersTo(java.lang.String)
   */
  @Override
  public boolean refersTo(String id) {
    return getName().equals(id);
  }

  /**
   * Set the definitionURL of the MathML element represented by
   * this {@link ASTCSymbolDelayNode}
   * 
   * @param definitionURL
   */
  private void setDefinitionURL(String definitionURL) {
    String old = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, old, encoding);
  }

  /**
   * Set the encoding of the MathML element represented by
   * this {@link ASTCSymbolDelayNode}
   * 
   * @param encoding
   */
  private void setEncoding(String encoding) {
    String old = this.encoding;
    this.encoding = encoding;
    firePropertyChange(TreeNodeChangeEvent.encoding, old, this.encoding);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toFormula()
   */
  @Override
  public String toFormula() throws SBMLException {
    return compile(new FormulaCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toLaTeX()
   */
  @Override
  public String toLaTeX() throws SBMLException {
    return compile(new LaTeXCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toMathML()
   */
  @Override
  public String toMathML() {
    try {
      return MathMLXMLStreamCompiler.toMathML(this);
    } catch (RuntimeException e) {
      logger.error("Unable to create MathML");
      return null;
    }
  }

}
