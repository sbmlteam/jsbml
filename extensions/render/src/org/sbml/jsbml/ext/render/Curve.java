/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
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
package org.sbml.jsbml.ext.render;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class Curve extends GraphicalPrimitive1D {
	/**
   * 
   */
  private static final long serialVersionUID = -1941713884972334826L;
  protected String startHead;
	protected String endHead;
	protected ListOf<RenderPoint> listOfElements;
	
	/**
	 * Creates an Curve instance 
	 */
	public Curve() {
		super();
		initDefaults();
	}

	/**
	 * Clone constructor
	 */
	public Curve(Curve obj) {
		super();
		startHead = obj.startHead;
		endHead = obj.endHead;
		listOfElements = obj.listOfElements;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#clone()
	 */
	@Override
	public Curve clone() {
		return new Curve(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#initDefaults()
	 */
	@Override
	public void initDefaults() {
		addNamespace(RenderConstants.namespaceURI);
	}

	/**
	 * @return the value of startHead
	 */
	public String getStartHead() {
		if (isSetStartHead()) {
			return startHead;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.startHead, this);
	}

	/**
	 * @return whether startHead is set 
	 */
	public boolean isSetStartHead() {
		return this.startHead != null;
	}

	/**
	 * Set the value of startHead
	 */
	public void setStartHead(String startHead) {
		String oldStartHead = this.startHead;
		this.startHead = startHead;
		firePropertyChange(RenderConstants.startHead, oldStartHead, this.startHead);
	}

	/**
	 * Unsets the variable startHead 
	 * @return <code>true</code>, if startHead was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetStartHead() {
		if (isSetStartHead()) {
			String oldStartHead = this.startHead;
			this.startHead = null;
			firePropertyChange(RenderConstants.startHead, oldStartHead, this.startHead);
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return the value of endHead
	 */
	public String getEndHead() {
		if (isSetEndHead()) {
			return endHead;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(RenderConstants.endHead, this);
	}

	/**
	 * @return whether endHead is set 
	 */
	public boolean isSetEndHead() {
		return this.endHead != null;
	}

	/**
	 * Set the value of endHead
	 */
	public void setEndHead(String endHead) {
		String oldEndHead = this.endHead;
		this.endHead = endHead;
		firePropertyChange(RenderConstants.endHead, oldEndHead, this.endHead);
	}

	/**
	 * Unsets the variable endHead 
	 * @return <code>true</code>, if endHead was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetEndHead() {
		if (isSetEndHead()) {
			String oldEndHead = this.endHead;
			this.endHead = null;
			firePropertyChange(RenderConstants.endHead, oldEndHead, this.endHead);
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return <code>true</code>, if listOfElements contains at least one element, 
	 *         otherwise <code>false</code>
	 */
	public boolean isSetListOfElements() {
		if ((listOfElements == null) || listOfElements.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the listOfElements
	 */
	public ListOf<RenderPoint> getListOfElements() {
		if (!isSetListOfElements()) {
			listOfElements = new ListOf<RenderPoint>(getLevel(), getVersion());
			listOfElements.addNamespace(RenderConstants.namespaceURI);
			listOfElements.setSBaseListType(ListOf.Type.other);
			registerChild(listOfElements);
		}
		return listOfElements;
	}

	/**
	 * @param listOfElements
	 */
	public void setListOfElements(ListOf<RenderPoint> listOfElements) {
		unsetListOfElements();
		this.listOfElements = listOfElements;
		registerChild(this.listOfElements);
	}

	/**
	 * @return <code>true</code>, if listOfElements contained at least one element, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetListOfElements() {
		if (isSetListOfElements()) {
			ListOf<RenderPoint> oldElements = this.listOfElements;
			this.listOfElements = null;
			oldElements.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * @param element
	 */
	public boolean addElement(RenderPoint element) {
		return getListOfElements().add(element);
	}

	/**
	 * @param element
	 */
	public boolean removeElement(RenderPoint element) {
		if (isSetListOfElements()) {
			return getListOfElements().remove(element);
		}
		return false;
	}

	/**
	 * @param i
	 */
	public void removeElement(int i) {
		if (!isSetListOfElements()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfElements().remove(i);
	}

	/**
	 * create a new Element element and adds it to the ListOfElements list
	 * <p><b>NOTE:</b>
	 * only use this method, if ID is not mandatory in Element
	 * otherwise use @see createElement(String id)!</p>
	 */
	public RenderPoint createElement() {
		return createElement(null);
	}

	/**
	 * create a new Element element and adds it to the ListOfElements list
	 */
	public RenderPoint createElement(String id) {
		RenderPoint element = new RenderPoint();
		addElement(element);
		return element;
	}

}
