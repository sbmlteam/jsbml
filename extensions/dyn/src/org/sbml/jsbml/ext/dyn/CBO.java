/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. Boston University, Boston, MA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.dyn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.biojava3.ontology.Ontology;
import org.biojava3.ontology.io.OboParser;
import org.biojava3.ontology.utils.Annotation;
import org.sbml.jsbml.resources.Resource;
import org.sbml.jsbml.util.StringTools;

/**
 * <p>
 * Methods for interacting with Cell Behavior Ontology (CBO) terms. This class
 * uses the BioJava classes for working with ontologies and contains static
 * classes to represent single {@link Term}s and {@link Triple}s of subject,
 * predicate, and object, where each of these three entities is again an
 * instance of {@link Term}. The classes {@link Term} and {@link Triple}
 * basically wrap the underlying functions from BioJava, but the original
 * {@link Object}s can be accessed via dedicated get methods. Furthermore, the
 * {@link Ontology} from BioJava, which is used in this class, can be obtained
 * using the method {@link #getOntology()}.
 * <p>
 * Cell Behavior Ontology and the cboTerm attribute It is difficult to determine
 * the semantics of Event constructs used to model intrinsic cellular behavior
 * from SBML attributes alone. The id attribute on Event objects allows for
 * unique identification and cross-referencing while the name attribute allows
 * the assignment of human readable labels to Events. Possible values for these
 * attributes are unrestricted so that modelers can choose whichever fits their
 * modeling framework and preference best. However, this means that without any
 * additional human intervention, software tools are unable to discern the
 * semantics of an extended Event element modeling dynamic behavior. For
 * instance, it would be inadvisable to interpret that an Event is modeling the
 * process of cellular death even if the id and name of such Event have the
 * string &ldquo;Cell Death&rdquo; as value. Additionally, as one may need to
 * convert a dynamic Event between different representations (e.g., Cellular
 * PottsModel vs. Center-based off-latticeModel), there is a need to provide a
 * standard, framework-independent, way of associating Event components with
 * given cellular processes. A solution inspired by SBML Level 3 Version 1 Core
 * is to associate model components with terms from carefully curated controlled
 * vocabularies (CVs) such as CBO. This is the purpose of the cboTerm provided
 * through the extended SBase class in this package.
 * 
 * @author Harold Gomez
 * @since 1.0
 * @version $Rev$
 */
