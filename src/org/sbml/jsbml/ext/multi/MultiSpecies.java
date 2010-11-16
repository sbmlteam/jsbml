package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.SpeciesExtension;

/**
 * 
 * 
 * @author
 */
public class MultiSpecies extends SpeciesExtension {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5396837209115412420L;
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
		if (listOfInitialSpeciesInstance == null) {
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
		if (isSetListOfSpeciesInstances()) {
			return listOfInitialSpeciesInstance.get(n);
		}
		throw new IndexOutOfBoundsException(Integer.toString(n));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public InitialSpeciesInstance getInitialSpeciesInstance(String id) {
		if (isSetListOfSpeciesInstances()) {
			for (InitialSpeciesInstance comp : listOfInitialSpeciesInstance) {
				if (comp.getId().equals(id)) {
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
	public boolean isSetListOfSpeciesInstances() {
		return (listOfInitialSpeciesInstance != null)
				&& (listOfInitialSpeciesInstance.size() > 0);
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
