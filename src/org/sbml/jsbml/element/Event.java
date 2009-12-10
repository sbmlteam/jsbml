/*
 * $Id: Event.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Event.java $
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

import java.util.HashMap;

import org.sbml.jsbml.xml.SBaseListType;

/**
 * Represents the event XML element of a SBML file.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @author marine
 * 
 */
public class Event extends AbstractNamedSBase {
	/**
	 * Represents the 'useValuesFromTriggerTime' XML attribute of an event element.
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
	 * Creates an Event instance. By default, if the level is set and is superior or equal to 3, the trigger, delay, listOfEventAssignemnts and timeUnitsID are null.
	 */
	public Event() {
		super();
		this.trigger = null;
		this.delay = null;
		this.listOfEventAssignments = null;
		this.timeUnitsID = null;
		
		if (isSetLevel() && getLevel() < 3){
			initDefaults();
		}
	}
	
	/**
	 * Creates an Event instance from a given event.
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public Event(Event event) {
		super(event);
		if (event.isSetTrigger()){
			setTrigger(event.getTrigger().clone());
		}
		else {
			trigger = null;
		}
		if (isSetUseValuesFromTriggerTime()){
			this.useValuesFromTriggerTime = new Boolean(event.isUseValuesFromTriggerTime());
		}
		if (event.isSetDelay()) {
			setDelay(event.getDelay().clone());
		} else{
			this.delay = null;
		}
		if (isSetListOfEventAssignments()){
			setListOfEventAssignments((ListOf<EventAssignment>) event.getListOfEventAssignments().clone());	
		}
		else {
			this.listOfEventAssignments = null;
		}
		if (event.isSetTimeUnits()){
			this.timeUnitsID = new String(event.getTimeUnits());
		}
	}

	/**
	 * Creates an Event from a level and version. By default, if the level is set and is superior or equal to 3, the trigger, delay, listOfEventAssignemnts and timeUnitsID are null.
	 * @param level
	 * @param version
	 */
	public Event(int level, int version) {
		super(level, version);
		this.trigger = null;
		this.delay = null;
		this.listOfEventAssignments = null;
		this.timeUnitsID = null;
		if (isSetLevel() && getLevel() < 3){
			initDefaults();
		}
	}

