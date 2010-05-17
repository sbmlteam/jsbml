/*
 * $Id$
 * $URL$
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

package org.sbml.jsbml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents the 'sbml' root node of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * 
 * @composed 1 model 1 Model
 */
public class SBMLDocument extends AbstractSBase {

	/**
	 * Represents the 'model' XML subnode of a SBML file.
	 */
	private Model model;
	/**
	 * Contains all the XML attributes of the sbml XML node.
	 */
	private HashMap<String, String> SBMLDocumentAttributes;
	/**
	 * Contains all the namespaces of the sbml XML node and their prefixes.
	 */
	private HashMap<String, String> SBMLDocumentNamespaces = new HashMap<String, String>();

	/**
	 * Creates a SBMLDocument instance. By default, the parent SBML object of
	 * this object is itself. The model is null. The SBMLDocumentAttributes and
	 * the SBMLDocumentNamespaces are empty.
	 * 
	 * @param sb
	 */
	public SBMLDocument() {
		super();
		this.model = null;
		SBMLDocumentAttributes = new HashMap<String, String>();
		setParentSBML(this);
	}

	/**
	 * Creates a SBMLDocument instance from a given SBMLDocument.
	 * 
	 * @param sb
	 */
	public SBMLDocument(SBMLDocument sb) {
		super(sb);
		if (sb.isSetModel()) {
			setModel(sb.getModel().clone());
		} else {
			this.model = null;
		}
		setParentSBML(this);
	}

	/**
	 * Creates a SBMLDocument instance from a level and version. By default, the
	 * parent SBML object of this object is itself. The model is null. The
	 * SBMLDocumentAttributes and the SBMLDocumentNamespaces are empty.
	 * 
	 * @param level
	 * @param version
	 */
	public SBMLDocument(int level, int version) {
		this();
		setLevel(level);
		setVersion(version);
	}

	/**
	 * Creates a new instance of Model from id and the level and version of this
	 * SBMLDocument.
	 * 
	 * @param id
	 * @return the new Model instance.
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
		setThisAsParentSBMLObject(this.model);
	}

	/**
	 * The default SBML Level of new SBMLDocument objects.
	 * 
	 * @return 2
	 */
	public int getDefaultLevel() {
		return 2;
	}

	/**
	 * The default Version of new SBMLDocument objects.
	 * 
	 * @return 4
	 */
	public int getDefaultVersion() {
		return 4;
	}

	/**
	 * @return the model of this SBMLDocument. Can be null if it is not set.
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @return true if the model of this SBMLDocument is not null.
	 */
	public boolean isSetModel() {
		return model != null;
	}

	/**
	 * 
	 * @param level
	 * @param version
	 * @return true if 'level' and 'version' are valid.
	 */
	public boolean setLevelAndVersion(int level, int version) {
		this.level = level;
		this.version = version;
		return hasValidLevelVersionNamespaceCombination();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.AbstractSBase#clone()
	 */
	// @Override
	public SBMLDocument clone() {
		return new SBMLDocument(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.AbstractSBase#equals(Object o)
	 */
	public boolean equals(Object o) {
		if (o instanceof SBMLDocument) {
			SBMLDocument d = (SBMLDocument) o;
			boolean equals = super.equals(o);

			if (!getSBMLDocumentAttributes().equals(
					d.getSBMLDocumentAttributes())) {
				return false;
			}
			if (!getSBMLDocumentNamespaces().equals(
					d.getSBMLDocumentNamespaces())) {
				return false;
			}
			return equals;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.AbstractSBase#toString()
	 */
	// @Override
	public String toString() {
		return String.format("SBML Level %d Version %d", level, version);
	}

	/**
	 * Sets the SBMLDocumentAttributes.
	 * 
	 * @param sBMLDocumentAttributes
	 */
	public void setSBMLDocumentAttributes(
			HashMap<String, String> sBMLDocumentAttributes) {
		SBMLDocumentAttributes = sBMLDocumentAttributes;
	}

	/**
	 * 
	 * @return the map SBMLDocumentAttributes of this SBMLDocument.
	 */
	public HashMap<String, String> getSBMLDocumentAttributes() {
		return SBMLDocumentAttributes;
	}

	/**
	 * 
	 * @return the map SBMLDocumentNamespaces of this SBMLDocument.
	 */
	public HashMap<String, String> getSBMLDocumentNamespaces() {
		return SBMLDocumentNamespaces;
	}

	/**
	 * Adds a namespace to the SBMLNamespaces of this SBMLDocument.
	 * 
	 * @param namespaceName
	 * @param prefix
	 * @param URI
	 */
	public void addNamespace(String namespaceName, String prefix, String URI) {
		if (!prefix.equals("")) {
			this.SBMLDocumentNamespaces.put(prefix + ":" + namespaceName, URI);

		} else {
			this.SBMLDocumentNamespaces.put(namespaceName, URI);
		}
		this.addNamespace(URI);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (!isAttributeRead) {
			if (!prefix.equals("")) {
				this.getSBMLDocumentAttributes().put(
						prefix + ":" + attributeName, value);
			} else {
				this.getSBMLDocumentAttributes().put(attributeName, value);
			}
			return true;
		}
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		attributes.putAll(SBMLDocumentAttributes);

		if (isSetLevel()) {
			attributes.put("level", Integer.toString(this.getLevel()));
		}
		if (isSetVersion()) {
			attributes.put("version", Integer.toString(this.getVersion()));
		}

		Iterator<Map.Entry<String, String>> it = this
				.getSBMLDocumentNamespaces().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			if (!entry.getKey().equals("xmlns")) {
				attributes.put(entry.getKey(), entry.getValue());
			}

		}
		return attributes;
	}
}
