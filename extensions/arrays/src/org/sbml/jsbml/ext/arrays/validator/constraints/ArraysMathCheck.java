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
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.arrays.validator.constraints;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;

/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 29, 2014
 */
public class ArraysMathCheck extends ArraysConstraint{

  //TODO: get right messages
  /**
   * 
   */
  private final MathContainer mathContainer;

  /**
   * @param model
   * @param mathContainer
   */
  public ArraysMathCheck(Model model, MathContainer mathContainer) {
    super(model);
    this.mathContainer = mathContainer;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.validator.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {
    if (model == null || mathContainer == null || !mathContainer.isSetMath()) {
      return;
    }
    if (!ArraysMath.isVectorBalanced(model, mathContainer)) {
      String shortMsg = "Vectors should not be ragged.";
      logVectorInconsistency(shortMsg);
      return;
    }
    if (!ArraysMath.checkVectorMath(model, mathContainer)) {
      String shortMsg = "If vectors appears in mathematical operations, then the vector dimensions should match in"
          + " size unless it is a scalar." +  mathContainer.toString() + " has invalid vector operation.";
      logMathVectorIrregular(shortMsg);
    }
    if (!ArraysMath.checkVectorAssignment(model, mathContainer)) {
      String shortMsg = "When there is an assignment, then it must the the case that the left-hand matches the"
          + " right-hand size in dimension sizes but " + mathContainer.toString() + " doesn't.";
      logMathVectorIrregular(shortMsg);
    }

    List<ASTNode> selectorNodes = getSelectorNodes(mathContainer);
    for (ASTNode selectorNode : selectorNodes) {
      checkSelector(selectorNode);
    }
  }

  /**
   * @param math
   */
  private void checkSelector(ASTNode math) {

    if (math.getChildCount() == 0) {
      String shortMsg = "Selector MathML needs more than 1 argument.";
      logSelectorInconsistency(shortMsg);
    }

    ASTNode obj = math.getChild(0);

    if (obj.isString())
    {
      SBase sbase = model.findNamedSBase(obj.toString());

      if (sbase == null)
      {
        String shortMsg = "The first argument of " +
            math.toString() + " does not have a valid SIdRef.";
        logSelectorInconsistency(shortMsg);
        return;
      }

      ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

      if (arraysSBasePlugin.getDimensionCount() < math.getChildCount()-1)
      {
        String shortMsg = "Selector number of arguments of " +
            math.toString() + " is inconsistency .";
        logSelectorInconsistency(shortMsg);
      }



    }
    else if (!obj.isVector())
    {
      String shortMsg = "The first argument of a selector object should be a vector or an arrayed object and " +
          math.toString() + " violates this condition.";
      logSelectorInconsistency(shortMsg);
    }

    boolean isStaticComp = ArraysMath.isStaticallyComputable(model, mathContainer);

    if (!isStaticComp) {
      String shortMsg = "Selector arguments other than first should either be dimensions id or constant but " +
          math.toString() + " violates this condition.";
      logSelectorInconsistency(shortMsg);
      return;
    }

    boolean isBounded = ArraysMath.evaluateSelectorBounds(model, mathContainer);

    if (!isBounded) {
      String shortMsg = "Selector arguments other than first should not go out of bounds but " +
          math.toString() + " violates this condition.";
      logSelectorInconsistency(shortMsg);
    }

  }

  /**
   * Get all the selector {@link ASTNode}s in the given math.
   * 
   * @param mathContainer
   * @return
   */
  private List<ASTNode> getSelectorNodes(MathContainer mathContainer) {
    ASTNode math = mathContainer.getMath();
    List<ASTNode> listOfNodes = new ArrayList<ASTNode>();
    getSelectorNodes(math, listOfNodes);
    return listOfNodes;
  }

  /**
   * Recursively checks if a node is of type FUNCTION_SELECTOR. If so,
   * add to the list of selector nodes.
   * 
   * @param math
   * @param listOfNodes
   */
  private void getSelectorNodes(ASTNode math, List<ASTNode> listOfNodes) {
    if (math.getType() == ASTNode.Type.FUNCTION_SELECTOR) {
      listOfNodes.add(math);
    }
    for (int i = 0; i < math.getChildCount(); ++i) {
      getSelectorNodes(math.getChild(i), listOfNodes);
    }
  }

  /**
   * Log an error indicating that the first argument of the selector math is invalid.
   * 
   * @param shortMsg
   */
  private void logMathVectorIrregular(String shortMsg) {
    int code = 10211, severity = 0, category = 0, line = 0, column = 0;

    String pkg = "arrays";
    String msg = "For MathML operations with two or more operands involving MathML vectors or SBase objects with a list of Dimension"+
        "objects, the number of dimensions and their size must agree for all operands unless the operand is a scalar type"+
        "(i.e., it does not have a list of Dimension 31 objects). (Reference: SBML Level 3 Package Specification for"+
        "Arrays, Version 1, Section 3.5 on page 10.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * Log an error to indicate that the vector is not regular.
   * @param shortMsg
   */
  private void logVectorInconsistency(String shortMsg) {
    int code = 10206, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The arguments of a MathML vector must all have the same number of dimensions and" +
        "agree in their size. (Reference: SBML Level 3 Package Specification for Arrays, Version 1, Section 3.5 on page 10.)";

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * Log an error indicating that the first argument of the selector math is invalid.
   * 
   * @param shortMsg
   */
  private void logSelectorInconsistency(String shortMsg) {
    int code = 10207, severity = 0, category = 0, line = 0, column = 0;

    String pkg = "arrays";
    String msg = "The first argument of a MathML selector must be a MathML vector object or a valid identifier" +
        "to an SBase object extended with a list of Dimension objects. (Reference: SBML Level 3 Package" +
        "Specification for Arrays, Version 1, Section 3.5 on page 10.)";

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
}
