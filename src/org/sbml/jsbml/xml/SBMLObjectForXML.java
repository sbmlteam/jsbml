/*
 * $Id: SBMLObjectForXML.java 38 2009-12-14 15:50:38Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/xml/SBMLObjectForXML.java $
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

package org.sbml.jsbml.xml;

import java.util.HashMap;

/**
 * A SBMLObjectForXML is an object to store the localName, prefix, namespace URI, attributes and text of
 * a SBML component we want to write.
 * @author compneur
 *
 */
public class SBMLObjectForXML {

	/**
	 * Represents the namespace URI of a SBML component to write.
	 */
	private String namespace;
	/**
	 * Represents the prefix of a SBML component to write.
	 */
	private String prefix;
	/**
	 * Represents the localName of a SBML component to write.
	 */
	private String name;
	/**
	 * Contains the XML attributes of a SBML component to write.
	 */
	private HashMap<String, String> attributes = new HashMap<String, String>();
	/**
	 * Represents the text of a SBML component to write.
	 */
	private String characters;
	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}
	/**
	 * 
	 * @return true if the namespace of this Object is not null;
	 */
	public boolean isSetNamespace() {
		return namespace != null;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * 
	 * @return true if the prefix of this Object is not null.
	 */
	public boolean isSetPrefix() {
		return prefix != null;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return true if the name of this object is not null.
	 */
	public boolean isSetName() {
		return name != null;
	}
	/**
	 * Adds an attribute to this attributes HashMap.
	 * @param attributes the attributes to set
	 */
	public void addAttributes(HashMap<String, String> attributes) {
		if (this.attributes == null){
			this.attributes = new HashMap<String, String>();
		}
		this.attributes.putAll(attributes);
	}
	/**
	 * @return the attributes
	 */
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	/**
	 * 
	 * @return true if the attributes of this object is not null.
	 */
	public boolean isSetAttributes() {
		return attributes != null;
	}
	/**
	 * @param characters the characters to set
	 */
	public void setCharacters(String characters) {
		this.characters = characters;
	}
	/**
	 * @return the characters
	 */
	public String getCharacters() {
		return characters;
	}
	/**
	 * 
	 * @return true if the characters of this object is not null.
	 */
	public boolean isSetCharacters() {
		return characters != null;
	}
	/**
	 * Sets all the variable of this object to null.
	 */
	public void clear(){
		this.attributes = null;
		this.characters = null;
		this.name = null;
		this.namespace = null;
		this.prefix = null;
	}
	
}
