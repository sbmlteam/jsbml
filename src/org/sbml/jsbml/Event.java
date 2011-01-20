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

import java.util.Map;

import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.StringTools;

/**
 * Represents the event XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 */
public class Event extends AbstractNamedSBaseWithUnit {
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 5282750820355199194L;
	/**
	 * Represents the delay sub-element of an event element.
	 */
	private Delay delay;
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
	 * timeUnitsID are null.
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
		} else {
			trigger = null;
		}
		if (isSetUseValuesFromTriggerTime()) {
			this.useValuesFromTriggerTime = new Boolean(event
					.isUseValuesFromTriggerTime());
		}
		if (event.isSetDelay()) {
			setDelay(event.getDelay().clone());
		} else {
			this.delay = null;
		}
		if (isSetListOfEventAssignments()) {
			setListOfEventAssignments((ListOf<EventAssignment>) event
					.getListOfEventAssignments().clone());
		} else {
			this.listOfEventAssignments = null;
		}
	}

	/**
	 * Creates an Event from a level and version. By default, if the level is
	 * set and is superior or equal to 3, the trigger, delay,
	 * listOfEventAssignemnts and timeUnitsID are null.
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
	 * listOfEventAssignemnts and timeUnitsID are null.
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
	 * delay, listOfEventAssignemnts and timeUnitsID are null.
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
	 * Adds an EventAssignment instance to the list of EventAssignments of this
	 * Event.
	 * 
	 * @param eventass
	 * @return <code>true</code> if the {@link #listOfEventAssignments} was
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	@Override
	public Event clone() {
		return new Event(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#containsUndeclaredUnits()
	 */
	@Override
	@Deprecated
	public boolean containsUndeclaredUnits() {
		return super.containsUndeclaredUnits();
	}

	/**
	 * 
	 * @return
	 */
	public Delay createDelay() {
		Delay d = new Delay(getLevel(), getVersion());
		d.addAllChangeListeners(getSetOfSBaseChangedListeners());
		setDelay(d);
		return d;
	}

	/**
	 * 
	 * @param math
	 * @return
	 */
	public Delay createDelay(ASTNode math) {
		Delay d = createDelay();
		d.setMath(math);
		return d;
	}

	/**
	 * 
	 * @return the new EventAssignment instance.
	 */
	public EventAssignment createEventAssignment() {
		EventAssignment ea = new EventAssignment(getLevel(), getVersion());
		addEventAssignment(ea);
		return ea;
	}

	/**
	 * 
	 * @param variable
	 * @param math
	 * @return
	 */
	public EventAssignment createEventAssignment(String variable, ASTNode math) {
		EventAssignment ea = createEventAssignment();
		ea.setVariable(variable);
		ea.setMath(math);
		return ea;
	}

	/**
	 * 
	 * @param variable
	 * @param math
	 * @return
	 */
	public EventAssignment createEventAssignment(Variable variable, ASTNode math) {
		return createEventAssignment(variable.getId(), math);
	}

	/**
	 * Creates a new, empty {@link Priority}, adds it to this {@link Event} and
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
	 * 
	 * @param math
	 * @return
	 */
	public Priority createPriority(ASTNode math) {
		Priority p = createPriority();
		p.setMath(math);
		return p;
	}

	/**
	 * 
	 * @return
	 */
	public Trigger createTrigger() {
		Trigger t = new Trigger(getLevel(), getVersion());
		setTrigger(t);
		return t;
	}

	/**
	 * 
	 * @param initialValue
	 * @param persistent
	 * @return
	 */
	public Trigger createTrigger(boolean initialValue, boolean persistent) {
		Trigger t = createTrigger();
		t.setInitialValue(initialValue);
		t.setPersistent(persistent);
		return t;
	}

	/**
	 * 
	 * @param initialValue
	 * @param persistent
	 * @param math
	 * @return
	 */
	public Trigger createTrigger(boolean initialValue, boolean persistent,
			ASTNode math) {
		Trigger t = createTrigger(initialValue, persistent);
		t.setMath(math);
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Event) {
			Event e = (Event) o;
			boolean equal = super.equals(o);
			equal &= e.getUseValuesFromTriggerTime() == getUseValuesFromTriggerTime();
			equal &= e.isSetTrigger() == isSetTrigger();
			equal &= e.isSetPriority() == isSetPriority();
			equal &= e.isSetTimeUnits() == isSetTimeUnits();
			equal &= e.isSetDelay() == isSetDelay();
			equal &= e.isSetListOfEventAssignments() == isSetListOfEventAssignments();
			if (equal && e.isSetDelay()) {
				equal &= e.getDelay().equals(getDelay());
			}
			if (equal && e.isSetTrigger()) {
				equal &= e.getTrigger().equals(getTrigger());
			}
			if (equal && e.isSetPriority()) {
				equal &= e.getPriority().equals(getPriority());
			}
			if (equal && e.isSetTimeUnits()) {
				equal &= e.getTimeUnits().equals(getTimeUnits());
			}
			if (equal && isSetListOfEventAssignments()) {
				equal &= e.getListOfEventAssignments().equals(
						getListOfEventAssignments());
			}
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public SBase getChildAt(int index) {
		int children = getChildCount();
		if (index >= children) {
			throw new IndexOutOfBoundsException(index + " >= " + children);
		}
		int pos = 0;
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
		if (isSetListOfEventAssignments()) {
			if (pos == index) {
				return getListOfEventAssignments();
			}
			pos++;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int children = 0;
		if (isSetTrigger()) {
			children++;
		}
		if (isSetPriority()) {
			children++;
		}
		if (isSetDelay()) {
			children++;
		}
		if (isSetListOfEventAssignments()) {
			children++;
		}
		return children;
	}

	/**
	 * 
	 * @return the Delay instance of this Event.
	 */
	public Delay getDelay() {
		return this.delay;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnitDefinition()
	 */
	@Override
	@Deprecated
	public UnitDefinition getDerivedUnitDefinition() {
		return super.getDerivedUnitDefinition();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnits()
	 */
	@Override
	@Deprecated
	public String getDerivedUnits() {
		return super.getDerivedUnits();
	}

	/**
	 * 
	 * @param n
	 * @return the nth EventAssignment instance of the list of EventAssignments
	 *         for this Event.
	 */
	public EventAssignment getEventAssignment(int n) {
		if (isSetListOfEventAssignments()) {
			return listOfEventAssignments.get(n);
		}
		throw new IndexOutOfBoundsException(Integer.toString(n));
	}

	/**
	 * 
	 * @return the list of eventAssignments of this Event.
	 */
	public ListOf<EventAssignment> getListOfEventAssignments() {
		if (listOfEventAssignments == null) {
			listOfEventAssignments = ListOf.newInstance(this, EventAssignment.class);
		}
		return listOfEventAssignments;
	}

	/**
	 * 
	 * @return the number of EventAssignments in the list of EventAssignements
	 *         of this Event.
	 */
	public int getNumEventAssignments() {
		return listOfEventAssignments == null ? 0 : listOfEventAssignments.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ListOf<Event> getParent() {
		return (ListOf<Event>) super.getParent();
	}

	/**
	 * @return the priority
	 */
	public Priority getPriority() {
		return this.priority;
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
	 * 
	 * @return the {@link UnitDefinition} instance of the model which matches
	 *         the timesUnitsID of this {@link Event}. Returns null if there is
	 *         no {@link UnitDefinition} id which matches the
	 *         {@link #timeUnitsID} of this {@link Event}.
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getUnits()
	 */
	@Override
	@Deprecated
	public String getUnits() {
		return super.getUnits();
	}

	/*
	 * (non-Javadoc)
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

	/**
	 * Initializes the default values of this {@link Event}.
	 */
	public void initDefaults() {
		this.trigger = null;
		this.delay = null;
		this.priority = null;
		this.listOfEventAssignments = null;
		if (isSetLevel() && (getLevel() < 3)) {
			useValuesFromTriggerTime = new Boolean(true);
		} else {
			useValuesFromTriggerTime = null;
		}
		if (isSetLevel() && (getLevel() < 2)) {
			throw new IllegalAccessError("Cannot create an Event with Level < 2.");
		}
	}

	/**
	 * 
	 * @return true if the delay of this Event is not null.
	 */
	public boolean isSetDelay() {
		return delay != null;
	}

	/**
	 * 
	 * @return true if the listOfEventAssignments of this Event is not null and
	 *         not empty;
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
		return this.priority != null;
	}

	/**
	 * 
	 * @return true if the timeUnitsID of this {@link Event} is not null.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetTimeUnits() {
		return isSetUnits();
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the timeUnitsID of this
	 *         Event as id is not null.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetTimeUnitsInstance() {
		return isSetUnitsInstance();
	}

	/**
	 * 
	 * @return true if the trigger of this Event is not null.
	 */
	public boolean isSetTrigger() {
		return this.trigger != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#isSetUnits()
	 */
	@Override
	@Deprecated
	public boolean isSetUnits() {
		return super.isSetUnits();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#isSetUnitsInstance()
	 */
	@Override
	@Deprecated
	public boolean isSetUnitsInstance() {
		return super.isSetUnitsInstance();
	}

	/**
	 * 
	 * @return true is the useValuesFromTriggerTime of this Event is not null.
	 */
	public boolean isSetUseValuesFromTriggerTime() {
		return this.useValuesFromTriggerTime != null;
	}

	/**
	 * 
	 * @return the boolean value of the useValuesFromTriggerTime of this {@link Event}
	 *         if it has been set, false otherwise.
	 */
	public boolean isUseValuesFromTriggerTime() {
		return isSetUseValuesFromTriggerTime() ? useValuesFromTriggerTime
				: false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			if (attributeName
					.equals(SBaseChangedEvent.useValuesFromTriggerTime)) {
				setUseValuesFromTriggerTime(StringTools.parseSBMLBoolean(value));
				return true;
			} else if (attributeName.equals(SBaseChangedEvent.timeUnits)) {
				setTimeUnits(value);
				return true;
			}
		}
		return isAttributeRead;
	}
	
	/**
	 * 
	 * @param i
	 * @return the removed ith EventAssignment instance.
	 */
	public EventAssignment removeEventAssignment(int i) {
		if ((i >= getNumEventAssignments()) || (i < 0)) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		return listOfEventAssignments.remove(i);
	}
	
	/**
	 * 
	 * @param id
	 * @return the removed EventAssignment instance which has 'id' as id.
	 */
	public EventAssignment removeEventAssignment(String id) {
		EventAssignment deletedEventAssignment = null;
		int index = 0;

		for (EventAssignment reactant : getListOfEventAssignments()) {
			if (reactant.getVariable().equals(id)) {
				deletedEventAssignment = reactant;
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
	 * Sets the delay of this Event to 'delay'. It automatically sets the Delay
	 * parentSBML object to this Event instance.
	 * 
	 * @param delay
	 */
	public void setDelay(Delay delay) {
		unsetDelay();
		this.delay = delay;
		setThisAsParentSBMLObject(this.delay);
	}
	
	/**
	 * Sets the {@link #listOfEventAssignments} of this {@link Event} to
	 * 'listOfEventAssignments'. It automatically sets the SBMLParent object of
	 * the listOfEventAssignments and all the {@link EventAssignment}s in this list to
	 * this {@link Event} instance.
	 * 
	 * @param listOfEventAssignments
	 */
	public void setListOfEventAssignments(
			ListOf<EventAssignment> listOfEventAssignments) {
		unsetListOfEventAssignments();
		this.listOfEventAssignments = listOfEventAssignments;
		if ((this.listOfEventAssignments != null) && (this.listOfEventAssignments.getSBaseListType() != ListOf.Type.listOfEventAssignments)) {
			this.listOfEventAssignments.setSBaseListType(ListOf.Type.listOfEventAssignments);
		}
		setThisAsParentSBMLObject(this.listOfEventAssignments);
	}
	
	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Priority priority) {
		if (getLevel() < 3) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.priority,
					this);
		}
		unsetPriority();
		this.priority = priority;
		setThisAsParentSBMLObject(this.priority);
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
		setThisAsParentSBMLObject(this.trigger);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
	 */
	@Override
	@Deprecated
	public void setUnits(Kind timeUnitKind) {
		if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.timeUnits,
					this);
		}
		super.setUnits(timeUnitKind);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(java.lang.String)
	 */
	@Override
	@Deprecated
	public void setUnits(String timeUnits) {
		if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.timeUnits,
					this);
		}
		super.setUnits(timeUnits);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
	 */
	@Override
	@Deprecated
	public void setUnits(Unit timeUnit) {
		if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.timeUnits,
					this);
		}
		super.setUnits(timeUnit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
	 */
	@Override
	@Deprecated
	public void setUnits(UnitDefinition timeUnits) {
		if (!((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2)))) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.timeUnits,
					this);
		}
		super.setUnits(timeUnits);
	}

	/**
	 * Sets the useValuesFromTriggerTime of this {@link Event} to
	 * 'useValuesFromTriggerTime'.
	 * 
	 * @param useValuesFromTriggerTime
	 */
	public void setUseValuesFromTriggerTime(boolean useValuesFromTriggerTime) {
		if (getLevelAndVersion().compareTo(Integer.valueOf(2),
				Integer.valueOf(4)) < 0) {
			throw new PropertyNotAvailableError(
					SBaseChangedEvent.useValuesFromTriggerTime, this);
		}
		Boolean oldUsesValuesFromTriggerTime = Boolean
				.valueOf(this.useValuesFromTriggerTime);
		this.useValuesFromTriggerTime = useValuesFromTriggerTime;
		firePropertyChange(SBaseChangedEvent.useValuesFromTriggerTime,
				oldUsesValuesFromTriggerTime, useValuesFromTriggerTime);
	}

	/**
	 * Sets the delay of this {@link Event} to null.
	 */
	public boolean unsetDelay() {
		if (this.delay != null) {
			Delay oldDelay = this.delay;
			this.delay = null;
			oldDelay.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Removes the {@link #listOfEventAssignments} from this {@link Model} and
	 * notifies all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfEventAssignments() {
		if (this.listOfEventAssignments != null) {
			ListOf<EventAssignment> oldListOfEventAssignments = this.listOfEventAssignments;
			this.listOfEventAssignments = null;
			oldListOfEventAssignments.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Sets the {@link Priority} of this {@link Event} to null and notifies
	 * {@link SBaseChangedListener}s.
	 */
	public boolean unsetPriority() {
		if (this.priority != null) {
			Priority oldPriority = this.priority;
			this.priority = null;
			oldPriority.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Sets the timeUnitsID of this {@link Event} to null.
	 */
	public void unsetTimeUnits() {
		setTimeUnitsID(null);
	}

	/**
	 * Sets the trigger of this {@link Event} to null and notifies
	 * {@link SBaseChangedListener}s.
	 */
	public boolean unsetTrigger() {
		if (this.trigger != null) {
			Trigger oldTrigger = this.trigger;
			this.trigger = null;
			oldTrigger.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Sets the useValuesFromTriggerTime of this Event to null.
	 */
	public void unsetUseValuesFromTriggerTime() {
		Boolean oldUseValuesFromTriggerTime = useValuesFromTriggerTime;
		this.useValuesFromTriggerTime = null;
		firePropertyChange(SBaseChangedEvent.useValuesFromTriggerTime,
				oldUseValuesFromTriggerTime, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetUseValuesFromTriggerTime()
				&& (((getLevel() == 2) && (getVersion() == 4)) || (getLevel() >= 3))) {
			attributes.put(SBaseChangedEvent.useValuesFromTriggerTime, Boolean
					.toString(getUseValuesFromTriggerTime()));
		}

		if (isSetTimeUnits()
				&& ((getLevel() == 1) || ((getLevel() == 2) && ((getVersion() == 1) || (getVersion() == 2))))) {
			attributes.put(SBaseChangedEvent.timeUnits, getTimeUnits());
		}

		return attributes;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getPredefinedUnitID()
	 */
	public String getPredefinedUnitID() {
		return null;
	}
}
