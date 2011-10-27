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
package org.sbml.jsbml.test;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.util.SubModel;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 0.8
 */
public class SubModelTest {

	public static void main(String[] args) throws XMLStreamException, SBMLException, IOException {
		
		if (args.length < 2) {
			System.out.println("Usage: java org.sbml.jsbml.test.SubModelTest sbmlInputName sbmlOutputName");
			System.exit(0);
		}

		// Getting the sbml file name from the arguments passed to the java program
		String fileName = args[0];

		// Reading the file and creating the jsbml objects structure
		SBMLDocument testDocument = new SBMLReader().readSBML(fileName);
		
		// Getting the model
		Model model = testDocument.getModel();
	
		// creating a variable to store all the ids of the reactions we want to keep
		ArrayList<Reaction> selectedReactions = new ArrayList<Reaction>();		
		
		// Doing a loop over the list of reactions
		for (Reaction reaction : model.getListOfReactions()) {
			
			// BEGIN : filtering
			
			// filtering the reaction as we want
			if (reaction.getName().contains("phosphorylation")) {
				// if the name of the reaction contain 'phosphorylation', we add the reaction to the selected ones
				selectedReactions.add(reaction);
			} else if (reaction.getSBOTermID().equals("SBO:0000216")) {
				// if the reaction is annotated with the SBO Id corresponding to phosphorylation,  we add the reaction to the selected ones
				selectedReactions.add(reaction);
			}
			
			// add/modify as needed the filtering
			
			// END : filtering. All the reactions added to the selectedReactionIds will be used
			// to create a new submodel
		}

		// Of course the filtering can be done on compartments, species, rules reactions or events
		// even if here we are using only reactions.
		
		// using the method filterList is an other way to filter a list on some specific criteria
		// model.getListOfReactions().filterList(new CVTermFilter(Qualifier.BQB_IS, "Any KEGG ID for ex"));
		// You can implement easily your own filters
		
		// creating the resulting model
		
		// Array of String that will hold the selected reaction ids 
		String[] selectedReactionIds = new String[selectedReactions.size()];
		
		// Doing a loop over the selected reactions to create the array of reaction ids
		int i = 0;
		for (Reaction reaction : selectedReactions) {
			selectedReactionIds[i] = reaction.getId();
			i++;
		}
		
		// calling the generateSubModel with the selected reaction ids.
		// We do not care about any specific compartments, species, rules or events so we can pass null for these.
		SBMLDocument subModel = SubModel.generateSubModel(model, null, null, selectedReactionIds, null, null);
		
		new SBMLWriter().write(subModel, args[1]);
		
	}
}
