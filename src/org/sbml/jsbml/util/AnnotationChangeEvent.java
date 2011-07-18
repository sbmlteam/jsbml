/*
 * $Id:  AnnotationChangedEvent.java 17:11:20 draeger $
 * $URL: AnnotationChangedEvent.java $
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

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AnnotationElement;

/**
 * This event tells an {@link AnnotationChangeListener} which values have been
 * changed in an {@link Annotation} and also provides the old and the new value.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 11.07.2011
 */
public class AnnotationChangeEvent extends ChangeEvent<AnnotationElement> {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -7994741197657827521L;

	public static final String about = "about";
	public static String nonRDFAnnotation = "nonRDFAnnotation";
	

	/**
	 * @param annotationChangedEvent
	 */
	public AnnotationChangeEvent(AnnotationChangeEvent annotationChangedEvent) {
		this(annotationChangedEvent.getSource(), annotationChangedEvent
				.getPropertyName(), annotationChangedEvent.getOldValue(),
				annotationChangedEvent.getNewValue());
	}
	
	/**
	 * @param source
	 */
	public AnnotationChangeEvent(AnnotationElement source, String propertyName,
			Object oldValue, Object newValue) {
		super(source, propertyName, oldValue, newValue);
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public AnnotationChangeEvent clone() {
		return new AnnotationChangeEvent(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.util.ChangeEvent#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AnnotationChangeEvent) {
			return super.equals((AnnotationChangeEvent) obj);
		}
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.util.EventObject#getSource()
	 */
	@Override
	public AnnotationElement getSource() {
		return (AnnotationElement) super.getSource();
	}

}
