package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractSBase;

public class SpeciesTypeRestrictionReference extends AbstractSBase {

	private String speciesTypeRestriction;
	
	
	/**
	 * Returns the speciesTypeRestriction.
	 * 
	 * @return the speciesTypeRestriction
	 */
	public String getSpeciesTypeRestriction() {
		return speciesTypeRestriction;
	}

	/**
	 * Sets the speciesTypeRestriction.
	 * 
	 * @param speciesTypeRestriction the speciesTypeRestriction to set
	 */
	public void setSpeciesTypeRestriction(String speciesTypeRestriction) {
		this.speciesTypeRestriction = speciesTypeRestriction;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	@Override
	public String toString() {
		// TODO
		return null;
	}

}
