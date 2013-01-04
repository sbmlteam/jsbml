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

import java.io.IOException;
import java.util.List;

import org.sbml.jsbml.util.IOProgressListener;

/**
 * This interface allows the implementing class to convert a JSBML model into
 * another data structure. Possible examples are a conversion into libSBML or
 * CellDesigner plug-in data structures. Other formats can also be implemented,
 * for instance, a BioPax or CellML converter could be implemented.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public interface SBMLOutputConverter {

	/**
	 * Allows this class to fire events through this event Listener.
	 * 
	 * @param listener
	 */
	public void addIOProgressListener(IOProgressListener listener);

	/**
	 * 
	 * @return
	 * @deprecated use {@link #getErrorCount(Object)}
	 */
	@Deprecated
	public int getNumErrors(Object sbase);
	
	/**
	 * 
	 * @param sbase
	 * @return
	 */
	public int getErrorCount(Object sbase);

	/**
	 * 
	 * @param sbase
	 * @return
	 */
	public List<SBMLException> getWriteWarnings(Object sbase);

	/**
	 * Deletes those elements that are not referenced or not needed within the
	 * model.
	 * 
	 * @param model
	 * @param orig
	 */
	public void removeUnneccessaryElements(Model model, Object orig);

	/**
	 * Save the changes in the model.
	 * 
	 * @param model
	 * @param object
	 * @throws SBMLException
	 */
	public boolean saveChanges(Model model, Object object) throws SBMLException;

	/**
	 * 
	 * @param reaction
	 * @param model
	 * @return
	 * @throws SBMLException
	 */
	public boolean saveChanges(Reaction reaction, Object model)
			throws SBMLException;

	/**
	 * 
	 * @param model
	 * @return
	 * @throws SBMLException
	 */
	public Object writeModel(Model model) throws SBMLException;

	/**
	 * 
	 * @param sbmlDocument
	 * @param filename
	 * @return
	 * @throws SBMLException
	 * @throws IOException
	 */
	public boolean writeSBML(Object sbmlDocument, String filename)
			throws SBMLException, IOException;

	/**
	 * 
	 * @param object
	 * @param filename
	 * @param programName
	 * @param versionNumber
	 * @return
	 * @throws SBMLException
	 * @throws IOException
	 */
	public boolean writeSBML(Object object, String filename,
			String programName, String versionNumber) throws SBMLException,
			IOException;

}
