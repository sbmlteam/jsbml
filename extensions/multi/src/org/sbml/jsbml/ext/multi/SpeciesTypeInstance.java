package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

public class SpeciesTypeInstance extends AbstractNamedSBase {

	private double initialAmount;
	
	private double initialConcentration;
	
	private ListOf<SelectorReference> listOfSelectorReferences;
	
	public boolean isIdMandatory() {
		// TODO
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	/**
	 * Returns the initialAmount.
	 * 
	 * @return the initialAmount
	 */
	public double getInitialAmount() {
		return initialAmount;
	}

	/**
	 * Sets the initialAmount.
	 * 
	 * @param initialAmount the initialAmount to set
	 */
	public void setInitialAmount(double initialAmount) {
		this.initialAmount = initialAmount;
	}

	/**
	 * Returns the initialConcentration.
	 * 
	 * @return the initialConcentration
	 */
	public double getInitialConcentration() {
		return initialConcentration;
	}

	/**
	 * Sets the initialConcentration.
	 * 
	 * @param initialConcentration the initialConcentration to set
	 */
	public void setInitialConcentration(double initialConcentration) {
		this.initialConcentration = initialConcentration;
	}

	/**
	 * @return the listOfSelectorReferences
	 */
	public ListOf<SelectorReference> getListOfSelectorReferences() {
		return listOfSelectorReferences;
	}

	/**
	 * @param listOfSelectorReferences the listOfSelectorReferences to set
	 */
	public void addSelectorReference(SelectorReference selectorReference) {
		getListOfSelectorReferences().add(selectorReference);
	}

	
}
