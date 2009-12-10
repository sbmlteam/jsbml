/*
 * $Id: ModelCreator.java 91 2009-12-07 13:12:30Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/branches/jsbmlStax/src/org/sbml/jsbml/element/ModelCreator.java $
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
package org.sbml.jsbml.element;

/**
 * Contains all the information about a creator of a Model (or other in level 3).
 * @author marine3
 *
 */
public class ModelCreator {

	/**
	 * email of the creator
	 */
	private String email;
	/**
	 * Family name of the creator
	 */
	private String familyName;
	/**
	 * Given name of the creator
	 */
	private String givenName;
	/**
	 * Organisation name of the creator.
	 */
	private String organisation;
	
	//private String otherXMLInformation;

	/**
	 * Creates a ModelCreator instance. By default, the email, familyName, givenName, organisation are null.
	 */
	public ModelCreator() {
		this.email = null;
		this.familyName = null;
		this.givenName = null;
		this.organisation = null;
		//this.otherXMLInformation = null;
	}

	/**
	 * Creates a ModelCreator instance from a given ModelCreator.
	 * @param  modelCreator
	 */
	public ModelCreator(ModelCreator modelCreator) {
		if (modelCreator.isSetEmail()){
			this.email = new String(modelCreator.getEmail());
		}
		else {
			this.email = null;
		}
		if (modelCreator.isSetFamilyName()){
			this.familyName = new String(modelCreator.getFamilyName());
		}
		else {
			this.familyName = null;
		}
		if (modelCreator.isSetGivenName()){
			this.givenName = new String(modelCreator.getGivenName());
		}
		else {
			this.givenName = null;
		}
		if (modelCreator.isSetOrganisation()){
			this.organisation = new String(modelCreator.getOrganisation());
		}
		else {
			this.organisation = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public ModelCreator clone() {
		return new ModelCreator(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object sb) {
		if (sb instanceof ModelCreator){
			ModelCreator m = (ModelCreator) sb;
			boolean equal = isSetEmail() == m.isSetEmail();
			if (equal && isSetEmail()){
				equal &= getEmail().equals(m.getEmail());
			}
			equal &= isSetFamilyName() == m.isSetFamilyName();
			if (equal && isSetFamilyName()){
				equal &= getFamilyName().equals(m.getFamilyName());	
			}
			equal &= isSetGivenName() == m.isSetGivenName();
			if (equal && isSetGivenName()){
				equal &= getGivenName().equals(m.getGivenName());
			}
			equal &= isSetOrganisation() == m.isSetOrganisation();
			if (equal && isSetOrganisation()){
				equal &= getOrganisation().equals(m.getOrganisation());
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the email from the ModelCreator. Returns an empty String if it is not set.
	 */
	public String getEmail() {
		return isSetEmail() ? email : "";
	}

	/**
	 * 
	 * @return the familyName from the ModelCreator. Returns an empty String if it is not set.
	 */
	public String getFamilyName() {
		return isSetFamilyName() ? familyName : "";
	}

	/**
	 * Returns the givenName from the ModelCreator. Returns an empty String if it is not set.
	 * 
	 * @return
	 */
	public String getGivenName() {
		return isSetGivenName() ? givenName : "";
	}

	/**
	 * Returns the organisation from the ModelCreator. Returns an empty String if it is not set.
	 * 
	 * @return
	 */
	public String getOrganisation() {
		return isSetOrganisation() ? organisation : "";
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's email has been set.
	 * 
	 * @return true if the email of this ModelCreator is not null.
	 */
	public boolean isSetEmail() {
		return email != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's familyName has been set.
	 * 
	 * @return true if the familyName of this ModelCreator is not null.
	 */
	public boolean isSetFamilyName() {
		return familyName != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's givenName has been set.
	 * 
	 * @return true if the givenName of this ModelCreator is not null.
	 */
	public boolean isSetGivenName() {
		return givenName != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's organisation has been set.
	 * 
	 * @return true if the organisation of this ModelCreator is not null.
	 */
	public boolean isSetOrganisation() {
		return organisation != null;
	}

	/**
	 * Sets the email
	 * 
	 * @param  email
	 * @return 0
	 */
	public int setEmail(String email) {
		this.email = email;
		// TODO
		return 0;
	}

	/**
	 * Sets the family name
	 * 
	 * @param  familyName
	 * @return 0
	 */
	public int setFamilyName(String familyName) {
		this.familyName = familyName;
		// TODO
		return 0;
	}

	/**
	 * Sets the given name
	 * 
	 * @param  givenName
	 * @return 0
	 */
	public int setGivenName(String givenName) {
		this.givenName = givenName;
		// TODO
		return 0;
	}

	/**
	 * Sets the organisation
	 * 
	 * @param  organisation
	 */
	public void setOrganization(String organization) {
		this.organisation = organization;
	}

	/**
	 * Unsets the email of this ModelCreator.
	 * 
	 * @return 0
	 */
	public int unsetEmail() {
		email = null;
		// TODO
		return 0;
	}

	/**
	 * Unsets the familyName of this ModelCreator.
	 * 
	 * @return 0
	 */
	public int unsetFamilyName() {
		familyName = null;
		// TODO
		return 0;
	}

	/**
	 * Unsets the givenName of this ModelCreator.
	 * 
	 * @return 0
	 */
	public int unsetGivenName() {
		givenName = null;
		// TODO
		return 0;
	}

	/**
	 * Unsets the organisation of this ModelCreator.
	 * 
	 */
	public void unsetOrganization() {
		organisation = null;
	}
	
	/**
	 * writes the N element of the ModelCreator in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createNElement(String indent, StringBuffer buffer){
		
		if (isSetFamilyName() || isSetGivenName()){
			buffer.append(indent).append("<vCard:N rdf:parseType=").append('"').append("Resource").append('"').append("> \n");
			if (isSetFamilyName()){
				buffer.append(indent).append("  <vCard:Family>").append(getFamilyName()).append("</vCard:Family> \n");
			}
			
			if (isSetGivenName()){
				buffer.append(indent).append("  <vCard:Given>").append(getGivenName()).append("</vCard:Given> \n");
			}
			buffer.append(indent).append("</vCard:N> \n");
		}

	}
	
	/**
	 * writes the EMAIL element of the ModelCreator in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createEMAILElement(String indent, StringBuffer buffer){
		if (isSetEmail()){
			buffer.append(indent).append("<vCard:EMAIL>").append(getEmail()).append("</vCard:EMAIL> \n");
		}
	}
	
	/**
	 * writes the ORG element of the ModelCreator in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createOrGElement(String indent, StringBuffer buffer){
		if (isSetOrganisation()){
			buffer.append(indent).append("<vCard:OrG> \n");
			buffer.append(indent).append("  <vCard:Orgname>").append(getOrganisation()).append("</vCard:Orgname> \n");
			buffer.append(indent).append("</vCard:OrG> \n");
		}
	}
	
	// /**
	 // * write the other elements of the ModelCreator in 'buffer'
	 // * @param indent
	 // * @param buffer
	 // */
	/*private void createOtherElement(String indent, StringBuffer buffer){
		
		if (getOtherXMLInformation() != null){
			String [] lines = getOtherXMLInformation().split("\n");
			for (int i = 0; i < lines.length; i++){
				buffer.append(indent).append(lines[i]).append(" \n");
			}
		}
	}*/
	
	/**
	 * converts the ModelCreator into XML
	 * @param indent
	 * @param buffer
	 */
	public void toXML(String indent, StringBuffer buffer){
		
		buffer.append(indent).append("<rdf:li rdf:parseType=").append('"').append("Resource").append('"').append("> \n");
		
		createNElement(indent + "  ", buffer);
		createEMAILElement(indent + "  ", buffer);
		createOrGElement(indent + "  ", buffer);
		//createOtherElement(indent, buffer);
		
		buffer.append(indent).append("</rdf:li> \n");
			
	}

	/**
	 * @return the information about the creator as a String.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isSetGivenName()) {
			sb.append(getGivenName());
			if (isSetFamilyName() || isSetEmail() || isSetOrganisation()){
				sb.append(' ');
			}
		}
		if (isSetFamilyName()) {
			sb.append(getFamilyName());
			if (isSetEmail() || isSetOrganisation()){
				sb.append(", ");
			}
		}
		if (isSetEmail()) {
			sb.append(getEmail());
			if (isSetOrganisation()){
				sb.append(", ");
			}
		}
		if (isSetOrganisation()){
			sb.append(getOrganisation());
		}
		return sb.toString();
	}
	
	///**
	// * changes the other information about the creator
	// * @param otherXMLInformation
	// */
	/*public void setOtherXMLInformation(String otherXMLInformation) {
		if (getOtherXMLInformation() == null){
			this.otherXMLInformation = otherXMLInformation;
		}
		else{
			this.otherXMLInformation += otherXMLInformation;
		}
	}*/

	// /**
	// * 
	// * @return the otherXMLInformation String of ModelCreator
	// */
	/*public String getOtherXMLInformation() {
		return otherXMLInformation;
	}*/

	/**
	 * @return true if the XML attribute is known by this ModelCreator.
	 */
	public boolean readAttribute(String elementName, String attributeName, String prefix, String value){
	
		if (elementName.equals("li") || elementName.equals("N") || elementName.equals("ORG")){
			if (attributeName.equals("parseType") && value.equals("Resource")){
				return true;
			}
		}
		return false;
	}
}
