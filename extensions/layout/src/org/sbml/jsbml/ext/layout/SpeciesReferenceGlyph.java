/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
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
package org.sbml.jsbml.ext.layout;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.SBMLException;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpeciesReferenceGlyph extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -8810905237933499989L;
	
	/**
	 * 
	 */
	private Curve curve;

	/**
	 * 
	 */
	private SpeciesReferenceRole role;
	
	/**
	 * 
	 */
	private String speciesGlyph;
	
	/**
	 * 
	 */
	private String speciesReference;
	
	/**
	 * 
	 */
	public SpeciesReferenceGlyph() {
		super();
		addNamespace(LayoutConstant.namespaceURI);
	}
	/**
	 * 
	 * @param speciesReferencesGlyph
	 */
	public SpeciesReferenceGlyph(SpeciesReferenceGlyph speciesReferencesGlyph) {
		super(speciesReferencesGlyph);
		// TODO 
	}

	/**
	 * 
	 * @param id
	 */
	public SpeciesReferenceGlyph(String id) {
		super(id);
		addNamespace(LayoutConstant.namespaceURI);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public SpeciesReferenceGlyph clone() {
		return new SpeciesReferenceGlyph(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			SpeciesReferenceGlyph s = (SpeciesReferenceGlyph) object;
			equals &= s.isSetSpeciesReferenceRole() && isSetSpeciesReferenceRole();
			if (equals && isSetSpeciesReferenceRole()) {
				equals &= s.getSpeciesReferenceRole().equals(getSpeciesReferenceRole());
			}
			equals &= s.isSetSpeciesReference() == isSetSpeciesReference();
			if (equals && isSetSpeciesReference()) {
				equals &= s.getSpeciesReference().equals(getSpeciesReference());
			}
			equals &= s.isSetSpeciesGlyph() == isSetSpeciesGlyph();
			if (equals && isSetSpeciesGlyph()) {
				equals &= s.getSpeciesGlyph().equals(getSpeciesGlyph());
			}
		}
		return equals;
	}
	
	/*
	 * (non-Javadoc)
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
		if (isSetCurve()) {
			if (pos == index) {
				return getCurve();
			}
			pos++;
		}

		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();
		if (isSetCurve()) {
			count++;
		}

		return count;
	}

	/**
	 * 
	 * @return
	 */
	public Curve getCurve() {
		return curve;
	}

	/**
	 * 
	 * @return
	 */
	public String getSpeciesGlyph() {
		return speciesGlyph;
	}

	/**
	 * 
	 * @return
	 */
	public String getSpeciesReference() {
		return speciesReference;
	}

	/**
	 * @return
	 */
	public SpeciesReferenceRole getSpeciesReferenceRole() {
		return role;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 953;
		int hashCode = super.hashCode();
		if (isSetId()) {
			hashCode += prime * getId().hashCode();
		}
		if (isSetSpeciesReferenceRole()) {
			hashCode += prime * getSpeciesReferenceRole().hashCode();
		}
		if (isSetSpeciesReference()) {
			hashCode += prime * getSpeciesReference().hashCode();
		}
		if (isSetSpeciesGlyph()) {
			hashCode += prime * getSpeciesGlyph().hashCode();
		}

		return hashCode;
	}

	/* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return false;
  }


	/**
	 * @return
	 */
	public boolean isSetCurve() {
		return curve != null;
	}
	
	/**
	 * @return
	 */
	public boolean isSetSpeciesGlyph() {
		return speciesGlyph != null;
	}

	/**
	 * @return
	 */
	public boolean isSetSpeciesReference() {
		return speciesReference != null;
	}

	/**
	 * @return
	 */
	public boolean isSetSpeciesReferenceRole() {
		return role != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if(!isAttributeRead) {
		
			isAttributeRead = true;
			
			if(attributeName.equals(LayoutConstant.speciesReference))
			{				
				setSpeciesReference(value);
			}
			else if(attributeName.equals(LayoutConstant.speciesGlyph))
			{
				setSpeciesGlyph(value);
			}
			else if(attributeName.equals(LayoutConstant.role))
			{
		    	  try {
		    		  setRole(SpeciesReferenceRole.valueOf(value.toUpperCase()));
		    	  } catch (Exception e) {
		    		  throw new SBMLException("Could not recognized the value '" + value
		    				  + "' for the attribute " + LayoutConstant.role
		    				  + " on the 'SpeciesReferenceGlyph' element.");
		    	  }
			}
			else
			{
				return false;
			}
		}
		
		return isAttributeRead;
	}
	
	public void setRole(SpeciesReferenceRole valueOf) {
		SpeciesReferenceRole oldRole = this.role;
		this.role = valueOf;
		firePropertyChange(LayoutConstant.role, oldRole, this.role);
	}
	/**
	 * 
	 * @param curve
	 */
	public void setCurve(Curve curve) {
		Curve oldCurve = this.curve;
		this.curve = curve;
		firePropertyChange(LayoutConstant.curve, oldCurve, this.role);
		registerChild(this.curve);
	}
	
	/**
	 * 
	 * @param speciesGlyph
	 */
	public void setSpeciesGlyph(String speciesGlyph) {
		String oldValue = this.speciesGlyph;
		this.speciesGlyph = speciesGlyph;
		firePropertyChange(LayoutConstant.speciesGlyph, oldValue, this.speciesGlyph);
	}

  /**
	 * 
	 * @param speciesReference
	 */
	public void setSpeciesReference(String speciesReference) {
		String oldSpeciesReference = this.speciesReference;
		this.speciesReference = speciesReference;
		firePropertyChange(LayoutConstant.speciesReference, oldSpeciesReference, this.speciesReference);
	}

	
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetSpeciesGlyph()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.speciesGlyph, speciesGlyph);
		}
		if (isSetSpeciesReference()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.speciesReference, speciesReference);
		}
		if (isSetSpeciesReferenceRole()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.role, role.toString().toLowerCase());
		}

		return attributes;
	}
}
