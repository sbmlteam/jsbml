/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2013  jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.util;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Unit;


/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 21.10.2013
 */
public class SBMLtools {
  
  /**
   * 
   * @param sbase
   * @param level
   * @param version
   * @return
   */
  public static final <T extends SBase> T setLevelAndVersion(T sbase, int level, int version) {
    
    // Some hard-coded default stuff:
    // Before SBML l3, exponent, scale and multiplier had default values.
    // => Restore them when upgrading to l3
    if (sbase instanceof Unit) {
      Unit ud = ((Unit) sbase);
      if ((sbase.getLevel() < 3) && (level >= 3)) {
        if (!ud.isSetExponent()) {
          ud.setExponent(1d);
        }
        if (!ud.isSetScale()) {
          ud.setScale(0);
        }
        if (!ud.isSetMultiplier()) {
          ud.setMultiplier(1d);
        }
      }
    }
    
    // Set level and version
    sbase.setVersion(version);
    sbase.setLevel(level);
    
    // Recurse to children
    for (int i = 0; i<sbase.getChildCount(); i++) {
      TreeNode child = sbase.getChildAt(i);
      if (child instanceof SBase) {
        setLevelAndVersion((SBase) child, level, version);
      }
    }
    return sbase;
  }
  
}
