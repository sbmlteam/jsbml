package org.sbml.jsbml.ext.qual;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBasePlugin;

public class QualitativeModel implements SBasePlugin {

	private ListOf<QualitativeSpecies> listOfQualitativeSpecies = new ListOf<QualitativeSpecies>();
	private ListOf<Transition> listOfTransitions = new ListOf<Transition>();

	private Model model;

	
	public QualitativeModel(Model model) {
		this.model = model;
	}


	public boolean isSetListOfQualitativeSpecies() {
		if ((listOfQualitativeSpecies == null) || listOfQualitativeSpecies.isEmpty()) {
			return false;			
		}		
		return true;
	}

	
	/**
	 * @return the listOfQualitativeSpecies
	 */
	public ListOf<QualitativeSpecies> getListOfQualitativeSpecies() {
		return listOfQualitativeSpecies;
	}

	public QualitativeSpecies getQualitativeSpecies(int i) {
		if ((i >= 0) && (i < listOfQualitativeSpecies.size())) {
			return listOfQualitativeSpecies.get(i);
		}

		return null;
	}

	/**
	 * @param listOfQualitativeSpecies the listOfQualitativeSpecies to set
	 */
	public void addQualitativeSpecies(QualitativeSpecies qualitativeSpecies) {
		this.listOfQualitativeSpecies.add(qualitativeSpecies);
	}
	
	
	public boolean isSetListOfTransitions() {
		if ((listOfTransitions == null) || listOfTransitions.isEmpty()) {
			return false;			
		}		
		return true;
	}



	/**
	 * @return the listOTransitions
	 */
	public ListOf<Transition> getListOfTransitions() {
		return listOfTransitions;
	}

	public Transition getTransition(int i) {
		if ((i >= 0) && (i < listOfTransitions.size())) {
			return listOfTransitions.get(i);
		}

		return null;
	}

	public void addTransition(Transition transition) {
		this.listOfTransitions.add(transition);
	}


	public QualitativeModel clone() {
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
		// TODO
		return null;
	}
	
	public int getChildCount() {
		// TODO
		return 0;
	}
	
	public TreeNode getParent() {
		return model;
	}
}
