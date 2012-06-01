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

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.filters.NameFilter;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 14.05.2012
 */
public class ExtendedRenderModel extends AbstractSBasePlugin {
	/**
	 *
	 */
	private static final long serialVersionUID = -7046023464349980639L;

	private ListOf<LocalRenderInformation> listOfLocalRenderInformation;
	private ListOf<GlobalRenderInformation> listOfGlobalRenderInformation;
	private Integer versionMajor;
	private Integer versionMinor;

	/**
	 * Creates an ExtendedRenderModel instance
	 */
	public ExtendedRenderModel() {
		super();
		initDefaults();
	}

	/**
	 * Creates a ExtendedRenderModel instance with a level and version.
	 *
	 * @param level
	 * @param version
	 */
	public ExtendedRenderModel(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Creates a ExtendedRenderModel instance with an id, level, and version.
	 *
	 * @param id
	 * @param level
	 * @param version
	 */
	public ExtendedRenderModel(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a ExtendedRenderModel instance with an id, name, level, and version.
	 *
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public ExtendedRenderModel(String id, String name, int level, int version) {
		super();
		/*if (getLevelAndVersion().compareTo(Integer.valueOf(MIN_SBML_LEVEL),
			Integer.valueOf(MIN_SBML_VERSION)) < 0) {
		throw new LevelVersionError(getElementName(), level, version);
	} */
		initDefaults();
	}

	/**
	 * Clone constructor
	 */
	public ExtendedRenderModel(ExtendedRenderModel obj) {
		super();
		versionMajor = obj.versionMajor;
		versionMinor = obj.versionMinor;
		listOfGlobalRenderInformation = obj.listOfGlobalRenderInformation;
		listOfLocalRenderInformation = obj.listOfLocalRenderInformation;
	}

	/**
	 * clones this class
	 */
	@Override
  public ExtendedRenderModel clone() {
		return new ExtendedRenderModel(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		//addNamespace(RenderConstants.namespaceURI);
		versionMajor = 0;
		versionMinor = 0;
	}

	/**
	 * @return the value of versionMinor
	 */
	public int getVersionMinor() {
		return versionMinor;
	}
	/**
	 * @return whether versionMinor is set
	 */
	public boolean isSetVersionMinor() {
		return true;
	}

	/**
	 * Set the value of versionMinor
	 */
	public void setVersionMinor(int versionMinor) {
		int oldVersionMinor = this.versionMinor;
		this.versionMinor = versionMinor;
		// FIXME firePropertyChange(RenderConstants.versionMinor, oldVersionMinor, this.versionMinor);
	}

	/**
	 * Unsets the variable versionMinor
	 * @return <code>true</code>, if versionMinor was set before,
	 *         otherwise <code>false</code>
	 */
	public boolean unsetVersionMinor() {
		if (isSetVersionMinor()) {
			int oldVersionMinor = this.versionMinor;
			//FIXME firePropertyChange(RenderConstants.versionMinor, oldVersionMinor, this.versionMinor);
			return true;
		}
		return false;
	}


	/**
	 * @return the value of versionMajor
	 */
	public int getVersionMajor() {
		return versionMajor;
	}

	/**
	 * @return whether versionMajor is set
	 */
	public boolean isSetVersionMajor() {
		return true;
	}

	/**
	 * Set the value of versionMajor
	 */
	public void setVersionMajor(int versionMajor) {
		int oldVersionMajor = this.versionMajor;
		this.versionMajor = versionMajor;
		//firePropertyChange(RenderConstants.versionMajor, oldVersionMajor, this.versionMajor);
	}

	/**
	 * Unsets the variable versionMajor
	 * @return <code>true</code>, if versionMajor was set before,
	 *         otherwise <code>false</code>
	 */
	public boolean unsetVersionMajor() {
		if (isSetVersionMajor()) {
			int oldVersionMajor = this.versionMajor;
			//firePropertyChange(RenderConstants.versionMajor, oldVersionMajor, this.versionMajor);
			return true;
		}
		return false;
	}


	/**
	 * @return <code>true</code>, if listOfGlobalRenderInformation contains at least one element,
	 *         otherwise <code>false</code>
	 */
	public boolean isSetListOfGlobalRenderInformation() {
		if ((listOfGlobalRenderInformation == null) || listOfGlobalRenderInformation.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the listOfGlobalRenderInformation
	 */
	public ListOf<GlobalRenderInformation> getListOfGlobalRenderInformation() {
		if (!isSetListOfGlobalRenderInformation()) {
			listOfGlobalRenderInformation = new ListOf<GlobalRenderInformation>();
			listOfGlobalRenderInformation.addNamespace(RenderConstants.namespaceURI);
			listOfGlobalRenderInformation.setSBaseListType(ListOf.Type.other);
			//FIXME registerChild(listOfGlobalRenderInformation);
		}
		return listOfGlobalRenderInformation;
	}

	/**
	 * @param listOfGlobalRenderInformation
	 */
	public void setListOfGlobalRenderInformation(ListOf<GlobalRenderInformation> listOfGlobalRenderInformation) {
		unsetListOfGlobalRenderInformation();
		this.listOfGlobalRenderInformation = listOfGlobalRenderInformation;
		//FIXME registerChild(this.listOfGlobalRenderInformation);
	}

	/**
	 * @return <code>true</code>, if listOfGlobalRenderInformation contained at least one element,
	 *         otherwise <code>false</code>
	 */
	public boolean unsetListOfGlobalRenderInformation() {
		if (isSetListOfGlobalRenderInformation()) {
			ListOf<GlobalRenderInformation> oldGlobalRenderInformation = this.listOfGlobalRenderInformation;
			this.listOfGlobalRenderInformation = null;
			oldGlobalRenderInformation.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * @param globalRenderInformation
	 */
	public boolean addGlobalRenderInformation(GlobalRenderInformation globalRenderInformation) {
		return getListOfGlobalRenderInformation().add(globalRenderInformation);
	}

	/**
	 * @param globalRenderInformation
	 */
	public boolean removeGlobalRenderInformation(GlobalRenderInformation globalRenderInformation) {
		if (isSetListOfGlobalRenderInformation()) {
			return getListOfGlobalRenderInformation().remove(globalRenderInformation);
		}
		return false;
	}

	/**
	 * @param i
	 */
	public void removeGlobalRenderInformation(int i) {
		if (!isSetListOfGlobalRenderInformation()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfGlobalRenderInformation().remove(i);
	}

	/**
	 *
	 * @param id
	 */
	public void removeGlobalRenderInformation(String id) {
	  getListOfGlobalRenderInformation().removeFirst(new NameFilter(id));
	}

	/**
	 * create a new GlobalRenderInformation element and adds it to the ListOfGlobalRenderInformation list
	 * <p><b>NOTE:</b>
	 * only use this method, if ID is not mandatory in GlobalRenderInformation
	 * otherwise use @see createGlobalRenderInformation(String id)!</p>
	 */
	public GlobalRenderInformation createGlobalRenderInformation() {
		return createGlobalRenderInformation(null);
	}

	/**
	 * create a new GlobalRenderInformation element and adds it to the ListOfGlobalRenderInformation list
	 */
	public GlobalRenderInformation createGlobalRenderInformation(String id) {
		GlobalRenderInformation globalRenderInformation = new GlobalRenderInformation(id);
		addGlobalRenderInformation(globalRenderInformation);
		return globalRenderInformation;
	}

	/**
	 * @return <code>true</code>, if listOfLocalRenderInformation contains at least one element,
	 *         otherwise <code>false</code>
	 */
	public boolean isSetListOfLocalRenderInformation() {
		if ((listOfLocalRenderInformation == null) || listOfLocalRenderInformation.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the listOfLocalRenderInformation
	 */
	public ListOf<LocalRenderInformation> getListOfLocalRenderInformation() {
		if (!isSetListOfLocalRenderInformation()) {
			listOfLocalRenderInformation = new ListOf<LocalRenderInformation>();
			listOfLocalRenderInformation.addNamespace(RenderConstants.namespaceURI);
			listOfLocalRenderInformation.setSBaseListType(ListOf.Type.other);
			//FIXME registerChild(listOfLocalRenderInformation);
		}
		return listOfLocalRenderInformation;
	}

	/**
	 * @param listOfLocalRenderInformation
	 */
	public void setListOfLocalRenderInformation(ListOf<LocalRenderInformation> listOfLocalRenderInformation) {
		unsetListOfLocalRenderInformation();
		this.listOfLocalRenderInformation = listOfLocalRenderInformation;
		// FIXME registerChild(this.listOfLocalRenderInformation);
	}

	/**
	 * @return <code>true</code>, if listOfLocalRenderInformation contained at least one element,
	 *         otherwise <code>false</code>
	 */
	public boolean unsetListOfLocalRenderInformation() {
		if (isSetListOfLocalRenderInformation()) {
			ListOf<LocalRenderInformation> oldLocalRenderInformation = this.listOfLocalRenderInformation;
			this.listOfLocalRenderInformation = null;
			oldLocalRenderInformation.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * @param localRenderInformation
	 */
	public boolean addLocalRenderInformation(LocalRenderInformation localRenderInformation) {
		return getListOfLocalRenderInformation().add(localRenderInformation);
	}

	/**
	 * @param localRenderInformation
	 */
	public boolean removeLocalRenderInformation(LocalRenderInformation localRenderInformation) {
		if (isSetListOfLocalRenderInformation()) {
			return getListOfLocalRenderInformation().remove(localRenderInformation);
		}
		return false;
	}

	/**
	 * @param i
	 */
	public void removeLocalRenderInformation(int i) {
		if (!isSetListOfLocalRenderInformation()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfLocalRenderInformation().remove(i);
	}

	/**
	 * create a new LocalRenderInformation element and adds it to the ListOfLocalRenderInformation list
	 * <p><b>NOTE:</b>
	 * only use this method, if ID is not mandatory in LocalRenderInformation
	 * otherwise use @see createLocalRenderInformation(String id)!</p>
	 */
	public LocalRenderInformation createLocalRenderInformation() {
		return createLocalRenderInformation(null);
	}

	/**
	 * create a new LocalRenderInformation element and adds it to the ListOfLocalRenderInformation list
	 */
	public LocalRenderInformation createLocalRenderInformation(String id) {
		LocalRenderInformation localRenderInformation = new LocalRenderInformation(id);
		addLocalRenderInformation(localRenderInformation);
		return localRenderInformation;
	}

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getAllowsChildren()
   */
  //@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getChildCount()
	 */
	//@Override
	public int getChildCount() {
		int count = 0;

		if (isSetListOfGlobalRenderInformation()) {
			count++;
		}
		if (isSetListOfLocalRenderInformation()) {
			count++;
		}

		return count;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getChildAt(int)
	 */
  //@Override
	public SBase getChildAt(int childIndex) {
		if (childIndex < 0) {
			throw new IndexOutOfBoundsException(childIndex + " < 0");
		}

		int pos = 0;
		if (isSetListOfGlobalRenderInformation()) {
			if (pos == childIndex) {
				return getListOfLocalRenderInformation();
			}
			pos++;
		}
		if (isSetListOfLocalRenderInformation()) {
			if (pos == childIndex) {
				return getListOfLocalRenderInformation();
			}
			pos++;
		}

		throw new IndexOutOfBoundsException(MessageFormat.format(
				"Index {0,number,integer} >= {1,number,integer}", childIndex,
				+Math.min(pos, 0)));
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
  //@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
	 */
  //@Override
	public Map<String, String> writeXMLAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
