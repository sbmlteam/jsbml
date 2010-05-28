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

import java.util.HashMap;

import org.sbml.jsbml.ListOf.Type;

/**
 * Represents the event XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * 
 * @composed 1 trigger 1 Trigger
 * @composed 0..1 delay 1 Delay
 * @composed 1..* ListOf 1 EventAssignment
 */
public class Event extends AbstractNamedSBase {
	/**
	 * Represents the 'useValuesFromTriggerTime' XML attribute of an event
	 * element.
	 */
	private Boolean useValuesFromTriggerTime;
	/**
	 * Represents the trigger subelement of an event element.
	 */
	private Trigger trigger;
	/**
	 * Represents the listOfEventAssignments subelement of an event element.
	 */
	private ListOf<EventAssignment> listOfEventAssignments;
	/**
	 * Represents the delay subelement of an event element.
	 */
	private Delay delay;

	/**
	 * Represents the 'timeUnits' XML attribute of an event element.
	 */
	@Deprecated
	private String timeUnitsID;

	/**
	 * Creates an Event instance. By default, if the level is set and is
	 * superior or equal to 3, the trigger, delay, listOfEventAssignemnts and
	 * timeUnitsID are null.
	 */
	public Event() {
		super();
		this.trigger = null;
		this.delay = null;
		this.listOfEventAssignments = null;
		this.timeUnitsID = null;

		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
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
		if (event.isSetTimeUnits()) {
			this.timeUnitsID = new String(event.getTimeUnits());
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
		this.trigger = null;
		this.delay = null;
		this.listOfEventAssignments = null;
		this.timeUnitsID = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
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
		this.trigger = null;
		this.delay = null;
		this.listOfEventAssignments = null;
		this.timeUnitsID = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
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
		this.trigger = null;
		this.delay = null;
		this.listOfEventAssignments = null;
		this.timeUnitsID = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}

	/**
	 * Adds an EventAssignment instance to the list of EventAssignments of this
	 * Event.
	 * 
	 * @param eventass
	 */
	public void addEventAssignment(EventAssignment eventass) {
		if (!isSetListOfEventAssignments()) {
			initListOfEventAssignments();
		}
		setThisAsParentSBMLObject(eventass);
		listOfEventAssignments.add(eventass);
	}

	/**
	 * Remove all the EventAssignments of the listOfEventAssignments of this
	 * Event.
	 */
	public void clearListOfEventAssignments() {
		this.listOfEventAssignments.clear();
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

	/**
	 * 
	 * @return
	 */
	public Delay createDelay() {
		Delay d = new Delay(getLevel(), getVersion());
		d.addAllChangeListeners(getSetOfSBaseChangeListeners());
		setDelay(d);
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
	 * @return
	 */
	public Trigger createTrigger() {
		Trigger t = new Trigger(getLevel(), getVersion());
		setTrigger(t);
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
			System.out.println("useValuseFromTriggerTime : " + equal);
			equal &= e.isSetListOfEventAssignments() == isSetListOfEventAssignments();
			if (equal && isSetListOfEventAssignments()) {
				equal &= e.getListOfEventAssignments().equals(
						getListOfEventAssignments());
			}
			if ((e.isSetDelay() && !isSetDelay())
					|| (!e.isSetDelay() && isSetDelay())) {
				return false;
			} else if (e.isSetDelay() && isSetDelay()) {
				equal &= e.getDelay().equals(getDelay());
			}
			System.out.println("Delay : " + equal);

			if ((!e.isSetTrigger() && isSetTrigger())
					|| (e.isSetTrigger() && !isSetTrigger())) {
				return false;
			} else if (e.isSetTrigger() && isSetTrigger()) {
				equal &= e.getTrigger().equals(getTrigger());
			}
			System.out.println("Trigger : " + equal);

			if ((!e.isSetTimeUnits() && isSetTimeUnits())
					|| (e.isSetTimeUnits() && !isSetTimeUnits())) {
				return false;
			} else if (e.isSetTimeUnits() && isSetTimeUnits()) {
				equal &= e.getTimeUnits().equals(getTimeUnits());
			}
			System.out.println("TimeUnist : " + equal);

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
		return delay;
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
		return null;
	}

	/**
	 * 
	 * @return the list of eventAssignments of this Event.
	 */
	public ListOf<EventAssignment> getListOfEventAssignments() {
		return listOfEventAssignments;
	}

	/**
	 * 
	 * @return the number of EventAssignments in the list of EventAssignements
	 *         of this Event.
	 */
	public int getNumEventAssignments() {
		if (isSetListOfEventAssignments()) {
			return listOfEventAssignments.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return The timeUnitsID of this Event. Return an empty String if it is
	 *         not set.
	 */
	@Deprecated
	public String getTimeUnits() {
		return isSetTimeUnits() ? this.timeUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance of the model which matches the
	 *         timesUnitsID of this Event. Return null if there is no
	 *         UnitDefinition id which matches the timeUnitsID of this Event.
	 */
	public UnitDefinition getTimeUnitsInstance() {
		Model m = getModel();
		return m != null ? m.getUnitDefinition(this.timeUnitsID) : null;
	}

	/**
	 * 
	 * @return the Trigger instance of this Event.
	 */
	public Trigger getTrigger() {
		return trigger;
	}

	/**
	 * 
	 * @return the useValuesFromTriggerTime instance of this Event. it is null
	 *         if it has not been set.
	 */
	public boolean getUseValuesFromTriggerTime() {
		return isSetUseValuesFromTriggerTime() ? useValuesFromTriggerTime
				: false;
	}

	/**
	 * Initializes the default values of this {@link Event}.
	 */
	public void initDefaults() {
		useValuesFromTriggerTime = new Boolean(true);
		setListOfEventAssignments(new ListOf<EventAssignment>(getLevel(),
				getVersion()));
		timeUnitsID = null;
		delay = null;
	}

	/**
	 * 
	 */
	private void initListOfEventAssignments() {
		this.listOfEventAssignments = new ListOf<EventAssignment>(getLevel(),
				getVersion());
		setThisAsParentSBMLObject(this.listOfEventAssignments);
		this.listOfEventAssignments
				.setSBaseListType(Type.listOfEventAssignments);
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
	 * @return true if the listOfEventAssignments of this Event is not null;
	 */
	public boolean isSetListOfEventAssignments() {
		return listOfEventAssignments != null;
	}

	/**
	 * 
	 * @return true if the timeUnitsID of this Event is not null.
	 */
	@Deprecated
	public boolean isSetTimeUnits() {
		return timeUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the timeUnitsID of this
	 *         Event as id is not null.
	 */
	@Deprecated
	public boolean isSetTimeUnitsInstance() {
		Model m = getModel();
		return m != null ? m.getUnitDefinition(this.timeUnitsID) != null
				: false;
	}

	/**
	 * 
	 * @return true if the trigger of this Event is not null.
	 */
	public boolean isSetTrigger() {
		return trigger != null;
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
	 * @return the boolean value of the useValuesFromTriggerTime of this Event
	 *         if it has been set, false otherwise.
	 */
	public boolean isUseValuesFromTriggerTime() {
		if (isSetUseValuesFromTriggerTime()) {
			return useValuesFromTriggerTime;
		}
		return false;
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
			if (attributeName.equals("useValuesFromTriggerTime")
					&& ((getLevel() == 2 && getVersion() == 4) || getLevel() >= 3)) {
				if (value.equals("true")) {
					this.setUseValuesFromTriggerTime(true);
					return true;
				} else if (value.equals("false")) {
					this.setUseValuesFromTriggerTime(false);
					return true;
				}
			} else if (attributeName.equals("timeUnits")
					&& (getLevel() == 1 || (getLevel() == 2 && (getVersion() == 1 || getVersion() == 2)))) {
				this.setTimeUnits(value);
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
		if (i >= listOfEventAssignments.size() || i < 0) {
			return null;
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

		for (EventAssignment reactant : listOfEventAssignments) {
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
		this.delay = delay;
		setThisAsParentSBMLObject(this.delay);
		this.delay.sbaseAdded();
		stateChanged();
	}

	/**
	 * Sets the listofEventAssignments of this Event to
	 * 'listOfEventAssignments'. It automatically sets the SBMLParent object of
	 * the listOfEventAssignments and all the EventAssignments in this list to
	 * this Event instance.
	 * 
	 * @param listOfEventAssignments
	 */
	public void setListOfEventAssignments(
			ListOf<EventAssignment> listOfEventAssignments) {
		this.listOfEventAssignments = listOfEventAssignments;
		this.listOfEventAssignments
				.setSBaseListType(ListOf.Type.listOfEventAssignments);
		setThisAsParentSBMLObject(this.listOfEventAssignments);
		stateChanged();
	}

	/**
	 * Sets the timeUnitsID of this Event to 'timeUnits'.
	 * 
	 * @param timeUnits
	 */
	@Deprecated
	public void setTimeUnits(String timeUnits) {
		if (timeUnits.equals("")) {
			unsetTimeUnits();
			return;
		}
		this.timeUnitsID = timeUnits;
		stateChanged();
	}

	/**
	 * Sets the timeUnitsID of this Event to the id of the UnitDefinition
	 * 'timeUnits'.
	 * 
	 * @param timeUnits
	 */
	@Deprecated
	public void setTimeUnits(UnitDefinition timeUnits) {
		this.timeUnitsID = timeUnits != null ? timeUnits.getId() : null;
		stateChanged();
	}

	/**
	 * Sets the trigger of this Event to 'trigger'. It automatically sets the
	 * Trigger parentSBML object to this Event instance.
	 * 
	 * @param trigger
	 */
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
		setThisAsParentSBMLObject(this.trigger);
		this.trigger.sbaseAdded();
	}

	/**
	 * Sets the useValuesFromTriggerTime of this Event to
	 * 'useValuesFromTriggerTime'.
	 * 
	 * @param useValuesFromTriggerTime
	 */
	public void setUseValuesFromTriggerTime(boolean useValuesFromTriggerTime) {
		this.useValuesFromTriggerTime = useValuesFromTriggerTime;
	}

	/**
	 * Sets the delay of this Event to null.
	 */
	public void unsetDelay() {
		this.delay = null;
	}

	/**
	 * Sets the listOfEventAssignments of this Event to null.
	 */
	public void unsetListOfEventAssignments() {
		this.listOfEventAssignments = null;
	}

	/**
	 * Sets the timeUnitsID of this Event to null.
	 */
	public void unsetTimeUnits() {
		this.timeUnitsID = null;
	}

	/**
	 * Sets the trigger of this Event to null.
	 */
	public void unsetTrigger() {
		this.trigger = null;
	}

	/**
	 * Sets the useValuesFromTriggerTime of this Event to null.
	 */
	public void unsetUseValuesFromTriggerTime() {
		this.useValuesFromTriggerTime = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetUseValuesFromTriggerTime()
				&& ((getLevel() == 2 && getVersion() == 4) || getLevel() >= 3)) {
			attributes.put("useValuesFromTriggerTime", Boolean
					.toString(getUseValuesFromTriggerTime()));
		}

		if (isSetTimeUnits()
				&& (getLevel() == 1 || (getLevel() == 2 && (getVersion() == 1 || getVersion() == 2)))) {
			attributes.put("timeUnits", getTimeUnits());
		}

		return attributes;
	}
}
