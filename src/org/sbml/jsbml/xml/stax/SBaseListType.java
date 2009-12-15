/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

package org.sbml.jsbml.xml.stax;

/**
 * This enum lists all the possible names of the listXXX components.
 * If the listXXX is a SBML package extension, the SBaseListType value to set would be 'other'.
 * @author marine
 *
 */
public enum SBaseListType {

	none, other, listOfFunctionDefinitions, listOfUnitDefinitions, listOfCompartments, listOfSpecies, listOfParameters, listOfInitialAssignments, listOfRules, listOfReactants, listOfProducts, listOfEventAssignments, listOfModifiers, listOfConstraints, listOfReactions, listOfEvents, listOfUnits, listOfLocalParameters, listOfCompartmentTypes, listOfSpeciesTypes
}
