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
package org.sbml.jsbml.ext.distrib;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * @author rodrigue
 * @since 1.4
 */
public class ExternalParameter extends UncertValue {

  // TODO - implements XML attributes, equals and hashcode

  /**
   * 
   */
  private String definitionURL;
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * 
   */
  public ExternalParameter() {
  }


  /**
   * @param level
   * @param version
   */
  public ExternalParameter(int level, int version) {
    super(level, version);
  }


  /**
   * @param sb
   */
  public ExternalParameter(SBase sb) {
    super(sb);
  }


  /**
   * @param id
   */
  public ExternalParameter(String id) {
    super(id);
  }


  /**
   * @param id
   * @param level
   * @param version
   */
  public ExternalParameter(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public ExternalParameter(String id, String name, int level, int version) {
    super(id, name, level, version);
  }
  
  
  /**
   * Returns the value of {@link #definitionURL}.
   *
   * @return the value of {@link #definitionURL}.
   */
  public String getDefinitionURL() {

    if (isSetDefinitionURL()) {
      return definitionURL;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(TreeNodeChangeEvent.definitionURL, this);
  }


  /**
   * Returns whether {@link #definitionURL} is set.
   *
   * @return whether {@link #definitionURL} is set.
   */
  public boolean isSetDefinitionURL() {
    return this.definitionURL != null;
  }


  /**
   * Sets the value of definitionURL
   *
   * @param definitionURL the value of definitionURL to be set.
   */
  public void setDefinitionURL(String definitionURL) {
    String oldDefinitionURL = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, oldDefinitionURL, this.definitionURL);
  }


  /**
   * Unsets the variable definitionURL.
   *
   * @return {@code true} if definitionURL was set before, otherwise {@code false}.
   */
  public boolean unsetDefinitionURL() {
    if (isSetDefinitionURL()) {
      String oldDefinitionURL = this.definitionURL;
      this.definitionURL = null;
      firePropertyChange(TreeNodeChangeEvent.definitionURL, oldDefinitionURL, this.definitionURL);
      return true;
    }
    return false;
  }
  
}
