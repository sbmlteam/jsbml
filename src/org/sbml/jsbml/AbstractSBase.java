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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.util.ValuePair;


/**
 * The base class for each {@link SBase} component.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @author marine
 */
public abstract class AbstractSBase implements SBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 8781459818293592636L;
	/**
	 * 
	 * @param level
	 * @param version
	 * @return
	 */
	public static boolean isValidLevelAndVersionCombination(int level, int version) {
		boolean has = false;
		if (level == 1) {
			if ((1 <= version) && (version <= 2)) {
				has = true;
			}
		} else if (level == 2) {
			if ((1 <= version) && (version <= 4)) {
				has = true;
			}
		} else if (level == 3) {
			if ((1 <= version) && (version <= 1)) {
				has = true;
			}
		}
		return has;
	}
	/**
	 * annotations of the SBML component. Matches the annotation XML node in a
	 * SBML file.
	 */
	private Annotation annotation;
	/**
	 * map containing the SBML extension object of additional packages with the
	 * appropriate name space of the package.
	 */
	private Map<String, SBase> extensions;
	/**
	 * Level and version of the SBML component. Matches the level XML attribute of a SBML
	 * node.
	 */
	ValuePair<Integer, Integer> lv;
	/**
	 * metaid of the SBML component. Matches the metaid XML attribute of an
	 * element in a SBML file.
	 */
	private String metaId;
	/**
	 * 
	 */
	private SortedSet<String> namespaces;

	/**
	 * notes of the SBML component. Matches the notes XML node in a SBML file.
	 *
	 */
	private XMLNode notesXMLNode;

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
	 * {@link Set} of listeners for this component
	 */
	protected Set<SBaseChangedListener> setOfListeners;

	/**
	 * Creates an AbstractSBase instance. By default, the sboTerm is -1, the
	 * metaid, notes, parentSBMLObject, annotation, level, version and
	 * notesBuffer are null. The setOfListeners list and the extensions hash map
	 * are empty.
	 */
	public AbstractSBase() {
		super();
		sboTerm = -1;
		metaId = null;
		notesXMLNode = null;
		lv = getLevelAndVersion();
		parentSBMLObject = null;
		annotation = null;
		setOfListeners = new HashSet<SBaseChangedListener>();
		extensions = new HashMap<String, SBase>();
		namespaces = new TreeSet<String>();
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
		if ((0 < level) && (level < 4)) {
			this.lv.setL(Integer.valueOf(level));
		} else {
			this.lv.setL(null);
		}
		if ((0 < version)) {
			this.lv.setV(Integer.valueOf(version));
		} else {
			this.lv.setV(null);
		}
		if (!hasValidLevelVersionNamespaceCombination()) {
			throw new LevelVersionError(this);
		}
	}

	/**
	 * Creates an AbstractSBase instance from a given AbstractSBase.
	 * 
	 * @param sb
	 */
	public AbstractSBase(SBase sb) {
		this(sb.getLevel(), sb.getVersion());
		this.parentSBMLObject = sb.getParentSBMLObject();
		if (sb.isSetSBOTerm()) {
			this.sboTerm = sb.getSBOTerm();
		}
		if (sb.isSetMetaId()) {
			this.metaId = new String(sb.getMetaId());
		}
		if (sb.isSetNotes()) {
			this.notesXMLNode = sb.getNotes().clone();
		}
		if (sb instanceof AbstractSBase) {
			this.setOfListeners.addAll(((AbstractSBase) sb).setOfListeners);
		}
		if (sb.isSetAnnotation()) {
			this.annotation = sb.getAnnotation().clone();
		}
		if (sb.isExtendedByOtherPackages()) {
			this.extensions.putAll(sb.getExtensionPackages());
		}
	}

	/**
	 * Recursively adds all given {@link SBaseChangedListener} instances to this
	 * element.
	 * 
	 * @param listeners
	 * @return
	 */
	public boolean addAllChangeListeners(Set<SBaseChangedListener> listeners) {
		boolean success = setOfListeners.addAll(listeners);
		Enumeration<TreeNode> children = children();
		while (children.hasMoreElements()) {
			TreeNode node = children.nextElement();
			if (node instanceof SBase) {
				success &= ((SBase) node).addAllChangeListeners(listeners);
			}
		}
		return success;
	}

	/**
	 * Recursively adds a listener to the {@link SBase} object and all of its
	 * sub-elements. From now on changes will be saved.
	 * 
	 * @param l
	 */
	public void addChangeListener(SBaseChangedListener l) {
		setOfListeners.add(l);
		Enumeration<TreeNode> children = children();
		for (int i = 0; children.hasMoreElements(); i++) {
			TreeNode node = children.nextElement();
			if (node instanceof SBase) {
				((SBase) node).addChangeListener(l);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#addCVTerm(org.sbml.jsbml.CVTerm)
	 */
	public boolean addCVTerm(CVTerm term) {
		if (!isSetAnnotation()) {
			this.annotation = new Annotation();
		}
		boolean returnValue = annotation.addCVTerm(term);
		firePropertyChange(SBaseChangedEvent.addCVTerm, null, term);
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#addExtension(java.lang.String, org.sbml.jsbml.SBase)
	 */
	public void addExtension(String namespace, SBase sbase) {
		this.extensions.put(namespace, sbase);
		addNamespace(namespace);
		firePropertyChange(SBaseChangedEvent.addExtension, null, this.extensions);
	}

	/**
	 * Adds an additional name space to the set of name spaces of this
	 * {@link SBase} if the given name space is not yet present within this
	 * {@link SortedSet}.
	 * 
	 * @param namespace
	 */
	public void addNamespace(String namespace) {
		this.namespaces.add(namespace);
		firePropertyChange(SBaseChangedEvent.addNamespace, null, namespace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#appendNotes(java.lang.String)
	 */
	public void appendNotes(String notes) {
		if (isSetNotes()) {
			// String oldNotes = notes;

			// TODO 
			
			// firePropertyChange(SBaseChangedEvent.notes, oldNotes, notes);

		} else {
			setNotes(notes);
		}
	}

	/**
	 * Checks whether or not the given {@link SBase} has the same level and
	 * version configuration than this element. If the L/V combination for the
	 * given sbase is not yet defined, this method sets it to the identical
	 * values as it is for the current object.
	 * 
	 * @param sbase the element to be checked.
	 * @return true if the given sbase and this object have the same L/V
	 *         configuration. False otherwise
	 */
	private boolean checkLevelAndVersionCompatibility(SBase sbase) {
		if (sbase.isSetLevel()) {
			if (sbase.getLevel() != getLevel()) {
				return false;
			}
		} else {
			sbase.setLevel(getLevel());
		}

		if (sbase.isSetVersion()) {
			if (sbase.getVersion() != getVersion()) {
				return false;
			}
		} else {
			sbase.setVersion(getVersion());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration<TreeNode> children() {
		return new Enumeration<TreeNode>() {
			/**
			 * Total number of children in this enumeration.
			 */
			private int childCount = getChildCount();
			/**
			 * Current position in the list of children.
			 */
			private int index = 0;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Enumeration#hasMoreElements()
			 */
			public boolean hasMoreElements() {
				return index < childCount;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Enumeration#nextElement()
			 */
			public TreeNode nextElement() {
				synchronized (this) {
					if (index < childCount) {
						return getChildAt(index++);
					}
				}
				throw new NoSuchElementException("SBase Enumeration");
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public abstract AbstractSBase clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof SBase) {
			SBase sbase = (SBase) o;
			boolean equals = true; // super.equals(o) ???
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
			return equals;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier)
	 */
	public List<CVTerm> filterCVTerms(CVTerm.Qualifier qualifier) {
		return getAnnotation().filterCVTerms(qualifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier,
	 * java.lang.String)
	 */
	public List<String> filterCVTerms(CVTerm.Qualifier qualifier, String pattern) {
		List<String> l = new LinkedList<String>();
		for (CVTerm c : filterCVTerms(qualifier)) {
			l.addAll(c.filterResources(pattern));
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		if ((setOfListeners.size() > 0)
				&& (((oldValue == null) && (newValue != null))
				|| ((oldValue != null) && (newValue == null))
				|| (oldValue != null && !oldValue.equals(newValue)))) 
		{
			SBaseChangedEvent changeEvent = new SBaseChangedEvent(this,
					propertyName, oldValue, newValue);
			for (SBaseChangedListener listener : setOfListeners) {
				listener.stateChanged(changeEvent);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#fireSBaseAddedEvent()
	 */
	public void fireSBaseAddedEvent() {
		for (SBaseChangedListener listener : setOfListeners) {
			listener.sbaseAdded(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#fireSBaseRemovedEvent()
	 */
	public void fireSBaseRemovedEvent() {
		for (SBaseChangedListener listener : setOfListeners) {
			listener.sbaseRemoved(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getAnnotation()
	 */
	public Annotation getAnnotation() {
		if (!isSetAnnotation()) {
			annotation = new Annotation();
		}
		return annotation;
	}

	/**
	 * Returns the annotation of this SBML object as a string.
	 * 
	 * @return the annotation of this SBML object as a string or an empty string
	 *         if there are no annotation.
	 * 
	 */
	public String getAnnotationString() {
		Annotation anno = getAnnotation();

		if (anno != null) {
			return anno.toXML("  ", this);
		}

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public TreeNode getChildAt(int childIndex) {
		throw new IndexOutOfBoundsException(String.format(
				"Node %s has no children.", getElementName()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getCVTerm(int)
	 */
	public CVTerm getCVTerm(int index) {
		if (isSetAnnotation()) {
			return annotation.getCVTerm(index);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getCVTerms()
	 */
	public List<CVTerm> getCVTerms() {
		if (!isSetAnnotation()) {
			annotation = new Annotation();
		}
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
	 * @see org.sbml.jsbml.SBase#getExtension(java.lang.String)
	 */
	public SBase getExtension(String namespace) {
		return this.extensions.get(namespace);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getExtensionPackages()
	 */
	public Map<String, SBase> getExtensionPackages() {
		return extensions;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getHistory()
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
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	public int getIndex(TreeNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		// linear search
		Enumeration<TreeNode> e = children();
		for (int i = 0; e.hasMoreElements(); i++) {
			TreeNode elem = e.nextElement();
			if ((node == elem) || node.equals(elem)) {
				return i;
			}
		}
		// not found => node is not a child.
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getLevel()
	 */
	public int getLevel() {
		return isSetLevel() ? this.lv.getL().intValue() : -1;
	}

	/**
	 * Access to the Level and Version combination of this {@link SBase}.
	 * 
	 * @return A {@link ValuePair} with the Level and Version of this
	 *         {@link SBase}. Note that the returned {@link ValuePair} is never
	 *         null, but if undeclared it may contain elements set to -1.
	 */
	public ValuePair<Integer, Integer> getLevelAndVersion() {
		if (this.lv == null) {
			this.lv = new ValuePair<Integer, Integer>(Integer.valueOf(-1),
					Integer.valueOf(-1));
		}
		return this.lv;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getMetaId()
	 */
	public String getMetaId() {
		return isSetMetaId() ? metaId : "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getModel()
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
	 * @see org.sbml.jsbml.SBase#getNamespaces()
	 */
	public SortedSet<String> getNamespaces() {
		// Need to separate the list of name spaces from the extensions.
		// SBase object directly from the extension need to set their name space.

		return this.namespaces;
	}

	/**
	 * 
	 * @return notes
	 */
	public XMLNode getNotes() {
		return notesXMLNode;
	}


	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getNotesString()
	 */
	public String getNotesString() {
		return notesXMLNode != null ? notesXMLNode.toXMLString() : "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getNumCVTerms()
	 */
	public int getNumCVTerms() {
		if (isSetAnnotation()) {
			return annotation.getListOfCVTerms().size();
		}
		return 0;
	}

	/**
	 * This is equivalent to calling {@link #getParentSBMLObject()}, but this
	 * method is needed for {@link TreeNode}.
	 * 
	 * @see #getParentSBMLObject()
	 */
	public SBase getParent() {
		return getParentSBMLObject();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getParentSBMLObject()
	 */
	public SBase getParentSBMLObject() {
		return parentSBMLObject;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getSBMLDocument()
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
	 * @see org.sbml.jsbml.SBase#getSBOTerm()
	 */
	public int getSBOTerm() {
		return sboTerm;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getSBOTermID()
	 */
	public String getSBOTermID() {
		return SBO.intToString(sboTerm);
	}

	/**
	 * Delivers all {@link SBaseChangeListener}s that are assigned to this
	 * element.
	 * 
	 * @return
	 */
	public Set<SBaseChangedListener> getSetOfSBaseChangeListeners() {
		return setOfListeners;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#getVersion()
	 */
	public int getVersion() {
		return isSetVersion() ? this.lv.getV().intValue() : -1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#hasValidAnnotation()
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
	 * @see org.sbml.jsbml.SBase#hasValidLevelVersionNamespaceCombination()
	 */
	public boolean hasValidLevelVersionNamespaceCombination() {
		return isValidLevelAndVersionCombination(getLevel(), getVersion());
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isExtendedByOtherPackages()
	 */
	public boolean isExtendedByOtherPackages() {
		return !this.extensions.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	public boolean isLeaf() {
		return (getChildCount() == 0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isSetAnnotation()
	 */
	public boolean isSetAnnotation() {
		return annotation != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isSetHistory()
	 */
	public boolean isSetHistory() {
		if (isSetAnnotation()) {
			return annotation.isSetHistory();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isSetLevel()
	 */
	public boolean isSetLevel() {
		return (lv != null) && (lv.getL() != null)
				&& (lv.getL().intValue() > -1);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isSetMetaId()
	 */
	public boolean isSetMetaId() {
		return metaId != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isSetNotes()
	 */
	public boolean isSetNotes() {
		return notesXMLNode != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isSetSBOTerm()
	 */
	public boolean isSetSBOTerm() {
		return sboTerm != -1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#isSetVersion()
	 */
	public boolean isSetVersion() {
		return (lv != null) && (lv.getV() != null)
				&& (lv.getV().intValue() > -1);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		if (attributeName.equals("sboTerm")) {
			setSBOTerm(value);
			return true;
		} else if (attributeName.equals("metaid")) {
			setMetaId(value);
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
	 * Recursively removes the given change listener from this element.
	 * 
	 * @param l
	 */
	public void removeChangeListener(SBaseChangedListener l) {
		setOfListeners.remove(l);
		Enumeration<TreeNode> children = children();
		for (int i = 0; children.hasMoreElements(); i++) {
			TreeNode node = children.nextElement();
			if (node instanceof SBase) {
				((SBase) node).removeChangeListener(l);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#setAnnotation(org.sbml.jsbml.Annotation)
	 */
	public void setAnnotation(Annotation annotation) {
		Annotation oldAnnotation = this.annotation;
		this.annotation = annotation;
		firePropertyChange(SBaseChangedEvent.setAnnotation, oldAnnotation, annotation);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#setHistory(org.sbml.jsbml.History)
	 */
	public void setHistory(History history) {
		History oldHistory = annotation.getHistory();
		annotation.setHistory(history);
		firePropertyChange(SBaseChangedEvent.history, oldHistory, history);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#setLevel(int)
	 */
	public void setLevel(int level) {
		if ((parentSBMLObject != null) && (parentSBMLObject != this)
				&& parentSBMLObject.isSetLevel()) {
			if (level != parentSBMLObject.getLevel()) {
				throw new LevelVersionError(this, parentSBMLObject);
			}
		}
		Integer oldLevel = getLevelAndVersion().getL();
		this.lv.setL(Integer.valueOf(level));
		firePropertyChange(SBaseChangedEvent.level, oldLevel, this.lv.getL());
	}

	/**
	 * Recursively sets the level and version attribute for this element
	 * and all sub-elements.
	 * 
	 * @param level
	 * @param version
	 * @param strict
	 * @return
	 */
	boolean setLevelAndVersion(int level, int version, boolean strict) {
		if (isValidLevelAndVersionCombination(level, version)) {
			setLevel(level);
			setVersion(version);
			// TODO: perform necessary conversion!
			boolean success = true;
			Enumeration<TreeNode> children = children();
			TreeNode child;
			while (children.hasMoreElements()) {
				child = children.nextElement();
				if (child instanceof AbstractSBase) {
					success &= ((AbstractSBase) child).setLevelAndVersion(
							level, version, strict);
				}
			}
			return success;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#setMetaId(java.lang.String)
	 */
	public void setMetaId(String metaId) {
		if (getLevel() == 1) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.metaId, this);
		}
		String oldMetaId = this.metaId;
		this.metaId = metaId;
		firePropertyChange(SBaseChangedEvent.metaId, oldMetaId, metaId);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#setNotes(java.lang.String)
	 */
	public void setNotes(XMLNode notes) {
		if (isSetNotes()) {
			// TODO : check if it is the default libsbml behavior
			// this.notes += notes;
		} else {
			this.notesXMLNode = notes;
		}
		// TODO : correct the fire Event
		firePropertyChange("notes", null, this.notesXMLNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#setNotes(java.lang.String)
	 */
	public void setNotes(String notes) {

		// TODO

		// firePropertyChange(SBaseChangedEvent.notes, oldNotes, this.notes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#setAnnotation(java.lang.String)
	 */
	public void setParentSBML(SBase parent) {
		SBase oldParent = this.parentSBMLObject;
		this.parentSBMLObject = parent;
		firePropertyChange(SBaseChangedEvent.parentSBMLObject, oldParent, parent);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#setSBOTerm(int)
	 */
	public void setSBOTerm(int term) {
		if (getLevelAndVersion().compareTo(Integer.valueOf(2),
				Integer.valueOf(2)) < 0) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.sboTerm, this);
		}
		if (!SBO.checkTerm(term)) {
			throw new IllegalArgumentException(String.format(
					"Cannot set invalid SBO term %d because it must not be smaller than zero or larger than 9999999."));
		}
		Integer oldTerm = Integer.valueOf(sboTerm);
		sboTerm = term;
		firePropertyChange(SBaseChangedEvent.sboTerm, oldTerm, sboTerm);
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
	 * @see org.sbml.jsbml.SBase#setThisAsParentSBMLObject(org.sbml.jsbml.SBase)
	 */
	public void setThisAsParentSBMLObject(SBase sbase) {
		if ((sbase != null) && checkLevelAndVersionCompatibility(sbase)) {
			if (sbase instanceof AbstractSBase) {
				((AbstractSBase) sbase).parentSBMLObject = this;
				sbase.addAllChangeListeners(getSetOfSBaseChangeListeners());
			}
			sbase.fireSBaseAddedEvent();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#setVersion(int)
	 */
	public void setVersion(int version) {
		if ((parentSBMLObject != null) && (parentSBMLObject != this)
				&& parentSBMLObject.isSetVersion()) {
			if (version != parentSBMLObject.getVersion()) {
				throw new LevelVersionError(parentSBMLObject, this);
			}
		}
		Integer oldVersion = getLevelAndVersion().getV();
		this.lv.setV(Integer.valueOf(version));
		firePropertyChange(SBaseChangedEvent.version, oldVersion, version);
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
	 * @see org.sbml.jsbml.SBase#unsetAnnotation()
	 */
	public void unsetAnnotation() {
		if (isSetAnnotation()) {
			Annotation oldAnnotation = annotation;
			annotation = null;
			firePropertyChange(SBaseChangedEvent.annotation, oldAnnotation,
					annotation);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#unsetCVTerms()
	 */
	public void unsetCVTerms() {
		if (isSetAnnotation() && getAnnotation().isSetListOfCVTerms()) {
			List<CVTerm> list = annotation.getListOfCVTerms();
			annotation.unsetCVTerms();
			firePropertyChange(SBaseChangedEvent.unsetCVTerms, list, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#unsetHistory()
	 */
	public void unsetHistory() {
		if (isSetAnnotation()) {
			History history = getHistory();
			this.annotation.unsetHistory();
			firePropertyChange(SBaseChangedEvent.history, history, getHistory());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetMetaId()
	 */
	public void unsetMetaId() {
		if (isSetMetaId()) {
			String oldMetaId = metaId; 
			metaId = null;
			firePropertyChange(SBaseChangedEvent.metaId, oldMetaId, getMetaId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetNotes()
	 */
	public void unsetNotes() {
		if (isSetNotes()) {
			XMLNode oldNotes = notesXMLNode;
			notesXMLNode = null;
			firePropertyChange(SBaseChangedEvent.notes, oldNotes, getNotes());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#unsetSBOTerm()
	 */
	public void unsetSBOTerm() {
		if (isSetSBOTerm()) {
			Integer oldSBOTerm = Integer.valueOf(sboTerm);
			sboTerm = -1;
			firePropertyChange(SBaseChangedEvent.sboTerm, oldSBOTerm, Integer
					.valueOf(getSBOTerm()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBase#writeXMLAttributes()
	 */
	public Map<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = new HashMap<String, String>();

		if (1 < getLevel()) {
			if (isSetMetaId()) {
				attributes.put("metaid", getMetaId());
			}
			if (((getLevel() == 2) && (getVersion() >= 2)) || (getLevel() == 3)) {
				if (isSetSBOTerm()) {
					attributes.put("sboTerm", getSBOTermID());
				}
			}
		}
		return attributes;
	}

}
