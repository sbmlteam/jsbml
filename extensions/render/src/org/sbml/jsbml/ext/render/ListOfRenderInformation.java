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
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * Derives from the {@link ListOf} class and contains one or more objects of type {@link RenderInformationBase}.
 * 
 * <p>In addition the ListOfRenderInformation object has the optional attributes versionMajor and versionMinor
 * as well as an optional DefaultValues element that provides the default values for the {@link RenderInformationBase}
 * objects contained in the list.</p>
 * 
 * @author Nicolas Rodriguez
 *
 */
public abstract class ListOfRenderInformation<T extends RenderInformationBase> extends ListOf<T> {


  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1321032127025154698L;

  /**
   * 
   */
  private Short versionMajor;
  /**
   * 
   */
  private Short versionMinor;
  /**
   * 
   */
  private DefaultValues defaultValues;

  /**
   * Creates a new {@link ListOfRenderInformation} instance
   */
  public ListOfRenderInformation() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link ListOfRenderInformation} instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public ListOfRenderInformation(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public ListOfRenderInformation(ListOfRenderInformation<?> obj) {
    super(obj);

    if (obj.isSetVersionMinor()) {
      setVersionMinor(obj.getVersionMinor());
    }
    if (obj.isSetVersionMajor()) {
      setVersionMajor(obj.getVersionMajor());
    }
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3041;
    int result = super.hashCode();
    result = prime * result
        + ((versionMajor == null) ? 0 : versionMajor.hashCode());
    result = prime * result
        + ((versionMinor == null) ? 0 : versionMinor.hashCode());
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
    ListOfRenderInformation<?> other = (ListOfRenderInformation<?>) obj;
    if (versionMajor == null) {
      if (other.versionMajor != null) {
        return false;
      }
    } else if (!versionMajor.equals(other.versionMajor)) {
      return false;
    }
    if (versionMinor == null) {
      if (other.versionMinor != null) {
        return false;
      }
    } else if (!versionMinor.equals(other.versionMinor)) {
      return false;
    }
    return true;
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = RenderConstants.shortLabel;
    setPackageVersion(-1);
    setSBaseListType(ListOf.Type.other);
  }

  /**
   * Returns the value of versionMinor
   * 
   * @return the value of versionMinor
   */
  public short getVersionMinor() {
    if (isSetVersionMinor()) {
      return versionMinor;
    }
    throw new PropertyUndefinedError(RenderConstants.versionMinor, this);
  }

  /**
   * Returns {@code true} if versionMinor is set.
   * 
   * @return whether versionMinor is set
   */
  public boolean isSetVersionMinor() {
    return versionMinor != null;
  }

  /**
   * Sets the value of versionMinor
   * 
   * @param versionMinor the value of versionMinor
   */
  public void setVersionMinor(short versionMinor) {
    Short oldVersionMinor = this.versionMinor;
    this.versionMinor = versionMinor;
    firePropertyChange(RenderConstants.versionMinor, oldVersionMinor, this.versionMinor);
  }

  /**
   * Unsets the variable versionMinor
   * 
   * @return {@code true}, if versionMinor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetVersionMinor() {
    if (isSetVersionMinor()) {
      Short oldVersionMinor = versionMinor;
      versionMinor = null;
      firePropertyChange(RenderConstants.versionMinor, oldVersionMinor, versionMinor);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of versionMajor
   * 
   * @return the value of versionMajor
   */
  public short getVersionMajor() {
    if (isSetVersionMajor()) {
      return versionMajor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.versionMajor, this);
  }

  /**
   * Returns {@code true} if versionMajor is set.
   * 
   * @return whether versionMajor is set
   */
  public boolean isSetVersionMajor() {
    return versionMajor != null;
  }

  /**
   * Sets the value of versionMajor
   * 
   * @param versionMajor the value of versionMajor
   */
  public void setVersionMajor(short versionMajor) {
    Short oldVersionMajor = this.versionMajor;
    this.versionMajor = versionMajor;
    firePropertyChange(RenderConstants.versionMajor, oldVersionMajor, this.versionMajor);
  }

  /**
   * @param versionMajor
   * @see #setVersionMajor(short)
   */
  public void setVersionMajor(int versionMajor) {
    setVersionMajor((short) versionMajor);
  }

  /**
   * @param versionMinor
   * @see #setVersionMinor(short)
   */
  public void setVersionMinor(int versionMinor) {
    setVersionMinor((short) versionMinor);
  }

  /**
   * Unsets the variable versionMajor
   * 
   * @return {@code true}, if versionMajor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetVersionMajor() {
    if (isSetVersionMajor()) {
      Short oldVersionMajor = versionMajor;
      versionMajor = null;
      firePropertyChange(RenderConstants.versionMajor, oldVersionMajor, versionMajor);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #defaultValues}.
   *
   * @return the value of {@link #defaultValues}.
   */
  public DefaultValues getDefaultValues() {
    if (isSetDefaultValues()) {
      return defaultValues;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(RenderConstants.defaultValues, this);
  }

  /**
   * Returns whether {@link #defaultValues} is set.
   *
   * @return whether {@link #defaultValues} is set.
   */
  public boolean isSetDefaultValues() {
    return defaultValues != null;
  }

  /**
   * Sets the value of defaultValues
   *
   * @param defaultValues the value of defaultValues to be set.
   */
  public void setDefaultValues(DefaultValues defaultValues) {
    DefaultValues oldDefaultValues = this.defaultValues;
    this.defaultValues = defaultValues;
    firePropertyChange(RenderConstants.defaultValues, oldDefaultValues, this.defaultValues);
  }

  /**
   * Unsets the variable defaultValues.
   *
   * @return {@code true} if defaultValues was set before, otherwise {@code false}.
   */
  public boolean unsetDefaultValues() {
    if (isSetDefaultValues()) {
      DefaultValues oldDefaultValues = this.defaultValues;
      this.defaultValues = null;
      firePropertyChange(RenderConstants.defaultValues, oldDefaultValues, this.defaultValues);
      return true;
    }
    return false;
  }

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetDefaultValues()) {
      count++;
    }

    return count;
  }

  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }

    int count = super.getChildCount(), pos = 0;

    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetDefaultValues()) {
      if (pos == index) {
        return getDefaultValues();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(
      MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",
        index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetVersionMajor()) {
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.versionMajor, versionMajor.toString());
    }

    if (isSetVersionMinor()) {
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.versionMinor, versionMinor.toString());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(RenderConstants.versionMajor)) {
        setVersionMajor(StringTools.parseSBMLShort(value));
      }
      else if (attributeName.equals(RenderConstants.versionMinor)) {
        setVersionMinor(StringTools.parseSBMLShort(value));
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

}