public class CBO {
  /**
   * This is a convenient wrapper class for the corresponding implementation
   * of {@link org.biojava.ontology.Term} in BioJava as it provides
   * specialized methods to obtain the information from the CBO OBO file
   * directly and under the same name as the keys are given in that file.
   * 
   * @see org.biojava.ontology.Term
   */
  public static class Term implements Cloneable, Comparable<Term>,
  Serializable {

    /**
     * Generated serial version identifier.
     */
    private static final long serialVersionUID = -2008899191800065887L;

    /**
     * The base properties of this {@link Term}.
     */
    private String id, name, def;

    /**
     * The underlying BioJava {@link org.biojava.ontology.Term}.
     */
    private org.biojava3.ontology.Term term;

    /**
     * Creates a new Term instance.
     * 
     * @param term
     *            a {@link org.biojava.ontology.Term} object
     */
    private Term(org.biojava3.ontology.Term term) {
      if (term == null) {
        throw new NullPointerException("Term must not be null.");
      }

      this.term = term;
      name = term.getName();
      id = "http://cbo.biocomplexity.indiana.edu/svn/cbo/trunk/CBO_1_0.owl#"
          + term.getName();
      def = setDefinition();
    }

    /**
     * Returns a String representing a term the same way as in the OBO file.
     * Ignores is_reflexive and is_transitive
     * 
     * @param term
     *            the term to print
     * @return a String representing a term the same way as in the OBO file.
     */
    public static String printTerm(Term term) {
      StringBuilder sb = new StringBuilder();
      StringTools.append(sb, "[Term]\nid: ", term.getId(), "\nname: ",
        term.getName(), "\ndef: ", term.getDefinition());

      for (Term parent : term.getParentTerms()) {
        StringTools.append(sb, "\nis_a ", parent, " ! ",
          parent.getName());
      }
      return sb.toString();
    }

    @Override
    public Term clone() {
      return new Term(term);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Term term) {
      return getId().compareTo(term.getId());
    }

    /*
     * (non-Javadoc)
     * 
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
     * @return a set of all the children Term.
     */
    public Set<Term> getChildren() {

      Set<Triple> childrenRelationShip = CBO.getTriples(null,
        CBO.getTerm("is_a"), this);
      Set<Term> children = new HashSet<Term>();

      for (Triple triple : childrenRelationShip) {
        children.add(triple.getSubject());
      }

      return children;
    }

    /**
     * Sets the definition of this {@link Term}, which is stored in the
     * corresponding OBO file under the key {@code def}.
     * 
     * @return the definition of this {@link Term}.
     */
    public String setDefinition() {
      Annotation annotation;
      Object definition;
      annotation = term.getAnnotation();
      definition = (annotation != null) && (annotation.keys().size() > 0) ? annotation
        .getProperty("def") : null;
        def = definition != null ? definition.toString() : "";
        def = def.replace("\\n", "\n").replace("\\t", "\t")
            .replace("\\", "");
        def = def.trim();
        return def;
    }

    /**
     * Returns the CBO identifier of this {@link Term}, for instance:
     * 
     * @return the CBO identifier of this {@link Term}
     *
     **/
    public String getId() {
      if (id == null) {
        id = term.getName();
      }
      return id;
    }

    /**
     * Returns the description of this {@link Term}, for instance:
     * 
     * @return the description of this {@link Term}
     *
     **/
    public String getDefinition() {
      if (def == null) {
        def = term.getDescription();
      }
      return def;
    }

    /**
     * Returns the name of this {@link Term}, i.e., a very short
     * characterization.
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

      Set<Triple> parentRelationShip = CBO.getTriples(this,
        CBO.getTerm("is_a"), null);
      Set<Term> parents = new HashSet<Term>();

      for (Triple triple : parentRelationShip) {
        parents.add(triple.getObject());
      }

      return parents;
    }

    /**
     * Grants access to the underlying BioJava
     * {@link org.biojava.ontology.Term}.
     * 
     * @return the underlying BioJava {@link org.biojava.ontology.Term}.
     */
    public org.biojava3.ontology.Term getTerm() {
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return getId();
    }

  }

