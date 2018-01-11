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
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.IdManager;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Represents the kineticLaw XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * 
 */
public class KineticLaw extends AbstractMathContainer implements SBaseWithUnit, IdManager, UniqueSId {

  /**
   * Exception to be displayed in case that an illegal variant of unit is to
   * be set for this {@link SBaseWithUnit}.
   */
  private static final String ILLEGAL_UNIT_KIND_EXCEPTION_MSG = "Cannot set unit {0} because only variants of substance or time units are acceptable.";
  /**
   * A logger for this class.
   */
  private static final transient Logger logger = Logger.getLogger(KineticLaw.class);
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7528194464711501708L;
  /**
   * Represents the listOfLocalParameters or listOfParameters sub-element of a
   * kineticLaw element.
   */
  private ListOf<LocalParameter> listOfLocalParameters;
  /**
   * For internal computation: a mapping between their identifiers and
   * the {@link LocalParameter}s in {@link KineticLaw}s themselves:
   */
  private Map<String, LocalParameter> mapOfLocalParameters;
  /**
   * Represents the 'substanceUnits' XML attribute of this KineticLaw.
   * 
   * @deprecated
   */
  @Deprecated
  private String substanceUnitsID;
  /**
   * Represents the 'timeUnits' XML attribute of this KineticLaw.
   * 
   * @deprecated
   */
  @Deprecated
  private String timeUnitsID;
  /**
   * In case that a variant of substance per time is set, this will memorize
   * the identifier of the corresponding element in the {@link Model}.
   * 
   * @deprecated
   */
  @Deprecated
  private String unitsID;

  /**
   * Creates a KineticLaw instance. By default, this listOfParameters, the
   * timeUnitsID and substanceUnitsID are {@code null}.
   */
  public KineticLaw() {
    super();
    initDefaults();
  }

