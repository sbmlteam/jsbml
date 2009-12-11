/*
 * $Id: SpeciesReference.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/SpeciesReference.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * Represents the speciesReference XML element of a SBML file.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @author marine
 */
public class SpeciesReference extends SimpleSpeciesReference {

	/**
	 * Represents the 'stoichiometry' XML attribute of this SpeciesReference.
	 */
	private Double stoichiometry;
	/**
	 * Contains the MathML expression for the stoichiometry of this SpeciesReference.
	 */
	private StoichiometryMath stoichiometryMath;
	/**
	 * Represents the 'constant' XML attribute of this SpeciesReference.
	 */
	private Boolean constant;
	
	private int denominator = 1;
	private boolean isSetDenominator = false;

	/**
	 * Creates a SpeciesReference instance. By default, if the level is superior or equal to 3, the constant, stoichiometryMath and stoichiometry are null.
	 * @param spec
	 */
	public SpeciesReference() {
		super();
		this.constant = null;
		this.stoichiometry = null;
		this.stoichiometryMath = null;
		
		if (isSetLevel() && getLevel() < 3){
			initDefaults();
		}
	}

	/**
	 * Creates a SpeciesReference instance from a given SpeciesReference.
	 * @param speciesReference
	 */
	public SpeciesReference(SpeciesReference speciesReference) {
		super(speciesReference);
		if (speciesReference.isSetStoichiometryMath()){
			setStoichiometryMath(speciesReference.getStoichiometryMath().clone());
		}
		else {
			this.stoichiometryMath = null;
		}
		if (speciesReference.isSetStoichiometry()){
			this.stoichiometry = new Double(speciesReference.getStoichiometry());
		}
		else {
			this.stoichiometry = null;
		}
		if (speciesReference.isSetConstant()){
			this.constant = new Boolean(speciesReference.getConstant());
		}
		else {
			this.constant = null;
		}
	}

	/**
	 * Creates a SpeciesReference instance from a Species. By default, if the level is superior or equal to 3, the constant, stoichiometryMath and stoichiometry are null.
	 * @param speciesReference
	 */
	public SpeciesReference(Species species) {
		super(species);
		this.constant = null;
		this.stoichiometry = null;
		this.stoichiometryMath = null;
	}

	
	public SpeciesReference(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	// @Override
	public SpeciesReference clone() {
		return new SpeciesReference(this);
	}

	/**
	 * 
	 * @return the stoichiometry value of this SpeciesReference if it is set, 0 otherwise.
	 */
	public double getStoichiometry() {
		return isSetStoichiometry() ? stoichiometry : 0;
	}

	/**
	 * 
	 * @return the stoichiometryMath of this SpeciesReference. Can be null if oit is not set.
	 */
	public StoichiometryMath getStoichiometryMath() {
		return stoichiometryMath;
	}

	/**
	 * Initialises the default values of this SpeciesReference.
	 */
	// @Override
	public void initDefaults() {
		stoichiometry = new Double(1);
		stoichiometryMath = null;
	}

	/**
	 * 
	 * @return true if the stoichiometryMath of this SpeciesReference is not null.
	 */
	public boolean isSetStoichiometryMath() {
		return stoichiometryMath != null;
	}
	
	/**
	 * 
	 * @return true if the stoichiometry of this SpeciesReference is not null.
	 */
	public boolean isSetStoichiometry(){
		return this.stoichiometry != null && this.stoichiometry != Double.NaN;
	}

	/**
	 * Sets the stoichiometry of this SpeciesReference.
	 * @param stoichiometry
	 */
	public void setStoichiometry(Double stoichiometry) {
		this.stoichiometry = stoichiometry;
		if (isSetStoichiometryMath()){
			stoichiometryMath = null;
		}
		stateChanged();
	}

	/**
	 * Sets the stoichiometryMath of this SpeciesReference.
	 * @param math
	 */
	public void setStoichiometryMath(StoichiometryMath math) {
		this.stoichiometryMath = math;
		setThisAsParentSBMLObject(this.stoichiometryMath);
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof SpeciesReference) {
			SpeciesReference sr = (SpeciesReference) o;
			if ((sr.isSetStoichiometryMath() && !isSetStoichiometryMath())
					|| (!sr.isSetStoichiometryMath() && isSetStoichiometryMath())){
				return false;
			}
			else if (sr.isSetStoichiometryMath() && isSetStoichiometryMath()){
				equal &= sr.getStoichiometryMath().equals(stoichiometryMath);
			}
			if ((sr.isSetStoichiometry() && !isSetStoichiometry())
					|| (!sr.isSetStoichiometry() && isSetStoichiometry())){
				return false;
			}
			else if (sr.isSetStoichiometry() && isSetStoichiometry()){
				equal &= sr.getStoichiometry() == stoichiometry;
			}
			if ((sr.isSetConstant() && !isSetConstant())
					|| (!sr.isSetConstant() && isSetConstant())){
				return false;
			}
			else if (sr.isSetConstant() && isSetConstant()){
				equal &= sr.getConstant() == constant;
			}
			return equal;
		} else
			equal = false;
		return equal;
	}
	
	/**
	 * Sets the constant boolean of this SpeciesReference.
	 * @param constant
	 */
	public void setConstant(boolean constant) {
		this.constant = constant;
	}
	
	/**
	 * @return the value of the constant Boolean if it is set, false otherwise.
	 */
	public boolean isConstant() {
		return isSetConstant() ? constant : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("stochiometry")){
				this.setStoichiometry(Double.parseDouble(value));
				return true;
			}
			else if (attributeName.equals("constant")){
				if (value.equals("true")){
					setConstant(true);
					return true;
				}
				else if (value.equals("false")){
					setConstant(false);
					return true;
				}
			} else if (attributeName.equals("denominator")) {
				setDenominator(denominator);
				return true;
			}
		}
		
		return isAttributeRead;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetStoichiometry()){
			attributes.put("stoichiometry", Double.toString(getStoichiometry()));
		}
		if (isSetConstant()){
			attributes.put("constant", Boolean.toString(getConstant()));
		}
		if (isSetDenominator() && level == 1) {
			attributes.put("denominator", Integer.toString(getDenominator()));
		}

		return attributes;
	}

	private boolean isSetDenominator() {
		return isSetDenominator;
	}

	public int getDenominator() {
		return denominator;
	}
	
	public void setDenominator(int denominator) {
		this.denominator = denominator;
		isSetDenominator = true;
	}
}
