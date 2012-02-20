package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

public class StateFeatureInstance extends AbstractNamedSBase {

	private String stateFeature;
	
	private ListOf<StateFeatureValue> listOfStateFeatureValues;
	
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO 
		return null;
	}

	/**
	 * Returns the stateFeature.
	 * 
	 * @return the stateFeature
	 */
	public String getStateFeature() {
		return stateFeature;
	}

	/**
	 * Sets the stateFeature. 
	 * 
	 * @param stateFeature the stateFeature to set
	 */
	public void setStateFeature(String stateFeature) {
		this.stateFeature = stateFeature;
	}

	/**
	 * @return the listOfStateFeatureValues
	 */
	public ListOf<StateFeatureValue> getListOfStateFeatureValues() {
		return listOfStateFeatureValues;
	}

	/**
	 * @param listOfStateFeatureValues the listOfStateFeatureValues to set
	 */
	public void addStateFeatureValue(StateFeatureValue stateFeatureValue) {
		this.listOfStateFeatureValues.add(stateFeatureValue);
	}
	
	// TODO : removeXX unsetXX, isSetXX

}
