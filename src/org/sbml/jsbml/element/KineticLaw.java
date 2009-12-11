/*
 * $Id: KineticLaw.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/KineticLaw.java $
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

package org.sbml.jsbml.element;

import java.util.HashMap;
import java.util.List;

import org.sbml.jsbml.xml.SBaseListType;


/**
 * Represents the kineticLaw XML element of a SBML file.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @author marine
 */
public class KineticLaw extends MathContainer {

	/**
	 * Represents the listOfLocalParameters or listOfParameters subelement of a kineticLaw element.
	 */
	private ListOf<Parameter> listOfParameters;

	/**
	 * Creates a KineticLaw instance. By default, this listOfParameters is null.
	 */
	public KineticLaw() {
		super();
		listOfParameters = null;
	}
	
	/**
	 * Creates a KineticLaw instance from a level and version. By default, this listOfParameters is null.
	 * @param level
	 * @param version
	 */
	public KineticLaw(int level, int version) {
		super(level, version);
		listOfParameters = null;
	}

	/**
	 * Creates a KineticLaw instance from a given KineticLaw.
	 * @param kineticLaw
	 */
	@SuppressWarnings("unchecked")
	public KineticLaw(KineticLaw kineticLaw) {
		super(kineticLaw);
		if (kineticLaw.isSetListOfParameters()){
			setListOfLocalParameters((ListOf<Parameter>) kineticLaw.getListOfParameters().clone());
		}
		else {
			listOfParameters = null;
		}
	}

