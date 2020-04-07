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

package org.sbml.jsbml.validator.offline;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.constraints.AbstractValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.constraints.CoreSpecialErrorCodes;
import org.sbml.jsbml.validator.offline.constraints.ValidationConstraint;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorFactory;

/**
 * A subclass of {@link ValidationContext} which implements the
 * {@link ValidationListener} interface to track his own validation process.
 * 
 * <p> An instance of this class creates an {@link SBMLErrorLog} which contains some
 * {@link SBMLError} object for every broken constraint.</p>
 * 
 * <p> The level and version parameter are
 * used to determine which rules will be checked.</p>
 * 
 * <p> For more informations about the SBML specifications look up
 * <a href="http://www.sbml.org"> sbml.org </a>.</p>
 * 
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public class LoggingValidationContext extends ValidationContext implements ValidationListener {

  /**
   * 
   */
  private SBMLErrorLog log;


  /**
   * Creates a new {@link LoggingValidationContext} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public LoggingValidationContext(int level, int version) {
    this(level, version, null, new HashSet<CHECK_CATEGORY>());
    this.addValidationListener(this);
  }


  /**
   * Creates a new {@link LoggingValidationContext} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @param rootConstraint the root constraint
   * @param categories the set of {@link CHECK_CATEGORY} to use during validation
   */
  public LoggingValidationContext(int level, int version,
    AnyConstraint<Object> rootConstraint, Set<CHECK_CATEGORY> categories) {
    super(level, version, rootConstraint, categories);
    log = new SBMLErrorLog();
  }

  @Override
  public void clear() {
    super.clear();
    this.clearErrorLog();
  }

  /**
   * Clears the error log.
   */
  public void clearErrorLog() {
    this.log.clearLog();
  }

  /**
   * Gets the {@link SBMLErrorLog} of this context.
   * 
   * @return the {@link SBMLErrorLog} of this context.
   */
  public SBMLErrorLog getErrorLog() {
    
    // Filter the error log to report the categories as libSBML
    SBMLErrorLog filteredLog = new SBMLErrorLog();
    Set<String> ignoredCategories = analyseSBMLerrorLog(log.getValidationErrors());
    
    for (SBMLError e : log.getValidationErrors()) {
      
      if (e.getCategory() != null && ignoredCategories.contains(e.getCategory())) {
        continue;
      }
      
      filteredLog.add(e);
    }
    
    return filteredLog;
  }

  /**
   * Logs an {@link SBMLError} into the {@link SBMLErrorLog}.
   * 
   * @param error the error to log
   */
  public void logFailure(SBMLError error) {
    if (error != null) {
      log.add(error);
    }
  }
  
  /**
   * Logs an {@link SBMLError} into the {@link SBMLErrorLog}.
   * 
   * @param id the error id to log
   */
  public void logFailure(int id, Object o) {
    
    if (id == CoreSpecialErrorCodes.ID_GROUP || id == CoreSpecialErrorCodes.ID_VALIDATE_TREE_NODE)
    {
      return;
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("Constraint " + id + " is broken!");
    }
    
    // Try to create the SBMLError from the .json file
    SBMLError e = null;
    
    if (o != null && o instanceof SBase) {
      e = SBMLErrorFactory.createError(id, this.getLevel(), this.getVersion(), true, (SBase) o);
    } else {
      e = SBMLErrorFactory.createError(id, this.getLevel(), this.getVersion());
      
      if (o instanceof TreeNodeWithChangeSupport) {
        SBase source = getParentSBase((TreeNodeWithChangeSupport) o);
        
        if (source != null) {
          e.setSource(source);
        }
      }
    }
    
    if (e != null) {
      
      log.add(e);
      
      // TODO - if it is an Error or above, set the current category as the maximum (or create a set of ignored categories) to validate for the next elements.
      
    } else {
      logger.warn("Couldn't load SBMLError for error code " + id);
      SBMLError defaultError = new SBMLError();
      defaultError.setCode(id);
      log.add(defaultError);
    }
  }


  /**
   * 
   * 
   * @param o
   * @return
   */
  public static SBase getParentSBase(TreeNodeWithChangeSupport o) 
  {
    if (o != null && o.isSetParent()) {
      TreeNode parent = o.getParent();
      
      if (parent instanceof SBase) {
        return (SBase) parent;
      } else {
        return getParentSBase((TreeNodeWithChangeSupport) parent);
      }
    }
    
    return null;
  }


  @Override
  public void didValidate(ValidationContext ctx, AnyConstraint<?> c, Object o, boolean success) {
    // System.out.println("Checked " + c.getId());
    if (!success) {
      
      if (c instanceof ValidationConstraint<?>) {
        ValidationConstraint<?> vc = (ValidationConstraint<?>) c;
      
        if ((vc.getValidationFunction() instanceof AbstractValidationFunction<?>) 
            && ((AbstractValidationFunction<?>) vc.getValidationFunction()).isSelfLogging()) 
        {
          // nothing to do, the SBMLerror is/are added directly by the ValidationFunction
        }
        else
        {
          // TODO - ask a factory to build a customized message
          logFailure(c.getErrorCode(), o);
        }
      }
    }

    // TODO - remove the values only at the very end
//    if (o instanceof SBase) {
//      SBase s = (SBase) o;
//      
//      if (s.isSetUserObjects()) {
//        s.userObjectKeySet().remove(JSBML.ALLOW_INVALID_SBML);
//      }   
//    }

  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.ValidationContext#validate(java.lang.Object, boolean)
   */
  @Override
  public boolean validate(Object o, boolean clearMap) {
    // go through each CategoryConstraintGroup here ? 
    // and maintain a set of category that we ignore if there was an error (not only warnings) in one of the previous category 
    
    return super.validate(o, clearMap);
  }


  @Override
  public void willValidate(ValidationContext ctx, AnyConstraint<?> c, Object o) {
    // nothing to do here
    
    
    if (o instanceof SBase) {
      SBase s = (SBase) o;
      
      s.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
    }
  }


  /**
   * Goes through the list of {@link SBMLError} given to determined which
   * check categories should be ignored.
   * 
   * <p>As soon as an error is detected in a check category, all the categories below it
   * should be ignored. The order of the check category is:
   * <ul>
   *   <li>identifier consistency</li>
   *   <li>general consistency</li>
   *   <li>sbo consistency</li>
   *   <li>math consistency</li>
   *   <li>units consistency</li>
   *   <li>overdetermined consistency</li>
   *   <li>modelling practice</li>
   * </ul>
   * </p>
   * 
   * @param validationErrors the list of {@link SBMLError} to check
   * @return a set of check categories that should be ignored.
   */
  public static Set<String> analyseSBMLerrorLog(List<SBMLError> validationErrors) 
  {
    Set<String> ignoredCategories = new HashSet<String>();
    
    for (SBMLError e : validationErrors) {
      String category = e.getCategory();
      String severity = e.getSeverity();
      
      if (severity != null && (severity.equalsIgnoreCase("error") || severity.equalsIgnoreCase("fatal"))) {
        // the category we are in will be the latest one.
        
        if (category == "SBML identifier consistency") {
            ignoredCategories.add("General SBML conformance");
            ignoredCategories.add("SBML component consistency");
        } else if ((category == "General SBML conformance") || (category == "SBML component consistency")) {
            ignoredCategories.add("SBO term consistency");
        } else if (category == "SBO term consistency") {
            ignoredCategories.add("MathML consistency");
        } else if (category == "MathML consistency") {
            ignoredCategories.add("SBML unit consistency");
        } else if (category == "SBML unit consistency") {
            ignoredCategories.add("Overdetermined model");
        } else if (category == "Overdetermined model") {
            ignoredCategories.add("Modeling practice");
        }
      }
    }
    
    return ignoredCategories;
  }
  
  
}