  /**
   * This is a wrapper class for the corresponding BioJava class
   * {@link org.biojava.ontology.Triple}, to allow for simplified access to
   * the properties of a subject-predicate-object triple in this ontology.
   * 
   * @see org.biojava.ontology.Triple
   */
  public static class Triple implements Cloneable, Comparable<Triple>,
  Serializable {

    /**
     * Generated serial version identifier.
     */
    private static final long serialVersionUID = 8489814012183978884L;

    /**
     * The BioJava {@link org.biojava.ontology.Triple}.
     */
    private org.biojava3.ontology.Triple triple;

    /**
     * Creates a new {@link Triple} from a given corresponding object from
     * BioJava.
     * 
     * @param triple
     */
    private Triple(org.biojava3.ontology.Triple triple) {
      if (triple == null) {
        throw new NullPointerException("Triple must not be null.");
      }
      this.triple = triple;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public Triple clone() {
      return new Triple(triple);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Triple triple) {
      String subject = getSubject() != null ? getSubject().toString()
        : "";
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

    /*
     * (non-Javadoc)
     * 
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
     * {@link org.biojava.ontology.Triple}.
     * 
     * @return the original BioJava {@link org.biojava.ontology.Triple}.
     */
    public org.biojava3.ontology.Triple getTriple() {
      return triple;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("%s %s %s", getSubject(), getPredicate(),
        getObject());
    }
  }

  /**
   * the prefix of all CBO ids.
   */
  private static final String prefix = "http://cbo.biocomplexity.indiana.edu/svn/cbo/trunk/CBO_1_0.owl#";

  /**
   * Ontology file
   */
  private static Ontology cbo;

  /**
   * Used to store converted BioJava terms
   */
  private static Set<Term> terms;

  static {
    System.out.println(Resource.getInstance());
    OboParser parser = new OboParser();
    try {
      String path = "org/sbml/jsbml/ext/dyn/";
      InputStream is = Resource.getInstance()
          .getStreamFromResourceLocation(path + "CBO_OBO.obo");
      cbo = parser.parseOBO(
        new BufferedReader(new InputStreamReader(is)), "CBO",
          "Cell Behavior Ontology");
      // convert between BioJava's Terms and our Terms.
      terms = new HashSet<Term>();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks the format of the given CBO string.
   * 
   * @param cboTerm
   * @return {@code true} if cboTerm is in the correct format (starts with
   *         right header), {@code false} otherwise.
   */
  public static boolean checkTerm(String cboTerm) {
    return (cboTerm.startsWith(prefix));
  }

  /**
   * Grants access to the underlying {@link Ontology} form BioJava.
   * 
   * @return
   */
  public static Ontology getOntology() {
    return cbo;
  }

  /**
   * Gets the CBO term with the id 'cboTerm'.
   * 
   * @jsbml.warning The methods will throw NoSuchElementException if the id is
   *                not found or null.
   * 
   * @param cboTerm
   *            the id of the CBO term to search for.
   * @return the CBO term with the id 'cboTerm'.
   * @throws NoSuchElementException
   *             if the id is not found or null.
   */
  public static Term getTerm(String cboTerm) {
    return new Term(cbo.getTerm(cboTerm));
  }

  /**
   * Return the set of terms of the CBO Ontology.
   * 
   * <p>
   * This methods return only Term object and no Triple object that represent
   * the relationship between terms. If you want to access the full set of
   * {@link org.biojava.ontology.Term} containing also the
   * {@link org.biojava.ontology.Triple}, use {@link CBO#getOntology()} to get
   * the underlying biojava object.
   * 
   * @return the set of terms of the CBO Ontology.
   */
  public static Set<Term> getTerms() {
    if (terms.size() < cbo.getTerms().size()) {
      for (org.biojava3.ontology.Term term : cbo.getTerms()) {
        if (term instanceof org.biojava3.ontology.Term) {
          terms.add(new Term(term));
        }
      }
    }
    return terms;
  }

  /**
   * Returns a set of Triple which match the supplied subject, predicate and
   * object.
   * 
   * <p>
   * If any of the parameters of this method are null, they are treated as
   * wildcards.
   * 
   * @param subject
   *            the subject to search for, or {@code null}.
   * @param predicate
   *            the relationship to search for, or {@code null}.
   * @param object
   *            the object to search for, or {@code null}.
   * @return a set of Triple which match the supplied subject, predicate and
   *         object.
   * 
   * @see org.biojava.ontology.Ontology#getTriples(org.biojava.ontology.Term,
   *      org.biojava.ontology.Term, org.biojava.ontology.Term)
   */
  public static Set<Triple> getTriples(Term subject, Term predicate,
    Term object) {
    Set<Triple> triples = new HashSet<Triple>();
    for (org.biojava3.ontology.Triple triple : cbo.getTriples(
      subject != null ? subject.getTerm() : null,
        object != null ? object.getTerm() : null,
          predicate != null ? predicate.getTerm() : null)) {
      triples.add(new Triple(triple));
    }
    return triples;
  }

  // Test main class
  public static void main(String[] args) {
    int i = 0;
    for (Term term : getTerms()) {
      System.out.println(Term.printTerm(term));
      i++;
    }
    System.out.println("\nThere is " + i + " terms in the CBO ontology.");
    
    System.out.println("Get CellDeath by name = " + Term.printTerm(getTerm("CellDeath")));
    System.out.println("Get CellDeath by id (url) = " + Term.printTerm(getTerm("http://cbo.biocomplexity.indiana.edu/svn/cbo/trunk/CBO_1_0.owl#CellDeath")));

  }
}
