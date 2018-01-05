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
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Checks if the {@link Dimension} objects of a given {@link SBase} have valid array dimension.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class DimensionArrayDimCheck extends ArraysConstraint {

  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.arrays.validator.constraints.Messages");

  /**
   * 
   */
  private final SBase sbase;

  /**
   * Creates a DimensionArrayDimCheck with a model and sbase.
   * 
   * @param model the top level {@link Model}
   * @param sbase the {@link SBase} to validate
   */
  public DimensionArrayDimCheck(Model model, SBase sbase)
  {
    super(model);
    this.sbase = sbase;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {

    if ((model == null) || (sbase == null)) {
      return;
    }

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

    int max = -1;

    if (arraysSBasePlugin == null) {
      return;
    }


    for (Dimension dim : arraysSBasePlugin.getListOfDimensions())
    {
      if (dim.isSetArrayDimension() && dim.getArrayDimension() > max) {
        max = dim.getArrayDimension();
      }
    }

    boolean[] isSetArrayDimAt = new boolean[max+1];

    for (Dimension dim : arraysSBasePlugin.getListOfDimensions())
    {
      if (!dim.isSetArrayDimension()) {
        continue;
      }
      
      int arrayDim = dim.getArrayDimension();

      if (!isSetArrayDimAt[arrayDim]) {
        isSetArrayDimAt[arrayDim] = true;
      }
      else
      {
        String shortMsg = MessageFormat.format("A listOfDimensions should have Dimension objects with unique attribute arrays:arrayDimension, but the value {0,number,integer} is used multiple times.", arrayDim);
        logArrayDimensionUniqueness(shortMsg);
      }
    }

    for (int i = 0; i <= max; i++) {
      if (!isSetArrayDimAt[i]) {
        String shortMsg = MessageFormat.format("A listOfDimensions should have a Dimension with arrays:arrayDimension {0,number,integer} before adding a Dimension object with arrays:arrayDimension {1,number,integer}", i, max);
        logArrayDimensionMissing(shortMsg);
        return;
      }
    }

  }

  /**
   * Logs an error indicating that two or more Dimension objects have the same array dimension.
   * 
   * @param shortMsg the short message
   */
  private void logArrayDimensionUniqueness(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20104, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("DimensionArrayDimCheck.logArrayDimensionUniqueness");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }


  /**
   * Logs an error indicating a listOfDimensions have a Dimension with array dimension n
   * but not a Dimension with array dimension from 0...n-1.
   * 
   * @param shortMsg the short message
   */
  private void logArrayDimensionMissing(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20103, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("DimensionArrayDimCheck.logArrayDimensionMissing");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

}

