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
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.IdentifierException;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

/**
 * Tests the registration and un-registration of global or local id using
 * core elements.
 * 
 * @since 1.0
 */
public class UnregisterTests {

  /**
   * 
   */
  SBMLDocument doc;
  /**
   * 
   */
  Model model;

  /**
   * 
   */
  @BeforeClass public static void initialSetUp() {}
  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(2, 4);
    model = doc.createModel("model");

    Compartment comp = model.createCompartment("cell");
    comp.setMetaId("cell");

    model.getListOfSpecies().setId("LOS");
    model.getListOfSpecies().setMetaId("LOS");
    Species s1 = model.createSpecies("S1", comp);
    s1.setMetaId("S1");

    Species s2 = model.createSpecies("S2", comp);
    s2.setMetaId("S2");

    Reaction r1 = model.createReaction("R1");
    r1.setMetaId("R1");
    r1.createKineticLaw().setId("KL1");
    r1.getKineticLaw().setMetaId("KL1");

    SpeciesReference reactant = model.createReactant("SP1");
    reactant.setMetaId("SP1");
    reactant.setSpecies(s1);

    SpeciesReference product = model.createProduct("SP2");
    product.setMetaId("SP2");
    product.setSpecies(s2);

    LocalParameter lp1 = r1.getKineticLaw().createLocalParameter("LP1");
    lp1.setMetaId("LP1");

    Constraint c1 = model.createConstraint();
    c1.setMetaId("c0");
    
