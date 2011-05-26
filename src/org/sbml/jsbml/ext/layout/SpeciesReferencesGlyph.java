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

import org.sbml.jsbml.AbstractNamedSBase;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpeciesReferencesGlyph extends AbstractNamedSBase {

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
	private String id;

	/**
	 * 
	 */
	private SpeciesReferenceRole role;
	/**
	 * 
	 */
	private SpeciesGlyph speciesGlyph;
	/**
	 * 
	 */
	private String speciesReference;
	
	/**
	 * 
	 */
	public SpeciesReferencesGlyph() {
		super();
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public SpeciesReferencesGlyph(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param speciesReferencesGlyph
	 */
	public SpeciesReferencesGlyph(SpeciesReferencesGlyph speciesReferencesGlyph) {
		super(speciesReferencesGlyph);
		// TODO Auto-generated constructor stub
	}

	public SpeciesReferencesGlyph(String id) {
		super(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public SpeciesReferencesGlyph clone() {
		return new SpeciesReferencesGlyph(this);
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
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public SpeciesGlyph getSpeciesGlyph() {
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
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = false; //super.readAttribute(attributeName, prefix,
				//value);
		System.out.println("readAttribute in SpeciesReference.class");
		if(!isAttributeRead)
		
			isAttributeRead = true;			
			if(attributeName.equals("speciesReference"))
			{				
				this.speciesReference = value;
				System.out.println("SpeciesReference: speciesReference: "+speciesReference);
			}
			else if(attributeName.equals("id"))
			{
				this.id = value;
				System.out.println("SpeciesReference: id: "+id);
			}
			return isAttributeRead;
	}

	/**
	 * 
	 * @param curve
	 */
	public void setCurve(Curve curve) {
		this.curve = curve;
	}

	/**
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @param speciesGlyph
	 */
	public void setSpeciesGlyph(SpeciesGlyph speciesGlyph) {
		this.speciesGlyph = speciesGlyph;
	}
	
	/**
	 * 
	 * @param speciesReference
	 */
	public void setSpeciesReference(String speciesReference) {
		this.speciesReference = speciesReference;
	}

}
