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
package org.sbml.jsbml.ext.groups.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.groups.Group;
import org.sbml.jsbml.ext.groups.GroupsConstants;
import org.sbml.jsbml.ext.groups.GroupsModelPlugin;
import org.sbml.jsbml.ext.groups.Member;

/**
 * Tests the creation of all Group package elements.
 * 
 * @since 1.2
 */
public class CreateGroupTests {

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
  @BeforeClass public static void initialSetUp() {    
  }

  /**
   * 
   */
  @Before public void setUp() {
    
    doc = new SBMLDocument(3, 1);
    model = doc.createModel("model");

    Compartment comp = model.createCompartment("cell");
    comp.setMetaId("cell");

    Species s1 = model.createSpecies("S1", comp);
    s1.setMetaId("S1");

    Species s2 = model.createSpecies("S2", comp);
    s2.setMetaId("S2");

    Reaction r1 = model.createReaction("R1");
    r1.setMetaId("R1");

    SpeciesReference reactant = model.createReactant("SP1");
    reactant.setMetaId("SP1");
    reactant.setSpecies(s1);

    SpeciesReference product = model.createProduct("SP2");
    product.setMetaId("SP2");
    product.setSpecies(s2);

    LocalParameter lp1 = r1.createKineticLaw().createLocalParameter("LP1");
    lp1.setMetaId("LP1");

  }




  /**
   * 
   */
  @Test public void testGroups() {

    GroupsModelPlugin groupsModel = (GroupsModelPlugin) model.getPlugin(GroupsConstants.shortLabel);
    Group group = groupsModel.createGroup();
    group.setId("G1");
    group.setKind(Group.Kind.classification);
    
    group.createMember("GM1");
    group.createMemberWithIdRef("GM2", "S2");
    Member member3 = group.createMemberWithIdRef("GM3", "S1");
    member3.setMetaId("GM3");

    assertTrue(groupsModel.getGroupCount() == 1);
    assertTrue(group.getMemberCount() == 3);

    Group group2 = groupsModel.createGroup("G2", "GMR1", "GMcell", "GMS1", "GMS2", "GMSP1");
    group2.setKind(Group.Kind.collection);    
    
    assertTrue(groupsModel.getGroupCount() == 2);
    assertTrue(group2.getMemberCount() == 5);
    assertTrue(group2.getMember(2).isSetId());
    assertTrue(group2.getMember(2).isSetIdRef() == false);
    
    group2.getListOfMembers().setId("collection_of_all_elements");
        
    Group group3 = groupsModel.createGroup("G3", "GMcollection_of_all_elements");
    group3.getMember("GMcollection_of_all_elements").setIdRef("collection_of_all_elements");
    group3.createMemberWithMetaIdRef("LP1");
    
    
    SBMLDocument clonedDoc = doc.clone();
    Model clonedModel = clonedDoc.getModel();
    GroupsModelPlugin clonedGroupModelPlugin = (GroupsModelPlugin) clonedModel.getPlugin("groups");

    assertTrue(clonedGroupModelPlugin.getGroupCount() == 3);
    assertTrue(clonedDoc.findSBase("GM3") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("GM3") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("G1") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("collection_of_all_elements") != null);
    assertTrue(clonedModel.findUniqueNamedSBase("GMcollection_of_all_elements") != null);

  }

}
