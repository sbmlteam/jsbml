/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * Represents the FunctionTerm element from the SBML Qualitative models package,
 * see http://sbml.org/Community/Wiki/SBML_Level_3_Proposals/Qualitative_Models.
 * 
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
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
	private String resultSymbol;
    /**
     * 
     */
	private Double temporisationValue;
	/**
	 * 
	 */
	private TemporisationMath temporisationMath;
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

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	public AbstractMathContainer clone() {
		return null;
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

  /**
   * Returns true.
   * 
   * @return true
   */
  public boolean isResultSymbolMandatory(){
    return true;
  }
  
  /**
   * Returns true if resultSymbol is set.
   * 
   * @return true if resultSymbol is set.
   */
  public boolean isSetResultSymbol() {
    return resultSymbol != null;
  }


  /**
   * Returns the resultSymbol.
   * 
   * @return the resultSymbol
   */
  public String getResultSymbol() {
    return isSetResultSymbol() ? resultSymbol : "";
  }


  /**
   * Sets the resultSymbol.
   * 
   * @param resultSymbol
   *        the resultSymbol to set
   */
  public void setResultSymbol(String resultSymbol) {
    String oldresultSymbol = this.resultSymbol;
    if ((resultSymbol == null) || (resultSymbol.length() == 0)) {
      this.resultSymbol = null;
    } else {
      this.resultSymbol = resultSymbol;
    }
    firePropertyChange(QualConstant.resultSymbol, oldresultSymbol,
      this.resultSymbol);
  }


  /**
   * Unset the resultSymbol.
   * 
   * @return true if the unset of the resultSymbol attribute was successful
   */
  public boolean unsetResultSymbol() {
    if (resultSymbol != null) {
      setResultSymbol(null);
      return true;
    }
    return false;
  }

  /**
   * Returns false, temporisationValue is not mandatory.
   * 
   * @return false
   */
  public boolean isTemporisationValueMandatory(){
    return false;
  }
  
  /**
   * Returns true if the temporisationValue is set.
   * 
   * @return true if the temporisationValue is set
   */
  public boolean isSetTemporisationValue(){
	  return this.temporisationValue!= null;
  }

  /**
   * Returns the temporisationValue.
   * 
   * @return the temporisationValue.
   */
  public double getTemporisationValue() {
    if (isSetTemporisationValue()) {
      return temporisationValue.doubleValue();
    } else {
      throw new PropertyUndefinedError(QualConstant.temporisationValue, this);
    }
  }


  /**
   * Sets the temporisationValue.
   * 
   * @param tempValue
   *        the temporisationValue to set
   */
  public void setTemporisationValue(double tempValue) {
    Double oldTempValue = this.temporisationValue;
    this.temporisationValue = tempValue;
    firePropertyChange(QualConstant.temporisationValue, oldTempValue, this.temporisationValue);
  }


  /**
   * Unsets the temporisationValue.
   * 
   * @return true if the temporisationValue is unset.
   */
  public boolean unsetTemporisationValue() {
	  if (isSetTemporisationValue()) {
		  Double oldTempValue = this.temporisationValue;
		  this.temporisationValue = null;
		  firePropertyChange(QualConstant.temporisationValue, oldTempValue, this.temporisationValue);
		  return true;
	  } else {
		  return false;
	  }
  }
	
  /**
   * Returns false, temporisationMath is not mandatory.
   * 
   * @return false
   */
  public boolean isTemporisationMathMandatory(){
    return false;
  }
  
  /**
   * Returns true is temporisationMath is set.
   * 
   * @return true is temporisationMath is set.
   */
  public boolean isSetTemporisationMath(){
	  return this.temporisationMath!=null;
  }

  /**
   * Returns the temporisationMath. 
   * 
   * @return the temporisationMath
   */
  public TemporisationMath getTemporisationMath() {
	  if(isSetTemporisationMath()){
		  return temporisationMath;
	  } else{
		  throw new PropertyUndefinedError(QualConstant.temporisationMath, this);
	  }
  }

	/**
	 * Sets the temporisationMath 
	 * 
	 * @param temporisationMath the temporisationMath to set
	 */
	public void setTemporisationMath(TemporisationMath temporisationMath) {
		TemporisationMath oldTM = this.temporisationMath;
	  this.temporisationMath = temporisationMath;
	  firePropertyChange(QualConstant.temporisationMath, oldTM, this.temporisationMath);
	  registerChild(temporisationMath);
	}

	/**
	 * Unset the temporisationMath.
	 *  
	 * @return true if the unset of the attribute temporisationMath was successful
	 */
	public boolean unsetTemporisationMath(){
	  if(isSetTemporisationMath()){
	    setTemporisationMath(null);
	    return true;
	  } else {
	    return false;
	  }
	  
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}

		int count = super.getChildCount();
		int pos = 0;

		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}

		if (isSetTemporisationMath()) {
			if (pos == index) {
				return temporisationMath;
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();;

		if (isSetTemporisationMath()) {
			count++;
		}

		return count;
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
			equals &= ft.isSetResultSymbol() == isSetResultSymbol();
			if (equals && isSetResultSymbol()) {
				equals &= (ft.getResultSymbol().equals(getResultSymbol()));
			}
			equals &= ft.isSetTemporisationValue() == isSetTemporisationValue();
			if (equals && isSetTemporisationValue()) {
				equals &= (ft.getTemporisationValue() == getTemporisationValue());
			}
			equals &= ft.isSetTemporisationMath() == isSetTemporisationMath();
			if (equals && isSetTemporisationMath()) {
				equals &= (ft.getTemporisationMath().equals(getTemporisationMath()));
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
		if (isSetResultSymbol()) {
			hashCode += prime * resultSymbol.hashCode();
		}
		if (isSetTemporisationValue()) {
			hashCode += prime * temporisationValue.hashCode();
		}
		if (isSetTemporisationMath()) {
			hashCode += prime * temporisationMath.hashCode();
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
			} else if (attributeName.equals(QualConstant.resultSymbol)) {
				setResultSymbol(value);
			} else if (attributeName.equals(QualConstant.temporisationValue)) {
				setTemporisationValue(StringTools.parseSBMLDouble(value));
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
		if (isSetResultSymbol()) {
			attributes.put(QualConstant.shortLabel + ":"+QualConstant.resultSymbol, getResultSymbol());
		}
		if (isSetTemporisationValue()) {
			attributes.put(QualConstant.shortLabel+ ":"+QualConstant.temporisationValue, Double.toString(getTemporisationValue()));
		}

		return attributes;
	}

	
}
