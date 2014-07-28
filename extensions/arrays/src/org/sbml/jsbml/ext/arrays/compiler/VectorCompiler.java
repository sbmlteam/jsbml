/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
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

import org.sbml.jsbml.ASTNode;
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
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.util.Maths;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 30, 2014
 */


//TODO: need to check for errors
//TODO: need to check if its name

public class VectorCompiler implements ASTNodeCompiler {

  private final ASTNodeValue dummy = new ASTNodeValue("dummy", null);
  private final Model model;
  private ASTNode node;
  private final boolean useId;

  public VectorCompiler(Model model) {
    this.model = model;
    node = new ASTNode();
    useId = false;
  }

  public VectorCompiler(Model model, boolean useId) {
    this.model = model;
    node = new ASTNode();
    this.useId = useId;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if(compiled.isVector()) {
      try{
        absRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.abs(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("abs(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void absRecursive(ASTNode value) throws SBMLException{
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.abs(child.getReal()));
      } else if(child.isVector()) {
        absRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("abs(" +child.toString() + ")");
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
    //TODO:
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if(compiled.isVector()) {
      try {
        arccosRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.acos(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arccos(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arccosRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.acos(child.getReal()));
      } else if(child.isVector()) {
        arccosRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arccos(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        arccoshRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arccosh(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arccosh(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arccoshRecursive(ASTNode value) throws SBMLException {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arccosh(child.getReal()));
      } else if(child.isVector()) {
        arccoshRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arccosh(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        arccotRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arccot(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arccot(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arccotRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arccot(child.getReal()));
      } else if(child.isVector()) {
        arccotRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arccot(" +child.toString() + ")");
      } else {
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
    if(compiled.isVector()) {
      try {
        arccothRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arccoth(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arccoth(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arccothRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arccoth(child.getReal()));
      } else if(child.isVector()) {
        arccothRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arccoth(" +child.toString() + ")");
      } else {
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
    if(compiled.isVector()) {
      try {
        arccscRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arccsc(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arccsc(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arccscRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arccsc(child.getReal()));
      } else if(child.isVector()) {
        arccscRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arccsc(" +child.toString() + ")");
      } else {
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
    if(compiled.isVector()) {
      try {
        arccschRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arccsch(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arccsch(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arccschRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arccsch(child.getReal()));
      } else if(child.isVector()) {
        arccschRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arccsch(" +child.toString() + ")");
      } else {
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
    if(compiled.isVector()) {
      try {
        arcsecRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arcsec(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arcsec(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arcsecRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arcsec(child.getReal()));
      } else if(child.isVector()) {
        arcsecRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arcsec(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        arcsechRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arcsech(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arcsech(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arcsechRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arcsech(child.getReal()));
      } else if(child.isVector()) {
        arcsechRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arcsech(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        arcsinRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.asin(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arcsin(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arcsinRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.asin(child.getReal()));
      } else if(child.isVector()) {
        arcsinRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arcsin(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      arcsinhRecursive(compiled);
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arcsinh(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arcsinh(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arcsinhRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arcsinh(child.getReal()));
      } else if(child.isVector()) {
        arcsinhRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arcsinh(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        arctanRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.atan(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arctan(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arctanRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.atan(child.getReal()));
      } else if(child.isVector()) {
        arctanRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arctan(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        arctanhRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.arctanh(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("arctanh(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void arctanhRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.arctanh(child.getReal()));
      } else if(child.isVector()) {
        arctanhRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("arctanh(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        ceilingRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.ceil(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("ceiling(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void ceilingRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.ceil(child.getReal()));
      } else if(child.isVector()) {
        ceilingRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("ceiling(" +child.toString() + ")");
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
    if(useId) {
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
    if(useId) {
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
    SBase sbase = model.findNamedSBase(name);
    if(sbase != null) {
      if(useId) {
        transformNamedSBase(sbase);
      } else {
        transformSBase(sbase);
      }
    } else {
      if(useId) {
        setNode(new ASTNode(name));
      }
      else {
        unknownValue();
      }
    }
    return dummy;
  }

  private void transformNamedSBase(SBase sbase) {
    ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
    if(arraysPlugin == null || arraysPlugin.getDimensionCount() == 0) {
      if(sbase instanceof NamedSBase) {
        NamedSBase namedSBase = (NamedSBase) sbase;
        setNode(new ASTNode(namedSBase.getId()));
      }
      else {
        unknownValue();
      }
    }
    else {
      if(sbase instanceof NamedSBase) {
        NamedSBase namedSBase = (NamedSBase) sbase;
        setNode(constructVector(arraysPlugin, namedSBase));
      }
      else {
        unknownValue();
      }
    }
  }

  private void transformSBase(SBase sbase) {
    ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
    if(arraysPlugin == null || arraysPlugin.getDimensionCount() == 0) {
      if(sbase instanceof Quantity) {
        Quantity quantity = (Quantity) sbase;
        InitialAssignment assign = model.getInitialAssignment(quantity.getId());
        if(assign == null) {
          setNode(new ASTNode(quantity.getValue()));
        } else {
          assign.getMath().compile(this);
        }
      }
      else {
        unknownValue();
      }
      //TODO
    }
    else {
      if(sbase instanceof Quantity) {
        Quantity quantity = (Quantity) sbase;
        InitialAssignment assign = model.getInitialAssignment(quantity.getId());
        if(assign != null) {
          assign.getMath().compile(this);
          ASTNode node = getNode();
          if(node.isVector()) {
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

  private ASTNode constructVector(ArraysSBasePlugin arraysPlugin, NamedSBase sbase) {
    String id = sbase.getId();
    Dimension dim = arraysPlugin.getDimensionByArrayDimension(0);
    Parameter p = model.getParameter(dim.getSize());
    double size = p.getValue();
    List<ASTNode> vector = new ArrayList<ASTNode>((int) size);
    for(int j = 0; j < size; ++j) {
      vector.add(new ASTNode("_" + j));
    }
    vector(vector);
    ASTNode vectorNode = getNode();
    for(int i = 1; i < arraysPlugin.getDimensionCount(); ++i) {
      dim = arraysPlugin.getDimensionByArrayDimension(i);
      p = model.getParameter(dim.getSize());
      size = p.getValue();
      vector = new ArrayList<ASTNode>((int) size);
      for(int j = 0; j < size; ++j) {
        updateASTNodeName(vectorNode.clone(), String.valueOf(j));
        vector.add(vectorNode);
      }
      vector(vector);
      vectorNode = getNode();
    }
    updateASTNodeName(vectorNode, id);
    return vectorNode;
  }

  private void updateASTNodeName(ASTNode node, String value) {
    if(node.isVector()) {
      for(int i = 0; i < node.getChildCount(); ++i) {
        ASTNode child = node.getChild(i);
        updateASTNodeName(child, value);
      }
    } else if(node.isName()) {
      node.setName(value + node.getName());
    } else {
      node.setName("unknown");
    }
  }

  private ASTNode constructVector(ArraysSBasePlugin arraysPlugin, Quantity quantity) {
    double value = quantity.getValue();
    Dimension dim = arraysPlugin.getDimensionByArrayDimension(0);
    Parameter p = model.getParameter(dim.getSize());
    double size = p.getValue();
    List<ASTNode> vector = new ArrayList<ASTNode>((int) size);
    for(int j = 0; j < size; ++j) {
      vector.add(new ASTNode(value));
    }
    vector(vector);
    ASTNode vectorNode = getNode();
    for(int i = 1; i < arraysPlugin.getDimensionCount(); ++i) {
      dim = arraysPlugin.getDimensionByArrayDimension(i);
      p = model.getParameter(dim.getSize());
      size = p.getValue();
      vector = new ArrayList<ASTNode>((int) size);
      for(int j = 0; j < size; ++j) {
        vector.add(vectorNode);
      }
      vector(vector);
      vectorNode = getNode();
    }

    return vectorNode;
  }

  private ASTNode constructVector(ArraysSBasePlugin arraysPlugin, ASTNode node) {

    Dimension dim = arraysPlugin.getDimensionByArrayDimension(0);
    Parameter p = model.getParameter(dim.getSize());
    double size = p.getValue();
    List<ASTNode> vector = new ArrayList<ASTNode>((int) size);
    for(int j = 0; j < size; ++j) {
      vector.add(node.clone());
    }
    vector(vector);
    ASTNode vectorNode = getNode();
    for(int i = 1; i < arraysPlugin.getDimensionCount(); ++i) {
      dim = arraysPlugin.getDimensionByArrayDimension(i);
      p = model.getParameter(dim.getSize());
      size = p.getValue();
      vector = new ArrayList<ASTNode>((int) size);
      for(int j = 0; j < size; ++j) {
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
    if(compiled.isVector()) {
      try{
        cosRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.cos(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("cos(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void cosRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.cos(child.getReal()));
      } else if(child.isVector()) {
        cosRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("cos(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        coshRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.cosh(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("cosh(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void coshRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.cosh(child.getReal()));
      } else if(child.isVector()) {
        coshRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("cosh(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        cotRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.cot(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("cot(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void cotRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.cot(child.getReal()));
      } else if(child.isVector()) {
        cotRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("cot(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        cothRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.coth(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("coth(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void cothRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.coth(child.getReal()));
      } else if(child.isVector()) {
        cothRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("coth(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        cscRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.csc(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("csc(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void cscRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.csc(child.getReal()));
      } else if(child.isVector()) {
        cscRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("csc(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        cschRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.csch(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("csch(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void cschRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.csch(child.getReal()));
      } else if(child.isVector()) {
        cschRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("csch(" +child.toString() + ")");
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

    if(leftCompiled.isVector()) {
      if(rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          eqRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);

      } else if(rightCompiled.isNumber()) {
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
    } else if(leftCompiled.isNumber()) {
      if(rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorEq(result, leftCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue == rightValue ? 1 : 0);
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
   * @param values
   * @param node
   */
  private void eqRecursive(ASTNode right, ASTNode left, ASTNode node) throws IndexOutOfBoundsException, SBMLException{

    if(node.isNumber()) {
      if(left.isNumber() && right.isNumber()) {
        throw new SBMLException();
      }
      if(left.getReal() == right.getReal()) {
        node.setValue(1);
      } else {
        node.setValue(0);
      }

    }

    for(int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if(rightResult.isVector() && leftResult.isVector()) {
        eqRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorEq(ASTNode vector, ASTNode scalar) {
    for(int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vector.getChild(i).setValue(result.getReal() == scalar.getReal() ? 1 : 0);
      }
      else if(result.isVector()) {
        scalarVectorEq(child, scalar);
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
    if(compiled.isVector()) {
      try {
        expRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.exp(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("exp(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void expRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.exp(child.getReal()));
      } 
      else if(child.isVector()) {
        expRecursive(value.getChild(i));
      } 
      else if(useId){
        value.getChild(i).setName("exp(" + child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        factorialRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      double result = Maths.factorial(compiled.getInteger());
      compiled.setValue(result);
    }
    else if(compiled.isName()) {
      compiled.setName("factorial(" + compiled.toString() + ")");
    }
    else {
      compiled = new ASTNode("unknown");
    }

    setNode(compiled);
    return dummy;
  }

  private void factorialRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.factorial(child.getInteger()));
      } else if(child.isVector()) {
        factorialRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("factorial(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        floorRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.floor(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("floor(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void floorRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.floor(child.getReal()));
      } else if(child.isVector()) {
        floorRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("floor(" +child.toString() + ")");
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
    numerator.compile(this);
    ASTNode rightCompiled = getNode();

    if(leftCompiled.isVector()) {
      if(rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          fracRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
        ASTNode result = leftCompiled.clone();
        try {
          scalarVectorFrac(result, rightCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else {
        unknownValue();
      }
    } else if(leftCompiled.isNumber()) {
      if(rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorFrac(result, leftCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        if(rightValue == 0) {
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
   * @param values
   * @param node
   */
  private void fracRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{

    if(node.isNumber()) {
      if(right.getReal() == 0) {
        throw new SBMLException();
      } else{
        node.setValue(left.getReal()/right.getReal());
      }
      return;
    }

    for(int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if(rightResult.isVector() && leftResult.isVector()) {
        fracRecursive(left.getChild(i),right.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }

  }

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorFrac(ASTNode vector, ASTNode scalar) {
    for(int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vector.getChild(i).setValue(result.getReal() == scalar.getReal() ? 1 : 0);
      }
      else if(result.isVector()) {
        scalarVectorEq(child, scalar);
      } else {
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
    // TODO Auto-generated method stub
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public ASTNodeValue function(String functionDefinitionName, List<ASTNode> args)
      throws SBMLException {
    FunctionDefinition func = model.getFunctionDefinition(functionDefinitionName);
    function(func, args);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#geq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
    lt(right, left);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    // TODO Auto-generated method stub
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    setNode(new ASTNode(Math.E));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public ASTNodeValue getConstantFalse() {
    setNode(new ASTNode(0));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public ASTNodeValue getConstantPi() {
    setNode(new ASTNode(Math.PI));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantTrue()
   */
  @Override
  public ASTNodeValue getConstantTrue() {
    setNode(new ASTNode(1));
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#gt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    ASTNode leftCompiled = getNode();
    right.compile(this);
    ASTNode rightCompiled = getNode();

    if(leftCompiled.isVector()) {
      if(rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          gtRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
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
    } else if(leftCompiled.isNumber()) {
      if(rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorGt(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
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
   * @param values
   * @param node
   */
  private void gtRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if(node.isNumber()) {
      if(left.getReal() > right.getReal()) {
        node.setValue(1);
      } else {
        node.setValue(0);
      }
      return;
    }

    for(int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if(rightResult.isVector() && leftResult.isVector()) {
        gtRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void vectorScalarGt(ASTNode vectorLHS, ASTNode scalarRHS) {
    for(int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vectorLHS.getChild(i).setValue(result.getReal() > scalarRHS.getReal() ? 1 : 0);
      }
      else if(result.isVector()) {
        vectorScalarGt(child, scalarRHS);
      } 
      else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorGt(ASTNode scalarLHS, ASTNode vectorRHS) {
    for(int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vectorRHS.getChild(i).setValue(result.getReal() <= scalarLHS.getReal() ? 1 : 0);
      }
      else if(result.isVector()) {
        scalarVectorGt(child, scalarLHS);
      } else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lambda(java.util.List)
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> values) throws SBMLException {
    // TODO Auto-generated method stub
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#leq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
    gt(right, left);
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if(compiled.isVector()) {
      try {
        lnRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.log(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("ln(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void lnRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.log(child.getReal()));
      } else if(child.isVector()) {
        lnRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("ln(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        logRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.log10(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("log(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void logRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.log10(child.getReal()));
      } else if(child.isVector()) {
        logRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("log(" +child.toString() + ")");
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
    //TODO
    base.compile(this);
    ASTNode baseCompiled = getNode();
    value.compile(this);
    ASTNode valueCompiled = getNode();
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

    if(leftCompiled.isVector()) {
      if(rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          ltRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
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
    } else if(leftCompiled.isNumber()) {
      if(rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorLt(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
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
   * @param values
   * @param node
   */
  private void ltRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if(node.isNumber()) {
      if(left.getReal() < right.getReal()) {
        node.setValue(1);
      } else {
        node.setValue(0);
      }
      return;
    }

    for(int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if(rightResult.isVector() && leftResult.isVector()) {
        ltRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void vectorScalarLt(ASTNode vectorLHS, ASTNode scalarRHS) {
    for(int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vectorLHS.getChild(i).setValue(result.getReal() < scalarRHS.getReal() ? 1 : 0);
      }
      else if(result.isVector()) {
        scalarVectorNeq(child, scalarRHS);
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorLt(ASTNode scalarLHS, ASTNode vectorRHS) {
    for(int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vectorRHS.getChild(i).setValue(result.getReal() >= scalarLHS.getReal() ? 1 : 0);
      }
      else if(result.isVector()) {
        scalarVectorLt(child, scalarLHS);
      } else {
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
    if(values.size() > 0) {
      values.get(0).compile(this);
      negValues.add(getNode());
    }
    for(int i = 1; i < values.size(); i++) {
      values.get(i).compile(this);
      uMinus(getNode());
      negValues.add(getNode());
    }

    plus(negValues);

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

    if(leftCompiled.isVector()) {
      if(rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          neqRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
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
    } else if(leftCompiled.isNumber()) {
      if(rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorNeq(result, leftCompiled);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
        double leftValue = leftCompiled.getReal();
        double rightValue = rightCompiled.getReal();
        ASTNode result = new ASTNode(leftValue == rightValue ? 0 : 1);
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
   * @param values
   * @param node
   */
  private void neqRecursive(ASTNode right, ASTNode left, ASTNode node) throws IndexOutOfBoundsException{
    if(node.isNumber()) {
      if(left.getReal() != right.getReal()) {
        node.setValue(1);
      } else {
        node.setValue(0);
      }
      return;
    }

    for(int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if(rightResult.isVector() && leftResult.isVector()) {
        neqRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorNeq(ASTNode vector, ASTNode scalar) {
    for(int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vector.getChild(i).setValue(result.getReal() == scalar.getReal() ? 0 : 1);
      }
      else if(result.isVector()) {
        scalarVectorNeq(child, scalar);
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
    if(compiled.isVector()) {
      try {
        notRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isBoolean()) {
      compiled.setValue(compiled.getInteger() == 1 ? 0 : 1);
    }     
    else if(compiled.isName()) {
      compiled.setName("not(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void notRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isBoolean()) {
        value.getChild(i).setValue(child.getInteger() == 1 ? 0 : 1);
      } else if(child.isVector()) {
        floorRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("not(" +child.toString() + ")");
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
    // TODO Auto-generated method stub
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> values) throws SBMLException {
    // TODO Auto-generated method stub
    return dummy;
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

    if(!success) {
      unknownValue();
      return dummy;
    }

    double sumScalar = 0;
    boolean isSetSumScalar = false;
    boolean hasIds = false;

    if(scalars.size() > 0) {
      isSetSumScalar = true;
    }
    if(ids.size() > 0) {
      hasIds = true;
    }

    for(ASTNode node : scalars) {
      sumScalar += node.getReal();
    }

    ASTNode out = new ASTNode(0);
    if(!vectors.isEmpty()) {
      out = vectors.get(0).clone();
      try {
        plusRecursive(vectors, out);
        if(isSetSumScalar) {
          scalarVectorPlus(out, new ASTNode(sumScalar));
        }
        if(hasIds) {
          for(ASTNode id : ids) {
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
      out = new ASTNode(sumScalar);
    }

    setNode(out);
    return dummy;
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void plusRecursive(List<ASTNode> values, ASTNode node) throws IndexOutOfBoundsException, SBMLException{

    if(node.isNumber()) {
      double result = 0;
      String resultString = null;
      for(ASTNode value : values) {
        if(value.isNumber()) {
          result += value.getReal();
        } else if(value.isName()) {
          if(resultString == null) {
            resultString = value.getName();
          } else {
            resultString += "+" + value.getName();
          }
        }
      }
      if(resultString == null) {
        node.setValue(result);
      } else {
        node.setName(result + "+" + resultString);
      }
      return;
    }
    else if(node.isName()) {
      String result;
      if(values.size() > 0) {
        result = values.get(0).toString();
        for(int i = 1; i < values.size(); ++i) {
          result += "+" + values.get(i).toString();
        }
        node.setName(result);
        return;
      }
      return;
    }

    for(int i = 0; i < node.getChildCount(); ++i) {
      List<ASTNode> nodes = new ArrayList<ASTNode>();
      for(ASTNode value : values) {
        value.compile(this);
        ASTNode result = getNode();
        if(result.isVector()) {
          if(result.getChildCount() != node.getChildCount()) {
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

  //TODO: SBMLException error id

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorPlus(ASTNode vector, ASTNode scalar) {
    for(int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isVector()) {
        scalarVectorPlus(child, scalar);
      } 
      else if(useId) {
        vector.getChild(i).setName(result.toString() + "+" + scalar.toString());
      }
      else if(result.isNumber()) {
        vector.getChild(i).setValue(result.getReal() + scalar.getReal());
      }
      else {
        throw new SBMLException();
      }
    }
  }

  //TODO: useId
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent) throws SBMLException {
    base.compile(this);
    ASTNode leftCompiled = getNode();
    exponent.compile(this);
    ASTNode rightCompiled = getNode();

    if(leftCompiled.isVector()) {
      if(rightCompiled.isVector()) {
        ASTNode result = leftCompiled.clone();
        try {
          powRecursive(leftCompiled, rightCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
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
    } else if(leftCompiled.isNumber()) {
      if(rightCompiled.isVector()) {
        ASTNode result = rightCompiled.clone();
        try {
          scalarVectorPow(leftCompiled, result);
        }
        catch(SBMLException e) {
          unknownValue();
          return dummy;
        }
        setNode(result);
      } else if(rightCompiled.isNumber()) {
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
   * @param values
   * @param node
   */
  private void powRecursive(ASTNode left, ASTNode right, ASTNode node) throws IndexOutOfBoundsException{
    if(node.isNumber()) {
      node.setValue(Math.pow(left.getReal() , right.getReal()));
      return;
    }

    for(int i = 0; i < node.getChildCount(); ++i) {
      right.compile(this);
      ASTNode rightResult = getNode();
      left.compile(this);
      ASTNode leftResult = getNode();
      if(rightResult.isVector() && leftResult.isVector()) {
        powRecursive(right.getChild(i),left.getChild(i), node.getChild(i));
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void vectorScalarPow(ASTNode vectorLHS, ASTNode scalarRHS) {
    for(int i = 0; i < vectorLHS.getChildCount(); ++i) {
      ASTNode child = vectorLHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vectorLHS.getChild(i).setValue(Math.pow(result.getReal(), scalarRHS.getReal()));
      }
      else if(result.isVector()) {
        scalarVectorPow(child, scalarRHS);
      } else {
        throw new SBMLException();
      }
    }
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorPow(ASTNode scalarLHS, ASTNode vectorRHS) {
    for(int i = 0; i < vectorRHS.getChildCount(); ++i) {
      ASTNode child = vectorRHS.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isNumber()) {
        vectorRHS.getChild(i).setValue(Math.pow(scalarLHS.getReal(), result.getReal()));
      }
      else if(result.isVector()) {
        scalarVectorPow(child, scalarLHS);
      } else {
        throw new SBMLException();
      }
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
    if(compiled.isVector()) {
      try {
        secRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.sec(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("sec(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void secRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.sec(child.getReal()));
      } else if(child.isVector()) {
        secRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("sec(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        sechRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Maths.sech(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("sech(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void sechRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Maths.sech(child.getReal()));
      } else if(child.isVector()) {
        sechRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("sech(" +child.toString() + ")");
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
    if(nodes.size() > 0) {
      nodes.get(0).compile(this);
      ASTNode vector = getNode();
      for(int i = 1; i < nodes.size(); ++i) {
        nodes.get(i).compile(this);
        ASTNode index = getNode();
        if(!index.isNumber()) {
          unknownValue();
          return dummy;
        }
        int indexValue = index.getInteger();
        vector = vector.getChild(indexValue);
      }
      setNode(vector);
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
    if(compiled.isVector()) {
      try {
        sinRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.sin(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("sin(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void sinRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.sin(child.getReal()));
      } else if(child.isVector()) {
        sinRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("sin(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        sinhRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.sinh(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("sinh(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void sinhRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.sinh(child.getReal()));
      } else if(child.isVector()) {
        sinhRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("sinh(" +child.toString() + ")");
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
    if(compiled.isVector()) {
      try {
        sqrtRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.sqrt(compiled.getReal()));
    } 
    else if(compiled.isName()){
     compiled.setName("sqrt(" +compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void sqrtRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(Math.sqrt(child.getReal()));
      } else if(child.isVector()) {
        sqrtRecursive(value.getChild(i));
      } else if(useId){
        value.getChild(i).setName("sqrt(" +child.toString() + ")");
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
    setNode(new ASTNode(0));
    return dummy;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if(compiled.isVector()) {
      try {
        tanRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.tan(compiled.getReal()));
    }
    else if(compiled.isName()) {
      compiled.setName("tanh(" + compiled.toString() + ")");
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void tanRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isVector()) {
        tanRecursive(value.getChild(i));
      }
      else if(useId) {
        value.getChild(i).setName("tan("+value.toString()+")");
      }
      else if(child.isNumber()) {
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
    if(compiled.isVector()) {
      try {
        tanhRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(compiled.isName()) {
      compiled.setName("tanh(" + compiled.toString() + ")");
    }
    else if(compiled.isNumber()) {
      compiled.setValue(Math.tanh(compiled.getReal()));
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void tanhRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isVector()) {
        tanhRecursive(value.getChild(i));
      }
      else if(useId) {
        value.getChild(i).setName("tanh(" + child.toString() + ")");
      }
      else if(child.isNumber()) {
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

    if(!success) {
      unknownValue();
      return dummy;
    }
    double sumScalar = 1;
    boolean isSetSumScalar = false;
    boolean hasIds = false;

    if(scalars.size() > 0) {
      isSetSumScalar = true;
    }
    if(ids.size() > 0) {
      hasIds = true;
    }

    for(ASTNode node : scalars) {
      sumScalar *= node.getReal();
    }

    ASTNode out = new ASTNode(0);
    if(!vectors.isEmpty()) {
      out = vectors.get(0).clone();
      try {
        timesRecursive(vectors, out);
      }
      catch(IndexOutOfBoundsException e) {
        unknownValue();
        return dummy;
      }
      if(isSetSumScalar) {
        scalarVectorTimes(out, new ASTNode(sumScalar));
      }
      if(hasIds) {
        for(ASTNode id : ids) {
          scalarVectorTimes(out, id);
        }
      }
    }
    else {
      out = new ASTNode(sumScalar);
    }

    setNode(out);
    return dummy;
  }

  /**
   * 
   * @param values
   * @param node
   */
  private void timesRecursive(List<ASTNode> values, ASTNode node) throws IndexOutOfBoundsException{

    if(node.isNumber()) {
      double result = 1;
      String resultString = null;
      for(ASTNode value : values) {
        if(value.isNumber()) {
          result *= value.getReal();
        } else if(value.isName()) {
          if(resultString == null) {
            resultString = value.getName();
          } else {
            resultString += "*" + value.getName();
          }
        }
      }
      if(resultString == null) {
        node.setValue(result);
      } else {
        node.setName(result + "*" + resultString);
      }
      return;
    }
    else if(node.isName()) {
      String result;
      if(values.size() > 0) {
        result = values.get(0).toString();
        for(int i = 1; i < values.size(); ++i) {
          result += "*" + values.get(i).toString();
        }
        node.setName(result);
        return;
      }
      return;
    }
    for(int i = 0; i < node.getChildCount(); ++i) {
      List<ASTNode> nodes = new ArrayList<ASTNode>();
      for(ASTNode value : values) {
        value.compile(this);
        ASTNode result = getNode();
        if(result.isVector()) {
          nodes.add(result.getChild(i));
        } 
      }
      timesRecursive(nodes, node.getChild(i));
    }

  }

  /**
   * 
   * @param values
   * @param node
   */
  private void scalarVectorTimes(ASTNode vector, ASTNode scalar) {
    for(int i = 0; i < vector.getChildCount(); ++i) {
      ASTNode child = vector.getChild(i);
      child.compile(this);
      ASTNode result = getNode();
      if(result.isVector()) {
        scalarVectorTimes(child, scalar);
      } 
      else if(useId) {
        vector.getChild(i).setName(result.toString() + "*" + scalar.toString());
      }
      else if(result.isNumber()) {
        vector.getChild(i).setValue(result.getReal() * scalar.getReal());
      }
      else {
        throw new SBMLException();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode value) throws SBMLException {
    value.compile(this);
    ASTNode compiled = getNode();
    if(compiled.isVector()) {
      try {
        uMinusRecursive(compiled);
      }
      catch(SBMLException e) {
        unknownValue();
        return dummy;
      }
    }
    else if(useId) {
      compiled.setName("-" + value.toString());
    }
    else if(compiled.isNumber()) {
      compiled.setValue(-value.getReal());
    }
    else {
      compiled.setName("unknown");
    }
    setNode(compiled);

    return dummy;
  }

  private void uMinusRecursive(ASTNode value) {
    for(int i = 0; i < value.getChildCount(); i++) {
      value.getChild(i).compile(this);
      ASTNode child = getNode();
      if(child.isNumber()) {
        value.getChild(i).setValue(-child.getReal());
      } else if(child.isVector()) {
        uMinusRecursive(value.getChild(i));
      } else if(useId) {
        value.getChild(i).setName("-" + child.toString());
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

    for(int i = 0; i < nodes.size(); ++i) {
      nodes.get(i).compile(this);
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
    //TODO
    return dummy;
  }


  public ASTNode getNode() {
    return node;
  }

  public void setNode(ASTNode node) {
    this.node = node;
  }

  private boolean getScalarsAndVectors(List<ASTNode> values, List<ASTNode> vectors, List<ASTNode> scalars, List<ASTNode> ids) {
    for(ASTNode node : values) {
      node.compile(this);
      ASTNode value = getNode();
      if(value.isVector()) {
        vectors.add(value);
      }
      else if (value.isNumber()){
        scalars.add(value);
      } 
      else if (value.isName()){
        ids.add(value);
      } 
      else {
        return false;
      }
    }
    return true;
  }
}
