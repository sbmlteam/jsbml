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
    QualitativeSpecies g0 = qModel.createSpecies("GO", true, comp1.getName(), false);
    g0.setName("");
    g0.setMaxLevel(1);
    g0.setInitialLevel(0);
    
    QualitativeSpecies g1 = qModel.createSpecies("G1", false, comp1.getName(), false);
    g1.setName("G1 name");
    g1.setMaxLevel(3);
    g1.setInitialLevel(1);


    QualitativeSpecies g2 = qModel.createSpecies("G2", false, comp1.getName(), false);
    g2.setName("");
    g2.setMaxLevel(2);
    g2.setInitialLevel(2);

    QualitativeSpecies g3 = qModel.createSpecies("G3", false, comp1.getName(), true);
    g3.setName("");
    g3.setMaxLevel(1);
    g3.setInitialLevel(1);

    // ListOfTransitions
    Transition tr_g1 = qModel.createTransition("tr_G1");
    tr_g1.setTemporisationType(TemporisationType.priority);
    
    //// ListOfInputs
    Input in0 = tr_g1.createInput(g0.getId(), InputTransitionEffect.consumption, Sign.dual);
    Input in2 = tr_g1.createInput(g2.getId(), InputTransitionEffect.none);
    Input in3 = tr_g1.createInput(g3.getId(), InputTransitionEffect.none);
    
    //// ListOfOutputs
    Output out1 = tr_g1.createOutput(g1.getId(), OutputTransitionEffect.assignmentLevel);
    
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
    
    Transition tr2 = qModel.createTransition("tr2", Sign.negative, in3, out1);
    
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
    
    String outFile = "testFile.xml";
   
    try {
       
       SBMLWriter.write(sbmlDoc, outFile, "BuildToyModelTest", "1");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
