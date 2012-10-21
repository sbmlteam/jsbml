package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

public class StateFeatureValue extends AbstractSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3920699098046832296L;
  private String possibleValue;

	public StateFeatureValue() {
		super();
		initDefaults();
	}
	
	@Override
	public AbstractSBase clone() {
		// TODO 
		return null;
	}

	
	/**
	 * Returns the possibleValue
	 * 
	 * @return the possibleValue
	 */
	public String getPossibleValue() {
		return possibleValue;
	}


	/**
	 * Sets the possibleValue
	 * 
	 * @param possibleValue the possibleValue to set
	 */
	public void setPossibleValue(String possibleValue) {
		this.possibleValue = possibleValue;
	}

	/**
	 * Returns true if possibleValue is set.
	 * 
	 * @return true if possibleValue is set.
	 */
	public boolean isSetPossibleValue() {
		return possibleValue != null;
	}

	@Override
	public String toString() {
		// TODO 
		return null;
	}
	
	
	/**
	 * 
	 */
	public void initDefaults() {
		addNamespace(MultiConstant.namespaceURI);
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {

			if (attributeName.equals(MultiConstant.possibleValue)){
				setPossibleValue(value);
				isAttributeRead = true;
			} 
		}	  

		return isAttributeRead;

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetPossibleValue()) {
			attributes.put(MultiConstant.shortLabel + ":" + MultiConstant.possibleValue, getPossibleValue());
		} 

		return attributes;
	}

	
}
