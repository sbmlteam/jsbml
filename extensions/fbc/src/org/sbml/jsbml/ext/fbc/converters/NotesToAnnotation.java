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

package org.sbml.jsbml.ext.fbc.converters;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.CobraUtil;

/**
 * NotesToAnnotation transfers information from the notes to the annotations
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.4
 */

public class NotesToAnnotation {

  /**
   * Transfers information from the notes to the annotations.
   * 
   * @param sbmlDocument: the SBMLDocument where the information should be transfered from the notes, keys: a string array with the keys from the elements of the notes that should be transfered     
   * @throws IOException if an error occurs
   */   

  public SBMLDocument transfer(SBMLDocument sbmlDocument, String[] keys) throws IOException {

    // read list that connects the keys from the notes and the corresponding identifiers.org namespaces  
    Properties namespaces = new Properties();
    Path path = Paths.get(".\\extensions\\fbc\\src\\org\\sbml\\jsbml\\ext\\fbc\\converters\\identifiersOrgNamespace.txt");
    Reader identifiersReader = Files.newBufferedReader(path);
    namespaces.load(identifiersReader);

    // transfers from the notes of the species
    Model model = sbmlDocument.getModel();

    for (String key : keys) {

      for (Species species : model.getListOfSpecies()) {
        Properties pElementsNote = new Properties();
        pElementsNote = CobraUtil.parseCobraNotes(species);
        if (pElementsNote.getProperty(key) != null) {
          CVTerm cvTerm = new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/" + namespaces.getProperty(key) + "/" + pElementsNote.getProperty(key));
          Annotation annotation = new Annotation();
          annotation = species.getAnnotation();
          annotation.addCVTerm(cvTerm);
          species.setAnnotation(annotation);

          // TODO: offer to choose the qualifier (now always "is")      

        }
      }
    }

    // transfers from the notes of the reactions
    for (String key : keys) {

      for (Reaction reaction : model.getListOfReactions()) {
        Properties pElementsNote = new Properties();
        pElementsNote = CobraUtil.parseCobraNotes(reaction);
        if (pElementsNote.getProperty(key) != null) {
          CVTerm cvTerm = new CVTerm(CVTerm.Qualifier.BQB_IS, "http://identifiers.org/" + namespaces.getProperty(key) + "/" + pElementsNote.getProperty(key));
          Annotation annotation = new Annotation();
          annotation = reaction.getAnnotation();
          annotation.addCVTerm(cvTerm);
          reaction.setAnnotation(annotation);

          // TODO: offer to choose the qualifier (now always "is")       

        }
      }
    }    

    return sbmlDocument;
  }  
}
