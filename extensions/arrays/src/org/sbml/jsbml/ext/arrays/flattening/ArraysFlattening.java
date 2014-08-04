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

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.compiler.ArraysCompiler;
import org.sbml.jsbml.ext.arrays.compiler.VectorCompiler;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


//TODO infix parsing selector where first arg is vector
//TODO update math to use right id
/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jul 9, 2014
 */
public class ArraysFlattening {

  private static final ASTNode unknown = new ASTNode("unknown");

  /**
   * This method flattens out arrays objects of a given {@link SBMLDocument}.
   * 
   * @param document - the document you want to convert.
   * @return new Document that is not associated with the arrays package.
   */
  public static SBMLDocument convert(SBMLDocument document) {
    SBMLDocument flattenedDoc = document.clone();
    Model model = flattenedDoc.getModel();

    List<SBase> itemsToDelete = new ArrayList<SBase>();
    convertEvents(model, itemsToDelete);
    convertReactions(model, itemsToDelete);
    removeSBases(itemsToDelete);

    itemsToDelete = new ArrayList<SBase>();
    convert(flattenedDoc, model, itemsToDelete);
    convertMath(flattenedDoc, model);
    removeSBases(itemsToDelete);
    return flattenedDoc;
  }

  /**
   * 
   * @param itemsToDelete
   */
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
  private static void convertEvents (Model model, List<SBase> itemsToDelete) {
    Enumeration<TreeNode> children = model.getListOfEvents().children();
    while(children.hasMoreElements()) {
      Event event = (Event)children.nextElement();
      convertEvent(model, event, itemsToDelete);
    }
  }

  /**
   * 
   * @param model
   * @param event
   * @param itemsToDelete
   * @throws SBMLException
   */
  private static void convertEvent (Model model, Event event, List<SBase> itemsToDelete) throws SBMLException {
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) event.getExtension(ArraysConstants.shortLabel);

    if(plugin == null) {
      return;
    }

    int dim = plugin.getDimensionCount() - 1;

