/* 
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class MultiReaction extends AbstractSBasePlugin {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7392764563567861115L;
  ListOf<ReactionRule> listOfReactionRules;
	
	
	/**
	 * @return the listOfReactionRules
	 */
	public ListOf<ReactionRule> getListOfReactionRules() {
		if (listOfReactionRules == null) {
			listOfReactionRules = new ListOf<ReactionRule>();
		}
		
		return listOfReactionRules;
	}

	/**
	 * @param listOfReactionRules the listOfReactionRules to set
	 */
	public void addReactionRule(ReactionRule reactionRule) {
		getListOfReactionRules().add(reactionRule);
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		// TODO Auto-generated method stub
		return false;
	}

	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, String> writeXMLAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public MultiReaction clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
