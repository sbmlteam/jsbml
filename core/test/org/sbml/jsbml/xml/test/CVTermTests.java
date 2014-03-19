/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;

/**
 * Tests the {@link CVTerm} class.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class CVTermTests {

  private static final String HTTP_IDENTIFIERS_ORG_TAXONOMY_9606 = "http://identifiers.org/taxonomy/9606";
  private static final String HTTP_IDENTIFIERS_ORG_GO_GO_1222222 = "http://identifiers.org/go/GO:1222222";
  private static final String HTTP_IDENTIFIERS_ORG_GO_GO_1234567 = "http://identifiers.org/go/GO:1234567";
  private static final String TEST_MODEL_NAME = "test_model";
  private SBMLDocument doc;
  private Model model;
  private Compartment compartment;

  
  @BeforeClass public static void initialSetUp() {}

  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(3, 1);
    model = doc.createModel(TEST_MODEL_NAME);
    model.setMetaId("M1");
    compartment = model.createCompartment("cytoplasm");
    compartment.setMetaId("M2");
    model.createReaction("R1");

    model.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_HAS_TAXON, HTTP_IDENTIFIERS_ORG_TAXONOMY_9606));

    compartment.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, HTTP_IDENTIFIERS_ORG_GO_GO_1234567));
    compartment.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_DESCRIBED_BY, "http://identifiers.org/pubmed/toDelete"));
    compartment.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_PROPERTY_OF, "toDelete", "uri2", "uri3"));
    compartment.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_HAS_PART, HTTP_IDENTIFIERS_ORG_GO_GO_1222222));
  }

  /**
   * 
   */
  @Test public void cvTermTest1() {

    assertTrue(doc.getLevel() == 3 && doc.getVersion() == 1);
    assertTrue(model.getLevel() == 3 && model.getVersion() == 1);

    assertTrue(model.getId().equals(TEST_MODEL_NAME));

    assertTrue(model.getCVTermCount() == 1);
    
    CVTerm cvTerm = model.getCVTerm(0);
    
    assertTrue(cvTerm != null);
    assertTrue(cvTerm.getQualifier().equals(CVTerm.Qualifier.BQB_HAS_TAXON));
    assertTrue(cvTerm.getResourceCount() == 1);
    assertTrue(cvTerm.getResourceURI(0).equals(HTTP_IDENTIFIERS_ORG_TAXONOMY_9606));
    
    boolean removed = cvTerm.removeFromParent();
    
    assertTrue(removed == true);
    assertTrue(model.getCVTermCount() == 0);
    
  }

  /**
   * @throws XMLStreamException 
   * @throws SBMLException 
   * 
   */
  @Test public void cvTermTest2() throws SBMLException, XMLStreamException {

    String docString = new SBMLWriter().writeSBMLToString(doc);
    
    doc = new SBMLReader().readSBMLFromString(docString);
    model = doc.getModel();
    
    assertTrue(doc.getLevel() == 3 && doc.getVersion() == 1);
    assertTrue(model.getLevel() == 3 && model.getVersion() == 1);

    assertTrue(model.getId().equals(TEST_MODEL_NAME));

    assertTrue(model.getCVTermCount() == 1);
    
    CVTerm cvTerm = model.getCVTerm(0);
    
    assertTrue(cvTerm != null);
    assertTrue(cvTerm.getQualifier().equals(CVTerm.Qualifier.BQB_HAS_TAXON));
    assertTrue(cvTerm.getResourceCount() == 1);
    assertTrue(cvTerm.getResourceURI(0).equals(HTTP_IDENTIFIERS_ORG_TAXONOMY_9606));
    
    boolean removed = cvTerm.removeFromParent();
    
    assertTrue(removed == true);
    assertTrue(model.getCVTermCount() == 0);
    
  }

  /**
   * 
   */
  @Test public void cvTermTest3() {

    assertTrue(compartment.getCVTermCount() == 4);
    
    CVTerm cvTerm = compartment.getCVTerm(1);
    
    assertTrue(cvTerm != null);
    assertTrue(cvTerm.getQualifier().equals(CVTerm.Qualifier.BQB_IS_DESCRIBED_BY));
    assertTrue(cvTerm.getResourceCount() == 1);
    
    CVTerm removedCVTerm = compartment.removeCVTerm(1);
    
    assertTrue(removedCVTerm != null);
    assertTrue(removedCVTerm.equals(cvTerm));
    assertTrue(removedCVTerm.hashCode() == cvTerm.hashCode());
    assertTrue(compartment.getCVTermCount() == 3);
    
  }

  /**
   * 
   */
  @Test public void cvTermTest4() {

    assertTrue(compartment.getCVTermCount() == 4);
    
    CVTerm cvTerm = compartment.getCVTerm(2);
    
    assertTrue(cvTerm != null);
    assertTrue(cvTerm.getQualifier().equals(CVTerm.Qualifier.BQB_IS_PROPERTY_OF));
    assertTrue(cvTerm.getResourceCount() == 3);
    assertTrue(cvTerm.getResourceURI(0).equals("toDelete"));
    cvTerm.removeResource(1);
    
    
    boolean removed = compartment.removeCVTerm(cvTerm);
    
    assertTrue(removed == true);
    assertTrue(compartment.getCVTermCount() == 3);
    
  }

  /**
   * 
   */
  @Test public void cvTermTest5() {

    assertTrue(compartment.getCVTermCount() == 4);
    
    try {
      compartment.getCVTerm(-1);
      assertTrue(false);
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    
    try {
      compartment.getCVTerm(4);
      assertTrue(false);
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    
    try {
      compartment.removeCVTerm(-1);
      assertTrue(false);
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }

    try {
      compartment.removeCVTerm(4);
      assertTrue(false);
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }

    assertTrue(compartment.getCVTermCount() == 4);
    
  }

}
