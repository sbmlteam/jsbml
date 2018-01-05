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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;


/**
 * A node in the Abstract Syntax Tree (AST) representation of a mathematical
 * expression.
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public interface ASTNode2 extends TreeNodeWithChangeSupport {

  /**
   * Clone ASTNode2
   * 
   * @return ASTNode2 node
   */
  public ASTNode2 clone();

  /**
   * Compiles this {@link ASTNode2} and returns the result.
   * 
   * @param compiler
   *            An instance of an {@link ASTNode2Compiler} that provides
   *            methods to translate this {@link ASTNode2} into something
   *            different.
   * @return Some value wrapped in an {@link ASTNode2Value}. The content of the
   *         wrapper depends on the {@link ASTNode2Compiler} used to create it.
   *         However, this {@link ASTNode2} will ensure that level and version
   *         are set appropriately according to this node's parent SBML
   *         object.
   * @throws SBMLException
   *             Thrown if an error occurs during the compilation process.
   * 
   */
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler);

  /**
   * Get the id of this node
   * 
   * @return the id
   */
  public String getId();

  /**
   * Returns the class of the MathML element represented by
   * this {@link ASTNode2}
   * 
   * @return String name
   */
  public String getMathMLClass();

  /**
   * Return the parentSBMLObject of this {@link ASTNode2}
   * 
   * @return MathContainer container
   */
  public MathContainer getParentSBMLObject();

  /**
   * Return the style of this node
   * 
   * @return the style
   */
  public String getStyle();

  /**
   * Returns the type of this ASTNode2.
   * 
   * @return Type type
   */
  public Type getType();

  /**
   * Returns {@code true} iff type is allowed in this {@link ASTNode2}
   * @param type
   * @return
   */
  public boolean isAllowableType(Type type);

  /**
   * Returns true iff id has been set
   * 
   * @return boolean
   */
  public boolean isSetId();

  /**
   * Returns true iff mathMLClass has been set
   * 
   * @return boolean
   */
  public boolean isSetMathMLClass();

  /**
   * Checks if a parent SBML object, i.e., a {@link MathContainer}, is set as a
   * parent SBML object for this {@link ASTNode2}.
   * 
   * @return boolean
   */
  public boolean isSetParentSBMLObject();

  /**
   * Returns true iff style has been set
   * 
   * @return boolean
   */
  public boolean isSetStyle();

  /**
   * Returns true iff type has been set
   * 
   * @return boolean
   */
  public boolean isSetType();

  /**
   * Specifies strictness. When true, ASTUnaryFunction and ASTBinaryFunction
   * nodes can only contain the specified # of children. When false, there is
   * a bit of leeway (i.e. ASTUnaryFunction can contain more than one child)
   * (not recommended).
   * 
   * @return boolean
   */
  public boolean isStrict();

  /**
   * Set the id of this node
   * 
   * @param id the id to set
   */
  public void setId(String id);

  /**
   * Set the class of the MathML element represented by
   * this {@link ASTNode2}
   * @param mathMLClass
   */
  public void setMathMLClass(String mathMLClass);

  /**
   * @param astNode2
   *            the parent to set
   */
  public void setParent(TreeNode astNode2);

  /**
   * Sets the parentSBMLObject to container recursively
   * 
   * @param container
   */
  public void setParentSBMLObject(MathContainer container);

  /**
   * Set the style of this node
   * 
   * @param style the style to set
   */
  public void setStyle(String style);

  /**
   * Sets the type from a String. This method accepts all the supported mathML
   * elements, the possible types of cn elements or the possible definitionURL
   * of csymbol elements.
   * 
   * @param typeStr
   *            the type as a String.
   */
  public void setType(String typeStr);

  /**
   * Set the type of the MathML element represented by this {@link ASTNode2}
   * 
   * @param type
   */
  public void setType(Type type);

  /**
   * <p>
   * Converts this {@link ASTNode2} to a text string using a specific syntax
   * for mathematical formulas.
   * </p>
   * <p>
   * The text-string form of mathematical formulas produced by
   * formulaToString() and read by parseFormula() are simple C-inspired infix
   * notation taken from SBML Level 1. A formula in this text-string form
   * therefore can be handed to a program that understands SBML Level 1
   * mathematical expressions, or used as part of a formula translation
   * system. The syntax is described in detail in the documentation for
   * ASTNode.
   * </p>
   * 
   * @return the formula from the given AST as an SBML Level 1 text-string
   *         mathematical formula. The caller owns the returned string and is
   *         responsible for freeing it when it is no longer needed. {@code null} is
   *         returned if the given argument is {@code null}.
   * @throws SBMLException
   *             if there is a problem in the {@link ASTNode2} tree.
   */
  public String toFormula();

  /**
   * Converts this node recursively into a LaTeX formatted String.
   * 
   * @return A String representing the LaTeX code necessary to write the
   *         formula corresponding to this node in a document.
   * @throws SBMLException
   *             if there is a problem in the AbstractASTNode tree.
   */
  public String toLaTeX();

  /**
   * Converts this node recursively into a MathML string that corresponds to
   * the subset of MathML defined in the SBML specification.
   * 
   * @return the representation of this node in MathML.
   */
  public String toMathML();

  @Override
  public String toString();

  /**
   * Unsets the parentSBMLObject to {@code null} recursively.
   */
  public void unsetParentSBMLObject();

  /**
   * Set the strictness of this node
   * 
   * @param strict
   */
  public abstract void setStrictness(boolean strict);

}
