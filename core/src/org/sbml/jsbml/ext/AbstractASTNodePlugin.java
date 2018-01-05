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
package org.sbml.jsbml.ext;

import java.text.MessageFormat;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public abstract class AbstractASTNodePlugin extends AbstractTreeNode implements ASTNodePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3741496965840142920L;

  /**
   * 
   */
  private static final transient Logger logger = Logger.getLogger(AbstractASTNodePlugin.class);
  
  /**
   * 
   */
  protected ASTNode extendedASTNode;

  /**
   * 
   */
  protected int packageVersion = -1;

  /**
   * 
   */
  protected String elementNamespace;
    
  /**
   * 
   */
  public AbstractASTNodePlugin() {
    super();
  }

  /**
   * @param extendedASTNode
   */
  public AbstractASTNodePlugin(ASTNode extendedASTNode) {
    this();
    this.extendedASTNode = extendedASTNode;
  }

  /**
   * This method will need to be further tested
   * @param plugin
   */
  public AbstractASTNodePlugin(ASTNodePlugin plugin) {
    super(plugin);
    setPackageVersion(plugin.getPackageVersion());
    setNamespace(plugin.getElementNamespace());
    
    // the extendedASTNode will be set when the cloned ASTNodePlugin is added inside an ASTNode
    // then the ids of his children will be registered in the correct maps.
    extendedASTNode = null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public abstract ASTNodePlugin clone();

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    // Check all child nodes recursively:
    boolean equal = super.equals(object);
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#fireNodeAddedEvent()
   */
  @Override
  public void fireNodeAddedEvent() {
    // TODO - see if we need to overwrite this method
    super.fireNodeAddedEvent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#fireNodeRemovedEvent()
   */
  @Override
  public void fireNodeRemovedEvent() {
    // TODO - see if we need to overwrite this method
    super.fireNodeRemovedEvent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
   */
  @Override
  public void firePropertyChange(String propertyName, Object oldValue,
    Object newValue) {
    
    // TODO - check if we need this method for ASTNode
    
    // TODO - this method is used to add or remove ASTNode or ASTNodePlugin, we should make sure to handle the registration/un-registration
    // in those cases.
    // the parent need to be set or unset as well (would be done, if we call the registerChild method)

//    if (oldValue != null && oldValue instanceof SBase && isSetExtendedASTNode()) {
//      extendedASTNode.unregisterChild((SBase) oldValue);
//    }

    // This case is generally handled properly in the setters
    // but it would be better and more consistent to handle it there
    //    if (newValue != null && newValue instanceof SBase && isSetExtendedASTNode()) {
    //      extendedASTNode.registerChild((SBase) newValue);
    //    }

    super.firePropertyChange(propertyName, oldValue, newValue);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return elementNamespace;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#getExtendedASTNode()
   */
  @Override
  public ASTNode getExtendedASTNode() {
    return extendedASTNode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#getLevel()
   */
  @Override
  public int getLevel() {
    if (isSetExtendedASTNode()) {
      SBase parentSBase = extendedASTNode.getParentSBMLObject();
      
      if (parentSBase != null) {
        return parentSBase.getLevel();
      }
    }
    
    return -1;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#getPackageVersion()
   */
  @Override
  public int getPackageVersion() {
    return packageVersion;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#getParentSBMLObject()
   */
  @Override
  public SBase getParentSBMLObject() { // TODO
    return (SBase) getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#getSBMLDocument()
   */
  @Override
  public SBMLDocument getSBMLDocument() {
    if (isSetExtendedASTNode()) {
      SBase parentSBase = extendedASTNode.getParentSBMLObject();
      
      if (parentSBase != null) {
        return parentSBase.getSBMLDocument();
      }
    }
    
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#getVersion()
   */
  @Override
  public int getVersion() {
    if (isSetExtendedASTNode()) {
      SBase parentSBase = extendedASTNode.getParentSBMLObject();
      
      if (parentSBase != null) {
        return parentSBase.getVersion();
      }
    }
    
    return -1;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    // A constant and arbitrary, sufficiently large prime number:
    final int prime = 769;
    int hashCode = super.hashCode();
    if (isSetExtendedASTNode()) {
      hashCode += prime * getExtendedASTNode().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#isSetExtendedASTNode()
   */
  @Override
  public boolean isSetExtendedASTNode() {
    return extendedASTNode != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#isSetPackageVersion()
   */
  @Override
  public boolean isSetPackageVersion() {
    return packageVersion > 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#removeFromParent()
   */
  @Override
  public boolean removeFromParent() {

    if (isSetExtendedASTNode()) {
      int n = extendedASTNode.getExtensionCount();
      extendedASTNode.unsetExtension(getPackageName());

      return n > extendedASTNode.getExtensionCount();
    }

    return super.removeFromParent();
  }

  /**
   * Sets the XML namespace to which this {@link ASTNodePlugin} belong.
   * 
   * <p>This an internal method that should not be used outside of the main jsbml code
   * (core + packages). One class should always belong to the same namespace, although the namespaces can
   * have different level and version (and package version). You have to know what you are doing
   * when using this method.
   * 
   * @param namespace the XML namespace to which this {@link ASTNodePlugin} belong.
   */
  public void setNamespace(String namespace) {

    if ((elementNamespace != null) && (namespace != null) && (!elementNamespace.equals(namespace))) {
      // if we implement proper conversion some days, we need to unset the namespace before changing it.
      logger.error(MessageFormat.format("An ASTNodePlugin element cannot belong to two different namespaces! Current namespace = ''{0}'', new namespace = ''{1}''", elementNamespace, namespace));
    }
    String old = elementNamespace;
    elementNamespace = namespace;

    firePropertyChange(TreeNodeChangeEvent.namespace, old, namespace);
  }

  /**
   * Sets the extended {@link ASTNode}.
   * 
   * <p>This method should not be called in general but it is necessary
   * to use it after cloning an {@link ASTNodePlugin} to be able to set properly
   * the new parent/extended {@link ASTNode}.
   * 
   * 
   * @param extendedASTNode
   */
  public void setExtendedASTNode(ASTNode extendedASTNode) {

    // TODO - unregister children if extendedASTNode was not null !!??
    // Or do we throw an exception asking to clone the object instead ??
    ASTNode oldExtendedASTNode = this.extendedASTNode;
    this.extendedASTNode = extendedASTNode;

    // changes in AbstractSBase#firePropertyChange might make this code unnecessary but we would need to update all the code
    // to remove calls to registerChild that won't be necessary anymore and that would print a warning message
    if (getChildCount() > 0) {
      for (int i = 0; i < getChildCount(); i++) {
        TreeNode child = getChildAt(i);

        if (child instanceof SBase) {
          // this.extendedASTNode.registerChild((SBase) child); // TODO - check if we need to do that for ASTNode ?
          // At the moment, no package plan to add more that attributes
          // TODO - if an error occur, we might have to unregister the first children, from i - 1 down to 0 ?
        }
      }
    }

    firePropertyChange("extendedASTNode", oldExtendedASTNode, extendedASTNode);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#setPackageVersion(int)
   */
  @Override
  public void setPackageVersion(int packageVersion) {
    int oldVersion = this.packageVersion;
    this.packageVersion = packageVersion;
    firePropertyChange("packageVersion", oldVersion, packageVersion);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.ASTNodePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    return new TreeMap<String, String>();
  }

}
