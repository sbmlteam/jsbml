package org.sbml.jsbml.ext.comp;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

public class ReplacedElement extends SBaseRef {

	private String submodelRef;
	
	private String deletion;
	
	private String conversionFactor;

	
	public ReplacedElement(int level, int version) {
		super(level, version);
		// TODO Auto-generated constructor stub
	}


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
	 * Returns the value of submodelRef
	 * 
	 * @return the value of submodelRef
	 */
	public String getSubmodelRef() {

		if (isSetSubmodelRef()) {
			return submodelRef;
		}

		return "";
	}

	/**
	 * Returns whether submodelRef is set
	 *  
	 * @return whether submodelRef is set 
	 */
	public boolean isSetSubmodelRef() {
		return this.submodelRef != null;
	}

	/**
	 * Sets the value of submodelRef
	 */
	public void setSubmodelRef(String submodelRef) {
		String oldSubmodelRef = this.submodelRef;
		this.submodelRef = submodelRef;
		firePropertyChange(CompConstant.submodelRef, oldSubmodelRef, this.submodelRef);
	}

	/**
	 * Unsets the variable submodelRef 
	 * @return {@code true}, if submodelRef was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetSubmodelRef() {
		if (isSetSubmodelRef()) {
			String oldSubmodelRef = this.submodelRef;
			this.submodelRef = null;
			firePropertyChange(CompConstant.submodelRef, oldSubmodelRef, this.submodelRef);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of deletion
	 *
	 * @return the value of deletion
	 */
	public String getDeletion() {

		if (isSetDeletion()) {
			return deletion;
		}

		return "";
	}

	/**
	 * Returns whether deletion is set 
	 *
	 * @return whether deletion is set 
	 */
	public boolean isSetDeletion() {
		return this.deletion != null;
	}

	/**
	 * Sets the value of deletion
	 */
	public void setDeletion(String deletion) {
		String oldDeletion = this.deletion;
		this.deletion = deletion;
		firePropertyChange(CompConstant.deletion, oldDeletion, this.deletion);
	}

	/**
	 * Unsets the variable deletion 
	 *
	 * @return {@code true}, if deletion was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetDeletion() {
		if (isSetDeletion()) {
			String oldDeletion = this.deletion;
			this.deletion = null;
			firePropertyChange(CompConstant.deletion, oldDeletion, this.deletion);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of conversionFactor
	 *
	 * @return the value of conversionFactor
	 */
	public String getConversionFactor() {

		if (isSetConversionFactor()) {
			return conversionFactor;
		}

		return "";
	}

	/**
	 * Returns whether conversionFactor is set 
	 *
	 * @return whether conversionFactor is set 
	 */
	public boolean isSetConversionFactor() {
		return this.conversionFactor != null;
	}

	/**
	 * Sets the value of conversionFactor
	 */
	public void setConversionFactor(String conversionFactor) {
		String oldConversionFactor = this.conversionFactor;
		this.conversionFactor = conversionFactor;
		firePropertyChange(CompConstant.conversionFactor, oldConversionFactor, this.conversionFactor);
	}

	/**
	 * Unsets the variable conversionFactor 
	 *
	 * @return {@code true}, if conversionFactor was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetConversionFactor() {
		if (isSetConversionFactor()) {
			String oldConversionFactor = this.conversionFactor;
			this.conversionFactor = null;
			firePropertyChange(CompConstant.conversionFactor, oldConversionFactor, this.conversionFactor);
			return true;
		}
		return false;
	}
	
	
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetSubmodelRef()) {
			attributes.put(CompConstant.shortLabel + ":" + CompConstant.submodelRef, getSubmodelRef());
		}
		if (isSetDeletion()) {
			attributes.put(CompConstant.shortLabel + ":" + CompConstant.deletion, getDeletion());
		}
		if (isSetConversionFactor()) {
			attributes.put(CompConstant.shortLabel + ":" + CompConstant.conversionFactor, getConversionFactor());
		}
		
		return attributes;
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(CompConstant.deletion)) {
				setDeletion(value);
			}
			else if (attributeName.equals(CompConstant.submodelRef)) {
				setSubmodelRef(value);
			}
			else if (attributeName.equals(CompConstant.conversionFactor)) {
				setConversionFactor(value);
			}
			else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}
}
