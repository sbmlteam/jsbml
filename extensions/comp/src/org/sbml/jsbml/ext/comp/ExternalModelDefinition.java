/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.comp;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class ExternalModelDefinition extends AbstractNamedSBase implements UniqueNamedSBase 
{

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 2005205309846284624L;

	private String source;
	private String modelRef;
	private String md5;
	
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
		
		if (getLevelAndVersion().compareTo(Integer.valueOf(CompConstants.MIN_SBML_LEVEL),
				Integer.valueOf(CompConstants.MIN_SBML_VERSION)) < 0) 
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

		if (obj.isSetSource()) {
			setSource(obj.getSource());
		}
		if (obj.isSetModelRef()) {
			setModelRef(obj.getModelRef());
		}
		if (obj.isSetMd5()) {
			setMd5(obj.getMd5());
		}
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
		addNamespace(CompConstants.namespaceURI);
	}



	/**
	 * Returns the value of source or an empty {@link String} if it is not set.
	 * 
	 * @return the value of source or an empty {@link String} if it is not set.
	 */
	public String getSource() {

		if (isSetSource()) {
			return source;
		}

		return "";
	}

	/**
	 * Returns whether source is set
	 * 
	 * @return whether source is set 
	 */
	public boolean isSetSource() {
		return this.source != null;
	}

	/**
	 * Sets the value of source
	 */
	public void setSource(String source) {
		String oldSource = this.source;
		this.source = source;
		firePropertyChange(CompConstants.source, oldSource, this.source);
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
			firePropertyChange(CompConstants.source, oldSource, this.source);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of modelRef or an empty {@link String} if it is not set.
	 *  
	 * @return the value of modelRef or an empty {@link String} if it is not set.
	 */
	public String getModelRef() {

		if (isSetModelRef()) {
			return modelRef;
		}

		return "";
	}

	/**
	 * Returns whether modelRef is set 
	 * 
	 * @return whether modelRef is set 
	 */
	public boolean isSetModelRef() {
		return this.modelRef != null;
	}

	/**
	 * Sets the value of modelRef
	 */
	public void setModelRef(String modelRef) {
		String oldModelRef = this.modelRef;
		
		if (modelRef != null && modelRef.trim().length() == 0) {
			modelRef = null;
		}
		this.modelRef = modelRef;
		firePropertyChange(CompConstants.modelRef, oldModelRef, this.modelRef);
	}

	/**
	 * Unsets the variable modelRef 
	 * @return {@code true}, if modelRef was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetModelRef() {
		if (isSetModelRef()) {
			String oldModelRef = this.modelRef;
			this.modelRef = null;
			firePropertyChange(CompConstants.modelRef, oldModelRef, this.modelRef);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of md5 or an empty {@link String} if it is not set.
	 *
	 * @return the value of md5 or an empty {@link String} if it is not set.
	 */
	public String getMd5() {

		if (isSetMd5()) {
			return md5;
		}

		return "";
	}

	/**
	 * Returns whether md5 is set 
	 *
	 * @return whether md5 is set 
	 */
	public boolean isSetMd5() {
		return this.md5 != null;
	}

	/**
	 * Sets the value of md5
	 */
	public void setMd5(String md5) {
		String oldMd5 = this.md5;
		
		if (md5 != null && md5.trim().length() == 0) {
			md5 = null;
		}
		
		this.md5 = md5;
		firePropertyChange(CompConstants.md5, oldMd5, this.md5);
	}

	/**
	 * Unsets the variable md5 
	 *
	 * @return {@code true}, if md5 was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetMd5() {
		if (isSetMd5()) {
			String oldMd5 = this.md5;
			this.md5 = null;
			firePropertyChange(CompConstants.md5, oldMd5, this.md5);
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		  if (isSetId()) {
		    attributes.remove("id");
		    attributes.put(CompConstants.shortLabel + ":id", getId());
		  }
		  if (isSetName()) {
			  attributes.remove("name");
			  attributes.put(CompConstants.shortLabel + ":name", getName());
		  }
		  if (isSetSource()) {
			  attributes.put(CompConstants.shortLabel + ":" + CompConstants.source, getSource());
		  }
		  if (isSetModelRef()) {
			  attributes.put(CompConstants.shortLabel + ":" + CompConstants.modelRef, getModelRef());
		  }
		  if (isSetMd5()) {
			  attributes.put(CompConstants.shortLabel + ":" + CompConstants.md5, getMd5());
		  }

		  return attributes;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(CompConstants.source)) {
				setSource(value);
			} else if (attributeName.equals(CompConstants.modelRef)) {
				setModelRef(value);
			} else if (attributeName.equals(CompConstants.md5)) {
				setMd5(value);
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
