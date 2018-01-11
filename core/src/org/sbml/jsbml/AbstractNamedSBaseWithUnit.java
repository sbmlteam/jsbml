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
package org.sbml.jsbml;

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * This simple implementation of the interfaces
 * {@link NamedSBaseWithDerivedUnit} and {@link SBaseWithUnit} defines elements
 * that can be addressed by their identifier and are or can be associated with a
 * defined {@link Unit} or {@link UnitDefinition}. Derived elements from this
 * class might be directly or indirectly associated with some value, i.e., the
 * value might be derived by evaluating some expression in form of an
 * {@link ASTNode}, or it might be directly defined as an attribute.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public abstract class AbstractNamedSBaseWithUnit extends AbstractNamedSBase
implements NamedSBaseWithDerivedUnit, SBaseWithUnit {

  /**
   * 
   */
  private static final long serialVersionUID = 3611229078069091891L;

  /**
   * Log4j logger
   */
  private static final transient Logger logger = Logger.getLogger(AbstractNamedSBaseWithUnit.class);

  /**
   * The unit attribute of this variable.
   */
  protected String unitsID;

  /**
   * Initializes a new object of type {@link AbstractNamedSBaseWithUnit} whose
   * identifier and name are set to {@code null} and whose level and
   * version are set to -1 each.
   */
  public AbstractNamedSBaseWithUnit() {
    this(null, null, -1, -1);
  }

  /**
   * @param nsbu
   */
  public AbstractNamedSBaseWithUnit(AbstractNamedSBaseWithUnit nsbu) {
    super(nsbu);        
    
    if (nsbu.isSetUnits()) {
      setUnits(new String(nsbu.getUnits()));
    } else {
      unitsID = nsbu.unitsID == null ? null : new String(nsbu.unitsID);
    }
  }

  /**
   * @param level
   * @param version
   */
  public AbstractNamedSBaseWithUnit(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * @param id
   */
  public AbstractNamedSBaseWithUnit(String id) {
    this(id, null, -1, -1);
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public AbstractNamedSBaseWithUnit(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public AbstractNamedSBaseWithUnit(String id, String name, int level,
    int version) {
    super(id, name, level, version);
    unitsID = null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public abstract AbstractNamedSBaseWithUnit clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    return !isSetUnits();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      AbstractNamedSBaseWithUnit v = (AbstractNamedSBaseWithUnit) object;
      equals &= v.isSetUnits() == isSetUnits();
      if (equals && isSetUnits()) {
        equals &= v.getUnits().equals(getUnits());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    if (isSetUnitsInstance()) {
      return getUnitsInstance();
    }
    String derivedUnits = getDerivedUnits();
    
    // System.out.println("AbstractNamedSBaseWithUnit - getDerivedUnits " + getElementName() + " = " + derivedUnits + " (unitsID = " + unitsID + ", isSetUnits = " + isSetUnits() + ")");

    Model model = getModel();
    if ((model != null) && (derivedUnits != null) && !derivedUnits.isEmpty()) {
      return model.getUnitDefinition(derivedUnits);
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
   */
  @Override
  public String getDerivedUnits() {
    if (isSetUnits()) {
      return unitsID;
    }
    String predef = getPredefinedUnitID();
    
    // System.out.println("AbstractNamedSBaseWithUnit - getDerivedUnits " + getElementName() + " - predef = " + predef);
    
    return predef != null ? predef : "";
  }

  /**
   * Returns the predefined unit identifier for this data type with the
   * current level/version combination.
   * 
   * @return an identifier of a unit in the containing {@link Model}. This can
   *         be one of the predefined unit identifiers if there are any.
   */
  public abstract String getPredefinedUnitID();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#getUnits()
   */
  @Override
  public String getUnits() {
    return isSetUnits() ? unitsID : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#getUnitsInstance()
   */
  @Override
  public UnitDefinition getUnitsInstance() {
    String unitsId = unitsID;
    if (isSetUnits()) {
      int level = getLevel(), version = getVersion();
      if (Unit.isUnitKind(unitsId, level, version)) {
        UnitDefinition ud = new UnitDefinition(unitsId
          + UnitDefinition.BASE_UNIT_SUFFIX, level, version);
        ud.addUnit(Unit.Kind.valueOf(unitsId.toUpperCase()));
        return ud;
      }
    } else {
      unitsId = getPredefinedUnitID();
    }
    Model model = getModel();
    return model == null ? null : model.getUnitDefinition(unitsId);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 859;
    int hashCode = super.hashCode();
    if (isSetUnits()) {
      hashCode += prime * getUnits().hashCode();
    }
    return hashCode;
  }

  /**
   * Checks whether or not a given identifier for a {@link Kind} or
   * {@link UnitDefinition} equals a predefined unit identifier for this type.
   * 
   * @param unitsID the identifier to be checked.
   * @return {@code true} if the given identifier equals the unit definition
   *         identifier that is predefined under the Level/Version combination
   *         for this data type.
   */
  public boolean isPredefinedUnitsID(String unitsID) {
    if (unitsID != null) {
      String predefID = getPredefinedUnitID();
      return (predefID != null) && unitsID.equals(predefID);
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#isSetUnits()
   */
  @Override
  public boolean isSetUnits() {
    return (unitsID != null) /*&& !isPredefinedUnitsID(unitsID)*/;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#isSetUnitsInstance()
   */
  @Override
  public boolean isSetUnitsInstance() {
    if (isSetUnits()) {
      if (Unit.isUnitKind(unitsID, getLevel(), getVersion())) {
        return true;
      }
      Model model = getModel();
      return model == null ? false : model
        .getUnitDefinition(unitsID) != null;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
   */
  @Override
  public void setUnits(Kind unitKind) {
    setUnits(unitKind.toString().toLowerCase());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(java.lang.String)
   */
  @Override
  public void setUnits(String units) {
    if ((units != null) && (units.trim().length() == 0)) {
      units = null; // If we pass the empty String or null, the value is reset.
    }

    String oldUnits = unitsID;

    if (units == null) {
      unitsID = null;
    } else {
      units = units.trim();

      boolean illegalArgument = false;

      if (!Unit.isValidUnit(getModel(), units)) {
        illegalArgument = true; // TODO - make use of the offline validation once attributes validation is in place.
      }
      if (illegalArgument) {
        if (!isReadingInProgress()) {
          throw new IllegalArgumentException(MessageFormat.format(
            JSBML.ILLEGAL_UNIT_EXCEPTION_MSG, units));
        } else {
          logger.info(MessageFormat.format(JSBML.ILLEGAL_UNIT_EXCEPTION_MSG, units));
        }
      }
      unitsID = units;
    }

    if (oldUnits != unitsID) {
      firePropertyChange(TreeNodeChangeEvent.units, oldUnits, unitsID);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
   */
  @Override
  @SuppressWarnings("deprecation")
  public void setUnits(Unit unit) {
    if ((unit.getExponent() != 1) || (unit.getScale() != 0)
        || (unit.getMultiplier() != 1d) || (unit.getOffset() != 0d)) {
      StringBuilder sb = new StringBuilder();
      sb.append('_');
      sb.append(unit.getMultiplier());
      sb.append('_');
      sb.append(unit.getScale());
      sb.append('_');
      sb.append(unit.getKind().toString());
      sb.append('_');
      sb.append(unit.getExponent());
      UnitDefinition ud = new UnitDefinition(sb.toString().replace('.', '_'),
        getLevel(), getVersion());
      ud.addUnit(unit);
      Model m = getModel();
      if (m != null) {
        m.addUnitDefinition(ud);
      }
      setUnits(ud);
    } else {
      // must be a base unit
      setUnits(unit.getKind().toString().toLowerCase());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
   */
  @Override
  public void setUnits(UnitDefinition units) {
    if (units != null) {
      setUnits(units.getId());
    } else {
      unsetUnits();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#unsetUnits()
   */
  @Override
  public void unsetUnits() {
    setUnits((String) null);
  }

}
