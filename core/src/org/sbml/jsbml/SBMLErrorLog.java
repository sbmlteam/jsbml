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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.sbml.jsbml.util.Option;

/**
 * Log of errors and other events encountered during SBML processing.
 * <p>
 * This class is used both to have the same API as the libSBML equivalent, and also
 * to store the information sent by the SBML online validator.
 * <p>
 * See:<ul>
 * <li><a href="http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/SBMLErrorLog.html">libSBML SBMLErrorLog</a>
 * <li><a href="http://sbml.org/Facilities/Documentation/Validator_Web_API">SBML Online Validator web API</a>
 * </ul>
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class SBMLErrorLog {

  /**
   * 
   */
  private File file;

  /**
   * 
   */
  private List<Option> options = new ArrayList<Option>();
  /**
   * 
   */
  private List<SBMLError> validationErrors = new ArrayList<SBMLError>();

  /**
   * 
   */
  private String status;

  /**
   * Adds an option.
   * 
   * @param option
   * @return {@code true} if the option was added successfully.
   */
  public boolean add(Option option) {
    return options.add(option);
  }

  /**
   * Adds an error.
   * 
   * @param e
   * @return {@code true} if the error was added successfully.
   */
  public boolean add(SBMLError e) {
    return validationErrors.add(e);
  }

  /**
   * Clears the log.
   */
  public void clearLog() {
    validationErrors.clear();
  }

  /**
   * Returns the <i>n</i>th {@link SBMLError} object in this log.
   * <p>
   * Index {@code n} is counted from 0.  Callers should first inquire about the
   * number of items in the log by using the
   * {@link #getNumErrors()} method.
   * Attempts to use an error index number that exceeds the actual number
   * of errors in the log will result in a {@code null} being returned.
   * <p>
   * @param n the index number of the error to retrieve (with 0 being the
   * first error).
   * <p>
   * @return the <i>n</i>th {@link SBMLError} in this log, or {@code null} if {@code n} is
   * greater than or equal to {@link #getNumErrors()}.
   * <p>
   * @see #getNumErrors()
   */
  public SBMLError getError(long n) {
    if (n >= 0 && n < validationErrors.size()) {
      return validationErrors.get((int) n);
    }

    return null;
  }

  /**
   * Returns the file containing the xml error log representation.
   * 
   * @return the file containing the xml error log representation.
   */
  File getFile() {
    return file;
  }

  /**
   * Returns the number of errors that have been logged.
   * <p>
   * To retrieve individual errors from the log, callers may use
   * {@link #getError(long n)}.
   * <p>
   * @return the number of errors that have been logged.
   * @libsbml.deprecated use {@link #getErrorCount()}
   */
  public int getNumErrors() {
    return getErrorCount();
  }

  /**
   * Returns the number of errors that have been logged.
   * <p>
   * To retrieve individual errors from the log, callers may use
   * {@link #getError(long n)}.
   * <p>
   * @return the number of errors that have been logged.
   */
  public int getErrorCount() {
    return validationErrors.size();
  }

  /**
   * Returns the number of errors that have been logged with the given
   * severity code.
   * <p>
   * LibSBML associates severity levels with every {@link SBMLError} object to
   * provide an indication of how serious the problem is.  Severities range
   * from informational diagnostics to fatal (irrecoverable) errors.  Given
   * an {@link SBMLError} object instance, a caller can interrogate it for its
   * severity level using methods such as {@link SBMLError#getSeverity()},
   * {@link SBMLError#isFatal()}, and so on.  The present method encapsulates
   * iteration and interrogation of all objects in an {@link SBMLErrorLog}, making
   * it easy to check for the presence of error objects with specific
   * severity levels.
   * <p>
   * @param severity a value from the enumeration {@link SBMLError.SEVERITY}
   * <p>
   * @return a count of the number of errors with the given severity code.
   * <p>
   * @see #getNumErrors()
   */
  public int getNumFailsWithSeverity(SBMLError.SEVERITY severity) {
    return getErrorsBySeverity(severity).size();
  }

  /**
   * 
   * @param severity
   * @return
   */
  public List<SBMLError> getErrorsBySeverity(SBMLError.SEVERITY severity) {
    List<SBMLError> listOfErrors = new LinkedList<SBMLError>();
    for (SBMLError error : validationErrors) {
      switch(severity) {
      case INFO: {
        if (error.isInfo()) {
          listOfErrors.add(error);
        }
        break;
      }
      case WARNING: {
        if (error.isWarning()) {
          listOfErrors.add(error);
        }
        break;
      }
      case ERROR: {
        if (error.isError()) {
          listOfErrors.add(error);
        }
        break;
      }
      case FATAL: {
        if (error.isFatal()) {
          listOfErrors.add(error);
        }
        break;
      }
      }
    }
    return listOfErrors;
  }

  /**
   * Returns the list of options.
   * 
   * @return the list of options.
   */
  List<Option> getOptions() {
    if (options == null) {
      options = new ArrayList<Option>();
    }
    return options;
  }

  /**
   * Returns the status of the error log.
   * 
   * @return the status of the error log.
   */
  String getStatus() {
    return status;
  }

  /**
   * Returns the list of {@link SBMLError}
   * 
   * @return the list of {@link SBMLError}
   */
  public List<SBMLError> getValidationErrors() {
    if (validationErrors == null) {
      // This is to prevent NullPointerException, if there is no errors, xstream set the collection to null.
      validationErrors = new ArrayList<SBMLError>();
    }

    return validationErrors;
  }

  /**
   * Sets the file.
   * 
   * @param file
   */
  public void setFile(File file) {
    this.file = file;
  }

  /**
   * Sets the list of options.
   * 
   * @param options
   */
  public void setOptions(List<Option> options) {
    this.options = options;
  }

  /**
   * Sets the status.
   * 
   * @param status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Sets the list of errors.
   * 
   * @param validationErrors
   */
  void setValidationErrors(List<SBMLError> validationErrors) {
    if (validationErrors == null) {
      clearLog();
      return;
    }

    this.validationErrors = validationErrors;
  }

}
