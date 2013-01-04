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

import org.sbml.jsbml.util.Message;

/**
 * An {@link SBMLError} that indicates that associated instances of
 * {@link SBase} cannot be combined within the same model due to their
 * differently set Level or Version attribute.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-11-21
 * @since 0.8
 * @version $Rev$
 */
public class LevelVersionError extends SBMLError {

	/**
	 * Message to display in cases that two objects do not have identical level
	 * attributes. Requires the following replacement arguments: Class name of
	 * first element, version in first element, class name of second element and
	 * level in second argument.
	 */
	public static final String LEVEL_MISMATCH_MSG = "Level mismatch between %s in L %d and %s in L %d";
	
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5549251465153191735L;
	/**
	 * Message to indicate that an invalid combination of the level and version
	 * attribute has been set.
	 */
	public static final String UNDEFINED_LEVEL_VERSION_COMBINATION_MSG = "Undefined combination of Level %d and Version %d.";
	/**
	 * Message to display in cases that two objects do not have identical
	 * version attributes. Requires the following replacement arguments: Class
	 * name of first element, version in first element, class name of second
	 * element and version in second argument.
	 */
	public static final String VERSION_MISMATCH_MSG = "Version mismatch between %s in V %d and %s in V %d.";

	/**
	 * Creates an error message if the level fields of both elements are not
	 * identical, or an empty {@link String} otherwise.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public static String levelMismatchMessage(SBase element1, SBase element2) {
		if (element1.getLevel() != element2.getLevel()) {
			return String.format(VERSION_MISMATCH_MSG, element1
					.getElementName(), element1.getLevel(), element2
					.getElementName(), element2.getLevel());
		}
		return "";
	}
	
	/**
	 * Creates an error message if the version fields of both elements are not
	 * identical, or an empty {@link String} otherwise.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	public static String versionMismatchMessage(SBase element1, SBase element2) {
		if (element1.getVersion() != element2.getVersion()) {
			return String.format(VERSION_MISMATCH_MSG, element1
					.getElementName(), element1.getVersion(), element2
					.getElementName(), element2.getVersion());
		}
		return "";
	}

	/**
	 * 
	 * @param sbase
	 */
	public LevelVersionError(SBase sbase) {
		this(sbase.getElementName(), sbase.getLevel(), sbase.getVersion());
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public LevelVersionError(int level, int version) {
		this(null, level, version);
	}
	
	/**
	 * 
	 * @param elementName
	 * @param level
	 * @param version
	 */
	public LevelVersionError(String elementName, int level, int version) {
		super();
		Message message = new Message();
		if (!AbstractSBase.isValidLevelAndVersionCombination(level, version)) {
			message.setMessage(String.format(
					UNDEFINED_LEVEL_VERSION_COMBINATION_MSG, level, version));
			if (elementName != null) {
				message.setMessage(String.format("%s for element %s.", message.getMessage().substring(0,
						message.getMessage().length() - 1), elementName));
			}
			setMessage(message);
		}
	}

	/**
	 * 
	 * @param element1
	 * @param element2
	 */
	public LevelVersionError(SBase element1, SBase element2) {
		super();
		Message message = new Message();
		if (element1.getLevel() != element2.getLevel()) {
			message.setMessage(levelMismatchMessage(element1, element2));
		} else if (element1.getVersion() != element2.getVersion()) {
			message.setMessage(versionMismatchMessage(element1, element2));
		}
		setMessage(message);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBMLError#toString()
	 */
	public String toString() {
		return getMessage();
	}

}
