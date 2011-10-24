package org.sbml.jsbml.ext.fba;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBasePlugin;

public class FBAModel implements SBasePlugin {

	private ListOf<FluxBound> listOfFluxBounds = new ListOf<FluxBound>();
	private ListOf<Objective> listOfObjectives = new ListOf<Objective>();
	
	private Model model;
	
	public FBAModel(Model model) {
		this.model = model;
	}
	
	public FBAModel clone() {
		// TODO 
		return null;
	}
	
	public boolean readAttribute(String attributeName, String prefix, String value) {
		return false;
	}

	public Map<String, String> writeXMLAttributes() {
		return null;
	}

	public TreeNode getChildAt(int childIndex) {
		return null;
	}
	public int getChildCount() {
		return 0;
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
		model.setThisAsParentSBMLObject(fluxBound);
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
		model.setThisAsParentSBMLObject(objective);
		this.listOfObjectives.add(objective);
	}
	
	// TODO : implement !!
}
