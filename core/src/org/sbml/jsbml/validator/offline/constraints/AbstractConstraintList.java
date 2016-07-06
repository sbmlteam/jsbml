package org.sbml.jsbml.validator.offline.constraints;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.validator.offline.ValidationContext;
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


  private static Class<?> getCachedList(SBMLPackage pkg, int level,
    int version) {
    String className = "L" + level + "V" + version
      + StringTools.firstLetterUpperCase(pkg.toString()) + "ConstraintList";

    return getCachedList(className);
  }


  private static Class<?> getCachedAttributeList(SBMLPackage pkg) {
    String className = pkg.toString() + "AttributeConstraintList";

    return getCachedList(className);
  }


  private static Class<?> getCachedList(String className) {
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


  public static Set<Integer> getIdsForClass(Class<?> clazz,
    CheckCategory category, ValidationContext ctx) {
    return getIdsForClass(clazz, category, ctx.getPackages(), ctx.getLevel(),
      ctx.getVersion());
  }


  public static Set<Integer> getIdsForClass(Class<?> clazz,
    CheckCategory category, SBMLPackage[] packages, int level, int version) {
    Set<Integer> set = new HashSet<Integer>();
    
    for (SBMLPackage pkg : packages) {
      Class<?> constraintList = getCachedList(pkg, level, version);
      
      if (constraintList != null) {
        String methodName = "add"
          + StringTools.firstLetterUpperCase(category.toString().toLowerCase())
          + clazz.getSimpleName() + "Ids";
        
        try {
          Method m = constraintList.getMethod(methodName, Set.class);
          m.invoke(null, set);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          logger.debug("Couldn't find Method: " + constraintList.getSimpleName()
            + "." + methodName);
        }
        
      }
    }
    
    return set;
  }


  public static Set<Integer> getIdsForAttribute(String attribute,
    SBMLPackage pkg, int level, int version) {
    Set<Integer> list = new HashSet<Integer>();
    Class<?> constraintList = getCachedList(pkg, level, version);

    if (constraintList != null) {
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


  protected static void addRangeToList(Set<Integer> list, int from, int to) {
    for (int i = from; i <= to; i++) {
      list.add(i);
    }
  }
}
