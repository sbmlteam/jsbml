/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.cdplugin.test;

import jp.sbi.celldesigner.plugin.CellDesignerPlugin;
import jp.sbi.celldesigner.plugin.PluginMenu;
import jp.sbi.celldesigner.plugin.PluginMenuItem;
import jp.sbi.celldesigner.plugin.PluginSBase;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.cdplugin.PluginChangeListener;
import org.sbml.jsbml.cdplugin.PluginSBMLReader;
import org.sbml.jsbml.test.gui.JSBMLvisualizer;

/**
 * This class is used to test the synchronization between Celldesigner and JSBML.
 * 
 * @author Alexander Peltzer
 * @version $Rev$
 */
public class CDPluginTester extends CellDesignerPlugin {
	public static final String ACTION = "Just a simple text for testing";
	public static final String APPLICATION_NAME = "I am a simple plugin.";
	
	/*
	 * Constructor for Creating the PluginClass
	 */
	
	public CDPluginTester(){
		super();
		try {
			System.out.printf("\n\nLoading_%s\n\n", APPLICATION_NAME);
			SimpleCellDesignerPluginAction action = new SimpleCellDesignerPluginAction(this);
			PluginMenu menu = new PluginMenu(APPLICATION_NAME);
			PluginMenuItem menuItem = new PluginMenuItem(ACTION, action);
			menu.add(menuItem);
			addCellDesignerPluginMenu(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void startPlugin() {
		PluginSBMLReader reader = new PluginSBMLReader(getSelectedModel(), SBO.getDefaultPossibleEnzymes());
		Model model = reader.getModel();
		SBMLDocument doc = new SBMLDocument(model.getLevel(), model.getVersion());
		doc.setModel(model);
		PluginChangeListener plugChangeListener = new PluginChangeListener(doc, this);
		// For a simple visualization we can run this line as well if we want to
		new JSBMLvisualizer(doc);
		
	}
	
	/**
	 * These methods are in our case unnecessary and therefore they're not implemented here.
	 * (non-Javadoc)
	 * @see jp.sbi.celldesigner.plugin.CellDesignerPlug#SBaseAdded(jp.sbi.celldesigner.plugin.PluginSBase)
	 */
	public void SBaseAdded(PluginSBase arg0) {	}
	public void SBaseChanged(PluginSBase arg0) {}
	public void SBaseDeleted(PluginSBase arg0) {}
	public void addPluginMenu() {}
	public void modelClosed(PluginSBase arg0) {}
	public void modelOpened(PluginSBase arg0) {}
	public void modelSelectChanged(PluginSBase arg0) {}

	
}
