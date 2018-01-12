/* ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc.converters;

import static org.sbml.jsbml.util.StringTools.getMessage;
import static java.text.MessageFormat.format;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.ext.fbc.*;
import org.sbml.jsbml.text.parser.CobraFormulaParser;

import java.io.StringReader;

public class GPRParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(GPRParser.class);

  /**
   * @param ast
   * @param reactionId
   * @param model
   * @return
   */
  private static Association convertToAssociation(ASTNode ast,
    String reactionId, Model model, boolean omitGenericTerms) {
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
        Association tmp = convertToAssociation(child, reactionId, model, omitGenericTerms);
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
    return createGPR(ast.toString(), reactionId, model);
  }


  /**
   * @param identifier
   * @param reactionId
   * @param model
   * @return
   */
  private static GeneProductRef createGPR(String identifier, String reactionId, Model model) {
    int level = model.getLevel(), version = model.getVersion();
    GeneProductRef gpr = new GeneProductRef(level, version);
    String id = GPRParser.updateGeneId(identifier);
    // check if this id exists in the model
    if (!model.containsUniqueNamedSBase(id)) {
      GeneProduct gp = (GeneProduct) model.findUniqueNamedSBase(identifier);
      if (gp == null) {
        logger.warn(format(
          "Creating missing gene product with id ''{0}'' because reaction ''{1}'' uses this id in its gene-product association.",
          id, reactionId));
        FBCModelPlugin fbcPlug = (FBCModelPlugin) model.getPlugin(FBCConstants.shortLabel);
        gp = fbcPlug.createGeneProduct(id);
        gp.setLabel(id);
      } else {
        logger.info(format("Updating the id of gene product ''{0}'' to ''{1}''.", gp.getId(), id));
        gp.setId(id);
      }
    }
    gpr.setGeneProduct(id);
    return gpr;
  }


  /**
   * @param r
   * @param geneReactionRule
   */
  public static GeneProductAssociation parseGPR(Reaction r, String geneReactionRule, boolean omitGenericTerms) {
    FBCReactionPlugin plugin = (FBCReactionPlugin) r.getPlugin(FBCConstants.shortLabel);
    if ((geneReactionRule != null) && (geneReactionRule.length() > 0)) {
      try {
        ASTNode ast = ASTNode.parseFormula(geneReactionRule, new CobraFormulaParser(new StringReader("")));
        Association association = GPRParser.convertToAssociation(ast, r.getId(), r.getModel(), omitGenericTerms);
        if (!plugin.isSetGeneProductAssociation() || !association.equals(
          plugin.getGeneProductAssociation().getAssociation())) {
          GeneProductAssociation gpa = new GeneProductAssociation(r.getLevel(), r.getVersion());
          gpa.setAssociation(association);
          plugin.setGeneProductAssociation(gpa);
          return gpa;
        }
      } catch (Throwable exc) {
        logger.warn(format("Could not parse ''{0}'' because of {1}",
          geneReactionRule, getMessage(exc)));
      }
    }
    return null;
  }

  /**
   * @param id
   * @return corrected gene id
   */
  private static String updateGeneId(String id) {
    id = id.replace("-", "_");
    if (!id.startsWith("G_")) {
      id = "G_" + id;
    }
    return id;
  }
  
}
