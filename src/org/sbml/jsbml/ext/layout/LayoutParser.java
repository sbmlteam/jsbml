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

package org.sbml.jsbml.ext.layout;

import java.util.ArrayList;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.parsers.ReadingParser;
import org.sbml.jsbml.xml.parsers.WritingParser;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * This class is used to parse the layout extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/layout/version1". This parser is
 * able to read and write elements of the layout package (implements
 * ReadingParser and WritingParser).
 * 
 * @author rodrigue
 * 
 */
public class LayoutParser implements ReadingParser, WritingParser {

	/**
	 * The namespace URI of this parser.
	 */
	private static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/layout/version1";

	/**
	 * The layoutList enum which represents the name of the list this parser is
	 * currently reading.
	 * 
	 */
	private LayoutList groupList = LayoutList.none;

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processAttribute(String
	 *      elementName, String attributeName, String value, String prefix,
	 *      boolean isLastAttribute, Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {

		boolean isAttributeRead = false;

		if (contextObject instanceof SBase) {
			SBase sbase = (SBase) contextObject;
			try {
				isAttributeRead = sbase.readAttribute(attributeName, prefix,
						value);
			} catch (Throwable exc) {
				System.err.println(exc.getMessage());
			}
		} else if (contextObject instanceof Annotation) {
			Annotation annotation = (Annotation) contextObject;
			isAttributeRead = annotation.readAttribute(attributeName, prefix,
					value);
		}

		if (!isAttributeRead) {
			// TODO : throw new SBMLException ("The attribute " + attributeName
			// + " on the element " + elementName +
			// "is not part of the SBML specifications");
		}
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(String
	 *      elementName, String prefix, boolean hasAttributes, boolean
	 *      hasNamespaces, Object contextObject)
	 */
	// Create the proper object and link it to his parent.
	@SuppressWarnings("unchecked")
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
		if (contextObject instanceof Model) {
			Model model = (Model) contextObject;
			if (elementName.equals("listOfLayouts")) {
				ListOf<Layout> listOfLayouts = new ListOf<Layout>();
				listOfLayouts.setSBaseListType(ListOf.Type.other);
				listOfLayouts.addNamespace(namespaceURI);
				this.groupList = LayoutList.listOfLayouts;

				ExtendedLayoutModel layoutModel = new ExtendedLayoutModel(model);
				layoutModel.setListOfLayouts(listOfLayouts);
				layoutModel.addNamespace(namespaceURI);
				model.addExtension(LayoutParser.namespaceURI, layoutModel);

				return listOfLayouts;
			}
		} else if (contextObject instanceof Layout) {
			Layout layout = (Layout) contextObject;
			if (elementName.equals("listOfSpeciesGlyphs")) {
				ListOf<SpeciesGlyph> listOfSpeciesGlyphs = new ListOf<SpeciesGlyph>();
				listOfSpeciesGlyphs.setSBaseListType(ListOf.Type.other);
				listOfSpeciesGlyphs.addNamespace(namespaceURI);
				this.groupList = LayoutList.listOfSpeciesGlyphs;

				layout.setListOfSpeciesGlyphs(listOfSpeciesGlyphs);

				return listOfSpeciesGlyphs;
			} else {
				// TODO : compartmentGlyph, reactionGlyph, textGlyph
			}
		}

		else if (contextObject instanceof ListOf<?>) {
			ListOf<SBase> listOf = (ListOf<SBase>) contextObject;

			if (elementName.equals("layout")
					&& this.groupList.equals(LayoutList.listOfLayouts)) {
				ExtendedLayoutModel extendeModel = (ExtendedLayoutModel) listOf
						.getParentSBMLObject();
				Layout layout = new Layout();
				layout.addNamespace(namespaceURI);
				extendeModel.addLayout(layout);

				return layout;
			} else if (elementName.equals("speciesGlyph")
					&& this.groupList.equals(LayoutList.listOfSpeciesGlyphs)) {

				Layout layout = (Layout) listOf.getParentSBMLObject();
				SpeciesGlyph speciesGlyph = new SpeciesGlyph();
				speciesGlyph.addNamespace(namespaceURI);
				layout.addSpeciesGlyph(speciesGlyph);

				return speciesGlyph;
			}

		}
		return contextObject;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.stax.ReadingParser#processCharactersOf(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : the basic Groups elements don't have any text. SBML syntax
		// error, throw an exception, log en error ?

	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(String
	 *      elementName, String prefix, boolean isNested, Object contextObject)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		if (elementName.equals("listOfLayouts")
				|| elementName.equals("listOfSpeciesGlyphs")) {
			this.groupList = LayoutList.none;
		}
		
		return true;
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndDocument(SBMLDocument
	 *      sbmlDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// Do some checking ??
	}

	/**
	 * 
	 * @return the namespaceURI of this parser.
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object
	 * sbase)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {

		System.out.println("GroupsParser : getListOfSBMLElementsToWrite\n");

		ArrayList<Object> listOfElementsToWrite = new ArrayList<Object>();

		if (sbase instanceof SBase) {
			if (sbase instanceof ExtendedLayoutModel) {

				ExtendedLayoutModel model = (ExtendedLayoutModel) sbase;

				if (model.isSetListOfLayouts()) {
					listOfElementsToWrite.add(model.getListOfLayouts());
				}
			} else if (sbase instanceof ListOf) {
				ListOf<SBase> listOf = (ListOf<SBase>) sbase;

				if (!listOf.isEmpty()) {
					listOfElementsToWrite = new ArrayList<Object>();
					for (int i = 0; i < listOf.size(); i++) {
						SBase element = listOf.get(i);

						if (element != null) {
							listOfElementsToWrite.add(element);
						}
					}
				}
			} else if (sbase instanceof Layout) {
				Layout layout = (Layout) sbase;

				if (layout.isSetListOfSpeciesGlyphs()) {
					listOfElementsToWrite.add(layout.getListOfSpeciesGlyphs());
				}
			}
		}

		if (listOfElementsToWrite.isEmpty()) {
			listOfElementsToWrite = null;
		}

		return listOfElementsToWrite;
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(SBMLObjectForXML
	 *      xmlObject, Object sbmlElementToWrite)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {

		System.out.println("GroupsParser : writeElement");

		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;

			if (!xmlObject.isSetName()) {
				if (sbase instanceof ListOf<?>) {
					xmlObject.setName(LayoutList.listOfLayouts.toString());
				} else {
					xmlObject.setName(sbase.getElementName());
				}
			}
			if (!xmlObject.isSetPrefix()) {
				xmlObject.setPrefix("groups");
			}
			xmlObject.setNamespace(namespaceURI);

		}

	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeAttributes(SBMLObjectForXML
	 *      xmlObject, Object sbmlElementToWrite)
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;

			xmlObject.addAttributes(sbase.writeXMLAttributes());
		}

	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeCharacters(SBMLObjectForXML
	 *      xmlObject, Object sbmlElementToWrite)
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO : Group elements do not have any characters in the XML file.
		// what to do?

	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeNamespaces(SBMLObjectForXML
	 *      xmlObject, Object sbmlElementToWrite)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		if (sbmlElementToWrite instanceof SBase) {
			// SBase sbase = (SBase) sbmlElementToWrite;

			xmlObject.setPrefix("groups");
		}

	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processNamespace(String
	 *      elementName, String URI, String prefix, String localName, boolean
	 *      hasAttributes, boolean isLastNamespace, Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		// Nothing to be done

	}

}

/**
 * 
 * 
 *
 */
enum LayoutList {
	/**
	 * 
	 */
	none,
	/**
	 * 
	 */
	listOfLayouts,
	/**
	 * 
	 */
	listOfSpeciesGlyphs,
	/**
	 * 
	 */
	listOfCompartmentGlyphs,
	/**
	 * 
	 */
	listOfCurves
};
