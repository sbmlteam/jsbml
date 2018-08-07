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
package org.sbml.jsbml;

import org.sbml.jsbml.util.Detail;
import org.sbml.jsbml.util.Message;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLException;

/**
 * Representation of errors, warnings and other diagnostics. <br>
 * <br>
 * For more details, see the <a href=
 * "http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/SBMLError.html"
 * >libSBML SBMLError javadoc</a>
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class SBMLError extends XMLException {

    /**
     * 
     * @author Nicolas Rodriguez
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
    private int column;

    /**
     * 
     */
    private int line;

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
     * 
     */
    private String packageName;
    
    /**
     * SBase that is the source for the reported error.
     */
    private SBase source;

    /**
     * Constructs a new {@link SBMLError}.
     */
    public SBMLError() {
	super();
    }

    /**
     * Constructs a new {@link SBMLError} with the given message.
     * 
     * @param message
     *            the error message
     */
    public SBMLError(String message) {
	super(message);
	this.message = new Message();
	this.message.setMessage(message);
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
     * @doc.note See the appendix on the SBML specifications about validations
     *           rules.
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
	return column;
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
	return line;
    }

    /**
     * Returns the full {@link Message}.
     * 
     * @return the full {@link Message}.
     */
    public Message getMessageInstance() {
	return message;
    }

    /**
     * Returns the message of the {@link SBMLError}
     *
     * @return the message of the {@link SBMLError}
     */
    @Override
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
     * Returns {@code true} is the {@link SBMLError} severity is of type
     * {@link SEVERITY#FATAL}
     * 
     * @return {@code true} is the {@link SBMLError} severity is of type
     *         {@link SEVERITY#FATAL}
     */
    public boolean isFatal() {
	return severity != null && severity.equalsIgnoreCase("Fatal");
    }

    /**
     * Returns {@code true} is the {@link SBMLError} severity is of type
     * {@link SEVERITY#ERROR}
     * 
     * @return {@code true} is the {@link SBMLError} severity is of type
     *         {@link SEVERITY#ERROR}
     */
    public boolean isError() {
	return severity != null && severity.equalsIgnoreCase("Error");
    }

    /**
     * Returns {@code true} is the {@link SBMLError} severity is of type
     * {@link SEVERITY#INFO}
     * 
     * @return {@code true} is the {@link SBMLError} severity is of type
     *         {@link SEVERITY#INFO}
     */
    public boolean isInfo() {
	return severity != null && (severity.equalsIgnoreCase("Info") || severity.equalsIgnoreCase("Advisory"));
    }

    /**
     * Returns {@code true} is the {@link SBMLError} severity is of type
     * {@link SEVERITY#WARNING}
     * 
     * @return {@code true} is the {@link SBMLError} severity is of type
     *         {@link SEVERITY#WARNING}
     */
    public boolean isWarning() {
	return severity != null && severity.equalsIgnoreCase("Warning");
    }

    /**
     * Returns {@code true} is the {@link SBMLError} category is of type
     * {@code internal}
     * 
     * @return {@code true} is the {@link SBMLError} category is of type
     *         {@code internal}
     */
    public boolean isInternal() {
	return category != null && category.equalsIgnoreCase("Internal");
    }

    /**
     * Returns {@code true} is the {@link SBMLError} category is of type
     * {@code system}
     * 
     * @return {@code true} is the {@link SBMLError} category is of type
     *         {@code system}
     */
    public boolean isSystem() {
	return category != null && category.equalsIgnoreCase("System");
    }

    /**
     * Returns {@code true} is the {@link SBMLError} category is of type
     * {@code xml}
     * 
     * @return {@code true} is the {@link SBMLError} category is of type
     *         {@code xml}
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
     * @doc.note See the appendix on the SBML specifications about validations
     *           rules.
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
     * Sets the message of this {@link SBMLError}.
     * 
     * @param message
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

    /**
     * 
     * @return
     */
    public Message getShortMessage() {
	return shortmessage;
    }

    /**
     * 
     * @param shortmessage
     */
    public void setShortMessage(Message shortmessage) {
	this.shortmessage = shortmessage;
    }

    /**
     * 
     * @return
     */
    public Detail getDetail() {
	return detail;
    }

    /**
     * 
     * @param detail
     */
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
	return StringTools.concat("SBMLError ", code, " [", severity, "] [", category, "] ", "\n  excerpt = ", excerpt,
		"\n  Line = ", line, ",  Column = ", column, "\n  package = ", packageName, "\n  short message = ",
		(shortmessage != null ? shortmessage.getMessage() : ""), " (lang='", (shortmessage != null ? shortmessage.getLang() : ""),
		"')", "\n  message = ", (message != null ? message.getMessage() : ""), "\n").toString();
    }

    /**
     * 
     * @param column
     */
    public void setColumn(int column) {
	this.column = column;
    }

    /**
     * 
     * @param line
     */
    public void setLine(int line) {
	this.line = line;
    }

    /**
     * 
     * @param packageName
     */
    public void setPackage(String packageName) {
	this.packageName = packageName;
    }

    /**
     * 
     * @return
     */
    public String getPackage() {
	return packageName;
    }

    /**
     * Returns the source for this {@link SBMLError}.
     * 
     * @return the source
     */
    public SBase getSource() {
      return source;
    }

    /**
     * Sets the source of this {@link SBMLError}.
     * 
     * @param source the source to set
     */
    public void setSource(SBase source) {
      this.source = source;
    }
    
    
}
