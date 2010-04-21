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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.sbml.jsbml.util.StringTools;

/**
 * The base class for each SBase component.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * @composed 0..1 notes 1 String
 * @depend - <use> - SBO
 */
public abstract class AbstractSBase implements SBase {

	/**
	 * annotations of the SBML component. Matches the annotation XML node in a
	 * SBML file.
	 */
	private Annotation annotation;
	/**
	 * map containing the SBML extension object of additional packages with the
	 * appropriate namespace of the package.
	 */
	private HashMap<String, SBase> extensions;
	/**
	 * level of the SBML component. Matches the level XML attribute of a sbml
	 * node.
	 */
	Integer level;
	/**
	 * metaid of the SBML component. Matches the metaid XML attribute of an
	 * element in a SBML file.
	 */
	private String metaId;
	private Set<String> namespaces = new TreeSet<String>();
	/**
	 * notes of the SBML component. Matches the notes XML node in a SBML file.
	 */
	private String notes;
	/**
	 * buffer containing the notes when parsing the notes XML node in a SBML
	 * file.
	 */
	private StringBuffer notesBuffer;
	/**
	 * parent sbml component
	 */
	SBase parentSBMLObject;
	/**
	 * sbo term of the SBML component. Matches the sboTerm XML attribute of an
	 * element in a SBML file.
	 */
	private int sboTerm;
	/**
	 * set of listeners for this component
	 */
	Set<SBaseChangedListener> setOfListeners;

	/**
	 * version of the SBML component. Matches the version XML attribute of a
	 * sbml node.
	 */
	Integer version;

	/**
	 * Creates an AbstractSBase instance. By default, the sboTerm is -1, the
	 * metaid, notes, parentSBMLObject, annotation, level, version and
	 * notesBuffer are null. The setOfListeners list and the extensions hashmap
	 * are empty.
	 */
	public AbstractSBase() {
		sboTerm = -1;
		metaId = null;
		notes = null;
		level = null;
		version = null;
		parentSBMLObject = null;
		setOfListeners = new HashSet<SBaseChangedListener>();
		annotation = null;
		notesBuffer = null;
		extensions = new HashMap<String, SBase>();
	}

	/**
	 * Creates an AbstractSBase instance from an id and name. By default, the
	 * sboTerm is -1, the metaid, notes, parentSBMLObject, annotation, level,
	 * version and notesBuffer are null. The setOfListeners list and the
	 * extensions hashmap are empty.
	 * 
	 * @param level
	 * @param version
	 */
	public AbstractSBase(int level, int version) {
		this();
		this.level = Integer.valueOf(level);
		this.version = Integer.valueOf(version);
	}

