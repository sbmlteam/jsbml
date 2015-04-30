/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.util;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.xml.XMLNode;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.0
 * @date 21.10.2013
 */
public class SBMLtools {

  /**
   * 
   * @param model
   */
  public static final void addPredefinedUnitDefinitions(Model model) {
    boolean isL3 = model.getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) >= 0;
    if (model.getUnitDefinition(UnitDefinition.SUBSTANCE) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.substance(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setSubstanceUnits(UnitDefinition.SUBSTANCE);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.VOLUME) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.volume(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setVolumeUnits(UnitDefinition.VOLUME);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.AREA) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.area(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setAreaUnits(UnitDefinition.AREA);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.LENGTH) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.length(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setLengthUnits(UnitDefinition.LENGTH);
      }
    }
    if (model.getUnitDefinition(UnitDefinition.TIME) == null) {
      model.addUnitDefinition(SBMLtools.setLevelAndVersion(UnitDefinition.time(2, 4), model.getLevel(), model.getVersion()));
      if (isL3) {
        model.setTimeUnits(UnitDefinition.TIME);
      }
    }
  }

  /**
   * @param nsb
   * @return preferably the id if it is set, otherwise the name or an empty
   *         {@link String} if both is undefined.
   */
  public static final String getIdOrName(NamedSBase nsb) {
    if (nsb.isSetId()) {
      return nsb.getId();
    } else if (nsb.isSetName()) {
      return nsb.getId();
    }
    return "";
  }

  /**
   * @param nsb
   * @return preferably the name if is set otherwise the id or an empty
   *         {@link String} if both is undefined.
   */
  public static final String getNameOrId(NamedSBase nsb) {
    if (nsb.isSetName()) {
      return nsb.getName();
    } else if (nsb.isSetId()) {
      return nsb.getId();
    }
    return "";
  }

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
    } else if (sbase instanceof Model) {
      for (UnitDefinition ud : ((Model) sbase).getListOfPredefinedUnitDefinitions()) {
        setLevelAndVersion(ud, level, version);
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

  /**
   * 
   * @param xml
   * @return
   */
  public static final String toXML(XMLNode xml) {
    try {
      return xml.toXMLString();
    } catch (XMLStreamException exc) {
      return "";
    } catch (RuntimeException e) {
      return ""; // needed when xml is null for example.
    }
  }

}
