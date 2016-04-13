/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2016 jointly by the following organizations:
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

import org.sbml.jsbml.IdentifierException;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.IdManager;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.validator.SyntaxChecker;

// TODO - get rid of id and name and all associated methods as soon as we use java 8 and we can put default method on interfaces

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.2
 */
public class ListOfMembers extends ListOf<Member> implements NamedSBase, UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6306206652658549671L;

  /**
   * id of the SBML component (can be optional depending on the level and
   * version). Matches the id attribute of an element in a SBML file.
   */
  private String id;

  /**
   * name of the SBML component (can be optional depending on the level and
   * version). Matches the name attribute of an element in a SBML file.
   */
  private String name;

  /**
   * 
   */
  public ListOfMembers() {
    super();
    setPackageVersion(-1);
    packageName = GroupsConstants.shortLabel;
  }

  /**
   * @param level
   * @param version
   */
  public ListOfMembers(int level, int version) {
    super(level, version);
    setPackageVersion(-1);
    packageName = GroupsConstants.shortLabel;
  }

  /**
   * @param listOf
   */
  public ListOfMembers(ListOfMembers listOf) {
    super(listOf);

    if (listOf.isSetName()) {
      setName(listOf.getName());
    }
    if (listOf.isSetId()) {
      setId(listOf.getId());
    }
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#clone()
   */
  @Override
  public ListOfMembers clone() {
    return new ListOfMembers(this);
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
    ListOfMembers other = (ListOfMembers) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#getId()
   */
  @Override
  public String getId() {
    return isSetId() ? id : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#getName()
   */
  @Override
  public String getName() {
    return isSetName() ? name : "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 829;
    int hashCode = super.hashCode();
    if (isSetId()) {
      hashCode *= prime * getId().hashCode();
    }
    if (isSetName()) {
      hashCode *= prime * getName().hashCode();
    }

    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isSetId()
   */
  @Override
  public boolean isSetId() {
    return id != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isSetName()
   */
  @Override
  public boolean isSetName() {
    return name != null;
  }

  /**
   * Checks if the sID is a valid identifier.
   * 
   * @param sID
   *            the identifier to be checked. If null or an invalid
   *            identifier, an exception will be thrown.
   * @return {@code true} only if the sID is a valid identifier.
   *         Otherwise this method throws an {@link IllegalArgumentException}.
   *         This is an intended behavior.
   * @throws IllegalArgumentException
   *             if the given id is not valid in this model.
   */
  boolean checkIdentifier(String sID) {
    if ((sID == null) || !SyntaxChecker.isValidId(sID, getLevel(), getVersion())) {
      throw new IllegalArgumentException(MessageFormat.format(
        "\"{0}\" is not a valid identifier for this {1}.", sID, getElementName()));
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    String property = TreeNodeChangeEvent.id;
    String oldId = this.id;

    // unregister id
    IdManager idManager = getIdManager(this);
    if (idManager != null) { // (oldId != null) // As the register and unregister are recursive, we need to call the unregister all the time until we have a non recursive method
      // Delete previous identifier only if defined.
      idManager.unregister(this); // TODO - do we need non recursive method on the IdManager interface ??
    }

    if ((id == null) || (id.trim().length() == 0)) {
      this.id = null;
    } else if (checkIdentifier(id)) {
      this.id = id;
    }

    // register the new id
    // TODO - could it be done by a protected method in AbstractSBase
    if ((idManager != null) && !idManager.register(this)) {
      IdentifierException exc = new IdentifierException(this, this.id);
      this.id = oldId; // restore the previous setting!
      throw new IllegalArgumentException(exc);
    }

    firePropertyChange(property, oldId, this.id);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#setName(java.lang.String)
   */
  @Override
  public void setName(String name) {
    // removed the call to the trim() function as a name with only space
    // should be considered valid.
    String oldName = this.name;
    if ((name == null) || (name.length() == 0)) {
      this.name = null;
    } else {
      this.name = name;
    }

    firePropertyChange(TreeNodeChangeEvent.name, oldName, this.name);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#unsetId()
   */
  @Override
  public void unsetId() {
    setId(null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#unsetName()
   */
  @Override
  public void unsetName() {
    setName(null);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      if (attributeName.equals("id")) {
        setId(value);
        return true;
      } else if (attributeName.equals("name")) {
        setName(value);

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

    if (isSetId()) {
      attributes.put(GroupsConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.put(GroupsConstants.shortLabel + ":name", getName());
    }

    return attributes;
  }

  @Override
  public boolean isIdMandatory() {
    return false;
  }
}
