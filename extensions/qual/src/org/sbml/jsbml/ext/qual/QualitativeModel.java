/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.qual;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @author Clemens Wrzodek
 * @version $Rev$
 * @since 1.0
 * @date $Date$
 */
public class QualitativeModel extends AbstractSBasePlugin {

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
	 */
	@Override
	public String getElementNamespace() {
		return QualConstants.getNamespaceURI(getLevel(), getVersion());
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
	 */
	@Override
	public String getPackageName() {
		return QualConstants.packageName;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
	 */
	@Override
	public String getPrefix() {
		return QualConstants.shortLabel;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
	 */
	@Override
	public String getURI() {
		return getElementNamespace();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#getParent()
	 */
	@Override
	public SBMLDocument getParent() {
		return (SBMLDocument) getExtendedSBase().getParent();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
	 */
	@Override
	public SBMLDocument getParentSBMLObject() {
		return getParent();
	}
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1861588578911387944L;

	/**
	 * 
	 */
	private ListOf<QualitativeSpecies> listOfQualitativeSpecies;
	/**
	 * 
	 */
	private ListOf<Transition> listOfTransitions;

	/**
	 * 
	 * @param model
	 */
	public QualitativeModel(Model model) {
		super(model);
	}

	/**
	 * @param qualitativeModel
	 */
	public QualitativeModel(QualitativeModel qualitativeModel) {
		super(qualitativeModel);
	}

	/**
	 * Adds a {@link QualitativeSpecies} to the model.
	 * 
	 * @param qualitativeSpecies the qualitativeSpecies to add.
	 */
	public void addQualitativeSpecies(QualitativeSpecies qualitativeSpecies) {
		getListOfQualitativeSpecies().add(qualitativeSpecies);
	}


	/**
	 * 
	 * @param transition
	 */
	public void addTransition(Transition transition) {
		if (!getListOfTransitions().contains(transition)) {
			getListOfTransitions().add(transition);
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
	 */
	@Override
	public QualitativeModel clone() {
		return new QualitativeModel(this);
	}


	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}


	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	@Override
	public SBase getChildAt(int childIndex) {
		if (childIndex < 0) {
			throw new IndexOutOfBoundsException(childIndex + " < 0");
		}

		int pos = 0;
		if (isSetListOfQualitativeSpecies()) {
			if (pos == childIndex) {
				return getListOfQualitativeSpecies();
			}
			pos++;
		}
		if (isSetListOfTransitions()) {
			if (pos == childIndex) {
				return getListOfTransitions();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(MessageFormat.format(
				"Index {0,number,integer} >= {1,number,integer}",
				childIndex, +Math.min(pos, 0)));
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = 0;
		if (isSetListOfQualitativeSpecies()) {
			count++;
		}
		if (isSetListOfTransitions()) {
			count++;
		}
		return count;
	}

	/**
	 * Creates a new {@link Transition} inside this {@link QualitativeModel} and returns it.
	 * <p>
	 * 
	 * @return the {@link Transition} object created
	 *         <p>
	 * @see #addTransition(Transition r)
	 */
	public Transition createTransition() {
		return createTransition(null);
	}

	/**
	 * Creates a new {@link Transition} inside this {@link QualitativeModel} and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @return the {@link Transition} object created
	 */
	public Transition createTransition(String id) {
		Transition transition = new Transition(id, getModel().getLevel(), getModel().getVersion());
		addTransition(transition);

		return transition;
	}

	/**
	 * 
	 * @param id
	 * @param in
	 * @param out
	 * @return
	 */
	public Transition createTransition(String id, Input in, Output out) {
		Transition transition = createTransition(id);
		transition.addInput(in);
		transition.addOutput(out);

		return transition;
	}


	/**
	 * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
	 * <p>
	 * 
	 * @return the {@link QualitativeSpecies} object created
	 *         <p>
	 * @see #addSpecies(QualitativeSpecies s)
	 */
	public QualitativeSpecies createQualitativeSpecies() {
		return createQualitativeSpecies(null);
	}

	/**
	 * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @return the {@link QualitativeSpecies} object created
	 */
	public QualitativeSpecies createQualitativeSpecies(String id) {
		QualitativeSpecies species = new QualitativeSpecies(id);
		addSpecies(species);
		return species;
	}

	/**
	 * Creates a new {@link QualitativeSpecies} inside this {@link QualitativeModel} and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @return the {@link QualitativeSpecies} object created
	 */
	public QualitativeSpecies createQualitativeSpecies(String id, String compartment, boolean constant) {
		QualitativeSpecies species = new QualitativeSpecies(id,
				getModel().getLevel(), getModel().getVersion());
		species.setCompartment(compartment);
		species.setConstant(constant);
		addSpecies(species);
		return species;
	}

	/**
	 * Creates a new {@link QualitativeSpecies}, based on an existing {@link Species},
	 * adds it to this {@link QualitativeModel} and returns it.
	 * @param id the id of the new element to create
	 * @param metaId the metaId of the new element to create
	 * @param species a template to copy fields from
	 * @return the {@link QualitativeSpecies} object created
	 */
	public QualitativeSpecies createQualitativeSpecies(String id, String metaId, Species species) {
		QualitativeSpecies qualSpecies = new QualitativeSpecies(species);
		qualSpecies.setId(id);
		qualSpecies.setMetaId(metaId);
		qualSpecies.setLevel(getModel().getLevel());
		qualSpecies.setVersion(getModel().getVersion());
		addSpecies(qualSpecies);
		return qualSpecies;
	}

	/**
	 * Adds a {@link QualitativeSpecies} instance to the listOfSpecies of this Model.
	 * 
	 * @param spec
	 * @return {@code true} if the {@link QualitativeSpecies} was added successfully.
	 */
	public boolean addSpecies(QualitativeSpecies spec) {
		return getListOfQualitativeSpecies().add(spec);
	}

	/**
	 * Creates a new {@link Species} inside this {@link Model} and returns it.
	 * 
	 * @param id
	 *        the id of the new element to create
	 * @param c
	 *        the Compartment of the new {@link Species}
	 * @return the {@link Species} object created
	 */
	public QualitativeSpecies createQualitativeSpecies(String id, Compartment c) {
		QualitativeSpecies s = createQualitativeSpecies(id);
		s.setCompartment(c);
		return s;
	}

	/**
	 * @return the listOfQualitativeSpecies
	 */
	public ListOf<QualitativeSpecies> getListOfQualitativeSpecies() {
		if (!isSetListOfQualitativeSpecies()) {
			listOfQualitativeSpecies = new ListOf<QualitativeSpecies>(
					getModel().getLevel(), getModel().getVersion());
			listOfQualitativeSpecies.addNamespace(QualConstants.namespaceURI);
			getModel().registerChild(listOfQualitativeSpecies);
			listOfQualitativeSpecies.setSBaseListType(ListOf.Type.other);
		}
		return listOfQualitativeSpecies;
	}

	/**
	 * @return the listOTransitions
	 */
	public ListOf<Transition> getListOfTransitions() {
		if (!isSetListOfTransitions()) {
			listOfTransitions = new ListOf<Transition>(getModel().getLevel(),
					getModel().getVersion());
			listOfTransitions.addNamespace(QualConstants.namespaceURI);
			getModel().registerChild(listOfTransitions);
			listOfTransitions.setSBaseListType(ListOf.Type.other);

		}
		return listOfTransitions;
	}

	/**
	 * @param transition
	 *        to remove from the listOfTransitions
	 * @return {@code TRUE} if the operation was successful
	 */
	public boolean removeTransition(Transition transition) {
		if (isSetListOfTransitions()) {
			return listOfTransitions.remove(transition);
		}
		return false;
	}

	/**
	 * @param QualitativeSpecies
	 *        to remove from the listOfQualitativeSpecies
	 * @return {@code TRUE} if the operation was successful
	 */
	public boolean removeQualitativeSpecies(QualitativeSpecies qualitativeSpecies) {
		if (isSetListOfQualitativeSpecies()) {
			return listOfQualitativeSpecies.remove(qualitativeSpecies);
		}
		return false;
	}

	/**
	 * Returns the {@link Model}
	 * 
	 * @return the {@link Model}
	 */
	public Model getModel() {
		return (Model) extendedSBase;
	}

	/**
	 * Gets the ith {@link QualitativeSpecies}.
	 * 
	 * @param i
	 * 
	 * @return the ith {@link QualitativeSpecies}
	 * @throws IndexOutOfBoundsException is the index is invalid.
	 */
	public QualitativeSpecies getQualitativeSpecies(int i) {
		return getListOfQualitativeSpecies().get(i);
	}

	/**
	 * Gets the {@link QualitativeSpecies} that has the given id.
	 * 
	 * @param id
	 * @return the {@link QualitativeSpecies} that has the given id or null if
	 * no {@link QualitativeSpecies} are found that match {@code id}.
	 */
	public QualitativeSpecies getQualitativeSpecies(String id) {
		if (isSetListOfQualitativeSpecies()) {
			return listOfQualitativeSpecies.firstHit(new NameFilter(id));
		}
		return null;
	}

	/**
	 * Returns {@code true} if {@code qs} is already part of this {@link QualitativeModel}
	 * 
	 * @param qs
	 * @return {@code true} if {@code qs} is already part of this {@link QualitativeModel}
	 */
	public boolean containsQualitativeSpecies(QualitativeSpecies qs) {
		return isSetListOfQualitativeSpecies()
				&& listOfQualitativeSpecies.contains(qs);
	}

	/**
	 * Gets the ith {@link Transition}.
	 * 
	 * @param i
	 * @return the ith {@link Transition}.
	 * @throws IndexOutOfBoundsException is the index is invalid.
	 */
	public Transition getTransition(int i) {
		return getListOfTransitions().get(i);
	}

	/**
	 * Gets the {@link Transition} that has the given id.
	 * 
	 * @param id
	 * @return the {@link Transition} that has the given id or null if
	 * no {@link Transition} are found that match {@code id}.
	 */
	public Transition getTransition(String id) {
		if (isSetListOfTransitions()) {
			Model m = getModel();
			if (m != null) {
				return (Transition) m.findNamedSBase(id);
			}
			return listOfTransitions.firstHit(new NameFilter(id));
		}
		return null;
	}

	/**
	 * Returns {@code true} if the listOfQualitativeSpecies is set.
	 * 
	 * @return {@code true} if the listOfQualitativeSpecies is set.
	 */
	public boolean isSetListOfQualitativeSpecies() {
		if ((listOfQualitativeSpecies == null) || listOfQualitativeSpecies.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns {@code true} if the listOfTransitions is set.
	 * 
	 * @return {@code true} if the listOfTransitions is set.
	 */
	public boolean isSetListOfTransitions() {
		if ((listOfTransitions == null) || listOfTransitions.isEmpty()) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		// no attribute to read
		return false;
	}

	/**
	 * 
	 * @param listOfQualitativeSpecies
	 */
	public void setListOfQualitativeSpecies(
			ListOf<QualitativeSpecies> listOfQualitativeSpecies) {
		this.listOfQualitativeSpecies = listOfQualitativeSpecies;
		getModel().registerChild(this.listOfQualitativeSpecies);
	}

	/**
	 * 
	 * @param listOfTransitions
	 */
	public void setListOfTransitions(ListOf<Transition> listOfTransitions) {
		this.listOfTransitions = listOfTransitions;
		getModel().registerChild(this.listOfTransitions);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		// no attribute to write
		return null;
	}

	/**
	 * 
	 * @return {@code true} is successful
	 */
	public boolean unsetListOfTransitions() {
		if (isSetListOfTransitions()) {
			// unregister the ids.
			listOfTransitions.unregister(listOfTransitions);
			listOfTransitions = null;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return {@code true} is successful
	 */
	public boolean unsetListOfQualitativeSpecies() {
		if (isSetListOfQualitativeSpecies()) {
			// unregister the ids
			listOfQualitativeSpecies.unregister(listOfQualitativeSpecies);
			listOfQualitativeSpecies = null;
			return true;
		}
		return false;
	}

}
