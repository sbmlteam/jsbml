/*
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2020 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.ext.fbc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.ListOfObjectives;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.fbc.Objective.Type;
import org.sbml.jsbml.ext.fbc.converters.CobraToFbcV1Converter;

import junit.framework.Assert;


/**
 * @author Thorsten Tiede
 * @since 1.5
 * @date 18 Mar 2020
 */
public class CobraToFbcV1ConverterTest {
 
  CobraToFbcV1Converter converter;

  private SBMLDocument loadDoc(String type) throws IOException, XMLStreamException {
    SBMLDocument doc;
    switch (type) {
    case "valid":
      doc = SBMLReader.read(""
        + "<?xml version='1.0' encoding='utf-8' standalone='no'?>\n" + 
        "<sbml xmlns=\"http://www.sbml.org/sbml/level2/version5\" level=\"2\" version=\"5\" >\n" + 
        "  <model id=\"fbcV2ToCobraConversion_test\" name=\"Simple test model for conversion from L2 with Cobra Annotation to L3V1 with fbcV1\">\n" + 
        "    <listOfUnitDefinitions>\n" + 
        "      <unitDefinition id=\"volume\" name=\"Volume units\">\n" + 
        "        <listOfUnits>\n" + 
        "          <unit exponent=\"1\" kind=\"litre\" multiplier=\"1\" scale=\"-3\" />\n" + 
        "        </listOfUnits>\n" + 
        "      </unitDefinition>\n" + 
        "      <unitDefinition id=\"substance\" name=\"Substance units\">\n" + 
        "        <listOfUnits>\n" + 
        "          <unit exponent=\"1\" kind=\"mole\" multiplier=\"1\" scale=\"-3\" />\n" + 
        "        </listOfUnits>\n" + 
        "      </unitDefinition>\n" + 
        "      <unitDefinition id=\"mmol_per_gDW_per_hr\" name=\"mmol_per_gDW_per_hr\">\n" + 
        "        <listOfUnits>\n" + 
        "          <unit exponent=\"1\" kind=\"mole\" multiplier=\"1\" scale=\"-3\" />\n" + 
        "          <unit exponent=\"-1\" kind=\"gram\" multiplier=\"1\" scale=\"0\" />\n" + 
        "          <unit exponent=\"-1\" kind=\"second\" multiplier=\"3600\" scale=\"0\" />\n" + 
        "        </listOfUnits>\n" + 
        "      </unitDefinition>\n" + 
        "    </listOfUnitDefinitions>\n" + 
        "    <listOfCompartments>\n" + 
        "      <compartment constant=\"true\" id=\"default\" name=\"default compartment\" size=\"1\" spatialDimensions=\"3\" units=\"volume\" />\n" + 
        "    </listOfCompartments>\n" + 
        "    <listOfSpecies>\n" + 
        "      <species boundaryCondition=\"false\" compartment=\"default\" constant=\"false\" hasOnlySubstanceUnits=\"true\" id=\"C3H7O7P\" initialConcentration=\"1\" name=\"C3H7O7P\" substanceUnits=\"substance\" />\n" + 
        "      <species boundaryCondition=\"false\" compartment=\"default\" constant=\"false\" hasOnlySubstanceUnits=\"true\" id=\"C3H5O6P\" initialConcentration=\"1\" name=\"C3H5O6P\" substanceUnits=\"substance\" />\n" + 
        "      <species boundaryCondition=\"false\" compartment=\"default\" constant=\"false\" hasOnlySubstanceUnits=\"true\" id=\"H2O\" initialConcentration=\"1\" name=\"H2O\" substanceUnits=\"substance\" />\n" + 
        "      <species boundaryCondition=\"false\" compartment=\"default\" constant=\"false\" hasOnlySubstanceUnits=\"true\" id=\"C2HO6P\" initialConcentration=\"1\" name=\"C2HO6P\" substanceUnits=\"substance\" />\n" + 
        "      <species boundaryCondition=\"false\" compartment=\"default\" constant=\"false\" hasOnlySubstanceUnits=\"true\" id=\"CH4\" initialConcentration=\"0\" name=\"CH4\" substanceUnits=\"substance\" />\n" + 
        "    </listOfSpecies>\n" + 
        "    <listOfReactions>\n" + 
        "      <reaction fast=\"false\" id=\"rnR00658\" name=\"2-phospho-D-glycerate hydro-lyase (phosphoenolpyruvate-forming)\" reversible=\"true\">\n" + 
        "        <notes>\n" + 
        "          <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + 
        "            <p>GENE_ASSOCIATION:</p>\n" + 
        "          </body>\n" + 
        "        </notes>\n" + 
        "        <listOfReactants>\n" + 
        "          <speciesReference species=\"C3H7O7P\" />\n" + 
        "        </listOfReactants>\n" + 
        "        <listOfProducts>\n" + 
        "          <speciesReference species=\"C3H5O6P\" />\n" + 
        "          <speciesReference species=\"H2O\" />\n" + 
        "        </listOfProducts>\n" + 
        "        <kineticLaw>\n" + 
        "          <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" + 
        "            <ci>FLUX_VALUE</ci>\n" + 
        "          </math>\n" + 
        "          <listOfParameters>\n" + 
        "            <parameter id=\"FLUX_VALUE\" units=\"mmol_per_gDW_per_hr\" value=\"0\" />\n" + 
        "            <parameter constant=\"true\" id=\"UPPER_BOUND\" units=\"mmol_per_gDW_per_hr\" value=\"100\" />\n" + 
        "            <parameter constant=\"true\" id=\"LOWER_BOUND\" units=\"mmol_per_gDW_per_hr\" value=\"1\" />\n" + 
        "            <parameter id=\"OBJECTIVE_COEFFICIENT\" units=\"mmol_per_gDW_per_hr\" value=\"1\" />\n" + 
        "          </listOfParameters>\n" + 
        "        </kineticLaw>\n" + 
        "      </reaction>\n" + 
        "      <reaction fast=\"false\" id=\"MethReact\" name=\"Fictive Methane reaction\" reversible=\"false\">\n" + 
        "        <notes>\n" + 
        "          <body xmlns=\"http://www.w3.org/1999/xhtml\">\n" + 
        "            <p>GENE_ASSOCIATION:</p>\n" + 
        "          </body>\n" + 
        "        </notes>\n" + 
        "        <listOfReactants>\n" + 
        "          <speciesReference species=\"C3H5O6P\" />\n" + 
        "        </listOfReactants>\n" + 
        "        <listOfProducts>\n" + 
        "          <speciesReference species=\"C2HO6P\" />\n" + 
        "          <speciesReference species=\"CH4\" />\n" + 
        "        </listOfProducts>\n" + 
        "        <kineticLaw>\n" + 
        "          <math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" + 
        "            <ci>FLUX_VALUE</ci>\n" + 
        "          </math>\n" + 
        "          <listOfParameters>\n" + 
        "            <parameter id=\"FLUX_VALUE\" units=\"mmol_per_gDW_per_hr\" value=\"0\" />\n" + 
        "            <parameter constant=\"true\" id=\"UPPER_BOUND\" units=\"mmol_per_gDW_per_hr\" value=\"100\" />\n" + 
        "            <parameter constant=\"true\" id=\"LOWER_BOUND\" units=\"mmol_per_gDW_per_hr\" value=\"1\" />\n" + 
        "            <parameter id=\"OBJECTIVE_COEFFICIENT\" units=\"mmol_per_gDW_per_hr\" value=\"0\" />\n" + 
        "          </listOfParameters>\n" + 
        "        </kineticLaw>\n" + 
        "      </reaction>\n" + 
        "    </listOfReactions>\n" + 
        "  </model>\n" + 
        "</sbml>\n");
      //CobraToFbcV1ConverterTest.class.getResourceAsStream("../../xml/test/data/fbc/Model_Cobra_Valid.xml"));//read("../../xml/test/data/fbc/Model_Cobra_Valid.xml");
      break;
    default:
      doc = new SBMLDocument();
      break;
    }
    return doc;
  }
  
