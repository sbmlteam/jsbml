/*
 *
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.math.ASTCiNumberNode;
import org.sbml.jsbml.math.ASTNode2;


/**
 * @author Nicolas Rodriguez
 * @since 1.1
 * 
 */
public class UserObjectTest {

	private final String KEY_1 = "KEY_1";
	private final String KEY_2 = "KEY_2";
	
  /**
   * 
   */
  private static final transient Logger logger = Logger.getLogger(UserObjectTest.class);

  /**
   * 
   */
  @Test
  public void SpeciesTest() {
	  Species s = new Species();
	  
	  assertTrue(s.isSetUserObjects() == false);
	  
	  s.putUserObject(KEY_1, 3);
	  s.putUserObject(KEY_2, "userObject");
	  
	  assertTrue(s.isSetUserObjects() == true);
	  assertTrue((Integer) s.getUserObject(KEY_1) == 3);
	  assertTrue(s.getUserObject(KEY_2) == "userObject");
	  
	  Species s2 = s.clone();
	  
	  assertTrue(s2.isSetUserObjects() == true);
	  assertTrue((Integer) s2.getUserObject(KEY_1) == 3);
	  assertTrue(s2.getUserObject(KEY_2) == "userObject");
	  
  }

  /**
   * 
   */
  @Test
  public void ASTNodeTest() {
	  ASTNode s = new ASTNode(Type.NAME);
	  
	  assertTrue(s.isSetUserObjects() == false);
	  
	  s.putUserObject(KEY_1, 3);
	  s.putUserObject(KEY_2, "userObject");
	  
	  assertTrue(s.isSetUserObjects() == true);
	  assertTrue((Integer) s.getUserObject(KEY_1) == 3);
	  assertTrue(s.getUserObject(KEY_2) == "userObject");
	  
	  ASTNode s2 = s.clone();
	  
	  assertTrue(s2.isSetUserObjects() == true);
	  assertTrue((Integer) s2.getUserObject(KEY_1) == 3);
	  assertTrue(s2.getUserObject(KEY_2) == "userObject");
	  
  }

  /**
   * 
   */
  @Test
  public void ASTNode2Test() {
      ASTNode2 s = new ASTCiNumberNode();
      
      assertTrue(s.isSetUserObjects() == false);
      
      s.putUserObject(KEY_1, 3);
      s.putUserObject(KEY_2, "userObject");
      
      assertTrue(s.isSetUserObjects() == true);
      assertTrue((Integer) s.getUserObject(KEY_1) == 3);
      assertTrue(s.getUserObject(KEY_2) == "userObject");
      
      ASTNode2 s2 = s.clone();
      
      assertTrue(s2.isSetUserObjects() == true);
      assertTrue((Integer) s2.getUserObject(KEY_1) == 3);
      assertTrue(s2.getUserObject(KEY_2) == "userObject");
      
  }

}
