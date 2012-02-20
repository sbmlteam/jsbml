package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;

public class SpeciesTypeRestriction extends AbstractNamedSBase {

	private String speciesTypeInstance;
	
	/**
	 * Returns the speciesTypeInstance.
	 * 
	 * @return the speciesTypeInstance
	 */
	public String getSpeciesTypeInstance() {
		return speciesTypeInstance;
	}

	/**
	 * Sets the speciesTypeInstance.
	 * 
	 * @param speciesTypeInstance the speciesTypeInstance to set
	 */
	public void setSpeciesTypeInstance(String speciesTypeInstance) {
		this.speciesTypeInstance = speciesTypeInstance;
	}
	
	
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

}
