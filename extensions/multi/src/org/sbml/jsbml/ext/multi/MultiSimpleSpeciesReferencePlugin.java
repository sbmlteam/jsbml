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

import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class MultiSimpleSpeciesReferencePlugin extends AbstractSBasePlugin {

  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 1L;
  /**
   * 
   */
  private String compartmentReference;

  /**
   * Creates an MultiSimpleSpeciesReferencePlugin instance
   */
  public MultiSimpleSpeciesReferencePlugin() {
    super();
    initDefaults();
  }


  /**
   * Creates a MultiSimpleSpeciesReferencePlugin instance with a {@link SimpleSpeciesReference}.
   * 
   * @param simpleSpeciesRef the {@link SimpleSpeciesReference} for the new element.
   */
  public MultiSimpleSpeciesReferencePlugin(SimpleSpeciesReference simpleSpeciesRef) {
    super(simpleSpeciesRef);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public MultiSimpleSpeciesReferencePlugin(MultiSimpleSpeciesReferencePlugin obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetCompartmentReference()) {
      setCompartmentReference(obj.getCompartmentReference());
    }
  }


  /**
   * clones this class
   */
  @Override
  public MultiSimpleSpeciesReferencePlugin clone() {
    return new MultiSimpleSpeciesReferencePlugin(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6271;
    int result = super.hashCode();
    result = prime * result
        + ((compartmentReference == null) ? 0 : compartmentReference.hashCode());
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
    MultiSimpleSpeciesReferencePlugin other = (MultiSimpleSpeciesReferencePlugin) obj;
    if (compartmentReference == null) {
      if (other.compartmentReference != null) {
        return false;
      }
    } else if (!compartmentReference.equals(other.compartmentReference)) {
      return false;
    }
    return true;
  }

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


  /**
   * Returns the value of {@link #compartmentReference}.
   *
   * @return the value of {@link #compartmentReference}.
   */
  public String getCompartmentReference() {
    if (isSetCompartmentReference()) {
      return compartmentReference;
    }

    return null;
  }


  /**
   * Returns whether {@link #compartmentReference} is set.
   *
   * @return whether {@link #compartmentReference} is set.
   */
  public boolean isSetCompartmentReference() {
    return compartmentReference != null;
  }


  /**
   * Sets the value of compartmentReference
   *
   * @param compartmentReference the value of compartmentReference to be set.
   */
  public void setCompartmentReference(String compartmentReference) {
    String oldCompartmentReference = this.compartmentReference;
    this.compartmentReference = compartmentReference;
    firePropertyChange(MultiConstants.compartmentReference, oldCompartmentReference, this.compartmentReference);
  }


  /**
   * Unsets the variable compartmentReference.
   *
   * @return {@code true} if compartmentReference was set before, otherwise {@code false}.
   */
  public boolean unsetCompartmentReference() {
    if (isSetCompartmentReference()) {
      String oldCompartmentReference = compartmentReference;
      compartmentReference = null;
      firePropertyChange(MultiConstants.compartmentReference, oldCompartmentReference, compartmentReference);
      return true;
    }
    return false;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetCompartmentReference()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.compartmentReference, getCompartmentReference());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = true;


    if (attributeName.equals(MultiConstants.compartmentReference)) {
      setCompartmentReference(value);
    }
    else {
      isAttributeRead = false;
    }

    return isAttributeRead;
  }

  @Override
  public boolean getAllowsChildren() {
    return false;
  }


  @Override
  public int getChildCount() {
    return 0;
  }


  @Override
  public TreeNode getChildAt(int index) {
    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(index, 0)));
  }
}
