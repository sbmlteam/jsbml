/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations:
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

  public static final String ACTION = "Test CD Listeners";
  public static final String APPLICATION_NAME = "Test the Listeners";

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

  @Override
  public void SBaseAdded(PluginSBase sbase) {

    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.addSBase(pModel.getId());
    }
    else if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment) sbase;
      propertyChangeVis.addSBase(pCompartment.getId());
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      propertyChangeVis.addSBase(pSpecies.getId());
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      propertyChangeVis.addSBase("alias_"+pSpeciesAlias.getSpecies().getId());
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      propertyChangeVis.addSBase(pReaction.getId());
    }
    else if (sbase instanceof PluginParameter)
    {
      propertyChangeVis.addSBase(((PluginParameter)sbase).getId());
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      propertyChangeVis.addSBase(((PluginAlgebraicRule)sbase).toString());
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      propertyChangeVis.addSBase(((PluginAssignmentRule)sbase).toString());
    }
    else if (sbase instanceof PluginRateRule)
    {
      propertyChangeVis.addSBase(((PluginRateRule)sbase).toString());
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      propertyChangeVis.addSBase(((PluginUnitDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      propertyChangeVis.addSBase(((PluginInitialAssignment)sbase).toString());
    }
    else if (sbase instanceof PluginEvent)
    {
      propertyChangeVis.addSBase(((PluginEvent)sbase).getId());
    }
    else if (sbase instanceof PluginConstraint)
    {
      propertyChangeVis.addSBase(((PluginConstraint)sbase).toString());
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      propertyChangeVis.addSBase(((PluginFunctionDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      propertyChangeVis.addSBase(((PluginCompartmentType)sbase).getId());
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      propertyChangeVis.addSBase(((PluginSpeciesType)sbase).getId());
    }
    else
    {
      propertyChangeVis.addSBase(sbase.toString());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#modelSelectChanged(jp.sbi.celldesigner.plugin.PluginSBase)
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.celldesigner.AbstractCellDesignerPlugin#SBaseChanged(jp.sbi.celldesigner.plugin.PluginSBase)
   */
  @Override
  public void SBaseChanged(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.changeSBase(pModel.getId());
    }
    else if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment) sbase;
      propertyChangeVis.changeSBase(pCompartment.getId());
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      propertyChangeVis.changeSBase(pSpecies.getId());
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      propertyChangeVis.changeSBase("alias_"+pSpeciesAlias.getSpecies().getId());
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      propertyChangeVis.changeSBase(pReaction.getId());
    }
    else if (sbase instanceof PluginParameter)
    {
      propertyChangeVis.changeSBase(((PluginParameter)sbase).getId());
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      propertyChangeVis.changeSBase(((PluginAlgebraicRule)sbase).toString());
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      propertyChangeVis.changeSBase(((PluginAssignmentRule)sbase).toString());
    }
    else if (sbase instanceof PluginRateRule)
    {
      propertyChangeVis.changeSBase(((PluginRateRule)sbase).toString());
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      propertyChangeVis.changeSBase(((PluginUnitDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      propertyChangeVis.changeSBase(((PluginInitialAssignment)sbase).toString());
    }
    else if (sbase instanceof PluginEvent)
    {
      propertyChangeVis.changeSBase(((PluginEvent)sbase).getId());
    }
    else if (sbase instanceof PluginConstraint)
    {
      propertyChangeVis.changeSBase(((PluginConstraint)sbase).toString());
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      propertyChangeVis.changeSBase(((PluginFunctionDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      propertyChangeVis.changeSBase(((PluginCompartmentType)sbase).getId());
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      propertyChangeVis.changeSBase(((PluginSpeciesType)sbase).getId());
    }
    else
    {
      propertyChangeVis.changeSBase(sbase.toString());
    }
  }

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

  @Override
  public void SBaseDeleted(PluginSBase sbase) {
    if (sbase instanceof PluginModel)
    {
      PluginModel pModel = (PluginModel) sbase;
      propertyChangeVis.deleteSBase(pModel.getId());
    }
    else if (sbase instanceof PluginCompartment)
    {
      PluginCompartment pCompartment = (PluginCompartment) sbase;
      propertyChangeVis.deleteSBase(pCompartment.getId());
    }
    else if (sbase instanceof PluginSpecies)
    {
      PluginSpecies pSpecies = (PluginSpecies) sbase;
      propertyChangeVis.deleteSBase(pSpecies.getId());
    }
    else if (sbase instanceof PluginSpeciesAlias)
    {
      PluginSpeciesAlias pSpeciesAlias = (PluginSpeciesAlias) sbase;
      propertyChangeVis.deleteSBase("alias_"+pSpeciesAlias.getSpecies().getId());
    }
    else if (sbase instanceof PluginReaction)
    {
      PluginReaction pReaction = (PluginReaction) sbase;
      propertyChangeVis.deleteSBase(pReaction.getId());
    }
    else if (sbase instanceof PluginParameter)
    {
      propertyChangeVis.deleteSBase(((PluginParameter)sbase).getId());
    }
    else if (sbase instanceof PluginAlgebraicRule)
    {
      propertyChangeVis.deleteSBase(((PluginAlgebraicRule)sbase).toString());
    }
    else if (sbase instanceof PluginAssignmentRule)
    {
      propertyChangeVis.deleteSBase(((PluginAssignmentRule)sbase).toString());
    }
    else if (sbase instanceof PluginRateRule)
    {
      propertyChangeVis.deleteSBase(((PluginRateRule)sbase).toString());
    }
    else if (sbase instanceof PluginUnitDefinition)
    {
      propertyChangeVis.deleteSBase(((PluginUnitDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginInitialAssignment)
    {
      propertyChangeVis.deleteSBase(((PluginInitialAssignment)sbase).toString());
    }
    else if (sbase instanceof PluginEvent)
    {
      propertyChangeVis.deleteSBase(((PluginEvent)sbase).getId());
    }
    else if (sbase instanceof PluginConstraint)
    {
      propertyChangeVis.deleteSBase(((PluginConstraint)sbase).toString());
    }
    else if (sbase instanceof PluginFunctionDefinition)
    {
      propertyChangeVis.deleteSBase(((PluginFunctionDefinition)sbase).getId());
    }
    else if (sbase instanceof PluginCompartmentType)
    {
      propertyChangeVis.deleteSBase(((PluginCompartmentType)sbase).getId());
    }
    else if (sbase instanceof PluginSpeciesType)
    {
      propertyChangeVis.deleteSBase(((PluginSpeciesType)sbase).getId());
    }
    else
    {
      propertyChangeVis.deleteSBase(sbase.toString());
    }
  }

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
}