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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Contains all the history information about a {@link Model} (or other
 * {@link SBase} if level &gt;= 3).
 * 
 * @author Marine Dumousseau
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class History extends AnnotationElement {
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1699117162462037149L;
  /**
   * Date of creation
   */
  private Date creation;
  /**
   * Contains all the {@link Creator} instances of this {@link History}.
   */
  private List<Creator> listOfCreators;
  /**
   * Contains all the modified date instances of this {@link History}.
   */
  private List<Date> listOfModification;
  /**
   * Last date of modification
   */
  private Date modified;

  /**
   * Creates a {@link History} instance. By default, the creation and modified
   * are {@code null}. The {@link #listOfModification} and
   * {@link #listOfCreators} are empty.
   */
  public History() {
    super();
    listOfCreators = new ArrayList<Creator>();
    listOfModification = new ArrayList<Date>();
    creation = null;
    modified = null;
  }

  /**
   * Creates a {@link History} instance from a given {@link History}.
   * 
   * @param history
   */
  public History(History history) {
    super(history);
    listOfCreators = new ArrayList<Creator>();
    for (Creator c : history.getListOfCreators()) {
      listOfCreators.add(c.clone());
    }
    listOfModification = new ArrayList<Date>();
    for (Date d : history.getListOfModifiedDates()) {
      listOfModification.add((Date) d.clone());
    }
    Calendar calendar = Calendar.getInstance();
    if (history.isSetCreatedDate()) {
      calendar.setTime(history.getCreatedDate());
      creation = calendar.getTime();
    }
    if (history.isSetModifiedDate()) {
      calendar.setTime(history.getModifiedDate());
      modified = calendar.getTime();
    }
  }

  /**
   * Adds a {@link Creator} instance to this {@link History}.
   * 
   * @param mc
   */
  public void addCreator(Creator mc) {
    boolean success = listOfCreators.add(mc);
    if (success) {
      mc.parent = this;
      firePropertyChange(TreeNodeChangeEvent.createdDate, null, mc);
    }
  }

  /**
   * Adds a {@link Date} of modification to this {@link History}.
   * 
   * @param date
   */
  public void addModifiedDate(Date date) {
    setModifiedDate(date);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public History clone() {
    return new History(this);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    // Check all child elements recursively in super class first:
    boolean equals = super.equals(object);
    if (equals) {
      // Cast is possible because super class checks the class attributes
      History mh = (History) object;
      equals &= listOfCreators.size() == mh.getListOfCreators().size();
      equals &= isSetModifiedDate() == mh.isSetModifiedDate();
      if (equals && isSetModifiedDate()) {
        equals &= getModifiedDate().equals(mh.getModifiedDate());
      }
      equals &= isSetCreatedDate() == mh.isSetCreatedDate();
      // isSetCreatedDate() may still be null.
      if (equals && isSetCreatedDate()) {
        equals &= getCreatedDate().equals(mh.getCreatedDate());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    int pos = 0;
    if (isSetListOfCreators()) {
      if (pos == childIndex) {
        return new TreeNodeAdapter(getListOfCreators(), this);
      }
      pos++;
    }
    if (isSetListOfModification()) {
      if (pos == childIndex) {
        return new TreeNodeAdapter(getListOfModifiedDates(), this);
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      childIndex, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    if (isSetListOfCreators()) {
      count ++;
    }
    if (isSetListOfModification()) {
      count++;
    }
    return count;
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
   * @return the nth {@link Creator} of this {@link History}. Can be
   *         {@code null}.
   */
  public Creator getCreator(int i) {
    return listOfCreators.get(i);
  }

  /**
   * Get the list of {@link Creator} objects in this {@link History}.
   * 
   * @return the list of {@link Creator}s for this {@link History}.
   */
  public List<Creator> getListOfCreators() {
    return listOfCreators;
  }

  /**
   * Get the list of modifiedDate objects in this {@link History}.
   * 
   * @return the list of modifiedDate for this {@link History}.
   */
  public List<Date> getListOfModifiedDates() {
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
   * @libsbml.deprecated use {@link #getCreatorCount()}
   */
  public int getNumCreators() {
    return getCreatorCount();
  }

  /**
   * Get the number of {@link Creator} objects in this {@link History}.
   * 
   * @return the number of {@link Creator}s in this {@link History}.
   */
  public int getCreatorCount() {
    return isSetListOfCreators() ? listOfCreators.size() : 0;
  }

  /**
   * Get the number of ModifiedDate objects in this {@link History}.
   * 
   * @return the number of ModifiedDates in this {@link History}.
   * @libsbml.deprecated use {@link #getModifiedDateCount()}
   */
  public int getNumModifiedDates() {
    return getModifiedDateCount();
  }

  /**
   * Get the number of ModifiedDate objects in this {@link History}.
   * 
   * @return the number of ModifiedDates in this {@link History}.
   */
  public int getModifiedDateCount() {
    return isSetListOfModification() ? listOfModification.size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 811;
    int hashCode = super.hashCode();
    if (isSetModifiedDate()) {
      hashCode += prime * getModifiedDate().hashCode();
    }
    if (isSetCreatedDate()) {
      hashCode += prime * getCreatedDate().hashCode();
    }
    return hashCode;
  }

  /**
   * Checks whether at least one attribute has been set for this
   * {@link History}.
   * 
   * @return {@code true} if at least one of the possible attributes is set, i.e., not
   *         null:
   *         <ul>
   *         <li> {@link #creation} date</li>
   *         <li> {@link #listOfCreators} is not null and contains at least one
   *         element</li>
   *         <li>
   *         {@link #listOfModification} is not null and contains at least one
   *         element.</li>
   *         <li> {@link #modified} is not {@code null}.</li>
   *         </ul>
   */
  public boolean isEmpty() {
    return !isSetCreatedDate() && !isSetListOfCreators()
        && !isSetListOfModification() && !isSetModifiedDate();

  }

  /**
   * Predicate returning true or false depending on whether this
   * {@link History}'s createdDate has been set.
   * 
   * @return {@code true} if the createdDate of this {@link History} has been
   *         set, {@code false} otherwise.
   */
  public boolean isSetCreatedDate() {
    return creation != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfCreators() {
    return listOfCreators != null && listOfCreators.size() > 0;
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfModification() {
    return listOfModification != null && listOfModification.size() > 0;
  }

  /**
   * Predicate returning true or false depending on whether this
   * {@link History}'s modifiedDate has been set.
   * 
   * @return {@code true} if the modifiedDate of this {@link History} has been
   *         set, {@code false} otherwise.
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
   * @return {@code true} if the XML attribute is known by this {@link History}.
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
   *If there is no i<sup>th</sup> {@link Creator}, it returns {@code null}.
   * 
   * @param i
   * @return the {@link Creator} removed from the {@link #listOfCreators}.
   */
  public Creator removeCreator(int i) {
    Creator c = listOfCreators.remove(i);
    if (c != null) {
      firePropertyChange(TreeNodeChangeEvent.creator, c, null);
    }
    return c;
  }

  /**
   * If there is no i<sup>th</sup> modified {@link Date}, it returns {@code null}.
   * 
   * @param i
   * @return the modified {@link Date} removed from the listOfModification.
   */
  public Date removeModifiedDate(int i) {
    if (i < listOfModification.size()) {
      if (i == listOfModification.size() - 1) {
        if (i - 2 >= 0) {
          modified = listOfModification.get(i - 2);
        } else {
          modified = null;
        }
      }
      Date d = listOfModification.remove(i);
      if (d != null) {
        firePropertyChange(TreeNodeChangeEvent.modifiedDate, d, null);
      }
      return d;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format("No modified date {0,number,integer} available.", i));
  }

  /**
   * Sets the createdDate.
   * 
   * @param date
   *            a {@link Date} object representing the date the {@link History} was
   *            created.
   */
  public void setCreatedDate(Date date) {
    Date oldValue = creation;
    creation = date;
    firePropertyChange(TreeNodeChangeEvent.createdDate, oldValue, date);
  }

  /**
   * Sets the modifiedDate.
   * 
   * @param date
   *            a {@link Date} object representing the date the {@link History} was
   *            modified.
   */
  public void setModifiedDate(Date date) {
    Date oldValue = modified;
    boolean success = listOfModification.add(date);
    modified = date;
    if (success) {
      firePropertyChange(TreeNodeChangeEvent.modifiedDate, oldValue, modified);
    }
  }

  /**
   * Sets the created of this {@link History} to {@code null}.
   */
  public void unsetCreatedDate() {
    if (creation != null) {
      Date oldValue = creation;
      creation = null;
      firePropertyChange(TreeNodeChangeEvent.createdDate, oldValue, creation);
    }
  }

  /**
   * Sets the created of this {@link History} to {@code null}.
   */
  public void unsetListOfModifiedDates() {
    if (modified != null) {
      modified = null;
    }
    if (listOfModification != null) {
      for (Date date : listOfModification) {
        firePropertyChange(TreeNodeChangeEvent.modifiedDate, date, null);
      }
      listOfModification = null;
    }
  }

}
