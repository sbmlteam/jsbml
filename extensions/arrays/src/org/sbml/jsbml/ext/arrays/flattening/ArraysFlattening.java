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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
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

  public static void main(String[] args) {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      n.setValue(10);
      model.addParameter(n);

      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      InitialAssignment ia = model.createInitialAssignment();
      ia.setVariable("X");
      ia.setMath(ASTNode.parseFormula("{1,2,3,4,5,6,7,8,9,10}"));
      
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);

      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);

      Dimension dimX = new Dimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);

      arraysSBasePluginX.addDimension(dimX);

      Parameter Y = new Parameter("Y");

      model.addParameter(Y);
      Y.setValue(2);
      ArraysSBasePlugin arraysSBasePluginY = new ArraysSBasePlugin(Y);

      Y.addExtension(ArraysConstants.shortLabel, arraysSBasePluginY);
      Dimension dimY = new Dimension("i");
      dimY.setSize(n.getId());
      dimY.setArrayDimension(0);

      arraysSBasePluginY.addDimension(dimY);

      AssignmentRule rule = new AssignmentRule();
      model.addRule(rule);

      ArraysSBasePlugin arraysSBasePluginRule = new ArraysSBasePlugin(rule);
      rule.addExtension(ArraysConstants.shortLabel, arraysSBasePluginRule);

      Dimension dimRule = new Dimension("i");
      dimRule.setSize(n.getId());
      dimRule.setArrayDimension(0);
      arraysSBasePluginRule.addDimension(dimRule);


      Index indexRule = arraysSBasePluginRule.createIndex();
      indexRule.setArrayDimension(0);
      indexRule.setReferencedAttribute("variable");
      ASTNode indexMath = new ASTNode();

      indexMath = ASTNode.diff(new ASTNode(9), new ASTNode("i"));
      indexRule.setMath(indexMath);

      rule.setVariable("Y");
      ASTNode ruleMath = ASTNode.parseFormula("selector(X, i)");

      rule.setMath(ruleMath);
      SBMLWriter.write(convert(doc), System.out, ' ', (short) 2);
    } catch (SBMLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (XMLStreamException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  //  public static void main(String[] args) {
  //    SBMLDocument document = new SBMLDocument(3,1);
  //
  //    Model model = document.createModel();
  //
  //    Species spec = model.createSpecies("s");
  //
  //    Parameter param = model.createParameter("n");
  //
  //    param.setConstant(true);
  //
  //    param.setValue(2);
  //
  //    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);
  //
  //    spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);
  //
  //    Dimension dim = arraysSBasePlugin.createDimension("i");
  //
  //    dim.setArrayDimension(0);
  //
  //    dim.setSize("n");
  //
  //    spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);
  //
  //    Dimension dim2 = arraysSBasePlugin.createDimension("j");
  //
  //    dim2.setArrayDimension(1);
  //
  //    dim2.setSize("n");
  //
  //    try {
  //      SBMLWriter.write(convert(document), System.out, ' ', (short) 2);
  //    } catch (SBMLException e) {
  //      // TODO Auto-generated catch block
  //      e.printStackTrace();
  //    } catch (XMLStreamException e) {
  //      // TODO Auto-generated catch block
  //      e.printStackTrace();
  //    }
  //  }

  //TODO
  public static SBMLDocument convert(SBMLDocument document) {
    SBMLDocument flattenedDoc = document.clone();
    Model model = flattenedDoc.getModel();
    Enumeration<TreeNode> children = flattenedDoc.children();

    List<SBase> itemsToDelete = new ArrayList<SBase>();
    while(children.hasMoreElements()) {
      TreeNode child = children.nextElement();
      convert(model, child, itemsToDelete);
    }

    for(SBase sbase : itemsToDelete) {
      sbase.removeFromParent();
    }

    return flattenedDoc;
  }

  private static void convert(Model model, TreeNode node, List<SBase> sbases) {

    Enumeration<?> children = node.children();

    while(children.hasMoreElements()) {
      TreeNode child = (TreeNode) children.nextElement();
      expandDim(model, child, sbases);
      convert(model, child, sbases);
    }
  }

  private static void expandDim(Model model, TreeNode child, List<SBase> sbases) {
    if(child instanceof SBase) {
      SBase sbase = ((SBase) child);
      ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      if(arraysPlugin == null) {
        return;
      }
      sbases.add(sbase);
      int dim = arraysPlugin.getDimensionCount() - 1;
      ArraysCompiler compiler = new ArraysCompiler();

      expandDim(model, sbase, sbase.getParentSBMLObject(), arraysPlugin, compiler, dim);
    }
  }


  private static void expandDim(Model model, SBase sbase, SBase parent, ArraysSBasePlugin arraysPlugin,ArraysCompiler compiler, int dim) {

    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {
      addToParent(parent, sbase);
      convertIndex(model, arraysPlugin, sbase, compiler);
      return;
    }

    int size = getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      SBase clone = sbase.clone();
      sbase.disablePackage(ArraysConstants.shortLabel);
      if(sbase instanceof NamedSBase) {
        updateNamedSBase(arraysPlugin, (NamedSBase) clone, i);
      }
      if(sbase instanceof MathContainer) {
        try {
          updateMathContainer(model, arraysPlugin, (MathContainer) clone, dimension.getId(), i);
        } catch (ParseException e) {
          // TODO 
          e.printStackTrace();
        }
      }
      updateSBase(arraysPlugin, clone, i);

      compiler.addValue(dimension.getId(), i);

      expandDim(model, clone, parent, arraysPlugin,compiler, dim-1);

    }
  }

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

  private static void addToParent(SBase parent, SBase child) {
    if(parent instanceof ListOf<?>) {
      ListOf<SBase> parentList = (ListOf<SBase>) parent;
      parentList.add(child);
      return;
    }
    throw new SBMLException();
  }

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

  private static void updateNamedSBase(ArraysSBasePlugin arraysPlugin, NamedSBase sbase, int index) {
    //TODO: check if unique id
    if(sbase.isSetId()) {
      String id = sbase.getId();
      String newId = id + "_" + index;
      sbase.setId(newId);
    }
  }

  private static void updateMathContainer(Model model, ArraysSBasePlugin arraysPlugin, MathContainer sbase, String dimId, int index) throws ParseException {
    if(sbase.isSetMath()) {
      String formula = sbase.getMath().toFormula().replaceAll(dimId, String.valueOf(index));
      sbase.setMath(ASTNode.parseFormula(formula));
      if(sbase.getMath().getType() == ASTNode.Type.FUNCTION_SELECTOR){
        VectorCompiler compiler = new VectorCompiler(model);
        sbase.getMath().getChild(0).compile(compiler);
        sbase.getMath().replaceChild(0, compiler.getNode());
      }
    }
  }

  private static void updateSBase(ArraysSBasePlugin arraysPlugin, SBase sbase, int index) {
    if(sbase.isSetMetaId()) {
      String metaId = sbase.getMetaId();
      String newMetaId = metaId + "_" + index;
      sbase.setMetaId(newMetaId);
    }
  }
}
