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
package org.sbml.jsbml.ext.layout.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;

/**
 * 
 * @since 1.0
 */
public class TestSpeciesReferenceGlyphCurve {

  /**
   * @param args
   * @throws XMLStreamException
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException, XMLStreamException {

    SBMLDocument d = new SBMLDocument(3,1);
    Model model = d.createModel("testLayoutWriting");

    LayoutModelPlugin lModel = new LayoutModelPlugin(model);
    Layout layout = lModel.createLayout("layout");

    model.addExtension(LayoutConstants.namespaceURI, lModel);

    ReactionGlyph rg = new ReactionGlyph("react_r1", model.getLevel(), model.getVersion());

    SpeciesReferenceGlyph srg1 = rg.createSpeciesReferenceGlyph("srg_r1_s1", "SPG1");
    SpeciesReferenceGlyph srg2 = rg.createSpeciesReferenceGlyph("srg_r1_s2", "SPG2");
    srg1.setRole(SpeciesReferenceRole.SUBSTRATE);
    srg2.setRole(SpeciesReferenceRole.PRODUCT);

    assert(model.findNamedSBase("srg_r1_s1") == null);

    BoundingBox bbRg = rg.createBoundingBox(10.0, 10.0, 0.0);
    bbRg.setPosition(new Point(100.0, 0.0, 0.0));

    //		Curve c = new Curve(3, 1);
    Curve c = srg1.createCurve();
    //		CurveSegmentImpl cs1 = (CurveSegmentImpl) c.createCurveSegment();
    //		CubicBezier cs1 = new CubicBezier();
    CubicBezier cs1 = c.createCubicBezier();
    cs1.setStart(new Point(35.0, 10.0, 0.0));
    cs1.setEnd(new Point(100.0, 10.0, 0.0));
    cs1.setBasePoint1(new Point(25, 35, 45));
    cs1.setBasePoint2(new Point(55, 65, 75));

    //		ListOf<CurveSegment> csList = new ListOf<CurveSegment>();
    //		csList.add(cs1);
    //		c.setListOfCurveSegments(csList);
    //		srg1.setCurve(c);

    LineSegment cs2 = new LineSegment();
    cs2.createStart(110.0, 10.0, 0.0);
    cs2.createEnd(235.0, 10.0, 0.0);
    Curve c2 = new Curve();
    ListOf<CurveSegment> csList2 = new ListOf<CurveSegment>();
    csList2.add(cs2);
    c2.setListOfCurveSegments(csList2);
    srg2.setCurve(c2);

    BoundingBox bbSrg2 = srg2.createBoundingBox();
    bbSrg2.setPosition(new Point(110.0, 10.0, 0.0));
    bbSrg2.setDimensions(new Dimensions(200.0, 10.0, 0, model.getLevel(), model.getVersion()));

    layout.addReactionGlyph(rg);

    assert(model.findNamedSBase("srg_r1_s1") != null);

    String writtenDocument = JSBML.writeSBMLToString(d);

    System.out.println(writtenDocument);

    SBMLDocument d2 = JSBML.readSBMLFromString(writtenDocument);

    assert(d2.getModel().findNamedSBase("srg_r1_s1") != null);

    SpeciesReferenceGlyph testSRG1 = (SpeciesReferenceGlyph) d2.getModel().findNamedSBase("srg_r1_s1");

    assert(testSRG1.getCurve().getListOfCurveSegments().size() == 1);

    CurveSegment cs1BisCS = testSRG1.getCurve().getListOfCurveSegments().getFirst();
    CubicBezier cs1Bis = (CubicBezier) cs1BisCS;

    assert(cs1Bis.getStart().getX() == 35);
    assert(cs1Bis.getStart().getZ() == 0);
    assert(cs1Bis.getEnd().getY() == 10);
    assert(cs1Bis.getBasePoint1().getY() == 35);
    assert(cs1Bis.getBasePoint1().getZ() == 45);
    assert(cs1Bis.getBasePoint2().getX() == 55);
    assert cs1Bis.getBasePoint2().getZ() == 75 : "BasePoint2.Z is not read properly!";
    assert cs1Bis.getType().equals(CurveSegment.Type.CUBIC_BEZIER) : "BasePoint2.type is not read properly!";

    System.out.println("CurveSegmentImpl.basePoint2.Z = " + cs1Bis.getBasePoint2().getZ());

    writtenDocument = JSBML.writeSBMLToString(d);

    System.out.println(writtenDocument);
  }

}
