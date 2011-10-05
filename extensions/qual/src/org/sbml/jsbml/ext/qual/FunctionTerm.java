package org.sbml.jsbml.ext.qual;

import org.sbml.jsbml.AbstractMathContainer;

public class FunctionTerm extends AbstractMathContainer {

	private int resultLevel;
	private String resultSymbol;
	
	private double temporisationValue;
	private TemporisationMath temporisationMath;
	
	private boolean defaultTerm;
	
	@Override
	public AbstractMathContainer clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the resultLevel
	 */
	public int getResultLevel() {
		return resultLevel;
	}

	/**
	 * @param resultLevel the resultLevel to set
	 */
	public void setResultLevel(int resultLevel) {
		this.resultLevel = resultLevel;
	}

	/**
	 * @return the resultSymbol
	 */
	public String getResultSymbol() {
		return resultSymbol;
	}

	/**
	 * @param resultSymbol the resultSymbol to set
	 */
	public void setResultSymbol(String resultSymbol) {
		this.resultSymbol = resultSymbol;
	}

	/**
	 * @return the temporisationValue
	 */
	public double getTemporisationValue() {
		return temporisationValue;
	}

	/**
	 * @param temporisationValue the temporisationValue to set
	 */
	public void setTemporisationValue(double temporisationValue) {
		this.temporisationValue = temporisationValue;
	}

	/**
	 * @return the temporisationMath
	 */
	public TemporisationMath getTemporisationMath() {
		return temporisationMath;
	}

	/**
	 * @param temporisationMath the temporisationMath to set
	 */
	public void setTemporisationMath(TemporisationMath temporisationMath) {
		this.temporisationMath = temporisationMath;
	}

	/**
	 * @return the defaultTerm
	 */
	public boolean isDefaultTerm() {
		return defaultTerm;
	}

	/**
	 * @param defaultTerm the defaultTerm to set
	 */
	public void setDefaultTerm(boolean defaultTerm) {
		this.defaultTerm = defaultTerm;
	}
	
	
	
}