	/**
	 * Creates an Event instance from an id, level and version. By default, if the level is set and is superior or equal to 3, the trigger, delay, listOfEventAssignemnts and timeUnitsID are null.
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
		if (isSetLevel() && getLevel() < 3){
			initDefaults();
		}
	}

	/**
	 * Creates an Event instance from an id, name, level and version. By default, if the level is set and is superior or equal to 3, the trigger, delay, listOfEventAssignemnts and timeUnitsID are null.
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
		if (isSetLevel() && getLevel() < 3){
			initDefaults();
		}
	}

	/**
	 * Adds an EventAssignment instance to the list of EventAssignments of this Event.
	 * @param eventass
	 */
	public void addEventAssignement(EventAssignment eventass) {
		if (!isSetListOfEventAssignments()){
			this.listOfEventAssignments = new ListOf<EventAssignment>();
			setThisAsParentSBMLObject(this.listOfEventAssignments);
		}
		setThisAsParentSBMLObject(eventass);
		listOfEventAssignments.add(eventass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	// @Override
	public Event clone() {
		return new Event(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof Event) {
			Event e = (Event) o;
			equal &= e.getUseValuesFromTriggerTime() == getUseValuesFromTriggerTime();
			equal &= e.isSetListOfEventAssignments() == isSetListOfEventAssignments();
			if (equal && isSetListOfEventAssignments()){
				equal &= e.getListOfEventAssignments().equals(getListOfEventAssignments());
			}
			if ((e.isSetDelay() && !isSetDelay())
					|| (!e.isSetDelay() && isSetDelay())){
				return false;
			}
			else if (e.isSetDelay() && isSetDelay()){
				equal &= e.getDelay().equals(getDelay());
			}
			if ((!e.isSetTrigger() && isSetTrigger())
					|| (e.isSetTrigger() && !isSetTrigger())){
				return false;
			}
			else if (e.isSetTrigger() && isSetTrigger()){
				equal &= e.getTrigger().equals(getTrigger());
			}
			if ((!e.isSetTimeUnits() && isSetTimeUnits())
					|| (e.isSetTimeUnits() && !isSetTimeUnits())){
				return false;
			}
			else if (e.isSetTimeUnits() && isSetTimeUnits()){
				equal &= e.getTimeUnits().equals(getTimeUnits());
			}
			return equal;
		}
		return false;
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
	 * @return the nth EventAssignment instance of the list of EventAssignments for this Event.
	 */
	public EventAssignment getEventAssignment(int n) {
		if (isSetListOfEventAssignments()){
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
	 * @return the number of EventAssignments in the list of EventAssignements of this Event.
	 */
	public int getNumEventAssignments() {
		if (isSetListOfEventAssignments()){
			return listOfEventAssignments.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return The timeUnitsID of this Event. Return an empty String if it is not set.
	 */
	@Deprecated
	public String getTimeUnits() {
		return isSetTimeUnits() ? this.timeUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance of the model which matches the timesUnitsID of this Event.
	 * Return null if there is no UnitDefinition id which matches the timeUnitsID of this Event.
	 */
	public UnitDefinition getTimeUnitsInstance() {
		if (getModel() != null){
			return getModel().getUnitDefinition(this.timeUnitsID);
		}
		return null;
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
	 * @return the useValuesFromTriggerTime instance of this Event. it is null if it has not been set.
	 */
	public Boolean getUseValuesFromTriggerTime() {
		return useValuesFromTriggerTime;
	}

	/**
	 * initialises the default values of this Event.
	 */
	public void initDefaults() {
		useValuesFromTriggerTime = new Boolean(true);
		setTrigger(new Trigger(getLevel(), getVersion()));
		setListOfEventAssignments(new ListOf<EventAssignment>(getLevel(), getVersion()));
		timeUnitsID = null;
		delay = null;
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
	 * @return true if the UnitDefinition which has the timeUnitsID of this Event as id is not null.
	 */
	@Deprecated
	public boolean isSetTimeUnitsInstance() {
		if (getModel() != null){
			return getModel().getUnitDefinition(this.timeUnitsID) != null;
		}
		return false;
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
	 * @return true if the trigger of this Event is not null.
	 */
	public boolean isSetTrigger() {
		return trigger != null;
	}

	/**
	 * 
	 * @return the boolean value of the useValuesFromTriggerTime of this Event if it has been set,
	 * false otherwise. 
	 */
	public boolean isUseValuesFromTriggerTime() {
		if (isSetUseValuesFromTriggerTime()){
			return useValuesFromTriggerTime;
		}
		return false;
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
	 * @return true is the useValuesFromTriggerTime of this Event is not null.
	 */
	public boolean isSetUseValuesFromTriggerTime(){
		return this.useValuesFromTriggerTime != null;
	}

	/**
	 * Sets the delay of this Event to 'delay'. It automatically sets the Delay parentSBML object to
	 * this Event instance.
	 * @param delay
	 */
	public void setDelay(Delay delay) {
		this.delay = delay;
		setThisAsParentSBMLObject(this.delay);
		this.delay.sbaseAdded();
		stateChanged();
	}

	/**
	 * Sets the listofEventAssignments of this Event to 'listOfEventAssignments'.
	 * It automatically sets the SBMLParent object of the listOfEventAssignments and all the
	 * EventAssignments in this list to this Event instance.
	 * @param listOfEventAssignments
	 */
	public void setListOfEventAssignments(
			ListOf<EventAssignment> listOfEventAssignments) {
		this.listOfEventAssignments = listOfEventAssignments;
		this.listOfEventAssignments.setSBaseListType(SBaseListType.listOfEventAssignments);
		setThisAsParentSBMLObject(this.listOfEventAssignments);
		stateChanged();
	}

	/**
	 * Sets the timeUnitsID of this Event to 'timeUnits'.
	 * @param timeUnits
	 */
	@Deprecated
	public void setTimeUnits(String timeUnits) {
		this.timeUnitsID = timeUnits;
		stateChanged();
	}

	/**
	 * Sets the timeUnitsID of this Event to the id of the UnitDefinition 'timeUnits'.
	 * @param timeUnits
	 */
	@Deprecated
	public void setTimeUnits(UnitDefinition timeUnits) {
		this.timeUnitsID = timeUnits != null ? timeUnits.getId() : null;
		stateChanged();
	}

	/**
	 * Sets the trigger of this Event to 'trigger'. It automatically sets the Trigger parentSBML object to
	 * this Event instance.
	 * @param trigger
	 */
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
		setThisAsParentSBMLObject(this.trigger);
		this.trigger.sbaseAdded();
	}

	/**
	 * Sets the useValuesFromTriggerTime of this Event to 'useValuesFromTriggerTime'.
	 * @param useValuesFromTriggerTime
	 */
	public void setUseValuesFromTriggerTime(boolean useValuesFromTriggerTime) {
		this.useValuesFromTriggerTime = useValuesFromTriggerTime;
	}
	
	/**
	 * Sets the trigger of this Event to null.
	 */
	public void unsetTrigger(){
		this.trigger = null;
	}
	/**
	 * Sets the delay of this Event to null.
	 */
	public void unsetDelay(){
		this.delay = null;
	}
	/**
	 * Sets the listOfEventAssignments of this Event to null.
	 */
	public void unsetListOfEventAssignments(){
		this.listOfEventAssignments = null;
	}
	/**
	 * Remove all the EventAssignments of the listOfEventAssignments of this Event.
	 */
	public void clearListOfEventAssignments(){
		this.listOfEventAssignments.clear();
	}
	/**
	 * Sets the timeUnitsID of this Event to null.
	 */
	public void unsetTimeUnits(){
		this.timeUnitsID = null;
	}
	/**
	 * Sets the useValuesFromTriggerTime of this Event to null.
	 */
	public void unsetUseValuesFromTriggerTime(){
		this.useValuesFromTriggerTime = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("useValuesFromTriggerTime")){
				if (value.equals("true")){
					this.setUseValuesFromTriggerTime(true);
					return true;
				}
				else if (value.equals("false")){
					this.setUseValuesFromTriggerTime(false);
					return true;
				}
			}
			else if (attributeName.equals("timeUnits")){
				this.setTimeUnits(value);
			}
		}
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetUseValuesFromTriggerTime()){
			attributes.put("useValuesFromTriggerTime", getUseValuesFromTriggerTime().toString());
		}
		
		if (isSetTimeUnits()){
			attributes.put("timeUnits", getTimeUnits());
		}
		
		return attributes;
	}
}
