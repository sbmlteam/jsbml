package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;

/**
 * Each state feature ({@link StateFeature}) also requires the definition of all the possible values it can take. Those values
 * will be used within a selector, to define the states an entity is allowed to take. A state feature is
 * not obligatory a boolean property, but can carry any number of {@link PossibleValue}. A {@link PossibleValue}
 * is identified by an id and an optional name.
 * 
 * 
 * @author rodrigue
 *
 */
public class PossibleValue extends AbstractNamedSBase {

	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

}
