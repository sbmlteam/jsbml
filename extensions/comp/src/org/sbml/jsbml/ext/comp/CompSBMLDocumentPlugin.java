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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * Allows SBML documents to contain more than one model.
 * 
 * <p>The top level of an {@link SBMLDocument} is a container whose
 * structure is defined by the object class SBML in the SBML Level
 * 3 Version 1 Core specification. In Level 3 Core, this container
 * can contain only one object of class {@link Model}. The
 * Hierarchical Model Composition package allows SBML documents to
 * contain more than one model.</ps>
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class CompSBMLDocumentPlugin extends CompSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6693188330172643491L;

  /**
   * 
   */
  private ListOf<ModelDefinition> listOfModelDefinitions;

  /**
   * 
   */
  private ListOf<ExternalModelDefinition> listOfExternalModelDefinitions;

  /**
   * Creates a new {@link CompSBMLDocumentPlugin} instance.
   * 
   * @param doc the core {@link SBMLDocument} to is extended
   */
  public CompSBMLDocumentPlugin(SBMLDocument doc) {
    super(doc);
  }

  /**
   * Creates a new {@link CompSBMLDocumentPlugin} instance.
   * 
   * @param compSBMLDocumentPlugin the instance to clone
   */
  public CompSBMLDocumentPlugin(CompSBMLDocumentPlugin compSBMLDocumentPlugin)
  {
    super(compSBMLDocumentPlugin);

    if (compSBMLDocumentPlugin.isSetListOfModelDefinitions()) {
      setListOfModelDefinitions(compSBMLDocumentPlugin.getListOfModelDefinitions().clone());
    }
    if (compSBMLDocumentPlugin.isSetListOfExternalModelDefinitions()) {
      setListOfExternalModelDefinitions(compSBMLDocumentPlugin.getListOfExternalModelDefinitions().clone());
    }

  }

  /**
   * Returns the n-th {@link ExternalModelDefinition} object in this {@link CompSBMLDocumentPlugin}.
   * 
   * @param index an index
   * @return the {@link ExternalModelDefinition} with the given index if it exists.
   * @throws IndexOutOfBoundsException if the index is out of range: (index < 0 || index >= size())
   */
  public ExternalModelDefinition getExternalModelDefinition(int index) {
    return getListOfExternalModelDefinitions().get(index);
  }

  /**
   * Returns a {@link ExternalModelDefinition} element that has the given 'id' within
   * this {@link CompSBMLDocumentPlugin} or {@code null} if no such element can be found.
   * 
   * @param id
   *        an id indicating a {@link ExternalModelDefinition} element of the
   *        {@link CompSBMLDocumentPlugin}.
   * @return a {@link ExternalModelDefinition} element of the {@link CompSBMLDocumentPlugin} that has
   *         the given 'id' as id or {@code null} if no element with this
   *         'id' can be found.
   */
  public ExternalModelDefinition getExternalModelDefinition(String id) {
    return getListOfExternalModelDefinitions().get(id);
  }



  /**
   * Returns the number of {@link ExternalModelDefinition} objects in this {@link CompSBMLDocumentPlugin}.
   * 
   * @return the number of {@link ExternalModelDefinition} objects in this {@link CompSBMLDocumentPlugin}.
   */
  public int getExternalModelDefinitionCount() {
    if (!isSetListOfExternalModelDefinitions()) {
      return 0;
    }

    return getListOfExternalModelDefinitions().size();
  }

  /**
   * Returns the listOfExternalModelDefinitions. Creates it if it is not already existing.
   *
   * @return the listOfExternalModelDefinitions
   */
  public ListOf<ExternalModelDefinition> getListOfExternalModelDefinitions() {
    if (!isSetListOfExternalModelDefinitions()) {
      if (extendedSBase != null) {
        listOfExternalModelDefinitions = new ListOf<ExternalModelDefinition>(extendedSBase.getLevel(),
            extendedSBase.getVersion());
      } else {
        listOfExternalModelDefinitions = new ListOf<ExternalModelDefinition>();
      }
      listOfExternalModelDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfExternalModelDefinitions.setPackageName(null);
      listOfExternalModelDefinitions.setPackageName(CompConstants.shortLabel);
      listOfExternalModelDefinitions.setSBaseListType(ListOf.Type.other);
      listOfExternalModelDefinitions.setOtherListName(CompConstants.listOfExternalModelDefinitions);

      if (extendedSBase != null) {
        extendedSBase.registerChild(listOfExternalModelDefinitions);
      }
    }
    return listOfExternalModelDefinitions;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#clone()
   */
  @Override
  public CompSBMLDocumentPlugin clone() {
    return new CompSBMLDocumentPlugin(this);
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3343;
    int result = super.hashCode();
    result = prime
        * result
        + ((listOfExternalModelDefinitions == null) ? 0
          : listOfExternalModelDefinitions.hashCode());
    result = prime
        * result
        + ((listOfModelDefinitions == null) ? 0
          : listOfModelDefinitions.hashCode());
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
    CompSBMLDocumentPlugin other = (CompSBMLDocumentPlugin) obj;
    if (listOfExternalModelDefinitions == null) {
      if (other.listOfExternalModelDefinitions != null) {
        return false;
      }
    } else if (!listOfExternalModelDefinitions.equals(other.listOfExternalModelDefinitions)) {
      return false;
    }
    if (listOfModelDefinitions == null) {
      if (other.listOfModelDefinitions != null) {
        return false;
      }
    } else if (!listOfModelDefinitions.equals(other.listOfModelDefinitions)) {
      return false;
    }
    return true;
  }

  /**
   * Returns {@code true}, if listOfExternalModelDefinitions contains at least one element.
   *
   * @return {@code true}, if listOfExternalModelDefinitions contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfExternalModelDefinitions() {
    if ((listOfExternalModelDefinitions == null) || listOfExternalModelDefinitions.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * The listOfExternamModelDefinitions is an optional element of {@link CompSBMLDocumentPlugin}
   * and defines external SBML document references within the {@link ExternalModelDefinition}
   * object.
   * 
   * Sets the given {@code ListOf<ExternalModelDefinition>}. If listOfExternalModelDefinitions
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfExternalModelDefinitions the list of {@link ExternalModelDefinition}s
   */
  public void setListOfExternalModelDefinitions(ListOf<ExternalModelDefinition> listOfExternalModelDefinitions) {
    unsetListOfExternalModelDefinitions();
    this.listOfExternalModelDefinitions = listOfExternalModelDefinitions;

    if (listOfExternalModelDefinitions != null) {
      listOfExternalModelDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfExternalModelDefinitions.setPackageName(null);
      listOfExternalModelDefinitions.setPackageName(CompConstants.shortLabel);
      listOfExternalModelDefinitions.setSBaseListType(ListOf.Type.other);
      listOfExternalModelDefinitions.setOtherListName(CompConstants.listOfExternalModelDefinitions);
    }
    if (extendedSBase != null) {
      extendedSBase.registerChild(this.listOfExternalModelDefinitions);
    }
  }

  /**
   * Returns {@code true}, if listOfExternalModelDefinitions contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfExternalModelDefinitions contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfExternalModelDefinitions() {
    if (isSetListOfExternalModelDefinitions()) {
      ListOf<ExternalModelDefinition> oldExternalModelDefinitions = listOfExternalModelDefinitions;
      listOfExternalModelDefinitions = null;
      oldExternalModelDefinitions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link ExternalModelDefinition} to the listOfExternalModelDefinitions.
   * <p>The listOfExternalModelDefinitions is initialized if necessary.
   *
   * @param externalModelDefinition the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addExternalModelDefinition(ExternalModelDefinition externalModelDefinition) {

    // TODO - the externalModelDefinition id is in the main SId namespace, so would need to be registered in the main model !

    return getListOfExternalModelDefinitions().add(externalModelDefinition);
  }

  /**
   * Removes an element from the listOfExternalModelDefinitions.
   *
   * @param externalModelDefinition the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeExternalModelDefinition(ExternalModelDefinition externalModelDefinition) {
    if (isSetListOfExternalModelDefinitions()) {
      return getListOfExternalModelDefinitions().remove(externalModelDefinition);
    }
    return false;
  }

  /**
   * Removes an element from the listOfExternalModelDefinitions at the given index.
   *
   * @param i the index where to remove the {@link ExternalModelDefinition}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeExternalModelDefinition(int i) {
    if (!isSetListOfExternalModelDefinitions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfExternalModelDefinitions().remove(i);
  }

  /**
   * Removes an element from the listOfExternalModelDefinitions with the given id.
   *
   * @param id the id of the {@link ExternalModelDefinition} to remove
   */
  public void removeExternalModelDefinition(String id) {
    getListOfExternalModelDefinitions().removeFirst(new NameFilter(id));
  }

  /**
   * Returns the n-th {@link ModelDefinition} object in this {@link CompSBMLDocumentPlugin}.
   * 
   * @param index an index
   * @return the {@link ModelDefinition} with the given index if it exists.
   * @throws IndexOutOfBoundsException if the index is out of range: (index < 0 || index >= size())
   */
  public ModelDefinition getModelDefinition(int index) {
    return getListOfModelDefinitions().get(index);
  }

  /**
   * Returns a {@link ModelDefinition} element that has the given 'id' within
   * this {@link CompSBMLDocumentPlugin} or {@code null} if no such element can be found.
   * 
   * @param id
   *        an id indicating a {@link ModelDefinition} element of the
   *        {@link CompSBMLDocumentPlugin}.
   * @return a {@link ModelDefinition} element of the {@link CompSBMLDocumentPlugin} that has
   *         the given 'id' as id or {@code null} if no element with this
   *         'id' can be found.
   */
  public ModelDefinition getModelDefinition(String id) {
    return getListOfModelDefinitions().get(id);
  }


  /**
   * Returns the number of {@link ModelDefinition} objects in this {@link CompSBMLDocumentPlugin}.
   * 
   * @return the number of {@link ModelDefinition} objects in this {@link CompSBMLDocumentPlugin}.
   */
  public int getModelDefinitionCount() {
    if (!isSetListOfModelDefinitions()) {
      return 0;
    }

    return getListOfModelDefinitions().size();
  }

  /**
   * Returns the number of {@link ExternalModelDefinition} of this {@link CompSBMLDocumentPlugin}.
   * 
   * @return the number of {@link ExternalModelDefinition} of this {@link CompSBMLDocumentPlugin}.
   * @libsbml.deprecated use {@link #getExternalModelDefinitionCount()}
   */
  public int getNumExternalModelDefinitions() {
    return getExternalModelDefinitionCount();
  }

  /**
   * Returns the number of {@link ModelDefinition} of this {@link CompSBMLDocumentPlugin}.
   * 
   * @return the number of {@link ModelDefinition} of this {@link CompSBMLDocumentPlugin}.
   * @libsbml.deprecated use {@link #getModelDefinitionCount()}
   */
  public int getNumModelDefinitions() {
    return getModelDefinitionCount();
  }

  /**
   * Creates a new ExternalModelDefinition element and adds it to the ListOfExternalModelDefinitions list
   * 
   * @return a new {@link ExternalModelDefinition} element
   */
  public ExternalModelDefinition createExternalModelDefinition() {
    return createExternalModelDefinition(null);
  }

  /**
   * Creates a new {@link ExternalModelDefinition} element and adds it to the ListOfExternalModelDefinitions list
   * 
   * @param id the id
   * @return a new {@link ExternalModelDefinition} element
   */
  public ExternalModelDefinition createExternalModelDefinition(String id) {
    ExternalModelDefinition externalModelDefinition = new ExternalModelDefinition(id);
    addExternalModelDefinition(externalModelDefinition);
    return externalModelDefinition;
  }


  /**
   * Returns {@code true}, if listOfModelDefinitions contains at least one element.
   *
   * @return {@code true}, if listOfModelDefinitions contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfModelDefinitions() {
    if ((listOfModelDefinitions == null) || listOfModelDefinitions.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the listOfModelDefinitions. Creates it if it is not already existing.
   *
   * @return the listOfModelDefinitions
   */
  public ListOf<ModelDefinition> getListOfModelDefinitions() {
    if (!isSetListOfModelDefinitions()) {
      if (extendedSBase != null) {
        listOfModelDefinitions = new ListOf<ModelDefinition>(extendedSBase.getLevel(),
            extendedSBase.getVersion());
      } else {
        listOfModelDefinitions = new ListOf<ModelDefinition>();
      }
      listOfModelDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfModelDefinitions.setPackageName(null);
      listOfModelDefinitions.setPackageName(CompConstants.shortLabel);
      listOfModelDefinitions.setSBaseListType(ListOf.Type.other);
      listOfModelDefinitions.setOtherListName(CompConstants.listOfModelDefinitions);

      if (extendedSBase != null) {
        extendedSBase.registerChild(listOfModelDefinitions);
      }
    }
    return listOfModelDefinitions;
  }

  /**
   * listOfModelDefinitions is an optional list of {@link CompSBMLDocumentPlugin}
   * which specifies {@link ModelDefinition}s for the SBML document.
   * 
   * Sets the given {@code ListOf<ModelDefinition>}. If listOfModelDefinitions
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfModelDefinitions the list of {@link ModelDefinition}s
   */
  public void setListOfModelDefinitions(ListOf<ModelDefinition> listOfModelDefinitions) {
    unsetListOfModelDefinitions();
    this.listOfModelDefinitions = listOfModelDefinitions;

    if (listOfModelDefinitions != null) {
      listOfModelDefinitions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfModelDefinitions.setPackageName(null);
      listOfModelDefinitions.setPackageName(CompConstants.shortLabel);
      listOfModelDefinitions.setSBaseListType(ListOf.Type.other);
      listOfModelDefinitions.setOtherListName(CompConstants.listOfModelDefinitions);
    }
    if (extendedSBase != null) {
      extendedSBase.registerChild(this.listOfModelDefinitions);
    }
  }

  /**
   * Returns {@code true}, if listOfModelDefinitions contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfModelDefinitions contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfModelDefinitions() {
    if (isSetListOfModelDefinitions()) {
      ListOf<ModelDefinition> oldModelDefinitions = listOfModelDefinitions;
      listOfModelDefinitions = null;
      oldModelDefinitions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link ModelDefinition} to the listOfModelDefinitions.
   * <p>The listOfModelDefinitions is initialized if necessary.
   *
   * @param modelDefinition the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addModelDefinition(ModelDefinition modelDefinition) {

    // TODO - the modelDefinition id is in the main SId namespace, so would need to be registered in the main model !

    return getListOfModelDefinitions().add(modelDefinition);
  }

  /**
   * Removes an element from the listOfModelDefinitions.
   *
   * @param modelDefinition the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeModelDefinition(ModelDefinition modelDefinition) {
    if (isSetListOfModelDefinitions()) {
      return getListOfModelDefinitions().remove(modelDefinition);
    }
    return false;
  }

  /**
   * Removes an element from the listOfModelDefinitions at the given index.
   *
   * @param i the index where to remove the {@link ModelDefinition}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeModelDefinition(int i) {
    if (!isSetListOfModelDefinitions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfModelDefinitions().remove(i);
  }

  /**
   * Removes an element from the listOfModelDefinitions with the given id.
   *
   * @param id the id of the {@link ModelDefinition} to remove
   */
  public void removeModelDefinition(String id) {
    getListOfModelDefinitions().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new ModelDefinition element and adds it to the ListOfModelDefinitions list
   * 
   * @return a new {@link ModelDefinition} element
   */
  public Model createModelDefinition() {
    return createModelDefinition(null);
  }

  /**
   * Creates a new {@link ModelDefinition} element and adds it to the ListOfModelDefinitions list
   * 
   * @param id the id
   * @return a new {@link ModelDefinition} element
   */
  public Model createModelDefinition(String id) {
    ModelDefinition modelDefinition = new ModelDefinition(id);
    addModelDefinition(modelDefinition);
    return modelDefinition;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value)
  {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }

    int pos = 0;
    if (isSetListOfExternalModelDefinitions()) {
      if (pos == childIndex) {
        return getListOfExternalModelDefinitions();
      }
      pos++;
    }
    if (isSetListOfModelDefinitions()) {
      if (pos == childIndex) {
        return getListOfModelDefinitions();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      childIndex, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfExternalModelDefinitions()) {
      count++;
    }
    if (isSetListOfModelDefinitions()) {
      count++;
    }

    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.CompSBasePlugin#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

}
