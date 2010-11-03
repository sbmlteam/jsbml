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

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.filters.NameFilter;

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
public class Reaction extends AbstractNamedSBase implements
		NamedSBaseWithDerivedUnit {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1385417662249487643L;
	/**
	 * Represents the 'compartment' XML attribute of a reaction element.
	 */
	private String compartmentID;
	/**
	 * Represents the 'fast' XML attribute of a reaction element.
	 */
	private Boolean fast;
	/**
	 * 
	 */
	private boolean isSetFast = false;

	/**
	 * 
	 */
	private boolean isSetReversible = false;
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

	/**
	 * Creates a Reaction instance. By default, the compartmentID, kineticLaw,
	 * listOfReactants, listOfProducts and listOfModifiers are null.
	 */
	public Reaction() {
		super();
		initDefaults();
	}

	/**
	 * 
	 * @param id
	 */
	public Reaction(int level, int version) {
		super(level, version);
		initDefaults();
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
	 * 
	 * @param id
	 */
	public Reaction(String id) {
		super(id);
		initDefaults();
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
		initDefaults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.element.SBase#addChangeListener(org.sbml.squeezer.io.
	 * SBaseChangedListener )
	 */
	public void addChangeListener(SBaseChangedListener l) {
		super.addChangeListener(l);
		listOfReactants.addChangeListener(l);
		listOfProducts.addChangeListener(l);
		listOfModifiers.addChangeListener(l);
	}

	/**
	 * Adds a ModifierSpeciesReference instance to this Reaction.
	 * 
	 * @param modspecref
	 */
	public void addModifier(ModifierSpeciesReference modspecref) {
		setThisAsParentSBMLObject(modspecref);
		listOfModifiers.add(modspecref);
	}

	/**
	 * Adds a SpeciesReference instance to the listOfProducts of this Reaction.
	 * 
	 * @param specref
	 */
	public void addProduct(SpeciesReference specref) {
		setThisAsParentSBMLObject(specref);
		listOfProducts.add(specref);
	}

	/**
	 * Adds a SpeciesReference instance to the listOfReactants of this Reaction.
	 * 
	 * @param specref
	 */
	public void addReactant(SpeciesReference specref) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
	 */
	public boolean containsUndeclaredUnits() {
		return isSetKineticLaw() ? getKineticLaw().containsUndeclaredUnits()
				: false;
	}

	/**
	 * Creates a new {@link KineticLaw} object, installs it as this
	 * {@link Reaction}'s 'kineticLaw' sub-element, and returns it.
	 * 
	 * If this {@link Reaction} had a previous KineticLaw, it will be destroyed.
	 * 
	 * @return the new {@link KineticLaw} object
	 */
	public KineticLaw createKineticLaw() {
		KineticLaw kl = new KineticLaw(this);
		setKineticLaw(kl);
		return kl;
	}

	/**
	 * Creates a new <code>ModifierSpeciesReference</code>, adds it to this
	 * Reaction's list of modifiers and returns it.
	 * 
	 * @return a new ModifierSpeciesReference object.
	 */
	public ModifierSpeciesReference createModifier() {
		return createModifier(null);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ModifierSpeciesReference createModifier(String id) {
		ModifierSpeciesReference modifier = new ModifierSpeciesReference(
				getLevel(), getVersion());
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
		return createProduct(null);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesReference createProduct(String id) {
		SpeciesReference product = new SpeciesReference(id, getLevel(),
				getVersion());
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
		return createReactant(null);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public SpeciesReference createReactant(String id) {
		SpeciesReference reactant = new SpeciesReference(id, getLevel(),
				getVersion());
		addReactant(reactant);
		return reactant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#equals(java.lang.Object)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public SBase getChildAt(int index) {
		int children = getChildCount();
		if (index >= children) {
			throw new IndexOutOfBoundsException(index + " >= " + children);
		}
		int pos = 0;
		if (isSetListOfReactants()) {
			if (pos == index) {
				return getListOfReactants();
			}
			pos++;
		}
		if (isSetListOfProducts()) {
			if (pos == index) {
				return getListOfProducts();
			}
			pos++;
		}
		if (isSetListOfModifiers()) {
			if (pos == index) {
				return getListOfModifiers();
			}
			pos++;
		}
		if (isSetKineticLaw()) {
			if (pos == index) {
				return getKineticLaw();
			}
			pos++;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int children = 0;
		if (isSetListOfReactants()) {
			children++;
		}
		if (isSetListOfProducts()) {
			children++;
		}
		if (isSetListOfModifiers()) {
			children++;
		}
		if (isSetKineticLaw()) {
			children++;
		}
		return children;
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
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		if (isSetKineticLaw()) {
			return kineticLaw.getDerivedUnitDefinition();
		}
		UnitDefinition ud = new UnitDefinition(getLevel(), getVersion());
		ud.addUnit(new Unit(getLevel(), getVersion()));
		return ud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	public String getDerivedUnits() {
		return isSetKineticLaw() ? kineticLaw.getDerivedUnits() : null;
	}

	/**
	 * 
	 * @return the fast Boolean of this Reaction.
	 */
	// Not using the isSetFast here to allow the value set in initDefaults() to
	// be returned.
	public boolean getFast() {
		return fast != null ? fast : false;
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
		return listOfModifiers;
	}

	/**
	 * 
	 * @return the listOfProducts of this Reaction. Is initialized here if not
	 *         yet set.
	 */
	public ListOf<SpeciesReference> getListOfProducts() {
		return listOfProducts;
	}

	/**
	 * 
	 * @return the listOfReactants of this Reaction. Is initialized here if not
	 *         yet set.
	 */
	public ListOf<SpeciesReference> getListOfReactants() {
		return listOfReactants;
	}

	/**
	 * 
	 * @param i
	 * @return the ith ModifierSpeciesReference of the listOfModifiers. Can be
	 *         null if it doesn't exist.
	 */
	public ModifierSpeciesReference getModifier(int i) {
		return listOfModifiers.get(i);
	}

	/**
	 * 
	 * @param idOrName
	 * @return the ModifierSpeciesReference of the listOfModifiers which has
	 *         'id' as id (or name depending on the level and version). Can be
	 *         null if it doesn't exist.
	 */
	public ModifierSpeciesReference getModifier(String idOrName) {
		if (isSetListOfModifiers()) {
			for (ModifierSpeciesReference msp : listOfModifiers) {
				if (msp.isSetId()) {
					if (msp.getId().equals(idOrName)) {
						return msp;
					}
				} else if (msp.isSetName()) {
					if (msp.getName().equals(idOrName)) {
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
		return listOfModifiers.size();
	}

	/**
	 * 
	 * @return the number of products SpeciesReference.
	 */
	public int getNumProducts() {
		return listOfProducts.size();
	}

	/**
	 * 
	 * @return the number of reactants SpeciesReference.
	 */
	public int getNumReactants() {
		return listOfReactants.size();
	}

	/**
	 * 
	 * @param i
	 * @return the ith product SpeciesReference of the listOfProducts. Can be
	 *         null if it doesn't exist.
	 */
	public SpeciesReference getProduct(int i) {
		return listOfProducts.get(i);
	}

	/**
	 * 
	 * @param id
	 * @return the SpeciesReference of the listOfProducts which has 'id' as id
	 *         (or name depending on the level and version). Can be null if it
	 *         doesn't exist.
	 */
	public SpeciesReference getProduct(String id) {
		return getListOfProducts().firstHit(new NameFilter(id));
	}

	/**
	 * 
	 * @param i
	 * @return the ith reactant SpeciesReference of the listOfReactants. Can be
	 *         null if it doesn't exist.
	 */
	public SpeciesReference getReactant(int i) {
		return listOfReactants.get(i);
	}

	/**
	 * 
	 * @param id
	 * @return the SpeciesReference of the listOfReactants which has 'id' as id
	 *         (or name depending on the level and version). Can be null if it
	 *         doesn't exist.
	 */
	public SpeciesReference getReactant(String id) {
		return getListOfReactants().firstHit(new NameFilter(id));
	}

	/**
	 * 
	 * @return the reversible Boolean of this reaction.
	 */
	// Not using the isSetReversible here to allow the value set in
	// initDefaults() to be returned.
	public boolean getReversible() {
		return reversible != null ? reversible : true;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
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
	 * Initializes the default variables of this Reaction.
	 */
	public void initDefaults() {
		initReactantList();
		initProductList();
		initModifierList();
		if (isSetLevel() && isSetVersion()) {
			if (level < 3) {
				reversible = new Boolean(true);
				fast = new Boolean(false);
			} else {
				reversible = fast = null;
			}
		}
	}

	/**
	 * 
	 */
	private void initModifierList() {
		initModifierList(getLevel(), getVersion());
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	private void initModifierList(int level, int version) {
		listOfModifiers = new ListOf<ModifierSpeciesReference>(level, version);
		listOfModifiers.setSBaseListType(ListOf.Type.listOfModifiers);
		listOfModifiers.parentSBMLObject = this;
	}

	/**
	 * 
	 */
	private void initProductList() {
		initProductList(getLevel(), getVersion());
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	private void initProductList(int level, int version) {
		listOfProducts = new ListOf<SpeciesReference>(level, version);
		listOfProducts.setSBaseListType(ListOf.Type.listOfProducts);
		listOfProducts.parentSBMLObject = this;
	}

	/**
	 * 
	 */
	private void initReactantList() {
		initReactantList(getLevel(), getVersion());
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	private void initReactantList(int level, int version) {
		listOfReactants = new ListOf<SpeciesReference>(level, version);
		listOfReactants.setSBaseListType(ListOf.Type.listOfReactants);
		listOfReactants.parentSBMLObject = this;
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
		return getFast();
	}

	/**
	 * 
	 * @return the value of reversible if it is set, false otherwise.
	 */
	public boolean isReversible() {
		return getReversible();
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
	 * @return true if the listOfModifiers of this Reaction is not null and not
	 *         empty.
	 */
	public boolean isSetListOfModifiers() {
		return (listOfModifiers != null) && (listOfModifiers.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfProducts of this reaction is not null and not
	 *         empty.
	 */
	public boolean isSetListOfProducts() {
		return (listOfProducts != null) && (listOfProducts.size() > 0);
	}

	/**
	 * 
	 * @return true if the listOfReactants of this Reaction is not null and not
	 *         empty.
	 */
	public boolean isSetListOfReactants() {
		return (listOfReactants != null) && (listOfReactants.size() > 0);
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
				this.setReversible(StringTools.parseSBMLBoolean(value));
			} else if (attributeName.equals("fast")) {
				this.setFast(StringTools.parseSBMLBoolean(value));
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
		for (SimpleSpeciesReference specRef : list) {
			if (specRef.getSpecies().equals(s.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param <T>
	 * @param list
	 * @param id
	 * @return
	 */
	private <T> SimpleSpeciesReference remove(
			ListOf<? extends SimpleSpeciesReference> list, String id) {
		SimpleSpeciesReference deleted = null;
		int index = 0;
		for (SimpleSpeciesReference reference : list) {
			if (reference.getSpecies().equals(id)) {
				deleted = reference;
				break;
			}
			index++;
		}
		if (deleted != null) {
			list.remove(index);
		}
		return deleted;

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
		return getListOfModifiers().remove(i);
	}

	/**
	 * Removes the ModifierSpeciesReference 'modspecref' from this Reaction.
	 * 
	 * @param modspecref
	 */
	public boolean removeModifier(ModifierSpeciesReference modspecref) {
		return getListOfModifiers().remove(modspecref);
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
		return (ModifierSpeciesReference) remove(listOfModifiers, id);
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
		return listOfProducts.remove(i);
	}

	/**
	 * Removes the SpeciesReference 'modspecref' from the listOfProducts of this
	 * Reaction.
	 * 
	 * @param specref
	 */
	public void removeProduct(SpeciesReference specref) {
		if (listOfProducts.remove(specref)) {
			specref.sbaseRemoved();
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
	public SpeciesReference removeProduct(String id) {
		return (SpeciesReference) remove(listOfProducts, id);
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
	public SpeciesReference removeReactant(int i) {
		return getListOfReactants().remove(i);
	}

	/**
	 * Removes the SpeciesReference 'modspecref' from the listOfReactants of
	 * this Reaction.
	 * 
	 * @param specref
	 */
	public boolean removeReactant(SpeciesReference specref) {
		return getListOfReactants().remove(specref);
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
	public SpeciesReference removeReactant(String id) {
		return (SpeciesReference) remove(listOfReactants, id);
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
		stateChanged();
	}

	/**
	 * Sets the fast Boolean of this Reaction.
	 * 
	 * @param fast
	 */
	public void setFast(boolean fast) {
		this.fast = Boolean.valueOf(fast);
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
		listOfModifiers = list;
		setThisAsParentSBMLObject(listOfModifiers);
		stateChanged();
	}

	/**
	 * Sets the listOfProducts of this Reaction. Automatically sets the
	 * parentSBML object of the list to this Reaction instance.
	 * 
	 * @param list
	 */
	public void setListOfProducts(ListOf<SpeciesReference> list) {
		listOfProducts = list;
		setThisAsParentSBMLObject(this.listOfProducts);
		listOfProducts.setSBaseListType(ListOf.Type.listOfProducts);
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
		this.reversible = Boolean.valueOf(reversible);
		isSetReversible = true;
		stateChanged();
	}

	/**
	 * Sets the fast Boolean of this Reaction to null.
	 */
	public void unsetFast() {
		isSetFast = false;
		fast = null;
		stateChanged();
	}

	/**
	 * Sets the reversible Boolean of this Reaction to null.
	 */
	public void unsetReversible() {
		isSetReversible = false;
		reversible = null;
		stateChanged();
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
