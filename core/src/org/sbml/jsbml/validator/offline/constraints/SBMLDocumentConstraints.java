/*
 * 
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

/**
 * 
 * @author Roman
 * @since 1.2
 * @date 04.08.2016
 */
public class SBMLDocumentConstraints extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      set.add(CORE_10102);
      
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
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode) {
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

    case CORE_20201:
      func = new ValidationFunction<SBMLDocument>() {

        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {

          return d.getModel() != null; // TODO - relaxed for SBML L3V2
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

    }

    return func;
  }
}
