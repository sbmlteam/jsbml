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
package org.sbml.jsbml.ext.comp;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * {@link Submodel}s are instantiations of models contained within other models.
 * Submodel instances represent submodels contained within {@link CompModelPlugin}.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class Submodel extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2588930216065448311L;

  /**
   * 
   */
  private String modelRef;

  /**
   * 
   */
  private String timeConversionFactor;

  /**
   * 
   */
  private String extentConversionFactor;

  /**
   * 
   */
  private ListOf<Deletion> listOfDeletions;

  /**
   * Creates an Submodel instance
   */
  public Submodel() {
    super();
    initDefaults();
  }

  /**
   * Creates a Submodel instance with an id.
   * 
   * @param id the id
   */
  public Submodel(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a Submodel instance with a level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Submodel(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a Submodel instance with an id, level, and version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public Submodel(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a Submodel instance with an id, name, level, and version.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public Submodel(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(CompConstants.MIN_SBML_LEVEL),
      Integer.valueOf(CompConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public Submodel(Submodel obj) {
    super(obj);

    if (obj.isSetListOfDeletions()) {
      setListOfDeletions(obj.getListOfDeletions().clone());
    }
    if (obj.isSetModelRef()) {
      setModelRef(new String(obj.getModelRef()));
    }
    if (obj.isSetTimeConversionFactor()) {
      setTimeConversionFactor(new String(obj.getTimeConversionFactor()));
    }
    if (obj.isSetExtentConversionFactor()) {
      setExtentConversionFactor(new String(obj.getExtentConversionFactor()));
    }
  }

  /**
   * clones this class
   */
  @Override
  public Submodel clone() {
    return new Submodel(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = CompConstants.shortLabel;
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime
        * result
        + ((extentConversionFactor == null) ? 0
          : extentConversionFactor.hashCode());
    result = prime * result
        + ((listOfDeletions == null) ? 0 : listOfDeletions.hashCode());
    result = prime * result + ((modelRef == null) ? 0 : modelRef.hashCode());
    result = prime * result
        + ((timeConversionFactor == null) ? 0 : timeConversionFactor.hashCode());
    return result;
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
    Submodel other = (Submodel) obj;
    if (extentConversionFactor == null) {
      if (other.extentConversionFactor != null) {
        return false;
      }
    } else if (!extentConversionFactor.equals(other.extentConversionFactor)) {
      return false;
    }
    if (listOfDeletions == null) {
      if (other.listOfDeletions != null) {
        return false;
      }
    } else if (!listOfDeletions.equals(other.listOfDeletions)) {
      return false;
    }
    if (modelRef == null) {
      if (other.modelRef != null) {
        return false;
      }
    } else if (!modelRef.equals(other.modelRef)) {
      return false;
    }
    if (timeConversionFactor == null) {
      if (other.timeConversionFactor != null) {
        return false;
      }
    } else if (!timeConversionFactor.equals(other.timeConversionFactor)) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isIdMandatory() {
    return true;
  }



  /**
   * Returns the value of modelRef
   *
   * @return the value of modelRef
   */
  public String getModelRef() {

    if (isSetModelRef()) {
      return modelRef;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(CompConstants.modelRef, this);
  }

  /**
   * Returns whether modelRef is set
   *
   * @return whether modelRef is set
   */
  public boolean isSetModelRef() {
    return modelRef != null;
  }

  /**
   * Sets the value of the required modelRef
   * 
   * <p>The whole purpose of a {@link Submodel} object
   * is to instantiate a model definition, which is
   * to say, either a {@link Model} object defined
   * in the same enclosing SBML document, or a model
   * defined in an external SBML document. The modelRef
   * attribute is the means by which that model is
   * identified. This required attribute must refer to
   * the identifier of a Model or {@link ExternalModelDefinition}
   * object within the enclosing SBML document (i.e., in the
   * model namespace of the document).</p>
   * 
   * @param modelRef the value of modelRef
   */
  public void setModelRef(String modelRef) {
    String oldModelRef = this.modelRef;
    this.modelRef = modelRef;
    firePropertyChange(CompConstants.modelRef, oldModelRef, this.modelRef);
  }

  /**
   * Unsets the variable modelRef
   *
   * @return {@code true}, if modelRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetModelRef() {
    if (isSetModelRef()) {
      String oldModelRef = modelRef;
      modelRef = null;
      firePropertyChange(CompConstants.modelRef, oldModelRef, modelRef);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of timeConversionFactor
   *
   * @return the value of timeConversionFactor
   */
  public String getTimeConversionFactor() {

    if (isSetTimeConversionFactor()) {
      return timeConversionFactor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(CompConstants.timeConversionFactor, this);
  }

  /**
   * Returns whether timeConversionFactor is set
   *
   * @return whether timeConversionFactor is set
   */
  public boolean isSetTimeConversionFactor() {
    return timeConversionFactor != null;
  }

  /**
   * Sets the value of the optional timeConversionFactor
   * 
   * <p>The optional timeConversionFactor attribute is provided to allow
   * references and assumptions about the scale of time in the {@link Submodel}
   * to be converted to the scale of time in the containing model. If set, it
   * must be the identifier of a {@link Parameter} object in the parent {@link Model}
   * object. The units of that {@link Parameter} object, if present, should reduce to
   * being dimensionless, and the {@link Parameter} must be constant.</p>
   * 
   * @param timeConversionFactor the value of timeConversionFactor
   */
  public void setTimeConversionFactor(String timeConversionFactor) {
    String oldTimeConversionFactor = this.timeConversionFactor;
    this.timeConversionFactor = timeConversionFactor;
    firePropertyChange(CompConstants.timeConversionFactor, oldTimeConversionFactor, this.timeConversionFactor);
  }

  /**
   * Unsets the variable timeConversionFactor
   *
   * @return {@code true}, if timeConversionFactor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTimeConversionFactor() {
    if (isSetTimeConversionFactor()) {
      String oldTimeConversionFactor = timeConversionFactor;
      timeConversionFactor = null;
      firePropertyChange(CompConstants.timeConversionFactor, oldTimeConversionFactor, timeConversionFactor);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of extentConversionFactor
   *
   * @return the value of extentConversionFactor
   */
  public String getExtentConversionFactor() {

    if (isSetExtentConversionFactor()) {
      return extentConversionFactor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(CompConstants.extentConversionFactor, this);
  }

  /**
   * Returns whether extentConversionFactor is set
   *
   * @return whether extentConversionFactor is set
   */
  public boolean isSetExtentConversionFactor() {
    return extentConversionFactor != null;
  }

  /**
   * Sets the value of the optional extentConversionFactor
   * 
   * <p>The optional extentConversionFactor attribute is provided to allow
   * references and assumptions about the scale of a model's reaction
   * extent to be converted to the scale of the containing model. If set,
   * it must be the identifier of a {@link Parameter} object in the
   * parent {@link Model} object. The units of that {@link Parameter}
   * object, if present, should reduce to being dimensionless, and the
   * {@link Parameter} must be constant.</p>
   * 
   * @param extentConversionFactor the value of the optional extentConversionFactor
   */
  public void setExtentConversionFactor(String extentConversionFactor) {
    String oldExtentConversionFactor = this.extentConversionFactor;
    this.extentConversionFactor = extentConversionFactor;
    firePropertyChange(CompConstants.extentConversionFactor, oldExtentConversionFactor, this.extentConversionFactor);
  }

  /**
   * Unsets the variable extentConversionFactor
   *
   * @return {@code true}, if extentConversionFactor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetExtentConversionFactor() {
    if (isSetExtentConversionFactor()) {
      String oldExtentConversionFactor = extentConversionFactor;
      extentConversionFactor = null;
      firePropertyChange(CompConstants.extentConversionFactor, oldExtentConversionFactor, extentConversionFactor);
      return true;
    }
    return false;
  }


  /**
   * Returns {@code true}, if listOfDeletions contains at least one element.
   *
   * @return {@code true}, if listOfDeletions contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfDeletions() {
    if ((listOfDeletions == null) || listOfDeletions.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the number of {@link Deletion} objects in this {@link Submodel}.
   * 
   * @return the number of {@link Deletion} objects in this {@link Submodel}.
   */
  public int getDeletionCount() {
    if (!isSetListOfDeletions()) {
      return 0;
    }

    return getListOfDeletions().size();
  }


  /**
   * Returns the listOfDeletions. Creates it if it is not already existing.
   *
   * @return the listOfDeletions
   */
  public ListOf<Deletion> getListOfDeletions() {
    if (!isSetListOfDeletions()) {
      listOfDeletions = new ListOf<Deletion>(getLevel(), getVersion());
      listOfDeletions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfDeletions.setPackageName(null);
      listOfDeletions.setPackageName(CompConstants.shortLabel);
      listOfDeletions.setSBaseListType(ListOf.Type.other);
      listOfDeletions.setOtherListName(CompConstants.listOfDeletions);
      
      registerChild(listOfDeletions);
    }
    return listOfDeletions;
  }

  /**
   * Sets the given {@code ListOf<Deletion>}. 
   * 
   * <p>If listOfDeletions
   * was defined before and contains some elements, they are all unset.</p>
   * 
   * <p>This list specifies objects to be removed from the submodel when
   * composing the overall model. (The "removal" is mathematical and
   * conceptual, not physical.)</p>
   *
   * @param listOfDeletions the list of {@link Deletion}s
   */
  public void setListOfDeletions(ListOf<Deletion> listOfDeletions) {
    unsetListOfDeletions();
    this.listOfDeletions = listOfDeletions;

    if ((listOfDeletions != null)) {
      listOfDeletions.setSBaseListType(ListOf.Type.other);
      listOfDeletions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfDeletions.setPackageName(null);
      listOfDeletions.setPackageName(CompConstants.shortLabel);
      listOfDeletions.setOtherListName(CompConstants.listOfDeletions);
    }

    registerChild(this.listOfDeletions);
  }

  /**
   * Returns {@code true}, if listOfDeletions contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfDeletions contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfDeletions() {
    if (isSetListOfDeletions()) {
      ListOf<Deletion> oldDeletions = listOfDeletions;
      listOfDeletions = null;
      oldDeletions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link Deletion} to the listOfDeletions.
   * <p>The listOfDeletions is initialized if necessary.
   *
   * @param deletion the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addDeletion(Deletion deletion) {
    return getListOfDeletions().add(deletion);
  }

  /**
   * Removes an element from the listOfDeletions.
   *
   * @param deletion the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeDeletion(Deletion deletion) {
    if (isSetListOfDeletions()) {
      return getListOfDeletions().remove(deletion);
    }
    return false;
  }

  /**
   * Removes an element from the listOfDeletions at the given index.
   *
   * @param i the index where to remove the {@link Deletion}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeDeletion(int i) {
    if (!isSetListOfDeletions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfDeletions().remove(i);
  }

  /**
   * Removes an element from the listOfDeletions with the given id.
   *
   * @param id the id of the {@link Deletion} element to remove.
   */
  public void removeDeletion(String id) {
    getListOfDeletions().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new Deletion element and adds it to the ListOfDeletions list
   * 
   * @return a new {@link Deletion} element
   */
  public Deletion createDeletion() {
    return createDeletion(null);
  }

  /**
   * Creates a new {@link Deletion} element and adds it to the ListOfDeletions list
   * 
   * @param id the id
   * @return a new {@link Deletion} element
   */
  public Deletion createDeletion(String id) {
    Deletion deletion = new Deletion(id, getLevel(), getVersion());
    addDeletion(deletion);
    return deletion;
  }



  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetListOfDeletions()) {
      count++;
    }

    return count;
  }

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

    if (isSetListOfDeletions()) {
      if (pos == index) {
        return getListOfDeletions();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(CompConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(CompConstants.shortLabel + ":name", getName());
    }
    if (isSetModelRef()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.modelRef, getModelRef());
    }
    if (isSetTimeConversionFactor()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.timeConversionFactor, getTimeConversionFactor());
    }
    if (isSetExtentConversionFactor()) {
      attributes.put(CompConstants.shortLabel + ":" + CompConstants.extentConversionFactor, getExtentConversionFactor());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(CompConstants.modelRef)) {
        setModelRef(value);
      }
      else if (attributeName.equals(CompConstants.timeConversionFactor)) {
        setTimeConversionFactor(value);
      }
      else if (attributeName.equals(CompConstants.extentConversionFactor)) {
        setExtentConversionFactor(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

}
