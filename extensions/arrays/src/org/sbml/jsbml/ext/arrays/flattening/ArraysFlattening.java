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
package org.sbml.jsbml.ext.arrays.flattening;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Assignment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.compiler.ArraysCompiler;
import org.sbml.jsbml.ext.arrays.compiler.VectorCompiler;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

//TODO infix parsing selector where first arg is vector
/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jul 9, 2014
 */
public class ArraysFlattening {

  /**
   * This method flattens out arrays objects of a given {@link SBMLDocument}.
   * 
   * @param document - the document you want to convert.
   * @return new Document that is not associated with the arrays package.
   */
  public static SBMLDocument convert(SBMLDocument document) {
    SBMLDocument flattenedDoc = document.clone();
    Model model = flattenedDoc.getModel();
    Map<SBase, List<Integer>> idToIndices = new HashMap<SBase, List<Integer>>();
    List<SBase> itemsToDelete = new ArrayList<SBase>();
    
    convert(flattenedDoc, model, idToIndices, itemsToDelete);
    convertMath(flattenedDoc, model, idToIndices);
    removeSBases(itemsToDelete);
    
    itemsToDelete = new ArrayList<SBase>();
    idToIndices = new HashMap<SBase, List<Integer>>();
    
    convert(flattenedDoc, model, idToIndices, itemsToDelete);
    convertMath(flattenedDoc, model, idToIndices);
    removeSBases(itemsToDelete);
    
    
    return flattenedDoc;
  }

  private static void removeSBases(List<SBase> itemsToDelete) {
    for(SBase sbase : itemsToDelete) {
      sbase.removeFromParent();
    }
  }
  /**
   * 
   * @param model
   * @param idToIndices
   * @param itemsToDelete
   */
  private static void convert (SBMLDocument doc, Model model,  Map<SBase, List<Integer>> idToIndices, List<SBase> itemsToDelete) {
    Enumeration<TreeNode> children = doc.children();
    while(children.hasMoreElements()) {
      TreeNode child = children.nextElement();
      convert(model, child, idToIndices, itemsToDelete);
    }
  }
  
  /**
   * 
   * @param model
   * @param idToIndices
   * @param itemsToDelete
   */
  private static void convertMath (SBMLDocument doc, Model model,  Map<SBase, List<Integer>> idToIndices) {
    Enumeration<TreeNode> children = doc.children();
    while(children.hasMoreElements()) {
      TreeNode child = children.nextElement();
      convertMath(model, child, idToIndices);
    }
  }
  
  /**
   * This is recursively getting each TreeNode of a certain SBMLDocument.
   * 
   * @param model
   * @param node
   * @param sbases
   */
  private static void convert(Model model, TreeNode node, Map<SBase, List<Integer>> idToIndices, List<SBase> sbases) {

    Enumeration<?> children = node.children();

    while(children.hasMoreElements()) {
      TreeNode child = (TreeNode) children.nextElement();
      expandDim(model, child, idToIndices, sbases);
      convert(model, child, idToIndices, sbases);
    }
  }

  /**
   * This is recursively getting each TreeNode of a certain SBMLDocument and
   * flattening vector/selector MathML objects.
   * 
   * @param model
   * @param node
   * @param sbases
   */
  private static void convertMath(Model model, TreeNode node, Map<SBase, List<Integer>> idToIndices) {

    Enumeration<?> children = node.children();
    while(children.hasMoreElements()) {
      TreeNode child = (TreeNode) children.nextElement();
      convertArraysMath(model, child, idToIndices);
      convertMath(model, child, idToIndices);
    }
  }

