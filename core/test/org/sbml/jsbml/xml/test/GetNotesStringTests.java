/* 
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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
package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 */
public class GetNotesStringTests {
	
	
	public static String DATA_FOLDER = null;
	
	static {
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getenv("DATA_FOLDER"); 
		}
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getProperty("DATA_FOLDER"); 
		}
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getProperty("user.dir") + "/core/test/org/sbml/jsbml/xml/test/data";
		}
	}

	/**
	 * 
	 */
	@Before public void setUp() { 
	}
	
	/**
	 * Tries to use getNotesString on a model without a notes element.
	 */
	@Test public void getNotesStringOnModel() throws IOException, XMLStreamException {
		String fileName = DATA_FOLDER + "/libsbml-test-data/l1v1-minimal.xml";
		
		SBMLDocument doc = new SBMLReader().readSBML(fileName);
        assertNotNull(doc.getModel().getNotesString());
		assertTrue(doc.getModel().getNotesString() == "");
	}

}
