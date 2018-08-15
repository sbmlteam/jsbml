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
package org.sbml.jsbml.util.converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.util.filters.Filter;

/**
 * Expands user-defined {@link FunctionDefinition} in an SBML document.
 * 
 *  <p>When invoked, it performs the following operations:
 *
 * <li> Looks for invocations of {@link FunctionDefinition} in mathematical expressions
 * throughout the model.</li>
 * <li> For each invocation found, replaces the invocation with a in-line copy
 * of the function's body, similar to how macro expansions might be performed
 * in scripting and programming languages.</li></p>
 *
 * <p>For example, suppose the model contains a function definition
 * representing the function {@code f(x, y) = x * y}.  Further,
 * suppose this function invoked somewhere else in the model, in
 * a mathematical formula, as {@code f(s, p)},  the converter will replace
 * it with the expression {@code s * p}.
 * 
 * @author rodrigue
 *
 */
public class ExpandFunctionDefinitionConverter implements SBMLConverter {

  
  @Override
  public SBMLDocument convert(SBMLDocument doc) throws SBMLException {
    SBMLDocument resultdoc = doc;

    Model m = doc.getModel();
    
    if (m == null || m.getFunctionDefinitionCount() == 0) {
      // In this case, we cannot do anything about the FunctionDefinition or there is nothing to do
      return doc;
    }
    
    try {
      resultdoc = doc.clone();

      resultdoc.filter(new Filter() {

        @Override
        public boolean accepts(Object o) {
          if (o instanceof MathContainer) {

            // call an utility that will replace the FDs
            expandFunctionDefinition((MathContainer) o);
          }

          return false;
        }
      });
    } catch (Exception e) {
      // something went wrong during cloning. It can happen with malformed FunctionDefinition math
      // just returning the original unmodified document
    }

    return resultdoc;
  }

  /**
   * Expands the {@link FunctionDefinition} used in the given {@link MathContainer}.
   * 
   * <p>The given {@link MathContainer} can be modified so make sure that you
   * clone it beforehand if you don't want that to happen.</p>
   * 
   * @param mathcontainer the math container to expands
   */
  public static void expandFunctionDefinition(final MathContainer mathcontainer) {
    if (mathcontainer == null || mathcontainer instanceof FunctionDefinition || !mathcontainer.isSetMath()) {
      // first checks on the argument 
      return;
    }
    
    final Model m = mathcontainer.getModel();
    
    if (m == null || m.getFunctionDefinitionCount() == 0) {
      // In this case, we cannot do anything about the FunctionDefinition or there is nothing to do
      return;
    }
    
    // Starting the actual conversion
    ASTNode math = expandFunctionDefinition(m, mathcontainer.getMath());

    mathcontainer.setMath(math);
  }
  
  /**
   * Expands the {@link FunctionDefinition} used in the given {@link ASTNode}.
   * 
   * <p>The given {@link ASTNode} can be modified so make sure that you
   * clone it beforehand if you don't want that to happen. Make sure to use the returned
   * ASTNode as it can be different from the given one if the top level ASTNode is of type
   * {@link org.sbml.jsbml.ASTNode.Type#FUNCTION}.</p>
   * 
   * @param math the ASTNode to expands.
   */
  public static ASTNode expandFunctionDefinition(final Model m, final ASTNode math) {
    
    if (m == null || m.getFunctionDefinitionCount() == 0) {
      // In this case, we cannot do anything about the FunctionDefinition or there is nothing to do
      return math;
    }
    
    // Starting the actual conversion
    boolean foundFunctionDefinition = false;
    // container for the math to be able to modify it inside the Filter if the top level AST is a functionDefinition
    final List<ASTNode> container = new ArrayList<ASTNode>();
    container.add(math);
    
    do {
      
      List<? extends TreeNode> foundFunctionDefinitions = container.get(0).filter((new Filter() {
        
        @Override
        public boolean accepts(Object o) {
          if (o instanceof ASTNode) {
            ASTNode current = (ASTNode) o;
            
            if (current.getType() == ASTNode.Type.FUNCTION) {
              String sid = current.getName();
              FunctionDefinition fd = m.getFunctionDefinition(sid);
              
              if (fd != null) {
                // We found a FunctionDefinition referenced in a 'ci' mathML element
                // we need to expand it
                
                // TODO - test for infinite loop
            	  // System.out.println("expandFunctionDefinition - fdNode nb child = " + current.getChildCount());
                
                if (current.getChildCount() != fd.getArgumentCount()) {
                  System.out.println("expandFunctionDefinition - number of arguments differ, aborting for " + sid);
                  // return the ASTNode as it is so that the validation work for this FunctionDefinition.
                  return false;
                }
                
                ASTNode newMath = fd.getBody().clone(); 
                
                for (int i = 0; i < current.getChildCount(); i++) {
                  ASTNode bvar = fd.getArgument(i);
                  ASTNode expandedBVar = current.getChild(i);
                  
                  // test if newMath is directly equals to one of the arguments
                  if (newMath.equals(bvar)) {
                    // in this case we return directly expandedBVar
                    newMath = expandedBVar;
                    break;
                  } else {                  
                    replaceAll(newMath, bvar, expandedBVar);
                  }
                }
                
                TreeNode parent = current.getParent();
                
                if (parent == null || parent instanceof MathContainer) {
                  container.add(0, newMath);
                } else {
                  int index = parent.getIndex(current);

                  if (index != -1) {
                    ((ASTNode) parent).replaceChild(index, newMath);
                  }
                }
                
                return true;
              }
            }
          }
          
          return false;
        }

      }), false, true);
      
      if (foundFunctionDefinitions != null && foundFunctionDefinitions.size() > 0) {
        // System.out.println("found size = " + foundFunctionDefinitions.size());
        foundFunctionDefinition = true;
      } else {
        foundFunctionDefinition = false;
      }
      
    } while (foundFunctionDefinition);
    
    return container.get(0);
  }

  /**
   * Replaces all instances of {@code bvar} by {@code expandedBvar} inside the given {@link ASTNode}.
   * 
   * @param newMath the ASTNode to modify
   * @param bvar the node to be replaced
   * @param expandedBVar the node to insert in place of {@code bvar} 
   */
  private static void replaceAll(ASTNode newMath, final ASTNode bvar, final ASTNode expandedBVar) {

    newMath.filter(new Filter() {
      
      @Override
      public boolean accepts(Object o) {
        if (o instanceof ASTNode) {
          ASTNode current = (ASTNode) o;
          
          if (current.equals(bvar)) {
            ASTNode parent = (ASTNode) current.getParent();
            int index = parent.getIndex(current);
            
            if (index != -1) {
              parent.replaceChild(index, expandedBVar);
            }
          }
        }
        return false;
      }
    });
    
  }

  /**
   * 
   * @param args
   * @throws XMLStreamException
   * @throws IOException
   */
  public static void main(String[] args) throws XMLStreamException, IOException {
    SBMLDocument doc = new SBMLReader().readSBMLFromFile(args[0]);
    
    ExpandFunctionDefinitionConverter converter = new ExpandFunctionDefinitionConverter();
    
    SBMLDocument resultdoc = converter.convert(doc);
    
    System.out.println(new SBMLWriter().writeSBMLToString(resultdoc));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.SBMLConverter#setOption(java.lang.String)
   */
  
  @Override
  public void setOption(String name, String value) {
    // TODO Auto-generated method stub
  }
}
