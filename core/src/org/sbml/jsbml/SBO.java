/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

package org.sbml.jsbml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.biojava.bio.Annotation;
import org.biojava.ontology.Ontology;
import org.biojava.ontology.Synonym;
import org.biojava.ontology.io.OboParser;
import org.sbml.jsbml.resources.Resource;
import org.sbml.jsbml.util.StringTools;

/**
 * Methods for interacting with Systems Biology Ontology (SBO) terms. 
 * 
 * <p>This class
 * uses the BioJava classes for working with ontologies and contains static
 * classes to represent single {@link Term}s and {@link Triple}s of subject,
 * predicate, and object, where each of these three entities is again an
 * instance of {@link Term}. The classes {@link Term} and {@link Triple}
 * basically wrap the underlying functions from BioJava, but the original
 * {@link Object}s can be accessed via dedicated get methods. Furthermore, the
 * {@link Ontology} from BioJava, which is used in this class, can also be
 * obtained using the method {@link #getOntology()}.
 * 
 * <p>
 * The values of 'id' attributes on SBML components allow the components to
 * be cross-referenced within a model. The values of 'name' attributes on
 * SBML components provide the opportunity to assign them meaningful labels
 * suitable for display to humans.  The specific identifiers and labels
 * used in a model necessarily must be unrestricted by SBML, so that
 * software and users are free to pick whatever they need.  However, this
 * freedom makes it more difficult for software tools to determine, without
 * additional human intervention, the semantics of models more precisely
 * than the semantics provided by the SBML object classes defined in other
 * sections of this document.  For example, there is nothing inherent in a
 * parameter with identifier <code>k</code> that would indicate to a
 * software tool it is a first-order rate constant (if that's what
 * <code>k</code> happened to be in some given model).  However, one may
 * need to convert a model between different representations (e.g.,
 * Henri-Michaelis-Menten versus elementary steps), or to use it with
 * different modeling approaches (discrete or continuous).  One may also
 * need to relate the model components with other description formats such
 * as SBGN (<a target='_blank'
 * href='http://www.sbgn.org/'>http://www.sbgn.org/</a>) using deeper
 * semantics.  Although an advanced software tool <em>might</em> be able to
 * deduce the semantics of some model components through detailed analysis
 * of the kinetic rate expressions and other parts of the model, this
 * quickly becomes infeasible for any but the simplest of models.
 * <p>
 * An approach to solving this problem is to associate model components
 * with terms from carefully curated controlled vocabularies (CVs).  This
 * is the purpose of the optional 'sboTerm' attribute provided on the SBML
 * class {@link SBase}.  The 'sboTerm' attribute always refers to terms belonging
 * to the Systems Biology Ontology (SBO).
 * <p>
 * <h2>Use of {@link SBO}</h2>
 * <p>
 * Labeling model components with terms from shared controlled vocabularies
 * allows a software tool to identify each component using identifiers that
 * are not tool-specific.  An example of where this is useful is the desire
 * by many software developers to provide users with meaningful names for
 * reaction rate equations.  Software tools with editing interfaces
 * frequently provide these names in menus or lists of choices for users.
 * However, without a standardized set of names or identifiers shared
 * between developers, a given software package cannot reliably interpret
 * the names or identifiers of reactions used in models written by other
 * tools.
 * <p>
 * The first solution that might come to mind is to stipulate that certain
 * common reactions always have the same name (e.g., 'Michaelis-Menten'), but
 * this is simply impossible to do: not only do humans often disagree on
 * the names themselves, but it would not allow for correction of errors or
 * updates to the list of predefined names except by issuing new releases
 * of the SBML specification&mdash;to say nothing of many other limitations
 * with this approach.  Moreover, the parameters and variables that appear
 * in rate expressions also need to be identified in a way that software
 * tools can interpret mechanically, implying that the names of these
 * entities would also need to be regulated.
 * <p>
 * The Systems Biology Ontology (SBO) provides terms for identifying most
 * elements of SBML. The relationship implied by an 'sboTerm' on an SBML
 * model component is <em>is-a</em> between the characteristic of the
 * component meant to be described by SBO on this element and the SBO
 * term identified by the value of the 'sboTerm'. By adding SBO term
 * references on the components of a model, a software tool can provide
 * additional details using independent, shared vocabularies that can
 * enable <em>other</em> software tools to recognize precisely what the
 * component is meant to be.  Those tools can then act on that information.
 * For example, if the SBO identifier <code>'SBO:0000049'</code> is assigned
 * to the concept of 'first-order irreversible mass-action kinetics,
 * continuous framework', and a given {@link KineticLaw} object in a model has an
 * 'sboTerm' attribute with this value, then regardless of the identifier
 * and name given to the reaction itself, a software tool could use this to
 * inform users that the reaction is a first-order irreversible mass-action
 * reaction.  This kind of reverse engineering of the meaning of reactions
 * in a model would be difficult to do otherwise, especially for more
 * complex reaction types.
 * <p>
 * The presence of SBO labels on {@link Compartment}, {@link Species}, and {@link Reaction}
 * objects in SBML can help map those entities to equivalent concepts in
 * other standards, such as (but not limited to) BioPAX (<a target='_blank'
 * href='http://www.biopax.org/'>http://www.biopax.org/</a>), PSI-MI (<a
 * target='_blank'
 * href='http://www.psidev.info/index.php?q=node/60'>http://www.psidev.info</a>),
 * or the Systems Biology Graphical Notation (SBGN, <a target='_blank'
 * href='http://www.sbgn.org/'>http://www.sbgn.org/</a>).  Such mappings
 * can be used in conversion procedures, or to build interfaces, with SBO
 * becoming a kind of 'glue' between standards of representation.
 * <p>
 * The presence of the label on a kinetic expression can also allow
 * software tools to make more intelligent decisions about reaction rate
 * expressions.  For example, an application could recognize certain types
 * of reaction formulas as being ones it knows how to solve with optimized
 * procedures.  The application could then use internal, optimized code
 * implementing the rate formula indexed by identifiers such as
 * <code>'SBO:0000049'</code> appearing in SBML models.
 * <p>
 * Finally, SBO labels may be very valuable when it comes to model
 * integration, by helping identify interfaces, convert mathematical
 * expressions and parameters etc.
 * <p>
 * Although the use of SBO can be beneficial, it is critical to keep in
 * mind that the presence of an 'sboTerm' value on an object <em>must not
 * change the fundamental mathematical meaning</em> of the model.  An SBML
 * model must be defined such that it stands on its own and does not depend
 * on additional information added by SBO terms for a correct mathematical
 * interpretation.  SBO term definitions will not imply any alternative
 * mathematical semantics for any SBML object labeled with that term.  Two
 * important reasons motivate this principle.  First, it would be too
 * limiting to require all software tools to be able to understand the SBO
 * vocabularies in addition to understanding SBML.  Supporting SBO is not
 * only additional work for the software developer; for some kinds of
 * applications, it may not make sense.  If SBO terms on a model are
 * optional, it follows that the SBML model <em>must</em> remain
 * unambiguous and fully interpretable without them, because an application
 * reading the model may ignore the terms.  Second, we believe allowing the
 * use of 'sboTerm' to alter the mathematical meaning of a model would
 * allow too much leeway to shoehorn inconsistent concepts into SBML
 * objects, ultimately reducing the interoperability of the models.
 * <p>
 * <h2>Relationships between {@link SBO} and SBML</h2>
 * <p>
 * The goal of SBO labeling for SBML is to clarify to the fullest extent
 * possible the nature of each element in a model.  The approach taken in
 * SBO begins with a hierarchically-structured set of controlled
 * vocabularies with six main divisions: (1) entity, (2) participant role,
 * (3) quantitative parameter, (4) modeling framework, (5) mathematical
 * expression, and (6) interaction.  The web site for SBO (<a
 * target='_blank'
 * href='http://biomodels.net/sbo'>http://biomodels.net</a>) should be
 * consulted for the current version of the ontology.
 * <p>
 * The Systems Biology Ontology (SBO) is not part of SBML; it is being
 * developed separately, to allow the modeling community to evolve the
 * ontology independently of SBML.  However, the terms in the ontology are
 * being designed keeping SBML components in mind, and are classified into
 * subsets that can be directly related with SBML components such as
 * reaction rate expressions, parameters, and others.  The use of 'sboTerm'
 * attributes is optional, and the presence of 'sboTerm' on an element does
 * not change the way the model is <em>interpreted</em>.  Annotating SBML
 * elements with SBO terms adds additional semantic information that may
 * be used to <em>convert</em> the model into another model, or another
 * format.  Although SBO support provides an important source of
 * information to understand the meaning of a model, software does not need
 * to support 'sboTerm' to be considered SBML-compliant.
 * <p>
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @since 0.8
 * @version $Rev$ 
 */
