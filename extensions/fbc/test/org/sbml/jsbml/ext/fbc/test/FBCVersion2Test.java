/*
 * $Id: FBCVersion2Test.java 2167 2015-03-31 08:45:35Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/fbc/test/org/sbml/jsbml/ext/fbc/test/FBCVersion2Test.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015  jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc.test;

import static org.sbml.jsbml.util.ModelBuilder.buildUnit;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ext.fbc.And;
import org.sbml.jsbml.ext.fbc.Association;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.ext.fbc.GeneProductRef;
import org.sbml.jsbml.ext.fbc.GeneProteinAssociation;
import org.sbml.jsbml.ext.fbc.Or;
import org.sbml.jsbml.util.ModelBuilder;


/**
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2167 $
 * @since 1.1
 * @date 06.03.2015
 */
public class FBCVersion2Test {

  /**
   * 
   */
  public FBCVersion2Test() {

    int level = 3, version = 1;

    ModelBuilder builder = new ModelBuilder(level, version);

    Model model = builder.buildModel("fbc_test", "Simple test model for FBC version 2");
    FBCModelPlugin modelPlugin = (FBCModelPlugin) model.getPlugin(FBCConstants.shortLabel);

    GeneProduct geneProduct = modelPlugin.createGeneProduct("gene1");
    geneProduct.setMetaId("meta_gene1");
    geneProduct.setName("ETFC");
    geneProduct.setLabel("2109.2");
    geneProduct.addCVTerm(new CVTerm(Qualifier.BQB_IS_ENCODED_BY, "http://identifiers.org/ncbigene/2109"));

    geneProduct = modelPlugin.createGeneProduct("gene2");
    geneProduct.setLabel("2108.1");

    geneProduct = modelPlugin.createGeneProduct("gene3");
    geneProduct.setLabel("2109.1");

    UnitDefinition ud = builder.buildUnitDefinition("volume", "Volume units");
    buildUnit(ud, 1d, -3, Unit.Kind.LITRE, 1d);
    model.setVolumeUnits(ud);

    Compartment compartment = builder.buildCompartment("default", true, "default compartment", 3d, 1d, model.getVolumeUnits());

    ud = builder.buildUnitDefinition("substance", "Substance units");
    buildUnit(ud, 1d, -3, Unit.Kind.MOLE, 1d);
    model.setSubstanceUnits(ud);

    Species s1 = builder.buildSpecies("glc", "glucose", compartment, true, false, false, 1d, model.getSubstanceUnits());
    Species s2 = builder.buildSpecies("g6p", "glucose 6 phosphate", compartment, true, false, false, 1d, model.getSubstanceUnits());

    ud = builder.buildUnitDefinition("time", "Time units");
    buildUnit(ud, 1d, 0, Unit.Kind.SECOND, 1d);
    model.setTimeUnits(ud);

    ud = builder.buildUnitDefinition("extent", "Extent units");
    ud.multiplyWith(model.getSubstanceUnitsInstance().clone());
    ud.divideBy(model.getTimeUnitsInstance().clone());
    model.setExtentUnits(ud);

    Parameter lowerFluxBound = builder.buildParameter("lb", "lower flux bound", 0d, true, model.getExtentUnits());
    Parameter upperFluxBound = builder.buildParameter("ub", "upper flux bound", Double.POSITIVE_INFINITY, true, model.getExtentUnits());

    Reaction reaction = builder.buildReaction("R_ETF", "electron transfer flavoprotein", compartment, false, false);
    try {
      reaction.appendNotes("<html:p>GENE ASSOCIATION: (2109.2) and (2108.1) or (2109.1) and  (2108.1)</html:p>");
    } catch (XMLStreamException exc) {
      exc.printStackTrace();
    }

    FBCReactionPlugin reactionPlugin = (FBCReactionPlugin) reaction.getPlugin(FBCConstants.shortLabel);
    GeneProteinAssociation gpa = reactionPlugin.createGeneProteinAssociation("R_ETF_assoc");
    Association association = new Or(level, version);
    And and = new And(level, version);
    GeneProductRef gpr = new GeneProductRef("gprf1", "R_ETF transcript 2", level, version);
    gpr.setGeneProduct("gene1");
    and.addAssociation(gpr);
    gpr = new GeneProductRef(level, version);
    gpr.setGeneProduct("gene2");
    and.addAssociation(gpr);
    ((Or) association).addAssociation(and);
    and = new And(level, version);
    gpr = new GeneProductRef(level, version);
    gpr.setGeneProduct("gene3");
    and.addAssociation(gpr);
    gpr = new GeneProductRef(level, version);
    gpr.setGeneProduct("gene2");
    and.addAssociation(gpr);
    ((Or) association).addAssociation(and);
    gpa.setAssociation(association);

    reactionPlugin.setLowerFluxBound(lowerFluxBound);
    reactionPlugin.setUpperFluxBound(upperFluxBound);

    SBMLDocument doc = builder.getSBMLDocument();
    try {
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
    } catch (SBMLException exc) {
      exc.printStackTrace();
    } catch (XMLStreamException exc) {
      exc.printStackTrace();
    }
    JTree tree = new JTree(doc);
    tree.setPreferredSize(new Dimension(640, 480));
    JOptionPane.showMessageDialog(null, new JScrollPane(tree));
  }


  /**
   * @param args
   */
  public static void main(String[] args) {
    new FBCVersion2Test();
  }
}
