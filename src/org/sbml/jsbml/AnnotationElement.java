/*
 * $Id:  AnnotationElement.java 17:34:10 draeger $
 * $URL: AnnotationElement.java $
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
package org.sbml.jsbml;

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.util.AnnotationChangeEvent;
import org.sbml.jsbml.util.AnnotationChangeListener;


/**
 * A common super class for all those elements that can be part of an {@link Annotation}.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 08.07.2011
 */
public abstract class AnnotationElement extends AbstractTreeNode {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 495755171215798027L;
	
	/**
	 * {@link Set} of listeners for this component
	 */
	protected Set<AnnotationChangeListener> setOfListeners;
	
	/**
	 * 
	 */
	public AnnotationElement() {
		super();
		setOfListeners = new HashSet<AnnotationChangeListener>();
	}
	
	/**
	 * 
	 * @param annotation
	 */
	public AnnotationElement(AnnotationElement annotation) {
		this();
		setOfListeners.addAll(annotation.setOfListeners);
	}
	
	

	/**
	 * All {@link AnnotationChangeListener} instances linked to this
	 * {@link AnnotationElement} are informed about the adding of this object to
	 * an owning {@link AnnotationElement} or to another new parent SBML object.
	 */
	public void fireAnnotationAddedEvent() {
		for (AnnotationChangeListener listener : setOfListeners) {
			listener.annotationAdded(this);
		}
	}
	
	/**
	 * All {@link AnnotationChangeListener} instances linked to this
	 * {@link Annotation} are informed about the deletion of this
	 * {@link AnnotationElement} from another parent SBML object.
	 */
	public void fireAnnotationRemovedEvent() {
		for (AnnotationChangeListener listener : setOfListeners) {
			listener.annotationRemoved(this);
		}
	}

	/**
	 * All {@link AnnotationChangeListener}s are informed about the change in this
	 * {@link AnnotationElement}.
	 * 
	 * @param propertyName
	 *            Tells the {@link AnnotationChangeListener} the name of the
	 *            property whose value has been changed.
	 * @param oldValue
	 *            This is the value before the change.
	 * @param newValue
	 *            This gives the new value that is now the new value for the
	 *            given property..
	 */
	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		if ((setOfListeners.size() > 0)
				&& (((oldValue == null) && (newValue != null))
				|| ((oldValue != null) && (newValue == null))
				|| (oldValue != null && !oldValue.equals(newValue)))) 
		{
			AnnotationChangeEvent changeEvent = new AnnotationChangeEvent(this,
					propertyName, oldValue, newValue);
			for (AnnotationChangeListener listener : setOfListeners) {
				listener.stateChanged(changeEvent);
			}
		}
	}

	/**
	 * @return
	 */
	public Set<AnnotationChangeListener> getSetOfAnnotationChangedListeners() {
		return setOfListeners;
	}
	
}
