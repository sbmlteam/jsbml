/*
 * $Id: RecursionTest.java 2088 2014-11-16 06:56:46Z kofiav $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/core/test/org/sbml/jsbml/math/test/RecursionTest.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
package org.sbml.jsbml.math.test;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2088 $
 * @since 1.0
 * @date 16.10.2014
 */
public class RecursionTest {

  /**
   * @param args
   * @throws ParseException
   */
  public static void main(String[] args) throws ParseException {
//    ASTNode ast = ASTNode.parseFormula("3 * 1/1");
//    System.out.println(ast.getType());
//    JOptionPane.showMessageDialog(null, new JScrollPane(new JTree(ast)));
//    System.out.println(ast.toFormula());
    
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
    
    JOptionPane.showMessageDialog(null, new JScrollPane(new JTree(indexMath)));

  }

}
