package org.sbml.jsbml;

import org.sbml.jsbml.util.TreeNodeWithChangeSupport;

/**
 * A generic visitor interface for traversing the JSBML tree structure.
 * This allows external classes to operate on SBML components without
 * modifying the core classes.
 *
 * @param <T> The return type of the visitor operations. Note that this generic 
 * type may also be {@link Void} if nothing is to be returned.
 * @author Deepak Yadav
 */
public interface TreeNodeVisitor<T> {

    /**
     * Primary traversal method for core SBML components.
     *
     * @param sbase the core SBML element to visit
     * @return a result of type T
     */
    T visit(SBase sbase);

    /**
     * Fallback traversal method for auxiliary nodes (e.g., annotations, notes)
     * that do not inherit from SBase but do inherit from TreeNodeWithChangeSupport.
     *
     * @param node the tree node to visit
     * @return a result of type T
     */
    T visit(TreeNodeWithChangeSupport node);
}