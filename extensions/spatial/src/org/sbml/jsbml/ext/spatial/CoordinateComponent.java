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
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBaseWithUnit;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class CoordinateComponent extends AbstractSpatialNamedSBase implements
SBaseWithUnit {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3561130269969678307L;

  /**
   * 
   */
  private String componentType;

  /**
   * 
   */
  private Integer coordinateIndex;

  /**
   * 
   */
  private Boundary maximum;

  /**
   * 
   */
  private Boundary minimum;

  /**
   * 
   */
  private String unit;

  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");

  /**
   * 
   */
  public CoordinateComponent() {
    super();
  }

  /**
   * @param coordComp
   */
  public CoordinateComponent(CoordinateComponent coordComp) {
    super(coordComp);
    if (coordComp.isSetComponentType()) {
      componentType = new String(coordComp.getComponentType());
    }
    if (coordComp.isSetCoordinateIndex()) {
      coordinateIndex = Integer.valueOf(coordComp.getCoordinateIndex());
    }
    if (coordComp.isSetUnits()) {
      unit = new String(coordComp.getUnits());
    }
    if (coordComp.isSetMinimum()) {
      minimum = coordComp.getMinimum().clone();
    }
    if (coordComp.isSetMaximum()) {
      maximum = coordComp.getMaximum().clone();
    }
  }

  /**
   * @param level
   * @param version
   */
  public CoordinateComponent(int level, int version) {
    super(level, version);
  }

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
   * Returns the value of minimum
   *
   * @return the value of minimum
   */
  public Boundary getMinimum() {
    if (isSetMinimum()) {
      return minimum;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.boundaryMinimum, this);
  }


  /**
   * Returns whether minimum is set
   *
   * @return whether minimum is set
   */
  public boolean isSetMinimum() {
    return minimum != null;
  }


  /**
   * Sets the value of minimum
   */
  public void setMinimum(Boundary minimum) {
    Boundary oldMinimum = this.minimum;
    this.minimum = minimum;
    firePropertyChange(SpatialConstants.boundaryMinimum, oldMinimum, this.minimum);
  }


  /**
   * Unsets the variable minimum
   *
   * @return {@code true}, if minimum was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMinimum() {
    if (isSetMinimum()) {
      Boundary oldMinimum = minimum;
      minimum = null;
      firePropertyChange(SpatialConstants.boundaryMinimum, oldMinimum, minimum);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of maximum
   *
   * @return the value of maximum
   */
  public Boundary getMaximum() {
    if (isSetMaximum()) {
      return maximum;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.boundaryMaximum, this);
  }


  /**
   * Returns whether maximum is set
   *
   * @return whether maximum is set
   */
  public boolean isSetMaximum() {
    return maximum != null;
  }


  /**
   * Sets the value of maximum
   */
  public void setMaximum(Boundary maximum) {
    Boundary oldMaximum = maximum;
    this.maximum = maximum;
    firePropertyChange(SpatialConstants.boundaryMaximum, oldMaximum, maximum);
  }


  /**
   * Unsets the variable maximum
   *
   * @return {@code true}, if maximum was set before,
   *         otherwise {@code false}
   */
  public boolean unsetMaximum() {
    if (isSetMaximum()) {
      Boundary oldMaximum = maximum;
      maximum = null;
      firePropertyChange(SpatialConstants.boundaryMaximum, oldMaximum, maximum);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of coordinateIndex
   *
   * @return the value of coordinateIndex
   */
  public int getCoordinateIndex() {
    if (isSetCoordinateIndex()) {
      return coordinateIndex;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.index, this);
  }


  /**
   * Returns whether coordinateIndex is set
   *
   * @return whether coordinateIndex is set
   */
  public boolean isSetCoordinateIndex() {
    return coordinateIndex != null;
  }


  /**
   * Sets the value of coordinateIndex
   */
  public void setCoordinateIndex(int coordinateIndex) {
    int oldCoordinateIndex = this.coordinateIndex;
    this.coordinateIndex = coordinateIndex;
    firePropertyChange(SpatialConstants.index, oldCoordinateIndex, this.coordinateIndex);
  }


  /**
   * Unsets the variable coordinateIndex
   *
   * @return {@code true}, if coordinateIndex was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoordinateIndex() {
    if (isSetCoordinateIndex()) {
      int oldCoordinateIndex = coordinateIndex;
      coordinateIndex = null;
      firePropertyChange(SpatialConstants.index, oldCoordinateIndex, coordinateIndex);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of componentType
   *
   * @return the value of componentType
   */
  public String getComponentType() {
    if (isSetComponentType()) {
      return componentType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.componentType, this);
  }


  /**
   * Returns whether componentType is set
   *
   * @return whether componentType is set
   */
  public boolean isSetComponentType() {
    return componentType != null;
  }


  /**
   * Sets the value of componentType
   */
  public void setComponentType(String componentType) {
    String oldComponentType = this.componentType;
    this.componentType = componentType;
    firePropertyChange(SpatialConstants.componentType, oldComponentType, this.componentType);
  }


  /**
   * Unsets the variable componentType
   *
   * @return {@code true}, if componentType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetComponentType() {
    if (isSetComponentType()) {
      String oldComponentType = componentType;
      componentType = null;
      firePropertyChange(SpatialConstants.componentType, oldComponentType, componentType);
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
      equal &= cc.isSetComponentType() == isSetComponentType();
      if (equal && isSetComponentType()) {
        equal &= cc.getComponentType().equals(getComponentType());
      }
      equal &= cc.isSetCoordinateIndex() == isSetCoordinateIndex();
      if (equal && isSetCoordinateIndex()) {
        equal &= cc.getCoordinateIndex() == getCoordinateIndex();
      }
      equal &= cc.isSetMinimum() == isSetMinimum();
      if (equal && isSetMinimum()) {
        equal &= cc.getMinimum().equals(getMinimum());
      }
      equal &= cc.isSetMaximum() == isSetMaximum();
      if (equal && isSetMaximum()) {
        equal &= cc.getMaximum().equals(getMaximum());
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
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    if (isSetMinimum()) {
      if (childIndex == pos)  {
        return getMinimum();
      }
      pos++;
    }
    if (isSetMaximum()) {
      if (childIndex == pos) {
        return getMaximum();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(isLeaf() ? MessageFormat.format(
      "Node {0} has no children.", getElementName()) : MessageFormat.format(
        "Index {0,number,integer} >= {1,number,integer}", childIndex, +Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int childCount = super.getChildCount();
    if (isSetMinimum()) {
      childCount++;
    }
    if (isSetMaximum()) {
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
   *
   * @return {@code true}, if unit was set before,
   *         otherwise {@code false}
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
    if (isSetComponentType()) {
      hashCode += prime * getComponentType().hashCode();
    }
    if (isSetCoordinateIndex()) {
      hashCode += prime * getCoordinateIndex();
    }
    if (isSetUnits()) {
      hashCode += prime * getUnits().hashCode();
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
    if (isSetComponentType()) {
      attributes.remove("componentType");
      attributes.put(SpatialConstants.shortLabel + ":componentType", getComponentType());
    }
    if (isSetCoordinateIndex()) {
      attributes.remove("coordinateIndex");
      attributes.put(SpatialConstants.shortLabel+":coordinateIndex", String.valueOf(getCoordinateIndex()));
    }
    if (isSetUnits()) {
      attributes.remove("unit");
      attributes.put(SpatialConstants.shortLabel+":unit", getUnits());
    }
    return attributes;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value) && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(SpatialConstants.componentType)) {
        try {
          setComponentType(value);
        }
        catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.componentType);
        }
      }
      else if (attributeName.equals(SpatialConstants.index)) {
        try {
          setCoordinateIndex(StringTools.parseSBMLInt(value));
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.index);
        }
      }
      else if (attributeName.equals(SpatialConstants.unit)) {
        try {
          setUnits(value);
        } catch (Exception e) {
          MessageFormat.format(bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.unit);
        }
      }
      else {
        isAttributeRead = false;
      }

    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoordinateComponent [componentType=");
    builder.append(componentType);
    builder.append(", coordinateIndex=");
    builder.append(coordinateIndex);
    builder.append(", maximum=");
    builder.append(maximum);
    builder.append(", minimum=");
    builder.append(minimum);
    builder.append(", unit=");
    builder.append(unit);
    builder.append("]");
    return builder.toString();
  }



}
