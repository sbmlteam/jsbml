/*
 * $Id: OverdeterminationValidator.java 273 2010-06-10 13:17:41Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/validator/OverdeterminationValidator.java $
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.validator;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.sbml.jsbml.ASTNode;
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
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

/**
 * This class creates a bipartite graph and a matching for the given model using
 * the Hopcroft-Karp-Algorithm
 * 
 * @author Alexander D&ouml;rr
 * @since 2010-06-17
 */
public class OverdeterminationValidator {

	/**
	 * This class represents an inner node in the bipartite graph, e.g. a
	 * varibale or an reaction
	 * 
	 * @author Alexander D&ouml;rr
	 * @since 1.4
	 */
	private class InnerNode implements Node {
		/**
		 * Adjacent nodes
		 */
		private List<Node> nodes;
		/**
		 * Value of this Node
		 */
		private String value;

		/**
		 * Creates a new inner node
		 * 
		 * @param name
		 */
		public InnerNode(String name) {
			this.nodes = new ArrayList<Node>();
			this.value = name;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.squeezer.math.AlgebraicRuleConverter.Node#addNode(org.sbml
		 * .squeezer.math.AlgebraicRuleConverter.Node)
		 */
		public void addNode(Node node) {
			this.nodes.add(node);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.squeezer.math.AlgebraicRuleConverter.Node#deleteNode(org
		 * .sbml.squeezer.math.AlgebraicRuleConverter.Node)
		 */
		public void deleteNode(Node node) {
			nodes.remove(node);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNextNode()
		 */
		public Node getNextNode() {
			if (nodes.isEmpty())
				return null;
			else
				return nodes.get(0);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNode(int)
		 */
		public Node getNode(int i) {
			if (nodes.size() > i && i >= 0)
				return nodes.get(i);
			else
				return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNodes()
		 */
		public List<Node> getNodes() {
			return this.nodes;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getValue()
		 */
		public String getValue() {
			return value;
		}

	}

	/**
	 * This Interface represents a node in the bipartite graph
	 * 
	 * @author Alexander D&ouml;rr
	 * @since 1.4
	 */
	private interface Node {
		/**
		 * Adds a node to the list of nodes (creates an edge from this node to
		 * another one)
		 * 
		 * @param node
		 */
		public void addNode(Node node);

		/**
		 * Deletes node from the list of adjacent nodes
		 * 
		 * @param node
		 */
		public void deleteNode(Node node);

		/**
		 * Returns the next node in the list of nodes
		 * 
		 * @return
		 */
		public Node getNextNode();

		/**
		 * Returns the i-th node in the list of nodes
		 * 
		 * @return
		 */
		public Node getNode(int i);

		/**
		 * Returns the list of adjacent nodes
		 * 
		 * @return
		 */
		public List<Node> getNodes();

		/**
		 * Returns the value of this node
		 * 
		 * @return
		 */
		public String getValue();
	}

	/**
	 * This class represents the start node in the bipartite graph
	 * 
	 * @author Alexander D&ouml;rr
	 * @since 1.4
	 */
	private class StartNode implements Node {
		private List<Node> nodes;

		/**
		 * Creates a new start node
		 */
		public StartNode() {
			this.nodes = new ArrayList<Node>();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.squeezer.math.AlgebraicRuleConverter.Node#addNode(org.sbml
		 * .squeezer.math.AlgebraicRuleConverter.Node)
		 */
		public void addNode(Node node) {
			this.nodes.add(node);
		}

		/**
		 * Clones this Graph without terminal node
		 */
		public StartNode clone() {
			StartNode start = new StartNode();
			Node ln, rn, rnode, lnode;
			int index;
			HashMap<String, Node> variables = new HashMap<String, Node>();
			// for all adjacent nodes (equation)
			for (int i = 0; i < this.nodes.size(); i++) {
				lnode = this.getNode(i);
				ln = new InnerNode(lnode.getValue());
				start.addNode(ln);
				index = 0;
				rnode = lnode.getNode(index);
				// for every variable adjacent to lnode
				while (rnode != null) {
					// check if variable has already been created
					if (variables.get(rnode.getValue()) != null) {
						rn = variables.get(rnode.getValue());
					} else {
						rn = new InnerNode(rnode.getValue());
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.squeezer.math.AlgebraicRuleConverter.Node#deleteNode(org
		 * .sbml.squeezer.math.AlgebraicRuleConverter.Node)
		 */
		public void deleteNode(Node node) {
			nodes.remove(node);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNextNode()
		 */
		public Node getNextNode() {
			if (nodes.isEmpty())
				return null;
			else
				return nodes.get(0);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNode(int)
		 */
		public Node getNode(int i) {
			if (nodes.size() > i && i >= 0)
				return nodes.get(i);
			else
				return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNodes()
		 */
		public List<Node> getNodes() {
			return this.nodes;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getValue()
		 */
		public String getValue() {
			return null;
		}

	}

	/**
	 * This class represents the end node in the bipartite graph
	 * 
	 * @author Alexander D&ouml;rr
	 * @since 1.4
	 */
	private class TerminalNode implements Node {

		/**
		 * Creates a new terminal node
		 */
		public TerminalNode() {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.squeezer.math.AlgebraicRuleConverter.Node#addNode(org.sbml
		 * .squeezer.math.AlgebraicRuleConverter.Node)
		 */
		public void addNode(Node node) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.squeezer.math.AlgebraicRuleConverter.Node#deleteNode(org
		 * .sbml.squeezer.math.AlgebraicRuleConverter.Node)
		 */
		public void deleteNode(Node node) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNextNode()
		 */
		public Node getNextNode() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNode(int)
		 */
		public Node getNode(int i) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getNodes()
		 */
		public List<Node> getNodes() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.squeezer.math.AlgebraicRuleConverter.Node#getValue()
		 */
		public String getValue() {
			return null;
		}

	}

	/**
	 * List with nodes representing an equation in the model
	 */
	private List<Node> equations;
	/**
	 * List with nodes representing an variable in the model
	 */
	private List<Node> variables;
	/**
	 * HashMap with id -> node for variables
	 */
	private HashMap<String, Node> variableHash;
	/**
	 * HashMap with id -> node for equations
	 */
	private HashMap<String, Node> equationHash;
	/**
	 * HashMap representing the current matching with value of the left node ->
	 * value of the right node
	 */
	private HashMap<String, String> matching;
	/**
	 * The source node of the bipartite graph, respectively the bipartite graph
	 */
	private StartNode bipartiteGraph;
	/**
	 * A list where the ids of all global species in an MathML expression are
	 * saved temporarily
	 */
	private List<String> svariables;
	/**
	 * A list of all paths of a certain length in the graph
	 */
	private ArrayList<ArrayList<Node>> paths;
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
		paths = new ArrayList<ArrayList<Node>>();
		int length = 1;

		while (length < (variables.size() + equations.size())) {
			System.out.println("Searching path of size: " + length);
			// Start search for all path of the current length of the adjacent
			// nodes of the start node
			for (Node node : bipartiteGraph.getNodes()) {
				findShortestPath(length, node, new ArrayList<Node>());
			}

			System.out.println("Found: " + paths.size());

			// Try to augment every found path
			augmentPath(length);

			// Matching is already maximal or there is no maximal matching
			// (length of the next path would be greater than the longest
			// possible path in the graph)
			if (matching.size() == equations.size()
					|| length > equations.size() * 2 - 3)
				break;

			// Increment length by 2 because last edge has to be a matching
			length = length + 2;
		}

	}

	/**
	 * Tries augment every path found in the graph to a new path of the given
	 * length + 2 (a new node at the beginning and the end of the path)
	 * 
	 * @param length
	 */
	private void augmentPath(int length) {
		System.out
				.println("Searching augmenting path of size: " + (length + 2));
		Node start = null, end = null;
		ArrayList<Node> path;

		// For every path of the current length
		while (!paths.isEmpty()) {
			path = paths.get(0);
			// Search for the start node of the path an unmatched adjacent node
			for (Node node : path.get(0).getNodes()) {

				// New start node not part of a matching
				if (!matching.containsKey(node.getValue())
						&& !matching.containsValue(node.getValue())) {
					start = node;
					break;
				}
			}

			// Search for the end node of the path an unmatched adjacent node
			for (Node node : path.get(path.size() - 1).getNodes()) {

				// New end node not part of a matching
				if (!matching.containsKey(node.getValue())
						&& !matching.containsValue(node.getValue())) {
					end = node;
					break;
				}

			}
			// New start and end node for this path found -> update path
			if (start != null && end != null) {
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
		equations = new ArrayList<Node>();
		variables = new ArrayList<Node>();
		variableHash = new HashMap<String, Node>();
		equationHash = new HashMap<String, Node>();
		Node equation, variable;
		int i;

		// Build vertices for compartments and hash them
		for (Compartment c : model.getListOfCompartments()) {
			if (!c.isConstant()) {
				variable = new InnerNode(c.getId());
				variables.add(variable);
				variableHash.put(variable.getValue(), variable);
			}
		}
		// Build vertices for species and hash them
		for (Species s : model.getListOfSpecies()) {
			if (!s.isConstant()) {
				variable = new InnerNode(s.getId());
				variables.add(variable);
				variableHash.put(variable.getValue(), variable);
			}
		}
		// Build vertices for parameter and hash them
		for (Parameter p : model.getListOfParameters()) {
			if (!p.isConstant()) {
				variable = new InnerNode(p.getId());
				variables.add(variable);
				variableHash.put(variable.getValue(), variable);
			}
		}

		// Build vertices for reaction and hash them
		for (Reaction r : model.getListOfReactions()) {
			variable = new InnerNode(r.getId());
			variables.add(variable);
			variableHash.put(variable.getValue(), variable);
		}

		// Create edges with reactions
		for (i = 0; i < model.getNumReactions(); i++) {
			Reaction r = model.getReaction(i);

			// Create vertices and edges for products
			for (SpeciesReference sref : r.getListOfProducts()) {
				if (!sref.getSpeciesInstance().isConstant()) {
					variable = variableHash.get(sref.getSpeciesInstance()
							.getId());
					if (!sref.getSpeciesInstance().getBoundaryCondition()) {

						equation = equationHash.get(sref.getSpeciesInstance()
								.getId());
						if (equation == null) {
							equation = new InnerNode(sref.getSpeciesInstance()
									.getId());
							equations.add(equation);
							equationHash.put(sref.getSpeciesInstance().getId(),
									equation);
							// link
							variable.addNode(equation);
							equation.addNode(variable);
							variableHash.put(variable.getValue(), variable);

						}
					}
				}
			}

			// Create vertices and edges for reactants
			for (SpeciesReference sref : r.getListOfReactants()) {

				if (!sref.getSpeciesInstance().isConstant()) {
					variable = variableHash.get(sref.getSpeciesInstance()
							.getId());
					if (!sref.getSpeciesInstance().getBoundaryCondition()) {

						equation = equationHash.get(sref.getSpeciesInstance()
								.getId());
						if (equation == null) {
							equation = new InnerNode(sref.getSpeciesInstance()
									.getId());
							equations.add(equation);
							equationHash.put(sref.getSpeciesInstance().getId(),
									equation);
							// link
							variable.addNode(equation);
							equation.addNode(variable);
							variableHash.put(variable.getValue(), variable);

						}
					}
				}
			}
			// link reaction with its kinetic law
			equation = new InnerNode(r.getId());
			equations.add(equation);
			variable = variableHash.get(equation.getValue());

			variable.addNode(equation);
			equation.addNode(variable);
			/**
			 * Not in 3.1 // link kinetic law with its variables
			 * svariables.clear();
			 * getVariables(r.getKineticLaw().getListOfParameters(), r
			 * .getKineticLaw().getMath(), svariables);
			 * 
			 * for (int j = 0; j < svariables.size(); j++) { variable =
			 * variableHash.get(svariables.get(j)); if (variable != null) {
			 * variable.addNode(equation); equation.addNode(variable); } }
			 */

		}

		// Create vertices and edges for assignment and rate rules
		for (i = 0; i < model.getNumRules(); i++) {
			equation = new InnerNode(model.getRule(i).getMetaId());
			Rule r = model.getRule(i);
			if (r instanceof RateRule) {
				equations.add(equation);
				variable = variableHash.get(((RateRule) r)
						.getVariableInstance().getId());
				// link
				variable.addNode(equation);
				equation.addNode(variable);

				/**
				 * Not in 3.1 // -- self creation svariables.clear();
				 * getVariables(null, model.getRule(i).getMath(), svariables);
				 * // link rule with its variables for (int j = 0; j <
				 * svariables.size(); j++) { variable =
				 * variableHash.get(svariables.get(j)); if (variable != null) {
				 * variable.addNode(equation); equation.addNode(variable);
				 * 
				 * } } // --
				 */
			}

			else if (r instanceof AssignmentRule) {
				equations.add(equation);
				variable = variableHash.get(((AssignmentRule) r)
						.getVariableInstance().getId());
				// link
				variable.addNode(equation);
				equation.addNode(variable);

				/**
				 * Not in 3.1 svariables.clear(); getVariables(null,
				 * model.getRule(i).getMath(), svariables); // link rule with
				 * its variables for (int j = 0; j < svariables.size(); j++) {
				 * variable = variableHash.get(svariables.get(j)); if (variable
				 * != null) { variable.addNode(equation);
				 * equation.addNode(variable);
				 * 
				 * } }
				 */
			}
		}

		// Create vertices and edges for algebraic rules
		for (i = 0; i < model.getNumRules(); i++) {
			equation = new InnerNode(model.getRule(i).getMetaId());
			Rule r = model.getRule(i);
			if (r instanceof AlgebraicRule) {
				equations.add(equation);
				// all identifiers withn the MathML of this AlgebraicRule
				svariables.clear();
				getVariables(null, model.getRule(i).getMath(), svariables);
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
		StartNode matchingGraph;
		// the source node
		matchingGraph = new StartNode();
		// the sink node
		TerminalNode tnode = new TerminalNode();
		matching = new HashMap<String, String>();
		Set<Node> B = new HashSet<Node>();
		Stack<Node> stack = new Stack<Node>();
		Node first, last;
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
					if (!(stack.peek() instanceof TerminalNode)) {
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

			}// else remove from stack
			else
				stack.pop();

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
	private void findShortestPath(int i, Node node, ArrayList<Node> path) {
		String value;
		// Path has reached the desired length -> store it in the list
		if (path.size() == i * 2) {
			paths.add(path);
		} else {
			value = matching.get(node.getValue());
			// Search for the adjacent node convenient with the current matching
			for (Node next : node.getNodes()) {
				if (next.getValue() == value) {
					path.add(node);
					path.add(next);
					// Edge saved in the path -> carry on with every adjacent
					// node of the last node in the path
					for (Node nextnext : next.getNodes()) {
						if (nextnext.getValue() != node.getValue())
							findShortestPath(i, nextnext,
									(ArrayList<Node>) path.clone());
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
	public HashMap<String, String> getMatching() {
		return matching;
	}

	/**
	 * Returns the variables in a MathML object without local parameter
	 * 
	 * @param param
	 * 
	 * @param node
	 * @param variables
	 */
	private void getVariables(ListOf<LocalParameter> param, ASTNode node,
			List<String> variables) {
		// found node with species
		if (node.isName() && !node.isFunction()) {
			if (!node.isConstant()) {
				if (param == null)
					variables.add(node.getName());
				else {
					if (!param.contains(node.getName())) {
						variables.add(node.getName());
					}
				}
			}

		}
		// Else found operator or function
		else {
			// carry on with all children
			Enumeration<ASTNode> nodes = node.children();
			while (nodes.hasMoreElements()) {
				getVariables(param, nodes.nextElement(), variables);
			}
		}

	}

	/**
	 * Initializes the Converter
	 */
	private void init() {
		this.svariables = new ArrayList<String>();
		this.reactants = new HashSet<String>();

		for (int i = 0; i < model.getNumReactions(); i++) {

			for (SpeciesReference sref : model.getReaction(i)
					.getListOfProducts()) {
				reactants.add(sref.getSpecies());
			}

			for (SpeciesReference sref : model.getReaction(i)
					.getListOfReactants()) {
				reactants.add(sref.getSpecies());
			}

		}

		// Build the graph the matching and try to improve the matching
		buildGraph();
		buildMatching();
		augmentMatching();

	}

	/**
	 * Returns a boolean that indicates whether the given model is
	 * overdetermined or not.
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
	private void updateMatching(ArrayList<Node> path) {
		System.out.println("new length: " + (path.size() - 1));
		int index;
		index = 1;
		while (path.size() > index) {
			matching.remove(path.get(index).getValue());
			matching.put(path.get(index).getValue(), path.get(index - 1)
					.getValue());
			index = index + 2;

		}

	}
}
