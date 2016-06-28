package org.sbml.jsbml.validator.offline.factory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.validator.offline.constraints.AbstractConstraintList;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;
import org.sbml.jsbml.validator.offline.constraints.CoreSpecialErrorCodes;

public class ConstraintFactory {

    /**
     * Log4j logger
     */
    protected static final transient Logger logger = Logger.getLogger(ConstraintFactory.class);

    /**
     * Caches the constraints with SoftReferences
     */
    private static HashMap<Integer, SoftReference<AnyConstraint<?>>> cache;
    
    private static ConstraintFactory instance;
   

    /**
     * Returns a instance.
     * 
     * @return
     */
    public static ConstraintFactory getInstance() {
	if (ConstraintFactory.instance == null)
	{
	    ConstraintFactory.instance = new ConstraintFactory();
	}
	
	return ConstraintFactory.instance;
    }
    
    protected ConstraintFactory() {
	if (ConstraintFactory.cache == null) {
	    ConstraintFactory.cache = new HashMap<Integer, SoftReference<AnyConstraint<?>>>(24);
	}
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
    public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz, CheckCategory category, SBMLPackage[] packages, int level, int version) {
	ConstraintGroup<T> group = new ConstraintGroup<T>();

	// Add constraints for interfaces
	if (!clazz.isInterface()) {
	    for (Class<?> ifc : clazz.getInterfaces()) {
		AnyConstraint<T> con = this.getConstraintsForClass(ifc, category, packages, level, version);

		if (con != null) {
		    group.add(con);
		}
	    }
	}

	// constraints for the next superclass (if not java.lang.Object)
	Class<?> superclass = clazz.getSuperclass();

	if (superclass != null && !superclass.equals(Object.class)) {
	    AnyConstraint<T> con = this.getConstraintsForClass(superclass, category, packages, level, version);

	    if (con != null) {
		group.add(con);
	    }
	}

	List<Integer> list = AbstractConstraintList.getIdsForClass(clazz, category, packages, level, version);
//		manager.getIdsForClass(clazz, category, packages);

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
    public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz, CheckCategory[] categories, SBMLPackage[] packages, int level, int version) {

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
	    AnyConstraint<T> c = this.getConstraintsForClass(clazz, check, pkgs, level, version);
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
	if (constraint == null || id == CoreSpecialErrorCodes.ID_DO_NOT_CACHE || id == CoreSpecialErrorCodes.ID_GROUP) {
	    return;
	}

	SoftReference<AnyConstraint<?>> ref = new SoftReference<AnyConstraint<?>>(constraint);
	ConstraintFactory.cache.put(new Integer(id), ref);
    }
}
