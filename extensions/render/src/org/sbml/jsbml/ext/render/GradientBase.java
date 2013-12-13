/* 
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueNamedSBase;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class GradientBase extends AbstractNamedSBase implements UniqueNamedSBase {  

  /**
   * 
   */
  private static final long serialVersionUID = -3921074024567604440L;

  /**
   * 
   * @author Eugen Netz
   * @author Alexander Diamantikos
   * @author Jakob Matthes
   * @author Jan Rudolph
   * @version $Rev$
   * @since 1.0
   * @date 08.05.2012
   */
  protected enum Spread {
		PAD,
		REFLECT,
		REPEAT,
	}
	
	protected Spread spreadMethod;
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
   */
  public GradientBase(String id, GradientStop stop) {
    initDefaults();
    this.listOfGradientStops.add(stop);
  }

  /**
   * Creates a GradientBase instance with an id, name, level, and version. 
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public GradientBase(String id, GradientStop stop, int level, int version) {
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
    this.listOfGradientStops.add(stop);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    if (isSetListOfGradientStops()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    if (isSetListOfGradientStops()) {
      if (pos == childIndex) {
        return getListOfGradientStops();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", childIndex,
      +((int) Math.min(pos, 0))));
  }

  /**
   * Clone constructor
   */
  public GradientBase(GradientBase obj) {
    super(obj);
    this.spreadMethod = obj.spreadMethod;
    this.listOfGradientStops = obj.listOfGradientStops;
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
    setNamespace(RenderConstants.namespaceURI);
    this.spreadMethod = Spread.PAD;
    this.listOfGradientStops = new ListOf<GradientStop>();
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
    return this.spreadMethod != null;
  }

  /**
   * Set the value of spreadMethod
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
      Spread oldSpreadMethod = this.spreadMethod;
      this.spreadMethod = null;
      firePropertyChange(RenderConstants.spreadMethod, oldSpreadMethod, this.spreadMethod);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
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
      listOfGradientStops.setNamespace(RenderConstants.namespaceURI);
      listOfGradientStops.setSBaseListType(ListOf.Type.other);
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
    registerChild(this.listOfGradientStops);
  }

  /**
   * @return {@code true}, if listOfGradientStops contained at least one element, 
   *         otherwise {@code false}
   */
  public boolean unsetListOfGradientStops() {
    if (isSetListOfGradientStops()) {
      ListOf<GradientStop> oldGradientStops = this.listOfGradientStops;
      this.listOfGradientStops = null;
      oldGradientStops.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param field
   */
  public boolean addGradientStop(GradientStop field) {
    return getListOfGradientStops().add(field);
  }

  /**
   * @param field
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
   */
  public GradientStop createGradientStop(double offset, String stopColor) {
    GradientStop field = new GradientStop(offset, stopColor, getLevel(), getVersion());
    addGradientStop(field);
    return field;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return true;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpreadMethod()) {
      attributes.remove(RenderConstants.spreadMethod);
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
        setSpreadMethod(Spread.valueOf(value.toUpperCase()));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
