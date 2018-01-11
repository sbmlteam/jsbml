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
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class GradientBase extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -3921074024567604440L;

  /**
   * The type GradientSpreadMethod is being used by GradientBase elements to decide how gradients propagate over
   * the whole element they are applied to.
   *
   * @author Eugen Netz
   * @author Alexander Diamantikos
   * @author Jakob Matthes
   * @author Jan Rudolph
   * @since 1.0
   */
  public enum Spread {
    /**
     * the gradient color at the endpoint of the vector defines how the gradient is continued beyond that point
     */
    PAD,
    /**
     * the gradient continues from end to start and then from start to end again and again.
     */
    REFLECT,
    /**
     * the gradient pattern is repeated from start to end over and over again.
     */
    REPEAT;
  }

  /**
   * 
   */
  protected Spread spreadMethod;
  /**
   * 
   */
  protected ListOf<GradientStop> listOfGradientStops;


  /**
   * Creates an GradientBase instance
   */
  public GradientBase() {
    super();
    initDefaults();
  }

  /**
   * Creates a GradientBase instance with an id.
   * 
   * @param id
   * @param stop
   */
  public GradientBase(String id, GradientStop stop) {
    super(id);
    initDefaults();
    listOfGradientStops.add(stop);
  }

  /**
   * Creates a GradientBase instance with an id, name, level, and version.
   * 
   * @param id
   * @param stop
   * @param level
   * @param version
   */
  public GradientBase(String id, GradientStop stop, int level, int version) {
    super(id, null, level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
    listOfGradientStops.add(stop);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    
    if (isSetListOfGradientStops()) {
      count += getListOfGradientStops().size();
    }
    return count;
  }

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

     if (isSetListOfGradientStops()) {
       return getListOfGradientStops().get(index);
     }

    throw new IndexOutOfBoundsException(
        MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",

            index, Math.min(pos, 0)));
  }


  /**
   * Clone constructor
   * @param obj
   */
  public GradientBase(GradientBase obj) {
    super(obj);
    spreadMethod = obj.spreadMethod;

    if (obj.isSetListOfGradientStops()) {
      setListOfGradientStops(obj.listOfGradientStops.clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public GradientBase clone() {
    return new GradientBase(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
    
    spreadMethod = Spread.PAD;
    listOfGradientStops = new ListOf<GradientStop>();
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3187;
    int result = super.hashCode();
    result = prime * result
        + ((listOfGradientStops == null) ? 0 : listOfGradientStops.hashCode());
    result = prime * result
        + ((spreadMethod == null) ? 0 : spreadMethod.hashCode());
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
    GradientBase other = (GradientBase) obj;
    if (listOfGradientStops == null) {
      if (other.listOfGradientStops != null) {
        return false;
      }
    } else if (!listOfGradientStops.equals(other.listOfGradientStops)) {
      return false;
    }
    if (spreadMethod != other.spreadMethod) {
      return false;
    }
    return true;
  }

  /**
   * @return the value of spreadMethod
   */
  public Spread getSpreadMethod() {
    if (isSetSpreadMethod()) {
      return spreadMethod;
    } else {
      return null;
    }
  }

  /**
   * @return whether spreadMethod is set
   */
  public boolean isSetSpreadMethod() {
    return spreadMethod != null;
  }

  /**
   * Set the value of spreadMethod
   * @param spreadMethod
   */
  public void setSpreadMethod(Spread spreadMethod) {
    Spread oldSpreadMethod = this.spreadMethod;
    this.spreadMethod = spreadMethod;
    firePropertyChange(RenderConstants.spreadMethod, oldSpreadMethod, this.spreadMethod);
  }

  /**
   * Unsets the variable spreadMethod
   * @return {@code true}, if spreadMethod was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSpreadMethod() {
    if (isSetSpreadMethod()) {
      Spread oldSpreadMethod = spreadMethod;
      spreadMethod = null;
      firePropertyChange(RenderConstants.spreadMethod, oldSpreadMethod, spreadMethod);
      return true;
    }
    return false;
  }

  /**
   * @return {@code true}, if listOfGradientStops contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfGradientStops() {
    if ((listOfGradientStops == null) || listOfGradientStops.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * @return the listOfGradientStops
   */
  public ListOf<GradientStop> getListOfGradientStops() {
    if (!isSetListOfGradientStops()) {
      listOfGradientStops = new ListOf<GradientStop>(getLevel(), getVersion());
      listOfGradientStops.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfGradientStops.setPackageName(null);
      listOfGradientStops.setPackageName(RenderConstants.shortLabel);
      listOfGradientStops.setSBaseListType(ListOf.Type.other);
      listOfGradientStops.setOtherListName(RenderConstants.listOfGradientStops);
      
      registerChild(listOfGradientStops);
    }
    return listOfGradientStops;
  }

  /**
   * @param listOfGradientStops
   */
  public void setListOfGradientStops(ListOf<GradientStop> listOfGradientStops) {
    unsetListOfGradientStops();
    this.listOfGradientStops = listOfGradientStops;

    if (listOfGradientStops != null) {
      listOfGradientStops.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfGradientStops.setPackageName(null);
      listOfGradientStops.setPackageName(RenderConstants.shortLabel);
      listOfGradientStops.setSBaseListType(ListOf.Type.other);
      listOfGradientStops.setOtherListName(RenderConstants.listOfGradientStops);

      registerChild(this.listOfGradientStops);
    }
  }

  /**
   * @return {@code true}, if listOfGradientStops contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfGradientStops() {
    if (isSetListOfGradientStops()) {
      ListOf<GradientStop> oldGradientStops = listOfGradientStops;
      listOfGradientStops = null;
      oldGradientStops.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param field
   * @return
   */
  public boolean addGradientStop(GradientStop field) {
    return getListOfGradientStops().add(field);
  }

  /**
   * @param field
   * @return
   */
  public boolean removeGradientStop(GradientStop field) {
    if (isSetListOfGradientStops()) {
      return getListOfGradientStops().remove(field);
    }
    return false;
  }

  /**
   * @param i
   */
  public void removeGradientStop(int i) {
    if (!isSetListOfGradientStops()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfGradientStops().remove(i);
  }

  /**
   * create a new GradientStop element and adds it to the ListOfGradientStops list
   * @param offset
   * @param stopColor
   * @return
   */
  public GradientStop createGradientStop(double offset, String stopColor) {
    GradientStop field = new GradientStop(offset, stopColor, getLevel(), getVersion());
    addGradientStop(field);
    return field;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(RenderConstants.shortLabel + ":id", getId());
    }
    
    if (isSetSpreadMethod()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.spreadMethod,
        getSpreadMethod().toString().toLowerCase());
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;
    
      if (attributeName.equals(RenderConstants.spreadMethod)) {
        try {
          setSpreadMethod(Spread.valueOf(value.toUpperCase()));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
              + "' for the attribute " + RenderConstants.spreadMethod
              + " on the 'gradient' element.");
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
