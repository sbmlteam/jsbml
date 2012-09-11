package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.util.TreeNodeChangeListener;

public class ReactionRule extends AbstractNamedSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7757296070294814156L;
  
  ListOf<SpeciesTypeRestrictionReference> listOfConditions;
	ListOf<SpeciesTypeRestrictionReference> listOfResults;
	
	// TODO : add a kineticLaw
	private KineticLaw kineticLaw;

	/**
	 * Returns the list of conditions ({@link SpeciesTypeRestrictionReference}).
	 * 
	 * @return the list of conditions ({@link SpeciesTypeRestrictionReference}).
	 */
	public ListOf<SpeciesTypeRestrictionReference> getListOfConditions() {
		if (listOfConditions == null) {
			listOfConditions = new ListOf<SpeciesTypeRestrictionReference>();
		}
		
		return listOfConditions;
	}

	/**
	 * Adds a condition.
	 * 
	 * @param condition the condition to add
	 */
	public void addCondition(SpeciesTypeRestrictionReference condition) {
		getListOfConditions().add(condition);
	}

	/**
	 * Returns the list of results ({@link SpeciesTypeRestrictionReference}).
	 * 
	 * @return the list of results ({@link SpeciesTypeRestrictionReference}).
	 */
	public ListOf<SpeciesTypeRestrictionReference> getListOfResults() {
		return listOfResults;
	}

	/**
	 * Adds a result.
	 * 
	 * @param results the result to add
	 */
	public void addResult(SpeciesTypeRestrictionReference result) {
		getListOfResults().add(result);
	}

	/**
	 * Creates a new {@link KineticLaw} object, installs it as this
	 * {@link ReactionRule}'s 'kineticLaw' sub-element, and returns it.
	 * 
	 * If this {@link ReactionRule} had a previous KineticLaw, it will be destroyed.
	 * 
	 * @return the new {@link KineticLaw} object
	 */
	public KineticLaw createKineticLaw() {
		KineticLaw kl = new KineticLaw(getLevel(), getVersion());
		setKineticLaw(kl);
		
		return kl;
	}

	/**
	 * 
	 * @return the kineticLaw of this {@link ReactionRule}. Can be null if not set.
	 */
	public KineticLaw getKineticLaw() {
		return kineticLaw;
	}

	/**
	 * 
	 * @return true if the kineticLaw of this {@link ReactionRule} is not null.
	 */
	public boolean isSetKineticLaw() {
		return kineticLaw != null;
	}

	/**
	 * Sets the kineticLaw of this {@link ReactionRule}.
	 * 
	 * @param kineticLaw
	 */
	public void setKineticLaw(KineticLaw kineticLaw) {
		unsetKineticLaw();
		this.kineticLaw = kineticLaw;
		registerChild(this.kineticLaw);
	}

	/**
	 * Sets the {@link KineticLaw} of this {@link ReactionRule} to null and notifies
	 * all {@link TreeNodeChangeListener} about changes.
	 * 
	 * @return {@code true} if calling this method changed the properties
	 *         of this element.
	 */
	public boolean unsetKineticLaw() {
		if (this.kineticLaw != null) {
			KineticLaw oldKinticLaw = this.kineticLaw;
			this.kineticLaw = null;
			oldKinticLaw.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
	//@Override
	public boolean isIdMandatory() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public AbstractSBase clone() {
		// TODO
		return null;
	}

}
