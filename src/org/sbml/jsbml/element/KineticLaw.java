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

import org.sbml.jsbml.xml.CurrentListOfSBMLElements;


/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class KineticLaw extends MathContainer {

	/**
	 * local parameters
	 */
	private ListOf<Parameter> listOfParameters;

	private String formula;

	/**
	 * 
	 */
	public KineticLaw() {
		super();
		listOfParameters = null;
		this.formula = null;
	}
	
	/**
	 * 
	 */
	public KineticLaw(int level, int version) {
		super(level, version);
		listOfParameters = new ListOf<Parameter>(level, version);
		listOfParameters.parentSBMLObject = this;
	}

	/**
	 * 
	 * @param kineticLaw
	 */
	@SuppressWarnings("unchecked")
	public KineticLaw(KineticLaw kineticLaw) {
		super(kineticLaw);
		listOfParameters = (ListOf<Parameter>) kineticLaw.getListOfParameters().clone();
		listOfParameters.parentSBMLObject = this;
	}

	/**
	 * 
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
	 * org.sbml.SBase#addChangeListener(org.sbml.squeezer.io.SBaseChangedListener
	 * )
	 */
	// @Override
	public void addChangeListener(SBaseChangedListener l) {
		super.addChangeListener(l);
		listOfParameters.addChangeListener(l);
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
		}
		if (!getListOfParameters().contains(parameter)) {
			listOfParameters.add(parameter);
			parameter.parentSBMLObject = this;
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
	 * @see org.sbml.MathContainer#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof KineticLaw) {
			KineticLaw kl = (KineticLaw) o;
			equal &= kl.getListOfParameters().equals(getListOfParameters());
			return equal;
		} else
			equal = false;
		return equal;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Parameter> getListOfParameters() {
		return listOfParameters;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfParameters() {
		return listOfParameters != null;
	}
	
	/**
	 * 
	 */
	public void setListOfLocalParameters(ListOf<Parameter> list){
		this.listOfParameters = list;
		setThisAsParentSBMLObject(list);
		this.listOfParameters.setCurrentList(CurrentListOfSBMLElements.listOfLocalParameters);
	}

	/**
	 * Returns the number of local parameters in this KineticLaw instance.
	 * 
	 * @return
	 */
	public int getNumParameters() {
		if (isSetListOfParameters()){
			return listOfParameters.size();
		}
		return 0;
	}

	/**
	 * Returns the ith Parameter object in the list of local parameters in this
	 * KineticLaw instance.
	 * 
	 * @param i
	 * @return
	 */
	public Parameter getParameter(int i) {
		if (isSetListOfParameters()){
			return listOfParameters.get(i);
		}
		return null;
	}

	/**
	 * Returns a local parameter based on its identifier.
	 * 
	 * @param id
	 * @return
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
	 * 
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
			if (i < listOfParameters.size())
				listOfParameters.remove(i).sbaseRemoved();
		}
	}
	
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public boolean isSetFormula(){
		return this.formula != null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
	
		if (!isAttributeRead){
			if (attributeName.equals("formula")){
				setFormula(value);
			}
		}
		return isAttributeRead;
	}
	
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetFormula()){
			attributes.put("formula", getFormula());
		}
		return attributes;
	}
}
