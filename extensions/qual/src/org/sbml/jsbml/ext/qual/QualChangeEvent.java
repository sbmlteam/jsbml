/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.qual;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * @author Finja B&uuml;chel
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class QualChangeEvent extends TreeNodeChangeEvent {
  
 
  /**
   * Generated serial version identifier. 
   */
  private static final long serialVersionUID = 944717095818356337L;
  
  
  public static final String initialLevel        = "initialLevel";
  public static final String maxLevel            = "maxLevel";
  public static final String outputLevel         = "outputLevel";
  public static final String qualitativeSpecies  = "qualitativeSpecies";
  public static final String rank                = "rank";
  public static final String resultLevel         = "resultLevel";
  public static final String resultSymbol        = "resultSymbol";
  public static final String sign                = "sign";
  public static final String temporisationMath   = "temporisationMath";
  public static final String temporisationType   = "temporisationType";
  public static final String temporisationValue  = "temporisationValue";
  public static final String transitionEffect    = "transitionEffect";
  public static final String thresholdLevel      = "thresholdLevel";
  public static final String thresholdSymbol     = "thresholdSymbol";
  

  public QualChangeEvent(TreeNode source, String propertyName, Object oldValue,
      Object newValue) {
    super(source, propertyName, oldValue, newValue);
  }

}
