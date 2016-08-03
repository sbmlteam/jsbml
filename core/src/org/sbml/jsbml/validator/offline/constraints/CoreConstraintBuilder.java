/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2016 jointly by the following organizations:
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
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Priority;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Variable;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

@SuppressWarnings("deprecation")
public class CoreConstraintBuilder implements SBMLErrorCodes{


  protected static  ValidationConstraint<?> createReactionConstraint(int errorCode)
  {
    ValidationFunction<Reaction> func = null;

    switch (errorCode) {
    case CORE_21101:
      func = new ValidationFunction<Reaction>() {

        @Override
        public boolean check(ValidationContext ctx, Reaction r) {
          // TODO Auto-generated method stub


          return r.getNumReactants() > 0 || r.getNumProducts() > 0;
        }
      };

    case CORE_21107:
      func = new ValidationFunction<Reaction>() {

        @Override
        public boolean check(ValidationContext ctx, Reaction r) {
          // TODO Auto-generated method stub

          if (r.isSetCompartment())
          {

            return r.getCompartmentInstance() != null;
          }

          return true;
        }
      };

    case CORE_21111:
      return new ValidationConstraint<SimpleSpeciesReference>(errorCode, new ValidationFunction<SimpleSpeciesReference>() {

        @Override
        public boolean check(ValidationContext ctx, SimpleSpeciesReference sr) {


          return sr.getSpeciesInstance() != null;
        }
      });


   

    case CORE_21125:
      return new ValidationConstraint<KineticLaw>(errorCode, new ValidationFunction<KineticLaw>() {
        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {

          return !kl.isSetSubstanceUnits();
        }
      });

    case CORE_21126:
      return new ValidationConstraint<KineticLaw>(errorCode, new ValidationFunction<KineticLaw>() {
        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {

          return !kl.isSetTimeUnits();
        }
      });

    case CORE_21130:
      return new ValidationConstraint<KineticLaw>(errorCode, new ValidationFunction<KineticLaw>() {
        @Override
        public boolean check(ValidationContext ctx, KineticLaw kl) {
          return kl.isSetMath();
        }
      });
    }

    return new ValidationConstraint<Reaction>(errorCode, func);
  }

  protected static AnyConstraint<?> createEventConstraint(int errorCode) {
    ValidationFunction<Event> func = null;

    switch (errorCode) {
    case CORE_21201:

      func = new ValidationFunction<Event>() {


        @Override
        public boolean check(ValidationContext ctx, Event e) {

          return e.isSetTrigger();
        }
      };

    case CORE_21202:
      return new ValidationConstraint<Trigger>(errorCode, new ValidationFunction<Trigger>() {
        @Override
        public boolean check(ValidationContext ctx, Trigger t) {

          if (t.isSetMath())
          {
            return t.getMath().isBoolean();
          }

          return false;
        }
      });

    case CORE_21203:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          return e.getNumEventAssignments() != 0;
        }
      };

    case CORE_21204:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (e.isSetTimeUnits())
          {
            String s = e.getTimeUnits();
            UnitDefinition def = e.getTimeUnitsInstance();


            boolean isTime = (s == "time") || 
                (s == "second") || 
                (def.isVariantOfTime());

            if (ctx.isLevelAndVersionEqualTo(2, 2))
            {
              return isTime;
            }
            else
            {
              return isTime ||
                  (s == "dimensionless");
            }
          }

          return e.getNumEventAssignments() != 0;
        }
      };

    case CORE_21206:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (!e.getUseValuesFromTriggerTime()){
            return e.isSetDelay();
          }

          return true;
        }
      };

    case CORE_21207:

      func = new ValidationFunction<Event>() {

        @Override
        public boolean check(ValidationContext ctx, Event e) {

          if (e.isSetDelay())
          {
            return e.isSetUseValuesFromTriggerTime();
          }

          return true;
        }
      };

    case CORE_21209:
      return new ValidationConstraint<Trigger>(errorCode, new ValidationFunction<Trigger>() {
        @Override
        public boolean check(ValidationContext ctx, Trigger t) {


          return t.isSetMath();
        }
      });

    case CORE_21210:
      return new ValidationConstraint<Delay>(errorCode, new ValidationFunction<Delay>() {
        @Override
        public boolean check(ValidationContext ctx, Delay d) {


          return d.isSetMath();
        }
      });

    case CORE_21211:
      return new ValidationConstraint<EventAssignment>(errorCode, new ValidationFunction<EventAssignment>() {
        @Override
        public boolean check(ValidationContext ctx, EventAssignment ea) {

          if (ea.isSetVariable())
          {
            Variable var = ea.getVariableInstance();
            boolean isComSpecOrPar =  (var instanceof Compartment) ||
                (var instanceof Species) ||
                (var instanceof Parameter);

            if (ctx.getLevel() == 2){
              return isComSpecOrPar;
            }
            else{
              return isComSpecOrPar || (var instanceof SpeciesReference);
            }

          } 

          return true;
        }
      });

    case CORE_21212:
      return new ValidationConstraint<EventAssignment>(errorCode, new ValidationFunction<EventAssignment>() {
        @Override
        public boolean check(ValidationContext ctx, EventAssignment ea) {

          if(ea.isSetVariable())
          {
            Variable var = ea.getVariableInstance();

            return var != null && var.isConstant();
          }

          return true;
        }
      });

    case CORE_21213:
      return new ValidationConstraint<EventAssignment>(errorCode, new ValidationFunction<EventAssignment>() {
        @Override
        public boolean check(ValidationContext ctx, EventAssignment ea) {

          return ea.isSetMath();
        }
      });

    case CORE_21231:
      return new ValidationConstraint<Priority>(errorCode, new ValidationFunction<Priority>() {
        @Override
        public boolean check(ValidationContext ctx, Priority p) {

          return p.isSetMath();
        }
      });
    }

    return new ValidationConstraint<Event>(errorCode, func);
  }



}
