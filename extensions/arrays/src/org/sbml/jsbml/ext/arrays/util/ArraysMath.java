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
package org.sbml.jsbml.ext.arrays.util;

import java.util.HashMap;
import java.util.Map;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.compiler.ArraysCompiler;
import org.sbml.jsbml.ext.arrays.compiler.StaticallyComputableCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jun 26, 2014
 */
public class ArraysMath {

  /**
   * This method is used to check if the math does not evaluate to a negative number or to a value greater or
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

    ASTNodeValue mathValue = math.compile(compiler);

    if(mathValue.isNumber()) {
      if(mathValue.toDouble() < 0 || mathValue.toDouble() >= size) {
        return false;
      }
    }
    else {
      return false;
    }

    compiler.setidToValue(getUpperBound(dimensionSizes));

    mathValue = math.compile(compiler);

    if(mathValue.isNumber()) {
      if(mathValue.toDouble() < 0 || mathValue.toDouble() >= size) {
        return false;
      }
    }
    else {
      return false;
    }

    return true;
  }
  
  /**
   * This method is used to check if the index math does not go out of bounds.
   * 
   * @param model
   * @param index
   * @return
   */
  public static boolean evaluateIndexBounds(Model model, Index index) {
    //TODO test it
    SBase parent = index.getParentSBMLObject().getParentSBMLObject();

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) parent.getExtension(ArraysConstants.shortLabel);

