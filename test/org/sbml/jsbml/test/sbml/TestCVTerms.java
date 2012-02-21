/*
 * @file    TestCVTerms.java
 * @brief   CVTerms unit tests
 *
 * This test file was converted from libsbml http://sbml.org/software/libsbml
 *
 * $Id$
 * $HeadURL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

package org.sbml.jsbml.test.sbml;

import java.util.List;

import org.junit.Test;
import org.sbml.jsbml.CVTerm;

/**
 * @author  Akiya Jouraku
 * @author  Sarah Keating
 * @since 0.8
 * @version $Rev$
 */
public class TestCVTerms {

  static void assertTrue(boolean condition) throws AssertionError
  {
    if (condition == true)
    {
      return;
    }
    throw new AssertionError();
  }

  static void assertEquals(Object a, Object b) throws AssertionError
  {
    if ( (a == null) && (b == null) )
    {
      return;
    }
    else if ( (a == null) || (b == null) )
    {
      throw new AssertionError();
    }
    else if (a.equals(b))
    {
      return;
    }

    throw new AssertionError();
  }

  static void assertNotEquals(Object a, Object b) throws AssertionError
  {
    if ( (a == null) && (b == null) )
    {
      throw new AssertionError();
    }
    else if ( (a == null) || (b == null) )
    {
      return;
    }
    else if (a.equals(b))
    {
      throw new AssertionError();
    }
  }

  static void assertEquals(boolean a, boolean b) throws AssertionError
  {
    if ( a == b )
    {
      return;
    }
    throw new AssertionError();
  }

  static void assertNotEquals(boolean a, boolean b) throws AssertionError
  {
    if ( a != b )
    {
      return;
    }
    throw new AssertionError();
  }

  static void assertEquals(int a, int b) throws AssertionError
  {
    if ( a == b )
    {
      return;
    }
    throw new AssertionError();
  }

  static void assertNotEquals(int a, int b) throws AssertionError
  {
    if ( a != b )
    {
      return;
    }
    throw new AssertionError();
  }

  @Test
  public void test_CVTerm_addResource()
  {
    CVTerm term = new  CVTerm(); // TODO : difference to document => CVTerm term = new  CVTerm(CVTerm.Type.MODEL_QUALIFIER); constructor does not exist
    term.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);    
    String resource =  "GO6666";
    assertTrue( term != null );
    assertTrue( term.getQualifierType() == CVTerm.Type.MODEL_QUALIFIER ); // TODO : difference to document ==> CVTerm.Type.MODEL_QUALIFIER
    term.addResource(resource);
    List<String> xa = term.getResources(); // TODO : difference to document ==> term.getResources(); does not return XMLAttributes but a List<String>
    assertTrue( xa.size() == 1 );
    // assertTrue(xa.getName(0).equals( "rdf:resource"));
    assertTrue(xa.get(0).equals( "GO6666"));
    term = null;
  }

  @Test public void test_CVTerm_create()
  {
    CVTerm term = new  CVTerm();
    term.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);  
    assertTrue( term != null );
    assertTrue( term.getQualifierType() == CVTerm.Type.MODEL_QUALIFIER );
    term = null;
  }

  /*
   * 
   * TODO :  difference to document ==> Annotation are not parse into XMLNode and the example is not yet supported.
   * 
  @Test public void test_CVTerm_createFromNode()
  {
    XMLAttributes xa;
    XMLTriple qual_triple = new  XMLTriple("is", "", "bqbiol");
    XMLTriple bag_triple = new  XMLTriple();
    XMLTriple li_triple = new  XMLTriple();
    XMLAttributes att = new  XMLAttributes();
    att.add( "", "This is my resource");
    XMLAttributes att1 = new  XMLAttributes();
    XMLToken li_token = new  XMLToken(li_triple,att);
    XMLToken bag_token = new  XMLToken(bag_triple,att1);
    XMLToken qual_token = new  XMLToken(qual_triple,att1);
    XMLNode li = new XMLNode(li_token);
    XMLNode bag = new XMLNode(bag_token);
    XMLNode node = new XMLNode(qual_token);
    bag.addChild(li);
    node.addChild(bag);
    CVTerm term = new  CVTerm(node);
    assertTrue( term != null );
    assertTrue( term.getQualifierType() == CVTerm.Type.BIOLOGICAL_QUALIFIER );
    assertTrue( term.getBiologicalQualifierType() == libsbml.BQB_IS );
    xa = term.getResources();
    assertTrue( xa.getLength() == 1 );
    assertTrue(xa.getName(0).equals( "rdf:resource"));
    assertTrue(xa.getValue(0).equals( "This is my resource"));
    qual_triple = null;
    bag_triple = null;
    li_triple = null;
    li_token = null;
    bag_token = null;
    qual_token = null;
    att = null;
    att1 = null;
    term = null;
    node = null;
    bag = null;
    li = null;
  }
  */

  @Test public void test_CVTerm_getResources()
  {
    CVTerm term = new  CVTerm();
    term.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);
    String resource =  "GO6666";
    String resource1 =  "OtherURI";
    long number;
    term.addResource(resource);
    term.addResource(resource1);
    number = term.getNumResources();
    assertTrue( number == 2 );
    assertTrue(term.getResourceURI(0).equals( "GO6666"));
    assertTrue(term.getResourceURI(1).equals( "OtherURI"));
    term = null;
  }

  @Test public void test_CVTerm_set_get()
  {
    CVTerm term = new  CVTerm();
    term.setQualifierType(CVTerm.Type.MODEL_QUALIFIER);
    assertTrue( term != null );
    assertTrue( term.getQualifierType() == CVTerm.Type.MODEL_QUALIFIER );
    term.setModelQualifierType(CVTerm.Qualifier.BQM_IS); // TODO : difference to document ==> libsbml.BQM_IS become 
    assertTrue( term != null );
    assertTrue( term.getQualifierType() == CVTerm.Type.MODEL_QUALIFIER );
    assertTrue( term.getModelQualifierType() == CVTerm.Qualifier.BQM_IS );
    term.setQualifierType(CVTerm.Type.BIOLOGICAL_QUALIFIER);
    term.setBiologicalQualifierType(CVTerm.Qualifier.BQB_IS);
    assertTrue( term.getQualifierType() == CVTerm.Type.BIOLOGICAL_QUALIFIER );
    assertTrue( term.getBiologicalQualifierType() == CVTerm.Qualifier.BQB_IS );
    term = null;
  }

}
