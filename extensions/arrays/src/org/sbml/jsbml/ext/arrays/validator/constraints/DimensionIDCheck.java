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

import java.util.HashSet;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;


/**
 * @author Leandro Watanabe
 * @since 1.0
 */
public class DimensionIDCheck extends ArraysConstraint {

  /**
   * 
   */
  private SBase sbase;

  /**
   * @param model
   * @param sbase
   */
  public DimensionIDCheck(Model model, SBase sbase) {
    super(model);
    this.sbase = sbase;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.validator.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {

    HashSet<String> dimensionIds = new HashSet<String>();

    if (model == null) {
      return;
    }

    while(sbase != null)
    {
      ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      sbase = sbase.getParentSBMLObject();
      if (arraysSBasePlugin == null) {
        continue;
      }
      for(Dimension dimension : arraysSBasePlugin.getListOfDimensions())
      {
        if(dimension.isSetId())
        {
          if(dimensionIds.contains(dimension.getId()))
          {
            String shortMsg = "";
            logDuplicatedDimensionId(shortMsg);
          }
          else
          {
            dimensionIds.add(dimension.getId());
          }
        }
      }

    }

  }

  /**
   * 
   * @param shortMsg
   */
  private void logDuplicatedDimensionId(String shortMsg) {
    int code = 20103, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = "";
    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

}
