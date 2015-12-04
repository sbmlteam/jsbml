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

import java.util.HashMap;
import java.util.Map;

import org.sbml.jsbml.SBMLException;

/**
 *
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.1
 */
public class MultiASTNodePlugin {

  /**
   * 
   */
  private String speciesReference;

  /**
   * 
   */
  private RepresentationType representationType;

  /**
   * 
   */
  public MultiASTNodePlugin() {
    // TODO
  }

  /**
   * Returns the value of {@link #speciesReference}.
   *
   * @return the value of {@link #speciesReference}.
   */
  public String getSpeciesReference() {
    if (isSetSpeciesReference()) {
      return speciesReference;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    // throw new PropertyUndefinedError(MultiConstants.speciesReference, this);
    return null;
  }


  /**
   * Returns whether {@link #speciesReference} is set.
   *
   * @return whether {@link #speciesReference} is set.
   */
  public boolean isSetSpeciesReference() {
    return speciesReference != null;
  }


  /**
   * Sets the value of speciesReference
   *
   * @param speciesReference the value of speciesReference to be set.
   */
  public void setSpeciesReference(String speciesReference) {
    String oldSpeciesReference = this.speciesReference;
    this.speciesReference = speciesReference;
    // firePropertyChange(MultiConstants.speciesReference, oldSpeciesReference, this.speciesReference);
  }


  /**
   * Unsets the variable speciesReference.
   *
   * @return {@code true} if speciesReference was set before, otherwise {@code false}.
   */
  public boolean unsetSpeciesReference() {
    if (isSetSpeciesReference()) {
      String oldSpeciesReference = speciesReference;
      speciesReference = null;
      // firePropertyChange(MultiConstants.speciesReference, oldSpeciesReference, this.speciesReference);
      return true;
    }
    return false;
  }



  /**
   * Returns the value of {@link #representationType}.
   *
   * @return the value of {@link #representationType}.
   */
  public RepresentationType getRepresentationType() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g., int instead of Integer)
    if (isSetRepresentationType()) {
      return representationType;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    // throw new PropertyUndefinedError(MultiConstants.representationType, this);

    return null;
  }


  /**
   * Returns whether {@link #representationType} is set.
   *
   * @return whether {@link #representationType} is set.
   */
  public boolean isSetRepresentationType() {
    return representationType != null;
  }


  /**
   * Sets the value of representationType
   *
   * @param representationType the value of representationType to be set.
   */
  public void setRepresentationType(RepresentationType representationType) {
    RepresentationType oldRepresentationType = this.representationType;
    this.representationType = representationType;
    // firePropertyChange(MultiConstants.representationType, oldRepresentationType, this.representationType);
  }


  /**
   * Unsets the variable representationType.
   *
   * @return {@code true} if representationType was set before, otherwise {@code false}.
   */
  public boolean unsetRepresentationType() {
    if (isSetRepresentationType()) {
      RepresentationType oldRepresentationType = representationType;
      representationType = null;
      // firePropertyChange(MultiConstants.representationType, oldRepresentationType, this.representationType);
      return true;
    }
    return false;
  }


  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = new HashMap<String, String>();

    if (isSetSpeciesReference()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.speciesReference, getSpeciesReference());
    }

    if (isSetRepresentationType()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.representationType, getRepresentationType().toString());
    }

    return attributes;
  }


  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = false;

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.speciesReference))
      {
        setSpeciesReference(value);
      }
      else if (attributeName.equals(MultiConstants.representationType))
      {
        try {
          setRepresentationType(RepresentationType.valueOf(value));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + MultiConstants.representationType
            + " on the 'ci' element.");
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((representationType == null) ? 0 : representationType.hashCode());
    result = prime * result
        + ((speciesReference == null) ? 0 : speciesReference.hashCode());
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
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MultiASTNodePlugin other = (MultiASTNodePlugin) obj;
    if (representationType != other.representationType) {
      return false;
    }
    if (speciesReference == null) {
      if (other.speciesReference != null) {
        return false;
      }
    } else if (!speciesReference.equals(other.speciesReference)) {
      return false;
    }
    return true;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "MultiASTNodePlugin [speciesReference=" + speciesReference
        + ", representationType=" + representationType + "]";
  }


}
