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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jp.sbi.celldesigner.plugin.PluginAlgebraicRule;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginProtein;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;

/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 30, 2014
 */
public class CellDesignerTest extends AbstractCellDesignerPlugin {

  /**
   * 
   */
  public static final String ACTION = "Test CD Listeners";
  /**
   * 
   */
  public static final String APPLICATION_NAME = "Test the Listeners";

  /**
   * 
   */
  private final CDPropertyChangeVis propertyChangeVis;

  /**
   * Creates a new CellDesigner plug-in with an entry in the menu bar.
   */
  public CellDesignerTest() {
    super();
    addPluginMenu();
    propertyChangeVis = new CDPropertyChangeVis();
    setStarted(true);
  }

  /* (non-Javadoc)
   * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#addPluginMenu()
   */
  @Override
  public void addPluginMenu() {
    // Initializing CellDesigner's menu entries
    PluginMenu menu = new PluginMenu(APPLICATION_NAME);
    PluginMenuItem menuItem = new PluginMenuItem(
      ACTION, new CellDesignerTestAction(this));
    menuItem.setToolTipText("Tests the CD Listeners");
    menu.add(menuItem);
    addCellDesignerPluginMenu(menu);
  }

  /**
   * Sends model ID when CellDesigner model closed.
   */
  @Override
  public void modelClosed(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.modelClosed(pModel.getId());
    }
    else
    {
      propertyChangeVis.modelClosed(sbase.toString());
    }
  }

  /**
   * Sends model ID when CellDesigner model closed.
   */
  @Override
  public void modelOpened(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.modelOpened(pModel.getId());
    }
    else
    {
      propertyChangeVis.modelOpened(sbase.toString());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * Sends model ID when CellDesigner model selected on the canvas.
   */
  @Override
  public void modelSelectChanged(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.modelSelectChanged(pModel.getId());
    }
    else
    {
      propertyChangeVis.modelSelectChanged(sbase.toString());
    }
  }

  /**
   * Performs related to the plugin window closing.
   */
  @Override
  public void run() {
    CDPropertyChangeVis CDvisualizer = new CDPropertyChangeVis();
    CDvisualizer.addWindowListener(new WindowAdapter() {

      @Override
      public void windowClosed(WindowEvent e) {
        setStarted(false);
        getReader().clearMap();
      }
    });
  }

  /**
   * Sends IDs of PluginSBases when they are added to a CellDesigner model.
   */
  @Override
  public void SBaseAdded(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.addSBase(" PluginModel: " + pModel.getId());
    }
    else if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment) sbase;
      propertyChangeVis.addSBase(" PluginCompartment: " + pCompartment.getId());
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      propertyChangeVis.addSBase(" PluginSpecies: " + pSpecies.getId());
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      propertyChangeVis.addSBase(" PluginSpeciesAlias: " + pSpeciesAlias.getAliasID());
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      propertyChangeVis.addSBase(" PluginReaction: " + pReaction.getId());
    }
    else if (sbase instanceof PluginParameter)
    {
      propertyChangeVis.addSBase(" PluginParameter: " + ((PluginParameter)sbase).getId());
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      propertyChangeVis.addSBase(" PluginAlgebraicRule: " + ((PluginAlgebraicRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      propertyChangeVis.addSBase(" PluginAssignmentRule: " + ((PluginAssignmentRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginRateRule)
    {
      propertyChangeVis.addSBase(" PluginRateRule: " + ((PluginRateRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      propertyChangeVis.addSBase(" PluginUnitDefinition: " + ((PluginUnitDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      propertyChangeVis.addSBase(" PluginInitialAssignment: " + ((PluginInitialAssignment)sbase).getMath());
    }
    else if (sbase instanceof PluginEvent)
    {
      propertyChangeVis.addSBase(" PluginEvent: " + ((PluginEvent)sbase).getId());
    }
    else if (sbase instanceof PluginConstraint)
    {
      propertyChangeVis.addSBase(" PluginConstraint: " + ((PluginConstraint)sbase).getMath()+"\t"
          +((PluginConstraint)sbase).getMessage());
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      propertyChangeVis.addSBase(" PluginFunctionDefinition: " + ((PluginFunctionDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      propertyChangeVis.addSBase(" PluginCompartmentType: " + ((PluginCompartmentType)sbase).getId());
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      propertyChangeVis.addSBase(" PluginSpeciesType: " + ((PluginSpeciesType)sbase).getId());
    }
    else if (sbase instanceof PluginProtein)
    {
      propertyChangeVis.addSBase(" PluginProtein: " + ((PluginProtein)sbase).getName());
    }
    else
    {
      propertyChangeVis.addSBase(sbase.toString());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  /**
   * Sends IDs of PluginSBases when they are changed in a CellDesigner model.
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.changeSBase(" PluginModel: " + pModel.getId());
    }
    else if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment) sbase;
      propertyChangeVis.changeSBase(" PluginCompartment: " + pCompartment.getId());
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      propertyChangeVis.changeSBase(" PluginSpecies: " + pSpecies.getId());
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      propertyChangeVis.changeSBase(" PluginSpeciesAlias: " + pSpeciesAlias.getAliasID());
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      propertyChangeVis.changeSBase(" PluginReaction: " + pReaction.getId());
    }
    else if (sbase instanceof PluginParameter)
    {
      propertyChangeVis.changeSBase(" PluginParameter: " + ((PluginParameter)sbase).getId());
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      propertyChangeVis.changeSBase(" PluginAlgebraicRule: " + ((PluginAlgebraicRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      propertyChangeVis.changeSBase(" PluginAssignmentRule: " + ((PluginAssignmentRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginRateRule)
    {
      propertyChangeVis.changeSBase(" PluginRateRule: " + ((PluginRateRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      propertyChangeVis.changeSBase(" PluginUnitDefinition: " + ((PluginUnitDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      propertyChangeVis.changeSBase(" PluginInitialAssignment: " + ((PluginInitialAssignment)sbase).getMath());
    }
    else if (sbase instanceof PluginEvent)
    {
      propertyChangeVis.changeSBase(" PluginEvent: " + ((PluginEvent)sbase).getId());
    }
    else if (sbase instanceof PluginConstraint)
    {
      propertyChangeVis.changeSBase(" PluginConstraint: " + ((PluginConstraint)sbase).getMath()+"\t"
          +((PluginConstraint)sbase).getMessage());
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      propertyChangeVis.changeSBase(" PluginFunctionDefinition: " + ((PluginFunctionDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      propertyChangeVis.changeSBase(" PluginCompartmentType: " + ((PluginCompartmentType)sbase).getId());
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      propertyChangeVis.changeSBase(" PluginSpeciesType: " + ((PluginSpeciesType)sbase).getId());
    }
    else if (sbase instanceof PluginProtein)
    {
      propertyChangeVis.changeSBase(" PluginProtein: " + ((PluginProtein)sbase).getName());
    }
    else
    {
      propertyChangeVis.changeSBase(sbase.toString());
    }
  }

  @Override
  /**
   * Sends IDs of PluginSBases when they are deleted in a CellDesigner model.
   */
  public void SBaseDeleted(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.deleteSBase(" PluginModel: " + pModel.getId());
    }
    else if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment) sbase;
      propertyChangeVis.deleteSBase(" PluginCompartment: " + pCompartment.getId());
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      propertyChangeVis.deleteSBase(" PluginSpecies: " + pSpecies.getId());
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      propertyChangeVis.deleteSBase(" PluginSpeciesAlias: " + pSpeciesAlias.getAliasID());
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      propertyChangeVis.deleteSBase(" PluginReaction: " + pReaction.getId());
    }
    else if (sbase instanceof PluginParameter)
    {
      propertyChangeVis.deleteSBase(" PluginParameter: " + ((PluginParameter)sbase).getId());
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      propertyChangeVis.deleteSBase(" PluginAlgebraicRule: " + ((PluginAlgebraicRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      propertyChangeVis.deleteSBase(" PluginAssignmentRule: " + ((PluginAssignmentRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginRateRule)
    {
      propertyChangeVis.deleteSBase(" PluginRateRule: " + ((PluginRateRule)sbase).getFormula());
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      propertyChangeVis.deleteSBase(" PluginUnitDefinition: " + ((PluginUnitDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      propertyChangeVis.deleteSBase(" PluginInitialAssignment: " + ((PluginInitialAssignment)sbase).getMath());
    }
    else if (sbase instanceof PluginEvent)
    {
      propertyChangeVis.deleteSBase(" PluginEvent: " + ((PluginEvent)sbase).getId());
    }
    else if (sbase instanceof PluginConstraint)
    {
      propertyChangeVis.deleteSBase(" PluginConstraint: " + ((PluginConstraint)sbase).getMath()+"\t"
          +((PluginConstraint)sbase).getMessage());
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      propertyChangeVis.deleteSBase(" PluginFunctionDefinition: " + ((PluginFunctionDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      propertyChangeVis.deleteSBase(" PluginCompartmentType: " + ((PluginCompartmentType)sbase).getId());
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      propertyChangeVis.deleteSBase(" PluginSpeciesType: " + ((PluginSpeciesType)sbase).getId());
    }
    else if (sbase instanceof PluginProtein)
    {
      propertyChangeVis.deleteSBase(" PluginProtein: " + ((PluginProtein)sbase).getName());
    }
    else
    {
      propertyChangeVis.deleteSBase(sbase.toString());
    }
  }
}