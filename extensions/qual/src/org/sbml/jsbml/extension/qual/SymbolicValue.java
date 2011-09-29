package org.sbml.jsbml.extension.qual;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;

public class SymbolicValue extends AbstractNamedSBase {

	private int rank;
	
	public boolean isIdMandatory() {
		return true;
	}

	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	// TODO : add the unset methods
}
