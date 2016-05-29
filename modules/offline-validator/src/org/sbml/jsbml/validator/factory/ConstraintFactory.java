package org.sbml.jsbml.validator.factory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
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
	if(id < 0)
	{
	    return createSpecialConstraint(id);
	}
	
	if (id > 50_000) {
	    if (id > 75_000) {

	    } else {

	    }
	} else {
	    if (id > 25_000) {

	    } else {
		if (id > 20_600)
		{
		    
		    return createSpeciesConstraint(id);
		}
		else if (id > 20_500) {
		    return createCompartmentConstraint(id);
		}
	    }
	}
	return null;
    }
    
    protected static AnyConstraint<?> createSpecialConstraint(int id)
    {
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
		    ConstraintFactory factory = 
			    ConstraintFactory.getInstance(ctx.getLevel(), ctx.getVersion());
		    
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
		    
		    System.out.println("model doc tree");
		    ConstraintFactory factory = 
			    ConstraintFactory.getInstance(ctx.getLevel(), ctx.getVersion());
		    
		    AnyConstraint<Species> sc = factory.getConstraintsForClass(Species.class, ctx.getCheckCategories());
		    
		    for (Species s:t.getListOfSpecies())
		    {
			sc.check(ctx, s);
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
     * @param id
     * @return 
     */
    protected static AnyConstraint<Compartment> createCompartmentConstraint(int id) {
	ValidationFunction<Compartment> func;

	switch (id) {
	case 20_501:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment t) {
		    /*
		     * Invalid use of the 'size' attribute for a zero-dimensional compartment
		     */
		    
		    return t.getSpatialDimensions() == 0 && t.isSetSize();
		}
	    };
	    break;

	case 20_502:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment t) {
		    /*
		     * Invalid use of the 'units' attribute for a zero-dimensional compartment
		     */
		    return (t.getSpatialDimensions() == 0) && (!t.isSetUnits());
		}
	    };
	    break;
	    
	case 20_503:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment t) {
		    return  t.getSpatialDimensions() == 0 && t.isConstant();
		}
	    };
	    break;
	    
	case 20_504:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment t) {
		    return  t.isSetOutside() && t.getOutside() != null;
		}
	    };
	    break;
	    
	case 20_505:
	    func = new ValidationFunction<Compartment>() {
		@Override
		public boolean check(ValidationContext ctx, Compartment t) {
		    // TODO
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
     * Creates constraints 20501 - 20517
     * @param id
     * @return 
     */
    protected static ValidationConstraint<Species> createSpeciesConstraint(int id) {
	ValidationFunction<Species> func;

	switch (id) {
	case 20_601:
	    func = new ValidationFunction<Species>() {
		@Override
		public boolean check(ValidationContext ctx, Species t) {
		    /*
		     * Invalid value found for Species 'compartment' attribute
		     */
		    if (t.isSetCompartment())
		    {
			return t.getModel().getCompartment(t.getCompartment()) != null;
		    }
		    
		    return true;
		}
	    };
	    break;
	    
	case 20_602:
	    func = new ValidationFunction<Species>() {
		@SuppressWarnings("deprecation")
		@Override
		public boolean check(ValidationContext ctx, Species t) {
		    /*
		     * Invalid value found for Species 'compartment' attribute
		     */
		    if (t.hasOnlySubstanceUnits())
		    {
			return t.isSetSpatialSizeUnits();
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

    /**
     * @param o
     * @param category
     * @return
     */
    public <T> AnyConstraint<T> getConstraintsForClass(Class<?> clazz, CheckCategory category) {
	ConstraintGroup<T> group = new ConstraintGroup<T>();
	
	if (!clazz.isInterface())
	{
	    for (Class<?> ifc:clazz.getInterfaces())
	    {
		AnyConstraint<T> con = this.getConstraintsForClass(ifc, category);
		group.add(con);
	    }
	}
	
	Class<?> superclass = clazz.getSuperclass();
	
	if (superclass != null && !superclass.equals(Object.class))
	{
	    AnyConstraint<T> con = this.getConstraintsForClass(superclass, category);
	    group.add(con);
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
    public <T> AnyConstraint<T> getConstraintsForClass(Class<?> clazz, CheckCategory[] categories) {
	
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
	if(constraint == null || id == ID_DO_NOT_CACHE || id == ID_GROUP)
	{
	    return;
	}
	
	SoftReference<AnyConstraint<?>> ref = new SoftReference<AnyConstraint<?>>(constraint);
	ConstraintFactory.cache.put(new Integer(id), ref);
    }
}
