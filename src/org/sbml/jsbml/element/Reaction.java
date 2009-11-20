/*
 * $Id: Reaction.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Reaction.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.element;

import org.sbml.jsbml.xml.CurrentListOfSBMLElements;


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
	private Compartment compartment;
	private String compartmentID;

	private ListOf listOfReactants;
	private ListOf listOfProducts;
	private ListOf listOfModifiers;
	private KineticLaw kineticLaw;

	/**
	 * 
	 */
	public Reaction() {
		super();
		this.kineticLaw = null;
		this.listOfReactants = null;
		this.listOfProducts = null;
		this.listOfModifiers = null;
		this.compartment = null;
		this.compartmentID = null;
	}

	
	/**
	 * 
	 * @param reaction
	 */
	public Reaction(Reaction reaction) {
		super(reaction);
		this.fast = reaction.getFast();
		if (reaction.isSetKineticLaw()){
			setKineticLaw(reaction.getKineticLaw().clone());
		}
		this.listOfReactants = (ListOf) reaction.getListOfReactants().clone();
		setThisAsParentSBMLObject(listOfReactants);
		this.listOfProducts = (ListOf) reaction.getListOfProducts().clone();
		setThisAsParentSBMLObject(listOfProducts);
		this.listOfModifiers = (ListOf) reaction.getListOfModifiers().clone();
		setThisAsParentSBMLObject(listOfModifiers);
		this.reversible = reaction.getReversible();
	}

	/**
	 * 
	 * @param id
	 */
	public Reaction(String id, int level, int version) {
		super(id, level, version);
		listOfReactants = new ListOf(level, version);
		listOfReactants.parentSBMLObject = this;
		listOfProducts = new ListOf(level, version);
		listOfProducts.parentSBMLObject = this;
		listOfModifiers = new ListOf(level, version);
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
		if (!listOfModifiers.getListOf().contains(modspecref)) {
			modspecref.parentSBMLObject = this;
			listOfModifiers.getListOf().add(modspecref);
		}
	}

	/**
	 * 
	 * @param specref
	 */
	public void addProduct(SpeciesReference specref) {
		if (!listOfProducts.getListOf().contains(specref)) {
			specref.parentSBMLObject = this;
			listOfProducts.getListOf().add(specref);
		}
	}

	/**
	 * 
	 * @param specref
	 */
	public void addReactant(SpeciesReference specref) {
		if (!listOfReactants.getListOf().contains(specref)) {
			specref.parentSBMLObject = this;
			listOfReactants.getListOf().add(specref);
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
			if (r.isSetListOfReactants() && isSetListOfReactants()){
				equal &= r.getListOfReactants().equals(listOfReactants);
			}
			if (r.isSetListOfProducts() && isSetListOfProducts()){
				equal &= r.getListOfProducts().equals(listOfProducts);
			}
			if (r.isSetListOfModifiers() && isSetListOfModifiers()){
				equal &= r.getListOfModifiers().equals(listOfModifiers);
			}
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
	public ListOf getListOfModifiers() {
		return listOfModifiers;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfProducts() {
		return listOfProducts;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf getListOfReactants() {
		return listOfReactants;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public ModifierSpeciesReference getModifier(int i) {
		return (ModifierSpeciesReference) listOfModifiers.getListOf().get(i);
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public ModifierSpeciesReference getModifier(String id) {
		for (SBase c : listOfModifiers.getListOf()) {
			ModifierSpeciesReference msp = (ModifierSpeciesReference) c;
			if (msp.getId().equals(id)){
				return msp;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumModifiers() {
		return listOfModifiers.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumProducts() {
		return listOfProducts.getListOf().size();
	}

	/**
	 * 
	 * @return
	 */
	public int getNumReactants() {
		return listOfReactants.getListOf().size();
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SpeciesReference getProduct(int i) {
		return (SpeciesReference) listOfProducts.getListOf().get(i);
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SpeciesReference getReactant(int i) {
		return (SpeciesReference) listOfReactants.getListOf().get(i);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesReference getProduct(String id) {
		for (SBase c : listOfProducts.getListOf()) {
			SpeciesReference sp = (SpeciesReference) c;
			if (sp.getId().equals(id)){
				return sp;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesReference getReactant(String id) {
		for (SBase c : listOfReactants.getListOf()) {
			SpeciesReference sp = (SpeciesReference) c;
			if (sp.getId().equals(id)){
				return sp;
			}
		}
		return null;
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
	 * @return
	 */
	public boolean isSetListOfReactants() {
		return listOfReactants != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfProducts() {
		return listOfProducts != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfModifiers() {
		return listOfModifiers != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetCompartment() {
		return compartment != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetCompartmentID() {
		return compartmentID != null;
	}

	/**
	 * 
	 * @param modspecref
	 */
	public void removeModifier(ModifierSpeciesReference modspecref) {
		if (listOfModifiers.getListOf().remove(modspecref))
			modspecref.sbaseRemoved();
	}

	/**
	 * 
	 * @param specref
	 */
	public void removeProduct(SpeciesReference specref) {
		if (listOfProducts.getListOf().remove(specref))
			specref.sbaseRemoved();
	}

	/**
	 * 
	 * @param specref
	 */
	public void removeReactant(SpeciesReference specref) {
		if (listOfReactants.getListOf().remove(specref))
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
	
	/**
	 * 
	 * @param list
	 */
	public void setListOfReactants(ListOf list) {
		this.listOfReactants = list;
		this.listOfReactants.setCurrentList(CurrentListOfSBMLElements.listOfReactants);

		stateChanged();
	}
	
	/**
	 * 
	 * @param list
	 */
	public void setListOfProducts(ListOf list) {
		this.listOfProducts = list;
		this.listOfProducts.setCurrentList(CurrentListOfSBMLElements.listOfProducts);

		stateChanged();
	}
	
	/**
	 * 
	 * @param list
	 */
	public void setListOfModifiers(ListOf list) {
		this.listOfModifiers = list;
		this.listOfModifiers.setCurrentList(CurrentListOfSBMLElements.listOfModifiers);
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
	
	public Compartment getCompartment() {
		return compartment;
	}


	public void setCompartment(Compartment compartment) {
		this.compartment = compartment;
	}


	public String getCompartmentID() {
		return compartmentID;
	}


	public void setCompartmentID(String compartmentID) {
		this.compartmentID = compartmentID;
	}


	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}


	public void setFast(boolean fast) {
		this.fast = fast;
	}


	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("reversible")){
				if (value.equals("true")){
					this.setReversible(true);
					return true;
				}
				else if (value.equals("false")){
					this.setReversible(false);
					return true;
				}
			}
			else if (attributeName.equals("fast")){
				if (value.equals("true")){
					this.setFast(true);
					return true;
				}
				else if (value.equals("false")){
					this.setFast(false);
					return true;
				}
			}
			if (attributeName.equals("compartment")){
				this.setCompartmentID(value);
				return true;
			}
		}
		return isAttributeRead;
	}
}