	/**
	 * Creates an AbstractSBase instance from a given AbstractSBase.
	 * 
	 * @param sb
	 */
	public AbstractSBase(SBase sb) {
		this(sb.getLevel(), sb.getVersion());
		if (sb.isSetSBOTerm()) {
			this.sboTerm = sb.getSBOTerm();
		}
		if (sb.isSetMetaId()) {
			this.metaId = new String(sb.getMetaId());
		}
		if (sb.isSetNotes()) {
			this.notes = new String(sb.getNotesString());
		}
		this.parentSBMLObject = sb.getParentSBMLObject();
		this.setOfListeners = new HashSet<SBaseChangedListener>();
		if (sb instanceof AbstractSBase) {
			this.setOfListeners.addAll(((AbstractSBase) sb).setOfListeners);
		}
		if (sb.isSetAnnotation()) {
			// TODO!!! Clone function not yet fully implemented!
			this.annotation = sb.getAnnotation().clone();
		}
		if (sb.isSetNotesBuffer()) {
			this.notesBuffer = new StringBuffer(sb.getNotesBuffer());
		}
		this.extensions = new HashMap<String, SBase>();
		if (sb.isExtendedByOtherPackages()) {
			this.extensions.putAll(sb.getExtensionPackages());
		}
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
	 * @see org.sbml.jsbml.element.SBase#addCVTerm(CVTerm term)
	 */
	public boolean addCVTerm(CVTerm term) {
		if (!isSetAnnotation()) {
			this.annotation = new Annotation();
		}
		stateChanged();
		return annotation.addCVTerm(term);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#addExtension(String namespace, SBase
	 * sbase)
	 */
	public void addExtension(String namespace, SBase sbase) {
		this.extensions.put(namespace, sbase);
		this.namespaces.add(namespace);
		stateChanged();
	}

	public void addNamespace(String namespace) {

		this.namespaces.add(namespace);
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
			if (this.notes.endsWith("\n")) {
				this.notes = this.notes.substring(0, this.notes.length() - 2);
			}
			if (this.notes.endsWith("</notes>")) {
				this.notes = this.notes.substring(0, this.notes.length() - 9);
			}
			if (this.notes.endsWith("</body>")) {
				body = true;
				this.notes = this.notes.substring(0, this.notes.length() - 8);
			}
			this.notes += notes;
			if (body) {
				this.notes += "</body>";
			}
			// this.notes += "</notes>";

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
			if (equals && sbase.isSetMetaId()) {
				equals &= sbase.getMetaId().equals(getMetaId());
			}
			equals &= sbase.isSetNotes() == isSetNotes();
			if (equals && sbase.isSetNotes()) {
				equals &= sbase.getNotesString().equals(getNotesString());
			}
			equals &= sbase.isSetSBOTerm() == isSetSBOTerm();
			if (equals && sbase.isSetSBOTerm()) {
				equals &= sbase.getSBOTerm() == getSBOTerm();
			}
			equals &= sbase.getLevel() == getLevel();
			equals &= sbase.getVersion() == getVersion();
			equals &= sbase.isSetAnnotation() == isSetAnnotation();
			if (equals && sbase.isSetAnnotation()) {
				equals &= sbase.getAnnotation().equals(getAnnotation());
			}
			equals &= sbase.isSetNotesBuffer() == isSetNotesBuffer();
			if (equals && sbase.isSetNotesBuffer()) {
				equals &= sbase.getNotesBuffer().equals(getNotesBuffer());
			}
			return equals;
		}
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier)
	 */
	public List<CVTerm> filterCVTerms(CVTerm.Qualifier qualifier) {
		LinkedList<CVTerm> l = new LinkedList<CVTerm>();
		if (isSetAnnotation()) {
			for (CVTerm term : annotation.getListOfCVTerms()) {
				if (term.isBiologicalQualifier()
						&& term.getBiologicalQualifierType() == qualifier)
					l.add(term);
				else if (term.isModelQualifier()
						&& term.getModelQualifierType() == qualifier)
					l.add(term);
			}
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier,
	 * java.lang.String)
	 */
	public List<String> filterCVTerms(CVTerm.Qualifier qualifier, String pattern) {
		List<String> l = new LinkedList<String>();
		for (CVTerm c : filterCVTerms(qualifier))
			l.addAll(c.filterResources(pattern));
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getAnnotation()
	 */
	public Annotation getAnnotation() {
		return isSetAnnotation() ? annotation : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getCVTerm(int index)
	 */
	public CVTerm getCVTerm(int index) {
		if (isSetAnnotation()) {
			return annotation.getCVTerm(index);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getCVTerms()
	 */
	public List<CVTerm> getCVTerms() {
		if (!isSetAnnotation())
			annotation = new Annotation();
		return annotation.getListOfCVTerms();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getElementName()
	 */
	public String getElementName() {
		return StringTools.firstLetterLowerCase(getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getExtension(String namespace)
	 */
	public SBase getExtension(String namespace) {
		return this.extensions.get(namespace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getExtensionPackages()
	 */
	public HashMap<String, SBase> getExtensionPackages() {
		return extensions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getModelHistory()
	 */
	public History getHistory() {
		if (isSetAnnotation()) {
			return annotation.getHistory();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getLevel()
	 */
	public int getLevel() {
		return isSetLevel() ? this.level : -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getMetaid()
	 */
	public String getMetaId() {
		return isSetMetaId() ? metaId : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getModel()
	 */
	public Model getModel() {
		if (this instanceof Model) {
			return (Model) this;
		}
		return getParentSBMLObject() != null ? getParentSBMLObject().getModel()
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#addExtension(String namespace, SBase
	 * sbase)
	 */
	public Set<String> getNamespaces() {
		// Need to separate the list of namespaces from the extensions.
		// SBase object directly from the extension need to set their namespace.

		return this.namespaces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getNotesBuffer()
	 */
	public StringBuffer getNotesBuffer() {
		return notesBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getNotesString()
	 */
	public String getNotesString() {
		return notes != null ? notes : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getNumCVTerms()
	 */
	public int getNumCVTerms() {
		if (isSetAnnotation()) {
			return annotation.getListOfCVTerms().size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getParentSBMLObject()
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
		if (this instanceof SBMLDocument) {
			return (SBMLDocument) this;
		}
		return getParentSBMLObject() != null ? getParentSBMLObject()
				.getSBMLDocument() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getSBOTerm()
	 */
	public int getSBOTerm() {
		return sboTerm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getSBOTermID()
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
		return isSetVersion() ? this.version : -1;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#hasValidAnnotation()
	 */
	public boolean hasValidAnnotation() {
		if (isSetAnnotation()) {
			if (isSetMetaId()) {
				if (getAnnotation().getAbout().equals("#_" + getMetaId())) {
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
	 * @see org.sbml.jlibsbml.SBase#hasValidLevelVersionNamespaceCombination()
	 */
	public boolean hasValidLevelVersionNamespaceCombination() {
		boolean has = true;
		if (level == 1) {
			if (1 <= version && version <= 2) {
				has = true;
			} else {
				has = false;
			}
		} else if (level == 2) {
			if (1 <= version && version <= 4) {
				has = true;
			} else {
				has = false;
			}
		} else {
			has = false;
		}
		return has;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isExtendedByOtherPackages()
	 */
	public boolean isExtendedByOtherPackages() {
		return !this.extensions.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isSetAnnotation()
	 */
	public boolean isSetAnnotation() {
		if (annotation == null) {
			return false;
		}
		return annotation.isSetAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isSetModelHistory()
	 */
	public boolean isSetHistory() {
		if (isSetAnnotation()) {
			return annotation.isSetHistory();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isSetLevel()
	 */
	public boolean isSetLevel() {
		return level != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isSetMetaId()
	 */
	public boolean isSetMetaId() {
		return metaId != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBasee#isSetNotes()
	 */
	public boolean isSetNotes() {
		return notes != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isSetNotesBuffer()
	 */
	public boolean isSetNotesBuffer() {
		return notesBuffer != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isSetSBOTerm()
	 */
	public boolean isSetSBOTerm() {
		return sboTerm != -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#isSetVersion(int version)
	 */
	public boolean isSetVersion() {
		return version != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	public boolean readAttribute(String attributeName, String prefix,
			String value) {

		if (attributeName.equals("level") && this instanceof SBMLDocument) {
			this.level = Integer.parseInt(value);
			return true;
		} else if (attributeName.equals("version")
				&& this instanceof SBMLDocument) {
			this.version = Integer.parseInt(value);
			return true;
		} else if (attributeName.equals("sboTerm")
				&& ((getLevel() == 2 && getVersion() >= 2) || getLevel() == 3)) {
			this.setSBOTerm(value);
			return true;
		} else if (attributeName.equals("metaid")) {
			this.metaId = value;
			return true;
		}
		return false;
	}

	/**
	 * Removes all SBase change listeners from this element.
	 */
	public void removeAllSBaseChangeListeners() {
		setOfListeners.clear();
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
	 * @see org.sbml.jsbml.element.SBase#sbaseAdded()
	 */
	public void sbaseAdded() {
		for (SBaseChangedListener listener : setOfListeners) {
			listener.sbaseAdded(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#sbaseRemoved()
	 */
	public void sbaseRemoved() {
		for (SBaseChangedListener listener : setOfListeners) {
			listener.sbaseRemoved(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#setAnnotation(java.lang.String)
	 */
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
		stateChanged();
	}

	public void setHistory(History modelHistory) {
		annotation.setHistory(modelHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setLevel(int Level)
	 */
	public void setLevel(int level) {
		if (parentSBMLObject != null && parentSBMLObject.isSetLevel()) {
			if (level != parentSBMLObject.getLevel()) {
				try {
					throw new SBMLException(String.format(
							"This %s can't have a different level from %d",
							getElementName(), parentSBMLObject.getLevel()));
				} catch (SBMLException e) {
					// TODO Different level, what to do?
					e.printStackTrace();
				}
			}
		}
		this.level = level;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setMetaId(java.lang.String)
	 */
	public void setMetaId(String metaid) {
		this.metaId = metaid;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setNotes(java.lang.String)
	 */
	public void setNotes(String notes) {
		if (isSetNotes()) {
			this.notes += notes;
		} else {
			this.notes = notes;
		}
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setNotesBuffer(StringBuffer
	 * notesBuffer)
	 */
	public void setNotesBuffer(StringBuffer notesBuffer) {
		this.notesBuffer = notesBuffer;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#setAnnotation(java.lang.String)
	 */
	public void setParentSBML(SBase parent) {
		this.parentSBMLObject = parent;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setSBOTerm(int)
	 */
	public void setSBOTerm(int term) {
		if (!SBO.checkTerm(term)) {
			throw new IllegalArgumentException(
					"SBO terms must not be smaller than zero or larger than 9999999.");
		}
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
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setThisAsParentSBMLObject(AbstractSBase
	 * sbase)
	 */
	public void setThisAsParentSBMLObject(SBase sbase) {
		checkLevelAndVersionCompatibility(sbase);
		if (sbase instanceof AbstractSBase) {
			((AbstractSBase) sbase).parentSBMLObject = this;
			for (SBaseChangedListener l : setOfListeners)
				sbase.addChangeListener(l);
		}
	}

	/**
	 * Checks whether or not the given {@link SBase} has the same level and
	 * version configuration than this element.
	 * 
	 * @param sbase
	 */
	private void checkLevelAndVersionCompatibility(SBase sbase) {
		if (sbase.isSetLevel()) {
			if (sbase.getLevel() != getLevel()) {
				try {
					throw new SBMLException();
				} catch (SBMLException e) {
					// TODO Level different, what to do?
					e.printStackTrace();
				}
			}
		} else {
			sbase.setLevel(getLevel());
		}

		if (sbase.isSetVersion()) {
			if (sbase.getVersion() != getVersion()) {
				try {
					throw new SBMLException();
				} catch (SBMLException e) {
					// TODO Version different, what to do?
					e.printStackTrace();
				}
			}
		} else {
			sbase.setVersion(getVersion());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setVersion(int version)
	 */
	public void setVersion(int version) {
		if (parentSBMLObject != null && parentSBMLObject.isSetVersion()) {
			if (version != parentSBMLObject.getVersion()) {
				try {
					throw new SBMLException(String.format(
							"This %s can't have a different version from %d",
							getElementName(), parentSBMLObject.getVersion()));
				} catch (SBMLException e) {
					// TODO Different version, what to do?
					e.printStackTrace();
				}
			}
		}
		this.version = version;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#stateChanged()
	 */
	public void stateChanged() {
		for (SBaseChangedListener listener : setOfListeners) {
			listener.stateChanged(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#unsetAnnotation()
	 */
	public void unsetAnnotation() {
		if (isSetAnnotation()) {
			annotation = null;
		}
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#unsetCVTerms()
	 */
	public void unsetCVTerms() {
		if (isSetAnnotation()) {
			annotation.unsetCVTerms();
		}
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetMetaId()
	 */
	public void unsetMetaId() {
		if (isSetMetaId()) {
			metaId = null;
		}
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#unsetModelHistory()
	 */
	public void unsetModelHistory() {
		if (isSetAnnotation()) {
			this.annotation.unsetModelHistory();
			stateChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetNotes()
	 */
	public void unsetNotes() {
		if (isSetNotes()) {
			notes = null;
		}
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetNotesBuffer()
	 */
	public void unsetNotesBuffer() {
		notesBuffer = null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetSBOTerm()
	 */
	public void unsetSBOTerm() {
		sboTerm = -1;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = new HashMap<String, String>();

		if (isSetMetaId()) {
			attributes.put("metaid", getMetaId());
		}
		if (isSetSBOTerm()
				&& ((getLevel() == 2 && getVersion() >= 2) || getLevel() == 3)) {
			attributes.put("sboTerm", getSBOTermID());
		}
		return attributes;
	}

}
