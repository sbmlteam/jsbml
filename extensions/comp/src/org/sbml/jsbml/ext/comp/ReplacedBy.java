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

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class ReplacedBy extends SBaseRef {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -7067666288732978615L;

	private String submodelRef;

	/**
	 * Creates a ReplacedBy instance 
	 */
	public ReplacedBy() {
		super();
		initDefaults();
	}

	/**
	 * Creates a ReplacedBy instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public ReplacedBy(int level, int version) {
		super(level, version);
	}

	/**
	 * Clone constructor
	 */
	public ReplacedBy(ReplacedBy obj) {
		super(obj);

		if (obj.isSetSubmodelRef()) {
			setSubmodelRef(obj.getSubmodelRef());
		}
	}

	/**
	 * clones this class
	 */
	public ReplacedBy clone() {
		return new ReplacedBy(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		addNamespace(CompConstant.namespaceURI);
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReplacedBy [submodelRef=" + submodelRef + "]";
	}
	
	
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		  if (isSetSubmodelRef()) {
		    attributes.put(CompConstant.shortLabel + "" + CompConstant.submodelRef, getSubmodelRef());
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

			if (attributeName.equals(CompConstant.submodelRef)) {
				setSubmodelRef(value);
			}
			else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}
}
