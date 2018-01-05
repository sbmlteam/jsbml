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

package org.sbml.jsbml.validator.offline.constraints.helper;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 *
 */
public class CycleDetectionTreeNode {

  private String                      name;

  private Set<CycleDetectionTreeNode> parents;
  private Set<CycleDetectionTreeNode> children;


  public CycleDetectionTreeNode(String name) {
    this.name = name;
    parents = new HashSet<CycleDetectionTreeNode>();
    children = new HashSet<CycleDetectionTreeNode>();
  }


  public String getName() {
    return this.name;
  }


  @Override
  public boolean equals(Object obj) {

    if (obj instanceof CycleDetectionTreeNode) {
      CycleDetectionTreeNode node = (CycleDetectionTreeNode) obj;
      return node.getName().equals(this.name);
    }
    return super.equals(obj);
  }


  public boolean addChild(CycleDetectionTreeNode child) {
    if (this.isAncestor(child)) {
      return false;
    }

    this.children.add(child);
    child.addParent(this);

    return true;
  }


  public void addParent(CycleDetectionTreeNode parent) {
    this.parents.add(parent);
  }


  public Set<CycleDetectionTreeNode> getParents() {
    return this.parents;
  }


  public boolean isAncestor(CycleDetectionTreeNode node) {
    if (node.name.equals(this.name)) {
      return true;
    }

    for (CycleDetectionTreeNode parent : node.getParents()) {
      if (parent.isAncestor(node)) {
        return true;
      }
    }

    return false;
  }

}
