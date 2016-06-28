package org.sbml.jsbml.validator.offline.factory;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import org.apache.log4j.Logger;



public abstract class AbstractConstraintBuilder implements ConstraintBuilder, SBMLErrorCodes {
    private static HashMap<String, SoftReference<ConstraintBuilder>> instances_ = new HashMap<String, SoftReference<ConstraintBuilder>>();
    /**
     * Log4j logger
     */
    protected static final transient Logger logger = Logger.getLogger(AbstractConstraintBuilder.class);

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
		Class<ConstraintBuilder> c = (Class<ConstraintBuilder>) Class.forName("org.sbml.jsbml.validator.offline.constraints." + className);
		
		builder = c.newInstance();
		
		instances_.put(pkgName, new SoftReference<ConstraintBuilder>(builder));
	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }
	}

	return builder;
    }
}
