/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml.element;


public class ModelCreator {

	private String email;
	private String familyName;
	private String givenName;
	private String organization;
	private String otherXMLInformation;

	/**
	 * 
	 */
	public ModelCreator() {
		this.email = null;
		this.familyName = null;
		this.givenName = null;
		this.organization = null;
		this.otherXMLInformation = null;
	}

	/**
	 * 
	 * @param  modelCreator
	 */
	public ModelCreator(ModelCreator modelCreator) {
		// TODO Auto-generated constructor stub
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
		return false;

	}

	/**
	 * Returns the email from the ModelCreator.
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the familyName from the ModelCreator.
	 * 
	 * @return
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * Returns the givenName from the ModelCreator.
	 * 
	 * @return
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * Returns the organization from the ModelCreator.
	 * 
	 * @return
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's email has been set.
	 * 
	 * @return
	 */
	public boolean isSetEmail() {
		return email != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's familyName has been set.
	 * 
	 * @return
	 */
	public boolean isSetFamilyName() {
		return familyName != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's givenName has been set.
	 * 
	 * @return
	 */
	public boolean isSetGivenName() {
		return givenName != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelCreator's organization has been set.
	 * 
	 * @return
	 */
	public boolean isSetOrganization() {
		return organization != null;
	}

	/**
	 * Sets the email
	 * 
	 * @param  email
	 * @return
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
	 * @return
	 */
	public int setFamilyName(String familyName) {
		this.familyName = familyName;
		// TODO
		return 0;
	}

	/**
	 * Sets the family name
	 * 
	 * @param  givenName
	 * @return
	 */
	public int setGivenName(String givenName) {
		this.givenName = givenName;
		// TODO
		return 0;
	}

	/**
	 * Sets the organization
	 * 
	 * @param  organization
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Unsets the email of this ModelCreator.
	 * 
	 * @return
	 */
	public int unsetEmail() {
		email = null;
		// TODO
		return 0;
	}

	/**
	 * Unsets the familyName of this ModelCreator.
	 * 
	 * @return
	 */
	public int unsetFamilyName() {
		familyName = null;
		// TODO
		return 0;
	}

	/**
	 * Unsets the givenName of this ModelCreator.
	 * 
	 * @return
	 */
	public int unsetGivenName() {
		givenName = null;
		// TODO
		return 0;
	}

	/**
	 * Unsets the organization of this ModelCreator.
	 * 
	 */
	public void unsetOrganization() {
		organization = null;
	}
	
	/**
	 * write the N element of the ModelCreator in 'buffer'
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
	 * write the EMAIL element of the ModelCreator in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createEMAILElement(String indent, StringBuffer buffer){
		if (isSetEmail()){
			buffer.append(indent).append("<vCard:EMAIL>").append(getEmail()).append("</vCard:EMAIL> \n");
		}
	}
	
	/**
	 * write the ORG element of the ModelCreator in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createOrGElement(String indent, StringBuffer buffer){
		if (isSetOrganization()){
			buffer.append(indent).append("<vCard:OrG> \n");
			buffer.append(indent).append("  <vCard:Orgname>").append(getOrganization()).append("</vCard:Orgname> \n");
			buffer.append(indent).append("</vCard:OrG> \n");
		}
	}
	
	/**
	 * write the other elements of the ModelCreator in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createOtherElement(String indent, StringBuffer buffer){
		
		if (getOtherXMLInformation() != null){
			String [] lines = getOtherXMLInformation().split("\n");
			for (int i = 0; i < lines.length; i++){
				buffer.append(indent).append(lines[i]).append(" \n");
			}
		}
	}
	
	/**
	 * convert the ModelCreator into XML
	 * @param indent
	 * @param buffer
	 */
	public void toXML(String indent, StringBuffer buffer){
		
		buffer.append(indent).append("<rdf:li rdf:parseType=").append('"').append("Resource").append('"').append("> \n");
		
		createNElement(indent + "  ", buffer);
		createEMAILElement(indent + "  ", buffer);
		createOrGElement(indent + "  ", buffer);
		createOtherElement(indent, buffer);
		
		buffer.append(indent).append("</rdf:li> \n");
			
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isSetGivenName()) {
			sb.append(getGivenName());
			if (isSetFamilyName() || isSetEmail() || isSetOrganization())
				sb.append(' ');
		}
		if (isSetFamilyName()) {
			sb.append(getFamilyName());
			if (isSetEmail() || isSetOrganization())
				sb.append(", ");
		}
		if (isSetEmail()) {
			sb.append(getEmail());
			if (isSetOrganization())
				sb.append(", ");
		}
		if (isSetOrganization())
			sb.append(getOrganization());
		return sb.toString();
	}

	/**
	 * changes the other information about the creator
	 * @param otherXMLInformation
	 */
	public void setOtherXMLInformation(String otherXMLInformation) {
		if (getOtherXMLInformation() == null){
			this.otherXMLInformation = otherXMLInformation;
		}
		else{
			this.otherXMLInformation += otherXMLInformation;
		}
	}

	/**
	 * 
	 * @return the otherXMLInformation String of ModelCreator
	 */
	public String getOtherXMLInformation() {
		return otherXMLInformation;
	}

	/*
	 * (non-Javadoc)
	 * 
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
