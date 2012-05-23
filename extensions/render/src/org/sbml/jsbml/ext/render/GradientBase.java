/*
 * $Id$
 * $URL$
 *
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
package org.sbml.jsbml.ext.render;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class GradientBase extends AbstractSBase {  

  /**
   * 
   */
  private static final long serialVersionUID = -3921074024567604440L;


  protected enum Spread {
		pad,
		reflect,
		repeat,
	}
	
	protected String id;
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
    this.id = id;
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
    if (getLevelAndVersion().compareTo(Integer.valueOf(MIN_SBML_LEVEL),
      Integer.valueOf(MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
    this.listOfGradientStops.add(stop);
  }


  /**
   * Clone constructor
   */
  public GradientBase(GradientBase obj) {
    super(obj);
    this.id = obj.id;
    this.spreadMethod = obj.spreadMethod;
    this.listOfGradientStops = obj.listOfGradientStops;
  }


  /**
   * clones this class
   */
  public GradientBase clone() {
    return new GradientBase(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    //addNamespace(constant_class.namespaceURI);
    this.spreadMethod = Spread.pad;
    this.listOfGradientStops = new ListOf<GradientStop>();
  }
  
  
  /**
   * @return the value of id
   */
  public String getId(){
    return this.id;
  }
  
  /**
   * Set the value of id
   */
  public void setId(String id){
    this.id = id;
    //TODO firePropertyChange(constant_class.id, oldId, this.id);
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
    this.spreadMethod = spreadMethod;
    //TODO firePropertyChange(constant_class.spreadMethod, oldSpreadMethod, this.spreadMethod);
  }


  /**
   * Unsets the variable spreadMethod 
   * @return <code>true</code>, if spreadMethod was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetSpreadMethod() {
    if (isSetSpreadMethod()) {
      this.spreadMethod = null;
      //TODO firePropertyChange(constant_class.spreadMethod, oldSpreadMethod, this.spreadMethod);
      return true;
    }
    return false;
  }

  public static final int MIN_SBML_LEVEL = 3;
  public static final int MIN_SBML_VERSION = 1;
	
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
  /**
   * @return <code>true</code>, if listOfGradientStops contains at least one element, 
   *         otherwise <code>false</code>
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
      //TODO listOfGradientStops.addNamespace(constant_class.namespaceURI);
      listOfGradientStops.setSBaseListType(ListOf.Type.other);
      //TODO registerChild(listOfGradientStops);
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
   * @return <code>true</code>, if listOfGradientStops contained at least one element, 
   *         otherwise <code>false</code>
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
  public GradientStop createGradientStop(double offset, ColorDefinition stopColor) {
    GradientStop field = new GradientStop(offset, stopColor, getLevel(), getVersion());
    addGradientStop(field);
    return field;
  }
}
