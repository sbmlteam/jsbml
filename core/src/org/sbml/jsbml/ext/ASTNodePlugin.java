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
package org.sbml.jsbml.ext;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;


/**
 * Defines the methods necessary for an {@link ASTNode} or {@link ASTNode2} Plugin. When a SBML level 3 is
 * extending one of the mathML elements with additional attributes or child
 * elements, a {@link ASTNodePlugin} is created to serve as a place holder for
 * these new attributes or elements.
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public interface ASTNodePlugin extends TreeNodeWithChangeSupport {

  /**
   * Creates a new instance of {@link ASTNodePlugin} from this {@link ASTNodePlugin}.
   * 
   * @return a new instance of {@link ASTNodePlugin} from this {@link ASTNodePlugin}.
   */
  public ASTNodePlugin clone();

  @Override
  public boolean equals(Object obj);

  /**
   * Returns the XML namespace (URI) of the package extension of this plugin object.
   * 
   * @return the URI of the package extension of this plugin object.
   * @see #getURI()
   */
  public String getElementNamespace();

  /**
   * Returns the object that is extended by this plug-in.
   * 
   * @return the object that is extended by this plug-in.
   */
  public TreeNode getExtendedASTNode();

  /**
   * Returns the SBML level of this plugin object.
   * 
   * @return the SBML level of this plugin object.
   * @see SBase#getLevel()
   */
  public int getLevel();

  /**
   * Returns the package name of this plugin object.
   * 
   * @return the package name of this plugin object.
   */
  public String getPackageName();

  /**
   * Returns the package version of the package extension of this plugin object.
   * 
   * @return the package version of the package extension of this plugin object.
   */
  public int getPackageVersion();


  /**
   * Returns the parent {@link SBase} object to which this plugin object is connected.
   * 
   * @return the parent {@link SBase} object to which this plugin object is connected.
   */
  public SBase getParentSBMLObject();

  /**
   * Returns the prefix of the package extension of this plugin object.
   * 
   * @return the prefix of the package extension of this plugin object.
   */
  public String getPrefix();

  /**
   * Returns the parent {@link SBMLDocument} of this plugin object.
   * 
   * @return the parent {@link SBMLDocument} object of this plugin object.
   */
  public SBMLDocument getSBMLDocument();

  /**
   * Gets the URI to which this element belongs to.
   * For example, all elements that belong to SBML Level 3
   * Version 1 Core must have the URI
   * 'http://www.sbml.org/sbml/level3/version1/core'; all
   * elements that belong to Layout Extension Version 1 for
   * SBML Level 3 Version 1 Core must would have the URI
   * 'http://www.sbml.org/sbml/level3/version1/layout/version1/'.
   * 
   * @return the URI for this element
   * @see #getElementNamespace()
   */
  public String getURI();

  /**
   * Returns the SBML version of this plugin object.
   * 
   * @return the SBML version of this plugin object.
   * @see SBase#getVersion()
   */
  public int getVersion();

  @Override
  public int hashCode();

  /**
   * Checks whether an extended TreeNode has been set.
   * 
   * @return true if an extended TreeNode has been set.
   */
  public boolean isSetExtendedASTNode();

  /**
   * Returns {@code true} if a package version is set, {@code false} otherwise.
   * 
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
   * Sets the package version.
   * 
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
