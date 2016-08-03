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

  
 
  

  /**
   * Create constraints 20204 and 20216
   * @param errorCode
   * @return
   */
  protected static AnyConstraint<?> createModelConstraint(int errorCode){
    ValidationFunction<Model> func = null;

    switch (errorCode) {
    case CORE_20201:
      return new ValidationConstraint<SBMLDocument>(errorCode, new ValidationFunction<SBMLDocument>() {
        @Override
        public boolean check(ValidationContext ctx, SBMLDocument d) {
          
          return d.getModel() != null;
        }
      });
    case CORE_20203:
      func = new ValidationFunction<Model>() {
        public boolean check(ValidationContext ctx, Model m) {
          boolean success = true;
          
          if (m.getCompartmentCount() == 0)
          {
            success = success && !m.isSetListOfCompartments();
          }
          
          if (m.getCompartmentTypeCount() == 0)
          {
            success = success && !m.isSetListOfCompartmentTypes();
          }
          
          if (m.getConstraintCount() == 0)
          {
            success = success && !m.isSetListOfConstraints();
          }
          
          if (m.getEventCount() == 0)
          {
            success = success && !m.isSetListOfEvents();
          }
          
          if (m.getFunctionDefinitionCount() == 0)
          {
            success = success && !m.isSetListOfFunctionDefinitions();
          }
          
          if (m.getInitialAssignmentCount() == 0)
          {
            success = success && !m.isSetListOfInitialAssignments();
          }
          
          if (m.getParameterCount() == 0)
          {
            success = success && !m.isSetListOfParameters();
          }
          
          if (m.getReactionCount() == 0)
          {
            success = success && !m.isSetListOfReactions();
          }
          
          if (m.getRuleCount() == 0)
          {
            success = success && !m.isSetListOfRules();
          }
          
          if (m.getSpeciesCount() == 0)
          {
            success = success && !m.isSetListOfSpecies();
          }
          
          if (m.getSpeciesTypeCount() == 0)
          {
            success = success && !m.isSetListOfSpeciesTypes();
          }
          
          if (m.getUnitDefinitionCount() == 0)
          {
            success = success && !m.isSetListOfUnitDefinitions();
          }
          
          return success;
        };
      };

    case CORE_20204:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (ctx.getLevel() > 1 && m.getNumSpecies() > 0)
          {
            return m.getNumCompartments() > 0;
          }

          return true;
        }
      };
      break;
   
    case CORE_20216:
      func = new ValidationFunction<Model>() {

        @Override
        public boolean check(ValidationContext ctx, Model m) {
          if (m.isSetConversionFactor())
          {
            return m.getConversionFactorInstance() != null;
          }
          return true;
        }
      };
      break;

    default:
      return null;
    }

    return new ValidationConstraint<Model>(errorCode, func);
  }

  protected static AnyConstraint<?> createUnitDefinitionConstraint(int errorCode) {
    ValidationFunction<UnitDefinition> func = null;

    switch (errorCode) {
    case CORE_20401:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          return Unit.isUnitKind(ud.getId(), ctx.getLevel(), ctx.getVersion());
        }
      };
      break;

    case CORE_20402:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ud.getId() == "substance"){
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1))
            {
              return ud.isVariantOfSubstance();
            }
            else {
              return ud.isVariantOfSubstance() || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
            }
          }

          return true;
        }
      };
      break;

    case CORE_20403:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          // TODO Auto-generated method stub

          if (ud.getId() == "length")
          {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1))
            {
              return ud.isVariantOfLength();
            }
            else {
              return ud.isVariantOfLength() || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
            }
          }

          return true;
        }
      };
      break;

    case CORE_20404:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          // TODO Auto-generated method stub

          if (ud.getId() == "area")
          {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1))
            {
              return ud.isVariantOfArea();
            }
            else {
              return ud.isVariantOfArea() || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
            }
          }

          return true;
        }
      };
      break;
    case CORE_20405:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          // TODO Auto-generated method stub

          if (ud.getId() == "time")
          {
            if (ctx.isLevelAndVersionLesserEqualThan(2, 1))
            {
              return ud.isVariantOfTime();
            }
            else {
              return ud.isVariantOfTime() || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
            }
          }

          return true;
        }
      };
      break;
    case CORE_20406:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          // TODO Auto-generated method stub

          if (ud.getId() == "volume")
          {
            if (ctx.isLevelAndVersionLessThan(2, 4))
            {
              if (ud.getUnitCount() == 1)
              {
                Unit u = ud.getUnit(0);
                if (ctx.getLevel() == 1)
                {
                  return u.isLitre();
                }
                else if (ctx.isLevelAndVersionEqualTo(2, 1))
                {
                  return u.isLitre() || u.isMetre();
                }
                else
                {
                  return u.isLitre() || u.isMetre() || u.isDimensionless();
                }
              }
              else{

                return ud.isVariantOfVolume();

              }

            }
            else {
              return ud.isVariantOfVolume() || (ud.getNumUnits() == 1 && ud.getUnit(0).isDimensionless());
            }
          }

          return true;
        }
      };
      break;

    case CORE_20407:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          if (ctx.isLevelAndVersionLessThan(2, 4) && 
              ud.getId() == "volume" && 
              ud.getNumUnits() == 1)
          {
            Unit u = ud.getUnit(0);

            return u.isLitre() && u.getExponent() == 1;
          }
          return true;
        }
      };
      break;

    case CORE_20408:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          if (ctx.isLevelAndVersionLessThan(2, 4) && 
              ud.getId() == "volume" && 
              ud.getNumUnits() == 1)
          {
            Unit u = ud.getUnit(0);

            return u.isMetre() && u.getExponent() == 3;
          }
          return true;
        }
      };
      break;

    case CORE_20409:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          return ud.getListOfUnits().size() > 0;
        }
      };
      break;

    case CORE_20410:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {

          boolean success = true;

          for (Unit u: ud.getListOfUnits())
          {
            if (!u.isCelsius())
            {
              success = success && Unit.isUnitKind(u.getKind(), ctx.getLevel(), ctx.getVersion());
            }

          }

          return success;
        }
      };
      break;

    case CORE_20411:
      func = new ValidationFunction<UnitDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, UnitDefinition ud) {
          boolean success = true;

          for (Unit u: ud.getListOfUnits())
          {
            success = success && u.getOffset() == 0;
          }

          return success;
        }
      };
      break;

    default:
      break;
    }

    return new ValidationConstraint<>(errorCode, func);
  }

  /**
   * Creates constraints 20501 - 20517
   * 
   * @param errorCode
   * @return
   */
  protected static AnyConstraint<?> createCompartmentConstraint(int errorCode) {
    ValidationFunction<Compartment> func;

    switch (errorCode) {
    case CORE_20501:

      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          /*
           * Invalid use of the 'size' attribute for a
           * zero-dimensional compartment
           */
          if (c.getSpatialDimensions() == 0)
          {
            return !c.isSetSize();
          }

          return true;
        }
      };
      break;

    case CORE_20502:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          /*
           * Invalid use of the 'units' attribute for a
           * zero-dimensional compartment
           */

          if (c.getSpatialDimensions() == 0)
          {
            return !c.isSetUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20503:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 0)
          {
            return c.isConstant();
          }

          return true;
        }
      };
      break;

    case CORE_20504:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetOutside())
          {
            return c.getOutsideInstance() != null;
          }

          return true;
        }
      };
      break;

    case CORE_20505:
      func = new OutsideCycleValidationFunction();
      break;

    case CORE_20506:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetOutside() && c.getSpatialDimensions() == 0) {

            Compartment outside = c.getOutsideInstance();

            if (outside != null) {
              return outside.getSpatialDimensions() == 0;
            }

          }
          return true;
        }
      };
      break;

    case CORE_20507:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 1 && c.isSetUnits()) {

            String unit = c.getUnits();
            UnitDefinition def = c.getUnitsInstance();

            boolean isLength = ValidationContext.isLength(unit, def);

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1) {
              return isLength;
            }

            if (ctx.getLevel() >= 2) {
              boolean isDimensionless = ValidationContext.isDimensionless(unit);

              return isDimensionless || isLength;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20508:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 2 && c.isSetUnits()) {
            String unit = c.getUnits();
            UnitDefinition def = c.getUnitsInstance();


            boolean isArea = ValidationContext.isArea(unit, def);

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1) {
              return isArea;
            }

            if (ctx.getLevel() >= 2) {
              boolean isDimensionless = ValidationContext.isDimensionless(unit);

              return isDimensionless || isArea;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20509:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.getSpatialDimensions() == 2 && c.isSetUnits()) {
            String unit = c.getUnits();
            UnitDefinition def = c.getUnitsInstance();


            boolean isVolume = ValidationContext.isVolume(unit, def);

            if (ctx.getLevel() == 2 && ctx.getLevel() == 1) {
              return isVolume;
            }

            if (ctx.getLevel() >= 2) {
              boolean isDimensionless = ValidationContext.isDimensionless(unit);

              return isDimensionless || isVolume;
            }
          }

          return true;
        }
      };
      break;

    case CORE_20510:
      func = new ValidationFunction<Compartment>() {
        // @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {

          if (c.isSetCompartmentType()) {
            return c.getCompartmentTypeInstance() != null;
          }

          return true;
        }
      };
      break;

    default:
      return null;
    }

    return new ValidationConstraint<Compartment>(errorCode, func);
  }


  protected static  ValidationConstraint<?> createParameterConstraint(int errorCode)
  {
    ValidationFunction<Parameter> func = null;

    switch (errorCode) {
    case CORE_20705:
      return new ValidationConstraint<Model>(errorCode, new ValidationFunction<Model>() {
        @Override
        public boolean check(ValidationContext ctx, Model m) {
          // TODO Auto-generated method stub
          Parameter fac = m.getConversionFactorInstance();

          if (fac != null)
          {
            return fac.isConstant();
          }

          return true;
        }
      });

    default:
      break;
    }

    return new ValidationConstraint<Parameter>(errorCode, func);
  }


  protected static ValidationConstraint<?> createInitialAssignmentConstraint(int errorCode) {
    ValidationFunction<InitialAssignment> f = null;

    switch (errorCode) {
    case CORE_20801:
      f = new ValidationFunction<InitialAssignment>() {
        @Override
        public boolean check(ValidationContext ctx, InitialAssignment ia) {
          Model m = ia.getModel();

          if (ia.isSetSymbol() && m != null) {

            String symbol = ia.getSymbol();

            boolean checkL2 = (m.getCompartment(symbol) != null) || (m.getSpecies(symbol) != null)
                || (m.getParameter(symbol) != null);

            if (ctx.getLevel() == 2) {
              return checkL2;
            } else {
              return checkL2 || (m.findNamedSBase(symbol) instanceof SpeciesReference);
            }
          }

          return false;
        }
      };
      break;

    case CORE_20804:
      f = new ValidationFunction<InitialAssignment>() {
        @Override
        public boolean check(ValidationContext ctx, InitialAssignment ia) {
          return ia.isSetMath();
        }
      };
      break;

    case CORE_20806:
      f = new ValidationFunction<InitialAssignment>() {
        @Override
        public boolean check(ValidationContext ctx, InitialAssignment ia) {

          Model m = ia.getModel();

          if (ia.isSetSymbol() && m != null) {
            String s = ia.getSymbol();

            Compartment c = m.getCompartment(s);

            if (c != null) {
              return c.getSpatialDimensions() != 0;
            }
          }

          return true;
        }
      };
      break;

    default:
      break;
    }

    return new ValidationConstraint<InitialAssignment>(errorCode, f);
  }


  protected static AnyConstraint<?> createExplictRuleConstraint(int errorCode) {
    ValidationFunction<ExplicitRule> func = null;
    switch (errorCode) {
    case CORE_20901:
      func = new ValidationFunction<ExplicitRule>() {
        @Override
        public boolean check(ValidationContext ctx, ExplicitRule r) {
          // TODO Auto-generated method stub

          //          Variable var = r.getVariableInstance();

          if ((ctx.getLevel() != 1 || r.isScalar()) && r.isSetVariable())
          {
            return r.getVariableInstance() != null;
          }

          return true;
        }
      };

    case CORE_20902:
      func = new ValidationFunction<ExplicitRule>() {

        @Override
        public boolean check(ValidationContext ctx, ExplicitRule r) {
          // TODO Auto-generated method stub

          if ((ctx.getLevel() != 1 || r.isRate()) && r.isSetVariable())
          {
            return r.getVariableInstance() != null;
          }

          return true;
        }
      };
      break;

    case CORE_20903:
      func = new ValidationFunction<ExplicitRule>() {
        @Override
        public boolean check(ValidationContext ctx, ExplicitRule r) {
          // TODO Auto-generated method stub

          Variable var = r.getVariableInstance();

          if (var != null)
          {
            return !var.getConstant();
          }

          return true;
        }
      };

    case CORE_20904:
      func = new ValidationFunction<ExplicitRule>() {

        @Override
        public boolean check(ValidationContext ctx, ExplicitRule r) {
          // TODO Auto-generated method stub

          Variable var = r.getVariableInstance();

          if (var != null)
          {
            return r.getVariableInstance() != null;
          }

          return true;
        }
      };
      break;

    case CORE_20907:
      func = new ValidationFunction<ExplicitRule>() {

        @Override
        public boolean check(ValidationContext ctx, ExplicitRule r) {
          // TODO Auto-generated method stub

          return r.isSetMath();
        }
      };
      break;

    case CORE_20911:
      func = new ValidationFunction<ExplicitRule>() {

        @Override
        public boolean check(ValidationContext ctx, ExplicitRule r) {
          // TODO Auto-generated method stub
          Model m = r.getModel();
          String var = r.getVariable();

          if (r.isSetVariable() && m != null)
          {
            Compartment c = m.getCompartment(var);

            if (c != null)
            {
              return c.getSpatialDimensions() != 0;
            }
          }

          return true;
        }
      };
      break;
    }

    return new ValidationConstraint<ExplicitRule>(errorCode, func);
  }


  protected static  ValidationConstraint<?> createConstraintConstraint(int errorCode)
  {
    ValidationFunction<Constraint> func = null;

    switch (errorCode) {
    case CORE_21001:
      func = new ValidationFunction<Constraint>() {


        @Override
        public boolean check(ValidationContext ctx, Constraint c) {

          if (c.isSetMath())
          {
            return c.getMath().isBoolean();
          }

          return true;
        }
      };

    case CORE_21007:
      func = new ValidationFunction<Constraint>() {

        @Override
        public boolean check(ValidationContext ctx, Constraint c) {

          return c.isSetMath();
        }
      };
    }

    return new ValidationConstraint<Constraint>(errorCode, func);
  }


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
