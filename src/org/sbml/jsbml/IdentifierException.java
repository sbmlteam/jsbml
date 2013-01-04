/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2013 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
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
 * @version $Rev$
 * @since 0.8
 * @date 19.09.2011
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
  public static final String DUPLICATE_IDENTIFIER_MSG = "Cannot set duplicate {0}identifier {1} for {2}.";

  /**
   * 
   * @param sb
   * @param id
   */
  public IdentifierException(NamedSBase sb, String id) {
    super(MessageFormat.format(DUPLICATE_IDENTIFIER_MSG, "", id, sb.getElementName()));
    logger.error(MessageFormat.format(
      "An element with the id \"{0}\" is already present in the SBML model. The identifier of {1} will not be set to this value.",
      id, sb.getElementName()));
  }

  /**
   * @param abstractSBase
   * @param metaId
   */
  public IdentifierException(SBase sb, String metaId) {
    super(MessageFormat.format(DUPLICATE_IDENTIFIER_MSG, "meta ", metaId, sb.getElementName()));
    logger.error(MessageFormat.format(
      "An element with the metaid \"{0}\" is already present in the SBML document. The element {1} will not be annotated with it.",
      metaId, sb.getElementName()));
  }

}
