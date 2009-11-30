package org.sbml.jsbml.xml;

import java.util.HashMap;

public class SBMLObjectForXML {

	private String namespace;
	private String prefix;
	private String name;
	private HashMap<String, String> attributes = new HashMap<String, String>();
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
	public boolean isSetName() {
		return name != null;
	}
	/**
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
	public boolean isSetCharacters() {
		return characters != null;
	}
	public void clear(){
		this.attributes = null;
		this.characters = null;
		this.name = null;
		this.namespace = null;
		this.prefix = null;
	}
	
}
