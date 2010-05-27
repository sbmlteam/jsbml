/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

import java.io.File;
import java.util.ArrayList;

import org.sbml.jsbml.util.Option;


public class SBMLErrorLog {

	private File file;
	
	private ArrayList<Option> options = new ArrayList<Option>();
	
	private ArrayList<SBMLError> validationErrors = new ArrayList<SBMLError>();

	private String status;
	
	public boolean add(SBMLError e) {
		return validationErrors.add(e);
	}
	
	public boolean add(Option option) {
		return options.add(option);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ArrayList<Option> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}

	public ArrayList<SBMLError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(ArrayList<SBMLError> validationErrors) {
		if (validationErrors == null) {
			clearLog();
			return;
		}
		
		this.validationErrors = validationErrors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getNumErrors() {
		return validationErrors.size();
	}
	
	public SBMLError getError(long i) {
		if (i >= 0 && i < validationErrors.size()) {
			return validationErrors.get((int) i);
		}
		
		return null;
	}
	
	public int getNumFailsWithSeverity(long severity) {
		// TODO 
		return 0;
	}
	
	public void clearLog() {
		validationErrors.clear();
	}
	
}
