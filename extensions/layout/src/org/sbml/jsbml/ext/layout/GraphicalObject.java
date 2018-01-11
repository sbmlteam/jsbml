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
package org.sbml.jsbml.ext.layout;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.ResourceManager;

/**
 * All the more specific layout elements ({@link CompartmentGlyph}, {@link GeneralGlyph},
 * {@link SpeciesGlyph}, {@link ReactionGlyph}, {@link ReferenceGlyph}, {@link TextGlyph},
 * and {@link SpeciesReferenceGlyph}) are derived from the class {@link GraphicalObject}.
 * Each object of class {@link GraphicalObject} has a mandatory {@link BoundingBox}, which
 * specifies the position and the size of the object. While {@link GraphicalObject} is the
 * base class for most elements in the {@link Layout} package, it is not an abstract class.
 * It can be instantiated when used in the listOfAdditionalGraphicalObjects to describe
 * additional elements and relationships. Since it only describes a {@link BoundingBox},
 * programs are encouraged to add {@link org.sbml.jsbml.Annotation} objects that describe program-specific
 * graphical information.
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 */
public class GraphicalObject extends AbstractNamedSBase implements UniqueNamedSBase, IBoundingBox {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7587814013754302901L;

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(GraphicalObject.class);

  /**
   * 
   */
  private BoundingBox boundingBox;

  /**
   * 
   */
  private String metaidRef;

  /**
   * 
   */
  public GraphicalObject() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param graphicalObject
   */
  public GraphicalObject(GraphicalObject graphicalObject) {
    super(graphicalObject);

    if (graphicalObject.isSetBoundingBox()) {
      setBoundingBox(graphicalObject.getBoundingBox().clone());
    }
    if (graphicalObject.isSetMetaidRef()) {
      setMetaidRef(graphicalObject.getMetaidRef());
    }
  }

  /**
   * 
   * @param level
   * @param version
   */
  public GraphicalObject(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * @param id
   */
  public GraphicalObject(String id) {
    super(id);
    initDefaults();
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public GraphicalObject(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public GraphicalObject clone() {
    return new GraphicalObject(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;
  }

  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.IBoundingBox#createBoundingBox()
   */
  @Override
  public BoundingBox createBoundingBox() {
    BoundingBox bb = new BoundingBox();
    setBoundingBox(bb);
    return bb;
  }

  /**
   * 
   * @param dimensions
   * @return
   */
  public BoundingBox createBoundingBox(Dimensions dimensions) {
    BoundingBox bb = createBoundingBox();
    bb.setDimensions(dimensions);
    return bb;
  }

  /**
   * Creates and sets a {@link BoundingBox} for this object, with the
   * given parameters for {@link Dimensions}.
   * @param width
   * @param height
   * @param depth
   * @return {@link BoundingBox}.
   */
  public BoundingBox createBoundingBox(double width, double height, double depth) {
    BoundingBox bb = createBoundingBox();
    bb.createDimensions(width, height, depth);
    return bb;
  }

  /**
   * Creates and sets a {@link BoundingBox} for this object, with the
   * given parameters for {@link Dimensions} and {@link Point}.
   * @param width
   * @param height
   * @param depth
   * @param x
   * @param y
   * @param z
   * @return {@link BoundingBox}.
   */
  public BoundingBox createBoundingBox(double width, double height, double depth,
    double x, double y, double z) {
    BoundingBox bb = createBoundingBox(width, height, depth);
    bb.createPosition(x, y, z);
    return bb;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      GraphicalObject graphicalObject = (GraphicalObject) object;
      equals &= graphicalObject.isSetBoundingBox() == isSetBoundingBox();
      if (equals && isSetBoundingBox()) {
        equals &= graphicalObject.getBoundingBox().equals(getBoundingBox());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.IBoundingBox#getBoundingBox()
   */
  @Override
  public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetBoundingBox()) {
      if (pos == index) {
        return getBoundingBox();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetBoundingBox()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 941;
    int hashCode = super.hashCode();
    if (isSetBoundingBox()) {
      hashCode += prime * getBoundingBox().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    // See Layout Extension (April 25, 2005) page 9, SId use="required"
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.IBoundingBox#isSetBoundingBox()
   */
  @Override
  public boolean isSetBoundingBox() {
    return boundingBox != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.IBoundingBox#setBoundingBox(org.sbml.jsbml.ext.layout.BoundingBox)
   */
  @Override
  public void setBoundingBox(BoundingBox boundingBox) {
    unsetBoundingBox();
    this.boundingBox = boundingBox;
    registerChild(this.boundingBox);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.layout.IBoundingBox#unsetBoundingBox()
   */
  @Override
  public void unsetBoundingBox() {
    if (isSetBoundingBox()) {
      BoundingBox oldValue = boundingBox;
      boundingBox = null;
      oldValue.fireNodeRemovedEvent();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(LayoutConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      logger.warn(MessageFormat.format(
        ResourceManager.getBundle("org.sbml.jsbml.resources.cfg.Messages").getString("UNDEFINED_ATTRIBUTE"),
        "name", getLevel(), getVersion(), getElementName()));
      // TODO: This must be generally solved. Here we have an SBase with ID but without name!
      // TODO: This does not have to be done at this level, however... also, why is this AbstractNamedSbase?
    }
    if (isSetMetaidRef())
    {
      attributes.put(LayoutConstants.shortLabel + ':' + LayoutConstants.metaidRef, getMetaidRef());
    }

    return attributes;
  }

  /**
   * Returns the value of metaidRef
   *
   * @return the value of metaidRef
   */
  public String getMetaidRef()
  {
    if (isSetMetaidRef()) {
      return metaidRef;
    }

    return "";
  }

  /**
   * Returns whether metaidRef is set
   *
   * @return whether metaidRef is set
   */
  public boolean isSetMetaidRef() {
    return metaidRef != null;
  }

  /**
   * Sets the value of metaidRef which uniquely references elements in the model.
   * 
   * @param metaidRef
   */
  public void setMetaidRef(String metaidRef) {
    String oldMetaidRef = this.metaidRef;
    this.metaidRef = metaidRef;
    firePropertyChange(LayoutConstants.metaidRef, oldMetaidRef, this.metaidRef);
  }

  /**
   * Unsets the variable metaidRef
   *
   * @return {@code true}, if metaidRef was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMetaidRef() {
    if (isSetMetaidRef()) {
      String oldMetaidRef = metaidRef;
      metaidRef = null;
      firePropertyChange(LayoutConstants.metaidRef, oldMetaidRef, metaidRef);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(LayoutConstants.metaidRef))
      {
        setMetaidRef(value);
      }
      else
      {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

}
