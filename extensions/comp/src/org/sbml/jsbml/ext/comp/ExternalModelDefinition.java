package org.sbml.jsbml.ext.comp;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.UniqueNamedSBase;

public class ExternalModelDefinition extends AbstractNamedSBase implements UniqueNamedSBase 
{

	private String source;
	private String modelRef;

	/**
	 * Creates an ExternalModelDefinition instance 
	 */
	public ExternalModelDefinition() {
		super();
		initDefaults();
	}

	/**
	 * Creates a ExternalModelDefinition instance with an id. 
	 * 
	 * @param id
	 */
	public ExternalModelDefinition(String id) {
		super(id);
		initDefaults();
	}

	/**
	 * Creates a ExternalModelDefinition instance with an id, level, and version. 
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public ExternalModelDefinition(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a ExternalModelDefinition instance with an id, name, level, and version. 
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public ExternalModelDefinition(String id, String name, int level, int version) {
		super(id, name, level, version);
		
		if (getLevelAndVersion().compareTo(Integer.valueOf(CompConstant.MIN_SBML_LEVEL),
				Integer.valueOf(CompConstant.MIN_SBML_VERSION)) < 0) 
		{
			throw new LevelVersionError(getElementName(), level, version);
		}
		initDefaults();
	}

	/**
	 * Clone constructor
	 */
	public ExternalModelDefinition(ExternalModelDefinition obj) {
		super(obj);

		// TODO: copy all class attributes, e.g.:
		// bar = obj.bar;
	}

	/**
	 * clones this class
	 */
	public ExternalModelDefinition clone() {
		return new ExternalModelDefinition(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		addNamespace(CompConstant.namespaceURI);
	}



	/**
	 * @return the value of source
	 */
	public String getSource() {

		if (isSetSource()) {
			return source;
		}

		return "";
	}

	/**
	 * @return whether source is set 
	 */
	public boolean isSetSource() {
		return this.source != null;
	}

	/**
	 * Set the value of source
	 */
	public void setSource(String source) {
		String oldSource = this.source;
		this.source = source;
		firePropertyChange(CompConstant.source, oldSource, this.source);
	}

	/**
	 * Unsets the variable source 
	 * @return {@code true }, if source was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetSource() {
		if (isSetSource()) {
			String oldSource = this.source;
			this.source = null;
			firePropertyChange(CompConstant.source, oldSource, this.source);
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return the value of modelRef
	 */
	public String getModelRef() {

		if (isSetModelRef()) {
			return modelRef;
		}

		return "";
	}

	/**
	 * @return whether modelRef is set 
	 */
	public boolean isSetModelRef() {
		return this.modelRef != null;
	}

	/**
	 * Set the value of modelRef
	 */
	public void setModelRef(String modelRef) {
		String oldModelRef = this.modelRef;
		
		if (modelRef != null && modelRef.trim().length() == 0) {
			modelRef = null;
		}
		this.modelRef = modelRef;
		firePropertyChange(CompConstant.modelRef, oldModelRef, this.modelRef);
	}

	/**
	 * Unsets the variable modelRef 
	 * @return <code>true</code>, if modelRef was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetModelRef() {
		if (isSetModelRef()) {
			String oldModelRef = this.modelRef;
			this.modelRef = null;
			firePropertyChange(CompConstant.modelRef, oldModelRef, this.modelRef);
			return true;
		}
		return false;
	}
	
	
	
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		  if (isSetId()) {
		    attributes.remove("id");
		    attributes.put(CompConstant.shortLabel + ":id", getId());
		  }
		  if (isSetName()) {
			  attributes.remove("name");
			  attributes.put(CompConstant.shortLabel + ":name", getName());
		  }
		  if (isSetSource()) {
			  attributes.put(CompConstant.shortLabel + ":" + CompConstant.source, getSource());
		  }
		  if (isSetModelRef()) {
			  attributes.put(CompConstant.shortLabel + ":" + CompConstant.modelRef, getName());
		  }

		  return attributes;
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(CompConstant.source)) {
				setSource(value);
			} else if (attributeName.equals(CompConstant.modelRef)) {
				setModelRef(value);
			}
			else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}
	

	public boolean isIdMandatory() {
		return true;
	}


}
