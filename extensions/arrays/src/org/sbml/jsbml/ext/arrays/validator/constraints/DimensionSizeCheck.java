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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;


/**
 * Checks if the given {@link Dimension} object has size that is both
 * scalar and constant. Also, the size should be a non-negative integer.
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 */
public class DimensionSizeCheck extends ArraysConstraint{
  
  private final Dimension dim;

  public DimensionSizeCheck(Model model, Dimension dim)
  {
    super(model);
    this.dim = dim;
    
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check()
  {
    if(model == null || dim == null) {
      return;
    }
    
    if(!dim.isSetSize()) {
      System.err.println("Dimension size should have a value.");
      String shortMsg = "";
      logMissingDimensionAttribute(shortMsg);
    }
    else {
      checkSize(dim.getSize());
    }

  }

  /**
   * Given an id, check if it points to a valid parameter that is
   * both scalar and constant.
   * 
   * @param id - identifier that needs to be checked
   */
  private void checkSize(String id)
  {
    Parameter param = model.getParameter(id);

    if(param == null) {
      System.err.println("Dimension size should point to an existing parameter.");
      String shortMsg = "";
      logDimensionSizeInvalid(shortMsg);
      return;
    }

    if(!param.isConstant()) {
      System.err.println("Dimension size should point to a CONSTANT parameter.");
      String shortMsg = "";
      logDimensionSizeValueInconsistency(shortMsg);
    }

    // Test if it is an integer
    if(param.getValue() % 1 != 0) {
      System.err.println("Dimension size should point to a parameter with integer value.");
      String shortMsg = "";
      logDimensionSizeValueInconsistency(shortMsg);
    }

    if(param.getValue()  > 0) {
      System.err.println("Dimension size should point to a parameter with value greater than 0.");
      String shortMsg = "";
      logDimensionSizeValueInconsistency(shortMsg);
    }

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) param.getExtension(ArraysConstants.shortLabel);

    if(arraysSBasePlugin != null) {
      if(arraysSBasePlugin.getDimensionCount() > 0) {
        System.err.println("Dimension size should point to scalar.");
        String shortMsg = "";
        logDimensionSizeInvalid(shortMsg);
      }
    }
  }

  /**
   * Log an error indicating that a required attribute value is not set.
   * 
   * @param shortMsg
   */
  private void logMissingDimensionAttribute(String shortMsg) {
    int code = 20202, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "A Dimension object must have a value for the attributes"+
        "arrays:arrayDimension and arrays:size, and may additionally" +
        "have the attributes arrays:id and arrays:name. (Reference:"+
        "SBML Level 3 Package Specification for Arrays, Version 1, Section 3.3 on page 6.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * Log an error indicating that the size associated with the given dimension object
   * doesn't point to a valid parameter.
   * 
   * @param shortMsg
   */
  private void logDimensionSizeInvalid(String shortMsg) {
    int code = 20204, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The value of the arrays:size attribute, if set on a given Dimension object,"+
        "must be a valid SIdRef to an object of type Parameter. (Reference: " +
        "SBML Level 3 Package Specification for Arrays, Version 1, Section 3.3 on page 6.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * Log an error indicating that the given dimension has value that is not scalar or
   * constant or non-negative integer.
   * 
   * @param shortMsg
   */
  private void logDimensionSizeValueInconsistency(String shortMsg) {
    int code = 20205, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The value of the Parameter referenced by the arrays:size attribute"+
        "must be a non-negative scalar constant integer. (Reference: SBML Level 3 Package"+
        "Specification for Arrays, Version 1, Section 3.3 on page 6.)";


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

}
