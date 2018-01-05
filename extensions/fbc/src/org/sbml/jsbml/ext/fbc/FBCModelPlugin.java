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
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.filters.NameFilter;
import org.sbml.jsbml.xml.XMLNode;

/**
 * {@link FBCModelPlugin} is the extended {@link Model} class for the FBC package.
 * 
 * <p>It is extended by the addition of two children, list of {@link FluxBound}
 * and list of {@link Objective}</p>
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class FBCModelPlugin extends AbstractFBCSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7451190347195219863L;

  /**
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  private ListOf<FluxBound> listOfFluxBounds;

  /**
   * Introduced to FBC in version 2.
   */
  private ListOf<GeneProduct> listOfGeneProducts;

  /**
   * 
   */
  private ListOfObjectives listOfObjectives;


  /**
   * The mandatory attribute strict is used to apply an additional set of
   * restrictions to the model.
   * The strict attribute ensures that the Flux Balance Constraints package can
   * be used to encode legacy FBA models expressible as Linear Programs (LP's)
   * with software that is unable to analyze arbitrary mathematical expressions.
   */
  private Boolean strict;


  /**
   * Clone constructor
   * 
   * @param fbcPlugin the instance to clone
   */
  public FBCModelPlugin(FBCModelPlugin fbcPlugin) {
    super(fbcPlugin);

    if (fbcPlugin.isSetListOfObjectives()) {
      setListOfObjectives((ListOfObjectives) fbcPlugin.getListOfObjectives().clone());
    }
    if (fbcPlugin.isSetListOfFluxBounds()) {
      setListOfFluxBounds(fbcPlugin.getListOfFluxBounds().clone());
    }
    if (fbcPlugin.isSetListOfGeneProducts()) {
      setListOfGeneProducts(fbcPlugin.getListOfGeneProducts().clone());
    }
    if (fbcPlugin.isSetStrict()) {
      setStrict(fbcPlugin.getStrict());
    }
  }


  /**
   * Creates a new {@link FBCModelPlugin} instance.
   * 
   * @param model the core {@link Model}
   */
  public FBCModelPlugin(Model model) {
    super(model);
  }

  /**
   * Adds a new {@link FluxBound} to the listOfFluxBounds.
   * <p>The listOfFluxBounds is initialized if necessary.
   *
   * @param fluxBound the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean addFluxBound(FluxBound fluxBound) {
    return getListOfFluxBounds().add(fluxBound);
  }

  /**
   * Adds a new {@link GeneProduct} to the {@link #listOfGeneProducts}.
   * <p>The listOfGeneProducts is initialized if necessary.
   *
   * @param geneProduct the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addGeneProduct(GeneProduct geneProduct) {
    return getListOfGeneProducts().add(geneProduct);
  }

  /**
   * Adds a new {@link Objective} to the listOfObjectives.
   * <p>The listOfObjectives is initialized if necessary.
   *
   * @param objective the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   */
  public boolean addObjective(Objective objective) {
    return getListOfObjectives().add(objective);
  }


  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public FBCModelPlugin clone() {
    return new FBCModelPlugin(this);
  }


  /**
   * Creates a new {@link FluxBound} element and adds it to the {@link #listOfFluxBounds} list.
   * 
   * @return a new {@link FluxBound} element
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound createFluxBound() {
    return createFluxBound(null);
  }


  /**
   * Creates a new {@link FluxBound} element and adds it to the ListOfFluxBounds list
   * 
   * @param id the id
   * @return a new {@link FluxBound} element
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound createFluxBound(String id) {
    FluxBound fluxBound = new FluxBound(id);
    addFluxBound(fluxBound);
    return fluxBound;
  }


  /**
   * Creates a new {@link GeneProduct} element and adds it to the ListOfGeneProducts list.
   * 
   * @return a new {@link GeneProduct} element
   */
  public GeneProduct createGeneProduct() {
    return createGeneProduct(null);
  }


  /**
   * Creates a new {@link GeneProduct} element and adds it to the {@link #listOfGeneProducts} list.
   * 
   * @param id the id
   * @return a new {@link GeneProduct} element.
   */
  public GeneProduct createGeneProduct(String id) {
    GeneProduct geneProduct = new GeneProduct(id);
    addGeneProduct(geneProduct);
    return geneProduct;
  }

  /**
   * Creates a new {@link Objective} element and adds it to the
   * {@link #listOfObjectives}.
   * @return the newly created {@link Objective} element or {@code null} if the
   *         operation fails.
   * @see #createObjective(String)
   */
  public Objective createObjective() {
    return createObjective(null);
  }

  /**
   * Creates a new {@link Objective} element and adds it to the
   * {@link #listOfObjectives}.
   * 
   * @param id
   *        the identifier for the {@link Objective} of type SId, can be
   *        {@code null}.
   * @return the newly created {@link Objective} element or {@code null} if the
   *         operation fails.
   * @see #createObjective(String, Objective.Type)
   */
  public Objective createObjective(String id) {
    return createObjective(id, null);
  }

  /**
   * Creates a new {@link Objective} element and adds it to the
   * {@link #listOfObjectives}.
   * 
   * @param id
   *        the identifier for the {@link Objective} of type SId, can be
   *        {@code null}.
   * @param type
   *        the type of the {@link Objective}, i.e., maximize or minimize.
   * @return the newly created {@link Objective} element or {@code null} if the
   *         operation fails.
   * @see #createObjective(String, String, Objective.Type)
   */
  public Objective createObjective(String id, Objective.Type type) {
    return createObjective(id, null, type);
  }

  /**
   * Creates a new {@link Objective} element and adds it to the
   * {@link #listOfObjectives}.
   * 
   * @param id
   *        the identifier for the {@link Objective} of type SId, can be
   *        {@code null}.
   * @param name
   *        the name of the objective that is to be created, can be {@code null}
   *        .
   * @param type
   *        the type of the {@link Objective}, i.e., maximize or minimize.
   * @return the newly created {@link Objective} element or {@code null} if the
   *         operation fails.
   */
  public Objective createObjective(String id, String name, Objective.Type type) {
    Objective objective = new Objective(getLevel(), getVersion());
    if (id != null) {
      objective.setId(id);
    }
    if (name != null) {
      objective.setName(name);
    }
    if (type != null) {
      objective.setType(type);
    }
    return addObjective(objective) ? objective : null;
  }

  /**
   * Gets the {@code activeObjective}.
   * <p>If the {@code activeObjective} is not defined, an empty String is returned.
   * 
   * @return the {@code activeObjective} or "".
   */
  public String getActiveObjective() {
    return isSetListOfObjectives() ? listOfObjectives.getActiveObjective() : "";
  }


  /**
   * 
   * @return
   */
  public Objective getActiveObjectiveInstance() {
    if (!isSetActiveObjective()) {
      return null;
    }

    return getListOfObjectives().firstHit(new NameFilter(getActiveObjective()));
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
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }

    int pos = 0;

    if (isSetListOfObjectives()) {
      if (pos == index) {
        return getListOfObjectives();
      }
      pos++;
    }
    if (isSetListOfFluxBounds()) {
      if (pos == index) {
        return getListOfFluxBounds();
      }
      pos++;
    }
    if (isSetListOfGeneProducts()) {
      if (pos == index) {
        return getListOfGeneProducts();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfObjectives()) {
      count++;
    }
    if (isSetListOfFluxBounds()) {
      count++;
    }
    if (isSetListOfGeneProducts()) {
      count++;
    }

    return count;
  }

  /**
   *  Gets an element from the listOfFluxBounds at the given index.
   *
   * @param i the index where to get the {@link FluxBound}
   * @throws IndexOutOfBoundsException
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   * @return an element from the listOfFluxBounds at the given index.
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound getFluxBound(int i) {
    return getListOfFluxBounds().get(i);
  }
  /**
   * Return the number of {@link FluxBound} in this {@link FBCModelPlugin}.
   * 
   * @return the number of {@link FluxBound} in this {@link FBCModelPlugin}.
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public int getFluxBoundCount() {
    if (isSetListOfFluxBounds()) {
      return listOfFluxBounds.size();
    }

    return 0;
  }

  /**
   * Gets an element from the {@link #listOfGeneProducts} at the given index.
   *
   * @param i the index of the {@link GeneProduct} element to get.
   * @return an element from the listOfGeneProducts at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public GeneProduct getGeneProduct(int i) {
    if (!isSetListOfGeneProducts()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfGeneProducts().get(i);
  }

  /**
   * Gets an element from the listOfGeneProducts, with the given id.
   *
   * @param geneProductId the id of the {@link GeneProduct} element to get.
   * @return an element from the listOfGeneProducts with the given id or {@code null}.
   */
  public GeneProduct getGeneProduct(String geneProductId) {
    if (isSetListOfGeneProducts()) {
      return getListOfGeneProducts().get(geneProductId);
    }
    return null;
  }

  /**
   * Returns the number of {@link GeneProduct}s in this {@link FBCModelPlugin}.
   * 
   * @return the number of {@link GeneProduct}s in this {@link FBCModelPlugin}.
   */
  public int getGeneProductCount() {
    return isSetListOfGeneProducts() ? getListOfGeneProducts().size() : 0;
  }

  /**
   * Returns the listOfFluxBounds. Creates it if it is not already existing.
   *
   * @return the listOfFluxBounds
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public ListOf<FluxBound> getListOfFluxBounds() {
    if (!isSetListOfFluxBounds()) {
      listOfFluxBounds = new ListOf<FluxBound>();
      listOfFluxBounds.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'fbc'
      listOfFluxBounds.setPackageName(null);
      listOfFluxBounds.setPackageName(FBCConstants.shortLabel);
      listOfFluxBounds.setSBaseListType(ListOf.Type.other);
      listOfFluxBounds.setOtherListName(FBCConstants.listOfFluxBounds);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfFluxBounds);
      }
    }
    return listOfFluxBounds;
  }

  /**
   * Returns the {@link #listOfGeneProducts}. Creates it if it is not already existing.
   *
   * @return the {@link #listOfGeneProducts}.
   */
  public ListOf<GeneProduct> getListOfGeneProducts() {
    if (!isSetListOfGeneProducts()) {
      listOfGeneProducts = new ListOf<GeneProduct>();
      listOfGeneProducts.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'fbc'
      listOfGeneProducts.setPackageName(null);
      listOfGeneProducts.setPackageName(FBCConstants.shortLabel);
      listOfGeneProducts.setSBaseListType(ListOf.Type.other);
      listOfGeneProducts.setOtherListName(FBCConstants.listOfGeneProducts);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfGeneProducts);
      }
    }
    return listOfGeneProducts;
  }

  /**
   * Returns the {@link #listOfObjectives}. Creates it if it is not already
   * existing.
   *
   * @return the listOfObjectives
   */
  public ListOfObjectives getListOfObjectives() {
    if (!isSetListOfObjectives()) {
      listOfObjectives = new ListOfObjectives();

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfObjectives);
      }
    }
    return listOfObjectives;
  }

  /**
   * Return the number of {@link FluxBound} in this {@link FBCModelPlugin}.
   * 
   * @return the number of {@link FluxBound} in this {@link FBCModelPlugin}.
   * @libsbml.deprecated same as {@link #getFluxBoundCount()}
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public int getNumFluxBound() {
    return getFluxBoundCount();
  }

  /**
   * Returns the number of {@link GeneProduct}s in this {@link FBCModelPlugin}.
   * 
   * @return the number of {@link GeneProduct}s in this {@link FBCModelPlugin}.
   * @libsbml.deprecated same as {@link #getGeneProductCount()}
   */
  public int getNumGeneProducts() {
    return getGeneProductCount();
  }

  /**
   * Return the number of {@link Objective}s in this {@link FBCModelPlugin}.
   * 
   * @return the number of {@link Objective}s in this {@link FBCModelPlugin}.
   * @libsbml.deprecated same as {@link #getObjectiveCount()}
   */
  public int getNumObjective() {
    return getObjectiveCount();
  }

  /**
   *  Gets an element from the listOfObjectives at the given index.
   *
   * @param i the index where to get the {@link Objective}
   * @throws IndexOutOfBoundsException
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   * @return an element from the listOfObjectives at the given index.
   */
  public Objective getObjective(int i) {
    return getListOfObjectives().get(i);
  }

  /**
   * Return the number of {@link Objective} in this {@link FBCModelPlugin}.
   * 
   * @return the number of {@link Objective} in this {@link FBCModelPlugin}.
   */
  public int getObjectiveCount() {
    if (isSetListOfObjectives()) {
      return listOfObjectives.size();
    }

    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public Model getParent() {
    if (isSetExtendedSBase()) {
      return (Model) getExtendedSBase();
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public Model getParentSBMLObject() {
    return getParent();
  }

  /**
   * Returns the value of {@link #strict}.
   *
   * @return the value of {@link #strict}.
   */
  public boolean getStrict() {
    if (isSetStrict()) {
      return strict;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(FBCConstants.strict, this);
  }

  /**
   * 
   * @return
   */
  public boolean isSetActiveObjective() {
    return isSetListOfObjectives() && getListOfObjectives().isSetActiveObjective();
  }

  /**
   * Returns {@code true} if listOfFluxBounds contains at least one element.
   *
   * @return {@code true} if listOfFluxBounds contains at least one element,
   *         otherwise {@code false}
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean isSetListOfFluxBounds() {
    return ((listOfFluxBounds != null) && !listOfFluxBounds.isEmpty());
  }

  /**
   * Returns {@code true} if {@link #listOfGeneProducts} contains at least one element.
   *
   * @return {@code true} if {@link #listOfGeneProducts} contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean isSetListOfGeneProducts() {
    if ((listOfGeneProducts == null) || listOfGeneProducts.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns {@code true} if listOfObjectives is not {@code null}.
   *
   * @return {@code true} if listOfObjectives is not null,
   *         otherwise {@code false}
   */
  public boolean isSetListOfObjectives() {
    // cannot use the isEmpty() test here to avoid loosing the activeObject attribute
    // when calling the getListOfObjectives() when there are not yet any objective object added to the list.
    // This happen for example when reading a file.
    return listOfObjectives != null;
  }

  /**
   * Returns whether {@link #strict} is set.
   *
   * @return whether {@link #strict} is set.
   */
  public boolean isSetStrict() {
    return strict != null;
  }

  /**
   * Returns the value of {@link #strict}.
   *
   * @return the value of {@link #strict}.
   */
  public boolean isStrict() {
    return getStrict();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    if (attributeName.equals(FBCConstants.strict)) {
      setStrict(StringTools.parseSBMLBoolean(value));
      return true;
    }
    return false;
  }

  /**
   * @param xmlNode
   * @param key
   * @return
   */
  private String recurseAndFind(XMLNode xmlNode, String key) {

    if (xmlNode.getChildCount() == 0) {
      if (xmlNode.getCharacters().startsWith(key)) {
        return xmlNode.getCharacters().replace(key, "").trim();
      }
    } else {
      for (int i = 0; i < xmlNode.getChildCount(); i++) {
        String potential = recurseAndFind(xmlNode.getChildAt(i), key);
        if (!(potential == null)) {
          return potential;
        } else {
          continue;
        }
      }
    }

    return null;
  }

  /**
   * Removes an element from the listOfFluxBounds.
   *
   * @param fluxBound the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see java.util.List#remove(Object)
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean removeFluxBound(FluxBound fluxBound) {
    if (isSetListOfFluxBounds()) {
      return getListOfFluxBounds().remove(fluxBound);
    }
    return false;
  }

  /**
   * Removes an element from the listOfFluxBounds at the given index.
   *
   * @param i the index where to remove the {@link FluxBound}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public void removeFluxBound(int i) {
    if (!isSetListOfFluxBounds()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfFluxBounds().remove(i);
  }

  /**
   * Removes an element from the {@link #listOfGeneProducts}.
   *
   * @param geneProduct the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeGeneProduct(GeneProduct geneProduct) {
    if (isSetListOfGeneProducts()) {
      return getListOfGeneProducts().remove(geneProduct);
    }
    return false;
  }

  /**
   * Removes an element from the listOfGeneProducts at the given index.
   *
   * @param i the index where to remove the {@link GeneProduct}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public GeneProduct removeGeneProduct(int i) {
    if (!isSetListOfGeneProducts()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfGeneProducts().remove(i);
  }

  // TODO - if GeneProduct has no id attribute, you should remove this method.
  /**
   * Removes an element from the {@link #listOfGeneProducts}.
   *
   * @param geneProductId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or {@code null}.
   */
  public GeneProduct removeGeneProduct(String geneProductId) {
    if (isSetListOfGeneProducts()) {
      return getListOfGeneProducts().remove(geneProductId);
    }
    return null;
  }

  /**
   * Removes an element from the listOfObjectives at the given index.
   *
   * @param i the index where to remove the {@link Objective}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeObjective(int i) {
    if (!isSetListOfObjectives()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfObjectives().remove(i);
  }

  /**
   * Removes an element from the listOfObjectives.
   *
   * @param objective the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see java.util.List#remove(Object)
   */
  public boolean removeObjective(Objective objective) {
    if (isSetListOfObjectives()) {
      return getListOfObjectives().remove(objective);
    }
    return false;
  }

  /**
   * 
   * @param objective
   */
  public void setActiveObjective(Objective objective) {
    setActiveObjective(objective.getId());
  }

  /**
   * The activeObjective refers to the id of an existing objective. This required
   * attribute exists so that if there are multiple objectives included in the
   * model, the model will still be well-described.
   * 
   * @param activeObjective
   */
  public void setActiveObjective(String activeObjective) {
    getListOfObjectives().setActiveObjective(activeObjective);
  }

  /**
   *  Sets the given {@code ListOf<FluxBound>}. <p>If listOfFluxBounds
   *  was defined before and contained some elements, they are all unset.
   * 
   * @param listOfFluxBounds
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public void setListOfFluxBounds(ListOf<FluxBound> listOfFluxBounds) {
    unsetListOfFluxBounds();
    this.listOfFluxBounds = listOfFluxBounds;

    if (listOfFluxBounds != null) {
      listOfFluxBounds.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'fbc'
      listOfFluxBounds.setPackageName(null);
      listOfFluxBounds.setPackageName(FBCConstants.shortLabel);
      listOfFluxBounds.setSBaseListType(ListOf.Type.other);
      listOfFluxBounds.setOtherListName(FBCConstants.listOfFluxBounds);
    }

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(this.listOfFluxBounds);
    }
  }

  /**
   * Sets the given {@code ListOf<GeneProduct>}. If {@link #listOfGeneProducts}
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfGeneProducts
   */
  public void setListOfGeneProducts(ListOf<GeneProduct> listOfGeneProducts) {
    unsetListOfGeneProducts();
    this.listOfGeneProducts = listOfGeneProducts;

    if (listOfGeneProducts != null) {
      listOfGeneProducts.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'fbc'
      listOfGeneProducts.setPackageName(null);
      listOfGeneProducts.setPackageName(FBCConstants.shortLabel);
      listOfGeneProducts.setSBaseListType(ListOf.Type.other);
      listOfGeneProducts.setOtherListName(FBCConstants.listOfGeneProducts);
    }

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(this.listOfGeneProducts);
    }
  }

  /**
   * Sets the given {@code ListOf<Objective>}.
   * 
   * <p>
   * If the given list is an instance of {@link ListOfObjectives}, a call of
   * this method is identical to directly calling
   * {@link #setListOfObjectives(ListOfObjectives)}.
   * Otherwise, a new {@link ListOfObjectives} will be created from the given
   * {@link ListOf}.</p>
   * 
   * <p>Note that in the second case there is no active objective defined.</p>
   * 
   * @param listOfObjectives
   */
  public void setListOfObjectives(ListOf<Objective> listOfObjectives) {
    if (listOfObjectives instanceof ListOfObjectives) {
      setListOfObjectives((ListOfObjectives) listOfObjectives);
    } else {
      setListOfObjectives(new ListOfObjectives(listOfObjectives));
    }
  }

  /**
   * Sets the given {@code ListOfObjectives}. <p>If listOfObjectives
   * was defined before and contained some elements, they are all unset.
   *
   * @param listOfObjectives
   */
  public void setListOfObjectives(ListOfObjectives listOfObjectives) {
    unsetListOfObjectives();
    this.listOfObjectives = listOfObjectives;

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(this.listOfObjectives);
    }
  }

  /**
   * @param key
   */
  public void setNotesKeyToUserObject(String key) {
    Model model = getParent();
    ListOf<Reaction> rxns = model.getListOfReactions();

    for (Reaction r : rxns) {
      String val = recurseAndFind(r.getNotes(), key);
      if (val != null) {
        r.putUserObject(key, val);
      }
    }

  }

  /**
   * Sets the value of strict
   *
   * @param strict the value of strict to be set.
   */
  public void setStrict(boolean strict) {
    Boolean oldStrict = this.strict;
    this.strict = strict;
    firePropertyChange(FBCConstants.strict, oldStrict, this.strict);
  }

  /**
   * Returns {@code true} if {@link #listOfFluxBounds} contain at least one
   * element, otherwise {@code false}
   * 
   * @return {@code true} if {@link #listOfFluxBounds} contain at least one
   *         element, otherwise {@code false}
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean unsetListOfFluxBounds() {
    if (isSetListOfFluxBounds()) {
      ListOf<FluxBound> oldFluxBounds = listOfFluxBounds;
      listOfFluxBounds = null;
      oldFluxBounds.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Returns {@code true} if {@link #listOfGeneProducts} contains at least one element,
   *         otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfGeneProducts} contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean unsetListOfGeneProducts() {
    if (isSetListOfGeneProducts()) {
      ListOf<GeneProduct> oldGeneProducts = listOfGeneProducts;
      listOfGeneProducts = null;
      oldGeneProducts.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Returns {@code true} if listOfObjectives contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true} if listOfObjectives contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfObjectives() {
    if (isSetListOfObjectives()) {
      ListOf<Objective> oldObjectives = listOfObjectives;
      listOfObjectives = null;
      oldObjectives.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable strict.
   *
   * @return {@code true} if strict was set before, otherwise {@code false}.
   */
  public boolean unsetStrict() {
    if (isSetStrict()) {
      boolean oldStrict = strict;
      strict = null;
      firePropertyChange(FBCConstants.strict, oldStrict, strict);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = new TreeMap<String, String>();

    if (isSetStrict()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.strict, Boolean.toString(isStrict()));
    }

    return attributes;
  }

}
