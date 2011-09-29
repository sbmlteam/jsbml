package org.sbml.jsbml.extension.fba;

import org.sbml.jsbml.AbstractSBase;

public class FluxObjective extends AbstractSBase {

	private String reaction;
	private double coefficient;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the reaction
	 */
	public String getReaction() {
		return reaction;
	}
	/**
	 * @param reaction the reaction to set
	 */
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}
	/**
	 * @return the coefficient
	 */
	public double getCoefficient() {
		return coefficient;
	}
	/**
	 * @param coefficient the coefficient to set
	 */
	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	
	
	
}
