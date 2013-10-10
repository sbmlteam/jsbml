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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.xml.parsers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCList;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FluxBound;
import org.sbml.jsbml.ext.fbc.FluxObjective;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * This class is used to parse the fbc extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * {@code http://www.sbml.org/sbml/level3/version1/fbc/version1}. This parser is
 * able to read and write elements of the fbc package (extends
 * {@link AbstractReaderWriter}).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class FBCParser extends AbstractReaderWriter {

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
	 */
	public String getNamespaceURI() {
		return FBCConstants.namespaceURI;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
	 */
	public String getShortLabel() {
	  return FBCConstants.shortLabel;
	}

	/**
	 * The {@link FBCList} enum which represents the name of the list this parser
	 * is currently reading.
	 * 
	 */
	private FBCList groupList = FBCList.none;
	
	private Logger logger = Logger.getLogger(FBCParser.class);

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

	  if (logger.isDebugEnabled()) {
	    logger.debug("getListOfSBMLElementsToWrite: " + sbase.getClass().getCanonicalName());
	  }

		List<Object> listOfElementsToWrite = new ArrayList<Object>();

		if (sbase instanceof SBMLDocument) {
			// nothing to do
			// TODO: the 'required' attribute is written even if there is no plugin class for the SBMLDocument, so I am not totally sure how this is done.
		} 
		else if (sbase instanceof Model) {
			FBCModelPlugin modelGE = (FBCModelPlugin) ((Model) sbase).getExtension(FBCConstants.namespaceURI);

			if (modelGE != null) {
			  if (modelGE.isSetListOfFluxBounds()) {
			    listOfElementsToWrite.add(modelGE.getListOfFluxBounds());
			    groupList = FBCList.listOfFluxBounds;
			  }
			  if (modelGE.isSetListOfObjectives()) {
			    listOfElementsToWrite.add(modelGE.getListOfObjectives());
			  }
			}
		} 
		else if (sbase instanceof TreeNode) {
			Enumeration<TreeNode> children = ((TreeNode) sbase).children();
			
			while (children.hasMoreElements()) {
				listOfElementsToWrite.add(children.nextElement());
			}
		}
		
		if (listOfElementsToWrite.isEmpty()) {
			listOfElementsToWrite = null;
		} else {
			logger.debug("getListOfSBMLElementsToWrite size = " + listOfElementsToWrite.size());
		}

		return listOfElementsToWrite;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
	 */
	@Override
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		if (elementName.equals(FBCList.listOfFluxBounds.name())
				|| elementName.equals(FBCList.listOfObjectives.name())) {
			this.groupList = FBCList.none;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
	 */
	// Create the proper object and link it to his parent.
	@SuppressWarnings("unchecked")
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) 
	{
		if (contextObject instanceof Model) {
			Model model = (Model) contextObject;
			FBCModelPlugin fbcModel = null;
			
			if (model.getExtension(FBCConstants.namespaceURI) != null) {
				fbcModel = (FBCModelPlugin) model.getExtension(FBCConstants.namespaceURI);
			} else {
				fbcModel = new FBCModelPlugin(model);
				model.addExtension(FBCConstants.namespaceURI, fbcModel);
			}

			if (elementName.equals(FBCList.listOfFluxBounds.name())) {
					
				ListOf<FluxBound> listOfFluxBounds = fbcModel.getListOfFluxBounds();
				listOfFluxBounds.setSBaseListType(ListOf.Type.other);
				listOfFluxBounds.addNamespace(FBCConstants.namespaceURI);
				model.registerChild(listOfFluxBounds);
				this.groupList = FBCList.listOfFluxBounds;
				return listOfFluxBounds;
			} 
			else if (elementName.equals(FBCList.listOfObjectives.name())) {

				ListOf<Objective> listOfObjectives = fbcModel.getListOfObjectives();
				listOfObjectives.setSBaseListType(ListOf.Type.other);
				listOfObjectives.addNamespace(FBCConstants.namespaceURI);
				model.registerChild(listOfObjectives);
				this.groupList = FBCList.listOfObjectives;
				return listOfObjectives;
			}
		} else if (contextObject instanceof Objective) {
			Objective objective = (Objective) contextObject;
			// TODO: Where is this name defined?
			if (elementName.equals("listOfFluxes")) {				
				ListOf<FluxObjective> listOfFluxObjectives = objective.getListOfFluxObjectives();
				listOfFluxObjectives.setSBaseListType(ListOf.Type.other);
				listOfFluxObjectives.addNamespace(FBCConstants.namespaceURI);
				objective.registerChild(listOfFluxObjectives);
				this.groupList = FBCList.listOfFluxObjectives;
				return listOfFluxObjectives;
			}
		}

		else if (contextObject instanceof ListOf<?>) {
			ListOf<SBase> listOf = (ListOf<SBase>) contextObject;

			if (elementName.equals("fluxBound")
					&& this.groupList.equals(FBCList.listOfFluxBounds)) {
				Model model = (Model) listOf.getParentSBMLObject();
				FBCModelPlugin extendeModel = (FBCModelPlugin) model.getExtension(FBCConstants.namespaceURI); 
				
				FluxBound fluxBound = new FluxBound();
				fluxBound.addNamespace(FBCConstants.namespaceURI);
				extendeModel.addFluxBound(fluxBound);
				return fluxBound;
				
			} else if (elementName.equals("objective")
					&& this.groupList.equals(FBCList.listOfObjectives)) {
				Model model = (Model) listOf.getParentSBMLObject();
				FBCModelPlugin extendeModel = (FBCModelPlugin) model.getExtension(FBCConstants.namespaceURI); 

				Objective objective = new Objective();
				objective.addNamespace(FBCConstants.namespaceURI);
				extendeModel.addObjective(objective);

				return objective;
			} else if (elementName.equals("fluxObjective")
					&& this.groupList.equals(FBCList.listOfFluxObjectives)) {
				Objective objective = (Objective) listOf.getParentSBMLObject();

				FluxObjective fluxObjective = new FluxObjective();
				fluxObjective.addNamespace(FBCConstants.namespaceURI);
				objective.addFluxObjective(fluxObjective);

				return fluxObjective;
			}

		}
		return contextObject;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	@Override
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {

		logger.debug("FBCParser: writeElement");

		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;

			if (!xmlObject.isSetName()) {
				if (sbase instanceof ListOf<?>) {
					ListOf<?> listOf = (ListOf<?>) sbase;
					
					if (listOf.size() > 0) {
						if (listOf.get(0) instanceof FluxBound) {
							xmlObject.setName(FBCList.listOfFluxBounds.toString());
						} else if (listOf.get(0) instanceof Objective) {
							xmlObject.setName(FBCList.listOfObjectives.toString());
						} else if (listOf.get(0) instanceof FluxObjective) {
							xmlObject.setName(FBCList.listOfFluxObjectives.toString());
						}
					}
				} else {
					xmlObject.setName(sbase.getElementName());
				}
			}
//			if (!xmlObject.isSetPrefix()) {
//				xmlObject.setPrefix(FBCConstants.shortLabel);
//			}
//			xmlObject.setNamespace(FBCConstants.namespaceURI);
		}

	}

}
