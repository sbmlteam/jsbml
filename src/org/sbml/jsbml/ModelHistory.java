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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-09-04
 */
public class ModelHistory {
	private LinkedList<ModelCreator> listOfModelCreators;
	private LinkedList<Date> listOfModification;
	private Date creation;
	private Date modifyed;

	/**
	 * 
	 */
	public ModelHistory() {
		listOfModelCreators = new LinkedList<ModelCreator>();
		listOfModification = new LinkedList<Date>();
		creation = null;
		modifyed = null;
	}

	/**
	 * 
	 * @param modelHistory
	 */
	public ModelHistory(ModelHistory modelHistory) {
		listOfModelCreators = new LinkedList<ModelCreator>();
		listOfModelCreators.addAll(modelHistory.getListCreators());
		listOfModification = new LinkedList<Date>();
		listOfModification.addAll(modelHistory.getListModifiedDates());
		creation = (Date) modelHistory.getCreatedDate().clone();
		modifyed = (Date) modelHistory.getModifiedDate().clone();
	}

	/**
	 * 
	 * @param mc
	 */
	public void addCreator(ModelCreator mc) {
		listOfModelCreators.add(mc);
	}

	/**
	 * 
	 * @param date
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
				equal &= modifyed.equals(mh.getModifiedDate());
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
	 * @param i
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
		return modifyed;
	}

	/**
	 * Get the nth Date object in the list of ModifiedDates in this
	 * ModelHistory.
	 * 
	 * @param n
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
		return modifyed != null;
	}

	/**
	 * Sets the createdDate.
	 * 
	 * @param date
	 *            a Date object representing the date the ModelHistory was
	 *            created.
	 */
	public void setCreatedDate(Date date) {
		creation = date;
	}

	/**
	 * Sets the modifiedDate.
	 * 
	 * @param date
	 *            a Date object representing the date the ModelHistory was
	 *            modified.
	 */
	public void setModifiedDate(Date date) {
		if (isSetModifiedDate())
			listOfModification.add(getModifiedDate());
		modifyed = date;
	}

}
