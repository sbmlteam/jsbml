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
 * complexes, the element model is extended to be linked to a list of {@link SpeciesType}s and a list of
 * {@link Selector}s.
 * 
 * @author Nicolas Rodriguez
 *
 */
public class MultiModel extends AbstractSBasePlugin {

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return MultiConstants.getNamespaceURI(getLevel(), getVersion());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return MultiConstants.packageName;
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
  ListOf<SpeciesType> listOfSpeciesTypes;
  /**
   * 
   */
  ListOf<Selector> listOfSelectors;

  /**
   * 
   * @param model
   */
  public MultiModel(Model model) {
    super(model);
  }

  /**
   * 
   * @param multiModel
   */
  public MultiModel(MultiModel multiModel) {
    super(multiModel);

    if (multiModel == null) {
      return;
    }
    if (multiModel.isSetListOfSelectors()) {
      listOfSelectors = multiModel.getListOfSelectors().clone();
    }
    if (multiModel.isSetListOfSpeciesTypes()) {
      listOfSpeciesTypes = multiModel.getListOfSpeciesTypes().clone();
    }
    // The listOf would have to be referenced elsewhere
    // TODO: check how libSBML is doing the cloning
    /*
		if (multiModel.getModel() != null) {
			getModel().registerChild(listOfSelectors);
			getModel().registerChild(listOfSpeciesTypes);
		}
     */

  }

  @Override
  public MultiModel clone() {
    return new MultiModel(this);
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
  public ListOf<SpeciesType> getListOfSpeciesTypes() {
    if (listOfSpeciesTypes == null) {
      listOfSpeciesTypes = new ListOf<SpeciesType>();
      listOfSpeciesTypes.setNamespace(MultiConstants.namespaceURI);
      getModel().registerChild(listOfSpeciesTypes);
      listOfSpeciesTypes.setSBaseListType(ListOf.Type.other);
    }

    return listOfSpeciesTypes;
  }

  /**
   * Adds a {@link SpeciesType}.
   * 
   * @param speciesType the speciesType to add
   * @return
   */
  public boolean addSpeciesType(SpeciesType speciesType) {
    return getListOfSpeciesTypes().add(speciesType);
  }

  /**
   * Creates a new {@link SpeciesType} inside this {@link MultiModel} and returns it.
   * <p>
   * 
   * @return the {@link SpeciesType} object created
   *         <p>
   * @see #addSpeciesType(SpeciesType r)
   */
  public SpeciesType createSpeciesType() {
    return createSpeciesType(null);
  }

  /**
   * Creates a new {@link SpeciesType} inside this {@link MultiModel} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link SpeciesType} object created
   */
  public SpeciesType createSpeciesType(String id) {
    SpeciesType speciesType = new SpeciesType();
    speciesType.setId(id);
    addSpeciesType(speciesType);

    return speciesType;
  }

  /**
   * Gets the ith {@link SpeciesType}.
   * 
   * @param i
   * 
   * @return the ith {@link SpeciesType}
   * @throws IndexOutOfBoundsException if the index is invalid.
   */
  public SpeciesType getSpeciesType(int i) {
    return getListOfSpeciesTypes().get(i);
  }

  /**
   * Gets the {@link SpeciesType} that has the given id.
   * 
   * @param id
   * @return the {@link SpeciesType} that has the given id or null if
   * no {@link SpeciesType} are found that match {@code id}.
   */
  public SpeciesType getSpeciesType(String id) {
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
    if ((listOfSpeciesTypes == null) || listOfSpeciesTypes.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Sets the listOfSpeciesTypes to null
   * 
   * @return {@code true} is successful
   */
  public boolean unsetListOfSpeciesTypes() {
    if (isSetListOfSpeciesTypes()) {
      // TODO: unregister the ids if needed.
      // getModel().unregisterChild(this.listOfSpeciesTypes);
      listOfSpeciesTypes = null;
      return true;
    }
    return false;
  }


  /**
   * Returns the listOfSelectors.
   * 
   * @return the listOfSelectors
   */
  public ListOf<Selector> getListOfSelectors() {
    if (listOfSelectors == null) {
      listOfSelectors = new ListOf<Selector>();
      listOfSelectors.setNamespace(MultiConstants.namespaceURI);
      getModel().registerChild(listOfSelectors);
      listOfSelectors.setSBaseListType(ListOf.Type.other);
    }

    return listOfSelectors;
  }

  /**
   * Adds a {@link Selector}.
   * 
   * @param selector the selector to add
   */
  public void addSelector(Selector selector) {
    getListOfSelectors().add(selector);
  }


  /**
   * Creates a new {@link Selector} inside this {@link MultiModel} and returns it.
   * <p>
   * 
   * @return the {@link Selector} object created
   *         <p>
   * @see #addSelector(Selector r)
   */
  public Selector createSelector() {
    return createSelector(null);
  }

  /**
   * Creates a new {@link Selector} inside this {@link MultiModel} and returns it.
   * 
   * @param id
   *        the id of the new element to create
   * @return the {@link Selector} object created
   */
  public Selector createSelector(String id) {
    Selector selector = new Selector();
    selector.setId(id);
    addSelector(selector);

    return selector;
  }

  /**
   * Gets the ith {@link Selector}.
   * 
   * @param i
   * 
   * @return the ith {@link Selector}
   * @throws IndexOutOfBoundsException if the index is invalid.
   */
  public Selector getSelector(int i) {
    return getListOfSelectors().get(i);
  }

  /**
   * Gets the {@link Selector} that has the given id.
   * 
   * @param id
   * @return the {@link Selector} that has the given id or null if
   * no {@link Selector} are found that match {@code id}.
   */
  public Selector getSelector(String id) {
    if (isSetListOfSelectors()) {
      return listOfSelectors.firstHit(new NameFilter(id));
    }
    return null;
  }

  /**
   * Returns {@code true} if the listOfSelector is set.
   * 
   * @return {@code true} if the listOfSelector is set.
   */
  public boolean isSetListOfSelectors() {
    if ((listOfSelectors == null) || listOfSelectors.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Sets the listOfSelectors to null
   * 
   * @return {@code true} is successful
   */
  public boolean unsetListOfSelectors() {
    if (isSetListOfSelectors()) {
      // TODO: unregister the ids if needed for SBasePlugin.
      // fireNodeRemoved(this.listOfSelectors);
      listOfSelectors = null;
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
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }

    int pos = 0;
    if (isSetListOfSpeciesTypes()) {
      if (pos == childIndex) {
        return getListOfSpeciesTypes();
      }
      pos++;
    }
    if (isSetListOfSelectors()) {
      if (pos == childIndex) {
        return getListOfSelectors();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
      childIndex, +Math.min(pos, 0)));
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
    if (isSetListOfSelectors()) {
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
