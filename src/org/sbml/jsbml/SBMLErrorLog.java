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
		this.validationErrors = validationErrors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getNumErrors() {
		if (validationErrors == null) {
			return 0;
		}
		
		return validationErrors.size();
	}
	
	public SBMLError getError(long i) {
		if (validationErrors != null && (i >= 0 && i < validationErrors.size())) {
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
