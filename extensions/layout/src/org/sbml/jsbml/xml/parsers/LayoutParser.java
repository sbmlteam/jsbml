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

package org.sbml.jsbml.xml.parsers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.ExtendedLayoutModel;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.xml.parsers.ReadingParser;
import org.sbml.jsbml.xml.parsers.WritingParser;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 0.8
 */
enum LayoutList {
	/**
	 * 
	 */
	listOfCompartmentGlyphs,
	/**
	 * 
	 */
	listOfCurves,
	/**
	 * 
	 */
	listOfLayouts,
	/**
	 * 
	 */
	listOfReactionGlyphs,
	/**
	 * 
	 */
	listOfSpeciesGlyphs,
	/**
	 * 
	 */
	listOfSpeciesReferenceGlyphs,
	/**
	 * 
	 */
	listOfTextGlyphs,
	/**
	 * 
	 */
	none;
}

/**
 * This class is used to parse the layout extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/layout/version1". This parser is
 * able to read and write elements of the layout package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 * 
 */
public class LayoutParser implements ReadingParser, WritingParser {

	/**
	 * The namespace URI of this parser.
	 */
	private static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/layout/version1";

	/**
	 * 
	 * @return the namespaceURI of this parser.
	 */
	public static String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * The layoutList enum which represents the name of the list this parser is
	 * currently reading.
	 * 
	 */
	private LayoutList groupList = LayoutList.none;

	/**
	 * Log4j logger
	 */
	private Logger log4jLogger = Logger.getLogger(LayoutParser.class);

	/**
	 * This map contains all the relationships XML element name <=> matching
	 * java class.
	 */
	private HashMap<String, Class<? extends Object>> sbmlLayoutElements;
	
