/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * Implements the curve concept from the SBML render extension.
 * <p>
 * The curve concept in the SBML render extension is similar to the curves in
 * the SBML layout. Each curve consists of a number of either straight line
 * segments or cubic B&eacute;zier elements. The two element types can also by
 * mixed in a single curve object.
 * <p>
 * In contrast to layout curves, render curves can not have gaps and the
 * individual coordinates of the curve elements can be specified as a
 * combination of absolute and relative values.
 * <p>
 * Another difference to layout curves is the fact that render curves can
 * specify decorations to be applied to the start and/or the end of the curve
 * (@see LineEnding).
 * <p>
 * Since {@link RenderCurve} is derived from {@link GraphicalPrimitive1D}, it
 * inherits all its attributes and methods.
 *
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class RenderCurve extends GraphicalPrimitive1D {
  /**
   * 
   */
  private static final long serialVersionUID = -1941713884972334826L;
  /**
   * 
   */
  protected String startHead;
  /**
   * 
   */
  protected String endHead;

  /**
   * 
   */
  protected ListOf<RenderPoint> listOfElements; // TODO - check the naming of this listOf and the associated methods compared with libsbml. Might need to extends ListOfWithName

  // TODO - implements the TreeNode methods

  /**
   * Creates an Curve instance
   */
  public RenderCurve() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public RenderCurve(RenderCurve obj) {
    super();
    startHead = obj.startHead;
    endHead = obj.endHead;

    if (obj.isSetListOfElements()) {
      setListOfElements(obj.getListOfElements().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#clone()
   */
  @Override
  public RenderCurve clone() {
    return new RenderCurve(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#initDefaults()
   */
  @Override
  public void initDefaults() {
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3119;
    int result = super.hashCode();
    result = prime * result + ((endHead == null) ? 0 : endHead.hashCode());
    result = prime * result
        + ((listOfElements == null) ? 0 : listOfElements.hashCode());
    result = prime * result + ((startHead == null) ? 0 : startHead.hashCode());
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
    RenderCurve other = (RenderCurve) obj;
    if (endHead == null) {
      if (other.endHead != null) {
        return false;
      }
    } else if (!endHead.equals(other.endHead)) {
      return false;
    }
    if (listOfElements == null) {
      if (other.listOfElements != null) {
        return false;
      }
    } else if (!listOfElements.equals(other.listOfElements)) {
      return false;
    }
    if (startHead == null) {
      if (other.startHead != null) {
        return false;
      }
    } else if (!startHead.equals(other.startHead)) {
      return false;
    }
    return true;
  }

  /**
   * @return the value of startHead
   */
  public String getStartHead() {
    if (isSetStartHead()) {
      return startHead;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.startHead, this);
  }

  /**
   * @return whether startHead is set
   */
  public boolean isSetStartHead() {
    return startHead != null;
  }

  /**
   * Set the value of startHead
   * @param startHead
   */
  public void setStartHead(String startHead) {
    String oldStartHead = this.startHead;
    this.startHead = startHead;
    firePropertyChange(RenderConstants.startHead, oldStartHead, this.startHead);
  }

  /**
   * Unsets the variable startHead
   * @return {@code true}, if startHead was set before,
   *         otherwise {@code false}
   */
  public boolean unsetStartHead() {
    if (isSetStartHead()) {
      String oldStartHead = startHead;
      startHead = null;
      firePropertyChange(RenderConstants.startHead, oldStartHead, startHead);
      return true;
    }
    return false;
  }


  /**
   * @return the value of endHead
   */
  public String getEndHead() {
    if (isSetEndHead()) {
      return endHead;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.endHead, this);
  }

  /**
   * @return whether endHead is set
   */
  public boolean isSetEndHead() {
    return endHead != null;
  }

  /**
   * Set the value of endHead
   * @param endHead
   */
  public void setEndHead(String endHead) {
    String oldEndHead = this.endHead;
    this.endHead = endHead;
    firePropertyChange(RenderConstants.endHead, oldEndHead, this.endHead);
  }

  /**
   * Unsets the variable endHead
   * @return {@code true}, if endHead was set before,
   *         otherwise {@code false}
   */
  public boolean unsetEndHead() {
    if (isSetEndHead()) {
      String oldEndHead = endHead;
      endHead = null;
      firePropertyChange(RenderConstants.endHead, oldEndHead, endHead);
      return true;
    }
    return false;
  }


  /**
   * @return {@code true}, if listOfElements contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfElements() {
    if ((listOfElements == null) || listOfElements.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * @return the listOfElements
   */
  public ListOf<RenderPoint> getListOfElements() {
    if (!isSetListOfElements()) {
      listOfElements = new ListOf<RenderPoint>(getLevel(), getVersion());
      listOfElements.setNamespace(RenderConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfElements.setPackageName(null);
      listOfElements.setPackageName(RenderConstants.shortLabel);
      listOfElements.setSBaseListType(ListOf.Type.other);
      registerChild(listOfElements);
    }
    return listOfElements;
  }

  /**
   * @param listOfElements
   */
  public void setListOfElements(ListOf<RenderPoint> listOfElements) {
    unsetListOfElements();
    this.listOfElements = listOfElements;
    
    if (listOfElements != null) {
      listOfElements.unsetNamespace();
      listOfElements.setNamespace(RenderConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfElements.setPackageName(null);
      listOfElements.setPackageName(RenderConstants.shortLabel);
      listOfElements.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfElements);
    }
  }

  /**
   * @return {@code true}, if listOfElements contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfElements() {
    if (isSetListOfElements()) {
      ListOf<RenderPoint> oldElements = listOfElements;
      listOfElements = null;
      oldElements.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param element
   * @return
   */
  public boolean addElement(RenderPoint element) {
    return getListOfElements().add(element);
  }

  /**
   * @param element
   * @return
   */
  public boolean removeElement(RenderPoint element) {
    if (isSetListOfElements()) {
      return getListOfElements().remove(element);
    }
    return false;
  }

  /**
   * @param i
   */
  public void removeElement(int i) {
    if (!isSetListOfElements()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfElements().remove(i);
  }

  /**
   * create a new Element element and adds it to the ListOfElements list
   * <p><b>NOTE:</b>
   * only use this method, if ID is not mandatory in Element
   * otherwise use @see createElement(String id)!</p>
   * @return
   */
  public RenderPoint createElement() {
    return createElement(null);
  }

  /**
   * create a new Element element and adds it to the ListOfElements list
   * @param id
   * @return
   */
  public RenderPoint createElement(String id) {
    RenderPoint element = new RenderPoint();
    addElement(element);
    return element;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetStartHead()) {
      attributes.remove(RenderConstants.startHead);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.startHead,
        getStartHead());
    }
    if (isSetEndHead()) {
      attributes.remove(RenderConstants.endHead);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.endHead,
        getEndHead());
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
      if (attributeName.equals(RenderConstants.startHead)) {
        setStartHead(value);
      }
      else if (attributeName.equals(RenderConstants.endHead)) {
        setEndHead(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
