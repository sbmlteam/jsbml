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
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author
 * @version $Rev$
 * @since 1.0
 * @date 27.10.2011
 */
public class Objective extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
   * 
   */
  private static final long serialVersionUID = -3466570440778373634L;
  private String type;
	private ListOf<FluxObjective> listOfFluxObjectives = new ListOf<FluxObjective>();
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
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
	 * Sets the type.
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the listOfFluxObjectives
	 * 
	 * @return the listOfFluxObjectives
	 */
	public ListOf<FluxObjective> getListOfFluxObjectives() {
		return listOfFluxObjectives;
	}

	/**
	 * @param listOfFluxObjectives the listOfFluxObjectives to set
	 */
	public void addFluxObjective(FluxObjective fluxObjective) {
		this.listOfFluxObjectives.add(fluxObjective);
	}

	// TODO : not sure why, but I had to make both Objective and FluxBound without mandatory ID, otherwise the parsing would fail !!!
	public boolean isIdMandatory() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
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

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (type != null) {
			attributes.put(FBCConstant.shortLabel+ ":type", getType());			
		}
		if (isSetId()) {
			attributes.remove("id");
			attributes.put(FBCConstant.shortLabel+ ":id", getId());
		}
		
		return attributes;
	}

	/**
	 * @param index
	 * @return
	 * @see org.sbml.jsbml.ListOf#getChildAt(int)
	 */
	public TreeNode getChildAt(int index) {
		if (index == 0 && listOfFluxObjectives.size() > 0) {
			return listOfFluxObjectives;
		}
		
		return null;
	}

	/**
	 * @return
	 * @see org.sbml.jsbml.ListOf#getChildCount()
	 */
	public int getChildCount() {
		if (listOfFluxObjectives.size() > 0) {
			return 1;
		}
		
		return 0;
	}

	
	
}