	/**
	 * 
	 */
	public LayoutParser() {
		super();
		sbmlLayoutElements = new HashMap<String, Class<? extends Object>>();
		JSBML.loadClasses(
				"org/sbml/jsbml/resources/cfg/SBMLLayoutElements.xml",
				sbmlLayoutElements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object
	 * sbase)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

		log4jLogger.debug(String.format("%s : getListOfSBMLElementsToWrite\n", getClass().getSimpleName()));

		List<Object> listOfElementsToWrite = new LinkedList<Object>();

		if (sbase instanceof SBase) {
			if (sbase instanceof ExtendedLayoutModel) {

				ExtendedLayoutModel model = (ExtendedLayoutModel) sbase;

				if (model.isSetListOfLayouts()) {
					listOfElementsToWrite.add(model.getListOfLayouts());
				}
			} else if (sbase instanceof ListOf) {
				ListOf<SBase> listOf = (ListOf<SBase>) sbase;

				if (!listOf.isEmpty()) {
					listOfElementsToWrite = new LinkedList<Object>();
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.Object)
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
				log4jLogger.error(exc.getLocalizedMessage(), exc);
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
			log4jLogger.debug(String.format(
									"atribute not read: elementName: %s\tattributeName: %s\t value: %s\n",
									elementName, attributeName, value));
		}
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndDocument(org.sbml.jsbml.SBMLDocument)
	 */
	public void processEndDocument(SBMLDocument sbmlDocument) {
		// Do some checking ??
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processEndElement(java.lang.String, java.lang.String, boolean, java.lang.Object)
	 */
	public boolean processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		if (elementName.equals("listOfLayouts")
				|| elementName.equals("listOfSpeciesGlyphs")
				|| elementName.equals("listOfReactionGlyphs")
				|| elementName.equals("listOfSpeciesReferenceGlyphs")
				|| elementName.equals("listOfCurveSegments")) {
			this.groupList = LayoutList.none;
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processNamespace(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
	 */
	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		// Nothing to be done

	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(java.lang.String, java.lang.String, boolean, boolean, java.lang.Object)
	 */
	// Create the proper object and link it to his parent.
	@SuppressWarnings("unchecked")
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {
		if (sbmlLayoutElements.containsKey(elementName)) {
			try {
				Object newContextObject = sbmlLayoutElements.get(elementName)
						.newInstance();
				if (contextObject instanceof SBase) {					
					setLevelAndVersionFor(newContextObject,
							(SBase) contextObject);
				}
				if (contextObject instanceof Model) {					
					Model model = (Model) contextObject;
					if (elementName.equals("listOfLayouts")) {
						ListOf<Layout> listOfLayouts = (ListOf<Layout>) newContextObject;
						listOfLayouts.setSBaseListType(ListOf.Type.other);
						listOfLayouts.addNamespace(namespaceURI);
						this.groupList = LayoutList.listOfLayouts;

						ExtendedLayoutModel layoutModel = new ExtendedLayoutModel();
						layoutModel.setListOfLayouts(listOfLayouts);
						// layoutModel.addNamespace(namespaceURI);
						model.addExtension(LayoutParser.namespaceURI,
								layoutModel);
						// TODO : check what to do for the parent of the listOfLayout !!
						listOfLayouts.setParentSBML(model);
						return listOfLayouts;
					} else {
						log4jLogger.warn(String.format("Element %s not recognized!", elementName));
					}
				}

				else if (contextObject instanceof ListOf<?>) {
					ListOf<?> listOf = (ListOf<?>) contextObject;
					if (elementName.equals("layout") && this.groupList.equals(LayoutList.listOfLayouts)) {
						ListOf<Layout> listOfLayouts = (ListOf<Layout>) listOf;
						Layout layout = (Layout) newContextObject;
						layout.addNamespace(namespaceURI);
						listOfLayouts.add(layout);
						return layout;
					} else if (elementName.equals("speciesGlyph")
							&& this.groupList
									.equals(LayoutList.listOfSpeciesGlyphs)) {
						ListOf<SpeciesGlyph> listOfSpeciesGlyphs = (ListOf<SpeciesGlyph>) listOf;
						SpeciesGlyph speicesGlyph = (SpeciesGlyph) newContextObject;
						listOfSpeciesGlyphs.add(speicesGlyph);
						return speicesGlyph;
					}else if(elementName.equals("reactionGlyph") /*&& this.groupList.equals(LayoutList.listOfReactionGlyphs)*/)
					{
						ListOf<ReactionGlyph> listOfReactionGlyphs = (ListOf<ReactionGlyph>)listOf;
						ReactionGlyph reactionGlyph = (ReactionGlyph) newContextObject;
						listOfReactionGlyphs.add(reactionGlyph);
						return reactionGlyph;
					}
					else if(elementName.equals("speciesReferenceGlyph") /*&& this.groupList.equals(LayoutList.listOfSpeciesReferenceGlyphs)*/)
					{
						ListOf<SpeciesReferenceGlyph> listOfSpeciesReferenceGlyph = (ListOf<SpeciesReferenceGlyph>) listOf;
						SpeciesReferenceGlyph speicesReferenceGlyph = (SpeciesReferenceGlyph) newContextObject;
						listOfSpeciesReferenceGlyph.add(speicesReferenceGlyph);
						return speicesReferenceGlyph;
					}
					else if(elementName.equals("lineSegment") /*&& this.groupList.equals(LayoutList.listOfCurves)*/)
					{
						ListOf<LineSegment> listOfLineSegments = (ListOf<LineSegment>)listOf;
						LineSegment	lineSegment = (LineSegment)newContextObject;
						listOfLineSegments.add(lineSegment);
						return lineSegment;
						
					}
					else if(elementName.equals("cubicBezier") /*&& this.groupList.equals(LayoutList.listOfCurves)*/)
					{
						ListOf<LineSegment> listOfLineSegments = (ListOf<LineSegment>)listOf;
						CubicBezier	cubicBezier = (CubicBezier)newContextObject;
						listOfLineSegments.add(cubicBezier);
						return cubicBezier;						
					}
					else if(elementName.equals("textGlyph"))
					{
						ListOf<TextGlyph> listOfTextGlyphs = (ListOf<TextGlyph>)listOf;
						TextGlyph textGlyph = (TextGlyph) newContextObject;
						listOfTextGlyphs.add(textGlyph);
						return textGlyph;
					}
				} else if (contextObject instanceof BoundingBox) {
					BoundingBox boundingBox = (BoundingBox) contextObject;
					if (elementName.equals("point") && this.groupList.equals(LayoutList.listOfSpeciesGlyphs)) {
						Point point = (Point) newContextObject;
						point.addNamespace(namespaceURI);
						boundingBox.setPoint(point);
						return point;
					}
					else if(elementName.equals("dimensions") && this.groupList.equals(LayoutList.listOfSpeciesGlyphs)){
						Dimensions dimensions = (Dimensions) newContextObject;
						dimensions.addNamespace(namespaceURI);
						boundingBox.setDimensions(dimensions);
						return dimensions;
					}
				} else if (contextObject instanceof Layout) {
					Layout layout = (Layout) contextObject;
					if (elementName.equals("dimensions")
							&& this.groupList.equals(LayoutList.listOfLayouts)) {
						Dimensions dimensions = (Dimensions) newContextObject;
						dimensions.addNamespace(namespaceURI);
						layout.setDimensions(dimensions);
						return dimensions;
					} else if (elementName.equals("listOfSpeciesGlyphs")
							&& this.groupList.equals(LayoutList.listOfLayouts)) {
						ListOf<SpeciesGlyph> listofSpeciesGlyph = (ListOf<SpeciesGlyph>) newContextObject;
						layout.setListOfSpeciesGlyphs(listofSpeciesGlyph);
						this.groupList = LayoutList.listOfSpeciesGlyphs;
						return listofSpeciesGlyph;
					} 
					else if(elementName.equals("listOfReactionGlyphs") && this.groupList.equals(LayoutList.listOfSpeciesGlyphs))
					{
						ListOf<ReactionGlyph> listOfReactionGlyphs = (ListOf<ReactionGlyph>) newContextObject;
						layout.setListOfReactionGlyphs(listOfReactionGlyphs);
						this.groupList = LayoutList.listOfReactionGlyphs;
						return listOfReactionGlyphs;
					}
					else if(elementName.equals("listOfTextGlyphs"))
					{
						ListOf<TextGlyph> listOfTextGlyphs = (ListOf<TextGlyph>) newContextObject;
						layout.setListOfTextGlyphs(listOfTextGlyphs);
						this.groupList = LayoutList.listOfTextGlyphs;
						return listOfTextGlyphs;
					}
				} 
				else if (contextObject instanceof SpeciesGlyph) {
					SpeciesGlyph speciesGlyph = (SpeciesGlyph) contextObject;
					if(elementName.equals("boundingBox") && this.groupList.equals(LayoutList.listOfSpeciesGlyphs))
					{
						BoundingBox boundingBox = (BoundingBox) newContextObject;
						boundingBox.addNamespace(namespaceURI);
						speciesGlyph.setBoundingBox(boundingBox);
						return boundingBox;
					}					
				} else if (contextObject instanceof ReactionGlyph){
					ReactionGlyph reactionGlyph = (ReactionGlyph) contextObject;
					///TODO unsure why the curve is not parsing
					if(elementName.equals("curve")  )
					{
						Curve curve = (Curve) newContextObject;
						curve.addNamespace(namespaceURI);
						reactionGlyph.setCurve(curve);
						return curve;
					}
					else if(elementName.equals("listOfSpeciesReferenceGlyphs")  /*&& this.groupList.equals(LayoutList.listOfReactionGlyphs)*/)
					{
						this.groupList = LayoutList.listOfSpeciesReferenceGlyphs;
						ListOf<SpeciesReferenceGlyph> listOfSpeciesReferencesGlyph = (ListOf<SpeciesReferenceGlyph>) newContextObject;
						listOfSpeciesReferencesGlyph.addNamespace(namespaceURI);
						reactionGlyph.setListOfSpeciesReferencesGlyph(listOfSpeciesReferencesGlyph);
						return listOfSpeciesReferencesGlyph;
					}
					else if(elementName.equals("listOfCurveSegments"))
					{
						Curve curve = new Curve(reactionGlyph.getLevel(), reactionGlyph.getVersion());
						reactionGlyph.setCurve(curve);
						ListOf<LineSegment> listOfCurveSegments = (ListOf<LineSegment>) newContextObject;
						curve.setListOfCurveSegments(listOfCurveSegments);
						return listOfCurveSegments;
					}					
				} else if(contextObject instanceof SpeciesReferenceGlyph){
					SpeciesReferenceGlyph speciesReferenceGlyph = (SpeciesReferenceGlyph) contextObject;
					if(elementName.equals("curve") )
					{
						Curve curve = (Curve) newContextObject;
						curve.addNamespace(namespaceURI);
						speciesReferenceGlyph.setCurve(curve);
						return curve;
					}
					///TODO unsure why the curve is not parsing
					else if(elementName.equals("listOfCurveSegments"))
					{
						log4jLogger.warn("parsing: listOfCurveSegments");
						Curve curve = new Curve(speciesReferenceGlyph.getLevel(), speciesReferenceGlyph.getVersion());
						speciesReferenceGlyph.setCurve(curve);
						ListOf<LineSegment> listOfCurveSegments = (ListOf<LineSegment>) newContextObject;
						curve.setListOfCurveSegments(listOfCurveSegments);
						return listOfCurveSegments;
					}					
				} else if(contextObject instanceof Curve){
					Curve curve = (Curve) contextObject;
					log4jLogger.warn("parsing instaceof Curve");
					if(elementName.equals("listOfCurveSegments") )
					{
						log4jLogger.warn("parsing: listOfCurveSegments");
						ListOf<LineSegment> listOfLineSegments = (ListOf<LineSegment>) newContextObject;
						curve.setListOfCurveSegments(listOfLineSegments);
						return listOfLineSegments;
					}					
				} else if (contextObject instanceof LineSegment)
				{
					LineSegment lineSegment = (LineSegment) contextObject;
					if(elementName.equals("start"))
					{
						Point start = (Point) newContextObject;
						lineSegment.setStart(start);
						return start;
					}
					else if(elementName.equals("end"))
					{
						Point end = (Point) newContextObject;
						lineSegment.setEnd(end);
						return end;
					}
				} else if (contextObject instanceof CubicBezier){
					CubicBezier cubicBezier = (CubicBezier) contextObject;
					if(elementName.equals("start"))
					{
						Point start = (Point) newContextObject;
						cubicBezier.setStart(start);
						return start;
					}
					else if(elementName.equals("end"))
					{
						Point end = (Point) newContextObject;
						cubicBezier.setEnd(end);
						return end;
					}
					else if(elementName.equals("basePoint1"))
					{
						Point basePoint1 = (Point) newContextObject;
						cubicBezier.setBasePoint1(basePoint1);
						return basePoint1;
					}
					else if(elementName.equals("basePoint2")){
						Point basePoint2 = (Point) newContextObject;
						cubicBezier.setBasePoint2(basePoint2);
						return basePoint2;
					}
				} else if (contextObject instanceof TextGlyph)
				{
					TextGlyph textGlyph = (TextGlyph) contextObject;
					if(elementName.equals("boundingBox"))
					{
						BoundingBox boundingBox = (BoundingBox)newContextObject;
						textGlyph.setBoundingBox(boundingBox);
						return boundingBox;
					}
					
				}
				

				else {
					log4jLogger.warn(String.format("Classname: %s", contextObject.getClass().getName()));
					log4jLogger.warn(String.format("Parsing-Error: Element %s is not recognized!", elementName));
				}

			} catch (Exception e) {
				log4jLogger.error(e.getLocalizedMessage(), e);
			}
		}
		return contextObject;
	}

	/**
	 * Sets level and version properties of the new object according to the
	 * value in the model.
	 * 
	 * @param newContextObject
	 * @param parent
	 */
	private void setLevelAndVersionFor(Object newContextObject, SBase parent) {
		if (newContextObject instanceof SBase) {
			SBase sb = (SBase) newContextObject;
			// Level and version will be -1 if not set, so we don't
			// have to check.
			sb.setLevel(parent.getLevel());
			sb.setVersion(parent.getVersion());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeAttributes(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		if (sbmlElementToWrite instanceof SBase) {
			SBase sbase = (SBase) sbmlElementToWrite;

			xmlObject.addAttributes(sbase.writeXMLAttributes());
		}

	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeCharacters(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO : Group elements do not have any characters in the XML file.
		// what to do?

	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {

		log4jLogger.warn(String.format("%s : writeElement", getClass().getSimpleName()));

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
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeNamespaces(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		if (sbmlElementToWrite instanceof SBase) {
			// SBase sbase = (SBase) sbmlElementToWrite;

			xmlObject.setPrefix("groups");
		}

	}


};
