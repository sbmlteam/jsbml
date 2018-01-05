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

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Species;

/**
 * Defines a bond existing within a {@link MultiSpeciesType}. The bond therefore exists in every
 * {@link Species} that references the {@link MultiSpeciesType}.
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class InSpeciesTypeBond extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8781651881389720582L;

  /**
   * 
   */
  private String bindingSite1;

  /**
   * 
   */
  private String bindingSite2;


  /**
   * Creates an InSpeciesTypeBond instance
   */
  public InSpeciesTypeBond() {
    super();
    initDefaults();
  }


  /**
   * Creates a InSpeciesTypeBond instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public InSpeciesTypeBond(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a InSpeciesTypeBond instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public InSpeciesTypeBond(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a InSpeciesTypeBond instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public InSpeciesTypeBond(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a InSpeciesTypeBond instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public InSpeciesTypeBond(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(MultiConstants.MIN_SBML_LEVEL),
      Integer.valueOf(MultiConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public InSpeciesTypeBond(InSpeciesTypeBond obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetBindingSite1()) {
      setBindingSite1(obj.getBindingSite1());
    }
    if (obj.isSetBindingSite2()) {
      setBindingSite2(obj.getBindingSite2());
    }
  }


  /**
   * clones this class
   */
  @Override
  public InSpeciesTypeBond clone() {
    return new InSpeciesTypeBond(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 5791;
    int result = super.hashCode();
    result = prime * result
        + ((bindingSite1 == null) ? 0 : bindingSite1.hashCode());
    result = prime * result
        + ((bindingSite2 == null) ? 0 : bindingSite2.hashCode());
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
    InSpeciesTypeBond other = (InSpeciesTypeBond) obj;
    if (bindingSite1 == null) {
      if (other.bindingSite1 != null) {
        return false;
      }
    } else if (!bindingSite1.equals(other.bindingSite1)) {
      return false;
    }
    if (bindingSite2 == null) {
      if (other.bindingSite2 != null) {
        return false;
      }
    } else if (!bindingSite2.equals(other.bindingSite2)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #bindingSite1}.
   *
   * @return the value of {@link #bindingSite1}.
   */
  public String getBindingSite1() {
    if (isSetBindingSite1()) {
      return bindingSite1;
    }

    return null;
  }


  /**
   * Returns whether {@link #bindingSite1} is set.
   *
   * @return whether {@link #bindingSite1} is set.
   */
  public boolean isSetBindingSite1() {
    return bindingSite1 != null;
  }


  /**
   * Sets the value of bindingSite1
   *
   * @param bindingSite1 the value of bindingSite1 to be set.
   */
  public void setBindingSite1(String bindingSite1) {
    String oldBindingSite1 = this.bindingSite1;
    this.bindingSite1 = bindingSite1;
    firePropertyChange(MultiConstants.bindingSite1, oldBindingSite1, this.bindingSite1);
  }


  /**
   * Unsets the variable bindingSite1.
   *
   * @return {@code true} if bindingSite1 was set before, otherwise {@code false}.
   */
  public boolean unsetBindingSite1() {
    if (isSetBindingSite1()) {
      String oldBindingSite1 = bindingSite1;
      bindingSite1 = null;
      firePropertyChange(MultiConstants.bindingSite1, oldBindingSite1, bindingSite1);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #bindingSite2}.
   *
   * @return the value of {@link #bindingSite2}.
   */
  public String getBindingSite2() {
    if (isSetBindingSite2()) {
      return bindingSite2;
    }

    return null;
  }


  /**
   * Returns whether {@link #bindingSite2} is set.
   *
   * @return whether {@link #bindingSite2} is set.
   */
  public boolean isSetBindingSite2() {
    return bindingSite2 != null;
  }


  /**
   * Sets the value of bindingSite2
   *
   * @param bindingSite2 the value of bindingSite2 to be set.
   */
  public void setBindingSite2(String bindingSite2) {
    String oldBindingSite2 = this.bindingSite2;
    this.bindingSite2 = bindingSite2;
    firePropertyChange(MultiConstants.bindingSite2, oldBindingSite2, this.bindingSite2);
  }


  /**
   * Unsets the variable bindingSite2.
   *
   * @return {@code true} if bindingSite2 was set before, otherwise {@code false}.
   */
  public boolean unsetBindingSite2() {
    if (isSetBindingSite2()) {
      String oldBindingSite2 = bindingSite2;
      bindingSite2 = null;
      firePropertyChange(MultiConstants.bindingSite2, oldBindingSite2, bindingSite2);
      return true;
    }
    return false;
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

      if (attributeName.equals(MultiConstants.bindingSite1)) {
        setBindingSite1(value);
        isAttributeRead = true;
      } else if (attributeName.equals(MultiConstants.bindingSite2)) {
        setBindingSite2(value);
        isAttributeRead = true;
      }

    }

    return isAttributeRead;

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

    if (isSetBindingSite1()) {
      attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.bindingSite1, getBindingSite1());
    }
    if (isSetBindingSite2()) {
      attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.bindingSite2, getBindingSite2());
    }

    return attributes;
  }

  @Override
  public boolean isIdMandatory() {
    return false;
  }

}