  @Test
  public void convertValidDocumentTest() {
  
   try {
     SBMLDocument doc = this.loadDoc("valid");
     converter = new CobraToFbcV1Converter();
     converter.convert(doc);
     assertNotNull(doc);
     Model model = doc.getModel();
     FBCModelPlugin fbcModelPlugin = (FBCModelPlugin) model.getExtension(FBCConstants.shortLabel);
     assertNotNull(fbcModelPlugin);
     assertEquals("Fbc Package Version should be 1", 1, fbcModelPlugin.getPackageVersion());
     assertTrue("ListOfObjectives not set", fbcModelPlugin.isSetListOfObjectives());
     ListOfObjectives listOfObjectives = fbcModelPlugin.getListOfObjectives();
     assertNotNull("ListOfObjectives should not be null", listOfObjectives);
     String activeObjectiveString = listOfObjectives.getActiveObjective();
     assertNotNull("There should be an active objective", activeObjectiveString);
     assertEquals("ActiveObjective should be named \"obj\"", "obj", activeObjectiveString);
     Objective activeObjective = listOfObjectives.get(activeObjectiveString);
     assertNotNull("Active objective should not be null", activeObjective);
     assertEquals("ActiveObjective Id should be \"obj\"", "obj", activeObjective.getId());
     Type activeObjectiveType = activeObjective.getType();
     assertNotNull("Type of activeObjective should not be null", activeObjectiveType);
     activeObjective.getListOfFluxObjectives();
     
   } catch (SBMLException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     fail();
   } catch (XMLStreamException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     fail();
   } catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     fail();
   }
   Assert.assertTrue(true);
  }
}
