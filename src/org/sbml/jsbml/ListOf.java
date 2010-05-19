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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.sbml.jsbml.util.Filter;

/**
 * This list implementation is a java List that extends AbstractSBase. It
 * represents the listOfxxx XML elements in a SBML file. Unfortunately, there is
 * no way for multiple inheritance in Java.
 * 
 * @author rodrigue
 * @author marine
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class ListOf<T extends SBase> extends AbstractSBase implements List<T> {

	/**
	 * This enum lists all the possible names of the listXXX components. If the
	 * listXXX is a SBML package extension, the SBaseListType value to set would
	 * be 'other'.
	 * 
	 * @author marine
	 * 
	 */
	public static enum Type {
		/**
		 * 
		 */
		none,
		/**
		 * 
		 */
		other,
		/**
		 * 
		 */
		listOfFunctionDefinitions,
		/**
		 * 
		 */
		listOfUnitDefinitions,
		/**
		 * 
		 */
		listOfCompartments,
		/**
		 * 
		 */
		listOfSpecies,
		/**
		 * 
		 */
		listOfParameters,
		/**
		 * 
		 */
		listOfInitialAssignments,
		/**
		 * 
		 */
		listOfRules,
		/**
		 * 
		 */
		listOfReactants,
		/**
		 * 
		 */
		listOfProducts,
		/**
		 * 
		 */
		listOfEventAssignments,
		/**
		 * 
		 */
		listOfModifiers,
		/**
		 * 
		 */
		listOfConstraints,
		/**
		 * 
		 */
		listOfReactions,
		/**
		 * 
		 */
		listOfEvents,
		/**
		 * 
		 */
		listOfUnits,
		/**
		 * 
		 */
		listOfLocalParameters,
		/**
		 * 
		 */
		listOfCompartmentTypes,
		/**
		 * 
		 */
		listOfSpeciesTypes
	}

	/**
	 * name of the list at it appears in the SBMLFile. By default, it is
	 * SBaseListType.none.
	 */
	private Type currentList = Type.none;
	/**
	 * list containing all the SBase elements of this object.
	 */
	private LinkedList<T> listOf = new LinkedList<T>();

	/**
	 * Creates a ListOf instance. By default, the list containing the SBase
	 * elements is empty.
	 */
	public ListOf() {
		super();
	}

	/**
	 * Creates a ListOf instance from a level and version. By default, the list
	 * containing the SBase elements is empty.
	 */
	public ListOf(int level, int version) {
		super(level, version);
	}

	/**
	 * creates a ListOf instance from a given ListOf.
	 * 
	 * @param listOf
	 */
	@SuppressWarnings("unchecked")
	public ListOf(ListOf<? extends SBase> listOf) {
		super(listOf);
		for (SBase base : listOf) {
			if (base != null) {
				add((T) base.clone());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#add(int index, T element)
	 */
	public void add(int index, T element) {
		setThisAsParentSBMLObject(element);
		listOf.add(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#add(java.lang.Object)
	 */
	public boolean add(T e) {
		if (e.getLevel() != getLevel()) {
			throw new IllegalArgumentException(String.format(
					"Level mismatch between %s in L %d and %s in L %d",
					getClass().getSimpleName(), getLevel(), e.getClass()
							.getSimpleName(), e.getLevel()));
		} else if (e.getVersion() != getVersion()) {
			throw new IllegalArgumentException(String.format(
					"Version mismatch between %s in V %d and %s in V %d",
					getClass().getSimpleName(), getVersion(), e.getClass()
							.getSimpleName(), e.getVersion()));
		}
		// Avoid adding the same thing twice.
		if (e instanceof NamedSBase) {
			NamedSBase nsb = (NamedSBase) e;
			if (nsb.isSetId()) {
				for (SBase element : this) {
					NamedSBase elem = ((NamedSBase) element);
					if (elem.isSetId() && elem.getId().equals(nsb.getId())) {
						return false;
					}
				}
			}
		}
		for (SBaseChangedListener l : setOfListeners) {
			e.addChangeListener(l);
		}
		setThisAsParentSBMLObject(e);
		return listOf.add(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#addAll(Collection<? extends T> c)
	 */
	public boolean addAll(Collection<? extends T> c) {
		for (T element : c) {
			setThisAsParentSBMLObject(element);
		}
		return listOf.addAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#addAll(int index, Collection<? extends T> c)
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		for (T element : c)
			setThisAsParentSBMLObject(element);
		return listOf.addAll(index, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#addChangeListener(org.sbml.squeezer.io.
	 * SBaseChangedListener)
	 */
	public void addChangeListener(SBaseChangedListener l) {
		setOfListeners.add(l);
		for (T element : listOf) {
			element.addChangeListener(l);
		}
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
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	@Override
	public ListOf<T> clone() {
		return new ListOf<T>(this);
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
	 * @see java.util.containsAll(Collection<?> c)
	 */
	public boolean containsAll(Collection<?> c) {
		return listOf.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	 * @see org.sbml.jsbml.SBase#equals(org.sbml.jsbml.SBase)
	 */
	public boolean equals(SBase sbase) {
		if (sbase instanceof ListOf<?>) {
			ListOf<?> listOf = (ListOf<?>) sbase;
			return listOf.containsAll(this) && this.containsAll(listOf);
		}
		return false;
	}

	/**
	 * Returns a new {@link ListOf} that contains only those elements that
	 * satisfy a certain filter criterion.
	 * 
	 * @param f
	 *            A filter that defines the criterion for elements to be put
	 *            into the list to be returned.
	 * @return A new list that can be empty if no element fulfills the filter
	 *         criterion.
	 */
	public ListOf<T> filterList(Filter f) {
		ListOf<T> list = new ListOf<T>();
		for (T sbase : this) {
			if (f.accepts(sbase)) {
				list.add(sbase);
			}
		}
		return list;
	}

	/**
	 * Returns the first element in this list that satisfies a certain filter
	 * criterion.
	 * 
	 * @param f
	 *            A filter defining the criterion for which to be filter.
	 * @return The first element of this list that satisfies the criterion or
	 *         null if no such element exists in this list.
	 */
	public T firstHit(Filter f) {
		for (T sbase : this) {
			if (f.accepts(sbase)) {
				return sbase;
			}
		}
		return null;
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
	 * @see org.sbml.jlibsbml.SBase#getAnnotationString()
	 */
	public String getElementName() {
		return getSBaseListType().toString();
	}

	/**
	 * The first element in this list.
	 * 
	 * @return
	 */
	public T getFirst() {
		return listOf.getFirst();
	}

	/**
	 * Returns the last element in this list.
	 * 
	 * @return
	 */
	public T getLast() {
		return listOf.getLast();
	}

	/**
	 * 
	 * @return the SBaseListType of this ListOf instance
	 */
	public Type getSBaseListType() {
		return currentList;
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
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		// TODO: What is the purpose of this method?
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#remove(int index)
	 */
	public T remove(int index) {
		T t = listOf.remove(index);
		if (t instanceof AbstractSBase)
			((AbstractSBase) t).parentSBMLObject = null;
		t.sbaseRemoved();
		return t;
	}

	/**
	 * Removes a named SBase according to its unique id.
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
						&& sb.getId().equals(nsb.getId())) {
					pos = i;
				}
			}
			if (pos >= 0) {
				T t = listOf.remove(pos);
				if (t instanceof AbstractSBase)
					((AbstractSBase) t).parentSBMLObject = null;
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#remove(Object o)
	 */
	public boolean remove(Object o) {
		return listOf.remove(o);
	}

	/**
	 * Specialized method to remove a named SBase according to its unique id.
	 * 
	 * @param id
	 *            the id of the object to be removed.
	 * @return success or failure.
	 */
	@SuppressWarnings("unchecked")
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedList#removeAll(Collection<?> c)
	 */
	public boolean removeAll(Collection<?> c) {
		return listOf.removeAll(c);
	}

	/**
	 * Removes all elements from this list that fulfill the filter property of
	 * the given filter.
	 * 
	 * @param f
	 * @return
	 */
	public boolean removeAll(Filter f) {
		return removeAll(filterList(f));
	}

	/**
	 * 
	 * @param l
	 */
	public void removeChangeListener(SBaseChangedListener l) {
		setOfListeners.remove(l);
	}

	/**
	 * Removes the first element from this list that fulfills the filter
	 * property of the given filter.
	 * 
	 * @param f
	 * @return
	 */
	public T removeFirst(Filter f) {
		T t = firstHit(f);
		if (remove(t))
			return t;
		return null;
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
		setThisAsParentSBMLObject(element);
		return listOf.set(index, element);
	}

	/**
	 * Sets the listOf of this Object to 'listOf'.
	 * 
	 * @param listOf
	 */
	public void setListOf(List<T> listOf) {
		boolean change = false;
		if (!this.listOf.isEmpty()) {
			this.listOf.clear();
			change = true;
		}
		if (listOf.size() > 0) {
			this.listOf.addAll(listOf);
			change = true;
		}
		if (change) {
			stateChanged();
		}
	}

	/**
	 * Sets the SBaseListType of this ListOf instance to 'currentList'.
	 * 
	 * @param currentList
	 */
	public void setSBaseListType(Type currentList) {
		this.currentList = currentList;
		stateChanged();
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
	 * @see java.util.LinkedList#toArray(T[] a)
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return listOf.toArray(a);
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
	 * Sets the SBaseListType of this ListOf to SBaseListType.none.
	 */
	public void unsetSBaseListType() {
		this.currentList = Type.none;
	}
}
