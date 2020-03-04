/*
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2020 jointly by the following organizations: 
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
package org.sbml.jsbml.ext.fbc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.sbml.jsbml.util.ModelBuilder.buildUnit;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ext.fbc.CobraConstants;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.FluxObjective;
import org.sbml.jsbml.ext.fbc.ListOfObjectives;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.fbc.converters.FbcV1ToCobraConverter;
import org.sbml.jsbml.ext.fbc.converters.FbcV2ToCobraConverter;
import org.sbml.jsbml.ext.fbc.converters.FbcV2ToFbcV1Converter;
import org.sbml.jsbml.util.ModelBuilder;


/**
 * @author Thorsten Tiede
 * @since 1.5
 * @date 12 Feb 2020
 */
public class FbcV2ToCobraConverterTest {

  FbcV2ToFbcV1Converter fbcV2ToFbcV1Converter;
  FbcV1ToCobraConverter fbcV1ToCobraConverter;
  FbcV2ToCobraConverter fbcV2ToCobraConverter;
  SBMLDocument doc;
  
  Double lowerFluxBound = 1d;
  Double upperFluxBound = 100d;
  String reactionId = "rnR00658";
  String reaction2Id = "MethReact";
  Double fluxValueValue = 0d;
  String defaultUpperBound = "9999";
  String defaultLowerBound = "0.1";
  Double objectiveCoefficient = 1d;
  Double defaultObjectiveCoefficient = 0d;
  
  private SBMLDocument createDoc(boolean hasFbc, boolean hasUpper, boolean hasLower) {
    int level = 3, version = 1;

    ModelBuilder builder = new ModelBuilder(level, version);

    Model model = builder.buildModel("fbcV2ToCobraConversion_test", "Simple test model for FBC version 2 conversion to L2 with Cobra Annotation");
    FBCModelPlugin modelPlugin = (FBCModelPlugin) model.getPlugin(FBCConstants.shortLabel);
    modelPlugin.setStrict(true);

    UnitDefinition ud = builder.buildUnitDefinition("volume", "Volume units");
    buildUnit(ud, 1d, -3, Unit.Kind.LITRE, 1d);
    model.setVolumeUnits(ud);

    ud = builder.buildUnitDefinition("substance", "Substance units");
    buildUnit(ud, 1d, -3, Unit.Kind.MOLE, 1d);
    model.setSubstanceUnits(ud);

    ud = builder.buildUnitDefinition("mmol_per_gDW_per_hr", "mmol_per_gDW_per_hr");
    buildUnit(ud, 1d, -3, Unit.Kind.MOLE, 1d);
    buildUnit(ud, 1d, 0, Unit.Kind.GRAM, -1d);
    buildUnit(ud, 3600, 0, Unit.Kind.SECOND, -1d);

    Compartment compartment = builder.buildCompartment("default", true, "default compartment", 3d, 1d, model.getVolumeUnits());

    Species reactant = builder.buildSpecies("C3H7O7P", "C3H7O7P", compartment, true, false, false, 1d, model.getSubstanceUnits());
    Species product1 = builder.buildSpecies("C3H5O6P", "C3H5O6P", compartment, true, false, false, 1d, model.getSubstanceUnits());
    Species product2 = builder.buildSpecies("H2O", "H2O", compartment, true, false, false, 1d, model.getSubstanceUnits());
    Species productProduct1 = builder.buildSpecies("C2HO6P", "C2HO6P", compartment, true, false, false, 1d, model.getSubstanceUnits());
    Species productProduct2 = builder.buildSpecies("CH4", "CH4", compartment, true, false, false, 0d, model.getSubstanceUnits());

    Reaction reaction = builder.buildReaction(reactionId, "2-phospho-D-glycerate hydro-lyase (phosphoenolpyruvate-forming)", compartment, false, false);
    reaction.addReactant(new SpeciesReference(reactant));
    reaction.addProduct(new SpeciesReference(product1));
    reaction.addProduct(new SpeciesReference(product2));
    reaction.setReversible(true);
    if(hasFbc) {
      FBCReactionPlugin reactionPlugin = (FBCReactionPlugin) reaction.getPlugin(FBCConstants.shortLabel);
      if (hasLower) {
        Parameter lowerFluxBoundParameter = builder.buildParameter("lfb","lowerFluxBound", 1d, true, "mmol_per_gDW_per_hr");
        reactionPlugin.setLowerFluxBound(lowerFluxBoundParameter);
      }
      if(hasUpper) {
        Parameter upperFluxBoundParameter = builder.buildParameter("ufb","upperFluxBound", 100d, true, "mmol_per_gDW_per_hr");
        reactionPlugin.setUpperFluxBound(upperFluxBoundParameter);
      }
    }

    // create a second reaction that has no FluxObjective -> should have OBJETIVE_COEFFICIENT with value 0 after conversion
    Reaction reaction2 = builder.buildReaction(reaction2Id, "Fictive Methane reaction", compartment, false, false);
    reaction2.addReactant(new SpeciesReference(product1));
    reaction2.addProduct(new SpeciesReference(productProduct1));
    reaction2.addProduct(new SpeciesReference(productProduct2));
    if(hasFbc) {
      FBCReactionPlugin reactionPlugin = (FBCReactionPlugin) reaction2.getPlugin(FBCConstants.shortLabel);
      if (hasLower) {
        //Parameter lowerFluxBoundParameter = builder.buildParameter("lfb","lowerFluxBound", 1d, true, "mmol_per_gDW_per_hr");
        reactionPlugin.setLowerFluxBound("lfb");
      }
      if(hasUpper) {
        //Parameter upperFluxBoundParameter = builder.buildParameter("ufb","upperFluxBound", 100d, true, "mmol_per_gDW_per_hr");
        reactionPlugin.setUpperFluxBound("ufb");
      }
    }

    // Create Objective for reaction -> reaction should have OBJECTIVE_COEFFICIENT with value 1 after conversion

    ListOf<FluxObjective> listOfFluxObjectives = new ListOf<>();
    FluxObjective fluxObjective = new FluxObjective();
    fluxObjective.setReaction(reaction);
    fluxObjective.setCoefficient(objectiveCoefficient);
    listOfFluxObjectives.add(fluxObjective);
    Objective objective = new Objective();
    objective.setId("obj");
    objective.setListOfFluxObjectives(listOfFluxObjectives);
    ListOfObjectives listOfObjectives = new ListOfObjectives();
    listOfObjectives.add(objective);
    listOfObjectives.setActiveObjective(objective);
    modelPlugin.setListOfObjectives(listOfObjectives);

    return builder.getSBMLDocument();
  }
  
