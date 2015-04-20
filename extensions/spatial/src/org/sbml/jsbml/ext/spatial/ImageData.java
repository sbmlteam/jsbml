/*
 * $Id: ImageData.java 2181 2015-04-09 13:44:21Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/spatial/src/org/sbml/jsbml/ext/spatial/ImageData.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev: 2181 $
 */
public class ImageData extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1872012239027099782L;

  /**
   * 
   */
  private Integer samplesLength;
  /**
   * 
   */
  private Integer samples[];

  /**
   * 
   */
  private String dataType;

  /**
   * 
   */
  public ImageData() {
    super();
    initDefaults();
  }

  /**
   * @param im
   */
  public ImageData(ImageData im) {
    super(im);
    if (im.isSetSamples()) {
      samples = im.getSamples().clone();
      samplesLength = samples.length;
    }
    if (im.isSetDataType()) {
      dataType = new String(im.getDataType());
    }
  }

  /**
   * @param level
   * @param version
   */
  public ImageData(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public ImageData clone() {
    return new ImageData(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(SpatialConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      ImageData im = (ImageData) object;

      equal &= im.isSetSamples() == isSetSamples();
      if (equal && isSetSamples()) {
        equal &= im.getSamples().equals(getSamples());
      }

      equal &= im.isSetDataType() == isSetDataType();
      if (equal && isSetDataType()) {
        equal &= im.getDataType().equals(getDataType());
      }

    }
    return equal;
  }


  /**
   * Returns the value of samples
   *
   * @return the value of samples
   */
  public Integer[] getSamples() {
    if (isSetSamples()) {
      return samples;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.samples, this);
  }


  /**
   * Returns whether samples is set
   *
   * @return whether samples is set
   */
  public boolean isSetSamples() {
    return samples != null;
  }

  /**
   * Sets the value of samples
   * @param samples
   */
  public void setSamples(Integer... samples) {
    Integer[] oldSamples = this.samples;
    this.samples = samples;
    samplesLength = samples.length;
    firePropertyChange(SpatialConstants.samples, oldSamples, this.samples);
    firePropertyChange(SpatialConstants.samples, oldSamples.length, samplesLength);
  }

  /**
   * Unsets the variable samples
   *
   * @return {@code true}, if samples was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSamples() {
    if (isSetSamples()) {
      Integer[] oldSamples = samples;
      samples = null;
      samplesLength = null;
      firePropertyChange(SpatialConstants.samples, oldSamples, samples);
      firePropertyChange(SpatialConstants.samplesLength, oldSamples.length, samplesLength);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of samplesLength
   *
   * @return the value of samplesLength
   */
  public int getSamplesLength() {
    if (isSetSamplesLength()) {
      return samplesLength;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.samplesLength, this);
  }


  /**
   * Returns whether samplesLength is set
   *
   * @return whether samplesLength is set
   */
  public boolean isSetSamplesLength() {
    return samplesLength != null;
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
    return null;
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
   * @param dataType
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetSamples()) {
      hashCode += prime * getSamples().hashCode();
    }
    if (isSetDataType()) {
      hashCode += prime * getDataType().hashCode();
    }

    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSamples()) {
      attributes.remove("samples");
      attributes.put(SpatialConstants.shortLabel + ":samples", Arrays.toString(getSamples()));
    }

    if (isSetSamplesLength()) {
      attributes.remove("samplesLength");
      attributes.put(SpatialConstants.shortLabel + ":samplesLength",
        String.valueOf(getSamplesLength()));
    }

    if (isSetDataType()) {
      attributes.remove("dataType");
      attributes.put(SpatialConstants.shortLabel + ":dataType",
        getDataType());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.samples)) {
        StringTokenizer test = new StringTokenizer(value);
        Integer[] samplesTemp = new Integer[test.countTokens()];
        int i = 0;
        while(test.hasMoreTokens()) {
          try {
            samplesTemp[i] = StringTools.parseSBMLInt(test.nextToken());
            i++;
          } catch (Exception e) {
            MessageFormat.format(
              SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
              SpatialConstants.pointIndex);
          }
        }
        if (samplesTemp.length > 0) {
          unsetSamples();
          setSamples(samplesTemp);
        }
      }
      else if (attributeName.equals(SpatialConstants.dataType)) {
        try {
          setDataType(value);
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.dataType);
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
    builder.append("ImageData [samplesLength=");
    builder.append(samplesLength);
    builder.append(", dataType=");
    builder.append(dataType);
    builder.append(", samples=");
    builder.append(Arrays.toString(samples)); // TODO - Should we really print the whole content of the array here? 
    builder.append("]");
    return builder.toString();
  }

}
