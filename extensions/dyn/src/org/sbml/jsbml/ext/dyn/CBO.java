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
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.biojava.nbio.ontology.Ontology;
import org.biojava.nbio.ontology.io.OboParser;
import org.sbml.jsbml.ontology.Term;
import org.sbml.jsbml.ontology.Triple;
import org.sbml.jsbml.resources.Resource;

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
 * @author Harold G&oacute;mez
 * @since 1.0
 */
public class CBO {
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
   * Return the set of terms of the CBO.
   * 
   * <p>
   * This methods return only Term object and no Triple object that represent
   * the relationship between terms. If you want to access the full set of
   * {@link org.biojava.nbio.ontology.Term} containing also the
   * {@link org.biojava.nbio.ontology.Triple}, use {@link CBO#getOntology()} to get
   * the underlying biojava object.
   * 
   * @return the set of terms of the CBO.
   */
  public static Set<Term> getTerms() {
    if (terms.size() < cbo.getTerms().size()) {
      for (org.biojava.nbio.ontology.Term term : cbo.getTerms()) {
        if (term instanceof org.biojava.nbio.ontology.Term) {
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
   * @see org.biojava.nbio.ontology.Ontology#getTriples(org.biojava.nbio.ontology.Term,
   *      org.biojava.nbio.ontology.Term, org.biojava.nbio.ontology.Term)
   */
  public static Set<Triple> getTriples(Term subject, Term predicate,
    Term object) {
    Set<Triple> triples = new HashSet<Triple>();
    for (org.biojava.nbio.ontology.Triple triple : cbo.getTriples(
      subject != null ? subject.getTerm() : null,
        object != null ? object.getTerm() : null,
          predicate != null ? predicate.getTerm() : null)) {
      triples.add(new Triple(triple));
    }
    return triples;
  }

  /**
   * Test main class
   * 
   * @param args
   */
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
