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
package org.sbml.jsbml.ext.groups;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 */
public class Group extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * This is a collection of possible values for the {@code kind} attribute within
   * a {@link Group}.
   * 
   */
  public enum Kind {
    /**
     * The group represents a class, and its members have an <i>is-a</i> relationship to the group. For example, the
     * group could represent a type of molecule such as ATP, and the members could be species located in different
     * compartments, thereby establishing that the species are pools of the same molecule in different locations.
     */
    classification,
    /**
     * The group represents a collection of parts, and its members have a <i>part-of</i> relationship to the group. For example,
     * the group could represent a cellular structure, and individual compartments could be made members
     * of the group to indicate they represent subparts of that cellular structure
     */
    partonomy,
    /**
     * The grouping is merely a collection for convenience, without an implied relationship between the members.
     * For example, the group could be used to collect together multiple disparate components of a model—species,
     * reactions, events—involved in a particular phenotype, and apply a common annotation rather than having to
     * copy the same annotation to each component individually.
     */
    collection
  }

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2361503116934849753L;

  /**
   * This attribute is used to indicate the nature of the group
   */
  private Kind kind = null;

  /**
   * 
   */
  protected ListOfMembers listOfMembers = null;

  /**
   * 
   */
  // removed as unsupported but could be added again in the future  private ListOfMemberConstraint listOfMemberConstraints = null;

  /**
   * Creates a new {@link Group} instance.
   */
  public Group() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link Group} instance from the given Group.
   * 
   * @param group the {@link Group} to clone
   */
  public Group(Group group) {
    super(group);

    if (group.isSetListOfMembers()) {
      setListOfMembers(group.getListOfMembers().clone());
    }
    if (group.isSetKind()) {
      setKind(group.getKind());
    }

    // removed as unsupported but could be added again in the future
    //    if (group.isSetListOfMemberConstraints()) {
    //      setListOfMemberConstraints(group.getListOfMemberConstraints().clone());
    //    }
  }

  /**
   * Creates a new {@link Group} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Group(int level, int version) {
    super(level, version);
    initDefaults();
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
    if (id != null) {
      m.setId(id);
    }
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
    if (id != null) {
      m.setId(id);
    }
    m.setMetaIdRef(metaIdRef);
    addMember(m);
    return m;
  }

  /**
   * @param sbase
   *        the element that should be referenced as a new member of this
   *        {@link Group}.
   * @return the newly created {@link Member} or {@code null} if the given
   *         {@link SBase} neither has a metaId nor an id.
   * @see #createMember(String, SBase)
   */
  public Member creteMember(SBase sbase) {
    return createMember(null, sbase);
  }

  /**
   * @param id
   *        the identifier to be set for the new {@link Member}, can be
   *        {@code null}.
   * @param sbase
   *        the element that should be referenced as a new member of this
   *        {@link Group}.
   * @return the newly created {@link Member} or {@code null} if the given
   *         {@link SBase} neither has a metaId nor an id.
   */
  public Member createMember(String id, SBase sbase) {
    if (sbase.isSetId()) {
      return createMemberWithIdRef(id, sbase.getId());
    }
    if (sbase.isSetMetaId()) {
      return createMemberWithMetaIdRef(id, sbase.getMetaId());
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
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

    // removed as unsupported but could be added again in the future
    //    if (isSetListOfMemberConstraints()) {
    //      if (position == index) {
    //        return getListOfMemberConstraints();
    //      }
    //      position++;
    //    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
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
    // removed as unsupported but could be added again in the future
    //    if (isSetListOfMemberConstraints()) {
    //      childCount++;
    //    }

    return childCount;
  }

  /**
   * 
   * @return
   */
  public Kind getKind() {
    return kind;
  }

  /**
   * Returns the {@link #listOfMembers}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfMembers}.
   */
  public ListOfMembers getListOfMembers() {
    if (!isSetListOfMembers()) {
      listOfMembers = new ListOfMembers();      
      registerChild(listOfMembers);
    }

    return listOfMembers;
  }


  /**
   * Returns {@code true} if {@link #listOfMembers} is not null.
   *
   * @return {@code true} if {@link #listOfMembers} is not null.
   */
  public boolean isSetListOfMembers() {
    return listOfMembers != null;
  }


  /**
   * Sets the given {@code ListOf<Member>}.
   * If {@link #listOfMembers} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfMembers
   */
  public void setListOfMembers(ListOfMembers listOfMembers) {
    unsetListOfMembers();
    this.listOfMembers = listOfMembers;

    if (listOfMembers != null) {
      registerChild(listOfMembers);
    }
  }


  /**
   * Returns {@code true} if {@link #listOfMembers} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfMembers} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfMembers() {
    if (isSetListOfMembers()) {
      ListOfMembers oldMembers = listOfMembers;
      listOfMembers = null;
      oldMembers.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link Member} to the {@link #listOfMembers}.
   * <p>The listOfMembers is initialized if necessary.
   *
   * @param member the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addMember(Member member) {
    return getListOfMembers().add(member);
  }


  /**
   * Removes an element from the {@link #listOfMembers}.
   *
   * @param member the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeMember(Member member) {
    if (isSetListOfMembers()) {
      return getListOfMembers().remove(member);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfMembers}.
   *
   * @param id the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public Member removeMember(String id) {
    if (isSetListOfMembers()) {
      return getListOfMembers().remove(id);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfMembers} at the given index.
   *
   * @param i the index where to remove the {@link Member}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfMembers)}).
   */
  public Member removeMember(int i) {
    if (!isSetListOfMembers()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfMembers().remove(i);
  }


  /**
   * Creates a new Member element and adds it to the
   * {@link #listOfMembers} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfMembers}
   */
  public Member createMember() {
    return createMember(null);
  }


  /**
   * Creates a new {@link Member} element and adds it to the
   * {@link #listOfMembers} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link Member} element, which is the last
   *         element in the {@link #listOfMembers}.
   */
  public Member createMember(String id) {
    Member member = new Member();
    member.setId(id);
    addMember(member);
    return member;
  }


  /**
   * Gets an element from the {@link #listOfMembers} at the given index.
   *
   * @param i the index of the {@link Member} element to get.
   * @return an element from the listOfMembers at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public Member getMember(int i) {
    if (!isSetListOfMembers()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfMembers().get(i);
  }


  /**
   * Gets an element from the listOfMembers, with the given id.
   *
   * @param id the id of the {@link Member} element to get.
   * @return an element from the listOfMembers with the given id
   *         or {@code null}.
   */
  public Member getMember(String id) {
    if (isSetListOfMembers()) {
      return getListOfMembers().get(id);
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

  // removed as unsupported but could be added again in the future
  //  /**
  //   * Returns the number of {@link MemberConstraint}s of this {@link Group}.
  //   *
  //   * @return the number of {@link MemberConstraint}s of this {@link Group}.
  //   */
  //  public int getMemberConstraintCount() {
  //    return isSetListOfMemberConstraints() ? getListOfMemberConstraints().size() : 0;
  //  }

  /**
   * Returns the number of {@link Member}s of this {@link Group}.
   * 
   * @return the number of {@link Member}s of this {@link Group}.
   * @libsbml.deprecated same as {@link #getMemberCount()}
   */
  public int getNumMembers() {
    return getMemberCount();
  }

  // removed as unsupported but could be added again in the future
  //  /**
  //   * Returns the number of {@link MemberConstraint}s of this {@link Group}.
  //   *
  //   * @return the number of {@link MemberConstraint}s of this {@link Group}.
  //   * @libsbml.deprecated same as {@link #getMemberConstraintCount()}
  //   */
  //  public int getNumMemberConstraints() {
  //    return getMemberConstraintCount();
  //  }

  /**
   * 
   */
  private void initDefaults() {
    packageName = GroupsConstants.shortLabel;
    setPackageVersion(-1);
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


  //
  //  /**
  //   * Returns {@code true}, if listOfMemberConstraints is not null.
  //   *
  //   * @return {@code true}, if listOfMemberConstraints is not null,
  //   *         otherwise {@code false}
  //   */
  //  public boolean isSetListOfMemberConstraints() {
  //    return listOfMemberConstraints != null;
  //  }
  //
  //  /**
  //   * Returns the listOfMemberConstraints. Creates it if it is not already existing.
  //   *
  //   * @return the listOfMemberConstraints
  //   */
  //  public ListOfMemberConstraint getListOfMemberConstraints() {
  //    if (!isSetListOfMemberConstraints()) {
  //      listOfMemberConstraints = new ListOfMemberConstraint(getLevel(), getVersion());
  //      listOfMemberConstraints.setPackageVersion(-1);
  //      // changing the ListOf package name from 'core' to 'groups'
  //      listOfMemberConstraints.setPackageName(null);
  //      listOfMemberConstraints.setPackageName(GroupsConstants.shortLabel);
  //      listOfMemberConstraints.setSBaseListType(ListOf.Type.other);
  //      registerChild(listOfMemberConstraints);
  //    }
  //    return listOfMemberConstraints;
  //  }
  //
  //  /**
  //   * Sets the given {@code ListOf<MemberConstraint>}. If listOfMemberConstraints
  //   * was defined before and contains some elements, they are all unset.
  //   *
  //   * @param listOfMemberConstraints
  //   */
  //  public void setListOfMemberConstraints(ListOfMemberConstraint listOfMemberConstraints) {
  //    unsetListOfMemberConstraints();
  //    this.listOfMemberConstraints = listOfMemberConstraints;
  //
  //    if (listOfMemberConstraints != null) {
  //      listOfMemberConstraints.setPackageVersion(-1);
  //      // changing the ListOf package name from 'core' to 'groups'
  //      listOfMemberConstraints.setPackageName(null);
  //      listOfMemberConstraints.setPackageName(GroupsConstants.shortLabel);
  //      listOfMemberConstraints.setSBaseListType(ListOf.Type.other);
  //
  //      registerChild(this.listOfMemberConstraints);
  //    }
  //  }
  //
  //  /**
  //   * Returns {@code true}, if listOfMemberConstraints contain at least one element,
  //   *         otherwise {@code false}
  //   *
  //   * @return {@code true}, if listOfMemberConstraints contain at least one element,
  //   *         otherwise {@code false}
  //   */
  //  public boolean unsetListOfMemberConstraints() {
  //    if (isSetListOfMemberConstraints()) {
  //      ListOf<MemberConstraint> oldMemberConstraints = listOfMemberConstraints;
  //      listOfMemberConstraints = null;
  //      oldMemberConstraints.fireNodeRemovedEvent();
  //      return true;
  //    }
  //    return false;
  //  }
  //
  //  /**
  //   * Adds a new {@link MemberConstraint} to the listOfMemberConstraints.
  //   * <p>The listOfMemberConstraints is initialized if necessary.
  //   *
  //   * @param memberConstraint the element to add to the list
  //   * @return true (as specified by {@link java.util.Collection#add})
  //   */
  //  public boolean addMemberConstraint(MemberConstraint memberConstraint) {
  //    return getListOfMemberConstraints().add(memberConstraint);
  //  }
  //
  //  /**
  //   * Removes an element from the listOfMemberConstraints.
  //   *
  //   * @param memberConstraint the element to be removed from the list
  //   * @return true if the list contained the specified element
  //   * @see java.util.List#remove(Object)
  //   */
  //  public boolean removeMemberConstraint(MemberConstraint memberConstraint) {
  //    if (isSetListOfMemberConstraints()) {
  //      return getListOfMemberConstraints().remove(memberConstraint);
  //    }
  //    return false;
  //  }
  //
  //  /**
  //   * Removes an element from the listOfMemberConstraints at the given index.
  //   *
  //   * @param i the index where to remove the {@link MemberConstraint}
  //   * @throws IndexOutOfBoundsException if the listOf is not set or
  //   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
  //   */
  //  public void removeMemberConstraint(int i) {
  //    if (!isSetListOfMemberConstraints()) {
  //      throw new IndexOutOfBoundsException(Integer.toString(i));
  //    }
  //    getListOfMemberConstraints().remove(i);
  //  }
  //
  //  /**
  //   * @param id
  //   */
  //  public void removeMemberConstraint(String id) {
  //    getListOfMemberConstraints().removeFirst(new NameFilter(id));
  //  }
  //
  //  /**
  //   * Creates a new MemberConstraint element and adds it to the ListOfMemberConstraints list
  //   * @return
  //   */
  //  public MemberConstraint createMemberConstraint() {
  //    return createMemberConstraint(null);
  //  }
  //
  //  /**
  //   * Creates a new {@link MemberConstraint} element and adds it to the ListOfMemberConstraints list
  //   * @param id
  //   *
  //   * @return a new {@link MemberConstraint} element
  //   */
  //  public MemberConstraint createMemberConstraint(String id) {
  //    MemberConstraint MemberConstraint = new MemberConstraint(id, getLevel(), getVersion());
  //    addMemberConstraint(MemberConstraint);
  //    return MemberConstraint;
  //  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead && attributeName.equals("kind")) {
      try {
        setKind(Kind.valueOf(value));
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
  public void setKind(Kind kind) {
    this.kind = kind;
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
    // removed as unsupported but could be added again in the future
    //    result = prime
    //        * result
    //        + ((listOfMemberConstraints == null) ? 0
    //          : listOfMemberConstraints.hashCode());
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
    // removed as unsupported but could be added again in the future
    //    if (listOfMemberConstraints == null) {
    //      if (other.listOfMemberConstraints != null) {
    //        return false;
    //      }
    //    } else if (!listOfMemberConstraints.equals(other.listOfMemberConstraints)) {
    //      return false;
    //    }
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
