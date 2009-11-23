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
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @author marine
 * 
 */
public class Reaction extends AbstractNamedSBase {

	private boolean reversible;
	private boolean fast;
	private Compartment compartment;
	private String compartmentID;

	private ListOf<SpeciesReference> listOfReactants;
	private ListOf<SpeciesReference> listOfProducts;
	private ListOf<ModifierSpeciesReference> listOfModifiers;
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
	@SuppressWarnings("unchecked")
	public Reaction(Reaction reaction) {
		super(reaction);
		this.fast = reaction.getFast();
		if (reaction.isSetKineticLaw()){
			setKineticLaw(reaction.getKineticLaw().clone());
		}
		this.listOfReactants = (ListOf<SpeciesReference>) reaction.getListOfReactants().clone();
		setThisAsParentSBMLObject(listOfReactants);
		this.listOfProducts = (ListOf<SpeciesReference>) reaction.getListOfProducts().clone();
		setThisAsParentSBMLObject(listOfProducts);
		this.listOfModifiers = (ListOf<ModifierSpeciesReference>) reaction.getListOfModifiers().clone();
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
		if (!isSetListOfModifiers()){
			this.listOfModifiers = new ListOf<ModifierSpeciesReference>();
		}
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
		if (!isSetListOfProducts()){
			this.listOfProducts = new ListOf<SpeciesReference>();
		}
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
		if (!isSetListOfReactants()){
			this.listOfReactants = new ListOf<SpeciesReference>();
		}
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
		if (isSetListOfModifiers()){
			return listOfModifiers.get(i);
		}
		return null;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public ModifierSpeciesReference getModifier(String id) {
		if (isSetListOfModifiers()){
			for (ModifierSpeciesReference msp : listOfModifiers) {
				if (msp.getId().equals(id)){
					return msp;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumModifiers() {
		if (isSetListOfModifiers()){
			return listOfModifiers.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumProducts() {
		if (isSetListOfProducts()){
			return listOfProducts.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumReactants() {
		if (isSetListOfReactants()){
			return listOfReactants.size();
		}
		return 0;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SpeciesReference getProduct(int i) {
		if (isSetListOfProducts()){
			return listOfProducts.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SpeciesReference getReactant(int i) {
		if (isSetListOfReactants()){
			return listOfReactants.get(i);
		}
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesReference getProduct(String id) {
		if (isSetListOfProducts()){
			for (SpeciesReference sp : listOfProducts) {
				if (sp.getId().equals(id)){
					return sp;
				}
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
		if (isSetListOfReactants()){
			for (SpeciesReference sp : listOfReactants) {
				if (sp.getId().equals(id)){
					return sp;
				}
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
		if (isSetListOfModifiers()){
			if (listOfModifiers.remove(modspecref))
				modspecref.sbaseRemoved();
		}
	}

	/**
	 * 
	 * @param specref
	 */
	public void removeProduct(SpeciesReference specref) {
		if (isSetListOfProducts()){
			if (listOfProducts.remove(specref))
				specref.sbaseRemoved();
		}
	}

	/**
	 * 
	 * @param specref
	 */
	public void removeReactant(SpeciesReference specref) {
		if (isSetListOfReactants()){
			if (listOfReactants.remove(specref))
				specref.sbaseRemoved();
		}
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
	public void setListOfReactants(ListOf<SpeciesReference> list) {
		this.listOfReactants = list;
		this.listOfReactants.setCurrentList(CurrentListOfSBMLElements.listOfReactants);

		stateChanged();
	}
	
	/**
	 * 
	 * @param list
	 */
	public void setListOfProducts(ListOf<SpeciesReference> list) {
		this.listOfProducts = list;
		this.listOfProducts.setCurrentList(CurrentListOfSBMLElements.listOfProducts);

		stateChanged();
	}
	
	/**
	 * 
	 * @param list
	 */
	public void setListOfModifiers(ListOf<ModifierSpeciesReference> list) {
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
