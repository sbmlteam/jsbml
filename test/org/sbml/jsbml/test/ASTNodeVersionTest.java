package org.sbml.jsbml.test;

import org.sbml.jsbml.ASTNode;

/**
 * Displays the version of the ASTNode used in the classpath.
 * 
 * @author rodrigue
 *
 */
public class ASTNodeVersionTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    System.out.println("Using the " + ASTNode.IMPLEMENTATION_VERSION);
  }

}
