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

import static org.sbml.jsbml.ext.layout.LayoutConstant.basePoint1;
import static org.sbml.jsbml.ext.layout.LayoutConstant.basePoint2;
import static org.sbml.jsbml.ext.layout.LayoutConstant.boundingBox;
import static org.sbml.jsbml.ext.layout.LayoutConstant.compartmentGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstant.curve;
import static org.sbml.jsbml.ext.layout.LayoutConstant.curveSegment;
import static org.sbml.jsbml.ext.layout.LayoutConstant.dimensions;
import static org.sbml.jsbml.ext.layout.LayoutConstant.end;
import static org.sbml.jsbml.ext.layout.LayoutConstant.layout;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfAdditionalGraphicalObjects;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfCompartmentGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfCurveSegments;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfLayouts;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfReactionGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfSpeciesGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfSpeciesReferenceGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstant.listOfTextGlyphs;
import static org.sbml.jsbml.ext.layout.LayoutConstant.namespaceURI;
import static org.sbml.jsbml.ext.layout.LayoutConstant.position;
import static org.sbml.jsbml.ext.layout.LayoutConstant.reactionGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstant.speciesGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstant.speciesReferenceGlyph;
import static org.sbml.jsbml.ext.layout.LayoutConstant.start;
import static org.sbml.jsbml.ext.layout.LayoutConstant.textGlyph;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.BasePoint1;
import org.sbml.jsbml.ext.layout.BasePoint2;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.End;
import org.sbml.jsbml.ext.layout.ExtendedLayoutModel;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstant;
import org.sbml.jsbml.ext.layout.Position;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.Start;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * This class is used to parse the layout extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/layout/version1". This parser is
 * able to read and write elements of the layout package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class L3LayoutParser extends AbstractReaderWriter {

	
	
	/**
	 * 
	 * @return the namespaceURI of this parser.
	 */
	public String getNamespaceURI() {
		return LayoutConstant.namespaceURI;
	}
	
	public String getShortLabel() {
		return LayoutConstant.shortLabel;
	}

	private Logger logger = Logger.getLogger(L3LayoutParser.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
	 * 
	 */
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {

		logger.debug("getListOfSBMLElementsToWrite : " + sbase.getClass().getCanonicalName());
		
		ArrayList<Object> listOfElementsToWrite = new ArrayList<Object>();
		
		if (sbase instanceof Model) {
			SBasePlugin modelPlugin = (SBasePlugin) ((Model) sbase).getExtension(getNamespaceURI());
			
			if (modelPlugin != null) {
				listOfElementsToWrite = super.getListOfSBMLElementsToWrite(modelPlugin);
			}
		} else {
			listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbase);
		}

		return listOfElementsToWrite;
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
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) 
	{
		if (contextObject instanceof Model) {
			Model model = (Model) contextObject;
			
			if (elementName.equals(listOfLayouts)) {

				ExtendedLayoutModel layoutModel = new ExtendedLayoutModel(model);
				model.addExtension(namespaceURI, layoutModel);

				return layoutModel.getListOfLayouts();
			}
		}
		else if (contextObject instanceof Layout) {
			Layout layout = (Layout) contextObject;
			SBase newElement = null; 
			
			if (elementName.equals(listOfCompartmentGlyphs)) {

				newElement = layout.getListOfCompartmentGlyphs();
			} 
			else if (elementName.equals(listOfSpeciesGlyphs)) {

				newElement = layout.getListOfSpeciesGlyphs();
			}
			else if (elementName.equals(listOfReactionGlyphs)) {

				newElement = layout.getListOfReactionGlyphs();
			}
			else if (elementName.equals(listOfTextGlyphs)) {

				newElement = layout.getListOfTextGlyphs();
			}
			else if (elementName.equals(listOfAdditionalGraphicalObjects)) {

				newElement = layout.getListOfAdditionalGraphicalObjects();
			}
			else if (elementName.equals(dimensions)) {
				Dimensions dimension = new Dimensions();
				layout.setDimensions(dimension);

				newElement = dimension;
			} 
			
			if (newElement != null) {
				layout.registerChild(newElement);
				return newElement;
			}
		} 
		else if (contextObject instanceof GraphicalObject) {
			GraphicalObject graphicalObject = (GraphicalObject) contextObject;
			
			if (elementName.equals(boundingBox)) {
				BoundingBox bbox = new BoundingBox();
				graphicalObject.setBoundingBox(bbox);
				
				return bbox;
			} 

			if (graphicalObject instanceof ReactionGlyph) {
				ReactionGlyph reactionGlyph = (ReactionGlyph) graphicalObject;
				
				if (elementName.equals(curve)) {
					Curve curve = new Curve();
					reactionGlyph.setCurve(curve);
					
					return curve;
				}
				else if (elementName.equals(listOfSpeciesReferenceGlyphs)) {
					ListOf<SpeciesReferenceGlyph> list = reactionGlyph.getListOfSpeciesReferenceGlyphs();
					reactionGlyph.registerChild(list);
					return list;
				}
				
			}
		}
		else if (contextObject instanceof SpeciesReferenceGlyph) {
			SpeciesReferenceGlyph speciesRefGlyph = (SpeciesReferenceGlyph) contextObject;
			
			if (elementName.equals(curve)) {
				Curve curve = new Curve();
				speciesRefGlyph.setCurve(curve);
				
				return curve;
			}

		}
		else if (contextObject instanceof BoundingBox) {
			BoundingBox bbox = (BoundingBox) contextObject;

			if (elementName.equals(position)) {
				Position position = new Position();
				bbox.setPosition(position);

				return position;
			} 
			else if (elementName.equals(dimensions)) {
				Dimensions dimension = new Dimensions();
				bbox.setDimensions(dimension);

				return dimension;
			} 
		}
		else if (contextObject instanceof Curve) {
			Curve curve = (Curve) contextObject;
			SBase newElement = null; 
			
			if (elementName.equals(listOfCurveSegments)) {

				newElement = curve.getListOfCurveSegments();
			}
			
			if (newElement != null) {
				curve.registerChild(newElement);
				return newElement;
			}
		}
		else if (contextObject instanceof CurveSegment) {
			CurveSegment curveSegment = (CurveSegment) contextObject;

			if (elementName.equals(start)) {
				Start point = new Start();
				curveSegment.setStart(point);

				return point;
			} 
			else if (elementName.equals(end)) {
				End point = new End();
				curveSegment.setEnd(point);

				return point;
			} 
			else if (elementName.equals(basePoint1)) {
				BasePoint1 point = new BasePoint1();
				curveSegment.setBasePoint1(point);

				return point;
			} 
			else if (elementName.equals(basePoint2)) {
				BasePoint2 point = new BasePoint2();
				curveSegment.setBasePoint2(point);

				return point;
			} 
			
		}
		else if (contextObject instanceof ListOf<?>) {
			ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
			SBase newElement = null;

			if (elementName.equals(layout)) {
				newElement = new Layout();
			} 
			else if (elementName.equals(compartmentGlyph)) {
				newElement = new CompartmentGlyph();
			}
			else if (elementName.equals(speciesGlyph)) {
				newElement = new SpeciesGlyph();
			}
			else if (elementName.equals(reactionGlyph)) {
				newElement = new ReactionGlyph();
			}
			else if (elementName.equals(textGlyph)) {
				newElement = new TextGlyph();
			}
			else if (elementName.equals(curveSegment)) {
				newElement = new CurveSegment();
			}
			else if (elementName.equals(speciesReferenceGlyph)) {
				newElement = new SpeciesReferenceGlyph();
			}
			
			if (newElement != null) {
				listOf.registerChild(newElement);
				listOf.add(newElement);
			}
			
			return newElement;

		}
		return contextObject;
	}

	

	
}
