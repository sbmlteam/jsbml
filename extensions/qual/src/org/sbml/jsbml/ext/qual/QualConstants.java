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
package org.sbml.jsbml.ext.qual;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * Contains some constants related to the qual package.
 * 
 * @author Finja B&uuml;chel
 * @since 1.0
 */
public class QualConstants  extends PropertyChangeEvent {

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/qual/version1";

  /**
   * The latest namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;

  /**
   * The short name/label of the package
   */
  public static final String shortLabel = "qual";

  /**
   * 
   */
  public static final List<String> namespaces;

  static {
    namespaces = new ArrayList<String>();
    namespaces.add(namespaceURI_L3V1V1);
  }

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 944717095818356337L;

  /**
   * 
   */
  public static final String compartment         = "compartment";
  /**
   * 
   */
  public static final String constant            = "constant";
  /**
   * 
   */
  public static final String initialLevel        = "initialLevel";
  /**
   * 
   */
  public static final String maxLevel            = "maxLevel";
  /**
   * 
   */
  public static final String name                = "name";
  /**
   * 
   */
  public static final String outputLevel         = "outputLevel";
  /**
   * 
   */
  public static final String qualitativeSpecies  = "qualitativeSpecies";
  /**
   * 
   */
  public static final String rank                = "rank";
  /**
   * 
   */
  public static final String resultLevel         = "resultLevel";
  /**
   * 
   */
  public static final String sign                = "sign";
  /**
   * 
   */
  public static final String transitionEffect    = "transitionEffect";
  /**
   * 
   */
  public static final String thresholdLevel      = "thresholdLevel";


  /**
   * 
   */
  public static final String packageName = "Qualitative";
  
  /**
   * 
   */
  public static final String defaultTerm = "defaultTerm";

  /**
   * 
   */
  public static final String listOfFunctionTerms = "listOfFunctionTerms";

  /**
   * 
   */
  public static final String listOfInputs = "listOfInputs";

  /**
   * 
   */
  public static final String listOfOutputs = "listOfOutputs";

  /**
   * 
   */
  public static final String listOfQualitativeSpecies = "listOfQualitativeSpecies";

  /**
   * 
   */
  public static final String listOfTransitions = "listOfTransitions";



  /**
   * Constructs a new {@code PropertyChangeEvent}.
   *
   * @param source  The bean that fired the event.
   * @param propertyName  The programmatic name of the property
   *    that was changed.
   * @param oldValue  The old value of the property.
   * @param newValue  The new value of the property.
   */
  public QualConstants(SBase source, String propertyName, Object oldValue,
    Object newValue) {
    super(source, propertyName, oldValue, newValue);
  }

  /**
   * Constructs a new {@link PropertyChangeEvent}, cloned from
   * the given {@code qualChangeEvent}.
   * 
   * @param qualChangeEvent the event to clone.
   */
  public QualConstants(QualConstants qualChangeEvent) {
    this((SBase) qualChangeEvent.getSource(), qualChangeEvent
      .getPropertyName(), qualChangeEvent.getOldValue(),
      qualChangeEvent.getNewValue());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public QualConstants clone() {
    return new QualConstants(this);
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
   * Returns {@code true} is the new value is set.
   * 
   * @return {@code true} is the new value is set.
   */
  public boolean isSetNewValue() {
    return getNewValue() != null;
  }

  /**
   * Returns {@code true} is the old value is set.
   * 
   * @return {@code true} is the old value is set.
   */
  public boolean isSetOldValue() {
    return getOldValue() != null;
  }

  /**
   * Returns {@code true} is the source is set.
   * 
   * @return  true is the source is set.
   */
  public boolean isSetSource() {
    return getSource() != null;
  }

  /**
   * Returns a namespace URI corresponding to the given level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return a namespace URI corresponding to the given level and version.
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }

}
