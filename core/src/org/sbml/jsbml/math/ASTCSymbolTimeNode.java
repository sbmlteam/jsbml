/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations:
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
 * An Abstract Syntax Tree (AST) node representing time
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCSymbolTimeNode extends ASTCiNumberNode implements
ASTCSymbolNode {

  /**
   * The encodingURL of this csymbol element
   */
  private String encodingURL;

  /**
   * The URI for the definition of the csymbol for time.
   */
  public static final transient String URI_TIME_DEFINITION = "http://www.sbml.org/sbml/symbols/time";

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCiNumberNode#getDefinitionURL()
   */
  @Override
  public String getDefinitionURL() {
    return definitionURL;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolNode#getEncodingURL()
   */
  @Override
  public String getEncodingURL() {
    return encodingURL;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCiNumberNode#setDefinitionURL(java.lang.String)
   */
  @Override
  public void setDefinitionURL(String definitionURL) {
    this.definitionURL = definitionURL;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolNode#setEncodingURL(java.lang.String)
   */
  @Override
  public void setEncodingURL(String encodingURL) {
    this.encodingURL = encodingURL;
  }

}
