/*
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
package org.sbml.jsbml.xml.test;

import javax.xml.stream.XMLStreamException;

import junit.framework.Assert;

import org.junit.Test;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;

/**
 * Tests the nested CVTerm support. This was introduced in SBML L2V5 and L3V2.
 * 
 * @author Nicolas Rodriguez
 *
 */
public class NestedCVTermTests {

  
  /**
   * 
   */
  @Test public void testNestedCTerm()  {
    try {
      SBMLDocument doc = new SBMLDocument(2, 5);
      Model m = doc.createModel("test_nested_annotations");
      Species s1 = m.createSpecies("S1");
      Species s2 = m.createSpecies("S2");
      
      // adding first level of CVTerm to s1
      s1.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/uniprot/P12444"));
      s1.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_OCCURS_IN, "http://identifiers.org/go/GO:0005764"));
      
      // adding second level of CVTerm to s1
      s1.getCVTerm(1).addNestedCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_DESCRIBED_BY, "http://identifiers.org/pubmed/1111111"));
      s1.getCVTerm(1).addNestedCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_DESCRIBED_BY, "http://identifiers.org/eco/ECO:0000004"));
      
      // adding third level of CVTerm to s1
      s1.getCVTerm(1).getNestedCVTerm(0).addNestedCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_DESCRIBED_BY, "http://identifiers.org/eco/ECO:0000005"));

      // adding first level of CVTerm to s2
      s2.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/uniprot/P12345"));
      s2.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_VERSION_OF, "http://identifiers.org/uniprot/P04551"));
      
      // adding second level of CVTerm to s2
      s2.getCVTerm(0).addNestedCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_DESCRIBED_BY, "http://identifiers.org/eco/ECO:0000004"));
      s2.getCVTerm(1).addNestedCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_VERSION_OF, "http://identifiers.org/psimod/MOD:00047"));
      
      // testing s1
      Assert.assertTrue(s1.getNumCVTerms() == 2);
      Assert.assertTrue(s1.getCVTerm(0).getNestedCVTermCount() == 0);
      Assert.assertTrue(s1.getCVTerm(1).getNestedCVTermCount() == 2);
      Assert.assertTrue(s1.getCVTerm(1).getNestedCVTerm(0).getNumNestedCVTerms() == 1);
      Assert.assertTrue(s1.getCVTerm(1).getNestedCVTerm(0).getNestedCVTerm(0).getResourceURI(0).equals("http://identifiers.org/eco/ECO:0000005"));

      // testing s2
      Assert.assertTrue(s2.getNumCVTerms() == 2);
      Assert.assertTrue(s2.getCVTerm(0).getNestedCVTermCount() == 1);
      Assert.assertTrue(s2.getCVTerm(1).getNestedCVTermCount() == 1);
      Assert.assertTrue(s2.getCVTerm(1).getNestedCVTerm(0).getResourceURI(0).equals("http://identifiers.org/psimod/MOD:00047"));
      
      // writing the SBMLDocument
      String sbmlDocStr = new SBMLWriter().writeSBMLToString(doc);
      
      // reading the SBMLDocument after writing
      doc = new SBMLReader().readSBMLFromString(sbmlDocStr);
      m = doc.getModel();
      s1 = m.getSpecies(0);
      s2 = m.getSpecies(1);
      
      // testing s1
      Assert.assertTrue(s1.getNumCVTerms() == 2);
      Assert.assertTrue(s1.getCVTerm(0).getNestedCVTermCount() == 0);
      Assert.assertTrue(s1.getCVTerm(1).getNestedCVTermCount() == 2);
      Assert.assertTrue(s1.getCVTerm(1).getNestedCVTerm(0).getNumNestedCVTerms() == 1);
      Assert.assertTrue(s1.getCVTerm(1).getNestedCVTerm(0).getNestedCVTerm(0).getResourceURI(0).equals("http://identifiers.org/eco/ECO:0000005"));
      
      // testing s2
      Assert.assertTrue(s2.getNumCVTerms() == 2);
      Assert.assertTrue(s2.getCVTerm(0).getNestedCVTermCount() == 1);
      Assert.assertTrue(s2.getCVTerm(1).getNestedCVTermCount() == 1);
      Assert.assertTrue(s2.getCVTerm(1).getNestedCVTerm(0).getResourceURI(0).equals("http://identifiers.org/psimod/MOD:00047"));
      
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } 
  }

}
