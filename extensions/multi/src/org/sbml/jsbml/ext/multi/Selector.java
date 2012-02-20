/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;

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
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class Selector extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1103757869624885889L;

	private ListOf<Bond> listOfBonds;
	
	private ListOf<BindingSiteReference> listOfUnBoundBindingSites;
	
	private ListOf<SpeciesTypeState> listOfSpeciesTypeStates;
	
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
  
	// TODO : removeXX unsetXX, isSetXX
  
}
