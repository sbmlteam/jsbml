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
package org.sbml.jsbml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class TestSemanticsMathML {

  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    String mathml = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">"
        + "  <semantics>"
        + "    <exponentiale/>"
        + "    <annotation encoding=\"infix-input\">ExponentialE</annotation>"
        + "    <annotation encoding=\"infix-output\">exponentiale</annotation>"
        + "    <annotation-xml encoding=\"infix-output\">exponentiale</annotation-xml>"
        + "  </semantics>"
        + "</math>";


    ASTNode node = ASTNode.readMathMLFromString(mathml);

    System.out.println("nb semantic annotations = " + node.getNumSemanticsAnnotations());

    System.out.println(node.toMathML());

    SBMLDocument doc = new SBMLDocument(3, 1);

    doc.createModel().createReaction().createKineticLaw().setMath(node);

    try {
      System.out.println(new SBMLWriter().writeSBMLToString(doc));
    } catch (SBMLException exc) {
      exc.printStackTrace();
    } catch (XMLStreamException exc) {
      exc.printStackTrace();
    }

    int n = doc.checkConsistency();

    if (n > 0) {
      doc.printErrors(System.out);
    }
  }

}
