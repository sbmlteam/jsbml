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
package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.SpeciesReference;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class ProductSpeciesReference extends SpeciesReference {

	// TODO: check if it is a new element or a plugin to speciesReference
	
	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5789882023489183232L;
  private String correspondingReactant;

	/**
	 * Returns the corresponding reactant.
	 * 
	 * @return the correspondingReactant
	 */
	public String getCorrespondingReactant() {
		return correspondingReactant;
	}

	/**
	 * Sets the corresponding reactant.
	 * 
	 * @param correspondingReactant the correspondingReactant to set
	 */
	public void setCorrespondingReactant(String correspondingReactant) {
		this.correspondingReactant = correspondingReactant;
	}
	
	
	
}
