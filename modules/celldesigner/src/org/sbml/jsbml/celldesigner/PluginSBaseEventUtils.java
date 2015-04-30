/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. Marquette University, Milwaukee, WI, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.celldesigner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jp.sbi.celldesigner.plugin.PluginAlgebraicRule;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginRule;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;

import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;

/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Aug 3, 2014
 */
@SuppressWarnings("deprecation")
public class PluginSBaseEventUtils {

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pAlgebraicRule
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects. @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument} The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginAlgebraicRuleAdded(PluginSBMLReader reader, PluginAlgebraicRule pAlgebraicRule,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      AlgebraicRule algebraRule = (AlgebraicRule) reader.readRule(pAlgebraicRule);
      document.getModel().addRule(algebraRule);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(algebraRule);
      map.put(pAlgebraicRule, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pAssignmentRule the PluginAssignmentRule.
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginAssignmentRuleAdded(PluginSBMLReader reader, PluginAssignmentRule pAssignmentRule,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      AssignmentRule assignmentRule = (AssignmentRule) reader.readRule(pAssignmentRule);
      document.getModel().addRule(assignmentRule);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(assignmentRule);
      map.put(pAssignmentRule, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pCompartment The PluginCompartment.
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginCompartmentAdded(PluginSBMLReader reader, PluginCompartment pCompartment,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    Compartment newCompartment = null;
    try {
      newCompartment = reader.readCompartment(pCompartment);
      document.getModel().addCompartment(newCompartment);

      Set<SBase> sBaseSet= LayoutConverter.extractLayout(pCompartment, layout);
      sBaseSet.add(newCompartment);
      map.put(pCompartment, sBaseSet);

      PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);

    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pCompartment The PluginCompartment.
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginCompartmentChanged(PluginSBMLReader reader, PluginCompartment pCompartment,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    try {
      Compartment newCompartment = null;
      newCompartment = reader.readCompartment(pCompartment);
      model.removeCompartment(newCompartment.getId());
      model.addCompartment(newCompartment);

      layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
      layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
      Set<SBase> sBaseSet = LayoutConverter.extractLayout(pCompartment, layout);

      PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);

      sBaseSet.add(newCompartment);

      if (map.isEmpty()) {
        map.put(pCompartment, sBaseSet);
      } else {
        for (PluginSBase pluginSBase: map.keySet())
        {
          if (pluginSBase instanceof PluginCompartment)
          {
            PluginCompartment compartment = (PluginCompartment)pluginSBase;
            if (compartment.getId().equals(pCompartment.getId())) {
              map.put(pluginSBase, sBaseSet);
            }
          }
        }
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pCompartment The PluginCompartment.
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginCompartmentDeleted(PluginSBMLReader reader, PluginCompartment pCompartment,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);

    layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
    layout.removeTextGlyph("tGlyph_" + pCompartment.getId());

    model.removeCompartment(pCompartment.getId());

    {
      for (PluginSBase pluginSBase: map.keySet())
      {
        PluginCompartment compartment = (PluginCompartment)pluginSBase;
        if (compartment.getId().equals(pCompartment.getId())) {
          map.remove(pluginSBase);
        }
      }
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pCompartmentType The PluginCompartmentType
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginCompartmentTypeAdded(PluginSBMLReader reader,
    PluginCompartmentType pCompartmentType, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document) {
    try {
      CompartmentType cType = reader.readCompartmentType(pCompartmentType);
      document.getModel().addCompartmentType(cType);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(cType);
      map.put(pCompartmentType, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginCompartmentTypeChangedOrDeleted(
    PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document) {
    try{
      Model model = document.getModel();

      while (model.getCompartmentTypeCount() != 0)
      {
        model.removeCompartmentType(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginCompartmentType)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumCompartmentTypes(); i++)
      {
        CompartmentType cType = reader.readCompartmentType(pModel.getCompartmentType(i));
        model.addCompartmentType(cType);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(cType);
        map.put(pModel.getCompartmentType(i), listOfSBases);
      }
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }

  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pConstraint The PluginConstraint.
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginConstraintAdded(PluginSBMLReader reader,
    PluginConstraint pConstraint, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document) {
    try {
      Constraint constraint = reader.readConstraint(pConstraint);
      document.getModel().addConstraint(constraint);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(constraint);
      map.put(pConstraint, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginConstraintChangedOrDeleted(PluginSBMLReader reader,
    PluginModel pModel, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document) {
    try{
      Model model = document.getModel();

      while (model.getConstraintCount() != 0)
      {
        model.removeConstraint(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginConstraint)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumConstraints(); i++)
      {
        Constraint constraint = reader.readConstraint(pModel.getConstraint(i));
        model.addConstraint(constraint);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(constraint);
        map.put(pModel.getConstraint(i), listOfSBases);
      }
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pEvent The PluginEvent
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginEventAdded(PluginSBMLReader reader,
    PluginEvent pEvent, Map<PluginSBase, Set<SBase>> map, SBMLDocument document) {
    try {
      Event event = reader.readEvent(pEvent);
      document.getModel().addEvent(event);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(event);
      map.put(pEvent, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginEventChangedOrDeleted(PluginSBMLReader reader,
    PluginModel pModel, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document) {
    try{
      Model model = document.getModel();

      while (model.getEventCount() != 0)
      {
        model.removeEvent(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginEvent)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumEvents(); i++)
      {
        Event event = reader.readEvent(pModel.getEvent(i));
        model.addEvent(event);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(event);
        map.put(pModel.getEvent(i), listOfSBases);
      }
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }

  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pFunctionDefinition
   * @param map  HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginFunctionDefinitionAdded(PluginSBMLReader reader,
    PluginFunctionDefinition pFunctionDefinition,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document) {
    try {
      FunctionDefinition functionDef = reader.readFunctionDefinition(pFunctionDefinition);
      document.getModel().addFunctionDefinition(functionDef);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(functionDef);
      map.put(pFunctionDefinition, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }

  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginFunctionDefinitionChangedOrDeleted(
    PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document) {
    try{
      Model model = document.getModel();

      while (model.getFunctionDefinitionCount() != 0)
      {
        model.removeFunctionDefinition(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginFunctionDefinition)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumFunctionDefinitions(); i++)
      {
        FunctionDefinition functionDef = reader.readFunctionDefinition(pModel.getFunctionDefinition(i));
        model.addFunctionDefinition(functionDef);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(functionDef);
        map.put(pModel.getFunctionDefinition(i), listOfSBases);
      }
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }

  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pInitialAssignment
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginInitialAssignmentAdded(PluginSBMLReader reader, PluginInitialAssignment pInitialAssignment,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      InitialAssignment initAssignment = reader.readInitialAssignment(pInitialAssignment);
      document.getModel().addInitialAssignment(initAssignment);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(initAssignment);
      map.put(pInitialAssignment, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginInitialAssignmentChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Model model = document.getModel();

      while (model.getInitialAssignmentCount() != 0)
      {
        model.removeInitialAssignment(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginInitialAssignment)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumInitialAssignments(); i++)
      {
        InitialAssignment initAssignment = reader.readInitialAssignment(pModel.getInitialAssignment(i));
        model.addInitialAssignment(initAssignment);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(initAssignment);
        map.put(pModel.getInitialAssignment(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pParameter
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginParameterAdded(PluginSBMLReader reader, PluginParameter pParameter, Map<PluginSBase,
    Set<SBase>> map, SBMLDocument document)
  {
    try {
      Parameter parameter = reader.readParameter(pParameter);
      document.getModel().addParameter(parameter);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(parameter);
      map.put(pParameter, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginParameterChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Model model = document.getModel();

      while (model.getParameterCount() != 0)
      {
        model.removeParameter(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginParameter)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumParameters(); i++)
      {
        Parameter parameter = reader.readParameter(pModel.getParameter(i));
        model.addParameter(parameter);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(parameter);
        map.put(pModel.getParameter(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pRateRule
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginRateRuleAdded(PluginSBMLReader reader, PluginRateRule pRateRule,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      RateRule rateRule = (RateRule) reader.readRule(pRateRule);
      document.getModel().addRule(rateRule);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(rateRule);
      map.put(pRateRule, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pReaction
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginReactionAdded(PluginSBMLReader reader, PluginReaction pReaction,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    Reaction newReaction = null;
    try {
      newReaction = reader.readReaction(pReaction);
      model.addReaction(newReaction);
      Set<SBase> sBaseSet = LayoutConverter.extractLayout(pReaction, layout);
      sBaseSet.add(newReaction);

      PluginUtils.transferNamedSBaseProperties(pReaction, newReaction);

      map.put(pReaction, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginReactionChanged(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);

      Model model = document.getModel();
      for (int i = 0; i<pModel.getNumReactions(); i++)
      {
        layout.removeTextGlyph("tGlyph_" + pModel.getReaction(i).getId());
      }

      while (model.getReactionCount() != 0)
      {
        model.removeReaction(0);
      }

      while (layout.getReactionGlyphCount()!=0)
      {
        layout.removeReactionGlyph(0);
      }
      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginReaction)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumReactions(); i++)
      {
        Reaction reaction = reader.readReaction(pModel.getReaction(i));
        model.addReaction(reaction);
        Set<SBase> listOfSBases = LayoutConverter.extractLayout(pModel.getReaction(i), layout);
        map.put(pModel.getReaction(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }
  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   * @param pReaction
   */
  public static void pluginReactionDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document, PluginReaction pReaction)
  {
    try {
      Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);

      Model model = document.getModel();
      for (int i = 0; i<pModel.getNumReactions(); i++)
      {
        layout.removeTextGlyph("tGlyph_" + pModel.getReaction(i).getId());
      }

      while (model.getReactionCount() != 0)
      {
        model.removeReaction(0);
      }

      while (layout.getReactionGlyphCount()!=0)
      {
        layout.removeReactionGlyph(0);
      }
      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginReaction)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumReactions(); i++)
      {
        Reaction reaction = reader.readReaction(pModel.getReaction(i));
        model.addReaction(reaction);
        Set<SBase> listOfSBases = LayoutConverter.extractLayout(pModel.getReaction(i), layout);
        layout.removeTextGlyph("tGlyph_" + pReaction.getId());
        map.put(pModel.getReaction(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects. HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginRuleChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Model model = document.getModel();

      while (model.getRuleCount() != 0)
      {
        model.removeRule(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginRule)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumRules(); i++)
      {
        Rule rule = reader.readRule(pModel.getRule(i));
        model.addRule(rule);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(rule);
        map.put(pModel.getRule(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pSpecies
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginSpeciesAdded(PluginSBMLReader reader, PluginSpecies pSpecies,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Species newSpecies = null;
    try {
      newSpecies = reader.readSpecies(pSpecies);
      model.addSpecies(newSpecies);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(newSpecies);
      map.put(pSpecies, sBaseSet);

    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param pSpeciesAlias
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginSpeciesAliasAdded(PluginSpeciesAlias pSpeciesAlias, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document)
  {
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    try {
      Set<SBase> sBaseSet = LayoutConverter.extractLayout(pSpeciesAlias, layout);
      map.put(pSpeciesAlias, sBaseSet);
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginSpeciesAliasChanged(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
      for (int i = 0; i<pModel.getListOfAllSpeciesAlias().size(); i++)
      {
        layout.removeTextGlyph("tGlyph_" + ((PluginSpeciesAlias)pModel.getListOfAllSpeciesAlias().get(i)).getAliasID());
      }
      while (layout.getSpeciesGlyphCount()!=0)
      {
        layout.removeSpeciesGlyph(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginSpeciesAlias)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getListOfAllSpeciesAlias().size(); i++)
      {
        Set<SBase> listOfSBases = LayoutConverter.extractLayout((PluginSpeciesAlias)
          pModel.getListOfAllSpeciesAlias().get(i), layout);
        map.put(pModel.getListOfAllSpeciesAlias().get(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   * @param pSpeciesAlias
   */
  public static void pluginSpeciesAliasDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document, PluginSpeciesAlias pSpeciesAlias)
  {
    try {
      Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
      for (int i = 0; i<pModel.getListOfAllSpeciesAlias().size(); i++)
      {
        layout.removeTextGlyph("tGlyph_" + ((PluginSpeciesAlias)pModel.getListOfAllSpeciesAlias().get(i)).getAliasID());
      }
      while (layout.getSpeciesGlyphCount()!=0)
      {
        layout.removeSpeciesGlyph(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginSpeciesAlias)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getListOfAllSpeciesAlias().size(); i++)
      {
        Set<SBase> listOfSBases = LayoutConverter.extractLayout((PluginSpeciesAlias)
          pModel.getListOfAllSpeciesAlias().get(i), layout);
        layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getAliasID());
        layout.removeSpeciesGlyph("sGlyph_"+pSpeciesAlias.getAliasID());
        map.put(pModel.getListOfAllSpeciesAlias().get(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * 
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginSpeciesChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Model model = document.getModel();
      while (model.getSpeciesCount() != 0)
      {
        model.removeSpecies(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginSpecies)
        {
          it.remove();
        }
      }
      if (pModel!=null)
      {
        for (int i = 0; i<pModel.getNumSpecies(); i++)
        {
          Species species = reader.readSpecies(pModel.getSpecies(i));
          model.addSpecies(species);
          Set<SBase> listOfSBases = new HashSet<SBase>();
          listOfSBases.add(species);
          map.put(pModel.getSpecies(i), listOfSBases);
        }
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pSpeciesType
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginSpeciesTypeAdded(PluginSBMLReader reader,
    PluginSpeciesType pSpeciesType, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document) {
    try {
      SpeciesType sType = reader.readSpeciesType(pSpeciesType);
      document.getModel().addSpeciesType(sType);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(sType);
      map.put(pSpeciesType, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginSpeciesTypeChangedOrDeleted(PluginSBMLReader reader,
    PluginModel pModel, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document) {
    try {
      Model model = document.getModel();

      while (model.getSpeciesTypeCount() != 0)
      {
        model.removeSpeciesType(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginSpeciesType)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumSpeciesTypes(); i++)
      {
        SpeciesType sType = reader.readSpeciesType(pModel.getSpeciesType(i));
        model.addSpeciesType(sType);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(sType);
        map.put(pModel.getSpeciesType(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pUnitDefinition
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginUnitDefinitionAdded(PluginSBMLReader reader, PluginUnitDefinition pUnitDefinition,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      UnitDefinition unitDef = reader.readUnitDefinition(pUnitDefinition);
      document.getModel().addUnitDefinition(unitDef);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(unitDef);
      map.put(pUnitDefinition, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  /**
   * @param reader Translates CellDesigner objects to JSBML objects.
   * @param pModel The PluginModel
   * @param map HashMap which stores all PluginSBases and associated JSBML objects.
   * @param document The {@link org.sbml.jsbml.SBMLDocument}
   */
  public static void pluginUnitDefinitionChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Model model = document.getModel();

      while (model.getUnitDefinitionCount() != 0)
      {
        model.removeUnitDefinition(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginUnitDefinition)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumUnitDefinitions(); i++)
      {
        UnitDefinition unitDef = reader.readUnitDefinition(pModel.getUnitDefinition(i));
        model.addUnitDefinition(unitDef);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(unitDef);
        map.put(pModel.getUnitDefinition(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }
}