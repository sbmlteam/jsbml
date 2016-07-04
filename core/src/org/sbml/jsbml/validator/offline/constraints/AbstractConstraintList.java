package org.sbml.jsbml.validator.offline.constraints;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.validator.offline.factory.AbstractConstraintBuilder;
import org.sbml.jsbml.validator.offline.factory.CheckCategory;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

public abstract class AbstractConstraintList implements SBMLErrorCodes {

  /**
   * HashMap of created instances. One instance per Level/Version
   */
  private static HashMap<String, SoftReference<Class<?>>> constraintLists =
    new HashMap<String, SoftReference<Class<?>>>(24);
  /**
   * Log4j logger
   */
  protected static final transient Logger                 logger          =
    Logger.getLogger(AbstractConstraintBuilder.class);


  public static Class<?> getCachedList(SBMLPackage pkg, int level, int version) {
    String className = "L" + level + "V" + version
      + StringTools.firstLetterUpperCase(pkg.toString()) + "ConstraintList";
    Class<?> listClass = getFromCache(className);
    if (listClass == null) {
      String fullName =
        "org.sbml.jsbml.validator.offline.constraints." + className;
      try {
        listClass = Class.forName(fullName);
        constraintLists.put(className, new SoftReference<Class<?>>(listClass));
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        logger.debug("Couldn't find ConstraintList: " + className);
      }
    }
    return listClass;
  }


  public static List<Integer> getIdsForClass(Class<?> clazz,
    CheckCategory category, SBMLPackage[] packages, int level, int version) {
    List<Integer> list = new ArrayList<Integer>();
    for (SBMLPackage pkg : packages) {
      Class<?> constraintList = getCachedList(pkg, level, version);
      if (constraintList != null) {
        String methodName = "add"
          + StringTools.firstLetterUpperCase(category.toString().toLowerCase())
          + clazz.getSimpleName() + "Ids";
        try {
          Method m = constraintList.getMethod(methodName, List.class);
          m.invoke(null, list);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          logger.debug("Couldn't find Method: " + constraintList.getSimpleName()
            + "." + methodName);
        }
      }
    }
    return list;
  }
  
  public static List<Integer> getIdsForAttribute(String attribute, SBMLPackage pkg, int level, int version)
  {
    List<Integer> list = new ArrayList<Integer>();
    Class<?> constraintList = getCachedList(pkg, level, version);
    
    if (constraintList != null)
    {
      String methodName = "add" + attribute + "Ids";
      
      try {
        Method m = constraintList.getMethod(methodName, List.class);
        m.invoke(null, list);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        logger.debug("Couldn't find Method: " + constraintList.getSimpleName()
          + "." + methodName);
      }
    }
    
    return list;
  }


  private static Class<?> getFromCache(String key) {
    SoftReference<Class<?>> ref = constraintLists.get(key);
    if (ref != null) {
      return ref.get();
    }
    return null;
  }


  protected static void addRangeToList(List<Integer> list, int from, int to) {
    for (int i = from; i <= to; i++) {
      list.add(i);
    }
  }
}
