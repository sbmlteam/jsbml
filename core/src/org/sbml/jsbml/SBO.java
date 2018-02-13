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
package org.sbml.jsbml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.biojava.nbio.ontology.Ontology;
import org.biojava.nbio.ontology.io.OboParser;
import org.sbml.jsbml.ontology.Term;
import org.sbml.jsbml.ontology.Triple;
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
 * parameter with identifier {@code k} that would indicate to a
 * software tool it is a first-order rate constant (if that's what
 * {@code k} happened to be in some given model).  However, one may
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
 * For example, if the SBO identifier {@code 'SBO:0000049'} is assigned
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
 * {@code 'SBO:0000049'} appearing in SBML models.
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
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class SBO {

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
   * @return {@code true} if sboTerm is in the range {0,.., 9999999}, {@code false} otherwise.
   */
  public static boolean checkTerm(int sboTerm) {
    return (0 <= sboTerm) && (sboTerm <= 9999999);
  }

  /**
   * Checks the format of the given SBO string.
   * 
   * @param sboTerm
   * @return {@code true} if sboTerm is in the correct format (a zero-padded, seven
   *         digit string), {@code false} otherwise.
   */
  public static boolean checkTerm(String sboTerm) {
    boolean correct = false;

    if (sboTerm != null) {
      correct = sboTerm.length() == 11;
      correct &= sboTerm.startsWith(prefix);
    }

    if (correct) {
      try {
        int sbo = Integer.parseInt(sboTerm.substring(4));
        correct &= checkTerm(sbo);
      } catch (NumberFormatException nfe) {
        correct = false;
      }
    }

    if (!correct && (sboTerm != null)) {
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
   * Interaction between several biochemical entities that results in the
   * formation of a non-covalent complex
   * 
   * @return 177
   */
  public static int getAssociation() {
    return 177;
  }

  /**
   * Describes an activator (ligand) which binds at a site other than the active site, resulting in a conformational change, enhancing the activity of the enzyme.
   * 
   * @return 636
   */
  public static int getAllostericActivator() {
    return 636;
  }

  /**
   * An inhibitor whose binding to an enzyme results in a conformational change, resulting in a loss of enzymatic activity. This activity can be restored upon removal of the inhibitor.
   * 
   * @return 639
   */
  public static int getAllostericInhibitor() {
    return 639;
  }

  /**
   * An essential activator that affects the apparent value of the Michaelis
   * constant(s).
   * 
   * @return 535
   */
  public static int getBindingActivator() {
    return 535;
  }

  /**
   * Modification of the velocity of a reaction by lowering the energy of the
   * transition state.
   * 
   * @return 172
   */
  public static int getCatalysis() {
    return 172;
  }

  /**
   * An event involving one or more chemical entities that modifies the electrochemical structure of at least one of the participants.
   * 
   * @return 176
   */
  public static int getBiochemicalReaction() {
    return 176;
  }

  /**
   * Substance that accelerates the velocity of a chemical reaction without
   * itself being consumed or transformed. This effect is achieved by lowering
   * the free energy of the transition state.
   * 
   * @return the SBO id corresponding to the alias 'CATALYSIS'
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
   * Substance that decreases the probability of a chemical reaction, without itself being consumed or transformed by the reaction, by stericaly hindering the interaction between reactants.
   * Also known as Inhibition Competitive
   * 
   * @return 206
   */
  public static int getCompetitiveInhibitor() {
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
   * Interaction between several biochemical entities that results in the formation of a non-covalent complex
   * Also known as non-covalent binding
   * 
   * @return 177
   */
  public static int getComplexAssembly() {
    return 177;
  }

  /**
   * 
   * @return
   */
  public static int getConservationLaw() {
    return 355;
  }

  /**
   * Decrease in amount of a material or conceptual entity.
   * 
   * @return 394
   */
  public static int getConsumption() {
    return 394;
  }

  /**
   * 
   * @return
   */
  public static int getContinuousFramework() {
    return 62;
  }

  /**
   * Biochemical reaction that results in the modification of some covalent bonds.
   * 
   * @return 182
   */
  public static int getConversion() {
    return 182;
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
      "UNKNOWN", "COMPLEX", "TRUNCATED", "GENERIC", "RNA", "RECEPTOR" }) {
      possibleEnzymes.add(Integer.valueOf(convertAlias2SBO(type)));
    }
    return possibleEnzymes;
  }

  /**
   * Complete disappearance of a physical entity.
   * 
   * @return 179
   */
  public static int getDegradation() {
    return 179;
  }

  /**
   * 
   * @return
   */
  public static int getDiscreteFramework() {
    return 63;
  }

  /**
   * Transformation of a non-covalent complex that results in the formation of
   * several independent biochemical entities.
   * 
   * @return 180
   */
  public static int getDissociation() {
    return 180;
  }

  /**
   * Polymer composed of nucleotides containing deoxyribose and linked by phosphodiester bonds.
   * 
   * @return 251
   */
  public static int getDNA() {
    return 251;
  }

  /**
   * Fragment or region of a DNA macromolecule.
   * @return 634
   */
  public static int getDNASegment() {
    return 634;
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
   * Change in enthalpy observed in the constituents of a thermodynamic system when undergoing
   * a transformation or chemical reaction. This is the preferred way of expressing the energy
   * changes to a system at constant pressure, since enthalpy itself cannot be directly measured.
   * The enthalpy change is positive in endothermic reactions, negative in exothermic reactions,
   * and is defined as the difference between the final and initial enthalpy of the system under
   * study: ΔH = Hf - Hi. The standard unit of measure is J. Symbol: ΔH
   * 
   * @return 573
   */
  public static int getEnthalpyChange() {
    return 573;
  }

  /**
   * 
   * @return
   */
  public static int getEntity() {
    return 236;
  }

  /**
   * The increase or decrease of the entropy of a system. For values greater than zero, there is an
   * implied increase in the disorder of a system, for example during a reaction, and decreased
   * disorder where the values are less than zero. The entropy change of a process is defined as the
   * initial system entropy value minus the final entropy value: DeltaS = Sf - Si. The standard unit
   * of measure is J/K. symbol: DeltaS
   * 
   * @return 577
   */
  public static int getEntropyChange() {
    return 577;
  }

  /**
   * 
   * @return
   */
  public static int getEnzymaticCatalysis() {
    return 460;
  }

  /**
   * Quantity characterizing a chemical equilibrium in a chemical reaction, which is a useful tool to
   * determine the concentration of various reactants or products in a system where chemical equilibrium
   * occurs.
   * 
   * @return 281
   */
  public static int getEquilibriumConstant() {
    return 281;
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
   * A phenomenon whereby an observed phenotype, qualitative or quantative, is not explainable by the simple additive effects of the individual gene pertubations alone. Genetic interaction between perturbed genes is usually expected to generate a 'defective' phenotype. The level of defectiveness is often used to sub-classify this phenomenon.
   * Biopax describes this as Genetic interactions between genes occur when two genetic perturbations (e.g. mutations) have a combined phenotypic effect not caused by either perturbation alone. A gene participant in a genetic interaction represents the gene that is perturbed. Genetic interactions are not physical interactions but logical (AND) relationships. Their physical manifestations can be complex and span an arbitarily long duration. Rationale: Currently, BioPAX provides a simple definition that can capture most genetic interactions described in the literature. In the future, if required, the definition can be extended to capture other logical relationships and different, participant specific phenotypes. Example: A synthetic lethal interaction occurs when cell growth is possible without either gene A OR B, but not without both gene A AND B. If you knock out A and B together, the cell will die.
   * @return 343
   */
  public static int getGeneticInteraction()
  {
    return 343;
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
   * The increase or decrease of the Gibbs free energy of a system. During a reaction, this is equal
   * to the change in enthalpy of the system minus the change in the product of the temperature times
   * the entropy of the system: ΔG = ΔH - T ΔS. A negative value indicates that the reaction will be
   * favoured and will release energy. The magnitude of the value indicates how far the reaction is
   * from equilibrium, where there will be no free energy change. The standard unit of measure is kJ/mol.
   * Symbol: ΔG.
   * 
   * @return 581
   */
  public static int getGibbsFreeEnergyChange() {
    return 581;
  }

  /**
   * 
   * @return
   */
  public static int getHillEquation() {
    return 192;
  }

  /**
   * Negative modulation of the execution of a process.
   * 
   * @return 169
   */
  public static int getInhibition() {
    return 169;
  }

  /**
   * Substance that decreases the probability of a chemical reaction without
   * itself being consumed or transformed by the reaction.
   * 
   * @return the SBO id corresponding to the alias 'INHIBITION'
   */
  public static int getInhibitor() {
    return convertAlias2SBO("INHIBITION");
  }

  /**
   * 
   * @return 231
   */
  public static int getInteraction() {
    return 231;
  }

  /**
   * An inhibitor which binds irreversibly with the enzyme such that it cannot be removed, and abolishes enzymatic function.
   * 
   * @return 638
   */
  public static int getIrreversibleInhibitor() {
    return 638;
  }

  /**
   * 
   * @return 459
   */
  public static int getActivator() {
    return 459;
  }

  /**
   * 
   * @return
   */
  public static int getIon() {
    return convertAlias2SBO("ION");
  }

  /**
   * A combined (weighted) measure of the concentration of all electrolytes present in a
   * solution. It is calculated as a half of the sum over all the ions in the solution
   * multiplied by the square of individual ionic valencies. Monovalent electrolytes have
   * a concentration equal to their ionic strength while multivalent electrolytes have
   * greater ionic strength, directly proportional to ionic valency. Symbol: I
   * 
   * @return 623
   */
  public static int getIonicStrength() {
    return 623;
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
   * Substance that changes the velocity of a process without itself being
   * consumed or transformed by the reaction.
   * 
   * @return the SBO id corresponding to the alias 'MODULATION'
   */
  public static int getModifier() {
    return convertAlias2SBO("MODULATION");
  }

  /**
   * A modifier whose activity is not known or has not been specified.
   * 
   * @return 596
   */
  public static int getModifierUnknownActivity()
  {
    return 596;
  }

  /**
   * Modification of the execution of an event or a process.
   * 
   * @return 168
   */
  public static int getModulation() {
    return 168;
  }

  /**
   * Relationship between molecular entities, based on contacts, direct or indirect.
   * Biopax describes this as an interaction in which participants bind physically to each other, directly or indirectly through intermediary molecules. Rationale: There is a large body of interaction data, mostly produced by high throughput systems, that does not satisfy the level of detail required to model them with ComplexAssembly class. Specifically, what is lacking is the stoichiometric information and completeness (closed-world) of participants required to model them as chemical processes. Nevertheless interaction data is extremely useful and can be captured in BioPAX using this class. Usage: This class should be used by default for representing molecular interactions such as those defined by PSI-MI level 2.5. The participants in a molecular interaction should be listed in the PARTICIPANT slot. Note that this is one of the few cases in which the PARTICPANT slot should be directly populated with instances (see comments on the PARTICPANTS property in the interaction class description). If all participants are known with exact stoichiometry, ComplexAssembly class should be used instead. Example: Two proteins observed to interact in a yeast-two-hybrid experiment where there is not enough experimental evidence to suggest that the proteins are forming a complex by themselves without any indirect involvement of other proteins. This is the case for most large-scale yeast two-hybrid screens.
   * @return 344
   */
  public static int getMolecularInteraction()
  {
    return 344;
  }

  /**
   * Control that is necessary to the execution of a process.
   * 
   * @return 171
   */
  public static int getNecessaryStimulation() {
    return 171;
  }

  /**
   * Describes an activator (ligand) which binds to the enzyme, which does not result in a conformational change, but which enhances the enzyme's activity.
   * 
   * @return 637
   */
  public static int getNonAllostericActivator() {
    return 637;
  }

  /**
   * Substance that decreases the probability of a chemical reaction, without itself being consumed or transformed by the reaction, and without sterically hindering the interaction between reactants.
   * Also known as Inhibition Non-Competitive Inhibitor or Inhibition Uncompetitive
   * @return 207
   */
  public static int getNonCompetitiveInhibitor() {
    return 207;
  }

  /**
   * Entity composed of several independant components that are not linked by covalent bonds.
   * 
   * @return
   */
  public static int getNonCovalentComplex() {
    return 253;
  }

  /**
   * An activator which is not necessary for an enzymatic reaction, but whose presence will further increase enzymatic activity.
   * Also known as Activation nonallosteric
   * 
   * @return 462
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
   * The function of a physical or conceptual entity, that is its role, in the
   * execution of an event or process.
   * 
   * @return 3
   */
  public static int getParticipantRole() {
    return 3;
  }

  /**
   * Perturbing agent.
   * 
   * @return
   */
  public static int getPertubingAgent() {
    return 405;
  }

  /**
   * "A measure of acidity and alkalinity of a solution that is a number on a scale on
   * which a value of 7 represents neutrality and lower numbers indicate increasing acidity
   * and higher numbers increasing alkalinity and on which each unit of change represents a
   * tenfold change in acidity or alkalinity and that is the negative logarithm of the
   * effective hydrogen-ion concentration or hydrogen-ion activity in gram equivalents per
   * liter of the solution. (Definition from Merriam-Webster Dictionary)"
   * 
   * @return 304
   */
  public static int getpH()
  {
    return 304;
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
   * An enumeration of the concentration of magnesium (Mg) in solution (pMg = -log10[Mg2+]).
   * 
   * @return 641
   */
  public static int getPMg() {
    return 641;
  }

  /**
   * 
   * @return
   */
  public static int getPhysicalParticipant() {
    return 236;
  }

  /**
   * 
   * @return
   */
  public static int getProcess() {
    return 375;
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
   * Substance that increases the probability of a chemical reaction without itself being consumed or transformed by the reaction. This effect is achieved by increasing the difference of free energy between the reactant(s) and the product(s)
   * Also known as Activation Allosteric
   * 
   * @return
   */
  public static int getPotentiator()
  {
    return 21;
  }

  /**
   * Substance that is produced in a reaction. In a chemical equation the
   * Products are the elements or compounds on the right hand side of the
   * reaction equation. A product can be produced and consumed by the same
   * reaction, its global quantity remaining unchanged.
   * 
   * @return the SBO id corresponding to the alias 'product'
   */
  public static int getProduct() {
    return convertAlias2SBO("product");
  }

  /**
   * Generation of a material or conceptual entity.
   * 
   * @return 393
   */
  public static int getProduction() {
    return 393;
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
   * Substance consumed by a chemical reaction. Reactants react with each
   * other to form the products of a chemical reaction. In a chemical equation
   * the Reactants are the elements or compounds on the left hand side of the
   * reaction equation. A reactant can be consumed and produced by the same
   * reaction, its global quantity remaining unchanged.
   * 
   * @return the SBO id corresponding to the alias 'reactant'
   */
  public static int getReactant() {
    return convertAlias2SBO("reactant");
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
   * Fragment or region of an RNA macromolecule.
   * @return 635
   */
  public static int getRNASegment() {
    return 635;
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
   * Returns the side product SBO term
   * @return the SBO term 603
   */
  public static int getSideProduct() {
    return 603;
  }

  /**
   * Returns the side substrate SBO term
   * @return the SBO term 604
   */
  public static int getSideSubstrate() {
    return 604;
  }

  /**
   * 
   * @return
   */
  public static int getSimpleMolecule() {
    return convertAlias2SBO("SIMPLE_MOLECULE");
  }

  /**
   * Simple, non-repetitive chemical entity. Also referred to as simple chemical
   * 
   * @return 247
   */
  public static int getSmallMolecule() {
    return 247;
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
   * Positive modulation of the execution of a process.
   * 
   * @return 170
   */
  public static int getStimulation() {
    return 170;
  }

  /**
   * 
   * @return
   */
  public static int getStimulator() {
    return convertAlias2SBO("PHYSICAL_STIMULATION");
  }

  /**
   * A composite biochemical process through which a gene sequence is fully converted into
   * mature gene products. These gene products may include RNA species as well as proteins,
   * and the process encompasses all intermediate steps required to generate the active form
   * of the gene product.
   * 
   * @return 589
   */
  public static int getTemplateReaction()
  {
    return 589;
  }

  /**
   * A phenomenon whereby an observed phenotype, qualitative or quantative, is not explainable by the simple additive effects of the individual gene pertubations alone. Genetic interaction between perturbed genes is usually expected to generate a 'defective' phenotype.
   * The level of defectiveness is often used to sub-classify this phenomenon.
   * Also known as Genetic Interaction
   * Biopax describes this as  Regulation of an expression reaction by a controlling element such as a transcription factor or microRNA. Usage: To represent the binding of the transcription factor to a regulatory element in the TemplateReaction, create a complex of the transcription factor and the regulatory element and set that as the controller.
   * 
   * @return 343
   */
  public static int getTemplateReactionRegulation() {
    return 343;
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
   * @jsbml.warning The methods will throw {@link NoSuchElementException} if the id is not found or {@code null}.
   * 
   * @param sboTerm the id of the SBO term to search for.
   * @return the SBO term with the id 'sboTerm'.
   * @throws NoSuchElementException if the id is not found or {@code null}.
   */
  public static Term getTerm(String sboTerm) {
    return new Term(sbo.getTerm(sboTerm));
  }

  /**
   * Return the set of terms of the SBO Ontology.
   * 
   * <p> This methods return only Term object and no Triple object that represent the
   * relationship between terms. If you want to access the full set of {@link org.biojava.nbio.ontology.Term}
   * containing also the {@link org.biojava.nbio.ontology.Triple}, use {@link SBO#getOntology()}
   * to get the underlying biojava object.
   * 
   * @return the set of terms of the SBO Ontology.
   */
  public static Set<Term> getTerms() {
    if (terms.size() < sbo.getTerms().size()) {
      for (org.biojava.nbio.ontology.Term term : sbo.getTerms()) {

        if (term instanceof org.biojava.nbio.ontology.Triple) {
          // does nothing
        } else if (term instanceof org.biojava.nbio.ontology.Term) {
          terms.add(new Term(term));
        }
      }
    }
    return terms;
  }

  /**
   * Temperature is the physical property of a system which underlies the common notions of
   * "hot" and "cold"; the material with the higher temperature is said to be hotter.
   * Temperature is a quantity related to the average kinetic energy of the particles in a
   * substance. The 10th Conference Generale des Poids et Mesures decided to define the
   * thermodynamic temperature scale by choosing the triple point of water as the
   * fundamental fixed point, and assigning to it the temperature 273,16 degrees Kelvin,
   * exactly (0.01 degree Celsius).
   * 
   * @return 147
   */
  public static int getThermodynamicTemperature()
  {
    return 147;
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
   * An event involving one or more physical entities that modifies the structure, location or free energy of at least one of the participants.
   * Also known as biochemical or transport reaction
   * 
   * @return 167
   */
  public static int getTransportWithBiochemicalReaction() {
    return 167;
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
   * For example:
   * 
   * <pre class="brush:java">
   * getTriples(SBO.getTerm("SBO:0000002"), SBO.getTerm("is_a"), null);
   * </pre>
   * 
   * will returned all the parent Terms of {@code SBO:0000002} and
   * 
   * <pre class="brush:java">
   * getTriples(null, SBO.getTerm("is_a"), SBO.getTerm(188));
   * </pre>
   * 
   * will returned all the children Terms of {@code SBO:0000188}
   * 
   * @param subject the subject to search for, or {@code null}.
   * @param predicate the relationship to search for, or {@code null}.
   * @param object the object to search for, or {@code null}.
   * @return a set of Triple which match the supplied subject, predicate and object.
   * 
   * @see org.biojava.nbio.ontology.Ontology#getTriples(org.biojava.nbio.ontology.Term,
   *      org.biojava.nbio.ontology.Term, org.biojava.nbio.ontology.Term)
   */
  public static Set<Triple> getTriples(Term subject, Term predicate, Term object) {
    Set<Triple> triples = new HashSet<Triple>();
    for (org.biojava.nbio.ontology.Triple triple : sbo.getTriples(
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
   * An inhibitor which binds only to the complex formed between the enzyme and substrate (E-S complex).
   * @return 640
   */
  public static int getUncompetitiveInhibitor() {
    return 640;
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
   * This method will test if a term for the given SBO code number exists and
   * returns {@code true} if this is the case. It is recommended to call this
   * method before trying to retrieve an actual {@link Term} object, because
   * this could result in a {@link NoSuchElementException}.
   * 
   * @param sboTerm
   *        the SBO identifier for which a term is to be retrieved.
   * @return {@code true} if an SBO {@link Term} object can be safely retrieved
   *         for the given identifier.
   * @see #hasTerm(String)
   */
  public boolean hasTerm(int sboTerm) {
    return hasTerm(intToString(sboTerm));
  }

  /**
   * This method will test if a term for the given SBO code number exists and
   * returns {@code true} if this is the case. It is recommended to call this
   * method before trying to retrieve an actual {@link Term} object, because
   * this could result in a {@link NoSuchElementException}.
   * 
   * @param sboTerm
   *        the SBO identifier for which a term is to be retrieved.
   * @return {@code true} if an SBO {@link Term} object can be safely retrieved
   *         for the given identifier.
   * @see #hasTerm(int)
   */
  public boolean hasTerm(String sboTerm) {
    try {
      return sbo.getTerm(sboTerm) != null;
    } catch (NoSuchElementException exc) {
      return false;
    }
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
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isAntisenseRNA(int sboTerm) {
    return isChildOf(sboTerm, getAntisenseRNA());
  }

  /**
   * 
   * @param sboTerm
   * @return
   */
  public static boolean isAssociation(int sboTerm) {
    return isChildOf(sboTerm, getAssociation());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isBindingActivator(int sboTerm) {
    return isChildOf(sboTerm, getBindingActivator());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isCatalyst(int sboTerm) {
    return isChildOf(sboTerm, getCatalyst());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
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
   * @return {@code true} if the subgraph of the SBO rooted at the term parent
   *         contains a term with the id corresponding to sboTerm.
   */
  public static boolean isChildOf(int sboTerm, int parent) {
    if (!checkTerm(sboTerm)) {
      return false;
    }
    return (sboTerm == parent) || isChildOf(
      new Term(sbo.getTerm(intToString(sboTerm))),
      new Term(sbo.getTerm(intToString(parent))));
  }

  /**
   * Traverses the systems biology ontology starting at {@link Term} subject until
   * either the root (SBO:0000000) or the {@link Term} object is reached.
   * 
   * @param subject
   *            Child
   * @param object
   *            Parent
   * @return {@code true} if subject is a child of object.
   */
  private static boolean isChildOf(Term subject, Term object) {
    if (subject.equals(object)) {
      return true;
    }
    Set<org.biojava.nbio.ontology.Triple> relations = sbo.getTriples(
      subject != null ? subject.getTerm() : null, null, null);
    for (org.biojava.nbio.ontology.Triple triple : relations) {
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
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isCompetetiveInhibitor(int sboTerm) {
    return isChildOf(sboTerm, getCompetitiveInhibitor());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isCompleteInhibitor(int sboTerm) {
    return isChildOf(sboTerm, getCompleteInhibitor());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isComplex(int sboTerm) {
    return isChildOf(sboTerm, getComplex());
  }

  /**
   * Returns {@code true} if the term is-a conservation law, {@code false} otherwise
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a conservation law, {@code false} otherwise
   */
  public static boolean isConservationLaw(int sboTerm) {
    return isChildOf(sboTerm, getConservationLaw());
  }

  /**
   * Returns {@code true} if the term is-a continuous framework, {@code false} otherwise
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a continuous framework, {@code false} otherwise
   */
  public static boolean isContinuousFramework(int sboTerm) {
    return isChildOf(sboTerm, getContinuousFramework());
  }

  /**
   * Returns {@code true} if the term is-a discrete framework, {@code false} otherwise
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a discrete framework, {@code false} otherwise
   */
  public static boolean isDiscreteFramework(int sboTerm) {
    return isChildOf(sboTerm, getDiscreteFramework());
  }

  /**
   * 
   * @param sboTerm
   * @return
   */
  public static boolean isDissociation(int sboTerm) {
    return isChildOf(sboTerm, getDissociation());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isDrug(int sboTerm) {
    return isChildOf(sboTerm, getDrug());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isEmptySet(int sboTerm) {
    return isChildOf(sboTerm, getEmptySet());
  }

  /**
   * Returns {@code true} if the term is-a Entity, {@code false} otherwise
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a Entity, {@code false} otherwise
   */
  public static boolean isEntity(int sboTerm) {
    return isChildOf(sboTerm, getEntity());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isEnzymaticCatalysis(int sboTerm) {
    return isChildOf(sboTerm, getEnzymaticCatalysis());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isEssentialActivator(int sboTerm) {
    return isChildOf(sboTerm, getEssentialActivator());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-an Event, {@code false} otherwise
   */
  public static boolean isEvent(int sboTerm) {
    return isChildOf(sboTerm, getEvent());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a functional compartment, {@code false} otherwise
   */
  public static boolean isFunctionalCompartment(int sboTerm) {
    return isChildOf(sboTerm, getFunctionalCompartment());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a functional entity, {@code false} otherwise
   */
  public static boolean isFunctionalEntity(int sboTerm) {
    return isChildOf(sboTerm, getFunctionalEntity());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isGene(int sboTerm) {
    return isChildOf(sboTerm, getGene());
  }

  /**
   * Returns {@code true} if the sboTerm stands for a gene coding region, false
   *         otherwise
   * 
   * @param sboTerm
   * @return {@code true} if the sboTerm stands for a gene coding region, false
   *         otherwise
   */
  public static boolean isGeneCodingRegion(int sboTerm) {
    return isChildOf(sboTerm, getGeneCodingRegion());
  }

  /**
   * Returns {@code true} if the sboTerm stands for a gene coding region or a gene,
   *         false otherwise
   * 
   * @param sboTerm
   * @return {@code true} if the sboTerm stands for a gene coding region or a gene,
   *         false otherwise
   */
  public static boolean isGeneOrGeneCodingRegion(int sboTerm) {
    return isGene(sboTerm) || isGeneCodingRegion(sboTerm);
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isGeneric(int sboTerm) {
    return isChildOf(sboTerm, getGeneric());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isHillEquation(int sboTerm) {
    return isChildOf(sboTerm, getHillEquation());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isInhibitor(int sboTerm) {
    return isChildOf(sboTerm, getInhibitor());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-an interaction, {@code false} otherwise
   */
  public static boolean isInteraction(int sboTerm) {
    return isChildOf(sboTerm, getInteraction());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isIon(int sboTerm) {
    return isChildOf(sboTerm, getIon());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isIonChannel(int sboTerm) {
    return isChildOf(sboTerm, getIonChannel());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a kinetic constant, {@code false} otherwise
   */
  public static boolean isKineticConstant(int sboTerm) {
    return isChildOf(sboTerm, getKineticConstant());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a logical framework, {@code false} otherwise
   */
  public static boolean isLogicalFramework(int sboTerm) {
    return isChildOf(sboTerm, getLogicalFramework());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a material entity, {@code false} otherwise
   */
  public static boolean isMaterialEntity(int sboTerm) {
    return isChildOf(sboTerm, getMaterialEntity());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a mathematical expression, {@code false} otherwise
   */
  public static boolean isMathematicalExpression(int sboTerm) {
    return isChildOf(sboTerm, getMathematicalExpression());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isMessengerRNA(int sboTerm) {
    return isChildOf(sboTerm, getMessengerRNA());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a modelling framework, {@code false} otherwise
   */
  public static boolean isModellingFramework(int sboTerm) {
    return isChildOf(sboTerm, getModellingFramework());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a modifier, {@code false} otherwise
   */
  public static boolean isModifier(int sboTerm) {
    return isChildOf(sboTerm, getModifier());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isNonCompetetiveInhibitor(int sboTerm) {
    return isChildOf(sboTerm, getNonCompetitiveInhibitor());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isNonEssentialActivator(int sboTerm) {
    return isChildOf(sboTerm, getNonEssentialActivator());
  }

  /**
   * Function for checking whether the SBO term is obsolete.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-an obsolete term, {@code false} otherwise
   */
  public static boolean isObsolete(int sboTerm) {
    return getTerm(intToString(sboTerm)).isObsolete();
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isPartialInhibitor(int sboTerm) {
    return isChildOf(sboTerm, getPartialInhibitor());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO. This term
   * is actually obsolete.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a participant, {@code false} otherwise
   */
  public static boolean isParticipant(int sboTerm) {
    return isChildOf(sboTerm, getParticipant());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a participant role, {@code false} otherwise
   */
  public static boolean isParticipantRole(int sboTerm) {
    return isChildOf(sboTerm, getParticipantRole());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isPhenotype(int sboTerm) {
    return isChildOf(sboTerm, getPhenotype());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isPhysicalCompartment(int sboTerm) {
    return isChildOf(sboTerm, getPhysicalCompartment());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO. Obsolete
   * term.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a physical participant, {@code false} otherwise
   */
  public static boolean isPhysicalParticipant(int sboTerm) {
    return isChildOf(sboTerm, getPhysicalParticipant());
  }

  /**
   * 
   * @param sboTerm
   * @return
   */
  public static boolean isProcess(int sboTerm) {
    return isChildOf(sboTerm, getProcess());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a product, {@code false} otherwise
   */
  public static boolean isProduct(int sboTerm) {
    return isChildOf(sboTerm, getProduct());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isProtein(int sboTerm) {
    return isChildOf(sboTerm, getProtein());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a quantitative parameter, {@code false} otherwise
   */
  public static boolean isQuantitativeParameter(int sboTerm) {
    return isChildOf(sboTerm, getQuantitativeParameter());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a rate law, {@code false} otherwise
   */
  public static boolean isRateLaw(int sboTerm) {
    return isChildOf(sboTerm, getRateLaw());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a reactant, {@code false} otherwise
   */
  public static boolean isReactant(int sboTerm) {
    return isChildOf(sboTerm, getReactant());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isReceptor(int sboTerm) {
    return isChildOf(sboTerm, getReceptor());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isRNA(int sboTerm) {
    return isChildOf(sboTerm, getRNA());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isRNAOrMessengerRNA(int sboTerm) {
    return isRNA(sboTerm) || isMessengerRNA(sboTerm);
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isSimpleMolecule(int sboTerm) {
    return isChildOf(sboTerm, getSimpleMolecule());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isSpecificActivator(int sboTerm) {
    return isChildOf(sboTerm, getSpecificActivator());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isStateTransition(int sboTerm) {
    return isChildOf(sboTerm, getStateTransition());
  }

  /**
   * Function for checking the SBO term is from correct part of SBO.
   * 
   * @param sboTerm
   * @return {@code true} if the term is-a steady state expression, {@code false} otherwise
   */
  public static boolean isSteadyStateExpression(int sboTerm) {
    return isChildOf(sboTerm, getSteadyStateExpression());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isStimulator(int sboTerm) {
    return isChildOf(sboTerm, getStimulator());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTranscription(int sboTerm) {
    return isChildOf(sboTerm, getTranscription());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTranscriptionalActivation(int sboTerm) {
    return isChildOf(sboTerm, getTranscriptionalActivation());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTranscriptionalInhibitor(int sboTerm) {
    return isChildOf(sboTerm, getTranscriptionalInhibitor());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTransitionOmitted(int sboTerm) {
    return isChildOf(sboTerm, getTransitionOmitted());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTranslation(int sboTerm) {
    return isChildOf(sboTerm, getTranslation());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTranslationalActivation(int sboTerm) {
    return isChildOf(sboTerm, getTranslationalActivation());
  }


  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTranslationalInhibitor(int sboTerm) {
    return isChildOf(sboTerm, getTranslationalInhibitor());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTransport(int sboTerm) {
    return isChildOf(sboTerm, getTransport());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTrigger(int sboTerm) {
    return isChildOf(sboTerm, getTrigger());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isTruncated(int sboTerm) {
    return isChildOf(sboTerm, getTruncated());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isUnknownMolecule(int sboTerm) {
    return isChildOf(sboTerm, getUnknownMolecule());
  }

  /**
   * Returns {@code true} if the given term identifier comes from the stated branch of SBO.
   * <p>
   * @param sboTerm
   * @return {@code true} if {@code term} is-a SBO <em>'product'</em>, {@code false} otherwise.
   */
  public static boolean isUnknownTransition(int sboTerm) {
    return isChildOf(sboTerm, getUnknownTransition());
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

  /**
   * For testing purposes.
   * 
   * @param args
   */
  public static void main(String[] args) {
    int i = 0;
    for (Term term : getTerms()) {
      if (!term.isObsolete()) {
        System.out.printf("%s\n", Term.printTerm(term));
        System.out.printf("%s\n", term.getTerm());
        // System.out.printf("%s\n\n", term.);

        i++;
      }
    }
    System.out.println("\nThere is " + i + " terms in the SBO ontology.");

    System.out.println("\nGet term by id = " + Term.printTerm(getTerm("SBO:0000620")));
  }
}
