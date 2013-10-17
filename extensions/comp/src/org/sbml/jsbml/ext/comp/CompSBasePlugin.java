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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class CompSBasePlugin extends AbstractSBasePlugin {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 3364911411091523856L;

	ListOf<ReplacedElement> listOfReplacedElements;
	
	ReplacedBy replacedBy;
	
	/**
	 * Creates an CompSBasePlugin instance 
	 */
	public CompSBasePlugin() {
		super();
		initDefaults();
	}

	/**
	 * Creates a CompSBasePlugin instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public CompSBasePlugin(SBase extendedSBase) {
		super(extendedSBase);
	}


	/**
	 * Clone constructor
	 */
	public CompSBasePlugin(CompSBasePlugin obj) {
		super(obj.getExtendedSBase());
		
		if (obj.isSetListOfReplacedElements()) {
			setListOfReplacedElements(obj.getListOfReplacedElements().clone());
		}
		if (obj.isSetReplacedBy()) {
			setReplacedBy((ReplacedBy) obj.getReplacedBy().clone());
		}
	}

	/**
	 * clones this class
	 */
	public CompSBasePlugin clone() {
		return new CompSBasePlugin(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {

	}



	/**
	 * Returns the value of replacedBy
	 * @return the value of replacedBy
	 */
	public ReplacedBy getReplacedBy() {
		if (isSetReplacedBy()) {
			return replacedBy;
		}

		return null;
	}

	/**
	 * Returns whether replacedBy is set 
	 * @return whether replacedBy is set 
	 */
	public boolean isSetReplacedBy() {
		return this.replacedBy != null;
	}

	/**
	 * Sets the value of replacedBy
	 */
	public void setReplacedBy(ReplacedBy replacedBy) {
		ReplacedBy oldReplacedBy = this.replacedBy;
		this.replacedBy = replacedBy;
		extendedSBase.registerChild(replacedBy);
		firePropertyChange(CompConstant.replacedBy, oldReplacedBy, this.replacedBy);
	}

	/**
	 * Creates a new {@link ReplacedBy} element and sets it in this {@link CompSBasePlugin}.
	 *
	 * @return a new {@link ReplacedBy} element.
	 */
	public ReplacedBy createReplacedBy() {
		ReplacedBy replacedBy = new ReplacedBy(extendedSBase.getLevel(), extendedSBase.getVersion());
		setReplacedBy(replacedBy);
		return replacedBy;
	}

	/**
	 * Unsets the variable replacedBy 
	 * @return {@code true}, if replacedBy was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetReplacedBy() {
		if (isSetReplacedBy()) {
			ReplacedBy oldReplacedBy = this.replacedBy;
			this.replacedBy = null;
			firePropertyChange(CompConstant.replacedBy, oldReplacedBy, this.replacedBy);
			return true;
		}
		return false;
	}	

	
	/**
	 * Returns {@code true}, if listOfReplacedElements contains at least one element, 
	 *         otherwise {@code false}
	 *         
	 * @return {@code true}, if listOfReplacedElements contains at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean isSetListOfReplacedElements() {
		if ((listOfReplacedElements == null) || listOfReplacedElements.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the listOfReplacedElements
	 * 
	 * @return the listOfReplacedElements
	 */
	public ListOf<ReplacedElement> getListOfReplacedElements() {
		if (!isSetListOfReplacedElements()) {
			listOfReplacedElements = new ListOf<ReplacedElement>(extendedSBase.getLevel(), extendedSBase.getVersion());
			listOfReplacedElements.addNamespace(CompConstant.namespaceURI);
			listOfReplacedElements.setSBaseListType(ListOf.Type.other);
			extendedSBase.registerChild(listOfReplacedElements);
		}
		return listOfReplacedElements;
	}

	/**
	 * Sets the listOfReplacedElements. If there was already some elements defined 
	 * on listOfReplacedElements, the will be unset beforehand.
	 * 
	 * @param listOfReplacedElements
	 */
	public void setListOfReplacedElements(ListOf<ReplacedElement> listOfReplacedElements) {
		unsetListOfReplacedElements();
		this.listOfReplacedElements = listOfReplacedElements;
		extendedSBase.registerChild(this.listOfReplacedElements);
	}

	/**
	 * Returns {@code true}, if listOfReplacedElements contained at least one element, 
	 *         otherwise {@code false}
	 *         
	 * @return {@code true}, if listOfReplacedElements contained at least one element, 
	 *         otherwise {@code false}
	 */
	public boolean unsetListOfReplacedElements() {
		if (isSetListOfReplacedElements()) {
			ListOf<ReplacedElement> oldReplacedElements = this.listOfReplacedElements;
			this.listOfReplacedElements = null;
			oldReplacedElements.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Adds a new element to listOfReplacedElements.
	 * <p>listOfReplacedElements is initialized if necessary.
	 * 
	 * @param replacedElement
	 * @return true (as specified by {@link Collection.add})
	 */
	public boolean addReplacedElement(ReplacedElement replacedElement) {
		return getListOfReplacedElements().add(replacedElement);
	}

	/**
	 * Removes an element from listOfReplacedElements.
	 * 
	 * @param replacedElement the element to be removed from the list
	 * @return true if this list contained the specified element
	 * @see List#remove(Object)
	 */
	public boolean removeReplacedElement(ReplacedElement replacedElement) {
		if (isSetListOfReplacedElements()) {
			return getListOfReplacedElements().remove(replacedElement);
		}
		return false;
	}

	/**
	 * Removes the ith element from the ListOfReplacedElements.
	 * 
	 * @param i the index of the element to be removed
	 * @throws IndexOutOfBoundsException if the listOf is not set or
	 * if the index is out of bound (index < 0 || index > list.size).
	 */
	public void removeReplacedElement(int i) {
		if (!isSetListOfReplacedElements()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfReplacedElements().remove(i);
	}


	/**
	 * Creates a new {@link ReplacedElement} element and adds it to the ListOfReplacedElements list.
	 *
	 * @return a new {@link ReplacedElement} element.
	 */
	public ReplacedElement createReplacedElement() {
		ReplacedElement replacedElement = new ReplacedElement(extendedSBase.getLevel(), extendedSBase.getVersion());
		addReplacedElement(replacedElement);
		return replacedElement;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompSBasePlugin [listOfReplacedElements="
				+ listOfReplacedElements + ", replacedBy=" + replacedBy + "]";
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{		
		return false; // no new attributes defined
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
	 */
	public Map<String, String> writeXMLAttributes()
	{
		// no new attributes defined
		return new TreeMap<String, String>();
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		int count = 0;

		 if (isSetListOfReplacedElements()) {
		  count++;
		 }
		 if (isSetReplacedBy()) {
			 count++;
		 }

		 return count;
	}

	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}

		int pos = 0;

		if (isSetListOfReplacedElements()) {
			if (pos == index) {
				return getListOfReplacedElements();
			}
			pos++;
		}
		if (isSetReplacedBy()) {
			if (pos == index) {
				return getReplacedBy();
			}
			pos++;
		}

		throw new IndexOutOfBoundsException(MessageFormat.format(
				"Index {0,number,integer} >= {1,number,integer}", index,
				+((int) Math.min(pos, 0))));
	}



}
