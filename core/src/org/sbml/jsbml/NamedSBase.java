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
 * Base class for all the SBML components with an id and a name (optional or
 * not).
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @since 0.8
 * @version $Rev$
 */
public interface NamedSBase extends SBase {

	/**
	 * 
	 * @return the id of the element if it is set, an empty string otherwise.
	 */
	public String getId();

	/**
	 * 
	 * @return the name of the element if it is set, an empty string otherwise.
	 */
	public String getName();

	/**
	 * This method can be used to query if the identifier of this
	 * {@link NamedSBase} is required to be defined (i.e., not {@code null})
	 * in the definition of SBML.
	 * 
	 * @return {@code true} if the identifier of this element must be set in
	 *         order to create a valid SBML representation. {@code false}
	 *         otherwise, i.e., if the identifier can be understood as an optional
	 *         attribute.
	 */
	public boolean isIdMandatory();

	/**
	 * 
	 * @return true if the id is not null.
	 */
	public boolean isSetId();

	/**
	 * 
	 * @return true if the name is not null.
	 */
	public boolean isSetName();

	/**
	 * sets the id value with 'id'
	 * 
	 * @param id
	 */
	public void setId(String id);

	/**
	 * sets the name value with 'name'. If level is 1, sets automatically the id
	 * to 'name'
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * sets the id value to null.
	 */
	public void unsetId();

	/**
	 * sets the name value to null.
	 */
	public void unsetName();

}
