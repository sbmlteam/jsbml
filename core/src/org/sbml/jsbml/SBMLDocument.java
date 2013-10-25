/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.validator.SBMLValidator;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;

/**
 * Represents the 'sbml' root node of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * @version $Rev$
 */
public class SBMLDocument extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3927709655186844513L;

	/**
	 * The namespace URI of SBML Level 1 Version 1 and 2.
	 */
	public static final transient String URI_NAMESPACE_L1 = "http://www.sbml.org/sbml/level1";

	/**
	 * The namespace URI of SBML Level 2 Version 1.
	 */
	public static final transient String URI_NAMESPACE_L2V1 = "http://www.sbml.org/sbml/level2";

	/**
	 * The namespace URI of SBML Level 2 Version 2.
	 */
	public static final String URI_NAMESPACE_L2V2 = "http://www.sbml.org/sbml/level2/version2";

	/**
	 * The namespace URI of SBML Level 2 Version 3.
	 */
	public static final transient String URI_NAMESPACE_L2V3 = "http://www.sbml.org/sbml/level2/version3";

	/**
	 * The namespace URI of SBML Level 2 Version 4.
	 */
	public static final transient String URI_NAMESPACE_L2V4 = "http://www.sbml.org/sbml/level2/version4";

	/**
	 * The namespace URI of SBML Level 3 Version 1.
	 */
	public static final transient String URI_NAMESPACE_L3V1Core = "http://www.sbml.org/sbml/level3/version1/core";

	/**
	 * Contains all the parameter to validate the SBML document
	 */
	private Map<String, Boolean> checkConsistencyParameters = new HashMap<String, Boolean>();

	/**
	 * Memorizes all {@link SBMLError} when parsing the file containing this
	 * document.
	 */
	private SBMLErrorLog listOfErrors;

	/**
	 * logger used to print messages
	 */
	private transient Logger logger = Logger.getLogger(getClass());

	/**
	 * Stores all the meta identifiers within this {@link SBMLDocument} to avoid
	 * the creation of multiple identical meta identifiers. These identifiers
	 * have to be unique within the document.
	 */
	private Map<String, SBase> mappingFromMetaId2SBase;
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
	 * this object is itself. The model is {@code null}. The SBMLDocumentAttributes and
	 * the SBMLDocumentNamespaces are empty.
	 * 
	 * @param sb
	 */
	public SBMLDocument() {
		super();
		this.mappingFromMetaId2SBase = new HashMap<String, SBase>();
		this.model = null;
		SBMLDocumentAttributes = new HashMap<String, String>();
		SBMLDocumentNamespaces = new HashMap<String, String>();
		// setParentSBML(this);
		checkConsistencyParameters.put(CHECK_CATEGORY.UNITS_CONSISTENCY.name(), false);
	}

	/**
	 * Creates a SBMLDocument instance from a level and version. By default, the
	 * parent SBML object of this object is itself. The model is {@code null}. The
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
		initDefaults();
	}

	/**
	 * Creates a new {@link SBMLDocument} instance from a given {@link SBMLDocument}.
	 * 
	 * @param sb
	 */
	public SBMLDocument(SBMLDocument sb) {
		super(sb);
		this.mappingFromMetaId2SBase = new HashMap<String, SBase>();
		this.SBMLDocumentAttributes = new HashMap<String, String>();
		this.SBMLDocumentNamespaces = new HashMap<String, String>();
		if (sb.isSetModel()) {
			// This will also cause that all metaIds are registered correctly.
			setModel(sb.getModel().clone());
		} else {
			this.model = null;
		}
		Iterator<Map.Entry<String, String>> entryIterator = sb.SBMLDocumentAttributes.entrySet().iterator();
		Map.Entry<String, String> entry;
		while (entryIterator.hasNext()) {
			entry = entryIterator.next();
			this.SBMLDocumentAttributes.put(entry.getKey(), entry.getValue());
		}
		entryIterator = sb.SBMLDocumentNamespaces.entrySet().iterator();
		while (entryIterator.hasNext()) {
			entry = entryIterator.next();
			this.SBMLDocumentNamespaces.put(entry.getKey(), entry.getValue());
		}
		// setParentSBML(this);
		checkConsistencyParameters.put(CHECK_CATEGORY.UNITS_CONSISTENCY.name(), Boolean.valueOf(false));
	}

	/**
	 * Adds a name space to the SBMLNamespaces of this SBMLDocument.
	 * 
	 * @param namespaceName
	 * @param prefix
	 * @param URI
	 */
	public void addNamespace(String namespaceName, String prefix, String URI) {
		if ((prefix != null) && (prefix.trim().length() > 0)) {
			this.SBMLDocumentNamespaces.put(prefix + ':' + namespaceName, URI);

		} else {
			this.SBMLDocumentNamespaces.put(namespaceName, URI);
		}
		// this.addNamespace(URI);
		this.firePropertyChange(TreeNodeChangeEvent.addNamespace, null, URI);
	}

	/**
	 * Validates the {@link SBMLDocument} using the
	 * SBML.org online validator (http://sbml.org/validator/).
	 * <p>
	 * you can control the consistency checks that are performed when
	 * {@link #checkConsistency()} is called with the 
	 * {@link #setConsistencyChecks(CHECK_CATEGORY, boolean)} method.
	 * It will fill this {@link SBMLDocument}'s {@link #listOfErrors}
	 * with {@link SBMLError}s for each problem within this whole data
	 * structure. You will then be able to obtain this list by calling
	 * {@link #getError(int)} or {@link #getListOfErrors()}.
	 * <p>
	 * If this method returns a nonzero value (meaning, one or more
	 * consistency checks have failed for SBML document), the failures may be
	 * due to warnings @em or errors.  Callers should inspect the severity
	 * flag in the individual SBMLError objects returned by
	 * {@link SBMLDocument#getError(int)} to determine the nature of the failures.
	 * 
	 * @return the number of errors found
	 * 
	 * @see #setConsistencyChecks(CHECK_CATEGORY, boolean)
	 */
	public int checkConsistency() {

		File tmpFile = null;

		try {
			tmpFile = File.createTempFile("jsbml-", ".xml");
		} catch (IOException e) {
			logger.error("There was an error creating a temporary file :" + e.getMessage());

			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			return -1;
		}

		try {
			new SBMLWriter().writeSBML(this, tmpFile);
		} catch (IOException e) {
			logger.error("There was an error creating a temporary file: " + e.getMessage());

			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			return -1;
		} catch (XMLStreamException e) {
			logger.error("There was an error creating a temporary file: " + e.getMessage());

			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			return -1;
		} catch (SBMLException e) {
			logger.error("There was an error creating a temporary file: " + e.getMessage());

			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			return -1;
		}

		/*
		 * u --> Disable checking the consistency of measurement units associated with quantities (SBML L2V3 rules 105nn) 
		 * i --> Disable checking the correctness and consistency of identifiers used for model entities (SBML L2V3 rules 103nn) 
		 * m --> Disable checking the syntax of MathML mathematical expressions (SBML L2V3 rules 102nn) 
		 * s --> Disable checking the validity of SBO identifiers (if any) used in the model (SBML L2V3 rules 107nn) 
		 * o --> Disable static analysis of whether the model is overdetermined 
		 * p --> Disable additional checks for recommended good modeling practices 
		 * g --> Disable all other general SBML consistency checks (SBML L2v3 rules 2nnnn) 
		 * 
		 */

		// checkConsistencyParameters.put("offcheck", "u");
		// checkConsistencyParameters.put("offcheck", "u,p,o");

		// System.out.println("SBMLDocument.checkConsistency : tmp file = " + tmpFile.getAbsolutePath());

		HashMap<String, String> consistencyParameters = new HashMap<String, String>(); 
		String offcheck = null;

		for (String checkCategory : checkConsistencyParameters.keySet()) {
			CHECK_CATEGORY typeOfCheck = CHECK_CATEGORY.valueOf(checkCategory);
			boolean checkIsOn = checkConsistencyParameters.get(checkCategory).booleanValue(); 

			logger.debug(" Type of check = " + typeOfCheck + " is " + checkIsOn);

			switch (typeOfCheck)
			{
			case IDENTIFIER_CONSISTENCY: {
				if (!checkIsOn) {
					offcheck = (offcheck == null) ? "i" : offcheck + ",i"; 
				}
				break;
			}
			case GENERAL_CONSISTENCY: {
				if (!checkIsOn) {
					offcheck = (offcheck == null) ? "g" : offcheck + ",g"; 
				}
				break;
			}
			case SBO_CONSISTENCY: {
				if (!checkIsOn) {
					offcheck = (offcheck == null) ? "s" : offcheck + ",s"; 
				}
				break;
			}
			case MATHML_CONSISTENCY: {
				if (!checkIsOn) {
					offcheck = (offcheck == null) ? "m" : offcheck + ",m"; 
				}
				break;
			}
			case UNITS_CONSISTENCY: {
				if (!checkIsOn) {
					offcheck = (offcheck == null) ? "u" : offcheck + ",u"; 
				}
				break;
			}
			case OVERDETERMINED_MODEL: {
				if (!checkIsOn) {
					offcheck = (offcheck == null) ? "o" : offcheck + ",o"; 
				}
				break;
			}
			case MODELING_PRACTICE: {
				if (!checkIsOn) {
					offcheck = (offcheck == null) ? "p" : offcheck + ",p"; 
				}
				break;
			}
			default: {
				// If it's a category for which we don't have validators, ignore it.
				// Should not happen as checkConsistencyParameters is only modified through
				// setConsistencyChecks(CHECK_CATEGORY, boolean)
				break;
			}
			}
		}
		if (offcheck != null) {
			consistencyParameters.put("offcheck", offcheck);
		}

		// Doing the actual check consistency
		listOfErrors = SBMLValidator.checkConsistency(tmpFile.getAbsolutePath(), consistencyParameters); 

		try {
			tmpFile.delete();
		} catch (SecurityException e) {
			logger.error("There was an error removing a temporary file :" + e.getMessage());

			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
		}

		if (listOfErrors == null) {                                                                                      
			logger.error("There was an error accessing the sbml online validator !!");                               
			return -1;                                                                                               
		}

		return listOfErrors.getErrorCount();
	}

	/**
	 * Checks if the given meta identifier can be added in this {@link SBMLDocument} 
	 * 's {@link #mappingFromMetaId2SBase}.
	 * 
	 * @param metaId
	 *            the identifier whose value is to be checked.
	 *            
	 * @throws IllegalArgumentException if a metaid to add is already present in the list of
	 * registered metaids.             
	 */
	private void checkMetaId(String metaId) {
		if (containsMetaId(metaId)) {
			logger.error(MessageFormat.format(
					"An element with the metaid \"{0}\" is already present in the SBML document. The new element will not get added to it.",
					metaId));
			throw new IllegalArgumentException(MessageFormat.format(
					"Cannot set duplicate meta identifier \"{0}\".", metaId));
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.element.AbstractSBase#clone()
	 */
	public SBMLDocument clone() {
		return new SBMLDocument(this);
	}

	/**
	 * Collects all meta identifiers of this {@link AbstractSBase} and all of
	 * its sub-elements if recursively is {@code true}.
	 * 
	 * @param metaIds
	 *        the {@link Map} that gathers the result.
	 * @param sbase
	 *        The {@link SBase} whose meta identifier is to be collected
	 *        and from which we maybe have to recursively go through all
	 *        of its children.
	 * @param recursively
	 *        if {@code true}, this method will also consider all
	 *        sub-elements of this {@link AbstractSBase}.
	 * @param delete
	 *        if {@code true} this method will not check if
	 *        the meta identifier can be added to the {@link SBMLDocument}.
	 * @throws IllegalArgumentException
	 *         However, duplications are not legal and an
	 *         {@link IllegalArgumentException} will be thrown in such
	 *         cases.
	 */
	@SuppressWarnings("unchecked")
	private void collectMetaIds(Map<String, SBase> metaIds, SBase sbase,
			boolean recursively, boolean delete) {
		if (sbase.isSetMetaId()) {
			if (!delete) {
				// checks if the metaid can be added, throws an exception if not.
				checkMetaId(sbase.getMetaId());
			}
			metaIds.put(sbase.getMetaId(), sbase);
		}
		if (recursively) {
			Enumeration<TreeNode> children = sbase.children(); 
			while (children.hasMoreElements()) {
				TreeNode node = children.nextElement();
				if (node instanceof SBase) {
					collectMetaIds(metaIds, (SBase) node, recursively, delete);
				}
			}
		}
	}

	/**
	 * A check to see whether elements have been registered to this
	 * {@link SBMLDocument} with the given meta identifier.
	 * 
	 * @param metaId
	 * @return
	 */
	public boolean containsMetaId(String metaId) {
		return mappingFromMetaId2SBase.containsKey(metaId);
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
		Model oldValue = getModel();
		this.setModel(new Model(getLevel(), getVersion()));
		this.firePropertyChange(TreeNodeChangeEvent.model, oldValue, getModel());
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
		setModel(new Model(id, getLevel(), getVersion()));
		return getModel();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean equals = super.equals(o);
		if (equals) {
			SBMLDocument d = (SBMLDocument) o;
			if (!getSBMLDocumentAttributes().equals(
					d.getSBMLDocumentAttributes())) {
				return false;
			}
			if (!getSBMLDocumentNamespaces().equals(
					d.getSBMLDocumentNamespaces())) {
				return false;
			}
		}
		return equals;
	}

	/**
	 * Looks up the {@link SBase} registered in this {@link SBMLDocument} for the
	 * given metaId.
	 * 
	 * @param metaId
	 * @return
	 */
	public SBase findSBase(String metaId) {
		return mappingFromMetaId2SBase.get(metaId);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}
		int count = super.getChildCount(), pos = 0;
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		if (isSetModel()) {
			if (pos == index) {
				return getModel();
			}
		}
		throw new IndexOutOfBoundsException(MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",
				index, +((int) Math.min(pos, 0))));
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return super.getChildCount() + (isSetModel() ? 1 : 0);
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

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	@Override
	public String getElementName() {
		return "sbml";
	}

	/**
	 * 
	 * @param i
	 * @return 
	 */
	public SBMLError getError(int i) {
		if (!isSetListOfErrors() || (i < 0) || (i >= getErrorCount())) {
			throw new IndexOutOfBoundsException("You are trying to access the error number " + i + ", which is invalid.");	
		}

		return listOfErrors.getError(i);
	}

	/**
	 * This method returns a collection of all {@link SBMLError}s reflecting
	 * problems in the overall data structure of this {@link SBMLDocument}.
	 * 
	 * @return
	 */
	public SBMLErrorLog getErrorLog() {
		return getListOfErrors();
	}

	/**
	 * This method returns a collection of all {@link SBMLError}s reflecting
	 * problems in the overall data structure of this {@link SBMLDocument}.
	 * 
	 * @return
	 */
	public SBMLErrorLog getListOfErrors() {
		if (listOfErrors == null) {
			listOfErrors = new SBMLErrorLog();
		}
		return listOfErrors;
	}

	/**
	 * Returns the model of this {@link SBMLDocument}.
	 * 
	 * @return the model of this {@link SBMLDocument}. Can be null if it is not set.
	 */
	@Override
	public Model getModel() {
		return model;
	}

	/**
	 * 
	 * @return
	 * @deprecated use {@link #getErrorCount()}
	 */
	@Deprecated
	public int getNumErrors() {
		return getErrorCount();
	}

	/**
	 * 
	 * @return
	 */
	public int getErrorCount() {
		return isSetListOfErrors() ? listOfErrors.getErrorCount() : 0;
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

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 827;
		int hashCode = super.hashCode();
		Map<String, String> map = getSBMLDocumentAttributes();
		if (map != null) {
			hashCode += prime * map.hashCode();
		}
		map = getSBMLDocumentNamespaces();
		if (map != null) {
			hashCode += prime * map.hashCode();
		}
		return hashCode;
	}

	/**
	 * 
	 */
	private void initDefaults() {
		String sbmlNamespace = JSBML.getNamespaceFrom(getLevel(), getVersion());
		SBMLDocumentNamespaces.put("xmlns", sbmlNamespace);
	}

	/**
	 * 
	 * @return
	 */
	private boolean isSetListOfErrors() {
		return (listOfErrors != null) && (listOfErrors.getErrorCount() > 0);
	}

	/**
	 * @return {@code true} if the {@link Model} of this {@link SBMLDocument} is not {@code null}.
	 */
	public boolean isSetModel() {
		return model != null;
	}

	/**
	 * Randomly creates a new {@link String} that can be used as a metaid, i.e., a
	 * String that is a valid metaid and that is not yet used by any other element
	 * within this {@link SBMLDocument}.
	 * 
	 * @return
	 */
	public String nextMetaId() {
		String currId;
		do {
			currId = UUID.randomUUID().toString();
			if (Character.isDigit(currId.charAt(0))) {
				// Add an underscore at the beginning of the new metaid only if necessary.
				currId = '_' + currId;
			}
		} while (containsMetaId(currId));
		return currId;
	}

	/**
	 * 
	 * @param stream
	 */
	public void printErrors(PrintStream stream) {
		int nbErrors = listOfErrors.getErrorCount();

		for (int i = 0; i < nbErrors; i++) {
			stream.println(listOfErrors.getError(i));
		}
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
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
			if (prefix != null && prefix.trim().length() > 0) {
				getSBMLDocumentAttributes().put(prefix + ':' + attributeName, value);
			} else {
				getSBMLDocumentAttributes().put(attributeName, value);
			}
		}
		return isAttributeRead;
	}

	/**
	 * Saves or removes the given meta identifier in this {@link SBMLDocument}'s
	 * {@link #mappingFromMetaId2SBase}.
	 * 
	 * @param sbase
	 *        the element whose meta identifier is to be registered (if it is set).
	 * @param add
	 *        if {@code true} this will add the given meta identifier
	 *        to this {@link SBMLDocument}'s {@link #mappingFromMetaId2SBase}.
	 *        Otherwise, the given identifier will be removed from this set.
	 * @return <ul>
	 *         <li>if add is {@code true}, then this method returns
	 *         {@code true} if this set did not already contain the specified
	 *         element, {@code false} otherwise.</li>
	 *         <li>if add is not {@code true}, this method returns
	 *         {@code true} if this set contained the specified element,
	 *         {@code false} otherwise.</li>
	 *         <li>This method also returns {@code false} if the given
	 *         {@link SBase} does not have a defined metaId</li>
	 *         </ul>
	 * @throws IllegalArgumentException
	 *         if a metaid to add is already present in the list of
	 *         registered metaids.
	 */
	boolean registerMetaId(SBase sbase, boolean add) {
		if (sbase.isSetMetaId()) {
			if (add) {
				return mappingFromMetaId2SBase.put(sbase.getMetaId(), sbase) == null;
			} else {
				SBase old = mappingFromMetaId2SBase.get(sbase.getMetaId());
				if ((old != null) && (old != sbase)) {
					/* This check is needed because the given SBase might originate from a 
					 * different Document or could be a clone of some other SBase registered
					 * here.
					 */
					return mappingFromMetaId2SBase.remove(sbase.getMetaId()) != null;
				}
			}
		}
		return false;
	}


	/**
	 * Collects all meta identifiers of this {@link AbstractSBase} and all of
	 * its sub-elements if recursively is {@code true}. It can also be used
	 * to delete meta identifiers from the given {@link Set}.
	 * 
	 * @param sbase
	 *            The {@link SBase} whose meta identifier is to be registered
	 *            and from which we maybe have to recursively go through all
	 *            of its children.
	 * @param recursively
	 *            if {@code true}, this method will also consider all
	 *            sub-elements of this {@link AbstractSBase}.
	 * @param delete
	 *            if {@code true} the purpose of this method will be to
	 *            delete the meta identifier from the given {@link Set}.
	 *            Otherwise, it will try to add it to the set.
	 * @throws IllegalArgumentException
	 *             However, duplications are not legal and an
	 *             {@link IllegalArgumentException} will be thrown in such
	 *             cases.
	 */
	void registerMetaIds(SBase sbase, boolean recursively, boolean delete) {

		Map<String, SBase> metaIds = new HashMap<String, SBase>();

		collectMetaIds(metaIds, sbase, recursively, delete);

		if (delete) {
			for (String key : metaIds.keySet()) {
				mappingFromMetaId2SBase.remove(key);
			}
		} else {
			mappingFromMetaId2SBase.putAll(metaIds);
		}		
	}

	/**
	 * Controls the consistency checks that are performed when
	 * {@link SBMLDocument#checkConsistency()} is called.
	 * <p>
	 * This method works by adding or subtracting consistency checks from the
	 * set of all possible checks that {@link SBMLDocument#checkConsistency()} knows
	 * how to perform.  This method may need to be called multiple times in
	 * order to achieve the desired combination of checks.  The first
	 * argument ({@code category}) in a call to this method indicates the category
	 * of consistency/error checks that are to be turned on or off, and the
	 * second argument ({@code apply}, a boolean) indicates whether to turn it on
	 * (value of {@code true}) or off (value of {@code false}).
	 * <p>
	 * * The possible categories (values to the argument {@code category}) are the
	 * set of values from the {@link CHECK_CATEGORYH} enumeration.
	 * The following are the possible choices:
	 * <p>
	 * <p>
	 * <li> {@link GENERAL_CONSISTENCY}:
	 * Correctness and consistency of specific SBML language constructs.
	 * Performing this set of checks is highly recommended.  With respect to
	 * the SBML specification, these concern failures in applying the
	 * validation rules numbered 2xxxx in the Level&nbsp;2 Versions&nbsp;2, 3
	 * and&nbsp;4 specifications.
	 * <p>
	 * <li> {@link IDENTIFIER_CONSISTENCY}:
	 * Correctness and consistency of identifiers used for model entities.
	 * An example of inconsistency would be using a species identifier in a
	 * reaction rate formula without first having declared the species.  With
	 * respect to the SBML specification, these concern failures in applying
	 * the validation rules numbered 103xx in the Level&nbsp;2
	 * Versions&nbsp;2, 3 and&nbsp;4 specifications.
	 * <p>
	 * <li> {@link UNITS_CONSISTENCY}:
	 * Consistency of measurement units associated with quantities in a
	 * model.  With respect to the SBML specification, these concern failures
	 * in applying the validation rules numbered 105xx in the Level&nbsp;2
	 * Versions&nbsp;2, 3 and&nbsp;4 specifications.
	 * <p>
	 * <li> {@link MATHML_CONSISTENCY}:
	 * Syntax of MathML constructs.  With respect to the SBML specification,
	 * these concern failures in applying the validation rules numbered 102xx
	 * in the Level&nbsp;2 Versions&nbsp;2, 3 and&nbsp;4 specifications.
	 * <p>
	 * <li> {@link SBO_CONSISTENCY}:
	 * Consistency and validity of SBO identifiers (if any) used in the
	 * model.  With respect to the SBML specification, these concern failures
	 * in applying the validation rules numbered 107xx in the Level&nbsp;2
	 * Versions&nbsp;2, 3 and&nbsp;4 specifications.
	 * <p>
	 * <li> {@link OVERDETERMINED_MODEL}:
	 * Static analysis of whether the system of equations implied by a model
	 * is mathematically overdetermined.  With respect to the SBML
	 * specification, this is validation rule #10601 in the SBML Level&nbsp;2
	 * Versions&nbsp;2, 3 and&nbsp;4 specifications.
	 * <p>
	 * <li> {@link MODELING_PRACTICE}:
	 * Additional checks for recommended good modeling practice. (These are
	 * tests performed by libSBML and do not have equivalent SBML validation
	 * rules.)
	 * <p>
	 * <em>By default, all validation checks are applied</em> to the model in
	 * an {@link SBMLDocument} object <em>unless</em> {@link SBMLDocument#setConsistencyChecks(int, boolean)}  is called to
	 * indicate that only a subset should be applied.  Further, this default
	 * (i.e., performing all checks) applies separately to <em>each new
	 * {@link SBMLDocument} object</em> created.  In other words, each time a model
	 * is read using {@link SBMLReader#readSBML(String)} , {@link SBMLReader#readSBMLFromString(String)}, a new
	 * {@link SBMLDocument} is created and for that document, a call to
	 * {@link SBMLDocument#checkConsistency()} will default to applying all possible checks.
	 * Calling programs must invoke {@link SBMLDocument#setConsistencyChecks(int, boolean)}  for each such new
	 * model if they wish to change the consistency checks applied.
	 * <p>
	 * @param category a value drawn from JSBML#JSBML.SBML_VALIDATOR_* indicating the
	 * consistency checking/validation to be turned on or off
	 * <p>
	 * @param apply a boolean indicating whether the checks indicated by
	 * {@code category} should be applied or not.
	 * <p>
	 * @see SBMLDocument#checkConsistency()
	 */
	public void setConsistencyChecks(SBMLValidator.CHECK_CATEGORY category, boolean apply)
	{		
		checkConsistencyParameters.put(category.name(), Boolean.valueOf(apply));
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
	 * @return {@code true} if 'level' and 'version' are valid.
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
	 * {@link #getErrorCount()} to find out if the conversion succeeded
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
		registerChild(this.model);
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
		firePropertyChange(TreeNodeChangeEvent.SBMLDocumentAttributes,
				oldAttributes, this.SBMLDocumentAttributes);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	public String toString() {
		//return String.format("SBML Level %d Version %d", getLevel(), getVersion());
		return "SBML Level " + getLevel() + " Version " + getVersion();
	}

	/**
	 * Sets the {@link Model} of this {@link SBMLDocument} to null and notifies
	 * all {@link TreeNodeChangeListener} about changes.
	 * 
	 * @return {@code true} if calling this method changed the properties
	 *         of this element.
	 */
	public boolean unsetModel() {
		if (this.model != null) {
			Model oldModel = this.model;
			this.model = null;
			oldModel.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
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

		Iterator<Map.Entry<String, String>> it = getSBMLDocumentNamespaces().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			if (!entry.getKey().equals("xmlns")) {
				attributes.put(entry.getKey(), entry.getValue());
			}
		}
		return attributes;
	}

}
