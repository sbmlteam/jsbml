package org.sbml.jsbml.ext.qual;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;

public class Output extends AbstractNamedSBase {

	private String qualitativeSpecies;
	
	private OutputTransitionEffect transitionEffect;

	private int level;
	
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
	public OutputTransitionEffect getTransitionEffect() {
		return transitionEffect;
	}

	/**
	 * @param transitionEffect the transitionEffect to set
	 */
	public void setTransitionEffect(OutputTransitionEffect transitionEffect) {
		this.transitionEffect = transitionEffect;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	
	
}
