package org.sbml.jsbml;

import org.junit.Test;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link TreeNodeVisitor} scaffolding.
 * Ensures the generic Visitor Pattern interfaces can be implemented and traversed.
 *
 * @author Deepak Yadav
 */
public class TreeNodeVisitorTest {

    /**
     * A simple dummy implementation to prove the generic interface works.
     * We use Integer as our <T> to act as a visit counter.
     */
    class DummyVisitor implements TreeNodeVisitor<Integer> {
        int nodesVisited = 0;

        @Override
        public Integer visit(SBase sbase) {
            nodesVisited++;
            return nodesVisited;
        }

        @Override
        public Integer visit(TreeNodeWithChangeSupport node) {
            nodesVisited++;
            return nodesVisited;
        }
    }

    @Test
    public void testVisitorAcceptance() {
        // Instantiate our custom visitor
        DummyVisitor visitor = new DummyVisitor();
        
        // ASTNode is one of the core components that received the accept() method in PR #312
        ASTNode dummyNode = new ASTNode(ASTNode.Type.PLUS);
        
        // Execute the accept method. This proves that ASTNode can take a TreeNodeVisitor,
        // route it to the correct visit() method, and return the generic <T> type.
        Integer result = dummyNode.accept(visitor);
        
        // Verify the visitor successfully entered the node and updated its internal state
        assertEquals("Visitor should have recorded exactly 1 visit", 1, visitor.nodesVisited);
        
        // Verify that the accept() method successfully returned the <T> (Integer) value
        assertEquals("The accept method should return the value from the visitor", Integer.valueOf(1), result);
    }
}