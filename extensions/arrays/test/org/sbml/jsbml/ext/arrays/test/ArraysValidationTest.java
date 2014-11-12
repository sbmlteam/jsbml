/*
 * $Id:  ArraysValidationTest.java 12:21:09 PM lwatanabe $
 * $URL: ArraysValidationTest.java $
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
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

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.validator.ArraysValidator;
import org.sbml.jsbml.math.parser.ParseException;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 16, 2014
 */
public class ArraysValidationTest {


  /**
   * Test validation where a dimension has non-constant size.
   */
  @Test
  public void test00() {
    System.out.println("Test 00");

    SBMLDocument doc = new SBMLDocument(3,1);

    Model model = doc.createModel();

    Species spec = new Species();

    Parameter param = new Parameter("n");

    param.setConstant(false);

    param.setValue(10);

    model.addSpecies(spec);

    model.addParameter(param);

    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

    spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

    Dimension dim = new Dimension();

    dim.setArrayDimension(0);

    dim.setSize("n");

    arraysSBasePlugin.addDimension(dim);

    ArraysValidator.validate(doc);

  }

  /**
   * Test whether the validator reports an error if something
   * that cannot have dimension has one.
   */
  @Test
  public void test01() {
    System.out.println("Test 01");

    SBMLDocument doc = new SBMLDocument(3,1);

    Model model = doc.createModel();

    Event event = new Event();

    Delay delay = new Delay();

    Trigger trigger = new Trigger();

    trigger.setMath(new ASTNode("true"));
    try {
      delay.setMath(ASTNode.parseFormula("3"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }

    event.setDelay(delay);

    event.setTrigger(trigger);

    model.addEvent(event);

    Parameter param = new Parameter("n");

    param.setConstant(false);

    param.setValue(10);

    model.addParameter(param);

    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(delay);

    delay.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

    Dimension dim = new Dimension();

    dim.setArrayDimension(0);

    dim.setSize("n");

    arraysSBasePlugin.addDimension(dim);

    ArraysValidator.validate(doc);

  }

  /**
   * Test validation where a dimension has non-constant size.
   */
  @Test
  public void test02() {
    System.out.println("Test 02");

    SBMLDocument doc = new SBMLDocument(3,1);

    Model model = doc.createModel();

    Species spec = new Species();

    Parameter param = new Parameter("n");

    param.setConstant(true);

    param.setValue(10);

    model.addSpecies(spec);

    model.addParameter(param);

    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

    spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

    Dimension dim = new Dimension();

    dim.setArrayDimension(2);

    dim.setSize("n");

    arraysSBasePlugin.addDimension(dim);

    ArraysValidator.validate(doc);

  }
}
