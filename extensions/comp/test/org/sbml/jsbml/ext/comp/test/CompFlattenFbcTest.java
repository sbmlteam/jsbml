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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;
import org.sbml.jsbml.util.filters.Filter;

/**
 * The flattening renames the references of the user defined constraints of
 * fbc version 3 (the lowerBound and upperBound of a constraint, the
 * coefficient, variable and variable2 of its components) of the elements of
 * a submodel, but keeps the coefficient of a flux objective, which is a
 * number.
 */
public class CompFlattenFbcTest {

  private static final String SBML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
    + "<sbml xmlns=\"http://www.sbml.org/sbml/level3/version1/core\" xmlns:comp=\"http://www.sbml.org/sbml/level3/version1/comp/version1\""
    + " xmlns:fbc=\"http://www.sbml.org/sbml/level3/version1/fbc/version3\" level=\"3\" version=\"1\" comp:required=\"true\" fbc:required=\"false\">\n"
    + "  <model id=\"top\" fbc:strict=\"false\">\n"
    + "    <comp:listOfSubmodels><comp:submodel comp:id=\"A\" comp:modelRef=\"sub\"/></comp:listOfSubmodels>\n"
    + "  </model>\n"
    + "  <comp:listOfModelDefinitions>\n"
    + "    <comp:modelDefinition id=\"sub\" fbc:strict=\"false\">\n"
    + "      <listOfCompartments><compartment id=\"c\" size=\"1\" constant=\"true\"/></listOfCompartments>\n"
    + "      <listOfSpecies>\n"
    + "        <species id=\"s1\" compartment=\"c\" hasOnlySubstanceUnits=\"false\" boundaryCondition=\"false\" constant=\"false\"/>\n"
    + "        <species id=\"s2\" compartment=\"c\" hasOnlySubstanceUnits=\"false\" boundaryCondition=\"false\" constant=\"false\"/>\n"
    + "      </listOfSpecies>\n"
    + "      <listOfParameters>\n"
    + "        <parameter id=\"lb\" value=\"0\" constant=\"true\"/>\n"
    + "        <parameter id=\"ub\" value=\"1000\" constant=\"true\"/>\n"
    + "        <parameter id=\"coef\" value=\"2\" constant=\"true\"/>\n"
    + "        <parameter id=\"p\" constant=\"false\"/>\n"
    + "      </listOfParameters>\n"
    + "      <listOfReactions>\n"
    + "        <reaction id=\"r1\" reversible=\"false\" fast=\"false\" fbc:lowerFluxBound=\"lb\" fbc:upperFluxBound=\"ub\">\n"
    + "          <listOfReactants><speciesReference species=\"s1\" stoichiometry=\"1\" constant=\"true\"/></listOfReactants>\n"
    + "          <listOfProducts><speciesReference species=\"s2\" stoichiometry=\"1\" constant=\"true\"/></listOfProducts>\n"
    + "        </reaction>\n"
    + "        <reaction id=\"r2\" reversible=\"false\" fast=\"false\" fbc:lowerFluxBound=\"lb\" fbc:upperFluxBound=\"ub\">\n"
    + "          <listOfReactants><speciesReference species=\"s2\" stoichiometry=\"1\" constant=\"true\"/></listOfReactants>\n"
    + "        </reaction>\n"
    + "      </listOfReactions>\n"
    + "      <fbc:listOfObjectives fbc:activeObjective=\"obj\">\n"
    + "        <fbc:objective fbc:id=\"obj\" fbc:type=\"maximize\">\n"
    + "          <fbc:listOfFluxObjectives>\n"
    + "            <fbc:fluxObjective fbc:reaction=\"r2\" fbc:coefficient=\"3\" fbc:variableType=\"linear\"/>\n"
    + "          </fbc:listOfFluxObjectives>\n"
    + "        </fbc:objective>\n"
    + "      </fbc:listOfObjectives>\n"
    + "      <fbc:listOfUserDefinedConstraints>\n"
    + "        <fbc:userDefinedConstraint fbc:id=\"uc\" fbc:lowerBound=\"lb\" fbc:upperBound=\"ub\">\n"
    + "          <fbc:listOfUserDefinedConstraintComponents>\n"
    + "            <fbc:userDefinedConstraintComponent fbc:id=\"uc_c1\" fbc:coefficient=\"coef\" fbc:variable=\"r1\" fbc:variableType=\"linear\"/>\n"
    + "            <fbc:userDefinedConstraintComponent fbc:id=\"uc_c2\" fbc:coefficient=\"coef\" fbc:variable=\"p\" fbc:variable2=\"r2\" fbc:variableType=\"quadratic\"/>\n"
    + "          </fbc:listOfUserDefinedConstraintComponents>\n"
    + "        </fbc:userDefinedConstraint>\n"
    + "      </fbc:listOfUserDefinedConstraints>\n"
    + "    </comp:modelDefinition>\n"
    + "  </comp:listOfModelDefinitions>\n"
    + "</sbml>\n";

  /** The fbc elements of the model with the given element name, in document order. */
  @SuppressWarnings("unchecked")
  private static List<SBase> elements(Model model, final String elementName) {
    return (List<SBase>) (List<? extends TreeNode>) model.filter(new Filter() {
      @Override
      public boolean accepts(Object o) {
        return o instanceof SBase && "fbc".equals(((SBase) o).getPackageName())
            && elementName.equals(((SBase) o).getElementName());
      }
    });
  }

  @Test
  public void flatteningRenamesTheUserDefinedConstraintReferences() throws Exception {
    SBMLDocument document = SBMLReader.read(SBML);
    Model flat = new CompFlatteningConverter().flatten(document).getModel();

    assertNotNull(flat.getReaction("A__r1"));
    assertNotNull(flat.getParameter("A__coef"));

    List<SBase> constraints = elements(flat, "userDefinedConstraint");
    assertEquals(1, constraints.size());
    Map<String, String> constraint = constraints.get(0).writeXMLAttributes();
    assertEquals("A__uc", constraint.get("fbc:id"));
    assertEquals("A__lb", constraint.get("fbc:lowerBound"));
    assertEquals("A__ub", constraint.get("fbc:upperBound"));

    List<SBase> components = elements(flat, "userDefinedConstraintComponent");
    assertEquals(2, components.size());
    Map<String, String> linear = components.get(0).writeXMLAttributes();
    assertEquals("A__uc_c1", linear.get("fbc:id"));
    assertEquals("A__coef", linear.get("fbc:coefficient"));
    assertEquals("A__r1", linear.get("fbc:variable"));
    assertEquals("linear", linear.get("fbc:variableType"));
    Map<String, String> quadratic = components.get(1).writeXMLAttributes();
    assertEquals("A__coef", quadratic.get("fbc:coefficient"));
    assertEquals("A__p", quadratic.get("fbc:variable"));
    assertEquals("A__r2", quadratic.get("fbc:variable2"));
    assertEquals("quadratic", quadratic.get("fbc:variableType"));

    // the coefficient of a flux objective is a number, not a reference
    List<SBase> fluxObjectives = elements(flat, "fluxObjective");
    assertEquals(1, fluxObjectives.size());
    Map<String, String> fluxObjective = fluxObjectives.get(0).writeXMLAttributes();
    assertEquals("A__r2", fluxObjective.get("fbc:reaction"));
    assertEquals("3", fluxObjective.get("fbc:coefficient"));
    assertEquals("linear", fluxObjective.get("fbc:variableType"));
  }
}
