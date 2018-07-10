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
package org.sbml.jsbml;

import java.util.Map;

import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * The base class of {@link SpeciesReference} and
 * {@link ModifierSpeciesReference}.
 * 
 * @author Simon Sch&auml;fer
 * @author Marine Dumousseau
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public abstract class SimpleSpeciesReference extends AbstractNamedSBase
implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -504780573593345060L;

  /**
   * Represents the 'species' XML attribute.
   */
  private String speciesID;
  /**
   * Creates a SimpleSpeciesReference instance. By default, the speciesId is
   * {@code null}.
   */
  public SimpleSpeciesReference() {
    super();
    speciesID = null;
  }

  /**
   * Creates a SimpleSpeciesReference instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public SimpleSpeciesReference(int level, int version) {
    this(null, level, version);
  }

  /**
   * Creates a SimpleSpeciesReference instance from a given
   * SimpleSpeciesReference.
   * 
   * @param ssr
   */
  public SimpleSpeciesReference(SimpleSpeciesReference ssr) {
    super(ssr);
    speciesID = ssr.isSetSpecies() ? new String(ssr.getSpecies())
      : null;
  }

  /**
   * Creates a SimpleSpeciesReference instance from a given Species.
   * 
   * @param species
   */
  public SimpleSpeciesReference(Species species) {
    this(species.getLevel(), species.getVersion());
    speciesID = species.isSetId() ? new String(species.getId()) : null;
  }

  /**
   * Creates a SimpleSpeciesReference instance.
   * @param id
   */
  public SimpleSpeciesReference(String id) {
    super(id);
  }

  /**
   * Creates a SimpleSpeciesReference instance.
   * 
   * @param id
   * @param level the SBML level
   * @param version the SBML version
   */
  public SimpleSpeciesReference(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a SimpleSpeciesReference instance.
   * 
   * @param id
   * @param name
   * @param level the SBML level
   * @param version the SBML version
   */
  public SimpleSpeciesReference(String id, String name, int level, int version) {
    super(id, name, level, version);
    speciesID = null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      SimpleSpeciesReference ssr = (SimpleSpeciesReference) object;
      equals &= isSetSpecies() == ssr.isSetSpecies();
      if (equals && isSetSpecies()) {
        equals &= ssr.getSpecies().equals(speciesID);
      }
    }
    return equals;
  }

  /**
   * 
   * @return the speciesID of this {@link Species}. The empty String if it is not set.
   */
  public String getSpecies() {
    return isSetSpecies() ? speciesID : "";
  }

  /**
   * 
   * @return The Species instance which has the speciesID of this
   *         SimpleSpeciesReference as id. Can be null if it doesn't exist.
   */
  public Species getSpeciesInstance() {
    Model m = getModel();
    return m != null ? m.getSpecies(speciesID) : null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 883;
    int hashCode = super.hashCode();
    if (isSetSpecies()) {
      hashCode += prime * getSpecies().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * 
   * @return {@code true} if this speciesID of this {@link SimpleSpeciesReference} is not {@code null}.
   */
  public boolean isSetSpecies() {
    return speciesID != null;
  }

  /**
   * 
   * @return {@code true} if the Species instance which has the speciesID of this
   *         {@link SimpleSpeciesReference} as id is not {@code null}.
   */
  public boolean isSetSpeciesInstance() {
    return getSpeciesInstance() != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals("species")
          && (((getLevel() == 1) && (getVersion() == 2)) || (getLevel() > 1))) {
        setSpecies(value);
      } else if (attributeName.equals("specie") && (getLevel() == 1)
          && (getVersion() == 1)) {
        setSpecies(value);
      } else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /**
   * Sets the speciesID to the id of the {@link Species} 'species'.
   * 
   * @param species
   */
  public void setSpecies(Species species) {
    setSpecies((species != null) && (species.isSetId()) ? species.getId() : null);
  }

  /**
   * Sets the speciesID to 'spec'.
   * 
   * @param species
   */
  public void setSpecies(String species) {

    if (species != null && species.trim().length() == 0) {
      species = null;
    }

    if (!isReadingInProgress() && species != null) {
      // This method will throw IllegalArgumentException if the given id does not respect the SId syntax
      checkIdentifier(species);
    }

    String oldSpecies = speciesID;
    speciesID = species;
    firePropertyChange(TreeNodeChangeEvent.species, oldSpecies, speciesID);
  }

  /**
   * Human-readable representation of this reference.
   * 
   * @return
   */
  public String printSpeciesReference() {

    /*
     * this block seems to be quite slow !!

            if (isSetSpeciesInstance()) {
			return getSpeciesInstance().toString();
		}
     * 
     */
    if (isSetId() || isSetName()) {
      return super.toString();
    }
    if (isSetSpecies()) {
      return getSpecies();
    }

    return getElementName();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpecies()) {
      if (((getLevel() == 1) && (getVersion() == 2)) || (getLevel() > 1)) {
        attributes.put("species", getSpecies());
      } else if ((getLevel() == 1) && (getVersion() == 1)) {
        attributes.put("specie", getSpecies());
      }
    }
    return attributes;
  }

}
