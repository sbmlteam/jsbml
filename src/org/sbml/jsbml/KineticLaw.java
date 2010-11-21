/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.filters.NameFilter;

/**
 * Represents the kineticLaw XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 */
public class KineticLaw extends AbstractMathContainer {

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
	 * Creates a KineticLaw instance. By default, this listOfParameters, the
	 * timeUnitsID and substanceUnitsID are null.
	 */
	public KineticLaw() {
		super();
		initDefaults();
	}

	/**
	 * Creates a KineticLaw instance from a level and version. By default, this
	 * listOfParameters, the timeUnitsID and substanceUnitsID are null.
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
		initDefaults();
		if (kineticLaw.isSetListOfParameters()) {
			setListOfLocalParameters((ListOf<LocalParameter>) kineticLaw
					.getListOfParameters().clone());
		}
		if (kineticLaw.isSetTimeUnits()) {
			this.timeUnitsID = new String(kineticLaw.getTimeUnits());
		} else {
			timeUnitsID = null;
		}
		if (kineticLaw.isSetSubstanceUnits()) {
			this.substanceUnitsID = new String(kineticLaw.getSubstanceUnits());
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

	/**
	 * Adds a copy of the given Parameter object to the list of local parameters
	 * in this KineticLaw.
	 * 
	 * @param parameter
	 * @return <code>true</code> if the {@link #listOfLocalParameters} was
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
	 * @param p
	 * @return <code>true</code> if the {@link #listOfLocalParameters} was
	 *         changed as a result of this call.
	 * @deprecated use {@link #addLocalParameter(LocalParameter)}.
	 */
	@Deprecated
	public boolean addParameter(LocalParameter parameter) {
		return addLocalParameter(parameter);
	}

	/**
	 * This method creates a new {@link LocalParameter} with identical
	 * properties as the given {@link Parameter} and adds this new
	 * {@link LocalParameter} to this {@link KineticLaw}'s
	 * {@link #listOfLocalParameter}s.
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	public KineticLaw clone() {
		return new KineticLaw(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof KineticLaw) {
			KineticLaw kl = (KineticLaw) o;
			if ((!kl.isSetListOfLocalParameters() && isSetListOfLocalParameters())
					|| (kl.isSetListOfLocalParameters() && !isSetListOfLocalParameters())) {
				return false;
			}
			boolean equal = super.equals(o);
			if (kl.isSetListOfLocalParameters() && isSetListOfLocalParameters()) {
				equal &= kl.getListOfLocalParameters().equals(getListOfLocalParameters());
			}
			if ((!kl.isSetTimeUnits() && isSetTimeUnits())
					|| (kl.isSetTimeUnits() && !isSetTimeUnits())) {
				return false;
			}
			if (kl.isSetTimeUnits() && isSetTimeUnits()) {
				equal &= kl.getTimeUnits().equals(getTimeUnits());
			}
			if ((!kl.isSetSubstanceUnits() && isSetSubstanceUnits())
					|| (kl.isSetSubstanceUnits() && !isSetSubstanceUnits())) {
				return false;
			}
			if (kl.isSetSubstanceUnits() && isSetSubstanceUnits()) {
				equal &= kl.getSubstanceUnits().equals(getSubstanceUnits());
			}
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		int children = getChildCount();
		if (index >= children) {
			throw new IndexOutOfBoundsException(index + " >= " + children);
		}
		int pos = 0;
		if (isSetMath()) {
			if (pos == index) {
				return getMath();
			}
			pos++;
		}
		if (isSetListOfLocalParameters()) {
			if (pos == index) {
				return getListOfLocalParameters();
			}
			pos++;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int children = super.getChildCount();
		if (isSetListOfLocalParameters()) {
			children++;
		}
		return children;
	}

	/**
	 * 
	 * @return the {@link #listOfLocalParameters} of this {@link KineticLaw}.
	 *         Returns null if it is not set.
	 */
	public ListOf<LocalParameter> getListOfLocalParameters() {
		if (listOfLocalParameters == null) {
			listOfLocalParameters = ListOf.newInstance(this, LocalParameter.class);
		}
		return listOfLocalParameters;
	}

