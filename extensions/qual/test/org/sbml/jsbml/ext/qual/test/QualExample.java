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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.qual.test;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.qual.FunctionTerm;
import org.sbml.jsbml.ext.qual.InputTransitionEffect;
import org.sbml.jsbml.ext.qual.OutputTransitionEffect;
import org.sbml.jsbml.ext.qual.QualConstants;
import org.sbml.jsbml.ext.qual.QualModelPlugin;
import org.sbml.jsbml.ext.qual.QualitativeSpecies;
import org.sbml.jsbml.ext.qual.Transition;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.filters.CVTermFilter;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class QualExample {

  /**
   * @param args
   */
  public static void main(String args[]) {
    new QualExample();
  }

  /**
   * 
   */
  public QualExample() {
    int level = 3, version = 1;
    SBMLDocument doc = new SBMLDocument(level, version);
    Model model = doc.createModel("my_model");

    // Creating the qualitative model extension and adding it to the document
    QualModelPlugin qualPlugin = new QualModelPlugin(model);
    model.addExtension(QualConstants.getNamespaceURI(level, version), qualPlugin);

    // ListOfCompartments
    Compartment comp1 = model.createCompartment("comp1");
    comp1.setConstant(true);

    // ListOfQualitativeSpecies
    QualitativeSpecies g0 = qualPlugin.createQualitativeSpecies("G0", comp1, false);
    QualitativeSpecies g1 = qualPlugin.createQualitativeSpecies("G1", comp1, false);

    // ListOfTransitions
    Transition t1G1 = qualPlugin.createTransition("t1G1");

    // ListOfInputs
    t1G1.createInput("in0", g0, InputTransitionEffect.consumption);
    // ListOfOutputs
    t1G1.createOutput("ou1", g1, OutputTransitionEffect.assignmentLevel);

    // ListOfFunctionTerms
    FunctionTerm defTerm = new FunctionTerm(level, version);
    defTerm.setDefaultTerm(true);
    defTerm.setResultLevel(0);

    FunctionTerm ft1 = new FunctionTerm(level, version);
    ft1.setResultLevel(1);

    try {
      ft1.setMath(ASTNode.parseFormula("G0 > 2"));
    } catch (ParseException exc) {
      exc.printStackTrace();
    }

    // G0 and G1
    ASTNode andNode = new ASTNode(ASTNode.Type.LOGICAL_AND);
    andNode.addChild(new ASTNode(g0.getId()));
    andNode.addChild(new ASTNode(g1.getId()));

    t1G1.addFunctionTerm(defTerm);
    t1G1.addFunctionTerm(ft1);



    Species species = model.createSpecies("species", comp1);
    species.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS,
      "http://identifiers.org/go/GO:0006915",
        "http://identifiers.org/kegg.genes/hsa:321"));
    species.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_DESCRIBED_BY,
        "http://identifiers.org/pubmed/16333295"));
    species.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_IS_ENCODED_BY,
        "http://identifiers.org/ensembl/ENSG00000085662"));
    species.addCVTerm(new CVTerm(CVTerm.Qualifier.BQB_OCCURS_IN,
        "http://identifiers.org/kegg.reaction/R01787"));

    /* This method call will return a List of Species that are annotated with
     * the Qualifier 'occursIn' and a resource attached to this qualifier that
     * contains the String 'kegg'. */
    model.getListOfSpecies().filter(new CVTermFilter(CVTerm.Qualifier.BQB_OCCURS_IN, ".*kegg.*"));

  }

}
