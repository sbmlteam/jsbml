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
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math;

/**
 * An Abstract Syntax Tree (AST) node representing a MathML element
 * that contains a definitionURL and name attribute.
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public interface ASTCSymbolBaseNode extends ASTNode2 {

  /**
   * Returns the definitionURL of the MathML element represented by
   * this {@link ASTCSymbolBaseNode}
   * 
   * @return String definitionURL
   */
  public String getDefinitionURL();

  /**
   * Returns the name of the MathML element represented by
   * this {@link ASTCSymbolBaseNode}
   * 
   * @return String name
   */
  public String getName();

  /**
   * Returns True iff definitionURL has been set
   * 
   * @return boolean
   */
  public boolean isSetDefinitionURL();

  /**
   * Returns True iff name has been set
   * 
   * @return boolean
   */
  public boolean isSetName();

  /**
   * Returns {@code true} if this node or one of its descendants contains some
   * identifier with the given id. This method can be used to scan a formula
   * for a specific parameter or species and detect whether this component is
   * used by this formula. This search is done using a DFS.
   * 
   * @param id
   *            the id of an SBML element.
   * @return {@code true} if this node or one of its descendants contains the
   *            given id.
   */
  public boolean refersTo(String id);

  /**
   * Set the name of the MathML element represented by
   * this {@link ASTCSymbolBaseNode}
   * 
   * @param name
   */
  public void setName(String name);

}
