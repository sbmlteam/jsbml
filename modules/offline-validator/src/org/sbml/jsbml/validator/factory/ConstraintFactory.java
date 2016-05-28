package org.sbml.jsbml.validator.factory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import org.sbml.jsbml.validator.constraint.AnyConstraint;
import org.sbml.jsbml.validator.constraint.ConstraintGroup;

public class ConstraintFactory {

  /**
   * Caches the constraints with SoftReferences
   */
  private static HashMap<Integer, SoftReference<AnyConstraint<?>>> cache;
  
  
  /**
   * HashMap of created instances.
   * One instance per Level/Version
   */
  private static HashMap<String, SoftReference<ConstraintFactory>> instances;

  private FactoryManager manager;


  /**
   * Returns a instance for the level and version.
   * @param level
   * @param version
   * @return
   */
  public static ConstraintFactory getInstance(int level, int version) 
  {
	ConstraintFactory factory = null;
    String key = "L" + level + "V" + version;
    
    if(ConstraintFactory.instances != null)
    {
    	SoftReference<ConstraintFactory> factoryRef = ConstraintFactory.instances.get(key);
        factory = factoryRef.get();
    }
    else
    {
    	ConstraintFactory.instances = new HashMap<String, SoftReference<ConstraintFactory>>();
    }
    
    
    // No factory cached
    if(factory == null)
    {
      try {
        String pkg = ConstraintFactory.class.getPackage().getName();
        String className = pkg + "." + key + "FactoryManager";
        Class<?> c = Class.forName(className);
        FactoryManager manager = (FactoryManager) (c.newInstance());
        
        factory = new ConstraintFactory(manager);
        instances.put(key, new SoftReference<ConstraintFactory>(factory));
      } catch (Exception e) {
        System.out.println("Couldn't find a ConstraintFactory for level " + level
          + " and version " + version + ".");
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
    return null;
  }



/**
   * @param o
   * @param category
   * @return
   */
  public <T> AnyConstraint<T> getConstraintsForClass(Class<?> c, CheckCategory category) {
    List<Integer> list = manager.getIdsForClass(c, category);
    int[] array = new int[list.size()];
    for (int i = 0; i < array.length; i++) {
      Integer integer = list.get(i);
      array[i] = integer.intValue();
    }
    return getConstraints(array);
  }

  
  
  /**
   * Returns the constraint with the ID. The IDs of a constraint
   * is identically to the error code it will log on failure.
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
   * @param ids
   * @return A ConstraintGroup 
   */
  public <T> AnyConstraint<T> getConstraints(int[] ids) {
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
      ConstraintFactory.cache =
        new HashMap<Integer, SoftReference<AnyConstraint<?>>>(24);
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
      // else
      // {
      // // Try to cast the constraint to the needed Type
      // try
      // {
      // AnyConstraint<T> out = (AnyConstraint<T>)(c);
      // return out;
      // }
      // catch (Exception e)
      // {
      // e.printStackTrace();
      // }
      // }
      return c;
    }
    return null;
  }


  private void addToCache(Integer id, AnyConstraint<?> constraint) {
    SoftReference<AnyConstraint<?>> ref =
      new SoftReference<AnyConstraint<?>>(constraint);
    ConstraintFactory.cache.put(new Integer(id), ref);
  }
}
