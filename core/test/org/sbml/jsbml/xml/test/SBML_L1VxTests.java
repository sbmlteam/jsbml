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

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * Tests reading SBML Level 1 version 1 or 2 files.
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class SBML_L1VxTests {

  /**
   * 
   */
  @Before public void setUp() {
  }

  /**
   * 
   * @throws XMLStreamException
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws InvalidPropertiesFormatException
   */
  @SuppressWarnings("deprecation")
  @Test public void readL1V2Branch() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    InputStream fileStream = SBML_L1VxTests.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/libsbml-test-data/l1v2-branch.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);

    Model model = doc.getModel();

    assertTrue(doc.getLevel() == 1 && doc.getVersion() == 2);

    assertTrue(model.getLevel() == 1 && model.getVersion() == 2);

    // assertTrue(model.getId().equals("")); // TODO: document. Different behavior than libsbml, we set the id as the name for SBML level 1 models.
    assertTrue(model.getId().equals("Branch"));
    assertTrue(model.getName().equals("Branch"));

    Species s1 = model.getSpecies("S1");

    assertTrue(s1 != null);

    assertTrue(s1.getName().equals("S1"));
    assertTrue(s1.getId().equals("S1")); // changed, was assertTrue(s1.getId().equals("")); cf comment above.
    assertTrue(s1.getCVTermCount() == 0);

    assertTrue(s1.getInitialAmount() == 0);
    assertTrue(s1.getBoundaryCondition() == false);

    Species x1 = model.getSpecies("X1");

    assertTrue(x1 != null);

    assertTrue(x1.getName().equals("X1"));
    assertTrue(x1.getInitialAmount() == 0);
    assertTrue(x1.getBoundaryCondition() == true);


    Reaction r1 = model.getReaction(0);

    assertTrue(r1 != null);

    assertTrue(r1.getName().equals("reaction_1"));
    assertTrue(r1.getListOfReactants().size() == 1);
    assertTrue(r1.getListOfProducts().size() == 1);
    assertTrue(r1.getNumModifiers() == 0);
    assertTrue(r1.getReversible() == false);

    assertTrue(r1.getListOfReactants().get(0).getSpecies().equals("X0"));
    assertTrue(r1.getListOfProducts().get(0).getSpecies().equals("S1"));

    KineticLaw rdClkKL = r1.getKineticLaw();

    assertTrue(rdClkKL != null);
    assertTrue(rdClkKL.getListOfLocalParameters().size() == 1);
    assertTrue(rdClkKL.getListOfLocalParameters().get(0).getName().equals("k1"));

    System.out.println("L1V2 formula = " + rdClkKL.getFormula());
  }

  /**
   * 
   * @throws XMLStreamException
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws InvalidPropertiesFormatException
   */
  @Test public void readL1V1Units() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    InputStream fileStream = SBML_L1VxTests.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/libsbml-test-data/l1v1-units.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);

    Model model = doc.getModel();

    assertTrue(doc.getLevel() == 1 && doc.getVersion() == 1);

    assertTrue(model.getLevel() == 1 && model.getVersion() == 1);

    assertTrue(model.getId().equals(""));
    assertTrue(model.getName().equals(""));

    Species s1 = model.getSpecies("s1");

    assertTrue(s1 != null);

    assertTrue(s1.getName().equals("s1"));
    assertTrue(s1.getId().equals("s1")); // changed, was:  assertTrue(s1.getId().equals(""));
    assertTrue(s1.getCVTermCount() == 0);

    assertTrue(s1.getInitialAmount() == 1);


    UnitDefinition mls = model.getUnitDefinition(1);

    assertTrue(mls != null);
    assertTrue(mls.getUnitCount() == 3);
    assertTrue(mls.getName().equals("mls"));
    assertTrue(mls.getUnit(0).getScale() == -3);
    assertTrue(mls.getUnit(0).getKind().getName().equals("mole"));

    assertTrue(mls.getUnit(2).getExponent() == -1);
    assertTrue(mls.getUnit(2).getKind().getName().equals("second"));
    assertTrue(mls.getUnit(2).getKind().equals(Kind.SECOND));

    Parameter vm = model.getParameter(0);

    assertTrue(vm != null);
    assertTrue(vm.getUnits().equals("mls"));

  }

  /**
   * 
   * @throws XMLStreamException
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws InvalidPropertiesFormatException
   */
  @Test public void readL1V1Rules() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {

    InputStream fileStream = SBML_L1VxTests.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/libsbml-test-data/l1v1-rules.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);

    Model model = doc.getModel();

    assertTrue(doc.getLevel() == 1 && doc.getVersion() == 1);

    assertTrue(model.getLevel() == 1 && model.getVersion() == 1);

    assertTrue(model.getId().equals(""));
    assertTrue(model.getName().equals(""));

    int nbRateRules = model.getListOfRules().filterList(new Filter() {

      /*
       * (non-Javadoc)
       * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
       */
      @Override
      public boolean accepts(Object o) {
        if (o instanceof RateRule) {
          return true;
        }

        return false;
      }
    }).size();

    int nbAssignmentRules = model.getListOfRules().filterList(new Filter() {

      /*
       * (non-Javadoc)
       * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
       */
      @Override
      public boolean accepts(Object o) {
        if (o instanceof AssignmentRule) {
          return true;
        }

        return false;
      }
    }).size();

    assertTrue(model.getListOfRules().size() == 4);
    assertTrue(nbRateRules == 1);
    assertTrue(nbAssignmentRules == 3);
  }

}
