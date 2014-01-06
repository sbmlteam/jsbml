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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBaseWithUnit;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class CoordinateComponent extends NamedSpatialElement implements
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
			this.componentType = new String(coordComp.getComponentType());
		}
		if (coordComp.isSetCoordinateIndex()) {
			this.coordinateIndex = Integer.valueOf(coordComp.getCoordinateIndex());
		}
		if (coordComp.isSetUnits()) {
			this.unit = new String(coordComp.getUnits());
		}
		if (coordComp.isSetMinimum()) {
			this.minimum = coordComp.getMinimum().clone();
		}
		if (coordComp.isSetMaximum()) {
			this.maximum = coordComp.getMaximum().clone();
		}
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public CoordinateComponent(int level, int version) {
		super(level, version);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public CoordinateComponent clone() {
		return new CoordinateComponent(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
	 */
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
				  "Index {0,number,integer} >= {1,number,integer}", childIndex, +((int) Math.min(pos, 0))));
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
	 * @return the componentType
	 */
	public String getComponentType() {
		return isSetComponentType() ? componentType : "";
	}

	/**
	 * @return the coordinateIndex
	 */
	public Integer getCoordinateIndex() {
		return coordinateIndex;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		if (isSetUnits()) {
			return getUnitsInstance();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	public String getDerivedUnits() {
		return getUnits();
	}

	/**
	 * @return the maximum
	 */
	public Boundary getMaximum() {
		return maximum;
	}
	
	/**
	 * @return the minimum
	 */
	public Boundary getMinimum() {
		return minimum;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#getUnits()
	 */
	public String getUnits() {
		return isSetUnits() ? unit : "";
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#getUnitsInstance()
	 */
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

	/**
	 * 
	 * @return
	 */
	public boolean isSetComponentType() {
		return componentType != null;
	}

	/**
	 * @return
	 */
	public boolean isSetCoordinateIndex() {
		return coordinateIndex != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetMaximum() {
		return maximum != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetMinimum() {
		return minimum != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#isSetUnits()
	 */
	public boolean isSetUnits() {
		return unit != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#isSetUnitsInstance()
	 */
	public boolean isSetUnitsInstance() {
		Model model = getModel();
		return model != null ? model.containsUnitDefinition(getUnits()) : false;
	}

	/**
	 * @param componentType the componentType to set
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	/**
	 * @param coordinateIndex the coordinateIndex to set
	 */
	public void setCoordinateIndex(Integer coordinateIndex) {
		this.coordinateIndex = coordinateIndex;
	}

	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(Boundary maximum) {
		this.maximum = maximum;
	}

	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(Boundary minimum) {
		this.minimum = minimum;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
	 */
	public void setUnits(Kind unitKind) {
		setUnits(unitKind.toString());
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(java.lang.String)
	 */
	public void setUnits(String units) {
		this.unit = units;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
	 */
	public void setUnits(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
	 */
	public void setUnits(UnitDefinition units) {
		if (units.isSetId()) {
			setUnits(units.getId());
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithUnit#unsetUnits()
	 */
	public void unsetUnits() {
		setUnits((String) null);
	}

}
