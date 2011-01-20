/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.sbml.jsbml.util.NotImplementedException;
import org.sbml.jsbml.util.StringTools;

/**
 * Represents the 'sbml' root node of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 */
public class SBMLDocument extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3927709655186844513L;
	/**
	 * Memorizes all {@link SBMLError} when parsing the file containing this
	 * document.
	 */
	private List<SBMLError> listOfErrors;
	/**
	 * Represents the 'model' XML subnode of a SBML file.
	 */
	private Model model;
	/**
	 * Contains all the XML attributes of the sbml XML node.
	 */
	private Map<String, String> SBMLDocumentAttributes;
	/**
	 * Contains all the namespaces of the sbml XML node and their prefixes.
	 */
	private Map<String, String> SBMLDocumentNamespaces;

	/**
	 * Creates a {@link SBMLDocument} instance. By default, the parent SBML object of
	 * this object is itself. The model is null. The SBMLDocumentAttributes and
	 * the SBMLDocumentNamespaces are empty.
	 * 
	 * @param sb
	 */
	public SBMLDocument() {
		super();
		this.model = null;
		SBMLDocumentAttributes = new HashMap<String, String>();
		SBMLDocumentNamespaces = new HashMap<String, String>();
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
		if (!hasValidLevelVersionNamespaceCombination()) {
			throw new LevelVersionError(this);
		}
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
	 * Adds a name space to the SBMLNamespaces of this SBMLDocument.
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

	/**
	 * Not yet implemented!
	 * 
	 * It is supposed to fill this {@link SBMLDocument}'s {@link #listOfErrors}
	 * with {@link SBMLError}s for each problem within this whole data
	 * structure. You will then be able to obtain this list by calling
	 * {@link #getError(int)} or {@link #getListOfErrors()}.
	 * 
	 * @return
	 * @throws NotImplementedException
	 *             Currently, this method is not implemented, so please catch a
	 *             {@link NotImplementedException} in your program until we
	 *             remove this error.
	 */
	public int checkConsistency() {
		listOfErrors = getListOfErrors();
		// TODO: IMPLEMENT!
		throw new NotImplementedException();
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
	
	/**
	 * Creates a new Model inside this {@link SBMLDocument}, and returns a
	 * pointer to it.
	 * 
	 * In SBML Level 2, the use of an identifier on a {@link Model} object is
	 * optional. This method takes an optional argument, sid, for setting the
	 * identifier. If not supplied, the identifier attribute on the Model
	 * instance is not set.
	 * 
	 * @return the new {@link Model} instance.
	 * @deprecated If not working with SBML Level 2 use
	 *             {@link #createModel(String)} instead.
	 */
	@Deprecated
	public Model createModel() {
		this.setModel(new Model(getLevel(), getVersion()));
		return getModel();
	}

	/**
	 * Creates a new instance of Model from id and the level and version of this
	 * SBMLDocument.
	 * 
	 * @param id
	 * @return the new {@link Model} instance.
	 */
	public Model createModel(String id) {
		this.setModel(new Model(id, getLevel(), getVersion()));
		return getModel();
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
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public Model getChildAt(int index) {
		int children = getChildCount();
		if (index >= children) {
			throw new IndexOutOfBoundsException(index + " >= " + children);
		}
		int pos = 0;
		if (isSetModel()) {
			if (pos == index) {
				return getModel();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return isSetModel() ? 1 : 0;
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	@Override
	public String getElementName() {
		return "sbml";
	}

	/**
	 * Not yet implemented!
	 * 
	 * @param i
	 * @return
	 */
	public SBMLError getError(int i) {
		if (isSetListOfErrors()) {
			return listOfErrors.get(i);
		}
		throw new IndexOutOfBoundsException(Integer.toString(i));
	}

	/**
	 * This method returns a collection of all {@link SBMLError}s reflecting
	 * problems in the overall data structure of this {@link SBMLDocument}.
	 * 
	 * @return
	 */
	public List<SBMLError> getListOfErrors() {
		if (listOfErrors == null) {
			listOfErrors = new LinkedList<SBMLError>();
		}
		return listOfErrors;
	}

	/**
	 * Returns the model of this {@link SBMLDocument}.
	 * 
	 * @return the model of this {@link SBMLDocument}. Can be null if it is not set.
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumErrors() {
		return isSetListOfErrors() ? listOfErrors.size() : 0;
	}

	/**
	 * 
	 * @return the map SBMLDocumentAttributes of this SBMLDocument.
	 */
	public Map<String, String> getSBMLDocumentAttributes() {
		return SBMLDocumentAttributes;
	}

	/**
	 * 
	 * @return the map SBMLDocumentNamespaces of this SBMLDocument.
	 */
	public Map<String, String> getSBMLDocumentNamespaces() {
		return SBMLDocumentNamespaces;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isSetListOfErrors() {
		return (listOfErrors != null) && (listOfErrors.size() > 0);
	}

	/**
	 * @return true if the model of this SBMLDocument is not null.
	 */
	public boolean isSetModel() {
		return model != null;
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
			isAttributeRead = true;
			
			if (attributeName.equals("level")) {
				setLevel(StringTools.parseSBMLInt(value));
			} else if (attributeName.equals("version")) {
				setVersion(StringTools.parseSBMLInt(value));
			}
			if (!prefix.equals("")) {
				getSBMLDocumentAttributes().put(
						prefix + ":" + attributeName, value);
			} else {
				getSBMLDocumentAttributes().put(attributeName, value);
			}
		}
		return isAttributeRead;
	}

	/**
	 * <p>
	 * Sets the SBML Level and Version of this {@link SBMLDocument} instance,
	 * attempting to convert the model as needed.
	 * </p>
	 * <p>
	 * This method is equivalent to calling
	 * 
	 * <pre>
	 * setLevelAndVersion(level, version, true);
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param level
	 *            the desired SBML Level
	 * @param version
	 *            the desired Version within the SBML Level
	 * @return true if 'level' and 'version' are valid.
	 * @see #setLevelAndVersion(int, int, boolean)
	 */
	public boolean setLevelAndVersion(int level, int version) {
		return super.setLevelAndVersion(level, version, true);
	}
	
	/**
	 * <p>
	 * Sets the SBML Level and Version of this {@link SBMLDocument} instance,
	 * attempting to convert the model as needed.
	 * </p><p>
	 * This method is the principal way in JSBML to convert models between
	 * Levels and Versions of SBML. Generally, models can be converted upward
	 * without difficulty (e.g., from SBML Level 1 to Level 2, or from an
	 * earlier Version of Level 2 to the latest Version of Level 2). Sometimes
	 * models can be translated downward as well, if they do not use constructs
	 * specific to more advanced Levels of SBML.
	 * </p><p>
	 * Calling this method will not necessarily lead to a successful conversion.
	 * If the conversion fails, it will be logged in the error list associated
	 * with this {@link SBMLDocument}. Callers should consult
	 * {@link #getNumErrors()} to find out if the conversion succeeded
	 * without problems. For conversions from Level 2 to Level 1, callers can
	 * also check the Level of the model after calling this method to find out
	 * whether it is Level 1. (If the conversion to Level 1 failed, the Level of
	 * this model will be left unchanged.)
	 * </p>
	 * 
	 * @param level
	 *            the desired SBML Level
	 * @param version
	 *            the desired Version within the SBML Level
	 * @param strict
	 *            boolean indicating whether to check consistency of both the
	 *            source and target model when performing conversion (defaults
	 *            to true)
	 * @return
	 */
	@Override
	public boolean setLevelAndVersion(int level, int version, boolean strict) {
		return super.setLevelAndVersion(level, version, strict);
	}

	/**
	 * Sets the {@link Model} for this {@link SBMLDocument} to the given {@link Model}.
	 * 
	 * @param model
	 */
	public void setModel(Model model) {
		unsetModel();
		this.model = model;
		setThisAsParentSBMLObject(this.model);
	}

	
	/**
	 * Sets the {@link Model} of this {@link SBMLDocument} to null and notifies
	 * all {@link SBaseChangedListener} about changes.
	 * 
	 * @return <code>true</code> if calling this method changed the properties
	 *         of this element.
	 */
	public boolean unsetModel() {
		if (this.model != null) {
			Model oldModel = this.model;
			this.model = null;
			oldModel.fireSBaseRemovedEvent();
			return true;
		}
		return false;
	}

	/**
	 * Sets the SBMLDocumentAttributes.
	 * 
	 * @param sBMLDocumentAttributes
	 */
	public void setSBMLDocumentAttributes(
			Map<String, String> sBMLDocumentAttributes) {
		Map<String, String> oldAttributes = this.SBMLDocumentAttributes;
		SBMLDocumentAttributes = sBMLDocumentAttributes;
		firePropertyChange(SBaseChangedEvent.SBMLDocumentAttributes,
				oldAttributes, this.SBMLDocumentAttributes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		return String.format("SBML Level %d Version %d", getLevel(),
				getVersion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

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
