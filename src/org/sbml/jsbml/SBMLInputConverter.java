/*
 * $Id: SBMLReader.java 102 2009-12-13 19:52:50Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/SBMLReader.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
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
 * 
 */
public interface SBMLInputConverter {

	/**
	 * 
	 * @return
	 */
	public int getNumErrors();

	/**
	 * 
	 * @param sbmlDocument
	 * @return
	 */
	public List<SBMLException> getWarnings();

	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public Model convert2Model(Object model) throws Exception;

	/**
	 * 
	 * @return
	 */
	public Object getOriginalModel();

	/**
	 * 
	 * @param listener
	 */
	public void addIOProgressListener(IOProgressListener listener);
}
