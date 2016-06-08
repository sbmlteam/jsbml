package org.sbml.jsbml.validator.factory;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Package;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.validator.ValidationContext;
import org.sbml.jsbml.validator.constraint.AnyConstraint;
import org.sbml.jsbml.validator.constraint.ConstraintGroup;
import org.sbml.jsbml.validator.constraint.ValidationConstraint;
import org.sbml.jsbml.validator.constraint.ValidationFunction;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

public class ConstraintFactory {
    public static final int ID_EMPTY_CONSTRAINT = -1;
    public static final int ID_VALIDATE_DOCUMENT_TREE = -2000;
    public static final int ID_VALIDATE_MODEL_TREE = -2010;
    public static final int ID_DO_NOT_CACHE = -99999;
    public static final int ID_GROUP = -99998;

    /**
     * Log4j logger
     */
    protected static final transient Logger logger = Logger.getLogger(ConstraintFactory.class);

    /**
     * Caches the constraints with SoftReferences
     */
    private static HashMap<Integer, SoftReference<AnyConstraint<?>>> cache;

    /**
     * HashMap of created instances. One instance per Level/Version
     */
    private static HashMap<String, SoftReference<ConstraintFactory>> instances;

    private FactoryManager manager;

    /**
     * Returns a instance for the level and version.
     * 
     * @param level
     * @param version
     * @return
     */
    public static ConstraintFactory getInstance(int level, int version) {
	ConstraintFactory factory = null;
	String key = "L" + level + "V" + version;

	if (ConstraintFactory.instances != null) {
	    SoftReference<ConstraintFactory> factoryRef = ConstraintFactory.instances.get(key);
	    factory = factoryRef.get();
	} else {
	    ConstraintFactory.instances = new HashMap<String, SoftReference<ConstraintFactory>>();
	}

	// No factory cached
	if (factory == null) {
	    try {
		String pkg = ConstraintFactory.class.getPackage().getName();
		String className = pkg + "." + key + "FactoryManager";
		Class<?> c = Class.forName(className);
		FactoryManager manager = (FactoryManager) (c.newInstance());

		factory = new ConstraintFactory(manager);
		instances.put(key, new SoftReference<ConstraintFactory>(factory));
	    } catch (Exception e) {
		System.out.println(
			"Couldn't find a ConstraintFactory for level " + level + " and version " + version + ".");
		e.printStackTrace();

		return null;
	    }
	}

	return factory;
    }

    /**
     * Returns a new constraint with the rule of the ID.
     * 
     * @param id
     * @return
     */
    public static AnyConstraint<?> createConstraint(int id) {
	if (id < 0) {
	    return createSpecialConstraint(id);
	}

	Package pkg = Package.getPackageForError(id);

	try {
	    String methodName = "create" + StringTools.firstLetterUpperCase(pkg.toString()) + "Constraint";
	    Method m = ConstraintFactory.class.getMethod(methodName, int.class);
	    Object o = m.invoke(null, id);

	    return (o != null) ? (AnyConstraint<?>) o : null;

	} catch (Exception e) {
	    logger.error(e.getMessage());
	}

	return null;
    }

    protected static AnyConstraint<?> createCompConstraint(int id) {
	return null;
    }

