package org.sbml.jsbml.validator.factory;

import java.lang.ref.SoftReference;
import java.util.HashMap;


public abstract class AbstractConstraintBuilder implements ConstraintBuilder {
    private static HashMap<String, SoftReference<ConstraintBuilder>> instances_ = new HashMap<>();

    public static ConstraintBuilder getInstance(String pkgName) {
	SoftReference<ConstraintBuilder> ref = instances_.get(pkgName);
	ConstraintBuilder builder = null;

	if (ref != null) {
	    builder = ref.get();
	}

	if (builder == null) {
	    try {
		String className = pkgName + "ConstraintBuilder";
		@SuppressWarnings("unchecked")
		Class<ConstraintBuilder> c = (Class<ConstraintBuilder>) Class.forName(className);
		
		builder = c.newInstance();
		
		instances_.put(pkgName, new SoftReference<ConstraintBuilder>(builder));
	    } catch (Exception e) {
		ConstraintFactory.logger.error(e.getMessage());
	    }
	}

	return builder;
    }
}
