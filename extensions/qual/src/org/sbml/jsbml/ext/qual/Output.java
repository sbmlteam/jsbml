/*
 * $Id: Output.java 811 2011-09-27 12:36:49Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/KineticLaw.java $
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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $$Rev$$
 * @since 0.8
 * @date ${date}
 * ${tags}
 */
public class Output extends AbstractNamedSBase {

	private String qualitativeSpecies;
	
	private OutputTransitionEffect transitionEffect;

	private int level;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIdMandatory() {
		return false;
	}

	/**
	 * @return the qualitativeSpecies
	 */
	public String getQualitativeSpecies() {
		return qualitativeSpecies;
	}

	/**
	 * @param qualitativeSpecies the qualitativeSpecies to set
	 */
	public void setQualitativeSpecies(String qualitativeSpecies) {
		this.qualitativeSpecies = qualitativeSpecies;
	}

	/**
	 * @return the transitionEffect
	 */
	public OutputTransitionEffect getTransitionEffect() {
		return transitionEffect;
	}

	/**
	 * @param transitionEffect the transitionEffect to set
	 */
	public void setTransitionEffect(OutputTransitionEffect transitionEffect) {
		this.transitionEffect = transitionEffect;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	
	
}
