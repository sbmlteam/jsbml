/*
 * $Id: ListOf.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/ListOf.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.element;

import java.util.HashSet;
import java.util.LinkedList;

import org.sbml.jsbml.xml.CurrentListOfSBMLElements;

/**
 * This list implementation is a java LinkedList that is however restricted to
 * generic types that implement the SBase interface and conatains all methods
 * from AbstractSBase. Unfortunately, there is no way for multiple inheritance
 * in Java.
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class ListOf extends AbstractSBase {

	private CurrentListOfSBMLElements currentList = CurrentListOfSBMLElements.none;
	private LinkedList<SBase> listOf = null;

	/**
	 * 
	 */
	public ListOf() {
		super();
		this.setListOf(new LinkedList<SBase>());
	}
	
	/**
	 * 
	 */
	public ListOf(int level, int version) {
		super(level, version);
		this.setOfListeners = new HashSet<SBaseChangedListener>();
		this.level = level;
		this.version = version;
	}

	/**
	 * 
	 * @param listOf
	 */
	public ListOf(ListOf listOf) {
		super();
		if (listOf.isSetSBOTerm())
			setSBOTerm(listOf.getSBOTerm());
		if (listOf.isSetMetaId())
			setMetaId(new String(listOf.getMetaId()));
		if (listOf.isSetNotes())
			setNotes(new String(listOf.getNotesString()));
		setParentSBML(listOf.getParentSBMLObject());
		this.setOfListeners = new HashSet<SBaseChangedListener>();
		this.setOfListeners.addAll(listOf.setOfListeners);
		this.level = listOf.getLevel();
		this.version = listOf.getVersion();
		this.listOf = listOf.getListOf();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jlibsbml.SBase#addChangeListener(org.sbml.squeezer.io.
	 * SBaseChangedListener)
	 */
	public void addChangeListener(SBaseChangedListener l) {
		setOfListeners.add(l);
		for (int i = 0; i < this.getListOf().size(); i++)
			((SBase) this.getListOf().get(i)).addChangeListener(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof ListOf) {
			boolean equals = super.equals(o);
			SBase sbase = (SBase) o;
			equals &= sbase.isSetMetaId() == isSetMetaId();
			if (sbase.isSetMetaId() && isSetMetaId())
				equals &= sbase.getMetaId().equals(getMetaId());
			equals &= sbase.isSetNotes() == isSetNotes();
			if (sbase.isSetNotes() && isSetNotes())
				equals &= sbase.getNotesString().equals(getNotesString());
			equals &= sbase.isSetSBOTerm() == isSetSBOTerm();
			if (sbase.isSetSBOTerm() && isSetSBOTerm())
				equals &= sbase.getSBOTerm() == getSBOTerm();
			equals &= sbase.getLevel() == getLevel();
			equals &= sbase.getVersion() == getVersion();
			equals &= sbase.getAnnotation().equals(getAnnotation());
			return equals;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getElementName()
	 */
	public String getElementName() {
		String name = getClass().getCanonicalName();
		char c = Character.toLowerCase(name.charAt(0));
		return Character.toString(c) + name.substring(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getLevel()
	 */
	public int getLevel() {
		return level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getModel()
	 */
	public Model getModel() {
		if (getParentSBMLObject() != null)
			return getParentSBMLObject().getModel();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getParentSBMLObject()
	 */
	public SBase getParentSBMLObject() {
		return parentSBMLObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getSBMLDocument()
	 */
	public SBMLDocument getSBMLDocument() {
		Model m = getModel();
		if (m != null)
			return m.getParentSBMLObject();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getVersion()
	 */
	public int getVersion() {
		return version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#hasValidLevelVersionNamespaceCombination()
	 */
	public boolean hasValidLevelVersionNamespaceCombination() {
		boolean has = true;
		if (level == 1) {
			if (1 <= version && version <= 2)
				has = true;
			else
				has = false;
		} else if (level == 2) {
			if (1 <= version && version <= 4)
				has = true;
			else
				has = false;
		} else
			has = false;
		return has;
	}

	/**
	 * 
	 * @param l
	 */
	public void removeChangeListener(SBaseChangedListener l) {
		setOfListeners.remove(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#sbaseAdded()
	 */
	public void sbaseAdded() {
		for (SBaseChangedListener listener : setOfListeners)
			listener.sbaseAdded(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#sbaseRemoved()
	 */
	public void sbaseRemoved() {
		for (SBaseChangedListener listener : setOfListeners)
			listener.stateChanged(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#setSBOTerm(java.lang.String)
	 */
	public void setSBOTerm(String sboid) {
		setSBOTerm(SBO.stringToInt(sboid));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#setAnnotation(java.lang.String)
	 */
	public void setParentSBML(SBase parent) {
		this.parentSBMLObject = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#stateChanged()
	 */
	public void stateChanged() {
		for (SBaseChangedListener listener : setOfListeners)
			listener.stateChanged(this);
	}

	public void setCurrentList(CurrentListOfSBMLElements currentList) {
		this.currentList = currentList;
	}

	public CurrentListOfSBMLElements getCurrentList() {
		return currentList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 */
	public boolean readAttribute(String attributeName, String prefix, String value){
	
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setListOf(LinkedList<SBase> listOf) {
		this.listOf = listOf;
	}

	public LinkedList<SBase> getListOf() {
		return listOf;
	}

	@Override
	public SBase clone() {
		return new ListOf(this);
	}
}
