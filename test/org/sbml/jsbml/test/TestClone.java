package org.sbml.jsbml.test;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

public class TestClone {
	
  public static void main(String[] args) throws SBMLException {
	  
	// TODO : transform this file into a proper junit test class that is included in the jsbml standard tests.
	// The junit test file would test uniqueness of metaids and ids in the model.  
	  
	// Setup : creating some simple objects 
    SBMLDocument doc=new SBMLDocument(2,4);
    Model m=doc.createModel("model1");
    Reaction r = m.createReaction("id1");
    SpeciesReference s=new SpeciesReference();
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
    
    try {
    	m.addReaction(clonedReaction);
    	throw new SBMLException("It should not be possible to add two elements with the same metaid in a SBML document !!");
    } catch (IllegalArgumentException e) {
    	// success, the exception should be thrown there
    }

    // setting a new unique metaid to the reaction
    clonedReaction.setMetaId("meta3");    

    System.out.println("Cloned reaction metaid and id                : " + clonedReaction.getMetaId() + " " + clonedReaction.getId());
    
    operationSuccessful = m.addReaction(clonedReaction);
    
    if (operationSuccessful != true) {
    	throw new SBMLException("It should be possible to add a reaction with an unique id and metaid to a model !!");
    }
    
    System.out.println("Cloned reaction parent and model should be set  : " + clonedReaction.getParent() + " " + clonedReaction.getModel());
    System.out.println("Model.getReaction(0) id                         : " + m.getReaction(0).getId());
    System.out.println("Model.getReaction(1) id                         : " + m.getReaction(1).getId());
    System.out.println("Model.getReaction(1) parent                     : " + m.getReaction(1).getParent());

    
    // At the moment (rev 744), the species can be created and added to the model
    // This should no be possible as the id of the species is the same as the one 
    // from the first reaction !!
    Species s1 = m.createSpecies("id1");

    System.out.println("NB species in the model (Should be 0)          : " + m.getNumSpecies());
    System.out.println(s1.getParent() + " " + s1.getModel());

  }
}
