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

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
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
public class ASTCiNumberNode extends ASTNumber implements
ASTCSymbolBaseNode {

  /**
   * 
   */
  private static final long serialVersionUID = -6842905005458975038L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCiNumberNode.class);

  /**
   * definitionURL attribute for MathML element
   */
  protected String definitionURL;

  /**
   * name attribute for MathML element
   */
  private String name;
  
  /**
   * A direct pointer to a referenced variable. This can save a lot of
   * computation time because it will then not be necessary to query the
   * corresponding model again and again for this variable.
   */
  private CallableSBase variable;

  /**
   * Creates a new {@link ASTCiNumberNode}.
   */
  public ASTCiNumberNode() {
    super();
    setDefinitionURL(null);
    setName(null);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCiNumberNode}.
   * 
   * @param node
   *            the {@link ASTCiNumberNode} to be copied.
   */
  public ASTCiNumberNode(ASTCiNumberNode node) {
    super(node);
    setDefinitionURL(node.getDefinitionURL());
    setName(node.getName());
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNumber#clone()
   */
  @Override
  public ASTCiNumberNode clone() {
    return new ASTCiNumberNode(this);
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
    ASTCiNumberNode other = (ASTCiNumberNode) obj;
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
   * Returns the variable ({@link CallableSBase}) of this {@link ASTCiNumberNode}. 
   * 
   * @return the variable of this node
   */
  public CallableSBase getCallableSBase() {
    if (variable == null) {
      /*
       * Possibly the name is just from the argument list
       * of some function definition. Hence, it won't be
       * possible to determine an element with the same
       * identifier within the model. In this case, this
       * warning is kind of senseless.
       */
      TreeNode parent = getParent();
      if ((parent != null) && (parent instanceof ASTBinaryFunctionNode)) {
        ASTBinaryFunctionNode parentNode = (ASTBinaryFunctionNode) parent;
        if ((parentNode.getType() == Type.LAMBDA) && (parentNode.getRightChild() != this)) {
          /*
           * The second condition is important, because the argument list
           * comprises only the first n children. Child n + 1 is the
           * expression for the function.
           */
          logger.debug(MessageFormat.format(
            "The name \"{0}\" represented by this node is an argument in a function call, i.e., a placeholder for some other element. No corresponding CallableSBase exists in the model",
            getName()));
          return variable;
        }
      }
      if (getParentSBMLObject() != null) {
        if (getParentSBMLObject() instanceof KineticLaw) {
          variable = ((KineticLaw) getParentSBMLObject()).getLocalParameter(getName());
        }
        if (variable == null) {
          Model m = getParentSBMLObject().getModel();
          if (m != null) {
            variable = m.findCallableSBase(getName());
            if (variable instanceof LocalParameter) {
              // in this case the parameter originates from a
              // different kinetic law.
              variable = null;
            } else if (variable == null) {
              // Could be any L3 package elements
              // that is not a CallableSBase
              // TODO: Actually, if something can be addressed in an ASTNode,
              // it MUST implement CallableSBase, no matter in which extension
              // package.
              // variable = m.findNamedSBase(getName());
              logger.debug(MessageFormat.format(
                "Cannot find any element with id \"{0}\" in the model.",
                getName()));
            }
          } else {
            logger.debug(MessageFormat.format(
              "This ASTNode is not yet linked to a model and can therefore not determine its variable \"{0}\".",
              getName()));
          }
        }
      }
    }
    return variable;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#getDefinitionURL()
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
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#getName()
   */
  @Override
  public String getName() {
    if (isSetName()) {
      return name;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("name", this);
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
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#isSetName()
   */
  @Override
  public boolean isSetName() {
    return name != null;
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
   * Set an instance of {@link CallableSBase} as the variable of this 
   * {@link ASTCiNumberNode}. Note that if the given variable does not
   * have a declared {@code id} field, the pointer to this variable will 
   * get lost when cloning this node. Only references to identifiers are
   * permanently stored. The pointer can also not be written to an SBML 
   * file without a valid identifier.
   * 
   * @param variable a pointer to a {@link CallableSBase}.
   */
  public void setCallableSBase(CallableSBase sbase) {
    CallableSBase oldValue = this.variable;
    if ((sbase != null) && sbase.isSetId()) {
      name = sbase.getId();
    }
    this.variable = sbase;
    firePropertyChange(TreeNodeChangeEvent.variable, oldValue, sbase);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#setDefinitionURL(java.lang.String)
   */
  @Override
  public void setDefinitionURL(String definitionURL) {
    String old = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, old, definitionURL);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#setName(java.lang.String)
   */
  @Override
  public void setName(String name) {
    String old = this.name;
    this.name = name;
    firePropertyChange(TreeNodeChangeEvent.name, old, this.name);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCiNumberNode [definitionURL=");
    builder.append(definitionURL);
    builder.append(", name=");
    builder.append(name);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
