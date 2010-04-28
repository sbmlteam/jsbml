/*
 *
 * @file    TestL3Layout.java
 * @brief   L3 Layout package unit tests
 *
 * @author rodrigue (jsbml conversion)
 * @author  Akiya Jouraku (Java conversion)
 * @author  Sarah Keating 
 *
 * $Id$
 * $URL$
 *
 * This test file was converted from libsbml http://sbml.org/software/libsbml
 *
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
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

package org.sbml.jsbml.test.sbml;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.layout.ExtendedLayoutModel;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.xml.stax.SBMLReader;

public class TestL3Layout {

	public static String DATA_FOLDER = null;
	public static String LAYOUT_NAMESPACE = "http://www.sbml.org/sbml/level3/version1/layout/version1";
	
	static {
		
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getenv("DATA_FOLDER"); 
		}
		if (DATA_FOLDER == null) {
			DATA_FOLDER = System.getProperty("DATA_FOLDER"); 
		}
		
	}


  public boolean isNaN(double x)
  {
    return Double.isNaN(x);
  }

  @Before public void setUp() throws Exception
  {
  }

  @After public void tearDown() throws Exception
  {
  }

  @Test public void test_L3_Layout_read1()
  {
		String fileName = DATA_FOLDER + "/layout/GlycolysisLayout_small.xml";
		
		SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
		Model model = doc.getModel();
		
		System.out.println("Model extension objects : " + model.getExtension(LAYOUT_NAMESPACE));
		ExtendedLayoutModel extendedModel = (ExtendedLayoutModel) model.getExtension(LAYOUT_NAMESPACE);
		
		System.out.println("Nb Layouts = " + extendedModel.getListOfLayouts().size());
		
		Layout layout = extendedModel.getLayout(0);
		
		// System.out.println("Group sboTerm, id = " + group.getSBOTermID() + ", " + group.getId()); print dimension
		System.out.println("Nb SpeciesGlyphs = " + layout.getListOfSpeciesGlyphs().size());
		
		SpeciesGlyph  speciesGlyph = layout.getSpeciesGlyph(0);
		
		// System.out.println("Member(0).symbol = " + member.getSymbol());
		
  }

  @Test public void test_L3_Layout_write1()
  {
	  String fileName = DATA_FOLDER + "/layout/GlycolysisLayout_small.xml";
		
	  // SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
	  
	  // SBMLWriter.write(doc, DATA_FOLDER + "/layout/GlycolysisLayout_small_write.xml");
  }
}