    VectorCompiler compiler = new VectorCompiler(model, true);
    if(event.isSetTrigger()) {
      Trigger trigger = event.getTrigger();
      ASTNode triggerMath = trigger.getMath();
      triggerMath.compile(compiler);
      ASTNode math = compiler.getNode();
      try {
        if(math.isVector()) {
          trigger.setMath(ASTNode.parseFormula(math.toFormula()));
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    if(event.isSetDelay()) {
      Delay delay = event.getDelay();
      ASTNode delayMath = delay.getMath();
      delayMath.compile(compiler);
      ASTNode math = compiler.getNode();
      try {
        if(math.isVector()) {
          delay.setMath(ASTNode.parseFormula(math.toFormula()));
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    if(event.isSetPriority()) {
      Priority priority = event.getPriority();
      ASTNode priorityMath = priority.getMath();
      priorityMath.compile(compiler);
      ASTNode math = compiler.getNode();
      try {
        if(math.isVector()) {
          priority.setMath(ASTNode.parseFormula(math.toFormula()));
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    expandEvent(model, plugin, model.getListOfEvents(), event, dim);

    itemsToDelete.add(event);

  }

  /**
   * 
   * @param model
   * @param arraysPlugin
   * @param parent
   * @param event
   * @param dim
   */
  private static void expandEvent (Model model, ArraysSBasePlugin arraysPlugin, SBase parent, Event event, int dim) {
    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {      
      event.unsetExtension(ArraysConstants.shortLabel); 

      addToParent(parent, event);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      Event clone = event.clone();
      updateNamedSBase(arraysPlugin, clone, i);
      updateSBase(arraysPlugin, clone, i);
      if(event.isSetDelay()) {
        if(!clone.getDelay().getMath().isVector() || clone.getDelay().getMath().getChildCount() < i) {
          throw new SBMLException();
        }
        clone.getDelay().setMath(clone.getDelay().getMath().getChild(i));
      }
      if(event.isSetTrigger()) {
        if(!clone.getTrigger().getMath().isVector() || clone.getTrigger().getMath().getChildCount() < i) {
          throw new SBMLException();
        }
        clone.getTrigger().setMath(clone.getTrigger().getMath().getChild(i));
      }
      if(event.isSetPriority()) {
        if(!clone.getPriority().getMath().isVector() || clone.getPriority().getMath().getChildCount() < i) {
          throw new SBMLException();
        }
        clone.getPriority().setMath(clone.getPriority().getMath().getChild(i));
      }
      updateEventAssignmentIndex(model, clone, dimension, i);
      expandEvent(model, arraysPlugin, parent, clone, dim-1);
    }
  }

  /**
   * 
   * @param model
   * @param event
   * @param dimension
   * @param index
   */
  private static void updateEventAssignmentIndex(Model model, Event event, Dimension dimension, int index) {
    if(!dimension.isSetId()) {
      return;
    }

    for(EventAssignment eventAssignment : event.getListOfEventAssignments()) {
      if(eventAssignment.isSetMath()) {
        ASTNode math = eventAssignment.getMath();
        String formula = math.toFormula().replaceAll(dimension.getId(), String.valueOf(index));
        try {
          eventAssignment.setMath(ASTNode.parseFormula(formula));
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      updateIndexMath(eventAssignment, dimension, index);
    }
  }

  /**
   * 
   * @param model
   * @param idToIndices
   * @param itemsToDelete
   */
  private static void convertReactions (Model model, List<SBase> itemsToDelete) {
    Enumeration<TreeNode> children = model.getListOfReactions().children();
    while(children.hasMoreElements()) {
      Reaction reaction = (Reaction)children.nextElement();
      convertReaction(model, reaction, itemsToDelete);
    }
  }

  /**
   * 
   * @param model
   * @param reaction
   * @param itemsToDelete
   * @throws SBMLException
   */
  private static void convertReaction (Model model, Reaction reaction, List<SBase> itemsToDelete) throws SBMLException {
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) reaction.getExtension(ArraysConstants.shortLabel);

    if(plugin == null) {
      return;
    }

    int dim = plugin.getDimensionCount() - 1;

    VectorCompiler compiler = new VectorCompiler(model, true);
    if(reaction.isSetKineticLaw()) {
      KineticLaw kinetic = reaction.getKineticLaw();
      ASTNode kineticMath = kinetic.getMath();
      kineticMath.compile(compiler);
      ASTNode math = compiler.getNode();
      try {
        if(!math.equals(unknown)) {
          kinetic.setMath(ASTNode.parseFormula(math.toFormula()));
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    expandReaction(model, plugin, model.getListOfReactions(), reaction, dim);
    itemsToDelete.add(reaction);

  }

  /**
   * 
   * @param arraysPlugin
   * @param reaction
   * @param index
   */
  private static void updateSpecRefId(ArraysSBasePlugin arraysPlugin, Reaction reaction, int index) {

    for(SpeciesReference ref : reaction.getListOfReactants()) {
      updateNamedSBase(arraysPlugin, ref, index);
    }
    for(SpeciesReference ref : reaction.getListOfProducts()) {
      updateNamedSBase(arraysPlugin, ref, index);
    }
  }
  
  /**
   * 
   * @param model
   * @param arraysPlugin
   * @param parent
   * @param reaction
   * @param dim
   */
  private static void expandReaction (Model model, ArraysSBasePlugin arraysPlugin, SBase parent, Reaction reaction, int dim) {
    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {
      reaction.unsetExtension(ArraysConstants.shortLabel); 
      addToParent(parent, reaction);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      Reaction clone = reaction.clone();
      updateNamedSBase(arraysPlugin, clone, i);
      updateSBase(arraysPlugin, clone, i);
      if(reaction.isSetKineticLaw()) {
        ASTNode math = clone.getKineticLaw().getMath();
        String formula = math.toFormula().replaceAll(dimension.getId(), String.valueOf(i));
        try {
          clone.getKineticLaw().setMath(ASTNode.parseFormula(formula));
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      updateSpecRefId(arraysPlugin, clone, i);
      updateSpecRefIndex(model, clone, dimension, i);
      expandReaction(model, arraysPlugin, parent, clone, dim-1);
    }
  }

  /**
   * 
   * @param model
   * @param reaction
   * @param dimension
   * @param index
   */
  private static void updateSpecRefIndex(Model model, Reaction reaction, Dimension dimension, int index) {
    if(!dimension.isSetId()) {
      return;
    }

    for(SpeciesReference ref : reaction.getListOfReactants()) {
      updateIndexMath(ref, dimension, index);
    }
    for(SpeciesReference ref : reaction.getListOfProducts()) {
      updateIndexMath(ref, dimension, index);
    }
    for(ModifierSpeciesReference ref : reaction.getListOfModifiers()) {
      updateIndexMath(ref, dimension, index);
    }
  }

  /**
   * 
   * @param sbase
   * @param dimension
   * @param index
   */
  private static void updateIndexMath(SBase sbase, Dimension dimension, int index) {

    ArraysSBasePlugin plugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);

    if(plugin != null) {
      for(Index i : plugin.getListOfIndices()) {
        if(i.isSetMath()) {
          ASTNode math = i.getMath();
          String formula = math.toFormula().replaceAll(dimension.getId(), String.valueOf(index));
          try {
            i.setMath(ASTNode.parseFormula(formula));
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
      }
    }  
  }

  /**
   * 
   * @param model
   * @param idToIndices
   * @param itemsToDelete
   */
  private static void convert (SBMLDocument doc, Model model, List<SBase> itemsToDelete) {
    Enumeration<TreeNode> children = doc.children();
    while(children.hasMoreElements()) {
      TreeNode child = children.nextElement();
      convert(model, child, itemsToDelete);
    }
  }

  /**
   * This is recursively getting each TreeNode of a certain SBMLDocument.
   * 
   * @param model
   * @param node
   * @param sbases
   */
  private static void convert(Model model, TreeNode node, List<SBase> sbases) {

    Enumeration<?> children = node.children();

    while(children.hasMoreElements()) {
      TreeNode child = (TreeNode) children.nextElement();
      expandDim(model, child, sbases);
      convert(model, child, sbases);
    }
  }

  /**
   * 
   * @param model
   * @param idToIndices
   * @param itemsToDelete
   */
  private static void convertMath (SBMLDocument doc, Model model) {
    Enumeration<TreeNode> children = doc.children();
    while(children.hasMoreElements()) {
      TreeNode child = children.nextElement();
      convertMath(model, child);
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
  private static void convertMath(Model model, TreeNode node) {

    Enumeration<?> children = node.children();
    while(children.hasMoreElements()) {
      TreeNode child = (TreeNode) children.nextElement();
      convertArraysMath(model, child);
      convertMath(model, child);
    }
  }

  /**
   * This expands an SBase that has a list of Dimension objects.
   * 
   * @param model
   * @param child
   * @param sbases
   */
  private static void convertArraysMath(Model model, TreeNode child) {
    if(child instanceof MathContainer) {
      VectorCompiler compiler = new VectorCompiler(model, true);
      MathContainer mathContainer = (MathContainer) child;
      mathContainer.getMath().compile(compiler);
      ASTNode math = compiler.getNode();
      if(!math.equals(unknown)) {
        try {
          mathContainer.setMath(ASTNode.parseFormula(math.toFormula()));
        } catch (SBMLException e) {
          e.printStackTrace();
        } catch (ParseException e) {
          e.printStackTrace();
        }
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
  private static void expandDim(Model model, SBase sbase, SBase parent, ArraysSBasePlugin arraysPlugin,ArraysCompiler compiler, int dim) {

    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {
      sbase.unsetExtension(ArraysConstants.shortLabel); 
      addToParent(parent, sbase);
      convertIndex(model, arraysPlugin, sbase, compiler);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      SBase clone = sbase.clone();
      updateSBase(arraysPlugin, clone, i);
      if(sbase instanceof NamedSBase) {
        updateNamedSBase(arraysPlugin, (NamedSBase) clone, i);
      }
      if(sbase instanceof MathContainer) {
        try {
          updateMathContainer(model, arraysPlugin, (MathContainer) clone, dimension.getId(), i);
        } catch (ParseException e) {

        }
      }

      compiler.addValue(dimension.getId(), i);

      expandDim(model, clone, parent, arraysPlugin,compiler, dim-1);

    }
  }

  /**
   * Add the new SBase to the corresponding ListOf object.
   * 
   * @param parent
   * @param child
   */
  private static void addToParent(SBase parent, SBase child) {
    if(parent instanceof ListOf<?>) {
      @SuppressWarnings("unchecked")
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
        if(index == null) {
          continue;
        }
        ASTNodeValue value = index.getMath().compile(compiler);
        if(value.isNumber()) {
          temp += "_" + value.toInteger();
        } else {
          continue;
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
      if(!math.equals(unknown)) {
        sbase.setMath(math);
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
    if(sbase.isSetMetaId()) {
      String metaId = sbase.getMetaId();
      String newMetaId = metaId + "_" + index;
      sbase.setMetaId(newMetaId);
    }
  }

}

