package org.sbml.jsbml.ext.fba;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;

public class FluxBound extends AbstractNamedSBase {

	
	private String reaction;
	private String operation;
	private double value;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the reaction id
	 * 
	 * @return the reaction id
	 */
	public String getReaction() {
		return reaction;
	}

	/**
	 * Sets the the reaction id
	 * 
	 * @param reaction the reaction id to set
	 */
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	/**
	 * Returns the operation
	 * 
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Returns the value
	 * 
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	public boolean isIdMandatory() {
		return true;
	}

	
}
