package org.sbml.jsbml.ext.comp;

import org.sbml.jsbml.LevelVersionError;

public class Port extends AbstractNamedSBaseRef {

	/**
	 * Creates an Port instance 
	 */
	public Port() {
		super();
		initDefaults();
	}

	/**
	 * Creates a Port instance with an id. 
	 * 
	 * @param id
	 */
	public Port(String id) {
		super(id);
		initDefaults();
	}

	/**
	 * Creates a Port instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public Port(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Creates a Port instance with an id, level, and version. 
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Port(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a Port instance with an id, name, level, and version. 
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public Port(String id, String name, int level, int version) {
		super(id, name, level, version);
		if (getLevelAndVersion().compareTo(
				Integer.valueOf(CompConstant.MIN_SBML_LEVEL),
				Integer.valueOf(CompConstant.MIN_SBML_VERSION)) < 0) {
			throw new LevelVersionError(getElementName(), level, version);
		}
		initDefaults();
	}

	/**
	 * Clone constructor
	 */
	public Port(Port obj) {
		super(obj);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		addNamespace(CompConstant.namespaceURI);
	}


	public boolean isIdMandatory() {
		return true;
	}

	/**
	 * clones this class
	 */
	public Port clone() {
		return new Port(this);
	}

}
