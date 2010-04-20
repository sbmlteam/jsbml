/*
 * $Id$
 * $URL$
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

package org.sbml.jsbml;

import java.util.HashMap;

/**
 * Represents the reaction XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * 
 * @composed 0..* reactant 1 SpeciesReference
 * @composed 0..* product 1 SpeciesReference
 * @composed 0..* modifier 1 ModifierSpeciesReference
 * @composed 0..1 kineticLaw 1 KineticLaw
 */
public class Reaction extends AbstractNamedSBase implements SBaseWithDerivedUnit {

	/**
	 * Represents the 'compartment' XML attribute of a reaction element.
	 */
	private String compartmentID;
	/**
	 * Represents the 'fast' XML attribute of a reaction element.
	 */
	private Boolean fast;
	private boolean isSetFast = false;
	
	/**
	 * Represents the 'kineticLaw' XML subNode of a reaction element.
	 */
	private KineticLaw kineticLaw;
	/**
	 * Represents the 'listOfModifiers' XML subNode of a reaction element.
	 */
	private ListOf<ModifierSpeciesReference> listOfModifiers;
	/**
	 * Represents the 'listOfProducts' XML subNode of a reaction element.
	 */
	private ListOf<SpeciesReference> listOfProducts;
	/**
	 * Represents the 'listOfReactants' XML subNode of a reaction element.
	 */
	private ListOf<SpeciesReference> listOfReactants;
	/**
	 * Represents the 'reversible' XML attribute of a reaction element.
	 */
	private Boolean reversible;
	private boolean isSetReversible = false;
	
