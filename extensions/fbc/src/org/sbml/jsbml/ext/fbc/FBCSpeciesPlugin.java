package org.sbml.jsbml.ext.fbc;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.StringTools;

public class FBCSpeciesPlugin extends AbstractSBasePlugin {

	private int charge;
	private String chemicalFormula;
	
	private boolean isSetCharge;
	
	/**
	 * Creates an FBCSpeciesPlugin instance 
	 */
	public FBCSpeciesPlugin(Species species) {
		super(species);		
		initDefaults();
		
		if (species == null) {
			throw new IllegalArgumentException("The value of the species argument cannot be null.");
		}
	}


	/**
	 * Clone constructor
	 */
	public FBCSpeciesPlugin(FBCSpeciesPlugin obj) {
		super(obj.getExtendedSBase());

		if (obj.isSetChemicalFormula()) {
			setChemicalFormula(obj.getChemicalFormula());
		}
		
		if (obj.isSetCharge()) {
			setCharge(obj.getCharge());
		}
			
	}

	/**
	 * clones this class
	 */
	public FBCSpeciesPlugin clone() {
		return new FBCSpeciesPlugin(this);
	}

	/**
	 * Initializes the default values.
	 */
	public void initDefaults() {}


	
	/**
	 * Returns the value of chemicalFormula
	 *
	 * @return the value of chemicalFormula
	 */
	public String getChemicalFormula() {
		if (isSetChemicalFormula()) {
			return chemicalFormula;
		}
		return null;
	}

	/**
	 * Returns whether chemicalFormula is set 
	 *
	 * @return whether chemicalFormula is set 
	 */
	public boolean isSetChemicalFormula() {
		return this.chemicalFormula != null;
	}

	/**
	 * Sets the value of chemicalFormula
	 */
	public void setChemicalFormula(String chemicalFormula) {
		String oldChemicalFormula = this.chemicalFormula;
		this.chemicalFormula = chemicalFormula;
		firePropertyChange(FBCConstants.chemicalFormula, oldChemicalFormula, this.chemicalFormula);
	}

	/**
	 * Unsets the variable chemicalFormula 
	 *
	 * @return {@code true}, if chemicalFormula was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetChemicalFormula() {
		if (isSetChemicalFormula()) {
			String oldChemicalFormula = this.chemicalFormula;
			this.chemicalFormula = null;
			firePropertyChange(FBCConstants.chemicalFormula, oldChemicalFormula, this.chemicalFormula);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of charge
	 *
	 * @return the value of charge
	 */
	public int getCharge() {
		if (isSetCharge()) {
			return charge;
		}
		
		throw new PropertyUndefinedError(FBCConstants.charge, this);
	}

	/**
	 * Returns whether charge is set 
	 *
	 * @return whether charge is set 
	 */
	public boolean isSetCharge() {
		return isSetCharge;
	}

	/**
	 * Sets the value of charge
	 */
	public void setCharge(int charge) {
		int oldCharge = this.charge;
		this.charge = charge;
		isSetCharge = true;
		firePropertyChange(FBCConstants.charge, oldCharge, this.charge);
	}

	/**
	 * Unsets the variable charge 
	 *
	 * @return {@code true}, if charge was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetCharge() {
		if (isSetCharge()) {
			int oldCharge = this.charge;
			this.charge = 0;
			isSetCharge = false;
			firePropertyChange(FBCConstants.charge, oldCharge, this.charge);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		
		if (attributeName.equals(FBCConstants.charge)) {
			setCharge(StringTools.parseSBMLInt(value));
			return true;
		} else if (attributeName.equals(FBCConstants.chemicalFormula)) {
			setChemicalFormula(value);
			return true;		
		}
		
		return false;
	}

	@Override
	public Map<String, String> writeXMLAttributes() {
		  Map<String, String> attributes = new HashMap<String, String>();;

		  
		  if (isSetCharge) {
			  attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.charge, Integer.toString(getCharge()));
		  }
		  if (isSetChemicalFormula()) {
			  attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.chemicalFormula, getChemicalFormula());
		  }
			
		  return attributes;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}


}
