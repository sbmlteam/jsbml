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

import java.util.Map;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;


/**
 * This checks if vector is not rigged.
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 */
public class VectorMathCheck extends ArraysConstraint {
  private final MathContainer mathContainer;

  public VectorMathCheck(Model model, MathContainer mathContainer)
  {
    super(model);
    this.mathContainer = mathContainer;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.ArraysConstraint#check()
   */
  @Override
  public void check() {
    if(model == null || mathContainer == null) {
      return;
    }

    ASTNode math = mathContainer.getMath();
    Map<Integer, Integer> sizeByLevel = ArraysMath.getVectorDimensionSizes(model, math);
    checkSizeRecursive(sizeByLevel, math, 0);
  }

  /**
   * This is used to check if the array is regular.
   * 
   * @param sizeByLevel
   * @param node
   * @param level
   * @return
   */
  private boolean checkSizeRecursive(Map<Integer, Integer> sizeByLevel, ASTNode node, int level) {

    if(!sizeByLevel.containsKey(level)) {
        //System.err.println("Vector is not regular");
        String shortMsg = "";
        logVectorInconsistency(shortMsg);
        return false;
    }


    int expected = sizeByLevel.get(level);

    if(!node.isVector()) {
      if(node.isString()) {
        String id = node.toString();
        SBase sbase = model.findNamedSBase(id);
        ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
        for(Dimension dim : arraysSBasePlugin.getListOfDimensions()) {
          String size = dim.getSize();
          Parameter p = model.getParameter(size);
          if(p == null) {
            return false;
          }
          int actual =  (int) p.getValue();
          if(expected != actual) {
            //System.err.println("Vector is not regular");
            String shortMsg = "";
            logVectorInconsistency(shortMsg);
            return false;
          }

        }
        return true;
      }
      else {
        //System.err.println("Should be a vector instead of scalar");
        String shortMsg = "";
        logVectorInconsistency(shortMsg);
        return false;
      }
    }

    if(expected != node.getChildCount()) {
      //System.err.println("Vector is not regular");
      String shortMsg = "";
      logVectorInconsistency(shortMsg);
      return false;
    }

    for(int i = 0; i < node.getChildCount(); i++) {
      ASTNode child = node.getChild(i);
      if(!checkSizeRecursive(sizeByLevel, child, level+1)) {
        return false;
      }
    }

    return true;
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


}
