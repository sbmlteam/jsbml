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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * Base interface for all the SBML components which contain MathML nodes.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public interface MathContainer extends SBaseWithDerivedUnit {

  /**
   * Converts this {@link MathContainer}'s internal {@link ASTNode} to a
   * C-like {@link String} according to the SBML Level 1 specifications and
   * returns it.
   * 
   * @return the math {@link ASTNode} of this object as a String. It returns
   *         the empty String if the math {@link ASTNode} is not set.
   * @deprecated As this is part of SBML Level 1, it is strongly recommended
   *             not to work with the {@link String} representation of a
   *             formula, but to deal with a more flexible {@link ASTNode}.
   * @see #getMath()
   */
  @Deprecated
  public String getFormula();

  /**
   * If {@link #isSetMath()} returns true, this method returns the
   * {@link ASTNode} belonging to this {@link MathContainer}.
   * 
   * @return the math {@link ASTNode} of this object. It return null if the
   *         math {@link ASTNode} is not set.
   */
  public ASTNode getMath();

  /**
   * If {@link #isSetMath()} returns true, this method returns the
   * corresponding MathML {@link String}, otherwise an empty {@link String}
   * will be returned.
   * 
   * @return the MathML representation of this {@link MathContainer}'s math
   *         element.
   */
  public String getMathMLString();

  /**
   * Checks if an {@link ASTNode} has been set for this {@link MathContainer}.
   * 
   * @return {@code true} if the math {@link ASTNode} of this object is not {@code null}.
   */
  public boolean isSetMath();

  /**
   * Sets the mathematical expression of this {@link MathContainer} instance
   * to the given formula. This method parses the given {@link String} and
   * stores the result in an {@link ASTNode} object.
   * 
   * @param formula
   *            a C-like {@link String} according to the definition in the
   *            SBML Level 1 specifications.
   * @deprecated As this is part of SBML Level 1, it is strongly recommended
   *             not to work with the {@link String} representation of a
   *             formula, but to deal with a more flexible {@link ASTNode}.
   *             Please use {@link ASTNode#parseFormula(String)} to create an
   *             {@link ASTNode} object from your formula {@link String}.
   * @throws ParseException
   *             If the given formula is invalid or cannot be parsed properly.
   * @throws PropertyNotAvailableException
   *             When trying to set this attribute and the Level is set to a
   *             value other than 1, this will throw an error.
   */
  @Deprecated
  public void setFormula(String formula) throws ParseException;

  /**
   * Sets the math {@link ASTNode} of this {@link MathContainer} to the given
   * value.
   * 
   * @param math
   *            an abstract syntax tree.
   */
  public void setMath(ASTNode math);

  /**
   * @deprecated use {@link #unsetMath()}
   */
  @Deprecated
  public void unsetFormula();

  /**
   * Sets the current {@link ASTNode} math element of this
   * {@link MathContainer} to {@code null} and notifies all
   * {@link TreeNodeChangeListener}s assigned to this object about this
   * change.
   */
  public void unsetMath();

}
