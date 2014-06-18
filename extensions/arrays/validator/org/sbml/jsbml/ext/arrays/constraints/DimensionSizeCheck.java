/*
 * $Id:  DimensionSize.java 12:15:02 PM lwatanabe $
 * $URL: DimensionSize.java $
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
package org.sbml.jsbml.ext.arrays.constraints;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.util.Message;


/**
 * Checks if all dimension objects have size that is both
 * scalar and constant.
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 10, 2014
 */
public class DimensionSizeCheck implements ArraysConstraint{
  Model model;
  SBase sbase;
  List<SBMLError> listOfErrors;
  
  public DimensionSizeCheck(Model model, SBase sbase)
  {
    this.model = model;
    this.sbase = sbase;
    listOfErrors = new ArrayList<SBMLError>();
  }
  
  /**
   * Validates a given sbase
   */
  @Override
  public void check()
  {
    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
    
    if(arraysSBasePlugin == null) {
      return;
    }
    
    for(Dimension dim : arraysSBasePlugin.getListOfDimensions())
    {
      if(!dim.isSetSize()) {
        System.err.println("Dimension size should have a value.");
        String shortMsg = "";
        logMissingDimensionAttribute(shortMsg);
      }
      else {
        checkSize(dim.getSize());
      }
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
      logDimensionSizeInconsistency(shortMsg);
      return;
    }
    
    if(!param.isConstant()) {
      System.err.println("Dimension size should point to a CONSTANT parameter.");
      String shortMsg = "";
      logDimensionSizeNotConstant(shortMsg);
    }
    
    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) param.getExtension(ArraysConstants.shortLabel);
    
    if(arraysSBasePlugin != null) {
      if(arraysSBasePlugin.getDimensionCount() > 0) {
        System.err.println("Dimension size should point to scalar.");
        String shortMsg = "";
        logDimensionSizeInconsistency(shortMsg);
      }
    }
  }
  
  /**
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
   * 
   * @param shortMsg
   */
  private void logDimensionSizeInconsistency(String shortMsg) {
    int code = 20204, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The value of the arrays:size attribute, if set on a given Dimension object,"+
                  "must be a valid SIdRef to an object of type Parameter. (Reference: " +
                  "SBML Level 3 Package Specification for Arrays, Version 1, Section 3.3 on page 6.)";
   
    
    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
  /**
   * 
   * @param shortMsg
   */
  private void logDimensionSizeNotConstant(String shortMsg) {
    int code = 20205, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The value of the Parameter referenced by the arrays:size attribute"+
                 "must be a non-negative scalar constant. (Reference: SBML Level 3 Package"+
                 "Specification for Arrays, Version 1, Section 3.3 on page 6.)";
   
    
    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
  /**
   * 
   * @param code
   * @param severity
   * @param category
   * @param line
   * @param column
   * @param pkg
   * @param msg
   * @param shortMsg
   */
  private void logFailure(int code, int severity, int category, int line, int column, String pkg, String msg, String shortMsg) {
    SBMLError error = new SBMLError();
    error.setCode(code);
    error.setSeverity(SBMLError.SEVERITY.values()[severity].name());
    error.setCategory("");
    error.setLine(line);
    error.setColumn(column);
    error.setPackage(pkg);
    Message message = new Message();
    message.setMessage(msg);
    error.setMessage(message);
    Message shortMessage = new Message();
    message.setMessage(shortMsg);
    error.setShortMessage(shortMessage);
    listOfErrors.add(error);
    
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#getListOfErrors()
   */
  @Override
  public List<SBMLError> getListOfErrors() {
    return listOfErrors;
  }
  
}
