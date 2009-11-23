/*
 * $Id: SBMLDocument.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/SBMLDocument.java $
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-09-07
 * 
 */
public class SBMLDocument extends AbstractSBase {

	private Model model;
	private HashMap<String, String> SBMLDocumentAttributes = new HashMap<String, String>();

	/**
	 * @param sb
	 */
	public SBMLDocument() {
		super();
		this.model = null;
	}
	
	/**
	 * @param sb
	 */
	public SBMLDocument(SBMLDocument sb) {
		super(sb);
		setModel(sb.getModel());
	}

	/**
	 * @param level
	 * @param version
	 */
	public SBMLDocument(int level, int version) {
		super(level, version);
		this.model = null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Model createModel(String id) {
		this.setModel(new Model(id, getLevel(), getVersion()));
		return getModel();
	}

	/**
	 * Sets the Model for this SBMLDocument to a copy of the given Model.
	 * 
	 * @param model
	 */
	public void setModel(Model model) {
		this.model = model;
		this.model.parentSBMLObject = this;
	}

	/**
	 * The default SBML Level of new SBMLDocument objects.
	 * 
	 * @return
	 */
	public int getDefaultLevel() {
		return 2;
	}

	/**
	 * The default Version of new SBMLDocument objects.
	 * 
	 * @return
	 */
	public int getDefaultVersion() {
		return 4;
	}

	/**
	 * 
	 */
	public Model getModel() {
		return model;
	}
	
	/**
	 * 
	 */
	public boolean isSetModel() {
		return model != null;
	}

	/**
	 * 
	 * @param level
	 * @param version
	 * @return
	 */
	public boolean setLevelAndVersion(int level, int version) {
		this.level = level;
		this.version = version;
		return hasValidLevelVersionNamespaceCombination();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.AbstractSBase#clone()
	 */
	// @Override
	public SBMLDocument clone() {
		return new SBMLDocument(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.AbstractSBase#toString()
	 */
	// @Override
	public String toString() {
		return "SBML Level " + level + " Version " + version;
	}

	public void setSBMLDocumentAttributes(HashMap<String, String> sBMLDocumentAttributes) {
		SBMLDocumentAttributes = sBMLDocumentAttributes;
	}

	public HashMap<String, String> getSBMLDocumentAttributes() {
		return SBMLDocumentAttributes;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		if (!isAttributeRead){
			this.getSBMLDocumentAttributes().put(prefix+":"+attributeName, value);
			return true;
		}
		return isAttributeRead;
	}
}