	/**
	 * Creates a Reaction instance. By default, the compartmentID, kineticLaw,
	 * listOfReactants, listOfProducts and listOfModifiers are null.
	 */
	public Reaction() {
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public Reaction(int level, int version) {
		super(level, version);
		listOfReactants = new ListOf<SpeciesReference>(level, version);
		listOfReactants.parentSBMLObject = this;
		listOfProducts = new ListOf<SpeciesReference>(level, version);
		listOfProducts.parentSBMLObject = this;
		listOfModifiers = new ListOf<ModifierSpeciesReference>(level, version);
		listOfModifiers.parentSBMLObject = this;

		if (level < 3) {
			initDefaults();
		}
	}

	/**
	 * Creates a Reaction instance from a given reaction.
	 * 
	 * @param reaction
	 */
	public Reaction(Reaction reaction) {
		super(reaction);
		
		if (reaction.isSetFast()) {
			this.fast = new Boolean(reaction.getFast());
		}
		if (reaction.isSetKineticLaw()) {
			setKineticLaw(reaction.getKineticLaw().clone());
		}
		if (reaction.isSetListOfReactants()) {
			this.listOfReactants = (ListOf<SpeciesReference>) reaction
					.getListOfReactants().clone();
			setThisAsParentSBMLObject(listOfReactants);
		}
		if (reaction.isSetListOfProducts()) {
			this.listOfProducts = (ListOf<SpeciesReference>) reaction
					.getListOfProducts().clone();
			setThisAsParentSBMLObject(listOfProducts);
		}
		if (reaction.isSetListOfModifiers()) {
			this.listOfModifiers = (ListOf<ModifierSpeciesReference>) reaction
					.getListOfModifiers().clone();
			setThisAsParentSBMLObject(listOfModifiers);
		}
		if (isSetReversible()) {
			this.reversible = new Boolean(reaction.getReversible());
		}
	}

	/**
	 * Creates a Reaction instance from an id, level and version. By default,
	 * the compartmentID, kineticLaw, listOfReactants, listOfProducts and
	 * listOfModifiers are null.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Reaction(String id, int level, int version) {
		super(id, level, version);

		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.element.SBase#addChangeListener(org.sbml.squeezer.io.
	 * SBaseChangedListener )
	 */
	public void addChangeListener(SBaseChangedListener l) {
		super.addChangeListener(l);
		if (!isSetListOfReactants())
			listOfReactants = new ListOf<SpeciesReference>(getLevel(),
					getVersion());
		listOfReactants.addChangeListener(l);
		if (!isSetListOfProducts())
			listOfProducts = new ListOf<SpeciesReference>(getLevel(),
					getVersion());
		listOfProducts.addChangeListener(l);
		if (!isSetListOfModifiers())
			listOfModifiers = new ListOf<ModifierSpeciesReference>(getLevel(),
					getVersion());
		listOfModifiers.addChangeListener(l);
	}

	/**
	 * Adds a ModifierSpeciesReference instance to this Reaction.
	 * 
	 * @param modspecref
	 */
	public void addModifier(ModifierSpeciesReference modspecref) {
		if (!isSetListOfModifiers()) {
			this.listOfModifiers = new ListOf<ModifierSpeciesReference>();
			setThisAsParentSBMLObject(this.listOfModifiers);
		}

		setThisAsParentSBMLObject(modspecref);
		listOfModifiers.add(modspecref);
	}

	/**
	 * Adds a SpeciesReference instance to the listOfProducts of this Reaction.
	 * 
	 * @param specref
	 */
	public void addProduct(SpeciesReference specref) {
		if (!isSetListOfProducts()) {
			this.listOfProducts = new ListOf<SpeciesReference>();
			setThisAsParentSBMLObject(this.listOfProducts);
		}

		setThisAsParentSBMLObject(specref);
		listOfProducts.add(specref);
	}

	/**
	 * Adds a SpeciesReference instance to the listOfReactants of this Reaction.
	 * 
	 * @param specref
	 */
	public void addReactant(SpeciesReference specref) {
		if (!isSetListOfReactants()) {
			this.listOfReactants = new ListOf<SpeciesReference>();
			setThisAsParentSBMLObject(this.listOfReactants);
		}

		setThisAsParentSBMLObject(specref);
		listOfReactants.add(specref);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	// @Override
	public Reaction clone() {
		return new Reaction(this);
	}

	/**
	 * Creates a new <code>ModifierSpeciesReference</code>, adds it to this
	 * Reaction's list of modifiers and returns it.
	 * 
	 * @return a new ModifierSpeciesReference object.
	 */
	public ModifierSpeciesReference createModifier() {
		ModifierSpeciesReference modifier = new ModifierSpeciesReference(level,
				version);
		addModifier(modifier);

		return modifier;
	}

	/**
	 * Creates a new <code>SpeciesReference</code>, adds it to this Reaction's
	 * list of products and returns it.
	 * 
	 * @return a new SpeciesReference object.
	 * 
	 * @return
	 */
	public SpeciesReference createProduct() {
		SpeciesReference product = new SpeciesReference(level, version);
		addProduct(product);

		return product;
	}

	/**
	 * Creates a new <code>SpeciesReference</code>, adds it to this Reaction's
	 * list of reactants and returns it.
	 * 
	 * @return a new SpeciesReference object.
	 * 
	 * @return
	 */
	public SpeciesReference createReactant() {
		SpeciesReference reactant = new SpeciesReference(level, version);
		addReactant(reactant);

		return reactant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Reaction) {
			boolean equal = super.equals(o);
			Reaction r = (Reaction) o;
			equal &= r.getFast() == getFast();
			equal &= r.isSetKineticLaw() == isSetKineticLaw();
			if (equal && isSetKineticLaw()) {
				equal &= r.getKineticLaw().equals(kineticLaw);
			}
			equal &= r.getReversible() == getReversible();
			equal &= r.isSetListOfReactants() == isSetListOfReactants();
			if (equal && isSetListOfReactants()) {
				equal &= r.getListOfReactants().equals(listOfReactants);
			}
			equal &= r.isSetListOfProducts() == isSetListOfProducts();
			if (equal && isSetListOfProducts()) {
				equal &= r.getListOfProducts().equals(listOfProducts);
			}
			equal &= r.isSetListOfModifiers() == isSetListOfModifiers();
			if (equal && isSetListOfModifiers()) {
				equal &= r.getListOfModifiers().equals(listOfModifiers);
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the compartmentID of this Reaction. The empty String if it is not
	 *         set.
	 */
	public String getCompartment() {
		return isSetCompartment() ? this.compartmentID : "";
	}

	/**
	 * 
	 * @return the Compartment instance which has the compartmentID of this
	 *         Reaction as id. Can be null if it doesn't exist.
	 */
	public Compartment getCompartmentInstance() {
		Model m = getModel();
		return m != null ? m.getCompartment(this.compartmentID) : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	public String getDerivedUnits() {
		if (isSetKineticLaw())
			return kineticLaw.getDerivedUnits();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		if (isSetKineticLaw())
			return kineticLaw.getDerivedUnitDefinition();
		return null;
	}

	/**
	 * 
	 * @return the fast Boolean of this Reaction.
	 */
	public boolean getFast() {
		return isSetFast() ? fast : false;
	}

	/**
	 * 
	 * @return the kineticLaw of this Reaction. Can be null if not set.
	 */
	public KineticLaw getKineticLaw() {
		return kineticLaw;
	}

	/**
	 * 
	 * @return the listOfModifiers of this Reaction. Is initialized here if not
	 *         yet set.
	 */
	public ListOf<ModifierSpeciesReference> getListOfModifiers() {
		if (!isSetListOfModifiers())
			listOfModifiers = new ListOf<ModifierSpeciesReference>();
		return listOfModifiers;
	}

	/**
	 * 
	 * @return the listOfProducts of this Reaction. Is initialized here if not
	 *         yet set.
	 */
	public ListOf<SpeciesReference> getListOfProducts() {
		if (!isSetListOfProducts())
			listOfProducts = new ListOf<SpeciesReference>();
		return listOfProducts;
	}

	/**
	 * 
	 * @return the listOfReactants of this Reaction. Is initialized here if not
	 *         yet set.
	 */
	public ListOf<SpeciesReference> getListOfReactants() {
		if (!isSetListOfReactants())
			listOfReactants = new ListOf<SpeciesReference>();
		return listOfReactants;
	}

	/**
	 * 
	 * @param i
	 * @return the ith ModifierSpeciesReference of the listOfModifiers. Can be
	 *         null if it doesn't exist.
	 */
	public ModifierSpeciesReference getModifier(int i) {
		if (isSetListOfModifiers()) {
			return listOfModifiers.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the ModifierSpeciesReference of the listOfModifiers which has
	 *         'id' as id (or name depending on the level and version). Can be
	 *         null if it doesn't exist.
	 */
	public ModifierSpeciesReference getModifier(String id) {
		if (isSetListOfModifiers()) {
			for (ModifierSpeciesReference msp : listOfModifiers) {
				if (msp.isSetId()) {
					if (msp.getId().equals(id)) {
						return msp;
					}
				} else if (msp.isSetName()) {
					if (msp.getName().equals(id)) {
						return msp;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return the number of ModifierSpeciesReferences of this Reaction.
	 */
	public int getNumModifiers() {
		if (isSetListOfModifiers()) {
			return listOfModifiers.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of products SpeciesReference.
	 */
	public int getNumProducts() {
		if (isSetListOfProducts()) {
			return listOfProducts.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return the number of reactants SpeciesReference.
	 */
	public int getNumReactants() {
		if (isSetListOfReactants()) {
			return listOfReactants.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getParentSBMLObject()
	 */
	// @Override
	public Model getParentSBMLObject() {
		return (Model) super.getParentSBMLObject();
	}

	/**
	 * 
	 * @param i
	 * @return the ith product SpeciesReference of the listOfProducts. Can be
	 *         null if it doesn't exist.
	 */
	public SpeciesReference getProduct(int i) {
		if (isSetListOfProducts()) {
			return listOfProducts.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the SpeciesReference of the listOfProducts which has 'id' as id
	 *         (or name depending on the level and version). Can be null if it
	 *         doesn't exist.
	 */
	public SpeciesReference getProduct(String id) {
		if (isSetListOfProducts()) {
			for (SpeciesReference sp : listOfProducts) {
				if (sp.isSetId()) {
					if (sp.getId().equals(id)) {
						return sp;
					}
				} else if (sp.isSetName()) {
					if (sp.getName().equals(id)) {
						return sp;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param i
	 * @return the ith reactant SpeciesReference of the listOfReactants. Can be
	 *         null if it doesn't exist.
	 */
	public SpeciesReference getReactant(int i) {
		if (isSetListOfReactants()) {
			return listOfReactants.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return the SpeciesReference of the listOfReactants which has 'id' as id
	 *         (or name depending on the level and version). Can be null if it
	 *         doesn't exist.
	 */
	public SpeciesReference getReactant(String id) {
		if (isSetListOfReactants()) {
			for (SpeciesReference sp : listOfReactants) {
				if (sp.isSetId()) {
					if (sp.getId().equals(id)) {
						return sp;
					}
				} else if (sp.isSetName()) {
					if (sp.getName().equals(id)) {
						return sp;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return the reversible Boolean of this reaction.
	 */
	public boolean getReversible() {
		return isSetReversible() ? reversible : false;
	}

	public boolean hasModifier(Species s) {
		return references(listOfModifiers, s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean hasProduct(Species s) {
		return references(listOfProducts, s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean hasReactant(Species s) {
		return references(listOfReactants, s);
	}

	/**
	 * Initialises the default variables of this Reaction.
	 */
	public void initDefaults() {
		reversible = new Boolean(true);
		fast = new Boolean(false);
	}

	/**
	 * Convenient test if the given species takes part in this reaction as a
	 * reactant, product, or modifier.
	 * 
	 * @param s
	 * @return
	 */
	public boolean involves(Species s) {
		return hasReactant(s) || hasProduct(s) || hasModifier(s);
	}

	/**
	 * 
	 * @return the boolean value of fast if it is set, false otherwise.
	 */
	public boolean isFast() {
		return isSetFast() ? fast : false;
	}

	/**
	 * 
	 * @return the value of reversible if it is set, false otherwise.
	 */
	public boolean isReversible() {
		return isSetReversible() ? reversible : false;
	}

	/**
	 * 
	 * @return true if the compartmentID of this Reaction is not null;
	 */
	public boolean isSetCompartment() {
		return this.compartmentID != null;
	}

	/**
	 * 
	 * @return true if the Compartment which has the compartmentID of this
	 *         Reaction as id is not null;
	 */
	public boolean isSetCompartmentInstance() {
		Model m = getModel();
		return m != null ? m.getCompartment(this.compartmentID) != null : false;
	}

	/**
	 * 
	 * @return true if fast is not null.
	 */
	public boolean isSetFast() {
		return isSetFast;
	}

	/**
	 * 
	 * @return true if the kineticLaw of this Reaction is not null.
	 */
	public boolean isSetKineticLaw() {
		return kineticLaw != null;
	}

	/**
	 * 
	 * @return true if the listOfModifiers of this Reaction is not null.
	 */
	public boolean isSetListOfModifiers() {
		return listOfModifiers != null;
	}

	/**
	 * 
	 * @return true if the listOfProducts of this reaction is not null.
	 */
	public boolean isSetListOfProducts() {
		return listOfProducts != null;
	}

	/**
	 * 
	 * @return true if the listOfReactants of this Reaction is not null.
	 */
	public boolean isSetListOfReactants() {
		return listOfReactants != null;
	}

	/**
	 * 
	 * @return true if reversible is not null.
	 */
	public boolean isSetReversible() {
		return isSetReversible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead) {
			if (attributeName.equals("reversible")) {
				if (value.equals("true")) {
					this.setReversible(true);
					return true;
				} else if (value.equals("false")) {
					this.setReversible(false);
					return true;
				}
			} else if (attributeName.equals("fast")) {
				if (value.equals("true")) {
					this.setFast(true);
					return true;
				} else if (value.equals("false")) {
					this.setFast(false);
					return true;
				}
			}
			if (attributeName.equals("compartment") && getLevel() == 3) {
				this.setCompartment(value);
				return true;
			}
		}
		return isAttributeRead;
	}

	/**
	 * Checks whether the given list references the given species.
	 * 
	 * @param list
	 * @param s
	 * @return
	 */
	private boolean references(ListOf<? extends SimpleSpeciesReference> list,
			Species s) {
		for (SimpleSpeciesReference specRef : list)
			if (specRef.getSpecies().equals(s.getId()))
				return true;
		return false;
	}

	/**
	 * Removes the nth modifier species (ModifierSpeciesReference object) in the
	 * list of modifiers in this Reaction and returns it.
	 * 
	 * @param i
	 *            the index of the ModifierSpeciesReference object to remove.
	 * @return the removed ModifierSpeciesReference object, or null if the given
	 *         index is out of range.
	 */
	public ModifierSpeciesReference removeModifier(int i) {

		if (i >= listOfModifiers.size() || i < 0) {
			System.out.println("removeModifier : index out of bound.");
			return null;
		}

		ModifierSpeciesReference modifier = listOfModifiers.get(i);
		listOfModifiers.remove(i);

		return modifier;
	}

	/**
	 * Removes the ModifierSpeciesReference 'modspecref' from this Reaction.
	 * 
	 * @param modspecref
	 */
	public void removeModifier(ModifierSpeciesReference modspecref) {
		if (isSetListOfModifiers()) {
			if (listOfModifiers.remove(modspecref)) {
				modspecref.sbaseRemoved();
			}
		}
	}

	/**
	 * Removes the modifier species (ModifierSpeciesReference object) having the
	 * given 'species' attribute in the list of modifiers in this Reaction and
	 * returns it.
	 * 
	 * @param id
	 *            the 'species' attribute of the ModifierSpeciesReference object
	 *            (which correspond to a species id).
	 * @return
	 */
	public ModifierSpeciesReference removeModifier(String id) {

		ModifierSpeciesReference deletedModifier = null;
		int index = 0;

		for (ModifierSpeciesReference modifier : listOfModifiers) {
			if (modifier.getSpecies().equals(id)) {
				deletedModifier = modifier;
				break;
			}
			index++;
		}

		if (deletedModifier != null) {
			listOfModifiers.remove(index);
		}

		return deletedModifier;
	}

	/**
	 * Removes the nth product species (SpeciesReference object) in the list of
	 * products in this Reaction and returns it.
	 * 
	 * @param i
	 *            the index of the SpeciesReference object to remove.
	 * @return the removed SpeciesReference object, or null if the given index
	 *         is out of range.
	 */
	public SpeciesReference removeProduct(int i) {

		if (i >= listOfProducts.size() || i < 0) {
			return null;
		}

		SpeciesReference product = listOfProducts.get(i);
		listOfProducts.remove(i);

		return product;
	}

	/**
	 * Removes the SpeciesReference 'modspecref' from the listOfProducts of this
	 * Reaction.
	 * 
	 * @param specref
	 */
	public void removeProduct(SpeciesReference specref) {
		if (isSetListOfProducts()) {
			if (listOfProducts.remove(specref)) {
				specref.sbaseRemoved();
			}
		}
	}

	/**
	 * Removes the product species (SpeciesReference object) having the given
	 * 'species' attribute in the list of products in this Reaction and returns
	 * it.
	 * 
	 * @param id
	 *            the 'species' attribute of the SpeciesReference object (which
	 *            correspond to a species id).
	 * @return
	 */
	public Object removeProduct(String id) {

		SpeciesReference deletedProduct = null;
		int index = 0;

		for (SpeciesReference product : listOfProducts) {
			if (product.getSpecies().equals(id)) {
				deletedProduct = product;
				break;
			}
			index++;
		}

		if (deletedProduct != null) {
			listOfProducts.remove(index);
		}

		return deletedProduct;
	}

	/**
	 * Removes the nth reactant species (SpeciesReference object) in the list of
	 * reactants in this Reaction and returns it.
	 * 
	 * @param i
	 *            the index of the SpeciesReference object to remove.
	 * @return the removed SpeciesReference object, or null if the given index
	 *         is out of range.
	 */
	public Object removeReactant(int i) {

		if (i >= listOfReactants.size() || i < 0) {
			return null;
		}

		SpeciesReference reactant = listOfReactants.get(i);
		listOfReactants.remove(i);

		return reactant;
	}

	/**
	 * Removes the SpeciesReference 'modspecref' from the listOfReactants of
	 * this Reaction.
	 * 
	 * @param specref
	 */
	public void removeReactant(SpeciesReference specref) {
		if (isSetListOfReactants()) {
			if (listOfReactants.remove(specref)) {
				specref.sbaseRemoved();
			}
		}
	}

	/**
	 * Removes the reactant species (SpeciesReference object) having the given
	 * 'species' attribute in the list of reactants in this Reaction and returns
	 * it.
	 * 
	 * @param id
	 *            the 'species' attribute of the SpeciesReference object (which
	 *            correspond to a species id).
	 * @return
	 */
	public Object removeReactant(String id) {

		SpeciesReference deletedReactant = null;
		int index = 0;

		for (SpeciesReference reactant : listOfReactants) {
			if (reactant.getSpecies().equals(id)) {
				deletedReactant = reactant;
				break;
			}
			index++;
		}

		if (deletedReactant != null) {
			listOfReactants.remove(index);
		}

		return deletedReactant;
	}

	/**
	 * Sets the compartmentID of this Reaction to the id of the Compartment
	 * 'compartment'.
	 * 
	 * @param compartment
	 */
	public void setCompartment(Compartment compartment) {
		this.compartmentID = compartment != null ? compartment.getId() : null;
		stateChanged();
	}

	/**
	 * Sets the compartmentID of this Reaction to 'compartmentID'.
	 * 
	 * @param compartmentID
	 */
	public void setCompartment(String compartmentID) {
		this.compartmentID = compartmentID;
	}

	/**
	 * Sets the fast Boolean of this Reaction.
	 * 
	 * @param fast
	 */
	public void setFast(Boolean fast) {
		this.fast = fast;
		isSetFast = true;
		stateChanged();
	}

	/**
	 * Sets the kineticLaw of this Reaction.
	 * 
	 * @param kineticLaw
	 */
	public void setKineticLaw(KineticLaw kineticLaw) {
		this.kineticLaw = kineticLaw;
		setThisAsParentSBMLObject(this.kineticLaw);
		this.kineticLaw.sbaseAdded();
		stateChanged();
	}

	/**
	 * Sets the listOfModifiers of this Reaction. Automatically sets the
	 * parentSBML object of the list to this Reaction instance.
	 * 
	 * @param list
	 */
	public void setListOfModifiers(ListOf<ModifierSpeciesReference> list) {
		this.listOfModifiers = list;
		setThisAsParentSBMLObject(this.listOfModifiers);
		this.listOfModifiers.setSBaseListType(ListOf.Type.listOfModifiers);
		stateChanged();
	}

	/**
	 * Sets the listOfProducts of this Reaction. Automatically sets the
	 * parentSBML object of the list to this Reaction instance.
	 * 
	 * @param list
	 */
	public void setListOfProducts(ListOf<SpeciesReference> list) {
		this.listOfProducts = list;
		setThisAsParentSBMLObject(this.listOfProducts);
		this.listOfProducts.setSBaseListType(ListOf.Type.listOfProducts);

		stateChanged();
	}

	/**
	 * Sets the listOfReactants of this Reaction. Automatically sets the
	 * parentSBML object of the list to this Reaction instance.
	 * 
	 * @param list
	 */
	public void setListOfReactants(ListOf<SpeciesReference> list) {
		this.listOfReactants = list;
		setThisAsParentSBMLObject(this.listOfReactants);
		this.listOfReactants.setSBaseListType(ListOf.Type.listOfReactants);

		stateChanged();
	}

	/**
	 * Sets the reversible Boolean of this Reaction.
	 * 
	 * @param reversible
	 */
	public void setReversible(Boolean reversible) {
		this.reversible = reversible;
		isSetReversible = true;
		stateChanged();
	}

	/**
	 * Sets the fast Boolean of this Reaction to null.
	 */
	public void unsetFast() {
		isSetFast = false;
		fast = null;
	}

	/**
	 * Sets the reversible Boolean of this Reaction to null.
	 */
	public void unsetReversible() {
		isSetReversible = false;
		reversible = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetReversible()) {
			attributes.put("reversible", Boolean.toString(getReversible()));
		}

		if (isSetFast()) {
			attributes.put("fast", Boolean.toString(getFast()));
		}

		if (isSetCompartment() && getLevel() == 3) {
			attributes.put("compartment", getCompartment());
		}

		return attributes;
	}
}
