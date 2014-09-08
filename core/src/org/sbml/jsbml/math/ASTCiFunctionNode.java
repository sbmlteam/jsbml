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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Variable;
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
   * The name of the MathML element represented by this
   * {@link ASTCiFunctionNode}.
   */
  private String name;

  /**
   * refId attribute for MathML element
   */
  private String refId;

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
    if (node.isSetName()) {
      setName(node.getName());      
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
    // TODO: Do the checking in the compiler
    CallableSBase variable = getReferenceInstance();
    if (variable instanceof FunctionDefinition) {
      value = compiler.function((FunctionDefinition) variable,
        getChildren());
    } else {
      logger
      .warn("ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
          + getRefId()
          + ", "
          + getParentSBMLObject().getElementName()
          + ")");
      throw new SBMLException(
        "ASTNode of type FUNCTION but the variable is not a FunctionDefinition! ("
            + getName() + ", " + getParentSBMLObject().getElementName()
            + ")");
    }
    return processValue(value);
  }
    
  /**
   * Returns {@code true} or {@code false} depending on whether this
   * {@link ASTCiFunctionNode} refers to elements such as parameters or 
   * numbers with undeclared units.
   * 
   * A return value of {@code true} indicates that the {@code UnitDefinition}
   * returned by {@link Variable#getDerivedUnitDefinition()} may not accurately
   * represent the units of the expression.
   * 
   * @return {@code true} if the math expression of this {@link ASTCiFunctionNode}
   *         includes parameters/numbers with undeclared units,
   *         {@code false} otherwise.
   */
  public boolean containsUndeclaredUnits() {
    // TODO: Needs to be verified
    if (isLeaf()) {
        if ((type == Type.NAME_TIME) && (isSetParentSBMLObject())) {
          Model model = getParentSBMLObject().getModel();
          if ((model != null) && model.isSetTimeUnits()) {
            return false;
          }
        }
        return true;
    } else {
      for (ASTNode2 child : getListOfNodes()) {
        if (child instanceof ASTCiFunctionNode 
            && ((ASTCiFunctionNode)child).containsUndeclaredUnits()) {
          return true;
        }
      }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#equals(java.lang.Object)
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
   * Goes through the formula and identifies all global parameters that are
   * referenced by this rate equation.
   * 
   * @return all global parameters that are referenced by this rate equation.
   */
  public List<Parameter> findReferencedGlobalParameters() {
    // TODO: Still need to recurse on (any) children
    ArrayList<Parameter> pList = new ArrayList<Parameter>();
    CallableSBase reference = getReferenceInstance();
    if (reference != null && reference instanceof Parameter) {
      Model model = reference.getModel();
      Parameter parameter = null;
      if (model != null) {
        parameter = model.getParameter(reference.getId());
      }
      if (model != null && parameter != null) {
        pList.add((Parameter) reference);
      }
    }
    return pList;
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
   * Returns the variable ({@link CallableSBase}) of this {@link ASTCiFunctionNode}. 
   * 
   * @return the variable of this node
   */
  public CallableSBase getReferenceInstance() {
    CallableSBase sbase = null;
    if (isSetParent()) {
      TreeNode parent = getParent();
      if (parent instanceof ASTLambdaFunctionNode) {
        ASTLambdaFunctionNode lambda = (ASTLambdaFunctionNode) parent;
        if (lambda.getChildAt(getChildCount() - 1) != this) { 
          logger.debug(MessageFormat.format(
            "The name \"{0}\" represented by this node is an " +
                "argument in a function call, i.e., a placeholder " +
                "for some other element. No corresponding CallableSBase " +
                "exists in the model",
                getName()));
          return sbase;
        }
      }
    }
    if (isSetParentSBMLObject()) {
      if (getParentSBMLObject() instanceof KineticLaw) {
        sbase = ((KineticLaw) getParentSBMLObject()).getLocalParameter(getName());
      }
      if (sbase == null) {
        Model m = getParentSBMLObject().getModel();
        if (m != null) {
          sbase = m.findCallableSBase(getName());
          if (sbase instanceof LocalParameter) {
            sbase = null;
          } else if (sbase == null) {
            logger.debug(MessageFormat.format(
              "Cannot find any element with id \"{0}\" in the model.",
              getName()));
          }
        } else {
          logger.debug(MessageFormat.format(
            "This ASTCiNumberNode is not yet linked to a model and " +
                "can therefore not determine its variable \"{0}\".",
                getName()));
        }
      }
    }
    return sbase;
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
  private boolean isSetRefId() {
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
   * this {@link ASTCSymbolCiFunctionNode}
   * 
   * @param String definitionURL
   */
  public void setDefinitionURL(String definitionURL) {
    String old = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, old, this.definitionURL);
  }

  /**
   * Set an instance of {@link CallableSBase} as the variable of this 
   * {@link ASTCiFunctionNode}. Note that if the given variable does not
   * have a declared {@code id} field, the pointer to this variable will 
   * get lost when cloning this node. Only references to identifiers are
   * permanently stored. The pointer can also not be written to an SBML 
   * file without a valid identifier.
   * 
   * @param callableSBase a pointer to a {@link CallableSBase}.
   */
  public void setReference(CallableSBase sbase) {
    setName(sbase.getId());
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
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCiFunctionNode [definitionURL=");
    builder.append(definitionURL);
    builder.append(", name=");
    builder.append(name);
    builder.append(", refId=");
    builder.append(refId);
    builder.append(", listOfNodes=");
    builder.append(listOfNodes);
    builder.append(", parentSBMLObject=");
    builder.append(parentSBMLObject);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append(", id=");
    builder.append(id);
    builder.append(", style=");
    builder.append(style);
    builder.append(", listOfListeners=");
    builder.append(listOfListeners);
    builder.append(", parent=");
    builder.append(parent);
    builder.append("]");
    return builder.toString();
  }


}
