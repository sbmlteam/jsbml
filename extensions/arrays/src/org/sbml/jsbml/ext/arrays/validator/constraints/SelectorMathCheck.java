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

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;


/**
 * This checks if the arguments of a selector function is correct.
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 */
public class SelectorMathCheck extends ArraysConstraint {

  // TODO: need to check each node in the tree and see if the node is selector
  private final MathContainer mathContainer;
  
  /**
   * Constructs a new SelectorMathCheck with a model and mathContainer.
   * @param model
   * @param sbase
   * @param math
   */
  public SelectorMathCheck(Model model, MathContainer mathContainer) {
    super(model);
    this.mathContainer = mathContainer;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check()
  {
    if(model == null || mathContainer == null) {
      return;
    }
    
    ASTNode math = mathContainer.getMath();
    
    if(math.getType() != ASTNode.Type.FUNCTION_SELECTOR) {
      return;
    }
    
    if(math.getChildCount() == 0) {
      String shortMsg = "Selector MathML needs more than 1 argument.";
      logSelectorInconsistency(shortMsg);
    }

    ASTNode obj = math.getChild(0);

    if(obj.isString())
    {
      SBase sbase = model.findNamedSBase(obj.toString());
      
      if(sbase == null)
      {
        //System.err.println("Selector references non-valid object");
        return;
      }
      
      ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      
      if(arraysSBasePlugin.getDimensionCount() < math.getChildCount()-1)
      {
        //System.err.println("Selector references an object with number of dimensions that doesn't match the number of arguments");
        String shortMsg = "";
        logSelectorInconsistency(shortMsg);
      }
      
      
      
    }
    else if(!obj.isVector())
    {
      //System.err.println("The first argument of selector should be a vector or valid SIdref");
      String shortMsg = "";
      logSelectorInconsistency(shortMsg);
    }
    
    boolean isStaticComp = ArraysMath.isStaticallyComputable(model, mathContainer);
    
    if(!isStaticComp) {
      //System.err.println("Selector should be statically computable");
      String shortMsg = "";
      logSelectorInconsistency(shortMsg);
      return;
    }
    
    
    // TODO: check if all arguments evaluate to an integer and that is less than the size of the array

    boolean isBounded = ArraysMath.evaluateSelectorBounds(model, mathContainer);
    
    if(!isBounded) {
      //System.err.println("Selector arguments other than first should not go out of bounds");
      String shortMsg = "";
      logSelectorInconsistency(shortMsg);
    }
    
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
