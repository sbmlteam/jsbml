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

import org.sbml.jsbml.util.Location;
import org.sbml.jsbml.util.Message;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLError;

/**
 * Representation of errors, warnings and other diagnostics. <br/>
 * <br/>
 * For more details, see the <a href=
 * "http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/SBMLError.html"
 * >libSBML SBMLError javadoc</a>
 * 
 * @author rodrigue
 * @author Andreas Dr&auml;ger
 * 
 */
public class SBMLError extends XMLError {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6071833473902680630L;
	/**
	 * 
	 */
	private String category;
	/**
	 * 
	 */
	private int code;
	/**
	 * 
	 */
	private String excerpt;
	/**
	 * 
	 */
	private Location location;
	/**
	 * 
	 */
	private Message message;
	/**
	 * 
	 */
	private String severity;

	/**
	 * 
	 */
	public SBMLError() {
		super();
	}
	
	/**
	 * 
	 * @param message
	 */
	public SBMLError(String message) {
		super(message);
	}

	// String category, int code, String severity,

	/**
	 * 
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 
	 * @return
	 */
	public int getColumn() {
		return location.getColumn();
	}

	/**
	 * 
	 * @return
	 */
	public String getExcerpt() {
		return excerpt;
	}

	/**
	 * 
	 * @return
	 */
	public int getLine() {
		return location.getLine();
	}

	/**
	 * 
	 * @return
	 */
	public Location getLocation() {
		return location;
	}


	/**
	 * 
	 * @return
	 */
	public Message getMessageInstance() {
		return message;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message != null ? message.getMessage() : "";
	}

	/**
	 * 
	 * @return
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFatal() {
		return severity != null && severity.equalsIgnoreCase("Fatal");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInfo() {
		return severity != null && (severity.equalsIgnoreCase("Info") || severity.equalsIgnoreCase("Avisory"));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInternal() {
		return category != null && category.equalsIgnoreCase("Internal");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSystem() {
		return category != null && category.equalsIgnoreCase("System");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isWarning() {
		return severity != null && severity.equalsIgnoreCase("Warning");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isXML() {
		return category != null && category.equalsIgnoreCase("xml");
	}

	/**
	 * 
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 
	 * @param excerpt
	 */
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	/**
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * 
	 * @param messageObj
	 */
	public void setMessage(Message message) {
		this.message = message;
	}
	
	/**
	 * 
	 * @param severity
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringTools.concat("SBMLError [category=", category, ", code=",
				code, ", excerpt=", excerpt, ", location=", location,
				", \nmessage =", message, ", severity=", severity, "]")
				.toString();
	}

}
