/*
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2017 jointly by the following organizations: 
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
 * 
 * 
 * @author rodrigue
 *
 */
public class ExpandFunctionDefinitionConverter implements SBMLConverter {

  
  @Override
  public SBMLDocument convert(SBMLDocument doc) throws SBMLException {
    SBMLDocument resultdoc = doc.clone();
    
    // TODO - do we need first to expand the FunctionDefinition themselves if they can depend onto each other on some levels and versions ?
    
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
    
    return resultdoc;
  }

  
  /**
   * 
   * 
   * @param mathcontainer
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
    ASTNode math = mathcontainer.getMath();
    boolean foundFunctionDefinition = false;
    
    do {
      
      List<? extends TreeNode> foundFunctionDefinitions = math.filter((new Filter() {
        
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
                
                // TODO - test for loop
                System.out.println("expandFunctionDefinition - fdNode nb child = " + current.getChildCount());
                
                if (current.getChildCount() != fd.getArgumentCount()) {
                  System.out.println("expandFunctionDefinition - number of arguments differ, aborting for " + sid);
                }
                
                ASTNode newMath = fd.getBody().clone(); 
                
                for (int i = 0; i < current.getChildCount(); i++) {
                  ASTNode bvar = fd.getArgument(i);
                  ASTNode expandedBVar = current.getChild(i);
                  
                  // TODO - test if newMath is directly equals to one of the arguments
                  
                  replaceAll(newMath, bvar, expandedBVar);
                  // newMath.replaceArgument(bvar.getName(), expandedBVar);
                  
                }
                
                TreeNode parent = current.getParent();
                
                if (parent == null || parent instanceof MathContainer) {
                  mathcontainer.setMath(newMath);
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
        System.out.println("found size = " + foundFunctionDefinitions.size());
        foundFunctionDefinition = true;
      } else {
        foundFunctionDefinition = false;
      }
      
      math = mathcontainer.getMath();
      
    } while (foundFunctionDefinition);
    
  }

  /**
   * 
   * @param newMath
   * @param bvar
   * @param expandedBVar
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
}
