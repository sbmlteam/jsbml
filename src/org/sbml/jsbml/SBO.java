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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.biojava.bio.seq.io.ParseException;
import org.biojava.ontology.Ontology;
import org.biojava.ontology.Term;
import org.biojava.ontology.Triple;
import org.biojava.ontology.io.OboParser;
import org.sbml.jsbml.resources.Resource;

/**
 * Methods for interacting with Systems Biology Ontology (SBO) terms.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class SBO {

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
			for (Object key : alias2sbo.keySet())
				sbo2alias.put(alias2sbo.get(key), key);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		return 0 <= sboTerm && sboTerm <= 9999999;
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
	 * 
	 * @return
	 */
	public static int getCatalysis() {
		return 13;
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
	public static int getEnzymaticCatalysis() {
		return 460;
	}

	/**
	 * 
	 * @return
	 */
	public static Ontology getOntology() {
		return sbo;
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
		for (String type : types)
			possibleEnzymes.add(Integer.valueOf(convertAlias2SBO(type)));
		return possibleEnzymes;
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static Term getTerm(int sboTerm) {
		return sbo.getTerm(intToString(sboTerm));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static Term getTerm(String sboTerm) {
		return sbo.getTerm(sboTerm);
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
		if (!checkTerm(sboTerm))
			return "";
		StringBuffer sbo = new StringBuffer(prefix);
		sbo.append(Integer.toString(sboTerm));
		while (sbo.length() < 11)
			sbo.insert(4, '0');
		return sbo.toString();
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isAntisenseRNA(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("ANTISENSERNA"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isBindingActivator(int sboTerm) {
		return isChildOf(sboTerm, 535);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isCatalyst(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("CATALYSIS"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isCatalyticActivator(int sboTerm) {
		return isChildOf(sboTerm, 534);
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
		if (!checkTerm(sboTerm))
			return false;
		return isChildOf(sbo.getTerm(intToString(sboTerm)), sbo
				.getTerm(intToString(parent)));
	}

	/**
	 * Traverses the systems biology ontology starting at Term subject until
	 * either the root (SBO:0000000) or the Term object is reached.
	 * 
	 * @param subject
	 *            Child
	 * @param object
	 *            Parent
	 * @return true if subject is a child of object.
	 */
	private static boolean isChildOf(Term subject, Term object) {
		if (subject.equals(object))
			return true;
		Set<Triple> relations = sbo.getTriples(subject, null, null);
		for (Triple triple : relations) {
			if (triple.getObject().equals(object))
				return true;
			if (isChildOf(triple.getObject(), object))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isCompetetiveInhibitor(int term) {
		return isChildOf(term, 206);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isCompleteInhibitor(int sboTerm) {
		return isChildOf(sboTerm, 537);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */

	public static boolean isComplex(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("COMPLEX"));
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a conservation law, false otherwise
	 */
	public static boolean isConservationLaw(int sboTerm) {
		return isChildOf(sboTerm, 355);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a continuous framework, false otherwise
	 */
	public static boolean isContinuousFramework(int sboTerm) {
		return isChildOf(sboTerm, 62);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a discrete framework, false otherwise
	 */
	public static boolean isDiscreteFramework(int sboTerm) {
		return isChildOf(sboTerm, 63);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isDrug(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("DRUG"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isEmptySet(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("DEGRADED"));
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a Entity, false otherwise
	 */
	public static boolean isEntity(int sboTerm) {
		return isChildOf(sboTerm, 236);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isEnzymaticCatalysis(int sboTerm) {
		return isChildOf(sboTerm, 460);
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isEssentialActivator(int term) {
		return isChildOf(term, 461);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-an Event, false otherwise
	 */
	public static boolean isEvent(int sboTerm) {
		return isChildOf(sboTerm, 231);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a functional compartment, false otherwise
	 */
	public static boolean isFunctionalCompartment(int sboTerm) {
		return isChildOf(sboTerm, 289);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a functional entity, false otherwise
	 */
	public static boolean isFunctionalEntity(int sboTerm) {
		return isChildOf(sboTerm, 241);
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isGene(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("GENE"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return true if the sboTerm stands for a gene coding region, false
	 *         otherwise
	 */
	public static boolean isGeneCodingRegion(int sboTerm) {
		return isChildOf(sboTerm, 335);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return true if the sboTerm stands for a gene coding region or a gene,
	 *         false otherwise
	 */
	public static boolean isGeneOrGeneCodingRegion(int sboTerm) {
		return (isChildOf(sboTerm, 335) || isChildOf(sboTerm,
				convertAlias2SBO("GENE")));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isGeneric(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("GENERIC"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isHillEquation(int sboTerm) {
		return isChildOf(sboTerm, 192);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isInhibitor(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("INHIBITION"));
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-an interaction, false otherwise
	 */
	public static boolean isInteraction(int sboTerm) {
		return isChildOf(sboTerm, 231);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isIon(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("ION"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isIonChannel(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("ION_CHANNEL"));

	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a kinetic constant, false otherwise
	 */
	public static boolean isKineticConstant(int sboTerm) {
		return isChildOf(sboTerm, 9);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a logical framework, false otherwise
	 */
	public static boolean isLogicalFramework(int sboTerm) {
		return isChildOf(sboTerm, 234);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a material entity, false otherwise
	 */
	public static boolean isMaterialEntity(int sboTerm) {
		return isChildOf(sboTerm, 240);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a mathematical expression, false otherwise
	 */
	public static boolean isMathematicalExpression(int sboTerm) {
		return isChildOf(sboTerm, 64);
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isMessengerRNA(int sboTerm) {
		return isChildOf(sboTerm, 278);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a modelling framework, false otherwise
	 */
	public static boolean isModellingFramework(int sboTerm) {
		return isChildOf(sboTerm, 4);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a modifier, false otherwise
	 */
	public static boolean isModifier(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("MODULATION"));
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isNonCompetetiveInhibitor(int term) {
		return isChildOf(term, 207);
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isNonEssentialActivator(int term) {
		return isChildOf(term, 462);
	}

	/**
	 * Function for checking whether the SBO term is obselete.
	 * 
	 * @param sboTerm
	 * @return true if the term is-an obsolete term, false otherwise
	 */
	public static boolean isObselete(int sboTerm) {
		return sbo.getTerm(intToString(sboTerm)).getDescription().startsWith(
				"obsolete");
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isPartialInhibitor(int sboTerm) {
		return isChildOf(sboTerm, 536);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO. This term
	 * is actually obsolete.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a participant, false otherwise
	 */
	public static boolean isParticipant(int sboTerm) {
		return isChildOf(sboTerm, 235);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a participant role, false otherwise
	 */
	public static boolean isParticipantRole(int sboTerm) {
		return isChildOf(sboTerm, 3);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isPhenotype(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("PHENOTYPE"));
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO. Obsolete
	 * term.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a physical participant, false otherwise
	 */
	public static boolean isPhysicalParticipant(int sboTerm) {
		return isChildOf(sboTerm, 236);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a product, false otherwise
	 */
	public static boolean isProduct(int sboTerm) {
		return isChildOf(sboTerm, 11);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isProtein(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("PROTEIN"));
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a quantitative parameter, false otherwise
	 */
	public static boolean isQuantitativeParameter(int sboTerm) {
		return isChildOf(sboTerm, 2);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a rate law, false otherwise
	 */
	public static boolean isRateLaw(int sboTerm) {
		return isChildOf(sboTerm, 1);
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a reactant, false otherwise
	 */
	public static boolean isReactant(int sboTerm) {
		return isChildOf(sboTerm, 10);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isReceptor(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("RECEPTOR"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isRNA(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("RNA"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isRNAOrMessengerRNA(int sboTerm) {
		return (isChildOf(sboTerm, 278) || isChildOf(sboTerm,
				convertAlias2SBO("RNA")));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isSimpleMolecule(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("SIMPLE_MOLECULE"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isSpecificActivator(int sboTerm) {
		return isChildOf(sboTerm, 533);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isStateTransition(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("STATE_TRANSITION"));
	}

	/**
	 * Function for checking the SBO term is from correct part of SBO.
	 * 
	 * @param sboTerm
	 * @return true if the term is-a steady state expression, false otherwise
	 */
	public static boolean isSteadyStateExpression(int sboTerm) {
		return isChildOf(sboTerm, 391);
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isStimulator(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("PHYSICAL_STIMULATION"));
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTranscription(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("TRANSCRIPTION"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranscriptionalActivation(int sboTerm) {
		return isChildOf(sboTerm,
				convertAlias2SBO("TRANSCRIPTIONAL_ACTIVATION"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranscriptionalInhibitor(int sboTerm) {
		return isChildOf(sboTerm,
				convertAlias2SBO("TRANSCRIPTIONAL_INHIBITION"));
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTransitionOmitted(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("KNOWN_TRANSITION_OMITTED"));
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTranslation(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("TRANSLATION"));
	}

	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranslationalActivation(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("TRANSLATIONAL_ACTIVATION"));
	}
	
	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTranslationalInhibitor(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("TRANSLATIONAL_INHIBITION"));
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isTransport(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("TRANSPORT"));
	}
	
	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTrigger(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("TRIGGER"));
	}
	
	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isTruncated(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("TRUNCATED"));
	}
	
	/**
	 * 
	 * @param sboTerm
	 * @return
	 */
	public static boolean isUnknownMolecule(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("UNKNOWN"));
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public static boolean isUnknownTransition(int sboTerm) {
		return isChildOf(sboTerm, convertAlias2SBO("UNKNOWN_TRANSITION"));
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
