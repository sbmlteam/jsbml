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
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.arrays.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;


/**
 * Test for {@link ArraysSBasePlugin}, {@link Dimension}, and {@link Index} classes.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArrayExtensionTest {

  /**
   * Binds a species with an array extension
   * @param spec
   * @return sBasePlugin
   */
  private ArraysSBasePlugin bindPluginToSpecies(Species spec)
  {
    SBMLDocument doc = new SBMLDocument(3,1);

    Model model = doc.createModel();

    model.addSpecies(spec);

    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

    spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);
    //spec.addExtension("arrays", sbasePlugin);
    return arraysSBasePlugin;
  }
  /**
   * Test if extension is successfully binding to SBase object
   */
  @Test
  public void testAddExtension() {


    Species spec = new Species();

    bindPluginToSpecies(spec);

    ArraysSBasePlugin specSBasePlugin = (ArraysSBasePlugin) spec.getExtension(ArraysConstants.shortLabel);

    assertTrue(specSBasePlugin != null);
  }

  /**
   * Test if listOf objects are correctly initialized
   */
  @Test
  public void testListOf() {

    Species spec = new Species();

    ArraysSBasePlugin arraysSBasePlugin = bindPluginToSpecies(spec);

    assertTrue(!arraysSBasePlugin.isSetListOfDimensions() && !arraysSBasePlugin.isSetListOfIndices());
  }



  /**
   * Test if dimension is properly added
   */
  @Test
  public void testAddDimension() {

    Species spec = new Species();

    ArraysSBasePlugin arraysSBasePlugin = bindPluginToSpecies(spec);

    Dimension dimension = new Dimension();

    arraysSBasePlugin.addDimension(dimension);

    assertTrue(arraysSBasePlugin.getListOfDimensions() != null &&
        arraysSBasePlugin.getChildCount() == 1);
  }


  /**
   * Test if dimension is properly added
   */
  @Test
  public void testAddIndex() {

    Species spec = new Species();

    ArraysSBasePlugin arraysSBasePlugin = bindPluginToSpecies(spec);

    Index index = new Index();

    arraysSBasePlugin.addIndex(index);

    assertTrue(arraysSBasePlugin.getListOfIndices() != null &&
        arraysSBasePlugin.getChildCount() == 1);
  }
  /**
   * Test if dimension size is properly set
   */
  @Test
  public void testDimensionSize() {

    String size = "n";
    Dimension dimension = new Dimension();
    dimension.setSize(size);
    assertTrue(dimension.getSize().equals(size));

  }

  /**
   * Test if array dimension is properly set
   */
  @Test
  public void testDimensionArrayDimension() {

    Dimension dimension = new Dimension();
    dimension.setArrayDimension(0);
    assertTrue(dimension.getArrayDimension() == 0);

  }

  /**
   * Test if set can be unset
   */
  @Test
  public void testDimensionSizeUnset() {

    Dimension dimension = new Dimension();
    dimension.setSize("n");
    dimension.unsetSize();
    assertTrue(!dimension.isSetSize());

  }

  /**
   * Test if set can be unset
   */
  @Test
  public void testDimensionArrayDimensionUnset() {

    Dimension dimension = new Dimension();
    dimension.setArrayDimension(1);
    dimension.unsetArrayDimension();
    assertTrue(!dimension.isSetArrayDimension());

  }

  /**
   * Test if dimension unique id is enforced
   */
  @Test
  public void testDimensionUniqueId() {
    try
    {
      Species spec = new Species(3,1);
      ArraysSBasePlugin plugin = bindPluginToSpecies(spec);
      Dimension dim = new Dimension("dim", 3, 1);
      Dimension dimCopy = new Dimension("dim", 3, 1);
      plugin.addDimension(dim);
      plugin.addDimension(dimCopy);
      assertTrue(false);
    }
    catch(IllegalArgumentException e)
    {
      //e.printStackTrace();
    }
  }

  /**
   * Test if dimension id can be the same for different species
   */
  @Test
  public void testDimensionIdDifferentSBase() {
    try
    {
      Species X = new Species(3,1);
      ArraysSBasePlugin pluginX = bindPluginToSpecies(X);
      Species Y = new Species(3,1);
      ArraysSBasePlugin pluginY = bindPluginToSpecies(Y);
      Dimension dim = new Dimension("dim", 3, 1);
      Dimension dimCopy = new Dimension("dim", 3, 1);
      pluginX.addDimension(dim);
      pluginY.addDimension(dimCopy);
      assertTrue(true);
    }
    catch(IllegalArgumentException e)
    {
      assertTrue(false);
      //e.printStackTrace();
    }
  }

  /**
   * Test if index referenced attribute is properly set
   */
  @Test
  public void testIndexSetReferencedAttribute() {
    Index index = new Index();
    index.setReferencedAttribute("variable");
    assertTrue(index.isSetReferencedAttribute());

  }

  /**
   * Test if index referenced attribute is properly set
   */
  @Test
  public void testIndexUnsetReferencedAttribute() {
    Index index = new Index();
    index.setReferencedAttribute("variable");
    index.unsetReferencedAttribute();
    assertTrue(!index.isSetReferencedAttribute());
  }

  /**
   * Test if index referenced attribute is properly set
   */
  @Test
  public void testIndexSetArrayDimension() {
    Index index = new Index();
    index.setArrayDimension(0);
    assertTrue(index.isSetArrayDimension());
  }

  /**
   * Test if index referenced attribute is properly unset
   */
  @Test
  public void testIndexUnsetArrayDimension() {
    Index index = new Index();
    index.setArrayDimension(0);
    index.unsetArrayDimension();
    assertTrue(!index.isSetArrayDimension());
  }

  /**
   * Test if index math is properly set
   */
  @Test
  public void testIndexSetMath() {
    Index index = new Index();
    index.setMath(new ASTNode());
    assertTrue(index.isSetMath());
  }

  /**
   * Test if index referenced attribute is properly unset
   */
  @Test
  public void testIndexUnsetMath() {
    Index index = new Index();
    index.setMath(new ASTNode());
    index.unsetMath();
    assertTrue(!index.isSetMath());
  }

  /**
   * Test if id is locally scoped but unique across the hierarchy
   */
  @Test
  public void testId() {
    boolean test = true;
    try{
      SBMLDocument doc = new SBMLDocument(3,1);
      Model m = doc.createModel();
      Event e = m.createEvent();
      EventAssignment a = e.createEventAssignment();
      EventAssignment b = e.createEventAssignment();
      ArraysSBasePlugin e_p = (ArraysSBasePlugin) e.createPlugin("arrays");
      ArraysSBasePlugin a_p = (ArraysSBasePlugin) a.createPlugin("arrays");
      ArraysSBasePlugin b_p = (ArraysSBasePlugin) b.createPlugin("arrays");
      a_p.createDimension("i");
      b_p.createDimension("i");
      test = false;
      assertFalse(test);
      test = true;
      e_p.createDimension("i");
    }
    catch(IllegalArgumentException e)
    {
      assertTrue(test);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  /**
   * Test if dimension ids can be set to same id iff they are part of
   * different SBase. The id is locally scoped.
   */
  @Test
  public void testDimensionIds() {
    try{
      Species S1 = new Species();
      ArraysSBasePlugin P1 = bindPluginToSpecies(S1);
      Dimension D1 = new Dimension("i");
      P1.addDimension(D1);

      Species S2 = new Species();
      ArraysSBasePlugin P2 = bindPluginToSpecies(S2);
      Dimension D2 = new Dimension("i");
      P2.addDimension(D2);


    }
    catch(IllegalArgumentException e)
    {
      assertTrue(false);
      e.printStackTrace();
    }
  }


  /**
   * Test removing elements even the case where it does not exist.
   */
  @Test
  public void testRemoveDimension() {
    try{
      Species S1 = new Species();
      ArraysSBasePlugin P1 = bindPluginToSpecies(S1);
      Dimension D1 = new Dimension("i");
      P1.addDimension(D1);

      Dimension D2 = new Dimension("j");

      P1.removeDimension(D2);
      assertTrue(P1.getDimensionCount() == 1);
      P1.removeDimension("k");
      assertTrue(P1.getDimensionCount() == 1);
      P1.removeDimension("i");
      assertTrue(P1.getDimensionCount() == 0);

    }
    catch(Exception e)
    {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * Test removing elements even the case where it does not exist.
   */
  @Test
  public void testRemoveDimensionByArrayDimension() {
    try{
      Species S1 = new Species();
      ArraysSBasePlugin P1 = bindPluginToSpecies(S1);
      Dimension D1 = new Dimension("i");
      P1.addDimension(D1);
      D1.setArrayDimension(0);
      Dimension D2 = new Dimension("j");
      D2.setArrayDimension(1);
      P1.addDimension(D2);

      assertTrue(P1.removeDimensionByArrayDimension(2) == null);
      assertTrue(P1.removeDimensionByArrayDimension(1) == D2);
      assertTrue(P1.removeDimensionByArrayDimension(0) == D1);

      Index I1 = P1.createIndex();
      I1.setArrayDimension(0);
      Index I2 = P1.createIndex();
      I2.setArrayDimension(1);

      assertTrue(!P1.removeIndexByArrayDimension(2));
      assertTrue(P1.removeIndexByArrayDimension(1));
      assertTrue(P1.removeIndexByArrayDimension(0));

    }
    catch(Exception e)
    {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testDimensionId() {
    try{
      Species S1 = new Species("X");
      ArraysSBasePlugin P1 = bindPluginToSpecies(S1);
      SBMLDocument doc = P1.getSBMLDocument();
      Model model = doc.getModel();
      /*Species S2 =*/
      model.createSpecies("Y");
      /*Dimension D1 =*/
      P1.createDimension("X");
      SBMLWriter.write(doc, System.out, ' ', (short)2);
    }
    catch(Exception e)
    {
      assertTrue(false);
      e.printStackTrace();
    }
  }
}
