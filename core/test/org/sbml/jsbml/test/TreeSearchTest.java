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
package org.sbml.jsbml.test;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.util.filters.SpeciesReferenceFilter;

/**
 * @author Andreas Dr&auml;ger
 */
/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class TreeSearchTest {

  /**
   * 
   */
  private static SBMLDocument doc;
  /**
   * 
   */
  private static Filter filter;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    doc = new SBMLDocument(3, 1);
    Model m = doc.createModel("test_model");

    Compartment c = m.createCompartment("default");
    c.setSpatialDimensions(3d);

    Species s1 = m.createSpecies("s1", "species1", c);
    Species s2 = m.createSpecies("s2", "species2", c);
    Species s3 = m.createSpecies("s3", "species3", c);
    Species s4 = m.createSpecies("s4", "species4", c);

    Reaction r1 = m.createReaction("r1");
    r1.setName("reaction1");
    r1.setCompartment(c);
    SpeciesReference sr1 = r1.createReactant("sr1", s1);
    sr1.setName("reactant1");
    sr1.setStoichiometry(1d);
    SpeciesReference sr2 = r1.createProduct("sr2", s2);
    sr2.setName("product1");
    sr2.setStoichiometry(1d);
    ModifierSpeciesReference msr1 = r1.createModifier("msr1", s3);
    msr1.setName("modifier");

    Reaction r2 = m.createReaction("r2");
    r2.setName("reaction2");
    r2.setCompartment(c);
    SpeciesReference sr3 = r2.createReactant("sr3", s1);
    sr3.setName("reactant2");
    sr3.setStoichiometry(2d);
    SpeciesReference sr4 = r2.createProduct("sr4", s4);
    sr4.setName("product2");
    sr4.setStoichiometry(1d);
    ModifierSpeciesReference msr2 = r2.createModifier("msr2", s3);
    msr2.setName("modifier");

    SBMLWriter.write(doc, System.out, ' ', (short) 2);
    System.out.println('\n');
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    filter = new SpeciesReferenceFilter(null, "modifier");
  }

  /**
   * Test method for {@link org.sbml.jsbml.AbstractTreeNode#filter(org.sbml.jsbml.util.filters.Filter)}.
   */
  @Test
  public void testFilter() {
    Model m = doc.getModel();
    List<TreeNode> list = new LinkedList<TreeNode>();
    list.add(m.findNamedSBase("msr1"));
    list.add(m.findNamedSBase("msr2"));
    List<? extends TreeNode> result = doc.filter(filter);
    System.out.println("filter result without internals and without pruning:\n  " + result);
    assertTrue(result.equals(list));
  }

  /**
   * Test method for {@link org.sbml.jsbml.AbstractTreeNode#filter(org.sbml.jsbml.util.filters.Filter, boolean)}.
   */
  @Test
  public void testFilterBoolean() {
    Model m = doc.getModel();
    List<TreeNode> list = new LinkedList<TreeNode>();
    list.add(doc);
    list.add(m);
    list.add(m.getListOfReactions());
    Reaction r1 = m.getReaction(0);
    list.add(r1);
    list.add(r1.getListOfModifiers());
    list.add(r1.getModifier(0));
    Reaction r2 = m.getReaction(1);
    list.add(r2);
    list.add(r2.getListOfModifiers());
    list.add(r2.getModifier(0));
    List<? extends TreeNode> result = doc.filter(filter, true);
    System.out.println("filter result retain internals without pruning:\n  " + result);
    assertTrue(result.equals(list));
  }

  /**
   * Test method for {@link org.sbml.jsbml.AbstractTreeNode#filter(org.sbml.jsbml.util.filters.Filter, boolean, boolean)}.
   */
  @Test
  public void testFilterBooleanBoolean() {
    Model m = doc.getModel();
    List<TreeNode> list = new LinkedList<TreeNode>();
    list.add(doc);
    list.add(m);
    list.add(m.getListOfReactions());
    Reaction r1 = m.getReaction(0);
    list.add(r1);
    list.add(r1.getListOfModifiers());
    list.add(r1.getModifier(0));
    List<? extends TreeNode> result = doc.filter(filter, true, true);
    System.out.println("filter result retain internals with pruning:\n  " + result);
    assertTrue(result.equals(list));
  }

}
