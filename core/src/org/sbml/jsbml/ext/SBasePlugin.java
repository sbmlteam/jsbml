/*
 * $Id$
 * $URL$
 *
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
package org.sbml.jsbml.ext;

import java.util.Map;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;

/**
 * Defines the methods necessary for an SBase Plugin. When a SBML level 3 is
 * extending one of the core SBML elements with additional attributes or child
 * elements, a {@link SBasePlugin} is created to serve as a place holder for
 * there new attributes or elements.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public interface SBasePlugin extends TreeNodeWithChangeSupport {

  /**
   * Creates a new instance of {@link SBasePlugin} from this {@link SBasePlugin}.
   * 
   * @return a new instance of {@link SBasePlugin} from this {@link SBasePlugin}.
   */
  public SBasePlugin clone();

  /**
   * 
   * @param obj
   * @return
   */
  @Override
  public boolean equals(Object obj);

  /**
   * Returns the XML namespace (URI) of the package extension of this plugin object.
   * @return the URI of the package extension of this plugin object.
   */
  public String getElementNamespace();

  /**
   * Returns the SBase object that is extended by this plug-in.
   * 
   * @return the SBase object that is extended by this plug-in.
   */
  public SBase getExtendedSBase();

  /**
   * 
   * @return
   * @see SBase#getLevel()
   */
  public int getLevel();

  /**
   * Returns the package name of this plugin object.
   * @return the package name of this plugin object.
   */
  public String getPackageName();

  /**
   * Returns the package version of the package extension of this plugin object.
   * @return the package version of the package extension of this plugin object.
   */
  public int getPackageVersion();


  /**
   * Returns the parent {@link SBase} object to which this plugin object connected.
   * 
   * @return the parent {@link SBase} object to which this plugin object connected.
   */
  public SBase getParentSBMLObject();

  /**
   * Returns the prefix of the package extension of this plugin object.
   * @return the prefix of the package extension of this plugin object.
   */
  public String getPrefix();

  /**
   * Returns the parent {@link SBMLDocument} of this plugin object.
   * @return the parent {@link SBMLDocument} object of this plugin object.
   */
  public SBMLDocument getSBMLDocument();

  /**
   * Gets the URI to which this element belongs to.
   * For example, all elements that belong to SBML Level 3
   * Version 1 Core must would have the URI
   * 'http://www.sbml.org/sbml/level3/version1/core'; all
   * elements that belong to Layout Extension Version 1 for
   * SBML Level 3 Version 1 Core must would have the URI
   * 'http://www.sbml.org/sbml/level3/version1/layout/version1/'
   * Unlike getElementNamespace, this function first returns
   * the URI for this element by looking into the SBMLNamespaces
   * object of the document with the its package name.
   * If not found it will return the result of getElementNamespace
   * @return the URI for this element
   */
  public String getURI();

  /**
   * 
   * @return
   * @see SBase#getVersion()
   */
  public int getVersion();

  /**
   * 
   * @return
   */
  @Override
  public int hashCode();

  /**
   * Check whether an extended SBase has been set.
   * 
   * @return
   */
  public boolean isSetExtendedSBase();

  /**
   * @return {@code true} if a package version is set, {@code false} otherwise.
   */
  public abstract boolean isSetPackageVersion();

  /**
   * Reads and sets the attribute if it is know from this {@link SBasePlugin}.
   * 
   * @param attributeName
   *           localName of the XML attribute
   * @param prefix
   *           prefix of the XML attribute
   * @param value
   *           value of the XML attribute
   * @return {@code true} if the attribute has been successfully read.
   */
  public boolean readAttribute(String attributeName, String prefix, String value);

  /**
   * @param packageVersion the packageVersion to set
   */
  public abstract void setPackageVersion(int packageVersion);

  /**
   * Returns a {@link Map} containing the XML attributes of this object.
   * 
   * @return a {@link Map} containing the XML attributes of this object.
   */
  public Map<String, String> writeXMLAttributes();

  //  /**
  //   * Sets the XML namespace to which this element belongs to.
  //   * For example, all elements that belong to SBML Level 3 Version 1 Core
  //   * must set the namespace to 'http://www.sbml.org/sbml/level3/version1/core';
  //   * all elements that belong to Layout Extension Version 1 for SBML Level 3
  //   * Version 1 Core must set the namespace to
  //   * 'http://www.sbml.org/sbml/level3/version1/layout/version1/'
  //   * @param uri
  //   * @return boolean indicating success/failure of the function. The possible values returned by this function are:
  //   */
  //  public boolean setElementNamespace(String uri);

}
