/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2018 jointly by the following organizations:
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

import static java.text.MessageFormat.format;

import java.util.ResourceBundle;
import java.util.UUID;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.xml.XMLNode;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class SBMLtools {
  
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(SBMLtools.class);

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
  public static final String getIdOrName(SBase nsb) {
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
  public static final String getNameOrId(SBase nsb) {
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
  
  /**
   * Generate a valid SBML identifier using UUID.
   * 
   * @param model
   * @return
   */
  public static String nextId(Model model) {
    String idOne;
    do {
      idOne = UUID.randomUUID().toString().replace("-", "_");
      if (Character.isDigit(idOne.charAt(0))) {
        // Add an underscore at the beginning of the new id only if
        // necessary.
        idOne = '_' + idOne;
      }
    } while (model.findNamedSBase(idOne) != null);
    return idOne;
  }
  
  /**
   * 
   * @param sbase
   * @param term
   */
  public static final void setSBOTerm(SBase sbase, int term) {
    if (-1 < sbase.getLevelAndVersion().compareTo(Integer.valueOf(2),
      Integer.valueOf(2))) {
      sbase.setSBOTerm(term);
    } else {
      ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.resources.cfg.Messages");
      logger.warn(format(bundle.getString("SBMLtools.COULD_NOT_SET_SBO_TERM"),
        SBO.sboNumberString(term), sbase.getElementName(), sbase.getLevel(), sbase.getVersion()));
    }
  }
  
  /**
   * Generates a valid SId from a given name. If the name already is a valid
   * SId, the name is returned. If the SId already exists in this document,
   * "_&lt;number>" will be appended and the next free number is being assigned.
   * => See SBML L2V4 document for the Definition of SId. (Page 12/13)
   * 
   * @param name
   * @return SId
   */
  public static String nameToSId(String name, SBMLDocument doc) {
    /*
     * letter = a-z,A-Z; digit = 0-9; idChar = (letter | digit | _ );
     * SId = ( letter | _ ) idChar*
     */
    String ret;
    if ((name == null) || (name.trim().length() == 0)) {
      ret = incrementSIdSuffix("SId", doc);
    } else {
      // Make unique
      ret = toSId(name);
      Model model = doc.getModel();
      if (model.containsUniqueNamedSBase(ret)) {
        ret = incrementSIdSuffix(ret, doc);
      }
    }
    
    return ret;
  }
  
  /**
   * Appends "_&lt;number&gt;" to a given String. &lt;number&gt; is being set to
   * the next free number, so that this sID is unique in this
   * {@link SBMLDocument}. Should only be called from {@link #nameToSId(String)}.
   * 
   * @return
   */
  private static String incrementSIdSuffix(String prefix, SBMLDocument doc) {
    int i = 1;
    String aktString = prefix + "_" + i;
    Model model = doc.getModel();
    while (model.containsUniqueNamedSBase(aktString)) {
      aktString = prefix + "_" + (++i);
    }
    return aktString;
  }
  
  /**
   * 
   * @param name
   * @return
   */
  public static String toSId(String name) {
    name = name.trim();
    StringBuilder id = new StringBuilder(name.length() + 4);
    char c = name.charAt(0);
    
    // Must start with letter or '_'.
    if (!(isLetter(c) || c == '_')) {
      id.append("_");
    }
    
    // May contain letters, digits or '_'
    for (int i = 0; i < name.length(); i++) {
      c = name.charAt(i);
      if (c == ' ') {
        c = '_'; // Replace spaces with "_"
      }
      if (isLetter(c) || Character.isDigit(c) || (c == '_')) {
        id.append(c);
      } else if ((c == '-') || (c == '(') || (c == ')')) {
        if (i < name.length() - 1) {
          id.append('_');
        }
      } // else: skip other invalid characters
    }
    return id.toString();
  }
  
  /**
   * @param c
   * @return {@code true} if c is out of A-Z or a-z
   */
  public static boolean isLetter(char c) {
    // Unfortunately Character.isLetter also accepts ??, but SBML doesn't.
    // a-z or A-Z
    return ((c >= 97) && (c <= 122)) || ((c >= 65) && (c <= 90));
  }

}