	/**
	 * Creates a KineticLaw instance from a given Reaction.
	 * @param parentReaction
	 */
	public KineticLaw(Reaction parentReaction) {
		this(parentReaction.getLevel(), parentReaction.getVersion());
		parentReaction.setKineticLaw(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.element.SBase#addChangeListener(org.sbml.squeezer.io.SBaseChangedListener
	 * )
	 */
	// @Override
	public void addChangeListener(SBaseChangedListener l) {
		super.addChangeListener(l);
		if (isSetListOfParameters()){
			listOfParameters.addChangeListener(l);
		}
	}

	/**
	 * Adds a copy of the given Parameter object to the list of local parameters
	 * in this KineticLaw.
	 * 
	 * @param p
	 */
	public void addParameter(Parameter parameter) {
		if (!isSetListOfParameters()){
			this.listOfParameters = new ListOf<Parameter>();
			setThisAsParentSBMLObject(this.listOfParameters);
		}
		if (!getListOfParameters().contains(parameter)) {
			setThisAsParentSBMLObject(parameter);
			listOfParameters.add(parameter);
			stateChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public KineticLaw clone() {
		return new KineticLaw(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof KineticLaw) {
			KineticLaw kl = (KineticLaw) o;
			if ((!kl.isSetListOfParameters() && isSetListOfParameters())
					|| (kl.isSetListOfParameters() && !isSetListOfParameters())){
				return false;
			}
			if (kl.isSetListOfParameters() && isSetListOfParameters()){
				equal &= kl.getListOfParameters().equals(getListOfParameters());
			}
			return equal;
		} else
			equal = false;
		return equal;
	}

	/**
	 * 
	 * @return the listOfParameters of this KineticLaw. Return null if it is not set.
	 */
	public ListOf<Parameter> getListOfParameters() {
		return listOfParameters;
	}
	
	/**
	 * 
	 * @return true if the listOfParameters of this KineticLaw is not null.
	 */
	public boolean isSetListOfParameters() {
		return listOfParameters != null;
	}
	
	/**
	 * Sets the listOfParameters of this KineticLaw to 'list'. It automatically sets this as
	 * parentSBML object of the listOfParameters as well as the Parameter instances in the list.
	 * @param list
	 */
	public void setListOfLocalParameters(ListOf<Parameter> list){
		this.listOfParameters = list;
		setThisAsParentSBMLObject(this.listOfParameters);
		this.listOfParameters.setSBaseListType(SBaseListType.listOfLocalParameters);
	}

	/**
	 * 
	 * @return the number of local parameters in this KineticLaw instance.
	 */
	public int getNumParameters() {
		if (isSetListOfParameters()){
			return listOfParameters.size();
		}
		return 0;
	}

	/**
	 * 
	 * @param i
	 * @return the ith Parameter object in the list of local parameters in this
	 * KineticLaw instance.
	 */
	public Parameter getParameter(int i) {
		if (isSetListOfParameters()){
			return listOfParameters.get(i);
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return a local parameter based on its identifier.
	 */
	public Parameter getParameter(String id) {
		if (isSetListOfParameters()){
			for (Parameter p : listOfParameters){
				if (p.isSetId()){
					if (p.getId().equals(id)) {
						return p;
					}
				}
				else if (p.isSetName()){
					if (p.getName().equals(id)){
						return p;
					}
				}
			}
		}
		return null;
	}

	/**
	 * This method is convenient when holding an object nested inside other
	 * objects in an SBML model. It allows direct access to the &lt;model&gt;
	 * 
	 * element containing it.
	 * 
	 * @return Returns the parent SBML object.
	 */
	public Reaction getParentSBMLObject() {
		return (Reaction) parentSBMLObject;
	}

	/**
	 * Removes the ith local parameter from this object.
	 * 
	 * @param i
	 */
	public void removeParameter(int i) {
		if (isSetListOfParameters()){
			listOfParameters.remove(i).sbaseRemoved();
		}
	}

	/**
	 * Removes the parameter 'p' from the listOfParameters of this KineticLaw.
	 * @param p
	 */
	public void removeParameter(Parameter p) {
		if (isSetListOfParameters()){
			listOfParameters.remove(p);

		}
	}

	/**
	 * Removes the ith local parameter from this object based on its id.
	 * 
	 * @param i
	 */
	public void removeParameter(String id) {
		if (isSetListOfParameters()){
			int i = 0;
			List<Parameter> listOfParameters = this.listOfParameters;
			while (i < listOfParameters.size()){
				if (listOfParameters.get(i) instanceof Parameter){
					Parameter parameter = listOfParameters.get(i);
					if (parameter.isSetId()){
						if (! parameter.getId().equals(id)){
							i++;
						}
					}
					else if (parameter.isSetName()){
						if (! parameter.getName().equals(id)){
							i++;
						}
					}
					else {
						break;
					}
				}
				else {
					break;
				}
			}
			if (i < listOfParameters.size()){
				listOfParameters.remove(i).sbaseRemoved();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		return isAttributeRead;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		return attributes;
	}

	/**
	 * Calculates and returns a <code>UnitDefinition</code> that expresses the units of measurement assumed for the 'math' expression of this <code>KineticLaw</code>.
	 * 
	 * The units are calculated based on the mathematical expression in the KineticLaw and the model quantities referenced by <ci> elements used within that expression.
	 *  The getDerivedUnitDefinition() method returns the calculated units. 
	 * <br/>
	 * Note that the functionality that facilitates unit analysis depends on the model as a whole. Thus, in cases where the object has not been added to a model 
	 * or the model itself is incomplete, unit analysis is not possible and this method will return NULL.
	 * 
	 * 
	 * @return
	 * 
	 * <b>Warning</b>:Note that it is possible the 'math' expression in the KineticLaw contains pure numbers or parameters with undeclared units. In those cases, 
	 * it is not possible to calculate the units of the overall expression without making assumptions. LibSBML does not make assumptions about the units, and 
	 * getDerivedUnitDefinition() only returns the units as far as it is able to determine them. For example, in an expression X + Y, if X has unambiguously-defined 
	 * units and Y does not, it will return the units of X. <b>It is important that callers also invoke the method</b> <code>containsUndeclaredUnits()</code> <b>to determine whether this 
	 * situation holds</b>. Callers may wish to take suitable actions in those scenarios.
	 * 
	 * @see containsUndeclaredUnits
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *  Returns <code>true</code> or <code>false</code> depending on whether the math expression of this KineticLaw contains parameters/numbers with undeclared units.
	 * 
	 * A return value of true indicates that the <code>UnitDefinition</code> returned by getDerivedUnitDefinition() may not accurately represent the units of the expression.
	 * 
	 * @return <code>true</code> if the math expression of this KineticLaw includes parameters/numbers with undeclared units, <code>false</code> otherwise.
	 */
	public boolean containsUndeclaredUnits() {
		// TODO Auto-generated method stub
		return false;
	}
}
