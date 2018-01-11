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
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.validator.SyntaxChecker;

/**
 * Contains all the information about a creator of a {@link Model} (or other
 * {@link SBase} in level 3).
 *
 * @author Marine Dumousseau
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class Creator extends AnnotationElement {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Creator.class);


  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3403463908044292946L;

  /**
   * URI for the RDF syntax name space definition for VCards.
   */
  public static final transient String URI_RDF_VCARD_NS = "http://www.w3.org/2001/vcard-rdf/3.0#"; //$NON-NLS-1$

  /**
   * email of the creator
   */
  private String email;
  /**
   * Family name of the creator
   */
  private String familyName;
  /**
   * Given name of the creator
   */
  private String givenName;

  /**
   * Organisation name of the creator.
   */
  private String organisation;

  /**
   * Holding any additional vCard elements.
   * This is a quick and dirty solution as the vCard can contain
   * many elements that have sub-elements.
   *
   * TODO: use an XMLNode to hold all the additional informations
   *
   */
  private Map<String, String> otherAttributes;

  /**
   * Creates a {@link Creator} instance. By default, the email, familyName,
   * givenName, organisation are {@code null}.
   */
  public Creator() {
    super();
    givenName = null;
    familyName = null;
    organisation = null;
    email = null;
    otherAttributes = null;
  }

  /**
   * Creates a {@link Creator} instance from a given {@link Creator}.
   *
   * @param creator
   */
  public Creator(Creator creator) {
    super(creator);
    email = creator.isSetEmail() ? new String(creator.getEmail()) : null;
    familyName = creator.isSetFamilyName() ? new String(creator.getFamilyName()) : null;
    givenName = creator.isSetGivenName() ? new String(creator.getGivenName()) : null;
    organisation = creator.isSetOrganisation() ? new String(creator.getOrganisation()) : null;

    if (creator.isSetOtherAttributes()) {
      otherAttributes = new LinkedHashMap<String,String>();

      for (String key : creator.getOtherAttributes().keySet()) {
        otherAttributes.put(new String(key), new String(creator.getOtherAttribute(key)));
      }
    } else {
      otherAttributes = null;
    }
  }

  /**
   * Creates a {@link Creator} instance.
   *
   * @param givenName
   * @param familyName
   * @param organization
   * @param email
   */
  public Creator(String givenName, String familyName, String organization,
    String email) {
    this();
    setGivenName(givenName);
    setFamilyName(familyName);
    setOrganization(organization);
    setEmail(email);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Creator clone() {
    return new Creator(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Creator m = (Creator) object;
      equals &= isSetEmail() == m.isSetEmail();
      if (equals && isSetEmail()) {
        equals &= getEmail().equals(m.getEmail());
      }
      equals &= isSetFamilyName() == m.isSetFamilyName();
      if (equals && isSetFamilyName()) {
        equals &= getFamilyName().equals(m.getFamilyName());
      }
      equals &= isSetGivenName() == m.isSetGivenName();
      if (equals && isSetGivenName()) {
        equals &= getGivenName().equals(m.getGivenName());
      }
      equals &= isSetOrganisation() == m.isSetOrganisation();
      if (equals && isSetOrganisation()) {
        equals &= getOrganisation().equals(m.getOrganisation());
      }
      equals &= isSetOtherAttributes() == m.isSetOtherAttributes();
      if (equals && isSetOtherAttributes()) {
        equals &= getOtherAttributes().equals(m.getOtherAttributes());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    throw new IndexOutOfBoundsException(Integer.toString(childIndex));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /**
   * Returns the email from the {@link Creator}. Returns an empty String if it is
   *         not set.
   *
   * @return the email from the {@link Creator}. Returns an empty String if it is
   *         not set.
   */
  public String getEmail() {
    return isSetEmail() ? email : "";
  }

  /**
   * Returns the familyName from the {@link Creator}. Returns an empty String if
   *         it is not set.
   *
   * @return the familyName from the {@link Creator}. Returns an empty String if
   *         it is not set.
   */
  public String getFamilyName() {
    return isSetFamilyName() ? familyName : "";
  }

  /**
   * Returns the givenName from the {@link Creator}. Returns an empty String if
   * it is not set.
   *
   * @return the givenName from the {@link Creator}. Returns an empty String if
   * it is not set.
   */
  public String getGivenName() {
    return isSetGivenName() ? givenName : "";
  }

  /**
   * Returns the organisation from the {@link Creator}. Returns an empty String
   * if it is not set.
   *
   * @return the organisation from the {@link Creator}. Returns an empty String
   * if it is not set.
   */
  public String getOrganisation() {
    return isSetOrganisation() ? organisation : "";
  }

  /**
   * Returns the organisation from the {@link Creator}. Returns an empty String
   * if it is not set.
   * <p>
   * Equal to {@link #getOrganisation()}
   *
   * @return the organisation from the {@link Creator}. Returns an empty String
   * if it is not set.
   */
  public String getOrganization() {
    return getOrganisation();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 797;
    int hashCode = super.hashCode();
    if (isSetEmail()) {
      hashCode += prime * getEmail().hashCode();
    }
    if (isSetFamilyName()) {
      hashCode += prime * getFamilyName().hashCode();
    }
    if (isSetGivenName()) {
      hashCode += prime * getGivenName().hashCode();
    }
    if (isSetOrganization()) {
      hashCode += prime * getOrganization().hashCode();
    }
    if (isSetOtherAttributes()) {
      hashCode += prime * getOtherAttributes().hashCode();
    }
    return hashCode;
  }

  /**
   * Returns {@code true} or false depending on whether this
   * {@link Creator}'s email has been set.
   *
   * @return {@code true} if the email of this {@link Creator} is not {@code null}.
   */
  public boolean isSetEmail() {
    return email != null;
  }

  /**
   * Returns {@code true} or false depending on whether this
   * {@link Creator}'s familyName has been set.
   *
   * @return {@code true} if the familyName of this {@link Creator} is not {@code null}.
   */
  public boolean isSetFamilyName() {
    return familyName != null;
  }

  /**
   * Returns {@code true} or false depending on whether this
   * {@link Creator}'s givenName has been set.
   *
   * @return {@code true} if the givenName of this {@link Creator} is not {@code null}.
   */
  public boolean isSetGivenName() {
    return givenName != null;
  }

  /**
   * Returns {@code true} or false depending on whether this
   * {@link Creator}'s organisation has been set.
   *
   * @return {@code true} if the organisation of this {@link Creator} is not {@code null}.
   */
  public boolean isSetOrganisation() {
    return organisation != null;
  }

  /**
   * Returns {@code true} or false depending on whether this
   * {@link Creator}'s organisation has been set.
   * <p>Equal to {@link #isSetOrganisation()}
   *
   * @return {@code true} or false depending on whether this
   * {@link Creator}'s organisation has been set.
   */
  public boolean isSetOrganization() {
    return isSetOrganisation();
  }

  /**
   * Returns {@code true} if the XML attribute is known by this {@link Creator}.
   * @param elementName
   * @param attributeName
   * @param prefix
   * @param value
   *
   * @return {@code true} if the XML attribute is known by this {@link Creator}.
   */
  public boolean readAttribute(String elementName, String attributeName,
    String prefix, String value) {
    if (elementName.equals("li") || elementName.equals("N")
        || elementName.equals("ORG")) {
      if (attributeName.equals("parseType") && value.equals("Resource")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the email if it follows the syntax rules of valid e-mail addresses
   * according to
   * <a href="http://en.wikipedia.org/wiki/E-mail_address">http://en.wikipedia.org/wiki/E-mail_address</a>.
   *
   * @param email
   * @return {@link JSBML#OPERATION_SUCCESS}
   */
  public int setEmail(String email) {
    if ((email != null) && !SyntaxChecker.isValidEmailAddress(email.trim())) {
      String errorMessage = MessageFormat.format(resourceBundle.getString("Creator.setEmail"), email);
      logger.warn(errorMessage);
    }
    String oldValue = this.email;
    this.email = email.trim();
    firePropertyChange(TreeNodeChangeEvent.email, oldValue, email);
    return JSBML.OPERATION_SUCCESS;
  }

  /**
   * Sets the family name
   *
   * @param familyName
   * @return {@link JSBML#OPERATION_SUCCESS}
   */
  public int setFamilyName(String familyName) {
    String oldValue = this.familyName;
    this.familyName = familyName;
    firePropertyChange(TreeNodeChangeEvent.familyName, oldValue, familyName);
    return JSBML.OPERATION_SUCCESS;
  }

  /**
   * Sets the given name
   *
   * @param givenName
   * @return {@link JSBML#OPERATION_SUCCESS}
   */
  public int setGivenName(String givenName) {
    String oldValue = this.givenName;
    this.givenName = givenName;
    firePropertyChange(TreeNodeChangeEvent.givenName, oldValue, givenName);
    return JSBML.OPERATION_SUCCESS;
  }

  /**
   * Sets the organisation
   *
   * @param organisation
   */
  public void setOrganisation(String organisation) {
    String oldValue = this.organisation;
    this.organisation = organisation;
    firePropertyChange(TreeNodeChangeEvent.organization, oldValue, organisation);
  }

  /**
   * Sets the organisation
   * <p>Equal to {@link #setOrganisation(String)}.
   *
   * @param organization
   */
  public void setOrganization(String organization) {
    setOrganisation(organization);
  }

  /**
   *
   * @param attributeName
   * @param attributeValue
   * @deprecated those other attributes are not saved when writing the model
   */
  @Deprecated
  public void setOtherAttribute(String attributeName, String attributeValue) {
    if (attributeName == null) {
      return;
    }
    getOtherAttributes().put(attributeName, attributeValue);
  }

  /**
   *
   * @return
   * @deprecated those other attributes are not saved when writing the model
   */
  @Deprecated
  public Map<String, String> getOtherAttributes() {

    if (!isSetOtherAttributes()) {
      otherAttributes = new LinkedHashMap<String, String>();
    }
    return otherAttributes;
  }

  /**
   *
   * @return
   * @deprecated those other attributes are not saved when writing the model
   */
  @Deprecated
  public boolean isSetOtherAttributes() {
    return otherAttributes != null;
  }

  /**
   *
   * @param attributeName
   * @return
   * @deprecated those other attributes are not saved when writing the model
   */
  @Deprecated
  public String getOtherAttribute(String attributeName) {
    if (attributeName == null) {
      return null;
    }
    return getOtherAttributes().get(attributeName);
  }

  /**
   * Returns the information about the {@link Creator} as a {@link String}.
   *
   * @return the information about the {@link Creator} as a {@link String}.
   */
  public String printCreator() {
    StringBuilder sb = new StringBuilder();
    if (isSetGivenName()) {
      sb.append(getGivenName());
      if (isSetFamilyName() || isSetEmail() || isSetOrganisation()) {
        sb.append(' ');
      }
    }
    if (isSetFamilyName()) {
      sb.append(getFamilyName());
      if (isSetEmail() || isSetOrganisation()) {
        sb.append(", ");
      }
    }
    if (isSetEmail()) {
      sb.append(getEmail());
      if (isSetOrganisation()) {
        sb.append(", ");
      }
    }
    if (isSetOrganisation()) {
      sb.append(getOrganisation());
    }

    return sb.toString();
  }

  /**
   * Unsets the email of this {@link Creator}.
   *
   * @return {@link JSBML#OPERATION_SUCCESS}
   */
  public int unsetEmail() {
    String oldValue = email;
    email = null;
    firePropertyChange(TreeNodeChangeEvent.email, oldValue, email);
    return JSBML.OPERATION_SUCCESS;
  }

  /**
   * Unsets the familyName of this {@link Creator}.
   *
   * @return {@link JSBML#OPERATION_SUCCESS}
   */
  public int unsetFamilyName() {
    String oldValue = familyName;
    familyName = null;
    firePropertyChange(TreeNodeChangeEvent.familyName, oldValue, familyName);
    return JSBML.OPERATION_SUCCESS;
  }

  /**
   * Unsets the givenName of this {@link Creator}.
   *
   * @return {@link JSBML#OPERATION_SUCCESS}
   */
  public int unsetGivenName() {
    String oldValue = givenName;
    givenName = null;
    firePropertyChange(TreeNodeChangeEvent.givenName, oldValue, givenName);
    return JSBML.OPERATION_SUCCESS;
  }

  /**
   * Unsets the organisation of this {@link Creator}.
   *
   */
  public void unsetOrganization() {
    String oldValue = organisation;
    organisation = null;
    firePropertyChange(TreeNodeChangeEvent.organization, oldValue, organisation);
  }

}