  @Test
  public void fullDocumentConversionTest() {
    
    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, true, true);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), lowerFluxBound);
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), upperFluxBound);
    
    LocalParameter objectiveParameter = kineticLaw.getLocalParameter(CobraConstants.OBJECTIVE_COEFFICIENT);
    assertNotNull(objectiveParameter);
    assertEquals((Double) objectiveParameter.getValue(), objectiveCoefficient);

    // does reaction2 have LocalParameter OBJECTIVE_COEFFICIENT with value 0 ?
    KineticLaw kineticLaw2 = doc.getModel().getListOfReactions().get(reaction2Id).getKineticLaw();
    assertNotNull(kineticLaw2);
    ASTNode mathNode2 = kineticLaw2.getMath();
    assertNotNull(mathNode2);
    assertEquals(mathNode2.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter2 = kineticLaw2.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter2);
    assertEquals(fluxValueParameter2.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter2.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter2 = kineticLaw2.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter2);
    assertEquals((Double) lowerBoundParameter2.getValue(), lowerFluxBound);
    LocalParameter upperBoundParameter2 = kineticLaw2.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter2);
    assertEquals((Double) upperBoundParameter2.getValue(), upperFluxBound);
    LocalParameter objectiveParameter2 = kineticLaw2.getLocalParameter(CobraConstants.OBJECTIVE_COEFFICIENT);
    assertNotNull(objectiveParameter2);
    assertEquals((Double) objectiveParameter2.getValue(), defaultObjectiveCoefficient);
    
  }
  
  @Test
  public void fullDocumentWithDefaultValuesConversionTest() {

    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, true, true);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_LOWER_BOUND_NAME, defaultLowerBound);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_UPPER_BOUND_NAME, defaultUpperBound);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), lowerFluxBound);
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), upperFluxBound);
  }
  
  @Test
  public void missingUpperBoundConversionTest() { 
     
    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, false, true);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), lowerFluxBound);
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), Double.valueOf(Double.POSITIVE_INFINITY));
  }
  
  @Test
  public void missingUpperBoundWithDefaultValueConversionTest() {
  
    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, false, true);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_UPPER_BOUND_NAME, defaultUpperBound);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), lowerFluxBound);
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), Double.valueOf(defaultUpperBound));
  }
  
  @Test
  public void missingLowerBoundConversionTest() { 
     
    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, true, false);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), Double.valueOf(Double.NEGATIVE_INFINITY));
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), upperFluxBound);
  }
  
  @Test
  public void missingLowerBoundWithDefaultValueConversionTest() {
  
    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, true, false);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_LOWER_BOUND_NAME, defaultLowerBound);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), Double.valueOf(defaultLowerBound));
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), upperFluxBound);
  }
  
  @Test
  public void missingLowerAndUpperBoundConversionTest() {
  
    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, false, false);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), Double.valueOf(Double.NEGATIVE_INFINITY));
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), Double.valueOf(Double.POSITIVE_INFINITY));
  }
  
  @Test
  public void missingLowerAndUpperBoundWithDefaultValuesConversionTest() {

    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(true, false, false);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_LOWER_BOUND_NAME, defaultLowerBound);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_UPPER_BOUND_NAME, defaultUpperBound);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), Double.valueOf(defaultLowerBound));
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), Double.valueOf(defaultUpperBound));
  }   
  
  @Test
  public void missingKineticLawConversionTest() {

    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(false, false, false);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    doc = fbcV1ToCobraConverter.convert(doc);
     
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), Double.valueOf(Double.NEGATIVE_INFINITY));
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), Double.valueOf(Double.POSITIVE_INFINITY));
  }
  
  @Test
  public void missingKineticLawWithDefaultValuesConversionTest() {

    fbcV2ToFbcV1Converter = new FbcV2ToFbcV1Converter();
    fbcV1ToCobraConverter = new FbcV1ToCobraConverter();
    doc = createDoc(false, false, false);
    assertNotNull(doc);
    doc = fbcV2ToFbcV1Converter.convert(doc);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_LOWER_BOUND_NAME, defaultLowerBound);
    fbcV1ToCobraConverter.setOption(CobraConstants.DEFAULT_UPPER_BOUND_NAME, defaultUpperBound);
    doc = fbcV1ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), Double.valueOf(defaultLowerBound));
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), Double.valueOf(defaultUpperBound));
  }
  
  @Test
  public void combinedConverterConversionTest() {
    
    fbcV2ToCobraConverter = new FbcV2ToCobraConverter();
    doc = createDoc(false, false, false);
    assertNotNull(doc);
    fbcV2ToCobraConverter.setOption(CobraConstants.DEFAULT_LOWER_BOUND_NAME, defaultLowerBound);
    fbcV2ToCobraConverter.setOption(CobraConstants.DEFAULT_UPPER_BOUND_NAME, defaultUpperBound);
    doc = fbcV2ToCobraConverter.convert(doc);
    
    assertNotNull(doc);
    KineticLaw kineticLaw = doc.getModel().getListOfReactions().get(reactionId).getKineticLaw();
    assertNotNull(kineticLaw);
    ASTNode mathNode = kineticLaw.getMath();
    assertNotNull(mathNode);
    assertEquals(mathNode.getName(), CobraConstants.FLUX_VALUE);
    LocalParameter fluxValueParameter = kineticLaw.getLocalParameter(CobraConstants.FLUX_VALUE);
    assertNotNull(fluxValueParameter);
    assertEquals(fluxValueParameter.getUnits(), CobraConstants.mmol_per_gDW_per_hr);
    assertEquals((Double)fluxValueParameter.getValue(), fluxValueValue);
    LocalParameter lowerBoundParameter = kineticLaw.getLocalParameter(CobraConstants.LOWER_BOUND);
    assertNotNull(lowerBoundParameter);
    assertEquals((Double) lowerBoundParameter.getValue(), Double.valueOf(defaultLowerBound));
    LocalParameter upperBoundParameter = kineticLaw.getLocalParameter(CobraConstants.UPPER_BOUND);
    assertNotNull(upperBoundParameter);
    assertEquals((Double) upperBoundParameter.getValue(), Double.valueOf(defaultUpperBound));
  }
}
