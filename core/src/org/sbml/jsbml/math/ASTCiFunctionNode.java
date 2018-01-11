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

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * An Abstract Syntax Tree (AST) node representing a MathML ci element
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @since 1.0
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
   * The name of the MathML element represented by this
   * {@link ASTCiFunctionNode}.
   */
  private String name; // TODO - remove this unused class attribute ?

  /**
   * refId attribute for MathML element
   */
  private String refId; // TODO - should we use the setName method for that to ensure to have both set/get. Or we overwrite the setName/getName to return
  // the refId

  /**
   * Creates a new {@link ASTCiFunctionNode}.
   */
  public ASTCiFunctionNode() {
    super();
    setDefinitionURL(null);
    setName(null);
    setType(Type.FUNCTION);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCiFunctionNode}.
   * 
   * @param node
   *            the {@link ASTCiFunctionNode} to be copied.
   */
  public ASTCiFunctionNode(ASTCiFunctionNode node) {
    super(node);
    if (node.isSetDefinitionURL()) {
      setDefinitionURL(node.getDefinitionURL());
    }
    if (node.isSetRefId()) {
      setRefId(node.getRefId());
    }
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTCiFunctionNode clone() {
    return new ASTCiFunctionNode(this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#compile(org.sbml.jsbml.math.compiler.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    FunctionDefinition variable = getReferenceInstance();
    if (variable instanceof FunctionDefinition) {
      value = compiler.function(variable,
        getChildren());
    } else {
      logger
      .warn("ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
          + (isSetRefId() ? getRefId() : "null")
          + ", "
          + (isSetParentSBMLObject() ? getParentSBMLObject().getElementName() : null)
          + ")");
      throw new SBMLException(
        "ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
            + (isSetName() ? getName() : "null") + ", "
            + (isSetParentSBMLObject() ? getParentSBMLObject().getElementName() : "null")
            + ")");
    }
    return processValue(value);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#equals(java.lang.Object)
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
    ASTCiFunctionNode other = (ASTCiFunctionNode) obj;
    if (definitionURL == null) {
      if (other.definitionURL != null) {
        return false;
      }
    } else if (!definitionURL.equals(other.definitionURL)) {
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

  /**
   * Returns the variable ({@link FunctionDefinition}) of this {@link ASTCiFunctionNode}.
   * 
   * @return the variable of this node
   */
  public FunctionDefinition getReferenceInstance() {
    FunctionDefinition function = null;
    Model m = isSetParentSBMLObject() ? getParentSBMLObject().getModel() : null;
    if (m != null) {
      function = isSetRefId() ? m.getFunctionDefinition(getRefId()) : null;
      if (function == null) {
        logger.debug(MessageFormat.format(
          "Cannot find any element with id \"{0}\" in the model.",
          isSetRefId() ? getRefId() : null));
      }
    } else {
      logger.debug(MessageFormat.format(
        "This ASTCiFunctionNode is not yet linked to a model. "
            + "No element with id \"{0}\" could be found.", isSetRefId() ? getRefId() : null));
    }
    return function;
  }

  /**
   * Get refId attribute
   * 
   * @return the refId
   */
  public String getRefId() {
    if (isSetRefId()) {
      return refId;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("refId", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1129;
    int result = super.hashCode();
    result = prime * result
        + ((definitionURL == null) ? 0 : definitionURL.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.FUNCTION;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#isSetDefinitionURL()
   */
  @Override
  public boolean isSetDefinitionURL() {
    return definitionURL != null;
  }

  /**
   * Return true iff refId is set
   * 
   * @return boolean
   */
  public boolean isSetRefId() {
    return refId != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#isSetType()
   */
  @Override
  public boolean isSetType() {
    return type == Type.FUNCTION;
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

  /**
   * Set the definitionURL of the MathML element represented by
   * this {@link ASTCiFunctionNode}
   * 
   * @param definitionURL
   */
  public void setDefinitionURL(String definitionURL) {
    String old = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, old, this.definitionURL);
  }

  /**
   * Set an instance of {@link FunctionDefinition} as the variable of this
   * {@link ASTCiFunctionNode}. Note that if the given variable does not
   * have a declared {@code id} field, the pointer to this variable will
   * get lost when cloning this node. Only references to identifiers are
   * permanently stored. The pointer can also not be written to an SBML
   * file without a valid identifier.
   * 
   * @param function a pointer to a {@link FunctionDefinition}.
   */
  public void setReference(FunctionDefinition function) {
    setRefId(function.getId());
  }

  /**
   * Set refId attribute
   * 
   * @param refId the refId to set
   */
  public void setRefId(String refId) {
    String old = this.refId;
    this.refId = refId;
    firePropertyChange(TreeNodeChangeEvent.refId, old, refId);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toFormula()
   */
  @Override
  public String toFormula() throws SBMLException {
    if (isSetRefId()) {
      return compile(new FormulaCompiler()).toString();
    }
    throw new SBMLException();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toLaTeX()
   */
  @Override
  public String toLaTeX() throws SBMLException {
    if (isSetRefId()) {
      return compile(new LaTeXCompiler()).toString();
    }
    throw new SBMLException();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toMathML()
   */
  @Override
  public String toMathML() {
    try {
      if (isSetRefId()) {
        return MathMLXMLStreamCompiler.toMathML(this);
      }
      throw new SBMLException();
    } catch (RuntimeException e) {
      logger.error("Unable to create MathML");
      return null;
    }
  }

}
