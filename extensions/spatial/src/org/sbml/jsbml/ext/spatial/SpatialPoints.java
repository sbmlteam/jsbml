/*
 * $Id: SpatialPoints.java 0 2015-04-22 04:50:45Z pdp10 $
 * $URL: svn+ssh://pdp10@svn.code.sf.net/p/jsbml/code/trunk/extensions/spatial/src/org/sbml/jsbml/ext/spatial/SpatialPoints.java $
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
import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;


/**
 * @author Piero Dalle Pezze
 * @since 1.0
 * @version $Rev: 0 $
 */
public class SpatialPoints extends AbstractSBase {

  /**
   * 
   */
  private static final long serialVersionUID = -3467717442431545263L;


  public enum CompressionKind {
    /**
     * 
     */
    UNCOMPRESSED,
    /**
     * 
     */
    DEFLATED,
    /**
     * 
     */
    BASE64;
  }

  public enum DataKind {
    /**
     * 
     */
    UINT8,
    /**
     * 
     */
    UINT16,
    /**
     * 
     */
    UINT32,
    /**
     * 
     */
    FLOAT,
    /**
     * 
     */
    DOUBLE;
  }
  
  /**
   * 
   */
  private CompressionKind compression;
  /**
   * 
   */
  private Integer arrayDataLength;
  /**
   * 
   */
  private DataKind dataType;

  
  
  
  /**
   * Creates an SpatialPoints instance 
   */
  public SpatialPoints() {
    super();
    initDefaults();
  }

  /**
   * Creates a SpatialPoints instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpatialPoints(int level, int version) {
    super(level, version);
  }


  /**
   * Clone constructor
   */
  public SpatialPoints(SpatialPoints sp) {
    super(sp);
    if (sp.isSetCompression()) {
      setCompression(sp.getCompression());
    }
    if (sp.isSetArrayDataLength()) {
      setArrayDataLength(sp.getArrayDataLength());
    }
    if (sp.isSetDataType()) {
      setDataType(sp.getDataType());
    }    
  }


  /**
   * clones this class
   */
  public SpatialPoints clone() {
    return new SpatialPoints(this);
  }

  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SpatialPoints sp = (SpatialPoints) object;

