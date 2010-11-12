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

/**
 * Represents the trigger XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 */
public class Trigger extends AbstractMathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6964409168614117235L;

	/**
	 * 
	 */
	private Boolean initialValue;

	/**
	 * 
	 */
	private Boolean persistent;

	/**
	 * Creates a {@link Trigger} instance.
	 */
	public Trigger() {
		super();
		initDefaults();
	}

	/**
	 * Creates a {@link Trigger} instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public Trigger(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/**
	 * Creates a {@link Trigger} instance from a given {@link Trigger}.
	 * 
	 * @param trigger
	 */
	public Trigger(Trigger trigger) {
		super(trigger);
		initDefaults();
		if (trigger.isSetInitialValue()) {
			this.initialValue = trigger.getInitialValue();
		}
		if (trigger.isSetPersistent()) {
			this.persistent = trigger.getPersistent();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	@Override
	public Trigger clone() {
		return new Trigger(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (super.equals(o) && (o instanceof Trigger)) {
			Trigger t = (Trigger) o;
			if (!(t.isSetInitialValue() == isSetInitialValue())
					&& (t.isSetPersistent() == isSetPersistent())) {
				return false;
			}
			boolean equal = true;
			if (t.isSetInitialValue() && isSetInitialValue()) {
				equal &= getInitialValue() == t.getInitialValue();
			}
			if (t.isSetPersistent() && isSetPersistent()) {
				equal &= getPersistent() == t.getPersistent();
			}
			return equal;
		}
		return false;
	}

	/**
	 * @return the initialValue
	 */
	public boolean getInitialValue() {
		if (getLevel() < 3) {
			return true;
		}
		return isSetInitialValue() ? initialValue.booleanValue() : false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	public Event getParent() {
		return (Event) super.getParent();
	}

	/**
	 * @return the persistent
	 */
	public boolean getPersistent() {
		if (getLevel() < 3) {
			return true;
		}
		return isSetPersistent() ? persistent.booleanValue() : false;
	}

	/**
	 * Sets the properties {@link #initialValue} and {@link #persistent} to null, i.e., undefined.
	 */
	public void initDefaults() {
		initialValue = persistent = null;
	}
	
	/**
	 * 
	 * @return whether or not this {@link Trigger} is initially set to true.
	 */
	public boolean isInitialValue() {
		return getInitialValue();
	}
	
	/**
	 * 
	 * @return whether or not this is a persistent {@link Trigger}
	 */
	public boolean isPersistent() {
		return getPersistent();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetInitialValue() {
		return initialValue != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetPersistent() {
		return persistent != null;
	}

	/**
	 * @param initialValue
	 *            the initialValue to set
	 */
	public void setInitialValue(boolean initialValue) {
		if (getLevel() < 3) {
			throw new IllegalArgumentException(
					"Cannot set initialValue property for Trigger with Level < 3.");
		}
		this.initialValue = Boolean.valueOf(initialValue);
	}

	/**
	 * @param persistent
	 *            the persistent to set
	 */
	public void setPersistent(boolean persistent) {
		if (getLevel() < 3) {
			throw new IllegalArgumentException(
					"Cannot set persistent property for Trigger with Level < 3.");
		}
		this.persistent = Boolean.valueOf(persistent);
	}
}
