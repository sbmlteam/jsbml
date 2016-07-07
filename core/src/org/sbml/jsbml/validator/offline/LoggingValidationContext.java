/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2016 jointly by the following organizations:
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

package org.sbml.jsbml.validator.offline;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.factory.CheckCategory;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorFactory;

/**
 * A subclass of {@link ValidationContext} which implements the
 * {@link ValidationListener} interface to track his own validation process.
 * <p>
 * A instance of this class creates a {@link SBMLErrorLog} which contains a
 * {@link SBMLError} object for every broken constraint.
 * <p>
 * The level and version parameter are
 * used to determine which rules will be checked.
 * <p>
 * For more informations about the SBML specifications look up
 * <a href="http://www.sbml.org"> sbml.org </a>
 * 
 * @author Roman
 * @since 1.2
 * @date 06.07.2016
 */
public class LoggingValidationContext extends ValidationContext
  implements ValidationListener {

  private SBMLErrorLog log;


  public LoggingValidationContext(int level, int version) {
    this(level, version, null, new ArrayList<CheckCategory>());
    this.addValidationListener(this);
    // TODO Auto-generated constructor stub
  }


  public LoggingValidationContext(int level, int version,
    AnyConstraint<Object> rootConstraint, List<CheckCategory> categories) {
    super(level, version, rootConstraint, categories);
    log = new SBMLErrorLog();
  }


  /**
   * Clears the error log.
   */
  public void clearErrorLog() {
    this.log.clearLog();
  }

  /**
   * Gets the {@link SBMLErrorLog} of this context.
   * @return
   */
  public SBMLErrorLog getErrorLog() {
    return this.log;
  }


  private void logFailure(int id) {
    logger.debug("Constraint " + id + " is broken!");
    
    // Try to create the SBMLError from the .json file
    SBMLError e =
      SBMLErrorFactory.createError(id, this.getLevel(), this.getVersion());
    
    if (e != null) {
      this.log.add(e);
      
    } else {
      logger.warn("Couldn't load SBMLError for error code " + id);
    }
  }


  @Override
  public void willValidate(ValidationContext ctx, AnyConstraint<?> c,
    Object o) {
    // TODO Auto-generated method stub
  }


  @Override
  public void didValidate(ValidationContext ctx, AnyConstraint<?> c, Object o,
    boolean success) {
    // System.out.println("Checked " + c.getId());
    if (!success) {
      logFailure(c.getId());
    }
  }
}