  /**
   * Creates a KineticLaw instance from a level and version. By default, this
   * listOfParameters, the timeUnitsID and substanceUnitsID are {@code null}.
   * 
   * @param level
   * @param version
   */
  public KineticLaw(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a KineticLaw instance from a given KineticLaw.
   * 
   * @param kineticLaw
   */
  public KineticLaw(KineticLaw kineticLaw) {
    super(kineticLaw);

    if (kineticLaw.isSetListOfLocalParameters()) {
      setListOfLocalParameters(kineticLaw
        .getListOfLocalParameters().clone());
    }
    if (kineticLaw.isSetTimeUnits()) {
      timeUnitsID = new String(kineticLaw.getTimeUnits());
    } else {
      timeUnitsID = null;
    }
    if (kineticLaw.isSetSubstanceUnits()) {
      substanceUnitsID = new String(kineticLaw.getSubstanceUnits());
    } else {
      substanceUnitsID = null;
    }
  }

  /**
   * Creates a KineticLaw instance from a given Reaction.
   * 
   * @param parentReaction
   */
  public KineticLaw(Reaction parentReaction) {
    this(parentReaction.getLevel(), parentReaction.getVersion());
    parentReaction.setKineticLaw(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#accept(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean accept(SBase sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format("accept called on {0}", sbase.getElementName()));
    }

    if (sbase instanceof LocalParameter) {
      return true;
    }

    if (sbase instanceof ListOf<?>) {
      ListOf<?> listOf = (ListOf<?>) sbase;

      if (listOf.size() > 0 && listOf.get(0) instanceof LocalParameter) {
        return true; // accept also ListOf<LocalParameter>
      }
    }

    return false;
  }

  /**
   * Adds a copy of the given Parameter object to the list of local parameters
   * in this KineticLaw.
   * 
   * @param parameter
   * @return {@code true} if the {@link #listOfLocalParameters} was
   *         changed as a result of this call.
   */
  public boolean addLocalParameter(LocalParameter parameter) {
    if (getListOfLocalParameters().add(parameter)) {
      if (parameter.isSetId() && isSetMath()) {
        getMath().updateVariables();
      }
      return true;
    }
    return false;
  }

  /**
   * Adds a copy of the given Parameter object to the list of local parameters
   * in this KineticLaw.
   * 
   * @param parameter
   * @return {@code true} if the {@link #listOfLocalParameters} was
   *         changed as a result of this call.
   * @deprecated use {@link #addLocalParameter(LocalParameter)}.
   */
  @Deprecated
  public boolean addParameter(LocalParameter parameter) {
    return addLocalParameter(parameter);
  }

  /**
   * Creates a new {@link LocalParameter} with identical
   * properties as the given {@link Parameter} and adds this new
   * {@link LocalParameter} to this {@link KineticLaw}'s
   * {@code listOfLocalParameter}s.
   * 
   * @param p
   * @deprecated A {@link KineticLaw} can only contain instances of
   *             {@link LocalParameter}s. Please use
   *             {@link #addLocalParameter(LocalParameter)} and create an
   *             instance of {@link LocalParameter} for your purposes.
   */
  @Deprecated
  public void addParameter(Parameter p) {
    addParameter(new LocalParameter(p));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public KineticLaw clone() {
    return new KineticLaw(this);
  }

  /**
   * @return
   */
  public LocalParameter createLocalParameter() {
    return createLocalParameter(null);
  }

  /**
   * @param id
   * @return
   */
  public LocalParameter createLocalParameter(String id) {
    LocalParameter parameter = new LocalParameter(id, getLevel(), getVersion());
    addLocalParameter(parameter);
    return parameter;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      KineticLaw kl = (KineticLaw) object;
      equals &= kl.isSetTimeUnits() == isSetTimeUnits();
      if (equals && isSetTimeUnits()) {
        equals &= kl.getTimeUnits().equals(getTimeUnits());
      }
      equals &= kl.isSetSubstanceUnits() == isSetSubstanceUnits();
      if (equals && isSetSubstanceUnits()) {
        equals &= kl.getSubstanceUnits().equals(getSubstanceUnits());
      }
      equals &= kl.isSetUnits() == isSetUnits();
      if (equals && isSetUnits()) {
        equals &= kl.getUnits().equals(getUnits());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    // since L3V2, empty ListOf are valid to be written to XML. The check to write them or not is done in the SBMLWriter
    if (listOfLocalParameters != null) {
      if (pos == index) {
        return getListOfLocalParameters();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#getChildCount()
   */
  @Override
  public int getChildCount() {
    int children = super.getChildCount();

    // since L3V2, empty ListOf are valid to be written to XML. The check to write them or not is done in the SBMLWriter
    if (listOfLocalParameters != null) {
      children++;
    }

    return children;
  }

  /**
   * 
   * @return the {@link #listOfLocalParameters} of this {@link KineticLaw}.
   *         Returns {@code null} if it is not set.
   */
  public ListOf<LocalParameter> getListOfLocalParameters() {
    if (listOfLocalParameters == null) {
      listOfLocalParameters = ListOf.newInstance(this, LocalParameter.class);
      registerChild(listOfLocalParameters);
    }
    return listOfLocalParameters;
  }

  /**
   * 
   * @return the {@link #listOfLocalParameters} of this {@link KineticLaw}.
   *         Returns {@code null} if it is not set.
   * @deprecated use {@link #getListOfLocalParameters()}
   */
  @Deprecated
  public ListOf<LocalParameter> getListOfParameters() {
    return getListOfLocalParameters();
  }

  /**
   * 
   * @param i
   * @return the ith {@link LocalParameter} object in the
   *         {@link #listOfLocalParameters} in this {@link KineticLaw}
   *         instance.
   */
  public LocalParameter getLocalParameter(int i) {
    return getListOfParameters().get(i);
  }

  /**
   * 
   * @param id
   * @return a {@link LocalParameter} based on its identifier.
   */
  public LocalParameter getLocalParameter(String id) {
    return mapOfLocalParameters != null ? mapOfLocalParameters.get(id) : null;
  }

  /**
   * @return the number of {@link LocalParameter} instances in this
   *         {@link KineticLaw} instance.
   */
  public int getLocalParameterCount() {
    return isSetListOfLocalParameters() ? listOfLocalParameters.size() : 0;
  }

  /**
   * 
   * @return the number of {@link LocalParameter} instances in this
   *         {@link KineticLaw} instance.
   * @deprecated use {@link #getLocalParameterCount()}
   */
  @Deprecated
  public int getNumLocalParameters() {
    return getLocalParameterCount();
  }

  /**
   * 
   * @return the number of {@link LocalParameter} instances in this
   *         {@link KineticLaw} instance.
   * @deprecated use {@link #getLocalParameterCount()}
   */
  @Deprecated
  public int getNumParameters() {
    return getNumLocalParameters();
  }

  /**
   * 
   * @param i
   * @return the ith {@link LocalParameter} object in the
   *         {@link #listOfLocalParameters} in this {@link KineticLaw}
   *         instance.
   * @deprecated use {@link #getLocalParameter(int)}
   */
  @Deprecated
  public LocalParameter getParameter(int i) {
    return getLocalParameter(i);
  }

  /**
   * 
   * @param id
   * @return a {@link LocalParameter} based on its identifier.
   * @deprecated use {@link #getLocalParameter(String)}
   */
  @Deprecated
  public LocalParameter getParameter(String id) {
    return getLocalParameter(id);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @Override
  public Reaction getParent() {
    return getParentSBMLObject();
  }

  /**
   * This method is convenient when holding an object nested inside other
   * objects in an SBML model. It allows direct access to the &lt;model&gt;
   * 
   * element containing it.
   * 
   * @return the parent SBML object.
   */
  @Override
  public Reaction getParentSBMLObject() {
    return (Reaction) parent;
  }

  /**
   * 
   * @return the substanceUnitsID of this {@link KineticLaw}. Return the empty {@link String}
   *         if it is not set.
   */
  @Deprecated
  public String getSubstanceUnits() {
    return isSetSubstanceUnits() ? substanceUnitsID : "";
  }

  /**
   * 
   * @return the UnitDefinition instance which has the substanceUnistID of
   *         this KineticLaw as id. Return null if it doesn't exist.
   */
  @Deprecated
  public UnitDefinition getSubstanceUnitsInstance() {
    Model m = getModel();
    if ((m != null) && isSetSubstanceUnits()) {
      return m.getUnitDefinition(substanceUnitsID);
    }
    if (unitsID != null) {
      UnitDefinition def = new UnitDefinition(UnitDefinition.SUBSTANCE, getLevel(),
        getVersion());
      for (Unit unit : getUnitsInstance().getListOfUnits()) {
        if (unit.isVariantOfSubstance()) {
          def.addUnit(unit);
        }
      }
      return def;
    }
    return null;
  }

  /**
   * 
   * @return the timeUnitsID of this {@link KineticLaw}. Return the empty {@link String} if it
   *         is not set.
   */
  @Deprecated
  public String getTimeUnits() {
    return isSetTimeUnits() ? timeUnitsID : "";
  }

  /**
   * 
   * @return the UnitDefinition instance which has the timeUnistID of this
   *         KineticLaw as id. Return null if it doesn't exist.
   */
  @Deprecated
  public UnitDefinition getTimeUnitsInstance() {
    Model m = getModel();
    if ((m != null) && isSetTimeUnits()) {
      return m.getUnitDefinition(timeUnitsID);
    }
    if (unitsID != null) {
      UnitDefinition def = new UnitDefinition(UnitDefinition.TIME, getLevel(), getVersion());
      Unit time;
      for (Unit unit : getUnitsInstance().getListOfUnits()) {
        time = unit.clone();
        time.setExponent(-unit.getExponent());
        if (unit.isVariantOfTime()) {
          def.addUnit(time);
        }
      }
      return def;
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#getUnits()
   */
  @Override
  @Deprecated
  public String getUnits() {
    return isSetUnits() ? unitsID : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#getUnitsInstance()
   */
  @Override
  @Deprecated
  public UnitDefinition getUnitsInstance() {
    if (unitsID != null) {
      if (Unit.isUnitKind(unitsID, getLevel(), getVersion())) {
        UnitDefinition ud = new UnitDefinition(unitsID, getLevel(),
          getVersion());
        ud.addUnit(Unit.Kind.valueOf(unitsID.toUpperCase()));
        return ud;
      }
      Model model = getModel();
      return model == null ? null : model.getUnitDefinition(unitsID);
    }
    UnitDefinition substancePerTimeUnits = getSubstanceUnitsInstance();
    UnitDefinition timeUnits = getTimeUnitsInstance();
    substancePerTimeUnits = (substancePerTimeUnits == null) ? new UnitDefinition(
      getLevel(), getVersion()) : substancePerTimeUnits.clone();
      if (timeUnits != null) {
        substancePerTimeUnits.divideBy(timeUnits);
        substancePerTimeUnits.setId(getSubstanceUnits() + "_per_"
            + getTimeUnits());
      } else if (!substancePerTimeUnits.isSetId()) {
        substancePerTimeUnits.setId(getSubstanceUnits());
      }
      return substancePerTimeUnits;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 857;
    int hashCode = super.hashCode();
    if (isSetSubstanceUnits()) {
      hashCode += prime * getSubstanceUnits().hashCode();
    }
    if (isSetTimeUnits()) {
      hashCode += prime * getTimeUnits().hashCode();
    }
    if (isSetUnits()) {
      hashCode += prime * getUnits().hashCode();
    }
    return hashCode;
  }

  /**
   * 
   */
  public void initDefaults() {
    timeUnitsID = null;
    substanceUnitsID = null;
  }

  /**
   * Returns {@code true}, if the list of local parameters is not null.
   * 
   * @return {@code true}, if the list of local parameters is not null.
   */
  public boolean isListOfLocalParametersEmpty() {
    return listOfLocalParameters != null && listOfLocalParameters.size() == 0;
  }

  /**
   * Returns {@code true}, if the list of local parameters is set and not empty..
   * 
   * @return{@code true}, if the list of local parameters is set and not empty..
   */
  public boolean isSetListOfLocalParameters() {
    return (listOfLocalParameters != null)
        && (listOfLocalParameters.size() > 0);
  }

  /**
   * 
   * @return {@code true} if the listOfParameters of this KineticLaw is not
   *         {@code null} and not empty.
   * @deprecated use {@link #isSetListOfLocalParameters()}
   */
  @Deprecated
  public boolean isSetListOfParameters() {
    return isSetListOfLocalParameters();
  }

  /**
   * 
   * @return {@code true} if the substanceUnitsID of this KineticLaw is not
   *         {@code null}.
   */
  @Deprecated
  public boolean isSetSubstanceUnits() {
    return substanceUnitsID != null;
  }

  /**
   * 
   * @return {@code true} if the UnistDefinition instance which has the
   *         substanceUnitsID of this KineticLaw as id is not {@code null}.
   * @deprecated
   */
  @Deprecated
  public boolean isSetSubstanceUnitsInstance() {
    Model m = getModel();
    return m != null ? m.getUnitDefinition(substanceUnitsID) != null
        : false;
  }

  /**
   * 
   * @return {@code true} if the timeUnitsID of this KineticLaw is not {@code null}.
   * @deprecated
   */
  @Deprecated
  public boolean isSetTimeUnits() {
    return timeUnitsID != null;
  }

  /**
   * 
   * @return {@code true} if the UnistDefinition instance which has the timeUnitsID of
   *         this KineticLaw as id is not {@code null}.
   * @deprecated
   */
  @Deprecated
  public boolean isSetTimeUnitsInstance() {
    Model m = getModel();
    return m != null ? m.getUnitDefinition(timeUnitsID) != null
        : false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#isSetUnits()
   */
  @Override
  @Deprecated
  public boolean isSetUnits() {
    return (isSetSubstanceUnits() && isSetTimeUnits()) || (unitsID != null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#isSetUnitsInstance()
   */
  @Override
  @Deprecated
  public boolean isSetUnitsInstance() {
    if (isSetSubstanceUnitsInstance() && isSetTimeUnitsInstance()) {
      return true;
    }
    if (unitsID != null) {
      if (Unit.isUnitKind(unitsID, getLevel(), getVersion())) {
        return true;
      }
      Model model = getModel();
      return model == null ? false
        : model.getUnitDefinition(unitsID) != null;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    if (!isAttributeRead) {
      if (attributeName.equals("timeUnits")) {
        setTimeUnits(value);
        return true;
      } else if (attributeName.equals("substanceUnits")) {
        setSubstanceUnits(value);
        return true;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#register(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean register(SBase sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format("register called on {0}", sbase.toString()));
    }

    return register(sbase, false);
  }

  /**
   * 
   * @param sbase
   * @param unregister
   * @return
   */
  private boolean register(SBase sbase, boolean unregister) {

    boolean success = true;

    if (sbase instanceof LocalParameter) {
      success = registerLocalParameter((LocalParameter) sbase, unregister);
    } else if (sbase instanceof ListOf<?>) {
      for (Object lp : ((ListOf<?>) sbase)) {
        success &= registerLocalParameter((LocalParameter) lp, unregister);
      }
    }

    if (success) {
      // in this case, we need to register the LocalParameter in the Model as well,
      // as the Model keep a Map of LocalParameter ids.
      Model model = getModel();

      if (model != null) {
        if (!unregister) {
          model.register(sbase);
        } else {
          model.unregister(sbase);
        }
      }
    }

    if (isReadingInProgress()) {
      // returning true when reading so that the validation can detect duplicated ids
      return true;
    }

    return success;
  }


  /**
   * @param parameter
   * @param delete
   * @return
   */
  boolean registerLocalParameter(LocalParameter parameter, boolean delete) {

    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format("registerLocalParameter called, unregister = {0}", delete));
    }

    boolean success = true;

    if (parameter.isSetId()) {
      String id = parameter.getId();
      if (delete) {
        if (mapOfLocalParameters != null) {
          mapOfLocalParameters.remove(id); // success = true all the time when deleting. Because when passing a ListOf with twice the same local parameter id, it would fail if we test the return value of mapOfLocalParameters.remove(id)
          if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("removed id=\"{0}\" from {1}", id, toString()));
          }
        }
      } else {
        if (mapOfLocalParameters == null) {
          mapOfLocalParameters = new HashMap<String, LocalParameter>();
        }
        if (mapOfLocalParameters.containsKey(id)) {
          logger.error(MessageFormat.format(
            "A local parameter with the id ''{0}'' is already present in the {1}. The new element will not be added to the list.",
            id, getListOfLocalParameters().getElementName()));
          
          if (!isReadingInProgress()) {
            throw new IllegalArgumentException(MessageFormat.format(
                "Cannot set duplicate identifier ''{0}''.", id));
          }
        }
        mapOfLocalParameters.put(id, parameter);
        success = true;

        if (logger.isDebugEnabled()) {
          logger.debug(MessageFormat.format("registered id=\"{0}\" in {1}", id, toString()));
        }
      }
    }

    return success;
  }

  /**
   * Removes the ith {@link LocalParameter} from this object.
   * 
   * @param i
   * @return the {@link LocalParameter} that has been removed or {@code null}.
   */
  public LocalParameter removeLocalParameter(int i) {
    if (!isSetListOfLocalParameters()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return listOfLocalParameters.remove(i);
  }

  /**
   * Removes the {@link LocalParameter} 'p' from the
   * {@link #listOfLocalParameters} of this {@link KineticLaw} according to
   * its 'id'.
   * 
   * @param p
   * @return {@code true} if the operation was performed successfully.
   */
  public boolean removeLocalParameter(LocalParameter p) {
    if (isSetListOfLocalParameters()) {
      return listOfLocalParameters.remove(p);
    }
    return false;
  }

  /**
   * Removes a {@link LocalParameter} from this object based on its 'id'.
   * 
   * @param id
   * @return {@code true} if the operation was performed successfully.
   */
  public boolean removeLocalParameter(String id) {
    if (isSetListOfLocalParameters()) {
      return getListOfLocalParameters().remove(getLocalParameter(id));
    }
    return false;
  }

  /**
   * Removes the ith {@link LocalParameter} from this object.
   * 
   * @param i
   * @deprecated use {@link #removeLocalParameter(int)}
   */
  @Deprecated
  public void removeParameter(int i) {
    removeLocalParameter(i);
  }

  /**
   * Removes the {@link Parameter} 'p' from the {@link #listOfLocalParameters}
   * of this {@link KineticLaw}.
   * 
   * @param p
   * @return {@code true} if the operation was performed successfully.
   * @deprecated use {@link #removeLocalParameter(LocalParameter)}
   */
  @Deprecated
  public boolean removeParameter(Parameter p) {
    return removeLocalParameter(p.getId());
  }

  /**
   * Removes a {@link LocalParameter} from this object based on its 'id'.
   * 
   * @param id
   * @return {@code true} if the operation was performed successfully.
   * @deprecated use {@link #removeLocalParameter(String)}
   */
  @Deprecated
  public boolean removeParameter(String id) {
    return removeLocalParameter(id);
  }

  /**
   * Sets the listOfParameters of this {@link KineticLaw} to 'list'. It automatically
   * sets this as parentSBML object of the listOfParameters as well as the
   * Parameter instances in the list.
   * 
   * @param listOfLocalParameters
   */
  public void setListOfLocalParameters(ListOf<LocalParameter> listOfLocalParameters)
  {
    boolean updateMathVariables = false;

    if (getLocalParameterCount() > 0 || listOfLocalParameters.size() > 0)
    {
      // We need to update the math only if there was already some local parameters
      // or if the new listOf contain some parameters
      updateMathVariables = true;
    }

    unsetListOfLocalParameters();
    this.listOfLocalParameters = listOfLocalParameters;
    if (this.listOfLocalParameters != null) {
      if (this.listOfLocalParameters.getSBaseListType() != ListOf.Type.listOfLocalParameters) {
        this.listOfLocalParameters.setSBaseListType(ListOf.Type.listOfLocalParameters);
      }

      // In this case, we have to register by hand the localParameters to the kineticLaw as registerChild
      // will not do it as we pass the listOf instead of directly the LocalParameters
      try
      {
        registerChild(this.listOfLocalParameters);
      }
      catch (IllegalArgumentException e) {

        // The listOf is wrong so we unset all the local parameters
        unsetListOfLocalParameters();

        logger.error("The list of local parameters will not be set as some ids are duplicated.");
        throw e;
      }


    }
    if (isSetMath() && updateMathVariables) {
      getMath().updateVariables();
    }
  }

  /**
   * Sets the substanceUnitsID of this {@link KineticLaw}.
   * 
   * @param substanceUnits
   * @deprecated Only defined for SBML Level 1 and Level 2 Version 1 and 2.
   * @throws PropertyNotAvailableException
   *             for inappropriate Level/Version combinations.
   */
  @Deprecated
  public void setSubstanceUnits(String substanceUnits) {
    if (((getLevel() == 2) && (getVersion() == 1)) || (getLevel() == 1)) {
      String oldSubstanceUnits = substanceUnitsID;
      substanceUnitsID = substanceUnits;
      firePropertyChange(TreeNodeChangeEvent.substanceUnits,
        oldSubstanceUnits, substanceUnitsID);
    } else {
      throw new PropertyNotAvailableException(
        TreeNodeChangeEvent.substanceUnits, this);
    }
  }

  /**
   * 
   * @param substanceUnit
   * @deprecated
   */
  @Deprecated
  public void setSubstanceUnits(Unit substanceUnit) {
    setUnits(substanceUnit);
  }

  /**
   * Sets the timeUnitsID of this {@link KineticLaw}.
   * 
   * @param timeUnits
   * @deprecated Only defined for Level 1 and Level 2 Version 1.
   * @throws PropertyNotAvailableException
   *             for inappropriate Level/Version combinations.
   */
  @Deprecated
  public void setTimeUnits(String timeUnits) {
    if (((getLevel() == 2) && (getVersion() == 1)) || (getLevel() == 1)) {
      String oldTimeUnits = timeUnitsID;
      timeUnitsID = timeUnits;
      firePropertyChange(TreeNodeChangeEvent.timeUnits, oldTimeUnits,
        timeUnitsID);
    } else {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.timeUnits,
        this);
    }
  }

  /**
   * 
   * @param timeUnit
   * @deprecated
   */
  @Deprecated
  public void setTimeUnits(Unit timeUnit) {
    setUnits(timeUnit);
  }

  /**
   * Sets the timeUnitsID of this KineticLaw.
   * 
   * @param timeUnits
   * @deprecated Only defined for Level 1 and Level 2 Version 1.
   */
  @Deprecated
  public void setTimeUnits(UnitDefinition timeUnits) {
    setTimeUnits(timeUnits.isSetId() ? timeUnits.getId() : null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
   */
  @Override
  @Deprecated
  public void setUnits(Kind unitKind) {
    setUnits(new Unit(1d, 0, unitKind, 1d, getLevel(), getVersion()));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(java.lang.String)
   */
  @Override
  @Deprecated
  public void setUnits(String units) {
    String oldUnits = unitsID;
    // TODO: Use the method Units.isValidUnit here!
    if (units == null) {
      unitsID = null;
    } else {
      units = units.trim();
      boolean illegalArgument = false;
      if (units.length() == 0) {
        illegalArgument = true;
      } else {
        Model model = getModel();
        if (((model == null) || Kind.isValidUnitKindString(units,
          getLevel(), getVersion()))
            || (((model != null) && (model.getUnitDefinition(units) != null)))) {
          unitsID = units;
        } else {
          illegalArgument = true;
        }
      }
      if (illegalArgument) {
        throw new IllegalArgumentException(MessageFormat.format(
          JSBML.ILLEGAL_UNIT_EXCEPTION_MSG, units));
      }
    }
    if (oldUnits != unitsID) {
      firePropertyChange(TreeNodeChangeEvent.units, oldUnits, unitsID);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
   */
  @Override
  @Deprecated
  public void setUnits(Unit unit) {
    if (!unit.isVariantOfSubstance() && !unit.isVariantOfTime()) {
      throw new IllegalArgumentException(MessageFormat.format(
        ILLEGAL_UNIT_KIND_EXCEPTION_MSG, unit.toString()));
    }
    if ((unit.getExponent() != 1) || (unit.getScale() != 0)
        || (unit.getMultiplier() != 1d) || (unit.getOffset() != 0d)) {
      StringBuilder sb = new StringBuilder();
      sb.append(unit.getMultiplier());
      sb.append('_');
      sb.append(unit.getScale());
      sb.append('_');
      sb.append(unit.getKind().toString());
      sb.append('_');
      sb.append(unit.getExponent());
      UnitDefinition ud = new UnitDefinition(sb.toString(), getLevel(),
        getVersion());
      ud.addUnit(unit);
      Model m = getModel();
      if (m != null) {
        m.addUnitDefinition(ud);
      }
      setUnits(ud.getId());
    } else {
      setUnits(unit.getKind().toString());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
   */
  @Override
  @Deprecated
  public void setUnits(UnitDefinition units) {
    if (units != null) {
      units = units.simplify();
      if (units.isVariantOfSubstance()) {
        setSubstanceUnits(units.getUnit(0));
      } else if (units.isVariantOfTime()) {
        setTimeUnits(units.getUnit(0));
      } else if (units.isVariantOfSubstancePerTime()) {
        for (Unit unit : units.getListOfUnits()) {
          if (unit.isVariantOfSubstance()) {
            setSubstanceUnits(unit);
          } else {
            // must be variant of time^-1
            Unit u = unit.clone();
            u.setExponent(-unit.getExponent());
            setTimeUnits(u);
          }
        }
      } else {
        throw new IllegalArgumentException(MessageFormat.format(
          ILLEGAL_UNIT_KIND_EXCEPTION_MSG, UnitDefinition
          .printUnits(units, true)));
      }
    } else {
      unsetUnits();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#unregister(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean unregister(SBase sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug(MessageFormat.format("unregister called on {0}", sbase.toString()));
    }

    return register(sbase, true);
  }

  /**
   * Removes the {@link #listOfLocalParameters} from this {@link KineticLaw} and notifies
   * all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfLocalParameters() {
    if (listOfLocalParameters != null) {
      ListOf<LocalParameter> oldListOfLocalParameters = listOfLocalParameters;
      listOfLocalParameters = null;
      oldListOfLocalParameters.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @deprecated use {@link #unsetListOfLocalParameters()}
   */
  @Deprecated
  public void unsetListOfParameters() {
    unsetListOfLocalParameters();
  }

  /**
   * Unsets the sunbstanceUnistID of this KineticLaw.
   * 
   * @deprecated
   */
  @Deprecated
  public void unsetSubstanceUnits() {
    String oldSubstanceUnitsID = substanceUnitsID;
    substanceUnitsID = null;
    firePropertyChange(TreeNodeChangeEvent.substanceUnits,
      oldSubstanceUnitsID, substanceUnitsID);
  }

  /**
   * Unsets the timeUnitsID of this KineticLaw.
   * 
   * @deprecated
   */
  @Deprecated
  public void unsetTimeUnits() {
    String oldTimeUnitsID = timeUnitsID;
    timeUnitsID = null;
    firePropertyChange(TreeNodeChangeEvent.timeUnits, oldTimeUnitsID,
      timeUnitsID);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#unsetUnits()
   */
  @Override
  @Deprecated
  public void unsetUnits() {
    unsetSubstanceUnits();
    unsetTimeUnits();
    if (unitsID != null) {
      String oldUnitID = unitsID;
      unitsID = null;
      firePropertyChange(TreeNodeChangeEvent.units, oldUnitID, unitsID);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if ((getLevel() == 1) || ((getLevel() == 2) && (getVersion() == 1))) {
      if (isSetTimeUnits()) {
        attributes.put(TreeNodeChangeEvent.timeUnits, getTimeUnits());
      }
      if (isSetSubstanceUnits()) {
        attributes.put(TreeNodeChangeEvent.substanceUnits,
          getSubstanceUnits());
      }
    }
    return attributes;
  }

}
