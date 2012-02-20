package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractSBase;

public class StateFeatureValue extends AbstractSBase {

	private String possibleValue;

	@Override
	public AbstractSBase clone() {
		// TODO 
		return null;
	}

	
	/**
	 * Returns the possibleValue
	 * 
	 * @return the possibleValue
	 */
	public String getPossibleValue() {
		return possibleValue;
	}


	/**
	 * Sets the possibleValue
	 * 
	 * @param possibleValue the possibleValue to set
	 */
	public void setPossibleValue(String possibleValue) {
		this.possibleValue = possibleValue;
	}


	@Override
	public String toString() {
		// TODO 
		return null;
	}
	
	
}
