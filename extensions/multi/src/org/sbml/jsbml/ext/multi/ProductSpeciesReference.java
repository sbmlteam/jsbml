package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.SpeciesReference;

public class ProductSpeciesReference extends SpeciesReference {

	// TODO : check if it is a new element or a plugin to speciesReference
	
	private String correspondingReactant;

	/**
	 * Returns the corresponding reactant.
	 * 
	 * @return the correspondingReactant
	 */
	public String getCorrespondingReactant() {
		return correspondingReactant;
	}

	/**
	 * Sets the corresponding reactant.
	 * 
	 * @param correspondingReactant the correspondingReactant to set
	 */
	public void setCorrespondingReactant(String correspondingReactant) {
		this.correspondingReactant = correspondingReactant;
	}
	
	
	
}
