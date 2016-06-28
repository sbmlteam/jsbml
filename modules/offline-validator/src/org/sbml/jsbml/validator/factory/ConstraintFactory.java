package org.sbml.jsbml.validator.factory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.SBMLPackage;
import org.sbml.jsbml.validator.constraints.AbstractConstraintBuilder;
import org.sbml.jsbml.validator.constraints.AnyConstraint;
import org.sbml.jsbml.validator.constraints.ConstraintBuilder;
import org.sbml.jsbml.validator.constraints.ConstraintGroup;

public class ConstraintFactory {

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

	String pkgName;

	if (id < 0) {
	    pkgName = "Special";
	} else {
	    SBMLPackage pkg = SBMLPackage.getPackageForError(id);
	    pkgName = StringTools.firstLetterUpperCase(pkg.toString());
	}

	ConstraintBuilder b = AbstractConstraintBuilder.getInstance(pkgName);
	
	return (b != null) ? b.createConstraint(id) : null; 
    }

    /**
     * @param o
     * @param category
     * @return
     */
    public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz, CheckCategory category, SBMLPackage[] packages) {
	ConstraintGroup<T> group = new ConstraintGroup<T>();

	// Add constraints for interfaces
	if (!clazz.isInterface()) {
	    for (Class<?> ifc : clazz.getInterfaces()) {
		AnyConstraint<T> con = this.getConstraintsForClass(ifc, category, packages);

		if (con != null) {
		    group.add(con);
		}
	    }
	}

	// constraints for the next superclass (if not java.lang.Object)
	Class<?> superclass = clazz.getSuperclass();

	if (superclass != null && !superclass.equals(Object.class)) {
	    AnyConstraint<T> con = this.getConstraintsForClass(superclass, category, packages);

	    if (con != null) {
		group.add(con);
	    }
	}

	List<Integer> list = manager.getIdsForClass(clazz, category, packages);

	int[] array = new int[list.size()];
	for (int i = 0; i < array.length; i++) {
	    Integer integer = list.get(i);
	    array[i] = integer.intValue();
	}

	return getConstraints(array);
    }

    /**
     * @param clazz
     * @param category
     * @param pkgs
     * @return
     */
    public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz, CheckCategory[] categories, SBMLPackage[] packages) {

	ConstraintGroup<T> group = new ConstraintGroup<T>();
	
	// checks if package checking is enabled
	SBMLPackage[] pkgs = {SBMLPackage.CORE};
	
	for (CheckCategory cat : categories)
	{
	    if (cat == CheckCategory.PACKAGE)
	    {
		pkgs = packages;
		break;
	    }
	}
	
	for (CheckCategory check : categories) {
	    AnyConstraint<T> c = this.getConstraintsForClass(clazz, check, packages);
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
	if (constraint == null || id == FactoryManager.ID_DO_NOT_CACHE || id == FactoryManager.ID_GROUP) {
	    return;
	}

	SoftReference<AnyConstraint<?>> ref = new SoftReference<AnyConstraint<?>>(constraint);
	ConstraintFactory.cache.put(new Integer(id), ref);
    }
}
