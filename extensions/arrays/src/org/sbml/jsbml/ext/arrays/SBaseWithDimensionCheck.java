/*
 * $Id:  SBaseWithDimensionCheck.java 1:55:44 PM lwatanabe $
 * $URL: SBaseWithDimensionCheck.java $
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
package org.sbml.jsbml.ext.arrays;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.Message;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 18, 2014
 */
public class SBaseWithDimensionCheck implements ArraysConstraint {

  private final Model model;
  private final SBase sbase;
  private final List<SBMLError> listOfErrors;

  /**
   * @param model
   * @param sbase
   */
  public SBaseWithDimensionCheck(Model model, SBase sbase)
  {
    this.model = model;
    this.sbase = sbase;
    listOfErrors = new ArrayList<SBMLError>();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {
    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

    if(arraysSBasePlugin != null)
    {
      if(arraysSBasePlugin.isSetListOfDimensions()) {
        System.err.println("Cannot have list of dimension.");
        String shortMsg = "";
        logDimensionError(shortMsg);
      }
    }

  }

  /**
   * 
   * @param shortMsg
   */
  private void logDimensionError(String shortMsg) {
    int code = 20107, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "In SBML Level~3 Core, Models, FunctionDefinitions, Units," +
        "UnitDefinitions, KineticLaws, LocalParameters, Triggers,"+
        "Priorities, and Delays are not permitted to have a ListOfDimensions."+
        " (Reference: SBML Level 3 11 Package Specification for Arrays, Version 1, Section 3.3 on page 7.)";


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
