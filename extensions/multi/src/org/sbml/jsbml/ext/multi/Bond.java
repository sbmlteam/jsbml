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
package org.sbml.jsbml.ext.multi;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBMLException;

/**
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class Bond extends AbstractSBase {

  // TODO: store the BindingSiteReference in a list and test that there are no more than two of them.

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8781651881389720582L;

  public enum BOND_OCCURRENCE_TYPE {prohibited, allowed, required};

  private BOND_OCCURRENCE_TYPE occurence;

  private BindingSiteReference bindingSiteReference1;

  private BindingSiteReference bindingSiteReference2;

  public Bond() {
    super();
    initDefaults();
  }

  @Override
  public AbstractSBase clone() {
    // TODO
    return null;
  }


  /**
   * Returns the occurrence.
   * 
   * @return the occurrence
   */
  public BOND_OCCURRENCE_TYPE getOccurence() {
    return occurence;
  }


  /**
   * Sets the occurrence.
   * 
   * @param occurence the occurrence to set
   */
  public void setOccurence(BOND_OCCURRENCE_TYPE occurence) {
    this.occurence = occurence;
  }

  /**
   * Returns {@code true} if the occurrence is set.
   * 
   * @return {@code true} if the occurrence is set.
   */
  public boolean isSetOccurence() {
    return occurence != null;
  }

  /**
   * Returns the first {@link BindingSiteReference}
   * 
   * @return the first {@link BindingSiteReference}
   */
  public BindingSiteReference getBindingSiteReference1() {
    return bindingSiteReference1;
  }

  /**
   * Sets the first {@link BindingSiteReference}.
   * 
   * @param bindingSite1 the {@link BindingSiteReference} to set
   */
  public void setBindingSiteReference1(BindingSiteReference bindingSite1) {
    bindingSiteReference1 = bindingSite1;
  }

  /**
   * Returns {@code true} if the first {@link BindingSiteReference} is set.
   * 
   * @return {@code true} if the first {@link BindingSiteReference} is set.
   */
  public boolean isSetBindingSiteReference1() {
    return bindingSiteReference1 != null;
  }

  /**
   * Returns the second {@link BindingSiteReference}.
   * 
   * @return the second {@link BindingSiteReference}.
   */
  public BindingSiteReference getBindingSiteReference2() {
    return bindingSiteReference2;
  }


  /**
   * Sets the second {@link BindingSiteReference}.
   * 
   * @param bindingSiteReference2 the {@link BindingSiteReference} to set
   */
  public void setBindingSiteReference2(BindingSiteReference bindingSiteReference2) {
    this.bindingSiteReference2 = bindingSiteReference2;
  }

  /**
   * Returns {@code true} if the second {@link BindingSiteReference} is set.
   * 
   * @return {@code true} if the second {@link BindingSiteReference} is set.
   */
  public boolean isSetBindingSiteReference2() {
    return bindingSiteReference2 != null;
  }

  /**
   * Adds a {@link BindingSiteReference} to this {@link Bond}. If there are
   * already two {@link BindingSiteReference}s, the new {@link BindingSiteReference} is
   * not added and false is returned.
   * 
   * @param bindingSiteReference
   * @return {@code true} is the {@link BindingSiteReference} was added successfully to the {@link Bond}.
   */
  public boolean addBindingSiteReference(BindingSiteReference bindingSiteReference) {

    if (!isSetBindingSiteReference1())
    {
      setBindingSiteReference1(bindingSiteReference);
    }
    else if (!isSetBindingSiteReference2())
    {
      setBindingSiteReference2(bindingSiteReference);
    } else {
      // There is already two bindingsiteReferences, we do nothing
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    // TODO
    return null;
  }


  /**
   * 
   */
  public void initDefaults() {
    setNamespace(MultiConstants.namespaceURI);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }

    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetBindingSiteReference1()) {
      if (pos == index) {
        return getBindingSiteReference1();
      }
      pos++;
    }
    if (isSetBindingSiteReference2()) {
      if (pos == index) {
        return getBindingSiteReference2();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
      index, +Math.min(pos, 0)));
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetBindingSiteReference1()) {
      count++;
    }
    if (isSetBindingSiteReference2()) {
      count++;
    }

    return count;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {

      if (attributeName.equals(MultiConstants.occurrence)) {
        try {
          setOccurence(BOND_OCCURRENCE_TYPE.valueOf(value));
          isAttributeRead = true;
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + MultiConstants.occurrence
            + " on the 'bond' element.");
        }
      }
    }

    return isAttributeRead;

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetOccurence()) {
      attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.occurrence, getOccurence().toString());
    }

    return attributes;
  }

}
