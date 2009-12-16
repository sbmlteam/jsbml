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

package org.sbml.jsbml;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.sbml.jsbml.xml.stax.SBaseListType;

/**
 * This list implementation is a java List that extends AbstractSBase. It represents the listOfxxx
 * XML elements in a SBML file. Unfortunately, there is no way for multiple inheritance
 * in Java.
 * @author rodrigue
 * @author marine
 * @author Andreas Dr&auml;ger
 * 
 */
public class ListOf<T extends SBase> extends AbstractSBase implements List<T> {

	/**
	 * name of the list at it appears in the SBMLFile. By default, it is SBaseListType.none.
	 */
	private SBaseListType currentList = SBaseListType.none;
	/**
	 * list containing all the SBase elements of this object.
	 */
	private LinkedList<T> listOf = new LinkedList<T>();

	/**
	 * Creates a ListOf instance. By default, the list containing the SBase elements is empty.
	 */
	public ListOf() {
		super();
	}
	
	/**
	 * Creates a ListOf instance from a level and version. By default, the list containing the SBase elements is empty.
	 */
	public ListOf(int level, int version) {
		super(level, version);
	}

	/**
	 * Specialized method to remove a named SBase according to its unique id.
	 * 
	 * @param nsb the object to be removed.
	 * @return success or failure.
	 */
	public boolean remove(NamedSBase nsb) {
		if (!listOf.remove(nsb) && nsb.isSetId()) {
			int pos = -1;
			for (int i = 0; i < size() && pos < 0; i++) {
				NamedSBase sb = (NamedSBase) get(i);
				if (sb.isSetId() && nsb.isSetId()
						&& sb.getId().equals(nsb.getId())){
					pos = i;
				}
			}
			if (pos >= 0) {
				listOf.remove(pos);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Specialized method to remove a named SBase according to its unique id.
	 * 
	 * @param id the id of the object to be removed.
	 * @return success or failure.
	 */
	public T remove(String removeId) {
		
		if (removeId != null && removeId.trim().length() == 0) {
			int pos = -1;
			SBase sbase = null; 
			for (int i = 0; i < size() && pos < 0; i++) {
				NamedSBase sb = (NamedSBase) get(i);
				if (sb.isSetId() && sb.getId().equals(removeId)) {
					pos = i;
					sbase = sb;
					break;
				}
			}
			if (pos >= 0) {
				listOf.remove(pos);
				return (T) sbase;
			}
		}
		return null;
	}

	/**
	 * creates a ListOf instance from a given ListOf.
	 * @param listOf
	 */
	@SuppressWarnings("unchecked")
	public ListOf(ListOf<? extends SBase> listOf) {
		super(listOf);
		for (SBase base : listOf){
			if (base != null){
				add((T) base.clone());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#add(java.lang.Object)
	 */
	// @Override
	public boolean add(T e) {
		if (e.getLevel() != getLevel()){
			throw new IllegalArgumentException("Level mismatch between "
					+ getParentSBMLObject().getClass().getSimpleName()
					+ " in V " + getLevel() + " and "
					+ e.getClass().getSimpleName() + " in L" + e.getLevel());
		}
		else if (e.getVersion() != getVersion()){
			throw new IllegalArgumentException("Version mismatch between "
					+ getParentSBMLObject().getClass().getSimpleName()
					+ " in V" + getVersion() + " and "
					+ e.getClass().getSimpleName() + " in V" + e.getVersion());
		}
		if (e instanceof NamedSBase) {
			NamedSBase nsb = (NamedSBase) e;
			if (nsb.isSetId()){
				for (SBase element : this) {
					NamedSBase elem = ((NamedSBase) element);
					if (elem.isSetId() && elem.getId().equals(nsb.getId())){
						return false;
					}
				}
			}
		}
		return listOf.add(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#addChangeListener(org.sbml.squeezer.io.
	 * SBaseChangedListener)
	 */
	public void addChangeListener(SBaseChangedListener l) {
		setOfListeners.add(l);
		for (int i = 0; i < size(); i++){
			get(i).addChangeListener(l);
		}
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
			ListOf<?> listOf = (ListOf<?>) o;
			equals &= getSBaseListType() == listOf.getSBaseListType();
			return listOf.containsAll(this) && equals;			
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getElementName()
	 */
	public String getElementName() {
		String name = getSBaseListType().toString();
		return name;
	}

	/**
	 * Sets the SBaseListType of this ListOf instance to 'currentList'.
	 * @param currentList
	 */
	public void setSBaseListType(SBaseListType currentList) {
		this.currentList = currentList;
		stateChanged();
	}

	/**
	 * 
	 * @return the SBaseListType of this ListOf instance
	 */
	public SBaseListType getSBaseListType() {
		return currentList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	public boolean readAttribute(String attributeName, String prefix, String value){
	
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#toString()
	 */
	@Override
	public String toString() {
		return listOf.toString();
	}

	/**
	 * Sets the listOf of this Object to 'listOf'.
	 * @param listOf
	 */
	public void setListOf(LinkedList<T> listOf) {
		this.listOf = listOf;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	@Override
	public SBase clone() {
		return new ListOf<T>(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#clear()
	 */
	public void clear() {
		listOf.clear();
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#contains(Object o)
	 */
	public boolean contains(Object o) {
		return listOf.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#indexOf(Object o)
	 */
	public int indexOf(Object o) {
		return listOf.indexOf(o);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#isEmpty()
	 */
	public boolean isEmpty() {
		return listOf.isEmpty();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#iterator()
	 */
	public Iterator<T> iterator() {
		return listOf.iterator();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#lastIndexOf(Object o)
	 */
	public int lastIndexOf(Object o) {
		return listOf.lastIndexOf(o);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#listIterator()
	 */
	public ListIterator<T> listIterator() {
		return listOf.listIterator();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#listIterator(int index)
	 */
	public ListIterator<T> listIterator(int index) {
		return listOf.listIterator(index);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#remove(Object o)
	 */
	public boolean remove(Object o) {
		return listOf.remove(o);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#size()
	 */
	public int size() {		
		return listOf.size();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#subList(int fromIndex, int toIndex)
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		return listOf.subList(fromIndex, toIndex);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#toArray()
	 */
	public Object[] toArray() {
		return listOf.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#add(int index, T element)
	 */
	public void add(int index, T element) {
		listOf.add(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.containsAll(Collection<?> c)
	 */
	public boolean containsAll(Collection<?> c) {
		return listOf.containsAll(c);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#removeAll(Collection<?> c)
	 */
	public boolean removeAll(Collection<?> c) {
		return listOf.removeAll(c);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#retainAll(Collection<?> c)
	 */
	public boolean retainAll(Collection<?> c) {
		return listOf.retainAll(c);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#set(int index, T element)
	 */
	public T set(int index, T element) {
		return listOf.set(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#get(int index)
	 */
	public T get(int index) {
		return listOf.get(index);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#remove(int index)
	 */
	public T remove(int index) {
		return listOf.remove(index);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#addAll(Collection<? extends T> c)
	 */
	public boolean addAll(Collection<? extends T> c) {
		return listOf.addAll(c);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#addAll(int index, Collection<? extends T> c)
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		return listOf.addAll(index, c);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#toArray(T[] a)
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return listOf.toArray(a);
	}
	
	/**
	 * Sets the SBaseListType of this ListOf to SBaseListType.none.
	 */
	public void unsetSBaseListType(){
		this.currentList = SBaseListType.none;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
	
		return attributes;
	}

}
