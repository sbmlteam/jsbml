package org.sbml.jsbml.ext.groups;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;

public class MemberConstraint extends AbstractNamedSBase {

	private String identicalAttribute;
	private String distinctAttribute;
	
	/**
	 * Creates an MemberConstraint instance 
	 */
	public MemberConstraint() {
		super();
		initDefaults();
	}

	/**
	 * Creates a MemberConstraint instance with an id. 
	 * 
	 * @param id
	 */
	public MemberConstraint(String id) {
		super(id);
		initDefaults();
	}

	/**
	 * Creates a MemberConstraint instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public MemberConstraint(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Creates a MemberConstraint instance with an id, level, and version. 
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public MemberConstraint(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a MemberConstraint instance with an id, name, level, and version. 
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public MemberConstraint(String id, String name, int level, int version) {
		super(id, name, level, version);
		if (getLevelAndVersion().compareTo(
				Integer.valueOf(GroupConstant.MIN_SBML_LEVEL),
				Integer.valueOf(GroupConstant.MIN_SBML_VERSION)) < 0) {
			throw new LevelVersionError(getElementName(), level, version);
		}
		initDefaults();
	}

	/**
	 * Clone constructor
	 */
	public MemberConstraint(MemberConstraint obj) {
		super(obj);

		if (obj.isSetIdenticalAttribute()) {
			setIdenticalAttribute(obj.getIdenticalAttribute());
		}
		if (obj.isSetDistinctAttribute()) {
			setDistinctAttribute(obj.getDistinctAttribute());
		}
	}

	/**
	 * Clones this instance of {@link MemberConstraint}
	 */
	public MemberConstraint clone() {
		return new MemberConstraint(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		addNamespace(GroupConstant.namespaceURI);
	}


	@Override
	public boolean isIdMandatory() {
		return false;
	}

	
	/**
	 * Returns the value of identicalAttribute
	 *
	 * @return the value of identicalAttribute
	 */
	public String getIdenticalAttribute() {
		if (isSetIdenticalAttribute()) {
			return identicalAttribute;
		}

		return null;
	}

	/**
	 * Returns whether identicalAttribute is set 
	 *
	 * @return whether identicalAttribute is set 
	 */
	public boolean isSetIdenticalAttribute() {
		return this.identicalAttribute != null;
	}

	/**
	 * Sets the value of identicalAttribute
	 */
	public void setIdenticalAttribute(String identicalAttribute) {
		String oldIdenticalAttribute = this.identicalAttribute;
		this.identicalAttribute = identicalAttribute;
		firePropertyChange(GroupConstant.identicalAttribute, oldIdenticalAttribute, this.identicalAttribute);
	}

	/**
	 * Unsets the variable identicalAttribute 
	 *
	 * @return {@code true}, if identicalAttribute was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetIdenticalAttribute() {
		if (isSetIdenticalAttribute()) {
			String oldIdenticalAttribute = this.identicalAttribute;
			this.identicalAttribute = null;
			firePropertyChange(GroupConstant.identicalAttribute, oldIdenticalAttribute, this.identicalAttribute);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of distinctAttribute
	 *
	 * @return the value of distinctAttribute
	 */
	public String getDistinctAttribute() {
		if (isSetDistinctAttribute()) {
			return distinctAttribute;
		}
		
		return null;
	}

	/**
	 * Returns whether distinctAttribute is set 
	 *
	 * @return whether distinctAttribute is set 
	 */
	public boolean isSetDistinctAttribute() {
		return this.distinctAttribute != null;
	}

	/**
	 * Sets the value of distinctAttribute
	 */
	public void setDistinctAttribute(String distinctAttribute) {
		String oldDistinctAttribute = this.distinctAttribute;
		this.distinctAttribute = distinctAttribute;
		firePropertyChange(GroupConstant.distinctAttribute, oldDistinctAttribute, this.distinctAttribute);
	}

	/**
	 * Unsets the variable distinctAttribute 
	 *
	 * @return {@code true}, if distinctAttribute was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetDistinctAttribute() {
		if (isSetDistinctAttribute()) {
			String oldDistinctAttribute = this.distinctAttribute;
			this.distinctAttribute = null;
			firePropertyChange(GroupConstant.distinctAttribute, oldDistinctAttribute, this.distinctAttribute);
			return true;
		}
		return false;
	}
	
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(GroupConstant.shortLabel + ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(GroupConstant.shortLabel + ":name", getName());
		}
		if (isSetIdenticalAttribute()) {
			attributes.put(GroupConstant.shortLabel + ":" + GroupConstant.identicalAttribute, getIdenticalAttribute());
		}
		if (isSetDistinctAttribute()) {
			attributes.put(GroupConstant.shortLabel + ":" + GroupConstant.distinctAttribute, getDistinctAttribute());
		}
		
		return attributes;
	}

	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(GroupConstant.identicalAttribute)) {
				setIdenticalAttribute(value);
			} else if (attributeName.equals(GroupConstant.distinctAttribute)) {
				setDistinctAttribute(value);
			} else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}
}
