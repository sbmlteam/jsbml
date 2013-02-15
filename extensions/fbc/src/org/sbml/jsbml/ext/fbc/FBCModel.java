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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * 
 * @author 
 * @version $Rev$
 * @since 1.0
 * @date 27.10.2011
 */
public class FBCModel extends AbstractSBasePlugin {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7451190347195219863L;
  private ListOf<FluxBound> listOfFluxBounds = new ListOf<FluxBound>();
  private ListOfObjectives listOfObjectives = new ListOfObjectives();
	
	private Model model;
	
	public FBCModel(Model model) {
		this.model = model;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public FBCModel clone() {
		// TODO 
		return null;
	}
	
	public boolean readAttribute(String attributeName, String prefix, String value) {
		return false;
	}

	public Map<String, String> writeXMLAttributes() {
		return null;
	}

	public SBase getChildAt(int childIndex) {
		return null; // TODO 
	}
	public int getChildCount() {
		return 0; // TODO
	}
	public TreeNode getParent() {
		return model;
	}

	
	public boolean isSetListOfFluxBounds() {
		if ((listOfFluxBounds == null) || listOfFluxBounds.isEmpty()) {
			return false;			
		}		
		return true;
	}

	/**
	 * @return the listOfFluxBounds
	 */
	public ListOf<FluxBound> getListOfFluxBounds() {
		return listOfFluxBounds;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public FluxBound getFluxBound(int i) {
		if ((i >= 0) && (i < listOfFluxBounds.size())) {
			return listOfFluxBounds.get(i);
		}
		
		return null;
	}

	/**
	 * @param listOfFluxBounds the listOfFluxBounds to set
	 */
	public void addFluxBound(FluxBound fluxBound) {
		this.listOfFluxBounds.add(fluxBound);
	}

	public boolean isSetListOfObjectives() {
		if ((listOfObjectives == null) || listOfObjectives.isEmpty()) {
			return false;			
		}		
		return true;
	}

	/**
	 * @return the listOfObjectives
	 */
	public ListOf<Objective> getListOfObjectives() {
		return listOfObjectives;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Objective getObjective(int i) {
		if ((i >= 0) && (i < listOfObjectives.size())) {
			return listOfObjectives.get(i);
		}
		
		return null;
	}

	/**
	 * @param listOfObjectives the listOfObjectives to set
	 */
	public void addObjective(Objective objective) {
		this.listOfObjectives.add(objective);
	}

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  public boolean getAllowsChildren() {
    // TODO
    return false;
  }
  
  public String getActiveObjective()
  {
	  if (listOfObjectives != null && listOfObjectives.isSetActiveObjective())
	  {
		  return listOfObjectives.getActiveObjective();
	  }
	  
	  return "";
  }
	
  public void setActiveObjective(String activeObjective)
  {
	  if (listOfObjectives == null)
	  {
	  }
	  
	  listOfObjectives.setActiveObjective(activeObjective);
  }
  
  // TODO : implement / check !!
}
