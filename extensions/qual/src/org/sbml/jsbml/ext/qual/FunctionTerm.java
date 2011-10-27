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

package org.sbml.jsbml.ext.qual;

import org.sbml.jsbml.AbstractMathContainer;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $$Rev$$
 * @since 0.8
 * @date ${date}
 * ${tags}
 */
public class FunctionTerm extends AbstractMathContainer {

	private int resultLevel;
	private String resultSymbol;
	
	private double temporisationValue;
	private TemporisationMath temporisationMath;
	
	private boolean defaultTerm;
	
	@Override
	public AbstractMathContainer clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the resultLevel
	 */
	public int getResultLevel() {
		return resultLevel;
	}

	/**
	 * @param resultLevel the resultLevel to set
	 */
	public void setResultLevel(int resultLevel) {
		this.resultLevel = resultLevel;
	}

	/**
	 * @return the resultSymbol
	 */
	public String getResultSymbol() {
		return resultSymbol;
	}

	/**
	 * @param resultSymbol the resultSymbol to set
	 */
	public void setResultSymbol(String resultSymbol) {
		this.resultSymbol = resultSymbol;
	}

	/**
	 * @return the temporisationValue
	 */
	public double getTemporisationValue() {
		return temporisationValue;
	}

	/**
	 * @param temporisationValue the temporisationValue to set
	 */
	public void setTemporisationValue(double temporisationValue) {
		this.temporisationValue = temporisationValue;
	}

	/**
	 * @return the temporisationMath
	 */
	public TemporisationMath getTemporisationMath() {
		return temporisationMath;
	}

	/**
	 * @param temporisationMath the temporisationMath to set
	 */
	public void setTemporisationMath(TemporisationMath temporisationMath) {
		this.temporisationMath = temporisationMath;
	}

	/**
	 * @return the defaultTerm
	 */
	public boolean isDefaultTerm() {
		return defaultTerm;
	}

	/**
	 * @param defaultTerm the defaultTerm to set
	 */
	public void setDefaultTerm(boolean defaultTerm) {
		this.defaultTerm = defaultTerm;
	}
	
	
	
}
