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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.compilers.ASTNode2Compiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * A node in the Abstract Syntax Tree (AST) representation of a mathematical
 * expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public interface ASTNode2 extends TreeNodeWithChangeSupport {

  /**
   * Clone ASTNode2
   * 
   * @return TreeNode node
   */
  public TreeNode clone();

  /**
   * Compiles this {@link ASTNode2} and returns the result.
   * 
   * @param compiler
   *            An instance of an {@link ASTNode2Compiler} that provides
   *            methods to translate this {@link ASTNode2} into something
   *            different.
   * @return Some value wrapped in an {@link ASTNodeValue}. The content of the
   *         wrapper depends on the {@link ASTNode2Compiler} used to create it.
   *         However, this {@link ASTNode2} will ensure that level and version
   *         are set appropriately according to this node's parent SBML
   *         object.
   * @throws SBMLException
   *             Thrown if an error occurs during the compilation process.
   * 
   */
  public ASTNodeValue compile(ASTNode2Compiler compiler);

  /**
   * Returns the type of this ASTNode2.
   * 
   * @return Type type
   */
  public Type getType();

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
   * @param parent
   *            the parent to set
   */
  public void setParent(TreeNode astNode2);

  /**
   * Sets the parentSBMLObject to container recursively
   * 
   * @param MathContainer container
   */
  public void setParentSBMLObject(MathContainer container);

  /**
   * <p>
   * Converts this AbstractASTNode to a text string using a specific syntax for
   * mathematical formulas.
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
   *             if there is a problem in the AbstractASTNode tree.
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

  /**
   * Unsets the parentSBMLObject to null recursively.
   * 
   * @param {@link ASTNode2} node
   */
  public void unsetParentSBMLObject();

}