	/**
	 * 
	 * @return the {@link #listOfLocalParameters} of this {@link KineticLaw}.
	 *         Returns null if it is not set.
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
		return getListOfParameters().firstHit(new NameFilter(id));
	}

	/**
	 * 
	 * @return the number of {@link LocalParameter} instances in this
	 *         {@link KineticLaw} instance.
	 */
	public int getNumLocalParameters() {
		return isSetListOfLocalParameters() ? listOfLocalParameters.size() : 0;
	}

	/**
	 * 
	 * @return the number of {@link LocalParameter} instances in this
	 *         {@link KineticLaw} instance.
	 * @deprecated use {@link #getNumLocalParameters()}
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	public Reaction getParent() {
		return (Reaction) super.getParent();
	}

	/**
	 * This method is convenient when holding an object nested inside other
	 * objects in an SBML model. It allows direct access to the &lt;model&gt;
	 * 
	 * element containing it.
	 * 
	 * @return Returns the parent SBML object.
	 */
	@Override
	public Reaction getParentSBMLObject() {
		return (Reaction) parentSBMLObject;
	}

	/**
	 * 
	 * @return the substanceUnitsID of this {@link KineticLaw}. Return the empty {@link String}
	 *         if it is not set.
	 */
	@Deprecated
	public String getSubstanceUnits() {
		return isSetSubstanceUnits() ? this.substanceUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance which has the substanceUnistID of
	 *         this KineticLaw as id. Return null if it doesn't exist.
	 */
	@Deprecated
	public UnitDefinition getSubstanceUnitsInstance() {
		if (getModel() == null) {
			return null;
		}
		return getModel().getUnitDefinition(this.substanceUnitsID);
	}

	/**
	 * 
	 * @return the timeUnitsID of this {@link KineticLaw}. Return the empty {@link String} if it
	 *         is not set.
	 */
	@Deprecated
	public String getTimeUnits() {
		return isSetTimeUnits() ? this.timeUnitsID : "";
	}

	/**
	 * 
	 * @return the UnitDefinition instance which has the timeUnistID of this
	 *         KineticLaw as id. Return null if it doesn't exist.
	 */
	@Deprecated
	public UnitDefinition getTimeUnitsInstance() {
		Model m = getModel();
		return m != null ? m.getUnitDefinition(this.timeUnitsID) : null;
	}

	/**
	 * 
	 */
	public void initDefaults() {
		this.timeUnitsID = null;
		this.substanceUnitsID = null;
	}

	/**
	 * 
	 * @return true if the listOfParameters of this KineticLaw is not null and
	 *         not empty.
	 * @deprecated use {@link #isSetListOfLocalParameters()}
	 */
	@Deprecated
	public boolean isSetListOfParameters() {
		return isSetListOfLocalParameters();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfLocalParameters() {
		return (listOfLocalParameters != null)
				&& (listOfLocalParameters.size() > 0);
	}

	/**
	 * 
	 * @return true if the substanceUnitsID of this KineticLaw is not null.
	 */
	@Deprecated
	public boolean isSetSubstanceUnits() {
		return this.substanceUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnistDefinition instance which has the
	 *         substanceUnitsID of this KineticLaw as id is not null.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetSubstanceUnitsInstance() {
		Model m = getModel();
		return m != null ? m.getUnitDefinition(this.substanceUnitsID) != null
				: false;
	}

	/**
	 * 
	 * @return true if the timeUnitsID of this KineticLaw is not null.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetTimeUnits() {
		return this.timeUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnistDefinition instance which has the timeUnitsID of
	 *         this KineticLaw as id is not null.
	 * @deprecated
	 */
	@Deprecated
	public boolean isSetTimeUnitsInstance() {
		Model m = getModel();
		return m != null ? m.getUnitDefinition(this.timeUnitsID) != null
				: false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead) {
			if (attributeName.equals("timeUnits")) {
				setTimeUnits(value);
			} else if (attributeName.equals("substanceUnits")) {
				setSubstanceUnits(value);
			}
		}
		return isAttributeRead;
	}

	/**
	 * Removes the ith {@link LocalParameter} from this object.
	 * 
	 * @param i
	 * @return the {@link LocalParameter} that has been removed or null.
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
	 * @return true if the operation was performed successfully.
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
	 * @param i
	 * @return true if the operation was performed successfully.
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
	 * @return true if the operation was performed successfully.
	 * @deprecated use {@link #removeLocalParameter(LocalParameter)}
	 */
	@Deprecated
	public boolean removeParameter(Parameter p) {
		return removeLocalParameter(p.getId());
	}
	
	/**
	 * Removes a {@link LocalParameter} from this object based on its 'id'.
	 * 
	 * @param i
	 * @return true if the operation was performed successfully.
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
	public void setListOfLocalParameters(ListOf<LocalParameter> listOfLocalParameters) {
		unsetListOfLocalParameters();
		this.listOfLocalParameters = listOfLocalParameters;
		if ((this.listOfLocalParameters != null) && (this.listOfLocalParameters.getSBaseListType() != ListOf.Type.listOfLocalParameters)) {
			this.listOfLocalParameters.setSBaseListType(ListOf.Type.listOfLocalParameters);
		}
		setThisAsParentSBMLObject(this.listOfLocalParameters);
		if (isSetMath()) {
			getMath().updateVariables();
		}
	}

	/**
	 * Sets the substanceUnitsID of this {@link KineticLaw}.
	 * 
	 * @param substanceUnits
	 * @deprecated Only defined for SBML Level 1 and Level 2 Version 1 and 2.
	 */
	@Deprecated
	public void setSubstanceUnits(String substanceUnits) {
		if ((getLevel() == 2 && getVersion() == 1) || getLevel() == 1) {
			String oldSubstanceUnits = this.substanceUnitsID;
			this.substanceUnitsID = substanceUnits;
			firePropertyChange(SBaseChangedEvent.substanceUnits,
					oldSubstanceUnits, substanceUnitsID);
		} else {
			throw new PropertyNotAvailableError(
					SBaseChangedEvent.substanceUnits, this);
		}
	}

	/**
	 * Sets the timeUnitsID of this {@link KineticLaw}.
	 * 
	 * @param timeUnits
	 * @deprecated Only defined for Level 1 and Level 2 Version 1.
	 */
	@Deprecated
	public void setTimeUnits(String timeUnits) {
		if (((getLevel() == 2) && (getVersion() == 1)) || (getLevel() == 1)) {
			String oldTimeUnits = this.timeUnitsID;
			this.timeUnitsID = timeUnits;
			firePropertyChange(SBaseChangedEvent.timeUnits, oldTimeUnits,
					timeUnitsID);
		} else {
			throw new PropertyNotAvailableError(SBaseChangedEvent.timeUnits,
					this);
		}
	}

	/**
	 * Sets the timeUnitsID of this KineticLaw.
	 * 
	 * @param timeUnits
	 * @deprecated Only defined for Level 1 and Level 2 Version 1.
	 */
	@Deprecated
	public void setTimeUnitsInstance(UnitDefinition timeUnits) {
		setTimeUnits(timeUnits.isSetId() ? timeUnits.getId() : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#toString()
	 */
	@Override
	public String toString() {
		StringBuilder kineticLawStr = new StringBuilder();
		kineticLawStr.append("kineticLaw(");
		kineticLawStr.append(((Reaction) getParent()).getId());
		kineticLawStr.append(") : ");
		kineticLawStr.append(super.toString());
		return kineticLawStr.toString();
	}
	
	/**
	 * Removes the {@link #listOfLocalParameters} from this {@link KineticLaw} and notifies
	 * all registered instances of {@link SBaseChangedListener}.
	 * 
	 * @return <code>true</code> if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfLocalParameters() {
		if (this.listOfLocalParameters != null) {
			ListOf<LocalParameter> oldListOfLocalParameters = this.listOfLocalParameters;
			this.listOfLocalParameters = null;
			oldListOfLocalParameters.fireSBaseRemovedEvent();
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
		this.substanceUnitsID = null;
	}

	/**
	 * Unsets the timeUnitsID of this KineticLaw.
	 * 
	 * @deprecated
	 */
	@Deprecated
	public void unsetTimeUnits() {
		this.timeUnitsID = null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if ((getLevel() == 1) || ((getLevel() == 2) && (getVersion() == 1))) {
			if (isSetTimeUnits()) {
				attributes.put("timeUnits", getTimeUnits());
			}
			if (isSetSubstanceUnits()) {
				attributes.put("substanceUnits", getSubstanceUnits());
			}
		}

		return attributes;
	}

}
