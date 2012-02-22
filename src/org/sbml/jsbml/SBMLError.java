/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import org.sbml.jsbml.util.Detail;
import org.sbml.jsbml.util.Location;
import org.sbml.jsbml.util.Message;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLException;

/**
 * Representation of errors, warnings and other diagnostics. <br/>
 * <br/>
 * For more details, see the <a href=
 * "http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/SBMLError.html"
 * >libSBML SBMLError javadoc</a>
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SBMLError extends XMLException {

	/**
	 * 
	 * @author Nicolas Rodriguez
	 * @version $Rev$
	 * @since 0.8
	 */
	public enum SEVERITY {
		/**
		 * 
		 */
		INFO, 
		/**
		 * 
		 */
		WARNING,
		/**
		 * 
		 */
		ERROR,
		/**
		 * 
		 */
		FATAL
	}; 
	
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
	private Message shortmessage;
	
	/**
	 * 
	 */
	private Detail detail;
	/**
	 * 
	 */
	private String severity;


	/**
	 * Constructs a new {@link SBMLError}.
	 */
	public SBMLError() {
		super();
	}
	
	/**
	 * Constructs a new {@link SBMLError} with the given message.
	 * 
	 * @param message the error message
	 */
	public SBMLError(String message) {
		super(message);
	}

	/**
	 * Returns the category of the {@link SBMLError}
	 * 
	 * @return the category of the {@link SBMLError}
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Returns the code of this {@link SBMLError}.
	 * 
	 * @return the code of this {@link SBMLError}.
	 * 
	 * @see the appendix on the SBML specifications about validations rules.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Returns the column where this {@link SBMLError} happened.
	 * 
	 * @return the column where this {@link SBMLError} happened.
	 */
	public int getColumn() {
		return location.getColumn();
	}

	/**
	 * Returns a fragment of the original XML file that induced the error.
	 * 
	 * @return a fragment of the original XML file that induced the error.
	 */
	public String getExcerpt() {
		return excerpt;
	}

	/**
	 * Returns the line where this {@link SBMLError} happened.
	 * 
	 * @return the line where this {@link SBMLError} happened.
	 */
	public int getLine() {
		return location.getLine();
	}

	/**
	 * Returns the location where this {@link SBMLError} happened.
	 * 
	 * @return the location where this {@link SBMLError} happened.
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
	 * Returns the message of the {@link SBMLError}
	 *
	 * @return the message of the {@link SBMLError}
	 */
	public String getMessage() {
		return message != null ? message.getMessage() : "";
	}

	/**
	 * Returns the severity of the {@link SBMLError}
	 * 
	 * @return the severity of the {@link SBMLError}
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * Returns true is the {@link SBMLError} severity is of type {@link SEVERITY#FATAL}
	 * 
	 * @return true is the {@link SBMLError} severity is of type {@link SEVERITY#FATAL}
	 */
	public boolean isFatal() {
		return severity != null && severity.equalsIgnoreCase("Fatal");
	}

	/**
	 * Returns true is the {@link SBMLError} severity is of type {@link SEVERITY#ERROR}
	 * 
	 * @return true is the {@link SBMLError} severity is of type {@link SEVERITY#ERROR}
	 */
	public boolean isError() {
		return severity != null && severity.equalsIgnoreCase("Error");
	}

	/**
	 * Returns true is the {@link SBMLError} severity is of type {@link SEVERITY#INFO}
	 * 
	 * @return true is the {@link SBMLError} severity is of type {@link SEVERITY#INFO}
	 */
	public boolean isInfo() {
		return severity != null && (severity.equalsIgnoreCase("Info") || severity.equalsIgnoreCase("Advisory"));
	}

	/**
	 * Returns true is the {@link SBMLError} severity is of type {@link SEVERITY#WARNING}
	 * 
	 * @return true is the {@link SBMLError} severity is of type {@link SEVERITY#WARNING}
	 */
	public boolean isWarning() {
		return severity != null && severity.equalsIgnoreCase("Warning");
	}

	/**
	 * Returns true is the {@link SBMLError} category is of type <code>internal</code>
	 * 
	 * @return true is the {@link SBMLError} category is of type <code>internal</code>
	 */
	public boolean isInternal() {
		return category != null && category.equalsIgnoreCase("Internal");
	}

	/**
	 * Returns true is the {@link SBMLError} category is of type <code>system</code>
	 * 
	 * @return true is the {@link SBMLError} category is of type <code>system</code>
	 */
	public boolean isSystem() {
		return category != null && category.equalsIgnoreCase("System");
	}


	/**
	 * Returns true is the {@link SBMLError} category is of type <code>xml</code>
	 * 
	 * @return true is the {@link SBMLError} category is of type <code>xml</code>
	 */
	public boolean isXML() {
		return category != null && category.equalsIgnoreCase("xml");
	}

	/**
	 * Sets the category of this {@link SBMLError}.
	 * 
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Sets the error code of this {@link SBMLError}.
	 * 
	 * @param code
	 * 
	 * @see the appendix on the SBML specifications about validations rules.
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Sets the excerpt of this {@link SBMLError}.
	 * 
	 * @param excerpt
	 */
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	/**
	 * Sets the location of this {@link SBMLError}.
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Sets the message of this {@link SBMLError}.
	 * 
	 * @param messageObj
	 */
	public void setMessage(Message message) {
		this.message = message;
	}
	
	/**
	 * Sets the severity of this {@link SBMLError}.
	 * 
	 * @param severity
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public Message getShortMessage() {
		return shortmessage;
	}

	public void setShortMessage(Message shortmessage) {
		this.shortmessage = shortmessage;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringTools.concat("SBMLError ", code, " [", severity, "] [", category, "] ","\n  excerpt = ", excerpt, "\n  ", location,
				"\n  message = ", message.getMessage() ,"\n").toString();
	}


}
