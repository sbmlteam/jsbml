/*
 * $Id:  IndexArrayDimCheck.java 8:41:08 PM lwatanabe $
 * $URL: IndexArrayDimCheck.java $
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
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.util.Message;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 18, 2014
 */
public class IndexArrayDimCheck implements ArraysConstraint {

  private final Model model;
  private final SBase sbase;
  private final List<SBMLError> listOfErrors;
  
  public IndexArrayDimCheck(Model model, SBase sbase)
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

    int max = -1;
    
    if(arraysSBasePlugin == null) {
      return;
    }

    
    for(Index index : arraysSBasePlugin.getListOfIndices())
    {
      if(index.getArrayDimension() > max) {
        max = index.getArrayDimension();
      }
    }
    
    boolean[] isSetArrayDimAt = new boolean[max+1];
    
    for(Dimension dim : arraysSBasePlugin.getListOfDimensions())
    {
      int arrayDim = dim.getArrayDimension();

      if(!isSetArrayDimAt[arrayDim]) {
        isSetArrayDimAt[arrayDim] = true;
      }
      else 
      {
        System.err.println("Array Dimension should be unique.");
        String shortMsg = "";
        logArrayDimensionUniqueness(shortMsg);
      }
    }

    for(int i = 0; i <= max; i++) {
      if(!isSetArrayDimAt[i]) {
        System.err.println("There is a Dimension with array dimension " + max + " but there is no array"
          + " dimension at " + i);
        String shortMsg = "";
        logArrayDimensionMissing(shortMsg);
        return;
      }
    }
    
  }

  /**
   * 
   * @param shortMsg
   */
  private void logArrayDimensionUniqueness(String shortMsg) {
    int code = 20111, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The  ListOfIndices  associated with an SBase object must not have multiple Index"+
                 "objects with the same arrays:arrayDimension attribute. (Reference: SBML Level 3 Package"+ 
                 "Specification for Arrays, Version 1, Section 3.3 on page 6.)";
   
    
    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
  
  
  /**
   * 
   * @param shortMsg
   */
  private void logArrayDimensionMissing(String shortMsg) {
    int code = 20110, severity = 2, category = 0, line = -1, column = -1;

    String pkg = "arrays";
    String msg = "The ListOfIndices associated with an SBase object must have a Index object"+
                 "with arrays:arrayDimension attribute set to 0 before adding a Index object with"+
                 "arrays:arrayDimension attribute set to 1. Similarly, the ListOfIndices in an SBase"+
                 "object must have Index objects, where one of them has arrays:arrayDimension attribute"+
                 "set to 0 and the other set to 1 before adding a Index object with arrays:arrayDimension"+
                 "attribute set to 2. (Reference: SBML Level 3 Package Specification for Arrays, Version 1,"+
                 "Section 3.3 on page 6.)";
   
    
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
