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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains all the history information about a {@link Model} (or other {@link SBase} if level 3).
 * 
 * @author marine
 * @author Andreas Dr&auml;ger
 * 
 */
public class History implements Cloneable, Serializable {
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -1699117162462037149L;
	/**
	 * Contains all the {@link Creator} instances of this {@link History}.
	 */
	private List<Creator> listOfCreators;
	/**
	 * Contains all the modified date instances of this {@link History}.
	 */
	private List<Date> listOfModification;
	/**
	 * Date of creation
	 */
	private Date creation;
	/**
	 * Last date of modification
	 */
	private Date modified;

	/**
	 * Creates a {@link History} instance. By default, the creation and modified
	 * are null. The {@link #listOfModification} and {@link #listOfCreators} are empty.
	 */
	public History() {
		listOfCreators = new LinkedList<Creator>();
		listOfModification = new LinkedList<Date>();
		creation = null;
		modified = null;
	}

	/**
	 * Creates a {@link History} instance from a given {@link History}.
	 * 
	 * @param modelHistory
	 */
	public History(History modelHistory) {
		listOfCreators = new LinkedList<Creator>();
		listOfCreators.addAll(modelHistory.getListCreators());
		listOfModification = new LinkedList<Date>();
		listOfModification.addAll(modelHistory.getListModifiedDates());
		Calendar calendar = Calendar.getInstance();
		if (modelHistory.isSetCreatedDate()) {
			calendar.setTime(modelHistory.getCreatedDate());
			creation = calendar.getTime();
		} else {
			creation = null;
		}
		if (modelHistory.isSetModifiedDate()) {
			calendar.setTime(modelHistory.getModifiedDate());
			modified = calendar.getTime();
		} else {
			modified = null;
		}
	}

	/**
	 * Adds a {@link Creator} instance to this {@link History}.
	 * 
	 * @param mc
	 */
	public void addCreator(Creator mc) {
		listOfCreators.add(mc);
	}

	/**
	 * Adds a Date of modification to this {@link History}.
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
	@Override
	public History clone() {
		return new History(this);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof History) {
			boolean equal = super.equals(o);
			History mh = (History) o;
			equal &= listOfCreators.size() == mh.getListCreators().size();

			if (equal) {
				for (int i = 0; i < listOfCreators.size(); i++) {
					Creator c1 = listOfCreators.get(i);
					Creator c2 = mh.getListCreators().get(i);

					if (c1 != null && c2 != null) {
						equal &= c1.equals(c2);
					} else if ((c1 == null && c2 != null)
							|| (c2 == null && c1 != null)) {
						return false;
					}
				}
				equal &= listOfModification.size() == mh.getListModifiedDates()
						.size();
				if (equal) {
					for (int i = 0; i < listOfModification.size(); i++) {
						Date d1 = listOfModification.get(i);
						Date d2 = mh.getListModifiedDates().get(i);

						if (d1 != null && d2 != null) {
							equal &= d1.equals(d2);
						} else if ((d1 == null && d2 != null)
								|| (d2 == null && d1 != null)) {
							return false;
						}
					}
				}
				equal &= isSetModifiedDate() == mh.isSetModifiedDate();
				if (equal) {
					equal &= getModifiedDate().equals(mh.getModifiedDate());
				}
				equal &= isSetCreatedDate() == mh.isSetCreatedDate();
				// isSetCreatedDate() may still be null.
				if (equal && isSetCreatedDate()) {
					equal &= getCreatedDate().equals(mh.getCreatedDate());
				}
			}
			return equal;
		}
		return false;
	}

	/**
	 * Returns the createdDate from the {@link History}.
	 * 
	 * @return {@link Date} object representing the createdDate from the {@link History}.
	 *         Can be null if it is not set.
	 */
	public Date getCreatedDate() {
		return creation;
	}

	/**
	 * Get the nth {@link Creator} object in this {@link History}.
	 * 
	 * @param i
	 * @return the nth {@link Creator} of this {@link History}. Can be null.
	 */
	public Creator getCreator(int i) {
		return listOfCreators.get(i);
	}

