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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.validator;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

/**
 * This class creates a bipartite graph and a matching for the given model using
 * the algorithm by Hopcroft and Karp (1973).
 * 
 * @author Alexander D&ouml;rr
 * @since 0.8
 */
public class OverdeterminationValidator {

  /**
   * This class represents an inner node in the bipartite graph, e.g., a
   * varibale or an reaction
   * 
   * @author Alexander D&ouml;rr
   * @param <T>
   * @since 0.8
   */
  private class InnerNode<T extends SBase> implements Node<T> {
    /**
     * Adjacent nodes
     */
    private List<Node<T>> nodes;
    /**
     * Value of this Node
     */
    private T value;

    /**
     * Creates a new inner node
     * 
     * @param name
     */
    public InnerNode(T name) {
      this.nodes = new ArrayList<Node<T>>();
      this.value = name;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#addNode(org.sbml.jsbml.validator.OverdeterminationValidator.Node)
     */
    @Override
    public void addNode(Node<T> node) {
      this.nodes.add(node);
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#deleteNode(org.sbml.jsbml.validator.OverdeterminationValidator.Node)
     */
    @Override
    public void deleteNode(Node<T> node) {
      nodes.remove(node);

    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNextNode()
     */
    @Override
    public Node<T> getNextNode() {
      if (nodes.isEmpty()) {
        return null;
      } else {
        return nodes.get(0);
      }

    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNode(int)
     */
    @Override
    public Node<T> getNode(int i) {
      if ((nodes.size() > i) && (i >= 0)) {
        return nodes.get(i);
      }
      return null;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNodes()
     */
    @Override
    public List<Node<T>> getNodes() {
      return this.nodes;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getValue()
     */
    @Override
    public T getValue() {
      return value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return getValue().toString();
    }

  }

  /**
   * This Interface represents a node in the bipartite graph
   * 
   * @author Alexander D&ouml;rr
   * 
   * @param <S>
   */
  private interface Node<S> {
    /**
     * Adds a node to the list of nodes (creates an edge from this node to
     * another one)
     * 
     * @param node
     */
    public void addNode(Node<S> node);

    /**
     * Deletes node from the list of adjacent nodes
     * 
     * @param node
     */
    public void deleteNode(Node<S> node);

    /**
     * Returns the next node in the list of nodes
     * 
     * @return
     */
    public Node<S> getNextNode();

    /**
     * Returns the i-th node in the list of nodes
     * @param i
     * 
     * @return
     */
    public Node<S> getNode(int i);

    /**
     * Returns the list of adjacent nodes
     * 
     * @return
     */
    public List<Node<S>> getNodes();

    /**
     * Returns the value of this node
     * 
     * @return
     */
    public S getValue();
  }

  /**
   * This class represents the start node in the bipartite graph
   * 
   * @author Alexander D&ouml;rr
   * @param <T>
   */
  private class StartNode<T extends SBase> implements Node<T> {
    /**
     * 
     */
    private List<Node<T>> nodes;

    /**
     * Creates a new start node
     */
    public StartNode() {
      this.nodes = new ArrayList<Node<T>>();
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#addNode(org.sbml.jsbml.validator.OverdeterminationValidator.Node)
     */
    @Override
    public void addNode(Node<T> node) {
      this.nodes.add(node);
    }

    /**
     * Clones this Graph without terminal node
     */
    @Override
    public StartNode<T> clone() {
      StartNode<T> start = new StartNode<T>();
      Node<T> ln, rn, rnode, lnode;
      int index;
      HashMap<T, Node<T>> variables = new HashMap<T, Node<T>>();
      // for all adjacent nodes (equation)
      for (int i = 0; i < this.nodes.size(); i++) {
        lnode = this.getNode(i);
        ln = new InnerNode<T>(lnode.getValue());
        start.addNode(ln);
        index = 0;
        rnode = lnode.getNode(index);
        // for every variable adjacent to lnode
        while (rnode != null) {
          // check if variable has already been created
          if (variables.get(rnode.getValue()) != null) {
            rn = variables.get(rnode.getValue());
          } else {
            rn = new InnerNode<T>(rnode.getValue());
            variables.put(rnode.getValue(), rn);
          }

          // link equation with variable and vice versa
          ln.addNode(rn);
          rn.addNode(ln);
          index++;
          rnode = lnode.getNode(index);
        }
      }
      return start;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#deleteNode(org.sbml.jsbml.validator.OverdeterminationValidator.Node)
     */
    @Override
    public void deleteNode(Node<T> node) {
      nodes.remove(node);
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNextNode()
     */
    @Override
    public Node<T> getNextNode() {
      if (nodes.isEmpty()) {
        return null;
      } else {
        return nodes.get(0);
      }
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNode(int)
     */
    @Override
    public Node<T> getNode(int i) {
      if ((nodes.size() > i) && (i >= 0)) {
        return nodes.get(i);
      }
      return null;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNodes()
     */
    @Override
    public List<Node<T>> getNodes() {
      return this.nodes;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getValue()
     */
    @Override
    public T getValue() {
      return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "start";
    }
  }

  /**
   * This class represents the end node in the bipartite graph
   * 
   * @author Alexander D&ouml;rr
   * @param <T>
   * @since 0.8
   */
  private class TerminalNode<T extends SBase> implements Node<T> {

    /**
     * Creates a new terminal node
     */
    public TerminalNode() {
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#addNode(org.sbml.jsbml.validator.OverdeterminationValidator.Node)
     */
    @Override
    public void addNode(Node<T> node) {

    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#deleteNode(org.sbml.jsbml.validator.OverdeterminationValidator.Node)
     */
    @Override
    public void deleteNode(Node<T> node) {

    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNextNode()
     */
    @Override
    public Node<T> getNextNode() {
      return null;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNode(int)
     */
    @Override
    public Node<T> getNode(int i) {
      return null;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getNodes()
     */
    @Override
    public List<Node<T>> getNodes() {
      return null;
    }

    /* (non-Javadoc)
     * @see org.sbml.jsbml.validator.OverdeterminationValidator.Node#getValue()
     */
    @Override
    public T getValue() {
      return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "end";
    }

  }

  /**
   * List with nodes representing an equation in the model
   */
  private List<Node<SBase>> equations;
  /**
   * List with nodes representing an variable in the model
   */
  private List<Node<SBase>> variables;
  /**
   * HashMap with id -> node for variables
   */
  private HashMap<SBase, Node<SBase>> variableHash;
  /**
   * HashMap with id -> node for equations
   */
  private HashMap<SBase, Node<SBase>> equationHash;
  /**
   * HashMap representing the current matching with value of the left node ->
   * value of the right node
   */
  private Map<SBase, SBase> matching;
  /**
   * The source node of the bipartite graph, respectively the bipartite graph
   */
  private StartNode<SBase> bipartiteGraph;
  /**
   * A list where the ids of all global species in an MathML expression are
   * saved temporarily
   */
  private List<SBase> svariables;
  /**
   * A list of all paths of a certain length in the graph
   */
  private List<List<Node<SBase>>> paths;
  /**
   * A set with the ids of all reactants in the model
   */
  private Set<String> reactants;
  /**
   * The given SBML model
   */
  private Model model;

  /**
   * Creates a new OverdeterminationValidator for the given model
   * 
   * @param model
   */
  public OverdeterminationValidator(Model model) {
    this.model = model;
    init();
  }

  /**
   * Improves the matching as far as possible with augmenting paths
   */
  private void augmentMatching() {
    paths = new ArrayList<List<Node<SBase>>>();
    int length = 1;

    while (length < (variables.size() + equations.size())) {
      // Start search for all path of the current length of the adjacent
      // nodes of the start node
      for (Node<SBase> node : bipartiteGraph.getNodes()) {
        findShortestPath(length, node, new ArrayList<Node<SBase>>());
      }

      // Try to augment every found path
      augmentPath(length);

      // Matching is already maximal or there is no maximal matching
      // (length of the next path would be greater than the longest
      // possible path in the graph)
      if ((matching.size() == equations.size())
          || (length > equations.size() * 2 - 3)) {
        break;
      }

      // Increment length by 2 because last edge has to be a matching
      length += 2;
    }

  }

  /**
   * Tries augment every path found in the graph to a new path of the given
   * length + 2 (a new node at the beginning and the end of the path)
   * 
   * @param length
   */
  private void augmentPath(int length) {
    Node<SBase> start = null, end = null;
    List<Node<SBase>> path;

    // For every path of the current length
    while (!paths.isEmpty()) {
      path = paths.get(0);
      // Search for the start node of the path an unmatched adjacent node
      for (Node<SBase> node : path.get(0).getNodes()) {
        // New start node not part of a matching
        if (!matching.containsValue(node.getValue())) {
          start = node;
          break;
        }
      }

      // Search for the end node of the path an unmatched adjacent node
      for (Node<SBase> node : path.get(path.size() - 1).getNodes()) {

        // New end node not part of a matching
        if (!matching.containsKey(node.getValue())) {
          end = node;
          break;
        }

      }
      // New start and end node for this path found -> update path
      if ((start != null) && (end != null)) {
        path.add(0, start);
        path.add(path.size(), end);

        // Update matching
        updateMatching(path);
      }

      // Path Augmented -> remove from the list
      paths.remove(path);
      start = null;
      end = null;
    }
  }

  /**
   * Build the bipartite graph by reference to SBML specification level 3
   * version 1 Core
   */
  private void buildGraph() {
    equations = new ArrayList<Node<SBase>>();
    variables = new ArrayList<Node<SBase>>();
    variableHash = new HashMap<SBase, Node<SBase>>();
    equationHash = new HashMap<SBase, Node<SBase>>();
    Node<SBase> equation;
    Node<SBase> variable;
    int i;

    // Build vertices for compartments and hash them
    if (model.isSetListOfCompartments()) {
      for (Compartment c : model.getListOfCompartments()) {
        if ((model.getLevel()==1) || !(c.isConstant())) {
          variable = new InnerNode<SBase>(c);
          variables.add(variable);
          variableHash.put(variable.getValue(), variable);
        }
      }
    }
    // Build vertices for species and hash them
    if (model.isSetListOfSpecies()) {
      for (Species s : model.getListOfSpecies()) {
        if (!s.isConstant()) {
          variable = new InnerNode<SBase>(s);
          variables.add(variable);
          variableHash.put(variable.getValue(), variable);
        }
      }
    }
    // Build vertices for parameter and hash them
    if (model.isSetListOfParameters()) {
      for (Parameter p : model.getListOfParameters()) {
        if (!p.isConstant()) {
          variable = new InnerNode<SBase>(p);
          variables.add(variable);
          variableHash.put(variable.getValue(), variable);
        }
      }
    }
    
    // Build vertices for reaction and hash them
    if (model.isSetListOfReactions()) {
      for (Reaction r : model.getListOfReactions()) {
        variable = new InnerNode<SBase>(r);
        variables.add(variable);
        variableHash.put(variable.getValue(), variable);
      }
    }

    // Create edges with reactions
    for (i = 0; i < model.getReactionCount(); i++) {
      Reaction r = model.getReaction(i);

      // Create vertices and edges for products
      if (r.isSetListOfProducts()) {
        for (SpeciesReference sref : r.getListOfProducts()) {
          Species species = sref.getSpeciesInstance();
          if (species != null && !species.isConstant()) {
            variable = variableHash.get(species);
            if (!species.getBoundaryCondition()) {

              equation = equationHash.get(species);
              if (equation == null) {
                equation = new InnerNode<SBase>(species);
                equations.add(equation);
                equationHash.put(species, equation);
                // link
                variable.addNode(equation);
                equation.addNode(variable);
                variableHash.put(variable.getValue(), variable);
              }
            }
          }
        }
      }

      // Create vertices and edges for reactants
      if (r.isSetListOfReactants()) {
        for (SpeciesReference sref : r.getListOfReactants()) {
          Species species = sref.getSpeciesInstance();
          if (species != null && !species.isConstant()) {
            variable = variableHash.get(species);
            if (!species.getBoundaryCondition()) {

              equation = equationHash.get(species);
              if (equation == null) {
                equation = new InnerNode<SBase>(species);
                equations.add(equation);
                equationHash.put(species, equation);
                // link
                variable.addNode(equation);
                equation.addNode(variable);
                variableHash.put(variable.getValue(), variable);
              }
            }
          }
        }
      }
      
      // link reaction with its kinetic law
      equation = new InnerNode<SBase>(r);
      equations.add(equation);
      variable = variableHash.get(equation.getValue());

      variable.addNode(equation);
      equation.addNode(variable);

    }

    // Create vertices and edges for assignment and rate rules
    for (i = 0; i < model.getRuleCount(); i++) {
      equation = new InnerNode<SBase>(model.getRule(i));
      Rule r = model.getRule(i);
      if (r instanceof RateRule) {
        equations.add(equation);
        variable = variableHash.get(((RateRule) r).getVariableInstance());
        // link
        
        if (variable != null)
        {
          variable.addNode(equation);
          equation.addNode(variable);
        }
        
      }

      else if (r instanceof AssignmentRule) {
        variable = variableHash.get(((AssignmentRule) r).getVariableInstance());
        // link
        if (variable != null) {
          equations.add(equation);
          variable.addNode(equation);
          equation.addNode(variable);
        }

      }
    }

    // Create vertices and edges for algebraic rules
    for (i = 0; i < model.getRuleCount(); i++) {
      equation = new InnerNode<SBase>(model.getRule(i));
      Rule r = model.getRule(i);
      if (r instanceof AlgebraicRule) {
        equations.add(equation);
        // all identifiers within the MathML of this AlgebraicRule
        svariables.clear();
        getVariables(null, model.getRule(i).getMath(), svariables, model.getLevel());
        // link rule with its variables
        for (int j = 0; j < svariables.size(); j++) {
          variable = variableHash.get(svariables.get(j));
          if (variable != null) {
            variable.addNode(equation);
            equation.addNode(variable);
          }
        }
      }
    }
  }

  /**
   * Build the maximum matching with the greedy algorithm from the Hopcroft
   * and Karp paper. Matching is not necessarily maximal.
   */
  private void buildMatching() {
    StartNode<SBase> matchingGraph;
    // the source node
    matchingGraph = new StartNode<SBase>();
    // the sink node
    TerminalNode<SBase> tnode = new TerminalNode<SBase>();
    matching = new HashMap<SBase, SBase>();
    Set<Node<SBase>> B = new HashSet<Node<SBase>>();
    Stack<Node<SBase>> stack = new Stack<Node<SBase>>();
    Node<SBase> first, last;
    int i;

    // connect equations with source node
    for (i = 0; i < equations.size(); i++) {
      matchingGraph.addNode(equations.get(i));
    }
    // connect equations with sink node
    for (i = 0; i < variables.size(); i++) {
      variables.get(i).addNode(tnode);
    }

    bipartiteGraph = matchingGraph.clone();

    // push source node on the stack
    stack.push(matchingGraph);

    while (!stack.isEmpty()) {

      // if node on stack has linked node
      if (stack.peek().getNextNode() != null) {
        // get first linked node
        first = stack.peek().getNextNode();
        // if node not already in the matching
        if (!B.contains(first)) {
          // delete connection
          stack.peek().deleteNode(first);
          first.deleteNode(stack.peek());
          // push first on stack
          stack.push(first);

          // if first not the sink node add to the matching
          if (!(stack.peek() instanceof TerminalNode<?>)) {
            B.add(first);
          }// first is sink node
          else {
            // remove sink node
            stack.pop();
            // build matching between 2 neighbouring in the list and
            // leave source node on the stack
            while (stack.size() > 1) {
              last = stack.pop();
              matching.put(stack.pop().getValue(), last
                .getValue());
            }
          }

        } // else delete connection
        else {
          stack.peek().deleteNode(first);
          first.deleteNode(stack.peek());
        }

      } // else remove from stack
      else {
        stack.pop();
      }

    }

  }

  /**
   * Finds all paths of the length i whose nodes are part of the matching.
   * 
   * @param i
   *            length
   * @param node
   *            next node
   * @param path
   *            nodes already visited in the path
   */
  @SuppressWarnings("unchecked")
  private void findShortestPath(int i, Node<SBase> node,
    List<Node<SBase>> path) {
    SBase value;
    // Path has reached the desired length -> store it in the list
    if (path.size() == i * 2) {
      paths.add(path);
    } else {
      value = matching.get(node.getValue());
      // Search for the adjacent node convenient with the current matching
      for (Node<SBase> next : node.getNodes()) {
        if (next.getValue() == value) {
          path.add(node);
          path.add(next);
          // Edge saved in the path -> carry on with every adjacent
          // node of the last node in the path
          for (Node<SBase> nextnext : next.getNodes()) {
            if (nextnext.getValue() != node.getValue()) {
              findShortestPath(
                i,
                nextnext,
                (List<Node<SBase>>) ((ArrayList<Node<SBase>>) path).clone());
            }
          }
        }
      }
    }
  }

  /**
   * Returns the determined matching
   * 
   * @return
   */
  public Map<SBase, SBase> getMatching() {
    return matching;
  }

  /**
   * Returns the variables in a MathML object without local parameter
   * 
   * @param param
   * @param node
   * @param variables
   * @param level
   */
  private void getVariables(ListOf<LocalParameter> param, ASTNode node,
    List<SBase> variables, int level) {
    
    if (node  == null)
    {
      return;
    }
    
    // found node with species
    if ((node.getChildCount() == 0) && (node.isString()) &&
        (node.getType() != Type.NAME_TIME) &&
        (node.getType() != Type.NAME_AVOGADRO)) { // TODO - deal with csymbol rateOf as well ?
      if (!node.isConstant()) {
        if (param == null) {
          SBase variable=node.getVariable();
          if (level==1) {
            int insertingPosition = 0;
            for (SBase element:variables) {
              if (!(element instanceof Parameter) || (!((Parameter)element).isSetValue())) {
                insertingPosition++;
              }
            }
            variables.add(insertingPosition, variable);
          }
          else {
            variables.add(variable);
          }
        } else {
          if (!param.contains(node.getVariable())) {
            SBase variable=node.getVariable();
            if (level==1) {
              int insertingPosition=0;
              for (SBase element:variables) {
                if (!(element instanceof Parameter) ||
                    (!((Parameter) element).isSetValue())) {
                  insertingPosition++;
                }
              }
              variables.add(insertingPosition, variable);
            }
            else {
              variables.add(variable);
            }
          }
        }
      }
    }

    // else found operator or function
    else {
      // carry on with all children
      Enumeration<TreeNode> nodes = node.children();
      while (nodes.hasMoreElements()) {
        getVariables(param, (ASTNode) nodes.nextElement(), variables, level);
      }
    }

  }

  /**
   * Initializes the Converter
   */
  private void init() {
    svariables = new ArrayList<SBase>();
    reactants = new HashSet<String>();

    for (int i = 0; i < model.getReactionCount(); i++) {

      if (model.getReaction(i).isSetListOfProducts()) {
        for (SpeciesReference sref : model.getReaction(i).getListOfProducts()) {
          reactants.add(sref.getSpecies());
        }
      }

      if (model.getReaction(i).isSetListOfReactants()) {
        for (SpeciesReference sref : model.getReaction(i).getListOfReactants()) {
          reactants.add(sref.getSpecies());
        }
      }
    }

    // Build the graph the matching and try to improve the matching
    buildGraph();
    buildMatching();
    augmentMatching();
  }

  /**
   * Returns a boolean that indicates whether the given model is over
   * determined or not.
   * 
   * @return
   */
  public boolean isOverdetermined() {
    return equations.size() > matching.size();
  }

  /**
   * Updates the matching of the model on the basis of the found augmented
   * path. Please note that because of starting the search for a path through
   * the graph at an equation, the first node in the augmented path is always
   * a variable and the last one an equation.
   * 
   * @param path
   */
  private void updateMatching(List<Node<SBase>> path) {
    for (int index = 1; path.size() > index; index += 2) {
      matching.remove(path.get(index).getValue());
      matching.put(path.get(index).getValue(), path.get(index - 1).getValue());
    }
  }

}
