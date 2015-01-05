/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.test;

import org.sbml.jsbml.SBO;
import org.sbml.jsbml.ontology.Term;

/**
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 28.10.2013
 */
public class SBOtest {


  /**
   * Tests method.
   * 
   * @param args no argument are processed.
   */
  public static void main(String[] args) {

    int i = 0;
    for (Term term : SBO.getTerms()) {
      if (!term.isObsolete()) {
        System.out.printf("%s\n\n", Term.printTerm(term));
        i++;
      }
    }
    System.out.println("\nThere is " + i + " terms in the SBO ontology.");

    System.out.println("\nPrinting the SBO ontology tree:");
    Term sboRoot = SBO.getSBORoot();

    for (Term term : sboRoot.getChildren()) {
      System.out.println("   " + term.getId() + " - " + term.getName());

      printChildren(term, "   ");
    }

    System.out.println("ANTISENSE_RNA = " + SBO.getAntisenseRNA());

    // TODO: Shall we catch the exception thrown by the biojava getTerm() method or let it like that ??
    System.out.println("Search null Term: " + SBO.getTerm(null));
    System.out.println("Search invalid id Term: " + SBO.getTerm("SBO:004"));
    System.out.println("Search invalid id Term: " + SBO.getTerm("0000004"));
  }

  /**
   * Prints to the console all the sub-tree of terms, starting from the children
   * of the given parent term.
   * 
   * @param parent the parent term
   * @param indent the indentation to use to print the children terms
   */
  private static void printChildren(Term parent, String indent) {
    for (Term term : parent.getChildren()) {
      System.out.println(indent + "   " + term.getId() + " - " + term.getName());
      printChildren(term, indent + "   ");
    }
  }

}
