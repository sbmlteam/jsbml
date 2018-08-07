/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
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
 * 
 */
public class ListOf<T extends SBase> extends AbstractSBase implements List<T>, UniqueSId {

  /**
   * This enum lists all the possible names of the listXXX components. If the
   * listXXX is a SBML package extension, the SBaseListType value to set would
   * be 'other'.
   * 
   * @author Marine Dumousseau
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
     * For instance, it is not possible to decide between reactants and
     * products because both elements are of the same type. Also in the
     * extension packages this type is required.
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
     * @param type the type
     * @return the corresponding {@link Type} for the given {@link Class}
     * object.
     */
    @SuppressWarnings("deprecation")
    public static Type valueOf(Class<? extends SBase> type) {
      if (type.equals(Compartment.class)) {
        return listOfCompartments;
      } else if (type.equals(CompartmentType.class)) {
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
      return other;
    }

    /**
     * Gives the corresponding {@link Class} object for this {@link Type}.
     * <p>However, in case of {@link #listOfReactants} and
     * {@link #listOfProducts} the same {@link Class} object is returned.</p>
     * 
     * @return the corresponding {@link Class} object for this {@link Type}.
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
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ListOf.class);

  /**
   * Initializes newly created lists.
   * 
   * @param parent the list parent, used to set the SBML level and version
   * @param list the list to initialize
   * @param type the type of the list
   * 
   * @return the initialized list for convenience
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
   * If this method returns {@code true}, the {@link #toString()} method of this
   * {@link ListOf} displays the whole content of the list. Otherwise, only
   * the {@link Type} is shown.
   * 
   * @return the debugMode
   */
  public static boolean isDebugMode() {
    return DEBUG_MODE;
  }

  /**
   * Initializes a new {@link ListOf} object for the given
   * parent SBML object and with the given {@link Class} as the type of the
   * list.
   * 
   * @param parent the ListOf parent
   * @param clazz the class of objects the new ListOf will contain
   * @return a new {@link ListOf} object for the given
   * parent SBML object and with the given {@link Class} as the type of the
   * list.
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
  protected List<T> listOf = new ArrayList<T>();

  /**
   * name of the list at it appears in the SBMLFile. By default, it is
   * SBaseListType.none.
   */
  protected Type listType = Type.none;

  /**
   * name of the list at it appears in the SBMLFile. By default, it is
   * null. This value is used only if the type of the ListOf is {@link Type#other}.
   */
  protected String otherListName = null;

    
  /**
   * Creates a ListOf instance. By default, the list containing the SBase
   * elements is empty.
   */
  public ListOf() {
    super();
  }

  /**
   * Creates a {@link ListOf} instance from a level and version. By default, the
   * list containing the SBase elements is empty.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public ListOf(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a ListOf instance from a given ListOf.
   * 
   * @param listOf the list to clone
   */
  @SuppressWarnings("unchecked")
  public ListOf(ListOf<? extends SBase> listOf) {
    super(listOf);
    setSBaseListType(listOf.getSBaseListType());
    setOtherListName(listOf.getOtherListName());
    
    for (SBase base : listOf) {
      if (base != null) {
        add((T) base.clone());
      }
    }
  }

  /**
   * Creates a new {@link ListOf} instance with the given id. 
   * 
   * @param id the ListOf id.
   */
  public ListOf(String id) {
    super(id);
  }

  /**
   * Creates a new {@link ListOf} instance.
   * 
   * @param id the ListOf id.
   * @param level the SBML level.
   * @param version the SBML version.
   */
  public ListOf(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new {@link ListOf} instance.
   * 
   * @param id the ListOf id.
   * @param name the ListOf name.
   * @param level the SBML level.
   * @param version the SBML version.
   */
  public ListOf(String id, String name, int level, int version) {
    super(id, name, level, version);
  }

  /* (non-Javadoc)
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(int index, T element) {
    registerChild(element);
    listOf.add(index, element);
  }

  /* (non-Javadoc) @see java.util.List#add(java.lang.Object)
   */
  @Override
  public boolean add(T element) throws LevelVersionError {
    /*
     * In order to ensure that listeners are notified correctly, the element
     * must be added to the list before registering it as a child. However, if
     * something goes wrong, we have to revert this action.
     */
    try {
      if (listOf.add(element)) {
        if (registerChild(element)) {
          return true;
        }
        listOf.remove(listOf.size() - 1);
      }
      return false;
    } catch (RuntimeException exc) {
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format(
          "Reverting change: removing element {0} from internal list", element));
      }
      // using the position when removing to be sure to delete the added element (and it is more efficient)
      listOf.remove(listOf.size() - 1);
      throw exc;
    }
  }

