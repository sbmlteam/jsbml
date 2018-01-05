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
package org.sbml.jsbml;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ext.SBasePlugin;

/**
 * This is an error of an undefined property or value for a property in some
 * instance of {@link SBase}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public abstract class PropertyException extends SBMLError {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -3416620362835594659L;

  /**
   * Creates an error message pointing out that the property of the given name is not defined
   * in the Level/Version combination of the given {@link SBase}.
   * 
   * @param baseMessage
   * @param property
   * @param sbase
   * 
   * @return
   */
  static String createMessage(String baseMessage, String property, SBase sbase) {
    return MessageFormat.format(baseMessage, property, sbase
      .getElementName(), Integer.valueOf(sbase.getLevel()), Integer
      .valueOf(sbase.getVersion()));
  }

  /**
   * 
   * @param baseMessage
   * @param property
   * @param sbasePlugin
   * @return
   */
  static String createMessage(String baseMessage,	String property, SBasePlugin sbasePlugin) {
    return MessageFormat.format(baseMessage, property, sbasePlugin.getExtendedSBase().getElementName(),
      Integer.valueOf(sbasePlugin.getExtendedSBase().getLevel()), Integer.valueOf(sbasePlugin.getExtendedSBase().getVersion()));
  }

  /**
   * Creates an error message pointing out that the property of the given name
   * is not defined in the Level/Version combination of the given
   * {@link TreeNode}.
   * 
   * @param baseMessage
   * @param property
   * @param node
   * @return
   */
  static String createMessage(String baseMessage, String property, TreeNode node) {
    return MessageFormat.format(baseMessage, property, node.getClass().getName());
  }

  /**
   * 
   */
  public PropertyException() {
    super();
  }

  /**
   * @param message
   */
  public PropertyException(String message) {
    super(message);
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLError#toString()
   */
  @Override
  public String toString() {
    return getMessage();
  }

}
