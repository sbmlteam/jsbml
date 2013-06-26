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

import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * @author Sebastian Fr&oum;hlich
 * @author rodrigue
 * @since 1.0
 * @version $Rev$
 */
public abstract class CurveSegment extends AbstractNamedSBase {

	public enum Type
	{
		LINE_SEGMENT("LineSegment"), 
		CUBIC_BEZIER("CubicBezier");
		
		private final String xmlString;
		
		/**
		 * Returns the xmlString
		 *
		 * @return the xmlString
		 */
		public String getXmlString() {
			return xmlString;
		}

		private Type(String xmlString)
		{
			this.xmlString = xmlString;
		}
		
		@Override
		public String toString() {
			return xmlString;
		}
		
		public static Type fromString(String value)
		{
			if (value == null)
			{
				throw new IllegalArgumentException();
			}
			
			for(Type v : values())
			{
				if(value.equalsIgnoreCase(v.getXmlString()))
				{
					return v;
				}
			}
			throw new IllegalArgumentException();

		}
	}

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5085246314333062152L;

	/**
	 * 
	 */
	private Logger logger = Logger.getLogger(CurveSegment.class);

	/**
	 * 
	 */
	private Type type;

	
	
	/**
	 * 
	 */
	public CurveSegment() {
	  super();
	  initDefaults();
	}

  /**
	 * 
	 * @param curveSegment
	 */
	public CurveSegment(CurveSegment curveSegment) {
		super(curveSegment);

		if (curveSegment.isSetType()) {
			setType(curveSegment.getType());
		}
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public CurveSegment(int level, int version) {
		super(level, version);
		initDefaults();
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			CurveSegment curveSegment = (CurveSegment) object;
      equals &= curveSegment.isSetType() == isSetType();
      if (equals && isSetType()) {
        equals &= curveSegment.getType().equals(getType());
      }
    }
    return equals;
  }

	/**
	 * 
	 * @return
	 */
	public Type getType() {
		return type;
	}

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 937;
    int hashCode = super.hashCode();
    if (isSetType()) {
      hashCode += prime * getType().hashCode();
    }
    return hashCode;
  }


	/**
	 * 
	 */
	private void initDefaults() {
	  addNamespace(LayoutConstants.namespaceURI);
  }


	/**
	 * 
	 * @return
	 */
	public boolean isSetType() {
		return type != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		logger.debug("reading CurveSegmentImpl: " + prefix + " : " + attributeName);

		if (!isAttributeRead) {
			
			if (attributeName.equals("type")) {
				try 
				{
					setType(Type.fromString(value));
				}
				catch (Exception e) 
				{
					throw new SBMLException("Could not recognized the value '" + value
							+ "' for the attribute " + LayoutConstants.type
							+ " on the 'curveSegment' element.");
				}
				return true;
			}
		}

		return isAttributeRead;
	}

	/**
	 * 
	 * @param type
	 */
	void setType(Type type) 
	{
		Type oldType = this.type;
		this.type = type;
		firePropertyChange(TreeNodeChangeEvent.type, oldType, this.type);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("process attributes of CurveSegmentImpl");
			logger.debug("isSetType: " + isSetType());
			logger.debug("Type = " + type);
		}
		
		if (isSetType()) {
			attributes.put(LayoutConstants.xsiShortLabel + ":type", getType().toString());
		}
		
		return attributes;
	}

	public boolean isIdMandatory() {
		return false;
	}

}
