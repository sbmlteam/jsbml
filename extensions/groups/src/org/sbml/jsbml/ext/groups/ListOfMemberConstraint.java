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
package org.sbml.jsbml.ext.groups;

import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * @deprecated This class was removed from the specifications as of version 0.7
 *             (2015-11-24) as no software wanted to implement support for it.
 *             It might be added back in a future version of the specifications
 *             if somebody want to implement it.
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@Deprecated
public class ListOfMemberConstraint extends ListOf<MemberConstraint> implements NamedSBase, UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6306206652658549671L;

  /**
   * 
   */
  private boolean membersShareType;
  /**
   * 
   */
  private boolean isSetMembersShareType;


  /**
   * 
   */
  public ListOfMemberConstraint() {
    super();
    initDefaults();
  }

  /**
   * @param level
   * @param version
   */
  public ListOfMemberConstraint(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * @param listOf
   */
  public ListOfMemberConstraint(ListOfMemberConstraint listOf) {
    super(listOf);

    if (listOf.isSetMembersShareType) {
      setMembersShareType(listOf.getMembersShareType());
    }
  }

  /**
   * 
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = GroupsConstants.shortLabel;
    setOtherListName(GroupsConstants.listOfMemberConstraints);
    setSBaseListType(ListOf.Type.other);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#clone()
   */
  @Override
  public ListOfMemberConstraint clone() {
    return new ListOfMemberConstraint(this);
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
    ListOfMemberConstraint other = (ListOfMemberConstraint) obj;
    if (membersShareType != other.membersShareType) {
      return false;
    }

    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 829;
    int hashCode = super.hashCode();
    hashCode = prime * hashCode + (membersShareType ? 1231 : 1237);

    return hashCode;
  }

  /**
   * Returns the value of membersShareType
   *
   * @return the value of membersShareType
   */
  public boolean getMembersShareType() {

    if (isSetMembersShareType()) {
      return membersShareType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(GroupsConstants.membersShareType, this);
  }

  /**
   * Returns whether membersShareType is set
   *
   * @return whether membersShareType is set
   */
  public boolean isSetMembersShareType() {
    return isSetMembersShareType;
  }

  /**
   * Sets the value of membersShareType
   * @param membersShareType
   */
  public void setMembersShareType(boolean membersShareType) {
    boolean oldMembersShareType = this.membersShareType;
    this.membersShareType = membersShareType;
    isSetMembersShareType = true;

    firePropertyChange(GroupsConstants.membersShareType, oldMembersShareType, this.membersShareType);
  }

  /**
   * Unsets the variable membersShareType
   *
   * @return {@code true}, if membersShareType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMembersShareType() {
    if (isSetMembersShareType()) {
      boolean oldMembersShareType = membersShareType;
      membersShareType = false;
      isSetMembersShareType = false;

      firePropertyChange(GroupsConstants.membersShareType, oldMembersShareType, membersShareType);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      if (attributeName.equals(GroupsConstants.membersShareType)) {
        setMembersShareType(StringTools.parseSBMLBoolean(value));

        return true;
      }
    }

    return isAttributeRead;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetMembersShareType) {
      attributes.put(GroupsConstants.shortLabel + ":" + GroupsConstants.membersShareType, Boolean.toString(getMembersShareType()));
    }

    return attributes;
  }

}
