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
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math.test;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.math.ASTCiNumberNode;

/**
 * Test cases for {@link ASTCiNumberNode}
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCiNumberNodeTest {

  /**
   * 
   */
  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#clone()}.
   */
  @Test
  public final void testClone() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ASTCiNumberNode unknown = ci.clone();
    assertTrue(ci.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#ASTCiNumberNode(org.sbml.jsbml.math.ASTCiNumberNode)}.
   */
  @Test
  public final void testCloneWithConstructor() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ASTCiNumberNode unknown = new ASTCiNumberNode(ci);
    assertTrue(ci.equals(unknown));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#containsUndeclaredUnits()}.
   */
  @Test
  public final void testContainsUndeclaredUnitsFalse() {
    Parameter tau = new Parameter(3, 1);
    tau.setName("tau2");
    tau.setId("parameter_0000009");
    tau.setValue(10);
    Unit unit = new Unit(3, 1);
    unit.setKind(Unit.Kind.SECOND);
    tau.setUnits(unit);
    tau.setConstant(true);
    Model model = new Model(3, 1);
    model.addParameter(tau);
    Constraint constraint = new Constraint(3, 1);
    model.addConstraint(constraint);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setParentSBMLObject(constraint);
    ci.setReference(tau);
    assertTrue(!ci.containsUndeclaredUnits());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#containsUndeclaredUnits()}.
   */
  @Test
  public final void testContainsUndeclaredUnitsTrue() {
    Parameter tau = new Parameter(2, 4);
    tau.setName("tau2");
    tau.setId("parameter_0000009");
    tau.setValue(10);
    tau.setConstant(true);
    Model model = new Model(2, 4);
    model.addParameter(tau);
    Constraint constraint = new Constraint(2, 4);
    model.addConstraint(constraint);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setParentSBMLObject(constraint);
    ci.setReference(tau);
    assertTrue(ci.containsUndeclaredUnits());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLExists() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    String url = "http://some-url.com";
    ci.setDefinitionURL(url);
    assertTrue(ci.getDefinitionURL().equals(url));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistent() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    exception.expect(PropertyUndefinedError.class);
    ci.getDefinitionURL();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getDefinitionURL()}.
   */
  @Test
  public final void testGetDefinitionURLNonExistentNonStrict() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setStrictness(false);
    assertTrue(ci.getDefinitionURL().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getReferenceInstance()}.
   */
  @Test
  public final void testGetReferenceInstanceKineticLaw() {
    LocalParameter cytosol = new LocalParameter();
    cytosol.setId("cytosol");
    cytosol.setValue(0.05);
    KineticLaw kineticLaw = new KineticLaw();
    kineticLaw.addLocalParameter(cytosol);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setParentSBMLObject(kineticLaw);
    ci.setReference(cytosol);
    assertTrue(ci.getReferenceInstance().equals(cytosol));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getReferenceInstance()}.
   */
  @Test
  public final void testGetReferenceInstanceModel() {
    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    Model model = new Model();
    model.addParameter(tau);
    Constraint constraint = new Constraint();
    model.addConstraint(constraint);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setParentSBMLObject(constraint);
    ci.setReference(tau);
    assertTrue(ci.getReferenceInstance().equals(tau));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getReferenceInstance()}.
   */
  @Test
  public final void testGetReferenceInstanceNoModel() {
    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    Constraint constraint = new Constraint();
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setParentSBMLObject(constraint);
    ci.setReference(tau);
    assertTrue(ci.getReferenceInstance() == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getReferenceInstance()}.
   */
  @Test
  public final void testGetReferenceInstanceNoParentSBMLObject() {
    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setReference(tau);
    assertTrue(ci.getReferenceInstance() == null);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdExists() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    String reference = "reference";
    ci.setRefId(reference);
    assertTrue(ci.getRefId().equals(reference));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdNonExistent() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    exception.expect(PropertyUndefinedError.class);
    ci.getRefId();
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getRefId()}.
   */
  @Test
  public final void testGetRefIdNonExistentNonStrict() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setStrictness(false);
    assertTrue(ci.getRefId().isEmpty());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getType()}.
   */
  @Test
  public final void testGetType() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    assertTrue(ci.getType() == Type.NAME);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#isSetType()}.
   */
  @Test
  public final void testIsSetType() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    assertTrue(ci.isSetType());
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#getReferenceInstance()}.
   */
  @Test
  public final void testRefersTo() {
    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setReference(tau);
    assertTrue(ci.refersTo("tau2"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#setType(Type)}.
   */
  @Test
  public final void testSetTypeAllowed() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setType(Type.NAME);
    assertTrue(ci.getType() == Type.NAME);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#setType(Type)}.
   */
  @Test
  public final void testSetTypeNotAllowed() {
    ASTCiNumberNode ci = new ASTCiNumberNode();
    exception.expect(IllegalArgumentException.class);
    ci.setType(Type.UNKNOWN);
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#toFormula()}.
   */
  @Test
  public final void testToFormula() {
    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setReference(tau);
    assertTrue(ci.toFormula().equals("tau2"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#toLaTeX()}.
   */
  @Test
  public final void testToLaTeX() {
    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setReference(tau);
    assertTrue(ci.toFormula().equals("tau2"));
  }

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTCiNumberNode#toMathML()}.
   */
  @Test
  public final void testToMathML() {
    Parameter tau = new Parameter();
    tau.setId("tau2");
    tau.setValue(3e-2);
    tau.setUnits("seconds");
    tau.setConstant(true);
    ASTCiNumberNode ci = new ASTCiNumberNode();
    ci.setReference(tau);
    assertTrue(ci.toFormula().equals("tau2"));
  }

}
