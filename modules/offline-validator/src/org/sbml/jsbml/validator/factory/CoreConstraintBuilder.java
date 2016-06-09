package org.sbml.jsbml.validator.factory;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.ValidationContext;
import org.sbml.jsbml.validator.constraint.AnyConstraint;
import org.sbml.jsbml.validator.constraint.ValidationConstraint;
import org.sbml.jsbml.validator.constraint.ValidationFunction;

public class CoreConstraintBuilder extends AbstractConstraintBuilder {

    @Override
    public AnyConstraint<?> createConstraint(int id) {
	// Using bisection to find constraints faster.
	// using comments to mark the intervalls

	// id -> (50_000, 99_999]
	if (id > 50_000) {
	    // id -> (75_000, 99_999]
	    if (id > 75_000) {

	    }
	    // id -> (50_000, 75_000]
	    else {

	    }
	}
	// id -> (0, 50_000]
	else {
	    // id -> (25_000, 50_000]
	    if (id > 25_000) {

	    }
	    // id -> (0, 25_000]
	    else {

		// id -> (12_500, 25_000]
		if (id > 12_500) {
		    // id -> (18_000, 25_000]
		    if (id > 18_000) {
			// id -> (20_000, 25_000]
			if (id > 20_000) {
			    // id -> (22_000, 25_000]
			    if (id > 22_000) {

			    }
			    // id -> (20_000, 22_000]
			    else {
				// id -> (21_000, 22_000]
				if (id > 21_000) {

				}
				// id -> (20_000, 21_000]
				else {
				    if (id > 20_700) {
					return createInitialAssignment(id);
				    } else if (id > 20_600) {
					return createSpeciesConstraint(id);
				    } else if (id > 20_500) {
					return createCompartmentConstraint(id);
				    }
				}
			    }
			}
			// id -> (18_000, 20_000]
			else {
			    // id -> (19_000, 20_000]
			    if (id > 19_000) {

			    }
			    // id -> (18_000, 19_000]
			    else {

			    }
			}
		    } else {

		    }
		    if (id > 20_000) {

		    } else {

		    }
		    // id -> (0, 12_500]
		} else {

		}
	    }
	}

	return null;
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
	case 20_501:
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

	case 20_502:
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

	case 20_503:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment c) {
		    return c.getSpatialDimensions() == 0 && c.isConstant();
		}
	    };
	    break;

	case 20_504:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment c) {
		    return c.isSetOutside() && c.getOutside() != null;
		}
	    };
	    break;

	case 20_505:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment c) {
		    // TODO CompartmentOutsieCycle not done yet
		    return true;
		}
	    };
	    break;

	case 20_506:
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

	case 20_507:
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

	case 20_508:
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

	case 20_510:
	    func = new ValidationFunction<Compartment>() {
		@SuppressWarnings("deprecation")
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
	case 20_601:
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

	case 20_602:
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

	case 20_603:
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

	case 20_604:
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

	case 20_605:
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

	case 20_606:
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

	case 20_607:
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

	case 20_609:
	    func = new ValidationFunction<Species>() {
		@Override
		public boolean check(ValidationContext ctx, Species s) {
		    if (s.isSetInitialAmount()) {

			return !s.isSetInitialConcentration();
		    }
		    return true;
		}
	    };
	    break;

	case 20611:
	    // SPECIAL CASE!!!
	    return new ValidationConstraint<SpeciesReference>(id, new ValidationFunction<SpeciesReference>() {
		@Override
		public boolean check(ValidationContext ctx, SpeciesReference sr) {
		    Species s = sr.getSpeciesInstance();

		    if (s != null) {
			return !s.getConstant() || s.getBoundaryCondition();
		    }

		    return true;
		}
	    });

	case 20_612:
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

	case 20_614:
	    func = new ValidationFunction<Species>() {
		@Override
		public boolean check(ValidationContext ctx, Species s) {
		    return s.isSetCompartment();
		}
	    };
	    break;

	case 20_615:
	    func = new ValidationFunction<Species>() {
		@SuppressWarnings("deprecation")
		@Override
		public boolean check(ValidationContext ctx, Species s) {
		    return !s.isSetSpatialSizeUnits();
		}
	    };
	    break;

	case 20_617:
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

    protected static ValidationConstraint<?> createInitialAssignment(int id) {
	ValidationFunction<InitialAssignment> f = null;

	switch (id) {
	case 20801:
	    f = new ValidationFunction<InitialAssignment>() {
		@Override
		public boolean check(ValidationContext ctx, InitialAssignment ia) {
		    if (ia.isSetSymbol()) {
			@SuppressWarnings("deprecation")
			String symbol = ia.getSymbol();
			Model m = ia.getModel();

			boolean checkL2 = m.getCompartment(symbol) != null || m.getSpecies(symbol) != null
				|| m.getParameter(symbol) != null;

			if (ctx.getLevel() == 2) {
			    return checkL2;
			} else {
			    // TODO: m.getSpeciesReference doesn't exists?
			    return checkL2;
			}
		    }
		    return false;
		}
	    };
	    break;
	case 20804:
	    f = new ValidationFunction<InitialAssignment>() {
		@Override
		public boolean check(ValidationContext ctx, InitialAssignment ia) {
		    return ia.isSetMath();
		}
	    };
	    break;
	case 20806:
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

}
