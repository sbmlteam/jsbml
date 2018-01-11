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
package org.sbml.jsbml.ext.arrays.validator.constraints;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.util.Message;


/**
 * An {@link ArraysConstraint} object ensures that a given {@link Model} doesn't
 * violate a certain validation rule defined in the arrays specification.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public abstract class ArraysConstraint {

  /**
   * This is used to track {@link SBMLError}s that are detected in the model.
   */
  protected List<SBMLError> listOfErrors;

  /**
   * A Model to be checked.
   */
  protected Model model;

  /**
   * Constructs an ArraysConstraint.
   */
  public ArraysConstraint() {
    model = null;
    listOfErrors = new ArrayList<SBMLError>();
  }

  /**
   * Constructs an ArraysConstraint with a model.
   * 
   * @param model a core {@link Model}
   */
  public ArraysConstraint(Model model) {
    this.model = model;
    listOfErrors = new ArrayList<SBMLError>();
  }

  /**
   * Checks if the given model
   * doesn't violate the constraint specified by this
   * object.
   */
  public abstract void check(); // TODO: return list of errors

  /**
   * Gets the listOfErrors after checking the model.
   * 
   * @return the list of {@link SBMLError}s after checking the model.
   */
  public List<SBMLError> getListOfErrors() {
    return listOfErrors;
  }

  /**
   * Logs an error when checking the given model.
   * 
   * @param code the error code
   * @param severity the error severity
   * @param category the error category
   * @param line the line number
   * @param column the column number
   * @param pkg the SBML package the {@link SBMLError} belong to.
   * @param msg the message
   * @param shortMsg the short message
   */
  protected void logFailure(int code, int severity, int category, int line, int column, String pkg, String msg, String shortMsg) {
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

}
