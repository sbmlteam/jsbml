package org.sbml.jsbml.extension.fba;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

public class Objective extends AbstractNamedSBase {

	private String type;
	private ListOf<FluxObjective> listOfFluxObjectives;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the listOfFluxObjectives
	 * 
	 * @return the listOfFluxObjectives
	 */
	public ListOf<FluxObjective> getListOfFluxObjectives() {
		return listOfFluxObjectives;
	}

	/**
	 * @param listOfFluxObjectives the listOfFluxObjectives to set
	 */
	public void addFluxObjective(FluxObjective fluxObjective) {
		this.listOfFluxObjectives.add(fluxObjective);
	}

	public boolean isIdMandatory() {
		return true;
	}
	
	
	
}
