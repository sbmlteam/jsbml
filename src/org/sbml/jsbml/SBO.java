/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import org.biojava.bio.Annotation;
import org.biojava.ontology.Ontology;
import org.biojava.ontology.Synonym;
import org.biojava.ontology.io.OboParser;
import org.sbml.jsbml.resources.Resource;
import org.sbml.jsbml.util.StringTools;

/**
 * Methods for interacting with Systems Biology Ontology (SBO) terms. This class
 * uses the BioJava classes for working with ontologies and contains static
 * classes to represent single {@link Term}s and {@link Triple}s of subject,
 * predicate, and object, where each of these three entities is again an
 * instance of {@link Term}. The classes {@link Term} and {@link Triple}
 * basically wrap the underlying functions from BioJava, but the original
 * {@link Object}s can be accessed via dedicated get methods. Furthermore, the
 * {@link Ontology} from BioJava, which is used in this class, can also be
 * obtained using the method {@link #getOntology()}.
 * 
 * @author Andreas Dr&auml;ger
 */
public class SBO {

	/**
	 * This is a convenient wrapper class for the corresponding implementation
	 * of {@link org.biojava.ontology.Term} in BioJava as it provides
	 * specialized methods to obtain the information from the SBO OBO file
	 * directly and under the same name as the keys are given in that file.
	 * 
	 * @author Andreas Dr&auml;ger
	 * @date 2010-12-12
	 * @see org.biojava.ontology.Term
	 */
	public static class Term {
		
		/**
		 * The base properties of this {@link Term}.
		 */
		private String id, name, def;
		
		/**
		 * The underlying BioJava {@link org.biojava.ontology.Term}.
		 */
		private org.biojava.ontology.Term term;

		/**
		 * 
		 * @param term
		 */
		private Term(org.biojava.ontology.Term term) {
			if (term == null) {
				throw new NullPointerException("Term must not be null.");
			}
			if (term instanceof org.biojava.ontology.Triple) {
				org.biojava.ontology.Triple triple = (org.biojava.ontology.Triple) term;
				this.term = triple.getSubject();
			} else {
				this.term = term;
			}
			id = name = def = null;
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
		 * The definition of this {@link Term}, which is stored in the
		 * corresponding OBO file under the key <code>def</code>.
		 * 
		 * @return
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
				def = def.replace("\\n", " ").replace("\\", "");
				def = def.trim();
			}
			return def;
		}

		/**
		 * The SBO identifier of this {@link Term}, for instance:
		 * <code>SBO:0000031</code>.
		 * 
		 * @return
		 */
		public String getId() {
			if (id == null) {
				id = this.term.toString().split(" ")[0];
			}
			return id;
		}

		/**
		 * The name of this {@link Term}, i.e., a very short characterization.
		 * 
		 * @return
		 */
		public String getName() {
			if (name == null) {
				String description;
				description = this.term.getDescription();
				name = description != null ? description.replace("\\n", " ")
						.replace("\\,", ",").trim() : "";
			}
			return name;
		}

		/**
		 * Creates and returns a set of {@link Triple}s, in which this
		 * {@link Term} is the subject and the predicate is always
		 * <code>is_a</code>. The object of each such {@link Triple} will be one
		 * of this {@link Term}'s parents.
		 * 
		 * @return
		 * @see SBO#getTriples(Term, Term, Term)
		 */
		public Set<Triple> getParents() {
			return SBO.getTriples(this, SBO.getTerm("is_a"), null);
		}

		/**
		 * Access to all {@link Synonym}s of this {@link Term}.
		 * 
		 * @return an empty array if no {@link Synonym}s exist for this term,
		 *         but never null.
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
		 * @return
		 */
		public org.biojava.ontology.Term getTerm() {
			return this.term;
		}

		/**
		 * Checks whether or not this {@link Term} is obsolete.
		 * 
		 * @return
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

		/**
		 * 
		 * @param complete
		 * @return
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
			for (Triple triple : term.getParents()) {
				StringTools.append(sb, "\n", triple.getPredicate(), " ", triple
						.getObject(), " ! ", triple.getObject().getName());
			}
			return sb.toString();
		}
	}

	/**
	 * This is a wrapper class for the corresponding BioJava class
	 * {@link org.biojava.ontology.Triple}, to allow for simplified access to
	 * the properties of a subject-predicate-object triple in this ontology.
	 * 
	 * @author Andreas Dr&auml;ger
	 * @date 2010-12-12
	 * @see org.biojava.ontology.Triple
	 */
	public static class Triple {
		
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
		 * The object of this {@link Triple}.
		 * 
		 * @return
		 */
		public Term getObject() {
			return new Term(triple.getObject());
		}

		/**
		 * The predicate of this {@link Triple}.
		 *  
		 * @return
		 */
		public Term getPredicate() {
			return new Term(triple.getPredicate());
		}

		/**
		 * The subject of this {@link Triple}.
		 * 
		 * @return
		 */
		public Term getSubject() {
			return new Term(triple.getSubject());
		}

