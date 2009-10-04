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
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

import java.util.Date;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public interface SBMLReader {

	/**
	 * 
	 * @param d
	 * @return
	 */
	public Date convertDate(Object d);
	
	/**
	 * 
	 * @param sbmlDocument
	 * @return
	 */
	public String getWarnings();
	
	/**
	 * 
	 * @param compartment
	 * @return
	 */
	public Compartment readCompartment(Object compartment);
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public CVTerm readCVTerm(Object term);

	/**
	 * 
	 * @param eventAssignment
	 * @return
	 */
	public EventAssignment readEventAssignment(Object eventAssignment);
	
	/**
	 * 
	 * @param functionDefinition
	 * @return
	 */
	public FunctionDefinition readFunctionDefinition(Object functionDefinition);

	/**
	 * 
	 * @param initialAssignment
	 * @return
	 */
	public InitialAssignment readInitialAssignment(Object initialAssignment);

	/**
	 * 
	 * @param kineticLaw
	 * @return
	 */
	public KineticLaw readKineticLaw(Object kineticLaw);

	/**
	 * 
	 * @param model
	 * @return
	 */
	public Model readModel(Object model);
	
	/**
	 * 
	 * @param plumod
	 * @return
	 */
	public ModifierSpeciesReference readModifierSpeciesReference(
			Object modifierSpeciesReference);

	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public Parameter readParameter(Object parameter);
	
	/**
	 * 
	 * @param reaction
	 * @return
	 */
	public Reaction readReaction(Object reaction);
	
	/**
	 * 
	 * @param rule
	 * @return
	 */
	public Rule readRule(Object rule);
	
	/**
	 * 
	 * @param species
	 * @return
	 */
	public Species readSpecies(Object species);

	/**
	 * 
	 * @param speciesReference
	 * @return
	 */
	public SpeciesReference readSpeciesReference(Object speciesReference);

	/**
	 * 
	 * @param speciesType
	 * @return
	 */
	public SpeciesType readSpeciesType(Object speciesType);

	/**
	 * 
	 * @param stoichiometryMath
	 * @return
	 */
	public StoichiometryMath readStoichiometricMath(Object stoichiometryMath);

	/**
	 * 
	 * @param unit
	 * @return
	 */
	public Unit readUnit(Object unit);
	
	/**
	 * 
	 * @param unitDefinition
	 * @return
	 */
	public UnitDefinition readUnitDefinition(Object unitDefinition);
	
	/**
	 * 
	 * @return
	 */
	public int getNumErrors();

}
