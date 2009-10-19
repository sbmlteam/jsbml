/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public interface SBMLWriter {
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public Object convertDate(Date date);
	
	/**
	 * 
	 * @return
	 */
	public int getNumErrors(Object sbase);

	/**
	 * 
	 * @param sbase
	 * @return
	 */
	public List<SBMLException> getWriteWarnings(Object sbase);
	
	/**
	 * 
	 * @param c
	 * @param comp
	 */
	public void saveCompartmentProperties(Compartment c, Object comp);

	/**
	 * 
	 * @param cvt
	 * @param term
	 */
	public void saveCVTermProperties(CVTerm cvt, Object term);
	
	/**
	 * 
	 * @param r
	 * @param event
	 * @throws SBMLException 
	 */
	public void saveEventProperties(Event r, Object event) throws SBMLException;
	
	/**
	 * 
	 * @param kl
	 * @param kineticLaw
	 * @throws SBMLException 
	 */
	public void saveKineticLawProperties(KineticLaw kl, Object kineticLaw) throws SBMLException;

	/**
	 * 
	 * @param mc
	 * @param sbase
	 * @throws SBMLException 
	 */
	public void saveMathContainerProperties(MathContainer mc, Object sbase) throws SBMLException;

	/**
	 * 
	 * @param mh
	 * @param modelHistory
	 */
	public void saveModelHistoryProperties(ModelHistory mh, Object modelHistory);

	/**
	 * 
	 * @param modifierSpeciesReference
	 * @param msr
	 */
	public void saveModifierSpeciesReferenceProperties(
			ModifierSpeciesReference modifierSpeciesReference, Object msr);

	/**
	 * 
	 * @param nsb
	 * @param sb
	 */
	public void saveNamedSBaseProperties(NamedSBase nsb, Object sb);

	/**
	 * 
	 * @param p
	 * @param parameter
	 */
	public void saveParameterProperties(Parameter p, Object parameter);

	/**
	 * 
	 * @param r
	 * @param reaction
	 * @throws SBMLException 
	 */
	public void saveReactionProperties(Reaction r, Object reaction) throws SBMLException;

	/**
	 * 
	 * @param s
	 * @param sb
	 */
	public void saveSBaseProperties(SBase s, Object sb);

	/**
	 * 
	 * @param s
	 * @param species
	 */
	public void saveSpeciesProperties(Species s, Object species);

	/**
	 * 
	 * @param compartment
	 * @return
	 */
	public Object writeCompartment(Compartment compartment);

	/**
	 * 
	 * @param compartmentType
	 * @return
	 */
	public Object writeCompartmentType(CompartmentType compartmentType);

	/**
	 * 
	 * @param constraint
	 * @return
	 */
	public Object writeConstraint(Constraint constraint);

	/**
	 * 
	 * @param cvt
	 * @return
	 */
	public Object writeCVTerm(CVTerm cvt);

	/**
	 * 
	 * @param delay
	 * @return
	 */
	public Object writeDelay(Delay delay);

	/**
	 * 
	 * @param event
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeEvent(Event event) throws SBMLException;

	/**
	 * 
	 * @param ea
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeEventAssignment(EventAssignment eventAssignment, Object...args) throws SBMLException;

	/**
	 * 
	 * @param functionDefinition
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeFunctionDefinition(FunctionDefinition functionDefinition) throws SBMLException;

	/**
	 * 
	 * @param initialAssignment
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeInitialAssignment(InitialAssignment initialAssignment) throws SBMLException;

	/**
	 * 
	 * @param kineticLaw
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeKineticLaw(KineticLaw kineticLaw, Object...args) throws SBMLException;

	/**
	 * 
	 * @param model
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeModel(Model model) throws SBMLException;

	/**
	 * 
	 * @param modifierSpeciesReference
	 * @return
	 */
	public Object writeModifierSpeciesReference(
			ModifierSpeciesReference modifierSpeciesReference, Object...args);

	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public Object writeParameter(Parameter parameter,Object...args);

	/**
	 * 
	 * @param reaction
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeReaction(Reaction reaction) throws SBMLException;

	/**
	 * 
	 * @param rule
	 * @return
	 */
	public Object writeRule(Rule rule,Object...args);

	/**
	 * 
	 * @param sbmlDocument
	 * @param filename
	 * @return
	 * @throws SBMLException 
	 * @throws IOException 
	 */
	public boolean writeSBML(Object sbmlDocument, String filename) throws SBMLException, IOException;

	/**
	 * 
	 * @param object
	 * @param filename
	 * @param programName
	 * @param versionNumber
	 * @return
	 * @throws SBMLException 
	 * @throws IOException 
	 */
	public boolean writeSBML(Object object, String filename, String programName,
			String versionNumber) throws SBMLException, IOException;

	/**
	 * 
	 * @param species
	 * @return
	 */
	public Object writeSpecies(Species species);

	/**
	 * 
	 * @param speciesReference
	 * @return
	 * @throws SBMLException 
	 */
	public Object writeSpeciesReference(SpeciesReference speciesReference,Object...args) throws SBMLException;

	/**
	 * 
	 * @param speciesType
	 * @return
	 */
	public Object writeSpeciesType(SpeciesType speciesType);

	/**
	 * 
	 * @param stoichiometryMath
	 * @return
	 */
	public Object writeStoichoimetryMath(StoichiometryMath stoichiometryMath);

	/**
	 * 
	 * @param trigger
	 * @return
	 */
	public Object writeTrigger(Trigger trigger);

	/**
	 * 
	 * @param unit
	 * @return
	 */
	public Object writeUnit(Unit unit,Object...args);
	
	/**
	 * 
	 * @param unitDefinition
	 * @return
	 */
	public Object writeUnitDefinition(UnitDefinition unitDefinition);
}
