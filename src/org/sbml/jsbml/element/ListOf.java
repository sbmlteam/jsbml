/*
 * $Id$
 * $URL$
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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
public class ListOf<T extends SBase> extends AbstractSBase implements List<T> {

	private CurrentListOfSBMLElements currentList = CurrentListOfSBMLElements.none;
	private LinkedList<T> listOf = null;

	/**
	 * 
	 */
	public ListOf() {
		super();
		this.setListOf(new LinkedList<T>());
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
	 * Specialized method to remove a named SBase according to its unique id.
	 * 
	 * @param nsb
	 *            the object to be removed.
	 * @return success or failure.
	 */
	public boolean remove(NamedSBase nsb) {
		if (!listOf.remove(nsb) && nsb.isSetId()) {
			int pos = -1;
			for (int i = 0; i < size() && pos < 0; i++) {
				NamedSBase sb = (NamedSBase) get(i);
				if (sb.isSetId() && nsb.isSetId()
						&& sb.getId().equals(nsb.getId()))
					pos = i;
			}
			if (pos >= 0) {
				listOf.remove(pos);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param listOf
	 */
	@SuppressWarnings("unchecked")
	public ListOf(ListOf<? extends SBase> listOf) {
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
		this.listOf = new LinkedList<T>();
		for (SBase base : listOf)
			add((T) base.clone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#add(java.lang.Object)
	 */
	// @Override
	public boolean add(T e) {
		if (e.getLevel() != getLevel())
			throw new IllegalArgumentException("Level mismatch between "
					+ getParentSBMLObject().getClass().getSimpleName()
					+ " in V " + getLevel() + " and "
					+ e.getClass().getSimpleName() + " in L" + e.getLevel());
		else if (e.getVersion() != getVersion())
			throw new IllegalArgumentException("Version mismatch between "
					+ getParentSBMLObject().getClass().getSimpleName()
					+ " in V" + getVersion() + " and "
					+ e.getClass().getSimpleName() + " in V" + e.getVersion());
		if (e instanceof NamedSBase) {
			NamedSBase nsb = (NamedSBase) e;
			if (nsb.isSetId())
				for (SBase element : this) {
					NamedSBase elem = ((NamedSBase) element);
					if (elem.isSetId() && elem.getId().equals(nsb.getId()))
						return false;
				}
		}
		return listOf.add(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jlibsbml.SBase#addChangeListener(org.sbml.squeezer.io.
	 * SBaseChangedListener)
	 */
	public void addChangeListener(SBaseChangedListener l) {
		setOfListeners.add(l);
		for (int i = 0; i < size(); i++)
			get(i).addChangeListener(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof ListOf<?>) {
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

			ListOf<?> listOf = (ListOf<?>) sbase;
			return listOf.containsAll(this) && equals;			
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.SBase#getElementName()
	 */
	public String getElementName() {
		String name = getCurrentList().toString();
		return name;
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
		return listOf.toString();
	}

	public void setListOf(LinkedList<T> listOf) {
		this.listOf = listOf;
	}

	@Override
	public SBase clone() {
		return new ListOf<T>(this);
	}

	public void clear() {
		listOf.clear();
		
	}

	public boolean contains(Object o) {
		return listOf.contains(o);
	}


	public int indexOf(Object o) {
		return listOf.indexOf(o);
	}

	public boolean isEmpty() {
		return listOf.isEmpty();
	}

	public Iterator<T> iterator() {
		return listOf.iterator();
	}

	public int lastIndexOf(Object o) {
		return listOf.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		return listOf.listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return listOf.listIterator(index);
	}

	public boolean remove(Object o) {
		return listOf.remove(o);
	}

	public int size() {		
		return listOf.size();
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return listOf.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return listOf.toArray();
	}


	public void add(int index, T element) {
		listOf.add(index, element);
	}


	public boolean containsAll(Collection<?> c) {
		return listOf.containsAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return listOf.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return listOf.retainAll(c);
	}

	public T set(int index, T element) {
		return listOf.set(index, element);
	}


	public T get(int index) {
		return listOf.get(index);
	}

	public T remove(int index) {
		return listOf.remove(index);
	}

	public boolean addAll(Collection<? extends T> c) {
		return listOf.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		return listOf.addAll(index, c);
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return listOf.toArray(a);
	}
	
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
	
		return attributes;
	}

}
