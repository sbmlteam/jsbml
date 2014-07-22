/*
 * $Id$
 * $URL$
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

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.flattening.ArraysFlattening;
import org.sbml.jsbml.text.parser.ParseException;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jul 17, 2014
 */
public class FlatteningTest {

  @Test
  public void simpleTest() {
    try {
      SBMLDocument document = new SBMLDocument(3,1);

      Model model = document.createModel();

      Species spec = model.createSpecies("s");

      Parameter param = model.createParameter("n");

      param.setConstant(true);

      param.setValue(2);

      ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

      spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

      spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

      Dimension dim = arraysSBasePlugin.createDimension("i");

      dim.setArrayDimension(0);

      dim.setSize("n");


      Dimension dim2 = arraysSBasePlugin.createDimension("j");

      dim2.setArrayDimension(1);

      dim2.setSize("n");

      SBMLDocument flattened = ArraysFlattening.convert(document);

      //SBMLWriter.write(flattened, System.out, ' ', (short) 2);

      assertTrue(flattened.getModel().getSpeciesCount() == 4);

    } catch (SBMLException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  @Test
  public void testFlatteningRule() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      n.setValue(10);
      model.addParameter(n);

      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      InitialAssignment ia = model.createInitialAssignment();
      ia.setVariable("X");
      ia.setMath(ASTNode.parseFormula("{1,2,3,4,5,6,7,8,9,10}"));
      ArraysSBasePlugin iaArraysPlugin = new ArraysSBasePlugin(ia);
      ia.addExtension(ArraysConstants.shortLabel, iaArraysPlugin);
      Dimension iaDim = iaArraysPlugin.createDimension("i");
      iaDim.setArrayDimension(0);
      iaDim.setSize("n");
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);

      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);

      Dimension dimX = new Dimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);

      arraysSBasePluginX.addDimension(dimX);

      Parameter Y = new Parameter("Y");

      model.addParameter(Y);
      Y.setValue(2);
      ArraysSBasePlugin arraysSBasePluginY = new ArraysSBasePlugin(Y);

      Y.addExtension(ArraysConstants.shortLabel, arraysSBasePluginY);
      Dimension dimY = new Dimension("i");
      dimY.setSize(n.getId());
      dimY.setArrayDimension(0);

      arraysSBasePluginY.addDimension(dimY);

      AssignmentRule rule = new AssignmentRule();
      model.addRule(rule);

      ArraysSBasePlugin arraysSBasePluginRule = new ArraysSBasePlugin(rule);
      rule.addExtension(ArraysConstants.shortLabel, arraysSBasePluginRule);

      Dimension dimRule = new Dimension("i");
      dimRule.setSize(n.getId());
      dimRule.setArrayDimension(0);
      arraysSBasePluginRule.addDimension(dimRule);


      Index indexRule = arraysSBasePluginRule.createIndex();
      indexRule.setArrayDimension(0);
      indexRule.setReferencedAttribute("variable");
      ASTNode indexMath = new ASTNode();

      indexMath = ASTNode.diff(new ASTNode(9), new ASTNode("i"));
      indexRule.setMath(indexMath);

      rule.setVariable("Y");
      ASTNode ruleMath = ASTNode.parseFormula("selector(X, i)");

      rule.setMath(ruleMath);
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      assertTrue(flattened.getModel().getParameterCount() == 21);
      assertTrue(flattened.getModel().getRuleCount() == 10);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

//TODO: test species reference index
  @Test
  public void testReaction() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(2);

      Parameter X = new Parameter("X");
      model.addParameter(X);
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);
      Dimension dimX = arraysSBasePluginX.createDimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);
      X.setValue(1);
      
      InitialAssignment ia = model.createInitialAssignment();
      ia.setVariable("X");
      ia.setMath(ASTNode.parseFormula("{0.1, 0.01}"));

      Species A = model.createSpecies("A");
      A.setValue(5);
      Species B = model.createSpecies("B");
      B.setValue(5);

      Reaction r = model.createReaction();
      r.createReactant(A);
      r.createProduct(B);
      r.setId("reaction");

      KineticLaw k = r.createKineticLaw();
      k.setMath(ASTNode.parseFormula("B - X*A"));
      ArraysSBasePlugin reactPlugin = new ArraysSBasePlugin(r);
      r.addExtension(ArraysConstants.shortLabel, reactPlugin);
      Dimension dim = reactPlugin.createDimension("i");
      dim.setSize(n.getId());
      dim.setArrayDimension(0);
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testEvent() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(3);

      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);
      Dimension dimX = arraysSBasePluginX.createDimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);

      Event e = model.createEvent();
      e.setId("event");
      Delay delay = e.createDelay();
      delay.setMath(ASTNode.parseFormula("{1,2,3}"));
      e.setDelay(delay);
      Trigger trigger = e.createTrigger();
      trigger.setMath(ASTNode.parseFormula("{true, true, true}"));
      EventAssignment assign = e.createEventAssignment();
      assign.setMath(ASTNode.parseFormula("{1, 10, 100}"));
      assign.setVariable("X");

      ArraysSBasePlugin eventAssignPlugin = new ArraysSBasePlugin(assign);
      assign.addExtension(ArraysConstants.shortLabel, eventAssignPlugin);
      Dimension dimAssign = eventAssignPlugin.createDimension("i");
      dimAssign.setSize(n.getId());
      dimAssign.setArrayDimension(0);

      ArraysSBasePlugin eventPlugin = new ArraysSBasePlugin(e);
      e.addExtension(ArraysConstants.shortLabel, eventPlugin);
      Dimension dim = eventPlugin.createDimension("i");
      dim.setSize(n.getId());
      dim.setArrayDimension(0);


      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

}
