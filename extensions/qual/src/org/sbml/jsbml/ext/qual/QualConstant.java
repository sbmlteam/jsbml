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

import java.beans.PropertyChangeEvent;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * @author Finja B&uuml;chel
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class QualConstant  extends PropertyChangeEvent {
  
 
  /**
   * Generated serial version identifier. 
   */
  private static final long serialVersionUID = 944717095818356337L;
  
  public static final String boundaryCondition   = "boundaryCondition";
  public static final String compartment         = "compartment";
  public static final String constant            = "constant";
  public static final String initialLevel        = "initialLevel";
  public static final String maxLevel            = "maxLevel";
  public static final String name                = "name";
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
  

  /**
   * Constructs a new <code>PropertyChangeEvent</code>.
   *
   * @param source  The bean that fired the event.
   * @param propertyName  The programmatic name of the property
   *    that was changed.
   * @param oldValue  The old value of the property.
   * @param newValue  The new value of the property.
   */
  public QualConstant(SBase source, String propertyName, Object oldValue,
      Object newValue) {
    super(source, propertyName, oldValue, newValue);
  }

  /**
   * 
   * @param treeNodeChangeEvent
   */
  public QualConstant(QualConstant qualChangeEvent) {
    this((SBase) qualChangeEvent.getSource(), qualChangeEvent
        .getPropertyName(), qualChangeEvent.getOldValue(),
        qualChangeEvent.getNewValue());
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  public QualConstant clone() {
    return new QualConstant(this);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    if (object.getClass().equals(getClass())) {
      TreeNodeChangeEvent tnce = (TreeNodeChangeEvent) object;
      boolean equals = tnce.isSetSource() == isSetSource();
      if (equals && isSetSource()) {
        equals &= tnce.getSource().equals(getSource());
      }
      equals &= tnce.isSetOldValue() == isSetOldValue();
      if (equals && isSetOldValue()) {
        equals &= tnce.getOldValue().equals(getOldValue());
      }
      equals &= tnce.isSetNewValue() == isSetNewValue();
      if (equals && isSetNewValue()) {
        equals &= tnce.getNewValue().equals(getNewValue());
      }
      return equals;
    }
    return false;
  }
  
  /* (non-Javadoc)
   * @see java.util.EventObject#getSource()
   */
  @Override
  public TreeNode getSource() {
    return (TreeNode) super.getSource();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 13;
    int hashCode = getClass().getName().hashCode();
    if (isSetSource()) {
      hashCode += prime * getSource().hashCode();
    }
    if (isSetOldValue()) {
      hashCode += prime * getOldValue().hashCode();
    }
    if (isSetNewValue()) {
      hashCode += prime * getNewValue().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   * @return
   */
  public boolean isSetNewValue() {
    return getNewValue() != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetOldValue() {
    return getOldValue() != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetSource() {
    return getSource() != null;
  }

}
