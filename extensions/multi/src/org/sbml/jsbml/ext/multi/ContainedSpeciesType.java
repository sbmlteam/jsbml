package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

public class ContainedSpeciesType extends AbstractSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 6818715002582655076L;

  private String speciesTypeState;
	
	public ContainedSpeciesType() {
		super();
		initDefaults();
	}
	
	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	/**
	 * Returns the speciesTypeState.
	 * 
	 * @return the speciesTypeState
	 */
	public String getSpeciesTypeState() {
		return speciesTypeState;
	}

	/**
	 * Sets the speciesTypeState.
	 * 
	 * @param speciesTypeState the speciesTypeState to set
	 */
	public void setSpeciesTypeState(String speciesTypeState) {
		this.speciesTypeState = speciesTypeState;
	}

	/**
	 * Returns true if the speciesTypeState is set.
	 * 
	 * @return true if the speciesTypeState is set.
	 */
	public boolean isSetSpeciesTypeState() {
		return speciesTypeState != null;
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

			if (attributeName.equals(MultiConstant.speciesTypeState)){
				setSpeciesTypeState(value);
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

		if (isSetSpeciesTypeState()) {
			attributes.put(MultiConstant.shortLabel + ":" + MultiConstant.speciesTypeState, getSpeciesTypeState());
		}

		return attributes;
	}

}
