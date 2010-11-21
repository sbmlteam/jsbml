package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBaseChangedListener;
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
	private ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstances;

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
		if (listOfInitialSpeciesInstances == null) {
			this.listOfInitialSpeciesInstances = new ListOf<InitialSpeciesInstance>();
		}
		if (!listOfInitialSpeciesInstances.contains(initialSpecies)) {
			initialSpecies.setParentSBML(this);
			listOfInitialSpeciesInstances.add(initialSpecies);
		}
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public InitialSpeciesInstance getInitialSpeciesInstance(int n) {
		if (isSetListOfSpeciesInstances()) {
			return listOfInitialSpeciesInstances.get(n);
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
			for (InitialSpeciesInstance comp : listOfInitialSpeciesInstances) {
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
		return listOfInitialSpeciesInstances;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfSpeciesInstances() {
		return (listOfInitialSpeciesInstances != null)
				&& (listOfInitialSpeciesInstances.size() > 0);
	}

	/**
	 * 
	 * @param listOfInitialSpeciesInstance
	 */
	public void setListOfInitialSpeciesInstance(
			ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstance) {
		unsetListOfInitialSpeciesInstances();
		this.listOfInitialSpeciesInstances = listOfInitialSpeciesInstance;
		if ((this.listOfInitialSpeciesInstances != null) && (this.listOfInitialSpeciesInstances.getSBaseListType() != ListOf.Type.other)) {
			this.listOfInitialSpeciesInstances.setSBaseListType(ListOf.Type.other);
		}
		setThisAsParentSBMLObject(this.listOfInitialSpeciesInstances);
	}
	
	/**
	 * Removes the {@link #listOfInitialSpeciesInstances} from this {@link Model} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfInitialSpeciesInstances() {
		if (this.listOfInitialSpeciesInstances != null) {
			ListOf<InitialSpeciesInstance> oldListOfInitialSpeciesInstances = this.listOfInitialSpeciesInstances;
			this.listOfInitialSpeciesInstances = null;
			oldListOfInitialSpeciesInstances.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

}
