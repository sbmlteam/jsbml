/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2017 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc.converters;

import java.io.IOException;
import java.util.Properties;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.TidySBMLWriter;
import org.sbml.jsbml.ext.fbc.FBCSpeciesPlugin;
import org.sbml.jsbml.util.CobraUtil;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.converters.SBMLConverter;

// kommentar was der code tut

/**
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public class CobraToFbcV1Converter implements SBMLConverter {
  // public void cobraToFbcV1(SBMLDocument cobraSBMLdocument) {
  //
  // }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#convert(org.sbml.jsbml.
   * SBMLDocument)
   */
  @Override
  public SBMLDocument convert(SBMLDocument doc) throws SBMLException {
    // TODO Auto-generated method stub
    return null;
  }


  public static void main(String[] args) throws XMLStreamException, IOException {
    System.out.println("TEST CobraToFbcV1Converter TEST");
    
    // read document 
    String folder = "C:\\Users\\TMH\\Desktop\\JSBML\\Daten_zu_cobraToFbcV2Converterjava\\alte_COBRA_Dateien\\test_set";
    String filename = "uterus__post_menopause_cells_in_endometrial_stroma.xml";
    
    SBMLReader sbmlReader = new SBMLReader();
    SBMLDocument sbmlDocument = sbmlReader.readSBMLFromFile(folder + "\\" + filename);
 
    Properties pElementsNote = new Properties();
    Model model = sbmlDocument.getModel();
    // only SBMLDocuments with version smaller than three are converted
    if (sbmlDocument.getLevel() < 3) {
    // initialize default values for species attributes 
    for (Species species : model.getListOfSpecies()) {
      if (species.isSetHasOnlySubstanceUnits() == false) {
        species.setHasOnlySubstanceUnits(false);
      }
      if (species.isSetBoundaryCondition() == false) {
        species.setBoundaryCondition(false);
      }
      if (species.isSetConstant() == false) {
        species.setConstant(false);
      }
    }
    // initialize default values for reaction attributes reversible and fast
    for (Reaction reaction : model.getListOfReactions()) {
      if (reaction.isSetReversible() == false) {
        reaction.setReversible(true);
      }
      if (reaction.isSetFast() == false) {
        reaction.setFast(false);
      }    
    }
    //set SBMLDocument to level 3 version 1
    SBMLtools.setLevelAndVersion(sbmlDocument, 3, 1);
     
    //parse the COBRA SBML files and extract the values for formula and charge
    for (Species species : model.getListOfSpecies()) {
      pElementsNote = CobraUtil.parseCobraNotes(species);
      FBCSpeciesPlugin fbcSpeciesPlugin = (FBCSpeciesPlugin)species.getPlugin("fbc");
      
      if (pElementsNote.getProperty("FORMULA") != null) {
        System.out.println(pElementsNote.getProperty("FORMULA"));
        fbcSpeciesPlugin.setChemicalFormula(pElementsNote.getProperty("FORMULA"));
      }
      if (pElementsNote.getProperty("CHARGE") != null) {
        if (species.isSetCharge() == true) {
          species.unsetCharge();
        }
        // charge ist auch eine attribute von sepecies kann als auch da sein; wenn da löschen nach schreiben
        System.out.println(pElementsNote.getProperty("CHARGE"));
        fbcSpeciesPlugin.setCharge(Integer.parseInt(pElementsNote.getProperty("CHARGE")));
      }
    }  
    // write document
    TidySBMLWriter tidySBMLWriter = new TidySBMLWriter();
    tidySBMLWriter.writeSBMLToFile(sbmlDocument,folder + "\\" + "mod_SBMLFBCV1_" + filename);
    }
    // return sbmlDocument;??
  }
}
