/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * <p/>A selector is a mask describing the rules that an entity has to pass in order to be used or rejected.
 * This selector is built of different components, carrying state features. The components can be
 * bound together or not. In a population-based model, the selector, when applied to a pool of
 * entities, permits to filter it, and to obtain a further, more refined, entity pool.
 * 
 * <p/>A selector can be reused in various places of a model, to restrict the application of a procedure
 * to a certain set of topologies and states. Selectors can be used to refine the initial conditions of
 * a species, for instance to specify the initial distribution of different states and topologies. They
 * can also be used in a reaction to decide if a this reaction happens, or to modulate its velocity,
 * in function of the state or topology of a reactant.
 * 
 * <p/>A selector defines the list of components composing the mask, that are species type existing
 * under a given state (that can be an ensemble of elementary states). In addition to the compo-
 * nents, the selector lists the possible or mandatory bonds, as well as the components that must
 * not be bound. It is to be noted that a selector must not necessarily be the most parsimonious.
 * One can use the selectors to describe the fine-grained topology of complexes, even if this topol-
 * ogy is not used to decide upon particular reactions.
 *
 *
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class Selector extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1103757869624885889L;

	/**
	 * 
	 */
	private ListOf<SpeciesTypeState> listOfSpeciesTypeStates;

	/**
	 * 
	 */
	private ListOf<Bond> listOfBonds;

	/**
	 * 
	 */
	private ListOf<BindingSiteReference> listOfUnboundBindingSites;


	/**
	 * 
	 */
	public Selector() {
		super();
	}

	/**
	 * 
	 * @param selector
	 */
	public Selector(Selector selector) {
		super(selector);
		// TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public Selector clone() {
		return new Selector(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
	public boolean isIdMandatory() {
		return false;
	}

	/**
	 * Returns the listOfSpeciesTypeStates
	 * 
	 * @return the listOfSpeciesTypeStates
	 */
	public ListOf<SpeciesTypeState> getListOfSpeciesTypeStates() {
		if (listOfSpeciesTypeStates == null) {
			listOfSpeciesTypeStates = new ListOf<SpeciesTypeState>();
			listOfSpeciesTypeStates.addNamespace(MultiConstant.namespaceURI);
			this.registerChild(listOfSpeciesTypeStates);
			listOfSpeciesTypeStates.setSBaseListType(ListOf.Type.other);
		}

		return listOfSpeciesTypeStates;
	}

	/**
	 * Adds a SpeciesTypeState.
	 * 
	 * @param speciesTypeState the SpeciesTypeState to add
	 */
	public void addSpeciesTypeState(SpeciesTypeState speciesTypeState) {
		getListOfSpeciesTypeStates().add(speciesTypeState);
	}

	/**
	 * Creates a new {@link SpeciesTypeState} inside this {@link Selector} and returns it.
	 * <p>
	 * 
	 * @return the {@link SpeciesTypeState} object created
	 *         <p>
	 * @see #addSpeciesTypeState(SpeciesTypeState r)
	 */
	public SpeciesTypeState createSpeciesTypeState() {
		return createSpeciesTypeState(null);
	}

	/**
	 * Creates a new {@link SpeciesTypeState} inside this {@link Selector} and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @return the {@link SpeciesTypeState} object created
	 */
	public SpeciesTypeState createSpeciesTypeState(String id) {
		SpeciesTypeState speciesTypeState = new SpeciesTypeState();
		speciesTypeState.setId(id);
		addSpeciesTypeState(speciesTypeState);

		return speciesTypeState;
	}

	/**
	 * Gets the ith {@link SpeciesTypeState}.
	 * 
	 * @param i
	 * 
	 * @return the ith {@link SpeciesTypeState}
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public SpeciesTypeState getSpeciesTypeState(int i) {
		return getListOfSpeciesTypeStates().get(i);
	}

	/**
	 * Gets the {@link SpeciesTypeState} that has the given id. 
	 * 
	 * @param id
	 * @return the {@link SpeciesTypeState} that has the given id or null if
	 * no {@link SpeciesTypeState} are found that match <code>id</code>.
	 */
	public SpeciesTypeState getSpeciesTypeState(String id){
		if(isSetListOfSpeciesTypeStates()) {
			return listOfSpeciesTypeStates.firstHit(new NameFilter(id));	    
		} 
		return null;
	}

	/**
	 * Returns true if the listOfSpeciesTypeState is set.
	 * 
	 * @return true if the listOfSpeciesTypeState is set.
	 */
	public boolean isSetListOfSpeciesTypeStates() {
		if ((listOfSpeciesTypeStates == null) || listOfSpeciesTypeStates.isEmpty()) {
			return false;			
		}		
		return true;
	}

	/**
	 * Sets the listOfSpeciesTypeStates to null
	 * 
	 * @return true is successful
	 */
	public boolean unsetListOfSpeciesTypeStates(){
		if(isSetListOfSpeciesTypeStates()) {
			// unregister the ids if needed.			  
			this.listOfSpeciesTypeStates.fireNodeRemovedEvent();
			this.listOfSpeciesTypeStates = null;
			return true;
		}
		return false;
	}


	/**
	 * Returns the listOfBonds
	 * 
	 * @return the listOfBonds
	 */
	public ListOf<Bond> getListOfBonds() {
		if (listOfBonds == null) {
			listOfBonds = new ListOf<Bond>();
			listOfBonds.addNamespace(MultiConstant.namespaceURI);
			this.registerChild(listOfBonds);
			listOfBonds.setSBaseListType(ListOf.Type.other);
		}

		return listOfBonds;
	}

	/**
	 * Adds a Bond.
	 * 
	 * @param bond the Bond to add
	 */
	public void addBond(Bond bond) {
		getListOfBonds().add(bond);
	}

	/**
	 * Creates a new {@link Bond} inside this {@link Selector} and returns it.
	 * 
	 * @return the {@link Bond} object created
	 */
	public Bond createBond() {
		Bond bond = new Bond();
		addBond(bond);

		return bond;
	}

	/**
	 * Gets the ith {@link Bond}.
	 * 
	 * @param i
	 * 
	 * @return the ith {@link Bond}
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public Bond getBond(int i) {
		return getListOfBonds().get(i);
	}

	/**
	 * Gets the {@link Bond} that has the given id. 
	 * 
	 * @param id
	 * @return the {@link Bond} that has the given id or null if
	 * no {@link Bond} are found that match <code>id</code>.
	 */
	public Bond getBond(String id){
		if(isSetListOfBonds()) {
			return listOfBonds.firstHit(new NameFilter(id));	    
		} 
		return null;
	}

	/**
	 * Returns true if the listOfBond is set.
	 * 
	 * @return true if the listOfBond is set.
	 */
	public boolean isSetListOfBonds() {
		if ((listOfBonds == null) || listOfBonds.isEmpty()) {
			return false;			
		}		
		return true;
	}

	/**
	 * Sets the listOfBonds to null
	 * 
	 * @return true is successful
	 */
	public boolean unsetListOfBonds(){
		if(isSetListOfBonds()) {
			// unregister the ids if needed.			  
			this.listOfBonds.fireNodeRemovedEvent();
			this.listOfBonds = null;
			return true;
		}
		return false;
	}

	/**
	 * Returns the listOfUnboundBindingSites
	 * 
	 * @return the listOfUnboundBindingSites
	 */
	public ListOf<BindingSiteReference> getListOfUnboundBindingSites() {
		if (listOfUnboundBindingSites == null) {
			listOfUnboundBindingSites = new ListOf<BindingSiteReference>();
			listOfUnboundBindingSites.addNamespace(MultiConstant.namespaceURI);
			this.registerChild(listOfUnboundBindingSites);
			listOfUnboundBindingSites.setSBaseListType(ListOf.Type.other);
		}

		return listOfUnboundBindingSites;
	}

	/**
	 * Adds an unboundBindingSite.
	 * 
	 * @param unboundBindingSite the UnboundBindingSite to add
	 */
	public void addUnboundBindingSite(BindingSiteReference unboundBindingSite) {
		getListOfUnboundBindingSites().add(unboundBindingSite);
	}

	/**
	 * Creates a new {@link BindingSiteReference} inside this {@link Selector} list
	 * of unbound binding site and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @return the {@link BindingSiteReference} object created
	 */
	public BindingSiteReference createUnboundBindingSite() {
		BindingSiteReference unBoundBindingSite = new BindingSiteReference();
		addUnboundBindingSite(unBoundBindingSite);

		return unBoundBindingSite;
	}

	/**
	 * Gets the ith unbound binding site ({@link BindingSiteReference}).
	 * 
	 * @param i
	 * 
	 * @return the ith {@link BindingSiteReference}
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public BindingSiteReference getUnboundBindingSite(int i) {
		return getListOfUnboundBindingSites().get(i);
	}

	/**
	 * Gets the unbound binding site {@link BindingSiteReference} that has the given speciesTypeState id. 
	 * 
	 * @param id
	 * @return the {@link BindingSiteReference} that has the given speciesTypeState id or null if
	 * no {@link BindingSiteReference} are found that match <code>id</code>.
	 */
	public BindingSiteReference getUnboundBindingSite(final String id){
		if(isSetListOfUnboundBindingSites()) {
			return listOfUnboundBindingSites.firstHit(new Filter() {

				public boolean accepts(Object o) {
					if (o instanceof BindingSiteReference &&
							((BindingSiteReference) o).getSpeciesTypeState().equals(id)) {
						return true;
					}
					return false;
				}
			});	    
		} 
		return null;
	}

	/**
	 * Returns true if the listOfUnBoundBindingSite is set.
	 * 
	 * @return true if the listOfUnBoundBindingSite is set.
	 */
	public boolean isSetListOfUnboundBindingSites() {
		if ((listOfUnboundBindingSites == null) || listOfUnboundBindingSites.isEmpty()) {
			return false;			
		}		
		return true;
	}

	/**
	 * Sets the listOfUnboundBindingSites to null
	 * 
	 * @return true is successful
	 */
	public boolean unsetListOfUnboundBindingSites(){
		if(isSetListOfUnboundBindingSites()) {
			// unregister the ids if needed.			  
			this.listOfUnboundBindingSites.fireNodeRemovedEvent();
			this.listOfUnboundBindingSites = null;
			return true;
		}
		return false;
	}



	/**
	 * 
	 */
	public void initDefaults() {
		addNamespace(MultiConstant.namespaceURI);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}

		int count = super.getChildCount(), pos = 0;
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		if (isSetListOfSpeciesTypeStates()) {
			if (pos == index) {
				return getListOfSpeciesTypeStates();
			}
			pos++;
		}
		if (isSetListOfBonds()) {
			if (pos == index) {
				return getListOfBonds();
			}
			pos++;
		}
		if (isSetListOfUnboundBindingSites()) {
			if (pos == index) {
				return getListOfUnboundBindingSites();
			}
			pos++;
		}

		throw new IndexOutOfBoundsException(MessageFormat.format(
		  "Index {0,number,integer} >= {1,number,integer}",
			index, +((int) Math.min(pos, 0))));
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();

		if (isSetListOfSpeciesTypeStates()) {
			count++;
		}
		if (isSetListOfBonds()) {
			count++;
		}
		if (isSetListOfUnboundBindingSites()) {
			count++;
		}

		return count;
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(MultiConstant.shortLabel+ ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(MultiConstant.shortLabel+ ":name", getName());
		}

		return attributes;
	}

	// TODO : equals, hashCode, toString, more constructors, ...

	// TODO : removeXX unsetXX, isSetXX

}
