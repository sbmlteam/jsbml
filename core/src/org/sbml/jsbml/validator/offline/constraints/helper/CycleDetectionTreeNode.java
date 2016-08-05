package org.sbml.jsbml.validator.offline.constraints.helper;

import java.util.HashSet;
import java.util.Set;

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
