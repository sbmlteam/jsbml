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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.XMLNode;


/**
 * 
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class SampledField extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SampledField.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 4345673559248715940L;

  /**
   * 
   */
  private Integer numSamples1;
  /**
   * 
   */
  private Integer numSamples2;
  /**
   * 
   */
  private Integer numSamples3;
  /**
   * 
   */
  private InterpolationKind interpolation;
  /**
   * 
   */
  private CompressionKind compression;
  /**
   * 
   */
  private String samples;
  /**
   * 
   */
  private Integer samplesLength;
  /**
   * 
   */
  private DataKind dataType;
  /**
   * 
   */
  private XMLNode data;

  /**
   * 
   */
  public SampledField() {
    super();
  }

  /**
   * @param sf
   */
  public SampledField(SampledField sf) {
    super(sf);
    if (sf.isSetNumSamples1()) {
      numSamples1 = sf.getNumSamples1();
    }
    if (sf.isSetNumSamples2()) {
      numSamples2 = sf.getNumSamples2();
    }
    if (sf.isSetNumSamples3()) {
      numSamples3 = sf.getNumSamples3();
    }
    if (sf.isSetDataType()) {
      setDataType(sf.getDataType());
    }
    if (sf.isSetCompression()) {
      setCompression(sf.getCompression());
    }
    if (sf.isSetSamples()) {
      setSamples(sf.getSamples());
    }
    if (sf.isSetSamplesLength()) {
      setSamplesLength(sf.getSamplesLength());
    }
    if (sf.isSetInterpolation()) {
      setInterpolation(sf.getInterpolation());
    }
  }

  /**
   * @param level
   * @param version
   */
  public SampledField(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public SampledField(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public SampledField clone() {
    return new SampledField(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SampledField sf = (SampledField) object;

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
      equal &= sf.isSetCompression() == isSetCompression();
      if (equal && isSetCompression()) {
        equal &= sf.getCompression().equals(getCompression());
      }
      equal &= sf.isSetInterpolation() == isSetInterpolation();
      if (equal && isSetInterpolation()) {
        equal &= sf.getInterpolation().equals(getInterpolation());
      }
      equal &= sf.isSetSamplesLength() == isSetSamplesLength();
      if (equal && isSetSamplesLength()) {
        equal &= sf.getSamplesLength() == getSamplesLength();
      }
      equal &= sf.isSetSamples() == isSetSamples();
      if (equal && isSetSamples()) {
        equal &= sf.getSamples() == getSamples();
      }
      equal &= sf.isSetDataType() == isSetDataType();
      if (equal && isSetDataType()) {
        equal &= sf.getDataType().equals(getDataType());
      }
    }
    return equal;
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
   * @param numSamples1
   */
  public void setNumSamples1(int numSamples1) {
    Integer oldNumSamples1 = this.numSamples1;
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
      Integer oldNumSamples1 = numSamples1;
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
   * @param numSamples2
   */
  public void setNumSamples2(int numSamples2) {
    Integer oldNumSamples2 = this.numSamples2;
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
      Integer oldNumSamples2 = numSamples2;
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
   * @param numSamples3
   */
  public void setNumSamples3(int numSamples3) {
    Integer oldNumSamples3 = this.numSamples3;
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
      Integer oldNumSamples3 = numSamples3;
      numSamples3 = null;
      firePropertyChange(SpatialConstants.numSamples3, oldNumSamples3, numSamples3);
      return true;
    }
    return false;
  }
  /**
   * Returns the value of dataType
   *
   * @return the value of dataType
   */
  public DataKind getDataType() {
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
   * @param dataType
   */
  public void setDataType(DataKind dataType) {
    DataKind oldDataType = this.dataType;
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
      DataKind oldDataType = dataType;
      dataType = null;
      firePropertyChange(SpatialConstants.dataType, oldDataType, dataType);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of compression
   *
   * @return the value of compression
   */
  public CompressionKind getCompression() {
    if (isSetCompression()) {
      return compression;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.compression, this);
  }


  /**
   * Returns whether compression is set
   *
   * @return whether compression is set
   */
  public boolean isSetCompression() {
    return compression != null;
  }


  /**
   * Sets the value of compression
   * @param compression
   */
  public void setCompression(CompressionKind compression) {
    CompressionKind oldCompression = this.compression;
    this.compression = compression;
    firePropertyChange(SpatialConstants.compression, oldCompression, this.compression);
  }


  /**
   * Unsets the variable compression
   *
   * @return {@code true}, if compression was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCompression() {
    if (isSetCompression()) {
      CompressionKind oldCompression = compression;
      compression = null;
      firePropertyChange(SpatialConstants.compression, oldCompression, compression);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of interpolation type
   *
   * @return the value of interpolation type
   */
  public InterpolationKind getInterpolationType() {
    if (isSetInterpolation()) {
      return interpolation;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.interpolationType, this);
  }

  /**
   * Returns the value of interpolation type
   *
   * @return the value of interpolation type
   * @see SampledField#getInterpolationType()
   */
  public InterpolationKind getInterpolation() {
    return getInterpolationType();
  }


  /**
   * Returns whether interpolation type is set
   *
   * @return whether interpolation type is set
   * @see #isSetInterpolationType()
   */
  public boolean isSetInterpolation() {
    return isSetInterpolationType();
  }

  /**
   * Returns whether interpolation type is set
   *
   * @return whether interpolation type is set
   */
  public boolean isSetInterpolationType() {
    return interpolation != null;
  }


  /**
   * Sets the value of interpolation type
   * 
   * @param interpolation the value of interpolation type
   * @see #setInterpolationType(InterpolationKind)
   */
  public void setInterpolation(InterpolationKind interpolation) {
    setInterpolationType(interpolation);
  }

  /**
   * Sets the value of interpolation type
   * 
   * @param interpolation the value of interpolation type
   */
  public void setInterpolationType(InterpolationKind interpolation) {
    InterpolationKind oldInterpolation = this.interpolation;
    this.interpolation = interpolation;
    firePropertyChange(SpatialConstants.interpolationType, oldInterpolation, this.interpolation);
  }


  /**
   * Unsets the variable interpolation type
   *
   * @return {@code true}, if interpolation type was set before,
   *         otherwise {@code false}
   * @see #unsetInterpolationType()
   */
  public boolean unsetInterpolation() {
    return unsetInterpolationType();
  }

  /**
   * Unsets the variable interpolation type
   *
   * @return {@code true}, if interpolation type was set before,
   *         otherwise {@code false}
   */
  public boolean unsetInterpolationType() {
    if (isSetInterpolation()) {
      InterpolationKind oldInterpolation = interpolation;
      interpolation = null;
      firePropertyChange(SpatialConstants.interpolationType, oldInterpolation, interpolation);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of {@link #samples}.
   *
   * @return the value of {@link #samples}.
   */
  public String getSamples() {
    if (isSetSamples()) {
      return samples;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.samples, this);
  }


  /**
   * Returns whether {@link #samples} is set.
   *
   * @return whether {@link #samples} is set.
   */
  public boolean isSetSamples() {
    return samples != null;
  }


  /**
   * Sets the value of samples
   *
   * @param samples the value of samples to be set.
   */
  public void setSamples(String samples) {
    String oldSamples = this.samples;
    this.samples = samples;
    firePropertyChange(SpatialConstants.samples, oldSamples, this.samples);
  }


  /**
   * Unsets the variable samples.
   *
   * @return {@code true} if samples was set before, otherwise {@code false}.
   */
  public boolean unsetSamples() {
    if (isSetSamples()) {
      String oldSamples = samples;
      samples = null;
      samplesLength = null;
      firePropertyChange(SpatialConstants.samples, oldSamples, samples);
      return true;
    }
    return false;
  }

  /**
   * Appends the variable data to samples.
   * @return {@code true} if data was appended to samples, otherwise {@code false}.
   */
  public boolean append(String data) {
    if (data == null) { return false; }
    if (isSetSamples()) {
      String oldSamples = samples;
      samples = samples + data;
      firePropertyChange(SpatialConstants.samples, oldSamples, samples);
    } else {
      setSamples(data);
    }
    return true;
  }

  /**
   * Returns the value of {@link #samplesLength}.
   *
   * @return the value of {@link #samplesLength}.
   */
  public int getSamplesLength() {
    if (isSetSamplesLength()) {
      return samplesLength;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.samplesLength, this);
  }


  /**
   * Returns whether {@link #samplesLength} is set.
   *
   * @return whether {@link #samplesLength} is set.
   */
  public boolean isSetSamplesLength() {
    return samplesLength != null;
  }

  /**
   * Sets the value of samplesLength
   *
   * @param samplesLength the value of samplesLength to be set.
   */
  public void setSamplesLength(String samplesLength) {
    Integer oldSamplesLength = this.samplesLength;
    this.samplesLength = StringTools.parseSBMLInt(samplesLength);
    firePropertyChange(SpatialConstants.samplesLength, oldSamplesLength, this.samplesLength);
  }

  /**
   * Sets the value of samplesLength
   *
   * @param samplesLength the value of samplesLength to be set.
   */
  public void setSamplesLength(int samplesLength) {
    Integer oldSamplesLength = this.samplesLength;
    this.samplesLength = samplesLength;
    firePropertyChange(SpatialConstants.samplesLength, oldSamplesLength, this.samplesLength);
  }


  /**
   * Unsets the variable samplesLength.
   *
   * @return {@code true} if samplesLength was set before, otherwise {@code false}.
   */
  public boolean unsetSamplesLength() {
    if (isSetSamplesLength()) {
      Integer oldSamplesLength = samplesLength;
      samplesLength = null;
      firePropertyChange(SpatialConstants.samplesLength, oldSamplesLength, samplesLength);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of data
   *
   * @return the value of data
   */
  public String getDataString() {
    if (isSetData()) {
      return data.getCharacters();
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.data, this);
  }

  /**
   * Returns the value of data
   *
   * @return the value of data
   */
  public XMLNode getData() {
    if (isSetData()) {
      return data;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.data, this);
  }


  /**
   * Returns whether data is set
   *
   * @return whether data is set
   */
  public boolean isSetData() {
    return data != null;
  }


  /**
   * Sets the value of data
   * @param data
   * @throws XMLStreamException
   */
  public void setData(String data) throws XMLStreamException {
    setData(XMLNode.convertStringToXMLNode(data));
  }

  /**
   * Sets the value of data
   * @param data
   */
  public void setData(XMLNode data) {
    XMLNode oldData = this.data;
    this.data = data;
    firePropertyChange(SpatialConstants.data, oldData, this.data);
  }

  /**
   * Unsets the variable data
   *
   * @return {@code true}, if data was set before,
   *         otherwise {@code false}
   */
  public boolean unsetData() {
    if (isSetData()) {
      XMLNode oldData = data;
      data = null;
      firePropertyChange(SpatialConstants.data, oldData, data);
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
    if (isSetData()) {
      count++;
    }
    return count;
  }


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
    if (isSetData()) {
      if (pos == index) {
        return getData(); // Need to address!
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }


  @Override
  public int hashCode() {
    final int prime = 1997;
    int hashCode = super.hashCode();
    if (isSetNumSamples1()) {
      hashCode += prime * getNumSamples1();
    }

    if (isSetNumSamples2()) {
      hashCode += prime * getNumSamples2();
    }

    if (isSetNumSamples3()) {
      hashCode += prime * getNumSamples3();
    }

    if (isSetDataType()) {
      hashCode += prime * getDataType().hashCode();
    }

    if (isSetCompression()) {
      hashCode += prime * getCompression().hashCode();
    }

    if (isSetSamples()) {
      hashCode += prime * getSamples().hashCode();
    }

    if (isSetSamplesLength()) {
      hashCode += prime * getSamplesLength();
    }

    if (isSetInterpolation()) {
      hashCode += prime * getInterpolation().hashCode();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetNumSamples1()) {
      attributes.put(SpatialConstants.shortLabel + ":" + SpatialConstants.numSamples1, String.valueOf(getNumSamples1()));
    }
    if (isSetNumSamples2()) {
      attributes.put(SpatialConstants.shortLabel + ":" + SpatialConstants.numSamples2,
        String.valueOf(getNumSamples2()));
    }
    if (isSetNumSamples3()) {
      attributes.put(SpatialConstants.shortLabel + ":" + SpatialConstants.numSamples3,
        String.valueOf(getNumSamples3()));
    }
    if (isSetDataType()) {
      // see DataKind.java
      attributes.put(SpatialConstants.shortLabel + ":dataType",
        getDataType().toString().toLowerCase());
    }
    if (isSetCompression()) {
      attributes.put(SpatialConstants.shortLabel + ":compression",
        getCompression().toString());
    }
    if (isSetInterpolation()) {
      attributes.put(SpatialConstants.shortLabel + ":interpolationType",
        getInterpolation().toString());
    }
    if (isSetSamplesLength()) {
      attributes.put(SpatialConstants.shortLabel + ":samplesLength",
        String.valueOf(getSamplesLength()));
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.numSamples1)) {
        try {
          setNumSamples1(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.numSamples1, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.numSamples2)) {
        try {
          setNumSamples2(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.numSamples2, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.numSamples3)) {
        try {
          setNumSamples3(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.numSamples3, getElementName()));
        }
      }
      // The specs were ambiguous for some time so we keep both interpolation and interpolationType in this test
      // to be able to read the incorrect models
      else if (attributeName.equals(SpatialConstants.interpolationType) || attributeName.equals("interpolation")) {
        try {
          // for some time JSBML was using "nearestneighbor" so we try to fix those values if found
          if (value.equals("nearestneighbor")) {
            value = "nearestNeighbor";
          }
          setInterpolation(InterpolationKind.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.interpolationType, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.compression)) {
        try {
          setCompression(CompressionKind.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.compression, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.samplesLength)) {
        try {
          setSamplesLength(String.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.samplesLength, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.dataType)) {
        try {
          // see DataKind.java
          setDataType(DataKind.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.dataType, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