    if(index.isSetReferencedAttribute()) {
      String refValue = parent.writeXMLAttributes().get(index.getReferencedAttribute());

      SBase refSBase = model.findNamedSBase(refValue);

      ArraysSBasePlugin refSbasePlugin = (ArraysSBasePlugin) refSBase.getExtension(ArraysConstants.shortLabel);

      Dimension dimByArrayDim = arraysSBasePlugin.getDimensionByArrayDimension(index.getArrayDimension());

      Map<String, Double> dimensionSizes = getDimensionSizes(model, arraysSBasePlugin);

      Map<String, Double> refSBaseSizes = getDimensionSizes(model, refSbasePlugin);

      double size = refSBaseSizes.get(dimByArrayDim.getId());

      return evaluateBounds(dimensionSizes, index.getMath(), size);
    }
    return false;
  }

  /**
   * This is used to check if the arguments of a selector function does not go out of bounds.
   * 
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean evaluateSelectorBounds(Model model, MathContainer mathContainer) {
    ASTNode math = mathContainer.getMath();

    ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) mathContainer.getExtension(ArraysConstants.shortLabel);

    Map<String, Double> dimensionSizes = getDimensionSizes(model, arraysSBasePlugin);

    if(math.getType() != ASTNode.Type.FUNCTION_SELECTOR) {
      return true;
    }

    ASTNode obj = math.getChild(0);

    if(obj.isString())
    {
      boolean result = true;

      SBase sbase = model.findNamedSBase(obj.toString());

      ArraysSBasePlugin plugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

      for(int i = 1; i < math.getChildCount(); ++i) {
        ASTNode index = math.getChild(i);

        Dimension dim = plugin.getDimensionByArrayDimension(i-1);

        Parameter param = model.getParameter(dim.getSize());

        if(param != null) {
          double size = param.getValue();
          result &= evaluateBounds(dimensionSizes, index, size);
        }
        else {
          return false;
        }

        return result;

      }

    }
    else if(obj.isVector())
    {
      boolean result = true;

      Map<Integer, Integer> vectorSizes = getVectorDimensionSizes(model, obj);

      for(int i = 1; i < math.getChildCount(); ++i) {
        ASTNode index = math.getChild(i);
        double size = vectorSizes.get(i);
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
   * This method maps a dimension id to the size of the dimension object.
   * 
   * @param model
   * @param arraysSBasePlugin
   * @return
   */
  public static Map<String, Double> getDimensionSizes(Model model, ArraysSBasePlugin arraysSBasePlugin) {
    Map<String, Double> dimensionValue = new HashMap<String, Double>();

    for(Dimension dim : arraysSBasePlugin.getListOfDimensions()) {
      if(dim.isSetId()) {
        Parameter size = model.getParameter(dim.getSize());
        if(size != null) {
          dimensionValue.put(dim.getId(), size.getValue());
        }
      }
    }
    return dimensionValue;
  }

  /**
   * This method is used to get the lower bound index from a collection of Dimension objects. 
   * 
   * @param dimSizes
   * @return
   */
  public static Map<String, Double> getLowerBound(Map<String, Double> dimSizes) {
    Map<String, Double> lowerBound = new HashMap<String, Double>();

    for(String id : dimSizes.keySet()) {
      lowerBound.put(id, 0.0);
    }

    return lowerBound;
  }

  /**
   * This method is used to get the upper bound index from a collection of Dimension objects. 
   * 
   * @param dimSizes
   * @return
   */
  public static Map<String, Double> getUpperBound(Map<String, Double> dimSizes) {
    Map<String, Double> upperBound = new HashMap<String, Double>();

    for(String id : dimSizes.keySet()) {
      upperBound.put(id, dimSizes.get(id) - 1);
    }

    return upperBound;
  }

  /**
   * This method is used to determine whether a {@link MathContainer} object is statically computable.
   * 
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean isStaticallyComputable(Model model, MathContainer mathContainer) {
    StaticallyComputableCompiler compiler = new StaticallyComputableCompiler(model);
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) mathContainer.getExtension(ArraysConstants.shortLabel);
    if(plugin != null) {
      for(Dimension dim : plugin.getListOfDimensions()) {
        if(dim.isSetId()) {
          compiler.addConstantId(dim.getId());
        }
      }
    }
    ASTNode math = mathContainer.getMath();

    ASTNodeValue value = math.compile(compiler);

    return value.toBoolean();

  }

  /**
   * This method is used to determine whether a {@link MathContainer} object is statically computable given
   * a list of ids that can appear in the math.
   * 
   * @param model
   * @param mathContainer
   * @return
   */
  public static boolean isStaticallyComputable(Model model, MathContainer mathContainer, String...constantIds) {
    StaticallyComputableCompiler compiler = new StaticallyComputableCompiler(model);
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) mathContainer.getExtension(ArraysConstants.shortLabel);
    if(plugin != null) 
    {
      for(Dimension dim : plugin.getListOfDimensions()) {
        if(dim.isSetId()) {
          compiler.addConstantId(dim.getId());
        }
      }
    }
    for(String id : constantIds) {
      compiler.addConstantId(id);  
    }
    ASTNode math = mathContainer.getMath();

    ASTNodeValue value = math.compile(compiler);

    return value.toBoolean();

  }

  /**
   * This is used to obtain a map containing a pair of array depth level and its size.
   * 
   * @param math
   * @return
   */
  public static Map<Integer, Integer> getVectorDimensionSizes(Model model, ASTNode math) {
    Map<Integer, Integer> sizeByLevel = new HashMap<Integer, Integer>();
    getSizeRecursive(sizeByLevel, model, math, 0);
    return sizeByLevel;
  }

  /**
   * This is used to determine how many levels deep the array is and what is the corresponding
   * size of each level.
   * 
   * @param sizeByLevel
   * @param node
   * @param level
   */
  private static void getSizeRecursive(Map<Integer, Integer> sizeByLevel, Model model, ASTNode node, int level) {
    if(node.isVector()) {
      sizeByLevel.put(level, node.getChildCount());
      if(node.getChildCount() > 0) {
        ASTNode child = node.getChild(0);
        getSizeRecursive(sizeByLevel, model, child, level+1);
      }
    }
    else if(node.isString()) {
      String id = node.toString();
      SBase sbase = model.findNamedSBase(id);
      ArraysSBasePlugin arraysSBasePlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      for(Dimension dim : arraysSBasePlugin.getListOfDimensions()) {
        String size = dim.getSize();
        Parameter p = model.getParameter(size);
        if (p == null) {
          continue;
        }
        sizeByLevel.put(level+dim.getArrayDimension(), (int) p.getValue());
      }
    }
  }
}
