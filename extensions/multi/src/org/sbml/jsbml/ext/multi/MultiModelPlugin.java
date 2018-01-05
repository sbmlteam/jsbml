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
package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * In order to encode the structures needed to define and use multistate and multi-component
 * complexes, the element model is extended to be linked to a list of {@link MultiSpeciesType}s.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class MultiModelPlugin extends AbstractSBasePlugin {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return MultiConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return MultiConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public SBMLDocument getParent() {
    return (SBMLDocument) getExtendedSBase().getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBMLDocument getParentSBMLObject() {
    return getParent();
  }
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2875299722787282885L;
  /**
   * 
   */
  ListOf<MultiSpeciesType> listOfSpeciesTypes;

  /**
   * 
   * @param model
   */
  public MultiModelPlugin(Model model) {
    super(model);
  }

  /**
   * 
   * @param multiModel
   */
  public MultiModelPlugin(MultiModelPlugin multiModel) {
    super(multiModel);

    if (multiModel == null) {
      return;
    }
    if (multiModel.isSetListOfSpeciesTypes()) {
      setListOfSpeciesTypes(multiModel.getListOfSpeciesTypes().clone());
    }
  }

  @Override
  public MultiModelPlugin clone() {
    return new MultiModelPlugin(this);
  }

  /**
   * Returns the {@link Model}
   * 
   * @return the {@link Model}
   */
  public Model getModel() {
    return (Model) extendedSBase;
  }


  /**
   * Returns the listOfSpeciesTypes.
   * 
   * @return the listOfSpeciesTypes
   */
  public ListOf<MultiSpeciesType> getListOfSpeciesTypes() {
    if (listOfSpeciesTypes == null) {
      listOfSpeciesTypes = new ListOf<MultiSpeciesType>();
      listOfSpeciesTypes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypes.setPackageName(null);
      listOfSpeciesTypes.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypes.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypes.setOtherListName(MultiConstants.listOfSpeciesTypes);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfSpeciesTypes);
      }
    }

    return listOfSpeciesTypes;
  }

  /**
   * Adds a {@link MultiSpeciesType}.
   * 
   * @param speciesType the speciesType to add
   * @return
   */
  public boolean addSpeciesType(MultiSpeciesType speciesType) {
    return getListOfSpeciesTypes().add(speciesType);
  }

  /**
   * Creates a new {@link BindingSiteSpeciesType} inside this {@link MultiModelPlugin} and returns it.
   * <p>
   * 
   * @return the {@link BindingSiteSpeciesType} object created
   *         <p>
   * @see #addSpeciesType(MultiSpeciesType r)
   */
  public BindingSiteSpeciesType createBindingSiteSpeciesType() {
    return createBindingSiteSpeciesType(null);
  }

  /**
   * Creates a new {@link BindingSiteSpeciesType} inside this {@link MultiModelPlugin} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link BindingSiteSpeciesType} object created
   */
  public BindingSiteSpeciesType createBindingSiteSpeciesType(String id) {
    BindingSiteSpeciesType speciesType = new BindingSiteSpeciesType();
    speciesType.setId(id);
    addSpeciesType(speciesType);

    return speciesType;
  }

  /**
   * Creates a new {@link IntraSpeciesReaction} inside this {@link MultiModelPlugin} and returns it.
   * <p>
   * 
   * @return the {@link IntraSpeciesReaction} object created
   *         <p>
   * @see #createIntraSpeciesReaction(String)
   */
  public IntraSpeciesReaction createIntraSpeciesReaction() {
    return createIntraSpeciesReaction(null);
  }

  /**
   * Creates a new {@link IntraSpeciesReaction} inside this {@link MultiModelPlugin} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link IntraSpeciesReaction} object created
   */
  public IntraSpeciesReaction createIntraSpeciesReaction(String id) {
    IntraSpeciesReaction reaction = new IntraSpeciesReaction();
    reaction.setId(id);
    getModel().addReaction(reaction);

    return reaction; // TODO - put the IntraSpeciesReaction into a separate ListOf, either on the ModelPlugin or a new ListOf plugin ?
  }

  /**
   * Creates a new {@link MultiSpeciesType} inside this {@link MultiModelPlugin} and returns it.
   * <p>
   * 
   * @return the {@link MultiSpeciesType} object created
   *         <p>
   * @see #addSpeciesType(MultiSpeciesType r)
   */
  public MultiSpeciesType createSpeciesType() {
    return createSpeciesType(null);
  }

  /**
   * Creates a new {@link MultiSpeciesType} inside this {@link MultiModelPlugin} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link MultiSpeciesType} object created
   */
  public MultiSpeciesType createSpeciesType(String id) {
    MultiSpeciesType speciesType = new MultiSpeciesType();
    speciesType.setId(id);
    addSpeciesType(speciesType);

    return speciesType;
  }

  /**
   * Gets the ith {@link MultiSpeciesType}.
   * 
   * @param i
   * 
   * @return the ith {@link MultiSpeciesType}
   * @throws IndexOutOfBoundsException if the index is invalid.
   */
  public MultiSpeciesType getSpeciesType(int i) {
    return getListOfSpeciesTypes().get(i);
  }

  /**
   * Gets the {@link MultiSpeciesType} that has the given id.
   * 
   * @param id
   * @return the {@link MultiSpeciesType} that has the given id or null if
   * no {@link MultiSpeciesType} are found that match {@code id}.
   */
  public MultiSpeciesType getSpeciesType(String id) {
    if (isSetListOfSpeciesTypes()) {
      return listOfSpeciesTypes.firstHit(new NameFilter(id));
    }
    return null;
  }

  /**
   * Returns {@code true} if the listOfSpeciesType is set.
   * 
   * @return {@code true} if the listOfSpeciesType is set.
   */
  public boolean isSetListOfSpeciesTypes() {
    return listOfSpeciesTypes != null;
  }

  /**
   * Sets the listOfSpeciesTypes. If there was already some elements defined
   * on listOfSpeciesTypes, they will be unset beforehand.
   *
   * @param listOfSpeciesTypes the list of {@link MultiSpeciesType}s to set
   */
  public void setListOfSpeciesTypes(ListOf<MultiSpeciesType> listOfSpeciesTypes) {
    unsetListOfSpeciesTypes();
    this.listOfSpeciesTypes = listOfSpeciesTypes;

    if (listOfSpeciesTypes != null) {
      listOfSpeciesTypes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypes.setPackageName(null);
      listOfSpeciesTypes.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypes.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypes.setOtherListName(MultiConstants.listOfSpeciesTypes);
    }
    if (extendedSBase != null) {
      extendedSBase.registerChild(this.listOfSpeciesTypes);
    }
  }


  /**
   * Sets the listOfSpeciesTypes to null
   * 
   * @return {@code true} is successful
   */
  public boolean unsetListOfSpeciesTypes() {
    if (isSetListOfSpeciesTypes()) {
      ListOf<MultiSpeciesType> oldSpeciesTypeElements = listOfSpeciesTypes;
      listOfSpeciesTypes = null;
      oldSpeciesTypeElements.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }



  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    // no attribute to read
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }

    int pos = 0;
    if (isSetListOfSpeciesTypes()) {
      if (pos == childIndex) {
        return getListOfSpeciesTypes();
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
    if (isSetListOfSpeciesTypes()) {
      count++;
    }

    return count;
  }

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public Map<String, String> writeXMLAttributes() {
    // no attribute to write
    return null;
  }


}
