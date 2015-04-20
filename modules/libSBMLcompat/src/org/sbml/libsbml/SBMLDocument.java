/*
 * $Id: SBMLDocument.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/libSBMLcompat/src/org/sbml/libsbml/SBMLDocument.java $
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

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.jsbml.xml.test.SBML_L2V1Test;


/**
 * 
 * @author Nicolas Rodriguez
 *
 */
public class SBMLDocument extends org.sbml.jsbml.SBMLDocument {

    /**
     * Generated serial version identifier
     */
    private static final long serialVersionUID = 7711298613548013138L;
    
	private Model model;

	/**
	 * 
	 */
	public SBMLDocument() {
		super();
	}

	/**
	 * @param sb
	 */
	public SBMLDocument(SBMLDocument sb) {
		// Call the AbstracSBase constructor
		
		if (sb.isSetModel()) {
			setModel(new Model(sb.getModel()));
		} else {
			this.model = null;
		}
		setParentSBML(this);
		
	}

	/**
	 * @param sb
	 */
	public SBMLDocument(org.sbml.jsbml.SBMLDocument sb) {
		// Call the AbstracSBase constructor
		super(sb);
		
		if (sb.isSetModel()) {
			setModel(new Model(sb.getModel()));
		} else {
			this.model = null;
		}
		setParentSBML(this);
		
	}

	/**
	 * @param level
	 * @param version
	 */
	public SBMLDocument(int level, int version) {
		super(level, version);
	}
	
	public SBMLDocument clone() {
		return new SBMLDocument(this);
	}
	
	/**
	 * Creates a new instance of Model from id and the level and version of this
	 * SBMLDocument.
	 * 
	 * @param id
	 * @return the new Model instance.
	 */
	public Model createModel(String id) {
		this.setModel(new Model(id, getLevel(), getVersion()));
		return model;
	}

	/**
	 * Sets the Model for this SBMLDocument to a copy of the given Model.
	 * 
	 * @param model
	 */
	public void setModel(Model model) {
		this.model = model;
		setThisAsParentSBMLObject(this.model);
	}

	/**
	 * Returns the model of this SBMLDocument.
	 * 
	 * @return the model of this SBMLDocument. Can be null if it is not set.
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * 
	 * @param args
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	public static void main(String[] args) throws XMLStreamException, IOException {
		
		String fileName = SBML_L2V1Test.DATA_FOLDER + "/l2v1/BIOMD0000000025.xml";
		
		// These two lines would be code for a org.sbml.libsbml.SBMLReader.readSBMLFile function
		SBMLReader reader = new SBMLReader();
		org.sbml.jsbml.SBMLDocument jsbmlDocument = reader.readSBMLFile(fileName);		
		SBMLDocument document = new SBMLDocument(jsbmlDocument);
		
		System.out.println("ListOfSpecies size = " + document.getModel().getListOfSpecies().size());
		
		Model model = document.getModel();
		
		System.out.println("Model class = " + model.getClass());
		
		System.out.println("Model is an instance of org.sbml.libsbml.Model: " + (model instanceof org.sbml.libsbml.Model));
		
		ListOfSpecies listOfSpecies = model.getListOfSpecies(); 
		
		System.out.println("the list of Species is an instance of org.sbml.libsbml.ListOfSpecies: " + (listOfSpecies instanceof ListOfSpecies));
		
		System.out.println("Species nb 2 id = " + listOfSpecies.get(2).getId());
		
		listOfSpecies.remove(listOfSpecies.get(2).getId());
		
		listOfSpecies.remove(2);
		
		System.out.println("ListOfSpecies size = " + document.getModel().getListOfSpecies().size());
	}
	
}
