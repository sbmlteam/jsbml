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

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.validator.constraints.ArraysConstraint;
import org.sbml.jsbml.ext.arrays.validator.constraints.ArraysMathCheck;

/**
 * This checks a given {@link MathContainer} object math
 * is in accordance with the arrays package specification.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArraysMathValidator {

  /**
   * Validates the given {@link SBase} object.
   * 
   * @param model
   * @param math
   * @return
   */
  public static List<SBMLError> validate(Model model, MathContainer math) {

    List<ArraysConstraint> listOfConstraints = new ArrayList<ArraysConstraint>();


    List<SBMLError> listOfErrors = new ArrayList<SBMLError>();

    addConstraints(model,math, listOfConstraints);

    for (ArraysConstraint constraint : listOfConstraints) {
      constraint.check();
      listOfErrors.addAll(constraint.getListOfErrors());
    }
    return listOfErrors;
  }

  /**
   * Populates the list of constraints based on the type of the given sbase
   * to be validated.
   * 
   * @param model
   * @param math
   * @param listOfConstraints
   */
  private static void addConstraints(Model model, MathContainer math, List<ArraysConstraint> listOfConstraints) {
    ArraysMathCheck mathCheck = new ArraysMathCheck(model, math);
    listOfConstraints.add(mathCheck);
  }

}
