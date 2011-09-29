package org.sbml.jsbml.extension.qual;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

public class QualitativeSpecies extends AbstractNamedSBase {

	private String compartment;
	private boolean boundaryCondition;

	private boolean constant; // TODO : extends/implements the jsbml interface that has the constant attribute.
	
	private int initialLevel;
	private int maxLevel;

	private ListOf<SymbolicValue> listOfSymbolicValues = new ListOf<SymbolicValue>();
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIdMandatory() {
		return true;
	}

	/**
	 * @return the compartment
	 */
	public String getCompartment() {
		return compartment;
	}

	/**
	 * @param compartment the compartment to set
	 */
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	/**
	 * @return the boundaryCondition
	 */
	public boolean isBoundaryCondition() {
		return boundaryCondition;
	}

	/**
	 * @param boundaryCondition the boundaryCondition to set
	 */
	public void setBoundaryCondition(boolean boundaryCondition) {
		this.boundaryCondition = boundaryCondition;
	}

	/**
	 * @return the constant
	 */
	public boolean isConstant() {
		return constant;
	}

	/**
	 * @param constant the constant to set
	 */
	public void setConstant(boolean constant) {
		this.constant = constant;
	}

	/**
	 * @return the initialLevel
	 */
	public int getInitialLevel() {
		return initialLevel;
	}

	/**
	 * @param initialLevel the initialLevel to set
	 */
	public void setInitialLevel(int initialLevel) {
		this.initialLevel = initialLevel;
	}

	/**
	 * @return the maxLevel
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * @param maxLevel the maxLevel to set
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	
	// TODO : add all the necessary methods to manipulate the list
	
	/**
	 * @return the listOfSymbolicValues
	 */
	public ListOf<SymbolicValue> getListOfSymbolicValues() {
		return listOfSymbolicValues;
	}

	/**
	 * @param listOfSymbolicValues the listOfSymbolicValues to set
	 */
	public void addSymbolicValue(SymbolicValue symbolicValue) {
		this.listOfSymbolicValues.add(symbolicValue);
	}

	
	// TODO : add the unset methods
	
}
