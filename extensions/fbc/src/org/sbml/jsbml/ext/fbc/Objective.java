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
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 27.10.2011
 */
public class Objective extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
   * 
   */
  private static final long serialVersionUID = -3466570440778373634L;
  private ListOf<FluxObjective> listOfFluxObjectives;
	private String type; // TODO make it an Enumeration
	

	/**
	 * Creates an Objective instance 
	 */
	public Objective() {
		super();
		initDefaults();
	}

	/**
	 * Creates a Objective instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public Objective(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Clone constructor
	 */
	public Objective(Objective obj) {
		super(obj);

		// TODO: copy all class attributes, e.g.:
		// bar = obj.bar;
	}

	/**
	 * Creates a Objective instance with an id. 
	 * 
	 * @param id
	 */
	public Objective(String id) {
		super(id);
		initDefaults();
	}

	/**
	 * Creates a Objective instance with an id, level, and version. 
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Objective(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a Objective instance with an id, name, level, and version. 
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public Objective(String id, String name, int level, int version) {
		super(id, name, level, version);
		if (getLevelAndVersion().compareTo(
				Integer.valueOf(FBCConstants.MIN_SBML_LEVEL),
				Integer.valueOf(FBCConstants.MIN_SBML_VERSION)) < 0) {
			throw new LevelVersionError(getElementName(), level, version);
		}
		initDefaults();
	}

	/**
	 * @param listOfFluxObjectives the listOfFluxObjectives to set
	 */
	public void addFluxObjective(FluxObjective fluxObjective) {
		getListOfFluxObjectives().add(fluxObjective);
	}

	/**
	 * clones this class
	 */
	public Objective clone() {
		return new Objective(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if ((index == 0) && isSetListOfFluxObjectives()) {
			return listOfFluxObjectives;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return isSetListOfFluxObjectives() ? 1 : 0;
	}

	/**
	 * Returns the listOfFluxObjectives
	 * 
	 * @return the listOfFluxObjectives
	 */
	public ListOf<FluxObjective> getListOfFluxObjectives() {
	  if (!isSetListOfFluxObjectives()) {
	    listOfFluxObjectives = new ListOf<FluxObjective>(getLevel(), getVersion());
	    listOfFluxObjectives.addNamespace(FBCConstants.namespaceURI);
	    listOfFluxObjectives.setSBaseListType(ListOf.Type.other);
      registerChild(listOfFluxObjectives);
    }
		return listOfFluxObjectives;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

  /**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		addNamespace(FBCConstants.namespaceURI);
		// TODO: init default values here if necessary, e.g.:
		// bar = null;
	}

	public boolean isIdMandatory() {
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfFluxObjectives() {
    return (listOfFluxObjectives != null) && (listOfFluxObjectives.size() > 0);
  }
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals("type")) {
				setType(value);
			} else {
				isAttributeRead = false;
			}
			
		}
		
		return isAttributeRead;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (type != null) {
			attributes.put(FBCConstants.shortLabel + ":type", getType());			
		}
		if (isSetId()) {
			attributes.remove("id");
			attributes.put(FBCConstants.shortLabel + ":id", getId());
		}
		// TODO: take care of id and name properly
		
		return attributes;
	}
	
}
