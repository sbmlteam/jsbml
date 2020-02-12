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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
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
import org.sbml.jsbml.ext.fbc.CobraConstants;
import org.sbml.jsbml.util.CobraUtil;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.converters.SBMLConverter;

/**
 * Converts SBML FBC Version 1 files to old COBRA SBML files.
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @author Thorsten Tiede
 * @since 1.3
 */
@SuppressWarnings("deprecation")
public class FbcV1ToCobraConverter implements SBMLConverter {

  Double defaultLowerBound = null;
  Double defaultUpperBound = null;
  
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
        if (species.isSetPlugin(FBCConstants.shortLabel)) {
          FBCSpeciesPlugin fbcSpeciesPlugin = (FBCSpeciesPlugin)species.getPlugin(FBCConstants.shortLabel);
          
          if (fbcSpeciesPlugin.isSetChemicalFormula()) {
            String chemicalFormula = fbcSpeciesPlugin.getChemicalFormula();
            Properties pElementsSpeciesNotes = new Properties();
            pElementsSpeciesNotes = CobraUtil.parseCobraNotes(species);
            if (pElementsSpeciesNotes.getProperty(CobraConstants.FORMULA) != null) {
              pElementsSpeciesNotes.remove(CobraConstants.FORMULA);
            }
            pElementsSpeciesNotes.setProperty(CobraConstants.FORMULA, chemicalFormula);
            CobraUtil.writeCobraNotes(species, pElementsSpeciesNotes);
          }
        
          if (fbcSpeciesPlugin.isSetCharge()) {
            int charge = fbcSpeciesPlugin.getCharge();
            Properties pElementsSpeciesNotes = new Properties();
            pElementsSpeciesNotes = CobraUtil.parseCobraNotes(species);
            if (pElementsSpeciesNotes.getProperty(CobraConstants.CHARGE) != null) {
              pElementsSpeciesNotes.remove(CobraConstants.CHARGE);
            }
            pElementsSpeciesNotes.setProperty(CobraConstants.CHARGE, Integer.toString(charge));
            CobraUtil.writeCobraNotes(species, pElementsSpeciesNotes);
          }
          species.unsetPlugin(FBCConstants.shortLabel);
        }
      }
      
      // Process reactions
      // 1. Collect lower and upper flux bounds from the list of flux bounds
      FBCModelPlugin fbcModelPlugin = (FBCModelPlugin)model.getPlugin(FBCConstants.shortLabel);
      Map<String, Double> reactionIdToLowerFluxBoundDoubleValueMap = new HashMap<>();
      Map<String, Double> reactionIdToUpperFluxBoundDoubleValueMap = new HashMap<>();
      
      for (FluxBound fluxBound : fbcModelPlugin.getListOfFluxBounds()) {
        if (fluxBound.getOperation().equals(FluxBound.Operation.GREATER_EQUAL))
        {
            reactionIdToLowerFluxBoundDoubleValueMap.put(fluxBound.getReaction(), Double.valueOf(fluxBound.getValue()));
        } else if (fluxBound.getOperation().equals(FluxBound.Operation.LESS_EQUAL))
        {
          reactionIdToUpperFluxBoundDoubleValueMap.put(fluxBound.getReaction(), Double.valueOf(fluxBound.getValue()));
        }
      }
      
      // 2. Create the kineticLaw element with FLUX_VALUE for each reaction
      for (Reaction reaction : model.getListOfReactions()) {
        KineticLaw kineticLaw = new KineticLaw(reaction);
        ASTNode astNode = new ASTNode(CobraConstants.FLUX_VALUE);
        kineticLaw.setMath(astNode);
        LocalParameter fluxValueParameter = reaction.getKineticLaw().createLocalParameter(CobraConstants.FLUX_VALUE);
        fluxValueParameter.setValue(0d);
        fluxValueParameter.setUnits(CobraConstants.mmol_per_gDW_per_hr);
        
        // 3. Assign lower and upper flux bounds if they were set, use defaults otherwise
        LocalParameter upperBoundParameter = reaction.getKineticLaw().createLocalParameter(CobraConstants.UPPER_BOUND);
        if (reactionIdToUpperFluxBoundDoubleValueMap.containsKey(reaction.getId()))
        {
          upperBoundParameter.setValue(reactionIdToUpperFluxBoundDoubleValueMap.get(reaction.getId()).doubleValue());
        } else if (this.defaultUpperBound != null)
        {
          upperBoundParameter.setValue(this.defaultUpperBound);
        } else {
          upperBoundParameter.setValue(Double.POSITIVE_INFINITY);
        }
        upperBoundParameter.setExplicitlyConstant(true);
        upperBoundParameter.setUnits(CobraConstants.mmol_per_gDW_per_hr);
        
        LocalParameter lowerBoundParameter = reaction.getKineticLaw().createLocalParameter(CobraConstants.LOWER_BOUND);
        if (reactionIdToLowerFluxBoundDoubleValueMap.containsKey(reaction.getId()))
        {
          lowerBoundParameter.setValue(reactionIdToLowerFluxBoundDoubleValueMap.get(reaction.getId()).doubleValue());
        } else if (this.defaultLowerBound != null)
        {
          lowerBoundParameter.setValue(this.defaultLowerBound);  
        } else {
          lowerBoundParameter.setValue(Double.NEGATIVE_INFINITY);
        }
        lowerBoundParameter.setExplicitlyConstant(true);
        lowerBoundParameter.setUnits(CobraConstants.mmol_per_gDW_per_hr);
               
        // 4. unset attribute constant for products and reactants
        for (SpeciesReference speciesReference: reaction.getListOfProducts()) {
          speciesReference.unsetConstant();
        }
        for (SpeciesReference speciesReference: reaction.getListOfReactants()) {
          speciesReference.unsetConstant();
        }
        
      }
      // 5. unset List of flux bounds
      fbcModelPlugin.unsetListOfFluxBounds();
    }
   
    // TODO: it is unclear whether this should be done
    sbmlDocument.removeDeclaredNamespaceByPrefix(FBCConstants.shortLabel);
    // TODO: the SBMLDocumentAttriute fbc:required="false" still remains and it is unclear how to remove it
    return sbmlDocument;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#setOption(java.lang.String, java.lang.String)
   */
  @Override
  public void setOption(String name, String value) {
    if (value == null) return;
    if (name.equals(CobraConstants.DEFAULT_LOWER_BOUND_NAME)) {
      try {
        this.defaultLowerBound = Double.valueOf(value);
      } catch (NumberFormatException e) {
        this.defaultLowerBound = null;
      }
    }
    if (name.equals(CobraConstants.DEFAULT_UPPER_BOUND_NAME)) {
      try {
        this.defaultUpperBound = Double.valueOf(value);
      } catch (NumberFormatException e) {
        this.defaultUpperBound = null;
      }
    }
  }

}
