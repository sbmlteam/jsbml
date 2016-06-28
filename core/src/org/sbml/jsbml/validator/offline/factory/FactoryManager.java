package org.sbml.jsbml.validator.offline.factory;

import java.util.List;

import org.sbml.jsbml.validator.offline.SBMLPackage;


public interface FactoryManager {
    public static final int ID_EMPTY_CONSTRAINT = -1;
    public static final int ID_VALIDATE_DOCUMENT_TREE = -2000;
    public static final int ID_VALIDATE_CORE_MODEL_TREE = -3000;
    public static final int ID_VALIDATE_LAYOUT_MODEL_TREE = -203000;
    public static final int ID_VALIDATE_LAYOUT_TREE = -203001;
    public static final int ID_DO_NOT_CACHE = -99999;
    public static final int ID_GROUP = -99998;

    /**
     * Returns a list with all the IDs which are needed to validate a object of
     * given class.
     * 
     * @param c
     * @param category
     * @return
     */
    abstract public List<Integer> getIdsForClass(Class<?> clazz, CheckCategory category, SBMLPackage[] packages);

}
