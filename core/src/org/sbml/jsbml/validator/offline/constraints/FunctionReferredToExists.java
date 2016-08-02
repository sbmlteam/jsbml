package org.sbml.jsbml.validator.offline.constraints;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.validator.offline.ValidationContext;

public class FunctionReferredToExists
implements ValidationFunction<FunctionDefinition> {

  private List<String> definedFunctions = new ArrayList<String>();


  @Override
  public boolean check(ValidationContext ctx, FunctionDefinition fd) {

    Model m = fd.getModel();
    if (m != null) {
      definedFunctions.clear();

      for (int i = 0; i < m.getFunctionDefinitionCount(); i++) {
        FunctionDefinition def = m.getFunctionDefinition(i);

        // Collect all functions which are defined before this one
        if (def.getId() != fd.getId()) {
          definedFunctions.add(def.getId());

        } else {
          break;
        }
      }

      return checkCiIsFunction(fd);

    }
    return true;
  }


  // Using iteration instead of recursion to avoid big stacks
  private boolean checkCiIsFunction(FunctionDefinition fd) {

    if (fd.getBody() == null) {
      return true;
    }

    Queue<ASTNode> queue = new LinkedList<ASTNode>();

    queue.offer(fd.getBody());

    while (queue.size() > 0) {
      ASTNode node = queue.poll();

      // Checks if the node is a function and if so, if it was declared before
      if (node.isFunction() && !definedFunctions.contains(node.getName())) {
        return false;
      }

      // Add all the children to the queue to check them
      for (ASTNode n : node.getListOfNodes()) {

        // Maybe unnecessary check?
        if (n != null) {
          queue.offer(n);
        }
      }
    }

    return true;
  }
}
