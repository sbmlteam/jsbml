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
package org.sbml.jsbml.ext.arrays.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Assignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.compiler.ArraysCompiler;
import org.sbml.jsbml.ext.arrays.compiler.StaticallyComputableCompiler;
import org.sbml.jsbml.ext.arrays.compiler.VectorCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

/**
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArraysMath {

  /**
   * Checks if the math does not evaluate to a negative number or to a value greater or
   * equal to a given size value.
   * 
   * @param dimensionSizes
   * @param math
   * @param size
   * @return
   */
  public static boolean evaluateBounds(Map<String, Double> dimensionSizes, ASTNode math, double size) {
    ArraysCompiler compiler = new ArraysCompiler();
    compiler.setidToValue(getLowerBound(dimensionSizes));

    if (math == null) {
      return false;  // TODO - not sure if we should return true or false in this case
    }
    
    ASTNodeValue mathValue = math.compile(compiler);

    if (mathValue.isNumber()) {
      if (mathValue.toDouble() < 0 || mathValue.toDouble() >= size) {
        return false;
      }
    }
    else {
      return false;
    }

    compiler.setidToValue(getUpperBound(dimensionSizes));

    mathValue = math.compile(compiler);

    if (mathValue.isNumber()) {
      if (mathValue.toDouble() < 0 || mathValue.toDouble() >= size) {
        return false;
      }
    }
    else {
      return false;
    }

    return true;
  }

  /**
   * Checks if the index math does not go out of bounds.
   * 
   * @param model
   * @param index
   * @return
   */
  public static boolean evaluateIndexBounds(Model model, Index index) {

    SBase parent = index.getParentSBMLObject().getParentSBMLObject();

    SBase parents = parent.getParentSBMLObject();

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);

    if (index.isSetReferencedAttribute()) {
      String refValue = parent.writeXMLAttributes().get(index.getReferencedAttribute());

      SBase refSBase = model.findNamedSBase(refValue);

      ArraysSBasePlugin refSbasePlugin = (ArraysSBasePlugin) refSBase.getExtension(ArraysConstants.shortLabel);

      if (refSbasePlugin == null) {
        return false;
      }

      Dimension dimByArrayDim = refSbasePlugin.getDimensionByArrayDimension(index.getArrayDimension());

      if (dimByArrayDim == null) {
        return false;
      }

      Map<String, Double> dimensionSizes = getDimensionSizes(model, arraysSBasePlugin);

      while(parents != null)
      {
        ArraysSBasePlugin parentArraysSBasePlugin = (ArraysSBasePlugin) parents.getExtension(ArraysConstants.shortLabel);

        if (parentArraysSBasePlugin != null) {
          dimensionSizes.putAll(getDimensionSizes(model, parentArraysSBasePlugin));
        }
        
        parents = parents.getParentSBMLObject();
      }

      Map<String, Double> refSBaseSizes = getDimensionSizes(model, refSbasePlugin);

      double size = refSBaseSizes.get(dimByArrayDim.getId());

      return evaluateBounds(dimensionSizes, index.getMath(), size);
    }
    return false;
  }

  /**
   * Checks if adding an {@link Index} object to a parent
   * {@link SBase} object for referencing another {@link SBase} object does not
   * cause out-of-bounds issues.
   * 
   * @param model
   * @param reference
   * @param arrayDim
   * @param math
   * @param dimSizes
   * @return
   */
  public static boolean evaluateIndexBounds(Model model, SBase reference, int arrayDim, ASTNode math, Map<String, Double> dimSizes) {

    ArraysSBasePlugin refSbasePlugin = (ArraysSBasePlugin) reference.getExtension(ArraysConstants.shortLabel);

    Dimension dim = refSbasePlugin.getDimensionByArrayDimension(arrayDim);

    if (dim == null) {
      return false;
    }

    Parameter paramSize = model.getParameter(dim.getSize());

    if (paramSize == null) {
      return false;
    }

    double size = paramSize.getValue();

    return evaluateBounds(dimSizes, math, size);

  }

  /**
   * Checks if adding an {@link Index} object to a parent
   * {@link SBase} object for referencing another {@link SBase} object does not
   * cause out-of-bounds issues.
   * 
   * @param model
   * @param parent
   * @param refAttribute
   * @param math
   * @param arrayDim
   * @return
   */
  public static boolean evaluateIndexBounds(Model model, SBase parent, String refAttribute, ASTNode math, int arrayDim) {

    String refId = parent.writeXMLAttributes().get(refAttribute);

    SBase reference = model.findNamedSBase(refId);

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);

    ArraysSBasePlugin parentArraysSBasePlugin = (ArraysSBasePlugin) parent.getParentSBMLObject().getExtension(ArraysConstants.shortLabel);

    ArraysSBasePlugin refSbasePlugin = (ArraysSBasePlugin) reference.getExtension(ArraysConstants.shortLabel);

    Dimension dimByArrayDim = refSbasePlugin.getDimensionByArrayDimension(arrayDim);

    if (dimByArrayDim == null) {
      return false;
    }

    Map<String, Double> dimensionSizes = getDimensionSizes(model, arraysSBasePlugin);

    if (parentArraysSBasePlugin != null) {
      dimensionSizes.putAll(getDimensionSizes(model,parentArraysSBasePlugin));
    }

    Map<String, Double> refSBaseSizes = getDimensionSizes(model, refSbasePlugin);

    double size = refSBaseSizes.get(dimByArrayDim.getId());

    return evaluateBounds(dimensionSizes, math, size);

  }

  /**
   * Checks if the arguments of a selector function does not go out of bounds.
   * 
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean evaluateSelectorBounds(Model model, MathContainer mathContainer) {
    ASTNode math = mathContainer.getMath();

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) mathContainer.getExtension(ArraysConstants.shortLabel);

    Map<String, Double> dimensionSizes = getDimensionSizes(model, arraysSBasePlugin);


    SBase parent = mathContainer.getParentSBMLObject();

    while(parent != null)
    {
      ArraysSBasePlugin parentArraysSBasePlugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);
      parent = parent.getParentSBMLObject();
      if (parentArraysSBasePlugin != null)
      {
        dimensionSizes.putAll(getDimensionSizes(model, parentArraysSBasePlugin));
      }
    }

    if (math.getType() != ASTNode.Type.FUNCTION_SELECTOR) {
      return true;
    }

    ASTNode obj = math.getChild(0);

    if (obj.isString())
    {
      boolean result = true;

      SBase sbase = model.findNamedSBase(obj.toString());

      ArraysSBasePlugin plugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      
      if (plugin == null) {
        // System.out.println("ArraysMath.evaluateSelectorBounds(model, mathContainer) - plugin is null - string = " + obj.toString());
        return true; // TODO - not sure if we should return true or false in this case
      }
      
      int i = 1;
      while (i < math.getChildCount()) {
        ASTNode index = math.getChild(i);
        Dimension dim = plugin.getDimensionByArrayDimension(i-1);
        Parameter param = model.getParameter(dim.getSize());

        if (param != null) {
          double size = param.getValue();
          result &= evaluateBounds(dimensionSizes, index, size);
        } else {
          return false;
        }
        return result;
      }

    }
    else if (obj.isVector())
    {
      boolean result = true;

      Map<Integer, Integer> vectorSizes = getVectorDimensionSizes(model, obj);

      for (int i = 1; i < math.getChildCount(); ++i) {
        ASTNode index = math.getChild(i);
        Integer size = vectorSizes.get(i);
        if (size == null) {
          return false; // TODO - not sure if we should return true or false in this case
        }
        result &= evaluateBounds(dimensionSizes, index, size);
      }
      return result;
    }
    else {
      return false;
    }

    return true;
  }

  /**
   * Gets the size of a Dimension object.
   * 
   * @param model
   * @param dimension
   * @return
   */
  public static int getSize(Model model, Dimension dimension) {
    if (dimension == null) {
      return 0;
    }

    String sizeRef = dimension.getSize();

    Parameter param = model.getParameter(sizeRef);

    if (param == null || !param.isSetValue()) {
      throw new SBMLException();
    }

    return (int) param.getValue();
  }


  /**
   * Maps a dimension id to the size of the dimension object.
   * 
   * @param model
   * @param arraysSBasePlugin
   * @return
   */
  public static Map<String, Double> getDimensionSizes(Model model, ArraysSBasePlugin arraysSBasePlugin) {
    Map<String, Double> dimensionValue = new HashMap<String, Double>();

    if (arraysSBasePlugin == null) {
      return dimensionValue;
    }

    for (Dimension dim : arraysSBasePlugin.getListOfDimensions()) {
      if (dim.isSetId()) {
        Parameter size = model.getParameter(dim.getSize());
        if (size != null) {
          dimensionValue.put(dim.getId(), size.getValue());
        }
      }
    }
    return dimensionValue;
  }

  /**
   * Returns the lower bound index from a collection of Dimension objects.
   * 
   * @param dimSizes
   * @return
   */
  public static Map<String, Double> getLowerBound(Map<String, Double> dimSizes) {
    Map<String, Double> lowerBound = new HashMap<String, Double>();

    for (String id : dimSizes.keySet()) {
      lowerBound.put(id, 0.0);
    }

    return lowerBound;
  }

  /**
   * Returns the upper bound index from a collection of Dimension objects.
   * 
   * @param dimSizes
   * @return
   */
  public static Map<String, Double> getUpperBound(Map<String, Double> dimSizes) {
    Map<String, Double> upperBound = new HashMap<String, Double>();

    for (String id : dimSizes.keySet()) {
      upperBound.put(id, dimSizes.get(id) - 1);
    }

    return upperBound;
  }



  /**
   * Determines whether a {@link MathContainer} object is statically computable.
   * 
   * @param model
   * @param index
   * @return
   */
  public static boolean isStaticallyComputable(Model model, Index index) {
    SBase parent = index.getParentSBMLObject().getParentSBMLObject();
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);
    SBase parents = parent.getParentSBMLObject();

    List<String> dimensionIds = new ArrayList<String>();
    if (plugin != null) {
      for (Dimension dim : plugin.getListOfDimensions()) {
        if (dim.isSetId()) {
          dimensionIds.add(dim.getId());
        }
      }
    }

    while(parents != null)
    {
      ArraysSBasePlugin parentArraysSBasePlugin = (ArraysSBasePlugin) parents.getExtension(ArraysConstants.shortLabel);

      if (parentArraysSBasePlugin != null) {
        for (Dimension dim : parentArraysSBasePlugin.getListOfDimensions()) {
          if (dim.isSetId()) {
            dimensionIds.add(dim.getId());
          }
        }
      }
      
      parents = parents.getParentSBMLObject();
    }



    return isStaticallyComputable(model, index, dimensionIds.toArray(new String[dimensionIds.size()]));

  }

  /**
   * Determines whether a {@link MathContainer} object is statically computable.
   * 
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean isStaticallyComputable(Model model, MathContainer mathContainer) {
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) mathContainer.getExtension(ArraysConstants.shortLabel);

    List<String> dimensionIds = new ArrayList<String>();
    if (plugin != null) {
      for (Dimension dim : plugin.getListOfDimensions()) {
        if (dim.isSetId()) {
          dimensionIds.add(dim.getId());
        }
      }
    }
    SBase parent = mathContainer.getParentSBMLObject();

    while(parent != null)
    {
      ArraysSBasePlugin parentArraysSBasePlugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);
      parent = parent.getParentSBMLObject();
      if (parentArraysSBasePlugin != null)
      {
        for (Dimension dim : parentArraysSBasePlugin.getListOfDimensions()) {
          if (dim.isSetId()) {
            dimensionIds.add(dim.getId());
          }
        }
      }
    }

    if (mathContainer instanceof KineticLaw)
    {
      KineticLaw law = (KineticLaw) mathContainer;
      for (LocalParameter local : law.getListOfLocalParameters())
      {
        if (local.isSetId()) {
          dimensionIds.add(local.getId());
        }
      }
    }

    return isStaticallyComputable(model, mathContainer, dimensionIds.toArray(new String[dimensionIds.size()]));

  }

  /**
   * Determines whether a {@link MathContainer} object is statically computable given
   * a list of ids that can appear in the math.
   * 
   * @param model
   * @param mathContainer
   * @param constantIds
   * @return
   */
  public static boolean isStaticallyComputable(Model model, MathContainer mathContainer, String...constantIds) {
    StaticallyComputableCompiler compiler = new StaticallyComputableCompiler(model);

    if (constantIds != null) {
      for (String id : constantIds) {
        compiler.addConstantId(id);
      }
    }
    ASTNode math = mathContainer.getMath();

    if (math == null) {
      return true; // TODO - not sure if we should return true or false in this case
    }
    
    ASTNodeValue value = math.compile(compiler);

    return value.toBoolean();

  }

  /**
   * Determines whether a {@link MathContainer} object is
   * statically computable given a list of ids that can appear in the math.
   * 
   * @param model
   * @param math
   * @param constantIds
   * @return
   */
  public static boolean isStaticallyComputable(Model model, ASTNode math, String...constantIds) {
    StaticallyComputableCompiler compiler = new StaticallyComputableCompiler(model);

    if (constantIds != null) {
      for (String id : constantIds) {
        compiler.addConstantId(id);
      }
    }

    ASTNodeValue value = math.compile(compiler);

    return value.toBoolean();

  }
  /**
   * @param math
   * @return
   */
  public static boolean isVectorOperation(ASTNode math) {
    boolean hasVector = false;

    for (int i = 0; i < math.getChildCount(); ++i) {
      if (math.getChild(i).isVector()) {
        hasVector = true;
        break;
      }
    }

    return hasVector;
  }

  /**
   * 
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean checkVectorMath(Model model, MathContainer mathContainer) {
    VectorCompiler compiler = new VectorCompiler(model);

    if (mathContainer.isSetMath()) {
      ASTNode math = mathContainer.getMath();

      boolean hasVector = isVectorOperation(math);

      if (hasVector)
      {
        math.compile(compiler);

        ASTNode nodeCompiled = compiler.getNode();

        if (nodeCompiled.isVector()) {
          return true;
        }
        else {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * 
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean checkVectorAssignment(Model model, MathContainer mathContainer) {
    VectorCompiler compiler = new VectorCompiler(model);

    if (mathContainer instanceof Assignment) {
      Assignment assignment = (Assignment) mathContainer;

      if (assignment.isSetMath()) {
        ASTNode math = assignment.getMath();

        boolean hasVector = isVectorOperation(math);

        if (hasVector)
        {
          math.compile(compiler);

          ASTNode nodeCompiled = compiler.getNode();

          if (assignment.isSetVariable()){
            SBase variable = model.findNamedSBase(assignment.getVariable());
            if (variable == null) {
              return false;
            }
            Map<Integer, Integer> sizeOfRHS = getVectorDimensionSizes(model, nodeCompiled);
            Map<Integer, Integer> sizeOfLHS = getVectorDimensionSizes(model, new ASTNode(assignment.getVariable()));

            if (sizeOfRHS.size() != sizeOfLHS.size()) {
              return false;
            }

            for (int i = 0; i < sizeOfRHS.size(); ++i) {
              if (sizeOfRHS.get(i) != sizeOfLHS.get(i)) {
                return false;
              }
            }
          } else {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Returns a map containing a pair of array depth level and its size.
   * 
   * @param model
   * @param math
   * @return
   */
  public static Map<Integer, Integer> getVectorDimensionSizes(Model model, ASTNode math) {
    Map<Integer, Integer> sizeByLevel = new HashMap<Integer, Integer>();
    getSizeRecursive(sizeByLevel, model, math, 0);
    return sizeByLevel;
  }

  /**
   * Determines how many levels deep the array is and what is the corresponding
   * size of each level.
   * 
   * @param sizeByLevel
   * @param model
   * @param node
   * @param level
   */
  private static void getSizeRecursive(Map<Integer, Integer> sizeByLevel, Model model, ASTNode node, int level) {
    if (node.isVector()) {
      sizeByLevel.put(level, node.getChildCount());
      if (node.getChildCount() > 0) {
        ASTNode child = node.getChild(0);
        getSizeRecursive(sizeByLevel, model, child, level+1);
      }
    }
    else if (node.isString()) {
      String id = node.toString();
      SBase sbase = model.findNamedSBase(id);
      if (sbase == null) {
        return;
      }
      ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      if (arraysSBasePlugin == null || !arraysSBasePlugin.isSetListOfDimensions()) {
        return;
      }
      int maxDim = arraysSBasePlugin.getDimensionCount() - 1;
      for (Dimension dim : arraysSBasePlugin.getListOfDimensions()) {
        String size = dim.getSize();
        if (size == null) {
          continue;
        }
        Parameter p = model.getParameter(size);
        if (p == null) {
          continue;
        }
        sizeByLevel.put(level+maxDim-dim.getArrayDimension(), (int) p.getValue());
      }
    }
  }

  /**
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean isVectorBalanced(Model model, MathContainer mathContainer) {
    if (model == null || mathContainer == null) {
      return false;
    }
    ASTNode math = mathContainer.getMath();
    if (math.isVector()) {
      Map<Integer, Integer> sizeByLevel = ArraysMath.getVectorDimensionSizes(model, math);
      return checkSizeRecursive(model, sizeByLevel, math, 0);
    }
    return true;
  }

  /**
   * Checka if the array is regular.
   * 
   * @param model
   * @param sizeByLevel
   * @param node
   * @param level
   * @return
   */
  private static boolean checkSizeRecursive(Model model, Map<Integer, Integer> sizeByLevel, ASTNode node, int level) {

    if (!sizeByLevel.containsKey(level)) {
      return false;
    }
    int expected = sizeByLevel.get(level);

    if (!node.isVector()) {
      if (node.isString()) {
        String id = node.toString();
        SBase sbase = model.findNamedSBase(id);
        ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
        for (Dimension dim : arraysSBasePlugin.getListOfDimensions()) {
          String size = dim.getSize();
          Parameter p = model.getParameter(size);
          if (p == null) {
            return false;
          }
          int actual =  (int) p.getValue();
          if (expected != actual) {
            return false;
          }

        }
        return true;
      }
      else {
        return false;
      }
    }
    if (expected != node.getChildCount()) {
      return false;
    }
    for (int i = 0; i < node.getChildCount(); i++) {
      ASTNode child = node.getChild(i);
      if (!checkSizeRecursive(model, sizeByLevel, child, level+1)) {
        return false;
      }
    }
    return true;
  }
}