    public static AnyConstraint<?> createCoreConstraint(int id) {
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

    protected static AnyConstraint<?> createSpecialConstraint(int id) {
	switch (id) {
	case ID_EMPTY_CONSTRAINT:
	    ValidationFunction<Object> f = new ValidationFunction<Object>() {
		@Override
		public boolean check(ValidationContext ctx, Object t) {
		    // TODO Auto-generated method stub
		    return true;
		}
	    };

	    return new ValidationConstraint<>(id, f);
	case ID_VALIDATE_DOCUMENT_TREE:
	    ValidationFunction<SBMLDocument> f2 = new ValidationFunction<SBMLDocument>() {
		@Override
		public boolean check(ValidationContext ctx, SBMLDocument t) {
		    /*
		     * Special constraint to validate the hole document tree
		     */
		    System.out.println("validate doc tree");
		    ConstraintFactory factory = ConstraintFactory.getInstance(ctx.getLevel(), ctx.getVersion());

		    AnyConstraint<Model> mc = factory.getConstraintsForClass(Model.class, ctx.getCheckCategories());
		    mc.check(ctx, t.getModel());

		    return true;
		}
	    };

	    return new ValidationConstraint<SBMLDocument>(id, f2);

	case ID_VALIDATE_MODEL_TREE:
	    ValidationFunction<Model> f3 = new ValidationFunction<Model>() {
		@Override
		public boolean check(ValidationContext ctx, Model t) {
		    /*
		     * Special constraint to validate the hole model tree
		     */
		    ConstraintFactory factory = ConstraintFactory.getInstance(ctx.getLevel(), ctx.getVersion());

		    {
			AnyConstraint<Species> sc = factory.getConstraintsForClass(Species.class,
				ctx.getCheckCategories());

			for (Species s : t.getListOfSpecies()) {
			    sc.check(ctx, s);
			}
		    }

		    {
			AnyConstraint<Compartment> cc = factory.getConstraintsForClass(Compartment.class,
				ctx.getCheckCategories());

			for (Compartment c : t.getListOfCompartments()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			@SuppressWarnings("deprecation")
			AnyConstraint<CompartmentType> cc = factory.getConstraintsForClass(CompartmentType.class,
				ctx.getCheckCategories());

			for (@SuppressWarnings("deprecation")
			CompartmentType c : t.getListOfCompartmentTypes()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Constraint> cc = factory.getConstraintsForClass(Constraint.class,
				ctx.getCheckCategories());

			for (Constraint c : t.getListOfConstraints()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Event> cc = factory.getConstraintsForClass(Event.class, ctx.getCheckCategories());

			for (Event c : t.getListOfEvents()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<FunctionDefinition> cc = factory.getConstraintsForClass(FunctionDefinition.class,
				ctx.getCheckCategories());

			for (FunctionDefinition c : t.getListOfFunctionDefinitions()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<InitialAssignment> cc = factory.getConstraintsForClass(InitialAssignment.class,
				ctx.getCheckCategories());

			for (InitialAssignment c : t.getListOfInitialAssignments()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Parameter> cc = factory.getConstraintsForClass(Parameter.class,
				ctx.getCheckCategories());

			for (Parameter c : t.getListOfParameters()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<UnitDefinition> cc = factory.getConstraintsForClass(UnitDefinition.class,
				ctx.getCheckCategories());

			for (UnitDefinition c : t.getListOfPredefinedUnitDefinitions()) {
			    cc.check(ctx, c);
			}

			for (UnitDefinition ud : t.getListOfUnitDefinitions()) {
			    cc.check(ctx, ud);
			}
		    }

		    {
			AnyConstraint<Reaction> cc = factory.getConstraintsForClass(Reaction.class,
				ctx.getCheckCategories());

			for (Reaction c : t.getListOfReactions()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<Rule> cc = factory.getConstraintsForClass(Rule.class, ctx.getCheckCategories());

			for (Rule c : t.getListOfRules()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			@SuppressWarnings("deprecation")
			AnyConstraint<SpeciesType> cc = factory.getConstraintsForClass(SpeciesType.class,
				ctx.getCheckCategories());

			for (@SuppressWarnings("deprecation")
			SpeciesType c : t.getListOfSpeciesTypes()) {
			    cc.check(ctx, c);
			}
		    }

		    {
			AnyConstraint<TreeNodeChangeListener> cc = factory
				.getConstraintsForClass(TreeNodeChangeListener.class, ctx.getCheckCategories());

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

    /**
     * @param o
     * @param category
     * @return
     */
    public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz, CheckCategory category) {
	ConstraintGroup<T> group = new ConstraintGroup<T>();

	// Add constraints for interfaces
	if (!clazz.isInterface()) {
	    for (Class<?> ifc : clazz.getInterfaces()) {
		AnyConstraint<T> con = this.getConstraintsForClass(ifc, category);

		if (con != null) {
		    group.add(con);
		}
	    }
	}

	// constraints for the next superclass (if not java.lang.Object)
	Class<?> superclass = clazz.getSuperclass();

	if (superclass != null && !superclass.equals(Object.class)) {
	    AnyConstraint<T> con = this.getConstraintsForClass(superclass, category);

	    if (con != null) {
		group.add(con);
	    }
	}

	List<Integer> list = manager.getIdsForClass(clazz, category);

	int[] array = new int[list.size()];
	for (int i = 0; i < array.length; i++) {
	    Integer integer = list.get(i);
	    array[i] = integer.intValue();
	}

	return getConstraints(array);
    }

    /**
     * @param o
     * @param category
     * @return
     */
    public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz, CheckCategory[] categories) {

	ConstraintGroup<T> group = new ConstraintGroup<T>();

	for (CheckCategory check : categories) {
	    AnyConstraint<T> c = this.getConstraintsForClass(clazz, check);
	    group.add(c);
	}

	return group;
    }

    /**
     * Returns the constraint with the ID. The IDs of a constraint is
     * identically to the error code it will log on failure.
     * 
     * @param id
     * @return
     */
    public AnyConstraint<?> getConstraint(int id) {
	AnyConstraint<?> c = getConstraintFromCache(id);
	if (c == null) {
	    c = createConstraint(id);
	    this.addToCache(id, c);
	}
	return c;
    }

    /**
     * Returns one constraint which covers all the rules for the given IDs.
     * 
     * @param ids
     * @return A ConstraintGroup
     */
    public <T> ConstraintGroup<T> getConstraints(int[] ids) {
	ConstraintGroup<T> group = null;
	for (int id : ids) {

	    @SuppressWarnings("unchecked")
	    AnyConstraint<T> c = (AnyConstraint<T>) this.getConstraint(id);
	    if (c != null) {
		// Init group if necassary
		if (group == null) {
		    group = new ConstraintGroup<T>();
		}

		group.add(c);
	    }
	}
	// Returns a group with at least 1 member or null
	return group;
    }

    protected ConstraintFactory(FactoryManager manager) {
	if (ConstraintFactory.cache == null) {
	    ConstraintFactory.cache = new HashMap<Integer, SoftReference<AnyConstraint<?>>>(24);
	}

	this.manager = manager;

    }

    private AnyConstraint<?> getConstraintFromCache(int id) {
	Integer key = new Integer(id);
	SoftReference<AnyConstraint<?>> ref = ConstraintFactory.cache.get(key);
	if (ref != null) {
	    AnyConstraint<?> c = (ref.get());

	    // If the constraint was cleared, the reference in the
	    // HashMap can be removed.
	    if (c == null) {
		ConstraintFactory.cache.remove(key);
	    }

	    return c;
	}
	return null;
    }

    private void addToCache(Integer id, AnyConstraint<?> constraint) {
	if (constraint == null || id == ID_DO_NOT_CACHE || id == ID_GROUP) {
	    return;
	}

	SoftReference<AnyConstraint<?>> ref = new SoftReference<AnyConstraint<?>>(constraint);
	ConstraintFactory.cache.put(new Integer(id), ref);
    }
}
