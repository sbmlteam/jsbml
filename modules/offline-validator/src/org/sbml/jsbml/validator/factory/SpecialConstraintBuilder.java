package org.sbml.jsbml.validator.factory;

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
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.validator.ValidationContext;
import org.sbml.jsbml.validator.constraints.AbstractConstraintBuilder;
import org.sbml.jsbml.validator.constraints.AnyConstraint;
import org.sbml.jsbml.validator.constraints.ValidationConstraint;
import org.sbml.jsbml.validator.constraints.ValidationFunction;

@SuppressWarnings("deprecation")
public class SpecialConstraintBuilder extends AbstractConstraintBuilder {

    @Override
    public AnyConstraint<?> createConstraint(int id) {
	switch (id) {
	case ConstraintFactory.ID_EMPTY_CONSTRAINT:
	    return new ValidationConstraint<>(id, null);

	case ConstraintFactory.ID_VALIDATE_DOCUMENT_TREE:
	    ValidationFunction<SBMLDocument> f2 = new ValidationFunction<SBMLDocument>() {
		@Override
		public boolean check(ValidationContext ctx, SBMLDocument t) {
		    /*
		     * Special constraint to validate the hole document tree
		     */
		    System.out.println("validate doc tree");
		    ConstraintFactory factory = ConstraintFactory.getInstance(ctx.getLevel(), ctx.getVersion());

		    AnyConstraint<Model> mc = factory.getConstraintsForClass(Model.class, ctx.getCheckCategories(), ctx.getPackages());
		    mc.check(ctx, t.getModel());

		    return true;
		}
	    };

	    return new ValidationConstraint<SBMLDocument>(id, f2);

	case ConstraintFactory.ID_VALIDATE_MODEL_TREE:
	    ValidationFunction<Model> f3 = new ValidationFunction<Model>() {
		@Override
		public boolean check(ValidationContext ctx, Model t) {
		    /*
		     * Special constraint to validate the hole model tree
		     */
		    ConstraintFactory factory = ConstraintFactory.getInstance(ctx.getLevel(), ctx.getVersion());

		    {
			AnyConstraint<Species> sc = factory.getConstraintsForClass(Species.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (Species s : t.getListOfSpecies()) {
			    sc.check(ctx, s);
			}
		    }

		    {
			AnyConstraint<Compartment> cc = factory.getConstraintsForClass(Compartment.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (Compartment c : t.getListOfCompartments()) {
			    cc.check(ctx, c);
			}
		    }

		    {

			AnyConstraint<CompartmentType> cc = factory.getConstraintsForClass(CompartmentType.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (CompartmentType c : t.getListOfCompartmentTypes()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Constraint> cc = factory.getConstraintsForClass(Constraint.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (Constraint c : t.getListOfConstraints()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Event> cc = factory.getConstraintsForClass(Event.class, ctx.getCheckCategories(), ctx.getPackages());

			for (Event c : t.getListOfEvents()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<FunctionDefinition> cc = factory.getConstraintsForClass(FunctionDefinition.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (FunctionDefinition c : t.getListOfFunctionDefinitions()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<InitialAssignment> cc = factory.getConstraintsForClass(InitialAssignment.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (InitialAssignment c : t.getListOfInitialAssignments()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Parameter> cc = factory.getConstraintsForClass(Parameter.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (Parameter c : t.getListOfParameters()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<UnitDefinition> cc = factory.getConstraintsForClass(UnitDefinition.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (UnitDefinition c : t.getListOfPredefinedUnitDefinitions()) {
			    cc.check(ctx, c);
			}

			for (UnitDefinition ud : t.getListOfUnitDefinitions()) {
			    cc.check(ctx, ud);
			}
		    }

		    {
			AnyConstraint<Reaction> cc = factory.getConstraintsForClass(Reaction.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (Reaction c : t.getListOfReactions()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Rule> cc = factory.getConstraintsForClass(Rule.class, ctx.getCheckCategories(), ctx.getPackages());

			for (Rule c : t.getListOfRules()) {
			    cc.check(ctx, c);
			}
		    }

		    {

			AnyConstraint<SpeciesType> cc = factory.getConstraintsForClass(SpeciesType.class,
				ctx.getCheckCategories(), ctx.getPackages());

			for (SpeciesType c : t.getListOfSpeciesTypes()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<TreeNodeChangeListener> cc = factory
				.getConstraintsForClass(TreeNodeChangeListener.class, ctx.getCheckCategories(), ctx.getPackages());

			for (TreeNodeChangeListener c : t.getListOfTreeNodeChangeListeners()) {
			    cc.check(ctx, c);
			}
		    }

		    return true;
		}
	    };

	    return new ValidationConstraint<Model>(id, f3);

	default:
	    break;
	}

	return null;
    }

}
