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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.spatial;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class CompartmentMapping extends NamedSpatialElement {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -4623759168043277022L;

	/**
	 * 
	 */
	private String compartment;

	/**
	 * 
	 */
	private String domainType;

	/**
	 * 
	 */
	private Double unitSize;
	
	/**
	 * 
	 */
	public CompartmentMapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public CompartmentMapping(int level, int version) {
		super(level, version);
	}
	
	/**
	 * @param cm
	 */
	public CompartmentMapping(CompartmentMapping cm) {
		super(cm);
		if (cm.isSetCompartment()) {
			this.compartment = new String(cm.getCompartment());
		}
		if (cm.isSetDomainType()) {
			this.domainType = new String(cm.getDomainType());
		}
		if (cm.isSetUnitSize()) {
			this.unitSize = Double.valueOf(cm.getUnitSize());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public CompartmentMapping clone() {
		return new CompartmentMapping(this);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub
		return super.equals(object);
	}

	/**
	 * @return the compartment
	 */
	public String getCompartment() {
		return isSetCompartment() ? compartment : "";
	}

	/**
	 * 
	 * @return
	 */
	public Compartment getCompartmentInstance() {
		Model m = getModel();
		return m != null ? m.getCompartment(getCompartment()) : null;
	}
	
	/**
	 * @return the domainType
	 */
	public String getDomainType() {
		return domainType;
	}
	
	/**
	 * 
	 * @return
	 */
	public DomainType getDomainTypeInstance() {
		Model m = getModel();
		// TODO
		return null;
	}

	/**
	 * @return the unitSize
	 */
	public double getUnitSize() {
		return isSetUnitSize() ? unitSize.doubleValue() : Double.NaN;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetCompartment() {
		return compartment != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetDomainType() {
		return domainType != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetUnitSize() {
		return (unitSize == null) || (Double.isNaN(unitSize.doubleValue()));
	}

	/**
	 * @param compartment the compartment to set
	 */
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}

	/**
	 * @param domainType the domainType to set
	 */
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	/**
	 * @param unitSize the unitSize to set
	 */
	public void setUnitSize(Double unitSize) {
		this.unitSize = unitSize;
	}

}
