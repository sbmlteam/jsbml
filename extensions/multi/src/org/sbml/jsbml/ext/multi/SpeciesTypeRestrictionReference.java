package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;

public class SpeciesTypeRestrictionReference extends AbstractSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8803949492166466113L;
  private String speciesTypeRestriction;
		
	
	/**
	 * @return the value of speciesTypeRestriction
	 */
	public String getSpeciesTypeRestriction() {
		if (isSetSpeciesTypeRestriction()) {
			return speciesTypeRestriction;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(MultiConstant.speciesTypeRestriction, this);
	}

	/**
	 * @return whether speciesTypeRestriction is set 
	 */
	public boolean isSetSpeciesTypeRestriction() {
		return this.speciesTypeRestriction != null;
	}

	/**
	 * Set the value of speciesTypeRestriction
	 */
	public void setSpeciesTypeRestriction(String speciesTypeRestriction) {
		String oldSpeciesTypeRestriction = this.speciesTypeRestriction;
		this.speciesTypeRestriction = speciesTypeRestriction;
		firePropertyChange(MultiConstant.speciesTypeRestriction, oldSpeciesTypeRestriction, this.speciesTypeRestriction);
	}

	/**
	 * Unsets the variable speciesTypeRestriction 
	 * @return <code>true</code>, if speciesTypeRestriction was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetSpeciesTypeRestriction() {
		if (isSetSpeciesTypeRestriction()) {
			String oldSpeciesTypeRestriction = this.speciesTypeRestriction;
			this.speciesTypeRestriction = null;
			firePropertyChange(MultiConstant.speciesTypeRestriction, oldSpeciesTypeRestriction, this.speciesTypeRestriction);
			return true;
		}
		return false;
	}


	@Override
	public String toString() {
		// TODO
		return null;
	}
	
	

	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetSpeciesTypeRestriction()) {
			attributes.put(MultiConstant.shortLabel + ":speciesTypeRestriction", getSpeciesTypeRestriction());
		}

		return attributes;
	}

	public boolean readAttribute(String attributeName, String prefix, String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(MultiConstant.speciesTypeRestriction)) {
				setSpeciesTypeRestriction(value);
			}
			else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}

	/**
	 * Creates an SpeciesTypeRestrictionReference instance 
	 */
	public SpeciesTypeRestrictionReference() {
		super();
		initDefaults();
	}

	/**
	 * Creates a SpeciesTypeRestrictionReference instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public SpeciesTypeRestrictionReference(int level, int version) {
		super(level, version);
	}


	/**
	 * Clone constructor
	 */
	public SpeciesTypeRestrictionReference(SpeciesTypeRestrictionReference obj) {
		super(obj);

		// TODO: copy all class attributes, e.g.:
		// bar = obj.bar;
	}

	/**
	 * clones this class
	 */
	public SpeciesTypeRestrictionReference clone() {
		return new SpeciesTypeRestrictionReference(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		addNamespace(MultiConstant.namespaceURI);
	}

	public static final int MIN_SBML_LEVEL = 3;
	public static final int MIN_SBML_VERSION = 1;	
}
