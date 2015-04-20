/*
 * $Id: Compartment.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/libSBMLcompat/src/org/sbml/libsbml/Compartment.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.libsbml;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.TreeNodeChangeListener;

/**
 * 
 * 
 * 
 * @author Nicolas Rodriguez
 * 
 * 
 *
 */
public class Compartment {

	/**
	 * 
	 */
	private org.sbml.jsbml.Compartment compartment;
	
	public Compartment(int level, int version) {
		compartment = new org.sbml.jsbml.Compartment(level, version);
	}
	
	// TODO: add all the constructors and missing methods
	
	/**
	 * 
	 * @param compartment
	 */
	public Compartment(org.sbml.jsbml.Compartment compartment) {
		this.compartment = compartment;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.Species#clone()
	 */
	@Override
	public org.sbml.jsbml.Compartment clone() {
		return compartment.clone();
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.Species#initDefaults()
	 */
	public void initDefaults() {
		compartment.initDefaults();
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#addChangeListener(org.sbml.jsbml.SBaseChangedListener)
	 */
	public void addChangeListener(TreeNodeChangeListener l) {
		compartment.addTreeNodeChangeListener(l);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#appendNotes(java.lang.String)
	 */
	public void appendNotes(String notes) {
		compartment.appendNotes(notes);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getAnnotation()
	 */
	public Annotation getAnnotation() {
		return compartment.getAnnotation();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	public String getElementName() {
		return compartment.getElementName();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getLevel()
	 */
	public int getLevel() {
		return compartment.getLevel();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getMetaId()
	 */
	public String getMetaId() {
		return compartment.getMetaId();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getModel()
	 */
	public Model getModel() {
		return compartment.getModel();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getNotesString()
	 */
	public String getNotesString() {
		return compartment.getNotesString();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getSBMLDocument()
	 */
	public SBMLDocument getSBMLDocument() {
		return compartment.getSBMLDocument();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getSBOTerm()
	 */
	public int getSBOTerm() {
		return compartment.getSBOTerm();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getSBOTermID()
	 */
	public String getSBOTermID() {
		return compartment.getSBOTermID();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getVersion()
	 */
	public int getVersion() {
		return compartment.getVersion();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hasValidAnnotation()
	 */
	public boolean hasValidAnnotation() {
		return compartment.hasValidAnnotation();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hasValidLevelVersionNamespaceCombination()
	 */
	public boolean hasValidLevelVersionNamespaceCombination() {
		return compartment.hasValidLevelVersionNamespaceCombination();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetAnnotation()
	 */
	public boolean isSetAnnotation() {
		return compartment.isSetAnnotation();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetMetaId()
	 */
	public boolean isSetMetaId() {
		return compartment.isSetMetaId();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetNotes()
	 */
	public boolean isSetNotes() {
		return compartment.isSetNotes();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#isSetSBOTerm()
	 */
	public boolean isSetSBOTerm() {
		return compartment.isSetSBOTerm();
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setAnnotation(org.sbml.jsbml.Annotation)
	 */
	public void setAnnotation(Annotation annotation) {
		compartment.setAnnotation(annotation);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setMetaId(java.lang.String)
	 */
	public void setMetaId(String metaid) {
		compartment.setMetaId(metaid);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setNotes(java.lang.String)
	 */
	public void setNotes(String notes) {
		compartment.setNotes(notes);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setSBOTerm(int)
	 */
	public void setSBOTerm(int term) {
		compartment.setSBOTerm(term);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#setSBOTerm(java.lang.String)
	 */
	public void setSBOTerm(String sboid) {
		compartment.setSBOTerm(sboid);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetAnnotation()
	 */
	public void unsetAnnotation() {
		compartment.unsetAnnotation();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetMetaId()
	 */
	public void unsetMetaId() {
		compartment.unsetMetaId();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetNotes()
	 */
	public void unsetNotes() {
		compartment.unsetNotes();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#unsetSBOTerm()
	 */
	public void unsetSBOTerm() {
		compartment.unsetSBOTerm();
	}

}
