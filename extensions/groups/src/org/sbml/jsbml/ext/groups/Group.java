/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
package org.sbml.jsbml.ext.groups;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev$
 */
public class Group extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2361503116934849753L;

  /**
   * Defined in version 3 of the groups proposal.
   */
  private GroupKind kind = null;

  /**
   * 
   */
  protected ListOf<Member> listOfMembers = null;

  private ListOfMemberConstraint listOfMemberConstraints = null;

  /**
   * 
   */
  public Group() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param group
   */
  public Group(Group group) {
    super(group);

    if (group.isSetListOfMembers()) {
      // TODO - update to have a proper clone of the ListOf as well
      for (Member m : group.listOfMembers) {
        addMember(m.clone());
      }
    }
    if (group.isSetKind()) {
      setKind(group.getKind());
    }
    if (group.isSetListOfMemberConstraints()) {
      setListOfMemberConstraints((ListOfMemberConstraint) group.getListOfMemberConstraints().clone());
    }
  }

  /**
   * 
   * @param level
   * @param version
   */
  public Group(int level, int version) {
    super(level, version);
  }

  /**
   * 
   */
  public boolean addMember(Member member) {
    return getListOfMembers().add(member);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Group clone() {
    return new Group(this);
  }

  /**
   * Creates a new instance of {@link Member} and add it to this {@link Group}.
   * 
   * @param id the id to be set to the new {@link Member}.
   * @return the new {@link Member} instance.
   */
  public Member createMember(String id) {
    Member m = new Member();
    m.setId(id);
    addMember(m);
    return m;
  }

  /**
   * Creates a new instance of {@link Member} and add it to this {@link Group}.
   * 
   * @param idRef the identifier of an object elsewhere in the Model. 
   * An example value of idRef might be the identifier of a species in the model, or the identifier of another group.
   * 
   * @return the new {@link Member} instance.
   */
  public Member createMemberWithIdRef(String idRef) {
    Member m = new Member();
    m.setIdRef(idRef);
    addMember(m);
    return m;
  }

  /**
   * Creates a new instance of {@link Member} and add it to this {@link Group}.
   *
   * @param metaIdRef
   * @return the new {@link Member} instance.
   */
  public Member createMemberWithMetaIdRef(String metaIdRef) {
    Member m = new Member();
    m.setMetaIdRef(metaIdRef);
    addMember(m);
    return m;
  }

  
  /**
   * Creates a new instance of {@link Member} and add it to this {@link Group}.
   *
   * @param id the id to be set to the new {@link Member}.
   * @param idRef the identifier of an object elsewhere in the Model. 
   * An example value of idRef might be the identifier of a species in the model, or the identifier of another group.
   * @return the new {@link Member} instance.
   */
  public Member createMemberWithIdRef(String id, String idRef) {
    Member m = new Member();
    m.setId(id);
    m.setIdRef(idRef);
    addMember(m);
    return m;
  }

  /**
   * Creates a new instance of {@link Member} and add it to this {@link Group}.
   * 
   * @param id the id to be set to the new {@link Member}.
   * @param metaIdRef
   * @return the new {@link Member} instance.
   */
  public Member createMemberWithMetaIdRef(String id, String metaIdRef) {
    Member m = new Member();
    m.setId(id);
    m.setMetaIdRef(metaIdRef);
    addMember(m);
    return m;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }

    int count = super.getChildCount();
    int position = 0;

    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetListOfMembers()) {
      if (position == index) {
        return getListOfMembers();
      }
      position++;
    }

    if (isSetListOfMemberConstraints()) {
      if (position == index) {
        return getListOfMemberConstraints();
      }
      position++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", index,
      +Math.min(position, 0)));
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int childCount = super.getChildCount();

    if (isSetListOfMembers()) {
      childCount++;
    }
    if (isSetListOfMemberConstraints()) {
      childCount++;
    }

    return childCount;
  }

  /**
   * 
   * @return
   */
  public GroupKind getKind() {
    return kind;
  }

  /**
   * 
   * @return
   */
  public ListOf<Member> getListOfMembers() {
    if (!isSetListOfMembers()) {
      listOfMembers = new ListOf<Member>();
      listOfMembers.setNamespace(GroupsConstants.namespaceURI);
      registerChild(listOfMembers);
      listOfMembers.setSBaseListType(ListOf.Type.other);
    }

    return listOfMembers;
  }

  /**
   * 
   * @param i
   * @return
   */
  public Member getMember(int i) {
    if (i >= 0 && i < getListOfMembers().size()) {
      return getListOfMembers().get(i);
    }

    return null;
  }

  /**
   * Returns the number of {@link Member}s of this {@link Group}.
   * 
   * @return the number of {@link Member}s of this {@link Group}.
   */
  public int getMemberCount() {
    return isSetListOfMembers() ? getListOfMembers().size() : 0;
  }

  /**
   * Returns the number of {@link MemberConstraint}s of this {@link Group}.
   * 
   * @return the number of {@link MemberConstraint}s of this {@link Group}.
   */
  public int getMemberConstraintCount() {
    return isSetListOfMemberConstraints() ? getListOfMemberConstraints().size() : 0;
  }

  /**
   * Returns the number of {@link Member}s of this {@link Group}.
   * 
   * @return the number of {@link Member}s of this {@link Group}.
   * @libsbml.deprecated same as {@link #getMemberCount()}
   */
  public int getNumMembers() {
    return getMemberCount();
  }

  /**
   * Returns the number of {@link MemberConstraint}s of this {@link Group}.
   * 
   * @return the number of {@link MemberConstraint}s of this {@link Group}.
   * @libsbml.deprecated same as {@link #getMemberConstraintCount()}
   */
  public int getNumMemberConstraints() {
    return getMemberConstraintCount();
  }

  private void initDefaults() {
    setNamespace(GroupsConstants.namespaceURI);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }


  /**
   * 
   * @return
   */
  public boolean isSetKind() {
    return kind!=null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfMembers() {
    if (listOfMembers == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns {@code true}, if listOfMemberConstraints contains at least one element.
   *
   * @return {@code true}, if listOfMemberConstraints contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfMemberConstraints() {
    if ((listOfMemberConstraints == null) || listOfMemberConstraints.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the listOfMemberConstraints. Creates it if it is not already existing.
   *
   * @return the listOfMemberConstraints
   */
  public ListOf<MemberConstraint> getListOfMemberConstraints() {
    if (!isSetListOfMemberConstraints()) {
      listOfMemberConstraints = new ListOfMemberConstraint(getLevel(), getVersion());
      listOfMemberConstraints.setNamespace(GroupsConstants.namespaceURI);
      listOfMemberConstraints.setSBaseListType(ListOf.Type.other);
      registerChild(listOfMemberConstraints);
    }
    return listOfMemberConstraints;
  }

  // TODO - helper method with setListOfMemberConstraints(ListOf<MemberConstraint>) ??

  /**
   * Sets the given {@code ListOf<MemberConstraint>}. If listOfMemberConstraints
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfMemberConstraints
   */
  public void setListOfMemberConstraints(ListOfMemberConstraint listOfMemberConstraints) {
    unsetListOfMemberConstraints();
    this.listOfMemberConstraints = listOfMemberConstraints;
    registerChild(this.listOfMemberConstraints);
  }

  /**
   * Returns {@code true}, if listOfMemberConstraints contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfMemberConstraints contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfMemberConstraints() {
    if (isSetListOfMemberConstraints()) {
      ListOf<MemberConstraint> oldMemberConstraints = listOfMemberConstraints;
      listOfMemberConstraints = null;
      oldMemberConstraints.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link MemberConstraint} to the listOfMemberConstraints.
   * <p>The listOfMemberConstraints is initialized if necessary.
   *
   * @param MemberConstraint the element to add to the list
   * @return true (as specified by {@link Collection.add})
   */
  public boolean addMemberConstraint(MemberConstraint memberConstraint) {
    return getListOfMemberConstraints().add(memberConstraint);
  }

  /**
   * Removes an element from the listOfMemberConstraints.
   *
   * @param MemberConstraint the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeMemberConstraint(MemberConstraint memberConstraint) {
    if (isSetListOfMemberConstraints()) {
      return getListOfMemberConstraints().remove(memberConstraint);
    }
    return false;
  }

  /**
   * Removes an element from the listOfMemberConstraints at the given index.
   *
   * @param i the index where to remove the {@link MemberConstraint}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removeMemberConstraint(int i) {
    if (!isSetListOfMemberConstraints()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfMemberConstraints().remove(i);
  }

  public void removeMemberConstraint(String id) {
    getListOfMemberConstraints().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new MemberConstraint element and adds it to the ListOfMemberConstraints list
   */
  public MemberConstraint createMemberConstraint() {
    return createMemberConstraint(null);
  }

  /**
   * Creates a new {@link MemberConstraint} element and adds it to the ListOfMemberConstraints list
   *
   * @return a new {@link MemberConstraint} element
   */
  public MemberConstraint createMemberConstraint(String id) {
    MemberConstraint MemberConstraint = new MemberConstraint(id, getLevel(), getVersion());
    addMemberConstraint(MemberConstraint);
    return MemberConstraint;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead && attributeName.equals("kind")) {
      try {
        setKind(GroupKind.valueOf(value));
        isAttributeRead = true;
      } catch (Exception e) {
        throw new SBMLException("Could not recognized the value '" + value
          + "' for the attribute 'kind' on the 'group' element.");
      }
    }

    return isAttributeRead;
  }

  /**
   * 
   * @param kind
   */
  public void setKind(GroupKind kind) {
    this.kind = kind;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Group [id=" + getId() + ", name=" + getName()
        + (isSetKind()?", kind=" + getKind():"")
        + ", listOfMembers=" + listOfMembers + "]";
  }

  /**
   * Removes the {@link #listOfMembers} from this {@link Model} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfMembers() {
    if (isSetListOfMembers()) {
      ListOf<Member> oldListOfMembers = listOfMembers;
      listOfMembers = null;
      oldListOfMembers.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(GroupsConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(GroupsConstants.shortLabel+ ":name", getName());
    }
    if (isSetKind()) {
      attributes.remove("kind");
      attributes.put(GroupsConstants.shortLabel+ ":kind", getKind().toString());
    }

    return attributes;
  }

  // TODO - add methods to get/set/... the attribute from the ListOfMemberConstraint


  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((kind == null) ? 0 : kind.hashCode());
    result = prime
      * result
      + ((listOfMemberConstraints == null) ? 0
        : listOfMemberConstraints.hashCode());
    result = prime * result
      + ((listOfMembers == null) ? 0 : listOfMembers.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Group other = (Group) obj;
    if (kind != other.kind) {
      return false;
    }
    if (listOfMemberConstraints == null) {
      if (other.listOfMemberConstraints != null) {
        return false;
      }
    } else if (!listOfMemberConstraints.equals(other.listOfMemberConstraints)) {
      return false;
    }
    if (listOfMembers == null) {
      if (other.listOfMembers != null) {
        return false;
      }
    } else if (!listOfMembers.equals(other.listOfMembers)) {
      return false;
    }
    return true;
  }

  
}