  /* (non-Javadoc)
   * @see java.util.List#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends T> c) throws LevelVersionError {
    if (listOf.addAll(c)) {
      for (T element : c) {
        try {
          registerChild(element);
        } catch (RuntimeException exc) {
          logger.debug(MessageFormat.format(
            "Reverting change: removing all elements from collection {0} from internal list",
            c));
          listOf.removeAll(c);
          throw exc;
        }
      }
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.util.List#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int index, Collection<? extends T> collection) throws LevelVersionError {
    if (listOf.addAll(index, collection)) {
      for (T element : collection) {
        try {
          registerChild(element);
        } catch (RuntimeException exc) {
          logger.debug(MessageFormat.format(
            "Reverting change: removing all elements from collection {0} from internal list",
            collection));
          listOf.removeAll(collection);
          throw exc;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Adds item to the end of this {@link ListOf}.
   * 
   * This variant of the method makes a clone of the item handed to it. This
   * means that when the {@link ListOf} is destroyed, the original items will
   * not be destroyed.
   * 
   * @param element
   *            the item to be added to the list.
   * @return {@code true} if this could be successfully appended.
   * @throws LevelVersionError if the SBML level and version of the new element is different from the SBML level and version of list.
   * @see #add(SBase)
   */
  @SuppressWarnings("unchecked")
  public boolean append(T element) throws LevelVersionError {
    return add((T) element.clone());
  }

