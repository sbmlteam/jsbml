/*
 * $Id$
 * $Rev$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
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
package org.sbml.jsbml.test;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.parser.ParseException;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 0.8
 */
public class MathMLSpecialTest {

  public static void main(String[] args) {

    ASTNode formula_base = new ASTNode(Double.NaN);

    try {
      System.out.println("Test mathML Special numbers\n");
      System.out.println("Build ASTNode by hand\n");

      System.out.println("\nNaN formula = " + formula_base.toFormula());
      System.out.println("NaN formula = " + formula_base.toMathML());
      System.out.println("NaN formula = " + formula_base.toLaTeX());
      System.out.println("NaN formula = " + formula_base.toString());

      formula_base = new ASTNode(Double.POSITIVE_INFINITY);

      System.out.println("\nInfinity formula = "
          + formula_base.toFormula());
      System.out.println("Infinity formula = " + formula_base.toMathML());
      System.out.println("Infinity formula = " + formula_base.toLaTeX());
      System.out.println("Infinity formula = " + formula_base.toString());

      formula_base = new ASTNode(Double.NEGATIVE_INFINITY);

      System.out.println("\n-Infinity formula = "
          + formula_base.toFormula());
      System.out
      .println("-Infinity formula = " + formula_base.toMathML());
      System.out.println("-Infinity formula = " + formula_base.toLaTeX());
      System.out
      .println("-Infinity formula = " + formula_base.toString());

      System.out.println("\n\nBuild ASTNode by formula\n\n");

      formula_base = ASTNode.parseFormula("NaN");

      System.out.println("\nNaN formula = " + formula_base.toFormula());
      System.out.println("NaN formula = " + formula_base.toMathML());
      System.out.println("NaN formula = " + formula_base.toLaTeX());
      System.out.println("NaN formula = " + formula_base.toString());

      formula_base = ASTNode.parseFormula("Infinity");

      System.out.println("\nInfinity formula = "
          + formula_base.toFormula());
      System.out.println("Infinity formula = " + formula_base.toMathML());
      System.out.println("Infinity formula = " + formula_base.toLaTeX());
      System.out.println("Infinity formula = " + formula_base.toString());

      formula_base = ASTNode.parseFormula("-Infinity");

      System.out.println("\n-Infinity formula = "
          + formula_base.toFormula());
      System.out
      .println("-Infinity formula = " + formula_base.toMathML());
      System.out.println("-Infinity formula = " + formula_base.toLaTeX());
      System.out
      .println("-Infinity formula = " + formula_base.toString());

      System.out.println("\n\nBuild ASTNode by mathML\n\n");

      formula_base = JSBML.readMathMLFromString("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"> \n"
          + "  <notanumber/>\n" + "</math>\n");

      System.out.println("\nNaN formula = " + formula_base.toFormula());
      System.out.println("NaN formula = " + formula_base.toMathML());
      System.out.println("NaN formula = " + formula_base.toLaTeX());
      System.out.println("NaN formula = " + formula_base.toString());

      formula_base = JSBML.readMathMLFromString("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"> \n"
          + "  <infinity/>\n" + "</math>\n");

      System.out.println("\nInfinity formula = "
          + formula_base.toFormula());
      System.out.println("Infinity formula = " + formula_base.toMathML());
      System.out.println("Infinity formula = " + formula_base.toLaTeX());
      System.out.println("Infinity formula = " + formula_base.toString());

      formula_base = JSBML.readMathMLFromString("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"> \n"
          + "  <apply>/n    <minus/>\n    <infinity/>\n  </apply>/n"
          + "</math>\n");

      System.out.println("\n-Infinity formula = "
          + formula_base.toFormula());
      System.out.println("-Infinity formula = " + formula_base.toMathML());
      System.out.println("-Infinity formula = " + formula_base.toLaTeX());
      System.out
      .println("-Infinity formula = " + formula_base.toString());

      // Testing pi
      System.out.println("\n\nBuild ASTNode by hand\n");
      formula_base = new ASTNode(Math.PI);

      System.out.println("\npi formula = " + formula_base.toFormula());
      System.out.println("pi formula = " + formula_base.toMathML());
      System.out.println("pi formula = " + formula_base.toLaTeX());
      System.out.println("pi formula = " + formula_base.toString());

      System.out.println("\n\nBuild ASTNode by formula\n\n");

      formula_base = ASTNode.parseFormula("pi");

      System.out.println("\npi formula = " + formula_base.toFormula());
      System.out.println("pi formula = " + formula_base.toMathML());
      System.out.println("pi formula = " + formula_base.toLaTeX());
      System.out.println("pi formula = " + formula_base.toString());

      System.out.println("\n\nBuild ASTNode by mathML\n\n");

      formula_base = JSBML
          .readMathMLFromString("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"> \n"
              + "  <pi/>\n"
              + "</math>\n");

      System.out.println("\npi formula = " + formula_base.toFormula());
      System.out.println("pi formula = " + formula_base.toMathML());
      System.out.println("pi formula = " + formula_base.toLaTeX());
      System.out.println("pi formula = " + formula_base.toString());

      // Testing exponentiale
      System.out.println("Build ASTNode by hand, number\n");
      formula_base = new ASTNode(Math.E);

      System.out.println("\ne formula = " + formula_base.toFormula());
      System.out.println("e formula = " + formula_base.toMathML());
      System.out.println("e formula = " + formula_base.toLaTeX());
      System.out.println("e formula = " + formula_base.toString());


      System.out.println("\nBuild ASTNode by hand, type CONSTANT_E\n");

      formula_base = new ASTNode(ASTNode.Type.CONSTANT_E);

      System.out.println("\ne formula = " + formula_base.toFormula());
      System.out.println("e formula = " + formula_base.toMathML());
      System.out.println("e formula = " + formula_base.toLaTeX());
      System.out.println("e formula = " + formula_base.toString());


      System.out.println("\n\nBuild ASTNode by formula, e\n\n");

      formula_base = ASTNode.parseFormula("e");

      System.out.println("\ne formula = " + formula_base.toFormula());
      System.out.println("e formula = " + formula_base.toMathML());
      System.out.println("e formula = " + formula_base.toLaTeX());
      System.out.println("e formula = " + formula_base.toString());

      System.out.println("\n\nBuild ASTNode by formula, exponentiale\n\n");

      formula_base = ASTNode.parseFormula("exponentiale");

      System.out.println("\ne formula = " + formula_base.toFormula());
      System.out.println("e formula = " + formula_base.toMathML());
      System.out.println("e formula = " + formula_base.toLaTeX());
      System.out.println("e formula = " + formula_base.toString());

      System.out.println("\n\nBuild ASTNode by mathML\n\n");

      formula_base = JSBML
          .readMathMLFromString("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"> \n"
              + "  <exponentiale/>\n"
              + "</math>\n");

      System.out.println("\ne formula = " + formula_base.toFormula());
      System.out.println("e formula = " + formula_base.toMathML());
      System.out.println("e formula = " + formula_base.toLaTeX());
      System.out.println("e formula = " + formula_base.toString());


    } catch (SBMLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (ParseException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }


  }
}
