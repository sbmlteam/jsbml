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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

import de.zbit.util.ResourceManager;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SampledField extends AbstractSpatialNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 4345673559248715940L;

  private String dataType;
  private Integer numSamples1;
  private Integer numSamples2;
  private Integer numSamples3;
  private String interpolationType;
  private String encoding;
  private ImageData imageData;

  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");

  /**
   * 
   */
  public SampledField() {
    super();
  }

  /**
   * @param node
   */
  public SampledField(SampledField sf) {
    super(sf);

    if (sf.isSetDataType()) {
      dataType = new String(sf.getDataType());
    }

    if (sf.isSetInterpolationType()) {
      interpolationType = new String(sf.getInterpolationType());
    }

    if (sf.isSetEncoding()) {
      encoding = new String(sf.getEncoding());
    }

    if (sf.isSetNumSamples1()) {
      numSamples1 = new Integer(sf.getNumSamples1());
    }

    if (sf.isSetNumSamples2()) {
      numSamples2 = new Integer(sf.getNumSamples2());
    }

    if (sf.isSetNumSamples3()) {
      numSamples3 = new Integer(sf.getNumSamples3());
    }

    if (sf.isSetImageData()) {
      imageData = sf.getImageData().clone();
    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public SampledField clone() {
    return new SampledField(this);
  }


  /**
   * Returns the value of dataType
   *
   * @return the value of dataType
   */
  public String getDataType() {
    if (isSetDataType()) {
      return dataType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.dataType, this);
  }


  /**
   * Returns whether dataType is set
   *
   * @return whether dataType is set
   */
  public boolean isSetDataType() {
    return dataType != null;
  }


  /**
   * Sets the value of dataType
   */
  public void setDataType(String dataType) {
    String oldDataType = this.dataType;
    this.dataType = dataType;
    firePropertyChange(SpatialConstants.dataType, oldDataType, this.dataType);
  }


  /**
   * Unsets the variable dataType
   *
   * @return {@code true}, if dataType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDataType() {
    if (isSetDataType()) {
      String oldDataType = dataType;
      dataType = null;
      firePropertyChange(SpatialConstants.dataType, oldDataType, dataType);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of numSamples1
   *
   * @return the value of numSamples1
   */
  public int getNumSamples1() {
    if (isSetNumSamples1()) {
      return numSamples1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.numSamples1, this);
  }


  /**
   * Returns whether numSamples1 is set
   *
   * @return whether numSamples1 is set
   */
  public boolean isSetNumSamples1() {
    return numSamples1 != null;
  }


  /**
   * Sets the value of numSamples1
   */
  public void setNumSamples1(int numSamples1) {
    int oldNumSamples1 = this.numSamples1;
    this.numSamples1 = numSamples1;
    firePropertyChange(SpatialConstants.numSamples1, oldNumSamples1, this.numSamples1);
  }


  /**
   * Unsets the variable numSamples1
   *
   * @return {@code true}, if numSamples1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetNumSamples1() {
    if (isSetNumSamples1()) {
      int oldNumSamples1 = numSamples1;
      numSamples1 = null;
      firePropertyChange(SpatialConstants.numSamples1, oldNumSamples1, numSamples1);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of numSamples2
   *
   * @return the value of numSamples2
   */
  public int getNumSamples2() {
    if (isSetNumSamples2()) {
      return numSamples2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.numSamples2, this);
  }


  /**
   * Returns whether numSamples2 is set
   *
   * @return whether numSamples2 is set
   */
  public boolean isSetNumSamples2() {
    return numSamples2 != null;
  }


  /**
   * Sets the value of numSamples2
   */
  public void setNumSamples2(int numSamples2) {
    int oldNumSamples2 = this.numSamples2;
    this.numSamples2 = numSamples2;
    firePropertyChange(SpatialConstants.numSamples2, oldNumSamples2, this.numSamples2);
  }


  /**
   * Unsets the variable numSamples2
   *
   * @return {@code true}, if numSamples2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetNumSamples2() {
    if (isSetNumSamples2()) {
      int oldNumSamples2 = numSamples2;
      numSamples2 = null;
      firePropertyChange(SpatialConstants.numSamples2, oldNumSamples2, numSamples2);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of numSamples3
   *
   * @return the value of numSamples3
   */
  public int getNumSamples3() {
    if (isSetNumSamples3()) {
      return numSamples3;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.numSamples3, this);
  }


  /**
   * Returns whether numSamples3 is set
   *
   * @return whether numSamples3 is set
   */
  public boolean isSetNumSamples3() {
    return numSamples3 != null;
  }


  /**
   * Sets the value of numSamples3
   */
  public void setNumSamples3(int numSamples3) {
    int oldNumSamples3 = this.numSamples3;
    this.numSamples3 = numSamples3;
    firePropertyChange(SpatialConstants.numSamples3, oldNumSamples3, this.numSamples3);
  }


  /**
   * Unsets the variable numSamples3
   *
   * @return {@code true}, if numSamples3 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetNumSamples3() {
    if (isSetNumSamples3()) {
      int oldNumSamples3 = numSamples3;
      numSamples3 = null;
      firePropertyChange(SpatialConstants.numSamples3, oldNumSamples3, numSamples3);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of interpolationType
   *
   * @return the value of interpolationType
   */
  public String getInterpolationType() {
    if (isSetInterpolationType()) {
      return interpolationType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.interpolationType, this);
  }


  /**
   * Returns whether interpolationType is set
   *
   * @return whether interpolationType is set
   */
  public boolean isSetInterpolationType() {
    return interpolationType != null;
  }


  /**
   * Sets the value of interpolationType
   */
  public void setInterpolationType(String interpolationType) {
    String oldInterpolationType = this.interpolationType;
    this.interpolationType = interpolationType;
    firePropertyChange(SpatialConstants.interpolationType, oldInterpolationType, this.interpolationType);
  }


  /**
   * Unsets the variable interpolationType
   *
   * @return {@code true}, if interpolationType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetInterpolationType() {
    if (isSetInterpolationType()) {
      String oldInterpolationType = interpolationType;
      interpolationType = null;
      firePropertyChange(SpatialConstants.interpolationType, oldInterpolationType, interpolationType);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of encoding
   *
   * @return the value of encoding
   */
  public String getEncoding() {
    if (isSetEncoding()) {
      return encoding;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.encoding, this);
  }


  /**
   * Returns whether encoding is set
   *
   * @return whether encoding is set
   */
  public boolean isSetEncoding() {
    return encoding != null;
  }


  /**
   * Sets the value of encoding
   */
  public void setEncoding(String encoding) {
    String oldEncoding = this.encoding;
    this.encoding = encoding;
    firePropertyChange(SpatialConstants.encoding, oldEncoding, this.encoding);
  }


  /**
   * Unsets the variable encoding
   *
   * @return {@code true}, if encoding was set before,
   *         otherwise {@code false}
   */
  public boolean unsetEncoding() {
    if (isSetEncoding()) {
      String oldEncoding = encoding;
      encoding = null;
      firePropertyChange(SpatialConstants.encoding, oldEncoding, encoding);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of imageData
   *
   * @return the value of imageData
   */
  public ImageData getImageData() {
    if (isSetImageData()) {
      return imageData;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.imageData, this);
  }


  /**
   * Returns whether imageData is set
   *
   * @return whether imageData is set
   */
  public boolean isSetImageData() {
    return imageData != null;
  }


  /**
   * Sets the value of imageData
   */
  public void setImageData(ImageData imageData) {
    ImageData oldImageData = this.imageData;
    this.imageData = imageData;
    firePropertyChange(SpatialConstants.imageData, oldImageData, this.imageData);
  }


  /**
   * Unsets the variable imageData
   *
   * @return {@code true}, if imageData was set before,
   *         otherwise {@code false}
   */
  public boolean unsetImageData() {
    if (isSetImageData()) {
      ImageData oldImageData = imageData;
      imageData = null;
      firePropertyChange(SpatialConstants.imageData, oldImageData, imageData);
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
    if (isSetImageData()) {
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
    if (isSetImageData()) {
      if (pos == index) {
        return getImageData();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", index,
      +Math.min(pos, 0)));
  }


  @Override
  public int hashCode() {
    final int prime = 421;
    int hashCode = super.hashCode();
    if (isSetDataType()) {
      hashCode += prime * getDataType().hashCode();
    }

    if (isSetEncoding()) {
      hashCode += prime * getEncoding().hashCode();
    }

    if (isSetInterpolationType()) {
      hashCode += prime * getInterpolationType().hashCode();
    }

    if (isSetNumSamples1()) {
      hashCode += prime * getNumSamples1();
    }

    if (isSetNumSamples2()) {
      hashCode += prime * getNumSamples2();
    }

    if (isSetNumSamples3()) {
      hashCode += prime * getNumSamples3();
    }

    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetDataType()) {
      attributes.remove("dataType");
      attributes.put(SpatialConstants.shortLabel + ":dataType", getDataType());
    }

    if (isSetNumSamples1()) {
      attributes.remove("numSamples1");
      attributes.put(SpatialConstants.shortLabel + ":numSamples1",
        String.valueOf(getNumSamples1()));
    }

    if (isSetNumSamples2()) {
      attributes.remove("numSamples2");
      attributes.put(SpatialConstants.shortLabel + ":numSamples2",
        String.valueOf(getNumSamples2()));
    }

    if (isSetNumSamples3()) {
      attributes.remove("numSamples3");
      attributes.put(SpatialConstants.shortLabel + ":numSamples3",
        String.valueOf(getNumSamples3()));
    }


    if (isSetInterpolationType()) {
      attributes.remove("interpolationType");
      attributes.put(SpatialConstants.shortLabel + ":interpolationType",
        getInterpolationType());
    }

    if (isSetEncoding()) {
      attributes.remove("encoding");
      attributes.put(SpatialConstants.shortLabel + ":encoding",
        getEncoding());
    }


    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.dataType)) {
        try {
          setDataType(value);
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.dataType);
        }
      }
      else if (attributeName.equals(SpatialConstants.interpolationType)) {
        try {
          setInterpolationType(value);
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.interpolationType);
        }
      }
      else if (attributeName.equals(SpatialConstants.encoding)) {
        try {
          setEncoding(value);
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.encoding);
        }
      }
      else if (attributeName.equals(SpatialConstants.numSamples1)) {
        try {
          setNumSamples1(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.numSamples1);
        }
      }
      else if (attributeName.equals(SpatialConstants.numSamples2)) {
        try {
          setNumSamples2(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.numSamples2);
        }
      }
      else if (attributeName.equals(SpatialConstants.numSamples3)) {
        try {
          setNumSamples3(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.numSamples3);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("SampledField [dataType=");
    builder.append(dataType);
    builder.append(", numSamples1=");
    builder.append(numSamples1);
    builder.append(", numSamples2=");
    builder.append(numSamples2);
    builder.append(", numSamples3=");
    builder.append(numSamples3);
    builder.append(", interpolationType=");
    builder.append(interpolationType);
    builder.append(", encoding=");
    builder.append(encoding);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SampledField sf = (SampledField) object;

      equal &= sf.isSetDataType() == isSetDataType();
      if (equal && isSetDataType()) {
        equal &= sf.getDataType().equals(getDataType());
      }

      equal &= sf.isSetNumSamples1() == isSetNumSamples1();
      if (equal && isSetNumSamples1()) {
        equal &= sf.getNumSamples1() == getNumSamples1();
      }

      equal &= sf.isSetNumSamples2() == isSetNumSamples2();
      if (equal && isSetNumSamples2()) {
        equal &= sf.getNumSamples2() == getNumSamples2();
      }

      equal &= sf.isSetNumSamples3() == isSetNumSamples3();
      if (equal && isSetNumSamples3()) {
        equal &= sf.getNumSamples3() == getNumSamples3();
      }

      equal &= sf.isSetEncoding() == isSetEncoding();
      if (equal && isSetEncoding()) {
        equal &= sf.getEncoding().equals(getEncoding());
      }

      equal &= sf.isSetInterpolationType() == isSetInterpolationType();
      if (equal && isSetInterpolationType()) {
        equal &= sf.getInterpolationType().equals(getInterpolationType());
      }

    }
    return equal;
  }



}
