/*
 * $Id: SBaseWithDimensionCheck.java 2186 2015-04-20 11:37:47Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/arrays/src/org/sbml/jsbml/ext/arrays/validator/constraints/SBaseWithDimensionCheck.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
import org.sbml.jsbml.util.ResourceManager;

/**
 * This checks if the given {@link SBase} is allowed to have a listOfDimensions.
 * 
 * @author Leandro Watanabe
 * @version $Rev: 2186 $
 * @since 1.0
 * @date Jun 18, 2014
 */
public class SBaseWithDimensionCheck extends ArraysConstraint {

  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.arrays.validator.constraints.Messages");

  /**
   * 
   */
  private final SBase sbase;

  /**
   * Creates a new SBaseWithDimensionCheck with a model and sbase.
   * @param model
   * @param sbase
   */
  public SBaseWithDimensionCheck(Model model, SBase sbase)
  {
    super(model);
    this.sbase = sbase;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {
    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

    if (arraysSBasePlugin != null)
    {
      if (arraysSBasePlugin.isSetListOfDimensions()) {
        String shortMsg = MessageFormat.format(bundle.getString("SBaseWithDimensionCheck.check"), sbase.getElementName());
        logDimensionError(shortMsg);
      }
    }

  }

  /**
   * Log an error indicating this object cannot have a listOfDimensions but it does.
   * @param shortMsg
   */
  private void logDimensionError(String shortMsg) {
    int code = 20107, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("SBaseWithDimensionCheck.logDimensionError");


    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }
}
