package org.sbml.jsbml.multiTest;

import org.sbml.jsbml.element.ListOf;
import org.sbml.jsbml.element.Species;
import org.sbml.jsbml.sbmlExtensions.SpeciesExtension;

public class MultiSpecies extends SpeciesExtension{

	private ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstance;
	
	public MultiSpecies(Species species) {
		super(species);
		this.setListOfInitialSpeciesInstance(null);
	}

	public void setListOfInitialSpeciesInstance(
			ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstance) {
		this.listOfInitialSpeciesInstance = listOfInitialSpeciesInstance;
	}

	public ListOf<InitialSpeciesInstance> getListOfInitialSpeciesInstance() {
		return listOfInitialSpeciesInstance;
	}
	
	public boolean isSetListOfSpeciesInstances(){
		return this.listOfInitialSpeciesInstance != null;
	}
	
	public void addInitialSpeciesInstance(InitialSpeciesInstance initialSpecies) {
		if (!isSetListOfSpeciesInstances()){
			this.listOfInitialSpeciesInstance = new ListOf<InitialSpeciesInstance>();
		}
		if (!listOfInitialSpeciesInstance.contains(initialSpecies)) {
			initialSpecies.setParentSBML(this);
			listOfInitialSpeciesInstance.add(initialSpecies);
		}
	}
	
	public InitialSpeciesInstance getInitialSpeciesInstance(int n) {
		if (isSetListOfSpeciesInstances()){
			return listOfInitialSpeciesInstance.get(n);
		}
		return null;
	}

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

}
