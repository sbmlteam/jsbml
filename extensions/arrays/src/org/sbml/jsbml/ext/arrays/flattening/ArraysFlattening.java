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
package org.sbml.jsbml.ext.arrays.flattening;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.compiler.ArraysCompiler;
import org.sbml.jsbml.ext.arrays.compiler.VectorCompiler;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.SBaseRef;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

/**
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArraysFlattening {

  //TODO: metaid event has implicit dimension
  /**
   * 
   */
  private static final ASTNode unknown = new ASTNode("unknown");

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ArraysFlattening.class);


  /**
   * This method flattens out arrays objects of a given {@link SBMLDocument}.
   * 
   * @param document - the document you want to convert.
   * @return new Document that is not associated with the arrays package.
   */
  public static SBMLDocument convert(SBMLDocument document) {

    try
    {
      SBMLDocument flattenedDoc = document.clone();
      Model model = flattenedDoc.getModel();

      Map<String, ASTNode> idToVector = new HashMap<String, ASTNode>();
      getVectors(flattenedDoc.getModel(), idToVector);

      convert(flattenedDoc, model, new ArraysCompiler(), idToVector, new ArrayList<Integer>());
      convertMath(flattenedDoc, model, idToVector);

      return flattenedDoc;
    }
    catch(SBMLException exception)
    {
      logger.error(exception.getMessage());
      return null;
    }
  }

  /**
   * Add the new {@link SBase} to the corresponding ListOf object.
   * 
   * @param model
   * @param parent
   * @param child
   */
  private static void addToParent(Model model, SBase parent, SBase child) {

    if(parent == null)
    {
      throw new SBMLException(MessageFormat.format(
        "Could not add SBase {0} a null parent. Flattening Failed.", child));
    }

    if (parent instanceof ListOf<?>) {
      @SuppressWarnings("unchecked")
      ListOf<SBase> parentList = (ListOf<SBase>) parent;
      //      if (child instanceof NamedSBase) {
      //        NamedSBase namedSbase = (NamedSBase) child;
      //        String id = namedSbase.getId();
      //        while(model.findNamedSBase(id) != null) {
      //          id = id.replaceFirst("_", "__");
      //        }
      //        namedSbase.setId(id);
      //
      //      }
      try{
        parentList.add(child);
      }
      catch(IllegalArgumentException exception)
      {
        throw new SBMLException(MessageFormat.format(
          "Could not add SBase {0} because this object has an id that"
              + " is already present in the model. Flattening Failed.", child));
      }
      return;
    }
    if (parent instanceof Port)
    {
      if (child instanceof SBaseRef)
      {
        Port port = ((Port)parent);
        port.setSBaseRef(((SBaseRef)child));
        return;
      }
    }

    throw new SBMLException(MessageFormat.format(
      "Could not add SBase {0} to the model because {1} does not"
          + " have the correct type (ListOf).", child, parent));
  }

  /**
   * This is recursively getting each TreeNode of a certain SBMLDocument.
   * 
   * @param model
   * @param node
   * @param compiler
   * @param idToVector
   * @param indices
   */
  private static void convert(Model model, TreeNode node,
    ArraysCompiler compiler, Map<String, ASTNode> idToVector, List<Integer> indices) {
    boolean isExpanded;
    for (int i = node.getChildCount() - 1; i >= 0; i--) {
      TreeNode child = node.getChildAt(i);
      isExpanded = expandDim(model, child, compiler.clone(), idToVector, indices);
      if(!isExpanded) {
        convert(model, child, compiler, idToVector, indices);
      }
    }
  }

  /**
   * Recursively converts arrays objects.
   * 
   * @param doc
   * @param model
   * @param compiler
   * @param idToVector
   * @param indices
   */
  private static void convert (SBMLDocument doc, Model model, ArraysCompiler compiler, Map<String, ASTNode> idToVector, List<Integer> indices) {
    for (int i = doc.getChildCount() - 1; i >= 0; i--) {
      TreeNode child = doc.getChildAt(i);
      convert(model, child, compiler, idToVector, indices);
    }
  }

  /**
   * Evaluates selector math.
   * 
   * @param model
   * @param child
   * @param idToVector
   */
  private static void convertArraysMath(Model model, TreeNode child, Map<String, ASTNode> idToVector) {
    if (child instanceof MathContainer) {
      VectorCompiler compiler = new VectorCompiler(model, true, idToVector);
      MathContainer mathContainer = (MathContainer) child;
      if(mathContainer.isSetMath())
      {
        mathContainer.getMath().compile(compiler);
        ASTNode math = compiler.getNode();
        if (!math.equals(unknown)) {
          try {
            mathContainer.setMath(math);
          } catch (SBMLException e) {
            throw new SBMLException(MessageFormat.format(
              "The math of the object {0} could not be evaluated. Flattening Failed.", mathContainer));
          }
        }
      }
    }
  }

  /**
   * This is flattening index objects by replacing the dimension ids with the corresponding index value
   * and updating the referenced attribute.
   * 
   * @param model
   * @param arraysPlugin
   * @param sbase
   * @param compiler
   * @param idToVector
   * @param indices
   */
  private static void convertIndex(Model model, ArraysSBasePlugin arraysPlugin, SBase sbase, ArraysCompiler compiler, Map<String,
    ASTNode> idToVector, List<Integer> indices) {
    if (arraysPlugin.getIndexCount() < 1) {
      return;
    }

    int maxIndex = -1;
    Set<String> attributes = new HashSet<String>();
    for (Index index : arraysPlugin.getListOfIndices()) {
      if (index.isSetArrayDimension() && index.getArrayDimension() > maxIndex) {
        maxIndex = index.getArrayDimension();
      }
      if (index.isSetReferencedAttribute() && !attributes.contains(index.getReferencedAttribute())) {
        attributes.add(index.getReferencedAttribute());
      }
    }

    for (String attribute : attributes) {
      String indexValue = getIndexedId(arraysPlugin, sbase, attribute, maxIndex, compiler, idToVector, indices);
      String[] parse = attribute.split(":");
      if (parse.length == 2) {
        sbase.readAttribute(parse[1], parse[0], indexValue);
      } else {
        sbase.readAttribute(attribute, "", indexValue);
      }
    }

  }

  /**
   * This is recursively getting each TreeNode of a certain SBMLDocument and
   * flattening vector/selector MathML objects.
   * 
   * @param model
   * @param node
   * @param idToVector
   */
  private static void convertMath(Model model, TreeNode node, Map<String, ASTNode> idToVector)
  {
    for (int i = node.getChildCount() - 1; i >= 0; i--) {
      TreeNode child = node.getChildAt(i);
      convertArraysMath(model, child, idToVector);
      convertMath(model, child, idToVector);
    }
  }

  /**
   * Recursively evaluates selector math.
   * 
   * @param doc
   * @param model
   * @param idToVector
   */
  private static void convertMath (SBMLDocument doc, Model model, Map<String, ASTNode> idToVector) {
    for (int i = doc.getChildCount() - 1; i >= 0; i--) {
      TreeNode child = doc.getChildAt(i);
      convertMath(model, child, idToVector);
    }
  }

  /**
   * This method is transforming the attributes of a certain SBase object associated with the arrays package
   * so that the SBase no longer uses the package.
   * 
   * @param model
   * @param sbase
   * @param parent
   * @param arraysPlugin
   * @param compiler
   * @param vector
   * @param dim
   * @param idToVector
   * @param indices
   */
  private static void expandDim(Model model, NamedSBase sbase, SBase parent, ArraysSBasePlugin arraysPlugin,ArraysCompiler compiler,
    ASTNode vector, int dim, Map<String, ASTNode> idToVector, List<Integer> indices) {

    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if (dimension == null) {
      sbase.unsetExtension(ArraysConstants.shortLabel);

      if (sbase.isSetId() && vector != null && vector.isName()) {
        sbase.setId(vector.getName());
      }

      convertIndex(model, arraysPlugin, sbase, compiler, idToVector, indices);

      for (int i = sbase.getChildCount() - 1; i >= 0; i--)
      {
        convert(model, sbase.getChildAt(i), compiler.clone(), idToVector, indices);
      }

      addToParent(model, parent, sbase);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for (int i = 0; i < size; i++) {
      NamedSBase clone = (NamedSBase) sbase.clone();
      ASTNode nodeCopy = vector.clone();
      nodeCopy = nodeCopy.getChild(i);
      updateSBase(model.getSBMLDocument(), arraysPlugin, clone, i);
      updateMath(clone, dimension.getId(), i);
      compiler.addValue(dimension.getId(), i);
      List<Integer> cloneIndices = new ArrayList<Integer>(indices);
      cloneIndices.add(i);
      expandDim(model, clone, parent, arraysPlugin, compiler.clone(), nodeCopy, dim-1, idToVector, cloneIndices);
    }
  }

  /**
   * This method is transforming the attributes of a certain SBase object associated with the arrays package
   * so that the SBase no longer uses the package.
   * 
   * @param model
   * @param sbase
   * @param parent
   * @param arraysPlugin
   * @param compiler
   * @param dim
   * @param idToVector
   * @param indices
   */
  private static void expandDim(Model model, SBase sbase, SBase parent, ArraysSBasePlugin arraysPlugin,ArraysCompiler compiler, int dim, Map<String, ASTNode> idToVector, List<Integer> indices) {

    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if (dimension == null) {
      sbase.unsetExtension(ArraysConstants.shortLabel);
      convertIndex(model, arraysPlugin, sbase, compiler, idToVector, indices);

      for (int i = sbase.getChildCount() - 1; i >= 0; i--)
      {
        convert(model, sbase.getChildAt(i), compiler.clone(), idToVector, indices);
      }

      addToParent(model, parent, sbase);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for (int i = 0; i < size; i++) {
      SBase clone = sbase.clone();
      updateSBase(model.getSBMLDocument(), arraysPlugin, clone, i);
      updateMath(clone, dimension.getId(), i);
      compiler.addValue(dimension.getId(), i);
      List<Integer> cloneIndices = new ArrayList<Integer>(indices);
      cloneIndices.add(i);
      expandDim(model, clone, parent, arraysPlugin,compiler.clone(), dim-1, idToVector, cloneIndices);
    }
  }

  /**
   * This expands an SBase that has a list of Dimension objects.
   * 
   * @param model
   * @param child
   * @param compiler
   * @param idToVector
   * @param indices
   * @return
   */
  private static boolean expandDim(Model model, TreeNode child, ArraysCompiler compiler, Map<String, ASTNode> idToVector, List<Integer> indices) {
    if (child instanceof SBase) {
      SBase sbase = ((SBase) child);
      ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

      if (arraysPlugin == null) {
        return false;
      }

      int dim = arraysPlugin.getDimensionCount() - 1;

      if (dim < 0) {
        convertIndex(model, arraysPlugin, sbase, compiler, idToVector, indices);
        sbase.unsetExtension(ArraysConstants.shortLabel);
        return false;
      }

      if (child instanceof NamedSBase) {
        expandDim(model, (NamedSBase)sbase.clone(), sbase.getParentSBMLObject(),
          arraysPlugin, compiler, idToVector.get(((NamedSBase) child).getId()),
          dim, idToVector, indices);
      } else {
        expandDim(model, sbase.clone(), sbase.getParentSBMLObject(), arraysPlugin, compiler, dim, idToVector, indices);
      }

      sbase.removeFromParent();

      return true;
    }

    return false;
  }


  /**
   * 
   * Gets the flattened id given the index values of a certain object.
   * 
   * @param arraysPlugin
   * @param sbase
   * @param attribute
   * @param maxIndex
   * @param compiler
   * @param idToVector
   * @param indices
   * @return
   */
  private static String getIndexedId(ArraysSBasePlugin arraysPlugin, SBase sbase, String attribute, int maxIndex,
    ArraysCompiler compiler, Map<String, ASTNode> idToVector, List<Integer> indices)
  {
    String temp = sbase.writeXMLAttributes().get(attribute);
    if(temp == null)
    {
      throw new SBMLException(MessageFormat.format(
        "Unable to get the value of attribute {0} of object {1}. Flattening Failed.", attribute, sbase));
    }

    ASTNode vector = idToVector.get(temp);

    if (vector == null)
    {
      String append = "";
      for (int i = maxIndex; i >= 0; i--) {
        Index index = arraysPlugin.getIndex(i, attribute);
        if (index == null) {
          throw new SBMLException(MessageFormat.format(
            "Unable to get index with arrayDimension {0} and referencedAttribute {1}. Flattening Failed.", i, attribute));
        }
        ASTNodeValue value = index.getMath().compile(compiler);
        if (value.isNumber()) {
          append = append + "_" + value.toInteger();
        } else {
          throw new SBMLException(MessageFormat.format(
            "Index math should be evaluated to a scalar, but Index {0} evaluates to a vector. Flattening Failed.", index));
        }
      }
      return temp + append;
    }
    else{
      for (int i = maxIndex; i >= 0; i--) {
        Index index = arraysPlugin.getIndex(i, attribute);
        if (index == null) {
          throw new SBMLException(MessageFormat.format(
            "Unable to get index with arrayDimension {0} and referencedAttribute {1}. Flattening Failed.", i, attribute));
        }
        ASTNodeValue value = index.getMath().compile(compiler);
        if (value.isNumber()) {
          vector = vector.getChild(value.toInteger());
        } else {
          throw new SBMLException(MessageFormat.format(
            "Index math should be evaluated to a scalar, but Index {0} evaluates to a vector. Flattening Failed.", index));
        }
      }

      if(vector.isName()) {
        return vector.getName();
      }

      int numOfImplicit = indices.size() - arraysPlugin.getDimensionCount();

      for(int i = 0; i < numOfImplicit; i++)
      {
        if(vector != null) {
          vector = vector.getChild(indices.get(i));
        } else {
          throw new SBMLException(MessageFormat.format(
            "Could not flatten the value for {0}. Flattening Failed.", temp));
        }
      }

      return vector.getName();
    }
  }

  /**
   * Get vector with flattened ids.
   * 
   * @param sbase
   * @param ids
   */
  private static void getVectors(SBase sbase, Map<String, ASTNode> ids) {
    for (int i = sbase.getChildCount() - 1; i >= 0; i--) {
      TreeNode node = sbase.getChildAt(i);
      if (node instanceof NamedSBase) {
        NamedSBase namedSbase = (NamedSBase) node;
        ASTNode idMath = new ASTNode(namedSbase.getId());
        VectorCompiler compiler = new VectorCompiler(sbase.getModel(), namedSbase, true);
        idMath.compile(compiler);
        idMath = compiler.getNode();
        if (idMath.isVector()) {
          ids.put(namedSbase.getId(), idMath);
        }
      }
      if (node instanceof SBase) {
        getVectors((SBase)node, ids);
      }
    }
  }

  /**
   * Replaces dimension id with the appropriate integer value.
   * 
   * @param math
   * @param id
   * @param index
   */
  private static void recursiveReplaceDimensionId(ASTNode math, String id, int index) {
    if (math.getChildCount() == 0) {
      if (math.isString() && math.getName().equals(id)) {
        math.setValue(index);
      }
      return;
    }

    for (int i = 0; i < math.getChildCount(); ++i) {
      recursiveReplaceDimensionId(math.getChild(i), id, index);
    }
  }


  /**
   * Recursively replaces dimension id with the appropriate integer value.
   * 
   * @param math
   * @param id
   * @param index
   * @return
   */
  private static ASTNode replaceDimensionId(ASTNode math, String id, int index) {
    ASTNode clone = math.clone();
    recursiveReplaceDimensionId(clone, id, index);
    return clone;
  }

  /**
   * This updates the dimension id that appears in the math.
   * 
   * @param sbase
   * @param dimId
   * @param index
   */
  private static void updateMath (SBase sbase, String dimId, int index) {
    if(sbase instanceof MathContainer)
    {
      MathContainer mathContainer = (MathContainer) sbase;
      if(mathContainer.isSetMath()) {
        mathContainer.setMath(replaceDimensionId(mathContainer.getMath(), dimId, index));
      }
      else
      {
        throw new SBMLException(MessageFormat.format(
          "MathContainer {0} does not have a math element associated with it. Flattening Failed.", mathContainer));
      }
    }

    for(int i = sbase.getChildCount() - 1; i >= 0; i--)
    {
      TreeNode child = sbase.getChildAt(i);

      if(child instanceof SBase)
      {
        updateMath((SBase) child, dimId, index);
      }
    }
  }

  //  /**
  //   *
  //   * @param sbase
  //   * @param dimId
  //   * @param index
  //   */
  //  private static void updateIndexMath(SBase sbase, String dimId, int index)
  //  {
  //    ArraysSBasePlugin plugin = (ArraysSBasePlugin) sbase.getPlugin("arrays");
  //
  //    for(Index i : plugin.getListOfIndices())
  //    {
  //      i.setMath(replaceDimensionId(i.getMath(), dimId, index));
  //    }
  //  }

  /**
   * This updates the metaid of an SBase.
   * 
   * @param doc
   * @param arraysPlugin
   * @param sbase
   * @param index
   */
  private static void updateSBase(SBMLDocument doc, ArraysSBasePlugin arraysPlugin, SBase sbase, int index) {
    //TODO check if unique
    if (sbase.isSetMetaId()) {
      String metaId = sbase.getMetaId();

      String appendId = "_" + index;
      while(doc.findSBase(metaId + appendId) != null) {
        appendId = "_" + appendId;
      }
      sbase.setMetaId(metaId+appendId);
    }
  }

}

