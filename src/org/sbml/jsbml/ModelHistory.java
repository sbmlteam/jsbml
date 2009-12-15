/*
 * $Id: ModelHistory.java 91 2009-12-07 13:12:30Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/branches/jsbmlStax/src/org/sbml/jsbml/element/ModelHistory.java $
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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.w3c.util.DateParser;

/**
 * Contains all the history information about a Model (or other if level 3).
 * @author marine
 *
 */
public class ModelHistory {
	/**
	 * Contains all the ModelCreator instances of this ModelHistory.
	 */
	private LinkedList<ModelCreator> listOfModelCreators;
	/**
	 * Contains all the modified date instances of this ModelHistory.
	 */
	private LinkedList<Date> listOfModification;
	/**
	 * Date of creation
	 */
	private Date creation;
	/**
	 * Last date of modification
	 */
	private Date modified;

	/**
	 * Creates a ModelHistory instance. By default, the creation and modified are null.
	 * The listOfModification and listOfModelCreators are empty.
	 */
	public ModelHistory() {
		listOfModelCreators = new LinkedList<ModelCreator>();
		listOfModification = new LinkedList<Date>();
		creation = null;
		modified = null;
	}

	/**
	 * Creates a ModelHistory instance from a given ModelHistory.
	 * @param  modelHistory
	 */
	public ModelHistory(ModelHistory modelHistory) {
		listOfModelCreators = new LinkedList<ModelCreator>();
		listOfModelCreators.addAll(modelHistory.getListCreators());
		listOfModification = new LinkedList<Date>();
		listOfModification.addAll(modelHistory.getListModifiedDates());
		if (modelHistory.isSetCreatedDate()){
			creation = new Date();
			creation = modelHistory.getCreatedDate();//.clone();
		}
		else {
			creation = null;
		}
		if (modelHistory.isSetModifiedDate()){
			modified = new Date();
			modified = modelHistory.getModifiedDate();//.clone();
		}
		else {
			modified = null;
		}
	}

	/**
	 * Adds a ModelCreator instance to this ModelHistory.
	 * @param  mc
	 */
	public void addCreator(ModelCreator mc) {
		listOfModelCreators.add(mc);
	}

