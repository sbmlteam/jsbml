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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBaseWithUnit;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class CoordinateComponent extends AbstractSpatialNamedSBase implements
SBaseWithUnit {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CoordinateComponent.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3561130269969678307L;


  /**
   * 
   */
  private CoordinateKind type;

  /**
   * 
   */
  private Boundary boundaryMaximum;

  /**
   * 
   */
  private Boundary boundaryMinimum;

  /**
   * 
   */
  private String unit;

  /**
   * Creates a new {@link CoordinateComponent} instance.
   */
  public CoordinateComponent() {
    super();
  }

  /**
   * Creates a new {@link CoordinateComponent} instance copied from the given {@link CoordinateComponent}.
   * 
   * @param coordComp the {@link CoordinateComponent} to clone or copy
   */
  public CoordinateComponent(CoordinateComponent coordComp) {
    super(coordComp);
    if (coordComp.isSetType()) {
      type = coordComp.getType();
    }
    if (coordComp.isSetUnits()) {
      unit = new String(coordComp.getUnits());
    }
    if (coordComp.isSetBoundaryMinimum()) {
      setBoundaryMinimum(coordComp.getBoundaryMinimum().clone());
    }
    if (coordComp.isSetBoundaryMaximum()) {
      setBoundaryMaximum(coordComp.getBoundaryMaximum().clone());
    }
  }

  /**
   * Creates a new {@link CoordinateComponent} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public CoordinateComponent(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new {@link CoordinateComponent} instance.
   * 
   * @param id the element id
   * @param level the SBML level
   * @param version the SBML version
   */
  public CoordinateComponent(String id,int level, int version) {
    super(id,level,version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public CoordinateComponent clone() {
    return new CoordinateComponent(this);
  }

  /**
   * Returns the value of boundaryMinimum
   *
   * @return the value of boundaryMinimum
   */
  public Boundary getBoundaryMinimum() {
    if (isSetBoundaryMinimum()) {
      return boundaryMinimum;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.boundaryMinimum, this);
  }


  /**
   * Returns whether boundaryMinimum is set
   *
   * @return whether boundaryMinimum is set
   */
  public boolean isSetBoundaryMinimum() {
    return boundaryMinimum != null;
  }


  /**
   * Sets the value of boundaryMinimum
   * 
   * @param boundaryMinimum the minimum boundary
   */
  public void setBoundaryMinimum(Boundary boundaryMinimum) {
    Boundary oldMinimum = this.boundaryMinimum;
    this.boundaryMinimum = boundaryMinimum;
    this.boundaryMinimum.setElementName(SpatialConstants.boundaryMinimum);
    registerChild(boundaryMinimum);
    firePropertyChange(SpatialConstants.boundaryMinimum, oldMinimum, this.boundaryMinimum);
  }


  /**
   * Unsets the variable boundaryMinimum
   *
   * @return {@code true}, if boundaryMinimum was set before,
   *         otherwise {@code false}
   */
  public boolean unsetBoundaryMinimum() {
    if (isSetBoundaryMinimum()) {
      Boundary oldMinimum = boundaryMinimum;
      boundaryMinimum = null;
      firePropertyChange(SpatialConstants.boundaryMinimum, oldMinimum, boundaryMinimum);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of boundaryMaximum
   *
   * @return the value of boundaryMaximum
   */
  public Boundary getBoundaryMaximum() {
    if (isSetBoundaryMaximum()) {
      return boundaryMaximum;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.boundaryMaximum, this);
  }


  /**
   * Returns whether boundaryMaximum is set
   *
   * @return whether boundaryMaximum is set
   */
  public boolean isSetBoundaryMaximum() {
    return boundaryMaximum != null;
  }


  /**
   * Sets the value of boundaryMaximum
   * 
   * @param boundaryMaximum the maximum boundary
   */
  public void setBoundaryMaximum(Boundary boundaryMaximum) {
    Boundary oldMaximum = boundaryMaximum;
    this.boundaryMaximum = boundaryMaximum;
    this.boundaryMaximum.setElementName(SpatialConstants.boundaryMaximum);
    registerChild(boundaryMaximum);
    firePropertyChange(SpatialConstants.boundaryMaximum, oldMaximum, boundaryMaximum);
  }


  /**
   * Unsets the variable boundaryMaximum
   *
   * @return {@code true}, if boundaryMaximum was set before,
   *         otherwise {@code false}
   */
  public boolean unsetBoundaryMaximum() {
    if (isSetBoundaryMaximum()) {
      Boundary oldMaximum = boundaryMaximum;
      boundaryMaximum = null;
      firePropertyChange(SpatialConstants.boundaryMaximum, oldMaximum, boundaryMaximum);
      return true;
    }
    return false;
  }



  /**
   * Returns the value of type
   *
   * @return the value of type
   */
  public CoordinateKind getType() {
    if (isSetType()) {
      return type;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.type, this);
  }


  /**
   * Returns whether type is set
   *
   * @return whether type is set
   */
  public boolean isSetType() {
    return type != null;
  }


  /**
   * Sets the value of type
   * 
   * @param type the type
   */
  public void setType(CoordinateKind type) {
    CoordinateKind oldType = this.type;
    this.type = type;
    firePropertyChange(SpatialConstants.type, oldType, this.type);
  }


  /**
   * Unsets the variable type
   *
   * @return {@code true}, if type was set before,
   *         otherwise {@code false}
   */
  public boolean unsetType() {
    if (isSetType()) {
      CoordinateKind oldType = type;
      type = null;
      firePropertyChange(SpatialConstants.type, oldType, type);
      return true;
    }
    return false;
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    return !isSetUnits();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CoordinateComponent cc = (CoordinateComponent) object;
      equal &= cc.isSetType() == isSetType();
      if (equal && isSetType()) {
        equal &= cc.getType().equals(getType());
      }
      equal &= cc.isSetBoundaryMinimum() == isSetBoundaryMinimum();
      if (equal && isSetBoundaryMinimum()) {
        equal &= cc.getBoundaryMinimum().equals(getBoundaryMinimum());
      }
      equal &= cc.isSetBoundaryMaximum() == isSetBoundaryMaximum();
      if (equal && isSetBoundaryMaximum()) {
        equal &= cc.getBoundaryMaximum().equals(getBoundaryMaximum());
      }
      equal &= cc.isSetUnits() == isSetUnits();
      if (equal && isSetUnits()) {
        equal &= cc.getUnits().equals(getUnits());
      }
    }
    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }
    int pos = 0;
    if (isSetBoundaryMinimum()) {
      if (childIndex == pos)  {
        return getBoundaryMinimum();
      }
      pos++;
    }
    if (isSetBoundaryMaximum()) {
      if (childIndex == pos) {
        return getBoundaryMaximum();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(isLeaf() ? MessageFormat.format(
      "Node {0} has no children.", getElementName()) : MessageFormat.format(
        resourceBundle.getString("IndexExceedsBoundsException"), childIndex, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int childCount = super.getChildCount();
    if (isSetBoundaryMinimum()) {
      childCount++;
    }
    if (isSetBoundaryMaximum()) {
      childCount++;
    }
    return childCount;
  }

  /**
   * Returns the value of unit
   *
   * @return the value of unit
   */
  @Override
  public String getUnits() {
    if (isSetUnits()) {
      return unit;
    }
    Model model = getModel();
    if (model.isSetLengthUnits()) {
      unit = model.getLengthUnits();
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.unit, this);
  }

  /**
   * Returns whether unit is set
   *
   * @return whether unit is set
   */
  @Override
  public boolean isSetUnits() {
    return unit != null;
  }

  /**
   * Sets the value of unit
   */
  @Override
  public void setUnits(String unit) {
    String oldUnits = this.unit;
    this.unit = unit;
    firePropertyChange(SpatialConstants.unit, oldUnits, this.unit);
  }

  /**
   * Unsets the variable unit
   */
  @Override
  public void unsetUnits() {
    if (isSetUnits()) {
      String oldUnits = unit;
      unit = null;
      firePropertyChange(SpatialConstants.unit, oldUnits, unit);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    if (isSetUnits()) {
      return getUnitsInstance();
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
   */
  @Override
  public String getDerivedUnits() {
    return getUnits();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#getUnitsInstance()
   */
  @Override
  public UnitDefinition getUnitsInstance() {
    Model model = getModel();
    return model != null ? model.getUnitDefinition(getUnits()) : null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 947;
    int hashCode = super.hashCode();
    if (isSetType()) {
      hashCode += prime * getType().hashCode();
    }
    if (isSetUnits()) {
      hashCode += prime * getUnits().hashCode();
    }
    if (isSetBoundaryMaximum()) {
      hashCode += prime * getBoundaryMaximum().hashCode();
    }
    if (isSetBoundaryMinimum()) {
      hashCode += prime * getBoundaryMinimum().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#isSetUnitsInstance()
   */
  @Override
  public boolean isSetUnitsInstance() {
    Model model = getModel();
    return model != null ? model.containsUnitDefinition(getUnits()) : false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
   */
  @Override
  public void setUnits(Kind unitKind) {
    setUnits(unitKind.toString());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
   */
  @Override
  public void setUnits(Unit unit) {
    this.unit = unit.toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
   */
  @Override
  public void setUnits(UnitDefinition units) {
    if (units.isSetId()) {
      setUnits(units.getId());
    }
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetType()) {
      attributes.remove("type");
      attributes.put(SpatialConstants.shortLabel+":type", String.valueOf(getType()));
    }
    if (isSetUnits()) {
      attributes.remove("unit");
      attributes.put(SpatialConstants.shortLabel+":unit", getUnits());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(SpatialConstants.type)) {
        try {
          setType(CoordinateKind.valueOf(value));
        }
        catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.type, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.unit)) {
        try {
          setUnits(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.unit, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }

    }
    return isAttributeRead;
  }

}