	/**
	 * Get the list of {@link Creator} objects in this {@link History}.
	 * 
	 * @return the list of {@link Creator}s for this {@link History}.
	 */
	public List<Creator> getListCreators() {
		return listOfCreators;
	}

	/**
	 * Get the list of ModifiedDate objects in this {@link History}.
	 * 
	 * @return the list of ModifiedDates for this {@link History}.
	 */
	public List<Date> getListModifiedDates() {
		return listOfModification;
	}

	/**
	 * Returns the modifiedDate from the {@link History}.
	 * 
	 * @return Date object representing the modifiedDate from the {@link History}.
	 *         Can be null if it is not set.
	 */
	public Date getModifiedDate() {
		return modified;
	}

	/**
	 * Get the nth {@link Date} object in the list of ModifiedDates in this
	 * {@link History}.
	 * 
	 * @param n
	 *            the nth {@link Date} in the list of ModifiedDates of this
	 *            {@link History}.
	 * @return the nth {@link Date} object in the list of ModifiedDates in this
	 *         {@link History}. Can be null if it is not set.
	 */
	public Date getModifiedDate(int n) {
		return listOfModification.get(n);
	}

	/**
	 * Get the number of {@link Creator} objects in this {@link History}.
	 * 
	 * @return the number of {@link Creator}s in this {@link History}.
	 */
	public int getNumCreators() {
		return listOfCreators.size();
	}

	/**
	 * Get the number of ModifiedDate objects in this {@link History}.
	 * 
	 * @return the number of ModifiedDates in this {@link History}.
	 */
	public int getNumModifiedDates() {
		return listOfModification.size();
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * {@link History}'s createdDate has been set.
	 * 
	 * @return true if the createdDate of this {@link History} has been set, false
	 *         otherwise.
	 */
	public boolean isSetCreatedDate() {
		return creation != null;
	}

	/**
	 * Predicate returning true or false depending on whether this
	 * {@link History}'s modifiedDate has been set.
	 * 
	 * @return true if the modifiedDate of this {@link History} has been set, false
	 *         otherwise.
	 */
	public boolean isSetModifiedDate() {
		return modified != null;
	}

	/**
	 * 
	 * @param nodeName
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return true if the XML attribute is known by this {@link History}.
	 */
	public boolean readAttribute(String nodeName, String attributeName,
			String prefix, String value) {

		if (nodeName.equals("creator") || nodeName.equals("created")
				|| nodeName.equals("modified")) {
			if (attributeName.equals("parseType") && value.equals("Resource")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *If there is no ith {@link Creator}, it returns null.
	 * 
	 * @param i
	 * @return the {@link Creator} removed from the {@link #listOfCreators}.
	 */
	public Creator removeCreator(int i) {
		if (i < listOfCreators.size()) {
			return listOfCreators.remove(i);
		}
		return null;
	}

	/**
	 * If there is no ith modified {@link Date}, it returns null.
	 * 
	 * @param i
	 * @return the modified {@link Date} removed from the listOfModification.
	 */
	public Date removeModifiedDate(int i) {
		if (i < listOfModification.size()) {
			if (i == listOfModification.size() - 1) {
				if (i - 2 >= 0) {
					this.modified = listOfModification.get(i - 2);
				} else {
					this.modified = null;
				}
			}
			return listOfModification.remove(i);
		}
		return null;
	}

	/**
	 * Sets the createdDate.
	 * 
	 * @param date
	 *            a {@link Date} object representing the date the {@link History} was
	 *            created.
	 */
	public void setCreatedDate(Date date) {
		creation = date;
	}

	/**
	 * Sets the modifiedDate.
	 * 
	 * @param date
	 *            a {@link Date} object representing the date the {@link History} was
	 *            modified.
	 */
	public void setModifiedDate(Date date) {
		listOfModification.add(date);
		modified = date;
	}

	/**
	 * converts the {@link History} into the XML history section of an annotation
	 * 
	 * @param indent
	 * @param buffer
	 */
	public void toXML(String indent, StringBuffer buffer) {
		// TODO 
	}

	/**
	 * Sets the created of this {@link History} to null.
	 */
	public void unsetCreatedDate() {
		this.creation = null;
	}

}
