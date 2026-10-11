/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.comp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.swing.tree.TreeNode;

import org.junit.Test;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;
import org.sbml.jsbml.util.filters.Filter;

/**
 * The flattening renames the references of the distrib package: the var, the
 * bounds of spans, the units and the math of the uncertainty parameters of the
 * elements of a submodel.
 */
public class CompFlattenDistribTest {

  private static final String SBML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
    + "<sbml xmlns=\"http://www.sbml.org/sbml/level3/version2/core\" xmlns:comp=\"http://www.sbml.org/sbml/level3/version1/comp/version1\""
    + " xmlns:distrib=\"http://www.sbml.org/sbml/level3/version1/distrib/version1\" level=\"3\" version=\"2\" comp:required=\"true\" distrib:required=\"true\">\n"
    + "  <model id=\"top\">\n"
    + "    <comp:listOfSubmodels><comp:submodel comp:id=\"A\" comp:modelRef=\"sub\"/></comp:listOfSubmodels>\n"
    + "  </model>\n"
    + "  <comp:listOfModelDefinitions>\n"
    + "    <comp:modelDefinition id=\"sub\">\n"
    + "      <listOfUnitDefinitions><unitDefinition id=\"per_s\"><listOfUnits><unit kind=\"second\" exponent=\"-1\" scale=\"0\" multiplier=\"1\"/></listOfUnits></unitDefinition></listOfUnitDefinitions>\n"
    + "      <listOfParameters>\n"
    + "        <parameter id=\"sd\" value=\"0.2\" units=\"per_s\" constant=\"true\"/>\n"
    + "        <parameter id=\"k1\" value=\"1\" units=\"per_s\" constant=\"true\">\n"
    + "          <distrib:listOfUncertainties><distrib:uncertainty>\n"
    + "            <distrib:uncertParameter distrib:type=\"standardDeviation\" distrib:var=\"sd\" distrib:units=\"per_s\"/>\n"
    + "            <distrib:uncertSpan distrib:type=\"range\" distrib:varLower=\"sd\" distrib:varUpper=\"k1\"/>\n"
    + "            <distrib:uncertParameter distrib:type=\"distribution\" distrib:definitionURL=\"http://www.sbml.org/sbml/symbols/distrib/normal\">\n"
    + "              <math xmlns=\"http://www.w3.org/1998/Math/MathML\"><apply><csymbol encoding=\"text\" definitionURL=\"http://www.sbml.org/sbml/symbols/distrib/normal\"> normal </csymbol><cn> 1 </cn><ci> sd </ci></apply></math>\n"
    + "            </distrib:uncertParameter>\n"
    + "          </distrib:uncertainty></distrib:listOfUncertainties>\n"
    + "        </parameter>\n"
    + "      </listOfParameters>\n"
    + "    </comp:modelDefinition>\n"
    + "  </comp:listOfModelDefinitions>\n"
    + "</sbml>\n";

  /** The uncertainty parameters and spans of the model, in document order. */
  @SuppressWarnings("unchecked")
  private static List<SBase> uncertParameters(Model model) {
    return (List<SBase>) (List<? extends TreeNode>) model.filter(new Filter() {
      @Override
      public boolean accepts(Object o) {
        return o instanceof SBase
            && ("uncertParameter".equals(((SBase) o).getElementName()) || "uncertSpan".equals(((SBase) o).getElementName()));
      }
    });
  }

  @Test
  public void flatteningRenamesTheDistribReferences() throws Exception {
    SBMLDocument document = SBMLReader.read(SBML);
    Model flat = new CompFlatteningConverter().flatten(document).getModel();

    assertNotNull(flat.getParameter("A__k1"));
    List<SBase> parameters = uncertParameters(flat);
    assertEquals(3, parameters.size());
    String units = parameters.get(0).writeXMLAttributes().get("distrib:units");
    assertEquals("A__sd", parameters.get(0).writeXMLAttributes().get("distrib:var"));
    assertEquals(flat.getParameter("A__k1").getUnits(), units);
    assertNotNull(flat.getUnitDefinition(units));
    assertEquals("A__sd", parameters.get(1).writeXMLAttributes().get("distrib:varLower"));
    assertEquals("A__k1", parameters.get(1).writeXMLAttributes().get("distrib:varUpper"));
    assertEquals("normal(1, A__sd)", ((MathContainer) parameters.get(2)).getMath().toFormula());
  }
}
