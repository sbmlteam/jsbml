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


import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.factory.AbstractConstraintBuilder;
import org.sbml.jsbml.validator.offline.factory.ConstraintFactory;

@SuppressWarnings("deprecation")
public class CoreConstraintBuilder extends AbstractConstraintBuilder {

  @Override
  public AnyConstraint<?> createConstraint(int id) {

    // int uid = Math.abs(id);

    if (id < 0) {
      return this.createSpecialConstraint(id);
    }

    int firstThree = id / 100;

    switch (firstThree) 
    {
    case 207:
      return CoreConstraintBuilder.createInitialAssignmentConstraint(id);
    case 206:
      return CoreConstraintBuilder.createSpeciesConstraint(id);
    case 205:
      return CoreConstraintBuilder.createCompartmentConstraint(id);

    default:
      return null;
    }

    // // Using bisection to find constraints faster.
    // // using comments to mark the intervalls
    //
    // // id -> (50_000, 99_999]
    // if (id > 50_000) {
    // // id -> (75_000, 99_999]
    // if (id > 75_000) {
    //
    // }
    // // id -> (50_000, 75_000]
    // else {
    //
    // }
    // }
    // // id -> (0, 50_000]
    // else {
    // // id -> (25_000, 50_000]
    // if (id > 25_000) {
    //
    // }
    // // id -> (0, 25_000]
    // else {
    //
    // // id -> (12_500, 25_000]
    // if (id > 12_500) {
    // // id -> (18_000, 25_000]
    // if (id > 18_000) {
    // // id -> (20_000, 25_000]
    // if (id > 20_000) {
    // // id -> (22_000, 25_000]
    // if (id > 22_000) {
    //
    // }
    // // id -> (20_000, 22_000]
    // else {
    // // id -> (21_000, 22_000]
    // if (id > 21_000) {
    //
    // }
    // // id -> (20_000, 21_000]
    // else {
    // if (id > 20_700) {
    // return createInitialAssignment(id);
    // } else if (id > 20_600) {
    // return createSpeciesConstraint(id);
    // } else if (id > 20_500) {
    // return createCompartmentConstraint(id);
    // }
    // }
    // }
    // }
    // // id -> (18_000, 20_000]
    // else {
    // // id -> (19_000, 20_000]
    // if (id > 19_000) {
    //
    // }
    // // id -> (18_000, 19_000]
    // else {
    //
    // }
    // }
    // } else {
    //
    // }
    // if (id > 20_000) {
    //
    // } else {
    //
    // }
    // // id -> (0, 12_500]
    // } else {
    //
    // }
    // }
    // }

    // return null;
  }

  /**
   * Creates constraints 20501 - 20517
   * 
   * @param id
   * @return
   */
  protected static AnyConstraint<?> createCompartmentConstraint(int id) {
    ValidationFunction<Compartment> func;

    switch (id) {
    case CORE_20501:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          /*
           * Invalid use of the 'size' attribute for a
           * zero-dimensional compartment
           */

          return c.getSpatialDimensions() == 0 && c.isSetSize();
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
          return (c.getSpatialDimensions() == 0) && (!c.isSetUnits());
        }
      };
      break;

