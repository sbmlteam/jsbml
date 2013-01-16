package org.sbml.jsbml.ext.comp;

import org.sbml.jsbml.LevelVersionError;

public class Deletion extends AbstractNamedSBaseRef {

	
	/**
	 * Creates an Deletion instance 
	 */
	public Deletion() {
		super();
		initDefaults();
	}

	/**
	 * Creates a Deletion instance with an id. 
	 * 
	 * @param id
	 */
	public Deletion(String id) {
		super(id);
		initDefaults();
	}

	/**
	 * Creates a Deletion instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public Deletion(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Creates a Deletion instance with an id, level, and version. 
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Deletion(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a Deletion instance with an id, name, level, and version. 
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public Deletion(String id, String name, int level, int version) {
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
	public Deletion(Deletion obj) {
		super(obj);
	}

	/**
	 * clones this class
	 */
	public Deletion clone() {
		return new Deletion(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		addNamespace(CompConstant.namespaceURI);
		// TODO: init default values here if necessary, e.g.:
		// bar = null;
	}


	public boolean isIdMandatory() {
		return false;
	}
}
