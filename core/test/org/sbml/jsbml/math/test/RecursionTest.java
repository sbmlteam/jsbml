/*
 * $Id$
 * $URL$
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
import org.sbml.jsbml.math.ASTFactory;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2014
 */
public class RecursionTest {

  /**
   * @param args
   * @throws ParseException
   */
  public static void main(String[] args) throws ParseException {
    ASTNode ast = ASTNode.parseFormula("1/1");
    System.out.println(ast.getType());
    //JOptionPane.showMessageDialog(null, new JScrollPane(new JTree(ast)));
    System.out.println(ast.toFormula());
  }

}
