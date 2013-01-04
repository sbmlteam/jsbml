/*
 * $Id$
 * $URL$
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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 0.8
 */
public class TestClone {
	
  public static void main(String[] args) throws SBMLException {
	  
	// TODO : transform this file into a proper junit test class that is included in the jsbml standard tests.
	// The junit test file would test uniqueness of metaids and ids in the model.  
	  
	// Setup : creating some simple objects 
    SBMLDocument doc=new SBMLDocument(2,4);
    Model m=doc.createModel("model1");
    Reaction r = m.createReaction("id1");
    SpeciesReference s = new SpeciesReference();
    s.setMetaId("meta2");
    r.addReactant(s);
    r.setMetaId("meta");

    System.out.println("Reaction parent and model                    : " + r.getParent() + " " + r.getModel() + "\n");

    Reaction clonedReaction = r.clone();
    
    // Here everything is fine, the cloned reaction get created with all
    // the appropriate fields but the parent that is set to null for all cloned JSBML objects
    // (any objects that inherit from AbstracTreeNode)
    // That is why the getParent() and getModel() are returning null as they should
    System.out.println("Cloned reaction metaid and id                : " + clonedReaction.getMetaId() + " " + clonedReaction.getId());
    System.out.println("Cloned reaction metaid of the first reactant : " + clonedReaction.getReactant(0).getMetaId());
    System.out.println("Cloned reaction parent and model             : " + clonedReaction.getParent() + " " + clonedReaction.getModel() + "\n");
    
    System.out.println("Trying to add the cloned reaction to the model, which should not be possible");
    // Trying to add the 
    boolean operationSuccessful = m.addReaction(clonedReaction);

    if (operationSuccessful != false) {
    	throw new SBMLException("It should not be possible to add two elements with the same id in a ListOf class !!");
    }
    // Here the parent is still null as it should as the cloned reaction
    // was not added to the model as her id is the same as the reaction 'r'
    System.out.println("Cloned reaction parent and model still null  : " + clonedReaction.getParent() + " " + clonedReaction.getModel() + "\n");

    // setting a new unit id the the cloned reaction
    clonedReaction.setId("id2");
    
    System.out.println("Trying to add the cloned reaction to the model, with a new unique id. It is still not possible as the metaids are not unique.");
    try {
    	m.addReaction(clonedReaction);
    	throw new SBMLException("It should not be possible to add two elements with the same metaid in a SBML document !!");
    } catch (IllegalArgumentException e) {
    	// success, the exception should be thrown there
    }

    // setting a new unique metaid to the reaction but not it's sub-elements
    clonedReaction.setMetaId("meta3");   
    
    System.out.println("Trying to add the cloned reaction to the model, with a new unique id and metaid. It is still not possible as the metaids are not unique.");
    try {
    	operationSuccessful = m.addReaction(clonedReaction);
    	throw new SBMLException("It should not be possible to add a reaction that has any sub-elements with a non unique metaid !!");
    } catch (IllegalArgumentException e) {
    	// success, the exception should be thrown there
    }

    System.out.println("Trying to add the cloned reaction to the model, with a new unique id and metaid for all sub-elements.");
    // setting a new unique metaid to the reaction and all it's sub-elements
    clonedReaction.getReactant(0).setMetaId("meta4");

    // At the moment (rev 768), the reaction cannot be added to the model as the metaid 'meta3' as
    // been added in the list of metaids in the previous call to addReaction() where only the speciesReference id
    // was invalid
    try {
    	operationSuccessful = m.addReaction(clonedReaction);

    	if (operationSuccessful != true) {
    		throw new SBMLException("It should be possible to add a reaction with an unique ids and metaids to a model !!");
    	}
    } catch (IllegalArgumentException e) {
    	// bug here
    	System.out.println("Bug detected : there is a problem in the recursive adding of metaids !!!");
    	System.out.println("The reaction was not added to the model where it should have been");
    }

    System.out.println("Cloned reaction parent and model should be set  : " + clonedReaction.getParent() + " " + clonedReaction.getModel());
    System.out.println("Model.getReaction(0) id                         : " + m.getReaction(0).getId());
    System.out.println("Model.getReaction(1)                            : " + m.getReaction(1));
    // System.out.println("Model.getReaction(1) parent                     : " + m.getReaction(1).getParent());

    
    // At the moment (rev 744), the species can be created and added to the model
    // This should no be possible as the id of the species is the same as the one 
    // from the first reaction !!
    Species s1 = m.createSpecies("id1");

    System.out.println("\n\nNB species in the model (Should be 0)          : " + m.getNumSpecies());
    System.out.println(s1.getParent() + " " + s1.getModel());

    if (m.getNumSpecies() > 0) {
    	// bug here
    	System.out.println("Bug detected :  it should no be possible as the id of the species is the same as the one from the first reaction !!!");    	
    }
  }
}
