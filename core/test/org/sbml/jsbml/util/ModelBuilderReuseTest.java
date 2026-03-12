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

package org.sbml.jsbml.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;

/**
 * Tests for the reuse and clash-detection behaviour in {@link ModelBuilder}.
 */
public class ModelBuilderReuseTest {

  /**
   * If a compartment with the given id already exists, buildCompartment should
   * return it unchanged and not overwrite its attributes.
   */
  @Test
  public void reusesExistingCompartmentWithoutOverwritingAttributes() {
    SBMLDocument doc = new SBMLDocument(3, 1);
    Model model = doc.createModel("m");

    Compartment original = model.createCompartment("c1");
    original.setName("original");
    original.setSpatialDimensions(3d);
    original.setSize(1.23);
    original.setUnits("litre");
    original.setConstant(false);

    ModelBuilder builder = new ModelBuilder(doc);

    // Call with different arguments – these should NOT be applied,
    // because the compartment already exists.
    Compartment result = builder.buildCompartment("c1", true, "newName",
        1d, 9.99, "otherUnits");

    assertSame(original, result);
    assertEquals("original", result.getName());
    assertEquals(3d, result.getSpatialDimensions(), 0.0);
    assertEquals(1.23, result.getSize(), 0.0);
    assertEquals("litre", result.getUnits());
    // remains false, not overwritten by the 'true' passed in
    assertEquals(false, result.getConstant());
  }

  /**
   * If an element with the same id exists but is of a different type,
   * ModelBuilder should throw an IllegalArgumentException instead of
   * silently creating a new element.
   */
  @Test(expected = IllegalArgumentException.class)
  public void throwsIfIdIsUsedByDifferentType() {
    SBMLDocument doc = new SBMLDocument(3, 1);
    Model model = doc.createModel("m");

    // Create a species with id 'shared'
    Species species = model.createSpecies("shared");
    species.setCompartment("c");
    species.setInitialConcentration(1.0);

    ModelBuilder builder = new ModelBuilder(doc);

    // Attempt to create a compartment with the same id.
    // Our new implementation should detect the clash via findNamedSBase
    // and throw an IllegalArgumentException.
    builder.buildCompartment("shared", true, "compartment", 3d, 1.0, "litre");
  }
}