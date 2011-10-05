package org.sbml.jsbml.ext.qual;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;

public class Input extends AbstractNamedSBase {

	private String qualitativeSpecies;
	
	private InputTransitionEffect transitionEffect;
	
	private int thresholdLevel;
	private String thresholdSymbol;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIdMandatory() {
		return false;
	}

	/**
	 * @return the qualitativeSpecies
	 */
	public String getQualitativeSpecies() {
		return qualitativeSpecies;
	}

	/**
	 * @param qualitativeSpecies the qualitativeSpecies to set
	 */
	public void setQualitativeSpecies(String qualitativeSpecies) {
		this.qualitativeSpecies = qualitativeSpecies;
	}

	/**
	 * @return the transitionEffect
	 */
	public InputTransitionEffect getTransitionEffect() {
		return transitionEffect;
	}

	/**
	 * @param transitionEffect the transitionEffect to set
	 */
	public void setTransitionEffect(InputTransitionEffect inputTransitionEffect) {
		this.transitionEffect = inputTransitionEffect;
	}

	/**
	 * @return the thresholdLevel
	 */
	public int getThresholdLevel() {
		return thresholdLevel;
	}

	/**
	 * @param thresholdLevel the thresholdLevel to set
	 */
	public void setThresholdLevel(int thresholdLevel) {
		this.thresholdLevel = thresholdLevel;
	}

	/**
	 * @return the thresholdSymbol
	 */
	public String getThresholdSymbol() {
		return thresholdSymbol;
	}

	/**
	 * @param thresholdSymbol the thresholdSymbol to set
	 */
	public void setThresholdSymbol(String thresholdSymbol) {
		this.thresholdSymbol = thresholdSymbol;
	}

	
}
