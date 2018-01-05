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

import java.util.Map;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.AbstractReaderWriter;

/**
 * Each {@link FunctionTerm} is also associated with a result and in addition to a Boolean
 * function inside a Math element that can be used to set the conditions under which this term
 * is selected.
 * 
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @since 1.0
 */
public class FunctionTerm extends AbstractMathContainer {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3456373304133826017L;

  /**
   * 
   */
  private Integer resultLevel;
  /**
   * 
   */
  private boolean defaultTerm;

  /**
   * Creates a new {@link FunctionTerm} instance.
   * 
   */
  public FunctionTerm() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link FunctionTerm} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public FunctionTerm(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link FunctionTerm} instance.
   * 
   * @param math the {@link ASTNode} representing the function.
   * @param level the SBML level
   * @param version the SBML version
   */
  public FunctionTerm(ASTNode math, int level, int version) {
    super(math, level, version);
    initDefaults();
  }

  /**
   * Creates a FunctionTerm instance from a given FunctionTerm.
   * 
   * @param ft an {@code FunctionTerm} object to clone
   */
  public FunctionTerm(FunctionTerm ft) {
    super(ft);

    setDefaultTerm(ft.isDefaultTerm());
    if (ft.isSetResultLevel()) {
      setResultLevel(ft.getResultLevel());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public FunctionTerm clone() {
    return new FunctionTerm(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = QualConstants.shortLabel;
  }

  /**
   * Returns false, resultLevel is not mandatory.
   * 
   * @return false
   */
  public boolean isResultLevelMandatory() {
    return false;
  }

  /**
   * Returns {@code true} if resultLevel is set.
   * 
   * @return {@code true} if resultLevel is set.
   */
  public boolean isSetResultLevel() {
    return resultLevel!= null;
  }

  /**
   * Returns the resultLevel if it is set, otherwise throw a {@link PropertyUndefinedError}
   * Exception.
   * 
   * @return the resultLevel if it is set.
   * @throws PropertyUndefinedError is {@link #isSetResultLevel()} return false.
   */
  public int getResultLevel() {
    if (isSetResultLevel()) {
      return resultLevel.intValue();
    } else {
      throw new PropertyUndefinedError(QualConstants.resultLevel, this);
    }
  }


  /**
   * The result of a term is described by a resultLevel. This attribute is required. The resultLevel
   * is a non-negative integer describing a level. The resultLevel is used; possibly together
   * with the thresholdLevel or outputLevel to determine the level of a {@link QualitativeSpecies}
   * resulting from the {@link Transition}.
   * 
   * @param resultLevel
   *        the resultLevel to set
   */
  public void setResultLevel(int resultLevel) {
    Integer oldResultLevel = this.resultLevel;
    this.resultLevel = resultLevel;
    firePropertyChange(QualConstants.resultLevel, oldResultLevel, this.resultLevel);
  }


  /**
   * Unsets the resultLevel.
   * 
   * @return {@code true} is the resultLevel was set beforehand.
   */
  public boolean unsetResultLevel() {
    if (isSetResultLevel()) {
      Integer oldResultLevel = resultLevel;
      resultLevel = null;
      firePropertyChange(QualConstants.resultLevel, oldResultLevel, resultLevel);
      return true;
    } else {
      return false;
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      FunctionTerm ft = (FunctionTerm) object;
      equals &= ft.isDefaultTerm() == isDefaultTerm();
      equals &= ft.isSetResultLevel() == isSetResultLevel();
      if (equals && isSetResultLevel()) {
        equals &= (ft.getResultLevel() == getResultLevel());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 953;
    int hashCode = super.hashCode();
    if (isDefaultTerm()) {
      hashCode *= 2;
    }
    if (isSetResultLevel()) {
      hashCode += prime * resultLevel.hashCode();
    }
    return hashCode;
  }


  /**
   * Returns  true if it is a defaultTerm.
   * 
   * @return {@code true} if it is a defaultTerm.
   */
  public boolean isDefaultTerm() {
    return defaultTerm;
  }

  /**
   * Sets if this {@link FunctionTerm} is a defaultTerm. The defaultTerm defines the
   * default result of a {@link Transition}. This term is used if there are no other
   * {@link FunctionTerm} elements or if none of the Math elements of the {@link FunctionTerm}
   * elements evaluates to "true".
   * 
   * @param defaultTerm the defaultTerm to set
   */
  public void setDefaultTerm(boolean defaultTerm) {
    this.defaultTerm = defaultTerm;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(QualConstants.resultLevel)) {
        try {
          setResultLevel(StringTools.parseSBMLInt(value));
        } catch (IllegalArgumentException e) {
          AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
        }
      } else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetResultLevel()) {
      attributes.put(QualConstants.shortLabel + ':' + QualConstants.resultLevel,
        Integer.toString(getResultLevel()));
    }
    return attributes;
  }


}
