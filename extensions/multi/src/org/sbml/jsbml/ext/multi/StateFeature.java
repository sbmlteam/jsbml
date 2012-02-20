package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Species;

/**
 * A species type ({@link SpeciesType}) can carry any number of state features ({@link StateFeature}), which are characteristic properties specific
 * for this type of species ({@link Species}). The element {@link StateFeature} of SBML Level 3 Version 1 multi Version 1
 * corresponds to the "state variable" of the SBGN Entity Relationship language. A {@link StateFeature}
 * is identified by an id and an optional name. A {@link StateFeature} is linked to a list of PossibleValues.
 * 
 * @author rodrigue
 *
 */
public class StateFeature extends AbstractNamedSBase {

	ListOf<PossibleValue> listOfPossibleValues;
	
	
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

}
