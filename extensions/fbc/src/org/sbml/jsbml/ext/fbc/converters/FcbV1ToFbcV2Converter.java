/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc.converters;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.util.CobraUtil;
import org.sbml.jsbml.util.converters.SBMLConverter;
import org.sbml.jsbml.util.filters.Filter;

/**
 * Converts SBML FBC Version 1 files to SBML FBC Version 2.
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.3
 */

@SuppressWarnings("deprecation")
public class FcbV1ToFbcV2Converter implements SBMLConverter {
  
  String userKey = null;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#convert(org.sbml.jsbml.SBMLDocument)
   */

  @Override
  public SBMLDocument convert(SBMLDocument sbmlDocument) throws SBMLException {
    if ( sbmlDocument.isPackageEnabled(FBCConstants.getNamespaceURI(3, 1, 1))) { 
  
      
      // TODO - it would probably be useful to have an utility method to do all of that automatically
      sbmlDocument.enablePackage(FBCConstants.getNamespaceURI(3, 1, 1), false);
      sbmlDocument.enablePackage(FBCConstants.getNamespaceURI(3, 1, 2));
      
      // set SBMLDocument to fbcV2
      // settings first the fbc plugins package version to avoid exception in org.sbml.jsbml.AbstractSBase.setPackageVersion
      sbmlDocument.filter(new Filter() {
        
        @Override
        public boolean accepts(Object o) {
          if (o instanceof SBase) {
            SBase sBase = (SBase) o;
            
            if (sBase.getNumPlugins() > 0) {
              SBasePlugin sBasePlugin = sBase.getPlugin("fbc");
              
              if (sBasePlugin != null) {
                if (sBasePlugin.getPackageName().equals("fbc")) {
                  sBasePlugin.setPackageVersion(2);
                }
                if (! sBasePlugin.getElementNamespace().equals(FBCConstants.namespaceURI_L3V1V2)) {
                  ((AbstractSBasePlugin) sBasePlugin).setNamespace(null);
                  ((AbstractSBasePlugin) sBasePlugin).setNamespace(FBCConstants.namespaceURI_L3V1V2);
                }                
              }
            }            
          }
          
          return false;
        }
      });

      // settings the fbc SBase package version to avoid exception 
      sbmlDocument.filter(new Filter() {
        
        @Override
        public boolean accepts(Object o) {
          if (o instanceof SBase) {
            SBase sBase = (SBase) o;
            if (sBase.getPackageName().equals("fbc")) {
              if (sBase.getPackageVersion() != 2) {
                sBase.setPackageVersion(2);
              }
              if (! sBase.getNamespace().equals(FBCConstants.namespaceURI_L3V1V2)) {
                ((AbstractSBase) sBase).setNamespace(null);
                ((AbstractSBase) sBase).setNamespace(FBCConstants.namespaceURI_L3V1V2);
              }
            }
          }
      
          return false;
        }
      });
      
      // set fbc:strict = true
      Model model = sbmlDocument.getModel();
      FBCModelPlugin fbcModelPlugin = (FBCModelPlugin)model.getPlugin("fbc");
      if (!fbcModelPlugin.isSetStrict()) {
        fbcModelPlugin.setStrict(true);
      }
      
      // add lower and upper flux bounds to the reactions; add flux bounds to list of parameters; delete the list of flux bounds 
      for (Reaction reaction : model.getListOfReactions()) {
        FBCReactionPlugin fbcReactionPlugin = (FBCReactionPlugin)reaction.getPlugin("fbc");
        List<FluxBound> fluxBList = new ArrayList<FluxBound>();
        for (FluxBound fluxBound : fbcModelPlugin.getListOfFluxBounds()) {
          if (fluxBound.getReaction().equals(reaction.getId())) {
            if (fluxBound.getOperation() == FluxBound.Operation.GREATER_EQUAL) {
              fbcReactionPlugin.setLowerFluxBound("fb_" + fluxBound.getReaction() + "_" + fluxBound.getOperation());
              Parameter parameter = new Parameter(3,1);
              parameter.setValue(fluxBound.getValue());
              parameter.setId("fb_" + fluxBound.getReaction() + "_" + fluxBound.getOperation());
              parameter.setSBOTerm("SBO:0000625");
              parameter.setConstant(true);
              model.addParameter(parameter);
            }
              else {
                fbcReactionPlugin.setUpperFluxBound("fb_" + fluxBound.getReaction() + "_" + fluxBound.getOperation());
                Parameter parameter = new Parameter(3,1);
                parameter.setValue(fluxBound.getValue());
                parameter.setId("fb_" + fluxBound.getReaction() + "_" + fluxBound.getOperation());
                parameter.setSBOTerm("SBO:0000625");
                parameter.setConstant(true);
                model.addParameter(parameter);
            }
            fluxBList.add(fluxBound);
          }  
        }
        for (FluxBound fluxBound : fluxBList) {
          fbcModelPlugin.removeFluxBound(fluxBound);
        }
      }

      // transfer the gene associations from the notes to fbc:geneProductAssociation in reactions and fbc:listOfGeneProducts in model
      Properties pElementsNote = new Properties();
      
      Pattern p;
      if (userKey != null) {
      // userKey is a way of writing “gene association” specified by the user    
       p = Pattern.compile(userKey);
      } else {
      // regular expression to match the most common ways of writing “gene association”  
       p = Pattern.compile("(?i)gene[\\-_ ]*association");
      }
      
      for (Reaction reaction : model.getListOfReactions()) {
        pElementsNote = CobraUtil.parseCobraNotes(reaction);
        String geneAssociation = null;
        Enumeration<?> keys = pElementsNote.propertyNames();
        String key;
        for (;keys.hasMoreElements();) {
          key = (String)keys.nextElement();
          Matcher m = p.matcher(key);
          if (m.matches()) {
            if (pElementsNote.getProperty(key) != null) {
              geneAssociation = pElementsNote.getProperty(key);
            }
          }
        }
        
        if (geneAssociation != null && geneAssociation.trim().length() > 0) {
          GPRParser.parseGPR(reaction, geneAssociation, false, false);
        }
      }
    }
    
    return sbmlDocument;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#setOption(java.lang.String)
   */
  @Override
  public void setOption(String name, String value) {
    this.userKey = value;
  }

}
