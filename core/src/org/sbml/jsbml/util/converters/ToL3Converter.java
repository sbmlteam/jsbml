/*
 * $IdToL3V1Converter.java 09:52:21 draeger $
 * $URLToL3V1Converter.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018  jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.util.converters;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.util.ValuePair;


/**
 * @author Andreas Dr&auml;ger
 * @since 1.2
 * @date 05.01.2018
 */
public abstract class ToL3Converter extends LevelVersionConverter {

  /**
   * @param targetLV
   */
  public ToL3Converter(ValuePair<Integer, Integer> targetLV) {
    super(targetLV);
  }

  /**
   * @param sourceLV
   * @param targetLV
   */
  public ToL3Converter(ValuePair<Integer, Integer> sourceLV,
    ValuePair<Integer, Integer> targetLV) {
    super(sourceLV, targetLV);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.LevelVersionConverter#needsAction(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean needsAction(SBase sbase) {
    return (sbase instanceof Unit) || (sbase instanceof Reaction) || (sbase instanceof Model);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.converters.LevelVersionConverter#performAction(org.sbml.jsbml.SBase)
   */
  @Override
  public <T extends SBase> boolean performAction(T sbase) {
    if (sbase instanceof Unit) {
      // Some hard-coded default stuff:
      // Before SBML l3, exponent, scale and multiplier had default values.
      // => Restore them when upgrading to l3
      Unit ud = ((Unit) sbase);
      if ((sbase.getLevel() < 3) && (getTargetLV().getL() >= 3)) {
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
      return true;
    } else if (sbase instanceof Model) {
      //      for (UnitDefinition ud : ((Model) sbase).getListOfPredefinedUnitDefinitions()) {
      //        ud.setLevelAndVersion(getTargetLV().getL().intValue(), getTargetLV().getV().intValue(), this);
      //      }
    }
    return false;
  }
}
