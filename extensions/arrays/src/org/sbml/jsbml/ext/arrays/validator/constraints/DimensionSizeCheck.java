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

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Checks if the given {@link Dimension} object has size that is both
 * scalar and constant. Also, the size should be a non-negative integer.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class DimensionSizeCheck extends ArraysConstraint {

  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.arrays.validator.constraints.Messages");

  /**
   * 
   */
  private final Dimension dim;

  /**
   * @param model
   * @param dim
   */
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
    if (model == null || dim == null) {
      return;
    }

    if (!dim.isSetSize()) {
      String shortMsg = MessageFormat.format("Dimension objects shoud have a value for the attribute arrays:size but {0} does not have one.", dim.toString());
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

    if (param == null) {
      String shortMsg = MessageFormat.format("The attribute arrays:size of a Dimension object should point to an existing parameter but {0} points to a non-existing parameter", dim.toString());
      logDimensionSizeInvalid(shortMsg);
      return;
    }

    if (!param.isConstant()) {
      String shortMsg = MessageFormat.format("The attribute arrays:size of a Dimension object should point to a constant parameter but {0} has a non-constant value.", dim.toString());

      logDimensionSizeValueInconsistency(shortMsg);
    }

    // Test if it is an integer
    if (param.getValue() % 1 != 0) {
      String shortMsg = "The attribute arrays:size of a Dimension object should point to a parameter containing an integer value.";
      logDimensionSizeValueInconsistency(shortMsg);
    }

    if (param.getValue()  < 0) {
      String shortMsg = MessageFormat.format("The attribute arrays:size of a Dimension object should point to a parameter that has a non-negative integer value but {0} has a negative value.", dim.toString());
      logDimensionSizeValueInconsistency(shortMsg);
    }

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) param.getExtension(ArraysConstants.shortLabel);

    if (arraysSBasePlugin != null) {
      if (arraysSBasePlugin.getDimensionCount() > 0) {
        String shortMsg = MessageFormat.format("The attribute arrays:size of a Dimension object should point to a scalar parameter but {0} has a non-scalar value.", dim.toString());
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
    int code = SBMLErrorCodes.ARRAYS_20202, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("DimensionSizeCheck.logMissingDimensionAttribute");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * Log an error indicating that the size associated with the given dimension object
   * doesn't point to a valid parameter.
   * 
   * @param shortMsg
   */
  private void logDimensionSizeInvalid(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20204, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("DimensionSizeCheck.logDimensionSizeInvalid");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * Log an error indicating that the given dimension has value that is not scalar or
   * constant or non-negative integer.
   * 
   * @param shortMsg
   */
  private void logDimensionSizeValueInconsistency(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20205, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("DimensionSizeCheck.logDimensionSizeValueInconsistency");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

}
