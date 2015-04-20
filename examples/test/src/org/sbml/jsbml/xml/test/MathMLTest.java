/*
 * $Id: MathMLTest.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/examples/test/src/org/sbml/jsbml/xml/test/MathMLTest.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev: 2109 $
 */
public class MathMLTest {

  /**
   * 
   */
  public MathMLTest() {
    int level = 3;
    int version = 1;
    SBMLDocument doc = new SBMLDocument(level, 1);
    Model m = doc.createModel("id");
    FunctionDefinition fd = m.createFunctionDefinition("fd");
    ASTNode math = new ASTNode(Type.LAMBDA, fd);
    math.addChild(new ASTNode("x", fd));
    ASTNode pieces = ASTNode.piecewise(new ASTNode(3, fd), ASTNode.lt("x",
      ASTNode.abs(Double.NEGATIVE_INFINITY, fd)), ASTNode.times(
        new ASTNode(5.3, fd), ASTNode.log(new ASTNode(8, fd))));
    pieces = ASTNode.times(pieces, ASTNode.root(new ASTNode(2, fd),
      new ASTNode(16, fd)));
    math.addChild(pieces);
    fd.setMath(math);
    System.out.println(math.toMathML());

    Species species = m.createSpecies("spec");
    Reaction r = m.createReaction("r");
    r.addReactant(new SpeciesReference(species));
    KineticLaw kl = new KineticLaw(level, version);
    math = new ASTNode(fd, kl);
    math.addChild(new ASTNode(species, kl));
    math = ASTNode.times(math, new ASTNode(3.7, 8, kl));
    kl.setMath(math);
    r.setKineticLaw(kl);

    System.out.println(math.toMathML());

    try {
      new SBMLWriter().write(doc, System.out);
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (SBMLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param args
   */
  public static void main(String args[]) {
    new MathMLTest();
  }

}
