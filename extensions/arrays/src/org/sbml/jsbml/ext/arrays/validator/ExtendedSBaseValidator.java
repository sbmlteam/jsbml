/*
 * 
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
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.ext.arrays.validator;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.Delay;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.validator.constraints.ArraysConstraint;
import org.sbml.jsbml.ext.arrays.validator.constraints.DimensionArrayDimCheck;
import org.sbml.jsbml.ext.arrays.validator.constraints.IndexArrayDimCheck;
import org.sbml.jsbml.ext.arrays.validator.constraints.SBaseWithDimensionCheck;


/**
 * Validates a given {@link SBase} object in the context of a given model and makes sure that
 * the {@link SBase}  object is in accordance with the arrays package specification.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ExtendedSBaseValidator{

  /**
   * Validates the given {@link SBase} object.
   * 
   * @param model the top level {@link Model}
   * @param sbase the {@link SBase} to validate
   * @return a list of {@link SBMLError}s
   */
  public static List<SBMLError> validate(Model model, SBase sbase) {

    List<ArraysConstraint> listOfConstraints = new ArrayList<ArraysConstraint>();
    List<SBMLError> listOfErrors = new ArrayList<SBMLError>();
    
    addConstraints(model, sbase, listOfConstraints);

    for (ArraysConstraint constraint : listOfConstraints) {
      constraint.check();
      listOfErrors.addAll(constraint.getListOfErrors());
    }
    return listOfErrors;
  }


  /**
   * Populates the list of constraints based on the type of the given {@link SBase}
   * to be validated.
   * 
   * @param model the top level {@link Model}
   * @param sbase the {@link SBase} to validate
   * @param listOfConstraints a list of {@link ArraysConstraint} to be populated
   */
  private static void addConstraints(Model model, SBase sbase, List<ArraysConstraint> listOfConstraints) {
    if (canHaveDimension(sbase)) 
    {      
      DimensionArrayDimCheck arrayDimCheck = new DimensionArrayDimCheck(model, sbase);
      listOfConstraints.add(arrayDimCheck);
    }
    else 
    {
      SBaseWithDimensionCheck dimensionCheck = new SBaseWithDimensionCheck(model, sbase);
      listOfConstraints.add(dimensionCheck);
    }
    
    IndexArrayDimCheck indexArrayDimCheck = new IndexArrayDimCheck(model, sbase);
    listOfConstraints.add(indexArrayDimCheck);
  }
  
  /**
   * Checks if this {@link SBase} can have a list of {@link Dimension}.
   * 
   * @param sbase the {@link SBase} to check
   * @return {@code true} if this {@link SBase} can have a list of {@link Dimension}.
   */
  public static boolean canHaveDimension(SBase sbase) {
    
    if (sbase instanceof Model) {
      return false;
    }
    if (sbase instanceof FunctionDefinition) {
      return false;
    }
    if (sbase instanceof Unit) {
      return false;
    }
    if (sbase instanceof UnitDefinition) {
      return false;
    }
    if (sbase instanceof KineticLaw) {
      return false;
    }
    if (sbase instanceof Trigger) {
      return false;
    }
    if (sbase instanceof Priority) {
      return false;
    }  
    if (sbase instanceof Delay) {
      return false;
    }
    if (sbase instanceof ListOf) {
      return false;
    }
    if (sbase instanceof Dimension) {
      return false;
    }
    if (sbase instanceof Index) {
      return false;
    }
    
    return true;
  }
  
}
