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

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class HashCodeTest {

  /**
   * @throws ParseException
   * 
   */
  @Test public void checkHashCode() throws ParseException {
    SBMLDocument doc1 = new SBMLDocument(3, 1);
    Model model = doc1.createModel("test_model");
    Compartment c = model.createCompartment("c1");
    Species s = model.createSpecies("s1", c);
    s.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/kegg.compound/C00001"));
    Rule r = model.createAlgebraicRule();
    r.setMath(ASTNode.parseFormula("sin(3) + 1"));

    SBMLDocument doc2 = doc1.clone();
    try {
      (new SBMLWriter()).write(doc1, System.out);
      System.out.println("\n==================");
      (new SBMLWriter()).write(doc2, System.out);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
    assertTrue(doc1.hashCode() == doc2.hashCode());
    assertTrue(model.hashCode() != doc2.hashCode());
    assertTrue(s.getCVTerm(0).equals(new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/kegg.compound/C00001")));
    assertTrue(s.getCVTerm(0).hashCode() == (new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/kegg.compound/C00001")).hashCode());
    assertTrue(doc1.equals(doc2));
    assertTrue(doc2.equals(doc1));
  }

}
