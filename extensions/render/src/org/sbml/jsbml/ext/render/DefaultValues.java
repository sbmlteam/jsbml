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

import java.util.HashMap;
import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

/**
 * Encodes default values within the containing {@link ListOfGlobalRenderInformation} or {@link ListOfLocalRenderInformation}.
 * 
 * <p>Previously the render package specified default values and inheritance in a similar fashion to the specification used
 * by SVG. However, in order to comply with the SBML development guidelines for Level 3 packages, we introduced
 * a new class DefaultValues to encode these values within the model. The DefaultValues class can occur as a child of
 * either the ListOfGlobalRenderInformation or a ListOfLocalRenderInformation.</p>
 *
 * @author rodrigue
 * @since 1.2
 */
public class DefaultValues extends AbstractSBase {

  /**
   * Contains all class attributes
   */
  Map<String, String> attributes;

  /**
   * Creates an DefaultValues instance 
   */
  public DefaultValues() {
    super();
    initDefaults();
  }

  /**
   * Creates a DefaultValues instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public DefaultValues(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public DefaultValues(DefaultValues obj) {
    super(obj);

    if (obj.attributes != null) {
      for (String name : obj.attributes.keySet()) {
        setDefaultValue(name, obj.attributes.get(name));
      }
    }
  }
  

  @Override
  public DefaultValues clone() {
    return new DefaultValues(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = RenderConstants.shortLabel;
    setPackageVersion(-1);
  }

  /**
   * @param name
   * @param value
   */
  public void setDefaultValue(String name, String value) {
    if (attributes == null) {
      attributes = new HashMap<String, String>();
    }
    
    attributes.put(name, value);
  }
  
  /**
   * @param name
   * @return
   */
  public String getDefaultValue(String name) {
    if (attributes == null) {
      return null;
    }
    
    return attributes.get(name);
  }
  
  /**
   * @return
   */
  public Map<String, String> getDefaultValues() {
    return attributes;
  }
  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> allAttributes = super.writeXMLAttributes();

    if (attributes != null) {
      for (String attributeName : attributes.keySet()) {
        allAttributes.put(RenderConstants.shortLabel + ":" + attributeName, attributes.get(attributeName));
      }
    }
    
    return allAttributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;

      setDefaultValue(attributeName, value);
    }

    return isAttributeRead;
  }

}
