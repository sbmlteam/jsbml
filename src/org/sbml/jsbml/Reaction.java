/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;


/**
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class Reaction extends AbstractNamedSBase {

	private boolean reversible;
	private boolean fast;

	private ListOf<SpeciesReference> listOfReactants;
	private ListOf<SpeciesReference> listOfProducts;
	private ListOf<ModifierSpeciesReference> listOfModifiers;
	private KineticLaw kineticLaw;

	/**
	 * 
	 * @param reaction
	 */
	public Reaction(Reaction reaction) {
		super(reaction);
		this.fast = reaction.getFast();
		if (reaction.isSetKineticLaw())
			setKineticLaw(reaction.getKineticLaw().clone());
		this.listOfReactants = reaction.getListOfReactants().clone();
		setThisAsParentSBMLObject(listOfReactants);
		this.listOfProducts = reaction.getListOfProducts().clone();
		setThisAsParentSBMLObject(listOfProducts);
		this.listOfModifiers = reaction.getListOfModifiers().clone();
		setThisAsParentSBMLObject(listOfModifiers);
		this.reversible = reaction.getReversible();
	}

	/**
	 * 
	 * @param id
	 */
	public Reaction(String id, int level, int version) {
		super(id, level, version);
		listOfReactants = new ListOf<SpeciesReference>(level, version);
		listOfReactants.parentSBMLObject = this;
		listOfProducts = new ListOf<SpeciesReference>(level, version);
		listOfProducts.parentSBMLObject = this;
		listOfModifiers = new ListOf<ModifierSpeciesReference>(level, version);
		listOfModifiers.parentSBMLObject = this;
		kineticLaw = null;
		reversible = true;
		fast = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.SBase#addChangeListener(org.sbml.squeezer.io.SBaseChangedListener
	 * )
	 */
	public void addChangeListener(SBaseChangedListener l) {
		super.addChangeListener(l);
		listOfReactants.addChangeListener(l);
		listOfProducts.addChangeListener(l);
		listOfModifiers.addChangeListener(l);
	}

	/**
	 * 
	 * @param modspecref
	 */
	public void addModifier(ModifierSpeciesReference modspecref) {
		if (!listOfModifiers.contains(modspecref)) {
			modspecref.parentSBMLObject = this;
			listOfModifiers.add(modspecref);
		}
	}

	/**
	 * 
	 * @param specref
	 */
	public void addProduct(SpeciesReference specref) {
		if (!listOfProducts.contains(specref)) {
			specref.parentSBMLObject = this;
			listOfProducts.add(specref);
		}
	}

	/**
	 * 
	 * @param specref
	 */
	public void addReactant(SpeciesReference specref) {
		if (!listOfReactants.contains(specref)) {
			specref.parentSBMLObject = this;
			listOfReactants.add(specref);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public Reaction clone() {
		return new Reaction(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Reaction) {
			boolean equal = super.equals(o);
			Reaction r = (Reaction) o;
			equal &= r.getFast() == getFast();
			if (r.isSetKineticLaw() && isSetKineticLaw())
				equal &= r.getKineticLaw().equals(kineticLaw);
			equal &= r.getReversible() == getReversible();
			equal &= r.getListOfReactants().equals(listOfReactants);
			equal &= r.getListOfModifiers().equals(listOfModifiers);
			equal &= r.getListOfProducts().equals(listOfProducts);
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getFast() {
		return fast;
	}

	/**
	 * 
	 * @return
	 */
	public KineticLaw getKineticLaw() {
		return kineticLaw;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<ModifierSpeciesReference> getListOfModifiers() {
		return listOfModifiers;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<SpeciesReference> getListOfProducts() {
		return listOfProducts;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<SpeciesReference> getListOfReactants() {
		return listOfReactants;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public ModifierSpeciesReference getModifier(int i) {
		return listOfModifiers.get(i);
	}

	/**
	 * 
	 * @return
	 */
	public int getNumModifiers() {
		return listOfModifiers.size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumProducts() {
		return listOfProducts.size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumReactants() {
		return listOfReactants.size();
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SpeciesReference getProduct(int i) {
		return listOfProducts.get(i);
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SpeciesReference getReactant(int i) {
		return listOfReactants.get(i);
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getReversible() {
		return reversible;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetKineticLaw() {
		return kineticLaw != null;
	}

	/**
	 * 
	 * @param modspecref
	 */
	public void removeModifier(ModifierSpeciesReference modspecref) {
		if (listOfModifiers.remove(modspecref))
			modspecref.sbaseRemoved();
	}

	/**
	 * 
	 * @param specref
	 */
	public void removeProduct(SpeciesReference specref) {
		if (listOfProducts.remove(specref))
			specref.sbaseRemoved();
	}

	/**
	 * 
	 * @param specref
	 */
	public void removeReactant(SpeciesReference specref) {
		if (listOfReactants.remove(specref))
			specref.sbaseRemoved();
	}

	/**
	 * 
	 * @param fast
	 */
	public void setFast(Boolean fast) {
		this.fast = fast;
		stateChanged();
	}

	/**
	 * 
	 * @param kineticLaw
	 */
	public void setKineticLaw(KineticLaw kineticLaw) {
		this.kineticLaw = kineticLaw;
		this.kineticLaw.parentSBMLObject = this;
		this.kineticLaw.sbaseAdded();
		stateChanged();
	}

	/**
	 * 
	 * @param reversible
	 */
	public void setReversible(Boolean reversible) {
		this.reversible = reversible;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getParentSBMLObject()
	 */
	// @Override
	public Model getParentSBMLObject() {
		return (Model) super.getParentSBMLObject();
	}
}
