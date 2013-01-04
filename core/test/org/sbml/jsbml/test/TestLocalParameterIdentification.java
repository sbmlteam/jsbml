/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2013 jointly by the following organizations: 
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
package org.sbml.jsbml.test;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.Species;


/**
 * @author Roland Keller
 * @version $Rev$
 * @since 0.8
 * @date 10.08.2012
 */
public class TestLocalParameterIdentification {

  public static void main(String[] args) throws IOException, XMLStreamException {
    
    SBMLDocument doc = new SBMLDocument(2, 4);
    Model model = doc.createModel("model_test");
    Compartment c1 = model.createCompartment("c1");
    Species s1 = model.createSpecies("s1", c1);
    s1.setMetaId("meta_" + s1.getId());
    
    Species s2 = model.createSpecies("s2", c1);
    s2.setMetaId("meta_" + s2.getId());
    
    Reaction r = model.createReaction("r1");
    r.createReactant(s1);
    r.createProduct(s2);
    
    model.createParameter("k");
    
    KineticLaw kl = r.createKineticLaw();
    kl.createLocalParameter("k");
    ASTNode node = ASTNode.readMathMLFromString("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"> <apply><times/><ci> k </ci><ci> S1 </ci></apply></math>");
    kl.setMath(node);
    
    if(!(kl.getMath().getChild(0).getVariable() instanceof LocalParameter)) {
      System.out.println("The local parameter k is not found!");
    }
    
    
    doc = new SBMLReader().readSBML("core/files/test-models/00733-sbml-l2v4.xml");
    r = doc.getModel().getReaction("reaction1");
    kl = r.getKineticLaw();
    if(!(kl.getMath().getChild(1).getVariable() instanceof LocalParameter)) {
      System.out.println("The local parameter k is not found!");
    }
  }
}


