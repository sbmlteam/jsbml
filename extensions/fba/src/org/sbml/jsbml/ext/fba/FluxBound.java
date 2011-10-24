package org.sbml.jsbml.ext.fba;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.FBAParser;

public class FluxBound extends AbstractNamedSBase implements UniqueNamedSBase {

	
	private String reaction;
	private String operation;
	private double value;
	
	private boolean isSetValue = false;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the reaction id
	 * 
	 * @return the reaction id
	 */
	public String getReaction() {
		return reaction;
	}

	/**
	 * Sets the the reaction id
	 * 
	 * @param reaction the reaction id to set
	 */
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	/**
	 * Returns the operation
	 * 
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Returns the value
	 * 
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
		isSetValue = true;
	}
	
	public boolean isSetValue() {
		return isSetValue;
	}
	

	public boolean isIdMandatory() {
		return false;
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
			} else if (attributeName.equals("operation")) {
				 setOperation(value);
			} else if (attributeName.equals("value")) {
				setValue(StringTools.parseSBMLDouble(value));
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
		if (operation != null) {
			attributes.put(FBAParser.shortLabel+ ":operation", getOperation());
		}
		if (isSetValue()) {
			attributes.put(FBAParser.shortLabel+ ":value", StringTools.toString(getValue()));
		}
		if (isSetId()) {
			attributes.remove("id");
			attributes.put(FBAParser.shortLabel+ ":id", getId());
		}
		
		return attributes;
	}


	
}
