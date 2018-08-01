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

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.fbc.And;
import org.sbml.jsbml.ext.fbc.Association;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.ext.fbc.GeneProductRef;
import org.sbml.jsbml.ext.fbc.LogicalOperator;
import org.sbml.jsbml.ext.fbc.Or;
import org.sbml.jsbml.util.CobraUtil;
import org.sbml.jsbml.util.compilers.ConfigurableLogicalFormulaCompiler;
import org.sbml.jsbml.util.converters.SBMLConverter;
import org.sbml.jsbml.util.filters.Filter;

/**
 * Converts SBML FBC Version 2 files to SBML FBC Version 1.
 * 
 * @author Thomas Hamm
 * @author Nicolas Rodriguez
 * @since 1.3
 */
@SuppressWarnings("deprecation")
public class FbcV2ToFbcV1Converter implements SBMLConverter {

  String userKey = null;
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#convert(org.sbml.jsbml.SBMLDocument)
   */
  
  @Override
  public SBMLDocument convert(SBMLDocument sbmlDocument) throws SBMLException {
    Model model = sbmlDocument.getModel();
    FBCModelPlugin fbcModelPlugin = (FBCModelPlugin)model.getPlugin("fbc");

    // only SBMLDocuments with FBC Version 2 and "fbc:strict = true" are converted 
    if (sbmlDocument.isPackageEnabled(FBCConstants.getNamespaceURI(3, 1, 2)) && fbcModelPlugin.isSetStrict()) {
      //disable package FBC Version 2 and enable package FBC Version 1
      sbmlDocument.enablePackage(FBCConstants.getNamespaceURI(3, 1, 2), false);
      sbmlDocument.enablePackage(FBCConstants.getNamespaceURI(3, 1, 1));
      
      // set SBMLDocument to fbcV1
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
                  sBasePlugin.setPackageVersion(1);
                }
                if (! sBasePlugin.getElementNamespace().equals(FBCConstants.namespaceURI_L3V1V1)) {
                  ((AbstractSBasePlugin) sBasePlugin).setNamespace(null);
                  ((AbstractSBasePlugin) sBasePlugin).setNamespace(FBCConstants.namespaceURI_L3V1V1);
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
              if (sBase.getPackageVersion() != 1) {
                sBase.setPackageVersion(1);
              }
              if (! sBase.getNamespace().equals(FBCConstants.namespaceURI_L3V1V1)) {
                ((AbstractSBase) sBase).setNamespace(null);
                ((AbstractSBase) sBase).setNamespace(FBCConstants.namespaceURI_L3V1V1);
              }
            }
          }
          return false;
        }
      });
      
      // unset fbc:strict = true
      fbcModelPlugin.unsetStrict();
      
      // delete lower and upper flux bounds in the reactions; delete flux bounds from the list of parameters; create the list of flux bounds
      for (Reaction reaction : model.getListOfReactions()) {
        FBCReactionPlugin fbcReactionPlugin = (FBCReactionPlugin)reaction.getPlugin("fbc");
        String lowerFluxBound = fbcReactionPlugin.getLowerFluxBound();
        String upperFluxBound = fbcReactionPlugin.getUpperFluxBound();
        Set<String> parametersToDelete = new HashSet<String>();
        for (Parameter parameter : model.getListOfParameters()) {
          if (parameter.getId().equals(lowerFluxBound)) {
            FluxBound fluxBoundLo = new FluxBound();
            fluxBoundLo.setReaction(reaction.getId());
            fluxBoundLo.setOperation(FluxBound.Operation.GREATER_EQUAL);
            fluxBoundLo.setValue(parameter.getValue());
            fbcModelPlugin.addFluxBound(fluxBoundLo);
            parametersToDelete.add(parameter.getId());
          }
          
          if (parameter.getId().equals(upperFluxBound)) {
            FluxBound fluxBoundUp = new FluxBound();
            fluxBoundUp.setReaction(reaction.getId());
            fluxBoundUp.setOperation(FluxBound.Operation.LESS_EQUAL);
            fluxBoundUp.setValue(parameter.getValue());
            fbcModelPlugin.addFluxBound(fluxBoundUp);
            parametersToDelete.add(parameter.getId());  
          } 
        }
        for (String parameterToDelete : parametersToDelete) {
          model.removeParameter(parameterToDelete);
        }
        fbcReactionPlugin.unsetLowerFluxBound();
        fbcReactionPlugin.unsetUpperFluxBound();
      }
      
      // write the gene associations to the notes of every reaction; delete fbc:geneProductAssociation for every reaction; delete fbc:listOfGeneProducts in model
      for (Reaction reaction : model.getListOfReactions()) {
        FBCReactionPlugin fbcReactionPlugin = (FBCReactionPlugin)reaction.getPlugin("fbc");
        
        // if there is an old gene association entry in the notes of this reaction it will be deleted
        Properties pElementsReactionNotes = new Properties();
        Pattern p;
        if (userKey != null) {
        // userKey is a way of writing “gene association” specified by the user    
          p = Pattern.compile(userKey);
        } else {
        // regular expression to match the most common ways of writing “gene association”  
          p = Pattern.compile("(?i)gene[\\-_ ]*association");
        }
        pElementsReactionNotes = CobraUtil.parseCobraNotes(reaction);
        Enumeration<?> keys = pElementsReactionNotes.propertyNames();
        String key;
        for (;keys.hasMoreElements();) {
          key = (String)keys.nextElement();
          Matcher m = p.matcher(key);
          if (m.matches()) {
            pElementsReactionNotes.remove(key);
          }
        }
        
        if (fbcReactionPlugin.isSetGeneProductAssociation()) {
          FbcV2ToFbcV1Converter fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
          ASTNode geneAssociationString = fbcV2ToFbcV1Converter.processAssociation(fbcReactionPlugin.getGeneProductAssociation().getAssociation(), fbcModelPlugin);
          String gAString = geneAssociationString.toFormula(new ConfigurableLogicalFormulaCompiler());
          pElementsReactionNotes.setProperty("GENE_ASSOCIATION", gAString);
          CobraUtil.writeCobraNotes(reaction, pElementsReactionNotes);
          fbcReactionPlugin.unsetGeneProductAssociation();
        } else {
          pElementsReactionNotes.setProperty("GENE_ASSOCIATION", "");
          CobraUtil.writeCobraNotes(reaction, pElementsReactionNotes);
        }
      }
      fbcModelPlugin.unsetListOfGeneProducts();
    }
    return sbmlDocument;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#setOption(java.lang.String, java.lang.String)
   */
  @Override
  public void setOption(String name, String value) {
    this.userKey = value;
  }
  
  private ASTNode processAssociation(Association association, FBCModelPlugin fbcModelPlugin) {
    ASTNode geneAssociationNode = new ASTNode();
    if (association instanceof LogicalOperator) {
      if (association instanceof Or) {
        geneAssociationNode.setType(ASTNode.Type.LOGICAL_OR);
      } 
      else if (association instanceof And) {
          geneAssociationNode.setType(ASTNode.Type.LOGICAL_AND);
      }
      for (int j = 0; j < association.getChildCount(); j++) {
            ASTNode newChild = processAssociation((Association)association.getChildAt(j), fbcModelPlugin);
            geneAssociationNode.addChild(newChild);
      }  
    } else if (association instanceof GeneProductRef) {
          GeneProductRef geneProductRef = (GeneProductRef)association;
          GeneProduct geneProduct = (GeneProduct) association.getModel().getSBaseById(geneProductRef.getGeneProduct());
          if (geneProduct != null) {
            geneAssociationNode = new ASTNode(geneProduct.getLabel());
          }
        }  
    return geneAssociationNode;
  }
}
