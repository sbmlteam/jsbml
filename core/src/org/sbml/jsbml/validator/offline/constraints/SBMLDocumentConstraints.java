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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;;

/**
 * 
 * @author Roman
 * @since 1.2
 */
public class SBMLDocumentConstraints extends AbstractConstraintDeclaration {

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
      set.add(CORE_10102);
      
      set.add(CORE_20101);
      set.add(CORE_20102);
      set.add(CORE_20103);
      set.add(CORE_20201);
      set.add(CORE_20108);      
      
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
      if (context.isLevelAndVersionGreaterEqualThan(3, 1)) {
        set.add(CORE_10719);
      }

      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<SBMLDocument> func = null;

    switch (errorCode) {

    case CORE_10102:
      func = new ValidationFunction<SBMLDocument>() {

        /**
         * List of SBase that contain any unknown attributes or elements.
         * This list can be used later on to build proper error messages
         * to tell the user which elements have problems.
         */
        private List<SBase> sbaseWithUnknownXML = new ArrayList<SBase>();

        @SuppressWarnings("unchecked")
        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {

          // loop through the whole document to search for any unknown attributes or elements.            
          sbaseWithUnknownXML = (List<SBase>) d.filter(new Filter() {

            @Override
            public boolean accepts(Object o) {

              if (o instanceof SBase) {
                SBase sbase = (SBase) o;

                // System.out.println(sbase.getElementName() + " - " + sbase); // TODO - for 10102-fail-01-25-sev2-l3v1.xml, we go over 4 ListOf which should not be defined !

                if (sbase.isSetUserObjects() && sbase.getUserObject(JSBML.UNKNOWN_XML) != null)
                {
                  // if the user object is set, we know they are some unknown XML
                  // we return true to add it to the list of sbaseWithUnknownXML
                  return true;
                }
              }

              return false;
            }
          });

          // System.out.println(d.getModel());

          // if we found at least one element with unknown XML, fail the constraint
          if (sbaseWithUnknownXML.size() > 0) {
            return false;
          }

          return true;
        }

        /**
         * Returns a list of error messages to be used when creating the {@link SBMLError} instances.
         * 
         * @return a list of error messages to be used in the {@link SBMLError}
         */
        public List<String> buildErrorMessages() {

          // TODO - we need first to modify the ValidationFunction interface to be able to get custom error messages.
          for(SBase sbase : sbaseWithUnknownXML) {
            System.out.println(sbase);
          }

          return null;
        }

      };
      break;

    case CORE_10719: {
      func = new ValidationFunction<SBMLDocument>() {

        @Override
        public boolean check(ValidationContext ctx, SBMLDocument doc) {

          return SBOValidationConstraints.isModellingFramework.check(ctx, doc);
        }
      };
      break;
    }

    case CORE_20101:
      func = new ValidationFunction<SBMLDocument>() {

        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {

          String sbmlNamespace = getSBMLDocumentNamespace(d);
          
          if (sbmlNamespace == null) {
            return false;
          }
          
          
          int level = d.getLevel();
          int version = d.getVersion();
          
          String levelAndVersionNamespace = JSBML.getNamespaceFrom(level, version);
          
          if (sbmlNamespace == null || levelAndVersionNamespace == null) {
            return false;
          }
        
          return sbmlNamespace.equals(levelAndVersionNamespace);
        }
      };
      break;

    case CORE_20102:
      func = new ValidationFunction<SBMLDocument>() {

        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {

          if (!d.isSetLevel()) {
            return false;
          }
          
          String sbmlNamespace = getSBMLDocumentNamespace(d);
          
          if (sbmlNamespace == null) {
            return false;
          }
          
          int level = d.getLevel();          
          int namespaceLevel = -1;
          
          int levelPosition = sbmlNamespace.indexOf("level");
          
          if (levelPosition == -1) {
            namespaceLevel = -1;
          } else {
            String levelString = sbmlNamespace.substring(levelPosition + 5, levelPosition + 6);
            
            // get an int
            try {
              namespaceLevel = Integer.parseInt(levelString);
            } catch (NumberFormatException e) {
              // nothing to do
            }
          }
          
          return level == namespaceLevel;
        }

      };
      break;
      
    case CORE_20103:
      func = new ValidationFunction<SBMLDocument>() {

        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {

          if (!d.isSetVersion()) {
            return false;
          }
          
          String sbmlNamespace = getSBMLDocumentNamespace(d);
          
          if (sbmlNamespace == null) {
            return false;
          }
          
          int version = d.getVersion();          
          int namespaceVersion = -1;
          
          int levelPosition = sbmlNamespace.indexOf("version");
          
          if (levelPosition == -1) {
            namespaceVersion = 1; // cover the case of SBML L2V1
            
            if (d.getLevel() == 1 && (version == 1 || version == 2)) {
              return true;
            }
          } else {
            String levelString = sbmlNamespace.substring(levelPosition + 7, levelPosition + 8);
            
            // get an int
            try {
              namespaceVersion = Integer.parseInt(levelString);
            } catch (NumberFormatException e) {
              // nothing to do
            }
          }
          
          return version == namespaceVersion;
        }
      };
      break;
      
    case CORE_20108:
      func = new ValidationFunction<SBMLDocument>() {

        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {

          // go through the getSBMLDocumentAttributes() map
          Map<String, String> attributeMap = d.getSBMLDocumentAttributes();
          
          for (String attributeNameWithPrefix : attributeMap.keySet()) {
            
            if (attributeNameWithPrefix.contains("xmlns") || attributeNameWithPrefix.endsWith("required")) {
              continue;
            }
            
            // If we arrive here, we have an unknown attribute !
            return false;
          }
          
          return true;
        }
      };
      break;
      
    case CORE_20201:
      func = new ValidationFunction<SBMLDocument>() {

        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {

          // count the number of model xml element and check if it is not above 1
          DuplicatedElementValidationFunction<SBMLDocument> duplicatedModelcheck = new DuplicatedElementValidationFunction<SBMLDocument>(TreeNodeChangeEvent.model);

          if (!duplicatedModelcheck.check(ctx, d)) {
            return false;
          }
          
          if (ctx.isLevelAndVersionLessThan(3, 2)) {
            return d.getModel() != null;
          }
          else {
            // from L3V2 the model element can be undefined
            return true;
          }
        }
      };
      break;

    }

    return func;
  }
  
  /**
   * Returns the namespace of an {@link SBMLDocument}.
   * 
   * <p>The namespace is taken from the declared namespace map 
   * to be able to validate models with wrong combination of 
   * namespace and level and version.</p>
   * 
   * @param d an {@link SBMLDocument}
   * @return the declared namespace of an {@link SBMLDocument}
   */
  private String getSBMLDocumentNamespace(SBMLDocument d) {
    Map<String, String> namespaceMap = d.getDeclaredNamespaces();
    String sbmlNamespace = null;
    
    if (namespaceMap.size() != 0) {

      String namespaceKey = "xmlns";

      if (d.isSetUserObjects() && d.getUserObject(JSBML.ELEMENT_XML_PREFIX) != null) {
        namespaceKey = namespaceKey + ":" + d.getUserObject(JSBML.ELEMENT_XML_PREFIX);
      }

      sbmlNamespace = namespaceMap.get(namespaceKey);
    }

    return sbmlNamespace;
  }

}
