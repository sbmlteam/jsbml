/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018  jointly by the following organizations:
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

import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.ModelBuilder;


/**
 * @author Andreas Dr&auml;ger
 * @since 1.2
 */
public class SpeciesDeriveUnitsTest {

  private Species sAmount, sConc;

  /**
   * Creates a minimal test model with species in different configurations.
   */
  public SpeciesDeriveUnitsTest() {
    // configure SBML basics
    ModelBuilder builder = new ModelBuilder(3, 1);
    builder.getSBMLDocument();
    Model m = builder.buildModel("Species_Units_Test", "Species Derive Units Test Model");

    // declare units
    m.setVolumeUnits(builder.buildUnitDefinition(UnitDefinition.VOLUME, "volume", builder.buildUnit(1d, 0, Kind.LITRE, 1d)).getId());
    m.setSubstanceUnits(builder.buildUnitDefinition(UnitDefinition.SUBSTANCE, "substance", builder.buildUnit(1d, 0, Kind.MOLE, 1d)).getId());
    m.setTimeUnits(builder.buildUnitDefinition(UnitDefinition.TIME, "time", builder.buildUnit(1d, 0, Kind.SECOND, 1d)).getId());
    m.setExtentUnits(builder.buildUnitDefinition(UnitDefinition.EXTENT, "extent", m.getSubstanceUnitsInstance().getUnit(0).clone()).getId());

    // declare compartments
    Compartment e = builder.buildCompartment("e", true, "extracellular space", 3d, 1d, m.getVolumeUnits());
    sAmount = builder.buildSpecies("s1", "species in amount", e, true, false, false, 1d, m.getSubstanceUnits());
    sConc = builder.buildSpecies("s2", "species in concentration", e, false, false, false, 1d, m.getSubstanceUnits());
  }


  /**
   * Test method for {@link org.sbml.jsbml.Species#getDerivedUnitDefinition()}.
   */
  @Test
  public void testGetDerivedUnitDefinition() {
    UnitDefinition udAmount = sAmount.getDerivedUnitDefinition();
    UnitDefinition udConc = sConc.getDerivedUnitDefinition();
    assertTrue(udAmount.isVariantOfSubstance());
    assertTrue(udConc.isVariantOfSubstancePerVolume());
  }


  /**
   * Test method for {@link org.sbml.jsbml.Species#getDerivedUnits()}.
   */
  @Test
  public void testGetDerivedUnits() {
    String udAmountId = sAmount.getDerivedUnits();
    String udConcId = sConc.getDerivedUnits();
    assertTrue(udAmountId.equals(UnitDefinition.SUBSTANCE));
    assertTrue((udConcId == null) || !udConcId.equals(UnitDefinition.SUBSTANCE));
  }


  /**
   * Test method for {@link org.sbml.jsbml.Species#getPredefinedUnitID()}.
   */
  @Test
  public void testGetPredefinedUnitID() {
    String predefAmount = sAmount.getPredefinedUnitID();
    String predefConc = sConc.getPredefinedUnitID();
    assertTrue(predefConc == null);
    assertTrue(predefAmount == null);
  }

}
