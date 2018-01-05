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
package org.sbml.jsbml.ext.fbc;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * An integral component in a complete description of a steady-state model is the so-called
 * 'objective function' which generally consists of a linear combination of model variables
 * (fluxes) and a sense (direction). In the FBC package this concept is succinctly captured
 * in the {@link Objective} class.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class Objective extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * 
   * @author Andreas Dr&auml;ger
   * @since 1.0
   */
  public static enum Type {
    /**
     * 
     */
    MAXIMIZE("maximize"),
    /**
     * 
     */
    MINIMIZE("minimize");

    /**
     * 
     * @param value
     * @return
     */
    public static Type fromString(String value)
    {
      if (value == null)
      {
        throw new IllegalArgumentException();
      }

      for (Type v : values())
      {
        if (value.equalsIgnoreCase(v.sbmlName))
        {
          return v;
        }
      }
      throw new IllegalArgumentException();

    }

    /**
     * 
     */
    private String sbmlName;

    /**
     * 
     * @param name
     */
    private Type(String name) {
      sbmlName = name;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
      return sbmlName;
    }


  }

  /**
   * 
   */
  private static final long serialVersionUID = -3466570440778373634L;
  /**
   * 
   */
  private ListOf<FluxObjective> listOfFluxObjectives;

  /**
   * 
   */
  private Type type;


  /**
   * Creates an Objective instance
   */
  public Objective() {
    super();
    initDefaults();
  }

  /**
   * Creates a Objective instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public Objective(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Clone constructor
   * @param obj
   */
  public Objective(Objective obj) {
    super(obj);

    if (obj.isSetListOfFluxObjectives()) {
      setListOfFluxObjectives(obj.getListOfFluxObjectives().clone());
    }
    if (obj.isSetType()) {
      setType(obj.getType());
    }
  }

  /**
   * Creates a Objective instance with an id.
   * 
   * @param id
   */
  public Objective(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a Objective instance with an id, level, and version.
   * 
   * @param id
   * @param level
   * @param version
   */
  public Objective(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a Objective instance with an id, name, level, and version.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Objective(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(FBCConstants.MIN_SBML_LEVEL),
      Integer.valueOf(FBCConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Adds a new {@link FluxObjective} to the listOfFluxObjectives.
   * <p>The listOfFluxObjectives is initialized if necessary.
   *
   * @param fluxObjective the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   */
  public boolean addFluxObjective(FluxObjective fluxObjective) {
    return getListOfFluxObjectives().add(fluxObjective);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Objective clone() {
    return new Objective(this);
  }

  /**
   * Creates a new {@link FluxObjective} element and adds it to the ListOfFluxObjectives list
   *
   * @return a new {@link FluxObjective} element
   */
  public FluxObjective createFluxObjective() {
    return createFluxObjective(null);
  }

  /**
   * Creates a new {@link FluxObjective} element and adds it to the ListOfFluxObjectives list
   *
   * @param id the id for the new {@link FluxObjective}
   * @return the newly created {@link FluxObjective} element or {@code null} if
   *         the operation failed.
   */
  public FluxObjective createFluxObjective(String id) {
    return createFluxObjective(id, null);
  }

  /**
   * @param id
   * @param name
   * @return the newly created {@link FluxObjective} element or {@code null} if
   *         the operation failed.
   */
  public FluxObjective createFluxObjective(String id, String name) {
    return createFluxObjective(id, name, Double.NaN, (Reaction) null);
  }

  /**
   * 
   * @param id
   * @param name the name of the flux objective to be created, can be {@code null}.
   * @param coefficient
   * @param reaction
   * @return the newly created {@link FluxObjective} element or {@code null} if
   *         the operation failed.
   */
  public FluxObjective createFluxObjective(String id, String name, double coefficient,
    Reaction reaction) {
    return createFluxObjective(id, name, coefficient, reaction != null ? reaction.getId() : null);
  }


  /**
   * @param id
   * @param name
   * @param coefficient
   * @param rId
   * @return the newly created {@link FluxObjective} element or {@code null} if
   *         the operation failed.
   */
  public FluxObjective createFluxObjective(String id, String name, double coefficient,
    String rId) {
    FluxObjective fluxObjective = new FluxObjective(id, name, getLevel(), getVersion());
    if (!Double.isNaN(coefficient)) {
      fluxObjective.setCoefficient(coefficient);
    }
    if (rId != null) {
      fluxObjective.setReaction(rId);
    }
    return getListOfFluxObjectives().add(fluxObjective) ? fluxObjective : null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Objective other = (Objective) obj;
    if (listOfFluxObjectives == null) {
      if (other.listOfFluxObjectives != null) {
        return false;
      }
    } else if (!listOfFluxObjectives.equals(other.listOfFluxObjectives)) {
      return false;
    }
    if (type != other.type) {
      return false;
    }
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
    if (isSetListOfFluxObjectives()) {
      if (pos == index) {
        return listOfFluxObjectives;
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
    return isSetListOfFluxObjectives() ? 1 : 0;
  }

  /**
   * @return
   */
  public int getFluxObjectiveCount() {
    return listOfFluxObjectives == null ? 0 : listOfFluxObjectives.size();
  }

  /**
   * Returns the listOfFluxObjectives
   * 
   * @return the listOfFluxObjectives
   */
  public ListOf<FluxObjective> getListOfFluxObjectives() {
    if (!isSetListOfFluxObjectives()) {
      listOfFluxObjectives = new ListOf<FluxObjective>(getLevel(), getVersion());
      listOfFluxObjectives.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'fbc'
      listOfFluxObjectives.setPackageName(null);
      listOfFluxObjectives.setPackageName(FBCConstants.shortLabel);
      listOfFluxObjectives.setSBaseListType(ListOf.Type.other);
      listOfFluxObjectives.setOtherListName(FBCConstants.listOfFluxObjectives);

      registerChild(listOfFluxObjectives);
    }
    return listOfFluxObjectives;
  }

  /**
   * 
   * @return
   * @deprecated use {@link #getFluxObjectiveCount()}
   */
  @Deprecated
  public int getNumFluxObjectives() {
    return getFluxObjectiveCount();
  }

  /**
   * Returns the type.
   * 
   * @return the type
   */
  public Type getType() {
    return type;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2383;
    int result = super.hashCode();
    result = prime * result
        + ((listOfFluxObjectives == null) ? 0 : listOfFluxObjectives.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  /**
   * 
   */
  private void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfFluxObjectives() {
    return (listOfFluxObjectives != null) && (listOfFluxObjectives.size() > 0);
  }

  /**
   * Returns whether type is set
   *
   * @return whether type is set
   */
  public boolean isSetType() {
    return type != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals("type")) {
        setType(value);
      } else {
        isAttributeRead = false;
      }

    }

    return isAttributeRead;
  }

  /**
   * Removes an element from the listOfFluxObjectives.
   *
   * @param fluxObjective the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see java.util.List#remove(Object)
   */
  public boolean removeFluxObjective(FluxObjective fluxObjective) {
    if (isSetListOfFluxObjectives()) {
      return getListOfFluxObjectives().remove(fluxObjective);
    }
    return false;
  }


  /**
   * Removes an element from the listOfFluxObjectives at the given index.
   *
   * @param i the index where to remove the {@link FluxObjective}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeFluxObjective(int i) {
    if (!isSetListOfFluxObjectives()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfFluxObjectives().remove(i);
  }


  /**
   * Removes an element from the listOfFluxObjectives with the given id.
   *
   * @param id the id of the {@link FluxObjective} to remove.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeFluxObjective(String id) {
    getListOfFluxObjectives().removeFirst(new NameFilter(id));
  }

  /**
   * This list houses the actual fluxes to partake in this objective function,
   * which are encoded by {@link FluxObjective}
   * 
   * Sets the given {@code ListOf<FluxObjective>}. If listOfFluxObjectives
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfFluxObjectives
   */
  public void setListOfFluxObjectives(ListOf<FluxObjective> listOfFluxObjectives) {
    unsetListOfFluxObjectives();
    this.listOfFluxObjectives = listOfFluxObjectives;

    if (listOfFluxObjectives != null) {
      listOfFluxObjectives.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'fbc'
      listOfFluxObjectives.setPackageName(null);
      listOfFluxObjectives.setPackageName(FBCConstants.shortLabel);
      listOfFluxObjectives.setSBaseListType(ListOf.Type.other);
      listOfFluxObjectives.setOtherListName(FBCConstants.listOfFluxObjectives);
      
      registerChild(this.listOfFluxObjectives);
    }
  }


  /**
   * The required type attribute contains a {@link Type} which represents the sense
   * of the optimality constraint and can take one of two values, minimize or maximize.
   * Sets the type from a {@link String}.
   * 
   * @param type the type to set
   */
  public void setType(String type) {
    try
    {
      setType(Type.fromString(type));
    }
    catch (Exception e)
    {
      throw new SBMLException("Could not recognized the value '" + type
        + "' for the attribute " + FBCConstants.type
        + " on the 'objective' element.");
    }
  }

  /**
   * Sets the value of type
   * @param type
   * 
   */
  public void setType(Type type) {
    Type oldType = this.type;
    this.type = type;
    firePropertyChange(FBCConstants.type, oldType, this.type);
  }


  /**
   * Unsets the {@code listOfFluxObjectives}.
   * 
   * <p>Returns {@code true}, if listOfFluxObjectives contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfFluxObjectives contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfFluxObjectives() {
    if (isSetListOfFluxObjectives()) {
      ListOf<FluxObjective> oldFluxObjectives = listOfFluxObjectives;
      listOfFluxObjectives = null;
      oldFluxObjectives.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable type
   *
   * @return {@code true}, if type was set before,
   *         otherwise {@code false}
   */
  public boolean unsetType() {
    if (isSetType()) {
      Type oldType = type;
      type = null;
      firePropertyChange(FBCConstants.type, oldType, type);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (type != null) {
      attributes.put(FBCConstants.shortLabel + ":type", getType().toString());
    }
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getName());
    }

    return attributes;
  }

}
