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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UniqueSId;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;

/**
 * 
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public class SBaseConstraints extends AbstractConstraintDeclaration {

  
  /**
   * 
   */
  private static final List<String> namespacesTocheckFor10403 = Arrays.asList("http://www.sbml.org/sbml/level1",
    "http://www.sbml.org/sbml/level2", "http://www.sbml.org/sbml/level2/version2",
    "http://www.sbml.org/sbml/level2/version3", "http://www.sbml.org/sbml/level2/version4",
    "http://www.sbml.org/sbml/level2/version5");
  
  /**
   * List of HTML flow content elements that are allowed inside the body tag.
   */
  private static final List<String> htmlFlowContentElementList = Arrays.asList("a", "abbr", "address", "article", "aside", "audio", "b","bdo", "bdi", "blockquote", "br", 
      "button", "canvas", "cite", "code", "command", "data", "datalist", "del", "details", "dfn", "div", "dl", "em", "embed", "fieldset", "figure", "footer", "form", 
      "h1", "h2", "h3", "h4", "h5", "h6", "header", "hgroup", "hr", "i", "iframe", "img", "input", "ins", "kbd", "keygen", "label", "main", "map", "mark", "math",
      "menu", "meter", "nav", "noscript", "object", "ol", "output", "p", "pre", "progress", "q", "ruby", "s", "samp", "script", "section", "select", "small",
      "span", "strong", "sub", "sup", "svg", "table", "template", "textarea", "time", "ul", "var", "video", "wbr", "center");
  
  
  
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForCheck(java.util.Set, int, int,
   * org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:

      set.add(CORE_10401);
      set.add(CORE_10402);
      set.add(CORE_10801);
      set.add(CORE_10802);
      set.add(CORE_10803);
      
      if (level == 2) {
        set.add(CORE_10403);
        set.add(CORE_10804);
      }

      if (level > 2) {
        set.add(CORE_10404);
        set.add(CORE_10805);
      }
      
      break;
    case IDENTIFIER_CONSISTENCY:
      
      set.add(CORE_10301);
      
      if (level > 1) {
        set.add(CORE_10307);
        set.add(CORE_10308);
        set.add(CORE_10309);
      }
      
      set.add(CORE_10310);
      
      break;
    case MATHML_CONSISTENCY:
      
      set.add(CORE_10201);
      
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:

      if (level > 2 || (level == 2 && version > 1)) {
        set.add(CORE_99701);
        set.add(CORE_99702);
      }

      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {

  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * getValidationFunction(int)
   */
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SBase> func = null;

    switch (errorCode) {
      case CORE_10201: {
        func = new ValidationFunction<SBase>() {

          @Override
          public boolean check(ValidationContext ctx, SBase node) {

            if (node instanceof MathContainer && node.getUserObject(JSBML.UNKNOWN_XML) != null) {

              // TODO - check that the top level node(s) is/are part of the mathml possible elements
//              try {
//                System.out.println("10201 - MathContainer - node unknown = " + ((XMLNode) node.getUserObject(JSBML.UNKNOWN_XML)).toXMLString());
//              } catch (XMLStreamException e) {
//                e.printStackTrace();
//              }

              return false;
            }

            return true;
          }
        };
        break;
      }

      case CORE_10301: 
      {
        func = new ValidationFunction<SBase>() {

          @Override
          public boolean check(ValidationContext ctx, SBase sb) {
            
            if (sb instanceof UniqueSId && sb.getPackageName().equals("core")) { // TODO - Each package will have a different error number
              if (!sb.isSetId()) {

                // checking in the invalid user object
                String invalidId = ValidationTools.checkInvalidAttribute(ctx, (AbstractTreeNode) sb, "id");

                if (invalidId != null && SyntaxChecker.isValidId(invalidId, ctx.getLevel(), ctx.getVersion())) {
                  // If the id has a valid syntax, then it is a duplicated id.
                  // TODO - create a nice error message
                  return false;
                }

                if (ctx.getLevel() == 1) {
                  invalidId = ValidationTools.checkInvalidAttribute(ctx, (AbstractTreeNode) sb, "name");

                  if (invalidId != null && SyntaxChecker.isValidId(invalidId, ctx.getLevel(), ctx.getVersion())) {
                    return false;
                  }
                } 
              }  
            }
            
            return true;
          }
        };
        break;
      }
      

    case CORE_10307:
      func = new ValidationFunction<SBase>() {

        @SuppressWarnings("unchecked")
        @Override
        public boolean check(ValidationContext ctx, SBase sb) {

          if (sb.isSetMetaId()) {
            Object o = ctx.getHashMap().get(ValidationTools.KEY_META_ID_SET);
            Set<String> metaIds;

            if (o != null && o instanceof Set) {
              metaIds = (Set<String>) o;
            } else {
              metaIds = new HashSet<String>();
              ctx.getHashMap().put(ValidationTools.KEY_META_ID_SET, metaIds);
            }

            boolean added = metaIds.add(sb.getMetaId());  
            
            return added;
          }

          return true;
        }
      };
      break;

    case CORE_10308:
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {

          if (sb.isSetSBOTerm()) {
            return ValidationTools.isSboTerm(sb.getSBOTermID()); 
          }
          String wrongSboTerm = getWrongSBOTerm(sb);
          
          if (wrongSboTerm != null) {
            return false;
          }
          
          return true;
        }
        
        /**
         * Returns a String corresponding to a wrong SBO term that
         * could not be set to the SBase.
         * 
         * @param sb an SBase
         * @return a String corresponding to a wrong SBO term
         */
        private String getWrongSBOTerm(SBase sb) {
        
          if (sb.isSetUserObjects() && (sb.getUserObject(AbstractSBase.JSBML_WRONG_SBO_TERM) != null)) {
            return (String) sb.getUserObject(AbstractSBase.JSBML_WRONG_SBO_TERM);
          }
          
          return null;
        }
        
      };
      break;

    case CORE_10309: 
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {

          if (sb.isSetMetaId()) {
            return SyntaxChecker.isValidMetaId(sb.getMetaId()); 
          }
          
          return true;
        }
      };
      break;
    }
     
    case CORE_10310: 
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          
          if (sb.isSetId()) {
            return SyntaxChecker.isValidId(sb.getId(), ctx.getLevel(), ctx.getVersion()); 
          }
          
          // checking in the invalid user object
          String invalidId = ValidationTools.checkInvalidAttribute(ctx, (AbstractTreeNode) sb, "id");
          
          if (invalidId != null) {
            return false;
          }
          
          if (ctx.getLevel() == 1) {
            invalidId = ValidationTools.checkInvalidAttribute(ctx, (AbstractTreeNode) sb, "name");
            
            if (invalidId != null) {
              return false;
            }
          }
          
          return true;
        }
      };
      break;
    }
    
    case CORE_10401: 
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          boolean isValid = true;
          
          if (sb.isSetAnnotation()) {
            
            XMLNode unknownNode = (XMLNode) sb.getAnnotation().getUserObject(JSBML.UNKNOWN_XML);
            
            if (unknownNode != null) {
                return false;
            }
          }

          return isValid;
        }
      };
      break;
    }

    case CORE_10402: 
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          boolean isValid = true;
          
          if (sb.isSetAnnotation()) {
            
            // loop over the top level elements of the annotation and compare the prefixes used
            XMLNode annoNode = sb.getAnnotation().getFullAnnotation();
            Set<String> namespaceSet = new HashSet<String>();
            
            for (int i = 0; i < annoNode.getChildCount(); i++) {
              XMLNode topLevelChild = annoNode.getChild(i);
              
              if (topLevelChild.isText()) {
                continue;
              }
              
              String namespace = topLevelChild.getNamespaceURI();
              String prefix = topLevelChild.getPrefix();              
              
              if (namespace == null || namespace.trim().length() == 0) {
                // Trying to get the namespace from the prefix
                namespace = topLevelChild.getNamespaceURI(prefix);
                
                // If not found, recursively go up the SBase tree to find the namespace definition ?
              }
              
              if (namespace != null && namespace.trim().length() > 0) {
                if (namespaceSet.contains(namespace)) {
                  // TODO - report error properly
                  return false;
                } else {
                  namespaceSet.add(namespace);
                }
              }
            }
          }

          return isValid;
        }
      };
      break;
    }

    case CORE_10403: 
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          boolean isValid = true;
          
          if (sb.isSetAnnotation()) {
            
            // loop over the top level elements of the annotation and make sure no SBML core namespace are used
            XMLNode annoNode = sb.getAnnotation().getFullAnnotation();
            
            for (int i = 0; i < annoNode.getChildCount(); i++) {
              XMLNode topLevelChild = annoNode.getChild(i);

              if (topLevelChild.isText()) {
                continue;
              }

              XMLNamespaces namespaces = topLevelChild.getNamespaces();
              
              for (int j = 0; j < namespaces.getLength(); j++) {
                String namespace = namespaces.getURI(j);
                
                if (namespacesTocheckFor10403.contains(namespace)) {
                  // TODO - report error properly
                  return false;
                }
              }
            }
          }

          return isValid;
        }
      };
      break;
    }

    case CORE_10404:
      func = new DuplicatedElementValidationFunction<SBase>("annotation");
      break;

    case CORE_10801: 
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          boolean isValid = true;
          
          XMLNode unknownNode = (XMLNode) sb.getUserObject(JSBML.UNKNOWN_XML);
          
          if (unknownNode != null) {
            // TODO - check that the unknown nodes are from the notes element there
            return false;
          }
          
          if (sb.isSetNotes()) {
            
            // loop over the top level elements of the notes and check that they are in the XHTML namespace
            XMLNode notesNode = sb.getNotes();
            
            for (int i = 0; i < notesNode.getChildCount(); i++) {
              XMLNode topLevelChild = notesNode.getChild(i);
              
              if (topLevelChild.isText()) {
                continue;
              }
              
              String namespace = topLevelChild.getNamespaceURI();
              String prefix = topLevelChild.getPrefix();              
              
              if (namespace == null || namespace.trim().length() == 0) {
                // Trying to get the namespace from the prefix
                namespace = topLevelChild.getNamespaceURI(prefix);
                
                if (namespace == null || namespace.trim().length() == 0) {
                  // Recursively go up the SBase tree to find the namespace definition 
                  HashMap<String, String> declaredNamespaces = AbstractSBase.getAllDeclaredNamespaces(sb);
                  
                  namespace = declaredNamespaces.get("xmlns:" + prefix);                  
                }
              }
              
              if (namespace != null && namespace.trim().length() > 0 && namespace.equals(JSBML.URI_XHTML_DEFINITION)) {
                continue;
              } else {
                // TODO - report error properly
                return false;
              }
            }
          }

          return isValid;
        }
      };
      break;
    }

