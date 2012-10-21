package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractMathContainer;

public class SpeciesTypeInstanceChange extends AbstractMathContainer {

	
	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 6354095852211081835L;
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
