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
package org.sbml.jsbml.xml.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.AbstractNamedSBaseWithUnit;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;


/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class TestAbstractNamedSBaseWithUnits {

  /**
   * The element to be tested.
   */
  private AbstractNamedSBaseWithUnit sbase;
  /**
   * 
   */
  private Unit.Kind kind;

  /**
   * Initialize an object
   */
  @Before
  public void init() {
    int level = 3, version = 1;
    SBMLDocument doc = new SBMLDocument(level, version);
    Model model = doc.createModel("test_model");
    sbase = model.createParameter("test_param");
    kind = Unit.Kind.AMPERE;
    assertTrue(!sbase.isSetUnits());
  }

  /**
   * Test method for {@link org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)}.
   */
  @Test
  public void testSetUnitsKind() {
    sbase.setUnits(kind);
    assertTrue(sbase.isSetUnits());
    assertTrue(sbase.getUnits().equals(kind.toString().toLowerCase()));
    assertTrue(sbase.isSetUnitsInstance());
    assertTrue(sbase.getUnitsInstance().getId().equals(
      kind.toString().toLowerCase() + UnitDefinition.BASE_UNIT_SUFFIX));
  }


  /**
   * Test method for {@link org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(java.lang.String)}.
   */
  @Test
  public void testSetUnitsString() {
    sbase.setUnits(kind.toString().toLowerCase());
    assertTrue(sbase.isSetUnits());
    assertTrue(sbase.getUnits().equals(kind.toString().toLowerCase()));
    assertTrue(sbase.isSetUnitsInstance());
    assertTrue(sbase.getUnitsInstance().getId().equals(
      kind.toString().toLowerCase() + UnitDefinition.BASE_UNIT_SUFFIX));
  }


  /**
   * Test method for {@link org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.Unit)}.
   */
  @Test
  public void testSetUnitsUnit() {
    Unit unit = new Unit(1d, 0, kind, 1d, sbase.getLevel(), sbase.getVersion());
    sbase.setUnits(unit);
    assertTrue(sbase.isSetUnits());
    assertTrue(sbase.getUnits().equals(kind.toString().toLowerCase()));
    unit.setExponent(2d);
    sbase.setUnits(unit);
    assertTrue(sbase.isSetUnits());
    assertTrue(sbase.isSetUnitsInstance());
    assertTrue(sbase.getUnits().equals(
      '_' + "1_0_0_" + kind.toString() + '_'
      + Double.toString(unit.getExponent()).replace('.', '_')));
  }


  /**
   * Test method for {@link org.sbml.jsbml.AbstractNamedSBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)}.
   */
  @Test
  public void testSetUnitsUnitDefinition() {
    UnitDefinition ud = sbase.getModel().getPredefinedUnitDefinition(
      kind.toString().toLowerCase());
    sbase.setUnits(ud);
    assertTrue(sbase.isSetUnits());
    assertTrue(sbase.isSetUnitsInstance());
    assertTrue(sbase.getUnits().equals(kind.toString().toLowerCase() + UnitDefinition.BASE_UNIT_SUFFIX));
  }

  /**
   * Test method for {@link org.sbml.jsbml.AbstractNamedSBaseWithUnit#isPredefinedUnitsID(java.lang.String)}.
   */
  @Test
  public void testIsPredefinedUnitsID() {
    assertTrue(!sbase.isPredefinedUnitsID(kind.toString().toLowerCase()));
  }

}
