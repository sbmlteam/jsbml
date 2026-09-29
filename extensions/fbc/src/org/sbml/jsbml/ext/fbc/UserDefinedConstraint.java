/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * A {@link UserDefinedConstraint}, introduced in FBC version 3, constrains a
 * sum of {@link UserDefinedConstraintComponent}s (the products of a
 * coefficient and one or two variables) between a lower and an upper bound,
 * both given as the id of a {@link Parameter}.
 * 
 * @since 1.7
 */
public class UserDefinedConstraint extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6152870829425740271L;

  /**
   * The id of the {@link Parameter} that is the lower bound.
   */
  private String lowerBound;

  /**
   * The id of the {@link Parameter} that is the upper bound.
   */
  private String upperBound;

  /**
   * The components of this constraint.
   */
  private ListOf<UserDefinedConstraintComponent> listOfUserDefinedConstraintComponents;

  /**
   * Creates a {@link UserDefinedConstraint} instance.
   */
  public UserDefinedConstraint() {
    super();
    initDefaults();
  }

  /**
   * Creates a {@link UserDefinedConstraint} instance with a level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public UserDefinedConstraint(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a {@link UserDefinedConstraint} instance with an id.
   * 
   * @param id the id
   */
  public UserDefinedConstraint(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a {@link UserDefinedConstraint} instance with an id, level, and
   * version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public UserDefinedConstraint(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a {@link UserDefinedConstraint} instance with an id, name, level,
   * and version.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public UserDefinedConstraint(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(FBCConstants.MIN_SBML_LEVEL),
      Integer.valueOf(FBCConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public UserDefinedConstraint(UserDefinedConstraint obj) {
    super(obj);

    if (obj.isSetLowerBound()) {
      setLowerBound(obj.getLowerBound());
    }
    if (obj.isSetUpperBound()) {
      setUpperBound(obj.getUpperBound());
    }
    if (obj.isSetListOfUserDefinedConstraintComponents()) {
      setListOfUserDefinedConstraintComponents(obj.getListOfUserDefinedConstraintComponents().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public UserDefinedConstraint clone() {
    return new UserDefinedConstraint(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * Returns the id of the {@link Parameter} that is the lower bound.
   *
   * @return the lower bound, the empty {@link String} if it is not set.
   */
  public String getLowerBound() {
    return isSetLowerBound() ? lowerBound : "";
  }

  /**
   * Returns the {@link Parameter} that is the lower bound.
   *
   * @return the {@link Parameter} of the lower bound, {@code null} if the
   *         lower bound is not set or no parameter of the model.
   */
  public Parameter getLowerBoundInstance() {
    return isSetLowerBound() ? findParameter(lowerBound) : null;
  }

  /**
   * Returns whether the lower bound is set.
   *
   * @return whether the lower bound is set.
   */
  public boolean isSetLowerBound() {
    return lowerBound != null;
  }

  /**
   * Sets the id of the {@link Parameter} that is the lower bound.
   *
   * @param lowerBound the id of a {@link Parameter}.
   */
  public void setLowerBound(String lowerBound) {
    String oldLowerBound = this.lowerBound;
    this.lowerBound = lowerBound;
    firePropertyChange(FBCConstants.lowerBound, oldLowerBound, this.lowerBound);
  }

  /**
   * Unsets the lower bound.
   *
   * @return {@code true}, if the lower bound was set before, otherwise
   *         {@code false}.
   */
  public boolean unsetLowerBound() {
    if (isSetLowerBound()) {
      setLowerBound(null);
      return true;
    }
    return false;
  }

  /**
   * Returns the id of the {@link Parameter} that is the upper bound.
   *
   * @return the upper bound, the empty {@link String} if it is not set.
   */
  public String getUpperBound() {
    return isSetUpperBound() ? upperBound : "";
  }

  /**
   * Returns the {@link Parameter} that is the upper bound.
   *
   * @return the {@link Parameter} of the upper bound, {@code null} if the
   *         upper bound is not set or no parameter of the model.
   */
  public Parameter getUpperBoundInstance() {
    return isSetUpperBound() ? findParameter(upperBound) : null;
  }

  /**
   * Returns whether the upper bound is set.
   *
   * @return whether the upper bound is set.
   */
  public boolean isSetUpperBound() {
    return upperBound != null;
  }

  /**
   * Sets the id of the {@link Parameter} that is the upper bound.
   *
   * @param upperBound the id of a {@link Parameter}.
   */
  public void setUpperBound(String upperBound) {
    String oldUpperBound = this.upperBound;
    this.upperBound = upperBound;
    firePropertyChange(FBCConstants.upperBound, oldUpperBound, this.upperBound);
  }

  /**
   * Unsets the upper bound.
   *
   * @return {@code true}, if the upper bound was set before, otherwise
   *         {@code false}.
   */
  public boolean unsetUpperBound() {
    if (isSetUpperBound()) {
      setUpperBound(null);
      return true;
    }
    return false;
  }

  /**
   * @param id the id of a parameter.
   * @return the {@link Parameter} of the model with the given id, {@code null}
   *         if there is none.
   */
  private Parameter findParameter(String id) {
    Model model = getModel();
    return (model != null) ? model.getParameter(id) : null;
  }

  /**
   * Returns the list of {@link UserDefinedConstraintComponent}s, creates it
   * if it does not exist yet.
   *
   * @return the list of {@link UserDefinedConstraintComponent}s.
   */
  public ListOf<UserDefinedConstraintComponent> getListOfUserDefinedConstraintComponents() {
    if (listOfUserDefinedConstraintComponents == null) {
      listOfUserDefinedConstraintComponents = new ListOf<UserDefinedConstraintComponent>();
      initListOf(listOfUserDefinedConstraintComponents);
      registerChild(listOfUserDefinedConstraintComponents);
    }
    return listOfUserDefinedConstraintComponents;
  }

  /**
   * Makes the given list an fbc listOfUserDefinedConstraintComponents.
   *
   * @param listOf the list.
   */
  private static void initListOf(ListOf<UserDefinedConstraintComponent> listOf) {
    listOf.setPackageVersion(-1);
    // changing the ListOf package name from 'core' to 'fbc'
    listOf.setPackageName(null);
    listOf.setPackageName(FBCConstants.shortLabel);
    listOf.setSBaseListType(ListOf.Type.other);
    listOf.setOtherListName(FBCConstants.listOfUserDefinedConstraintComponents);
  }

  /**
   * Returns whether the list of {@link UserDefinedConstraintComponent}s is
   * set.
   *
   * @return whether the list of {@link UserDefinedConstraintComponent}s is
   *         set.
   */
  public boolean isSetListOfUserDefinedConstraintComponents() {
    return listOfUserDefinedConstraintComponents != null;
  }

  /**
   * Sets the list of {@link UserDefinedConstraintComponent}s. The components
   * of a list set before are removed.
   *
   * @param listOfUserDefinedConstraintComponents the list.
   */
  public void setListOfUserDefinedConstraintComponents(
    ListOf<UserDefinedConstraintComponent> listOfUserDefinedConstraintComponents) {
    unsetListOfUserDefinedConstraintComponents();
    this.listOfUserDefinedConstraintComponents = listOfUserDefinedConstraintComponents;
    if (listOfUserDefinedConstraintComponents != null) {
      initListOf(listOfUserDefinedConstraintComponents);
      registerChild(listOfUserDefinedConstraintComponents);
    }
  }

  /**
   * Unsets the list of {@link UserDefinedConstraintComponent}s.
   *
   * @return {@code true}, if the list was set before, otherwise {@code false}.
   */
  public boolean unsetListOfUserDefinedConstraintComponents() {
    if (isSetListOfUserDefinedConstraintComponents()) {
      ListOf<UserDefinedConstraintComponent> oldList = listOfUserDefinedConstraintComponents;
      listOfUserDefinedConstraintComponents = null;
      oldList.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a {@link UserDefinedConstraintComponent} to this constraint.
   *
   * @param component the component.
   * @return {@code true} (as specified by {@link java.util.Collection#add}).
   */
  public boolean addUserDefinedConstraintComponent(UserDefinedConstraintComponent component) {
    return getListOfUserDefinedConstraintComponents().add(component);
  }

  /**
   * Creates a {@link UserDefinedConstraintComponent} without id and adds it to
   * this constraint.
   *
   * @return the new {@link UserDefinedConstraintComponent}.
   */
  public UserDefinedConstraintComponent createUserDefinedConstraintComponent() {
    return createUserDefinedConstraintComponent(null);
  }

  /**
   * Creates a {@link UserDefinedConstraintComponent} and adds it to this
   * constraint.
   *
   * @param id the id of the component, can be {@code null}.
   * @return the new {@link UserDefinedConstraintComponent}, {@code null} if
   *         it could not be added.
   */
  public UserDefinedConstraintComponent createUserDefinedConstraintComponent(String id) {
    UserDefinedConstraintComponent component = new UserDefinedConstraintComponent(id, getLevel(), getVersion());
    return addUserDefinedConstraintComponent(component) ? component : null;
  }

  /**
   * Returns the {@link UserDefinedConstraintComponent} at the given index.
   *
   * @param i the index.
   * @return the {@link UserDefinedConstraintComponent} at the given index.
   * @throws IndexOutOfBoundsException if the list is not set or the index is
   *         out of bounds.
   */
  public UserDefinedConstraintComponent getUserDefinedConstraintComponent(int i) {
    if (!isSetListOfUserDefinedConstraintComponents()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return listOfUserDefinedConstraintComponents.get(i);
  }

  /**
   * Returns the number of {@link UserDefinedConstraintComponent}s.
   *
   * @return the number of {@link UserDefinedConstraintComponent}s.
   */
  public int getUserDefinedConstraintComponentCount() {
    return isSetListOfUserDefinedConstraintComponents() ? listOfUserDefinedConstraintComponents.size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetListOfUserDefinedConstraintComponents()) {
      if (pos == index) {
        return listOfUserDefinedConstraintComponents;
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfUserDefinedConstraintComponents()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2657;
    int result = super.hashCode();
    result = prime * result + ((lowerBound == null) ? 0 : lowerBound.hashCode());
    result = prime * result + ((upperBound == null) ? 0 : upperBound.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    // compares the children, including the list of components
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UserDefinedConstraint other = (UserDefinedConstraint) obj;
    return equal(lowerBound, other.lowerBound) && equal(upperBound, other.upperBound);
  }

  /**
   * @param a an object or {@code null}
   * @param b an object or {@code null}
   * @return whether both are {@code null} or equal.
   */
  private static boolean equal(Object a, Object b) {
    return (a == null) ? (b == null) : a.equals(b);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(FBCConstants.lowerBound)) {
        setLowerBound(value);
      } else if (attributeName.equals(FBCConstants.upperBound)) {
        setUpperBound(value);
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getName());
    }
    if (isSetLowerBound()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.lowerBound, getLowerBound());
    }
    if (isSetUpperBound()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.upperBound, getUpperBound());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getElementName());
    builder.append(" [id=").append(getId());
    builder.append(", name=").append(getName());
    builder.append(", lowerBound=").append(lowerBound);
    builder.append(", upperBound=").append(upperBound);
    builder.append(", userDefinedConstraintComponents=").append(getUserDefinedConstraintComponentCount());
    builder.append("]");
    return builder.toString();
  }

}
