/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

package org.sbml.jsbml;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.sbml.jsbml.CVTerm.Qualifier;

/**
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public abstract class AbstractSBase implements SBase {

	private String annotation;
	private List<CVTerm> listOfCVTerms;
	private String metaId;
	private String notes;
	private int sboTerm;
	int level;
	SBase parentSBMLObject;
	Set<SBaseChangedListener> setOfListeners;
	int version;
	
	/**
	 * 
	 */
	public AbstractSBase(int level, int version) {
		sboTerm = -1;
		metaId = null;
		notes = null;
		parentSBMLObject = null;
		setOfListeners = new HashSet<SBaseChangedListener>();
		this.level = level;
		this.version = version;
		this.listOfCVTerms = new LinkedList<CVTerm>();
	}

	/**
	 * 
	 * @param sb
	 */
	public AbstractSBase(SBase sb) {
		this(sb.getLevel(), sb.getVersion());
		if (sb.isSetSBOTerm())
			this.sboTerm = sb.getSBOTerm();
		if (sb.isSetMetaId())
			this.metaId = new String(sb.getMetaId());
		if (sb.isSetNotes())
			this.notes = new String(sb.getNotesString());
		this.parentSBMLObject = sb.getParentSBMLObject();
		this.setOfListeners = new HashSet<SBaseChangedListener>();
		if (sb instanceof AbstractSBase)
			this.setOfListeners.addAll(((AbstractSBase) sb).setOfListeners);
		else if (sb instanceof ListOf<?>)
			this.setOfListeners.addAll(((ListOf<?>) sb).setOfListeners);
		this.level = sb.getLevel();
		this.version = sb.getVersion();
		this.listOfCVTerms = new LinkedList<CVTerm>();
		for (CVTerm cvt : sb.getCVTerms())
			this.listOfCVTerms.add(cvt.clone());
	}

	/**
	 * adds a listener to the SBase object. from now on changes will be saved
	 * 
	 * @param l
	 */
	public void addChangeListener(SBaseChangedListener l) {
		setOfListeners.add(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#addCVTerm(org.sbml.jlibsbml.CVTerm)
	 */
	public boolean addCVTerm(CVTerm term) {
		return listOfCVTerms.add(term);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#appendNotes(java.lang.String)
	 */
	public void appendNotes(String notes) {
		if (isSetNotes()) {
			this.notes = this.notes.trim();
			boolean body = false;
			if (this.notes.endsWith("\n"))
				this.notes = this.notes.substring(0, this.notes.length() - 2);
			if (this.notes.endsWith("</notes>"))
				this.notes = this.notes.substring(0, this.notes.length() - 9);
			if (this.notes.endsWith("</body>")) {
				body = true;
				this.notes = this.notes.substring(0, this.notes.length() - 8);
			}
			this.notes += notes;
			if (body)
				this.notes += "</body>";
			this.notes += "</notes>";
		} else
			this.notes = notes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public abstract SBase clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof SBase) {
			SBase sbase = (SBase) o;
			boolean equals = true;
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
			equals &= sbase.getCVTerms().equals(getCVTerms());
			return equals;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier)
	 */
	public List<CVTerm> filterCVTerms(Qualifier qualifier) {
		LinkedList<CVTerm> l = new LinkedList<CVTerm>();
		for (CVTerm term : listOfCVTerms) {
			if (term.isBiologicalQualifier()
					&& term.getBiologicalQualifierType() == qualifier)
				l.add(term);
			else if (term.isModelQualifier()
					&& term.getModelQualifierType() == qualifier)
				l.add(term);
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier, java.lang.String)
	 */
	public List<String> filterCVTerms(Qualifier qualifier, String pattern) {
		List<String> l = new LinkedList<String>();
		for (CVTerm c : filterCVTerms(qualifier))
			l.addAll(c.filterResources(pattern));
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getAnnotationString()
	 */
	public String getAnnotationString() {
		return isSetAnnotation() ? annotation : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getCVTerm(int)
	 */
	public CVTerm getCVTerm(int i) {
		return listOfCVTerms.get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getCVTerms()
	 */
	public List<CVTerm> getCVTerms() {
		return listOfCVTerms;
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
	 * @see org.sbml.SBase#getMetaId()
	 */
	public String getMetaId() {
		return metaId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getModel()
	 */
	public Model getModel() {
		if (this instanceof Model)
			return (Model) this;
		if (getParentSBMLObject() != null)
			return getParentSBMLObject().getModel();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getNotesString()
	 */
	public String getNotesString() {
		return notes != null ? notes : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getNumCVTerms()
	 */
	public int getNumCVTerms() {
		return listOfCVTerms.size();
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
		if (this instanceof SBMLDocument)
			return (SBMLDocument) this;
		Model m = getModel();
		if (m != null)
			return m.getParentSBMLObject();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getSBOTerm()
	 */
	public int getSBOTerm() {
		return sboTerm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getSBOTermID()
	 */
	public String getSBOTermID() {
		return SBO.intToString(sboTerm);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#isSetAnnotation()
	 */
	public boolean isSetAnnotation() {
		return annotation != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#isSetMetaId()
	 */
	public boolean isSetMetaId() {
		return metaId != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#isSetNotes()
	 */
	public boolean isSetNotes() {
		return notes != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#isSetSBOTerm()
	 */
	public boolean isSetSBOTerm() {
		return sboTerm != -1;
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
	 * @see org.sbml.jlibsbml.SBase#setAnnotation(java.lang.String)
	 */
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#setMetaId(java.lang.String)
	 */
	public void setMetaId(String metaid) {
		this.metaId = metaid;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#setNotes(java.lang.String)
	 */
	public void setNotes(String notes) {
		this.notes = notes;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#setSBOTerm(int)
	 */
	public void setSBOTerm(int term) {
		if (!SBO.checkTerm(term))
			throw new IllegalArgumentException(
					"SBO terms must not be smaller than zero or larger than 9999999.");
		sboTerm = term;
		stateChanged();
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
	 * @see org.sbml.SBase#stateChanged()
	 */
	public void stateChanged() {
		for (SBaseChangedListener listener : setOfListeners)
			listener.stateChanged(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	// @Override
	public abstract String toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetAnnotation()
	 */
	public void unsetAnnotation() {
		if (isSetAnnotation())
			annotation = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetCVTerms()
	 */
	public void unsetCVTerms() {
		listOfCVTerms.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetMetaId()
	 */
	public void unsetMetaId() {
		if (isSetMetaId())
			metaId = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetNotes()
	 */
	public void unsetNotes() {
		if (isSetNotes())
			notes = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetSBOTerm()
	 */
	public void unsetSBOTerm() {
		sboTerm = -1;
	}

	/**
	 * Sets the parent SBML object of the given list and all of its elements to
	 * this object.
	 * 
	 * @param list
	 */
	void setThisAsParentSBMLObject(ListOf<? extends AbstractSBase> list) {
		list.parentSBMLObject = this;
		for (AbstractSBase base : list)
			base.parentSBMLObject = this;
	}
}
