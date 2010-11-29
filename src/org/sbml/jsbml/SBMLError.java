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
