/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

package org.sbml.jsbml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.filters.Filter;

/**
 * This list implementation is a Java {@link List} that extends
 * {@link AbstractSBase}. It represents the listOfxxx XML elements in a SBML
 * file. Unfortunately, there is no way for multiple inheritance in Java.
 * 
 * @author Nicolas Rodriguez
 * @author Marine Dumousseau
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class ListOf<T extends SBase> extends AbstractSBase implements List<T> {
	
	/**
	 * A {@link Logger} for this class.
	 */
	private static final Logger logger = Logger.getLogger(ListOf.class);
	
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
		listOfCompartments,
		/**
		 * 
		 */
		listOfCompartmentTypes,
		/**
		 * 
		 */
		listOfConstraints,
		/**
		 * 
		 */
		listOfEventAssignments,
		/**
		 * 
		 */
		listOfEvents,
		/**
		 * 
		 */
		listOfFunctionDefinitions,
		/**
		 * 
		 */
		listOfInitialAssignments,
		/**
		 * 
		 */
		listOfLocalParameters,
		/**
		 * 
		 */
		listOfModifiers,
		/**
		 * 
		 */
		listOfParameters,
		/**
		 * 
		 */
		listOfProducts,
		/**
		 * 
		 */
		listOfReactants,
		/**
		 * 
		 */
		listOfReactions,
		/**
		 * 
		 */
		listOfRules,
		/**
		 * 
		 */
		listOfSpecies,
		/**
		 * 
		 */
		listOfSpeciesTypes,
		/**
		 * 
		 */
		listOfUnitDefinitions,
		/**
		 * 
		 */
		listOfUnits,
		/**
		 * Indicates that the {@link Type} is not known yet or has not been
		 * configured so far.
		 */
		none,
		/**
		 * For instance, it is not possible to decide between reactants and products
		 * because both elements are of the same type. Also in the extension packages
		 * this type is required.
		 */
		other;

		/**
		 * Gives the corresponding {@link Type} for the given {@link Class}
		 * object. However, in the case of {@link #listOfReactants} and
		 * {@link #listOfProducts} it is not possible to make a distinction
		 * because both types refer to the same {@link Class} object. Therefore,
		 * in this case this method will return the type {@link #other}, which
		 * is to be clearly distinguished from {@link #none}.
		 * 
		 * @param type
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public static Type valueOf(Class<? extends SBase> type) {
			if (type.equals(Compartment.class)) {
				return listOfCompartments;
			} else if(type.equals(CompartmentType.class)) {
				return listOfCompartmentTypes;
			} else if (type.equals(Constraint.class)) {
				return listOfConstraints;
			} else if (type.equals(Event.class)) {
				return listOfEvents;
			} else if (type.equals(EventAssignment.class)) {
				return listOfEventAssignments;
			} else if (type.equals(FunctionDefinition.class)) {
				return listOfFunctionDefinitions;
			} else if (type.equals(InitialAssignment.class)) {
				return listOfInitialAssignments;
			} else if (type.equals(LocalParameter.class)) {
				return listOfLocalParameters;
			} else if (type.equals(ModifierSpeciesReference.class)) {
				return listOfModifiers;
			} else if (type.equals(Parameter.class)) {
				return listOfParameters;
			} else if (type.equals(SpeciesReference.class)) {
				// Can be reactant or product!
				return other;
			} else if (type.equals(Reaction.class)) {
				return listOfReactions;
			} else if (type.equals(Rule.class)) {
				return listOfRules;
			} else if (type.equals(Species.class)) {
				return listOfSpecies;
			} else if (type.equals(SpeciesType.class)) {
				return listOfSpeciesTypes;
			} else if (type.equals(UnitDefinition.class)) {
				return listOfUnitDefinitions;
			} else if (type.equals(Unit.class)) {
				return listOfUnits;
			}
			return none;
		}
		
		/**
		 * Gives the corresponding {@link Class} object for this {@link Type}.
		 * However, in case of {@link #listOfReactants} and
		 * {@link #listOfProducts} the same {@link Class} object is returned.
		 * 
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public Class<? extends SBase> toClass() {
			switch (this) {
			case listOfCompartments:
				return Compartment.class;
			case listOfCompartmentTypes:
				return CompartmentType.class;
			case listOfConstraints:
				return Constraint.class;
			case listOfEventAssignments:
				return EventAssignment.class;
			case listOfEvents:
				return Event.class;
			case listOfFunctionDefinitions:
				return FunctionDefinition.class;
			case listOfInitialAssignments:
				return InitialAssignment.class;
			case listOfLocalParameters:
				return LocalParameter.class;
			case listOfModifiers:
				return ModifierSpeciesReference.class;
			case listOfParameters:
				return Parameter.class;
			case listOfProducts:
				return SpeciesReference.class;
			case listOfReactants:
				return SpeciesReference.class;
			case listOfReactions:
				return Reaction.class;
			case listOfRules:
				return Rule.class;
			case listOfSpecies:
				return Species.class;
			case listOfSpeciesTypes:
				return SpeciesType.class;
			case listOfUnitDefinitions:
				return UnitDefinition.class;
			case listOfUnits:
				return Unit.class;
			case none:
				return null;
			default:
				return SBase.class;
			}
		}
	}

	/**
	 * Switches the behavior of the {@link #toString()} method from displaying
	 * the {@link Type} (default) to the complete content of all instances of
	 * {@link ListOf}
	 */
	private static boolean DEBUG_MODE;

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 5757549697766609627L;

	/**
	 * Helper method to initialize newly created lists.
	 * 
	 * @param list
	 * @param type
	 */
	public static <T extends SBase> ListOf<T> initListOf(SBase parent,
			ListOf<T> list, ListOf.Type type) {
		if (parent.isSetLevel()) {
			list.setLevel(parent.getLevel());
		}
		if (parent.isSetVersion()) {
			list.setVersion(parent.getVersion());
		}
		list.setSBaseListType(type);
		/* Note:
		 * It is not possible to register the list as a child of the given parent at this position.
		 * If we would do this here, a nodeAdded-Event would be triggered before there is a pointer
		 * from the parent to the new child. Hence, callers must make sure that the created ListOf
		 * object will be registered as a child of the parent.
		 */
		return list;
	}

	/**
	 * If this method returns true, the {@link #toString()} method of this
	 * {@link ListOf} displays the whole content of the list. Otherwise, only
	 * the {@link Type} is shown.
	 * 
	 * @return the debugMode
	 */
	public static boolean isDebugMode() {
		return DEBUG_MODE;
	}
	/**
	 * Helper method to initialize a new {@link ListOf} object for the given
	 * parent SBML object and with the given {@link Class} as the type of the
	 * list.
	 * 
	 * @param <T>
	 * @param parent
	 * @param clazz
	 * @return
	 */
	public static <T extends SBase> ListOf<T> newInstance(SBase parent,
			Class<T> clazz) {
		/*
		 * Again, note that the created element cannot be registered as a child in
		 * this position. See the comment in the called method for details.
		 */
		return initListOf(parent, new ListOf<T>(), ListOf.Type.valueOf(clazz));
	}

	/**
	 * Allows you to influence the behavior of the {@link #toString()} method.
	 * If set to {@code true}, the whole content of this {@link ListOf} is
	 * displayed by the {@link #toString()} method, just like it is done in
	 * other {@link List} implementations. The default for JSBML, however, is,
	 * to only display the {@link Type} of this list.
	 * 
	 * @param debugMode
	 *            the debugMode to set
	 */
	public static void setDebugMode(boolean debugMode) {
		DEBUG_MODE = debugMode;
	}

	/**
	 * list containing all the SBase elements of this object.
	 */
	protected ArrayList<T> listOf = new ArrayList<T>();

	/**
	 * name of the list at it appears in the SBMLFile. By default, it is
	 * SBaseListType.none.
	 */
	protected Type listType = Type.none;

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
		setSBaseListType(listOf.getSBaseListType());
		
		for (SBase base : listOf) {
			if (base != null) {
				add((T) base.clone());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, T element) {
	    setThisAsParentSBMLObject(element);
	    registerChild(element);
	    listOf.add(index, element);
	}


    /*
     * (non-Javadoc) @see java.util.List#add(java.lang.Object)
     */
    public boolean add(T e) throws LevelVersionError {
  	  /*
  	   * In order to ensure that listeners are notified correctly, the element
  	   * must be added to the list before registering it as a child. However, if
  	   * something goes wrong, we have to revert this action.
  	   */
  	  try {
  	    boolean success = listOf.add(e);
  	    registerChild(e);
  	    return success;
  	  } catch (Throwable exc) {
  	    listOf.remove(e);
  	    if (exc instanceof LevelVersionError) {
  	      throw (LevelVersionError) exc;
  	    }
  	    logger.debug(exc);
  	  }
  	  return false;
   }

	/*
	 * (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends T> c) {
		if (listOf.addAll(c)) {
			for (T element : c) {
				setThisAsParentSBMLObject(element);
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		if (listOf.addAll(index, c)) {
			for (T element : c) {
				setThisAsParentSBMLObject(element);
			}
			return true;
		}
		return false;
	}

	/**
	 * Adds item to the end of this ListOf.
	 * 
	 * This variant of the method makes a clone of the item handed to it. This
	 * means that when the {@link ListOf} is destroyed, the original items will not be
	 * destroyed.
	 * 
	 * @param e
	 *            the item to be added to the list.
	 * @return true if this could be successfully appended.
	 * @see #add(T)
	 */
	@SuppressWarnings("unchecked")
	public boolean append(T e) {
		return add((T) e.clone());
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#clear()
	 */
	public void clear() {
		for (T element : listOf) {
			((TreeNodeWithChangeSupport) element).fireNodeRemovedEvent();
		}
		listOf.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public ListOf<T> clone() {
		return new ListOf<T>(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return listOf.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return listOf.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean equals = super.equals(o);
		if (equals) {
			ListOf<?> listOf = (ListOf<?>) o;
			equals &= getSBaseListType() == listOf.getSBaseListType();
		}
		return equals;
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
		ListOf<T> list = ListOf.initListOf(getParentSBMLObject(), 
				new ListOf<T>(getLevel(), getVersion()), getSBaseListType());
		for (T sbase : this) {
			if (f.accepts(sbase)) {
				list.add(sbase);
			}
		}
		list.parent = parent;
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

	/**
	 * Returns the element at the specified position in this list or null if the index is negative or too big.
	 * 
	 * @param index index of the element to return.
	 * @return the element at the specified position in this list or null if the index is negative or too big.
	 * 
	 * @see java.util.List#get(int)
	 */
	public T get(int index) {
		if (index < 0 || index >= listOf.size()) {
			return null;
		}
		
		return listOf.get(index);
	}

	/**
	 * Gets the list element which has the id 'id'. 
	 * 
	 * <p>The elements of the list have to implement {@link NamedSBase}, if they are not
	 * or if the id is not found, null is returned.
	 * 
	 * @param id the id to search for.
	 * @return the list element which has the id 'id'.
	 */
	public T get(String id) {
		
		T foundElement = null;
		
		for (T element : listOf) {
			if (element instanceof NamedSBase) {
				if (((NamedSBase) element).getId().equals(id)) {
					foundElement = element;
					break;
				}
			}
			if (element instanceof ExplicitRule) {
				if (((ExplicitRule) element).getVariable().equals(id)) {
					foundElement = element;
					break;
				}
			}
		}
		
		return foundElement;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}
		int count = super.getChildCount();
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		return get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return super.getChildCount() + size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	@Override
	public String getElementName() {
		if ((getLevel() < 3)
				&& (getSBaseListType() == ListOf.Type.listOfLocalParameters)) {
			return "listOfParameters";
		}
		return (getSBaseListType() != null ? getSBaseListType().toString()
				: Type.none.toString());
	}

	/**
	 * The first element in this list.
	 * 
	 * @return
	 */
	public T getFirst() {
		return listOf.get(0);
	}

	/**
	 * Returns the last element in this list.
	 * 
	 * @return
	 */
	public T getLast() {
		return listOf.get(listOf.size() - 1);
	}

	/**
	 * 
	 * @return the SBaseListType of this ListOf instance
	 */
	public Type getSBaseListType() {
		return listType;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return listOf.indexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return listOf.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#iterator()
	 */
	public Iterator<T> iterator() {
		return listOf.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return listOf.lastIndexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<T> listIterator() {
		return listOf.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<T> listIterator(int index) {
		return listOf.listIterator(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			// no special attributes for ListOf
		}
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	public T remove(int index) {
		T t = listOf.remove(index);
		((TreeNodeWithChangeSupport) t).fireNodeRemovedEvent();
		return t;
	}

	/**
	 * Removes a {@link NamedSBase} according to its unique id.
	 * 
	 * @param nsb
	 *            the object to be removed.
	 * @return success or failure.
	 */
	public boolean remove(NamedSBase nsb) {
		if (!listOf.remove(nsb)) {
			if (nsb.isSetId()) {
				int pos = -1;
				for (int i = 0; (i < size()) && (pos < 0); i++) {
					NamedSBase sb = (NamedSBase) get(i);
					if (sb.isSetId() && nsb.isSetId()
							&& sb.getId().equals(nsb.getId())) {
						pos = i;
					}
				}
				if (pos >= 0) {
					T t = listOf.remove(pos);
					((TreeNodeWithChangeSupport) t).fireNodeRemovedEvent();
					return true;
				}
			}
		} else {
			((TreeNodeWithChangeSupport) nsb).fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		if (!(o instanceof SBase)) {
			return false;
		}
		SBase sbase = (SBase) o;
		if (listOf.remove(sbase)) {
			((TreeNodeWithChangeSupport) o).fireNodeRemovedEvent();
			return true;
		}
		return false;
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
		if (removeId != null && removeId.trim().length() > 0) {
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
				T element = listOf.remove(pos);
				((TreeNodeWithChangeSupport) element).fireNodeRemovedEvent();
				return (T) sbase;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		boolean success = listOf.removeAll(c);
		if(success){ // TODO : a success does not mean that all elements from c have been removed from the listOf
			 for(Iterator<?> i = c.iterator(); i.hasNext();){
				 SBase element = (SBase) i.next();
				 element.fireNodeRemovedEvent();
			 }
		}
		return success;
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
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		for(T element : listOf){
			if (!c.contains(element)) {
				listOf.remove(element);
				((TreeNodeWithChangeSupport) element).fireNodeRemovedEvent();
				modified = true;
			}
		}
		return modified;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public T set(int index, T element) {
		T prevElem = listOf.set(index, element);
		// TODO: this should rather be a firePropertyChangedEvent, as the 
		// element is first removed and then added again. But the method
		// setThisAsParentSBMLObject fires a NodeAddedEvent
		((TreeNodeWithChangeSupport) element).fireNodeRemovedEvent();
		setThisAsParentSBMLObject(element);
		return prevElem;
	}

	/**
	 * Clears this {@link ListOf} if not empty and then adds all elements in 
	 * the given {@link List} to this {@link ListOf}.
	 * 
	 * @param listOf
	 */
	public void setListOf(List<T> listOf) {
		if (!this.listOf.isEmpty()) {
			this.clear();
		}
		if ((listOf != null) && (listOf.size() > 0)) {
			this.addAll(listOf);
		}
	}

	/**
	 * Sets the {@link Type} of this {@link ListOf} instance to the {@link Type}
	 * defined by the given {@link Class}.
	 * 
	 * @param type
	 */
	public void setSBaseListType(Class<T> type) {
		setSBaseListType(Type.valueOf(type));
	}
	
	/**
	 * Sets the {@link Type} of this {@link ListOf} instance to 'listType'.
	 * 
	 * @param listType
	 */
	public void setSBaseListType(Type currentList) {
		Type oldType = this.listType;
		this.listType = currentList;
		firePropertyChange(TreeNodeChangeEvent.baseListType, oldType, listType);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#size()
	 */
	public int size() {
		return listOf.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		return listOf.subList(fromIndex, toIndex);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return listOf.toArray();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.List#toArray(T[])
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return listOf.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	@Override
	public String toString() {
		
		// TODO : replace the code below by log4j debug message ?
		
		if (DEBUG_MODE) {
			return listOf.toString();
		}
		
		if (listType == null) {
			// Can happen in the clone constructor when using the SimpleSBaseChangeListener
			// The super constructor is called before listType is initialized and
			// it is using the toString() method
			return Type.none.toString();
		}
		
		return listType.toString();
	}

	/**
	 * Sets the SBaseListType of this ListOf to SBaseListType.none.
	 */
	public void unsetSBaseListType() {
		Type oldType = this.listType;
		this.listType = Type.none;
		firePropertyChange(TreeNodeChangeEvent.baseListType, oldType, listType);
	}

}
