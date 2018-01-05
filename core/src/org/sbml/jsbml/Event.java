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
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * Represents the event XML element of a SBML file. Since {@link Event}s were
 * introduced to SBML in Level 2, this class must not be used for models in
 * Level 1.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 */
public class Event extends AbstractNamedSBaseWithUnit implements
UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5282750820355199194L;
  /**
   * Represents the delay sub-element of an event element.
   */
  private Delay delay;
  /**
   * 
   */
  private boolean isSetUseValuesFromTriggerTime;

  /**
   * Represents the listOfEventAssignments sub-element of an event element.
   */
  private ListOf<EventAssignment> listOfEventAssignments;

  /**
   * Represents the priority sub-element of an event.
   */
  private Priority priority;

  /**
   * Represents the trigger sub-element of an event element.
   */
  private Trigger trigger;

  /**
   * Represents the 'useValuesFromTriggerTime' XML attribute of an event
   * element.
   */
  private Boolean useValuesFromTriggerTime;

  /**
   * Creates an Event instance. By default, if the level is set and is
   * superior or equal to 3, the trigger, delay, listOfEventAssignemnts and
   * timeUnitsID are {@code null}.
   */
  public Event() {
    super();
    initDefaults();
  }

  /**
   * Creates an Event instance from a given event.
   * 
   * @param event
   */
  public Event(Event event) {
    super(event);

    if (event.isSetTrigger()) {
      setTrigger(event.getTrigger().clone());
    }
    if (event.isSetUseValuesFromTriggerTime()) {
      setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
    } else {
      useValuesFromTriggerTime = event.useValuesFromTriggerTime == null ? null : new Boolean(event.useValuesFromTriggerTime);
    }
    if (event.isSetDelay()) {
      setDelay(event.getDelay().clone());
    }
    if (event.isSetListOfEventAssignments()) {
      setListOfEventAssignments(event
        .getListOfEventAssignments().clone());
    }
    if (event.isSetPriority()) {
      setPriority(event.getPriority().clone());
    }
  }

  /**
   * Creates an Event from a level and version. By default, if the level is
   * set and is superior or equal to 3, the trigger, delay,
   * {@link #listOfEventAssignments} and timeUnitsID are {@code null}.
   * 
   * @param level
   * @param version
   */
  public Event(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * 
   * @param id
   */
  public Event(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates an Event instance from an id, level and version. By default, if
   * the level is set and is superior or equal to 3, the trigger, delay,
   * listOfEventAssignemnts and timeUnitsID are {@code null}.
   * 
   * @param id
   * @param level
   * @param version
   */
  public Event(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * Creates an Event instance from an id, name, level and version. By
   * default, if the level is set and is superior or equal to 3, the trigger,
   * delay, listOfEventAssignemnts and timeUnitsID are {@code null}.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Event(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /**
   * Adds a new {@link EventAssignment} to the listOfEventAssignments.
   * <p>The listOfEventAssignments is initialized if necessary.
   * 
   * @param eventass the element to add to the list
   * @return {@code true} if the {@link #listOfEventAssignments} was
   *         changed as a result of this call.
   */
  public boolean addEventAssignment(EventAssignment eventass) {
    return getListOfEventAssignments().add(eventass);
  }

  /**
   * Remove all the EventAssignments of the listOfEventAssignments of this
   * Event.
   */
  public void clearListOfEventAssignments() {
    if (isSetListOfEventAssignments()) {
      listOfEventAssignments.clear();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#clone()
   */
  @Override
  public Event clone() {
    return new Event(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#containsUndeclaredUnits()
   */
  @Override
  @Deprecated
  public boolean containsUndeclaredUnits() {
    return super.containsUndeclaredUnits();
  }

  /**
   * Creates a new {@link Delay} instance and sets it to this {@link Event}.
   * 
   * @return the new {@link Delay} instance.
   */
  public Delay createDelay() {
    Delay d = new Delay(getLevel(), getVersion());
    d.addAllChangeListeners(getListOfTreeNodeChangeListeners());
    setDelay(d);
    return d;
  }

  /**
   * Creates a new {@link Delay} instance and sets it to this {@link Event}.
   * 
   * @param math the math expression for the new {@link Delay}
   * @return the new {@link Delay} instance.
   */
  public Delay createDelay(ASTNode math) {
    Delay d = createDelay();
    d.setMath(math);
    return d;
  }

  /**
   * Creates a new {@link EventAssignment} element and adds it to the ListOfEventAssignments list.
   * 
   * @return the new {@link EventAssignment} instance.
   */
  public EventAssignment createEventAssignment() {
    EventAssignment ea = new EventAssignment(getLevel(), getVersion());
    addEventAssignment(ea);
    return ea;
  }

  /**
   * Creates a new {@link EventAssignment} element and adds it to the ListOfEventAssignments list.
   * 
   * @param variable the target of the new {@link EventAssignment}
   * @param math the math expression for the new {@link EventAssignment}
   * @return the new {@link EventAssignment} instance.
   */
  public EventAssignment createEventAssignment(String variable, ASTNode math) {
    EventAssignment ea = createEventAssignment();
    ea.setVariable(variable);
    ea.setMath(math);
    return ea;
  }

  /**
   * Creates a new {@link EventAssignment} element and adds it to the ListOfEventAssignments list.
   * 
   * @param variable the target of the new {@link EventAssignment}
   * @param math the math expression for the new {@link EventAssignment}
   * @return the new {@link EventAssignment} instance.
   */
  public EventAssignment createEventAssignment(Variable variable, ASTNode math) {
    return createEventAssignment(variable.getId(), math);
  }

  /**
   * Creates a new, empty {@link Priority}, sets it to this {@link Event} and
   * returns the {@link Priority}.
   * 
   * @return the newly created {@link Priority} object instance
   */
  public Priority createPriority() {
    Priority p = new Priority(getLevel(), getVersion());
    setPriority(p);
    return p;
  }

  /**
   * Creates a new {@link Priority}, sets it to this {@link Event} and
   * returns it.
   * 
   * @return the newly created {@link Priority} object instance
   * @param math the {@link ASTNode} representing the expression to be set to the {@link Priority}
   */
  public Priority createPriority(ASTNode math) {
    Priority p = createPriority();
    p.setMath(math);
    return p;
  }

  /**
   * Creates a new {@link Trigger} instance and sets it to this {@link Event}.
   * 
   * @return the new {@link Trigger} instance.
   */
  public Trigger createTrigger() {
    Trigger t = new Trigger(getLevel(), getVersion());
    setTrigger(t);
    return t;
  }

  /**
   * Creates a new {@link Trigger} instance and sets it to this {@link Event}.
   * 
   * @param initialValue
   * @param persistent
   * @return the new {@link Trigger} instance.
   */
  public Trigger createTrigger(boolean initialValue, boolean persistent) {
    Trigger t = createTrigger();
    t.setInitialValue(initialValue);
    t.setPersistent(persistent);
    return t;
  }

  /**
   * Creates a new {@link Trigger} instance and sets it to this {@link Event}.
   * 
   * @param initialValue
   * @param persistent
   * @param math the math expression for the new {@link Trigger}
   * @return the new {@link Trigger} instance.
   */
  public Trigger createTrigger(boolean initialValue, boolean persistent,
    ASTNode math) {
    Trigger t = createTrigger(initialValue, persistent);
    t.setMath(math);
    return t;
  }

  /**
   * Creates a new {@link Trigger} instance and sets it to this {@link Event}.
   * 
   * @param math math the math expression for the new {@link Trigger}
   * @return the new {@link Trigger} instance.
   */
  public Trigger createTrigger(ASTNode math) {
    Trigger t = createTrigger();
    t.setMath(math);
    return t;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Event e = (Event) object;
      equals &= e.getUseValuesFromTriggerTime() == getUseValuesFromTriggerTime();
    }
    return equals;
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
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetTrigger()) {
      if (pos == index) {
        return getTrigger();
      }
      pos++;
    }
    if (isSetPriority()) {
      if (pos == index) {
        return getPriority();
      }
      pos++;
    }
    if (isSetDelay()) {
      if (pos == index) {
        return getDelay();
      }
      pos++;
    }
    // since L3V2, empty ListOf are valid to be written to XML
    if (listOfEventAssignments != null) {
      if (pos == index) {
        return getListOfEventAssignments();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int children = super.getChildCount();
    if (isSetTrigger()) {
      children++;
    }
    if (isSetPriority()) {
      children++;
    }
    if (isSetDelay()) {
      children++;
    }
    // since L3V2, empty ListOf are valid to be written to XML
    if (listOfEventAssignments != null) {
      children++;
    }

    return children;
  }

  /**
   * 
   * @return the Delay instance of this Event.
   */
  public Delay getDelay() {
    return delay;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnitDefinition()
   */
  @Override
  @Deprecated
  public UnitDefinition getDerivedUnitDefinition() {
    return super.getDerivedUnitDefinition();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnits()
   */
  @Override
  @Deprecated
  public String getDerivedUnits() {
    return super.getDerivedUnits();
  }

  /**
   * Gets an element from the listOfEventAssignments at the given index.
   *
   * @param n the index of the {@link EventAssignment} element to get.
   * @return an element from the listOfEventAssignments at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public EventAssignment getEventAssignment(int n) {
    if (isSetListOfEventAssignments()) {
      return listOfEventAssignments.get(n);
    }
    throw new IndexOutOfBoundsException(Integer.toString(n));
  }


  /**
   * Gets an element from the listOfEventAssignments, with the given id.
   *
   * @param eventAssignmentId the id of the {@link EventAssignment} element to get.
   * @return an element from the listOfEventAssignments with the given id or null.
   */
  public EventAssignment getEventAssignment(String eventAssignmentId) {
    if (isSetListOfEventAssignments()) {
      return getListOfEventAssignments().get(eventAssignmentId);
    }
    return null;
  }


  /**
   * Returns the listOfEventAssignments. Creates it if it is not already existing.
   *
   * @return the list of eventAssignments of this Event.
   */
  public ListOf<EventAssignment> getListOfEventAssignments() {
    if (listOfEventAssignments == null) {
      listOfEventAssignments = ListOf.newInstance(this, EventAssignment.class);
      registerChild(listOfEventAssignments);
    }
    return listOfEventAssignments;
  }


  /**
   * Returns the number of {@link EventAssignment}s in this {@link Event}.
   * 
   * @return the number of {@link EventAssignment}s in this {@link Event}.
   * @libsbml.deprecated same as {@link #getEventAssignmentCount()}
   */
  public int getNumEventAssignments() {
    return getEventAssignmentCount();
  }

  /**
   * Returns the number of {@link EventAssignment}s in this {@link Event}.
   * 
   * @return the number of {@link EventAssignment}s in this {@link Event}.
   */
  public int getEventAssignmentCount() {
    return listOfEventAssignments == null ? 0 : listOfEventAssignments.size();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Event> getParent() {
    return (ListOf<Event>) super.getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getPredefinedUnitID()
   */
  @Override
  public String getPredefinedUnitID() {
    return null;
  }

  /**
   * @return the priority
   */
  public Priority getPriority() {
    return priority;
  }

  /**
   * 
   * @return The timeUnitsID of this Event. Return an empty String if it is
   *         not set.
   * @deprecated
   */
  @Deprecated
  public String getTimeUnits() {
    return getUnits();
  }

  /**
   * @return the timeUnitsID
   * @deprecated
   */
  @Deprecated
  public String getTimeUnitsID() {
    return getUnits();
  }

  /**
   * Returns the {@link UnitDefinition} instance of the model which matches
   * the timesUnitsID of this {@link Event}.
   * 
   * @return the {@link UnitDefinition} instance of the model which matches
   *         the timesUnitsID of this {@link Event}. Returns {@code null} if there is
   *         no {@link UnitDefinition} id which matches the value of the
   *         {@code timeUnits} attribute of this {@link Event}.
   * @deprecated
   */
  @Deprecated
  public UnitDefinition getTimeUnitsInstance() {
    return getUnitsInstance();
  }

  /**
   * 
   * @return the Trigger instance of this Event.
   */
  public Trigger getTrigger() {
    return trigger;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getUnits()
   */
  @Override
  @Deprecated
  public String getUnits() {
    return super.getUnits();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getUnitsInstance()
   */
  @Override
  @Deprecated
  public UnitDefinition getUnitsInstance() {
    return super.getUnitsInstance();
  }

  /**
   * 
   * @return the useValuesFromTriggerTime instance of this Event. it is null
   *         if it has not been set.
   */
  public boolean getUseValuesFromTriggerTime() {
    return isUseValuesFromTriggerTime();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 911;
    int hashCode = super.hashCode();
    if (isSetUseValuesFromTriggerTime()) {
      hashCode += prime * useValuesFromTriggerTime.hashCode();
    }
    return hashCode;
  }

  /**
   * Initializes the default values using the current Level/Version configuration.
   */
  public void initDefaults() {
    initDefaults(getLevel(), getVersion());
  }

  /**
   * Initializes the default values of this {@link Event}.
   * @param level
   * @param version
   */
  public void initDefaults(int level, int version) {
    trigger = null;
    delay = null;
    priority = null;
    listOfEventAssignments = null;
    if ((0 < level) && (level < 3)) {
      useValuesFromTriggerTime = new Boolean(true);
    } else {
      useValuesFromTriggerTime = null;
    }
    if ((0 < level) && (level < 2)) {
      throw new IllegalAccessError("Cannot create an Event with Level < 2.");
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * Returns {@code true}, if listOfEventAssignments is not null.
   *
   * @return {@code true} if the listOfEventAssignments is not {@code null}.
   */
  public boolean isListOfEventAssignmentEmpty() {
    return listOfEventAssignments != null && listOfEventAssignments.isEmpty();
  }

  /**
   * 
   * @return {@code true} if the delay of this Event is not {@code null}.
   */
  public boolean isSetDelay() {
    return delay != null;
  }

  /**
   * Returns {@code true}, if listOfEventAssignments contains at least one element.
   *
   * @return {@code true} if the listOfEventAssignments of this Event is not
   *         {@code null} and not empty;
   */
  public boolean isSetListOfEventAssignments() {
    return (listOfEventAssignments != null)
        && (listOfEventAssignments.size() > 0);
  }


  /**
   * 
   * @return
   */
  public boolean isSetPriority() {
    return priority != null;
  }

  /**
   * 
   * @return {@code true} if the timeUnitsID of this {@link Event} is not {@code null}.
   * @deprecated
   */
  @Deprecated
  public boolean isSetTimeUnits() {
    return isSetUnits();
  }

  /**
   * 
   * @return {@code true} if the UnitDefinition which has the timeUnitsID of this
   *         Event as id is not {@code null}.
   * @deprecated
   */
  @Deprecated
  public boolean isSetTimeUnitsInstance() {
    return isSetUnitsInstance();
  }

  /**
   * 
   * @return {@code true} if the trigger of this Event is not {@code null}.
   */
  public boolean isSetTrigger() {
    return trigger != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#isSetUnits()
   */
  @Override
  @Deprecated
  public boolean isSetUnits() {
    return super.isSetUnits();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#isSetUnitsInstance()
   */
  @Override
  @Deprecated
  public boolean isSetUnitsInstance() {
    return super.isSetUnitsInstance();
  }

  /**
   * 
   * @return {@code true} is the useValuesFromTriggerTime of this Event is not {@code null}.
   */
  public boolean isSetUseValuesFromTriggerTime() {
    return isSetUseValuesFromTriggerTime;
  }

  /**
   * 
   * @return the boolean value of the useValuesFromTriggerTime of this {@link Event}
   *         if it has been set, {@code false} otherwise.
   */
  public boolean isUseValuesFromTriggerTime() {
    return isSetUseValuesFromTriggerTime() ? useValuesFromTriggerTime
      : true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      if (attributeName
          .equals(TreeNodeChangeEvent.useValuesFromTriggerTime)) {
        setUseValuesFromTriggerTime(StringTools.parseSBMLBoolean(value));
        return true;
      } else if (attributeName.equals(TreeNodeChangeEvent.timeUnits)) {
        setTimeUnits(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /**
   * Removes an element from the listOfEventAssignments at the given index.
   *
   * @param i the index where to remove the {@link EventAssignment}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public EventAssignment removeEventAssignment(int i) {
    if ((i >= getNumEventAssignments()) || (i < 0)) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return listOfEventAssignments.remove(i);
  }

  /**
   * Removes an element from the listOfEventAssignments.
   *
   * @param id the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or null.
   */
  public EventAssignment removeEventAssignment(String id) {
    EventAssignment deletedEventAssignment = null;
    int index = 0;

    for (EventAssignment eventAssignment : getListOfEventAssignments()) {
      if (eventAssignment.getVariable().equals(id)) {
        deletedEventAssignment = eventAssignment;
        break;
      }
      index++;
    }

    if (deletedEventAssignment != null) {
      listOfEventAssignments.remove(index);
    }

    return deletedEventAssignment;
    // return listOfEventAssignments.remove(id);
  }


  /**
   * Removes an element from the listOfEventAssignments.
   *
   * @param eventAssignment the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeEventAssignment(EventAssignment eventAssignment) {
    if (isSetListOfEventAssignments()) {
      return getListOfEventAssignments().remove(eventAssignment);
    }
    return false;
  }


  /**
   * Sets the delay of this Event to 'delay'. It automatically sets the Delay
   * parentSBML object to this Event instance.
   * 
   * @param delay
   */
  public void setDelay(Delay delay) {
    unsetDelay();
    this.delay = delay;
    registerChild(this.delay);
  }

  /**
   * Sets the given {@code ListOf<EventAssignment>}. If listOfEventAssignments
   * was defined before and contains some elements, they are all unset.
   * 
   * @param listOfEventAssignments
   */
  public void setListOfEventAssignments(
    ListOf<EventAssignment> listOfEventAssignments) {
    unsetListOfEventAssignments();
    this.listOfEventAssignments = listOfEventAssignments;
    if (this.listOfEventAssignments != null) {
      this.listOfEventAssignments.setSBaseListType(ListOf.Type.listOfEventAssignments);
      registerChild(this.listOfEventAssignments);
    }
  }


  /**
   * @param priority
   *            the priority to set
   * @throws PropertyNotAvailableException if Level &lt; 3.
   */
  public void setPriority(Priority priority) {
    if (getLevel() < 3) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.priority,
        this);
    }
    unsetPriority();
    this.priority = priority;
    registerChild(this.priority);
  }

  /**
   * Sets the timeUnitsID of this {@link Event} to 'timeUnits'.
   * 
   * @param timeUnits
   * @deprecated This is only applicable for SBML Level 2, Versions 1 and 2.
   */
  @Deprecated
  public void setTimeUnits(String timeUnits) {
    setUnits(timeUnits);
  }

  /**
   * Sets the timeUnitsID of this {@link Event} to the id of the {@link UnitDefinition}
   * 'timeUnits'.
   * 
   * @param timeUnits
   */
  @Deprecated
  public void setTimeUnits(UnitDefinition timeUnits) {
    setTimeUnits(timeUnits != null ? timeUnits.getId() : null);
  }

  /**
   * @param timeUnitsID
   *            the timeUnitsID to set
   * @deprecated
   */
  @Deprecated
  public void setTimeUnitsID(String timeUnitsID) {
    setTimeUnits(timeUnitsID);
  }

  /**
   * Sets the trigger of this Event to 'trigger'. It automatically sets the
   * {@link Trigger} parentSBML object to this Event instance.
   * 
   * @param trigger
   */
  public void setTrigger(Trigger trigger) {
    unsetTrigger();
    this.trigger = trigger;
    registerChild(this.trigger);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
   */
  @Override
  @Deprecated
  public void setUnits(Kind timeUnitKind) {
    if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.timeUnits,
        this);
    }
    super.setUnits(timeUnitKind);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(java.lang.String)
   */
  @Override
  @Deprecated
  public void setUnits(String timeUnits) {
    if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.timeUnits,
        this);
    }
    super.setUnits(timeUnits);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
   */
  @Override
  @Deprecated
  public void setUnits(Unit timeUnit) {
    if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.timeUnits,
        this);
    }
    super.setUnits(timeUnit);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
   */
  @Override
  @Deprecated
  public void setUnits(UnitDefinition timeUnits) {
    if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.timeUnits,
        this);
    }
    super.setUnits(timeUnits);
  }

  /**
   * Sets the useValuesFromTriggerTime of this {@link Event} to
   * 'useValuesFromTriggerTime'.
   * 
   * @param useValuesFromTriggerTime
   * @throws PropertyNotAvailableException if the Level/Version combination is lower than 2.4.
   */
  public void setUseValuesFromTriggerTime(boolean useValuesFromTriggerTime) {
    if (getLevelAndVersion().compareTo(Integer.valueOf(2),
      Integer.valueOf(4)) < 0) {
      throw new PropertyNotAvailableException(
        TreeNodeChangeEvent.useValuesFromTriggerTime, this);
    }
    Boolean oldUsesValuesFromTriggerTime = this.useValuesFromTriggerTime;
    this.useValuesFromTriggerTime = useValuesFromTriggerTime;
    isSetUseValuesFromTriggerTime = true;
    firePropertyChange(TreeNodeChangeEvent.useValuesFromTriggerTime,
      oldUsesValuesFromTriggerTime, useValuesFromTriggerTime);
  }

  /**
   * Sets the delay of this {@link Event} to {@code null}.
   * @return
   */
  public boolean unsetDelay() {
    if (delay != null) {
      Delay oldDelay = delay;
      delay = null;
      oldDelay.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Removes the {@link #listOfEventAssignments} from this {@link Event} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfEventAssignments() {
    if (listOfEventAssignments != null) {
      ListOf<EventAssignment> oldListOfEventAssignments = listOfEventAssignments;
      listOfEventAssignments = null;
      oldListOfEventAssignments.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Sets the {@link Priority} of this {@link Event} to null and notifies
   * {@link TreeNodeChangeListener}s.
   * @return
   */
  public boolean unsetPriority() {
    if (priority != null) {
      Priority oldPriority = priority;
      priority = null;
      oldPriority.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Sets the timeUnitsID of this {@link Event} to {@code null}.
   */
  public void unsetTimeUnits() {
    setTimeUnitsID(null);
  }

  /**
   * Sets the trigger of this {@link Event} to null and notifies
   * {@link TreeNodeChangeListener}s.
   * @return
   */
  public boolean unsetTrigger() {
    if (trigger != null) {
      Trigger oldTrigger = trigger;
      trigger = null;
      oldTrigger.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Sets the useValuesFromTriggerTime of this Event to {@code null}.
   */
  public void unsetUseValuesFromTriggerTime() {
    if (useValuesFromTriggerTime != null) {
      Boolean oldUseValuesFromTriggerTime = useValuesFromTriggerTime;
      useValuesFromTriggerTime = null;
      isSetUseValuesFromTriggerTime = false;
      firePropertyChange(TreeNodeChangeEvent.useValuesFromTriggerTime,
        oldUseValuesFromTriggerTime, null);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetUseValuesFromTriggerTime()
        && (((getLevel() == 2) && (getVersion() == 4)) || (getLevel() >= 3))) {
      attributes.put(TreeNodeChangeEvent.useValuesFromTriggerTime, Boolean
        .toString(getUseValuesFromTriggerTime()));
    }

    if (isSetTimeUnits()
        && ((getLevel() == 1) || ((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2))))) {
      attributes.put(TreeNodeChangeEvent.timeUnits, getTimeUnits());
    }

    return attributes;
  }

}
