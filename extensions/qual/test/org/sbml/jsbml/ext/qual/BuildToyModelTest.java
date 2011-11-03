/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.qual;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.test.gui.JSBMLvisualizer;
import org.sbml.jsbml.text.parser.ParseException;

/**
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 27.10.2011
 */
public class BuildToyModelTest {

  public static final String QUAL_NS = "http://www.sbml.org/sbml/level3/version1/qual/version1";
  
  public static final String QUAL_NS_PREFIX = "qual";
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    SBMLDocument sbmlDoc = new SBMLDocument(3, 1);
    sbmlDoc.addDeclaredNamespace(QUAL_NS, QUAL_NS_PREFIX);
    sbmlDoc.addNamespace(QUAL_NS_PREFIX, "xmlns", QUAL_NS);
    
    //sbmlDoc.readAttribute("required", QUAL_NS_PREFIX, "true");
    
    Model model = new Model("m_default_name");
    model.addNamespace(QUAL_NS);
    QualitativeModel qModel = new QualitativeModel(model);

    model.addExtension(QUAL_NS, qModel);

    //qModel.readAttribute("required", QUAL_NS_PREFIX, "true");

    sbmlDoc.setModel(model);

    // ListOfCompartments
    Compartment comp1 = model.createCompartment("comp1");
    comp1.setConstant(true);
    
    // ListOfQualitativeSpecies
    QualitativeSpecies g0 = new QualitativeSpecies();
    g0.setId("G0");
    g0.setName("");
    g0.setMaxLevel(1);
    g0.setInitialLevel(0);
    g0.setBoundaryCondition(true);
    g0.setCompartment(comp1.getName());
    g0.setConstant(false);

    QualitativeSpecies g1 = new QualitativeSpecies();
    g1.setId("G1");
    g1.setName("G1 name");
    g1.setMaxLevel(3);
    g1.setInitialLevel(1);
    g1.setBoundaryCondition(false);
    g1.setCompartment(comp1.getName());
    g1.setConstant(false);

    QualitativeSpecies g2 = new QualitativeSpecies();
    g2.setId("G2");
    g2.setName("");
    g2.setMaxLevel(2);
    g2.setInitialLevel(2);
    g2.setBoundaryCondition(false);
    g2.setCompartment(comp1.getName());
    g2.setConstant(false);

    QualitativeSpecies g3 = new QualitativeSpecies();
    g3.setId("G3");
    g3.setName("");
    g3.setMaxLevel(1);
    g3.setInitialLevel(1);
    g3.setBoundaryCondition(false);
    g3.setCompartment(comp1.getName());
    g3.setConstant(true);

    qModel.addQualitativeSpecies(g0);
    qModel.addQualitativeSpecies(g1);
    qModel.addQualitativeSpecies(g2);
    qModel.addQualitativeSpecies(g3);

    // ListOfTransitions
    Transition tr_g1 = qModel.createTransition("tr_G1");
    tr_g1.setTemporisationType(TemporisationType.priority);
    
    //// ListOfInputs
    Input in0 = new Input();
    in0.setQualitativeSpecies(g0.getId());
    in0.setTransitionEffect(InputTransitionEffect.consumption);

    Input in2 = new Input();
    in2.setQualitativeSpecies(g2.getId());
    in2.setTransitionEffect(InputTransitionEffect.none);

    Input in3 = new Input();
    in3.setQualitativeSpecies(g3.getId());
    in3.setTransitionEffect(InputTransitionEffect.none);
    
    tr_g1.addInput(in0);
    tr_g1.addInput(in2);
    tr_g1.addInput(in3);

    //// ListOfOutputs
    Output out1 = new Output();
    out1.setQualitativeSpecies(g1.getId());
    out1.setTransitionEffect(OutputTransitionEffect.assignmentLevel);
    
    tr_g1.addOutput(out1);
    
    //// ListOfFunctionTerms
    FunctionTerm defTerm = new FunctionTerm();
    defTerm.setDefaultTerm(true);
    defTerm.setResultLevel(0);

    FunctionTerm ft1 = new FunctionTerm();
    ft1.setResultLevel(1);

    ASTNode mathNode = null;
    try {
    	mathNode = ASTNode.parseFormula("G0 + 2");
        ft1.setMath(mathNode);
    } catch (ParseException e) {
    	e.printStackTrace();
    }
      
    tr_g1.addFunctionTerm(defTerm);
    tr_g1.addFunctionTerm(ft1);
    
    qModel.addTransition(tr_g1);
    
    try {
		new SBMLWriter().write(sbmlDoc, "testQual.xml");
	} catch (SBMLException e) {
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (XMLStreamException e) {
		e.printStackTrace();
	}
    
    new JSBMLvisualizer(sbmlDoc); 
  }

}