    case CORE_20503:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          return c.getSpatialDimensions() == 0 && c.isConstant();
        }
      };
      break;

    case CORE_20504:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          return c.isSetOutside() && c.getOutside() != null;
        }
      };
      break;

    case CORE_20505:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          // TODO CompartmentOutsieCycle not done yet
          return true;
        }
      };
      break;

    case CORE_20506:
      func = new ValidationFunction<Compartment>() {
        @Override
        public boolean check(ValidationContext ctx, Compartment c) {
          if (c.isSetOutside() && c.getSpatialDimensions() == 0) {
            Model m = c.getModel();

            if (m != null) {
              Compartment outside = m.getCompartment(c.getOutside());

              if (outside != null) {
                return outside.getSpatialDimensions() == 0;
              }

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

    return new ValidationConstraint<Compartment>(id, func);
  }

  /**
   * Creates constraints 20601 - 20617 for Species instances
   * 
   * @param id
   * @return
   */
  protected static ValidationConstraint<?> createSpeciesConstraint(int id) {
    ValidationFunction<Species> func;

    switch (id) {
    case CORE_20601:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          /*
           * Invalid value found for Species 'compartment' attribute
           */
          if (s.isSetCompartment()) {
            return s.getCompartmentInstance() != null;
          }

          return true;
        }
      };
      break;

    case CORE_20602:
      func = new ValidationFunction<Species>() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          /*
           * Invalid value found for Species 'compartment' attribute
           */
          if (s.hasOnlySubstanceUnits()) {
            return s.isSetSpatialSizeUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20603:
      func = new ValidationFunction<Species>() {

        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getModel().getCompartment(s.getCompartment());

          if (c != null && c.getSpatialDimensions() == 0) {
            return !s.isSetSpatialSizeUnits();
          }

          return true;
        }
      };
      break;

    case CORE_20604:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 0) {
            return !s.isSetInitialConcentration();
          }

          return true;
        }
      };
      break;

    case CORE_20605:
      func = new ValidationFunction<Species>() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 0 && s.isSetSpatialSizeUnits()) {
            String unit = s.getUnits();
            UnitDefinition def = s.getUnitsInstance();

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

    case CORE_20606:
      func = new ValidationFunction<Species>() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 0 && s.isSetSpatialSizeUnits()) {
            String unit = s.getSpatialSizeUnits();
            UnitDefinition def = s.getUnitsInstance();

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

    case CORE_20607:
      func = new ValidationFunction<Species>() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Species s) {

          Compartment c = s.getCompartmentInstance();

          if (c != null && c.getSpatialDimensions() == 3 && s.isSetSpatialSizeUnits()) {
            String unit = s.getSpatialSizeUnits();
            UnitDefinition def = s.getUnitsInstance();

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

      // Conststraint 20609 can never be broken in JSBML!
      //	case CORE_20609:
      //	    func = new ValidationFunction<Species>() {
      //		@Override
      //		public boolean check(ValidationContext ctx, Species s) 
      //		{
      //		    if (s.isSetInitialAmount()) {
      //
      //		      return !s.isSetInitialConcentration();
      //		    }
      //		    
      //		    return true;
      //		}
      //	    };
      //	    break;

    case CORE_20611:
      // SPECIAL CASE!!!
      return new ValidationConstraint<SpeciesReference>(id, new ValidationFunction<SpeciesReference>() {
        @Override
        public boolean check(ValidationContext ctx, SpeciesReference sr) {
          Species s = sr.getSpeciesInstance();

          System.out.println("Species " + s);
          if (s != null) {
            System.out.println("Test " + s.isConstant() + s.isBoundaryCondition());
            return !(s.isConstant() && s.isBoundaryCondition());
          }

          return true;
        }
      });

    case CORE_20612:
      func = new ValidationFunction<Species>() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          if (s.isSetSpeciesType()) {

            return s.getSpeciesTypeInstance() != null;
          }
          return true;
        }
      };
      break;

    case CORE_20614:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          return s.isSetCompartment();
        }
      };
      break;

    case CORE_20615:
      func = new ValidationFunction<Species>() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          return !s.isSetSpatialSizeUnits();
        }
      };
      break;

    case CORE_20617:
      func = new ValidationFunction<Species>() {
        @Override
        public boolean check(ValidationContext ctx, Species s) {
          if (s.isSetConversionFactor()) {
            return s.getConversionFactorInstance() != null;
          }

          return true;
        }
      };
      break;

    default:
      return null;
    }

    return new ValidationConstraint<Species>(id, func);
  }

  protected static ValidationConstraint<?> createInitialAssignmentConstraint(int id) {
    ValidationFunction<InitialAssignment> f = null;

    switch (id) {
    case CORE_20801:
      f = new ValidationFunction<InitialAssignment>() {
        @Override
        public boolean check(ValidationContext ctx, InitialAssignment ia) {
          if (ia.isSetSymbol()) {
            @SuppressWarnings("deprecation")
            String symbol = ia.getSymbol();
            Model m = ia.getModel();

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

          if (ia.isSetSymbol()) {
            @SuppressWarnings("deprecation")
            String s = ia.getSymbol();
            Model m = ia.getModel();
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

    return new ValidationConstraint<InitialAssignment>(id, f);
  }

  protected static AnyConstraint<?> createSpecialConstraint(int id) {
    switch (id) {
    case CoreSpecialErrorCodes.ID_TRUE_CONSTRAINT:
      return new ValidationConstraint<Object>(id, null);
    case CoreSpecialErrorCodes.ID_FALSE_CONSTRAINT:
      return new ValidationConstraint<Object>(id, new ValidationFunction<Object>() {
        @Override
        public boolean check(ValidationContext ctx, Object t) {
          return false;
        }
      });

    case CoreSpecialErrorCodes.ID_VALIDATE_TREE_NODE:
      return new ValidationConstraint<TreeNode>(id, new ValidationFunction<TreeNode>() {

        @Override
        public boolean check(ValidationContext ctx, TreeNode t) {

          boolean success = true;
          Enumeration<?> children = t.children();
          //          ConstraintFactory factory = ConstraintFactory.getInstance();

          //          System.out.println("Found Tree " + t.getChildCount() + " " + children.hasMoreElements());
          AnyConstraint<Object> root = ctx.getRootConstraint();

          while (children.hasMoreElements())
          {
            Object child = children.nextElement();

            if (child != null)
            {
              ctx.loadConstraints(child.getClass());
              success = ctx.validate(child) && success;
            }

          }

          ctx.setRootConstraint(root);

          return success;
        }
      });


      //    case CoreSpecialErrorCodes.ID_VALIDATE_DOCUMENT_TREE:
      //      ValidationFunction<SBMLDocument> f2 = new ValidationFunction<SBMLDocument>() {
      //        @Override
      //        public boolean check(ValidationContext ctx, SBMLDocument t) {
      //          /*
      //           * Special constraint to validate the hole document tree
      //           */
      //          //		    System.out.println("validate doc tree");
      //          ConstraintFactory factory = ConstraintFactory.getInstance();
      //
      //          AnyConstraint<Model> mc = factory.getConstraintsForClass(Model.class, ctx.getCheckCategories(),
      //            ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //          mc.check(ctx, t.getModel());
      //
      //          return true;
      //        }
      //      };
      //
      //      return new ValidationConstraint<SBMLDocument>(id, f2);
      //
      //    case CoreSpecialErrorCodes.ID_VALIDATE_MODEL_TREE:
      //      ValidationFunction<Model> f3 = new ValidationFunction<Model>() {
      //        @SuppressWarnings("deprecation")
      //        @Override
      //        public boolean check(ValidationContext ctx, Model t) {
      //          /*
      //           * Special constraint to validate the hole model tree
      //           */
      //          ConstraintFactory factory = ConstraintFactory.getInstance();
      //          
      //          
      //         
      //          
      //          {
      //            AnyConstraint<Species> sc = factory.getConstraintsForClass(Species.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (Species s : t.getListOfSpecies()) {
      //              sc.check(ctx, s);
      //            }
      //          }
      //
      //          {
      //
      //            AnyConstraint<Compartment> cc = factory.getConstraintsForClass(Compartment.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (Compartment c : t.getListOfCompartments()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //
      //            AnyConstraint<CompartmentType> cc = factory.getConstraintsForClass(CompartmentType.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (CompartmentType c : t.getListOfCompartmentTypes()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<Constraint> cc = factory.getConstraintsForClass(Constraint.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (Constraint c : t.getListOfConstraints()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<Event> cc = factory.getConstraintsForClass(Event.class, ctx.getCheckCategories(),
      //              ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (Event c : t.getListOfEvents()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<FunctionDefinition> cc = factory.getConstraintsForClass(FunctionDefinition.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (FunctionDefinition c : t.getListOfFunctionDefinitions()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<InitialAssignment> cc = factory.getConstraintsForClass(InitialAssignment.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (InitialAssignment c : t.getListOfInitialAssignments()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<Parameter> cc = factory.getConstraintsForClass(Parameter.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (Parameter c : t.getListOfParameters()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<UnitDefinition> cc = factory.getConstraintsForClass(UnitDefinition.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (UnitDefinition c : t.getListOfPredefinedUnitDefinitions()) {
      //              cc.check(ctx, c);
      //            }
      //
      //            for (UnitDefinition ud : t.getListOfUnitDefinitions()) {
      //              cc.check(ctx, ud);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<Reaction> cc = factory.getConstraintsForClass(Reaction.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (Reaction c : t.getListOfReactions()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<Rule> cc = factory.getConstraintsForClass(Rule.class, ctx.getCheckCategories(),
      //              ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (Rule c : t.getListOfRules()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //
      //            AnyConstraint<SpeciesType> cc = factory.getConstraintsForClass(SpeciesType.class,
      //              ctx.getCheckCategories(), ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
      //
      //            for (SpeciesType c : t.getListOfSpeciesTypes()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          {
      //            AnyConstraint<TreeNodeChangeListener> cc = factory.getConstraintsForClass(
      //              TreeNodeChangeListener.class, ctx.getCheckCategories(), ctx.getPackages(),
      //              ctx.getLevel(), ctx.getVersion());
      //
      //            for (TreeNodeChangeListener c : t.getListOfTreeNodeChangeListeners()) {
      //              cc.check(ctx, c);
      //            }
      //          }
      //
      //          return true;
      //        }
      //      };
      //
      //      return new ValidationConstraint<Model>(id, f3);
    default:
      return null;
    }
  }

}
