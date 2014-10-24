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
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.SBaseRef;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

/**
 * 
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jul 9, 2014
 */
public class ArraysFlattening {

  //TODO: metaid event has implicit dimension
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
    Map<String, ASTNode> idToVector = new HashMap<String, ASTNode>();
    getVectors(flattenedDoc.getModel(), idToVector);

    convertEvents(model, itemsToDelete, idToVector);
    convertReactions(model, itemsToDelete, idToVector);
    removeSBases(itemsToDelete);

    itemsToDelete = new ArrayList<SBase>();
    convert(flattenedDoc, model, new ArraysCompiler(), itemsToDelete, idToVector);
    convertMath(flattenedDoc, model, idToVector);
    removeSBases(itemsToDelete);
    return flattenedDoc;
  }

  /**
   * This method removes a list of SBase from the respective parent. 
   * 
   * @param itemsToDelete
   */
  private static void removeSBases(List<SBase> itemsToDelete) {
    for(SBase sbase : itemsToDelete) {
      sbase.removeFromParent();
    }
  }

  private static void getVectors(SBase sbase, Map<String, ASTNode> ids) {
    for(int i = sbase.getChildCount() - 1; i >= 0; i--) {
      TreeNode node = sbase.getChildAt(i);
      if(node instanceof NamedSBase) {
        NamedSBase namedSbase = (NamedSBase)sbase.getChildAt(i);
        ASTNode idMath = new ASTNode(namedSbase.getId());
        VectorCompiler compiler = new VectorCompiler(sbase.getModel(), namedSbase, true);
        idMath.compile(compiler);
        idMath = compiler.getNode();
        if(idMath.isVector()) {
          ids.put(namedSbase.getId(), idMath);
        }
      }
      if(node instanceof SBase) {
        getVectors((SBase)node, ids);
      }
    }
  }



  /**
   * Iterate through the events in the model and convert the events using the arrays package.
   * 
   * @param model
   * @param idToIndices
   * @param itemsToDelete
   */
  private static void convertEvents (Model model, List<SBase> itemsToDelete, Map<String, ASTNode> idToVector) {
    Enumeration<TreeNode> children = model.getListOfEvents().children();

    while(children.hasMoreElements()) {
      Event event = (Event)children.nextElement();
      convertEvent(model, event, itemsToDelete, idToVector);
    }
  }

  /**
   * When an event is converted, the MathContainer objects (trigger, delay, and priority) are expanded.
   * The event is expanded afterwards in case it is an array.
   * 
   * @param model
   * @param event
   * @param itemsToDelete
   * @throws SBMLException
   */
  private static void convertEvent (Model model, Event event, List<SBase> itemsToDelete, Map<String, ASTNode> idToVector) throws SBMLException {
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) event.getExtension(ArraysConstants.shortLabel);

    if(plugin == null) {
      return;
    }

    int dim = plugin.getDimensionCount() - 1;

    VectorCompiler compiler = new VectorCompiler(model, true, idToVector);
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

    expandEvent(model, plugin, model.getListOfEvents(), event, idToVector.get(event.getId()), dim);

    itemsToDelete.add(event);

  }

  /**
   * This method is inlining the event objects present in an array.
   * 
   * @param model
   * @param arraysPlugin
   * @param parent
   * @param event
   * @param dim
   */
  private static void expandEvent (Model model, ArraysSBasePlugin arraysPlugin, SBase parent, Event event, ASTNode vector, int dim) {
    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {      
      event.unsetExtension(ArraysConstants.shortLabel); 
      if(event.isSetId() && vector.isName()) {
        event.setId(vector.getName());
      }
      addToParent(model, parent, event);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      Event clone = event.clone();
      updateSBase(model.getSBMLDocument(), arraysPlugin, clone, i);
      if(event.isSetDelay()) {
        if(clone.getDelay().getMath().isVector()) {
          if(clone.getDelay().getMath().getChildCount() > i) {
            clone.getDelay().setMath(clone.getDelay().getMath().getChild(i));
          } else {
            throw new SBMLException();
          }
        }
        ASTNode math = clone.getDelay().getMath();
        clone.getDelay().setMath(replaceDimensionId(math, dimension.getId(), i));
      }
      if(event.isSetTrigger()) {
        if(clone.getTrigger().getMath().isVector()) {
          if(clone.getTrigger().getMath().getChildCount() > i) {
            clone.getTrigger().setMath(clone.getTrigger().getMath().getChild(i));
          } else {
            throw new SBMLException();
          }
        }
        ASTNode math = clone.getTrigger().getMath();
        clone.getTrigger().setMath(replaceDimensionId(math, dimension.getId(), i));
      }
      if(event.isSetPriority()) {
        if(clone.getPriority().getMath().isVector()) {
          if(clone.getPriority().getMath().getChildCount() > i) {
            clone.getPriority().setMath(clone.getPriority().getMath().getChild(i));
          } else {
            throw new SBMLException();
          }
        }
        ASTNode math = clone.getPriority().getMath();
        clone.getPriority().setMath(replaceDimensionId(math, dimension.getId(), i));
      }
      updateEventAssignmentIndex(model, clone, dimension, i);
      updateEventChildrenMetaId(model, arraysPlugin, clone, i);
      ASTNode nodeCopy = vector.clone();
      nodeCopy = nodeCopy.getChild(i); 
      expandEvent(model, arraysPlugin, parent, clone, nodeCopy, dim-1);
    }
  }

  /**
   * This updates the index math of the EventAssignments of a certain Event object that utlizes the 
   * Dimension id of the parent Event.
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
        eventAssignment.setMath(replaceDimensionId(math, dimension.getId(), index));
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
  private static void convertReactions (Model model, List<SBase> itemsToDelete, Map<String, ASTNode> idToVector) {
    Enumeration<TreeNode> children = model.getListOfReactions().children();
    while(children.hasMoreElements()) {
      Reaction reaction = (Reaction)children.nextElement();
      convertReaction(model, reaction, itemsToDelete, idToVector);
    }
  }

  /**
   * 
   * @param model
   * @param reaction
   * @param itemsToDelete
   * @throws SBMLException
   */
  private static void convertReaction (Model model, Reaction reaction, List<SBase> itemsToDelete, Map<String, ASTNode> idToVector) throws SBMLException {
    ArraysSBasePlugin plugin = (ArraysSBasePlugin) reaction.getExtension(ArraysConstants.shortLabel);

    if(plugin == null) {
      return;
    }

    int dim = plugin.getDimensionCount() - 1;

    VectorCompiler compiler = new VectorCompiler(model, true, idToVector);
    if(reaction.isSetKineticLaw()) {
      KineticLaw kinetic = reaction.getKineticLaw();
      ASTNode kineticMath = kinetic.getMath();
      kineticMath.compile(compiler);
      ASTNode math = compiler.getNode();
      if(!math.equals(unknown)) {
        kinetic.setMath(math);
      }
    }

    expandReaction(model, plugin, model.getListOfReactions(), reaction, idToVector,idToVector.get(reaction.getId()), dim);
    itemsToDelete.add(reaction);

  }

  /**
   * 
   * @param arraysPlugin
   * @param reaction
   * @param index
   */
  private static void updateSpecRefId(Model model, ArraysSBasePlugin arraysPlugin, Reaction reaction, Map<String, ASTNode> idToVector, int index) {

    for(SpeciesReference ref : reaction.getListOfReactants()) {
      if(ref.isSetId())
      {
        String id = ref.getId();
        ASTNode node = idToVector.get(id).getChild(index);
        String appendId = "_" + index;
        while(model.findNamedSBase(id + appendId) != null) {
          appendId = "_" + appendId;
        }
        ref.setId(id+appendId);
        if(node != null) {
          idToVector.put(id+appendId, node);
        }
      }
    }
    for(SpeciesReference ref : reaction.getListOfProducts()) {
      if(ref.isSetId())
      {
        String id = ref.getId();
        ASTNode node = idToVector.get(id).getChild(index);
        String appendId = "_" + index;
        while(model.findNamedSBase(id + appendId) != null) {
          appendId = "_" + appendId;
        }
        ref.setId(id+appendId);
        if(node != null) {
          idToVector.put(id+appendId, node);
        }
      }
    }
    for(ModifierSpeciesReference ref : reaction.getListOfModifiers()) {
      if(ref.isSetId())
      {
        String id = ref.getId();
        ASTNode node = idToVector.get(id).getChild(index);
        String appendId = "_" + index;
        while(model.findNamedSBase(id + appendId) != null) {
          appendId = "_" + appendId;
        }
        ref.setId(id+appendId);
        if(node != null) {
          idToVector.put(id+appendId, node);
        }
      }
    }
  }

  /**
   * 
   * @param arraysPlugin
   * @param reaction
   * @param index
   */
  private static void updateReactionChildrenMetaId(Model model, ArraysSBasePlugin arraysPlugin, Reaction reaction, int index) {

    SBMLDocument doc = model.getSBMLDocument();
    if(reaction.isSetKineticLaw()) {
      updateSBase(doc, arraysPlugin, reaction.getKineticLaw(), index);
    }
    for(SpeciesReference ref : reaction.getListOfReactants()) {
      updateSBase(doc, arraysPlugin, ref, index);
    }
    for(SpeciesReference ref : reaction.getListOfProducts()) {
      updateSBase(doc, arraysPlugin, ref, index);
    }
    for(ModifierSpeciesReference ref : reaction.getListOfModifiers()) {
      updateSBase(doc, arraysPlugin, ref, index);
    }
  }

  /**
   * 
   * @param arraysPlugin
   * @param reaction
   * @param index
   */
  private static void updateEventChildrenMetaId(Model model, ArraysSBasePlugin arraysPlugin, Event event, int index) {
    SBMLDocument doc = model.getSBMLDocument();
    if(event.isSetDelay()) {
      updateSBase(doc, arraysPlugin, event.getDelay(), index);
    }
    if(event.isSetPriority()) {
      updateSBase(doc, arraysPlugin, event.getPriority(), index);
    }
    if(event.isSetTrigger()) {
      updateSBase(doc, arraysPlugin, event.getTrigger(), index);
    }
    for(EventAssignment ea : event.getListOfEventAssignments()) {
      updateSBase(doc, arraysPlugin, ea, index);
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
  private static void expandReaction (Model model, ArraysSBasePlugin arraysPlugin, SBase parent, Reaction reaction, Map<String, ASTNode> idToVector,ASTNode vector, int dim) {
    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {
      reaction.unsetExtension(ArraysConstants.shortLabel); 
      if(reaction.isSetId() && vector.isName()) {
        reaction.setId(vector.getName());
      }
      addToParent(model, parent, reaction);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      Reaction clone = reaction.clone();
      updateSBase(model.getSBMLDocument(), arraysPlugin, clone, i);
      if(reaction.isSetKineticLaw()) {
        ASTNode math = clone.getKineticLaw().getMath();
        clone.getKineticLaw().setMath(replaceDimensionId(math, dimension.getId(), i));
        if( clone.isSetKineticLaw()) {
          if(clone.getKineticLaw().getMath().isVector()) {
            if(clone.getKineticLaw().getMath().getChildCount() > i) {
              clone.getKineticLaw().setMath(clone.getKineticLaw().getMath().getChild(i));
            } else {
              throw new SBMLException();
            }
          }
        }
      }
      updateSpecRefId(model, arraysPlugin, clone, idToVector, i);
      updateReactionChildrenMetaId(model, arraysPlugin, clone, i);
      updateSpecRefIndex(model, clone, dimension, i);
      ASTNode nodeCopy = vector.clone();
      nodeCopy = nodeCopy.getChild(i); 
      expandReaction(model, arraysPlugin, parent, clone, idToVector, nodeCopy, dim-1);
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
          i.setMath(replaceDimensionId(math, dimension.getId(), index));
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
  private static void convert (SBMLDocument doc, Model model, ArraysCompiler compiler,List<SBase> itemsToDelete, Map<String, ASTNode> idToVector) {
    Enumeration<TreeNode> children = doc.children();
    while(children.hasMoreElements()) {
      TreeNode child = children.nextElement();
      convert(model, child, compiler, itemsToDelete, idToVector);
    }
    //    for(int i = doc.getChildCount() - 1; i >= 0; i--) {
    //      ((SBase)doc.getChildAt(i);
    //      
    //    }
  }

  /**
   * This is recursively getting each TreeNode of a certain SBMLDocument.
   * 
   * @param model
   * @param node
   * @param sbases
   */
  private static void convert(Model model, TreeNode node, 
    ArraysCompiler compiler, List<SBase> sbases, Map<String, ASTNode> idToVector) {

    Enumeration<?> children = node.children();

    while(children.hasMoreElements()) {
      TreeNode child = (TreeNode) children.nextElement();
      expandDim(model, child, compiler, sbases, idToVector);
      convert(model, child, compiler, sbases, idToVector);
    }
  }

  /**
   * 
   * @param model
   * @param idToIndices
   * @param itemsToDelete
   */
  private static void convertMath (SBMLDocument doc, Model model, Map<String, ASTNode> idToVector) {
    Enumeration<TreeNode> children = doc.children();
    while(children.hasMoreElements()) {
      TreeNode child = children.nextElement();
      convertMath(model, child, idToVector);
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
  private static void convertMath(Model model, TreeNode node, Map<String, ASTNode> idToVector) {

    Enumeration<?> children = node.children();
    while(children.hasMoreElements()) {
      TreeNode child = (TreeNode) children.nextElement();
      convertArraysMath(model, child, idToVector);
      convertMath(model, child, idToVector);
    }
  }

  /**
   * 
   * @param model
   * @param child
   * @param sbases
   */
  private static void convertArraysMath(Model model, TreeNode child, Map<String, ASTNode> idToVector) {
    if(child instanceof MathContainer) {
      VectorCompiler compiler = new VectorCompiler(model, true, idToVector);
      MathContainer mathContainer = (MathContainer) child;
      mathContainer.getMath().compile(compiler);
      ASTNode math = compiler.getNode();
      if(!math.equals(unknown)) {
        try {
          mathContainer.setMath(math);
        } catch (SBMLException e) {
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
  private static void expandDim(Model model, TreeNode child, ArraysCompiler compiler, List<SBase> sbases, Map<String, ASTNode> idToVector) {
    if(child instanceof SBase) {
      SBase sbase = ((SBase) child);
      ArraysSBasePlugin arraysPlugin = (ArraysSBasePlugin) sbase.getExtension(ArraysConstants.shortLabel);
      if(arraysPlugin == null) {
        return;
      }
      sbases.add(sbase);
      int dim = arraysPlugin.getDimensionCount() - 1;
      if(child instanceof NamedSBase) {
        expandDim(model, (NamedSBase)sbase.clone(), sbase.getParentSBMLObject(), arraysPlugin, compiler, idToVector.get(((NamedSBase) child).getId()),dim, idToVector);
      } else {
        expandDim(model, sbase.clone(), sbase.getParentSBMLObject(), arraysPlugin, compiler, dim, idToVector);
      }
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
  private static void expandDim(Model model, NamedSBase sbase, SBase parent, ArraysSBasePlugin arraysPlugin,ArraysCompiler compiler, ASTNode vector, int dim, Map<String, ASTNode> idToVector) {

    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {
      sbase.unsetExtension(ArraysConstants.shortLabel); 
      if(sbase.isSetId() && vector != null && vector.isName()) {
        sbase.setId(vector.getName());
      }

      convertIndex(model, arraysPlugin, sbase, compiler, idToVector);
      addToParent(model, parent, sbase);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      NamedSBase clone = (NamedSBase) sbase.clone();
      ASTNode nodeCopy = vector.clone();
      nodeCopy = nodeCopy.getChild(i);
      updateSBase(model.getSBMLDocument(), arraysPlugin, clone, i);


      if(sbase instanceof MathContainer) {
        updateMathContainer(model, arraysPlugin, (MathContainer) clone, dimension.getId(), i, idToVector);
      }

      compiler.addValue(dimension.getId(), i);

      expandDim(model, clone, parent, arraysPlugin,compiler, nodeCopy, dim-1, idToVector);

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
  private static void expandDim(Model model, SBase sbase, SBase parent, ArraysSBasePlugin arraysPlugin,ArraysCompiler compiler, int dim, Map<String, ASTNode> idToVector) {

    Dimension dimension = arraysPlugin.getDimensionByArrayDimension(dim);

    if(dimension == null) {
      sbase.unsetExtension(ArraysConstants.shortLabel); 
      convertIndex(model, arraysPlugin, sbase, compiler, idToVector);
      addToParent(model, parent, sbase);
      return;
    }

    int size = ArraysMath.getSize(model, dimension);

    for(int i = 0; i < size; i++) {
      SBase clone = sbase.clone();
      updateSBase(model.getSBMLDocument(), arraysPlugin, clone, i);

      if(sbase instanceof MathContainer) {
        updateMathContainer(model, arraysPlugin, (MathContainer) clone, dimension.getId(), i, idToVector);
      }

      compiler.addValue(dimension.getId(), i);

      expandDim(model, clone, parent, arraysPlugin,compiler, dim-1, idToVector);

    }
  }

  /**
   * Add the new SBase to the corresponding ListOf object.
   * 
   * @param parent
   * @param child
   */
  private static void addToParent(Model model, SBase parent, SBase child) {
    if(parent instanceof ListOf<?>) {
      @SuppressWarnings("unchecked")
      ListOf<SBase> parentList = (ListOf<SBase>) parent;
      //      if(child instanceof NamedSBase) {
      //        NamedSBase namedSbase = (NamedSBase) child;
      //        String id = namedSbase.getId();
      //        while(model.findNamedSBase(id) != null) {
      //          id = id.replaceFirst("_", "__");
      //        }
      //        namedSbase.setId(id);
      //      
      //      }
      parentList.add(child);
      return;
    }
    if(parent instanceof Port)
    {
      if(child instanceof SBaseRef)
      {
        Port port = ((Port)parent);
        port.setSBaseRef(((SBaseRef)child));
        return;
      }
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
  private static void convertIndex(Model model, ArraysSBasePlugin arraysPlugin, SBase sbase, ArraysCompiler compiler, Map<String, ASTNode> idToVector) {
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
      String indexValue = getIndexedId(arraysPlugin, sbase, attribute, maxIndex, compiler, idToVector);
      String[] parse = attribute.split(":");
      if(parse.length == 2) {
        sbase.readAttribute(parse[1], parse[0], indexValue);
      } else {
        sbase.readAttribute(attribute, "", indexValue);
      }
    }

  }


  public static String getIndexedId(ArraysSBasePlugin arraysPlugin, SBase sbase, String attribute, int maxIndex, ArraysCompiler compiler, Map<String, ASTNode> idToVector)
  {
    String temp = sbase.writeXMLAttributes().get(attribute);
    ASTNode vector = idToVector.get(temp);
    if(vector == null)
    {
      String append = "";
      for(int i = maxIndex; i >= 0; i--) {
        Index index = arraysPlugin.getIndex(i, attribute);
        if(index == null) {
          throw new SBMLException();
        }
        ASTNodeValue value = index.getMath().compile(compiler);
        if(value.isNumber()) {
          append = append + "_" + value.toInteger();
        } else {
          throw new SBMLException();
        }
      }
      return temp + append;
    }
    else{
      for(int i = maxIndex; i >= 0; i--) {
        Index index = arraysPlugin.getIndex(i, attribute);
        if(index == null) {
          throw new SBMLException();
        }
        ASTNodeValue value = index.getMath().compile(compiler);
        if(value.isNumber()) {
          vector = vector.getChild(value.toInteger());
        } else {
          throw new SBMLException();
        }
      }
      return vector.getName();
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
  private static void updateMathContainer(Model model, ArraysSBasePlugin arraysPlugin, MathContainer sbase, String dimId, int index, Map<String, ASTNode> idToVector) {
    if(sbase.isSetMath()) {
      //String formula = sbase.getMath().toFormula().replaceAll(dimId, String.valueOf(index));
      //sbase.setMath(ASTNode.parseFormula(formula));
      sbase.setMath(replaceDimensionId(sbase.getMath(), dimId, index));
      //sbase.getMath().replaceArgument(dimId, new ASTNode(index));
      VectorCompiler compiler = new VectorCompiler(model, true, idToVector);
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
  private static void updateSBase(SBMLDocument doc, ArraysSBasePlugin arraysPlugin, SBase sbase, int index) {
    //TODO check if unique
    if(sbase.isSetMetaId()) {
      String metaId = sbase.getMetaId();

      String appendId = "_" + index;
      while(doc.findSBase(metaId + appendId) != null) {
        appendId = "_" + appendId;
      }
      sbase.setMetaId(metaId+appendId);
    }
  }

  private static ASTNode replaceDimensionId(ASTNode math, String id, int index) {
    ASTNode clone = math.clone();
    recursiveReplaceDimensionId(clone, id, index);
    return clone;
  }

  private static void recursiveReplaceDimensionId(ASTNode math, String id, int index) {
    if(math.getChildCount() == 0) {
      if(math.isString() && math.getName().equals(id)) {
        math.setValue(index);
      }
      return;
    }

    for(int i = 0; i < math.getChildCount(); ++i) {
      recursiveReplaceDimensionId(math.getChild(i), id, index);
    }
  }
}

