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
package org.sbml.jsbml.ext.qual;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * Represents the FunctionTerm element from the SBML Qualitative models package,
 * see http://sbml.org/Community/Wiki/SBML_Level_3_Proposals/Qualitative_Models.
 * 
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date $Date$
 */
public class FunctionTerm extends AbstractMathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3456373304133826017L;

	/**
	 * 
	 */
	private Integer resultLevel;
    /**
     * 
     */
	private boolean defaultTerm;

	/**
	 * Default constructor
	 * 
	 */
	public FunctionTerm() {
		super();
		addNamespace(QualConstant.namespaceURI);
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public FunctionTerm(int level, int version) {
		super(level, version);
		addNamespace(QualConstant.namespaceURI);
	}
	
	/**
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public FunctionTerm(ASTNode math, int level, int version) {
		super(math, level, version);
		addNamespace(QualConstant.namespaceURI);
	}

	/**
	 * Creates a FunctionTerm instance from a given FunctionTerm.
	 * 
	 * @param ft an <code>FunctionTerm</code> object to clone
	 */
	public FunctionTerm(FunctionTerm ft) {
		super(ft);

		setDefaultTerm(ft.isDefaultTerm());
		if (ft.isSetResultLevel()) {
			setResultLevel(ft.getResultLevel());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	public FunctionTerm clone() {
	  return new FunctionTerm(this);
	}

	/**
	 * Return false, resultLevel is not mandatory.
	 * 
	 * @return false
	 */
	public boolean isResultLevelMandatory(){
	  return false;
	}
	
	/**
	 * Returns true if resultLevel is set.
	 * 
	 * @return true if resultLevel is set.
	 */
	public boolean isSetResultLevel(){
	  return this.resultLevel!= null;
	}
	
	/**
	 * Returns the resultLevel if it is set, otherwise throw a {@link PropertyUndefinedError}
	 * Exception.
	 * 
	 * @return the resultLevel if it is set.
	 * @throws PropertyUndefinedError
	 */
	public int getResultLevel() {
		if (isSetResultLevel()) {
			return resultLevel.intValue();
		} else {
			throw new PropertyUndefinedError(QualConstant.resultLevel, this);
		}
	}


  /**
   * Sets the resultLevel.
   * 
   * @param resultLevel
   *        the resultLevel to set
   */
  public void setResultLevel(int resultLevel) {
    Integer oldResultLevel = this.resultLevel;
    this.resultLevel = resultLevel;
    firePropertyChange(QualConstant.resultLevel, oldResultLevel, this.resultLevel);
  }


  /**
   * Unsets the resultLevel.
   * 
   * @return true is the resultLevel was set beforehand.
   */
  public boolean unsetResultLevel() {
	  if (isSetResultLevel()) {
		  Integer oldResultLevel = this.resultLevel;
		  this.resultLevel = null;
		  firePropertyChange(QualConstant.resultLevel, oldResultLevel, this.resultLevel);
		  return true;
	  } else {
		  return false;
	  }
  }

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			FunctionTerm ft = (FunctionTerm) object;
			equals &= ft.isDefaultTerm() == isDefaultTerm();
			equals &= ft.isSetResultLevel() == isSetResultLevel();
			if (equals && isSetResultLevel()) {
				equals &= (ft.getResultLevel() == getResultLevel());
			}
		}
		return equals;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 953;
		int hashCode = super.hashCode();
		if (isDefaultTerm()) {
			hashCode *= 2;
		}
		if (isSetResultLevel()) {
			hashCode += prime * resultLevel.hashCode();
		}
		return hashCode;
	}
	

	/**
	 * Returns  true if it is a defaultTerm.
	 * 
	 * @return true if it is a defaultTerm.
	 */
	public boolean isDefaultTerm() {
		return defaultTerm;
	}

	/**
	 * Sets defaultTerm.
	 * 
	 * @param defaultTerm the defaultTerm to set
	 */
	public void setDefaultTerm(boolean defaultTerm) {
		this.defaultTerm = defaultTerm;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	@Override
	public String toString() {
		if (isDefaultTerm()) {
			return "defaultTerm";
		}
		return super.toString();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(QualConstant.resultLevel)) {
				setResultLevel(StringTools.parseSBMLInt(value));
			} else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetResultLevel()) {
			attributes.put(QualConstant.shortLabel + ":"+QualConstant.resultLevel, Integer.toString(getResultLevel()));
		}	  
		return attributes;
	}
	
}
