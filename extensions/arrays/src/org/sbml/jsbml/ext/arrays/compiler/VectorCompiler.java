/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.arrays.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Quantity;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;
import org.sbml.jsbml.util.Maths;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

/**
 * @author Leandro Watanabe
 * @since 1.0
 */
//TODO: need to check for errors
//TODO: need to check if its name
//TODO: SBMLException error id
//TODO: need to test functions. might have warnings
public class VectorCompiler implements ASTNodeCompiler {

  /**
   * 
   */
  private final ASTNodeValue dummy = new ASTNodeValue("dummy", null);
  /**
   * 
   */
  private final Model model;
  /**
   * 
   */
  private ASTNode node;
  /**
   * 
   */
  private Map<String, ASTNode> idToVector;
  /**
   * 
   */
  private boolean isSetIdToVector;
  /**
   * 
   */
  private boolean useId;
  /**
   * 
   */
  private NamedSBase sbase;

  /**
   * @param model
   */
  public VectorCompiler(Model model) {
    this.model = model;
    node = new ASTNode();
    useId = false;
    isSetIdToVector = false;
  }

  /**
   * @param model
   * @param useId
   */
  public VectorCompiler(Model model, boolean useId) {
    this.model = model;
    node = new ASTNode();
    this.useId = useId;
    isSetIdToVector = false;

  }

  /**
   * @param model
   * @param sbase
   * @param useId
   */
  public VectorCompiler(Model model, NamedSBase sbase, boolean useId) {
    this.model = model;
    node = new ASTNode();
    this.useId = useId;
    this.sbase = sbase;
    isSetIdToVector = false;
  }



  /**
   * @param model
   * @param useId
   * @param idToVector
   */
  public VectorCompiler(Model model, boolean useId, Map<String, ASTNode> idToVector) {
    this.model = model;
    node = new ASTNode();
    this.useId = useId;
    this.idToVector = idToVector;
    if (idToVector == null) {
      isSetIdToVector = false;
    } else {
      isSetIdToVector = true;
    }
  }

  /**
   * @return
   */
  public ASTNode getNode() {
    return node;
  }

  /**
   * @param node
   */
  public void setNode(ASTNode node) {
    this.node = node;
  }

  /**
   * @param idToVector
   */
  public void setIdToVector(Map<String, ASTNode> idToVector) {
    this.idToVector = idToVector;
    if (idToVector == null) {
      isSetIdToVector = false;
    } else {
      isSetIdToVector = true;
    }
  }

  /**
   * 
   */
  public void clearIdToVector() {
    idToVector.clear();
    isSetIdToVector = false;
  }

