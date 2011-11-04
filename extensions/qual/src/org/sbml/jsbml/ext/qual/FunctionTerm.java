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
package org.sbml.jsbml.ext.qual;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.QualParser;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class FunctionTerm extends AbstractMathContainer {

	/**
   * Generated serial version identifier.
   */
	private static final long serialVersionUID = -3456373304133826017L;

	private Integer resultLevel;
	private String resultSymbol;

	private Double temporisationValue;
	private TemporisationMath temporisationMath;

	private boolean defaultTerm;


	public FunctionTerm() {
		super();
		addNamespace(QualParser.getNamespaceURI());
	}

	@Override
	public AbstractMathContainer clone() {
		return null;
	}

	/**
	 * 
	 * @return false
	 */
	public boolean isResultLevelMandatory(){
	  return false;
	}
	
	public boolean isSetResultLevel(){
	  return this.resultLevel!= null;
	}
	
	/**
   * @return the level
   */
  public int getResultLevel() {
    if (isSetResultLevel()) {
      return resultLevel.intValue();
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.resultLevel, this);
    }
  }


  /**
   * @param resultLevel
   *        the resultLevel to set
   */
  public void setResultLevel(int resultLevel) {
    Integer oldResultLevel = this.resultLevel;
    this.resultLevel = resultLevel;
    firePropertyChange(QualChangeEvent.resultLevel, oldResultLevel, this.resultLevel);
  }


  public boolean unsetResultLevel() {
    if (isSetResultLevel()) {
      Integer oldResultLevel = this.resultLevel;
      this.resultLevel = null;
      firePropertyChange(QualChangeEvent.resultLevel, oldResultLevel, this.resultLevel);
      return true;
    } else {
      return false;
    }
  }

  /**
   * 
   * @return true
   */
  public boolean isResultSymbolMandatory(){
    return true;
  }
  
  /**
   * @return if boundaryCondition attribute is set
   */
  public boolean isSetResultSymbol() {
    return resultSymbol != null;
  }


  /**
   * @return the resultSymbol
   */
  public String getResultSymbol() {
    return isSetResultSymbol() ? resultSymbol : "";
  }


  /**
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
    firePropertyChange(QualChangeEvent.resultSymbol, oldresultSymbol,
      this.resultSymbol);
  }


  /**
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
   * 
   * @return false
   */
  public boolean isTemporisationValueMandatory(){
    return false;
  }
  
  public boolean isSetTemporisationValue(){
    return this.temporisationValue!= null;
  }
  
  /**
   * @return the level
   */
  public double getTemporisationValue() {
    if (isSetTemporisationValue()) {
      return temporisationValue.doubleValue();
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.temporisationValue, this);
    }
  }


  /**
   * @param level
   *        the remporisationValue to set
   */
  public void setTemporisationValue(double tempValue) {
    Double oldTempValue = this.temporisationValue;
    this.temporisationValue = tempValue;
    firePropertyChange(QualChangeEvent.temporisationValue, oldTempValue, this.temporisationValue);
  }


  public boolean unsetTemporisationValue() {
    if (isSetResultLevel()) {
      Double oldTempValue = this.temporisationValue;
      this.temporisationValue = null;
      firePropertyChange(QualChangeEvent.temporisationValue, oldTempValue, this.temporisationValue);
      return true;
    } else {
      return false;
    }
  }
	
  /**
   * @return false
   */
  public boolean isTemporisationMathMandatory(){
    return false;
  }
  
  public boolean isSetTemporisationMath(){
    return this.temporisationMath!=null;
  }
  
  /**
	 * @return the temporisationMath
	 */
	public TemporisationMath getTemporisationMath() {
	  if(isSetTemporisationMath()){
	    return temporisationMath;
	  } else{
	    throw new PropertyUndefinedError(QualChangeEvent.temporisationMath, this);
	  }
	}

	/**
	 * @param temporisationMath the temporisationMath to set
	 */
	public void setTemporisationMath(TemporisationMath temporisationMath) {
		TemporisationMath oldTM = this.temporisationMath;
	  this.temporisationMath = temporisationMath;
	  firePropertyChange(QualChangeEvent.temporisationMath, oldTM, this.temporisationMath);
	  this.setThisAsParentSBMLObject(temporisationMath);
	}

	/**
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
	 * @return the defaultTerm
	 */
	public boolean isDefaultTerm() {
		return defaultTerm;
	}

	/**
	 * @param defaultTerm the defaultTerm to set
	 */
	public void setDefaultTerm(boolean defaultTerm) {
		this.defaultTerm = defaultTerm;
	}

	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {

		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals(QualChangeEvent.resultLevel)) {
				setResultLevel(StringTools.parseSBMLInt(value));
			} else if (attributeName.equals(QualChangeEvent.resultSymbol)) {
				setResultSymbol(value);
			} else if (attributeName.equals(QualChangeEvent.temporisationValue)) {
				setTemporisationValue(StringTools.parseSBMLDouble(value));
			} else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}

	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetResultLevel()) {
			attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.resultLevel, Integer.toString(getResultLevel()));
		}	  
		if (isSetResultSymbol()) {
			attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.resultSymbol, getResultSymbol());
		}
		if (isSetTemporisationValue()) {
			attributes.put(QualParser.shortLabel+ ":"+QualChangeEvent.temporisationValue, Double.toString(getTemporisationValue()));
		}

		return attributes;
	}

	
}
