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

package org.sbml.jsbml.sbmlExtensions.layout;

import java.util.ArrayList;

import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.stax.ReadingParser;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;
import org.sbml.jsbml.xml.stax.SBaseListType;
import org.sbml.jsbml.xml.stax.WritingParser;

/**
 * This class is used to parse the layout extension package elements and attributes. The namespaceURI URI
 * of this parser is "http://www.sbml.org/sbml/level3/version1/layout/version1". This parser is able to read and
 * write elements of the layout package (implements ReadingParser and WritingParser).
 * 
 * @author rodrigue
 *
 */
public class LayoutParser implements ReadingParser, WritingParser{
	
	/**
	 * The namespace URI of this parser.
	 */
	private static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/layout/version1";
	
	/**
	 * The layoutList enum which represents the name of the list this parser is currently reading.
	 *
	 */
	private LayoutList groupList = LayoutList.none;

	/**
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processAttribute(String elementName, String attributeName,
	 *		String value, String prefix, boolean isLastAttribute,
	 *		Object contextObject)
	 */
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		boolean isAttributeRead = false;
		
		if (contextObject instanceof SBase){
			SBase sbase = (SBase) contextObject;
			isAttributeRead = sbase.readAttribute(attributeName, prefix, value);
			
		} else if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			isAttributeRead = annotation.readAttribute(attributeName, prefix, value);
		}
		
		if (!isAttributeRead){
			// TODO : throw new SBMLException ("The attribute " + attributeName + " on the element " + elementName + "is not part of the SBML specifications");
		}
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject)
	 */
	// Create the proper object and link it to his parent.
	@SuppressWarnings("unchecked")
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) 
	{
		if (contextObject instanceof Model){
			Model model = (Model) contextObject;
			if (elementName.equals("listOfLayouts")){
				ListOf<Layout> listOfLayouts = new ListOf<Layout>();
				listOfLayouts.setSBaseListType(SBaseListType.other);
				listOfLayouts.addNamespace(namespaceURI);
				this.groupList = LayoutList.listOfLayouts;
				
				ExtendedLayoutModel layoutModel = new ExtendedLayoutModel(model);
				layoutModel.setListOfLayouts(listOfLayouts);
				layoutModel.addNamespace(namespaceURI);
				model.addExtension(LayoutParser.namespaceURI, layoutModel);
				
				return listOfLayouts;
			}
		} else if (contextObject instanceof Layout){
			Layout layout = (Layout) contextObject;
			if (elementName.equals("listOfSpeciesGlyphs")){
				ListOf<SpeciesGlyph> listOfSpeciesGlyphs = new ListOf<SpeciesGlyph>();
				listOfSpeciesGlyphs.setSBaseListType(SBaseListType.other);
				listOfSpeciesGlyphs.addNamespace(namespaceURI);
				this.groupList = LayoutList.listOfSpeciesGlyphs;
				
				layout.setListOfSpeciesGlyphs(listOfSpeciesGlyphs);
				
				return listOfSpeciesGlyphs;
			} else {
				//TODO : compartmentGlyph, reactionGlyph, textGlyph
			}
		}

		else if (contextObject instanceof ListOf<?>){
			ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
			
			if (elementName.equals("layout") && this.groupList.equals(LayoutList.listOfLayouts)){
				ExtendedLayoutModel extendeModel = (ExtendedLayoutModel) listOf.getParentSBMLObject();
				Layout layout = new Layout();
				layout.addNamespace(namespaceURI);
				extendeModel.addLayout(layout);
				
				return layout;
			} else if (elementName.equals("speciesGlyph") && this.groupList.equals(LayoutList.listOfSpeciesGlyphs)){
				
				Layout layout = (Layout) listOf.getParentSBMLObject();
				SpeciesGlyph speciesGlyph = new SpeciesGlyph();
				speciesGlyph.addNamespace(namespaceURI);
				layout.addSpeciesGlyph(speciesGlyph);
				
				return speciesGlyph;
			}
			
		}
		return contextObject;
	}

	@Override
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : the basic Groups elements don't have any text. SBML syntax error, throw an exception, log en error ?
		
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject)
	 */
	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		if (elementName.equals("notes") && contextObject instanceof SBase){
			SBase sbase = (SBase) contextObject;
			sbase.setNotes(sbase.getNotesBuffer().toString());
		}
		
		if (elementName.equals("listOfLayouts") || elementName.equals("listOfSpeciesGlyphs")){
			this.groupList = LayoutList.none;
		}
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processEndDocument(SBMLDocument sbmlDocument)
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

	/* (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {
		
		System.out.println("GroupsParser : getListOfSBMLElementsToWrite\n");
		
		ArrayList<Object> listOfElementsToWrite = new ArrayList<Object>();
		
		if (sbase instanceof SBase){
			if (sbase instanceof ExtendedLayoutModel){
				
				ExtendedLayoutModel model = (ExtendedLayoutModel) sbase;

				if (model.isSetListOfLayouts()){
					listOfElementsToWrite.add(model.getListOfLayouts());
				}
			}
			else if (sbase instanceof ListOf){
				ListOf<SBase> listOf = (ListOf<SBase>) sbase;
				
				if (!listOf.isEmpty()){
					listOfElementsToWrite = new ArrayList<Object>();
					for (int i = 0; i < listOf.size(); i++){
						SBase element = listOf.get(i);
						
						if (element != null){
							listOfElementsToWrite.add(element);
						}
					}
				}
			}
			else if (sbase instanceof Layout){
				Layout layout = (Layout) sbase;

				if (layout.isSetListOfSpeciesGlyphs()){
					listOfElementsToWrite.add(layout.getListOfSpeciesGlyphs());
				}
			}
		}
		
		if (listOfElementsToWrite.isEmpty()){
			listOfElementsToWrite = null;
		}

		return listOfElementsToWrite;
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite)
	 */
	public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {
		
		System.out.println("GroupsParser : writeElement");
		
		if (sbmlElementToWrite instanceof SBase){
			SBase sbase = (SBase) sbmlElementToWrite;

			if (!xmlObject.isSetName()){
				if (sbase instanceof ListOf<?>) {
					xmlObject.setName(LayoutList.listOfLayouts.toString());
				} else {
					xmlObject.setName(sbase.getElementName());
				}
			}
			if (!xmlObject.isSetPrefix()){
				xmlObject.setPrefix("groups");
			}
			xmlObject.setNamespace(namespaceURI);

		}
		
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite)
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		if (sbmlElementToWrite instanceof SBase){
			SBase sbase = (SBase) sbmlElementToWrite;
			
			xmlObject.addAttributes(sbase.writeXMLAttributes());
		}
		
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite)
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		// TODO : Group elements do not have any characters in the XML file. what to do?
		
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		if (sbmlElementToWrite instanceof SBase){
			//SBase sbase = (SBase) sbmlElementToWrite;
			
			xmlObject.setPrefix("groups");
		}
		
	}

	/**
	 * 
	 * @see org.sbml.jsbml.xml.ReadingParser#processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) 
	{
		// Nothing to be done
		
	}


}


enum LayoutList {none, listOfLayouts, listOfSpeciesGlyphs, listOfCompartmentGlyphs, listOfCurves};

