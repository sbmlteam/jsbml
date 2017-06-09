/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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

package org.sbml.jsbml.validator.offline.constraints;

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.validator.offline.LoggingValidationContext;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorFactory;

/**
 * The basic constraint which uses a {@link ValidationFunction} object to
 * perform validation.
 * 
 * <p>Notice that two {@link ValidationConstraint} objects are equal if they have the same error Code.</p>
 * 
 * @author Roman
 * @since 1.2
 */
public class ValidationConstraint<T> extends AbstractConstraint<T> {

  /**
   * the logger
   */
  protected static transient Logger logger = Logger.getLogger(ValidationConstraint.class);
  /**
   *  the {@link ValidationFunction} that will perform the validation
   */
  private ValidationFunction<T>     func;


  
  /**
   * Logs a new {@link SBMLError} into a {@link LoggingValidationContext}.
   * 
   * @param ctx the context
   * @param errorCode the error code
   */
  public static void logError(ValidationContext ctx, int errorCode) {
    if (ctx != null && ctx instanceof LoggingValidationContext) {
      LoggingValidationContext lctx = (LoggingValidationContext) ctx;
      
      lctx.logFailure(errorCode);
    }
  }

  /**
   * Logs a new {@link SBMLError} into a {@link LoggingValidationContext}.
   * 
   * @param ctx the context
   * @param errorCode the error code
   */
  public static void logError(ValidationContext ctx, int errorCode, String messageFormat, String...args) {
    if (ctx != null && ctx instanceof LoggingValidationContext) {
      LoggingValidationContext lctx = (LoggingValidationContext) ctx;
      
      // Try to create the SBMLError from the .json file
      SBMLError e = SBMLErrorFactory.createError(errorCode, ctx.getLevel(), ctx.getVersion());
      
      String detailedMessage = MessageFormat.format(messageFormat, (Object[]) args);

      e.getMessageInstance().setMessage(e.getMessageInstance().getMessage() + detailedMessage);
      
      lctx.logFailure(e);
    }
  }

  /**
   * Creates a new {@link ValidationConstraint} instance.
   * 
   * @param errorCode the error code
   * @param func the {@link ValidationFunction} that will perform the validation
   */
  public ValidationConstraint(int errorCode, ValidationFunction<T> func) {
    super(errorCode);
    this.func = func;
  }


  /**
   * Returns {@code true} if the two {@link ValidationConstraint} objects have the same error code.
   * 
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ValidationConstraint<?>) {
      return ((ValidationConstraint<?>) obj).getErrorCode() == this.getErrorCode();
    }
    return false;
  }

 
  @Override
  public boolean check(ValidationContext context, T t) {
    boolean passed = true;
    
    if (logger.isDebugEnabled()) {
      logger.debug("Validate constraint " + this.getErrorCode());
    }

    if (this.func != null) {
      passed = func.check(context, t);

    }

    context.didValidate(this, t, passed);

    return passed;
  }
  
  
  /**
   * Returns the {@link ValidationFunction} registered in this {@link ValidationConstraint}.
   * 
   * @return the {@link ValidationFunction} registered in this {@link ValidationConstraint}.
   */
  public ValidationFunction<T> getValidationFunction() {
    return func;
  }

}
