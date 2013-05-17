package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;

public class SpeciesTypeRestriction extends AbstractNamedSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6703552149441215128L;
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
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
	//@Override
	public boolean isIdMandatory() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}
		
}
