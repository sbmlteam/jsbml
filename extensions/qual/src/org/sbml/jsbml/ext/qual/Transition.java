/*
 * $Id$
 * $URL:
 * https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml
 * /KineticLaw.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date $Date$
 */
public class Transition extends AbstractNamedSBase implements UniqueNamedSBase{

  /**
   * Generated serial version identifier.
   */
  private static final long    serialVersionUID = 8343744362262634585L;
  /**
   * 
   */
  private TemporisationType    temporisationType;
  /**
   * 
   */
  private ListOf<Input>        listOfInputs;
  /**
   * 
   */
  private ListOf<Output>       listOfOutputs;
  /**
   * 
   */
  private ListOf<FunctionTerm> listOfFunctionTerms;

  /**
   * 
   */
  public Transition() {
    super();
    initDefaults();
  }
  
  /**
   * 
   * @param id
   */
  public Transition(String id) {
    super(id);
    initDefaults();
  }
  
  /**
   * 
   * @param level
   * @param version
   */
  public Transition(int level, int version){
    this(null, null, level, version);
  }
  
  /**
   * @param id
   * @param level
   * @param version
   */
  public Transition(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Transition(String id, String name, int level, int version) {
    super(id, name, level, version);
    // TODO: replace level/version check with call to helper method
    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  public Transition(Transition t) {
    super(t);
    
    temporisationType = t.temporisationType;
    if (t.isSetListOfFunctionTerms()) {
      listOfFunctionTerms = t.listOfFunctionTerms.clone();
    }
    if (t.isSetListOfInputs()) {
      listOfInputs = t.listOfInputs.clone();
    }
    if (t.isSetListOfOutputs()) {
      listOfOutputs = t.listOfOutputs.clone();
    }
  }
  
  /**
   * 
   */
  public void initDefaults() {
    addNamespace(QualConstant.namespaceURI);
    temporisationType = null;
    listOfFunctionTerms = null;
    listOfInputs = null;
    listOfOutputs = null;
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  public Transition clone() {
    return new Transition(this);
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


  /**
   * 
   * @return
   */
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
      throw new PropertyUndefinedError(QualConstant.temporisationType, this);
    }
  }


  /**
   * @param temporisationType
   *        the temporisationType to set
   */
  public void setTemporisationType(TemporisationType temporisationType) {
    TemporisationType oldType = this.temporisationType;
    this.temporisationType = temporisationType;
    firePropertyChange(QualConstant.temporisationType, oldType,
      this.temporisationType);
  }


  /**
   * 
   * @return
   */
  public boolean unsetTemporisationType() {
    if (isSetTemporisationType()) {
      TemporisationType oldType = this.temporisationType;
      this.temporisationType = null;
      firePropertyChange(QualConstant.initialLevel, oldType,
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

  /**
   * @return true
   */
  public boolean isListOfOutputsMandatory() {
    return true;
  }


  /**
   * 
   * @return
   */
  public boolean isSetListOfOutputs() {
    return listOfOutputs != null;
  }


  /**
   * 
   * @param loo
   */
  public void setListOfOutputs(ListOf<Output> loo) {
    unsetListOfOutputs();
    this.listOfOutputs = loo;
    registerChild(this.listOfOutputs);
  }


  /**
   * 
   * @return
   */
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
      listOfOutputs = new ListOf<Output>(getLevel(), getVersion());
      listOfOutputs.setSBaseListType(ListOf.Type.other);
      listOfOutputs.addNamespace(QualConstant.namespaceURI);
      registerChild(listOfOutputs);
    }
    return listOfOutputs;
  }


  /**
   * @param output
   *        the output to add
   */
  public boolean addOutput(Output output) {
      return getListOfOutputs().add(output);
  }


  /**
   * @param output
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
   * @param i position
   *        in the listOfOutputs which should be deleted
   * @throws IndexOutOfBoundsException if the index is not valid
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


  /**
   * 
   * @return
   */
  public boolean isSetListOfFunctionTerms() {
    return listOfFunctionTerms != null;
  }


  /**
   * 
   * @param loft
   */
  public void setListOfFunctionTerms(ListOf<FunctionTerm> loft) {
    unsetListOfFunctionTerms();
    this.listOfFunctionTerms = loft;
    registerChild(this.listOfFunctionTerms);
  }


  /**
   * 
   * @return
   */
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
      listOfFunctionTerms = new ListOf<FunctionTerm>(getLevel(), getVersion());
      listOfFunctionTerms.setSBaseListType(ListOf.Type.other);
      listOfFunctionTerms.addNamespace(QualConstant.namespaceURI);
      registerChild(listOfFunctionTerms);
    }
    return listOfFunctionTerms;
  }

  /**
   * @param functionTerm
   *            <code>true</code> if the {@link FunctionTerm} was added to the
   *            list or <code>false</code> if adding the new
   *            {@link FunctionTerm} was not successful.
   */
  public boolean addFunctionTerm(FunctionTerm functionTerm) {
    if (getListOfFunctionTerms().add(functionTerm)) {
      return true;
    }
    return false;
  }

  /**
   * 
   * @return the newly created {@link FunctionTerm} or <code>null</code> if
   *         adding the new {@link FunctionTerm} was not successful.
   */
  public FunctionTerm createFunctionTerm() {
	  FunctionTerm ft = new FunctionTerm(getLevel(), getVersion());
	  if (addFunctionTerm(ft)) {
		  return ft;
	  }
	  return null;
  }

  /**
   * @param functionTerm
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
   * @param i position
   *        in the listOfFunctionTerms which should be deleted
   * @throws IndexOutOfBoundsException if the index is invalid.
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


  /**
   * 
   * @return
   */
  public boolean isSetListOfInputs() {
    return listOfInputs != null;
  }


  /**
   * 
   * @param loi
   */
  public void setListOfInputs(ListOf<Input> loi) {
    unsetListOfInputs();
    this.listOfInputs = loi;
    registerChild(this.listOfInputs);
  }


  /**
   * 
   * @return
   */
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
      listOfInputs = new ListOf<Input>(getLevel(), getVersion());
      listOfInputs.setSBaseListType(ListOf.Type.other);
      listOfInputs.addNamespace(QualConstant.namespaceURI);
      registerChild(listOfInputs);
    }
    return listOfInputs;
  }


  /**
   * @param input
   *        the input to add
   */
  public boolean addInput(Input input) {
      return getListOfInputs().add(input);
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
   * @param i position
   *        in the listOfInputs which should be deleted
   * @throws IndexOutOfBoundsException if the index is invalid.
   */
  public void removeInput(int i) {
    if (!isSetListOfInputs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    listOfInputs.remove(i);
  }


  /* (non-Javadoc)
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
    }
    return equals;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 977;
    int hashCode = super.hashCode();
    if (isSetTemporisationType()) {
      hashCode += prime * getTemporisationType().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
		  String value) 
  {
	  boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

	  if (!isAttributeRead) {
		  isAttributeRead = true;
		  
		  if (attributeName.equals(QualConstant.temporisationType)){
			  try {
				  setTemporisationType(TemporisationType.valueOf(value));
			  } catch (Exception e) {
				  throw new SBMLException("Could not recognized the value '" + value
						  + "' for the attribute " + QualConstant.temporisationType
						  + " on the 'transition' element.");
			  }
		  } else {
		    isAttributeRead = false;
		  }
	  }	  
	  
	  return isAttributeRead;
	  
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();
	  
	  if (isSetId()) {
		  attributes.remove("id");
		  attributes.put(QualConstant.shortLabel+ ":id", getId());
	  }
	  if (isSetName()) {
		  attributes.remove("name");
		  attributes.put(QualConstant.shortLabel+ ":name", getName());
	  }
	  if (isSetTemporisationType()) {
		  attributes.put(QualConstant.shortLabel+ ":"+QualConstant.temporisationType, getTemporisationType().toString());
	  }

	  
	  return attributes;
  }


  /**
   * 
   * @return
   */
  public Output createOutput() {
    return createOutput(null);
  }
  
  /**
   * 
   * @param id
   * @return
   */
  public Output createOutput(String id) {
    Output output = new Output(id, getModel().getLevel(), getModel().getVersion());
    addOutput(output);
    return output;
  }
  
  /**
   * 
   * @param id
   * @param qualitativeSpecies
   * @param transitionEffect
   * @return
   */
  public Output createOutput(String id, QualitativeSpecies qualitativeSpecies,
      OutputTransitionEffect transitionEffect) {
    Output output = createOutput(id);
    output.setQualitativeSpecies(qualitativeSpecies.getId());
    output.setTransitionEffect(transitionEffect);
    return output;
  }

  /**
   * 
   * @return
   */
  public Input createInput() {
    return createInput(null);
  }
  
  /**
   * 
   * @param id
   * @return
   */
  public Input createInput(String id) {
    Input input = new Input(id, getModel().getLevel(), getModel().getVersion());
    addInput(input);
    return input;
  }
  
  /**
   * 
   * @param id
   * @param qualitativeSpecies
   * @param transitionEffect
   * @return
   */
  public Input createInput(String id, QualitativeSpecies qualitativeSpecies,
    InputTransitionEffect transitionEffect) {
    Input input = createInput(id);
    input.setQualitativeSpecies(qualitativeSpecies.getId());
    input.setTransitionEffect(transitionEffect);
    return input;
  }
  
}
