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
package src.org.sbml.jsbml.cdplugin.test;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import jp.sbi.celldesigner.plugin.PluginAction;

/**
 * 
 * @author Alexander Peltzer
 * @version $Rev$
 */
public class SimpleCellDesignerPluginAction extends PluginAction{

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 2080508810969190318L;
	
	private CDPluginTester plugin;
	
	public SimpleCellDesignerPluginAction(CDPluginTester plugTest) {
		this.plugin = plugTest;
	}

	/* (non-Javadoc)
	 * @see jp.sbi.celldesigner.plugin.PluginActionListener#myActionPerformed(java.awt.event.ActionEvent)
	 */
	public void myActionPerformed(ActionEvent ae) {
		if (ae.getSource() instanceof JMenuItem){
			String itemText = ((JMenuItem) ae.getSource()).getText();
			if (itemText.equals(CDPluginTester.ACTION)) {
				plugin.startPlugin();
			}
		} else {
			System.err.printf("Unsupported source of action %s \n", ae.getSource().getClass().getName());
		}
		
	}
	
	
}
