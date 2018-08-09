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

package org.sbml.jsbml.validator.offline.constraints;

import java.util.Set;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ElementOrderValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;;

/**
 * @author Roman
 * @since 1.2
 */
public class ConstraintConstraints extends AbstractConstraintDeclaration {

  /**
   * 
   *
   */
  public static String[] CONSTRAINT_ELEMENTS_ORDER = 
    {"notes", "annotation", "math", "message"};

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      if (level >= 3) {
        set.add(CORE_21007);
        set.add(CORE_21008);
        set.add(CORE_21009);
      }
      if (level >= 2) {
        
        if (context.isLevelAndVersionLessThan(3, 2)) {
          set.add(CORE_21001);
        }
        
        set.add(CORE_21003);
      }
      
      if (level == 2) {
        set.add(CORE_21002);    
        
        if (version > 1) {
          set.add(CORE_21006);
        }
      }
      
      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 1) || level > 2)
      {
        set.add(CORE_10706);
      }
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Constraint> func = null;

    switch (errorCode) {
    case CORE_10706:
      return SBOValidationConstraints.isMathematicalExpression;
      
    case CORE_21001:
      func = new ValidationFunction<Constraint>() {

        @Override
        public boolean check(ValidationContext ctx, Constraint c) {

          if (c.isSetMath()) {

            return c.getMath().isBoolean();
          }

          return true;
        }
      };
      break;
      
    case CORE_21002:
      func = new ElementOrderValidationFunction<Constraint>(CONSTRAINT_ELEMENTS_ORDER);
      break;
      
    case CORE_21003: {
      func = new ValidationFunction<Constraint>() {

        @Override
        public boolean check(ValidationContext ctx, Constraint c) {

          if (c.isSetMessage()) {

            // check XMLNode structure
            XMLNode message = c.getMessage();
            boolean xhtmlNamespaceDeclared = true;
            
            if (message.getChildCount() > 0)
            {
              for (int i = 0; i < message.getChildCount(); i++)
              {
                // Doing the test on each top level element.
                XMLNode child = message.getChild(i);
                
                if (!child.isElement()) {
                  continue;
                }
                
                String namespace = child.getNamespaceURI();
                String prefix = child.getPrefix();

                if (namespace != null && namespace.equals(JSBML.URI_XHTML_DEFINITION)) {
                  continue;
                }
                if (prefix != null && prefix.trim().length() > 0) {
                  namespace = child.getNamespaceURI(prefix);

                  if (JSBML.URI_XHTML_DEFINITION.equals(namespace)) {
                    continue;
                  }
                  
                  namespace = message.getNamespaceURI(prefix);
                  if (JSBML.URI_XHTML_DEFINITION.equals(namespace)) {
                    continue;
                  }
                  
                  xhtmlNamespaceDeclared = recursiveNamespaceCheck((SBase) message.getParent(), prefix);
                } else {
                  // There is not the correct namespace URI or any prefix so it is in the sbml namespace.
                  return false;
                }
                
                if (!xhtmlNamespaceDeclared) {
                  break;
                }
              }
            }
            
            return xhtmlNamespaceDeclared;
          }

          return true;
        }

        /**
         * Checks recursively the parent tree of the given SBase to search if
         * one declared namespace with the given prefix correspond to the XHTML
         * namespace.
         * 
         * @param sbase the sbase to search
         * @param prefix the prefix to search
         * @return true if we find the given prefix and it  correspond to the XHTML
         * namespace, false otherwise.
         */
        private boolean recursiveNamespaceCheck(SBase sbase, String prefix) 
        {
          if (sbase != null) {
            String uri1 = sbase.getDeclaredNamespaces().get(prefix);
            String uri2 = sbase.getDeclaredNamespaces().get("xmlns:" + prefix); // TODO - check if we always store one of these two form in the map
                        
            if (JSBML.URI_XHTML_DEFINITION.equals(uri1) || JSBML.URI_XHTML_DEFINITION.equals(uri2)) {
              return true;
            } else if (uri1 != null || uri2 != null) {
              // the prefix is found but it is not the right namespace
              return false;
            } else {
              return recursiveNamespaceCheck((SBase) sbase.getParent(), prefix);
            }
          }
          
          return false;
        }
      };
      break;
    }
      
    case CORE_21006: 
    {
      func = new ValidationFunction<Constraint>() {

        @Override
        public boolean check(ValidationContext ctx, Constraint sb) {
          boolean isValid = true;
          
          if (sb.isSetMessage()) {
            
            // checking that the notes follow a specific valid HTML structure
            XMLNode messageNode = sb.getMessage();
            
            int firstElementIndex = AbstractSBase.getFirstElementIndex(messageNode);

            String cname = "";

            if (firstElementIndex != -1) {
              cname = messageNode.getChildAt(firstElementIndex).getName(); 
              
              if (cname == "html") {
                
                // check the structure of the html element
                XMLNode htmlNode = messageNode.getChildAt(firstElementIndex);
                XMLNode headNode = null;
                XMLNode bodyNode = null;
                
                boolean headFound = false;
                boolean bodyFound = false;
                boolean otherElementFound = false;

                for (int i = 0; i < htmlNode.getChildCount(); i++) {
                  XMLNode child = htmlNode.getChildAt(i);

                  if (child.isElement()) {
                    if (child.getName().equals("head")) {
                      headFound = true;
                      headNode = child;
                    } else if (child.getName().equals("body") && headFound) {
                      bodyFound = true;
                      bodyNode = child;
                    } else {
                      otherElementFound = true;
                    }
                  }
                }

                if (!headFound || !bodyFound || otherElementFound) {
                  // TODO - do a proper error message
                  return false;
                }
                
                boolean validHead = SBaseConstraints.checkHtmlHeadContent(ctx, sb, headNode);;
                
                return validHead && SBaseConstraints.checkHtmlBodyContent(ctx, sb, bodyNode);
                
              } else if (cname == "body") {

                // check each top level elements inside the body element
                XMLNode bodyNode = messageNode.getChildAt(firstElementIndex);
                
                return SBaseConstraints.checkHtmlBodyContent(ctx, sb, bodyNode);
                
              } else {
                
                // check each top level elements
                return SBaseConstraints.checkHtmlBodyContent(ctx, sb, messageNode);
              }
            }            
          }

          return isValid;
        }
      };
      break;
    }

    case CORE_21007:
      func = new ValidationFunction<Constraint>() {

        @Override
        public boolean check(ValidationContext ctx, Constraint ia) {
          
          if (ia.isSetMath()) {
            if (ia.isSetUserObjects() && ia.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT) != null) {
              int nbMath = ((Number) ia.getUserObject(MathMLStaxParser.JSBML_MATH_COUNT)).intValue();
          
              return nbMath == 1;                  
            }
          } else if (ia.getLevelAndVersion().compareTo(3, 2) < 0) {
            // math is mandatory before SBML L3V2
            return false;
          }
          
          return true;
        }
      };
      break;

    case CORE_21008:
      func = new DuplicatedElementValidationFunction<Constraint>("message");
      break;
      
    case CORE_21009:
      func = new UnknownAttributeValidationFunction<Constraint>();
      break;
      
    }
    
    return func;
  }
}
