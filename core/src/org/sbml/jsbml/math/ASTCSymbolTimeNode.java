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

import org.apache.log4j.Logger;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.TreeNodeChangeEvent;



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
   * 
   */
  private static final long serialVersionUID = 3788959657360296692L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCSymbolTimeNode.class);

  /**
   * The encodingURL of this csymbol element
   */
  private String encodingURL;

  /**
   * The URI for the definition of the csymbol for time.
   */
  public static final transient String URI_TIME_DEFINITION =
      "http://www.sbml.org/sbml/symbols/time";

  /**
   * Creates a new {@link ASTCSymbolTimeNode}.
   */
  public ASTCSymbolTimeNode() {
    super();
    setEncodingURL(null);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCSymbolTimeNode}.
   * 
   * @param node
   *            the {@link ASTCSymbolTimeNode} to be copied.
   */
  public ASTCSymbolTimeNode(ASTCSymbolTimeNode node) {
    super(node);
    setEncodingURL(node.getEncodingURL());
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCiNumberNode#clone()
   */
  @Override
  public ASTCSymbolTimeNode clone() {
    return new ASTCSymbolTimeNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolNode#getEncodingURL()
   */
  @Override
  public String getEncodingURL() {
    if (isSetEncodingURL()) {
      return encodingURL;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("encodingURL", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolNode#isSetEncodingURL()
   */
  @Override
  public boolean isSetEncodingURL() {
    return encodingURL != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolNode#setEncodingURL(java.lang.String)
   */
  @Override
  public void setEncodingURL(String encodingURL) {
    String old = this.encodingURL;
    this.encodingURL = encodingURL;
    firePropertyChange(TreeNodeChangeEvent.encoding, old, this.encodingURL);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTCSymbolTimeNode [encodingURL=");
    builder.append(encodingURL);
    builder.append(", definitionURL=");
    builder.append(definitionURL);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
