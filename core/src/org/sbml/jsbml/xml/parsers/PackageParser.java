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
package org.sbml.jsbml.xml.parsers;

import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;

/**
 * Interface that define some common methods that the parsers for SBML packages need
 * to implement in order to provide some basic information about them.
 * 
 * @author Nicolas Rodriguez
 *
 */
public interface PackageParser {


  /**
   * Creates a new {@link SBasePlugin} for the given {@link SBase}
   * and add it to the {@link SBasePlugin} map of the {@link SBase}.
   * 
   * @param sbase - the sbase for which you want to create an {@link SBasePlugin}
   * @return a new {@link SBasePlugin} corresponding to the given {@link SBase}.
   */
  public SBasePlugin createPluginFor(SBase sbase);

  /**
   * Creates a new {@link ASTNodePlugin} for the given {@link ASTNode}
   * and add it to the {@link ASTNodePlugin} map of the {@link ASTNode}.
   * 
   * @param astNode - the astNode for which you want to create an {@link ASTNodePlugin}
   * @return a new {@link ASTNodePlugin} corresponding to the given {@link ASTNode}.
   */
  public ASTNodePlugin createPluginFor(ASTNode astNode);

  /**
   * Gets the namespace for this package that correspond to the given SBML level, version
   * and the package version.
   * 
   * <p>Returns null if the combined level, version and packageVersion is
   * invalid or not known from the package parser implementation.
   * 
   * @param level - the SBML level
   * @param version - the SBML version
   * @param packageVersion - the package version
   * @return the namespace for this package that correspond to the given SBML level and version
   * and the package version or null if nothing valid is found.
   */
  public String getNamespaceFor(int level, int version, int packageVersion);

  /**
   * Returns a {@link List} of all the valid namespaces for the package.
   * 
   * @return a {@link List} of all the valid namespaces for the package.
   */
  public List<String> getPackageNamespaces();


  /**
   * Returns the short name of this package.
   * 
   * @return the short name of this package.
   */
  public String getPackageName();

  /**
   * Returns the value of the {@code required} attribute for this package.
   * 
   * @return the value of the {@code required} attribute for this package.
   */
  public boolean isRequired();


  // TODO - methods to change an id or metaid ??

}
