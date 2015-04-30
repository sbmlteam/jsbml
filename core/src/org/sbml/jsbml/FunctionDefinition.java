/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.util.IdManager;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.validator.SyntaxChecker;

/**
 * Represents the functionDefinition XML element of a SBML file. Since
 * {@link FunctionDefinition}s were introduced to SBML in Level 2, this
 * class must not be used for models in Level 1.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * @version $Rev$
 */
public class FunctionDefinition extends AbstractMathContainer implements
CallableSBase, UniqueNamedSBase {

  // TODO - for L3V2, we probably will need to have AbstractMathContainer extending AbstractNamedSBase ??

  /**
   * Error message to indicate that an incorrect {@link ASTNode.Type} has been passed
   * to a method.
   */
  private static final String ILLEGAL_ASTNODE_TYPE_MSG = "Math element is expected to be of type {0}, but given is {1}.";
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5103621145642898899L;
  /**
   * Represents the "id" attribute of a functionDefinition element.
   */
  private String id;
  /**
   * Represents the "name" attribute of a functionDefinition element.
   */
  private String name;

  /**
   * Creates a FunctionDefinition instance. By default, id and name are {@code null}.
   */
  public FunctionDefinition() {
    super();
    id = null;
    name = null;
  }

  /**
   * Creates a FunctionDefinition instance from a given FunctionDefinition.
   * 
   * @param sb
   */
  public FunctionDefinition(FunctionDefinition sb) {
    super(sb);
    if (sb.isSetId()) {
      id = new String(sb.getId());
    } else {
      id = null;
    }
    if (sb.isSetName()) {
      name = new String(sb.getName());
    } else {
      name = null;
    }
  }

  /**
   * Creates a FunctionDefinition instance from a level and version. By
   * default, name is {@code null}.
   * 
   * @param level
   * @param version
   */
  public FunctionDefinition(int level, int version) {
    super(level, version);
    if (getLevel() < 2) {
      throw new IllegalArgumentException(MessageFormat.format(
        "Cannot create a {0} with Level = {1,number,integer}.", getElementName(),
        getLevel()));
    }
  }

  /**
   * Creates a FunctionDefinition instance from an id, ASTNode, level and
   * version. By default, name is {@code null}. If the ASTNode is not of type lambda,
   * an IllegalArgumentException is thrown.
   * 
   * @param id
   * @param lambda
   * @param level
   * @param version
   */
  public FunctionDefinition(String id, ASTNode lambda, int level, int version) {
    super(lambda, level, version);
    if (!lambda.isLambda()) {
      throw new IllegalArgumentException(MessageFormat.format(
        ILLEGAL_ASTNODE_TYPE_MSG,
        ASTNode.Type.LAMBDA, lambda.getType()));
    }
    if (id != null) {
      this.id = new String(id);
    } else {
      this.id = null;
    }
    name = null;
  }

  /**
   * Creates a FunctionDefinition instance from an id, level and version.
   * 
   * @param id
   * @param level
   * @param version
   */
  public FunctionDefinition(String id, int level, int version) {
    super(level, version);
    if (id != null) {
      this.id = new String(id);
    } else {
      this.id = null;
    }
    name = null;
  }

  /**
   * Checks if the sID is a valid identifier.
   * 
   * @param sID
   *            the identifier to be checked. If null or an invalid
   *            identifier, an exception will be thrown.
   * @return {@code true} only if the sID is a valid identifier.
   *         Otherwise this method throws an {@link IllegalArgumentException}.
   *         This is an intended behavior.
   * @throws IllegalArgumentException
   *             if the given id is not valid in this model.
   */
  boolean checkIdentifier(String sID) {
    if ((sID == null)
        || !SyntaxChecker.isValidId(sID, getLevel(), getVersion())) {
      throw new IllegalArgumentException(MessageFormat.format(
        "\"{0}\" is not a valid identifier.", sID));
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public FunctionDefinition clone() {
    return new FunctionDefinition(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      FunctionDefinition fd = (FunctionDefinition) object;
      equals &= fd.isSetId() == isSetId();
      if (equals && isSetId()) {
        equals &= fd.getId().equals(getId());
      }
      equals &= fd.isSetName() == isSetName();
      if (equals && isSetName()) {
        equals &= fd.getName().equals(getName());
      }
    }
    return equals;
  }

  /**
   * Get the nth argument to this function.
   * 
   * Callers should first find out the number of arguments to the function by
   * calling {@link #getArgumentCount()}.
   * 
   * @param n
   *            an integer index for the argument sought.
   * @return the nth argument (bound variable) passed to this
   *         {@link FunctionDefinition}.
   */
  public ASTNode getArgument(int n) {
    if (getArgumentCount() < n) {
      throw new IndexOutOfBoundsException(String.format(
        "No such argument with index {0,number,integer}.", n));
    }
    return getMath().getChild(n);
  }

  /**
   * Get the argument named name to this {@link FunctionDefinition}.
   * 
   * @param name
   *            the exact name (case-sensitive) of the sought-after argument
   * @return the argument (bound variable) having the given name, or null if
   *         no such argument exists.
   */
  public ASTNode getArgument(String name) {
    ASTNode arg = null;
    for (int i = 0; i < getArgumentCount(); i++) {
      arg = getArgument(i);
      if (arg.getName().equals(name)) {
        return arg;
      }
    }
    return null;
  }

  /**
   * Get the mathematical expression that is the body of this
   * {@link FunctionDefinition} object.
   * 
   * @return the body of this {@link FunctionDefinition} as an Abstract Syntax
   *         Tree, or null if no body is defined.
   */
  public ASTNode getBody() {
    return isSetMath() ? getMath().getRightChild() : null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#getId()
   */
  @Override
  public String getId() {
    return isSetId() ? id : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#getName()
   */
  @Override
  public String getName() {
    return isSetName() ? name : "";
  }

  /**
   * Get the number of arguments (bound variables) taken by this
   * {@link FunctionDefinition}.
   * 
   * @return the number of arguments (bound variables) that must be passed to
   *         this {@link FunctionDefinition}.
   * @libsbml.deprecated use {@link #getArgumentCount()}
   */
  public int getNumArguments() {
    return getArgumentCount();
  }

  /**
   * Get the number of arguments (bound variables) taken by this
   * {@link FunctionDefinition}.
   * 
   * @return the number of arguments (bound variables) that must be passed to
   *         this {@link FunctionDefinition}.
   */
  public int getArgumentCount() {
    return isSetMath() && (getMath().getChildCount() > 1)
        ? getMath().getChildCount() - 1 : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<FunctionDefinition> getParent() {
    return (ListOf<FunctionDefinition>) super.getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 877;
    int hashCode = super.hashCode();
    if (isSetId()) {
      hashCode += prime * getId().hashCode();
    }
    if (isSetName()) {
      hashCode += prime * getName().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isSetId()
   */
  @Override
  public boolean isSetId() {
    return id != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isSetName()
   */
  @Override
  public boolean isSetName() {
    return name != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      if (attributeName.equals("id")) {
        setId(value);
        return true;
      } else if (attributeName.equals("name")) {
        setName(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    if (getLevel() < 2) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.id, this);
    }
    String property = TreeNodeChangeEvent.id;
    String oldId = this.id;

    IdManager idManager = getIdManager(this);
    if ((oldId != null) && (idManager != null)) {
      // Delete previous identifier only if defined.
      idManager.unregister(this); // TODO - do we need non recursive method on the IdManager interface ??
    }
    if ((id == null) || (id.trim().length() == 0)) {
      this.id = null;
    } else if (checkIdentifier(id)) {
      this.id = id;
    }
    if ((idManager != null) && !idManager.register(this)) {
      IdentifierException exc = new IdentifierException(this, this.id);
      this.id = oldId; // restore the previous setting!
      throw new IllegalArgumentException(exc);
    }
    firePropertyChange(property, oldId, this.id);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#setMath(org.sbml.jsbml.ASTNode)
   */
  @Override
  public void setMath(ASTNode math) {
    if (getLevel() < 2) {
      // throw new PropertyNotAvailableError(SBaseChangedEvent.id, this);
      // We can use internally ASTNode even if working on level 1 model !!
      throw new PropertyNotAvailableException("Level 1 not supported", this);
    }

    if (math.getType() != ASTNode.Type.LAMBDA) {
      throw new IllegalArgumentException(MessageFormat.format(
        ILLEGAL_ASTNODE_TYPE_MSG, ASTNode.Type.LAMBDA, math.getType()));
    }
    super.setMath(math);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#setName(java.lang.String)
   */
  @Override
  public void setName(String name) {
    if (getLevel() < 2) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.id, this);
    }
    String oldName = this.name;
    if ((name == null) || (name.length() == 0)) {
      this.name = null;
    } else {
      this.name = name;
    }
    firePropertyChange(TreeNodeChangeEvent.name, oldName, name);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#toString()
   */
  @Override
  public String toString() {
    if (isSetName() && getName().length() > 0) {
      return name;
    }
    if (isSetId()) {
      return id;
    }
    String name = getClass().getName();
    return name.substring(name.lastIndexOf('.') + 1);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#unsetId()
   */
  @Override
  public void unsetId() {
    setId(null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#unsetName()
   */
  @Override
  public void unsetName() {
    setName(null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId() && (getLevel() > 1)) {
      attributes.put("id", getId());
    }
    if (isSetName() && (getLevel() > 1)) {
      attributes.put("name", getName());
    }

    return attributes;
  }

}
