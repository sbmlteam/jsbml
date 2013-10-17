/*
 *
 * @file    TestL3Layout.java
 * @brief   L3 Layout package unit tests
 *
 * @author  Nicolas Rodriguez (JSBML conversion)
 * @author  Akiya Jouraku (Java conversion)
 * @author  Sarah Keating 
 *
 * This test file was converted from libsbml http://sbml.org/software/libsbml
 *
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
package org.sbml.jsbml.ext.layout;


import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import javax.xml.stream.XMLStreamException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class TestL3Layout {

  /**
   * 
   */
	public static String DATA_FOLDER = null;
	/**
	 * 
	 */
	public static String LAYOUT_NAMESPACE = "http://www.sbml.org/sbml/level3/version1/layout/version1";
	
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
	 * @param x
	 * @return
	 */
  public boolean isNaN(double x)
  {
    return Double.isNaN(x);
  }

  /**
   * 
   * @throws Exception
   */
  @Before public void setUp() throws Exception
  {
  }

  /**
   * 
   * @throws Exception
   */
  @After public void tearDown() throws Exception
  {
  }

  /**
   * 
   * @throws XMLStreamException
 * @throws ClassNotFoundException 
 * @throws IOException 
 * @throws InvalidPropertiesFormatException 
   */
  @Test public void test_L3_Layout_read1() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException
  {
	  if (DATA_FOLDER == null)
	  {
		  DATA_FOLDER = System.getProperty("user.dir") + "/extensions/layout/test/org/sbml/jsbml/xml/test/data";
	  }
		String fileName = DATA_FOLDER + "/layout/GlycolysisLayout_small.xml";
		
		SBMLDocument doc = new SBMLReader().readSBMLFile(fileName);
		Model model = doc.getModel();
		
		System.out.println("Model extension objects : " + model.getExtension(LAYOUT_NAMESPACE));
		LayoutModelPlugin extendedModel = (LayoutModelPlugin) model.getExtension(LAYOUT_NAMESPACE);
		
		System.out.println("Nb Layouts = " + extendedModel.getListOfLayouts().size());
		
		Layout layout = extendedModel.getLayout(0);
		
		// System.out.println("Group sboTerm, id = " + group.getSBOTermID() + ", " + group.getId()); print dimension
		System.out.println("Nb SpeciesGlyphs = " + layout.getListOfSpeciesGlyphs().size());
		
		SpeciesGlyph  speciesGlyph = layout.getSpeciesGlyph(0);
		
		// System.out.println("Member(0).symbol = " + member.getSymbol());
		
  }

  /**
   * 
   */
  @Test public void test_L3_Layout_write1()
  {
	  String fileName = DATA_FOLDER + "/layout/GlycolysisLayout_small.xml";
		
	  // SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
	  
	  // SBMLWriter.write(doc, DATA_FOLDER + "/layout/GlycolysisLayout_small_write.xml");
  }
}
