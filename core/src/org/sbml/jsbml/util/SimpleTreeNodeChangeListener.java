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
package org.sbml.jsbml.util;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;

/**
 * This very simple implementation of an {@link TreeNodeChangeListener} writes
 * all the events to the standard out stream.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class SimpleTreeNodeChangeListener implements TreeNodeChangeListener, Serializable {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SimpleTreeNodeChangeListener.class);
  private static final transient boolean isDebugEnabled = logger.isDebugEnabled();

  /**
   * Creates an {@link TreeNodeChangeListener} that writes all events to the
   * standard output.
   */
  public SimpleTreeNodeChangeListener() {
    super();
  }

  /**
   * @return the logger
   */
  public Logger getLogger() {
    return logger;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeAdded(javax.swing.tree.TreeNode)
   */
  @Override
  public void nodeAdded(TreeNode treeNode) {
    if (isDebugEnabled) {
      logger.debug(MessageFormat.format("[ADD]\t{0}", saveToString(treeNode)));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeRemoved(org.sbml.jsbml.util.TreeNodeRemovedEvent)
   */
  @Override
  public void nodeRemoved(TreeNodeRemovedEvent evt) {
    if (isDebugEnabled) {
      String element = "null", prevParent = "null";
      if (evt != null) {
        element = saveToString(evt.getSource());
        prevParent = saveToString(evt.getPreviousParent());
      }
      logger.debug(MessageFormat.format("[DEL]\t{0} from {1}", element, prevParent));
    }
  }

  /* (non-Javadoc)
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (isDebugEnabled) {
      logger.debug(MessageFormat.format("[CHG]\t{0}", saveToString(evt)));
    }
  }

  /**
   * Tries to call the {@link #toString()} method on the given object. If the
   * argument is {@code null}, it returns "null". In case that the call of
   * {@link #toString()} fails, the simple class name of the object is returned.
   * 
   * @param object the java Object to write as a String
   * @return some {@link String} representation of the given object.
   */
  private String saveToString(Object object) {
    if (object == null) {
      return "null";
    }
    if (object instanceof ASTNode) {
      return ((ASTNode) object).toSimpleString();
    }
    if (object instanceof PropertyChangeEvent) {
      return toString((PropertyChangeEvent) object);
    }
    
    try {
      return object.toString();
    } catch (Throwable t) {
      return object.getClass().getSimpleName();
    }
  }
  
  public String toString(PropertyChangeEvent evt) {
    StringBuilder sb = new StringBuilder(evt.getClass().getName());
    sb.append("[propertyName=").append(evt.getPropertyName());
    
    if (evt.getOldValue() != null && evt.getOldValue() instanceof ASTNode) {
      sb.append("; oldValue=").append(((ASTNode) evt.getOldValue()).toSimpleString());
    } else {
      sb.append("; oldValue=").append(evt.getOldValue());
    }
    if ((evt.getNewValue() != null) && (evt.getNewValue() instanceof ASTNode)) {
      sb.append("; newValue=").append(((ASTNode) evt.getNewValue()).toSimpleString());
    } else {
      sb.append("; newValue=").append(evt.getNewValue());
    }
    sb.append("; propagationId=").append(evt.getPropagationId());
    
    if (evt.getSource() != null && evt.getSource() instanceof ASTNode) {
      sb.append("; source=").append(((ASTNode) evt.getSource()).toSimpleString());
    } else {        
      sb.append("; source=").append(evt.getSource());
    }
    return sb.append("]").toString();
}


}
