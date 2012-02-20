package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

public class SpeciesTypeState extends AbstractNamedSBase {

	private String speciesType;	
	
	private int minOccur;
	
	private int maxOccur;
	
	private boolean connex;
	
	private boolean saturated;
	
	private ListOf<StateFeatureInstance> listOfStateFeatureInstances;
	
	private ListOf<ContainedSpeciesType> listOfContainedSpeciesTypes;
	
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	/**
	 * Returns the speciesType.
	 * 
	 * @return the speciesType
	 */
	public String getSpeciesType() {
		return speciesType;
	}

	/**
	 * Sets the speciesType.
	 * 
	 * @param speciesType the speciesType to set
	 */
	public void setSpeciesType(String speciesType) {
		this.speciesType = speciesType;
	}

	/**
	 * Returns the minOccur.
	 * 
	 * @return the minOccur
	 */
	public int getMinOccur() {
		return minOccur;
	}

	/**
	 * Sets the minOccur.
	 * 
	 * @param minOccur the minOccur to set
	 */
	public void setMinOccur(int minOccur) {
		this.minOccur = minOccur;
	}

	/**
	 * Returns the maxOccur.
	 * 
	 * @return the maxOccur
	 */
	public int getMaxOccur() {
		return maxOccur;
	}

	/**
	 * Sets the maxOccur.
	 * 
	 * @param maxOccur the maxOccur to set
	 */
	public void setMaxOccur(int maxOccur) {
		this.maxOccur = maxOccur;
	}

	/**
	 * Returns if this {@link SpeciesTypeState} is connex or not.
	 * 
	 * @return the connex
	 */
	public boolean isConnex() {
		return connex;
	}

	/**
	 * Sets the connex
	 * 
	 * @param connex the connex to set
	 */
	public void setConnex(boolean connex) {
		this.connex = connex;
	}

	/**
	 * Returns if this {@link SpeciesTypeState} is saturated or not.
	 * 
	 * @return the saturated
	 */
	public boolean isSaturated() {
		return saturated;
	}

	/**
	 * Sets the saturated.
	 * 
	 * @param saturated the saturated to set
	 */
	public void setSaturated(boolean saturated) {
		this.saturated = saturated;
	}

	/**
	 * @return the listOfStateFeatureInstances
	 */
	public ListOf<StateFeatureInstance> getListOfStateFeatureInstances() {
		return listOfStateFeatureInstances;
	}

	/**
	 * @param listOfStateFeatureInstances the listOfStateFeatureInstances to set
	 */
	public void addStateFeatureInstance(StateFeatureInstance stateFeatureInstance) {
		getListOfStateFeatureInstances().add(stateFeatureInstance);
	}

	/**
	 * @return the listOfContainedSpeciesTypes
	 */
	public ListOf<ContainedSpeciesType> getListOfContainedSpeciesTypes() {
		return listOfContainedSpeciesTypes;
	}

	/**
	 * @param listOfContainedSpeciesTypes the listOfContainedSpeciesTypes to set
	 */
	public void addContainedSpeciesType(ContainedSpeciesType containedSpeciesType) {
		getListOfContainedSpeciesTypes().add(containedSpeciesType);
	}

	// TODO : removeXX unsetXX, isSetXX
}
