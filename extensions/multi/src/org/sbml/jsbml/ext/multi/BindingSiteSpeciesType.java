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
package org.sbml.jsbml.ext.multi;

/**
 * A {@link BindingSiteSpeciesType} object is a binding site, and therefore its instance can further define the bindingStatus
 * attribute and can participate a binding internally and explicitly in an {@link InSpeciesTypeBond} object, or externally and
 * implicitly defined by an {@link OutwardBindingSite} object. A binding site must be an atomic component which means
 * that a {@link BindingSiteSpeciesType} object cannot contain a ListOfSpeciesTypeInstances subobject.
 * 
 * <p>Note:<br/>
 * In the Multi package, a binding site can only participate in one binding at a time. That means a binding site cannot
 * bind two partners at the same time. The binding relationship is one-to-one.</p>
 * 
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class BindingSiteSpeciesType extends MultiSpeciesType {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 290729813426543362L;

  /**
   * Creates a new {@link BindingSiteSpeciesType} instance.
   */
  public BindingSiteSpeciesType() {
    super();
    initDefaults();
  }


  /**
   * Creates a new {@link BindingSiteSpeciesType} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public BindingSiteSpeciesType(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Creates a new {@link BindingSiteSpeciesType} instance, cloned from the given object.
   * 
   * @param obj the {@link BindingSiteSpeciesType} instance to clone.
   */
  public BindingSiteSpeciesType(BindingSiteSpeciesType obj) {
    super(obj);
  }


  /**
   * Creates a new {@link BindingSiteSpeciesType} instance.
   * 
   * @param id the identifier for this element.
   */
  public BindingSiteSpeciesType(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a new {@link BindingSiteSpeciesType} instance.
   * 
   * @param id the identifier for this element.
   * @param level the SBML level
   * @param version the SBML version
   */
  public BindingSiteSpeciesType(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }


  /**
   * Creates a new {@link BindingSiteSpeciesType} instance.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML level
   * @param version the SBML version
   */
  public BindingSiteSpeciesType(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link BindingSiteSpeciesType} instance, cloned from itself.
   */
  @Override
  public BindingSiteSpeciesType clone() {
    return new BindingSiteSpeciesType(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  @Override
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return MultiConstants.bindingSiteSpeciesType;
  }

}
