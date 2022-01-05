/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
 * An Abstract Syntax Tree (AST) node representing a MathML csymbol element
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public interface ASTCSymbolNode extends ASTCSymbolBaseNode {

  /**
   * Returns the encoding of the MathML element represented by
   * this {@link ASTCSymbolNode}
   * 
   * @return String encoding
   */
  public String getEncoding();

  /**
   * Returns true iff encoding has been set
   * 
   * @return boolean
   */
  public boolean isSetEncoding();

}
