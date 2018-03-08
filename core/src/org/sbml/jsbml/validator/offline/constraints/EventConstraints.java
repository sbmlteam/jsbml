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

import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedElementValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ElementOrderValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UniqueValidation;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownElementValidationFunction;;

/**
 * @author Roman
 * @since 1.2
 */
public class EventConstraints extends AbstractConstraintDeclaration {

  /**
   * 
   *
   */
  public static String[] EVENT_ELEMENTS_ORDER = 
    {"notes", "annotation", "trigger", "delay", "listOfEventAssignments"};
  
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
      if (level == 2) {
        set.add(CORE_21201);
        set.add(CORE_21203);
        set.add(CORE_21205);

        if (version < 3) {
          set.add(CORE_21204);
        } else if (version >= 4) {
          set.add(CORE_21206);
        }

        if (version > 2) {
          set.add(CORE_99206);
        }
      } else if (level == 3) {
        addRangeToSet(set, CORE_21201, CORE_21202);
        
        if (version == 1) {
          set.add(CORE_21203);
        }
        set.add(CORE_21221);
        set.add(CORE_21222);
        set.add(CORE_21223);
        set.add(CORE_21224);
        set.add(CORE_21225);
        set.add(CORE_21230);
        set.add(CORE_99206);
      }
      break;
    case IDENTIFIER_CONSISTENCY:
      set.add(CORE_10305);
      
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
        set.add(CORE_10710);
      }
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Event> func = null;

    switch (errorCode) {
    case CORE_10305:
      func = new UniqueValidation<Event, String>() {
       
        @Override
        public int getNumObjects(ValidationContext ctx, Event e) {
          return e.getNumEventAssignments();
        }
        @Override
        public String getNextObject(ValidationContext ctx, Event e, int n) {
          return e.getEventAssignment(n).getVariable();
        }
        
      };
      break;
      
    case CORE_10710:
      return SBOValidationConstraints.isInteraction;
      
    case CORE_21201:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          boolean duplicatedTriggerElement = new DuplicatedElementValidationFunction<TreeNodeWithChangeSupport>("trigger").check(ctx, e);
          
          if (ctx.isLevelAndVersionGreaterEqualThan(3, 2)) {
            return duplicatedTriggerElement;
          }
          
          return e.isSetTrigger() && duplicatedTriggerElement;
        }
      };
      break;
      
    case CORE_21203:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          return e.getNumEventAssignments() != 0;
        }
      };
      break;

    case CORE_21204:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (e.isSetTimeUnits()) {
            UnitDefinition def = e.getTimeUnitsInstance();

            if (def != null) {
              boolean isTime = def.isVariantOfTime();

              if (ctx.isLevelAndVersionEqualTo(2, 2)) {
                return isTime || def.isVariantOfDimensionless();
              } else {
                return isTime;
              }
            } else {
              // the units id is invalid
              return false;
            }
          }

          return true;
        }
      };
      break;
      
    case CORE_21205:
      func = new ElementOrderValidationFunction<Event>(EVENT_ELEMENTS_ORDER);
      break;
      
    case CORE_21206:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (!e.getUseValuesFromTriggerTime()) {
            return e.isSetDelay();
          }

          return true;
        }
      };
      break;
      
    case CORE_21207:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (e.isSetDelay()) {
            return e.isSetUseValuesFromTriggerTime();
          }

          return true;
        }
      };
      break;
      
    case CORE_21221:
      func = new DuplicatedElementValidationFunction<Event>("delay");
      break;
      
    case CORE_21222:
      func = new DuplicatedElementValidationFunction<Event>("listOfEventAssignments");
      break;
      
    case CORE_21223:
      func = new ValidationFunction<Event>() {
        
        @Override
        public boolean check(ValidationContext ctx, Event event) {
          
          if (event.isSetListOfEventAssignments() || event.isListOfEventAssignmentEmpty()) {
            UnknownElementValidationFunction<ListOf<EventAssignment>> unknownFunc = new UnknownElementValidationFunction<ListOf<EventAssignment>>();
            return unknownFunc.check(ctx, event.getListOfEventAssignments());
          }
          
          return true;
        }
      };
      break;
      
    case CORE_21224:
      func = new ValidationFunction<Event>() {
        
        @Override
        public boolean check(ValidationContext ctx, Event kl) {
          
          if (kl.isSetListOfEventAssignments()) {
            UnknownAttributeValidationFunction<ListOf<EventAssignment>> unknownFunc = 
                new UnknownAttributeValidationFunction<ListOf<EventAssignment>>();
            return unknownFunc.check(ctx, kl.getListOfEventAssignments());
          }
          
          return true;
        }
      };
      break;
      
    case CORE_21225:
      func = new UnknownAttributeValidationFunction<Event>() {
        
        @Override
        public boolean check(ValidationContext ctx, Event c) {
          // useValuesFromTriggerTime is a mandatory attribute
          if (!c.isSetUseValuesFromTriggerTime()) {
            return false;
          }
          return super.check(ctx, c);
        }
      };
      break;
      
    case CORE_21230:
      func = new DuplicatedElementValidationFunction<Event>("priority");
      break;
      
    case CORE_99206:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          return e.getTimeUnits().isEmpty();
        }
      };
      break;
    }

    return func;
  }
}
