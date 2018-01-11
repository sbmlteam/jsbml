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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentalizedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.UniqueNamedSBase;

 /**
 * 
 * <p>The element MultiSpeciesType, which is part of SBML Level 2 Version 4 specification, is not part
 * of SBML Level 3 Version 1 Core any more. Instead, it will be defined in the multi package. The
 * MultiSpeciesType element carries not only the basic attributes which it had in SBML Level 2 Version 4
 * (metaid, id, name), but is also extended for the needs of describing multi-component entities
 * with the attribute bindingSite and for the needs of multistate entities by linking it to a list of
 * StateFeatures</p>
 * 
 * <p>A species type can be used to describe a component of a supra-macromolecular assembly,
 * but also a domain of a macromolecule. Such a domain can be a portion of the macromolecule,
 * a non-connex set of atoms forming a functional domain, or just a conceptual construct suiting
 * the needs of the modeler. The type of component can be specified by referring terms from the
 * subbranch functional entity of the <a href="http://biomodels.net/sbo/">Systems Biology Ontology</a>
 * through the optional sboTerm attribute. The following table provides typical examples of
 * component or domains (the list is absolutely not complete).</p>
 * <table>
 *   <caption>Typical examples of components or domains</caption>
 *   <tr>
 *     <th> SBO identifier </th><th> Definition </th>
 *   </tr><tr>
 *     <td> SBO:0000242 </td><td> channel </td>
 *   </tr><tr>
 *     <td> SBO:0000244 </td><td> receptor </td>
 *   </tr><tr>
 *     <td> SBO:0000284 </td><td> transporter </td>
 *   </tr><tr>
 *     <td> SBO:0000280 </td><td> ligand </td>
 *   </tr><tr>
 *     <td> SBO:0000493 </td><td> functional domain </td>
 *   </tr><tr>
 *     <td> SBO:0000494 </td><td> binding site </td>
 *   </tr><tr>
 *     <td> SBO:0000495 </td><td> catalytic site </td>
 *   </tr><tr>
 *     <td> SBO:0000496 </td><td> transmembrane domain </td>
 *   </tr>
 * </table>
 * 
 * @author Nicolas Rodriguez
 *
 */
