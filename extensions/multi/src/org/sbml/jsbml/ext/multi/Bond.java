package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;

public class Bond extends AbstractSBase {

	public enum BOND_OCCURENCE_TYPE {prohibited, allowed, required};
	
	private BOND_OCCURENCE_TYPE occurence;
	
	private BindingSiteReference bindingSite1;
	
	private BindingSiteReference bindingSite2;
	
	@Override
	public AbstractSBase clone() {
		// TODO 
		return null;
	}

	
	/**
	 * @return the occurence
	 */
	public BOND_OCCURENCE_TYPE getOccurence() {
		return occurence;
	}


	/**
	 * @param occurence the occurence to set
	 */
	public void setOccurence(BOND_OCCURENCE_TYPE occurence) {
		this.occurence = occurence;
	}


	/**
	 * @return the bindingSite1
	 */
	public BindingSiteReference getBindingSite1() {
		return bindingSite1;
	}


	/**
	 * @param bindingSite1 the bindingSite1 to set
	 */
	public void setBindingSite1(BindingSiteReference bindingSite1) {
		this.bindingSite1 = bindingSite1;
	}


	/**
	 * @return the bindingSite2
	 */
	public BindingSiteReference getBindingSite2() {
		return bindingSite2;
	}


	/**
	 * @param bindingSite2 the bindingSite2 to set
	 */
	public void setBindingSite2(BindingSiteReference bindingSite2) {
		this.bindingSite2 = bindingSite2;
	}


	@Override
	public String toString() {
		// TODO
		return null;
	}

	
}
