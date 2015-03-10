/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.ext.distrib;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

public class DistribFunctionDefinitionPlugin extends AbstractSBasePlugin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DrawFromDistribution drawFromDistribution;
	
	public DistribFunctionDefinitionPlugin(FunctionDefinition fd) {
		super(fd);
	}
	
	/**
	 * Returns the value of drawFromDistribution
	 *
	 * @return the value of drawFromDistribution
	 */
	public DrawFromDistribution getDrawFromDistribution() {
		if (isSetDrawFromDistribution()) {
			return drawFromDistribution;
		}
		
		return null;
	}

	/**
	 * Returns whether drawFromDistribution is set 
	 *
	 * @return whether drawFromDistribution is set 
	 */
	public boolean isSetDrawFromDistribution() {
		return this.drawFromDistribution != null;
	}

	/**
	 * Sets the value of drawFromDistribution
	 */
	public void setDrawFromDistribution(DrawFromDistribution drawFromDistribution) {
		DrawFromDistribution oldDrawFromDistribution = this.drawFromDistribution;
		this.drawFromDistribution = drawFromDistribution;
		firePropertyChange(DistribConstants.drawFromDistribution, oldDrawFromDistribution, this.drawFromDistribution);
	}

	/**
	 * Unsets the variable drawFromDistribution 
	 *
	 * @return {@code true}, if drawFromDistribution was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetDrawFromDistribution() {
		if (isSetDrawFromDistribution()) {
			DrawFromDistribution oldDrawFromDistribution = this.drawFromDistribution;
			this.drawFromDistribution = null;
			firePropertyChange(DistribConstants.drawFromDistribution, oldDrawFromDistribution, this.drawFromDistribution);
			return true;
		}
		return false;
	}
	
	@Override
	public String getElementNamespace() {		
		return DistribConstants.namespaceURI;
	}

	@Override
	public String getPackageName() {
		return DistribConstants.shortLabel;
	}

	@Override
	public String getPrefix() {
		return DistribConstants.shortLabel;
	}

	@Override
	public String getURI() {
		return DistribConstants.namespaceURI;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public AbstractSBasePlugin clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		return false;
	}

}
