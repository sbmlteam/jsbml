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
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.qual;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date $Date$
 */
public class Transition extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long    serialVersionUID = 8343744362262634585L;
  /**
   * 
   */
  private ListOf<FunctionTerm> listOfFunctionTerms;
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
  public Transition() {
    super();
    initDefaults();
  }
  
  /**
   * 
   * @param level
   * @param version
   */
  public Transition(int level, int version) {
    this(null, null, level, version);
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
   * @param functionTerm
   *            {@code true} if the {@link FunctionTerm} was added to the
   *            list or {@code false} if adding the new
   *            {@link FunctionTerm} was not successful.
   */
  public boolean addFunctionTerm(FunctionTerm functionTerm) {
    if (getListOfFunctionTerms().add(functionTerm)) {
      return true;
    }
    return false;
  }
  
  /**
   * @param input
   *        the input to add
   */
  public boolean addInput(Input input) {
      return getListOfInputs().add(input);
  }


  /**
   * @param output
   *        the output to add
   */
  public boolean addOutput(Output output) {
      return getListOfOutputs().add(output);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  public Transition clone() {
    return new Transition(this);
  }


  /**
   * Creates a new instance of {@link FunctionTerm} and adds it to this
   * {@link Transition}'s {@link #listOfFunctionTerms}. In case that this
   * operation fails, {@code null} will be returned. In case of success,
   * this method will return the newly created {@link FunctionTerm}.
   * 
   * @return the newly created {@link FunctionTerm} or {@code null} if
   *         adding the new {@link FunctionTerm} was not successful.
   * @see #createFunctionTerm(ASTNode)
   */
  public FunctionTerm createFunctionTerm() {
	  return createFunctionTerm(null);
  }


  /**
   * Creates a new instance of {@link FunctionTerm} with the given
   * mathematical expression (encoded in an {@link ASTNode}) and adds it to
   * this {@link Transition}'s {@link #listOfFunctionTerms}. In case that this
   * operation fails, {@code null} will be returned. In case of success,
   * this method will return the newly created {@link FunctionTerm}.
   * 
   * @param math
   *            The mathematical expression for the new {@link FunctionTerm}.
   * @return newly created {@link FunctionTerm} or {@code null} if adding
   *         the new {@link FunctionTerm} was not successful.
   */
  public FunctionTerm createFunctionTerm(ASTNode math) {
	  FunctionTerm ft = new FunctionTerm(math, getLevel(), getVersion());
	  return addFunctionTerm(ft) ? ft : null;
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
    return createInput(id, qualitativeSpecies.getId(), transitionEffect);
  }


  /**
   * 
   * @param id
   * @param input_species_id
   * @param transitionEffect
   * @return
   */
  public Input createInput(String id, String input_species_id,
    InputTransitionEffect transitionEffect) {
    Input input = createInput(id);
    input.setQualitativeSpecies(input_species_id);
    input.setTransitionEffect(transitionEffect);
    return input;
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
    return createOutput(id, qualitativeSpecies.getId(), transitionEffect);
  }


  /**
   * 
   * @param id
   * @param output_species_id
   * @param transitionEffect
   * @return
   */
  public Output createOutput(String id, String output_species_id,
    OutputTransitionEffect transitionEffect) {
    Output Output = createOutput(id);
    Output.setQualitativeSpecies(output_species_id);
    Output.setTransitionEffect(transitionEffect);
    return Output;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    return equals;
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
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}",
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
   * 
   * @return
   */
  public int getFunctionTermCount() {
	  return isSetListOfFunctionTerms() ? getListOfFunctionTerms().size() : 0;
  }

  /**
   * 
   * @return
   */
  public int getInputCount() {
	  return isSetListOfInputs() ? getListOfInputs().size() : 0;
  }

  /**
   * @return the listOfFunctionTerms
   */
  public ListOf<FunctionTerm> getListOfFunctionTerms() {
    if (!isSetListOfFunctionTerms()) {
      listOfFunctionTerms = new ListOf<FunctionTerm>(getLevel(), getVersion());
      listOfFunctionTerms.setSBaseListType(ListOf.Type.other);
      listOfFunctionTerms.setNamespace(QualConstants.namespaceURI);
      registerChild(listOfFunctionTerms);
    }
    return listOfFunctionTerms;
  }

  /**
   * @return the listOfInputs
   */
  public ListOf<Input> getListOfInputs() {
    if (!isSetListOfInputs()) {
      listOfInputs = new ListOf<Input>(getLevel(), getVersion());
      listOfInputs.setSBaseListType(ListOf.Type.other);
      listOfInputs.setNamespace(QualConstants.namespaceURI);
      registerChild(listOfInputs);
    }
    return listOfInputs;
  }
  
  /**
   * @return the listOfOutputs
   */
  public ListOf<Output> getListOfOutputs() {
    if (!isSetListOfOutputs()) {
      listOfOutputs = new ListOf<Output>(getLevel(), getVersion());
      listOfOutputs.setSBaseListType(ListOf.Type.other);
      listOfOutputs.setNamespace(QualConstants.namespaceURI);
      registerChild(listOfOutputs);
    }
    return listOfOutputs;
  }

  /**
   * 
   * @return
   */
  public int getOutputCount() {
	  return isSetListOfOutputs() ? getListOfOutputs().size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = super.hashCode();
    return hashCode;
  }


  /**
   * 
   */
  public void initDefaults() {
    setNamespace(QualConstants.namespaceURI);
    listOfFunctionTerms = null;
    listOfInputs = null;
    listOfOutputs = null;
  }

  /**
   * @return false
   */
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * @return {@code false}
   */
  public boolean isListOfFunctionTermsMandatory() {
    return false;
  }

  /**
   * @return {@code false}
   */
  public boolean isListOfInputsMandatory() {
    return false;
  }

  /**
   * @return {@code true}
   */
  public boolean isListOfOutputsMandatory() {
    return true;
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
   * @return
   */
  public boolean isSetListOfInputs() {
    return listOfInputs != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetListOfOutputs() {
    return listOfOutputs != null;
  }

  /**
   * @return {@code false}
   */
  public boolean isSignMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
		  String value) 
  {
	  boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
	  return isAttributeRead;
	  
  }

  /**
   * @param functionTerm
   *        to remove from the listOfFunctionTerms
   * @return {@code true} if the operation was successful
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
   * @param input
   *        to remove from the listOfInputs
   * @return {@code true} if the operation was successful
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
   * @param output
   *        to remove from the listOfOutputs
   * @return {@code true} if the operation was successful
   */
  public boolean removeOutput(Output output) {
    if (isSetListOfOutputs()) {
      return listOfOutputs.remove(output);
    }
    return false;
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
   * @param loi
   */
  public void setListOfInputs(ListOf<Input> loi) {
    unsetListOfInputs();
    this.listOfInputs = loi;
    registerChild(this.listOfInputs);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();
	  
	  if (isSetId()) {
		  attributes.remove("id");
		  attributes.put(QualConstants.shortLabel+ ":id", getId());
	  }
	  if (isSetName()) {
		  attributes.remove("name");
		  attributes.put(QualConstants.shortLabel+ ":name", getName());
	  }
	  
	  return attributes;
  }

}
