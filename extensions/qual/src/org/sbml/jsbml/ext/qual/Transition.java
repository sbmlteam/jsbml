/*
 * $Id$
 * $URL:
 * https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml
 * /KineticLaw.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.qual;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class Transition extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long    serialVersionUID = 8343744362262634585L;
  private TemporisationType    temporisationType;
  private Sign                 sign;
  private ListOf<Input>        listOfInputs;
  private ListOf<Output>       listOfOutputs;
  private ListOf<FunctionTerm> listOfFunctionTerms;


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  public AbstractSBase clone() {
    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
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
    if (isSetListOfInputs()) {
      if (pos == index) {
        return getListOfInputs();
      }
      pos++;
    }
    if (isSetListOfOutputs()) {
      if (pos == index) {
        return getListOfOutputs();
      }
      pos++;
    }
    if (isSetListOfFunctionTerms()) {
      if (pos == index) {
        return getListOfFunctionTerms();
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
    int count = super.getChildCount();
    if (isSetListOfInputs()) {
      count++;
    }
    if (isSetListOfOutputs()) {
      count++;
    }
    if (isSetListOfFunctionTerms()) {
      count++;
    }
    return count;
  }


  /**
   * @return false
   */
  public boolean isIdMandatory() {
    return false;
  }


  /**
   * @return false
   */
  public boolean isTemporisationTypeMandatory() {
    return false;
  }


  public boolean isSetTemporisationType() {
    return temporisationType != null;
  }


  /**
   * @return the temporisationType
   */
  public TemporisationType getTemporisationType() {
    if (isSetTemporisationType()) {
      return temporisationType;
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.temporisationType, this);
    }
  }


  /**
   * @param temporisationType
   *        the temporisationType to set
   */
  public void setTemporisationType(TemporisationType temporisationType) {
    TemporisationType oldType = this.temporisationType;
    this.temporisationType = temporisationType;
    firePropertyChange(QualChangeEvent.temporisationType, oldType,
      this.temporisationType);
  }


  public boolean unsetTemporisationType() {
    if (isSetTemporisationType()) {
      TemporisationType oldType = this.temporisationType;
      this.temporisationType = null;
      firePropertyChange(QualChangeEvent.initialLevel, oldType,
        this.temporisationType);
      return true;
    }
    return false;
  }


  /**
   * @return false
   */
  public boolean isSignMandatory() {
    return false;
  }


  public boolean isSetSign() {
    return sign != null;
  }


  /**
   * @return the sign
   */
  public Sign getSign() {
    if (isSetSign()) {
      return sign;
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.sign, this);
    }
  }


  /**
   * @param sign
   *        the sign to set
   */
  public void setSign(Sign sign) {
    Sign oldSign = this.sign;
    this.sign = sign;
    firePropertyChange(QualChangeEvent.sign, oldSign, this.sign);
  }


  /**
   * @return true if unset the sign attribute was successful
   */
  public boolean unsetSign() {
    if (isSetSign()) {
      setSign(null);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return true
   */
  public boolean isListOfOutputsMandatory() {
    return true;
  }


  public boolean isSetListOfOutputs() {
    return listOfOutputs != null;
  }


  public void setListOfOutputs(ListOf<Output> loo) {
    unsetListOfOutputs();
    this.listOfOutputs = loo;
    setThisAsParentSBMLObject(this.listOfOutputs);
  }


  public boolean unsetListOfOutputs() {
    if (isSetListOfOutputs()) {
      ListOf<Output> oldLoo = this.listOfOutputs;
      this.listOfOutputs = null;
      oldLoo.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @return the listOfOutputs
   */
  public ListOf<Output> getListOfOutputs() {
    if (!isSetListOfOutputs()) {
      // TODO: initialize the ListOf correctly
      listOfOutputs = new ListOf<Output>();
    }
    return listOfOutputs;
  }


  /**
   * @param listOfOutputs
   *        the listOfOutputs to set
   */
  public boolean addOutput(Output output) {
    if (getListOfOutputs().add(output)) {
      return true;
    }
    return false;
  }


  /**
   * @param input
   *        to remove from the listOfOutputs
   * @return true if the operation was successful
   */
  public boolean removeOutput(Output output) {
    if (isSetListOfOutputs()) {
      return listOfOutputs.remove(output);
    }
    return false;
  }


  /**
   * @param position
   *        in the listOfOutputs which should be deleted
   * @return true if the operation was successful
   */
  public void removeOutput(int i) {
    if (!isSetListOfOutputs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    listOfOutputs.remove(i);
  }


  /**
   * @return false
   */
  public boolean isListOfFunctionTermsMandatory() {
    return false;
  }


  public boolean isSetListOfFunctionTerms() {
    return listOfFunctionTerms != null;
  }


  public void setListOfFunctionTerms(ListOf<FunctionTerm> loft) {
    unsetListOfFunctionTerms();
    this.listOfFunctionTerms = loft;
    setThisAsParentSBMLObject(this.listOfFunctionTerms);
  }


  public boolean unsetListOfFunctionTerms() {
    if (isSetListOfFunctionTerms()) {
      ListOf<FunctionTerm> oldLoft = this.listOfFunctionTerms;
      this.listOfFunctionTerms = null;
      oldLoft.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @return the listOfFunctionTerms
   */
  public ListOf<FunctionTerm> getListOfFunctionTerms() {
    if (!isSetListOfFunctionTerms()) {
      // TODO: initialize the ListOf correctly
      listOfFunctionTerms = new ListOf<FunctionTerm>();
    }
    return listOfFunctionTerms;
  }


  /**
   * @param listOfFunctionTerms
   *        the listOfFunctionTerms to set
   */
  public boolean addFunctionTerm(FunctionTerm functionTerm) {
    if (getListOfFunctionTerms().add(functionTerm)) {
      return true;
    }
    return false;
  }


  /**
   * @param input
   *        to remove from the listOfFunctionTerms
   * @return true if the operation was successful
   */
  public boolean removeFunctionTerm(FunctionTerm functionTerm) {
    if (isSetListOfFunctionTerms()) {
      return listOfFunctionTerms.remove(functionTerm);
    }
    return false;
  }


  /**
   * @param position
   *        in the listOfFunctionTerms which should be deleted
   * @return true if the operation was successful
   */
  public void removeFunctionTerm(int i) {
    if (!isSetListOfFunctionTerms()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    listOfFunctionTerms.remove(i);
  }


  /**
   * @return false
   */
  public boolean isListOfInputsMandatory() {
    return false;
  }


  public boolean isSetListOfInputs() {
    return listOfInputs != null;
  }


  public void setListOfInputs(ListOf<Input> loi) {
    unsetListOfInputs();
    this.listOfInputs = loi;
    setThisAsParentSBMLObject(this.listOfInputs);
  }


  public boolean unsetListOfInputs() {
    if (isSetListOfInputs()) {
      ListOf<Input> oldLoi = this.listOfInputs;
      this.listOfInputs = null;
      oldLoi.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @return the listOfInputs
   */
  public ListOf<Input> getListOfInputs() {
    if (!isSetListOfInputs()) {
      // TODO: initialize the ListOf correctly
      listOfInputs = new ListOf<Input>();
    }
    return listOfInputs;
  }


  /**
   * @param listOfInputs
   *        the listOfInputs to set
   */
  public boolean addInput(Input input) {
    if (getListOfInputs().add(input)) {
      return true;
    }
    return false;
  }


  /**
   * @param input
   *        to remove from the listOfInputs
   * @return true if the operation was successful
   */
  public boolean removeInput(Input input) {
    if (isSetListOfInputs()) {
      return listOfInputs.remove(input);
    }
    return false;
  }


  /**
   * @param position
   *        in the listOfInputs which should be deleted
   * @return true if the operation was successful
   */
  public void removeInput(int i) {
    if (!isSetListOfInputs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    listOfInputs.remove(i);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Transition t = (Transition) object;
      equals &= t.isSetTemporisationType() == isSetTemporisationType();
      if (equals && isSetTemporisationType()) {
        equals &= (t.getTemporisationType().equals(getTemporisationType()));
      }
      equals &= t.isSetSign() == isSetSign();
      if (equals && isSetSign()) {
        equals &= (t.getSign() == getSign());
      }
    }
    return equals;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 977;
    int hashCode = super.hashCode();
    if (isSetTemporisationType()) {
      hashCode += prime * getTemporisationType().hashCode();
    }
    if (isSetSign()) {
      hashCode += prime * getSign().hashCode();
    }
    return hashCode;
  }

}
