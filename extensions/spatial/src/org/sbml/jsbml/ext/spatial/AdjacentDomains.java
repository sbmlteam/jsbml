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

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class AdjacentDomains extends AbstractSBase {

	  // private String spatialId; // TODO : see what to do about this id, either extends NamedSBase or a custom Spatial only abstract class
	
	  private String domain1;
	  private String domain2;

	
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1600690320824551145L;

	/**
	 * Creates a {@link AdjacentDomains} instance. 
	 */
	public AdjacentDomains() {
	}

	/**
	 * Creates a {@link AdjacentDomains} instance with a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public AdjacentDomains(int level, int version) {
		super(level, version);
	}

	/**
	 * Clone constructor
	 * 
	 * @param sb
	 */
	public AdjacentDomains(AdjacentDomains sb) {
		super(sb);

		if (sb.isSetDomain1()) {
			setDomain1(sb.getDomain1());
		}
		if (sb.isSetDomain2()) {
			setDomain2(sb.getDomain2());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public AdjacentDomains clone() {
		return new AdjacentDomains(this);
	}

	
	/**
	 * Returns the value of domain1
	 *
	 * @return the value of domain1
	 */
	public String getDomain1() 
	{
		return domain1;
	}

	/**
	 * Returns whether domain1 is set 
	 *
	 * @return whether domain1 is set 
	 */
	public boolean isSetDomain1() {
		return this.domain1 != null;
	}

	/**
	 * Sets the value of domain1
	 */
	public void setDomain1(String domain1) {
		String oldDomain1 = this.domain1;
		this.domain1 = domain1;
		firePropertyChange(SpatialConstant.domain1, oldDomain1, this.domain1);
	}

	/**
	 * Unsets the variable domain1 
	 *
	 * @return {@code true}, if domain1 was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetDomain1() {
		if (isSetDomain1()) {
			String oldDomain1 = this.domain1;
			this.domain1 = null;
			firePropertyChange(SpatialConstant.domain1, oldDomain1, this.domain1);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of domain2
	 *
	 * @return the value of domain2
	 */
	public String getDomain2() 
	{
		return domain2;
	}

	/**
	 * Returns whether domain2 is set 
	 *
	 * @return whether domain2 is set 
	 */
	public boolean isSetDomain2() {
		return this.domain2 != null;
	}

	/**
	 * Sets the value of domain2
	 */
	public void setDomain2(String domain2) {
		String oldDomain2 = this.domain2;
		this.domain2 = domain2;
		firePropertyChange(SpatialConstant.domain2, oldDomain2, this.domain2);
	}

	/**
	 * Unsets the variable domain2 
	 *
	 * @return {@code true}, if domain2 was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetDomain2() {
		if (isSetDomain2()) {
			String oldDomain2 = this.domain2;
			this.domain2 = null;
			firePropertyChange(SpatialConstant.domain2, oldDomain2, this.domain2);
			return true;
		}
		return false;
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
		if (!(obj instanceof AdjacentDomains)) {
			return false;
		}
		AdjacentDomains other = (AdjacentDomains) obj;
		if (domain1 == null) {
			if (other.domain1 != null) {
				return false;
			}
		} else if (!domain1.equals(other.domain1)) {
			return false;
		}
		if (domain2 == null) {
			if (other.domain2 != null) {
				return false;
			}
		} else if (!domain2.equals(other.domain2)) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((domain1 == null) ? 0 : domain1.hashCode());
		result = prime * result + ((domain2 == null) ? 0 : domain2.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdjacentDomains [domain1=" + domain1 + ", domain2=" + domain2
				+ "]";
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
	 */
	public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();

	  if (isSetDomain1()) {
	    attributes.put(SpatialConstant.shortLabel + ":domain1", getDomain1());
	  }
	  if (isSetDomain2()) {
	    attributes.put(SpatialConstant.shortLabel + ":domain2", getDomain2());
	  }

	  return attributes;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(SpatialConstant.domain1)) {
				setDomain1(value);
			} 
			else if (attributeName.equals(SpatialConstant.domain2)) 
			{
				setDomain2(value);
			}
			else 
			{
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}

}
