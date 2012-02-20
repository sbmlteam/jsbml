package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractMathContainer;

public class SpeciesTypeInstanceChange extends AbstractMathContainer {

	
	private String speciesTypeInstance;
	
	@Override
	public AbstractMathContainer clone() {
		// TODO 
		return null;
	}

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

}