  /**
   * @return
   */
  public boolean isSetIdToVector() {
    if (idToVector != null && isSetIdToVector) {
      return true;
    } else {
      return false;
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try{
        absRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ABS, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.abs(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * This function calculates the abs value of a vector element-wise.
   * 
   * @param value
   * @throws SBMLException
   */
  private void absRecursive(ASTNode value) throws SBMLException{
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        absRecursive(value.getChild(i));
      } else if (useId){
        ASTNode absValue = new ASTNode(ASTNode.Type.FUNCTION_ABS, child.getParentSBMLObject());
        absValue.addChild(child.clone());
        value.replaceChild(i, absValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.abs(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> values) throws SBMLException {

    List<ASTNode> vectors = new ArrayList<ASTNode>();
    List<ASTNode> scalars = new ArrayList<ASTNode>();
    List<ASTNode> ids = new ArrayList<ASTNode>();
    boolean success = getScalarsAndVectors(values, vectors, scalars, ids);

    if (!success) {
      unknownValue();
      return dummy;
    }
    double sumScalar;
    boolean isSetSumScalar = false;
    boolean hasIds = false;

    if (scalars.size() > 0) {
      isSetSumScalar = true;
    }
    if (ids.size() > 0) {
      hasIds = true;
    }

    boolean scalarResult = true;
    for (ASTNode node : scalars) {
      scalarResult &= node.getReal() == 1 ? true : false;
    }
    sumScalar = scalarResult ? 1 : 0;
    ASTNode out = new ASTNode(sumScalar);
    if (!vectors.isEmpty()) {
      out = vectors.get(0).clone();
      try {
        andRecursive(vectors, out);
      }
      catch(IndexOutOfBoundsException e) {
        unknownValue();
        return dummy;
      }
      if (isSetSumScalar) {
        scalarVectorAnd(out, new ASTNode(sumScalar));
      }
      if (hasIds) {
        for (ASTNode id : ids) {
          scalarVectorAnd(out, id);
        }
      }
    }
    else {
      if (hasIds) {
        ASTNode result = new ASTNode(ASTNode.Type.LOGICAL_AND, out.getParentSBMLObject());
        if (!ids.isEmpty()) {
          for (int i = 0; i < ids.size(); ++i) {
            result.addChild(ids.get(i).clone());
          }
        }
        out = result;
      }
      if (isSetSumScalar) {
        out.addChild(new ASTNode(sumScalar));
      }
    }

    setNode(out);
    return dummy;
  }

  /**
   * 
   * @param values
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void andRecursive(List<ASTNode> values, ASTNode node) throws IndexOutOfBoundsException{
    if (!node.isVector()) {
      boolean result = true;
      boolean isResultSet = false;
      node.setType(ASTNode.Type.LOGICAL_AND);
      node.getChildren().clear();
      if (values.size() > 0) {
        for (int i = 0; i < values.size(); ++i) {
          ASTNode value = values.get(i);
          if (value.isNumber()) {
            if (!isResultSet) {
              result = value.getInteger() == 1;
              isResultSet = true;
            } else {
              result &= value.getInteger() == 1 ? true : false;
            }
          } else if (useId) {
            node.addChild(values.get(i).clone());
          } else {
            throw new SBMLException();
          }
        }
        if (isResultSet) {
          node.addChild(new ASTNode(result ? 1 : 0));
        }
        return;
      }
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      List<ASTNode> nodes = new ArrayList<ASTNode>();
      for (ASTNode value : values) {
        value.compile(this);
        ASTNode result = getNode();
        if (result.isVector()) {
          nodes.add(result.getChild(i));
        }
      }
      andRecursive(nodes, node.getChild(i));
    }

  }

  /**
   * 
   * @param vector
   * @param scalar
   */
  private void scalarVectorAnd(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorAnd(child, scalar);
      }
      else if (useId) {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.LOGICAL_AND, vector.getChild(i).getParentSBMLObject());
        nodeValue.addChild(result.clone());
        nodeValue.addChild(scalar.clone());
        vector.replaceChild(i, nodeValue);
      }
      else if (result.isNumber()) {
        boolean resBool = result.getReal() == 1 ? true : false;
        boolean scalBool = scalar.getReal() == 1 ? true : false;
        vector.getChild(i).setValue(resBool & scalBool ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arccosRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCOS, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.acos(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * This function calculates the arcccos value of a vector element-wise.
   * 
   * @param value
   * @throws SBMLException
   */
  private void arccosRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arccosRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arccosValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCOS, child.getParentSBMLObject());
        arccosValue.addChild(child.clone());
        value.replaceChild(i, arccosValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.acos(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccosh(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arccoshRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        compiled.setName("arccosh(" + compiled.getName() + ")");
      }
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arccosh(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   * @throws SBMLException
   */
  private void arccoshRecursive(ASTNode value) throws SBMLException {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arccoshRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arccoshValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCOSH, child.getParentSBMLObject());
        arccoshValue.addChild(child.clone());
        value.replaceChild(i, arccoshValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arccosh(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccot(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arccotRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        compiled.setName("arccot(" + compiled.getName() + ")");
      }
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arccot(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arccotRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arccotRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arccotValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCOT, child.getParentSBMLObject());
        arccotValue.addChild(child.clone());
        value.replaceChild(i, arccotValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arccot(child.getReal()));
      }  else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccoth(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arccothRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCOTH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arccoth(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arccothRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arccothRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arccothValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCOTH, child.getParentSBMLObject());
        arccothValue.addChild(child.clone());
        value.replaceChild(i, arccothValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arccoth(child.getReal()));
      }  else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsc(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arccscRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCSC, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arccsc(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arccscRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arccscRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arccscValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCSC, child.getParentSBMLObject());
        arccscValue.addChild(child.clone());
        value.replaceChild(i, arccscValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arccsc(child.getReal()));
      }  else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsch(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arccschRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCSCH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arccsch(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arccschRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arccschRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arccschValue = new ASTNode(ASTNode.Type.FUNCTION_ARCCSCH, child.getParentSBMLObject());
        arccschValue.addChild(child.clone());
        value.replaceChild(i, arccschValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arccsch(child.getReal()));
      } else{
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsec(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arcsecRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    } else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSEC, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    } else if (compiled.isNumber()) {
      compiled.setValue(Maths.arcsec(compiled.getReal()));
    } else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arcsecRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arcsecRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arcsecValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSEC, child.getParentSBMLObject());
        arcsecValue.addChild(child.clone());
        value.replaceChild(i, arcsecValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arcsec(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsech(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arcsechRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSECH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arcsech(compiled.getReal()));
    }

    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arcsechRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arcsechRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arcsechValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSECH, child.getParentSBMLObject());
        arcsechValue.addChild(child.clone());
        value.replaceChild(i, arcsechValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arcsech(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsin(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arcsinRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSIN, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.asin(compiled.getReal()));
    }

    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arcsinRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arcsinRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arcsinValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSIN, child.getParentSBMLObject());
        arcsinValue.addChild(child.clone());
        value.replaceChild(i, arcsinValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.asin(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsinh(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      arcsinhRecursive(compiled);
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSINH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arcsinh(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arcsinhRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arcsinhRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arcsinhValue = new ASTNode(ASTNode.Type.FUNCTION_ARCSINH, child.getParentSBMLObject());
        arcsinhValue.addChild(child.clone());
        value.replaceChild(i, arcsinhValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arcsinh(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctan(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arctanRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCTAN, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.atan(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arctanRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arctanRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arctanValue = new ASTNode(ASTNode.Type.FUNCTION_ARCTAN, child.getParentSBMLObject());
        arctanValue.addChild(child.clone());
        value.replaceChild(i, arctanValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.atan(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctanh(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        arctanhRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ARCTANH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.arctanh(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void arctanhRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        arctanhRecursive(value.getChild(i));
      } else if (useId){
        ASTNode arctanhValue = new ASTNode(ASTNode.Type.FUNCTION_ARCTANH, child.getParentSBMLObject());
        arctanhValue.addChild(child.clone());
        value.replaceChild(i, arctanhValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.arctanh(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ceiling(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        ceilingRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_CEILING, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.ceil(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void ceilingRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        ceilingRecursive(value.getChild(i));
      } else if (useId){
        ASTNode ceilValue = new ASTNode(ASTNode.Type.FUNCTION_CEILING, child.getParentSBMLObject());
        ceilValue.addChild(child.clone());
        value.replaceChild(i, ceilValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.ceil(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public ASTNodeValue compile(Compartment c) {
    if (useId) {
      transformNamedSBase(c);
    } else {
      transformSBase(c);
    }
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double mantissa, int exponent, String units) {
    ASTNode node = new ASTNode();
    node.setValue(mantissa, exponent);
    setNode(node);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double real, String units) {
    setNode(new ASTNode(real));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(int integer, String units) {
    setNode(new ASTNode(integer));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNodeValue compile(CallableSBase variable) throws SBMLException {
    if (useId) {
      transformNamedSBase(variable);
    }
    else {
      transformSBase(variable);
    }
    return dummy;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  public ASTNodeValue compile(String name) {

    if(isSetIdToVector() && idToVector.containsKey(name))
    {
      setNode(idToVector.get(name));
    }
    else
    {
      SBase sbase = model.findNamedSBase(name);
      if (sbase != null) {
        if (useId) {
          if (isSetIdToVector() && idToVector.containsKey(name)) {
            setNode(idToVector.get(name));
          } else {
            transformNamedSBase(sbase);
          }
        } else {
          transformSBase(sbase);
        }
      }
      else if (this.sbase != null && this.sbase.getId().equals(name)) {
        if (useId) {
          if (isSetIdToVector() && idToVector.containsKey(name)) {
            setNode(idToVector.get(name));
          } else {
            transformNamedSBase(this.sbase);
          }
        } else {
          transformSBase(this.sbase);
        }
      }
      else
      {
        if (useId) {
          setNode(new ASTNode(name));
        }
        else {
          unknownValue();
        }
      }
    }
    return dummy;
  }

  /**
   * @param sbase
   */
  private void transformNamedSBase(SBase sbase) {
    ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
    if (arraysPlugin == null || arraysPlugin.getDimensionCount() == 0) {
      if (sbase instanceof FunctionDefinition) {
        FunctionDefinition namedSBase = (FunctionDefinition) sbase;
        ASTNode func = new ASTNode(ASTNode.Type.FUNCTION);
        func.setName(namedSBase.getId());
        setNode(func);
      }
      else if (sbase instanceof NamedSBase) {
        NamedSBase namedSBase = (NamedSBase) sbase;
        setNode(new ASTNode(namedSBase.getId()));
      }
      else {
        unknownValue();
      }
    }
    else {
      if (sbase instanceof SpeciesReference) {
        SpeciesReference ref = (SpeciesReference) sbase;
        setNode(constructVector(arraysPlugin, ref));

      }
      else if (sbase instanceof NamedSBase) {
        NamedSBase namedSBase = (NamedSBase) sbase;
        setNode(constructVector(arraysPlugin, namedSBase));
      }
      else {
        unknownValue();
      }
    }
  }

  /**
   * @param arraysPlugin
   * @param sbase
   * @return
   */
  private ASTNode constructVector(ArraysSBasePlugin arraysPlugin, NamedSBase sbase) {
    String id = sbase.getId();
    Dimension dim = arraysPlugin.getDimensionByArrayDimension(0);
    double size = ArraysMath.getSize(model, dim);
    List<ASTNode> vector = new ArrayList<ASTNode>((int) size);
    for (int i = 0; i < size; ++i) {
      vector.add(new ASTNode("_" + i));
    }
    vector(vector);
    ASTNode vectorNode = getNode();
    for (int i = 1; i < arraysPlugin.getDimensionCount(); ++i) {
      dim = arraysPlugin.getDimensionByArrayDimension(i);
      size = ArraysMath.getSize(model, dim);
      vector = new ArrayList<ASTNode>((int) size);
      for (int j = 0; j < size; ++j) {
        ASTNode clone = vectorNode.clone();
        updateASTNodeName(clone, j);
        //TODO check if clone is unique
        vector.add(clone);
      }
      vector(vector);
      vectorNode = getNode();
    }
    updateASTNodeName(vectorNode, id);
    return vectorNode;
  }

  /**
   * @param arraysPlugin
   * @param quantity
   * @return
   */
  private ASTNode constructVector(ArraysSBasePlugin arraysPlugin, Quantity quantity) {
    double value = quantity.getValue();
    Dimension dim = arraysPlugin.getDimensionByArrayDimension(0);
    Parameter p = model.getParameter(dim.getSize());
    double size = p.getValue();
    List<ASTNode> vector = new ArrayList<ASTNode>((int) size);
    for (int j = 0; j < size; ++j) {
      vector.add(new ASTNode(value));
    }
    vector(vector);
    ASTNode vectorNode = getNode();
    for (int i = 1; i < arraysPlugin.getDimensionCount(); ++i) {
      dim = arraysPlugin.getDimensionByArrayDimension(i);
      p = model.getParameter(dim.getSize());
      size = p.getValue();
      vector = new ArrayList<ASTNode>((int) size);
      for (int j = 0; j < size; ++j) {
        vector.add(vectorNode);
      }
      vector(vector);
      vectorNode = getNode();
    }

    return vectorNode;
  }

  /**
   * @param arraysPlugin
   * @param node
   * @return
   */
  private ASTNode constructVector(ArraysSBasePlugin arraysPlugin, ASTNode node) {

    Dimension dim = arraysPlugin.getDimensionByArrayDimension(0);
    Parameter p = model.getParameter(dim.getSize());
    double size = p.getValue();
    List<ASTNode> vector = new ArrayList<ASTNode>((int) size);
    for (int j = 0; j < size; ++j) {
      vector.add(node.clone());
    }
    vector(vector);
    ASTNode vectorNode = getNode();
    for (int i = 1; i < arraysPlugin.getDimensionCount(); ++i) {
      dim = arraysPlugin.getDimensionByArrayDimension(i);
      p = model.getParameter(dim.getSize());
      size = p.getValue();
      vector = new ArrayList<ASTNode>((int) size);
      for (int j = 0; j < size; ++j) {
        vector.add(vectorNode);
      }
      vector(vector);
      vectorNode = getNode();
    }

    return vectorNode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cos(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try{
        cosRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_COS, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.cos(compiled.getReal()));
    }

    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void cosRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isNumber()) {
        value.getChild(i).setValue(Math.cos(child.getReal()));
      } else if (child.isVector()) {
        cosRecursive(value.getChild(i));
      } else if (useId){
        ASTNode cosValue = new ASTNode(ASTNode.Type.FUNCTION_COS, child.getParentSBMLObject());
        cosValue.addChild(child.clone());
        value.replaceChild(i, cosValue);
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cosh(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        coshRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_COSH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.cosh(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void coshRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isNumber()) {
        value.getChild(i).setValue(Math.cosh(child.getReal()));
      } else if (child.isVector()) {
        coshRecursive(value.getChild(i));
      } else if (useId){
        ASTNode coshValue = new ASTNode(ASTNode.Type.FUNCTION_COSH, child.getParentSBMLObject());
        coshValue.addChild(child.clone());
        value.replaceChild(i, coshValue);
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cot(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        cotRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_COT, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.cot(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void cotRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isNumber()) {
        value.getChild(i).setValue(Maths.cot(child.getReal()));
      } else if (child.isVector()) {
        cotRecursive(value.getChild(i));
      } else if (useId){
        ASTNode cotValue = new ASTNode(ASTNode.Type.FUNCTION_COT, child.getParentSBMLObject());
        cotValue.addChild(child.clone());
        value.replaceChild(i, cotValue);
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue coth(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        cothRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_COTH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.coth(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void cothRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        cothRecursive(value.getChild(i));
      } else if (useId){
        ASTNode cothValue = new ASTNode(ASTNode.Type.FUNCTION_COTH, child.getParentSBMLObject());
        cothValue.addChild(child.clone());
        value.replaceChild(i, cothValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.coth(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csc(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        cscRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_CSC, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.csc(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void cscRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        cscRecursive(value.getChild(i));
      } else if (useId){
        ASTNode cscValue = new ASTNode(ASTNode.Type.FUNCTION_CSC, child.getParentSBMLObject());
        cscValue.addChild(child.clone());
        value.replaceChild(i, cscValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.csc(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csch(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        cschRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_CSCH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.csch(compiled.getReal()));
    }

    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void cschRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        cschRecursive(value.getChild(i));
      } else if (useId){
        ASTNode cschValue = new ASTNode(ASTNode.Type.FUNCTION_CSCH, child.getParentSBMLObject());
        cschValue.addChild(child.clone());
        value.replaceChild(i, cschValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.csch(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode, java.lang.String)
   */
  @Override
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
    String timeUnits) throws SBMLException {
    // TODO Auto-generated method stub
    unknownValue();
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#eq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    ASTNode leftCompiled = getNode();
    right.compile(this);
    ASTNode rightCompiled = getNode();

    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          eqRecursive(rightCompiled,leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);

      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          scalarVectorEq(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);

      } else {
        unknownValue();
      }
    }
    else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorEq(result, leftCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId) {
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.RELATIONAL_EQ);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (leftCompiled.isNumber() && rightCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue == rightValue ? 1 : 0, left.getParentSBMLObject());
        setNode(result);
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }

  /**
   * 
   * @param right
   * @param left
   * @param node
   * @throws IndexOutOfBoundsException
   * @throws SBMLException
   */
  private void eqRecursive(ASTNode right, ASTNode left, ASTNode node) throws IndexOutOfBoundsException, SBMLException{
    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.RELATIONAL_EQ);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        if (left.getReal() == right.getReal()) {
          node.setValue(1);
        } else {
          node.setValue(0);
        }
      } else {
        throw new SBMLException();
      }
      return;
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        eqRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param vector
   * @param scalar
   */
  private void scalarVectorEq(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorEq(child, scalar);
      } else if (useId) {
        vector.replaceChild(i, ASTNode.eq(result, scalar));
      } else if (result.isNumber() && scalar.isNumber()) {
        vector.getChild(i).setValue(result.getReal() == scalar.getReal() ? 1 : 0);
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue exp(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        expRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_EXP, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.exp(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void expRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        expRecursive(value.getChild(i));
      }
      else if (useId){
        ASTNode expValue = new ASTNode(ASTNode.Type.FUNCTION_EXP, child.getParentSBMLObject());
        expValue.addChild(child.clone());
        value.replaceChild(i, expValue);
      }
      else if (child.isNumber()) {
        value.getChild(i).setValue(Math.exp(child.getReal()));
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue factorial(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        factorialRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_FACTORIAL, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      double result = Maths.factorial(compiled.getInteger());
      compiled.setValue(result);
    }
    else {
      compiled = new ASTNode("unknown");
    }

    setNode(compiled);
    return dummy;
  }

  /**
   * @param value
   */
  private void factorialRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        factorialRecursive(value.getChild(i));
      } else if (useId){
        ASTNode factValue = new ASTNode(ASTNode.Type.FUNCTION_FACTORIAL, child.getParentSBMLObject());
        factValue.addChild(child.clone());
        value.replaceChild(i, factValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.factorial(child.getInteger()));
      } else {
        throw new SBMLException();
      }
    }
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue floor(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        floorRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_FLOOR, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.floor(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void floorRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        floorRecursive(value.getChild(i));
      } else if (useId){
        ASTNode floorValue = new ASTNode(ASTNode.Type.FUNCTION_FLOOR, child.getParentSBMLObject());
        floorValue.addChild(child.clone());
        value.replaceChild(i, floorValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.floor(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws SBMLException {
    numerator.compile(this);
    ASTNode leftCompiled = getNode();
    denominator.compile(this);
    ASTNode rightCompiled = getNode();
    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          fracRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          vectorScalarFrac(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else {
        unknownValue();
      }
    } else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorFrac(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId) {
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.DIVIDE);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (leftCompiled.isNumber() && rightCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        if (rightValue == 0) {
          unknownValue();
        } else{
          ASTNode result = new ASTNode(leftValue/rightValue);
          setNode(result);
        }
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }

  /**
   * 
   * @param left
   * @param right
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void fracRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{

    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.RATIONAL);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        if (right.getReal() == 0) {
          throw new SBMLException();
        } else {
          node.setValue(left.getReal() / right.getReal());
        }
      }
      else {
        throw new SBMLException();
      }
      return;
    }

    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        fracRecursive(left.getChild(i),right.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }

  }

  /**
   * @param vector
   * @param scalar
   */
  private void vectorScalarFrac(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        vectorScalarGt(child, scalar);
      } else if (useId) {
        vector.replaceChild(i, ASTNode.frac(result, scalar));
      } else if (result.isNumber() && scalar.isNumber()) {
        vector.getChild(i).setValue(result.getReal() / scalar.getReal());
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * @param scalar
   * @param vector
   */
  private void scalarVectorFrac(ASTNode scalar, ASTNode vector) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorGt(child, scalar);
      } else if (useId) {
        vector.replaceChild(i, ASTNode.frac(scalar, result));
      } else if (result.isNumber() && scalar.isNumber() && result.getReal() != 0) {
        vector.getChild(i).setValue(scalar.getReal() / result.getReal());
      }
      else {
        throw new SBMLException();
      }
    }
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(int, int)
   */
  @Override
  public ASTNodeValue frac(int numerator, int denominator) throws SBMLException {
    frac(new ASTNode(numerator), new ASTNode(denominator));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public ASTNodeValue function(FunctionDefinition functionDefinition,
    List<ASTNode> args) throws SBMLException {

    if (functionDefinition != null) {
      if (useId) {
        if (isSetIdToVector() && idToVector.containsKey(functionDefinition.getId())) {
          setNode(idToVector.get(functionDefinition.getId()));
        } else {
          transformNamedSBase(functionDefinition);
        }

        ASTNode newNode = getNode();

        for (ASTNode child : args)
        {
          child.compile(this);
          newNode.addChild(getNode());
        }

        setNode(newNode);

      }
      else {
        ASTNode math = functionDefinition.getBody();
        if (functionDefinition.getArgumentCount() != args.size()) {
          throw new SBMLException();
        }
        for (int i = 0; i < functionDefinition.getArgumentCount(); i++) {
          ASTNode arg = functionDefinition.getArgument(i);
          if (arg.isString()) {
            math = replaceMath(math, arg.toString(), args.get(i));
          } else {
            throw new SBMLException();
          }
        }
        math.compile(this);
      }
    }
    else
    {
      unknownValue();
    }



    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public ASTNodeValue function(String functionDefinitionName, List<ASTNode> args)
      throws SBMLException {
    FunctionDefinition func = model.getFunctionDefinition(functionDefinitionName);
    if (func == null) {
      throw new SBMLException("FunctionDefinition with id ' " + functionDefinitionName + "' cannot be found in the model.");
    }
    function(func, args);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#geq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    ASTNode leftCompiled = getNode();
    right.compile(this);
    ASTNode rightCompiled = getNode();
    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          geqRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          vectorScalarGeq(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else {
        unknownValue();
      }
    } else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorGeq(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId){
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.RELATIONAL_GEQ);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (rightCompiled.isNumber() && leftCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue >= rightValue ? 1 : 0);
        setNode(result);
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }


  /**
   * 
   * @param left
   * @param right
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void geqRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.RELATIONAL_GEQ);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        if (left.getReal() >= right.getReal()) {
          node.setValue(1);
        } else {
          node.setValue(0);
        }
      }
      else {
        throw new SBMLException();
      }
      return;
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        geqRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param vectorLHS
   * @param scalarRHS
   */
  private void vectorScalarGeq(ASTNode vectorLHS, ASTNode scalarRHS) {
    for (int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        vectorScalarGeq(child, scalarRHS);
      } else if (useId) {
        vectorLHS.replaceChild(i, ASTNode.geq(result, scalarRHS));
      } else if (result.isNumber() && scalarRHS.isNumber()) {
        vectorLHS.getChild(i).setValue(result.getReal() >= scalarRHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param scalarLHS
   * @param vectorRHS
   */
  private void scalarVectorGeq(ASTNode scalarLHS, ASTNode vectorRHS) {
    for (int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorGeq(child, scalarLHS);
      } else if (useId) {
        vectorRHS.replaceChild(i, ASTNode.geq(scalarLHS, result));
      } else if (result.isNumber() && scalarLHS.isNumber()) {
        vectorRHS.getChild(i).setValue(result.getReal() >= scalarLHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    ASTNode avogNode = new ASTNode(ASTNode.Type.REAL);
    avogNode.setValue(Math.pow(6.02214179, Math.pow(10, 23)));
    setNode(avogNode);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    ASTNode eNode = new ASTNode(Math.E);
    eNode.setType(ASTNode.Type.CONSTANT_E);
    setNode(eNode);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public ASTNodeValue getConstantFalse() {
    ASTNode falseNode = new ASTNode(0);
    falseNode.setType(ASTNode.Type.CONSTANT_FALSE);
    setNode(falseNode);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public ASTNodeValue getConstantPi() {
    ASTNode piNode = new ASTNode(Math.PI);
    piNode.setType(ASTNode.Type.CONSTANT_PI);
    setNode(piNode);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantTrue()
   */
  @Override
  public ASTNodeValue getConstantTrue() {
    ASTNode trueNode = new ASTNode(1);
    trueNode.setType(ASTNode.Type.CONSTANT_TRUE);
    setNode(trueNode);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getNegativeInfinity()
   */
  @Override
  public ASTNodeValue getNegativeInfinity() throws SBMLException {
    setNode(new ASTNode(Double.NEGATIVE_INFINITY));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public ASTNodeValue getPositiveInfinity() {
    setNode(new ASTNode(Double.POSITIVE_INFINITY));
    return dummy;
  }

  /**
   * @param values
   * @param vectors
   * @param scalars
   * @param ids
   * @return
   */
  private boolean getScalarsAndVectors(List<ASTNode> values, List<ASTNode> vectors, List<ASTNode> scalars, List<ASTNode> ids) {
    for (ASTNode node : values) {
      node.compile(this);
      ASTNode value = getNode();
      if (value.isVector()) {
        vectors.add(value);
      }
      else if (useId){
        if (value.isName() && value.getName().equals("unknown")) {
          return false;
        }
        ids.add(value);
      }
      else if (value.isNumber()){
        scalars.add(value);
      }
      else if (value.isName()){
        if (value.getName().equals("unknown")) {
          return false;
        }
        ids.add(value);
      }
      else {
        return false;
      }
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#gt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    ASTNode leftCompiled = getNode();
    right.compile(this);
    ASTNode rightCompiled = getNode();
    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          gtRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          vectorScalarGt(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else {
        unknownValue();
      }
    } else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorGt(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId){
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.RELATIONAL_GT);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (rightCompiled.isNumber() && leftCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue > rightValue ? 1 : 0);
        setNode(result);
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }

  /**
   * 
   * @param left
   * @param right
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void gtRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.RELATIONAL_GT);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        if (left.getReal() > right.getReal()) {
          node.setValue(1);
        } else {
          node.setValue(0);
        }
      }
      else {
        throw new SBMLException();
      }
      return;
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        gtRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param vectorLHS
   * @param scalarRHS
   */
  private void vectorScalarGt(ASTNode vectorLHS, ASTNode scalarRHS) {
    for (int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        vectorScalarGt(child, scalarRHS);
      } else if (useId) {
        vectorLHS.replaceChild(i, ASTNode.gt(result, scalarRHS));
      } else if (result.isNumber() && scalarRHS.isNumber()) {
        vectorLHS.getChild(i).setValue(result.getReal() > scalarRHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param scalarLHS
   * @param vectorRHS
   */
  private void scalarVectorGt(ASTNode scalarLHS, ASTNode vectorRHS) {
    for (int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorGt(child, scalarLHS);
      } else if (useId) {
        vectorRHS.replaceChild(i, ASTNode.gt(scalarLHS, result));
      } else if (result.isNumber() && scalarLHS.isNumber()) {
        vectorRHS.getChild(i).setValue(result.getReal() <= scalarLHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lambda(java.util.List)
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> values) throws SBMLException {
    if (useId) {
      ASTNode value = new ASTNode(ASTNode.Type.LAMBDA);
      for (ASTNode node : values) {
        node.compile(this);
        value.addChild(getNode());
      }
      setNode(value);
    } else {
      values.get(values.size() - 1).compile(this);
    }
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#leq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    ASTNode leftCompiled = getNode();
    right.compile(this);
    ASTNode rightCompiled = getNode();
    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          leqRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          vectorScalarLeq(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else {
        unknownValue();
      }
    } else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorLeq(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId){
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.RELATIONAL_LEQ);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (rightCompiled.isNumber() && leftCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue <= rightValue ? 1 : 0);
        setNode(result);
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }

  /**
   * 
   * @param left
   * @param right
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void leqRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.RELATIONAL_LEQ);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        if (left.getReal() <= right.getReal()) {
          node.setValue(1);
        } else {
          node.setValue(0);
        }
      }
      else {
        throw new SBMLException();
      }
      return;
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        leqRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param vectorLHS
   * @param scalarRHS
   */
  private void vectorScalarLeq(ASTNode vectorLHS, ASTNode scalarRHS) {
    for (int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        vectorScalarLeq(child, scalarRHS);
      } else if (useId) {
        vectorLHS.replaceChild(i, ASTNode.leq(result, scalarRHS));
      } else if (result.isNumber() && scalarRHS.isNumber()) {
        vectorLHS.getChild(i).setValue(result.getReal() <= scalarRHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param scalarLHS
   * @param vectorRHS
   */
  private void scalarVectorLeq(ASTNode scalarLHS, ASTNode vectorRHS) {
    for (int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorLt(scalarLHS, child);
      } else if (useId) {
        vectorRHS.replaceChild(i, ASTNode.leq(scalarLHS, result));
      } else if (result.isNumber() && scalarLHS.isNumber()) {
        vectorRHS.getChild(i).setValue(result.getReal() < scalarLHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        lnRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_LN, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.log(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void lnRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        lnRecursive(value.getChild(i));
      } else if (useId){
        ASTNode lnValue = new ASTNode(ASTNode.Type.FUNCTION_LN, child.getParentSBMLObject());
        lnValue.addChild(child.clone());
        value.replaceChild(i, lnValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.log(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        logRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_LOG, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.log10(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void logRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        logRecursive(value.getChild(i));
      } else if (useId){
        ASTNode logValue = new ASTNode(ASTNode.Type.FUNCTION_LOG, child.getParentSBMLObject());
        logValue.addChild(child.clone());
        value.replaceChild(i, logValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.log10(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode base, ASTNode value) throws SBMLException {
    base.compile(this);
    ASTNode baseCompiled = getNode();
    log(baseCompiled);
    baseCompiled = getNode();
    value.compile(this);
    ASTNode valueCompiled = getNode();
    log(valueCompiled);
    valueCompiled = getNode();
    frac(valueCompiled, baseCompiled);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    ASTNode leftCompiled = getNode();
    right.compile(this);
    ASTNode rightCompiled = getNode();
    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          ltRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          vectorScalarLt(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else {
        unknownValue();
      }
    } else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorLt(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId){
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.RELATIONAL_LT);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (rightCompiled.isNumber() && leftCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue < rightValue ? 1 : 0);
        setNode(result);
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }

  /**
   * 
   * @param left
   * @param right
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void ltRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.RELATIONAL_LT);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        if (left.getReal() < right.getReal()) {
          node.setValue(1);
        } else {
          node.setValue(0);
        }
      }
      else {
        throw new SBMLException();
      }
      return;
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        ltRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param vectorLHS
   * @param scalarRHS
   */
  private void vectorScalarLt(ASTNode vectorLHS, ASTNode scalarRHS) {
    for (int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        vectorScalarLt(child, scalarRHS);
      } else if (useId) {
        vectorLHS.replaceChild(i, ASTNode.lt(result, scalarRHS));
      } else if (result.isNumber() && scalarRHS.isNumber()) {
        vectorLHS.getChild(i).setValue(result.getReal() < scalarRHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param scalarLHS
   * @param vectorRHS
   */
  private void scalarVectorLt(ASTNode scalarLHS, ASTNode vectorRHS) {
    for (int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorLt(scalarLHS, child);
      } else if (useId) {
        vectorRHS.replaceChild(i, ASTNode.lt(scalarLHS, result));
      } else if (result.isNumber() && scalarLHS.isNumber()) {
        vectorRHS.getChild(i).setValue(result.getReal() >= scalarLHS.getReal() ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#minus(java.util.List)
   */
  @Override
  public ASTNodeValue minus(List<ASTNode> values) throws SBMLException {
    List<ASTNode> negValues = new ArrayList<ASTNode>();
    if (values.size() > 0) {
      values.get(0).compile(this);
      negValues.add(getNode());
    }
    for (int i = 1; i < values.size(); i++) {
      values.get(i).compile(this);
      uMinus(getNode()); // TODO - check with Leandro - we should not use UMinus here, I think.
      negValues.add(getNode());
    }

    plus(negValues);

    ASTNode plus = getNode();
    setNode(plus);
    
    if(useId)
    {
      ASTNode minus = new ASTNode(ASTNode.Type.MINUS);
      if(plus.getChildCount() > 0) {
        minus.addChild(plus.getChild(0));

        for(int i = 1; i < plus.getChildCount(); i++)
        {
          ASTNode child = plus.getChild(i);
          if(child.getType() == ASTNode.Type.MINUS && child.getChildCount() == 1) // TODO - then those special cases might not be needed any more ?!
          {
            minus.addChild(child.getChild(0));
          }
          else if(child.getType() == ASTNode.Type.REAL && child.getMantissa() < 0)
          {
            child.setValue(-child.getMantissa());
            minus.addChild(child);
          }
          else if(child.getType() == ASTNode.Type.REAL_E && child.getMantissa() < 0)
          {
            child.setValue(-child.getMantissa(), child.getExponent());
            minus.addChild(child);
          }
          else if(child.getType() == ASTNode.Type.INTEGER && child.getInteger() < 0)
          {
            child.setValue(-child.getInteger());
            minus.addChild(child);
          }
          else if(child.getType() == ASTNode.Type.RATIONAL && child.getNumerator() < 0)
          {
            child.setValue(-child.getNumerator(), child.getDenominator());
            minus.addChild(child);
          }
          else
          {
            minus.addChild(child);
          }
        }

        setNode(minus);
      }
    }
    return dummy;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#neq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    ASTNode leftCompiled = getNode();
    right.compile(this);
    ASTNode rightCompiled = getNode();

    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          neqRecursive(rightCompiled,leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);

      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          scalarVectorNeq(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);

      } else {
        unknownValue();
      }
    }
    else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorNeq(result, leftCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId) {
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.RELATIONAL_NEQ);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (leftCompiled.isNumber() && rightCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue != rightValue ? 1 : 0, left.getParentSBMLObject());
        setNode(result);
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }

  /**
   * 
   * @param right
   * @param left
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void neqRecursive(ASTNode right, ASTNode left, ASTNode node) throws IndexOutOfBoundsException{
    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.RELATIONAL_NEQ);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        if (left.getReal() != right.getReal()) {
          node.setValue(1);
        } else {
          node.setValue(0);
        }
      }
      else {
        throw new SBMLException();
      }
      return;
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        neqRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param vector
   * @param scalar
   */
  private void scalarVectorNeq(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorNeq(child, scalar);
      } else if (useId) {
        vector.replaceChild(i, ASTNode.neq(result, scalar));
      } else if (result.isNumber() && scalar.isNumber()) {
        vector.getChild(i).setValue(result.getReal() != scalar.getReal() ? 1 : 0);
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue not(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        notRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.LOGICAL_NOT, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isBoolean()) {
      compiled.setValue(compiled.getInteger() == 1 ? 0 : 1);
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void notRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        notRecursive(value.getChild(i));
      } else if (useId){
        ASTNode notValue = new ASTNode(ASTNode.Type.LOGICAL_NOT, child.getParentSBMLObject());
        notValue.addChild(child.clone());
        value.replaceChild(i, notValue);
      } else if (child.isBoolean()) {
        value.getChild(i).setValue(child.getInteger() == 1 ? 0 : 1);
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#or(java.util.List)
   */
  @Override
  public ASTNodeValue or(List<ASTNode> values) throws SBMLException {

    List<ASTNode> vectors = new ArrayList<ASTNode>();
    List<ASTNode> scalars = new ArrayList<ASTNode>();
    List<ASTNode> ids = new ArrayList<ASTNode>();
    boolean success = getScalarsAndVectors(values, vectors, scalars, ids);

    if (!success) {
      unknownValue();
      return dummy;
    }
    double sumScalar;
    boolean isSetSumScalar = false;
    boolean hasIds = false;

    if (scalars.size() > 0) {
      isSetSumScalar = true;
    }
    if (ids.size() > 0) {
      hasIds = true;
    }

    boolean scalarResult = false;
    for (ASTNode node : scalars) {
      scalarResult |= node.getReal() == 1 ? true : false;
    }
    sumScalar = scalarResult ? 1 : 0;
    ASTNode out = new ASTNode(sumScalar);
    if (!vectors.isEmpty()) {
      out = vectors.get(0).clone();
      try {
        orRecursive(vectors, out);
      }
      catch(IndexOutOfBoundsException e) {
        unknownValue();
        return dummy;
      }
      if (isSetSumScalar) {
        scalarVectorOr(out, new ASTNode(sumScalar));
      }
      if (hasIds) {
        for (ASTNode id : ids) {
          scalarVectorOr(out, id);
        }
      }
    }
    else {
      if (hasIds) {
        ASTNode result = new ASTNode(ASTNode.Type.LOGICAL_OR, out.getParentSBMLObject());
        if (!ids.isEmpty()) {
          for (int i = 0; i < ids.size(); ++i) {
            result.addChild(ids.get(i).clone());
          }
        }
        out = result;
      }
      if (isSetSumScalar) {
        out.addChild(new ASTNode(sumScalar));
      }
    }

    setNode(out);
    return dummy;
  }

  /**
   * 
   * @param values
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void orRecursive(List<ASTNode> values, ASTNode node) throws IndexOutOfBoundsException{
    if (node.isVector()) {
      boolean result = false;
      boolean isResultSet = false;
      node.setType(ASTNode.Type.LOGICAL_OR);
      node.getChildren().clear();
      if (values.size() > 0) {
        for (int i = 0; i < values.size(); ++i) {
          ASTNode value = values.get(i);
          if (value.isNumber()) {
            if (!isResultSet) {
              result = value.getInteger() == 1;
              isResultSet = true;
            } else {
              result |= value.getInteger() == 1 ? true : false;
            }
          } else if (useId) {
            node.addChild(values.get(i).clone());
          } else {
            throw new SBMLException();
          }
        }
        if (isResultSet) {
          node.addChild(new ASTNode(result ? 1 : 0));
        }
        return;
      }
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      List<ASTNode> nodes = new ArrayList<ASTNode>();
      for (ASTNode value : values) {
        value.compile(this);
        ASTNode result = getNode();
        if (result.isVector()) {
          nodes.add(result.getChild(i));
        }
      }
      orRecursive(nodes, node.getChild(i));
    }

  }

  /**
   * 
   * @param vector
   * @param scalar
   */
  private void scalarVectorOr(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorOr(child, scalar);
      }
      else if (useId) {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.LOGICAL_OR, vector.getChild(i).getParentSBMLObject());
        nodeValue.addChild(result.clone());
        nodeValue.addChild(scalar.clone());
        vector.replaceChild(i, nodeValue);
      }
      else if (result.isNumber()) {
        boolean resBool = result.getReal() == 1 ? true : false;
        boolean scalBool = scalar.getReal() == 1 ? true : false;
        vector.getChild(i).setValue(resBool | scalBool ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> values) throws SBMLException {
    boolean isVector = false;
    List<ASTNode> compiledValues = new ArrayList<ASTNode>();
    ASTNode vector = new ASTNode();
    for (ASTNode value : values) {
      value.compile(this);
      ASTNode compiledValue = getNode();
      compiledValues.add(compiledValue);
      if (compiledValue.isVector()) {
        isVector = true;
        vector = compiledValue.clone();
      }
    }
    if (isVector) {
      piecewiseRecursive(values, vector);
      setNode(vector);
    } else {
      if (useId) {
        ASTNode piecewise = new ASTNode(ASTNode.Type.FUNCTION_PIECEWISE);
        piecewise.getChildren().addAll(compiledValues);
        setNode(piecewise);
        return dummy;
      } else {
        int i = 0;
        for (; i < compiledValues.size(); i+=2) {
          ASTNode math = values.get(i);
          ASTNode cond = values.get(i+1);
          if (cond.isOne()) {
            setNode(math);
            return dummy;
          }
        }
        if (i < compiledValues.size()) {
          setNode(compiledValues.get(compiledValues.size() - 1));
          return dummy;
        }
      }
    }

    return dummy;
  }

  /**
   * @param values
   * @param vector
   * @throws SBMLException
   */
  private void piecewiseRecursive(List<ASTNode> values, ASTNode vector) throws SBMLException{
    if (!vector.isVector()) {
      vector.setType(ASTNode.Type.FUNCTION_PIECEWISE);
      vector.getChildren().clear();
      for (ASTNode value : values) {
        vector.addChild(value);
      }
      return;
    }

    for (int i = 0; i < vector.getChildCount(); ++i) {
      List<ASTNode> children = new ArrayList<ASTNode>();
      for (ASTNode value : values) {
        if (value.getChildCount() == 0) {
          children.add(value.clone());
        } else if (value.isVector()) {
          if (value.getChildCount() != vector.getChildCount()) {
            throw new SBMLException();
          }
          children.add(value.getChild(i).clone());
        }
      }
      piecewiseRecursive(children, vector.getChild(i));
    }

  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#plus(java.util.List)
   */
  @Override
  public ASTNodeValue plus(List<ASTNode> values) throws SBMLException {
    List<ASTNode> vectors = new ArrayList<ASTNode>();
    List<ASTNode> scalars = new ArrayList<ASTNode>();
    List<ASTNode> ids = new ArrayList<ASTNode>();
    boolean success = getScalarsAndVectors(values, vectors, scalars, ids);

    if (!success) {
      unknownValue();
      return dummy;
    }

    double sumScalar = 0;
    boolean isSetSumScalar = false;
    boolean hasIds = false;

    if (scalars.size() > 0) {
      isSetSumScalar = true;
    }
    if (ids.size() > 0) {
      hasIds = true;
    }

    for (ASTNode node : scalars) {
      sumScalar += node.getReal();
    }

    ASTNode out = new ASTNode(sumScalar);
    if (!vectors.isEmpty()) {
      out = vectors.get(0).clone();
      try {
        plusRecursive(vectors, out);
        if (isSetSumScalar) {
          scalarVectorPlus(out, new ASTNode(sumScalar));
        }
        if (hasIds) {
          for (ASTNode id : ids) {
            scalarVectorPlus(out, id);
          }
        }
      }
      catch(IndexOutOfBoundsException e) {
        unknownValue();
        return dummy;
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }

    }
    else {
      if (hasIds) {
        ASTNode result = new ASTNode(ASTNode.Type.PLUS, out.getParentSBMLObject());
        if (!ids.isEmpty()) {
          for (int i = 0; i < ids.size(); ++i) {
            result.addChild(ids.get(i).clone());
          }
        }
        out = result;
      }
      if (isSetSumScalar) {
        out.addChild(new ASTNode(sumScalar));
      }
    }

    setNode(out);
    return dummy;
  }

  /**
   * 
   * @param values
   * @param node
   * @throws IndexOutOfBoundsException
   * @throws SBMLException
   */
  private void plusRecursive(List<ASTNode> values, ASTNode node) throws IndexOutOfBoundsException, SBMLException{
    if (!node.isVector()) {
      double result = 0;

      node.getChildren().clear();

      if (! node.getType().equals(ASTNode.Type.PLUS)) {
        node.setType(ASTNode.Type.PLUS);
      }

      if (values.size() > 0) {
        for (int i = 0; i < values.size(); ++i) {
          ASTNode value = values.get(i);
          if (value.isNumber()) {
            result += value.getReal();
          } else if (useId) {
            node.addChild(value.clone());
          } else {
            throw new SBMLException();
          }
        }
        node.addChild(new ASTNode(result));
        return;
      }
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      List<ASTNode> nodes = new ArrayList<ASTNode>();
      for (ASTNode value : values) {
        value.compile(this);
        ASTNode result = getNode();
        if (result.isVector()) {
          if (result.getChildCount() != node.getChildCount()) {
            throw new SBMLException();
          }
          nodes.add(result.getChild(i));
        } else {
          throw new SBMLException();
        }
      }
      plusRecursive(nodes, node.getChild(i));
    }

  }

  /**
   * 
   * @param vector
   * @param scalar
   */
  private void scalarVectorPlus(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorPlus(child, scalar);
      }
      else if (useId) {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.PLUS, vector.getChild(i).getParentSBMLObject());
        nodeValue.addChild(result.clone());
        nodeValue.addChild(scalar.clone());
        vector.replaceChild(i, nodeValue);
      }
      else if (result.isNumber()) {
        vector.getChild(i).setValue(result.getReal() + scalar.getReal());
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent) throws SBMLException {
    base.compile(this);
    ASTNode leftCompiled = getNode();
    exponent.compile(this);
    ASTNode rightCompiled = getNode();

    if (leftCompiled.isVector()) {
      if (rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          powRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (rightCompiled.isNumber() || useId) {
        ASTNode result = leftCompiled.clone();
        try {
          vectorScalarPow(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else {
        unknownValue();
      }
    } else if (leftCompiled.isNumber() || useId) {
      if (rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorPow(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if (useId) {
        if (leftCompiled.toString().equals("unknown") || rightCompiled.toString().equals("unknown")) {
          unknownValue();
        } else {
          ASTNode result = new ASTNode(ASTNode.Type.FUNCTION_POWER);
          result.addChild(leftCompiled);
          result.addChild(rightCompiled);
          setNode(result);
        }
      } else if (rightCompiled.isNumber() && leftCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(Math.pow(leftValue, rightValue));
        setNode(result);
      } else {
        unknownValue();
      }
    } else {
      unknownValue();
    }
    return dummy;
  }

  /**
   * 
   * @param left
   * @param right
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void powRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if (node.getChildCount() == 0) {
      if (useId) {
        node.setType(ASTNode.Type.FUNCTION_POWER);
        node.getChildren().clear();
        node.addChild(left);
        node.addChild(right);
      }
      else if (left.isNumber() && right.isNumber()) {
        node.setValue(Math.pow(left.getReal(), right.getReal()));
      }
      else {
        unknownValue();
      }
      return;
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if (rightResult.isVector() && leftResult.isVector()) {
        powRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param vectorLHS
   * @param scalarRHS
   */
  private void vectorScalarPow(ASTNode vectorLHS, ASTNode scalarRHS) {
    for (int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        vectorScalarLt(child, scalarRHS);
      } else if (useId) {
        vectorLHS.replaceChild(i, ASTNode.pow(result, scalarRHS));
      } else if (result.isNumber() && scalarRHS.isNumber()) {
        vectorLHS.getChild(i).setValue(Math.pow(result.getReal(), scalarRHS.getReal()));
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param scalarLHS
   * @param vectorRHS
   */
  private void scalarVectorPow(ASTNode scalarLHS, ASTNode vectorRHS) {
    for (int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorLt(scalarLHS, child);
      } else if (useId) {
        vectorRHS.replaceChild(i, ASTNode.pow(scalarLHS, result));
      } else if (result.isNumber() && scalarLHS.isNumber()) {
        vectorRHS.getChild(i).setValue(Math.pow(scalarLHS.getReal(), result.getReal()));
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * @param math
   * @param bvar
   * @param value
   * @return
   */
  private ASTNode replaceMath(ASTNode math, String bvar, ASTNode value) {
    ASTNode clone = math.clone();
    if (math.isString() && math.toString().equals(bvar)) {
      return value.clone();
    } else {
      recursiveReplaceDimensionId(clone, bvar, value);
      return clone;
    }
  }

  /**
   * @param math
   * @param bvar
   * @param value
   */
  private void recursiveReplaceDimensionId(ASTNode math, String bvar, ASTNode value) {
    for (int i = 0; i < math.getChildCount(); ++i) {
      if (math.getChild(i).isString() && math.getChild(i).getName().equals(bvar)) {
        math.replaceChild(i, value);
      }
      recursiveReplaceDimensionId(math.getChild(i), bvar, value);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
      throws SBMLException {
    frac(new ASTNode(1), rootExponent);
    rootExponent = getNode();
    pow(rootExponent, radiant);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws SBMLException {
    root(new ASTNode(rootExponent), radiant);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sec(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        secRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_SEC, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.sec(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void secRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        secRecursive(value.getChild(i));
      } else if (useId){
        ASTNode secValue = new ASTNode(ASTNode.Type.FUNCTION_SEC, child.getParentSBMLObject());
        secValue.addChild(child.clone());
        value.replaceChild(i, secValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.sec(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sech(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        sechRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_SECH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Maths.sech(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void sechRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        sechRecursive(value.getChild(i));
      } else if (useId){
        ASTNode sechValue = new ASTNode(ASTNode.Type.FUNCTION_SECH, child.getParentSBMLObject());
        sechValue.addChild(child.clone());
        value.replaceChild(i, sechValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Maths.sech(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public ASTNodeValue selector(List<ASTNode> nodes) throws SBMLException {
    if (nodes.size() > 0) {
      nodes.get(0).compile(this);
      ASTNode vector = getNode();
      ASTNode selector = new ASTNode(ASTNode.Type.FUNCTION_SELECTOR);
      boolean temp = useId;
      useId = false;
      boolean useNumber = true;
      for (int i = 1; i < nodes.size(); ++i) {
        nodes.get(i).compile(this);
        ASTNode index = getNode();
        if (useNumber) {
          if (!index.isNumber()) {
            if (useId && useNumber) {
              selector.addChild(vector);
              selector.addChild(index);
              useNumber = false;
            }
            else {
              unknownValue();
              return dummy;
            }
          }
          else {
            int indexValue = (int)index.getReal();
            vector = vector.getChild(indexValue);
          }
        } else {
          selector.addChild(index);
        }
      }
      if (useNumber) {
        setNode(vector);
      } else {
        setNode(selector);
      }
      useId = temp;
    }
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sin(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        sinRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_SIN, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.sin(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void sinRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        sinRecursive(value.getChild(i));
      } else if (useId){
        ASTNode sinValue = new ASTNode(ASTNode.Type.FUNCTION_SIN, child.getParentSBMLObject());
        sinValue.addChild(child.clone());
        value.replaceChild(i, sinValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.sin(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sinh(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        sinhRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_SINH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.sinh(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void sinhRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        sinhRecursive(value.getChild(i));
      } else if (useId){
        ASTNode sinhValue = new ASTNode(ASTNode.Type.FUNCTION_SINH, child.getParentSBMLObject());
        sinhValue.addChild(child.clone());
        value.replaceChild(i, sinhValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.sinh(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sqrt(ASTNode radiant) throws SBMLException {
    radiant.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        sqrtRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId){
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_ROOT, compiled.getParentSBMLObject());
        nodeValue.addChild(new ASTNode(2));
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.sqrt(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void sqrtRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        sqrtRecursive(value.getChild(i));
      } else if (useId){
        ASTNode sqrtValue = new ASTNode(ASTNode.Type.FUNCTION_ROOT, child.getParentSBMLObject());
        sqrtValue.addChild(new ASTNode(2));
        sqrtValue.addChild(child.clone());
        value.replaceChild(i, sqrtValue);
      } else if (child.isNumber()) {
        value.getChild(i).setValue(Math.sqrt(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNodeValue symbolTime(String time) {
    ASTNode node = new ASTNode(ASTNode.Type.NAME_TIME);
    node.setName(time);
    setNode(node);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        tanRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      compiled.setName("tanh(" + compiled.getName() + ")");
    }
    else if (compiled.isNumber()) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_TAN, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void tanRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        tanRecursive(value.getChild(i));
      }
      else if (useId) {
        ASTNode tanValue = new ASTNode(ASTNode.Type.FUNCTION_TAN, child.getParentSBMLObject());
        tanValue.addChild(child.clone());
        value.replaceChild(i, tanValue);
      }
      else if (child.isNumber()) {
        value.getChild(i).setValue(Math.tan(child.getReal()));
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tanh(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        tanhRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.FUNCTION_TANH, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else if (compiled.isNumber()) {
      compiled.setValue(Math.tanh(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void tanhRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        tanhRecursive(value.getChild(i));
      }
      else if (useId) {
        ASTNode tanhValue = new ASTNode(ASTNode.Type.FUNCTION_TANH, child.getParentSBMLObject());
        tanhValue.addChild(child.clone());
        value.replaceChild(i, tanhValue);
      }
      else if (child.isNumber()) {
        value.getChild(i).setValue(Math.tanh(child.getReal()));
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#times(java.util.List)
   */
  @Override
  public ASTNodeValue times(List<ASTNode> values) throws SBMLException {
    List<ASTNode> vectors = new ArrayList<ASTNode>();
    List<ASTNode> scalars = new ArrayList<ASTNode>();
    List<ASTNode> ids = new ArrayList<ASTNode>();
    boolean success = getScalarsAndVectors(values, vectors, scalars, ids);

    if (!success) {
      unknownValue();
      return dummy;
    }
    double sumScalar = 1;
    boolean isSetSumScalar = false;
    boolean hasIds = false;

    if (scalars.size() > 0) {
      isSetSumScalar = true;
    }
    if (ids.size() > 0) {
      hasIds = true;
    }

    for (ASTNode node : scalars) {
      sumScalar *= node.getReal();
    }

    ASTNode out = new ASTNode(sumScalar);
    if (!vectors.isEmpty()) {
      out = vectors.get(0).clone();
      try {
        timesRecursive(vectors, out);
      }
      catch(IndexOutOfBoundsException e) {
        unknownValue();
        return dummy;
      }
      if (isSetSumScalar) {
        scalarVectorTimes(out, new ASTNode(sumScalar));
      }
      if (hasIds) {
        for (ASTNode id : ids) {
          scalarVectorTimes(out, id);
        }
      }
    }
    else {
      if (hasIds) {
        ASTNode result = new ASTNode(ASTNode.Type.TIMES, out.getParentSBMLObject());
        if (!ids.isEmpty()) {
          for (int i = 0; i < ids.size(); ++i) {
            result.addChild(ids.get(i).clone());
          }
        }
        out = result;
      }
      if (isSetSumScalar) {
        out.addChild(new ASTNode(sumScalar));
      }
    }

    setNode(out);
    return dummy;
  }

  /**
   * 
   * @param values
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void timesRecursive(List<ASTNode> values, ASTNode node) throws IndexOutOfBoundsException{
    if (node.getChildCount() == 0) {
      double result = 1;
      node.getChildren().clear();
      node.setType(ASTNode.Type.PLUS);
      if (values.size() > 0) {
        for (int i = 0; i < values.size(); ++i) {
          ASTNode value = values.get(i);
          if (value.isNumber()) {
            result *= value.getReal();
          } else if (useId) {
            node.addChild(values.get(i).clone());
          } else {
            throw new SBMLException();
          }
        }
        node.addChild(new ASTNode(result));
        return;
      }
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      List<ASTNode> nodes = new ArrayList<ASTNode>();
      for (ASTNode value : values) {
        value.compile(this);
        ASTNode result = getNode();
        if (result.isVector()) {
          nodes.add(result.getChild(i));
        }
      }
      timesRecursive(nodes, node.getChild(i));
    }

  }

  /**
   * 
   * @param vector
   * @param scalar
   */
  private void scalarVectorTimes(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorTimes(child, scalar);
      }
      else if (useId) {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.TIMES, vector.getChild(i).getParentSBMLObject());
        nodeValue.addChild(result.clone());
        nodeValue.addChild(scalar.clone());
        vector.replaceChild(i, nodeValue);
      }
      else if (result.isNumber()) {
        vector.getChild(i).setValue(result.getReal() * scalar.getReal());
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * @param sbase
   */
  private void transformSBase(SBase sbase) {
    ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
    if (arraysPlugin == null || arraysPlugin.getDimensionCount() == 0) {
      if (sbase instanceof Quantity) {
        Quantity quantity = (Quantity) sbase;
        InitialAssignment assign = model.getInitialAssignment(quantity.getId());
        if (assign == null) {
          setNode(new ASTNode(quantity.getValue()));
        } else {
          assign.getMath().compile(this);
        }
      }
      else {
        unknownValue();
      }
    }
    else {
      if (sbase instanceof Quantity) {
        Quantity quantity = (Quantity) sbase;
        InitialAssignment assign = model.getInitialAssignment(quantity.getId());
        if (assign != null) {
          assign.getMath().compile(this);
          ASTNode node = getNode();
          if (node.isVector()) {
            return;
          } else {
            setNode(constructVector(arraysPlugin, node));
          }
        } else {
          setNode(constructVector(arraysPlugin, quantity));
        }
      }
      else {
        unknownValue();
      }
    }
  }

  /**
   * @param arraysPlugin
   * @param sbase
   * @return
   */
  private ASTNode constructVector(ArraysSBasePlugin arraysPlugin, SpeciesReference sbase) {
    SBase parent = sbase.getParentSBMLObject();
    if (parent != null) {
      parent = parent.getParentSBMLObject();

      if (parent == null) {
        return constructVector(arraysPlugin,(NamedSBase) sbase);
      }
      ArraysSBasePlugin arraysParentPlugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);

      if (arraysParentPlugin == null || arraysParentPlugin.getDimensionCount() == 0) {
        return constructVector(arraysPlugin, (NamedSBase)sbase);
      }

      String id = sbase.getId();
      Dimension dim = arraysParentPlugin.getDimensionByArrayDimension(0);
      double size = ArraysMath.getSize(model, dim);

      List<ASTNode> vector = new ArrayList<ASTNode>((int) size);
      for (int i = 0; i < size; ++i) {
        vector.add(new ASTNode("_" + i));
      }
      vector(vector);
      ASTNode vectorNode = getNode();
      for (int i = 1; i < arraysPlugin.getDimensionCount(); ++i) {
        dim = arraysPlugin.getDimensionByArrayDimension(i);
        size = ArraysMath.getSize(model, dim);
        vector = new ArrayList<ASTNode>((int) size);
        for (int j = 0; j < size; ++j) {
          updateASTNodeName(vectorNode.clone(), j);
          vector.add(vectorNode);
        }
        vector(vector);
        vectorNode = getNode();
      }
      for (int i = 0; i < arraysParentPlugin.getDimensionCount(); ++i) {
        dim = arraysParentPlugin.getDimensionByArrayDimension(i);
        size = ArraysMath.getSize(model, dim);
        vector = new ArrayList<ASTNode>((int) size);
        for (int j = 0; j < size; ++j) {
          ASTNode temp = vectorNode.clone();
          updateASTNodeName(temp, j);
          vector.add(temp);
        }
        vector(vector);
        vectorNode = getNode();
      }
      updateASTNodeName(vectorNode, id);
      return vectorNode;
    }

    return constructVector(arraysPlugin, (NamedSBase)sbase);
  }



  /**
   * @param node
   * @param value
   */
  private void updateASTNodeName(ASTNode node, int value) {
    if (node.isVector()) {
      for (int i = 0; i < node.getChildCount(); ++i) {
        ASTNode child = node.getChild(i);
        updateASTNodeName(child, value);
      }
    } else if (node.isName()) {
      node.setName("_" + String.valueOf(value) + node.getName());
    } else {
      node.setName("unknown");
    }
  }

  /**
   * @param node
   * @param value
   */
  private void updateASTNodeName(ASTNode node, String value) {
    if (node.isVector()) {
      for (int i = 0; i < node.getChildCount(); ++i) {
        ASTNode child = node.getChild(i);
        updateASTNodeName(child, value);
      }
    } else if (node.isName()) {
      String appendName = node.getName();
      while(model.findNamedSBase(value + appendName) != null) {
        appendName = "_" + appendName;
      }
      node.setName(value + appendName);
    } else {
      node.setName("unknown");
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if (compiled.isVector()) {
      try {
        uMinusRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if (compiled.equals(new ASTNode("unknown"))) {
      unknownValue();
      return dummy;
    }
    else if (compiled.isNumber()) {
      if (compiled.isInteger()) {
        compiled.setValue(-value.getInteger());
      } else if (compiled.isRational()) {
        compiled.setValue(-value.getNumerator(), value.getDenominator());
      } else if (compiled.getType().equals(Type.REAL_E)) {
        compiled.setValue(-value.getMantissa(), value.getExponent());
      } else {
        compiled.setValue(-value.getReal());
      }
    }
    else if (useId) {
      if (compiled.toString().equals("unknown")) {
        compiled.setName("unknown");
      } else {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.MINUS, compiled.getParentSBMLObject());
        nodeValue.addChild(compiled);
        setNode(nodeValue);
      }
      return dummy;
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  /**
   * @param value
   */
  private void uMinusRecursive(ASTNode value) {
    for (int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if (child.isVector()) {
        uMinusRecursive(value.getChild(i));
      } else if (useId) {
        value.replaceChild(i, ASTNode.uMinus(child));
      }  else if (child.isNumber()) {
        if (child.isInteger()) {
          value.getChild(i).setValue(-child.getInteger());
        } else if (child.isRational()) {
          value.getChild(i).setValue(-child.getNumerator(), child.getDenominator());
        } else if (child.getType().equals(Type.REAL_E)) {
          value.getChild(i).setValue(-child.getMantissa(), child.getExponent());
        } else {
          value.getChild(i).setValue(-child.getReal());
        }
      } else {
        throw new SBMLException();
      }
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#unknownValue()
   */
  @Override
  public ASTNodeValue unknownValue() throws SBMLException {
    setNode(new ASTNode("unknown"));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector(java.util.List)
   */
  @Override
  public ASTNodeValue vector(List<ASTNode> nodes) throws SBMLException {
    ASTNode vector = new ASTNode();

    vector.setType(ASTNode.Type.VECTOR);

    for (int i = 0; i < nodes.size(); ++i) {
      nodes.get(i).compile(this);
      ASTNode node = getNode();
      if (node.equals(new ASTNode("unknown"))) {
        unknownValue();
        return dummy;
      }
      vector.addChild(getNode());
    }
    setNode(vector);

    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#xor(java.util.List)
   */
  @Override
  public ASTNodeValue xor(List<ASTNode> values) throws SBMLException {
    List<ASTNode> vectors = new ArrayList<ASTNode>();
    List<ASTNode> scalars = new ArrayList<ASTNode>();
    List<ASTNode> ids = new ArrayList<ASTNode>();
    boolean success = getScalarsAndVectors(values, vectors, scalars, ids);

    if (!success) {
      unknownValue();
      return dummy;
    }
    double sumScalar = 0;
    boolean isSetSumScalar = false;
    boolean hasIds = false;

    if (scalars.size() > 0) {
      isSetSumScalar = true;
      boolean scalarResult = true;
      scalarResult ^= scalars.get(0).getReal() == 1 ? true : false;
      for (int i = 1; i < scalars.size(); ++i) {
        scalarResult ^= scalars.get(i).getReal() == 1 ? true : false;
      }
      sumScalar = scalarResult ? 1 : 0;
    }

    if (ids.size() > 0) {
      hasIds = true;
    }

    ASTNode out = new ASTNode(sumScalar);
    if (!vectors.isEmpty()) {
      out = vectors.get(0).clone();
      try {
        xorRecursive(vectors, out);
      }
      catch(IndexOutOfBoundsException e) {
        unknownValue();
        return dummy;
      }
      if (isSetSumScalar) {
        scalarVectorXor(out, new ASTNode(sumScalar));
      }
      if (hasIds) {
        for (ASTNode id : ids) {
          scalarVectorXor(out, id);
        }
      }
    }
    else {
      if (hasIds) {
        ASTNode result = new ASTNode(ASTNode.Type.LOGICAL_XOR, out.getParentSBMLObject());
        if (!ids.isEmpty()) {
          for (int i = 0; i < ids.size(); ++i) {
            result.addChild(ids.get(i).clone());
          }
        }
        out = result;
      }
      out.addChild(new ASTNode(sumScalar));
    }

    setNode(out);
    return dummy;
  }

  /**
   * 
   * @param values
   * @param node
   * @throws IndexOutOfBoundsException
   */
  private void xorRecursive(List<ASTNode> values, ASTNode node) throws IndexOutOfBoundsException{
    if (!node.isVector()) {
      boolean result = false;
      boolean isResultSet = false;
      node.setType(ASTNode.Type.LOGICAL_XOR);
      node.getChildren().clear();
      if (values.size() > 0) {
        for (int i = 0; i < values.size(); ++i) {
          ASTNode value = values.get(i);
          if (value.isNumber()) {
            if (!isResultSet) {
              result = value.getInteger() == 1;
              isResultSet = true;
            } else {
              result ^= value.getInteger() == 1 ? true : false;
            }
          } else if (useId) {
            node.addChild(values.get(i).clone());
          } else {
            throw new SBMLException();
          }
        }
        if (isResultSet) {
          node.addChild(new ASTNode(result ? 1 : 0));
        }
        return;
      }
    }
    for (int i = 0; i < node.getChildCount(); ++i) {
      List<ASTNode> nodes = new ArrayList<ASTNode>();
      for (ASTNode value : values) {
        value.compile(this);
        ASTNode result = getNode();
        if (result.isVector()) {
          nodes.add(result.getChild(i));
        }
      }
      xorRecursive(nodes, node.getChild(i));
    }

  }

  /**
   * 
   * @param vector
   * @param scalar
   */
  private void scalarVectorXor(ASTNode vector, ASTNode scalar) {
    for (int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if (result.isVector()) {
        scalarVectorXor(child, scalar);
      }
      else if (useId) {
        ASTNode nodeValue = new ASTNode(ASTNode.Type.LOGICAL_XOR, vector.getChild(i).getParentSBMLObject());
        nodeValue.addChild(result.clone());
        nodeValue.addChild(scalar.clone());
        vector.replaceChild(i, nodeValue);
      }
      else if (result.isNumber()) {
        boolean resBool = result.getReal() == 1 ? true : false;
        boolean scalBool = scalar.getReal() == 1 ? true : false;
        vector.getChild(i).setValue(resBool ^ scalBool ? 1 : 0);
      }
      else {
        throw new SBMLException();
      }
    }
  }

  @Override
  public ASTNodeValue max(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue min(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue quotient(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue rem(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue implies(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue getRateOf(ASTNode nameAST) {
    // TODO Auto-generated method stub
    return null;
  }
}