  /* (non-Javadoc)
   * @see java.util.List#clear()
   */
  @Override
  public void clear() {
    for (T element : listOf) {
      ((TreeNodeWithChangeSupport) element).fireNodeRemovedEvent();
    }
    listOf.clear();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public ListOf<T> clone() {
    return new ListOf<T>(this);
  }

  /* (non-Javadoc)
   * @see java.util.List#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object o) {
    return listOf.contains(o);
  }

  /* (non-Javadoc)
   * @see java.util.List#containsAll(java.util.Collection)
   */
  @Override
  public boolean containsAll(Collection<?> c) {
    return listOf.containsAll(c);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    boolean equals = super.equals(o);
    if (equals) {
      ListOf<?> listOf = (ListOf<?>) o;
      equals &= getSBaseListType() == listOf.getSBaseListType();
      equals &= getOtherListName() == listOf.getOtherListName();
    }
    return equals;
  }

  /**
   * Returns a new {@link List} that contains only those elements that
   * satisfy a certain filter criterion.
   * 
   * @param f
   *            A filter that defines the criterion for elements to be put
   *            into the list to be returned.
   * @return A new list that can be empty if no element fulfills the filter
   *         criterion.
   */
  public List<T> filterList(Filter f) {
    /*
     * The new list should not be linked to the model or SBMLDocument
     * as otherwise, it will try to register elements that are
     * already registered and send an exception instead doing any
     * filtering
     */
    ListOf<T> list = new ListOf<T>(getLevel(), getVersion());
    list.setSBaseListType(getSBaseListType());

    for (T sbase : this) {
      if (f.accepts(sbase)) {
        list.listOf.add(sbase);
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
  @Override
  public T get(int index) {
    if ((index < 0) || (index >= listOf.size())) {
      return null; // TODO - throw IndexOutOfBoundsException instead of null here ?
    }

    return listOf.get(index);
  }

  /**
   * Gets the list element which has the id 'id'.
   * 
   * <p>The elements of the list have to implement {@link SBase}, if they are not
   * or if the id is not found, null is returned.
   * 
   * @param id the id to search for.
   * @return the list element which has the id 'id'.
   */
  public T get(String id) {

    T foundElement = null;

    for (T element : listOf) {
      if (element instanceof SBase) {
        if (((SBase) element).getId().equals(id)) {
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount();
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    return get(index);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return super.getChildCount() + size();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    if ((getLevel() < 3)
        && (listType == Type.listOfLocalParameters)) {
      return Type.listOfParameters.toString();
    }
    
    if ((listType == Type.other) && otherListName != null) 
    {
      return otherListName;
    }
    else if ((listType == Type.other) && (listOf.size() > 0)) 
    {
      // Important for extension packages:
      String className = getFirst().getClass().getSimpleName();

      return StringTools.firstLetterLowerCase(getClass().getSimpleName())
          + className
          + ((className.endsWith("s") || className.toLowerCase()
              .endsWith("information")) ? "" : "s");
    }
    
    return (listType != null) ? listType.toString() : Type.none.toString();
  }

  /**
   * Returns the first element in this list.
   * 
   * @return the first element in this list.
   * @throws IndexOutOfBoundsException if there are no element in the list
   */
  public T getFirst() {
    return listOf.get(0);
  }

  /**
   * Returns the last element in this list.
   * 
   * @return the last element in this list.
   * @throws IndexOutOfBoundsException if there are no element in the list
   */
  public T getLast() {
    return listOf.get(listOf.size() - 1);
  }

  /**
   * Returns the SBaseListType of this ListOf instance.
   * 
   * @return the SBaseListType of this ListOf instance
   */
  public Type getSBaseListType() {
    return listType;
  }

  /* (non-Javadoc)
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Override
  public int indexOf(Object o) {
    return listOf.indexOf(o);
  }

  /* (non-Javadoc)
   * @see java.util.List#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return listOf.isEmpty();
  }

  /* (non-Javadoc)
   * @see java.util.List#iterator()
   */
  @Override
  public Iterator<T> iterator() {
    return listOf.iterator();
  }

  /* (non-Javadoc)
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */
  @Override
  public int lastIndexOf(Object o) {
    return listOf.lastIndexOf(o);
  }

  /* (non-Javadoc)
   * @see java.util.List#listIterator()
   */
  @Override
  public ListIterator<T> listIterator() {
    return listOf.listIterator();
  }

  /* (non-Javadoc)
   * @see java.util.List#listIterator(int)
   */
  @Override
  public ListIterator<T> listIterator(int index) {
    return listOf.listIterator(index);
  }

  /* (non-Javadoc)
   * @see java.util.List#remove(int)
   */
  @Override
  public T remove(int index) {
    T t = listOf.remove(index);
    ((TreeNodeWithChangeSupport) t).fireNodeRemovedEvent();
    return t;
  }

  /**
   * Removes a {@link SBase} according to its unique id.
   * 
   * @param nsb
   *            the object to be removed.
   * @return success or failure.
   */
  public boolean remove(SBase nsb) {
    if (!listOf.remove(nsb)) {
      if (nsb.isSetId()) {
        int pos = -1;
        for (int i = 0; (i < size()) && (pos < 0); i++) {
          SBase sb = (SBase) get(i);
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

  /* (non-Javadoc)
   * @see java.util.List#remove(java.lang.Object)
   */
  @Override
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
   * @param removeId
   *            the id of the object to be removed.
   * @return success or failure.
   */
  @SuppressWarnings("unchecked")
  public T remove(String removeId) {
    if (removeId != null && removeId.trim().length() > 0) {
      int pos = -1;
      SBase sbase = null;
      for (int i = 0; i < size() && pos < 0; i++) {
        if (! (get(i) instanceof NamedSBase)) {
          return null;
        }
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

  /* (non-Javadoc)
   * @see java.util.List#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection<?> c) {
    boolean success = listOf.removeAll(c);
    if (success) { // TODO: a success does not mean that all elements from c have been removed from the listOf
      for (Iterator<?> i = c.iterator(); i.hasNext();) {
        SBase element = (SBase) i.next();
        element.fireNodeRemovedEvent();
      }
    }
    return success;
  }

  /**
   * Removes all elements from this list that are accepted by the {@link Filter#accepts(Object)}
   * method of the given filter.
   * 
   * @param f a {@link Filter} to apply
   * @return {@code true} if this list changed as a result of the call
   */
  public boolean removeAll(Filter f) {
    return removeAll(filterList(f));
  }

  /**
   * Removes the first element from this list that is accepted by the {@link Filter#accepts(Object)}
   * method of the given filter.
   * 
   * @param f a {@link Filter} to apply
   * @return the removed element or null if no elements satisfied the filter
   */
  public T removeFirst(Filter f) {
    T t = firstHit(f);
    if (remove(t)) {
      return t;
    }
    return null;
  }

  /* (non-Javadoc)
   * @see java.util.List#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection<?> c) {
    boolean modified = false;
    for (T element : listOf) {
      if (!c.contains(element)) {
        listOf.remove(element);
        ((TreeNodeWithChangeSupport) element).fireNodeRemovedEvent();
        modified = true;
      }
    }
    return modified;
  }

  /* (non-Javadoc)
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public T set(int index, T element) throws LevelVersionError {
    T prevElem = listOf.set(index, element);

    if (prevElem != null) {
      ((TreeNodeWithChangeSupport) prevElem).fireNodeRemovedEvent();
    }
    try {
      registerChild(element);
    } catch (RuntimeException exc) {
      logger.debug(MessageFormat.format(
        "Reverting change: removing element {0} from internal list",
        element));
      listOf.remove(index);
      throw exc;
    }
    return prevElem;
  }

  /**
   * Clears this {@link ListOf} if not empty and then adds all elements in
   * the given {@link List} to this {@link ListOf}.
   * 
   * @param listOf the list that contain all the elements to add to this {@link ListOf}
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
   * Sets the name of the package to which this {@link SBase} belong.
   * 
   * <p>This an internal method that should not be used outside of the main jsbml code
   * (core + packages). One class should always belong to the same package.
   * You have to know what you are doing when using this method.
   * 
   * @param newPackageName the name of the package to which this {@link SBase} belong.
   */
  public void setPackageName(String newPackageName) {

    if ((packageName != null) && (newPackageName != null) && (!packageName.equals(newPackageName))) {
      // if we implement proper conversion some days, we need to unset the namespace before changing it.
      logger.error(MessageFormat.format("An SBase element cannot belong to two different packages! Current package = ''{0}'', new package = ''{1}''", packageName, newPackageName));
    }
    String old = packageName;
    packageName = newPackageName;

    firePropertyChange(TreeNodeChangeEvent.packageName, old, newPackageName);
  }

  /**
   * Sets the {@link Type} of this {@link ListOf} instance to the {@link Type}
   * defined by the given {@link Class}.
   * 
   * @param type a class instance
   */
  public void setSBaseListType(Class<T> type) {
    setSBaseListType(Type.valueOf(type));
  }

  /**
   * Sets the {@link Type} of this {@link ListOf} instance to 'listType'.
   * 
   * @param listType a list {@link Type}
   */
  public void setSBaseListType(Type listType) {
    Type oldType = this.listType;
    this.listType = listType;
    firePropertyChange(TreeNodeChangeEvent.baseListType, oldType, listType);
  }

  /* (non-Javadoc)
   * @see java.util.List#size()
   */
  @Override
  public int size() {
    return listOf.size();
  }

  /* (non-Javadoc)
   * @see java.util.List#subList(int, int)
   */
  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return listOf.subList(fromIndex, toIndex);
  }

  /* (non-Javadoc)
   * @see java.util.List#toArray()
   */
  @Override
  public Object[] toArray() {
    return listOf.toArray();
  }

  /* (non-Javadoc)
   * @see java.util.List#toArray(T[])
   */
  @Override
  @SuppressWarnings("hiding")
  public <T> T[] toArray(T[] a) {
    return listOf.toArray(a);
  }

  /**
   * Sets the SBaseListType of this ListOf to SBaseListType.none.
   */
  public void unsetSBaseListType() {
    Type oldType = this.listType;
    this.listType = Type.none;
    firePropertyChange(TreeNodeChangeEvent.baseListType, oldType, listType);
  }

  
  /**
   * Returns the XML element name used when the ListOf type is set to {@link Type#other}.
   *
   * <p>This an internal method that should not be used outside of the main jsbml code
   * (packages). You have to know what you are doing when using this method.
   * 
   * @return the XML element name or null if it was not set.
   */
  public String getOtherListName() {
    return otherListName;
  }

  /**
   * Sets the XML element name used when the ListOf type is set to {@link Type#other}.
   *
   * <p>This an internal method that should not be used outside of the main jsbml code
   * (packages). You have to know what you are doing when using this method.
   *
   * @param otherListName the value of otherListName to be set.
   */
  public void setOtherListName(String otherListName) {
    this.otherListName = otherListName;
  }
}
