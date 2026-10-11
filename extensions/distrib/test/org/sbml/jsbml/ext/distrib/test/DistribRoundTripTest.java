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
package org.sbml.jsbml.ext.distrib.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.distrib.UncertParameter;
import org.sbml.jsbml.ext.distrib.UncertSpan;
import org.sbml.jsbml.ext.distrib.Uncertainty;
import org.sbml.jsbml.util.filters.Filter;

/**
 * Writing and reading, and cloning a model keep all uncertainties: the uncertainty
 * parameters and spans with all attributes, their math and nested parameters.
 */
public class DistribRoundTripTest {

  private static final Filter SBASES = new Filter() {
    @Override
    public boolean accepts(Object o) {
      return o instanceof SBase;
    }
  };

  /** One line per uncertainty and uncertainty parameter of the document, in document order. */
  private static List<String> describe(SBMLDocument document) {
    List<String> lines = new ArrayList<String>();
    for (TreeNode node : document.getModel().filter(SBASES)) {
      SBase sbase = (SBase) node;
      if (sbase.getExtension(DistribConstants.shortLabel) instanceof DistribSBasePlugin) {
        DistribSBasePlugin plugin = (DistribSBasePlugin) sbase.getExtension(DistribConstants.shortLabel);
        for (Uncertainty uncertainty : plugin.getListOfUncertainties()) {
          lines.add(sbase.getElementName() + " uncertainty id=" + uncertainty.getId() + " name=" + uncertainty.getName());
          for (UncertParameter parameter : uncertainty.getListOfUncertParameters()) {
            describe(parameter, "  ", lines);
          }
        }
      }
    }
    return lines;
  }

  private static void describe(UncertParameter p, String indent, List<String> lines) {
    StringBuilder line = new StringBuilder(indent).append(p.getElementName()).append(' ').append(p.getType());
    line.append(" value=").append(p.isSetValue() ? p.getValue() : null);
    line.append(" var=").append(p.isSetVar() ? p.getVar() : null);
    line.append(" units=").append(p.isSetUnits() ? p.getUnits() : null);
    line.append(" definitionURL=").append(p.isSetDefinitionURL() ? p.getDefinitionURL() : null);
    line.append(" name=").append(p.getName());
    line.append(" math=").append(p.isSetMath() ? p.getMath().toFormula() : null);
    if (p instanceof UncertSpan) {
      UncertSpan span = (UncertSpan) p;
      line.append(" lower=").append(span.isSetValueLower() ? span.getValueLower() : span.getVarLower());
      line.append(" upper=").append(span.isSetValueUpper() ? span.getValueUpper() : span.getVarUpper());
    }
    lines.add(line.toString());
    for (UncertParameter nested : p.getListOfUncertParameters()) {
      describe(nested, indent + "  ", lines);
    }
  }

  @Test
  public void writingAndReadingKeepsAllUncertainties() throws Exception {
    SBMLDocument document = SBMLReader.read(DistribRoundTripTest.class.getResourceAsStream("data/distrib_uncertainties.xml"));
    SBMLDocument written = SBMLReader.read(new SBMLWriter().writeSBMLToString(document));

    List<String> expected = describe(document);
    // 10 uncertainties with 16 uncertainty parameters and spans
    assertEquals(26, expected.size());
    assertEquals(expected, describe(written));
  }

  @Test
  public void cloningKeepsAllUncertainties() throws Exception {
    SBMLDocument document = SBMLReader.read(DistribRoundTripTest.class.getResourceAsStream("data/distrib_uncertainties.xml"));

    assertEquals(describe(document), describe(document.clone()));
  }

  @Test
  public void cloningKeepsTheVarsOfASpan() {
    UncertSpan span = new UncertSpan(3, 2);
    span.setType(UncertParameter.Type.range);
    span.setVarLower("lower");
    span.setVarUpper("upper");

    UncertSpan clone = span.clone();

    assertEquals("lower", clone.getVarLower());
    assertEquals("upper", clone.getVarUpper());
  }
}
