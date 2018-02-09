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

import static java.text.MessageFormat.format;
import static org.sbml.jsbml.util.StringTools.getMessage;

import java.io.StringReader;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.ext.fbc.And;
import org.sbml.jsbml.ext.fbc.Association;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.ext.fbc.GeneProductAssociation;
import org.sbml.jsbml.ext.fbc.GeneProductRef;
import org.sbml.jsbml.ext.fbc.LogicalOperator;
import org.sbml.jsbml.ext.fbc.Or;
import org.sbml.jsbml.text.parser.CobraFormulaParser;

/**
 * Provides a method to converts gene association string as used in COBRA in SBML level 2 into {@link GeneProductAssociation}
 * used in SBML level 3.
 * 
 * <p>The conversion modify directly the arguments so clone them beforehand if needed. The SBML level is not changed so
 * that's also something that need to be done before calling the method {@link #parseGPR(Reaction, String, boolean)}
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.3
 */
public class GPRParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(GPRParser.class);


  /**
   * Converts a given {@link ASTNode} into an {@link Association}.
   * 
   * @param ast the ASTNode to convert
   * @param reactionId the {@link Reaction} id
   * @param model the {@link Model}
   * @param omitGenericTerms boolean to indicate if we should set SBO term for the operators and and or.
   * @param displayWarning boolean to indicates if we should display a warning to the user 
   *    when a {@link GeneProduct} is not found for the given identifier
   * @return an {@link Association} that represents the given ASTNode.
   */
  private static Association convertToAssociation(ASTNode ast,
    String reactionId, Model model, boolean omitGenericTerms, boolean displayWarning) {
    int level = model.getLevel(), version = model.getVersion();
    
    if (ast.isLogical()) {
      LogicalOperator operator;
      if (ast.getType() == ASTNode.Type.LOGICAL_AND) {
        operator = new And(level, version);
        if (!omitGenericTerms) {
          operator.setSBOTerm(173); // AND
        }
      } else {
        operator = new Or(level, version);
        if (!omitGenericTerms) {
          operator.setSBOTerm(174); // OR
        }
      }
      for (ASTNode child : ast.getListOfNodes()) {
        Association tmp = convertToAssociation(child, reactionId, model, omitGenericTerms, displayWarning);
        if (tmp.getClass().equals(operator.getClass())) {
          // flatten binary trees to compact representation
          LogicalOperator lo = (LogicalOperator) tmp;
          for (int i = lo.getAssociationCount() - 1; i >= 0; i--) {
            operator.addAssociation(lo.removeAssociation(i));
          }
        } else {
          operator.addAssociation(tmp);
        }
      }
      return operator;
    }
    
    // if it is not logical it should be a Gene symbol/name
    return createGPR(ast.toString(), reactionId, model, displayWarning);
  }


  /**
   * Creates a {@link GeneProductRef} instance for the given identifier.
   *  
   * <p>If there is no {@link GeneProduct} corresponding to this identifier in the model, 
   * creates one.
   * 
   * @param identifier the gene identifier
   * @param reactionId the {@link Reaction} id
   * @param model the Model
   * @param displayWarning boolean to indicates if we should display a warning to the user 
   *    when a {@link GeneProduct} is not found for the given identifier
   * @return a {@link GeneProductRef} for the given gene id.
   */
  private static GeneProductRef createGPR(String identifier, String reactionId, Model model, boolean displayWarning) {
    int level = model.getLevel(), version = model.getVersion();
    GeneProductRef gpr = new GeneProductRef(level, version);
    String id = GPRParser.updateGeneId(identifier);
    
    // check if this id exists in the model
    if (!model.containsUniqueNamedSBase(id)) {
      GeneProduct gp = (GeneProduct) model.findUniqueNamedSBase(identifier);
      GeneProduct gp2 = (GeneProduct) model.findUniqueNamedSBase(id);
      
      if (gp == null && gp2 == null) {
        if (displayWarning) {
          logger.warn(format("Creating missing gene product with id ''{0}'' because reaction ''{1}'' uses this id"
              + " in its gene-product association.", id, reactionId));
        }
        
        FBCModelPlugin fbcPlug = (FBCModelPlugin) model.getPlugin(FBCConstants.shortLabel);
        gp = fbcPlug.createGeneProduct(id);
        gp.setLabel(id);
        
      } else if (gp != null) {        
        logger.info(format("Updating the id of gene product ''{0}'' to ''{1}''.", gp.getId(), id));
        gp.setId(id);
      }
    }
    
    gpr.setGeneProduct(id);
    return gpr;
  }


  /**
   * Parses a gene association string as used in COBRA in SBML level 2 into a {@link GeneProductAssociation}
   * used in SBML level 3.
   * 
   * @param r the {@link Reaction}
   * @param geneReactionRule the gene association
   * @param omitGenericTerms boolean to indicate if we should set SBO term for the operators and and or.
   * @return a {@link GeneProductAssociation} instance representing the given gene association string.
   */
  public static GeneProductAssociation parseGPR(Reaction r, String geneReactionRule, boolean omitGenericTerms) {
    return parseGPR(r, geneReactionRule, omitGenericTerms, true);
  }
  
  /**
   * Parses a gene association string as used in COBRA in SBML level 2 into a {@link GeneProductAssociation}
   * used in SBML level 3.
   * 
   * @param r the {@link Reaction}
   * @param geneReactionRule the gene association
   * @param omitGenericTerms boolean to indicate if we should set SBO term for the operators and and or.
   * @param displayWarning boolean to indicates if we should display a warning to the user 
   *    when a {@link GeneProduct} is not found for the given identifier
   * @return a {@link GeneProductAssociation} instance representing the given gene association string.
   */
  public static GeneProductAssociation parseGPR(Reaction r, String geneReactionRule, boolean omitGenericTerms, boolean displayWarning) {
    FBCReactionPlugin plugin = (FBCReactionPlugin) r.getPlugin(FBCConstants.shortLabel);
    
    if ((geneReactionRule != null) && (geneReactionRule.length() > 0)) {
      try {
        ASTNode ast = ASTNode.parseFormula(geneReactionRule, new CobraFormulaParser(new StringReader("")));
        Association association = GPRParser.convertToAssociation(ast, r.getId(), r.getModel(), omitGenericTerms, displayWarning);
        
        if (!plugin.isSetGeneProductAssociation() || !association.equals(
          plugin.getGeneProductAssociation().getAssociation())) 
        {
          GeneProductAssociation gpa = plugin.createGeneProductAssociation();
          gpa.setAssociation(association);
          
          return gpa;
        }
      } catch (Throwable exc) {
        logger.error(format("Could not parse ''{0}'' because of {1}", geneReactionRule, getMessage(exc)));
      }
    }
    
    return null;
  }

  /**
   * Corrects a gene id to match BiGG and SBML specification.
   *
   * @param id
   *        Gene ID to correct
   * @return a corrected gene id
   */
  private static String updateGeneId(String id) {
    id = id.replace("-", "_");
    
    // replacing invalid character for SBML id
    id = id.replace(".", "_");
    
    if (!id.startsWith("G_")) {
      id = "G_" + id;
    }
    
    return id;
  }

}
