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
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.biojava.nbio.ontology.Synonym;
import org.biojava.nbio.ontology.utils.Annotation;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.util.StringTools;

/**
 * This is a convenient wrapper class for the corresponding implementation
 * of {@link org.biojava.nbio.ontology.Term} in BioJava as it provides
 * specialized methods to obtain the information from the SBO OBO file
 * directly and under the same name as the keys are given in that file.
 * 
 * @see org.biojava.nbio.ontology.Term
 */
public class Term implements Cloneable, Comparable<Term>, Serializable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2753964994128680208L;

  /**
   * Returns a String representing a term the same way as in the OBO file.
   * 
   * @param term the term to print
   * @return a String representing a term the same way as in the OBO file.
   */
  public static String printTerm(Term term) {
    StringBuilder sb = new StringBuilder();
    StringTools.append(sb, "[Term]\nid: ", term.getId(), "\nname: '",
      term.getName(), "'\ndef: ", term.getDefinition());
    if (term.isObsolete()) {
      sb.append("\nis_obsolete: true");
    }
    for (Object synonym : term.getSynonyms()) {
      sb.append("\nsynonym: ");
      if (synonym instanceof Synonym) {
        Synonym s = (Synonym) synonym;
        StringTools.append(sb, "\"", s.getName(), "\" [");
        if (s.getCategory() != null) {
          sb.append(s.getCategory());
        }
        sb.append(']');
      } else {
        sb.append(synonym);
      }
    }
    for (Term parent : term.getParentTerms()) {
      StringTools.append(sb, "\nis_a ", parent, " ! ", parent.getName());
    }

    return sb.toString();
  }

  /**
   * The base properties of this {@link Term}.
   */
  private String id;
  /**
   * The base properties of this {@link Term}.
   */
  private String name;
  /**
   * The base properties of this {@link Term}.
   */
  private String def;

  /**
   * The underlying BioJava {@link Term}.
   */
  private org.biojava.nbio.ontology.Term term;

  /**
   * Creates a new Term instance.
   * 
   * @param term a {@link org.biojava.nbio.ontology.Term} object
   */
  public Term(org.biojava.nbio.ontology.Term term) {
    if (term == null) {
      throw new NullPointerException("Term must not be null.");
    }

    this.term = term;

    id = name = def = null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Term clone() {
    return new Term(term);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Term term) {
    return getId().compareTo(term.getId());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Term) {
      Term term = (Term) o;
      return (term.getId() != null) && term.getId().equals(getId());
    }
    return false;
  }

  /**
   * Returns a set of all the children Term.
   * 
   * @return  a set of all the children Term.
   */
  public Set<Term> getChildren() {

    Set<Triple> childrenRelationShip = SBO.getTriples(null, SBO.getTerm("is_a"), this);
    Set<Term> children = new HashSet<Term>();

    for (Triple triple : childrenRelationShip) {
      children.add(triple.getSubject());
    }

    return children;
  }

  /**
   * Returns the definition of this {@link Term}, which is stored in the
   * corresponding OBO file under the key {@code def}.
   * 
   * @return the definition of this {@link Term}.
   */
  public String getDefinition() {
    if (def == null) {
      Annotation annotation;
      Object definition;
      annotation = term.getAnnotation();
      try {
      definition = (annotation != null)
          && (annotation.keys().size() > 0) ? annotation
            .getProperty("def") : null;
            def = definition != null ? definition.toString() : "";
            def = def.replace("\\n", "\n").replace("\\t", "\t").replace("\\", "");
            def = def.trim();
      } catch (NoSuchElementException e) {
        def = "";
      }
    }
    return def;
  }

  /**
   * Returns the SBO identifier of this {@link Term}, for instance:
   * {@code SBO:0000031}.
   * 
   * @return the SBO identifier of this {@link Term}
   */
  public String getId() {
    if (id == null) {
      id = term.getName();
    }
    return id;
  }

  /**
   * Returns the name of this {@link Term}, i.e., a very short characterization.
   * 
   * @return the name of this {@link Term}
   */
  public String getName() {
    if (name == null) {
      String description;
      description = term.getDescription();
      name = description != null ? description.replace("\\n", "\n")
        .replace("\\,", ",").trim() : "";
    }
    return name;
  }


  /**
   * Returns the parent Terms.
   * 
   * @return the parent Terms.
   * 
   */
  public Set<Term> getParentTerms() {

    Set<Term> parents = new HashSet<Term>();

    for (org.biojava.nbio.ontology.Triple triple : term.getOntology().getTriples(term, null, term.getOntology().getTerm("is_a")))
    {
      parents.add(new Term(triple.getObject()));
    }

    return parents;
  }

  /**
   * Returns all {@link Synonym}s of this {@link Term}.
   * 
   * @return all {@link Synonym}s of this {@link Term}. Returns
   * an empty array if no {@link Synonym}s exist for this term,
   * but never null.
   */
  public Synonym[] getSynonyms() {
    Object synonyms[] = term.getSynonyms();
    if ((synonyms == null) || (synonyms.length == 0)) {
      return new Synonym[0];
    }
    Synonym syn[] = new Synonym[synonyms.length];
    for (int i = 0; i < synonyms.length; i++) {
      syn[i] = (Synonym) synonyms[i];
    }
    return syn;
  }

  /**
   * Grants access to the underlying BioJava
   * {@link org.biojava3.ontology.Term}.
   * 
   * @return the underlying BioJava
   * {@link org.biojava3.ontology.Term}.
   */
  public org.biojava.nbio.ontology.Term getTerm() {
    return term;
  }

  /**
   * Checks whether or not this {@link Term} is obsolete.
   * 
   * @return whether or not this {@link Term} is obsolete.
   */
  public boolean isObsolete() {
    if (term.getAnnotation().keys().contains("is_obsolete")) {
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getId();
  }

}