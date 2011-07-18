/*
 * $Id:  ChangeEvent.java 17:24:07 draeger $
 * $URL: ChangeEvent.java $
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2011 jointly by the following organizations: 
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
package org.sbml.jsbml.util;

import java.util.EventObject;

import org.sbml.jsbml.SBase;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 11.07.2011
 */
public abstract class ChangeEvent<S> extends EventObject {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 923488666393391987L;

	/**
	 * The previous value and the new value of the changed property in the
	 * {@link SBase}
	 */
	private Object oldValue, newValue;
	/**
	 * The name of the property that has changed.
	 */
	private String propertyName;
	
	/**
	 * 
	 * @param source
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	public ChangeEvent(S source, String propertyName, Object oldValue,
			Object newValue) {
		super(source);
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract ChangeEvent<S> clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ChangeEvent<?>) {
			ChangeEvent<?> sbce = (ChangeEvent<?>) o;
			if (sbce == this) {
				return true;
			}
			boolean equal = sbce.getSource().equals(getSource());
			equal &= sbce.getPropertyName().equals(getPropertyName());
			equal &= sbce.getOldValue().equals(getOldValue());
			equal &= sbce.getNewValue().equals(getNewValue());
			return equal;
		}
		return false;
	}
	
	/**
	 * @return the newValue
	 */
	public Object getNewValue() {
		return newValue;
	}
	
	/**
	 * @return the oldValue
	 */
	public Object getOldValue() {
		return oldValue;
	}
	
	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.EventObject#getSource()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public S getSource() {
		return (S) super.getSource();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.EventObject#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"%s [source=%s, property=%s, oldValue=%s, newValue=%s]",
				getClass().getSimpleName(), getSource().toString(),
				getPropertyName(), (getOldValue() != null ? getOldValue()
						: "null"), getNewValue() != null ? getNewValue()
						.toString() : "null");
	}

}
