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

import org.apache.log4j.Logger;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;


/**
 * This checks that a given {@link Index} object references a valid referenced attribute and that
 * it doesn't go out of bounds.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class IndexAttributesCheck extends ArraysConstraint {

  /**
   * Localization support.
   */
  private static final transient ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.arrays.validator.constraints.Messages");

  /**
   * 
   */
  private final Index index;

  /**
   * 
   */
  private static final Logger logger = Logger.getLogger(IndexAttributesCheck.class);

  /**
   * @param model
   * @param index
   */
  public IndexAttributesCheck(Model model, Index index)
  {
    super(model);
    this.index = index;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.arrays.constraints.ArraysConstraint#check()
   */
  @Override
  public void check() {
    boolean isComp = false;

    if ((model == null) || (index == null)) {
      return;
    }

    String refAttribute = index.getReferencedAttribute();

    if ((index.getParentSBMLObject() == null) || (index.getParentSBMLObject().getParentSBMLObject() == null))
    {
      logger.debug(MessageFormat.format(
        "WARNING: Index objects must be associated with a parent but {0} does not have a parent. Therefore, validation on Index {0} cannot be performed.",
        index));
      return;
    }

    SBase parent = index.getParentSBMLObject().getParentSBMLObject();

    if (refAttribute == null) {
      String msg = MessageFormat.format(
        "Index objects should have a value for attribute arrays:referencedAttribute. However, the referenced attribute of Index {0} for object {1} doesn't have a value.",
        index.toString(), parent.toString());
      logIndexMissingAttribute(msg);
      return;
    }

    String[] parse = refAttribute.split(":");

    if (parse.length == 2 && parse[0].equals("comp"))
    {
      isComp = true;
    }

    String refValue = parent.writeXMLAttributes().get(refAttribute);

    // TODO: split at ':'
    if (refValue == null) {
      if (isComp)
      {
        String shortMsg ="Array validation has encountered indices for references to variables defined outside this SBML document, so it currently cannot validate whether these indices are valid.";
        String msg = "";
        logWarning(msg, shortMsg);
      }
      else
      {
        String msg = MessageFormat.format("Index objects attribute arrays:referencedAttribute should reference a valid attribute but {0} of object {1} references an attribute that doesn't exist.", index.toString(), parent.toString());
        logInvalidRefAttribute(msg);
      }
      return;
    }

    SBase refSBase = model.findNamedSBase(refValue);

    if (refSBase == null) {
      if (isComp)
      {
        String shortMsg ="Array validation has encountered indices for references to variables defined outside this SBML document, so it currently cannot validate whether these indices are valid.";
        String msg = "";
        logWarning(msg, shortMsg);
      }
      else
      {
        String msg = MessageFormat.format("Index objects should reference a valid SIdRef but {0} of object {1} references an unknown SBase.", index.toString(), parent.toString());
        logInvalidRefAttribute(msg);
      }
      return;
    }

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) refSBase.getExtension(ArraysConstants.shortLabel);
    if (!index.isSetArrayDimension()) {
      String msg = MessageFormat.format("Index objects should have a value for attribute arrays:arrayDimension but SBase {0} has index {1} without a value for arrays:arrayDimension.", parent.toString(), index.toString());

      logIndexMissingAttribute(msg);
    }
    int arrayDimension = index.getArrayDimension();
    Dimension dim = arraysSBasePlugin.getDimensionByArrayDimension(arrayDimension);

    if (dim == null) {
      String msg = MessageFormat.format("The SIdRef of an Index object should have arrays:arrayDimension of same value of the Index object. Index {0} is referring to arrays:arrayDimension {1,number,integer} but {2} doesn't have a Dimension object with arrays:arrayDimension {1,number,integer}.", index.toString(), arrayDimension, refSBase.toString());
      logDimensionMismatch(msg);
    }

    boolean isStaticComp = ArraysMath.isStaticallyComputable(model, index);

    if (!isStaticComp) {
      String msg = MessageFormat.format("Index math should be statically computable, meaning that it should only contain dimension ids or constant values but index {0} of object {1} is not statically computable.", index.toString(), parent.toString());
      logNotStaticComp(msg);
    }

    //TODO: needs to check all bounds
    boolean isBounded = ArraysMath.evaluateIndexBounds(model, index);

    if (!isBounded) {
      if (isComp)
      {
        String shortMsg ="Array validation has encountered indices for references to variables defined outside this SBML document, so it currently cannot validate whether these indices are valid.";
        String msg = "";
        logWarning(msg, shortMsg);
      }
      else
      {
        String msg = MessageFormat.format("Index math should not go out-of-bounds but index {0} of object {1} goes out-of-bounds.", index.toString(), parent.toString());
        logNotBounded(msg);

      }
    }
  }

  /**
   * @param msg
   * @param shortMsg
   */
  private void logWarning(String msg, String shortMsg) {
    int code = -1, severity = 1, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * @param shortMsg
   */
  private void logDimensionMismatch(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20305, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("IndexAttributesCheck.logDimensionMismatch");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * @param shortMsg
   */
  private void logNotBounded(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20308, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("IndexAttributesCheck.logNotBounded");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * @param shortMsg
   */
  private void logNotStaticComp(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20307, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("IndexAttributesCheck.logNotStaticComp");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * @param shortMsg
   */
  private void logIndexMissingAttribute(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20302, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("IndexAttributesCheck.logIndexMissingAttribute");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

  /**
   * @param shortMsg
   */
  private void logInvalidRefAttribute(String shortMsg) {
    int code = SBMLErrorCodes.ARRAYS_20303, severity = 2, category = 0, line = -1, column = -1;

    String pkg = ArraysConstants.packageName;
    String msg = bundle.getString("IndexAttributesCheck.logInvalidRefAttribute");

    logFailure(code, severity, category, line, column, pkg, msg, shortMsg);
  }

}
