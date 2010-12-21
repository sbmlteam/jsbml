/*
 * $Id: QuantityWithUnit.java 173 2010-04-09 06:32:34Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/QuantityWithUnit.java $
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
 * This object represents an element with identifier and name, a value, and a
 * defined unit. In particular, this class defines methods to access and
 * manipulate the value and the unit properties of an element within a model.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @date 2010-04-20
 */
public abstract class QuantityWithUnit extends AbstractNamedSBaseWithUnit
		implements Quantity {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -9088772458214208160L;
	/**
	 * a boolean to help knowing is the value as been set by the user or is the
	 * default one.
	 */
	private boolean isSetValue = false;

	/**
	 * The size, initial amount or concentration, or the actual value of this
	 * variable.
	 */
	/*
	 * Visibility modified to allow children classes to set a default value
	 * without having the isSetValue returning true.
	 */
	protected Double value = Double.NaN;

	/**
	 * 
	 */
	public QuantityWithUnit() {
		super();
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public QuantityWithUnit(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * 
	 * @param qwdu
	 */
	public QuantityWithUnit(QuantityWithUnit qwdu) {
		super(qwdu);
		if (qwdu.isSetValue()) {
			this.value = new Double(qwdu.getValue());
			isSetValue = true;
		} else {
			this.value = null;
		}
	}

	/**
	 * 
	 * @param id
	 */
	public QuantityWithUnit(String id) {
		this();
		setId(id);
	}

	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public QuantityWithUnit(String id, int level, int version) {
		super(id, null, level, version);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public QuantityWithUnit(String id, String name, int level,
			int version) {
		super(id, name, level, version);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#clone()
	 */
	public abstract QuantityWithUnit clone();

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof QuantityWithUnit) {			
			boolean equal = super.equals(o);
			QuantityWithUnit v = (QuantityWithUnit) o;
			if (!(Double.isNaN(v.getValue()) && Double.isNaN(getValue()))) {
				equal &= v.getValue() == getValue();
			} else {
				equal &= (Double.isNaN(v.getValue()) && Double
						.isNaN(getValue()));
			}			
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#getValue()
	 */
	public double getValue() {
		return value != null ? value : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#isSetValue()
	 */
	public boolean isSetValue() {
		return isSetValue;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#setValue(double)
	 */
	public void setValue(double value) {
		Double oldValue = this.value;
		this.value = value;
		if (!Double.isNaN(value)) {
			isSetValue = true;
		}
		firePropertyChange(SBaseChangedEvent.value, oldValue, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#unsetValue()
	 */
	public void unsetValue() {
		Double oldValue = value;
		value = Double.NaN;
		isSetValue = false;
		firePropertyChange(SBaseChangedEvent.value, oldValue, value);
	}
}
