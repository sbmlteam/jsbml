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
	package org.sbml.jsbml.xml.parsers;

	import java.text.MessageFormat;
	import java.util.ArrayList;
	import java.util.Enumeration;
	import java.util.List;
	import java.util.ResourceBundle;

	import javax.swing.tree.TreeNode;

	import org.apache.log4j.Logger;
	import org.mangosdk.spi.ProviderFor;
	import org.sbml.jsbml.ASTNode;
	import org.sbml.jsbml.ListOf;
	import org.sbml.jsbml.Model;
	import org.sbml.jsbml.SBase;
	import org.sbml.jsbml.ext.ASTNodePlugin;
	import org.sbml.jsbml.ext.SBasePlugin;
	import org.sbml.jsbml.ext.qual.FunctionTerm;
	import org.sbml.jsbml.ext.qual.Input;
	import org.sbml.jsbml.ext.qual.Output;
	import org.sbml.jsbml.ext.qual.QualConstants;
	import org.sbml.jsbml.ext.qual.QualList;
	import org.sbml.jsbml.ext.qual.QualModelPlugin;
	import org.sbml.jsbml.ext.qual.QualitativeSpecies;
	import org.sbml.jsbml.ext.qual.Transition;
	import org.sbml.jsbml.util.ResourceManager;
	import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

	/**
	 * This class is used to parse the qual extension package elements and
	 * attributes. The namespaceURI URI of this parser is
	 * "http://www.sbml.org/sbml/level3/version1/qual/version1". This parser is able
	 * to read and write elements of the qual package (implements ReadingParser and
	 * WritingParser).
	 * 
	 * @author Nicolas Rodriguez
	 * @since 1.0
	 */
	@ProviderFor(ReadingParser.class)
	public class QualParser extends AbstractReaderWriter implements PackageParser {

		/**
		 * The QualList enum which represents the name of the list this parser is
		 * currently reading.
		 */
		private QualList groupList = QualList.none;
		/**
		 * 
		 */
		private static final transient Logger logger = Logger.getLogger(QualParser.class);

		/**
		 * Localization support.
		 */
		private static final transient ResourceBundle bundle = ResourceManager
				.getBundle("org.sbml.jsbml.resources.cfg.Messages");

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object
		 * sbase)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

			if (logger.isDebugEnabled()) {
				logger.debug("getListOfSBMLElementsToWrite: " + sbase.getClass().getCanonicalName());
			}

			List<Object> listOfElementsToWrite = new ArrayList<Object>();

			if (sbase instanceof Model) {
				QualModelPlugin modelGE = (QualModelPlugin) ((Model) sbase).getExtension(QualConstants.namespaceURI);

				Enumeration<TreeNode> children = modelGE.children();

				while (children.hasMoreElements()) {
					listOfElementsToWrite.add(children.nextElement());
				}
			} else if (sbase instanceof TreeNode) {
				Enumeration<? extends TreeNode> children = ((TreeNode) sbase).children();

				while (children.hasMoreElements()) {
					listOfElementsToWrite.add(children.nextElement());
				}
			}

			if (listOfElementsToWrite.isEmpty()) {
				listOfElementsToWrite = null;
			} else if (logger.isDebugEnabled()) {
				logger.debug("getListOfSBMLElementsToWrite size = " + listOfElementsToWrite.size());
			}

			return listOfElementsToWrite;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.jsbml.xml.parsers.AbstractReaderWriter#processEndElement(java.lang.
		 * String, java.lang.String, boolean, java.lang.Object)
		 */
		@Override
		public boolean processEndElement(String elementName, String prefix, boolean isNested, Object contextObject) {

			if (elementName.equals("listOfQualitativeSpecies") || elementName.equals("listOfTransitions")) {
				groupList = QualList.none;
			}

			if (elementName.equals("listOfInputs") || elementName.equals("listOfOutputs")
					|| elementName.equals("listOfFunctionTerms")) {
				groupList = QualList.listOfTransitions;
			}

			if (elementName.equals("listOfSymbolicValues")) {
				groupList = QualList.listOfQualitativeSpecies;
			}

			return true;
		}

		/**
		 * 
		 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(String,
		 *      String, String, boolean, boolean, Object)
		 */
		@Override
		// Create the proper object and link it to his parent.
		@SuppressWarnings("unchecked")
		public Object processStartElement(String elementName, String uri, String prefix, boolean hasAttributes,
				boolean hasNamespaces, Object contextObject) {
			// logger.debug("processStartElement: elementName = " + elementName + " (" +
			// contextObject.getClass().getSimpleName() + ")");

			if (contextObject instanceof Model) {
				Model model = (Model) contextObject;
				QualModelPlugin qualModel = null;

				if (model.getExtension(QualConstants.namespaceURI) != null) {
					qualModel = (QualModelPlugin) model.getExtension(QualConstants.namespaceURI);
				} else {
					qualModel = new QualModelPlugin(model);
					model.addExtension(QualConstants.namespaceURI, qualModel);
				}
				AbstractReaderWriter.storeElementsOrder(elementName, qualModel);
				
				if (elementName.equals("listOfQualitativeSpecies")) {
					ListOf<QualitativeSpecies> listOfQualitativeSpecies = qualModel.getListOfQualitativeSpecies();
					groupList = QualList.listOfQualitativeSpecies;
					return listOfQualitativeSpecies;
				} else if (elementName.equals("listOfTransitions")) {

					ListOf<Transition> listOfTransitions = qualModel.getListOfTransitions();
					groupList = QualList.listOfTransitions;
					
					return listOfTransitions;
				} else {
					logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
					return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
				}

			} else if (contextObject instanceof Transition) {
				Transition transition = (Transition) contextObject;

				// keep order of elements for later validation
				AbstractReaderWriter.storeElementsOrder(elementName, contextObject);

				if (elementName.equals("listOfInputs")) {
					ListOf<Input> listOfInputs = transition.getListOfInputs();
					groupList = QualList.listOfInputs;
					return listOfInputs;
				} else if (elementName.equals("listOfOutputs")) {
					ListOf<Output> listOfOutputs = transition.getListOfOutputs();
					groupList = QualList.listOfOutputs;
					return listOfOutputs;
				} else if (elementName.equals("listOfFunctionTerms")) {
					ListOf<FunctionTerm> listOfFunctionTerms = transition.getListOfFunctionTerms();
					groupList = QualList.listOfFunctionTerms;
					return listOfFunctionTerms;
				} else {
					logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
					return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
				}
				
			} else if (contextObject instanceof ListOf<?>) {
				ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
				
				// keep order of elements for later validation
				AbstractReaderWriter.storeElementsOrder(elementName, contextObject);

				if (elementName.equals("transition") && groupList.equals(QualList.listOfTransitions)) {
					Model model = (Model) listOf.getParentSBMLObject();
					QualModelPlugin extendeModel = (QualModelPlugin) model.getExtension(QualConstants.namespaceURI);
					
					Transition transition = new Transition();
					extendeModel.addTransition(transition);
					return transition;

				} else if ((elementName.equals("qualitativeSpecies"))
						&& groupList.equals(QualList.listOfQualitativeSpecies)) {
					Model model = (Model) listOf.getParentSBMLObject();
					QualModelPlugin extendeModel = (QualModelPlugin) model.getExtension(QualConstants.namespaceURI);

					QualitativeSpecies qualSpecies = new QualitativeSpecies();
					extendeModel.addQualitativeSpecies(qualSpecies);
					return qualSpecies;

				} else if (elementName.equals("input") && groupList.equals(QualList.listOfInputs)) {
					Transition transition = (Transition) listOf.getParentSBMLObject();

					Input input = new Input();
					transition.addInput(input);
					return input;

				} else if (elementName.equals("output") && groupList.equals(QualList.listOfOutputs)) {
					Transition transition = (Transition) listOf.getParentSBMLObject();

					Output output = new Output();
					transition.addOutput(output);
					return output;

				} else if (elementName.equals("functionTerm") && groupList.equals(QualList.listOfFunctionTerms)) {
					Transition transition = (Transition) listOf.getParentSBMLObject();

					FunctionTerm functionTerm = new FunctionTerm();
					transition.addFunctionTerm(functionTerm);
					return functionTerm;

				} else if (elementName.equals("defaultTerm") && groupList.equals(QualList.listOfFunctionTerms)) {
					Transition transition = (Transition) listOf.getParentSBMLObject();

					FunctionTerm functionTerm = new FunctionTerm();
					functionTerm.setDefaultTerm(true);
					transition.addFunctionTerm(functionTerm);
					return functionTerm;
					
				} else {
					logger.warn(MessageFormat.format(bundle.getString("SBMLCoreParser.unknownElement"), elementName));
					return AbstractReaderWriter.processUnknownElement(elementName, uri, prefix, contextObject);
				}
			}
			return contextObject;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.sbml.jsbml.xml.parsers.AbstractReaderWriter#writeElement(org.sbml.jsbml.
		 * xml.stax.SBMLObjectForXML, java.lang.Object)
		 */
		@Override
		public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {
			super.writeElement(xmlObject, sbmlElementToWrite);

			if (logger.isDebugEnabled()) {
				logger.debug("writeElement: " + sbmlElementToWrite.getClass().getSimpleName());
			}

			if ((sbmlElementToWrite instanceof FunctionTerm) && ((FunctionTerm) sbmlElementToWrite).isDefaultTerm()) {
				xmlObject.setName("defaultTerm");
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
		 */
		@Override
		public String getShortLabel() {
			return QualConstants.shortLabel;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
		 */
		@Override
		public String getNamespaceURI() {
			return QualConstants.namespaceURI;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(int, int, int)
		 */
		@Override
		public String getNamespaceFor(int level, int version, int packageVersion) {

			if (level == 3 && version == 1 && packageVersion == 1) {
				return QualConstants.namespaceURI_L3V1V1;
			}

			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
		 */
		@Override
		public List<String> getNamespaces() {
			return QualConstants.namespaces;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageNamespaces()
		 */
		@Override
		public List<String> getPackageNamespaces() {
			return getNamespaces();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageName()
		 */
		@Override
		public String getPackageName() {
			return getShortLabel();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
		 */
		@Override
		public boolean isRequired() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.sbml.jsbml.xml.parsers.PackageParser#createPluginFor(org.sbml.jsbml.
		 * SBase)
		 */
		@Override
		public SBasePlugin createPluginFor(SBase sbase) {

			if (sbase != null) {
				if (sbase instanceof Model) {
					return new QualModelPlugin((Model) sbase);
				}
			}

			return null;
		}

		@Override
		public ASTNodePlugin createPluginFor(ASTNode astNode) {
			// This package does not extends ASTNode
			return null;
		}

	}
