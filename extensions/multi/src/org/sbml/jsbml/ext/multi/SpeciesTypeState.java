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
package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class SpeciesTypeState extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7110790636241167977L;

  /**
	 * 
	 */
	private String speciesType;	
	
	/**
	 * 
	 */
	private Integer minOccur;
	
	/**
	 * 
	 */
	private Integer maxOccur;
	
	/**
	 * 
	 */
	private Boolean connex;
	
	/**
	 * 
	 */
	private Boolean saturated;
	
	/**
	 * 
	 */
	private ListOf<StateFeatureInstance> listOfStateFeatureInstances;
	
	/**
	 * 
	 */
	private ListOf<ContainedSpeciesType> listOfContainedSpeciesTypes;
	
	
	public SpeciesTypeState() {
		super();
		initDefaults();
	}
	
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

	/**
	 * Returns the speciesType.
	 * 
	 * @return the speciesType
	 */
	public String getSpeciesType() {
		return speciesType;
	}

	/**
	 * Sets the speciesType.
	 * 
	 * @param speciesType the speciesType to set
	 */
	public void setSpeciesType(String speciesType) {
		System.out.println("setSpeciesType called (" + speciesType + ") !!");
		this.speciesType = speciesType;
	}

	/**
	 * Returns {@code true} if the speciesType is defined.
	 * 
	 * @return {@code true} if the speciesType is defined, {@code false} otherwise.
	 */
	public boolean isSetSpeciesType() {
		return speciesType != null;
	}
	
	/**
	 * Returns the minOccur.
	 * 
	 * @return the minOccur
	 */
	public int getMinOccur() {
		return minOccur;
	}

	/**
	 * Sets the minOccur.
	 * 
	 * @param minOccur the minOccur to set
	 */
	public void setMinOccur(int minOccur) {
		this.minOccur = minOccur;
	}

	/**
	 * Returns {@code true} if minOccur is not {@code null}.
	 * 
	 * @return {@code true} if minOccur is not {@code null}.
	 */
	public boolean isSetMinOccur() {
		return minOccur != null;
	}
	
	
	/**
	 * Returns the maxOccur.
	 * 
	 * @return the maxOccur
	 */
	public int getMaxOccur() {
		return maxOccur;
	}

	/**
	 * Sets the maxOccur.
	 * 
	 * @param maxOccur the maxOccur to set
	 */
	public void setMaxOccur(int maxOccur) {
		System.out.println("setMaxOccur called (" + maxOccur + ") !!");
		this.maxOccur = maxOccur;
	}

	
	/**
	 * Returns {@code true} if maxOccur is not {@code null}.
	 * 
	 * @return {@code true} if maxOccur is not {@code null}.
	 */
	public boolean isSetMaxOccur() {
		return maxOccur != null;
	}

	/**
	 * Returns if this {@link SpeciesTypeState} is connex or not.
	 * 
	 * @return the connex
	 */
	public boolean isConnex() {
		return connex;
	}

	/**
	 * Returns if this {@link SpeciesTypeState} is connex or not.
	 * 
	 * @return the connex
	 */
	public boolean getConnex() {
		return isConnex();
	}

	/**
	 * Sets the connex
	 * 
	 * @param connex the connex to set
	 */
	public void setConnex(boolean connex) {
		this.connex = connex;
	}

	/**
	 * Returns {@code true} if connex is not {@code null}.
	 * 
	 * @return {@code true} if connex is not {@code null}.
	 */
	public boolean isSetConnex() {
		return connex != null;
	}

	/**
	 * Returns if this {@link SpeciesTypeState} is saturated or not.
	 * 
	 * @return the saturated
	 */
	public boolean isSaturated() {
		return saturated;
	}

	/**
	 * Returns if this {@link SpeciesTypeState} is saturated or not.
	 * 
	 * @return the saturated
	 */
	public boolean getSaturated() {
		return isSaturated();
	}

	/**
	 * Sets the saturated.
	 * 
	 * @param saturated the saturated to set
	 */
	public void setSaturated(boolean saturated) {
		this.saturated = saturated;
	}

	/**
	 * Returns {@code true} if saturated is not {@code null}.
	 * 
	 * @return {@code true} if saturated is not {@code null}.
	 */
	public boolean isSetSaturated() {
		return saturated != null;
	}

	/**
	 * Returns the listOfStateFeatureInstances
	 * 
	 * @return the listOfStateFeatureInstances
	 */
	public ListOf<StateFeatureInstance> getListOfStateFeatureInstances() {
		if (listOfStateFeatureInstances == null) {
			listOfStateFeatureInstances = new ListOf<StateFeatureInstance>();
			listOfStateFeatureInstances.setNamespace(MultiConstants.namespaceURI);
			this.registerChild(listOfStateFeatureInstances);
			listOfStateFeatureInstances.setSBaseListType(ListOf.Type.other);
		}
		
		return listOfStateFeatureInstances;
	}

	/**
	 * Adds a StateFeatureInstance.
	 * 
	 * @param stateFeatureInstance the StateFeatureInstance to add
	 */
	public void addStateFeatureInstance(StateFeatureInstance stateFeatureInstance) {
		getListOfStateFeatureInstances().add(stateFeatureInstance);
	}
	
	/**
	 * Creates a new {@link StateFeatureInstance} inside this {@link SpeciesTypeState} and returns it.
	 * <p>
	 * 
	 * @return the {@link StateFeatureInstance} object created
	 *         <p>
	 * @see #addStateFeatureInstance(StateFeatureInstance r)
	 */
	public StateFeatureInstance createStateFeatureInstance() {
		return createStateFeatureInstance(null);
	}

	/**
	 * Creates a new {@link StateFeatureInstance} inside this {@link SpeciesTypeState} and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @return the {@link StateFeatureInstance} object created
	 */
	public StateFeatureInstance createStateFeatureInstance(String id) {
		StateFeatureInstance stateFeatureInstance = new StateFeatureInstance();
		stateFeatureInstance.setId(id);
		addStateFeatureInstance(stateFeatureInstance);

		return stateFeatureInstance;
	}

	/**
	 * Gets the ith {@link StateFeatureInstance}.
	 * 
	 * @param i
	 * 
	 * @return the ith {@link StateFeatureInstance}
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public StateFeatureInstance getStateFeatureInstance(int i) {
		return getListOfStateFeatureInstances().get(i);
	}

	/**
	 * Gets the {@link StateFeatureInstance} that has the given id. 
	 * 
	 * @param id
	 * @return the {@link StateFeatureInstance} that has the given id or null if
	 * no {@link StateFeatureInstance} are found that match {@code id}.
	 */
	public StateFeatureInstance getStateFeatureInstance(String id) {
		if (isSetListOfStateFeatureInstances()) {
			return listOfStateFeatureInstances.firstHit(new NameFilter(id));	    
		} 
		return null;
	}

	/**
	 * Returns {@code true} if the listOfStateFeatureInstance is set.
	 * 
	 * @return {@code true} if the listOfStateFeatureInstance is set.
	 */
	public boolean isSetListOfStateFeatureInstances() {
		if ((listOfStateFeatureInstances == null) || listOfStateFeatureInstances.isEmpty()) {
			return false;			
		}		
		return true;
	}
	
	/**
	 * Sets the listOfStateFeatureInstances to null
	 * 
	 * @return {@code true} is successful
	 */
	public boolean unsetListOfStateFeatureInstances() {
		if (isSetListOfStateFeatureInstances()) {
			// unregister the ids if needed.			  
			this.listOfStateFeatureInstances.fireNodeRemovedEvent();
			this.listOfStateFeatureInstances = null;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the listOfContainedSpeciesTypes
	 * 
	 * @return the listOfContainedSpeciesTypes
	 */
	public ListOf<ContainedSpeciesType> getListOfContainedSpeciesTypes() {
		if (listOfContainedSpeciesTypes == null) {
			listOfContainedSpeciesTypes = new ListOf<ContainedSpeciesType>();
			listOfContainedSpeciesTypes.setNamespace(MultiConstants.namespaceURI);
			this.registerChild(listOfContainedSpeciesTypes);
			listOfContainedSpeciesTypes.setSBaseListType(ListOf.Type.other);
		}
		
		return listOfContainedSpeciesTypes;
	}

	/**
	 * Adds a ContainedSpeciesType.
	 * 
	 * @param containedSpeciesType the ContainedSpeciesType to add
	 */
	public void addContainedSpeciesType(ContainedSpeciesType containedSpeciesType) {
		getListOfContainedSpeciesTypes().add(containedSpeciesType);
	}

	/**
	 * Creates a new {@link ContainedSpeciesType} inside this {@link StateFeature} and returns it.
	 * 
	 * @return the {@link ContainedSpeciesType} object created
	 */
	public ContainedSpeciesType createContainedSpeciesType() {
		ContainedSpeciesType containedSpeciesType = new ContainedSpeciesType();
		addContainedSpeciesType(containedSpeciesType);

		return containedSpeciesType;
	}

	/**
	 * Gets the ith {@link ContainedSpeciesType}.
	 * 
	 * @param i
	 * 
	 * @return the ith {@link ContainedSpeciesType}
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public ContainedSpeciesType getContainedSpeciesType(int i) {
		return getListOfContainedSpeciesTypes().get(i);
	}

	/**
	 * Gets the {@link ContainedSpeciesType} that has the given speciesTypeState id. 
	 * 
	 * @param id
	 * @return the {@link ContainedSpeciesType} that has the given speciesTypeState id or null if
	 * no {@link ContainedSpeciesType} are found that match {@code id}.
	 */
	public ContainedSpeciesType getContainedSpeciesType(final String id) {
		if (isSetListOfContainedSpeciesTypes()) {
			return listOfContainedSpeciesTypes.firstHit(new Filter() {

				public boolean accepts(Object o) {
					if (o instanceof ContainedSpeciesType &&
							((ContainedSpeciesType) o).getSpeciesTypeState().equals(id)) {
						return true;
					}
					return false;
				}
			});	    
		} 
		return null;
	}

	/**
	 * Returns {@code true} if the listOfContainedSpeciesType is set.
	 * 
	 * @return {@code true} if the listOfContainedSpeciesType is set.
	 */
	public boolean isSetListOfContainedSpeciesTypes() {
		if ((listOfContainedSpeciesTypes == null) || listOfContainedSpeciesTypes.isEmpty()) {
			return false;			
		}		
		return true;
	}
	
	/**
	 * Sets the listOfContainedSpeciesTypes to null
	 * 
	 * @return {@code true} is successful
	 */
	public boolean unsetListOfContainedSpeciesTypes() {
		if (isSetListOfContainedSpeciesTypes()) {
			// unregister the ids if needed.			  
			this.listOfContainedSpeciesTypes.fireNodeRemovedEvent();
			this.listOfContainedSpeciesTypes = null;
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void initDefaults() {
		setNamespace(MultiConstants.namespaceURI);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}

		int count = super.getChildCount(), pos = 0;
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		if (isSetListOfStateFeatureInstances()) {
			if (pos == index) {
				return getListOfStateFeatureInstances();
			}
			pos++;
		}
		if (isSetListOfContainedSpeciesTypes()) {
			if (pos == index) {
				return getListOfContainedSpeciesTypes();
			}
			pos++;
		}

		throw new IndexOutOfBoundsException(MessageFormat.format(
		  "Index {0,number,integer} >= {1,number,integer}",
			index, +((int) Math.min(pos, 0))));
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();

		if (isSetListOfStateFeatureInstances()) {
			count++;
		}
		if (isSetListOfContainedSpeciesTypes()) {
			count++;
		}

		return count;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(MultiConstants.minOccur)) {
				setMinOccur(StringTools.parseSBMLInt(value));
			} else if (attributeName.equals(MultiConstants.maxOccur)) {
				setMaxOccur(StringTools.parseSBMLInt(value));
			} else if (attributeName.equals(MultiConstants.connex)) {
				setConnex(StringTools.parseSBMLBoolean(value));
			} else if (attributeName.equals(MultiConstants.saturated)) {
				setSaturated(StringTools.parseSBMLBoolean(value));
			} else if (attributeName.equals(MultiConstants.speciesType)) {
				setSpeciesType(value);
			} else {
				isAttributeRead = false;
			}
		}	  

		return isAttributeRead;

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(MultiConstants.shortLabel+ ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(MultiConstants.shortLabel+ ":name", getName());
		}

		if (isSetMinOccur()) {
			attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.minOccur, Integer.toString(getMinOccur()));
		}
		if (isSetMaxOccur()) {
			attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.maxOccur, Integer.toString(getMaxOccur()));
		}
		if (isSetConnex()) {
			attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.connex, Boolean.toString(isConnex()));
		}
		if (isSetSaturated()) {
			attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.saturated, Boolean.toString(isSaturated()));
		}
		if (isSetSpeciesType()) {
			attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.speciesType, getSpeciesType());
		}


		return attributes;
	}

	// TODO: removeXX unsetXX
}