	/**
	 * Adds a Date of modification to this ModelHistory.
	 * @param  date
	 */
	public void addModifiedDate(Date date) {
		setModifiedDate(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public ModelHistory clone() {
		return new ModelHistory(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof ModelHistory) {
			boolean equal = super.equals(o);
			ModelHistory mh = (ModelHistory) o;
			equal &= listOfModelCreators.size() == mh.getListCreators().size();

			if (equal){
				for (int i = 0; i < listOfModelCreators.size(); i++){
					ModelCreator c1 = listOfModelCreators.get(i);
					ModelCreator c2 = mh.getListCreators().get(i);
					
					if (c1 != null && c2!= null){
						equal &= c1.equals(c2);
					}
					else if ((c1 == null && c2 != null) || (c2 == null && c1 != null)){
						return false;
					}
				}
				equal &= listOfModification.size() == mh.getListModifiedDates().size();
				if (equal){
					for (int i = 0; i < listOfModification.size(); i++){
						Date d1 = listOfModification.get(i);
						Date d2 = mh.getListModifiedDates().get(i);
						
						if (d1 != null && d2!= null){
							equal &= d1.equals(d2);
						}
						else if ((d1 == null && d2 != null) || (d2 == null && d1 != null)){
							return false;
						}
					}
				}
				equal &= isSetModifiedDate() == mh.isSetModifiedDate();
				if (equal){
					equal &= getModifiedDate().equals(mh.getModifiedDate());
				}
				equal &= isSetCreatedDate() == mh.isSetCreatedDate();
				if (equal){
					equal &= getCreatedDate().equals(mh.getCreatedDate());
				}
			}
			return equal;
		}
		return false;
	}

	/**
	 * Returns the createdDate from the ModelHistory.
	 * 
	 * @return Date object representing the createdDate from the ModelHistory. Can be null if it is not set.
	 */
	public Date getCreatedDate() {
		return creation;
	}

	/**
	 * Get the nth ModelCreator object in this ModelHistory.
	 * 
	 * @param  i
	 * @return the nth ModelCreator of this ModelHistory. Can be null.
	 */
	public ModelCreator getCreator(int i) {
		return listOfModelCreators.get(i);
	}

	/**
	 * Get the list of ModelCreator objects in this ModelHistory.
	 * 
	 * @return the list of ModelCreators for this ModelHistory.
	 */
	public List<ModelCreator> getListCreators() {
		return listOfModelCreators;
	}

	/**
	 * Get the list of ModifiedDate objects in this ModelHistory.
	 * 
	 * @return the list of ModifiedDates for this ModelHistory.
	 */
	public List<Date> getListModifiedDates() {
		return listOfModification;
	}

	/**
	 * Returns the modifiedDate from the ModelHistory.
	 * 
	 * @return Date object representing the modifiedDate from the ModelHistory. Can be null if it is not set.
	 */
	public Date getModifiedDate() {
		return modified;
	}

	/**
	 * Get the nth Date object in the list of ModifiedDates in this
	 * ModelHistory.
	 * 
	 * @param  n
	 *            the nth Date in the list of ModifiedDates of this
	 *            ModelHistory.
	 * @return the nth Date object in the list of ModifiedDates in this
	 * ModelHistory. Can be null if it is not set.
	 */
	public Date getModifiedDate(int n) {
		return listOfModification.get(n);
	}

	/**
	 * Get the number of ModelCreator objects in this ModelHistory.
	 * 
	 * @return the number of ModelCreators in this ModelHistory.
	 */
	public int getNumCreators() {
		return listOfModelCreators.size();
	}

	/**
	 * Get the number of ModifiedDate objects in this ModelHistory.
	 * 
	 * @return the number of ModifiedDates in this ModelHistory.
	 */
	public int getNumModifiedDates() {
		return listOfModification.size();
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelHistory's createdDate has been set.
	 * 
	 * @return true if the createdDate of this ModelHistory has been set, false
	 *         otherwise.
	 */
	public boolean isSetCreatedDate() {
		return creation != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * ModelHistory's modifiedDate has been set.
	 * 
	 * @return true if the modifiedDate of this ModelHistory has been set, false
	 *         otherwise.
	 */
	public boolean isSetModifiedDate() {
		return modified != null;
	}

	/**
	 * Sets the createdDate.
	 * 
	 * @param  date
	 *            a Date object representing the date the ModelHistory was
	 *            created.
	 */
	public void setCreatedDate(Date date) {
		creation = date;
	}

	/**
	 * Sets the modifiedDate.
	 * 
	 * @param  date
	 *            a Date object representing the date the ModelHistory was
	 *            modified.
	 */
	public void setModifiedDate(Date date) {
		listOfModification.add(date);
		modified = date;
	}
	
	/**
	 * writes the beginning of a creator element in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void beginCreatorElement(String indent, StringBuffer buffer){
		
		buffer.append(indent).append("<dc:creator> \n");
		buffer.append(indent).append("  <rdf:Bag> \n");
	}
	
	/**
	 * writes the end of a creator element in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void endCreatorElement(String indent, StringBuffer buffer){
		
		buffer.append(indent).append("  </rdf:Bag> \n");
		buffer.append(indent).append("</dc:creator> \n");	
	}
	
	/**
	 * writes the content of a creator element in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createCreatorElements(String indent, StringBuffer buffer){
		
		beginCreatorElement(indent, buffer);
		if (listOfModelCreators != null){
			for (int i = 0; i < getNumCreators(); i++){
				ModelCreator creator = getCreator(i);
				
				creator.toXML(indent + "    ", buffer);
			}
		}
		endCreatorElement(indent, buffer);
	}
	
	/**
	 * writes the content of a created element in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createCreatedElement(String indent, StringBuffer buffer){
		
		if (isSetCreatedDate()){
			String createdDate = DateParser.getIsoDate(getCreatedDate());
			
			buffer.append(indent).append("<dcterms:created rdf:parseType=").append('"').append("Resource").append('"').append("> \n");
			buffer.append(indent).append("  <dcterms:W3CDTF>").append(createdDate).append("<dcterms:W3CDTF> \n");
			buffer.append(indent).append("</dcterms:created> \n");
		}
	}
	
	/**
	 * writes the content of the modified elements in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void createModifiedElements(String indent, StringBuffer buffer){
		if (isSetModifiedDate()){
			
			for (int i = 0; i < getNumModifiedDates(); i++){
				String modifiedDate = DateParser.getIsoDate(getModifiedDate(i));
				buffer.append(indent).append("<dcterms:modified rdf:parseType=").append('"').append("Resource").append('"').append("> \n");
				buffer.append(indent).append("  <dcterms:W3CDTF>").append(modifiedDate).append("<dcterms:W3CDTF> \n");
				buffer.append(indent).append("</dcterms:modified> \n");
			}
		}
	}
	
	/**
	 * converts the ModelHistory into the XML history section of an annotation
	 * @param indent
	 * @param buffer
	 */
	public void toXML(String indent, StringBuffer buffer){
		
		createCreatorElements(indent, buffer);
		createCreatedElement(indent, buffer);
		createModifiedElements(indent, buffer);
	}
	
	/**
	 * If there is no ith ModelCreator, it returns null.
	 * @param i
	 * @return the ModelCreator removed from the listOfModelCreators.
	 */
	public ModelCreator removeModelCreator(int i){
		if (i < listOfModelCreators.size()){
			return listOfModelCreators.remove(i);
		}
		return null;
	}
	
	/**
	 * If there is no ith modified Date, it returns null.
	 * @param i
	 * @return the modified Date removed from the listOfModification.
	 */
	public Date removeModifiedDate(int i){
		if (i < listOfModification.size()){
			if (i == listOfModification.size() - 1){
				if (i - 2 >= 0){
					this.modified = listOfModification.get(i - 2);
				}
				else {
					this.modified = null;
				}
			}
			return listOfModification.remove(i);
		}
		return null;
	}
	/**
	 * Sets the created of this ModelHistory to null.
	 */
	public void unsetCreatedDate(){
		this.creation = null;
	}
	
	/**
	 * 
	 * @param nodeName
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return true if the XML attribute is known by this ModelHistory.
	 */
	public boolean readAttribute(String nodeName, String attributeName, String prefix, String value){
	
		if (nodeName.equals("creator") || nodeName.equals("created") || nodeName.equals("modified")){
			if (attributeName.equals("parseType") && value.equals("Resource")){
				return true;
			}
		}
		return false;
	}

}
