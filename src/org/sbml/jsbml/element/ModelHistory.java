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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.w3c.util.DateParser;

public class ModelHistory {
	private LinkedList<ModelCreator> listOfModelCreators;
	private LinkedList<Date> listOfModification;
	private Date creation;
	private Date modified;

	/**
	 * 
	 */
	public ModelHistory() {
		listOfModelCreators = new LinkedList<ModelCreator>();
		listOfModification = new LinkedList<Date>();
		creation = null;
		modified = null;
	}

	/**
	 * 
	 * @param  modelHistory
	 */
	public ModelHistory(ModelHistory modelHistory) {
		listOfModelCreators = new LinkedList<ModelCreator>();
		listOfModelCreators.addAll(modelHistory.getListCreators());
		listOfModification = new LinkedList<Date>();
		listOfModification.addAll(modelHistory.getListModifiedDates());
		creation = (Date) modelHistory.getCreatedDate();//.clone();
		modified = (Date) modelHistory.getModifiedDate();//.clone();
	}

	/**
	 * 
	 * @param  mc
	 */
	public void addCreator(ModelCreator mc) {
		listOfModelCreators.add(mc);
	}

	/**
	 * 
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
			equal &= listOfModelCreators.equals(mh.getListCreators());
			equal &= listOfModification.equals(mh.getListModifiedDates());
			equal &= isSetCreatedDate() == mh.isSetCreatedDate();
			if (isSetCreatedDate() && mh.isSetCreatedDate())
				equal &= creation.equals(mh.getCreatedDate());
			equal &= isSetModifiedDate() == mh.isSetModifiedDate();
			if (isSetModifiedDate() && mh.isSetModifiedDate())
				equal &= modified.equals(mh.getModifiedDate());
			return equal;
		}
		return false;
	}

	/**
	 * Returns the createdDate from the ModelHistory.
	 * 
	 * @return Date object representing the createdDate from the ModelHistory.
	 */
	public Date getCreatedDate() {
		return creation;
	}

	/**
	 * Get the nth ModelCreator object in this ModelHistory.
	 * 
	 * @param  i
	 * @return the nth ModelCreator of this ModelHistory.
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
	 * @return Date object representing the modifiedDate from the ModelHistory.
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
	 * @return
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
	 * write the beginning of a creator element in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void beginCreatorElement(String indent, StringBuffer buffer){
		
		buffer.append(indent).append("<dc:creator> \n");
		buffer.append(indent).append("  <rdf:Bag> \n");
	}
	
	/**
	 * write the end of a creator element in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	private void endCreatorElement(String indent, StringBuffer buffer){
		
		buffer.append(indent).append("  </rdf:Bag> \n");
		buffer.append(indent).append("</dc:creator> \n");	
	}
	
	/**
	 * write the content of a creator element in 'buffer'
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
	 * write the content of a created element in 'buffer'
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
	 * write the content of the modified elements in 'buffer'
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
	
	/*
	 * (non-Javadoc)
	 * 
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
