/*
 *
 * @file    TestL3Group.java
 * @brief   L3 Groups package unit tests
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


import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.groups.Group;
import org.sbml.jsbml.ext.groups.Member;
import org.sbml.jsbml.ext.groups.ModelGroupExtension;
import org.sbml.jsbml.xml.stax.SBMLReader;
import org.sbml.jsbml.xml.stax.SBMLWriter;

public class TestL3groups {

	public static String DATA_FOLDER = null;
	public static String GROUPS_NAMESPACE = "http://www.sbml.org/sbml/level3/version1/groups/version1";
	
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
 * @throws FileNotFoundException 
   */
  @Test public void test_L3_Groups_read1() throws XMLStreamException, FileNotFoundException
  {
		String fileName = DATA_FOLDER + "/groups/groups1.xml";
		
		SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
		Model model = doc.getModel();
		
		System.out.println("Model extension objects : " + model.getExtension(GROUPS_NAMESPACE));
		ModelGroupExtension extendedModel = (ModelGroupExtension) model.getExtension(GROUPS_NAMESPACE);
		
		System.out.println("Nb Groups = " + extendedModel.getListOfGroups().size());
		
		Group group = extendedModel.getGroup(0);
		
		System.out.println("Group sboTerm, id = " + group.getSBOTermID() + ", " + group.getId());
		System.out.println("Nb Members = " + group.getListOfMembers().size());
		
		Member member = group.getMember(0);
		
		System.out.println("Member(0).symbol = " + member.getSymbol());
		
  }

  /**
   * 
   * @throws XMLStreamException
   * @throws InstantiationException
   * @throws IllegalAccessException
 * @throws FileNotFoundException 
   */
  @Test public void test_L3_Groups_write1() throws XMLStreamException, InstantiationException, IllegalAccessException, FileNotFoundException
  {
	  String fileName = DATA_FOLDER + "/groups/groups1.xml";
		
	  SBMLDocument doc = SBMLReader.readSBMLFile(fileName);
	  
	  SBMLWriter.write(doc, DATA_FOLDER + "/groups/groups1_write.xml");
  }
}
