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

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCSpeciesPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.util.CobraUtil;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.converters.SBMLConverter;

/**
 * Converts SBML FBC Version 1 files to old COBRA SBML files.
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.3
 */
@SuppressWarnings("deprecation")
public class FbcV1ToCobraConverter implements SBMLConverter {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#convert(org.sbml.jsbml.SBMLDocument)
   */
  @Override
  public SBMLDocument convert(SBMLDocument sbmlDocument) throws SBMLException {
    // only SBMLDocuments with level 3 version 1 and package version 1 are converted
    if (sbmlDocument.isPackageEnabled(FBCConstants.getNamespaceURI(3, 1, 1))) {
      Model model = sbmlDocument.getModel();
      
      //disable package FBC Version 1 and set SBMLDocument to level 2 and version 5 
      sbmlDocument.enablePackage(FBCConstants.getNamespaceURI(3, 1, 1), false);
      SBMLtools.setLevelAndVersion(sbmlDocument, 2, 5);
      
      for (Species species : model.getListOfSpecies()) {
        
        // reading the species attributes charge and chemicalFormula from every species, writing them into the notes and deleting the species attributes 
        if (species.isSetPlugin("fbc")) {
          FBCSpeciesPlugin fbcSpeciesPlugin = (FBCSpeciesPlugin)species.getPlugin("fbc");
          
          if (fbcSpeciesPlugin.isSetChemicalFormula()) {
            String chemicalFormula = fbcSpeciesPlugin.getChemicalFormula();
            Properties pElementsSpeciesNotes = new Properties();
            pElementsSpeciesNotes = CobraUtil.parseCobraNotes(species);
            if (pElementsSpeciesNotes.getProperty("FORMULA") != null) {
              pElementsSpeciesNotes.remove("FORMULA");
            }
            pElementsSpeciesNotes.setProperty("FORMULA", chemicalFormula);
            CobraUtil.writeCobraNotes(species, pElementsSpeciesNotes);
          }
        
          if (fbcSpeciesPlugin.isSetCharge()) {
            int charge = fbcSpeciesPlugin.getCharge();
            Properties pElementsSpeciesNotes = new Properties();
            pElementsSpeciesNotes = CobraUtil.parseCobraNotes(species);
            if (pElementsSpeciesNotes.getProperty("CHARGE") != null) {
              pElementsSpeciesNotes.remove("CHARGE");
            }
            pElementsSpeciesNotes.setProperty("CHARGE", Integer.toString(charge));
            CobraUtil.writeCobraNotes(species, pElementsSpeciesNotes);
          }
          species.unsetPlugin("fbc");
        }
      }
      
      // get lower and upper flux bound from the list of flux bounds and write them in the kinetic law; delete the list of flux bounds
      FBCModelPlugin fbcModelPlugin = (FBCModelPlugin)model.getPlugin("fbc");
      Set<String> listOFBReactionIds = new HashSet<String>();
      for (FluxBound fluxBound : fbcModelPlugin.getListOfFluxBounds()) {
        listOFBReactionIds.add(fluxBound.getReaction());
      }
      
      for (Reaction reaction : model.getListOfReactions()) {
        if (listOFBReactionIds.contains(reaction.getId())) {

          KineticLaw kineticLaw = new KineticLaw(reaction);
          ASTNode astNode = new ASTNode("FLUX_VALUE");
          kineticLaw.setMath(astNode);
          
          for (FluxBound fluxBound : fbcModelPlugin.getListOfFluxBounds()) {
            if (fluxBound.getReaction().equals(reaction.getId())) {
              
              if (fluxBound.getOperation().equals(FluxBound.Operation.LESS_EQUAL)) {
                reaction.getKineticLaw().createLocalParameter("UPPER_BOUND");
                reaction.getKineticLaw().getLocalParameter("UPPER_BOUND").setValue(fluxBound.getValue());
                reaction.getKineticLaw().getLocalParameter("UPPER_BOUND").setExplicitlyConstant(true);
                reaction.getKineticLaw().getLocalParameter("UPPER_BOUND").setUnits("mmol_per_gDW_per_hr");
              }
              
              if (fluxBound.getOperation().equals(FluxBound.Operation.GREATER_EQUAL)) {
                reaction.getKineticLaw().createLocalParameter("LOWER_BOUND");
                reaction.getKineticLaw().getLocalParameter("LOWER_BOUND").setValue(fluxBound.getValue());
                reaction.getKineticLaw().getLocalParameter("LOWER_BOUND").setExplicitlyConstant(true);
                reaction.getKineticLaw().getLocalParameter("LOWER_BOUND").setUnits("mmol_per_gDW_per_hr");
              } 
            }
          }
        }
        
        // unset attribute constant for products and reactants
        for (SpeciesReference speciesReference: reaction.getListOfProducts()) {
          speciesReference.unsetConstant();
        }
        for (SpeciesReference speciesReference: reaction.getListOfReactants()) {
          speciesReference.unsetConstant();
        }
        
      }
      fbcModelPlugin.unsetListOfFluxBounds();
    }
    return sbmlDocument;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#setOption(java.lang.String, java.lang.String)
   */
  @Override
  public void setOption(String name, String value) {
    // TODO Auto-generated method stub
    
  }

}