    Event e1 = model.createEvent("E1");
    e1.createTrigger().setId("ET1");
    e1.createDelay().setId("ED1");
    e1.createEventAssignment().setId("EEV1");
  }

  /**
   * 
   */
  @Test public void testRegister() {

    try {
      model.createSpecies("model");
      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    try {
      model.createSpecies("R1");
      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    assertTrue(model.getSBaseById("ET1") != null);
    assertTrue(model.getSBaseById("LOS") != null);
    assertTrue(model.getSBaseById("KL1") != null);
    assertTrue(model.getSBaseById("ED1") != null);
    assertTrue(model.getSBaseById("EEV1") != null);
    try {
      model.createSpecies("ET1");
      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    try {
      model.createSpecies("LOS");
      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    try {
      model.createSpecies("KL1");
      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    Species s3 = new Species();
    s3.setId("SP1");

    try {
      model.addSpecies(s3);
      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }
    assertTrue(model.getSpecies("S3") == null);

    s3.setId("LP1");

    try {
      model.addSpecies(s3);
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      fail("We should be able to register an id that is the same as a local parameter id.");
    }

    model.removeSpecies(s3);
    assertTrue(model.getSpecies("S3") == null);

    // Testing again after having removed the Species
    try {
      model.addSpecies(s3);
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      fail("We should be able to register an id that is the same as a local parameter id.");
    }

    model.removeSpecies(s3);
    assertTrue(model.getSpecies("S3") == null);

    // Setting the same metaid as a SpeciesReference
    try {
      s3.setMetaId("SP1");
      assertTrue(true);
    } catch (IdentifierException e) {
      fail("We should be able to put any metaId to a Species removed from a model.");
    }

    try {
      model.addSpecies(s3);
      fail("We should not be able to register twice the same metaid.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    assertTrue(model.getSpecies("S3") == null);

    try {
      s3.setId("S1");
      assertTrue(true);
    } catch (IdentifierException e) {
      fail("We should be able to put any id to a Species removed from a model.");
    }

    try {
      model.addSpecies(s3);
      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    assertTrue(model.getSpecies("S3") == null);

    // Setting the same metaid as a LocalParameter

    try {
      s3.setMetaId("LP1");
      assertTrue(true);
    } catch (IdentifierException e) {
      fail("We should be able to put any metaId to a Species not linked to a model.");
    }

    try {
      model.addSpecies(s3);
      fail("We should not be able to register twice the same metaid.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    Model clonedModel = model.clone();
    
    assertTrue(clonedModel.getSBaseById("ET1") != null);
    assertTrue(clonedModel.getSBaseById("LOS") != null);
    assertTrue(clonedModel.getSBaseById("KL1") != null);
    assertTrue(clonedModel.getSBaseById("ED1") != null);
    assertTrue(clonedModel.getSBaseById("EEV1") != null);
    assertTrue(clonedModel.getSBaseById("LP1") == null);
    

  }

  /**
   * 
   */
  @Test public void testRegister2() {

    Species s3 = new Species();
    // Setting the same id as a LocalParameter
    s3.setId("LP1");

    // Setting the same metaid as a LocalParameter
    try {
      s3.setMetaId("LP1");
      assertTrue(true);
    } catch (IdentifierException e) {
      fail("We should be able to put any metaId to a Species not linked to a model.");
    }

    try {
      boolean speciesAdded = model.addSpecies(s3);

      if (speciesAdded) {
        fail("We should not be able to register twice the same metaid.");
      }
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }
  }

  /**
   * 
   */
  @Test public void testRegister3() {

    Reaction r2 = new Reaction(2,4);
    r2.setId("R2");
    KineticLaw k = r2.createKineticLaw();

    k.createLocalParameter("LP1");

    // We should not be allowed to register an other local parameter with the same id
    try {
      k.createLocalParameter("LP1");
      // fail("We should not be able to add a local parameter with the same id as an other local parameter in the same kineticLaw.");
    } catch (IdentifierException e) {
      // success
    } catch (IllegalArgumentException e) {
      // success (this the exception returned often, IdentifierException would be better)
    }

    assertTrue(k.getLocalParameterCount() == 1);

    try {
      model.addReaction(r2);
    } catch (IllegalArgumentException e) {
      // failure
      e.printStackTrace();
    }

    assertTrue(model.getReactionCount() == 2);

  }


  /**
   * 
   */
  @Test public void testRegister3_1() {

    // Creating a new reaction without id
    Reaction r2 = model.createReaction();

    assertTrue(model.getReactionCount() == 2);

    KineticLaw k = r2.createKineticLaw();

    k.createLocalParameter("LP1");

    k.getLocalParameter("LP1").setMetaId("LP1_2");
    k.getLocalParameter(0).setName("LP1_2");

    assertTrue(k.getLocalParameterCount() == 1);

    assertTrue(model.findLocalParameters("LP1").size() == 2);
    assertTrue(model.findReactionsForLocalParameter("LP1").size() == 1);

    r2.setId("R2");

    assertTrue(model.findReactionsForLocalParameter("LP1").size() == 2);

    assertTrue(doc.findSBase("LP1_2") != null);

    r2.unsetKineticLaw();

    assertTrue(doc.findSBase("LP1_2") == null);

    assertTrue(model.findLocalParameters("LP1").size() == 1);

    assertTrue(model.findReactionsForLocalParameter("LP1").size() == 1);

    model.getReaction(0).getKineticLaw().removeLocalParameter(0);

    assertTrue(model.findLocalParameters("LP1").size() == 0);
    assertTrue(model.findReactionsForLocalParameter("LP1") == null);
  }

  /**
   * 
   */
  @Test public void testRegister3_2() {

    // Using a List instead of a Set to store the Reaction associated with a local Parameter as
    // if the object HashCode change, it is not possible anymore to remove the element !!!

    // http://stackoverflow.com/questions/254441/hashset-remove-and-iterator-remove-not-working

    Reaction r2 = model.createReaction("R2");
    Reaction r3 = model.createReaction("R3");

    HashSet<Reaction> reactionSet = new HashSet<Reaction>();

    reactionSet.add(r2);
    reactionSet.add(r3);

    KineticLaw kl = r2.createKineticLaw();
    kl.createLocalParameter("LP1");

    reactionSet.remove(r2); // unsuccessful as the hashCode of r2 has changed since we added it to the HashSet !!
    // The javadoc is misleading on this case as it just says that the equals method will be used
    // They probably use the hashcode as the key for the underlying HashMap.

    // We cannot use HashSet for object that change over time !!
    assertTrue(reactionSet.size() == 2);

  }

  /**
   * 
   */
  @Test public void testRegister3_3() {

    // Creating a new reaction without id
    Reaction r2 = model.createReaction();

    assertTrue(model.getReactionCount() == 2);

    KineticLaw k = r2.createKineticLaw();

    // Creating a new local parameter without id
    LocalParameter lp = k.createLocalParameter();

    lp.setMetaId("LP1_2");
    lp.setName("LP1_2");
    lp.setId("LP1");

    assertTrue(k.getLocalParameterCount() == 1);

    assertTrue(doc.findSBase("LP1_2") != null);

    assertTrue(k.getLocalParameter("LP1") != null);
    assertTrue(model.findLocalParameters("LP1").size() == 2);
    assertTrue(model.findReactionsForLocalParameter("LP1").size() == 1); // because r2 has no ID yet !!

    lp.setId("LP1_2"); // changing the id of the localParameter !!

    assertTrue(k.getLocalParameter("LP1") == null);
    assertTrue(k.getLocalParameter("LP1_2") != null);

    assertTrue(model.findLocalParameters("LP1_2").size() == 1);

    assertTrue(model.findLocalParameters("LP1").size() == 1);

    assertTrue(model.findReactionsForLocalParameter("LP1_2").size() == 0);  // because r2 has no ID yet !!

    r2.setId("R2");

    assertTrue(model.findReactionsForLocalParameter("LP1_2").size() == 1);
    assertTrue(model.findReactionsForLocalParameter("LP1").size() == 1);

    assertTrue(doc.findSBase("LP1_2") != null);
    assertTrue(model.getReaction("R2") != null);
    assertTrue(model.findNamedSBase("R2") != null);

    r2.unsetKineticLaw();

    assertTrue(doc.findSBase("LP1_2") == null);

    assertTrue(model.findLocalParameters("LP1_2").size() == 0);

    assertTrue(model.findReactionsForLocalParameter("LP1").size() == 1);
    assertTrue(model.findReactionsForLocalParameter("LP1_2") == null);

    model.getReaction(0).getKineticLaw().removeLocalParameter(0);

    assertTrue(model.findLocalParameters("LP1").size() == 0);
    assertTrue(model.findReactionsForLocalParameter("LP1") == null);
  }


  /**
   * 
   */
  @Test public void testRegister4() {

    assertTrue(model.getId().equals("model"));
    assertTrue(model.getSpeciesCount() == 2);

    // We should be allowed to change an id !
    model.setId("newModelID");

    try {
      // fail("We should not be able to add a local parameter with the same id as an other local parameter in the same kineticLaw.");
    } catch (IdentifierException e) {
      // success
    }

    assertTrue(model.getId().equals("newModelID"));

  }


  /**
   * 
   */
  @Test public void testRegister5_2() {

    Reaction r2 = new Reaction();
    r2.setId("R2");

    // Setting the same id as an existing Species
    r2.createReactant("S1");

    try {
      model.addReaction(r2);

      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }
  }

  /**
   * 
   */
  @Test public void testRegister5_3() {

    Reaction r2 = new Reaction();
    r2.setId("R2");

    // Setting the same id as an existing Species
    r2.createReactant("S1");

    // Setting the parent by hand !!  // TODO: should we limit the access of SBase.setParentSBML and TreeNode.setParent (should be possible) ??
    // r2.setParentSBML(new ListOf<Reaction>(2, 4));
    // r2.getParent().setParentSBML(model); // This will make the registration of the reaction non recursive

    try {
      model.addReaction(r2);

      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }
  }

  /**
   * 
   */
  @Test public void testRegister6() {

    Species s3 = new Species();
    s3.setId("S3");

    model.addSpecies(s3);

    // calling the registerChild by hand !!
    model.registerChild(s3);// This call does nothing if the parent of the SBase we try to register is already defined
    // a warning is displayed on the shell
  }

  /**
   * 
   */
  @Test public void testRegister7() {

    Reaction r2 = model.createReaction("R2");

    r2.createReactant("SR3");

    r2.createKineticLaw().createLocalParameter("LP1");

    Reaction clonedR2 = r2.clone();

    assertTrue(clonedR2.getKineticLaw().getLocalParameterCount() == 1);

    try {
      clonedR2.getKineticLaw().createLocalParameter("LP1");

      fail("We should not be able to register twice the same id.");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      // success
    }

    try {
      clonedR2.getKineticLaw().createLocalParameter("LP2");
      assertTrue(true);
      // success
    } catch (IllegalArgumentException e) {
      fail("We should be able to register a new local parameter on a cloned reaction.");
    }

    assertTrue(clonedR2.getKineticLaw().getLocalParameterCount() == 2);
  }

  /**
   * 
   */
  @Test public void testRegister8() {

    Reaction r2 = model.createReaction("R2");

    KineticLaw kl = r2.createKineticLaw();

    ListOf<LocalParameter> listOfLP = new ListOf<LocalParameter>(2, 4);
    listOfLP.add(new LocalParameter("LP1"));
    listOfLP.add(new LocalParameter("LP2"));
    listOfLP.add(new LocalParameter("LP3"));
    listOfLP.add(new LocalParameter("LP1"));

    try {

      kl.setListOfLocalParameters(listOfLP);

      fail("We should not be able to register twice the same local parameter id.");
    } catch(IllegalArgumentException e) {
      // success
    }

    assertTrue(kl.getLocalParameterCount() == 0);
    assertTrue(kl.getLocalParameter("LP1") == null);
    assertTrue(kl.getLocalParameter("LP3") == null);
    assertTrue(model.findLocalParameters("LP1").size() == 1);
    assertTrue(model.findLocalParameters("LP2").size() == 0);

    listOfLP.clear();
    listOfLP.add(new LocalParameter("LP1"));
    listOfLP.add(new LocalParameter("LP2"));
    listOfLP.add(new LocalParameter("LP3"));

    try {

      kl.setListOfLocalParameters(listOfLP);

    } catch(IllegalArgumentException e) {
      fail("We should be able to set a valid list of local parameters.");
    }

    assertTrue(kl.getLocalParameterCount() == 3);

    assertTrue(model.findLocalParameters("LP1").size() == 2);
    assertTrue(model.findLocalParameters("LP2").size() == 1);

    kl.removeLocalParameter("LP2");
    kl.removeLocalParameter(1);

    assertTrue(kl.getLocalParameterCount() == 1);
    assertTrue(model.findLocalParameters("LP2").size() == 0);
    assertTrue(model.findLocalParameters("LP3").size() == 0);

    kl.createLocalParameter("LP2");

    assertTrue(kl.getLocalParameterCount() == 2);
    assertTrue(model.findLocalParameters("LP2").size() == 1);
  }


  /**
   * 
   */
  @Test public void testUnRegister() {


    Reaction r1 = model.removeReaction("R1");

    assertTrue(model.getReaction("R1") == null);
    assertTrue(model.getReaction(0) == null);
    assertTrue(model.findNamedSBase("SP1") == null);
    assertTrue(model.findNamedSBase("SP2") == null);
    // assertTrue(model.findNamedSBase("LP1") == null);
    assertTrue(doc.findSBase("SP1") == null);
    assertTrue(doc.findSBase("R1") == null);
    assertTrue(doc.findSBase("LP1") == null);

    Species s3 = new Species();

    // Setting the same id as a removed SpeciesReference
    s3.setId("SP1");

    // Setting the same metaid as a removed LocalParameter
    try {
      s3.setMetaId("LP1");
      assertTrue(true);
    } catch (IdentifierException e) {
      fail("We should be able to put any metaId to a Species not linked to a model.");
    }

    try {
      model.addSpecies(s3);
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      fail("We should be able to register an id or metaid from a removed element.");
    }

    try {
      SpeciesReference reactant = r1.createReactant("S1");
      reactant.setMetaId("S1");
    } catch (IllegalArgumentException e) {
      fail("We should be able to put any id to an element removed from a model.");
    } catch (IdentifierException e) {
      fail("We should be able to put any id to an element removed from a model.");
    }
  }

  /**
   * 
   */
  @Test public void testUnRegister2() {

    Reaction r1 = model.removeReaction("R1");

    assertTrue(r1.getReactant("SP1") != null);

    assertTrue(model.findNamedSBase("LP1") == null);

    // creating a product with the same id as a species
    r1.createProduct("S1");
  }


  /**
   * 
   */
  @Test public void testUnRegister3() {

    model.unsetListOfReactions();

    assertTrue(model.getReaction("R1") == null);
    assertTrue(model.getReaction(0) == null);
    assertTrue(model.findNamedSBase("SP1") == null);
    assertTrue(model.findNamedSBase("SP2") == null);
    // assertTrue(model.findNamedSBase("LP1") == null);
    assertTrue(doc.findSBase("SP1") == null);
    assertTrue(doc.findSBase("R1") == null);
    assertTrue(doc.findSBase("LP1") == null);

    Species s3 = new Species();

    // Setting the same id as a removed SpeciesReference
    s3.setId("SP1");

    // Setting the same metaid as a removed LocalParameter
    try {
      s3.setMetaId("LP1");
      assertTrue(true);
    } catch (IdentifierException e) {
      fail("We should be able to put any metaId to a Species not linked to a model.");
    }

    try {
      model.addSpecies(s3);
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      fail("We should be able to register an id or metaid from a removed element.");
    }
  }

  /**
   * 
   */
  @Test public void testUnRegisterConstraintMetaid() {

    Constraint c0 = (Constraint) doc.findSBase("c0");
    Constraint c1 = model.getConstraint(0);

    assertTrue(c0 != null);
    assertTrue(c1 != null);
    assertTrue(c0.equals(c1));

    c0.setMetaId("c1");

    assertTrue(doc.findSBase("c0") == null);
    assertTrue(doc.findSBase("c1") != null);

    Constraint c3 = model.createConstraint();
    c3.setMetaId("c0");

    assertTrue(doc.findSBase("c0") != null);
    assertTrue(doc.findSBase("c0") == c3);

    Constraint c4 = c3.clone();

    try {
      model.addConstraint(c4);
      fail("We should not be able to register a cloned element with the same metaid as an other element in the model.");
    } catch (IllegalArgumentException e) {
      // success
    }

    assertTrue(doc.findSBase("c0") == c3);
  }
}
