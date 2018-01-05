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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;


/**
 * Test the writing and reading of arrays models.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArraysWriteTest {

  /**
   * This is the file we are dealing with
   */
  final String path = System.getProperty("java.io.tmpdir") + File.separator + "jsbml-arrays-example.xml";


  /**
   * Constructs the model before the testing.
   * 
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    SBMLDocument doc = new SBMLDocument(3,1);
    Model model = doc.createModel();

    Parameter n = new Parameter("n");
    n.setValue(10);
    model.addParameter(n);

    Parameter X = new Parameter("X");

    model.addParameter(X);
    ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);

    X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);

    Dimension dimX = new Dimension("i");
    dimX.setSize(n.getId());
    dimX.setArrayDimension(0);

    arraysSBasePluginX.addDimension(dimX);

    Parameter Y = new Parameter("Y");

    model.addParameter(Y);
    ArraysSBasePlugin arraysSBasePluginY = new ArraysSBasePlugin(Y);

    Y.addExtension(ArraysConstants.shortLabel, arraysSBasePluginY);
    Dimension dimY = new Dimension("i");
    dimY.setSize(n.getId());
    dimY.setArrayDimension(0);

    arraysSBasePluginY.addDimension(dimY);

    AssignmentRule rule = new AssignmentRule();
    model.addRule(rule);
    rule.setMetaId("rule");

    ArraysSBasePlugin arraysSBasePluginRule = new ArraysSBasePlugin(rule);
    rule.addExtension(ArraysConstants.shortLabel, arraysSBasePluginRule);

    Dimension dimRule = new Dimension("i");
    dimRule.setSize(n.getId());
    dimRule.setArrayDimension(0);
    arraysSBasePluginRule.addDimension(dimRule);


    Index indexRule = new Index();
    indexRule.setArrayDimension(0);
    indexRule.setReferencedAttribute("variable");
    ASTNode indexMath = new ASTNode();

    indexMath = ASTNode.diff(new ASTNode(9), new ASTNode("i"));
    indexRule.setMath(indexMath);
    arraysSBasePluginRule.addIndex(indexRule);

    rule.setVariable("Y");
    ASTNode ruleMath = ASTNode.parseFormula("selector(X, i)");

    rule.setMath(ruleMath);

    SBMLWriter writer = new SBMLWriter();
    writer.writeSBMLToFile(doc, path);
  }


  /**
   * Test if given parameter's plugin was set properly.
   * @param model
   * @param id
   * @param arrayId
   * @param arrayName
   * @param arraySize
   * @param arrayDimension
   * @return
   */
  private boolean testParameterDimension(Model model, String id, String arrayId,
    String arrayName, String arraySize, int arrayDimension)
  {
    boolean result = true;

    Parameter p = model.getParameter(id);

    ArraysSBasePlugin arraysSBasePluginRule = (ArraysSBasePlugin) p.getExtension("arrays");

    Dimension dim = arraysSBasePluginRule.getDimension(arrayId);

    result &= dim.isSetName() ? dim.getName().equals(arrayName) : true;
    result &= dim.getSize().equals(arraySize);
    result &= dim.getArrayDimension() == arrayDimension;

    return result;

  }

  /**
   * Test if given rule's plugin was set properly.
   * @param model
   * @param child
   * @param arrayId
   * @param arrayName
   * @param arraySize
   * @param arrayDimension
   * @return
   */
  private boolean testRuleDimension(Model model, int child, String arrayId,
    String arrayName, String arraySize, int arrayDimension)
  {
    boolean result = true;

    Rule r = model.getRule(child);

    ArraysSBasePlugin arraysSBasePluginRule = (ArraysSBasePlugin) r.getExtension("arrays");

    Dimension dim = arraysSBasePluginRule.getDimension(arrayId);

    result &= dim.isSetName() ? dim.getName().equals(arrayName) : true;
    result &= dim.getSize().equals(arraySize);
    result &= dim.getArrayDimension() == arrayDimension;

    return result;

  }

  /**
   * Test if given rule's index was set properly.
   * @param model
   * @param child
   * @param arraySize
   * @param arrayDimension
   * @param arrayMath
   * @return
   */
  private boolean testRuleIndex(Model model, int child, String arraySize, int arrayDimension, ASTNode arrayMath)
  {

    Rule r = model.getRule(child);

    ArraysSBasePlugin arraysSBasePluginRule = (ArraysSBasePlugin) r.getExtension("arrays");

    Index index = arraysSBasePluginRule.getIndex(arrayDimension, "variable");

    ASTNode math = index.getMath();

    return math.equals(arrayMath);

  }

  /**
   * Test if the model that was constructed in the setUp can be properly read.
   * @throws IOException
   */
  @Test
  public void arrayReadTest() throws IOException {
    try {
      SBMLDocument doc = SBMLReader.read(new File(path));
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      Model model = doc.getModel();
      assertTrue(testParameterDimension(model, "X", "i", null, "n", 0));
      assertTrue(testParameterDimension(model, "Y", "i", null, "n", 0));
      assertTrue(testRuleDimension(model, 0, "i", null, "n", 0));
      assertTrue(testRuleIndex(model,0,"n", 0, ASTNode.diff(new ASTNode(9), new ASTNode("i"))));
    } catch (XMLStreamException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

}
