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

import static java.text.MessageFormat.format;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.validator.SyntaxChecker;

/**
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 */
public class Member extends AbstractNamedSBase  implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1726020714284762330L;

  /**
   * 
   */
  private String idRef;

  /**
   * 
   */
  private String metaIdRef;

  /**
   * Creates a new {@link Member} instance.
   */
  public Member() {
    super();
    initDefaults();
  }

  /**
   * 
   */
  private void initDefaults() {
    packageName = GroupsConstants.shortLabel;
    setPackageVersion(-1);
  }

  /**
   * Creates a new {@link Member} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Member(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new {@link Member} instance.
   * 
   * @param member the {@link Member} instance to clone
   */
  public Member(Member member) {
    super(member);

    if (member.isSetIdRef()) {
      setIdRef(member.getIdRef());
    }
    if (member.isSetMetaIdRef()) {
      setIdRef(member.getMetaIdRef());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Member clone() {
    return new Member(this);
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2671;
    int result = super.hashCode();
    result = prime * result + ((idRef == null) ? 0 : idRef.hashCode());
    result = prime * result + ((metaIdRef == null) ? 0 : metaIdRef.hashCode());
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
    Member other = (Member) obj;
    if (idRef == null) {
      if (other.idRef != null) {
        return false;
      }
    } else if (!idRef.equals(other.idRef)) {
      return false;
    }
    if (metaIdRef == null) {
      if (other.metaIdRef != null) {
        return false;
      }
    } else if (!metaIdRef.equals(other.metaIdRef)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of idRef
   *
   * @return the value of idRef
   */
  public String getIdRef() {
    if (isSetIdRef()) {
      return idRef;
    }

    return null;
  }

  /**
   * Returns whether idRef is set
   *
   * @return whether idRef is set
   */
  public boolean isSetIdRef() {
    return idRef != null;
  }

  /**
   * Sets the value of idRef
   * 
   * @param idRef the value of idRef
   */
  public void setIdRef(String idRef) {
    
    if (idRef != null && idRef.trim().length() == 0) {
      idRef = null;
    }
    
    if (!isReadingInProgress() && idRef != null) {
      // This method will throw IllegalArgumentException if the given id does not respect the SId syntax
      checkIdentifier(idRef);
    }
    
    String oldIdRef = this.idRef;
    this.idRef = idRef;
    firePropertyChange(GroupsConstants.idRef, oldIdRef, this.idRef);
  }

  /**
   * Unsets the variable idRef
   *
   * @return {@code true}, if idRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetIdRef() {
    if (isSetIdRef()) {
      String oldIdRef = idRef;
      idRef = null;
      firePropertyChange(GroupsConstants.idRef, oldIdRef, idRef);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of metaIdRef
   *
   * @return the value of metaIdRef
   */
  public String getMetaIdRef() {
    if (isSetMetaIdRef()) {
      return metaIdRef;
    }

    return null;
  }

  /**
   * Returns whether metaIdRef is set
   *
   * @return whether metaIdRef is set
   */
  public boolean isSetMetaIdRef() {
    return metaIdRef != null;
  }

  /**
   * Sets the value of metaIdRef
   * 
   * @param metaIdRef the value of metaIdRef
   */
  public void setMetaIdRef(String metaIdRef) {
    
    if (metaIdRef != null && metaIdRef.trim().length() == 0) {
      metaIdRef = null;
    }
    
    if (!isReadingInProgress() && metaIdRef != null) {
      // This method will throw IllegalArgumentException if the given id does not respect the SId syntax
      // checkIdentifier(idRef);
      boolean isValid = SyntaxChecker.isValidMetaId(metaIdRef);
      
      if (!isValid) {
        throw new IllegalArgumentException(format(
            resourceBundle.getString("AbstractSBase.setMetaId"),
            metaIdRef, getElementName()));
      }
    }
    
    String oldMetaIdRef = this.metaIdRef;
    this.metaIdRef = metaIdRef;
    firePropertyChange(GroupsConstants.metaIdRef, oldMetaIdRef, this.metaIdRef);
  }

  /**
   * Unsets the variable metaIdRef
   *
   * @return {@code true}, if metaIdRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMetaIdRef() {
    if (isSetMetaIdRef()) {
      String oldMetaIdRef = metaIdRef;
      metaIdRef = null;
      firePropertyChange(GroupsConstants.metaIdRef, oldMetaIdRef, metaIdRef);
      return true;
    }
    return false;
  }

  /**
   * Gets the actual SBase instance referred by the {@code idRef} or {@code metaIdRef}, returns null
   * if nothing is found.
   * 
   * @return the actual SBase instance referred by the {@code idRef} or {@code metaIdRef}, returns null
   * if nothing is found.
   */
  public SBase getSBaseInstance() {

    SBase instance = null;

    if (isSetIdRef()) {
      Model model = getModel();

      if (model != null) {
        instance = model.findNamedSBase(idRef);
      }
    } else if (isSetMetaIdRef()) {
      SBMLDocument doc = getSBMLDocument();

      if (doc != null) {
        instance = doc.findSBase(metaIdRef);
      }
    }

    return instance;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      if (attributeName.equals(GroupsConstants.idRef)) {
        setIdRef(value);
        return true;
      }
      if (attributeName.equals(GroupsConstants.metaIdRef)) {
        setMetaIdRef(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /**
   * Sets the value of idRef, using the id defined in the given {@link SBase}
   * 
   * @param namedSbase the {@link SBase} that contain the id to be set.
   * 
   */
  public void setIdRef(SBase namedSbase) {
    setIdRef(namedSbase != null ? namedSbase.getId() : null);
  }

  /**
   * Sets the value of metaIdRef, using the metaid defined in the given {@link SBase}
   * 
   * @param sbase the {@link SBase} that contain the metaid to be set.
   * 
   */
  public void setMetaIdRef(SBase sbase) {
    setMetaIdRef(sbase != null ? sbase.getMetaId() : null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetIdRef()) {
      attributes.put(GroupsConstants.shortLabel + ":" + GroupsConstants.idRef, idRef);
    }
    if (isSetMetaIdRef()) {
      attributes.put(GroupsConstants.shortLabel + ":" + GroupsConstants.metaIdRef, metaIdRef);
    }
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

  @Override
  public boolean isIdMandatory() {
    return false;
  }

}
