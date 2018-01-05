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
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.xml.stax.SBMLReader;
import org.xml.sax.SAXException;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class SBML_L2V1Test {

  /**
   * 
   */
  @BeforeClass public static void initialSetUp() {}

  /**
   * 
   */
  @Before public void setUp() {}

  /**
   * @throws XMLStreamException
   * @throws InvalidPropertiesFormatException
   * @throws IOException
   * @throws ClassNotFoundException
   * 
   */
  @SuppressWarnings("deprecation")
  @Test public void read1() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    InputStream fileStream = SBML_L2V1Test.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000025.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);
    Model model = doc.getModel();

    assertTrue(doc.getLevel() == 2 && doc.getVersion() == 1);

    assertTrue(model.getLevel() == 2 && model.getVersion() == 1);

    assertTrue(model.getId().equals("Smolen2002"));
    assertTrue(model.getName().equals("Smolen2002_CircClock"));

    /*
     * <dcterms:created rdf:parseType="Resource">
          <dcterms:W3CDTF>2005-06-29T11:01:49Z</dcterms:W3CDTF>
        </dcterms:created>
        <dcterms:modified rdf:parseType="Resource">
          <dcterms:W3CDTF>2008-08-21T11:47:14Z</dcterms:W3CDTF>
        </dcterms:modified>
     * 
     */
    // System.out.println("Created on : " + model.getHistory().getCreatedDate());
    // System.out.println("Modified on: " + model.getHistory().getModifiedDate());

    // asserTrue(model.getHistory().getCreatedDate());

    assertTrue(model.getUnitDefinition("substance").getName().equals("nanomole (new default)"));

    Species dClk = model.getSpecies("dClk");

    assertTrue(dClk.getCompartment().equals("CELL"));
    dClk.unsetCompartment();
    assertTrue(dClk.getCompartment().length() == 0);

    assertTrue(dClk != null);

    assertTrue(dClk.getName().length() == 0);
    assertTrue(dClk.getCVTermCount() == 1);

    assertTrue(dClk.getInitialAmount() == Double.parseDouble("1e-16"));

    Species dClkF = model.getSpecies("dClkF");

    assertTrue(dClkF != null);

    assertTrue(dClkF.getName().equals("free dClk"));

    Reaction rdClk = model.getReaction("rdClk");

    assertTrue(rdClk != null);

    assertTrue(rdClk.getName().equals("dClk production"));
    assertTrue(rdClk.getMetaId().equals("metaid_0000012"));
    assertTrue(rdClk.getListOfReactants().size() == 1);
    assertTrue(rdClk.getListOfProducts().size() == 1);
    assertTrue(rdClk.getListOfModifiers().size() == 1);

    assertTrue(rdClk.getListOfReactants().get(0).getSpecies().equals("EmptySet"));
    assertTrue(rdClk.getListOfProducts().get(0).getSpecies().equals("dClk"));
    assertTrue(rdClk.getListOfModifiers().get(0).getSpecies().equals("dClkF"));

    KineticLaw rdClkKL = rdClk.getKineticLaw();

    assertTrue(rdClkKL.getListOfParameters().size() == 3);
    assertTrue(rdClkKL.getListOfParameters().get(2).getId().equals("parameter_0000009"));
    assertTrue(rdClkKL.getListOfParameters().get(2).getName().equals("tau2"));
    assertTrue(rdClkKL.getListOfParameters().get(2).getValue() == 10);

    Event event = model.getEvent(0);

    assertTrue(event.getMetaId().equals("metaid_0000015"));
    assertTrue(event.getEventAssignmentCount() == 1);
    assertTrue(event.getEventAssignment(0).getVariable().equals("dClkF"));

    System.out.println("First Trigger = " + event.getTrigger().getFormula());

    assertTrue(event.getTrigger().getMathMLString().contains("<math"));
    assertTrue(!event.getTrigger().getMathMLString().contains("athML<apply"));

  }

  /**
   * @throws XMLStreamException
   * @throws InvalidPropertiesFormatException
   * @throws IOException
   * @throws ClassNotFoundException
   * 
   */
  @Test public void read2() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    InputStream fileStream = SBML_L2V1Test.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000227.xml");
    @SuppressWarnings("unused")
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);

    // TODO - do some tests on this model !!
  }

  /**
   * @throws XMLStreamException
   * @throws InvalidPropertiesFormatException
   * @throws IOException
   * @throws ClassNotFoundException
   * 
   */
  @Test public void read3() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    InputStream fileStream = SBML_L2V1Test.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000228.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);
    Model model = doc.getModel();

    assertTrue(doc.getLevel() == 2 && doc.getVersion() == 4);

    assertTrue(model.getId().equals(""));
    assertTrue(model.getName().equals("Swat2004_Mammalian_G1_S_Transition"));

    Compartment cell = model.getCompartment(0);

    assertTrue(cell.getSize() == 1);

    assertTrue(model.getListOfUnitDefinitions().size() == 3);
    assertTrue(model.getListOfUnitDefinitions().get(1).getMetaId().equals("metaid_0000004"));

    Species pRBp = model.getSpecies("pRBp");
    pRBp.setHasOnlySubstanceUnits(false);

    assertTrue(pRBp != null);
    System.out.println("pRBp notes: " + pRBp.getNotesString()); // namespace lost, should probably here.

    // TODO: add more complex test for Notes !! assertTrue(pRBp.getNotesString().contains(JSBML.URI_XHTML_DEFINITION));

    System.out.println("pRBp annotation: " + pRBp.getAnnotation().getNonRDFannotation().toXMLString());
    System.out.println("pRBp annotation: " + pRBp.getCVTerm(0).toString());

    assertTrue(model.getListOfParameters().size() == 40);

    //org.sbml.libsbml.SBMLReader libSBMlReader = new org.sbml.libsbml.SBMLReader();
    //org.sbml.libsbml.SBMLDocument libsbmlDoc = libSBMlReader.readSBML(fileName);
    //org.sbml.jsbml.xml.libsbml.SBMLReader libSBMLAdapterReader = new	org.sbml.jsbml.xml.libsbml.LibSBMLReader();

    //Model libsbmlAdapterModel = libSBMLAdapterReader.readModel(libsbmlDoc.getModel());

    // System.out.println("nb global parameters = " + libsbmlDoc.getModel().getParameters());

    Parameter J18 = model.getParameter("J18");

    assertTrue(J18 != null);
    assertTrue(J18.getValue() == 0.6);

    Reaction pRB_synthesis = model.getReaction("pRB_synthesis");

    assertTrue(pRB_synthesis != null);

    System.out.println("pRB_synthesis additional annotation: '" + pRB_synthesis.getAnnotation().getNonRDFannotation().toXMLString() + "'");

    assertTrue(pRB_synthesis.getAnnotation().getNonRDFannotationAsString().trim().contains("<jigcell:ratelaw jigcell:name=\"Local\""));

    assertTrue(pRB_synthesis.getCVTerm(0).getResourceURI(0).equals("urn:miriam:obo.go:GO%3A0006412"));
    // GO:3A0006412

  }

  /**
   * @throws XMLStreamException
   * @throws InvalidPropertiesFormatException
   * @throws IOException
   * @throws ClassNotFoundException
   * 
   */
  @Test public void read4() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    InputStream fileStream = SBML_L2V1Test.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v4/BIOMD0000000229.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);
    Model model = doc.getModel();

    assertTrue(doc.getLevel() == 2 && doc.getVersion() == 4);

    assertTrue(model.getId().equals("Ma2002_cAMP_oscillations"));
    assertTrue(model.getName().equals("Ma2202_cAMP_oscillations"));

    System.out.println(" Create date, read: " + model.getHistory().getCreatedDate() + ", from file: 2009-08-18T15:45:28Z");
    System.out.println(" Create date, read: " + model.getHistory().getModifiedDate() + ", from file: 2009-08-25T14:48:18Z");

    assertTrue(model.getHistory().getCreator(0).getGivenName().equals("Vijayalakshmi"));
    assertTrue(model.getHistory().getCreator(1).getGivenName().equals("Lan"));
    assertTrue(model.getHistory().getCreator(1).getEmail().equals("lma@jhu.edu"));
    assertTrue(model.getHistory().getCreator(0).getOrganisation().equals("EMBL-EBI"));
    assertTrue(model.getCVTermCount() == 5);

    Species erk2 = model.getSpecies("ERK2");

    assertTrue(erk2 != null);
    assertTrue(erk2.getSBOTermID().equals("SBO:0000014"));
    assertTrue(erk2.getSBOTerm() == 14);
    assertTrue(erk2.isSetInitialAmount() == false);
    assertTrue(erk2.isSetInitialConcentration() == true);
    assertTrue(erk2.getInitialConcentration() == 1.13);
    assertTrue(erk2.getCVTerm(0).getResourceCount() == 2);
    assertTrue(erk2.getCompartment().equals("compartment"));
    assertTrue(erk2.getCompartmentInstance().getId().equals("compartment"));
  }


  /**
   * @throws XMLStreamException
   * @throws InvalidPropertiesFormatException
   * @throws IOException
   * @throws ClassNotFoundException
   * 
   */
  @Test public void read5() throws XMLStreamException, InvalidPropertiesFormatException, IOException, ClassNotFoundException {
    InputStream fileStream = SBML_L2V1Test.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v3/BIOMD0000000191.xml");
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);
    Model model = doc.getModel();

    assertTrue(doc.getLevel() == 2 && doc.getVersion() == 3);

    assertTrue(model.getUnitDefinitionCount() == 4);

    UnitDefinition microM = model.getUnitDefinition(2);
    UnitDefinition microMById = model.getUnitDefinition("microM");

    assertTrue(microM.equals(microMById));

    assertTrue(microM.getUnitCount() == 2);
    assertTrue(microM.getName().equals("microM"));
    assertTrue(microM.getUnit(0).getScale() == -6);
    assertTrue(microM.getUnit(0).getKind().getName().equals("mole"));

    int nbSpecies = model.getListOfSpecies().filterList(new Filter() {

      /*
       * (non-Javadoc)
       * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
       */
      @Override
      public boolean accepts(Object o) {
        if (o instanceof Species) {
          return true;
        }

        return false;
      }
    }).size();

    assertTrue(nbSpecies == model.getSpeciesCount());
  }

  /**
   *  Example that check that an exception is correctly launched.
   */
  @Test(expected= IndexOutOfBoundsException.class) public void empty() {
    new ArrayList<Object>().get(0);
  }

  /**
   * @throws XMLStreamException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws InvalidPropertiesFormatException
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws SBMLException
   * @throws SAXException
   * 
   */
  @Test public void write1() throws XMLStreamException, InstantiationException, IllegalAccessException, InvalidPropertiesFormatException, IOException, ClassNotFoundException, SBMLException, SAXException{
    InputStream fileStream = SBML_L2V1Test.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000025.xml");
    @SuppressWarnings("unused")
    SBMLDocument doc = new SBMLReader().readSBMLFromStream(fileStream);

    //String resultFile = new SBMLWriter().writeSBMLToString(doc);

    // TODO - re-read the file and do some test on it
  }
}
