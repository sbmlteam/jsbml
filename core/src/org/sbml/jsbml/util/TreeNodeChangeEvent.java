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
package org.sbml.jsbml.util;

import java.beans.PropertyChangeEvent;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;

/**
 * This event tells an {@link TreeNodeChangeListener} which values have been
 * changed in an {@link SBase} and also provides the old and the new value.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class TreeNodeChangeEvent extends PropertyChangeEvent {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 1669574491009205844L;

  /*
   * Property names that can change in the life time of an SBML document.
   */

  /**
   * 
   */
  public static final String addExtension="addExtension"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String extension="sbasePlugin"; //$NON-NLS-1$
  //public static final String addNamespace="addNamespace"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String addDeclaredNamespace="addDeclaredNamespace"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String namespace = "namespace"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String notes="notes"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String setAnnotation="setAnnotation"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String level = "level"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String version="version"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String packageVersion = "packageVersion";
  /**
   * 
   */
  public static final String packageName = "packageName";
  /**
   * 
   */
  public static final String metaId = "metaId"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String parentSBMLObject = "parentSBMLObject"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String sboTerm = "sboTerm"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String annotation = "annotation"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String unsetCVTerms = "unsetCVTerms"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String symbol = "symbol"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String math = "math"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String name = "name"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String id = "id"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String compartmentType = "compartmentType"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String outside = "outside"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String message = "message"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String timeUnits = "timeUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String useValuesFromTriggerTime = "useValuesFromTriggerTime"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String variable = "variable"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String units = "units"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String baseListType = "baseListType"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String areaUnits = "areaUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String conversionFactor = "conversionFactor"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String extentUnits = "extentUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String lengthUnits = "lengthUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String substanceUnits = "substanceUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String volumeUnits = "volumeUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String value = "value"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String compartment = "compartment"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String fast = "fast"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String reversible = "reversible"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String species = "species"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String boundaryCondition = "boundaryCondition"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String charge = "charge"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String hasOnlySubstanceUnits = "hasOnlySubstanceUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String initialAmount = "initialAmount"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String spatialSizeUnits = "spatialSizeUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String speciesType = "speciesType"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String denominator = "denominator"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String stoichiometry = "stoichiometry"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String constant = "constant"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String exponent = "exponent"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String kind = "kind"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String multiplier = "multiplier"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String offset = "offset"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String scale = "scale"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String listOfUnits = "listOfUnits"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String priority = "priority"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String initialValue = "initialValue"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String persistent = "persistent"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String SBMLDocumentAttributes = "SBMLDocumentAttributes"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String model = "model"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String kineticLaw = "kineticLaw"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String spatialDimensions = "spatialDimensions"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String formula = "formula"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String size = "size"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String volume = "volume"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String className = "className"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String definitionURL = "definitionURL"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String childNode = "childNode"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String numerator = "numerator"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String mantissa = "mantissa"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String type = "type"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String style = "style"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String isSetNumberType = "isSetNumberType"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String encoding = "encoding"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String xmlTriple = "xmlTriple"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String isEOF = "isEOF"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String qualifier = "qualifier"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String modifiedDate = "modifiedDate"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String createdDate = "createdDate"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String creator = "creator"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String isExplicitlySetConstant = "isExplicitlySetConstant"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String numBvars = "numBvars"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String number = "number"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String base = "base"; //$NON-NLS-1$
  /**
   * 
   */
  public static String numPiece = "numPiece"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String mathMLClass = "class"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String refId = "refId"; //$NON-NLS-1$



  /* ==========================================================================
   * Annotation
   * =========================================================================*/

  /**
   * 
   */
  public static final String about = "about"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String nonRDFAnnotation = "nonRDFAnnotation"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String annotationNameSpaces = "annotationNameSpaces"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String history = "history"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String addCVTerm = "addCVTerm"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String removeCVTerm = "removeCVTerm"; //$NON-NLS-1$


  /* ==========================================================================
   * ASTNode
   * =========================================================================*/

  /**
   * 
   */
  public static final String userObject = "userObject"; //$NON-NLS-1$

  /**
   * 
   */
  public static final String email = "email"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String familyName = "familyName"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String givenName = "givenName"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String organization = "organization"; //$NON-NLS-1$

  /**
   * 
   */
  public static final String text = "text"; //$NON-NLS-1$

  // public static final String removeNamespace = "removeNamespace"; //$NON-NLS-1$

  /**
   * @param source
   * @param newValue
   * @param oldValue
   * @param propertyName
   */
  public TreeNodeChangeEvent(TreeNode source, String propertyName,
    Object oldValue, Object newValue) {
    super(source, propertyName, oldValue, newValue);
  }

  /**
   * 
   * @param treeNodeChangeEvent
   */
  public TreeNodeChangeEvent(TreeNodeChangeEvent treeNodeChangeEvent) {
    this(treeNodeChangeEvent.getSource(), treeNodeChangeEvent
      .getPropertyName(), treeNodeChangeEvent.getOldValue(),
      treeNodeChangeEvent.getNewValue());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public TreeNodeChangeEvent clone() {
    return new TreeNodeChangeEvent(this);
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
    final int prime = 7;
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
