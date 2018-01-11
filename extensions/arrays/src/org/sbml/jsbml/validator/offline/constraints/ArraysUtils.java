/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.validator.offline.constraints;

import java.util.List;

import org.sbml.jsbml.SBMLError;

/**
 * Contains some utility methods for the Arrays package validation.
 * 
 * @author rodrigue
 *
 */
public class ArraysUtils {

	/**
	 * Checks the list of SBMLError to see if it contains the given error code, returns
	 * {@code true} if it is found, else otherwise.
	 * 
	 * @param listOfErrors the list of errors to search
	 * @param errorCode the error code to search for
	 * @return {@code true} if the error code is found, else otherwise.
	 */
	public static boolean checkListOfErrors(List<SBMLError> listOfErrors, int errorCode) 
	{
		if (listOfErrors == null) {
			return false;
		}

		for (SBMLError error : listOfErrors) {
			// System.out.println("checkListOfErrors - current error = " + error.getCode() + ", error to found = " + errorCode);
			if (error.getCode() == errorCode) {
				return true;
			}
		}

		return false;
	}  
}