public class SBO {

	/**
	 * This is a convenient wrapper class for the corresponding implementation
	 * of {@link org.biojava.ontology.Term} in BioJava as it provides
	 * specialized methods to obtain the information from the SBO OBO file
	 * directly and under the same name as the keys are given in that file.
	 * 
	 * @see org.biojava.ontology.Term
	 */
	public static class Term implements Cloneable, Comparable<Term>, Serializable {
		
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
			StringTools.append(sb, "[Term]\nid: ", term.getId(), "\nname: ",
					term.getName(), "\ndef: ", term.getDefinition());
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
		private String id, name, def;
		
		/**
		 * The underlying BioJava {@link org.biojava.ontology.Term}.
		 */
		private org.biojava.ontology.Term term;

		/**
		 * Creates a new Term instance.
		 * 
		 * @param term a {@link org.biojava.ontology.Term} object
		 */
		private Term(org.biojava.ontology.Term term) {
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
			return new Term(this.term);
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
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
		 * Returns the definition of this {@link Term}, which is stored in the
		 * corresponding OBO file under the key <code>def</code>.
		 * 
		 * @return the definition of this {@link Term}.
		 */
		public String getDefinition() {
			if (def == null) {
				Annotation annotation;
				Object definition;
				annotation = this.term.getAnnotation();
				definition = (annotation != null)
						&& (annotation.keys().size() > 0) ? annotation
						.getProperty("def") : null;
				def = definition != null ? definition.toString() : "";
				def = def.replace("\\n", "\n").replace("\\t", "\t").replace("\\", "");
				def = def.trim();
			}
			return def;
		}

		/**
		 * Returns the SBO identifier of this {@link Term}, for instance:
		 * <code>SBO:0000031</code>.
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
				description = this.term.getDescription();
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

			Set<Triple> parentRelationShip = SBO.getTriples(this, SBO.getTerm("is_a"), null);
			Set<Term> parents = new HashSet<Term>();
			
			for (Triple triple : parentRelationShip) {
				parents.add(triple.getObject());
			}
			
			return parents;
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
		 * {@link org.biojava.ontology.Term}.
		 * 
		 * @return the underlying BioJava
		 * {@link org.biojava.ontology.Term}.
		 */
		public org.biojava.ontology.Term getTerm() {
			return this.term;
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

	/**
	 * This is a wrapper class for the corresponding BioJava class
	 * {@link org.biojava.ontology.Triple}, to allow for simplified access to
	 * the properties of a subject-predicate-object triple in this ontology.
	 * 
	 * @see org.biojava.ontology.Triple
	 */
	public static class Triple implements Cloneable, Comparable<Triple>, Serializable {
		
		/**
		 * Generated serial version identifier.
		 */
		private static final long serialVersionUID = 7289048361260650338L;
		
		/**
		 * The BioJava {@link org.biojava.ontology.Triple}.
		 */
		private org.biojava.ontology.Triple triple;

		/**
		 * Creates a new {@link Triple} from a given corresponding object from
		 * BioJava.
		 *  
		 * @param triple
		 */
		private Triple(org.biojava.ontology.Triple triple) {
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
			return new Triple(this.triple);
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
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
		 * {@link org.biojava.ontology.Triple}.
		 * 
		 * @return the original BioJava
		 * {@link org.biojava.ontology.Triple}.
		 */
		public org.biojava.ontology.Triple getTriple() {
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

	/**
	 * 
	 */
	private static Properties alias2sbo;

	/**
	 * the prefix of all SBO ids.
	 */
	private static final String prefix = "SBO:";
	
	/**
	 * 
	 */
	private static Ontology sbo;
	
	/**
	 * 
	 */
	private static Properties sbo2alias;
	
	/**
	 * 
	 */
	private static Set<Term> terms;

	static {
		OboParser parser = new OboParser();
		try {
			String path = "org/sbml/jsbml/resources/cfg/";
			InputStream is = Resource.getInstance()
					.getStreamFromResourceLocation(path + "SBO_OBO.obo");
			sbo = parser.parseOBO(
					new BufferedReader(new InputStreamReader(is)), "SBO",
					"Systems Biology Ontology");
			alias2sbo = Resource.readProperties(path + "Alias2SBO.cfg");
			sbo2alias = new Properties();
			for (Object key : alias2sbo.keySet()) {
				sbo2alias.put(alias2sbo.get(key), key);
			}
			// convert between BioJava's Terms and our Terms.
			terms = new HashSet<Term>();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks the format of the given SBO integer portion.
	 * 
	 * @param sboTerm
	 * @return true if sboTerm is in the range {0,.., 9999999}, false otherwise.
	 */
	public static boolean checkTerm(int sboTerm) {
		return (0 <= sboTerm) && (sboTerm <= 9999999);
	}

	/**
	 * Checks the format of the given SBO string.
	 * 
	 * @param sboTerm
	 * @return true if sboTerm is in the correct format (a zero-padded, seven
	 *         digit string), false otherwise.
	 */
	public static boolean checkTerm(String sboTerm) {
		boolean correct = sboTerm.length() == 11;
		correct &= sboTerm.startsWith(prefix);
		
		if (correct) {
			try {
				int sbo = Integer.parseInt(sboTerm.substring(4));
				correct &= checkTerm(sbo);
			} catch (NumberFormatException nfe) {
				correct = false;
			}
		}
		
		if (!correct) {
			Logger logger = Logger.getLogger(SBO.class);
			logger.warn("The SBO term '" + sboTerm + "' does not seem to be valid.");
		}
		
		return correct;
	}

	/**
	 * Returns an SBO id corresponding to the given alias.
	 * 
	 * @param alias
	 * @return an SBO id corresponding to the given alias.
	 */
	public static int convertAlias2SBO(String alias) {
		Object value = alias2sbo.get(alias);
		return value != null ? Integer.parseInt(value.toString()) : -1;
	}

	/**
	 * Returns an alias corresponding to the given SBO id.
	 * 
	 * @param sboterm
	 * @return an alias corresponding to the given SBO id.
	 */
	public static String convertSBO2Alias(int sboterm) {
		Object value = sbo2alias.get(Integer.toString(sboterm));
		return value != null ? value.toString() : "";
	}

	/**
	 * Returns the SBO id for antisense RNA.
	 * 
	 * @return the SBO id for antisense RNA.
	 */
	public static int getAntisenseRNA() {
		return convertAlias2SBO("ANTISENSE_RNA");
	}

	/**
	 * 
	 * @return
	 */
	public static int getBindingActivator() {
		return 535;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCatalysis() {
		return 13;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCatalyst() {
		return convertAlias2SBO("CATALYSIS");
	}

	/**
	 * 
	 * @return
	 */
	public static int getCatalyticActivator() {
		return 534;
	}

  /**
   * Physical compartment.
   * 
   * @return
   */
  public static int getCompartment() {
    return 290;
  }
	
	/**
	 * 
	 * @return
	 */
	public static int getCompetetiveInhibitor() {
		return 206;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCompleteInhibitor() {
		return 537;
	}

	/**
	 * 
	 * @return
	 */
	public static int getComplex() {
		return convertAlias2SBO("COMPLEX");
	}

	/**
	 * 
	 * @return
	 */
	public static int getConservationLaw() {
		return 355;
	}

	/**
	 * 
	 * @return
	 */
	public static int getContinuousFramework() {
		return 62;
	}

	/**
	 * Creates and returns a list of molecule types accepted as an enzyme by
	 * default. These are:
	 * <ul type="disk">
	 * <li>ANTISENSE_RNA</li>
	 * <li>SIMPLE_MOLECULE</li>
	 * <li>UNKNOWN</li>
	 * <li>COMPLEX</li>
	 * <li>TRUNCATED</li>
	 * <li>GENERIC</li>
	 * <li>RNA</li>
	 * <li>RECEPTOR</li>
	 * </ul>
	 * 
	 * @return
	 */
	public static final Set<Integer> getDefaultPossibleEnzymes() {
		Set<Integer> possibleEnzymes = new HashSet<Integer>();
		for (String type : new String[] { "ANTISENSE_RNA", "SIMPLE_MOLECULE",
				"UNKNOWN", "COMPLEX", "TRUNCATED", "GENERIC", "RNA", "RECEPTOR" })
			possibleEnzymes.add(Integer.valueOf(convertAlias2SBO(type)));
		return possibleEnzymes;
	}

	/**
	 * 
	 * @return
	 */
	public static int getDiscreteFramework() {
		return 63;
	}

	/**
	 * 
	 * @return
	 */
	public static int getDrug() {
		return convertAlias2SBO("DRUG");
	}
	
  /**
   * Empty set is used to represent the source of a creation process or the
   * result of a degradation process.
   * 
   * @return
   */
	public static int getEmptySet() {
		return convertAlias2SBO("DEGRADED");
	}

	/**
	 * 
	 * @return
	 */
	public static int getEntity() {
		return 236;
	}

	/**
	 * 
	 * @return
	 */
	public static int getEnzymaticCatalysis() {
		return 460;
	}

	/**
	 * 
	 * @return
	 */
	public static int getEssentialActivator() {
		return 461;
	}

	/**
	 * 
	 * @return
	 */
	public static int getEvent() {
		return 231;
	}

	/**
	 * 
	 * @return
	 */
	public static int getFunctionalCompartment() {
		return 289;
	}

	/**
	 * 
	 * @return
	 */
	public static int getFunctionalEntity() {
		return 241;
	}

	/**
	 * A gene is an informational molecule segment.
	 * 
   * The Nucleic acid feature construct in SBGN is meant to represent a fragment
   * of a macro- molecule carrying genetic information. A common use for this
   * construct is to represent a gene or transcript. The label of this EPN and
   * its units of information are often important for making the purpose clear
   * to the reader of a map.
   * 
	 * @return
	 */
	public static int getGene() {
		return convertAlias2SBO("GENE");
	}

	/**
	 * 
	 * @return
	 */
	public static int getGeneCodingRegion() {
		return 335;
	}

	/**
	 * 
	 * @return
	 */
	public static int getGeneric() {
		return convertAlias2SBO("GENERIC");
	}

	/**
	 * 
	 * @return
	 */
	public static int getHillEquation() {
		return 192;
	}

	/**
	 * 
	 * @return
	 */
	public static int getInhibitor() {
		return convertAlias2SBO("INHIBITION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getInteraction() {
		return 231;
	}

	/**
	 * 
	 * @return
	 */
	public static int getIon() {
		return convertAlias2SBO("ION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getIonChannel() {
		return convertAlias2SBO("ION_CHANNEL");
	}

	/**
	 * 
	 * @return
	 */
	public static int getKineticConstant() {
		return 9;
	}

	/**
	 * 
	 * @return
	 */
	public static int getLogicalFramework() {
		return 234;
	}

	/**
	 * Returns the SBO term id for macromolecule.
	 * @return
	 */
	public static int getMacromolecule() {
	  return 245;
	}
	  
	/**
	 * 
	 * @return
	 */
	public static int getMaterialEntity() {
		return 240;
	}

	/**
	 * 
	 * @return
	 */
	public static int getMathematicalExpression() {
		return 64;
	}

	/**
	 * 
	 * @return
	 */
	public static int getMessengerRNA() {
		return 278;
	}

	/**
	 * 
	 * @return
	 */
	public static int getModellingFramework() {
		return 4;
	}

	/**
	 * 
	 * @return
	 */
	public static int getModifier() {
		return convertAlias2SBO("MODULATION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getNonCompetetiveInhibitor() {
		return 207;
	}

	/**
   * 
   * @return
   */
  public static int getNonCovalentComplex() {
    return 253;
  }
  
	/**
	 * 
	 * @return
	 */
	public static int getNonEssentialActivator() {
		return 462;
	}

	/**
	 * Grants access to the underlying {@link Ontology} form BioJava.
	 * @return
	 */
	public static Ontology getOntology() {
		return sbo;
	}

	/**
	 * 
	 * @return The SBO term identifier corresponding to the {@link Term} with
	 *         the name "partial inhibitor".
	 */
	public static int getPartialInhibitor() {
		return 536;
	}

	/**
	 * 
	 * @return
	 */
	public static int getParticipant() {
		return 235;
	}

	/**
	 * 
	 * @return
	 */
	public static int getParticipantRole() {
		return 3;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getPhenotype() {
		return convertAlias2SBO("PHENOTYPE");
	}

	/**
	 * 
	 * @return
	 */
	public static int getPhysicalCompartment() {
		return 290;
	}

	/**
	 * 
	 * @return
	 */
	public static int getPhysicalParticipant() {
		return 236;
	}

	/**
	 * Creates and returns a list of molecule types that are accepted as an
	 * enzyme for the given type names.
	 * 
	 * @param types
	 * @return
	 */
	public static final Set<Integer> getPossibleEnzymes(String... types) {
		Set<Integer> possibleEnzymes = new HashSet<Integer>();
		for (String type : types) {
			possibleEnzymes.add(Integer.valueOf(convertAlias2SBO(type)));
		}
		return possibleEnzymes;
	}

	/**
	 * 
	 * @return
	 */
	public static int getProduct() {
		return 11;
	}

	/**
	 * 
	 * @return
	 */
	public static int getProtein() {
		return convertAlias2SBO("PROTEIN");
	}

	/**
	 * 
	 * @return
	 */
	public static int getQuantitativeParameter() {
		return 2;
	}

	/**
	 * 
	 * @return
	 */
	public static int getRateLaw() {
		return 1;
	}

	/**
	 * 
	 * @return
	 */
	public static int getReactant() {
		return 10;
	}

	/**
	 * 
	 * @return
	 */
	public static int getReceptor() {
		return convertAlias2SBO("RECEPTOR");
	}

	/**
	 * 
	 * @return
	 */
	public static int getRNA() {
		return convertAlias2SBO("RNA");
	}

	/**
	 * 
	 * @return
	 */
	public static int getSimpleMolecule() {
		return convertAlias2SBO("SIMPLE_MOLECULE");
	}

	/**
	 * 
	 * @return
	 */
	public static int getSpecificActivator() {
		return 533;
	}

	/**
	 * 
	 * @return
	 */
	public static int getStateTransition() {
		return convertAlias2SBO("STATE_TRANSITION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getSteadyStateExpression() {
		return 391;
	}

	/**
	 * 
	 * @return
	 */
	public static int getStimulator() {
		return convertAlias2SBO("PHYSICAL_STIMULATION");
	}

	/**
	 * Gets the SBO term with the id 'sboTerm'.
	 * 
	 * @jsbml.warning The methods will throw NoSuchElementException if the id is not found.
	 * 
	 * @param sboTerm the id of the SBO term to search for.
	 * @return the SBO term with the id 'sboTerm'.
	 * @throws NoSuchElementException if the id is not found.
	 */
	public static Term getTerm(int sboTerm) {
		return getTerm(intToString(sboTerm));
	}

	/**
	 * Gets the SBO term with the id 'sboTerm'.
	 * 
	 * <p> The id need to be of the form 'SBO:XXXXXXX' where X is a digit number.
	 * 
	 * @jsbml.warning The methods will throw NoSuchElementException if the id is not found or null.
	 * 
	 * @param sboTerm the id of the SBO term to search for.
	 * @return the SBO term with the id 'sboTerm'.
	 * @throws NoSuchElementException if the id is not found or null.
	 */
	public static Term getTerm(String sboTerm) {
		return new Term(sbo.getTerm(sboTerm));
	}

	/**
	 * Returns the root element of the SBO Ontology (SBO:0000000).
	 * 
	 * @return the root element of the SBO Ontology (SBO:0000000).
	 */
	public static Term getSBORoot() {
		return getTerm("SBO:0000000");
	}
	
	/**
	 * Return the set of terms of the SBO Ontology.
	 * 
	 * <p> This methods return only Term object and no Triple object that represent the
	 * relationship between terms. If you want to access the full set of {@link org.biojava.ontology.Term}
	 * containing also the {@link org.biojava.ontology.Triple}, use {@link SBO#getOntology()} 
	 * to get the underlying biojava object.
	 * 
	 * @return the set of terms of the SBO Ontology.
	 */
	public static Set<Term> getTerms() {
		if (terms.size() < sbo.getTerms().size()) {
			for (org.biojava.ontology.Term term : sbo.getTerms()) {
				
				if (term instanceof org.biojava.ontology.Triple) {					
					// does nothing
				} else if (term instanceof org.biojava.ontology.Term) {
					terms.add(new Term(term));
				} 
			}
		}
		return terms;
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRANSCRIPTION'
	 * 
	 * @return the SBO id corresponding to the alias 'TRANSCRIPTION'
	 */
	public static int getTranscription() {
		return convertAlias2SBO("TRANSCRIPTION");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRANSCRIPTIONAL_ACTIVATION'
	 * 
	 * @return the SBO id corresponding to the alias 'TRANSCRIPTIONAL_ACTIVATION'
	 */
	public static int getTranscriptionalActivation() {
		return convertAlias2SBO("TRANSCRIPTIONAL_ACTIVATION");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRANSCRIPTIONAL_INHIBITION'
	 * 
	 * @return the SBO id corresponding to the alias 'TRANSCRIPTIONAL_INHIBITION'
	 */
	public static int getTranscriptionalInhibitor() {
		return convertAlias2SBO("TRANSCRIPTIONAL_INHIBITION");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'KNOWN_TRANSITION_OMITTED'
	 * 
	 * @return the SBO id corresponding to the alias 'KNOWN_TRANSITION_OMITTED'
	 */
	public static int getTransitionOmitted() {
		return convertAlias2SBO("KNOWN_TRANSITION_OMITTED");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRANSLATION'
	 * 
	 * @return the SBO id corresponding to the alias 'TRANSLATION'
	 */
	public static int getTranslation() {
		return convertAlias2SBO("TRANSLATION");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRANSLATIONAL_ACTIVATION'
	 * 
	 * @return the SBO id corresponding to the alias 'TRANSLATIONAL_ACTIVATION'
	 */
	public static int getTranslationalActivation() {
		return convertAlias2SBO("TRANSLATIONAL_ACTIVATION");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRANSLATIONAL_INHIBITION'
	 * 
	 * @return the SBO id corresponding to the alias 'TRANSLATIONAL_INHIBITION'
	 */
	public static int getTranslationalInhibitor() {
		return convertAlias2SBO("TRANSLATIONAL_INHIBITION");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRANSPORT'
	 * 
	 * @return the SBO id corresponding to the alias 'TRANSPORT'
	 */
	public static int getTransport() {
		return convertAlias2SBO("TRANSPORT");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'TRIGGER'
	 * 
	 * @return the SBO id corresponding to the alias 'TRIGGER'
	 */
	public static int getTrigger() {
		return convertAlias2SBO("TRIGGER");
	}

	/**
	 * Returns a set of Triple which match the supplied subject, predicate and object.
	 *  
	 * <p>If any of the parameters of this method are null, they are treated as wildcards.
	 * 
	 * <pre> for example : 
	 *    getTriples(SBO.getTerm("SBO:0000002"), SBO.getTerm("is_a"), null);
	 *    will returned all the parent Terms of SBO:0000002
	 * 
	 *    getTriples(null, SBO.getTerm("is_a"), SBO.getTerm(188));
	 *    will returned all the children Terms of SBO:0000188
	 *  
	 *  <p>
	 * @param subject the subject to search for, or null.
	 * @param predicate the relationship to search for, or null. 
	 * @param object the object to search for, or null.
	 * @return a set of Triple which match the supplied subject, predicate and object.
	 * 
	 * @see org.biojava.ontology.Ontology#getTriples(org.biojava.ontology.Term,
	 *      org.biojava.ontology.Term, org.biojava.ontology.Term)
	 */
	public static Set<Triple> getTriples(Term subject, Term predicate, Term object) {
		Set<Triple> triples = new HashSet<Triple>();
		for (org.biojava.ontology.Triple triple : sbo.getTriples(
				subject != null ? subject.getTerm() : null,
				object != null ? object.getTerm() : null,
				predicate != null ? predicate.getTerm() : null)) {
			triples.add(new Triple(triple));
		}
		return triples;
	}
	
	/**
	 * Returns the SBO id corresponding to the alias 'TRUNCATED'
	 * 
	 * @return the SBO id corresponding to the alias 'TRUNCATED'
	 */
	public static int getTruncated() {
		return convertAlias2SBO("TRUNCATED");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'UNKNOWN'
	 * 
	 * @return the SBO id corresponding to the alias 'UNKNOWN'
	 */
	public static int getUnknownMolecule() {
		return convertAlias2SBO("UNKNOWN");
	}

	/**
	 * Returns the SBO id corresponding to the alias 'UNKNOWN_TRANSITION'
	 * 
	 * @return the SBO id corresponding to the alias 'UNKNOWN_TRANSITION'
	 */
	public static int getUnknownTransition() {
		return convertAlias2SBO("UNKNOWN_TRANSITION");
	}

	/**
	 * Returns the integer as a correctly formatted SBO string. If the sboTerm
	 * is not in the correct range ({0,.., 9999999}), an empty string is
	 * returned.
	 * 
	 * @param sboTerm
	 * @return the given integer sboTerm as a zero-padded seven digit string.
	 */
	public static String intToString(int sboTerm) {
		if (!checkTerm(sboTerm)) {
			return "";
		}
		return StringTools.concat(prefix, sboNumberString(sboTerm)).toString();
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isAntisenseRNA(int sboTerm) {
		return isChildOf(sboTerm, getAntisenseRNA());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isBindingActivator(int sboTerm) {
		return isChildOf(sboTerm, getBindingActivator());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isCatalyst(int sboTerm) {
		return isChildOf(sboTerm, getCatalyst());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isCatalyticActivator(int sboTerm) {
		return isChildOf(sboTerm, getCatalyticActivator());
	}

	/**
	 * Checks whether the given sboTerm is a member of the SBO subgraph rooted
	 * at parent.
	 * 
	 * @param sboTerm
	 *            An SBO term.
	 * @param parent
	 *            An SBO term that is the root of a certain subgraph within the
	 *            SBO.
	 * @return true if the subgraph of the SBO rooted at the term parent
	 *         contains a term with the id corresponding to sboTerm.
	 */
	public static boolean isChildOf(int sboTerm, int parent) {
		if (!checkTerm(sboTerm)) {
			return false;
		}
		return isChildOf(new Term(sbo.getTerm(intToString(sboTerm))), new Term(sbo
				.getTerm(intToString(parent))));
	}

	/**
	 * Traverses the systems biology ontology starting at {@link Term} subject until
	 * either the root (SBO:0000000) or the {@link Term} object is reached.
	 * 
	 * @param subject
	 *            Child
	 * @param object
	 *            Parent
	 * @return true if subject is a child of object.
	 */
	private static boolean isChildOf(Term subject, Term object) {
		if (subject.equals(object)) {
			return true;
		}
		Set<org.biojava.ontology.Triple> relations = sbo.getTriples(
				subject != null ? subject.getTerm() : null, null, null);
		for (org.biojava.ontology.Triple triple : relations) {
			if (triple.getObject().equals(object.getTerm())) {
				return true;
			}
			if (isChildOf(new Term(triple.getObject()), object)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isCompetetiveInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getCompetetiveInhibitor());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isCompleteInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getCompleteInhibitor());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isComplex(int sboTerm) {
		return isChildOf(sboTerm, getComplex());
	}

	/**
	 * Returns true if the term is-a conservation law, false otherwise
	 * 
	 * @param sboTerm
	 * @return true if the term is-a conservation law, false otherwise
	 */
	public static boolean isConservationLaw(int sboTerm) {
		return isChildOf(sboTerm, getConservationLaw());
	}

	/**
	 * Returns true if the term is-a continuous framework, false otherwise
	 * 
	 * @param sboTerm
	 * @return true if the term is-a continuous framework, false otherwise
	 */
	public static boolean isContinuousFramework(int sboTerm) {
		return isChildOf(sboTerm, getContinuousFramework());
	}

	/**
	 * Returns true if the term is-a discrete framework, false otherwise
	 * 
	 * @param sboTerm
	 * @return true if the term is-a discrete framework, false otherwise
	 */
	public static boolean isDiscreteFramework(int sboTerm) {
		return isChildOf(sboTerm, getDiscreteFramework());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isDrug(int sboTerm) {
		return isChildOf(sboTerm, getDrug());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isEmptySet(int sboTerm) {
		return isChildOf(sboTerm, getEmptySet());
	}

	/**
	 * Returns true if the term is-a Entity, false otherwise
	 * 
	 * @param sboTerm
	 * @return true if the term is-a Entity, false otherwise
	 */
	public static boolean isEntity(int sboTerm) {
		return isChildOf(sboTerm, getEntity());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isEnzymaticCatalysis(int sboTerm) {
		return isChildOf(sboTerm, getEnzymaticCatalysis());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isEssentialActivator(int sboTerm) {
		return isChildOf(sboTerm, getEssentialActivator());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-an Event, false otherwise
	 */
	public static boolean isEvent(int sboTerm) {
		return isChildOf(sboTerm, getEvent());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a functional compartment, false otherwise
	 */
	public static boolean isFunctionalCompartment(int sboTerm) {
		return isChildOf(sboTerm, getFunctionalCompartment());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a functional entity, false otherwise
	 */
	public static boolean isFunctionalEntity(int sboTerm) {
		return isChildOf(sboTerm, getFunctionalEntity());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isGene(int sboTerm) {
		return isChildOf(sboTerm, getGene());
	}

	/**
	 * Returns true if the sboTerm stands for a gene coding region, false
	 *         otherwise
	 *         
	 * @param sboTerm
	 * @return true if the sboTerm stands for a gene coding region, false
	 *         otherwise
	 */
	public static boolean isGeneCodingRegion(int sboTerm) {
		return isChildOf(sboTerm, getGeneCodingRegion());
	}

	/**
	 * Returns true if the sboTerm stands for a gene coding region or a gene,
	 *         false otherwise
	 * 
	 * @param sboTerm
	 * @return true if the sboTerm stands for a gene coding region or a gene,
	 *         false otherwise
	 */
	public static boolean isGeneOrGeneCodingRegion(int sboTerm) {
		return isGene(sboTerm) || isGeneCodingRegion(sboTerm);
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isGeneric(int sboTerm) {
		return isChildOf(sboTerm, getGeneric());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isHillEquation(int sboTerm) {
		return isChildOf(sboTerm, getHillEquation());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getInhibitor());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-an interaction, false otherwise
	 */
	public static boolean isInteraction(int sboTerm) {
		return isChildOf(sboTerm, getInteraction());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isIon(int sboTerm) {
		return isChildOf(sboTerm, getIon());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isIonChannel(int sboTerm) {
		return isChildOf(sboTerm, getIonChannel());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a kinetic constant, false otherwise
	 */
	public static boolean isKineticConstant(int sboTerm) {
		return isChildOf(sboTerm, getKineticConstant());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a logical framework, false otherwise
	 */
	public static boolean isLogicalFramework(int sboTerm) {
		return isChildOf(sboTerm, getLogicalFramework());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a material entity, false otherwise
	 */
	public static boolean isMaterialEntity(int sboTerm) {
		return isChildOf(sboTerm, getMaterialEntity());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a mathematical expression, false otherwise
	 */
	public static boolean isMathematicalExpression(int sboTerm) {
		return isChildOf(sboTerm, getMathematicalExpression());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isMessengerRNA(int sboTerm) {
		return isChildOf(sboTerm, getMessengerRNA());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a modelling framework, false otherwise
	 */
	public static boolean isModellingFramework(int sboTerm) {
		return isChildOf(sboTerm, getModellingFramework());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a modifier, false otherwise
	 */
	public static boolean isModifier(int sboTerm) {
		return isChildOf(sboTerm, getModifier());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isNonCompetetiveInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getNonCompetetiveInhibitor());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isNonEssentialActivator(int sboTerm) {
		return isChildOf(sboTerm, getNonEssentialActivator());
	}

	/**
	 * Function for checking whether the SBO term is obsolete.
	 * 
	 * @param sboTerm
	 * @return true if the term is-an obsolete term, false otherwise
	 */
	public static boolean isObsolete(int sboTerm) {
		return getTerm(intToString(sboTerm)).isObsolete();
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isPartialInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getPartialInhibitor());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO. This term
	 * is actually obsolete.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a participant, false otherwise
	 */
	public static boolean isParticipant(int sboTerm) {
		return isChildOf(sboTerm, getParticipant());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a participant role, false otherwise
	 */
	public static boolean isParticipantRole(int sboTerm) {
		return isChildOf(sboTerm, getParticipantRole());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isPhenotype(int sboTerm) {
		return isChildOf(sboTerm, getPhenotype());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isPhysicalCompartment(int sboTerm) {
		return isChildOf(sboTerm, getPhysicalCompartment());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO. Obsolete
	 * term.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a physical participant, false otherwise
	 */
	public static boolean isPhysicalParticipant(int sboTerm) {
		return isChildOf(sboTerm, getPhysicalParticipant());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a product, false otherwise
	 */
	public static boolean isProduct(int sboTerm) {
		return isChildOf(sboTerm, getProduct());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isProtein(int sboTerm) {
		return isChildOf(sboTerm, getProtein());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a quantitative parameter, false otherwise
	 */
	public static boolean isQuantitativeParameter(int sboTerm) {
		return isChildOf(sboTerm, getQuantitativeParameter());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a rate law, false otherwise
	 */
	public static boolean isRateLaw(int sboTerm) {
		return isChildOf(sboTerm, getRateLaw());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a reactant, false otherwise
	 */
	public static boolean isReactant(int sboTerm) {
		return isChildOf(sboTerm, getReactant());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isReceptor(int sboTerm) {
		return isChildOf(sboTerm, getReceptor());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isRNA(int sboTerm) {
		return isChildOf(sboTerm, getRNA());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isRNAOrMessengerRNA(int sboTerm) {
		return isRNA(sboTerm) || isMessengerRNA(sboTerm);
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isSimpleMolecule(int sboTerm) {
		return isChildOf(sboTerm, getSimpleMolecule());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isSpecificActivator(int sboTerm) {
		return isChildOf(sboTerm, getSpecificActivator());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isStateTransition(int sboTerm) {
		return isChildOf(sboTerm, getStateTransition());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a steady state expression, false otherwise
	 */
	public static boolean isSteadyStateExpression(int sboTerm) {
		return isChildOf(sboTerm, getSteadyStateExpression());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isStimulator(int sboTerm) {
		return isChildOf(sboTerm, getStimulator());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTranscription(int sboTerm) {
		return isChildOf(sboTerm, getTranscription());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTranscriptionalActivation(int sboTerm) {
		return isChildOf(sboTerm, getTranscriptionalActivation());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTranscriptionalInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getTranscriptionalInhibitor());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTransitionOmitted(int sboTerm) {
		return isChildOf(sboTerm, getTransitionOmitted());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTranslation(int sboTerm) {
		return isChildOf(sboTerm, getTranslation());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTranslationalActivation(int sboTerm) {
		return isChildOf(sboTerm, getTranslationalActivation());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTranslationalInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getTranslationalInhibitor());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTransport(int sboTerm) {
		return isChildOf(sboTerm, getTransport());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTrigger(int sboTerm) {
		return isChildOf(sboTerm, getTrigger());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isTruncated(int sboTerm) {
		return isChildOf(sboTerm, getTruncated());
	}

	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isUnknownMolecule(int sboTerm) {
		return isChildOf(sboTerm, getUnknownMolecule());
	}
	
	/**
	 * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
	 * <p>
	 * @param sboTerm
	 * @return {@code true} if <code>term</code> is-a SBO <em>'product'</em>, {@code false} otherwise.
	 */
	public static boolean isUnknownTransition(int sboTerm) {
		return isChildOf(sboTerm, getUnknownTransition());
	}
		
	/**
	 * Tests method.
	 * 
	 * @param args no argument are processed.
	 */
	public static void main(String[] args) {

		int i = 0; 
		for (Term term : getTerms()) {
			if (!term.isObsolete()) {
				System.out.printf("%s\n\n", Term.printTerm(term));
				i++;
			}
		}
		System.out.println("\nThere is " + i + " terms in the SBO ontology.");
		
		System.out.println("\nPrinting the SBO ontology tree :");
		Term sboRoot = SBO.getSBORoot();
		
		for (Term term : sboRoot.getChildren()) {
			System.out.println("   " + term.getId() + " - " + term.getName());
			
			printChildren(term, "   ");
		}
		
		System.out.println("ANTISENSE_RNA = " + SBO.getAntisenseRNA());
		
		// TODO : Shall we catch the exception thrown by the biojava getTerm() method or let it like that ??
		System.out.println("Search null Term : " + SBO.getTerm(null));
		System.out.println("Search invalid id Term : " + SBO.getTerm("SBO:004"));
		System.out.println("Search invalid id Term : " + SBO.getTerm("0000004"));
	}

	
	/**
	 * Prints to the console all the sub-tree of terms, starting from the children
	 * of the given parent term.
	 * 
	 * @param parent the parent term
	 * @param indent the indentation to use to print the children terms
	 */
	private static void printChildren(Term parent, String indent) {
		for (Term term : parent.getChildren()) {
			System.out.println(indent + "   " + term.getId() + " - " + term.getName());
			printChildren(term, indent + "   ");
		}		
	}
	
	/**
	 * Creates and returns a 7 digit SBO number for the given {@link Term} identifier (if
	 * this is a valid identifier). The returned {@link String} will not contain the
	 * SBO prefix.
	 * 
	 * @param sboTerm
	 * @return a 7 digit SBO number for the given {@link Term} identifier (if
	 * this is a valid identifier). The returned {@link String} will not contain the
	 * SBO prefix.
	 * @throws IllegalArgumentException if the given value is no valid SBO term number.
	 */
	public static String sboNumberString(int sboTerm) {
		if (!checkTerm(sboTerm)) {
			throw new IllegalArgumentException("Illegal sboTerm " + sboTerm);
		}
		return StringTools.leadingZeros(7, sboTerm);
	}
	
	/**
	 * Returns the string as a correctly formatted SBO integer portion.
	 * 
	 * @param sboTerm
	 * @return the given string sboTerm as an integer. If the sboTerm is not in
	 *         the correct format (a zero-padded, seven digit string), -1 is
	 *         returned.
	 */
	public static int stringToInt(String sboTerm) {
		return checkTerm(sboTerm) ? Integer.parseInt(sboTerm.substring(4)) : -1;
	}

}
