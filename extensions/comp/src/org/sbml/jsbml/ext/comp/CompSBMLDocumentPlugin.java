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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class CompSBMLDocumentPlugin extends CompSBasePlugin {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6693188330172643491L;

	/**
	 * 
	 */
	private ListOf<ModelDefinition> listOfModelDefinitions;
	
	/**
	 * 
	 */
	private ListOf<ExternalModelDefinition> listOfExternalModelDefinitions;
	
	
	public CompSBMLDocumentPlugin(SBMLDocument doc) {
		super(doc);
	}

	public CompSBMLDocumentPlugin(CompSBMLDocumentPlugin compSBMLDocumentPlugin) 
	{
		super(compSBMLDocumentPlugin);
		
		if (compSBMLDocumentPlugin.isSetListOfExternalModelDefinitions()) {
			setListOfExternalModelDefinitions(compSBMLDocumentPlugin.getListOfExternalModelDefinitions().clone());
		}
		if (compSBMLDocumentPlugin.isSetListOfModelDefinitions()) {
			setListOfModelDefinitions(compSBMLDocumentPlugin.getListOfModelDefinitions().clone());
		}
	}

	@Override
	public CompSBMLDocumentPlugin clone() {
		return new CompSBMLDocumentPlugin(this);
	}

	/**
	 * Returns {@code true}, if listOfExternalModelDefinitions contains at least one element.
	 *
	 * @return {@code true}, if listOfExternalModelDefinitions contains at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean isSetListOfExternalModelDefinitions() {
		if ((listOfExternalModelDefinitions == null) || listOfExternalModelDefinitions.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the listOfExternalModelDefinitions. Creates it if it is not already existing.
	 *
	 * @return the listOfExternalModelDefinitions
	 */
	public ListOf<ExternalModelDefinition> getListOfExternalModelDefinitions() {
		if (!isSetListOfExternalModelDefinitions()) {
			listOfExternalModelDefinitions = new ListOf<ExternalModelDefinition>(extendedSBase.getLevel(),
					extendedSBase.getVersion());
			listOfExternalModelDefinitions.addNamespace(CompConstant.namespaceURI);
			listOfExternalModelDefinitions.setSBaseListType(ListOf.Type.other);
			extendedSBase.registerChild(listOfExternalModelDefinitions);
		}
		return listOfExternalModelDefinitions;
	}

	/**
	 * Sets the given {@code ListOf<ExternalModelDefinition>}. If listOfExternalModelDefinitions
	 * was defined before and contains some elements, they are all unset.
	 *
	 * @param listOfExternalModelDefinitions
	 */
	public void setListOfExternalModelDefinitions(ListOf<ExternalModelDefinition> listOfExternalModelDefinitions) {
		unsetListOfExternalModelDefinitions();
		this.listOfExternalModelDefinitions = listOfExternalModelDefinitions;
		extendedSBase.registerChild(this.listOfExternalModelDefinitions);
	}

	/**
	 * Returns {@code true}, if listOfExternalModelDefinitions contain at least one element, 
	 *         otherwise {@code false}
	 *
	 * @return {@code true}, if listOfExternalModelDefinitions contain at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfExternalModelDefinitions() {
		if (isSetListOfExternalModelDefinitions()) {
			ListOf<ExternalModelDefinition> oldExternalModelDefinitions = this.listOfExternalModelDefinitions;
			this.listOfExternalModelDefinitions = null;
			oldExternalModelDefinitions.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Adds a new {@link ExternalModelDefinition} to the listOfExternalModelDefinitions.
	 * <p>The listOfExternalModelDefinitions is initialized if necessary.
	 *
	 * @param externalModelDefinition the element to add to the list
	 * @return true (as specified by {@link Collection.add})
	 */
	public boolean addExternalModelDefinition(ExternalModelDefinition externalModelDefinition) {
		return getListOfExternalModelDefinitions().add(externalModelDefinition);
	}

	/**
	 * Removes an element from the listOfExternalModelDefinitions.
	 *
	 * @param externalModelDefinition the element to be removed from the list
	 * @return true if the list contained the specified element
	 * @see List#remove(Object)
	 */
	public boolean removeExternalModelDefinition(ExternalModelDefinition externalModelDefinition) {
		if (isSetListOfExternalModelDefinitions()) {
			return getListOfExternalModelDefinitions().remove(externalModelDefinition);
		}
		return false;
	}

	/**
	 * Removes an element from the listOfExternalModelDefinitions at the given index.
	 *
	 * @param i the index where to remove the {@link ExternalModelDefinition}
	 * @throws IndexOutOfBoundsException if the listOf is not set or
	 * if the index is out of bound (index < 0 || index > list.size)
	 */
	public void removeExternalModelDefinition(int i) {
		if (!isSetListOfExternalModelDefinitions()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfExternalModelDefinitions().remove(i);
	}

	/**
	 * Removes an element from the listOfExternalModelDefinitions with the given id.
	 *
	 * @param id the id of the {@link ExternalModelDefinition} to remove 
	 */
	public void removeExternalModelDefinition(String id) {
		getListOfExternalModelDefinitions().removeFirst(new NameFilter(id));
	}

	/**
	 * Creates a new ExternalModelDefinition element and adds it to the ListOfExternalModelDefinitions list
	 */
	public ExternalModelDefinition createExternalModelDefinition() {
		return createExternalModelDefinition(null);
	}

	/**
	 * Creates a new {@link ExternalModelDefinition} element and adds it to the ListOfExternalModelDefinitions list
	 *
	 * @return a new {@link ExternalModelDefinition} element
	 */
	public ExternalModelDefinition createExternalModelDefinition(String id) {
		ExternalModelDefinition externalModelDefinition = new ExternalModelDefinition(id, extendedSBase.getLevel(), extendedSBase.getVersion());
		addExternalModelDefinition(externalModelDefinition);
		return externalModelDefinition;
	}

	
	/**
	 * Returns {@code true}, if listOfModelDefinitions contains at least one element.
	 *
	 * @return {@code true}, if listOfModelDefinitions contains at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean isSetListOfModelDefinitions() {
		if ((listOfModelDefinitions == null) || listOfModelDefinitions.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the listOfModelDefinitions. Creates it if it is not already existing.
	 *
	 * @return the listOfModelDefinitions
	 */
	public ListOf<ModelDefinition> getListOfModelDefinitions() {
		if (!isSetListOfModelDefinitions()) {
			listOfModelDefinitions = new ListOf<ModelDefinition>(extendedSBase.getLevel(),
					extendedSBase.getVersion());
			listOfModelDefinitions.addNamespace(CompConstant.namespaceURI);
			listOfModelDefinitions.setSBaseListType(ListOf.Type.other);
			extendedSBase.registerChild(listOfModelDefinitions);
		}
		return listOfModelDefinitions;
	}

	/**
	 * Sets the given {@code ListOf<ModelDefinition>}. If listOfModelDefinitions
	 * was defined before and contains some elements, they are all unset.
	 *
	 * @param listOfModelDefinitions
	 */
	public void setListOfModelDefinitions(ListOf<ModelDefinition> listOfModelDefinitions) {
		unsetListOfModelDefinitions();
		this.listOfModelDefinitions = listOfModelDefinitions;
		extendedSBase.registerChild(this.listOfModelDefinitions);
	}

	/**
	 * Returns {@code true}, if listOfModelDefinitions contain at least one element, 
	 *         otherwise {@code false}
	 *
	 * @return {@code true}, if listOfModelDefinitions contain at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfModelDefinitions() {
		if (isSetListOfModelDefinitions()) {
			ListOf<ModelDefinition> oldModelDefinitions = this.listOfModelDefinitions;
			this.listOfModelDefinitions = null;
			oldModelDefinitions.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Adds a new {@link ModelDefinition} to the listOfModelDefinitions.
	 * <p>The listOfModelDefinitions is initialized if necessary.
	 *
	 * @param modelDefinition the element to add to the list
	 * @return true (as specified by {@link Collection.add})
	 */
	public boolean addModelDefinition(ModelDefinition modelDefinition) {
		return getListOfModelDefinitions().add(modelDefinition);
	}

	/**
	 * Removes an element from the listOfModelDefinitions.
	 *
	 * @param modelDefinition the element to be removed from the list
	 * @return true if the list contained the specified element
	 * @see List#remove(Object)
	 */
	public boolean removeModelDefinition(ModelDefinition modelDefinition) {
		if (isSetListOfModelDefinitions()) {
			return getListOfModelDefinitions().remove(modelDefinition);
		}
		return false;
	}

	/**
	 * Removes an element from the listOfModelDefinitions at the given index.
	 *
	 * @param i the index where to remove the {@link ModelDefinition}
	 * @throws IndexOutOfBoundsException if the listOf is not set or
	 * if the index is out of bound (index < 0 || index > list.size)
	 */
	public void removeModelDefinition(int i) {
		if (!isSetListOfModelDefinitions()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfModelDefinitions().remove(i);
	}

	/**
	 * Removes an element from the listOfModelDefinitions with the given id.
	 *
	 * @param id the id of the {@link ModelDefinition} to remove 
	 */
	public void removeModelDefinition(String id) {
	  getListOfModelDefinitions().removeFirst(new NameFilter(id));
	}

	/**
	 * Creates a new ModelDefinition element and adds it to the ListOfModelDefinitions list
	 */
	public Model createModelDefinition() {
		return createModelDefinition(null);
	}

	/**
	 * Creates a new {@link ModelDefinition} element and adds it to the ListOfModelDefinitions list
	 *
	 * @return a new {@link ModelDefinition} element
	 */
	public Model createModelDefinition(String id) {
		ModelDefinition modelDefinition = new ModelDefinition(id, extendedSBase.getLevel(), extendedSBase.getVersion());
		addModelDefinition(modelDefinition);
		return modelDefinition;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) 
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#writeXMLAttributes()
	 */
	public Map<String, String> writeXMLAttributes() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int childIndex) {
		  if (childIndex < 0) {
			  throw new IndexOutOfBoundsException(childIndex + " < 0");
		  }

		  int pos = 0;
		  if (isSetListOfExternalModelDefinitions()) {
			  if (pos == childIndex) {
				  return getListOfExternalModelDefinitions();
			  }
			  pos++;
		  }
		  if (isSetListOfModelDefinitions()) {
			  if (pos == childIndex) {
				  return getListOfModelDefinitions();
			  }
			  pos++;
		  }
		  throw new IndexOutOfBoundsException(MessageFormat.format(
		    "Index {0,number,integer} >= {1,number,integer}",
				childIndex, +((int) Math.min(pos, 0))));	  
	}

	public int getChildCount() {
		int count = 0;
		
		if (isSetListOfExternalModelDefinitions()) {
			count++;
		}
		if (isSetListOfModelDefinitions()) {
			count++;
		}
		
		return count;
	}

	public boolean getAllowsChildren() {
		return true;
	}


}