		/**
		 * Grants access to the original BioJava
		 * {@link org.biojava.ontology.Triple}.
		 * 
		 * @return
		 */
		public org.biojava.ontology.Triple getTriple() {
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
	 * 
	 */
	private static Properties alias2sbo;

	/**
	 * 
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
		if (correct)
			try {
				int sbo = Integer.parseInt(sboTerm.substring(4));
				correct &= checkTerm(sbo);
			} catch (NumberFormatException nfe) {
				correct = false;
			}
		return correct;
	}

	/**
	 * 
	 * @param aliasType
	 * @return
	 */
	public static int convertAlias2SBO(String aliasType) {
		Object value = alias2sbo.get(aliasType);
		return value != null ? Integer.parseInt(value.toString()) : -1;
	}

	/**
	 * 
	 * @param sboterm
	 * @return
	 */
	public static String convertSBO2Alias(int sboterm) {
		Object value = sbo2alias.get(Integer.toString(sboterm));
		return value != null ? value.toString() : "";
	}

	/**
	 * The SBO term for antisense RNA.
	 * 
	 * @return
	 */
	public static int getAntisenseRNA() {
		return convertAlias2SBO("ANTISENSERNA");
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
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static Term getTerm(int sboTerm) {
		return getTerm(intToString(sboTerm));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static Term getTerm(String sboTerm) {
		return new Term(sbo.getTerm(sboTerm));
	}

	/**
	 * 
	 * @return
	 */
	public static Set<Term> getTerms() {
		if (terms.size() < sbo.getTerms().size()) {
			for (org.biojava.ontology.Term term : sbo.getTerms()) {
				terms.add(new Term(term));
			}
		}
		return terms;
	}

	/**
	 * 
	 * @return
	 */
	public static int getTranscription() {
		return convertAlias2SBO("TRANSCRIPTION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTranscriptionalActivation() {
		return convertAlias2SBO("TRANSCRIPTIONAL_ACTIVATION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTranscriptionalInhibitor() {
		return convertAlias2SBO("TRANSCRIPTIONAL_INHIBITION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTransitionOmitted() {
		return convertAlias2SBO("KNOWN_TRANSITION_OMITTED");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTranslation() {
		return convertAlias2SBO("TRANSLATION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTranslationalActivation() {
		return convertAlias2SBO("TRANSLATIONAL_ACTIVATION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTranslationalInhibitor() {
		return convertAlias2SBO("TRANSLATIONAL_INHIBITION");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTransport() {
		return convertAlias2SBO("TRANSPORT");
	}

	/**
	 * 
	 * @return
	 */
	public static int getTrigger() {
		return convertAlias2SBO("TRIGGER");
	}

	/**
	 * 
	 * @param subject
	 * @param predicate
	 * @param object
	 * @return
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
	 * 
	 * @return
	 */
	public static int getTruncated() {
		return convertAlias2SBO("TRUNCATED");
	}

	/**
	 * 
	 * @return
	 */
	public static int getUnknownMolecule() {
		return convertAlias2SBO("UNKNOWN");
	}

	/**
	 * 
	 * @return
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
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isAntisenseRNA(int sboTerm) {
		return isChildOf(sboTerm, getAntisenseRNA());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isBindingActivator(int sboTerm) {
		return isChildOf(sboTerm, getBindingActivator());
	}
	
	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isCatalyst(int sboTerm) {
		return isChildOf(sboTerm, getCatalyst());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
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
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isCompetetiveInhibitor(int term) {
		return isChildOf(term, getCompetetiveInhibitor());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isCompleteInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getCompleteInhibitor());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */

	public static boolean isComplex(int sboTerm) {
		return isChildOf(sboTerm, getComplex());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a conservation law, false otherwise
	 */
	public static boolean isConservationLaw(int sboTerm) {
		return isChildOf(sboTerm, getConservationLaw());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a continuous framework, false otherwise
	 */
	public static boolean isContinuousFramework(int sboTerm) {
		return isChildOf(sboTerm, getContinuousFramework());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a discrete framework, false otherwise
	 */
	public static boolean isDiscreteFramework(int sboTerm) {
		return isChildOf(sboTerm, getDiscreteFramework());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isDrug(int sboTerm) {
		return isChildOf(sboTerm, getDrug());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isEmptySet(int sboTerm) {
		return isChildOf(sboTerm, getEmptySet());
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a Entity, false otherwise
	 */
	public static boolean isEntity(int sboTerm) {
		return isChildOf(sboTerm, getEntity());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isEnzymaticCatalysis(int sboTerm) {
		return isChildOf(sboTerm, getEnzymaticCatalysis());
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isEssentialActivator(int term) {
		return isChildOf(term, getEssentialActivator());
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
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isGene(int sboTerm) {
		return isChildOf(sboTerm, getGene());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return true if the sboTerm stands for a gene coding region, false
	 *         otherwise
	 */
	public static boolean isGeneCodingRegion(int sboTerm) {
		return isChildOf(sboTerm, getGeneCodingRegion());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return true if the sboTerm stands for a gene coding region or a gene,
	 *         false otherwise
	 */
	public static boolean isGeneOrGeneCodingRegion(int sboTerm) {
		return isGene(sboTerm) || isGeneCodingRegion(sboTerm);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isGeneric(int sboTerm) {
		return isChildOf(sboTerm, getGeneric());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isHillEquation(int sboTerm) {
		return isChildOf(sboTerm, getHillEquation());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
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
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isIon(int sboTerm) {
		return isChildOf(sboTerm, getIon());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
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
	 * 
	 * @param term
	 * @return
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
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isNonCompetetiveInhibitor(int term) {
		return isChildOf(term, getNonCompetetiveInhibitor());
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isNonEssentialActivator(int term) {
		return isChildOf(term, getNonEssentialActivator());
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
	 * 
	 * @param sboTerm
	 * @return
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
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isPhenotype(int sboTerm) {
		return isChildOf(sboTerm, getPhenotype());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
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
	 * 
	 * @param sboTerm
	 * @return
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
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isReceptor(int sboTerm) {
		return isChildOf(sboTerm, getReceptor());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isRNA(int sboTerm) {
		return isChildOf(sboTerm, getRNA());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isRNAOrMessengerRNA(int sboTerm) {
		return isRNA(sboTerm) || isMessengerRNA(sboTerm);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isSimpleMolecule(int sboTerm) {
		return isChildOf(sboTerm, getSimpleMolecule());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isSpecificActivator(int sboTerm) {
		return isChildOf(sboTerm, getSpecificActivator());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
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
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isStimulator(int sboTerm) {
		return isChildOf(sboTerm, getStimulator());
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTranscription(int sboTerm) {
		return isChildOf(sboTerm, getTranscription());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranscriptionalActivation(int sboTerm) {
		return isChildOf(sboTerm, getTranscriptionalActivation());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranscriptionalInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getTranscriptionalInhibitor());
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTransitionOmitted(int sboTerm) {
		return isChildOf(sboTerm, getTransitionOmitted());
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTranslation(int sboTerm) {
		return isChildOf(sboTerm, getTranslation());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranslationalActivation(int sboTerm) {
		return isChildOf(sboTerm, getTranslationalActivation());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranslationalInhibitor(int sboTerm) {
		return isChildOf(sboTerm, getTranslationalInhibitor());
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTransport(int sboTerm) {
		return isChildOf(sboTerm, getTransport());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTrigger(int sboTerm) {
		return isChildOf(sboTerm, getTrigger());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTruncated(int sboTerm) {
		return isChildOf(sboTerm, getTruncated());
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isUnknownMolecule(int sboTerm) {
		return isChildOf(sboTerm, getUnknownMolecule());
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isUnknownTransition(int sboTerm) {
		return isChildOf(sboTerm, getUnknownTransition());
	}
		
	/**
	 * This method creates a 7 digit SBO number for the given {@link Term} identifier (if
	 * this is a valid identifier). The returned {@link String} will not contain the
	 * SBO prefix.
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static String sboNumberString(int sboTerm) {
		if (!checkTerm(sboTerm)) {
			return "";
		}
		StringBuilder sbo = new StringBuilder();
		sbo.append(Integer.toString(sboTerm));
		while (sbo.length() < 7) {
			sbo.insert(0, '0');
		}
		return sbo.toString();
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
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		int i = 0; 
		for (org.biojava.ontology.Term term : sbo.getTerms()) {
			//System.out.printf("%s\n\n", Term.printTerm(new Term(term)));
			
			boolean isObsolete = false;
			try {
				term.getAnnotation().getProperty("is_obsolete");
				
				// If an exception as not been thrown, the property exist and the term is obsolete
				// We could do another test to be sure that the property value is equals to 'true'
				isObsolete = true;
				
			} catch (NoSuchElementException e) {
				// does nothing, the term is not obsolete
			}
			
			// There are terms that store the relationships, they are of the class Triple
			// so we exclude these to show only the actual SBO ontology terms.
			if (!(term instanceof Triple) && !isObsolete) {

				System.out.println("[Term : " + term + "]");
				System.out.println("    id : " + term.getName()); // There is no id in the biojava Term class, so the id is store in the name field
				System.out.println("    name : " + term.getDescription()); // as name is used to store the id, the description is storing the name !!
				if (term.getSynonyms().length > 0) {
					System.out.print("    Term Synonyms : ");
					for (Object synonym : term.getSynonyms()) {
						System.out.print(((Synonym) synonym).getName() + ", ");
					}
					System.out.println(" ");
				}
				
				for (Object key : term.getAnnotation().keys()) {
					
					System.out.println("    " + key + " : " + term.getAnnotation().getProperty(key));
				}
				
				i++;
			}
		}
		
		System.out.println("\nThere is " + i + " terms in the SBO ontology.");
	}
}
