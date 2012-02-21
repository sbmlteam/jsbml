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

package org.sbml.jsbml;

/**
 * Contains the MathMl expression of the Stoichiometry.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 * @deprecated Use {@link AssignmentRule} with instances of
 *             {@link SpeciesReference} as {@link Variable} instead.
 */
@Deprecated
public class StoichiometryMath extends AbstractMathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -7070065639669486763L;

	/**
	 * Creates a StoichiometryMath instance.
	 */
	@Deprecated
	public StoichiometryMath() {
		super();
	}

	/**
	 * Creates a StoichiometryMath instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	@Deprecated
	public StoichiometryMath(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a StoichiometryMath instance from a given StoichiometryMath.
	 * 
	 * @param stoichiometryMath
	 */
	@Deprecated
	public StoichiometryMath(StoichiometryMath stoichiometryMath) {
		super(stoichiometryMath);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	@Deprecated
	public StoichiometryMath clone() {
		return new StoichiometryMath(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	@Deprecated
	public SpeciesReference getParent() {
		return (SpeciesReference) super.getParent();
	}
}
