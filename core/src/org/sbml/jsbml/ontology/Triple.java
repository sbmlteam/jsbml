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

package org.sbml.jsbml.ontology;

import java.io.Serializable;


/**
 * This is a wrapper class for the corresponding BioJava class
 * {@link org.biojava.nbio.ontology.Triple}, to allow for simplified access to
 * the properties of a subject-predicate-object triple in this ontology.
 * 
 * @see org.biojava.nbio.ontology.Triple
 */
public class Triple implements Cloneable, Comparable<Triple>, Serializable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7289048361260650338L;

  /**
   * The BioJava {@link org.biojava.nbio.ontology.Triple}.
   */
  private org.biojava.nbio.ontology.Triple triple;

  /**
   * Creates a new {@link Triple} from a given corresponding object from
   * BioJava.
   * 
   * @param triple
   */
  public Triple(org.biojava.nbio.ontology.Triple triple) {
    if (triple == null) {
      throw new NullPointerException("Triple must not be null.");
    }
    this.triple = triple;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Triple clone() {
    return new Triple(triple);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Triple triple) {
    String subject = getSubject() != null ? getSubject().toString() : "";
    String predicate = getPredicate() != null ? getPredicate()
      .toString() : "";
      String object = getObject() != null ? getObject().toString() : "";
      String tSub = triple.getSubject() != null ? triple.getSubject()
        .toString() : "";
        String tPred = triple.getPredicate() != null ? triple
          .getPredicate().toString() : "";
          String tObj = triple.getObject() != null ? triple.getObject()
            .toString() : "";
            int compare = subject.compareTo(tSub);
            if (compare != 0) {
              return compare;
            }
            compare = predicate.compareTo(tPred);
            if (compare != 0) {
              return compare;
            }
            compare = object.compareTo(tObj);
            return compare;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Triple) {
      return getTriple().equals(((Triple) o).getTriple());
    }
    return false;
  }

  /**
   * Returns the object of this {@link Triple}.
   * 
   * @return the object of this {@link Triple}.
   */
  public Term getObject() {
    return new Term(triple.getObject());
  }

  /**
   * Returns the predicate of this {@link Triple}.
   * 
   * @return the predicate of this {@link Triple}.
   */
  public Term getPredicate() {
    return new Term(triple.getPredicate());
  }

  /**
   * Returns the subject of this {@link Triple}.
   * 
   * @return the subject of this {@link Triple}.
   */
  public Term getSubject() {
    return new Term(triple.getSubject());
  }

  /**
   * Grants access to the original BioJava
   * {@link org.biojava.nbio.ontology.Triple}.
   * 
   * @return the original BioJava
   * {@link org.biojava.nbio.ontology.Triple}.
   */
  public org.biojava.nbio.ontology.Triple getTriple() {
    return triple;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s %s %s", getSubject(), getPredicate(),
      getObject());
  }

}