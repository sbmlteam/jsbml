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

import java.util.List;

import org.sbml.jsbml.util.IOProgressListener;

/**
 * This interface allows the implementing class to create a JSBML model based on
 * some other data structure. Possible examples are CellDesigner plug-in data
 * structures or Objects from libSBML. Other data structures can also be
 * considered, such as a conversion of BioPax or CellML into JSBML data
 * structures.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public interface SBMLInputConverter {

	/**
	 * 
	 * @param listener
	 */
	public void addIOProgressListener(IOProgressListener listener);

	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public Model convertModel(Object model) throws Exception;

	/**
	 * 
	 * @return
	 * @deprecated use {@link #getErrorCount()}
	 */
	@Deprecated
	public int getNumErrors();
	
	/**
	 * 
	 * @return
	 */
	public int getErrorCount();

	/**
	 * 
	 * @return
	 */
	public Object getOriginalModel();

	/**
	 * 
	 * @param sbmlDocument
	 * @return
	 */
	public List<SBMLException> getWarnings();

}
