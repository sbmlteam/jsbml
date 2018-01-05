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

import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public class ListOfMembers extends ListOf<Member> implements NamedSBase, UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6306206652658549671L;

  /**
   * Creates a new instance of {@link ListOfMembers}.
   */
  public ListOfMembers() {
    super();
    initDefaults();
  }

  /**
   * Creates a new instance of {@link ListOfMembers}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public ListOfMembers(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new instance of {@link ListOfMembers}, cloned from the given {@code listOf}.
   * 
   * @param listOf the list to clone.
   */
  public ListOfMembers(ListOfMembers listOf) {
    super(listOf);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#clone()
   */
  @Override
  public ListOfMembers clone() {
    return new ListOfMembers(this);
  }

  /**
   * 
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = GroupsConstants.shortLabel;
    setOtherListName(GroupsConstants.listOfMembers);
    setSBaseListType(ListOf.Type.other);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 829;
    int hashCode = super.hashCode();

    return prime * hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(GroupsConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(GroupsConstants.shortLabel + ":name", getName());
    }

    return attributes;
  }

  
}
