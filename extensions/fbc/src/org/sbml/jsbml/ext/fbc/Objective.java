/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 27.10.2011
 */
public class Objective extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * 
   * @author Andreas Dr&auml;ger
   * @version $Rev$
   * @since 1.0
   * @date $Date$
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

    public static Type fromString(String value)
    {
      if (value == null)
      {
        throw new IllegalArgumentException();
      }

      for(Type v : values())
      {
        if(value.equalsIgnoreCase(v.sbmlName))
        {
          return v;
        }
      }
      throw new IllegalArgumentException();

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
   * @return {@code true} (as specified by {@link Collection.add})
   */
  public void addFluxObjective(FluxObjective fluxObjective) {
    getListOfFluxObjectives().add(fluxObjective);
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
   * @return a new {@link FluxObjective} element
   */
  public FluxObjective createFluxObjective(String id) {
    return createFluxObjective(id, null);
  }

  /**
   * 
   * @param id
   * @param name
   * @return
   */
  public FluxObjective createFluxObjective(String id, String name) {
    FluxObjective fluxObjective = new FluxObjective(id, name, getLevel(), getVersion());
    getListOfFluxObjectives().add(fluxObjective);
    return fluxObjective;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
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
      "Index {0,number,integer} >= {1,number,integer}",
      index, +Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetListOfFluxObjectives() ? 1 : 0;
  }

  /**
   * Returns the listOfFluxObjectives
   * 
   * @return the listOfFluxObjectives
   */
  public ListOf<FluxObjective> getListOfFluxObjectives() {
    if (!isSetListOfFluxObjectives()) {
      listOfFluxObjectives = new ListOf<FluxObjective>(getLevel(), getVersion());
      listOfFluxObjectives.setNamespace(FBCConstants.namespaceURI);
      listOfFluxObjectives.setSBaseListType(ListOf.Type.other);
      registerChild(listOfFluxObjectives);
    }
    return listOfFluxObjectives;
  }

  /**
   * Returns the type.
   * 
   * @return the type
   */
  public Type getType() {
    return type;
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(FBCConstants.namespaceURI);
  }

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
   */
  public void setType(Type type) {
    Type oldType = this.type;
    this.type = type;
    firePropertyChange(FBCConstants.type, oldType, this.type);
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


  /**
   * Returns whether type is set
   *
   * @return whether type is set
   */
  public boolean isSetType() {
    return type != null;
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


  /**
   * Sets the given {@code ListOf<FluxObjective>}. If listOfFluxObjectives
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfFluxObjectives
   */
  public void setListOfFluxObjectives(ListOf<FluxObjective> listOfFluxObjectives) {
    unsetListOfFluxObjectives();
    this.listOfFluxObjectives = listOfFluxObjectives;
    registerChild(this.listOfFluxObjectives);
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
   * Removes an element from the listOfFluxObjectives.
   *
   * @param fluxObjective the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
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
   * if the index is out of bound (index < 0 || index > list.size)
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
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removeFluxObjective(String id) {
    getListOfFluxObjectives().removeFirst(new NameFilter(id));
  }

}
