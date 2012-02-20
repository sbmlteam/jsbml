package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.ListOf;

/**
 * 
 * <p/>The element SpeciesType, which is part of SBML Level 2 Version 4 specification, is not part
 * of SBML Level 3 Version 1 Core any more. Instead, it will be defined in the multi package. The
 * SpeciesType element carries not only the basic attributes which it had in SBML Level 2 Version 4
 * (metaid, id, name), but is also extended for the needs of describing multi-component entities
 * with the attribute bindingSite and for the needs of multistate entities by linking it to a list of
 * StateFeatures
 * <p/>A species type can be used to describe a component of a supra-macromolecular assembly,
 * but also a domain of a macromolecule. Such a domain can be a portion of the macromolecule,
 * a non-connex set of atoms forming a functional domain, or just a conceptual construct suiting
 * the needs of the modeler. The type of component can be specified by referring terms from the
 * subbranch functional entity of the <a href="http://biomodels.net/sbo/">Systems Biology Ontology</a>
 * through the optional sboTerm attribute. The following table provides typical examples of
 * component or domains (the list is absolutely not complete).
 * <pre>
 *   | SBO identifier  |  definition
 *  ----------------------------------- 
 *   | SBO:0000242     |  channel
 *   | SBO:0000244     |  receptor
 *   | SBO:0000284     |  transporter
 *   | SBO:0000280     |  ligand
 *   | SBO:0000493     |  functional domain
 *   | SBO:0000494     |  binding site
 *   | SBO:0000495     |  catalytic site
 *   | SBO:0000496     |  transmembrane domain
 * </pre>
 * 
 * @author rodrigue
 *
 */
@SuppressWarnings("deprecation")
public class SpeciesType extends org.sbml.jsbml.SpeciesType {

	ListOf<StateFeature> listOfStateFeatures;
	
	boolean bindingSite;
	
}