  /**
   * This expands an SBase that has a list of Dimension objects.
   * 
   * @param model
   * @param child
   * @param sbases
   */
  private static void convertArraysMath(Model model, TreeNode child, Map<SBase, List<Integer>> idToIndices) {
    if(child instanceof MathContainer) {
      VectorCompiler compiler = new VectorCompiler(model, true);
      MathContainer mathContainer = (MathContainer) child;
      mathContainer.getMath().compile(compiler);
      ASTNode math = compiler.getNode();
      
      if(math.isVector()) {
        if(!idToIndices.containsKey(mathContainer.getParentSBMLObject())) {
          
          return;
        }
        List<Integer> indices = idToIndices.get(mathContainer.getParentSBMLObject());
        if(indices == null) {
          return;
        }
        for(int i = 0; i < indices.size(); ++i) {
          if(!math.isVector()) {
            throw new SBMLException();
          }
          math = math.getChild(indices.get(i));
        }
      }
      
      try {
        List<Integer> tempList = idToIndices.get(mathContainer.getParentSBMLObject());
        idToIndices.remove(mathContainer.getParentSBMLObject());
        mathContainer.setMath(ASTNode.parseFormula(math.toString()));
        idToIndices.put(mathContainer.getParentSBMLObject(), tempList);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * This expands an SBase that has a list of Dimension objects.
   * 
   * @param model
   * @param child
   * @param sbases
   */
  private static void expandDim(Model model, TreeNode child, Map<SBase, List<Integer>> idToIndices, List<SBase> sbases) {
    if(child instanceof SBase) {
      SBase sbase = ((SBase) child);
      ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      if(arraysPlugin == null) {
        return;
      }
      sbases.add(sbase);
      int dim = arraysPlugin.getDimensionCount() - 1;
      ArraysCompiler compiler = new ArraysCompiler();

      expandDim(model, sbase, sbase.getParentSBMLObject(),idToIndices, new ArrayList<Integer>(), arraysPlugin, compiler, dim);
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
   */
  private static void expandDim(Model model, SBase sbase, SBase parent, Map<SBase, List<Integer>> idToIndices, List<Integer> indices, ArraysSBasePlugin arraysPlugin,ArraysCompiler compiler, int dim) {

    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {
      sbase.unsetExtension(ArraysConstants.shortLabel); 
      addToParent(parent, sbase);
      idToIndices.put(sbase, indices);
      convertIndex(model, arraysPlugin, sbase, compiler);
      return;
    }

    int size = getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      SBase clone = sbase.clone();
      if(sbase instanceof NamedSBase) {
        updateNamedSBase(arraysPlugin, (NamedSBase) clone, i);
      }
      if(sbase instanceof MathContainer) {
        try {
          updateMathContainer(model, arraysPlugin, (MathContainer) clone, dimension.getId(), i);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      updateSBase(arraysPlugin, clone, i);

      compiler.addValue(dimension.getId(), i);

      List<Integer> copyList = new ArrayList<Integer>(indices);
      copyList.add(i);
      expandDim(model, clone, parent, idToIndices, copyList, arraysPlugin,compiler, dim-1);

    }
  }

  /**
   * Gets the size of a Dimension object.
   * 
   * @param model
   * @param dimension
   * @return
   */
  private static int getSize(Model model, Dimension dimension) {
    if(dimension == null) {
      return 0;
    }

    String sizeRef = dimension.getSize();

    Parameter param = model.getParameter(sizeRef);

    if(param == null || !param.isSetValue()) {
      throw new SBMLException();
    }

    return (int) param.getValue();
  }

  /**
   * Add the new SBase to the corresponding ListOf object.
   * 
   * @param parent
   * @param child
   */
  private static void addToParent(SBase parent, SBase child) {
    if(parent instanceof ListOf<?>) {
      ListOf<SBase> parentList = (ListOf<SBase>) parent;
      parentList.add(child);
      return;
    }
    throw new SBMLException();
  }

  /**
   * This is flattening index objects by replacing the dimension ids with the corresponding index value
   * and updating the referenced attribute.
   * 
   * @param model
   * @param arraysPlugin
   * @param sbase
   * @param compiler
   */
  private static void convertIndex(Model model, ArraysSBasePlugin arraysPlugin, SBase sbase, ArraysCompiler compiler) {
    if(arraysPlugin.getIndexCount() < 1) {
      return;
    }

    int maxIndex = -1;
    Set<String> attributes = new HashSet<String>();
    for(Index index : arraysPlugin.getListOfIndices()) {
      if(index.getArrayDimension() > maxIndex) {
        maxIndex = index.getArrayDimension();
      }
      if(!attributes.contains(index.getReferencedAttribute())) {
        attributes.add(index.getReferencedAttribute());
      }
    }

    for(String attribute : attributes) {
      String temp = sbase.writeXMLAttributes().get(attribute);
      for(int i = maxIndex; i >= 0; i--) {
        Index index = arraysPlugin.getIndex(i, attribute);

        ASTNodeValue value = index.getMath().compile(compiler);
        if(value.isNumber()) {
          temp += "_" + value.toInteger();
        } else {
          throw new SBMLException();
        }
      }
      sbase.readAttribute(attribute, "", temp);
    }

  }

  /**
   * This updates the id of a NamedSBase.
   * 
   * @param arraysPlugin
   * @param sbase
   * @param index
   */
  private static void updateNamedSBase(ArraysSBasePlugin arraysPlugin, NamedSBase sbase, int index) {
    //TODO: check if unique id
    if(sbase.isSetId()) {
      String id = sbase.getId();
      String newId = id + "_" + index;
      sbase.setId(newId);
    }
  }

  /**
   * This updates the dimension id that appears in the math.
   * 
   * @param model
   * @param arraysPlugin
   * @param sbase
   * @param dimId
   * @param index
   * @throws ParseException
   */
  private static void updateMathContainer(Model model, ArraysSBasePlugin arraysPlugin, MathContainer sbase, String dimId, int index) throws ParseException {
    if(sbase.isSetMath()) {
      String formula = sbase.getMath().toFormula().replaceAll(dimId, String.valueOf(index));
      sbase.setMath(ASTNode.parseFormula(formula));

      VectorCompiler compiler = new VectorCompiler(model, true);
      sbase.getMath().compile(compiler);
      ASTNode math = compiler.getNode();
      
      sbase.setMath(math);
      if(sbase.getMath().isVector()) {
        sbase.setMath(sbase.getMath().getChild(index));
      }
      //TODO: is this ok?
      if(sbase instanceof Assignment && arraysPlugin.getIndexCount() == 0) {
        Assignment assign = (Assignment) sbase;
        assign.setVariable(assign.getVariable() + "_" + index);

      }
    }
  }


  /**
   * This updates the metaid of an SBase.
   * @param arraysPlugin
   * @param sbase
   * @param index
   */
  private static void updateSBase(ArraysSBasePlugin arraysPlugin, SBase sbase, int index) {
    //TODO check if unique
    SBMLDocument document = sbase.getSBMLDocument();
    //document.
    if(sbase.isSetMetaId()) {
      String metaId = sbase.getMetaId();
      String newMetaId = metaId + "_" + index;
      sbase.setMetaId(newMetaId);
    }
  }
}
