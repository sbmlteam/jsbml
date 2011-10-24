package org.sbml.jsbml.ext.fba;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.FBAParser;

public class FluxObjective extends AbstractSBase {

	private String reaction;
	private double coefficient;
	
	private boolean isSetCoefficient = false;
	
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
		isSetCoefficient = true;
	}
	
	public boolean isSetCoefficient() {
		return isSetCoefficient;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals("reaction")) {
				setReaction(value);
			} else if (attributeName.equals("coefficient")) {
				setCoefficient(StringTools.parseSBMLDouble(value));
			} else {
				isAttributeRead = false;
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
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (reaction != null) {
			attributes.put(FBAParser.shortLabel+ ":reaction", getReaction());			
		}
		if (isSetCoefficient()) {
			attributes.put(FBAParser.shortLabel+ ":coefficient", StringTools.toString(getCoefficient()));
		}
		
		return attributes;
	}

}
