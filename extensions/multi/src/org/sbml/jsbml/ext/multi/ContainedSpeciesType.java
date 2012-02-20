package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractSBase;

public class ContainedSpeciesType extends AbstractSBase {

	private String speciesTypeState;
	
	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	/**
	 * Returns the speciesTypeState.
	 * 
	 * @return the speciesTypeState
	 */
	public String getSpeciesTypeState() {
		return speciesTypeState;
	}

	/**
	 * Sets the speciesTypeState.
	 * 
	 * @param speciesTypeState the speciesTypeState to set
	 */
	public void setSpeciesTypeState(String speciesTypeState) {
		this.speciesTypeState = speciesTypeState;
	}

	@Override
	public String toString() {
		// TODO
		return null;
	}

}
