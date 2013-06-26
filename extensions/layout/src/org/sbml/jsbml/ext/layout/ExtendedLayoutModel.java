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
package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.Model;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @version $Rev$
 * @since 1.0
 * @deprecated use {@link LayoutModelPlugin}
 */
@Deprecated
public class ExtendedLayoutModel extends LayoutModelPlugin {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6666014348571697514L;

	/**
	 * 
	 * @param elm
	 */
	public ExtendedLayoutModel(ExtendedLayoutModel elm) {
		super(elm);
	}

	/**
	 * 
	 */
	public ExtendedLayoutModel(Model model) {
		super(model);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.Model#clone()
	 */
	@Override
	public ExtendedLayoutModel clone() {
		return new ExtendedLayoutModel(this);
	}

}
