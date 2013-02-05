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
package org.sbml.jsbml.ext.comp;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;


/**
 * Contains the machinery for constructing references to specific components within enclosed models
 * or even within external models located in other files.
 * 
 * <p>
 * The four different attributes on SBaseRef are mutually exclusive: only one of the attributes can have a value at any
 * given time, and exactly one must have a value in a given SBaseRef object instance.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public abstract class SBaseRef extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -4434500961448381830L;

	/**
	 * This attribute is used to refer
	 * to a port identifier, in the case when the reference being constructed with the SBaseRef is intended to refer to a
	 * port on a submodel. The namespace of the PortSIdRef value is the set of identifiers of type PortSId defined in the
	 * submodel, not the parent model.
	 */
	private String portRef;
	
	/**
	 * This attribute is used to refer to
	 * a regular identifier (i.e., the value of an id attribute on some other object), in the case when the reference being
	 * constructed with the SBaseRef is intended to refer to an object that does not have a port identifier. The namespace
	 * of the SIdRef value is the set of identifiers of type SId defined in the submodel, not the parent model.
	 */
	private String idRef;
	
	/**
	 * This attribute is used to refer to the identifier of a
	 * UnitDefinition object. The namespace of the UnitSIdRef value is the set of unit identifiers defined in the submodel,
	 * not the parent model.
	 */
	private String unitRef;
	
	/**
	 * This attribute is used to refer to a metaid attribute
	 * value on some other object, in the case when the reference being constructed with the SBaseRef is intended to refer
	 * to an object that does not have a port identifier. The namespace of the metaIdRef value is the entire document in
	 * which the referenced model resides
	 */
	private String metaIdRef;
	
	/**
	 * An SBaseRef object may have up to one subcomponent named sBaseRef.
	 * This permits recursive structures to be constructed so that objects inside submodels can be referenced.
	 */
	private SBaseRef sBaseRef;
	
	
	/**
	 * Creates a SBaseRef instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public SBaseRef() {
		super();
	}

	/**
	 * Creates a SBaseRef instance with a level and version. 
	 * 
	 * @param level
	 * @param version
	 */
	public SBaseRef(int level, int version) {
		super(level, version);
	}

	/**
	 * Clone constructor
	 */
	public SBaseRef(SBaseRef obj) {
		super(obj);

		if (obj.isSetPortRef()) {
			setPortRef(obj.getPortRef());
		}
		if (obj.isSetIdRef()) {
			setIdRef(obj.getIdRef());
		}
		if (obj.isSetUnitRef()) {
			setUnitRef(obj.getUnitRef());
		}
		if (obj.isSetMetaIdRef()) {
			setMetaIdRef(obj.getMetaIdRef());
		}
	}

	
	/**
	 * Returns the value of portRef
	 * 
	 * @return the value of portRef
	 */
	public String getPortRef() {

		if (isSetPortRef()) {
			return portRef;
		}

		return "";
	}

	/**
	 * Returns whether portRef is set
	 * 
	 * @return whether portRef is set 
	 */
	public boolean isSetPortRef() {
		return this.portRef != null;
	}

	/**
	 * Sets the value of portRef
	 */
	public void setPortRef(String portRef) {
		String oldPortRef = this.portRef;
		this.portRef = portRef;
		firePropertyChange(CompConstant.portRef, oldPortRef, this.portRef);
	}

	/**
	 * Unsets the variable portRef
	 *  
	 * @return {@code true}, if portRef was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetPortRef() {
		if (isSetPortRef()) {
			String oldPortRef = this.portRef;
			this.portRef = null;
			firePropertyChange(CompConstant.portRef, oldPortRef, this.portRef);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the value of idRef
	 * 
	 * @return the value of idRef
	 */
	public String getIdRef() {

		if (isSetIdRef()) {
			return idRef;
		}

		return "";
	}

	/**
	 * Returns whether idRef is set
	 * 
	 * @return whether idRef is set 
	 */
	public boolean isSetIdRef() {
		return this.idRef != null;
	}

	/**
	 * Sets the value of idRef
	 */
	public void setIdRef(String idRef) {
		String oldIdRef = this.idRef;
		this.idRef = idRef;
		firePropertyChange(CompConstant.idRef, oldIdRef, this.idRef);
	}

	/**
	 * Unsets the variable idRef
	 *  
	 * @return {@code true}, if idRef was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetIdRef() {
		if (isSetIdRef()) {
			String oldIdRef = this.idRef;
			this.idRef = null;
			firePropertyChange(CompConstant.idRef, oldIdRef, this.idRef);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of unitRef
	 * 
	 * @return the value of unitRef
	 */
	public String getUnitRef() {

		if (isSetUnitRef()) {
			return unitRef;
		}

		return "";
	}

	/**
	 * Returns whether unitRef is set
	 * 
	 * @return whether unitRef is set 
	 */
	public boolean isSetUnitRef() {
		return this.unitRef != null;
	}

	/**
	 * Sets the value of unitRef
	 */
	public void setUnitRef(String unitRef) {
		String oldUnitRef = this.unitRef;
		this.unitRef = unitRef;
		firePropertyChange(CompConstant.unitRef, oldUnitRef, this.unitRef);
	}

	/**
	 * Unsets the variable unitRef
	 *  
	 * @return {@code true}, if unitRef was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetUnitRef() {
		if (isSetUnitRef()) {
			String oldUnitRef = this.unitRef;
			this.unitRef = null;
			firePropertyChange(CompConstant.unitRef, oldUnitRef, this.unitRef);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the value of metaIdRef
	 * 
	 * @return the value of metaIdRef
	 */
	public String getMetaIdRef() {

		if (isSetMetaIdRef()) {
			return metaIdRef;
		}

		return "";
	}

	/**
	 * Returns whether metaIdRef is set
	 * 
	 * @return whether metaIdRef is set 
	 */
	public boolean isSetMetaIdRef() {
		return this.metaIdRef != null;
	}

	/**
	 * Sets the value of metaIdRef
	 */
	public void setMetaIdRef(String metaIdRef) {
		String oldIdRef = this.metaIdRef;
		this.metaIdRef = metaIdRef;
		firePropertyChange(CompConstant.metaIdRef, oldIdRef, this.metaIdRef);
	}

	/**
	 * Unsets the variable metaIdRef 
	 * @return {@code true}, if metaIdRef was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetMetaIdRef() {
		if (isSetMetaIdRef()) {
			String oldIdRef = this.metaIdRef;
			this.metaIdRef = null;
			firePropertyChange(CompConstant.metaIdRef, oldIdRef, this.metaIdRef);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns the value of sBaseRef
	 * 
	 * @return the value of sBaseRef
	 */
	public SBaseRef getSBaseRef() {

		if (isSetSBaseRef()) {
			return sBaseRef;
		}

		return null;
	}

	/**
	 * Returns whether sBaseRef is set
	 * 
	 * @return whether sBaseRef is set 
	 */
	public boolean isSetSBaseRef() {
		return this.sBaseRef != null;
	}

	/**
	 * Sets the value of sBaseRef
	 */
	public void setSBaseRef(SBaseRef sBaseRef) {
		SBaseRef oldSBaseRef = this.sBaseRef;
		this.sBaseRef = sBaseRef;
		firePropertyChange(CompConstant.sBaseRef, oldSBaseRef, this.sBaseRef);
	}

	/**
	 * Unsets the variable sBaseRef
	 *  
	 * @return {@code true}, if sBaseRef was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetSBaseRef() {
		if (isSetSBaseRef()) {
			SBaseRef oldSBaseRef = this.sBaseRef;
			this.sBaseRef = null;
			firePropertyChange(CompConstant.sBaseRef, oldSBaseRef, this.sBaseRef);
			return true;
		}
		return false;
	}
	
	

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	public int getChildCount() {
		int count = super.getChildCount();

		 if (isSetSBaseRef()) {
		  count++;
		 }

		return count;
	}

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

		 if (isSetSBaseRef()) {
		   if (pos == index) {
		     return getSBaseRef();
		   }
		   pos++;
		 }

		throw new IndexOutOfBoundsException(MessageFormat.format(
				"Index {0,number,integer} >= {1,number,integer}", index,
				+((int) Math.min(pos, 0))));
	}
	
	
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		  if (isSetPortRef()) {
		    attributes.put(CompConstant.shortLabel + ":" + CompConstant.portRef, getPortRef());
		  }
		  if (isSetIdRef()) {
			  attributes.put(CompConstant.shortLabel + ":" + CompConstant.idRef, getIdRef());
		  }
		  if (isSetUnitRef()) {
			  attributes.put(CompConstant.shortLabel + ":" + CompConstant.unitRef, getUnitRef());
		  }
		  if (isSetMetaIdRef()) {
			  attributes.put(CompConstant.shortLabel + ":" + CompConstant.metaIdRef, getMetaIdRef());
		  }

		  return attributes;
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(CompConstant.portRef)) {
				setPortRef(value);
			}
			else if (attributeName.equals(CompConstant.idRef)) {
				setIdRef(value);
			} else if (attributeName.equals(CompConstant.unitRef)) {
				setUnitRef(value);
			} else if (attributeName.equals(CompConstant.metaIdRef)) {
				setMetaIdRef(value);
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
		return "SBaseRef [portRef=" + portRef + ", idRef=" + idRef
				+ ", unitRef=" + unitRef + ", metaIdRef=" + metaIdRef
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idRef == null) ? 0 : idRef.hashCode());
		result = prime * result
				+ ((metaIdRef == null) ? 0 : metaIdRef.hashCode());
		result = prime * result + ((portRef == null) ? 0 : portRef.hashCode());
		result = prime * result
				+ ((sBaseRef == null) ? 0 : sBaseRef.hashCode());
		result = prime * result + ((unitRef == null) ? 0 : unitRef.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SBaseRef other = (SBaseRef) obj;
		if (idRef == null) {
			if (other.idRef != null)
				return false;
		} else if (!idRef.equals(other.idRef))
			return false;
		if (metaIdRef == null) {
			if (other.metaIdRef != null)
				return false;
		} else if (!metaIdRef.equals(other.metaIdRef))
			return false;
		if (portRef == null) {
			if (other.portRef != null)
				return false;
		} else if (!portRef.equals(other.portRef))
			return false;
		if (sBaseRef == null) {
			if (other.sBaseRef != null)
				return false;
		} else if (!sBaseRef.equals(other.sBaseRef))
			return false;
		if (unitRef == null) {
			if (other.unitRef != null)
				return false;
		} else if (!unitRef.equals(other.unitRef))
			return false;
		return true;
	}
	
	
}