//    case CORE_10802: // nothing to test for 10802 and 10803, the XML would not be read by the library
//    case CORE_10803: 

    case CORE_10804: 
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {
          boolean isValid = true;
          
          if (sb.isSetNotes()) {
            
            // checking that the notes follow a specific valid HTML structure
            XMLNode notesNode = sb.getNotes();
            
            int firstElementIndex = AbstractSBase.getFirstElementIndex(notesNode);

            String cname = "";

            if (firstElementIndex != -1) {
              cname = notesNode.getChildAt(firstElementIndex).getName(); 
              
              if (cname == "html") {
                
                // check the structure of the html element
                XMLNode htmlNode = notesNode.getChildAt(firstElementIndex);
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
                
                boolean validHead = checkHtmlHeadContent(ctx, sb, headNode);;
                
                return validHead && checkHtmlBodyContent(ctx, sb, bodyNode);
                
              } else if (cname == "body") {

                // check each top level elements inside the body element
                XMLNode bodyNode = notesNode.getChildAt(firstElementIndex);
                
                return checkHtmlBodyContent(ctx, sb, bodyNode);
                
              } else {
                
                // check each top level elements
                return checkHtmlBodyContent(ctx, sb, notesNode);
              }
            }            
          }

          return isValid;
        }
      };
      break;
    }

    case CORE_10805:
      func = new DuplicatedElementValidationFunction<SBase>("notes");
      break;
      
    case CORE_99701:
    {
      func = new ValidationFunction<SBase>() {

        @Override
        public boolean check(ValidationContext ctx, SBase sb) {

          if (sb.isSetSBOTerm()) {
            return ValidationTools.isSboTerm(sb.getSBOTermID());
          }

          return true;
        }
      };
      break;
    }
    
    case CORE_99702:
    {
      func = SBOValidationConstraints.isObsolete;
      break;
    }
    }
    
    return func;
  }


  /**
   * Checks that each sub elements of the given {@link XMLNode} are allowed inside a body element.
   * 
   * <p>Meaning that they are part of the flow content HTML elements.</p>
   * 
   * @param ctx the Validation context
   * @param sb the SBase we are validating
   * @param topLevelNode the XMLNode to validate
   * @return true if each sub elements of the given {@link XMLNode} are allowed inside a body element, false otherwise.
   */
  public static boolean checkHtmlBodyContent(ValidationContext ctx, SBase sb, XMLNode topLevelNode) {
    
    if (sb.isSetUserObjects() && sb.getUserObject(JSBML.UNKNOWN_XML) != null) {
      // unknown elements in the sbml namespace
      // TODO - the unknown elements should be added to the proper XMLNode directly and not the SBase 
      return false;
    }
    
    for (int i = 0; i < topLevelNode.getChildCount(); i++) {
      XMLNode topLevelChild = topLevelNode.getChild(i);
      
      if (topLevelChild.isElement() && !htmlFlowContentElementList.contains(topLevelChild.getName())) {
        // TODO - do a proper error message
        return false;
      }
    }

    return true;
  }

  /**
   * Checks that each sub elements of the given {@link XMLNode} are allowed inside a head element.
   * 
   * 
   * @param ctx the Validation context
   * @param sb the SBase we are validating
   * @param topLevelNode the XMLNode to validate
   * @return true if each sub elements of the given {@link XMLNode} are allowed inside a head element, false otherwise.
   */
  public static  boolean checkHtmlHeadContent(ValidationContext ctx, SBase sb, XMLNode topLevelNode) {
    
    boolean titlePresent = false;
    
    for (int i = 0; i < topLevelNode.getChildCount(); i++) {
      XMLNode topLevelChild = topLevelNode.getChild(i);
      
      if (topLevelChild.isElement() && topLevelChild.getName().equals("title")) {
        titlePresent = true;
      }
    }

    if (!titlePresent) {
      // TODO - do a proper error message
    }
    
    return titlePresent;
  }
}
