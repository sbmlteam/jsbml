/*
 *
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
package org.sbml.jsbml;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

/**
 * This exception is thrown when trying to set or add an identifier to an
 * instance of {@link NamedSBase} but if the given identifier is already
 * registered in the containing {@link Model}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class IdentifierException extends SBMLException {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3203848126194894206L;
  /**
   * 
   */
  private static transient final Logger logger = Logger.getLogger(IdentifierException.class);
  /**
   * 
   */
  public static final String DUPLICATE_IDENTIFIER_MSG = "Cannot set duplicate {0}id ''{1}'' for {2}.";

  /**
   * Creates a new {@link IdentifierException} instance.
   * 
   * @param sb
   * @param identifier
   */
  public IdentifierException(SBase sb, String identifier, String message, String preIdentifierName) {
    super(MessageFormat.format(message, preIdentifierName, identifier, sb.getElementName()));
    logger.error(MessageFormat.format(
      "An element with the {2}id \"{0}\" is already present in the SBML model. The element {1} will ignore the value.",
      identifier, sb.getElementName(), preIdentifierName));
  }

  /**
   * Creates a new {@link IdentifierException} instance for id.
   * 
   * @param sb
   * @param id
   */
  public static IdentifierException createIdentifierExceptionForId(SBase sb, String id) {
    return new IdentifierException(sb, id, DUPLICATE_IDENTIFIER_MSG, "");
  }

  /**
   * Creates a new {@link IdentifierException} instance for metaid.
   * 
   * @param sb
   * @param metaId
   */
  public static IdentifierException createIdentifierExceptionForMetaId(SBase sb, String metaId) {
    return new IdentifierException(sb, metaId, DUPLICATE_IDENTIFIER_MSG, "meta");
  }

}
