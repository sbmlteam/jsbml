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
package org.sbml.jsbml.ext.layout;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SimpleSpeciesReference;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpeciesReferenceGlyph extends NamedSBaseGlyph {

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
	public SpeciesReferenceGlyph() {
		super();
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public SpeciesReferenceGlyph(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param speciesReferenceGlyph
	 */
	public SpeciesReferenceGlyph(SpeciesReferenceGlyph speciesReferenceGlyph) {
		super(speciesReferenceGlyph);
		if (speciesReferenceGlyph.isSetCurve()) {
			this.curve = speciesReferenceGlyph.getCurve().clone();
		}
		if (speciesReferenceGlyph.isSetSpeciesReferenceRole()) {
			this.role = SpeciesReferenceRole.valueOf(speciesReferenceGlyph.getSpeciesReferenceRole().toString());
		}
		if (speciesReferenceGlyph.isSetSpeciesGlyph()) {
			this.speciesGlyph = new String(speciesReferenceGlyph.getSpeciesGlyph());
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public SpeciesReferenceGlyph(String id) {
		super(id);
	}
	
	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public SpeciesReferenceGlyph(String id, int level, int version) {
		super(id, level, version);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	public SpeciesReferenceGlyph clone() {
		return new SpeciesReferenceGlyph(this);
	}
	
	/**
	 * 
	 * @return
	 */
	public Curve createCurve() {
	  if (isSetCurve()) {
	    unsetCurve();
	  }
	  setCurve(new Curve(getLevel(), getVersion()));
	  return getCurve();
	}
	
	/* (non-Javadoc)
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
			equals &= s.isSetSpeciesGlyph() == isSetSpeciesGlyph();
			if (equals && isSetSpeciesGlyph()) {
				equals &= s.getSpeciesGlyph().equals(getSpeciesGlyph());
			}
		}
		return equals;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#getChildAt(int)
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

		throw new IndexOutOfBoundsException(MessageFormat.format(
		  "Index {0,number,integer} >= {1,number,integer}",
			index, +((int) Math.min(pos, 0))));
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#getChildCount()
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
	public SpeciesGlyph getSpeciesGlyphInstance() {
	  if (!isSetSpeciesGlyph()) {
	    return null;
	  }
	  Model model = getModel();
	  return (model != null) ? (SpeciesGlyph) model.findNamedSBase(getSpeciesGlyph()) : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSpeciesReference() {
		return getNamedSBase();
	}

	/**
   * Note that the return type of this method is {@link NamedSBase} because it
   * could be possible to link some element from other packages to this glyph.
   * 
   * @return
   */
	public NamedSBase getSpeciesReferenceInstance() {
	  return getNamedSBaseInstance();
	}

  /**
	 * @return
	 */
	public SpeciesReferenceRole getSpeciesReferenceRole() {
		return role;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 953;
		int hashCode = super.hashCode();
		if (isSetSpeciesReferenceRole()) {
			hashCode += prime * getSpeciesReferenceRole().hashCode();
		}
		if (isSetSpeciesGlyph()) {
			hashCode += prime * getSpeciesGlyph().hashCode();
		}
		return hashCode;
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
		return isSetNamedSBase();
	}
	
	/**
	 * @return
	 */
	public boolean isSetSpeciesReferenceRole() {
		return role != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if(!isAttributeRead) {
		
			isAttributeRead = true;
			
			if(attributeName.equals(LayoutConstants.speciesReference))
			{				
				setSpeciesReference(value);
			}
			else if(attributeName.equals(LayoutConstants.speciesGlyph))
			{
				setSpeciesGlyph(value);
			}
			else if(attributeName.equals(LayoutConstants.role))
			{
		    	  try {
		    		  setRole(SpeciesReferenceRole.valueOf(value.toUpperCase()));
		    	  } catch (Exception e) {
		    		  throw new SBMLException("Could not recognized the value '" + value
		    				  + "' for the attribute " + LayoutConstants.role
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

	/**
	 * 
	 * @param curve
	 */
	public void setCurve(Curve curve) {
		Curve oldCurve = this.curve;
		this.curve = curve;
		firePropertyChange(LayoutConstants.curve, oldCurve, this.role);
		registerChild(this.curve);
	}

	/**
	 * 
	 * @param valueOf
	 */
	public void setRole(SpeciesReferenceRole valueOf) {
		SpeciesReferenceRole oldRole = this.role;
		this.role = valueOf;
		firePropertyChange(LayoutConstants.role, oldRole, this.role);
	}
	
	/**
	 * 
	 * @param speciesGlyph
	 */
	public void setSpeciesGlyph(String speciesGlyph) {
		String oldValue = this.speciesGlyph;
		this.speciesGlyph = speciesGlyph;
		firePropertyChange(LayoutConstants.speciesGlyph, oldValue, this.speciesGlyph);
	}
	
	/**
	 * 
	 * @param speciesReference
	 */
	public void setSpeciesReference(SimpleSpeciesReference speciesReference) {
		setSpeciesReference(speciesReference.getId());
	}
	
	/**
	 * 
	 * @param speciesReference
	 */
	public void setSpeciesReference(String speciesReference) {
		setNamedSBase(speciesReference, LayoutConstants.speciesReference);
	}

	/**
	 * 
	 * @return
	 */
	public boolean unsetCurve() {
	  if (isSetCurve()) {
	    Curve oldCurve = getCurve();
	    this.curve = null;
	    oldCurve.fireNodeRemovedEvent();
	    return true;
	  }
    return false;
  }
	
	/**
	 * 
	 */
	public void unsetSpeciesReference() {
		unsetNamedSBase();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetSpeciesGlyph()) {
			attributes.put(LayoutConstants.shortLabel + ':'
					+ LayoutConstants.speciesGlyph, speciesGlyph);
		}
		if (isSetSpeciesReference()) {
			attributes.put(LayoutConstants.shortLabel + ':'
					+ LayoutConstants.speciesReference, getSpeciesReference());
		}
		if (isSetSpeciesReferenceRole()) {
			attributes.put(LayoutConstants.shortLabel + ':'
					+ LayoutConstants.role, role.toString().toLowerCase());
		}

		return attributes;
	}

}
