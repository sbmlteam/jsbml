/*
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

package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * 
 * @author
 */
public class CheckConsistencyTests {
	
	
	public static String DATA_FOLDER = null;
	
	static {
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getenv("DATA_FOLDER"); 
		}
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getProperty("DATA_FOLDER"); 
		}
	}

	/**
	 * 
	 */
	@Before public void setUp() { 
	}
	
	/**
	 * Tries to validate biomodels file with id 228. 
	 */
	@Test public void checkConsistency() throws IOException, XMLStreamException {
		String fileName = DATA_FOLDER + "/l2v4/BIOMD0000000228.xml";
		
		SBMLDocument doc = new SBMLReader().readSBML(fileName);
		assertTrue(doc.checkConsistency() == 0);
	}
}
