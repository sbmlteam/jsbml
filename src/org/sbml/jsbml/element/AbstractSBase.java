/*
 * $Id: AbstractSBase.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/AbstractSBase.java $
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

package org.sbml.jsbml.element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @author marine
 */
public abstract class AbstractSBase implements SBase {

	SBase parentSBMLObject;
	Set<SBaseChangedListener> setOfListeners;
	int level;
	int version;
	private String metaId;
	private int sboTerm;
	private String notes;
	private Annotation annotation;
	private StringBuffer notesBuffer;
	private HashMap<String, SBase> extensions;

	/**
	 * 
	 */
	public AbstractSBase() {
		sboTerm = -1;
		metaId = null;
		notes = null;
		parentSBMLObject = null;
		setOfListeners = new HashSet<SBaseChangedListener>();
		annotation = null;
		this.notesBuffer = null;
		this.extensions = new HashMap<String, SBase>();
	}
	
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
		this.annotation = null;
		this.extensions = new HashMap<String, SBase>();
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
		this.annotation = sb.getAnnotation();
		this.extensions = new HashMap<String, SBase>();
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
			if (sbase.isSetAnnotation() && isSetAnnotation()){
				equals &= sbase.getAnnotation().equals(getAnnotation());
			}
			return equals;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getAnnotationString()
	 */
	public Annotation getAnnotation() {
		return isSetAnnotation() ? annotation : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getElementName()
	 */
	public String getElementName() {
		String name = getClass().getSimpleName();
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
		return notes != null ? notes : null;
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
	
	public boolean hasValidAnnotation(){
		if (isSetAnnotation()){
			if (isSetMetaId()){
				if (getAnnotation().getAbout().equals("#_"+getMetaId())){
					return true;
				}
			}
			return false;
		}
		return true;
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
	public void setParentSBML(SBase parent) {
		this.parentSBMLObject = parent;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#setAnnotation(java.lang.String)
	 */
	public void setAnnotation(Annotation annotation) {
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
		if (isSetNotes()){
			this.notes += notes;
		}
		else {
			this.notes = notes;
		}
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
	void setThisAsParentSBMLObject(ListOf<?> list) {
		list.parentSBMLObject = this;
		for (SBase base : list){
			AbstractSBase sbase = (AbstractSBase) base;
			sbase.setParentSBML(this);
		}
	}
	
	public void setNotesBuffer(StringBuffer notesBuffer) {
		this.notesBuffer = notesBuffer;
	}

	public StringBuffer getNotesBuffer() {
		return notesBuffer;
	}
	
	public boolean isSetNotesStringBuffer(){
		return this.notesBuffer != null;
	}
	
	public boolean readAttribute(String attributeName, String prefix, String value){
		
		if (attributeName.equals("level")){
			this.level = Integer.parseInt(value);
			return true;
		}
		else if (attributeName.equals("version")){
			this.version = Integer.parseInt(value);
			return true;
		}
		else if (attributeName.equals("sboTerm")){
			this.setSBOTerm(value);
			return true;
		}
		else if (attributeName.equals("metaid")){
			this.metaId = value;
			return true;
		}
		return false;
	}
	
	public List<CVTerm> getCVTerms() {
		if (isSetAnnotation()){
			return annotation.getListOfCVTerms();
		}
		return null;
	}
	
	public boolean addCVTerm(CVTerm term) {
		if (!isSetAnnotation()){
			this.annotation = new Annotation();
		}
		return annotation.addCVTerm(term);
	}
	
	public void unsetCVTerms(){
		if (isSetAnnotation()){
			annotation.unsetCVTerms();
		}
	}
	
	public int getNumCVTerms() {
		if (isSetAnnotation()){
			return annotation.getListOfCVTerms().size();
		}
		return 0;
	}
	
	public CVTerm getCVTerm(int index) {
		return annotation.getCVTerm(index);
	}
	
	public boolean isSetModelHistory() {
		return annotation.isSetModelHistory();
	}
	
	public ModelHistory getModelHistory() {
		return annotation.getModelHistory();
	}
	
	public SBase getExtension(String namespace){
		return this.extensions.get(namespace);
	}
	
	public void addExtension(String namespace, SBase sbase){
		this.extensions.put(namespace, sbase);
	}
	
	public Set<String> getNamespaces(){
		return this.extensions.keySet();
	}
	
	public HashMap<String, String> writeXMLAttributes(){
		HashMap<String, String> attributes = new HashMap<String, String>();
		
		if (isSetMetaId()){
			attributes.put("metaid", getMetaId());
		}
		if (isSetSBOTerm()){
			attributes.put("sboTerm", getSBOTermID());
		}
		return attributes;
	}
}
