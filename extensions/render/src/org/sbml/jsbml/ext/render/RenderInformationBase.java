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

import java.awt.Color;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 04.05.2012
 */
public class RenderInformationBase  extends AbstractNamedSBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9096154126197866584L;

	protected String programName;
	protected String programVersion; //TODO int better?
	protected String referenceRenderInformation;
	protected Color backgroundColor; 
	protected ListOf<ColorDefinition> listOfColorDefinitions;
	// TODO maybe wrong class for linear and radial gradients
	protected ListOf<GradientBase> listOfGradientBases;
	protected ListOf<LineEnding> listofLineEndings;

	/**
	 * Creates an RenderInformationBase instance 
	 */
	public RenderInformationBase() {
		super();
		initDefaults();
	}

	/**
	 * Creates a RenderInformationBase instance with an id. 
	 * 
	 * @param id
	 */
	public RenderInformationBase(String id) {
		super(id);
		initDefaults();
	}

	/**
	 * Creates a RenderInformationBase instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public RenderInformationBase(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Creates a RenderInformationBase instance with an id, level, and version. 
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public RenderInformationBase(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a RenderInformationBase instance with an id, name, level, and version. 
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public RenderInformationBase(String id, String name, int level, int version) {
		super(id, name, level, version);
		if (getLevelAndVersion().compareTo(Integer.valueOf(MIN_SBML_LEVEL),
				Integer.valueOf(MIN_SBML_VERSION)) < 0) {
			throw new LevelVersionError(getElementName(), level, version);
		}
		initDefaults();
	}

	/**
	 * Clone constructor
	 */
	public RenderInformationBase(RenderInformationBase obj) {
		super(obj);
		this.programName = obj.programName;
		this.programVersion = obj.programVersion;
		this.referenceRenderInformation = obj.referenceRenderInformation;
		this.backgroundColor = obj.backgroundColor; 
		this.listOfColorDefinitions = obj.listOfColorDefinitions;
		this.listOfGradientBases = obj.listOfGradientBases;
		this.listofLineEndings = obj.listofLineEndings;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public RenderInformationBase clone() {
		return new RenderInformationBase(this);
	}

	/**
	 * Initializes the default values using the namespace.
	 */
	public void initDefaults() {
		//TODO addNamespace(.namespaceURI);
		this.backgroundColor = new Color(0, 0, 0);
		this.programName = null;
		this.programVersion = null;
		this.referenceRenderInformation = null;
		this.listOfColorDefinitions = null;
		this.listOfGradientBases = null;
		this.listofLineEndings = null;
	}

	/**
	 * @return the value of programName
	 */
	public String getProgramName() {

		if (isSetProgramName()) {
			return this.programName;
		} else {
			return null;
		}
		//TODO Create a Constants class?
		// This is necessary if we cannot return null here.
		//throw new PropertyUndefinedError("programmName", this);
	}

	/**
	 * @return whether programmName is set 
	 */
	public boolean isSetProgramName() {
		return this.programName != null;
	}

	/**
	 * Set the value of programmName
	 */
	public void setProgramName(String programName) {
		String oldProgramName = this.programName;
		this.programName = programName;
		firePropertyChange("programName", oldProgramName, this.programName);
	}

	/**
	 * Unsets the variable programmName 
	 * @return <code>true</code>, if programmName was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetProgramName() {
		if (isSetProgramName()) {
			String oldProgramName = this.programName;
			this.programName = null;
			firePropertyChange("programmName", oldProgramName, this.programName);
			return true;
		}
		return false;
	}

	/**
	 * @return the value of programVersion
	 */
	public String getProgramVersion() {
		//TODO: return primitive data type if possible (e.g. int instead of Integer)
		if (isSetProgramVersion()) {
			return programVersion;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError("programVersion", this);
	}

	/**
	 * @return whether programVersion is set 
	 */
	public boolean isSetProgramVersion() {
		return this.programVersion != null;
	}

	/**
	 * Set the value of programVersion
	 */
	public void setProgramVersion(String programVersion) {
		String oldProgramVersion = this.programVersion;
		this.programVersion = programVersion;
		firePropertyChange("programVersion", oldProgramVersion, this.programVersion);
	}

	/**
	 * Unsets the variable programVersion 
	 * @return <code>true</code>, if programVersion was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetProgramVersion() {
		if (isSetProgramVersion()) {
			String oldProgramVersion = this.programVersion;
			this.programVersion = null;
			firePropertyChange("programVersion", oldProgramVersion, this.programVersion);
			return true;
		}
		return false;
	}

	/**
	 * @return the value of referenceRenderInformation
	 */
	public String getReferenceRenderInformation() {
		//TODO: if variable is boolean, create an additional "isVar"
		//TODO: return primitive data type if possible (e.g. int instead of Integer)
		if (isSetReferenceRenderInformation()) {
			return referenceRenderInformation;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError("referenceRenderInformation", this);
	}

	/**
	 * @return whether referenceRenderInformation is set 
	 */
	public boolean isSetReferenceRenderInformation() {
		return this.referenceRenderInformation != null;
	}

	/**
	 * Set the value of referenceRenderInformation
	 */
	public void setReferenceRenderInformation(String referenceRenderInformation) {
		String oldReferenceRenderInformation = this.referenceRenderInformation;
		this.referenceRenderInformation = referenceRenderInformation;
		firePropertyChange("referenceRenderInformation", oldReferenceRenderInformation, this.referenceRenderInformation);
	}

	/**
	 * Unsets the variable referenceRenderInformation 
	 * @return <code>true</code>, if referenceRenderInformation was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetReferenceRenderInformation() {
		if (isSetReferenceRenderInformation()) {
			String oldReferenceRenderInformation = this.referenceRenderInformation;
			this.referenceRenderInformation = null;
			firePropertyChange("referenceRenderInformation", oldReferenceRenderInformation, this.referenceRenderInformation);
			return true;
		}
		return false;
	}

	/**
	 * @return the value of backgroundColor
	 */
	public Color getBackgroundColor() {
		//TODO: return primitive data type if possible (e.g. int instead of Integer)
		if (isSetBackgroundColor()) {
			return backgroundColor;
		} else {
			return null;
		}
		// This is necessary if we cannot return null here.
		//throw new PropertyUndefinedError("backgroundColor", this);
	}

	/**
	 * @return whether backgroundColor is set 
	 */
	public boolean isSetBackgroundColor() {
		return this.backgroundColor != null;
	}

	/**
	 * Set the value of backgroundColor
	 */
	public void setBackgroundColor(Color backgroundColor) {
		Color oldBackgroundColor = this.backgroundColor;
		this.backgroundColor = backgroundColor;
		firePropertyChange("backgroundColor", oldBackgroundColor, this.backgroundColor);
	}

	/**
	 * Unsets the variable backgroundColor 
	 * @return <code>true</code>, if backgroundColor was set before, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetBackgroundColor() {
		if (isSetBackgroundColor()) {
			Color oldBackgroundColor = this.backgroundColor;
			this.backgroundColor = null;
			firePropertyChange("backgroundColor", oldBackgroundColor, this.backgroundColor);
			return true;
		}
		return false;
	}

	/**
	 * @return <code>true</code>, if listOfColorDefinitions contains at least one element, 
	 *         otherwise <code>false</code>
	 */
	public boolean isSetListOfColorDefinitions() {
		if ((this.listOfColorDefinitions == null) || listOfColorDefinitions.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the listOfColorDefinitions
	 */
	public ListOf<ColorDefinition> getListOfColorDefinitions() {
		if (!isSetListOfColorDefinitions()) {
			listOfColorDefinitions = new ListOf<ColorDefinition>(getLevel(), getVersion());
			//TODO
			//listOfColorDefinitions.addNamespace(constant_class.namespaceURI);
			listOfColorDefinitions.setSBaseListType(ListOf.Type.other);
			registerChild(listOfColorDefinitions);
		}
		return listOfColorDefinitions;
	}

	/**
	 * @param listOfColorDefinitions
	 */
	public void setListOfColorDefinitions(ListOf<ColorDefinition> listOfColorDefinitions) {
		unsetListOfColorDefinitions();
		this.listOfColorDefinitions = listOfColorDefinitions;
		registerChild(this.listOfColorDefinitions);
	}

	/**
	 * @return <code>true</code>, if listOfColorDefinitions contained at least one element, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetListOfColorDefinitions() {
		if (isSetListOfColorDefinitions()) {
			ListOf<ColorDefinition> oldColorDefinitions = this.listOfColorDefinitions;
			this.listOfColorDefinitions = null;
			oldColorDefinitions.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * @param field
	 */
	public boolean addColorDefinition(ColorDefinition field) {
		return getListOfColorDefinitions().add(field);
	}

	/**
	 * @param field
	 */
	public boolean removeColorDefinition(ColorDefinition field) {
		if (isSetListOfColorDefinitions()) {
			return getListOfColorDefinitions().remove(field);
		}
		return false;
	}

	/**
	 * @param i
	 */
	public void removeColorDefinition(int i) {
		if (!isSetListOfColorDefinitions()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfColorDefinitions().remove(i);
	}

	/**
	 * TODO: if the ID is mandatory for ColorDefinition objects, 
	 * one should also add this methods
	 */
	public void removeColorDefinition(String id) {
		getListOfColorDefinitions().removeFirst(new NameFilter(id));
	}

	/**
	 * create a new ColorDefinition element and adds it to the ListOfColorDefinitions list
	 */
	public ColorDefinition createColorDefinition(String id, Color value) {
		ColorDefinition field = new ColorDefinition(id, value, getLevel(), getVersion());
		addColorDefinition(field);
		return field;
	}

	/**
	 * @return <code>true</code>, if listOfGradientBases contains at least one element, 
	 *         otherwise <code>false</code>
	 */
	public boolean isSetListOfGradientBases() {
		if ((listOfGradientBases == null) || listOfGradientBases.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the listOfGradientBases
	 */
	public ListOf<GradientBase> getListOfGradientBases() {
		if (!isSetListOfGradientBases()) {
			listOfGradientBases = new ListOf<GradientBase>(getLevel(), getVersion());
			//TODO
			//listOfGradientBases.addNamespace(constant_class.namespaceURI);
			listOfGradientBases.setSBaseListType(ListOf.Type.other);
			registerChild(listOfGradientBases);
		}
		return listOfGradientBases;
	}

	/**
	 * @param listOfGradientBases
	 */
	public void setListOfGradientBases(ListOf<GradientBase> listOfGradientBases) {
		unsetListOfGradientBases();
		this.listOfGradientBases = listOfGradientBases;
		registerChild(this.listOfGradientBases);
	}

	/**
	 * @return <code>true</code>, if listOfGradientBases contained at least one element, 
	 *         otherwise <code>false</code>
	 */
	public boolean unsetListOfGradientBases() {
		if (isSetListOfGradientBases()) {
			ListOf<GradientBase> oldGradientBases = this.listOfGradientBases;
			this.listOfGradientBases = null;
			oldGradientBases.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * @param field
	 */
	public boolean addGradientBase(GradientBase field) {
		return getListOfGradientBases().add(field);
	}

	/**
	 * @param field
	 */
	public boolean removeGradientBase(GradientBase field) {
		if (isSetListOfGradientBases()) {
			return getListOfGradientBases().remove(field);
		}
		return false;
	}

	/**
	 * @param i
	 */
	public void removeGradientBase(int i) {
		if (!isSetListOfGradientBases()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		getListOfGradientBases().remove(i);
	}

  // TODO: Move to RenderConstants
	public static final int MIN_SBML_LEVEL = 3;
	public static final int MIN_SBML_VERSION = 1;

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
  //@Override
	public boolean isIdMandatory() {
		// TODO Auto-generated method stub
		return false;
	}

}