@SuppressWarnings("deprecation")
public class MultiSpeciesType extends org.sbml.jsbml.SpeciesType  implements CompartmentalizedSBase, UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2L;

  // TODO - implement IdManager at least for SpeciesComponentIndex and InSpeciesTypeBond ids

  /**
   * 
   */
  private String compartment;

  /**
   * 
   */
  private ListOf<InSpeciesTypeBond> listOfInSpeciesTypeBonds;

  /**
   * 
   */
  private ListOf<SpeciesFeatureType> listOfSpeciesFeatureTypes;

  /**
   * 
   */
  private ListOf<SpeciesTypeComponentIndex> listOfSpeciesTypeComponentIndexes;

  /**
   * 
   */
  private ListOf<SpeciesTypeInstance> listOfSpeciesTypeInstances;

  /**
   * Creates an MultiSpeciesType instance
   */
  public MultiSpeciesType() {
    super();
    initDefaults();
  }


  /**
   * Creates a MultiSpeciesType instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public MultiSpeciesType(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a new {@link MultiSpeciesType} instance cloned from the given {@link MultiSpeciesType}.
   * 
   * @param obj the {@link MultiSpeciesType} to clone
   */
  public MultiSpeciesType(MultiSpeciesType obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetListOfSpeciesFeatureTypes()) {
      setListOfSpeciesFeatureTypes(obj.getListOfSpeciesFeatureTypes().clone());
    }
    if (obj.isSetListOfSpeciesTypeInstances()) {
      setListOfSpeciesTypeInstances(obj.getListOfSpeciesTypeInstances().clone());
    }
    if (obj.isSetListOfSpeciesTypeComponentIndexes()) {
      setListOfSpeciesTypeComponentIndexes(obj.getListOfSpeciesTypeComponentIndexes().clone());
    }
    if (obj.isSetListOfInSpeciesTypeBonds()) {
      setListOfInSpeciesTypeBonds(obj.getListOfInSpeciesTypeBonds().clone());
    }
    if (obj.isSetCompartment()) {
      setCompartment(obj.getCompartment());
    }

  }


  /**
   * Creates a MultiSpeciesType instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public MultiSpeciesType(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a MultiSpeciesType instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public MultiSpeciesType(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a MultiSpeciesType instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public MultiSpeciesType(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(MultiConstants.MIN_SBML_LEVEL),
      Integer.valueOf(MultiConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Adds a new {@link InSpeciesTypeBond} to the {@link #listOfInSpeciesTypeBonds}.
   * <p>The listOfInSpeciesTypeBonds is initialized if necessary.
   *
   * @param inSpeciesTypeBond the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addInSpeciesTypeBond(InSpeciesTypeBond inSpeciesTypeBond) {
    return getListOfInSpeciesTypeBonds().add(inSpeciesTypeBond);
  }


  /**
   * Adds a new {@link SpeciesFeatureType} to the {@link #listOfSpeciesFeatureTypes}.
   * <p>The listOfSpeciesFeatureTypes is initialized if necessary.
   *
   * @param speciesFeatureType the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSpeciesFeatureType(SpeciesFeatureType speciesFeatureType) {
    return getListOfSpeciesFeatureTypes().add(speciesFeatureType);
  }


  /**
   * Adds a new {@link SpeciesTypeComponentIndex} to the {@link #listOfSpeciesTypeComponentIndexes}.
   * <p>The listOfSpeciesTypeComponentIndexes is initialized if necessary.
   *
   * @param speciesTypeComponentIndex the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSpeciesTypeComponentIndex(SpeciesTypeComponentIndex speciesTypeComponentIndex) {
    return getListOfSpeciesTypeComponentIndexes().add(speciesTypeComponentIndex);
  }


  /**
   * Adds a new {@link SpeciesTypeInstance} to the {@link #listOfSpeciesTypeInstances}.
   * <p>The listOfSpeciesTypeInstances is initialized if necessary.
   *
   * @param speciesTypeInstance the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSpeciesTypeInstance(SpeciesTypeInstance speciesTypeInstance) {
    return getListOfSpeciesTypeInstances().add(speciesTypeInstance);
  }


  /**
   * clones this class
   */
  @Override
  public MultiSpeciesType clone() {
    return new MultiSpeciesType(this);
  }


  /**
   * Creates a new InSpeciesTypeBond element and adds it to the
   * {@link #listOfInSpeciesTypeBonds} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfInSpeciesTypeBonds}
   */
  public InSpeciesTypeBond createInSpeciesTypeBond() {
    return createInSpeciesTypeBond(null);
  }


  /**
   * Creates a new {@link InSpeciesTypeBond} element and adds it to the
   * {@link #listOfInSpeciesTypeBonds} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link InSpeciesTypeBond} element, which is the last
   *         element in the {@link #listOfInSpeciesTypeBonds}.
   */
  public InSpeciesTypeBond createInSpeciesTypeBond(String id) {
    InSpeciesTypeBond inSpeciesTypeBond = new InSpeciesTypeBond(id);
    addInSpeciesTypeBond(inSpeciesTypeBond);
    return inSpeciesTypeBond;
  }


  /**
   * Creates a new SpeciesFeatureType element and adds it to the
   * {@link #listOfSpeciesFeatureTypes} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSpeciesFeatureTypes}
   */
  public SpeciesFeatureType createSpeciesFeatureType() {
    return createSpeciesFeatureType(null);
  }


  /**
   * Creates a new {@link SpeciesFeatureType} element and adds it to the
   * {@link #listOfSpeciesFeatureTypes} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link SpeciesFeatureType} element, which is the last
   *         element in the {@link #listOfSpeciesFeatureTypes}.
   */
  public SpeciesFeatureType createSpeciesFeatureType(String id) {
    SpeciesFeatureType speciesFeatureType = new SpeciesFeatureType(id);
    addSpeciesFeatureType(speciesFeatureType);
    return speciesFeatureType;
  }


  /**
   * Creates a new SpeciesTypeComponentIndex element and adds it to the
   * {@link #listOfSpeciesTypeComponentIndexes} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSpeciesTypeComponentIndexes}
   */
  public SpeciesTypeComponentIndex createSpeciesTypeComponentIndex() {
    return createSpeciesTypeComponentIndex(null);
  }


  /**
   * Creates a new {@link SpeciesTypeComponentIndex} element and adds it to the
   * {@link #listOfSpeciesTypeComponentIndexes} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link SpeciesTypeComponentIndex} element, which is the last
   *         element in the {@link #listOfSpeciesTypeComponentIndexes}.
   */
  public SpeciesTypeComponentIndex createSpeciesTypeComponentIndex(String id) {
    SpeciesTypeComponentIndex speciesTypeComponentIndex = new SpeciesTypeComponentIndex(id);
    addSpeciesTypeComponentIndex(speciesTypeComponentIndex);
    return speciesTypeComponentIndex;
  }


  /**
   * Creates a new SpeciesTypeInstance element and adds it to the
   * {@link #listOfSpeciesTypeInstances} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSpeciesTypeInstances}
   */
  public SpeciesTypeInstance createSpeciesTypeInstance() {
    return createSpeciesTypeInstance(null);
  }


  /**
   * Creates a new {@link SpeciesTypeInstance} element and adds it to the
   * {@link #listOfSpeciesTypeInstances} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link SpeciesTypeInstance} element, which is the last
   *         element in the {@link #listOfSpeciesTypeInstances}.
   */
  public SpeciesTypeInstance createSpeciesTypeInstance(String id) {
    SpeciesTypeInstance speciesTypeInstance = new SpeciesTypeInstance(id);
    addSpeciesTypeInstance(speciesTypeInstance);
    return speciesTypeInstance;
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
    MultiSpeciesType other = (MultiSpeciesType) obj;
    if (compartment == null) {
      if (other.compartment != null) {
        return false;
      }
    } else if (!compartment.equals(other.compartment)) {
      return false;
    }
    if (listOfInSpeciesTypeBonds == null) {
      if (other.listOfInSpeciesTypeBonds != null) {
        return false;
      }
    } else if (!listOfInSpeciesTypeBonds.equals(other.listOfInSpeciesTypeBonds)) {
      return false;
    }
    if (listOfSpeciesFeatureTypes == null) {
      if (other.listOfSpeciesFeatureTypes != null) {
        return false;
      }
    } else if (!listOfSpeciesFeatureTypes.equals(other.listOfSpeciesFeatureTypes)) {
      return false;
    }
    if (listOfSpeciesTypeComponentIndexes == null) {
      if (other.listOfSpeciesTypeComponentIndexes != null) {
        return false;
      }
    } else if (!listOfSpeciesTypeComponentIndexes.equals(other.listOfSpeciesTypeComponentIndexes)) {
      return false;
    }
    if (listOfSpeciesTypeInstances == null) {
      if (other.listOfSpeciesTypeInstances != null) {
        return false;
      }
    } else if (!listOfSpeciesTypeInstances.equals(other.listOfSpeciesTypeInstances)) {
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

    if (isSetListOfSpeciesFeatureTypes()) {
      if (pos == index) {
        return getListOfSpeciesFeatureTypes();
      }
      pos++;
    }
    if (isSetListOfSpeciesTypeInstances()) {
      if (pos == index) {
        return getListOfSpeciesTypeInstances();
      }
      pos++;
    }
    if (isSetListOfSpeciesTypeComponentIndexes()) {
      if (pos == index) {
        return getListOfSpeciesTypeComponentIndexes();
      }
      pos++;
    }
    if (isSetListOfInSpeciesTypeBonds()) {
      if (pos == index) {
        return getListOfInSpeciesTypeBonds();
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

    if (isSetListOfSpeciesFeatureTypes()) {
      count++;
    }
    if (isSetListOfSpeciesTypeInstances()) {
      count++;
    }
    if (isSetListOfSpeciesTypeComponentIndexes()) {
      count++;
    }
    if (isSetListOfInSpeciesTypeBonds()) {
      count++;
    }

    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartment()
   */
  @Override
  public String getCompartment() {
    return isSetCompartment() ? compartment : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartmentInstance()
   */
  @Override
  public Compartment getCompartmentInstance() {
    if (isSetCompartment()) {
      Model model = getModel();
      if (model != null) {
        return model.getCompartment(getCompartment());
      }
    }
    return null;
  }

  

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return MultiConstants.speciesType;
  }


  /**
   * Gets an element from the {@link #listOfInSpeciesTypeBonds} at the given index.
   *
   * @param i the index of the {@link InSpeciesTypeBond} element to get.
   * @return an element from the listOfInSpeciesTypeBonds at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public InSpeciesTypeBond getInSpeciesTypeBond(int i) {
    if (!isSetListOfInSpeciesTypeBonds()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfInSpeciesTypeBonds().get(i);
  }


  /**
   * Gets an element from the listOfInSpeciesTypeBonds, with the given id.
   *
   * @param inSpeciesTypeBondId the id of the {@link InSpeciesTypeBond} element to get.
   * @return an element from the listOfInSpeciesTypeBonds with the given id
   *         or {@code null}.
   */
  public InSpeciesTypeBond getInSpeciesTypeBond(String inSpeciesTypeBondId) {
    if (isSetListOfInSpeciesTypeBonds()) {
      return getListOfInSpeciesTypeBonds().get(inSpeciesTypeBondId);
    }
    return null;
  }


  /**
   * Returns the number of {@link InSpeciesTypeBond}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link InSpeciesTypeBond}s in this
   *         {@link MultiSpeciesType}.
   */
  public int getInSpeciesTypeBondCount() {
    return isSetListOfInSpeciesTypeBonds() ? getListOfInSpeciesTypeBonds().size() : 0;
  }


  /**
   * Returns the {@link #listOfInSpeciesTypeBonds}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfInSpeciesTypeBonds}.
   */
  public ListOf<InSpeciesTypeBond> getListOfInSpeciesTypeBonds() {
    if (listOfInSpeciesTypeBonds == null) {
      listOfInSpeciesTypeBonds = new ListOf<InSpeciesTypeBond>();
      listOfInSpeciesTypeBonds.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfInSpeciesTypeBonds.setPackageName(null);
      listOfInSpeciesTypeBonds.setPackageName(MultiConstants.shortLabel);
      listOfInSpeciesTypeBonds.setSBaseListType(ListOf.Type.other);
      listOfInSpeciesTypeBonds.setOtherListName(MultiConstants.listOfInSpeciesTypeBonds);
      
      registerChild(listOfInSpeciesTypeBonds);
    }
    return listOfInSpeciesTypeBonds;
  }


  /**
   * Returns the {@link #listOfSpeciesFeatureTypes}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSpeciesFeatureTypes}.
   */
  public ListOf<SpeciesFeatureType> getListOfSpeciesFeatureTypes() {
    if (listOfSpeciesFeatureTypes == null) {
      listOfSpeciesFeatureTypes = new ListOf<SpeciesFeatureType>();
      listOfSpeciesFeatureTypes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatureTypes.setPackageName(null);
      listOfSpeciesFeatureTypes.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesFeatureTypes.setSBaseListType(ListOf.Type.other);
      listOfSpeciesFeatureTypes.setOtherListName(MultiConstants.listOfSpeciesFeatureTypes);
      
      registerChild(listOfSpeciesFeatureTypes);
    }
    return listOfSpeciesFeatureTypes;
  }


  /**
   * Returns the {@link #listOfSpeciesTypeComponentIndexes}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSpeciesTypeComponentIndexes}.
   */
  public ListOf<SpeciesTypeComponentIndex> getListOfSpeciesTypeComponentIndexes() {
    if (listOfSpeciesTypeComponentIndexes == null) {
      listOfSpeciesTypeComponentIndexes = new ListOf<SpeciesTypeComponentIndex>();
      listOfSpeciesTypeComponentIndexes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypeComponentIndexes.setPackageName(null);
      listOfSpeciesTypeComponentIndexes.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypeComponentIndexes.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypeComponentIndexes.setOtherListName(MultiConstants.listOfSpeciesTypeComponentIndexes);
      
      registerChild(listOfSpeciesTypeComponentIndexes);
    }
    return listOfSpeciesTypeComponentIndexes;
  }


  /**
   * Returns the {@link #listOfSpeciesTypeInstances}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSpeciesTypeInstances}.
   */
  public ListOf<SpeciesTypeInstance> getListOfSpeciesTypeInstances() {
    if (listOfSpeciesTypeInstances == null) {
      listOfSpeciesTypeInstances = new ListOf<SpeciesTypeInstance>();
      listOfSpeciesTypeInstances.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypeInstances.setPackageName(null);
      listOfSpeciesTypeInstances.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypeInstances.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypeInstances.setOtherListName(MultiConstants.listOfSpeciesTypeInstances);
      
      registerChild(listOfSpeciesTypeInstances);
    }
    return listOfSpeciesTypeInstances;
  }


  /**
   * Returns the number of {@link InSpeciesTypeBond}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link InSpeciesTypeBond}s in this
   *         {@link MultiSpeciesType}.
   * @libsbml.deprecated same as {@link #getInSpeciesTypeBondCount()}
   */
  public int getNumInSpeciesTypeBonds() {
    return getInSpeciesTypeBondCount();
  }


  /**
   * Returns the number of {@link SpeciesFeatureType}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link SpeciesFeatureType}s in this
   *         {@link MultiSpeciesType}.
   * @libsbml.deprecated same as {@link #getSpeciesFeatureTypeCount()}
   */
  public int getNumSpeciesFeatureTypes() {
    return getSpeciesFeatureTypeCount();
  }


  /**
   * Returns the number of {@link SpeciesTypeComponentIndex}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link SpeciesTypeComponentIndex}s in this
   *         {@link MultiSpeciesType}.
   * @libsbml.deprecated same as {@link #getSpeciesTypeComponentIndexCount()}
   */
  public int getNumSpeciesTypeComponentIndexes() {
    return getSpeciesTypeComponentIndexCount();
  }


  /**
   * Returns the number of {@link SpeciesTypeInstance}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link SpeciesTypeInstance}s in this
   *         {@link MultiSpeciesType}.
   * @libsbml.deprecated same as {@link #getSpeciesTypeInstanceCount()}
   */
  public int getNumSpeciesTypeInstances() {
    return getSpeciesTypeInstanceCount();
  }


  /**
   * Gets an element from the {@link #listOfSpeciesFeatureTypes} at the given index.
   *
   * @param i the index of the {@link SpeciesFeatureType} element to get.
   * @return an element from the listOfSpeciesFeatureTypes at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public SpeciesFeatureType getSpeciesFeatureType(int i) {
    if (!isSetListOfSpeciesFeatureTypes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatureTypes().get(i);
  }


  /**
   * Gets an element from the listOfSpeciesFeatureTypes, with the given id.
   *
   * @param speciesFeatureTypeId the id of the {@link SpeciesFeatureType} element to get.
   * @return an element from the listOfSpeciesFeatureTypes with the given id
   *         or {@code null}.
   */
  public SpeciesFeatureType getSpeciesFeatureType(String speciesFeatureTypeId) {
    if (isSetListOfSpeciesFeatureTypes()) {
      return getListOfSpeciesFeatureTypes().get(speciesFeatureTypeId);
    }
    return null;
  }


  /**
   * Returns the number of {@link SpeciesFeatureType}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link SpeciesFeatureType}s in this
   *         {@link MultiSpeciesType}.
   */
  public int getSpeciesFeatureTypeCount() {
    return isSetListOfSpeciesFeatureTypes() ? getListOfSpeciesFeatureTypes().size() : 0;
  }


  /**
   * Gets an element from the {@link #listOfSpeciesTypeComponentIndexes} at the given index.
   *
   * @param i the index of the {@link SpeciesTypeComponentIndex} element to get.
   * @return an element from the listOfSpeciesTypeComponentIndexes at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public SpeciesTypeComponentIndex getSpeciesTypeComponentIndex(int i) {
    if (!isSetListOfSpeciesTypeComponentIndexes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesTypeComponentIndexes().get(i);
  }


  /**
   * Gets an element from the listOfSpeciesTypeComponentIndexes, with the given id.
   *
   * @param speciesTypeComponentIndexId the id of the {@link SpeciesTypeComponentIndex} element to get.
   * @return an element from the listOfSpeciesTypeComponentIndexes with the given id
   *         or {@code null}.
   */
  public SpeciesTypeComponentIndex getSpeciesTypeComponentIndex(String speciesTypeComponentIndexId) {
    if (isSetListOfSpeciesTypeComponentIndexes()) {
      return getListOfSpeciesTypeComponentIndexes().get(speciesTypeComponentIndexId);
    }
    return null;
  }


  /**
   * Returns the number of {@link SpeciesTypeComponentIndex}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link SpeciesTypeComponentIndex}s in this
   *         {@link MultiSpeciesType}.
   */
  public int getSpeciesTypeComponentIndexCount() {
    return isSetListOfSpeciesTypeComponentIndexes() ? getListOfSpeciesTypeComponentIndexes().size() : 0;
  }


  /**
   * Gets an element from the {@link #listOfSpeciesTypeInstances} at the given index.
   *
   * @param i the index of the {@link SpeciesTypeInstance} element to get.
   * @return an element from the listOfSpeciesTypeInstances at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public SpeciesTypeInstance getSpeciesTypeInstance(int i) {
    if (!isSetListOfSpeciesTypeInstances()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesTypeInstances().get(i);
  }


  /**
   * Gets an element from the listOfSpeciesTypeInstances, with the given id.
   *
   * @param speciesTypeInstanceId the id of the {@link SpeciesTypeInstance} element to get.
   * @return an element from the listOfSpeciesTypeInstances with the given id
   *         or {@code null}.
   */
  public SpeciesTypeInstance getSpeciesTypeInstance(String speciesTypeInstanceId) {
    if (isSetListOfSpeciesTypeInstances()) {
      return getListOfSpeciesTypeInstances().get(speciesTypeInstanceId);
    }
    return null;
  }


  /**
   * Returns the number of {@link SpeciesTypeInstance}s in this
   * {@link MultiSpeciesType}.
   * 
   * @return the number of {@link SpeciesTypeInstance}s in this
   *         {@link MultiSpeciesType}.
   */
  public int getSpeciesTypeInstanceCount() {
    return isSetListOfSpeciesTypeInstances() ? getListOfSpeciesTypeInstances().size() : 0;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 5807;
    int result = super.hashCode();
    result = prime * result
        + ((compartment == null) ? 0 : compartment.hashCode());
    result = prime
        * result
        + ((listOfInSpeciesTypeBonds == null) ? 0
          : listOfInSpeciesTypeBonds.hashCode());
    result = prime
        * result
        + ((listOfSpeciesFeatureTypes == null) ? 0
          : listOfSpeciesFeatureTypes.hashCode());
    result = prime
        * result
        + ((listOfSpeciesTypeComponentIndexes == null) ? 0
          : listOfSpeciesTypeComponentIndexes.hashCode());
    result = prime
        * result
        + ((listOfSpeciesTypeInstances == null) ? 0
          : listOfSpeciesTypeInstances.hashCode());
    return result;
  }


  /**
   * Initializes the default values.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isCompartmentMandatory()
   */
  @Override
  public boolean isCompartmentMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SpeciesType#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartment()
   */
  @Override
  public boolean isSetCompartment() {
    return (compartment != null) && (compartment.length() > 0);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartmentInstance()
   */
  @Override
  public boolean isSetCompartmentInstance() {
    return getCompartmentInstance() != null;
  }


  /**
   * Returns {@code true} if {@link #listOfInSpeciesTypeBonds} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfInSpeciesTypeBonds} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfInSpeciesTypeBonds() {
    if (listOfInSpeciesTypeBonds == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatureTypes} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatureTypes} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSpeciesFeatureTypes() {
    if (listOfSpeciesFeatureTypes == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesTypeComponentIndexes} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSpeciesTypeComponentIndexes} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSpeciesTypeComponentIndexes() {
    if (listOfSpeciesTypeComponentIndexes == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesTypeInstances} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSpeciesTypeInstances} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSpeciesTypeInstances() {
    if (listOfSpeciesTypeInstances == null) {
      return false;
    }
    return true;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.compartment)) {
        setCompartment(value);
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;

  }


  /**
   * Removes an element from the {@link #listOfInSpeciesTypeBonds}.
   *
   * @param inSpeciesTypeBond the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeInSpeciesTypeBond(InSpeciesTypeBond inSpeciesTypeBond) {
    if (isSetListOfInSpeciesTypeBonds()) {
      return getListOfInSpeciesTypeBonds().remove(inSpeciesTypeBond);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfInSpeciesTypeBonds} at the given index.
   *
   * @param i the index where to remove the {@link InSpeciesTypeBond}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfInSpeciesTypeBonds)}).
   */
  public InSpeciesTypeBond removeInSpeciesTypeBond(int i) {
    if (!isSetListOfInSpeciesTypeBonds()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfInSpeciesTypeBonds().remove(i);
  }


  /**
   * Removes an element from the {@link #listOfInSpeciesTypeBonds}.
   *
   * @param inSpeciesTypeBondId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public InSpeciesTypeBond removeInSpeciesTypeBond(String inSpeciesTypeBondId) {
    if (isSetListOfInSpeciesTypeBonds()) {
      return getListOfInSpeciesTypeBonds().remove(inSpeciesTypeBondId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureTypes} at the given index.
   *
   * @param i the index where to remove the {@link SpeciesFeatureType}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSpeciesFeatureTypes)}).
   */
  public SpeciesFeatureType removeSpeciesFeatureType(int i) {
    if (!isSetListOfSpeciesFeatureTypes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesFeatureTypes().remove(i);
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureTypes}.
   *
   * @param speciesFeatureType the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSpeciesFeatureType(SpeciesFeatureType speciesFeatureType) {
    if (isSetListOfSpeciesFeatureTypes()) {
      return getListOfSpeciesFeatureTypes().remove(speciesFeatureType);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesFeatureTypes}.
   *
   * @param speciesFeatureTypeId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public SpeciesFeatureType removeSpeciesFeatureType(String speciesFeatureTypeId) {
    if (isSetListOfSpeciesFeatureTypes()) {
      return getListOfSpeciesFeatureTypes().remove(speciesFeatureTypeId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeComponentIndexes} at the given index.
   *
   * @param i the index where to remove the {@link SpeciesTypeComponentIndex}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSpeciesTypeComponentIndexes)}).
   */
  public SpeciesTypeComponentIndex removeSpeciesTypeComponentIndex(int i) {
    if (!isSetListOfSpeciesTypeComponentIndexes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesTypeComponentIndexes().remove(i);
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeComponentIndexes}.
   *
   * @param speciesTypeComponentIndex the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSpeciesTypeComponentIndex(SpeciesTypeComponentIndex speciesTypeComponentIndex) {
    if (isSetListOfSpeciesTypeComponentIndexes()) {
      return getListOfSpeciesTypeComponentIndexes().remove(speciesTypeComponentIndex);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeComponentIndexes}.
   *
   * @param speciesTypeComponentIndexId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public SpeciesTypeComponentIndex removeSpeciesTypeComponentIndex(String speciesTypeComponentIndexId) {
    if (isSetListOfSpeciesTypeComponentIndexes()) {
      return getListOfSpeciesTypeComponentIndexes().remove(speciesTypeComponentIndexId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeInstances} at the given index.
   *
   * @param i the index where to remove the {@link SpeciesTypeInstance}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSpeciesTypeInstances)}).
   */
  public SpeciesTypeInstance removeSpeciesTypeInstance(int i) {
    if (!isSetListOfSpeciesTypeInstances()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesTypeInstances().remove(i);
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeInstances}.
   *
   * @param speciesTypeInstance the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSpeciesTypeInstance(SpeciesTypeInstance speciesTypeInstance) {
    if (isSetListOfSpeciesTypeInstances()) {
      return getListOfSpeciesTypeInstances().remove(speciesTypeInstance);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeInstances}.
   *
   * @param speciesTypeInstanceId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public SpeciesTypeInstance removeSpeciesTypeInstance(String speciesTypeInstanceId) {
    if (isSetListOfSpeciesTypeInstances()) {
      return getListOfSpeciesTypeInstances().remove(speciesTypeInstanceId);
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(org.sbml.jsbml.Compartment)
   */
  @Override
  public boolean setCompartment(Compartment compartment) {
    return setCompartment(compartment.getId());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(java.lang.String)
   */
  @Override
  public boolean setCompartment(String compartment) {
    if (compartment != this.compartment) {
      String oldCompartment = this.compartment;
      this.compartment = compartment;
      firePropertyChange(MultiConstants.compartment, oldCompartment, this.compartment);
      return true;
    }
    return false;
  }

  /**
   * Sets the given {@code ListOf<InSpeciesTypeBond>}.
   * If {@link #listOfInSpeciesTypeBonds} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfInSpeciesTypeBonds the list of {@link InSpeciesTypeBond}
   */
  public void setListOfInSpeciesTypeBonds(ListOf<InSpeciesTypeBond> listOfInSpeciesTypeBonds) {
    unsetListOfInSpeciesTypeBonds();
    this.listOfInSpeciesTypeBonds = listOfInSpeciesTypeBonds;

    if (listOfInSpeciesTypeBonds != null) {
      listOfInSpeciesTypeBonds.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfInSpeciesTypeBonds.setPackageName(null);
      listOfInSpeciesTypeBonds.setPackageName(MultiConstants.shortLabel);
      listOfInSpeciesTypeBonds.setSBaseListType(ListOf.Type.other);
      listOfInSpeciesTypeBonds.setOtherListName(MultiConstants.listOfInSpeciesTypeBonds);
      
      registerChild(listOfInSpeciesTypeBonds);
    }
  }


  /**
   * Sets the given {@code ListOf<SpeciesFeatureType>}.
   * If {@link #listOfSpeciesFeatureTypes} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSpeciesFeatureTypes the list of {@link SpeciesFeatureType}
   */
  public void setListOfSpeciesFeatureTypes(ListOf<SpeciesFeatureType> listOfSpeciesFeatureTypes) {
    unsetListOfSpeciesFeatureTypes();
    this.listOfSpeciesFeatureTypes = listOfSpeciesFeatureTypes;
    if (listOfSpeciesFeatureTypes != null) {
      listOfSpeciesFeatureTypes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesFeatureTypes.setPackageName(null);
      listOfSpeciesFeatureTypes.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesFeatureTypes.setSBaseListType(ListOf.Type.other);
      listOfSpeciesFeatureTypes.setOtherListName(MultiConstants.listOfSpeciesFeatureTypes);
      
      registerChild(this.listOfSpeciesFeatureTypes);
    }
  }


  /**
   * Sets the given {@code ListOf<SpeciesTypeComponentIndex>}.
   * If {@link #listOfSpeciesTypeComponentIndexes} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSpeciesTypeComponentIndexes the list of {@link SpeciesTypeComponentIndex}
   */
  public void setListOfSpeciesTypeComponentIndexes(ListOf<SpeciesTypeComponentIndex> listOfSpeciesTypeComponentIndexes) {
    unsetListOfSpeciesTypeComponentIndexes();
    this.listOfSpeciesTypeComponentIndexes = listOfSpeciesTypeComponentIndexes;

    if (listOfSpeciesTypeComponentIndexes != null) {
      listOfSpeciesTypeComponentIndexes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypeComponentIndexes.setPackageName(null);
      listOfSpeciesTypeComponentIndexes.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypeComponentIndexes.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypeComponentIndexes.setOtherListName(MultiConstants.listOfSpeciesTypeComponentIndexes);
      
      registerChild(listOfSpeciesTypeComponentIndexes);
    }
  }


  /**
   * Sets the given {@code ListOf<SpeciesTypeInstance>}.
   * If {@link #listOfSpeciesTypeInstances} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSpeciesTypeInstances the list of {@link SpeciesTypeInstance}
   */
  public void setListOfSpeciesTypeInstances(ListOf<SpeciesTypeInstance> listOfSpeciesTypeInstances) {
    unsetListOfSpeciesTypeInstances();
    this.listOfSpeciesTypeInstances = listOfSpeciesTypeInstances;
    if (listOfSpeciesTypeInstances != null) {
      listOfSpeciesTypeInstances.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypeInstances.setPackageName(null);
      listOfSpeciesTypeInstances.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypeInstances.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypeInstances.setOtherListName(MultiConstants.listOfSpeciesTypeInstances);
      
      registerChild(this.listOfSpeciesTypeInstances);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#unsetCompartment()
   */
  @Override
  public boolean unsetCompartment() {
    return setCompartment((String) null);
  }

  /**
   * Returns {@code true} if {@link #listOfInSpeciesTypeBonds} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfInSpeciesTypeBonds} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfInSpeciesTypeBonds() {
    if (isSetListOfInSpeciesTypeBonds()) {
      ListOf<InSpeciesTypeBond> oldInSpeciesTypeBonds = listOfInSpeciesTypeBonds;
      listOfInSpeciesTypeBonds = null;
      oldInSpeciesTypeBonds.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Returns {@code true} if {@link #listOfSpeciesFeatureTypes} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSpeciesFeatureTypes} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSpeciesFeatureTypes() {
    if (isSetListOfSpeciesFeatureTypes()) {
      ListOf<SpeciesFeatureType> oldSpeciesFeatureTypes = listOfSpeciesFeatureTypes;
      listOfSpeciesFeatureTypes = null;
      oldSpeciesFeatureTypes.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Returns {@code true} if {@link #listOfSpeciesTypeComponentIndexes} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSpeciesTypeComponentIndexes} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSpeciesTypeComponentIndexes() {
    if (isSetListOfSpeciesTypeComponentIndexes()) {
      ListOf<SpeciesTypeComponentIndex> oldSpeciesTypeComponentIndexs = listOfSpeciesTypeComponentIndexes;
      listOfSpeciesTypeComponentIndexes = null;
      oldSpeciesTypeComponentIndexs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Returns {@code true} if {@link #listOfSpeciesTypeInstances} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSpeciesTypeInstances} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSpeciesTypeInstances() {
    if (isSetListOfSpeciesTypeInstances()) {
      ListOf<SpeciesTypeInstance> oldSpeciesTypeInstances = listOfSpeciesTypeInstances;
      listOfSpeciesTypeInstances = null;
      oldSpeciesTypeInstances.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(MultiConstants.shortLabel+ ":name", getName());
    }

    if (isSetCompartment()) {
      attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.compartment, compartment);
    }

    return attributes;
  }

}
