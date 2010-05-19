package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.SpeciesExtension;

/**
 * 
 * 
 *
 */
public class MultiSpecies extends SpeciesExtension{

	/**
	 * 
	 */
	private ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstance;
	
	/**
	 * 
	 * @param species
	 */
	public MultiSpecies(Species species) {
		super(species);
		this.setListOfInitialSpeciesInstance(null);
	}

	/**
	 * 
	 * @param initialSpecies
	 */
	public void addInitialSpeciesInstance(InitialSpeciesInstance initialSpecies) {
		if (!isSetListOfSpeciesInstances()){
			this.listOfInitialSpeciesInstance = new ListOf<InitialSpeciesInstance>();
		}
		if (!listOfInitialSpeciesInstance.contains(initialSpecies)) {
			initialSpecies.setParentSBML(this);
			listOfInitialSpeciesInstance.add(initialSpecies);
		}
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public InitialSpeciesInstance getInitialSpeciesInstance(int n) {
		if (isSetListOfSpeciesInstances()){
			return listOfInitialSpeciesInstance.get(n);
		}
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public InitialSpeciesInstance getInitialSpeciesInstance(String id) {
		if (isSetListOfSpeciesInstances()){
			for (InitialSpeciesInstance comp : listOfInitialSpeciesInstance) {
				if (comp.getId().equals(id)){
					return comp;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public ListOf<InitialSpeciesInstance> getListOfInitialSpeciesInstance() {
		return listOfInitialSpeciesInstance;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfSpeciesInstances(){
		return this.listOfInitialSpeciesInstance != null;
	}

	/**
	 * 
	 * @param listOfInitialSpeciesInstance
	 */
	public void setListOfInitialSpeciesInstance(
			ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstance) {
		this.listOfInitialSpeciesInstance = listOfInitialSpeciesInstance;
	}

}
