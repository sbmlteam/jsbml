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
 * 
 * Represents the rateRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * @version $Rev$
 */
public class RateRule extends ExplicitRule {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -1631920547145001765L;

	/**
	 * Creates a RateRule instance. By default, the variableID is null.
	 */
	public RateRule() {
		super();
	}

	/**
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public RateRule(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * 
	 * @param math
	 * @param parameter
	 */
	public RateRule(ASTNode math, Parameter parameter) {
		this(parameter, math);
	}

	/**
	 * 
	 * @param variable
	 * @param math
	 */
	public RateRule(ASTNode math, Variable variable) {
		this(variable, math);
	}

	/**
	 * Creates a RateRule instance from a given RateRule.
	 * 
	 * @param sb
	 */
	public RateRule(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param parameter
	 */
	public RateRule(Parameter parameter) {
		super(parameter);
	}

	/**
	 * 
	 * @param parameter
	 * @param math
	 */
	public RateRule(Parameter parameter, ASTNode math) {
		super(parameter, math);
	}

	/**
	 * @param sb
	 */
	public RateRule(RateRule sb) {
		super(sb);
	}

	/**
	 * Creates a RateRule instance from a given Symbol. Takes level and version
	 * from the variable.
	 * 
	 * @param variable
	 */
	public RateRule(Variable variable) {
		super(variable);
	}
	
	/**
	 * Creates a RateRule instance cloned from the given {@link ExplicitRule}
	 * 
	 * @param rule
	 */
	public RateRule(ExplicitRule rule) {
		super(rule);
	}

	/**
	 * Creates a RateRule instance from a given Symbol and ASTNode. Takes level
	 * and version from the variable.
	 * 
	 * @param variable
	 * @param math
	 */
	public RateRule(Variable variable, ASTNode math) {
		super(math, variable);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ExplicitRule#clone()
	 */
	@Override
	public RateRule clone() {
		return new RateRule(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ExplicitRule#isScalar()
	 */
	@Override
	public boolean isScalar() {
		return false;
	}
	
}
