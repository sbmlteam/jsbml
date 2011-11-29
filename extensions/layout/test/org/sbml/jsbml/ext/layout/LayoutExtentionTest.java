/*
 * $Id$
 * $URL$
 *
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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * 
 * @author Sebastian Fr&ouml;hlich
 * @version $Rev$
 * @since 1.0
 * @date 21.11.2011
 */
public class LayoutExtentionTest {
  
  /**
   * 
   */
	static final ExtendedLayoutModel layoutModel;
	
	/**
	 * 
	 */
	static SBMLDocument doc = null;
	
	static {
		final SBMLReader reader = new SBMLReader();
		final String LAYOUT = System.getProperty("user.dir") + "/extensions/layout/test/org/sbml/jsbml/xml/test/data/layout/Layout_Example_1.xml";
		final String LAYOUT_NS = "http://www.sbml.org/sbml/level3/version1/layout/version1";

		try {
			doc = reader.readSBML(new File(LAYOUT));
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		layoutModel = (ExtendedLayoutModel) doc.getModel().getExtension(
				LAYOUT_NS);
	}

	/**
	 * 
	 */
	@Test
	public void testLayoutAttributes() {
		assertEquals("Layout_1", layoutModel.getLayout(0).getId());
	}

	/**
	 * 
	 */
	@Test
	public void testCompartmentAttributes() {
		ListOf<CompartmentGlyph> compartmentGlyphs = layoutModel.getLayout(0)
				.getListOfCompartmentGlyphs();
		assertEquals(1, compartmentGlyphs.size());
		CompartmentGlyph compartmentGlyph = compartmentGlyphs.get(0);
		BoundingBox boundingBox = compartmentGlyph.getBoundingBox();
		assertEquals("bb1", boundingBox.getId());
		assertEquals("Compartment_1", compartmentGlyph.getCompartment());
		assertEquals("CompartmentGlyph_1", compartmentGlyph.getId());
		Dimensions dimensions = boundingBox.getDimensions();
		assertEquals("390.0", Double.toString(dimensions.getWidth()));
		assertEquals("210.0", Double.toString(dimensions.getHeight()));
		Point position = boundingBox.getPoint();
		assertEquals("5.0", Double.toString(position.getX()));
		assertEquals("6.0", Double.toString(position.getY()));
	}

	/**
	 * 
	 */
	@Test
	public void testReactionGlyphAttributes() {
		ListOf<ReactionGlyph> reactionGlyphs = layoutModel.getLayout(0)
				.getListOfReactionGlyphs();
		assertEquals(2, reactionGlyphs.size());
		ReactionGlyph reactionGlyph = reactionGlyphs.get(0);
		Curve curve = reactionGlyph.getCurve();
		assertEquals(1, curve.getListOfCurveSegments().size());
		CurveSegment lineSegment = curve.getListOfCurveSegments().get(0);
		Point start = lineSegment.getStart();
		assertEquals("165.0", Double.toString(start.getX()));
		assertEquals("105.0", Double.toString(start.getY()));
		assertEquals("1.0", Double.toString(start.getZ()));
		Point end = lineSegment.getEnd();
		assertEquals("175.0", Double.toString(end.getX()));
		assertEquals("15.0", Double.toString(end.getY()));
		assertEquals("12.0", Double.toString(end.getZ()));
		SpeciesReferenceGlyph speciesReferenceGlyph = reactionGlyph
				.getListOfSpeciesReferenceGlyphs().get(0);
		assertEquals("SpeciesGlyph_1", speciesReferenceGlyph.getSpeciesGlyph());
    assertEquals("SpeciesReference_1",
      speciesReferenceGlyph.getSpeciesReference());
		assertEquals("SpeciesReferenceGlyph_1", speciesReferenceGlyph.getId());
		assertEquals("substrate",
				speciesReferenceGlyph.getSpeciesReferenceRole());
		assertEquals(2, reactionGlyph.getListOfSpeciesReferenceGlyphs().size());
		curve = speciesReferenceGlyph.getCurve();
		ListOf<CurveSegment> listOfCurveSegments = curve
				.getListOfCurveSegments();
		assertEquals(1, listOfCurveSegments.size());
		CurveSegment curveSegment = listOfCurveSegments.get(0);
		CubicBezier cubicBezir = (CubicBezier) curveSegment;
		assertEquals("165.0", Double.toString(cubicBezir.getStart().getX()));
		assertEquals("205.0", Double.toString(cubicBezir.getStart().getY()));
		assertEquals("195.0", Double.toString(cubicBezir.getEnd().getX()));
		assertEquals("60.0", Double.toString(cubicBezir.getEnd().getY()));
		assertEquals("165.0",
				Double.toString(cubicBezir.getBasePoint1().getX()));
		assertEquals("90.0", Double.toString(cubicBezir.getBasePoint1().getY()));
		assertEquals("168.0",
				Double.toString(cubicBezir.getBasePoint2().getX()));
		assertEquals("95.0", Double.toString(cubicBezir.getBasePoint2().getY()));
	}

	/**
	 * 
	 */
	@Test
	public void testTextGlyph() {
		ListOf<TextGlyph> textGlyphs = layoutModel.getLayout(0)
				.getListOfTextGlyphs();
		assertEquals(2, textGlyphs.size());
		TextGlyph textGlyph_1 = textGlyphs.get(0);
		assertEquals("TextGlyph_01", textGlyph_1.getId());
		assertEquals("SpeciesGlyph_1", textGlyph_1.getGraphicalObject());
		assertEquals("SpeciesGlyph_1", textGlyph_1.getOriginOfText());
		BoundingBox boundingBox1 = textGlyph_1.getBoundingBox();
		assertEquals("bbA", boundingBox1.getId());
		Dimensions dimensions = boundingBox1.getDimensions();
		assertEquals("228.0", Double.toString(dimensions.getWidth()));
		assertEquals("24.0", Double.toString(dimensions.getHeight()));
		Point position = boundingBox1.getPoint();
		assertEquals("92.0", Double.toString(position.getX()));
		assertEquals("26.0", Double.toString(position.getY()));
		TextGlyph textGlyph_2 = textGlyphs.get(1);
		assertEquals("TextGlyph_02", textGlyph_2.getId());
		assertEquals("SpeciesGlyph_2", textGlyph_2.getGraphicalObject());
		assertEquals("SpeciesGlyph_2", textGlyph_2.getOriginOfText());
		BoundingBox boundingBox2 = textGlyph_2.getBoundingBox();
		assertEquals("bbB", boundingBox2.getId());
		Dimensions dimensions2 = boundingBox2.getDimensions();
		assertEquals("229.0", Double.toString(dimensions2.getWidth()));
		assertEquals("25.0", Double.toString(dimensions2.getHeight()));
		Point position2 = boundingBox2.getPoint();
		assertEquals("93.0", Double.toString(position2.getX()));
		assertEquals("170.0", Double.toString(position2.getY()));
	}

	/**
	 * 
	 */
	@Test
	public void testSpeciesGlyph() {
		ListOf<SpeciesGlyph> speciesGlyphs = layoutModel.getLayout(0)
				.getListOfSpeciesGlyphs();
		assertEquals(2, speciesGlyphs.size());
		SpeciesGlyph speciesGlyph1 = speciesGlyphs.get(0);
		assertEquals("SpeciesGlyph_1", speciesGlyph1.getId());
		assertEquals("Species_1", speciesGlyph1.getSpecies());
		BoundingBox boundingBox1 = speciesGlyph1.getBoundingBox();
		assertEquals("bb2", boundingBox1.getId());
		Dimensions dimensions = boundingBox1.getDimensions();
		assertEquals("240.0", Double.toString(dimensions.getWidth()));
		assertEquals("25.0", Double.toString(dimensions.getHeight()));
		Point position = boundingBox1.getPoint();
		assertEquals("80.0", Double.toString(position.getX()));
		assertEquals("26.0", Double.toString(position.getY()));
	}

}
