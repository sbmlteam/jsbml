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

package org.sbml.jsbml;

import java.io.File;
import java.util.ArrayList;

import org.sbml.jsbml.util.Option;

/**
 * 
 * @author rodrigue
 * 
 */
public class SBMLErrorLog {

	/**
	 * 
	 */
	private File file;

	/**
	 * 
	 */
	private ArrayList<Option> options = new ArrayList<Option>();
	/**
	 * 
	 */
	private ArrayList<SBMLError> validationErrors = new ArrayList<SBMLError>();

	/**
	 * 
	 */
	private String status;

	/**
	 * 
	 * @param option
	 * @return
	 */
	public boolean add(Option option) {
		return options.add(option);
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	public boolean add(SBMLError e) {
		return validationErrors.add(e);
	}

	/**
	 * 
	 */
	public void clearLog() {
		validationErrors.clear();
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public SBMLError getError(long i) {
		if (i >= 0 && i < validationErrors.size()) {
			return validationErrors.get((int) i);
		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumErrors() {
		return validationErrors.size();
	}

	/**
	 * 
	 * @param severity
	 * @return
	 */
	public int getNumFailsWithSeverity(long severity) {
		// TODO
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Option> getOptions() {
		return options;
	}

	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<SBMLError> getValidationErrors() {
		return validationErrors;
	}

	/**
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 
	 * @param options
	 */
	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @param validationErrors
	 */
	public void setValidationErrors(ArrayList<SBMLError> validationErrors) {
		if (validationErrors == null) {
			clearLog();
			return;
		}

		this.validationErrors = validationErrors;
	}

}
