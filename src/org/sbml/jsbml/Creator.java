/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Contains all the information about a creator of a {@link Model} (or other {@link SBase} in level
 * 3).
 * 
 * @author marine3
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
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
	public static final transient String URI_RDF_VCARD_NS = "http://www.w3.org/2001/vcard-rdf/3.0#";
	
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
	 * TODO : use an XMLNode to hold all the additional informations
	 * 
	 */
	private Map<String, String> otherAttributes;

	/**
	 * Creates a {@link Creator} instance. By default, the email, familyName,
	 * givenName, organisation are null.
	 */
	public Creator() {
		super();
		this.givenName = null;
		this.familyName = null;
		this.organisation = null;
		this.email = null;
		this.otherAttributes = null;
	}

	/**
	 * Creates a {@link Creator} instance from a given {@link Creator}.
	 * 
	 * @param creator
	 */
	public Creator(Creator creator) {
		super(creator);
		this.email = creator.isSetEmail() ? new String(creator.getEmail()) : null;
		this.familyName = creator.isSetFamilyName() ? new String(creator.getFamilyName()) : null;
		this.givenName = creator.isSetGivenName() ? new String(creator.getGivenName()) : null;
		this.organisation = creator.isSetOrganisation() ? new String(creator.getOrganisation()) : null;

		if (creator.isSetOtherAttributes()) {
			this.otherAttributes = new LinkedHashMap<String,String>();

			for (String key : creator.getOtherAttributes().keySet()) {
				this.otherAttributes.put(new String(key), new String(creator.getOtherAttribute(key)));
			}
		} else {
			this.otherAttributes = null;
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
  public boolean getAllowsChildren() {
	  return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  public TreeNode getChildAt(int childIndex) {
	  throw new IndexOutOfBoundsException(Integer.toString(childIndex));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
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
   * Returns true or false depending on whether this
   * {@link Creator}'s email has been set.
   * 
   * @return true if the email of this {@link Creator} is not null.
   */
  public boolean isSetEmail() {
	  return email != null;
  }

  /**
   * Returns true or false depending on whether this
   * {@link Creator}'s familyName has been set.
   * 
   * @return true if the familyName of this {@link Creator} is not null.
   */
  public boolean isSetFamilyName() {
	  return familyName != null;
  }

  /**
   * Returns true or false depending on whether this
   * {@link Creator}'s givenName has been set.
   * 
   * @return true if the givenName of this {@link Creator} is not null.
   */
  public boolean isSetGivenName() {
	  return givenName != null;
  }

  /**
   * Returns true or false depending on whether this
   * {@link Creator}'s organisation has been set.
   * 
   * @return true if the organisation of this {@link Creator} is not null.
   */
  public boolean isSetOrganisation() {
	  return organisation != null;
  }

  /**
   * Returns true or false depending on whether this
   * {@link Creator}'s organisation has been set.
   * <p>Equal to {@link #isSetOrganisation()}
   * 
   * @return true or false depending on whether this
   * {@link Creator}'s organisation has been set.
   */
  public boolean isSetOrganization() {
	  return isSetOrganisation();
  }

  /**
   * Returns true if the XML attribute is known by this {@link Creator}.
   * 
   * @return true if the XML attribute is known by this {@link Creator}.
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
	  /*
	   * ^                     # start of the line
	   *   [_A-Za-z0-9-]+      # must start with string in the bracket [ ], must contains one or more (+)
	   *   (                   # start of group #1
	   *     \\.[_A-Za-z0-9-]+ # follow by a dot "." and string in the bracket [ ], must contains one or more (+)
	   *   )*                  # end of group #1, this group is optional (*)
	   *   @                   # must contains a "@" symbol
	   *   [A-Za-z0-9-]+       # follow by string in the bracket [ ], must contains one or more (+)
	   *   (                   # start of group #2 - first level TLD checking
	   *     \\.[A-Za-z0-9-]+  # follow by a dot "." and string in the bracket [ ], must contains one or more (+)
	   *   )*                  # end of group #2, this group is optional (*)
	   *   (                   # start of group #3 - second level TLD checking
	   *     \\.[A-Za-z]{2,}   # follow by a dot "." and string in the bracket [ ], with minimum length of 2
	   *   )                   # end of group #3
	   *$                      # end of the line
	   */
    final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    if ((email != null) && !Pattern.matches(emailPattern, email)) {
    	logger.warn(String.format("Invalid e-mail address %s", email));
    	throw new IllegalArgumentException(String.format("Invalid e-mail address %s", email));
    }
		String oldValue = this.email;
		this.email = email;
		this.firePropertyChange(TreeNodeChangeEvent.email, oldValue, email);
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
		this.firePropertyChange(TreeNodeChangeEvent.familyName, oldValue, familyName);
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
		this.firePropertyChange(TreeNodeChangeEvent.givenName, oldValue, givenName);
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
		this.firePropertyChange(TreeNodeChangeEvent.organisation, oldValue, organisation);
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
	 */
	public void setOtherAttribute(String attributeName, String attributeValue) {
		if (attributeName == null) {
			return;
		}
		getOtherAttributes().put(attributeName, attributeValue);
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getOtherAttributes() {

		if (!isSetOtherAttributes()) {
			otherAttributes = new LinkedHashMap<String, String>();
		}
		return otherAttributes;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetOtherAttributes() {
		return otherAttributes != null;
	}
	
	/**
	 * 
	 * @param attributeName
	 * @return
	 */
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
	@Override
	public String toString() {
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
		String oldValue = this.email;
		email = null;
		this.firePropertyChange(TreeNodeChangeEvent.email, oldValue, email);
		return JSBML.OPERATION_SUCCESS;
	}

	/**
	 * Unsets the familyName of this {@link Creator}.
	 * 
	 * @return {@link JSBML#OPERATION_SUCCESS}
	 */
	public int unsetFamilyName() {
		String oldValue = this.familyName;
		this.familyName = null;
		this.firePropertyChange(TreeNodeChangeEvent.familyName, oldValue, familyName);
		return JSBML.OPERATION_SUCCESS;
	}

	/**
	 * Unsets the givenName of this {@link Creator}.
	 * 
	 * @return {@link JSBML#OPERATION_SUCCESS}
	 */
	public int unsetGivenName() {
		String oldValue = this.givenName;
		this.givenName = null;
		this.firePropertyChange(TreeNodeChangeEvent.givenName, oldValue, givenName);
		return JSBML.OPERATION_SUCCESS;
	}

	/**
	 * Unsets the organisation of this {@link Creator}.
	 * 
	 */
	public void unsetOrganization() {
		String oldValue = this.organisation;
		this.organisation = null;
		this.firePropertyChange(TreeNodeChangeEvent.organisation, oldValue, organisation);
	}

}