      equal &= sp.isSetCompression() == isSetCompression();
      if (equal && isSetCompression()) {
        equal &= sp.getCompression().equals(getCompression());
      }
      equal &= sp.isSetArrayDataLength() == isSetArrayDataLength();
      if (equal && isSetArrayDataLength()) {
        equal &= sp.getArrayDataLength() == getArrayDataLength();
      }
      equal &= sp.isSetDataType() == isSetDataType();
      if (equal && isSetDataType()) {
        equal &= sp.getDataType().equals(getDataType());
      }      
    }
    return equal;
  }
  
  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(SpatialConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    packageName = SpatialConstants.shortLabel;
    setPackageVersion(-1);
  }
  
  /**
   * Returns the value of compression.
   *
   * @return the value of compression.
   */
  public CompressionKind getCompression() {
    if (isSetCompression()) {
      return compression;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.compression, this);
  }


  /**
   * Returns whether compression is set.
   *
   * @return whether compression is set.
   */
  public boolean isSetCompression() {
    return this.compression != null;
  }

  /**
   * Sets the value of compression
   * @param compression
   */
  public void setCompression(String compression) {
    setCompression(CompressionKind.valueOf(compression));
  }  

  /**
   * Sets the value of compression
   *
   * @param compression the value of compression to be set.
   */
  public void setCompression(CompressionKind compression) {
    CompressionKind oldCompression = this.compression;
    this.compression = compression;
    firePropertyChange(SpatialConstants.compression, oldCompression, this.compression);
  }


  /**
   * Unsets the variable compression.
   *
   * @return {@code true} if compression was set before, otherwise {@code false}.
   */
  public boolean unsetCompression() {
    if (isSetCompression()) {
      CompressionKind oldCompression = this.compression;
      this.compression = null;
      firePropertyChange(SpatialConstants.compression, oldCompression, this.compression);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of arrayDataLength.
   *
   * @return the value of arrayDataLength.
   */
  public int getArrayDataLength() {
    if (isSetArrayDataLength()) {
      return arrayDataLength.intValue();
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.arrayDataLength, this);
  }


  /**
   * Returns whether arrayDataLength is set.
   *
   * @return whether arrayDataLength is set.
   */
  public boolean isSetArrayDataLength() {
    return this.arrayDataLength != null;
  }


  /**
   * Sets the value of arrayDataLength
   *
   * @param arrayDataLength the value of arrayDataLength to be set.
   */
  public void setArrayDataLength(int arrayDataLength) {
    Integer oldArrayDataLength = this.arrayDataLength;
    this.arrayDataLength = arrayDataLength;
    firePropertyChange(SpatialConstants.arrayDataLength, oldArrayDataLength, this.arrayDataLength);
  }


  /**
   * Unsets the variable arrayDataLength.
   *
   * @return {@code true} if arrayDataLength was set before, otherwise {@code false}.
   */
  public boolean unsetArrayDataLength() {
    if (isSetArrayDataLength()) {
      Integer oldArrayDataLength = this.arrayDataLength;
      this.arrayDataLength = null;
      firePropertyChange(SpatialConstants.arrayDataLength, oldArrayDataLength, this.arrayDataLength);
      return true;
    }
    return false;
  }

  
  /**
   * Returns the value of dataType.
   *
   * @return the value of dataType.
   */
  public DataKind getDataType() {
    if (isSetDataType()) {
      return dataType;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(SpatialConstants.dataType, this);
  }


  /**
   * Returns whether dataType is set.
   *
   * @return whether dataType is set.
   */
  public boolean isSetDataType() {
    return this.dataType != null;
  }

  /**
   * Sets the value of dataType
   * @param dataType
   */
  public void setDataType(String dataType) {
    setDataType(DataKind.valueOf(dataType));
  }

  /**
   * Sets the value of dataType
   *
   * @param dataType the value of dataType to be set.
   */
  public void setDataType(DataKind dataType) {
    DataKind oldDataType = this.dataType;
    this.dataType = dataType;
    firePropertyChange(SpatialConstants.dataType, oldDataType, this.dataType);
  }


  /**
   * Unsets the variable dataType.
   *
   * @return {@code true} if dataType was set before, otherwise {@code false}.
   */
  public boolean unsetDataType() {
    if (isSetDataType()) {
      DataKind oldDataType = this.dataType;
      this.dataType = null;
      firePropertyChange(SpatialConstants.dataType, oldDataType, this.dataType);
      return true;
    }
    return false;
  }
  


  @Override
  public int hashCode() {
    final int prime = 2011;
    int hashCode = super.hashCode();
    if (isSetCompression()) {
      hashCode += prime * getCompression().hashCode();
    }
    if (isSetArrayDataLength()) {
      hashCode += prime * getArrayDataLength();
    }
    if (isSetDataType()) {
      hashCode += prime * getDataType().hashCode();
    }
    return hashCode;
  }
  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetCompression()) {
      attributes.remove("compression");
      attributes.put(SpatialConstants.shortLabel + ":compression",
        getCompression().toString());
    }
    if (isSetArrayDataLength()) {
      attributes.remove("arrayDataLength");
      attributes.put(SpatialConstants.shortLabel + ":arrayDataLength",
        String.valueOf(getArrayDataLength()));
    }
    if (isSetDataType()) {
      attributes.remove("dataType");
      attributes.put(SpatialConstants.shortLabel + ":dataType",
        getDataType().toString());
    }    

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (attributeName.equals(SpatialConstants.compression)) {
      try {
        setCompression(value);
      } catch (Exception e) {
        MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.polygonType);
      }
    }
    else if (attributeName.equals(SpatialConstants.arrayDataLength)) {
      try {
        setArrayDataLength(StringTools.parseSBMLInt(value));
      } catch (Exception e) {
        MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.polygonType);
      }
    }
    else if (attributeName.equals(SpatialConstants.dataType)) {
      try {
        setDataType(value);
      } catch (Exception e) {
        MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.polygonType);
      }
    }      
    else {
      isAttributeRead = false;
    }
    return isAttributeRead;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ParametricObject [compression=");
    builder.append(compression);
    builder.append(", arrayDataLength=");
    builder.append(arrayDataLength);
    builder.append(", dataType=");
    builder.append(dataType);    
    builder.append("]");
    return builder.toString();
  }  
  
  
}
