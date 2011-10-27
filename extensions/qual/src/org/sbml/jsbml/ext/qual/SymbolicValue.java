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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class SymbolicValue extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -214835834453944834L;
  private Integer           rank;


  public boolean isIdMandatory() {
    return true;
  }


  @Override
  public AbstractSBase clone() {
    return null;
  }


  /**
   * @return false
   */
  public boolean isRankMandatory() {
    return false;
  }


  /**
   * @return the rank
   */
  public int getRank() {
    if (isSetRank()) {
      return rank.intValue();
    }
    throw new PropertyUndefinedError(QualChangeEvent.rank, this);
  }


  public boolean isSetRank() {
    return this.rank != null;
  }


  /**
   * @param rank
   *        the rank to set
   */
  public void setRank(int rank) {
    Integer oldRank = this.rank;
    this.rank = rank;
    firePropertyChange(QualChangeEvent.rank, oldRank, this.rank);
  }


  /**
   * @return true if unset rank attribute was successful
   */
  public boolean unsetRank() {
    if (isSetRank()) {
      Integer oldRank = this.rank;
      this.rank = null;
      firePropertyChange(QualChangeEvent.rank, oldRank, this.rank);
      return true;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      SymbolicValue sv = (SymbolicValue) object;
      equals &= sv.isSetRank() == isSetRank();
      if (equals && isSetRank()) {
        equals &= (sv.getRank() == (getRank()));
      }
    }
    return equals;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;
    int hashCode = super.hashCode();
    if (isSetRank()) {
      hashCode += prime * getRank();
    }
    return hashCode;
  }
}
