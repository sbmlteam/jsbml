/*
 * $Id: LayoutConverter.java 2132 2015-03-13 04:51:23Z yvazirabad $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/celldesigner/src/org/sbml/jsbml/celldesigner/LayoutConverter.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. Marquette University, Milwaukee, WI, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.celldesigner;

import static org.sbml.jsbml.celldesigner.CellDesignerConstants.LINK_TO_CELLDESIGNER;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.DataObject.PluginRealLineInformationDataObjOfReactionLink;
import jp.sbi.celldesigner.plugin.util.PluginSystemOutUtils;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * @author Ibrahim Vazirabad
 * @version $Rev: 2132 $
 * @since 1.0
 * @date Jun 19, 2014
 */
public class LayoutConverter {

  /**
   * 
   */
  final static double depth  =  1d;
  /**
   * 
   */
  final static double z  =  0d;
  static String deletedReactions = "";

  /**
   * 
   */
  static List<String> debugReactionPositioningArray = new ArrayList<String>(100);

  /**
   *
   * Extracts Compartment Layout data from CellDesigner.
   * @param pCompartment the PluginCompartment
   * @param layout The Layout object
   * @return a Set of SBases which are related to the CellDesigner Compartment.
   */
  public static Set<SBase> extractLayout(PluginCompartment pCompartment, Layout layout)
  {
    Set<SBase> compartmentList = new HashSet<SBase>();
    CompartmentGlyph cGlyph  =  layout.createCompartmentGlyph("cGlyph_" + pCompartment.getId());
    cGlyph.setReference(pCompartment.getId());
    cGlyph.createBoundingBox(pCompartment.getWidth(), pCompartment.getHeight(), depth, pCompartment.getX(), pCompartment.getY(), z);
    compartmentList.add(cGlyph);

    TextGlyph tGlyph = layout.createTextGlyph("tGlyph_" + pCompartment.getId());
    BoundingBox bBox  =  tGlyph.createBoundingBox(pCompartment.getName().length()*5,10,depth);
    bBox.createPosition(pCompartment.getNamePositionX(), pCompartment.getNamePositionY(), z);
    tGlyph.setOriginOfText(pCompartment.getId());
    tGlyph.setGraphicalObject(cGlyph);
    compartmentList.add(tGlyph);

    cGlyph.putUserObject(LINK_TO_CELLDESIGNER, pCompartment);
    tGlyph.putUserObject(LINK_TO_CELLDESIGNER, pCompartment);

    return compartmentList;
  }

  /**
   * Extracts Species Layout data from CellDesigner.
   * @param pSpeciesAlias the PluginSpeciesAlias
   * @param layout The Layout object
   * @return a Set of SBases which are related to the CellDesigner Species.
   */
  public static Set<SBase> extractLayout(PluginSpeciesAlias pSpeciesAlias, Layout layout)
  {
    Set<SBase> speciesAliasList = new HashSet<SBase>();
    SpeciesGlyph sGlyph = layout.createSpeciesGlyph("sGlyph_" + pSpeciesAlias.getAliasID());
    sGlyph.setReference(pSpeciesAlias.getSpecies().getId());
    sGlyph.createBoundingBox(pSpeciesAlias.getWidth(), pSpeciesAlias.getHeight(), depth, pSpeciesAlias.getX(), pSpeciesAlias.getY(), z);
    speciesAliasList.add(sGlyph);

    TextGlyph tGlyph = layout.createTextGlyph("tGlyph_" + pSpeciesAlias.getAliasID());
    tGlyph.createBoundingBox(pSpeciesAlias.getWidth(), pSpeciesAlias.getHeight(), depth,
      pSpeciesAlias.getX(), pSpeciesAlias.getY(), z);
    tGlyph.setOriginOfText(pSpeciesAlias.getSpecies().getId());
    tGlyph.setGraphicalObject(sGlyph);
    speciesAliasList.add(tGlyph);

    sGlyph.putUserObject(LINK_TO_CELLDESIGNER, pSpeciesAlias);
    tGlyph.putUserObject(LINK_TO_CELLDESIGNER, pSpeciesAlias);

    return speciesAliasList;
  }

  /**
   * Extracts Reaction Layout data from CellDesigner.
   * @param pReaction  the PluginReaction
   * @param layout The Layout object
   * @return a Set of SBases which are related to the CellDesigner Reaction.
   */
  public static Set<SBase> extractLayout(PluginReaction pReaction, Layout layout)
  {
    Set<SBase> reactionList = new HashSet<SBase>();
    try
    {
      ReactionGlyph rGlyph = layout.createReactionGlyph("rGlyph_" + pReaction.getId());
      rGlyph.setReference(pReaction.getId());
      reactionList.add(rGlyph);

      TextGlyph tGlyph = layout.createTextGlyph("tGlyph_" + pReaction.getId());
      tGlyph.createBoundingBox(20, 10, depth, pReaction.getLabelX()-2, pReaction.getLabelY()-2, z);
      tGlyph.setOriginOfText(pReaction.getId());
      tGlyph.setGraphicalObject(rGlyph);
      reactionList.add(tGlyph);

      rGlyph.putUserObject(LINK_TO_CELLDESIGNER, pReaction);
      tGlyph.putUserObject(LINK_TO_CELLDESIGNER, pReaction);

      String[] listOfReactionTypes = {"Product", "Modifier", "Reactant"};
      createSRG(listOfReactionTypes, pReaction, reactionList, layout);

      //starting the reaction position algorithm
      PluginRealLineInformationDataObjOfReactionLink inputInfo = pReaction.getAllMyPostionInfomations();
      debugReactionPositioningArray.add(debugReactionPosition(pReaction, inputInfo));
      if (inputInfo!= null && inputInfo.getPointsInLine() != null) {
        ListOf<SpeciesReferenceGlyph> list = layout.getReactionGlyph("rGlyph_" + pReaction.getId()).getListOfSpeciesReferenceGlyphs();

        if (inputInfo.getPointsInLine().size() == 1) //one sub-line
        {
          @SuppressWarnings("unchecked")
          Vector<Point2D> mainReactionAxis = (Vector<Point2D>) inputInfo.getPointsInLine().get(0);
          //reactions with only a start and end point
          if (mainReactionAxis.size() == 2 && inputInfo.getPointsInLine().size() == 1)
          {
            Point2D.Double coordinatesReactant = (Point2D.Double) mainReactionAxis.get(0);
            Point2D.Double coordinatesProduct = (Point2D.Double) mainReactionAxis.get(1);

            LineSegment reactantSegment = new LineSegment();
            reactantSegment.setStart(new Point(coordinatesReactant.x, coordinatesReactant.y, z));
            reactantSegment.setEnd(new Point((coordinatesReactant.x+coordinatesProduct.x)/2,
              (coordinatesReactant.y+coordinatesProduct.y)/2, z));

            LineSegment productSegment = new LineSegment();
            productSegment.setStart(new Point((coordinatesReactant.x+coordinatesProduct.x)/2,
              (coordinatesReactant.y+coordinatesProduct.y)/2, z));
            productSegment.setEnd(new Point(coordinatesProduct.x, coordinatesProduct.y, z));

            //srGlyph will be first reactant
            SpeciesReferenceGlyph srGlyph;
            Curve curve;
            srGlyph = list.get("srGlyphReactant_" + pReaction.getId() + "_" + pReaction.getReactant(0).getSpecies());
            curve = new Curve();
            curve.addCurveSegment(reactantSegment);
            srGlyph.setCurve(curve);

            //srGlyph will be first product
            srGlyph = list.get("srGlyphProduct_" + pReaction.getId() + "_" + pReaction.getProduct(0).getSpecies());
            curve = new Curve();
            curve.addCurveSegment(productSegment);
            srGlyph.setCurve(curve);

            //curve will now describe ReactionGlyph
            curve = new Curve();
            LineSegment rGlyphSegment = new LineSegment();
            rGlyphSegment.setStart(new Point(pReaction.getConnectionPointX()-1, pReaction.getConnectionPointY()-1,z));
            rGlyphSegment.setEnd(new Point(pReaction.getConnectionPointX()+1, pReaction.getConnectionPointY()+1,z));
            curve.addCurveSegment(rGlyphSegment);
            layout.getReactionGlyph("rGlyph_" + pReaction.getId()).setCurve(curve);
          }
          else if (mainReactionAxis.size()==3 && pReaction.getConnectionPointX()>0 && pReaction.getConnectionPointY()>0)
          {
            Point2D.Double coordinate1 = (Point2D.Double) mainReactionAxis.get(0);
            Point2D.Double coordinate2 = (Point2D.Double) mainReactionAxis.get(1);
            Point2D.Double coordinate3 = (Point2D.Double) mainReactionAxis.get(2);

            LineSegment reactantSegment = new LineSegment();
            reactantSegment.setStart(new Point(coordinate1.x, coordinate1.y, z));
            reactantSegment.setEnd(new Point(coordinate2.x, coordinate2.y, z));

            //srGlyph will be first reactant
            SpeciesReferenceGlyph srGlyph;
            Curve curve;
            srGlyph = list.get("srGlyphReactant_"+pReaction.getId()+"_"+pReaction.getReactant(0).getSpeciesInstance().getId());
            curve = new Curve();
            curve.addCurveSegment(reactantSegment);
            srGlyph.setCurve(curve);

            LineSegment productSegment = new LineSegment();
            productSegment.setStart(new Point(coordinate2.x, coordinate2.y, z));
            productSegment.setEnd(new Point(coordinate3.x, coordinate3.y, z));

            //srGlyph will be first product
            srGlyph = list.get("srGlyphProduct_"+pReaction.getId()+"_"+pReaction.getProduct(0).getSpeciesInstance().getId());
            curve = new Curve();
            curve.addCurveSegment(productSegment);
            srGlyph.setCurve(curve);

            //curve will now describe ReactionGlyph
            curve = new Curve();
            LineSegment rGlyphSegment = new LineSegment();
            rGlyphSegment.setStart(new Point(pReaction.getConnectionPointX()-1, pReaction.getConnectionPointY()-1,z));
            rGlyphSegment.setEnd(new Point(pReaction.getConnectionPointX()+1, pReaction.getConnectionPointY()+1,z));
            curve.addCurveSegment(rGlyphSegment);
            layout.getReactionGlyph("rGlyph_"+pReaction.getId()).setCurve(curve);
          }
          else if (mainReactionAxis.size()==4 && pReaction.getConnectionPointX()>0 && pReaction.getConnectionPointY()>0)
          {
            Point2D.Double coordinatesReactant = (Point2D.Double) mainReactionAxis.get(0);
            Point2D.Double coordinatesReactant2 = (Point2D.Double) mainReactionAxis.get(1);
            Point2D.Double coordinatesProduct = (Point2D.Double) mainReactionAxis.get(2);
            Point2D.Double coordinatesProduct2 = (Point2D.Double) mainReactionAxis.get(3);

            LineSegment reactantSegment = new LineSegment();
            reactantSegment.setStart(new Point(coordinatesReactant.x, coordinatesReactant.y, z));
            reactantSegment.setEnd(new Point(coordinatesReactant2.x, coordinatesReactant2.y, z));

            //srGlyph will be first reactant
            SpeciesReferenceGlyph srGlyph;
            Curve curve;
            srGlyph = list.get("srGlyphReactant_"+pReaction.getId()+"_"+pReaction.getReactant(0).getSpeciesInstance().getId());
            curve = new Curve();
            curve.addCurveSegment(reactantSegment);
            reactantSegment = new LineSegment();
            reactantSegment.setStart(new Point(coordinatesReactant2.x, coordinatesReactant2.y, z));
            reactantSegment.setEnd(new Point(coordinatesProduct.x, coordinatesProduct.y, z));
            curve.addCurveSegment(reactantSegment);
            srGlyph.setCurve(curve);

            LineSegment productSegment = new LineSegment();
            productSegment.setStart(new Point(coordinatesProduct.x, coordinatesProduct.y, z));
            productSegment.setEnd(new Point(coordinatesProduct2.x, coordinatesProduct2.y, z));

            //srGlyph will be first product
            srGlyph = list.get("srGlyphProduct_"+pReaction.getId()+"_"+pReaction.getProduct(0).getSpeciesInstance().getId());
            curve = new Curve();
            curve.addCurveSegment(productSegment);
            srGlyph.setCurve(curve);

            //curve will now describe ReactionGlyph
            curve = new Curve();
            LineSegment rGlyphSegment = new LineSegment();
            rGlyphSegment.setStart(new Point(pReaction.getConnectionPointX()-1, pReaction.getConnectionPointY()-1,z));
            rGlyphSegment.setEnd(new Point(pReaction.getConnectionPointX()+1, pReaction.getConnectionPointY()+1,z));
            curve.addCurveSegment(rGlyphSegment);
            layout.getReactionGlyph("rGlyph_"+pReaction.getId()).setCurve(curve);
          }
          else
          {
            LineSegment reactantSegment;
            LineSegment productSegment;
            LineSegment rGlyphSegment = new LineSegment();
            Curve reactantCurve = new Curve();
            Curve productCurve = new Curve();
            Curve rGlyphCurve = new Curve();
            for (int i = 0; i<mainReactionAxis.size()-1; i++)
            {
              Point2D.Double firstPoint = (Point2D.Double) mainReactionAxis.get(i);
              Point2D.Double nextPoint = (Point2D.Double) mainReactionAxis.get(i+1);
              if (pReaction.getConnectionPointX()<0 && pReaction.getConnectionPointY()<0)
              {
                //for some lines without a ReactionGlyph
                for (int j = 0; j<mainReactionAxis.size()-1; j++)
                {
                  Point2D.Double firstPoint2 = (Point2D.Double) mainReactionAxis.get(i);
                  Point2D.Double nextPoint2 = (Point2D.Double) mainReactionAxis.get(i+1);

                  reactantSegment = new LineSegment();
                  reactantSegment.setStart(new Point(firstPoint2.x, firstPoint2.y, z));
                  reactantSegment.setEnd(new Point(nextPoint2.x, nextPoint2.y, z));
                  reactantCurve.addCurveSegment(reactantSegment);
                }
                SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_" + pReaction.getId() + "_" + pReaction.getReactant(0).getSpecies());
                srGlyph.setCurve(reactantCurve);
              }

              if (isPointOnLineSegment(firstPoint, nextPoint, pReaction))
              {
                rGlyphSegment.setStart(new Point(pReaction.getConnectionPointX()-1, pReaction.getConnectionPointY()-1,z));
                rGlyphSegment.setEnd(new Point(pReaction.getConnectionPointX()+1, pReaction.getConnectionPointY()+1,z));
                rGlyphCurve.addCurveSegment(rGlyphSegment);
                layout.getReactionGlyph("rGlyph_" + pReaction.getId()).setCurve(rGlyphCurve);

                reactantSegment = new LineSegment();
                reactantSegment.setStart(new Point(firstPoint.x, firstPoint.y, z));
                reactantSegment.setEnd(new Point(new Point(pReaction.getConnectionPointX(), pReaction.getConnectionPointY(), z)));
                reactantCurve.addCurveSegment(reactantSegment);
                SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_" + pReaction.getId() + "_" + pReaction.getReactant(0).getSpecies());
                srGlyph.setCurve(reactantCurve);
                productSegment = new LineSegment();
                productSegment.setStart(new Point(new Point(pReaction.getConnectionPointX(), pReaction.getConnectionPointY(), z)));
                productSegment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
                productCurve.addCurveSegment(productSegment);
              }
              else if (!layout.getReactionGlyph("rGlyph_" + pReaction.getId()).isSetCurve())
              {
                reactantSegment = new LineSegment();
                reactantSegment.setStart(new Point(firstPoint.x, firstPoint.y, z));
                reactantSegment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
                reactantCurve.addCurveSegment(reactantSegment);
              }
              else if (layout.getReactionGlyph("rGlyph_" + pReaction.getId()).isSetCurve())
              {
                productSegment = new LineSegment();
                productSegment.setStart(new Point(firstPoint.x, firstPoint.y, z));
                productSegment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
                productCurve.addCurveSegment(productSegment);
              }
            }
            SpeciesReferenceGlyph srGlyph = list.get("srGlyphProduct_" + pReaction.getId() + "_" + pReaction.getProduct(0).getSpecies());
            srGlyph.setCurve(productCurve);
          }
        }

        else if (inputInfo.getPointsInLine().size()>1) //multi-subline reactions
        {
          Curve curve = new Curve();
          LineSegment rGlyphSegment = new LineSegment();
          rGlyphSegment.setStart(new Point(pReaction.getConnectionPointX()-1, pReaction.getConnectionPointY()-1,z));
          rGlyphSegment.setEnd(new Point(pReaction.getConnectionPointX()+1, pReaction.getConnectionPointY()+1,z));
          curve.addCurveSegment(rGlyphSegment);
          layout.getReactionGlyph("rGlyph_" + pReaction.getId()).setCurve(curve);

          for (int i = 0; i<pReaction.getNumReactants() && i<3; i++)
          {
            @SuppressWarnings("unchecked")
            Vector<Point2D.Double> reactionPoints = (Vector<Point2D.Double>) inputInfo.getPointsInLine().get(i);

            LineSegment segment = new LineSegment();
            curve = new Curve();
            for (int j = reactionPoints.size()-1; j>0; j--)
            {
              Point2D.Double firstPoint = reactionPoints.get(j);
              Point2D.Double nextPoint =  reactionPoints.get(j-1);

              segment = new LineSegment();
              segment.setStart(new Point(firstPoint.x, firstPoint.y, z));
              segment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
              curve.addCurveSegment(segment);
              PluginSpeciesReference p = pReaction.getReactant(i);
              SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_" + pReaction.getId() + "_" + pReaction.getReactant(i).getSpecies());
              srGlyph.setCurve(curve);
            }
          }
          for (int i = 0; i<pReaction.getNumProducts() && pReaction.getNumReactants()<3; i++)
          {
            if (i+pReaction.getNumReactants()<3)
            {
              @SuppressWarnings("unchecked")
              Vector<Point2D.Double> reactionPoints = (Vector<Point2D.Double>) inputInfo.getPointsInLine().get(i+pReaction.getNumReactants());

              LineSegment segment = new LineSegment();
              curve = new Curve();
              for (int j = 0; j<reactionPoints.size()-1; j++)
              {
                Point2D.Double firstPoint = reactionPoints.get(j);
                Point2D.Double nextPoint =  reactionPoints.get(j+1);

                segment = new LineSegment();
                segment.setStart(new Point(firstPoint.x, firstPoint.y, z));
                segment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
                curve.addCurveSegment(segment);
                SpeciesReferenceGlyph srGlyph = list.get("srGlyphProduct_" + pReaction.getId() + "_" + pReaction.getProduct(i).getSpecies());
                srGlyph.setCurve(curve);
              }
            }
          }
        }

        //collecting ReactionLink information
        for (int i = 0; i < inputInfo.getReactionLinkMembers().size(); i++)
        {
          LineSegment reactantSegment = new LineSegment();
          CubicBezier cbReactantSegment = new CubicBezier();
          final double twoThirds = 2d/3d;
          PluginRealLineInformationDataObjOfReactionLink rtnLinesInfo =
              (PluginRealLineInformationDataObjOfReactionLink)inputInfo.getReactionLinkMembers().get(i);
          if (rtnLinesInfo.getPointsInLine().size()>0) {
            Vector<?> reactionLinkMembers = (Vector<?>) rtnLinesInfo.getPointsInLine().get(0);

            //if there is more than one reactant and we have not set a reactant, we know we are working with the reactants.

            if (!list.get("srGlyphReactant_" + pReaction.getId() + "_"
                +pReaction.getReactant(pReaction.getNumReactants()-1).getSpecies()).isSetCurve())
            {
              if (reactionLinkMembers.size() == 2)
              {
                Point2D.Double reactantBegin = (Point2D.Double) reactionLinkMembers.get(0);
                Point2D.Double reactantEnd = (Point2D.Double) reactionLinkMembers.get(1);
                Point2D.Double[] coords = pReaction.getCoordinatesOfConnectionPoint();
                if (rtnLinesInfo.getTypeOfLine() != null && rtnLinesInfo.getTypeOfLine().equals("Curve"))
                {
                  cbReactantSegment.setStart(new Point(reactantBegin.x, reactantBegin.y, z));
                  cbReactantSegment.setEnd(new Point(reactantEnd.x, reactantEnd.y, z));
                  cbReactantSegment.setBasePoint1(new Point(reactantBegin.x + twoThirds*(coords[0].x-reactantBegin.x),
                    reactantBegin.y + twoThirds*(coords[0].y-reactantBegin.y), 0d));
                  cbReactantSegment.setBasePoint2(new Point(reactantEnd.x + twoThirds*(coords[0].x-reactantEnd.x),
                    reactantEnd.y + twoThirds*(coords[0].y-reactantBegin.y), 0d));
                }
                reactantSegment.setStart(new Point(reactantBegin.x, reactantBegin.y, z));
                reactantSegment.setEnd(new Point(reactantEnd.x, reactantEnd.y, z));
                //adding next reactant
                for (int j = 1; j < pReaction.getNumReactants(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_" + pReaction.getId() + "_"
                      +pReaction.getReactant(j).getSpecies());
                  if (!srGlyph.isSetCurve())
                  {
                    Curve curve = new Curve();
                    if (rtnLinesInfo.getTypeOfLine() != null && rtnLinesInfo.getTypeOfLine().equals("Curve"))
                    {
                      curve.addCurveSegment(cbReactantSegment);
                    } else {
                      curve.addCurveSegment(reactantSegment);
                    }
                    srGlyph.setCurve(curve);
                    break;
                  }
                }
              }
              else if (reactionLinkMembers.size()>2)
              {
                LineSegment segment = new LineSegment();
                Curve curve = new Curve();
                for (int j = 0; j<reactionLinkMembers.size()-1; j++)
                {
                  Point2D.Double reactant1 = (Point2D.Double) reactionLinkMembers.get(j);
                  Point2D.Double reactant2 = (Point2D.Double) reactionLinkMembers.get(j+1);
                  segment = new LineSegment();
                  segment.setStart(new Point(reactant1.x, reactant1.y, z));
                  segment.setEnd(new Point(reactant2.x, reactant2.y, z));
                  curve.addCurveSegment(segment);
                }
                for (int j = 1; j < pReaction.getNumReactants(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_" + pReaction.getId() + "_"
                      +pReaction.getReactant(j).getSpecies());
                  if (!srGlyph.isSetCurve())
                  {
                    srGlyph.setCurve(curve);
                    break;
                  }
                }
              }
            }

            else if (!list.get("srGlyphProduct_" + pReaction.getId() + "_" + pReaction.getProduct(pReaction.getNumProducts()-1).getSpecies()).isSetCurve())
            {
              if (reactionLinkMembers.size() == 2)
              {
                Point2D.Double productBegin = (Point2D.Double) reactionLinkMembers.get(0);
                Point2D.Double productEnd = (Point2D.Double) reactionLinkMembers.get(1);
                CubicBezier cbProductSegment = new CubicBezier();

                Point2D.Double[] coords = pReaction.getCoordinatesOfConnectionPoint();
                if (rtnLinesInfo!=null && rtnLinesInfo.getTypeOfLine() != null && rtnLinesInfo.getTypeOfLine().equals("Curve"))
                {
                  cbProductSegment.setStart(new Point(productBegin.x, productBegin.y, z));
                  cbProductSegment.setEnd(new Point(productEnd.x, productEnd.y, z));
                  cbProductSegment.setBasePoint1(new Point(productBegin.x + twoThirds*(coords[1].x-productBegin.x),
                    productBegin.y + twoThirds*(coords[1].y-productBegin.y), 0d));
                  cbProductSegment.setBasePoint2(new Point(productEnd.x + twoThirds*(coords[1].x-productEnd.x),
                    productEnd.y + twoThirds*(coords[1].y-productBegin.y), 0d));
                }
                reactantSegment = new LineSegment();
                reactantSegment.setStart(new Point(productBegin.x, productBegin.y, z));
                reactantSegment.setEnd(new Point(productEnd.x, productEnd.y, z));
                //adding next product
                for (int j = 0; j < pReaction.getNumProducts(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphProduct_" + pReaction.getId() + "_" + pReaction.getProduct(j).getSpecies());
                  if (!srGlyph.isSetCurve())
                  {
                    Curve curve = new Curve();
                    if (rtnLinesInfo!=null && rtnLinesInfo.getTypeOfLine() != null)
                    {
                      if (rtnLinesInfo.getTypeOfLine().equals("Curve"))
                      {
                        curve.addCurveSegment(cbProductSegment);
                      } else {
                        curve.addCurveSegment(reactantSegment);
                      }
                      srGlyph.setCurve(curve);
                      break;
                    }
                  }
                }
              }
              else if (reactionLinkMembers.size()>2)
              {
                LineSegment segment = new LineSegment();
                Curve curve = new Curve();
                for (int j = 0; j<reactionLinkMembers.size()-1; j++)
                {
                  Point2D.Double product1 = (Point2D.Double) reactionLinkMembers.get(j);
                  Point2D.Double product2 = (Point2D.Double) reactionLinkMembers.get(j+1);
                  segment = new LineSegment();
                  segment.setStart(new Point(product1.x, product1.y, z));
                  segment.setEnd(new Point(product2.x, product2.y, z));
                  curve.addCurveSegment(segment);
                }

                for (int j = 0; j < pReaction.getNumProducts(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphProduct_" + pReaction.getId() + "_"
                      + pReaction.getProduct(j).getSpecies());
                  if (!srGlyph.isSetCurve())
                  {
                    srGlyph.setCurve(curve);
                    break;
                  }
                }
              }
            }
            else if (pReaction.getNumModifiers()>0 && !list.get("srGlyphModifier_" + pReaction.getId() + "_"
                +pReaction.getModifier(pReaction.getNumModifiers()-1).getSpecies()).isSetCurve())
            {
              if (reactionLinkMembers.size() == 2)
              {
                Point2D.Double modifierBegin = (Point2D.Double) reactionLinkMembers.get(0);
                Point2D.Double modifierEnd = (Point2D.Double) reactionLinkMembers.get(1);

                reactantSegment.setStart(new Point(modifierBegin.x, modifierBegin.y, z));
                reactantSegment.setEnd(new Point(modifierEnd.x, modifierEnd.y, z));
                //adding modifier
                for (int j = 0; j < pReaction.getNumModifiers(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphModifier_" + pReaction.getId() + "_"
                      + pReaction.getModifier(j).getSpecies());
                  if (!srGlyph.isSetCurve())
                  {
                    Curve curve = new Curve();
                    curve.addCurveSegment(reactantSegment);
                    srGlyph.setCurve(curve);
                    break;
                  }
                }
              }
              else if (reactionLinkMembers.size()>2)
              {
                Curve curve = new Curve();
                for (int j = 0; j<reactionLinkMembers.size()-1; j++)
                {
                  Point2D.Double modifier1 = (Point2D.Double) reactionLinkMembers.get(j);
                  Point2D.Double modifier2 = (Point2D.Double) reactionLinkMembers.get(j+1);
                  LineSegment segment = new LineSegment();
                  segment.setStart(new Point(modifier1.x, modifier1.y, z));
                  segment.setEnd(new Point(modifier2.x, modifier2.y, z));
                  curve.addCurveSegment(segment);
                }
                for (int j = 0; j < pReaction.getNumModifiers(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphModifier_" + pReaction.getId() + "_"
                      + pReaction.getModifier(j).getSpecies());
                  if (!srGlyph.isSetCurve())
                  {
                    srGlyph.setCurve(curve);
                    break;
                  }
                }
              }
            }
          }
        }

        //removing products with 0 length that appear randomly
        for (int k = 0; k < list.size(); k++)
        {
          SpeciesReferenceGlyph srGlyph = list.get(k);
          if (srGlyph.getCurve() != null)
          {
            ListOf<CurveSegment> curves = srGlyph.getCurve().getListOfCurveSegments();
            for (int m = 0; m<curves.size(); m++)
            {
              CurveSegment curveSegment = curves.get(m);
              if (curveSegment.getStart().x() == curveSegment.getEnd().x() && curveSegment.getStart().y() == curveSegment.getEnd().y())
              {
                deletedReactions+=srGlyph.getId()+"\t"+srGlyph.getCurve().getCurveSegmentCount()+"\n";
                curves.remove(m);
                m--;
              }
            }
          }
        }
      }
    }
    catch (Throwable e)
    {
      new GUIErrorConsole(e);
    }
    return reactionList;
  }

  /**
   * Prints reaction positioning data from CellDesigner (great debugging method).<p>
   * Call this method after inputInfo is initialized in {@link #extractLayout(PluginReaction, Layout)}
   * @param pReaction The reaction that is to be debugged.
   * @param inputInfo the DataObject associated with the Reaction.
   */
  private static String debugReactionPosition(PluginReaction pReaction,
    PluginRealLineInformationDataObjOfReactionLink inputInfo)
  {
    PrintStream oldOut = System.out;
    JTextArea textArea = new JTextArea();
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintStream newOut = new PrintStream(baos);

      System.setOut(newOut);
      PluginSystemOutUtils.printDebugInfoOfRealLineInformationOfReactionLink(inputInfo, 0);
      textArea.setText(pReaction.getId() + "\n" + baos.toString());
      return ("\n" + pReaction.getId() + "\n" + baos.toString());
    } finally {
      System.setOut(oldOut);
    }
  }

  /**
   * Prints all points from the reactions in the CellDesigner file.
   */
  protected static void debugReactionsInLayoutConverter()
  {
    StringBuffer sumOfReactions = new StringBuffer();
    //sum all Reactions positioning data
    for (int i = 0; i<debugReactionPositioningArray.size(); i++)
    {
      sumOfReactions.append(debugReactionPositioningArray.get(i));
    }
    JTextArea area = new JTextArea("\t\t\tREACTION DEBUGGING WINDOW\n"+sumOfReactions.toString());
    JScrollPane pane = new JScrollPane(area);
    pane.setPreferredSize(new Dimension(640, 480));
    JOptionPane.showMessageDialog(null, pane);
  }

  /**
   * Check if the point is on the line segment.
   * Thanks to: http://www.sunshine2k.de/coding/java/PointOnLine/PointOnLine.html and http://www.sunshine2k.de/
   * @param firstPoint
   * @param nextPoint
   * @param pReaction
   * @return
   */
  private static boolean isPointOnLineSegment(Point2D.Double firstPoint, Point2D.Double nextPoint, PluginReaction pReaction)
  {
    if (!((firstPoint.x <= pReaction.getConnectionPointX() && pReaction.getConnectionPointX()-.2d <= nextPoint.x)
        || (nextPoint.x <= pReaction.getConnectionPointX() && pReaction.getConnectionPointX() <= firstPoint.x)))
    {
      return false;
    }
    if (!((firstPoint.y <= pReaction.getConnectionPointY() && pReaction.getConnectionPointY() <= nextPoint.y)
        || (nextPoint.y <= pReaction.getConnectionPointY() && pReaction.getConnectionPointY() <= firstPoint.y)))
    {
      // test point not in y-range
      return false;
    }
    return isPointOnLineviaPDP(firstPoint, nextPoint, pReaction);
  }

  /**
   * Thanks to: http://www.sunshine2k.de/
   * @param firstPoint
   * @param nextPoint
   * @param pReaction
   * @return
   */
  private static boolean isPointOnLineviaPDP(Point2D.Double firstPoint, Point2D.Double nextPoint, PluginReaction pReaction)
  {
    return (Math.abs(perpDotProduct(firstPoint, nextPoint, pReaction)) < getEpsilon(firstPoint, nextPoint));
  }

  /**
   * Thanks to: http://www.sunshine2k.de/
   * @param firstPoint
   * @param nextPoint
   * @return
   */
  private static double getEpsilon(Point2D.Double firstPoint, Point2D.Double nextPoint)
  {
    double dx1 = nextPoint.x - firstPoint.x;
    double dy1 = nextPoint.y - firstPoint.y;
    double epsilon = 0.013d * (dx1 * dx1 + dy1 * dy1);
    return epsilon;
  }

  /**
   * Thanks to: http://www.sunshine2k.de/
   * @param a
   * @param b
   * @param pReaction
   * @return
   */
  private static double perpDotProduct(Point2D.Double a, Point2D.Double b, PluginReaction pReaction) {
    return ((a.x - pReaction.getConnectionPointX() - .2d) * (b.y - pReaction.getConnectionPointY()))
        - ((a.y - pReaction.getConnectionPointY())
            * (b.x - pReaction.getConnectionPointX() - .2));
  }

  /**
   * Prints the reaction points for debugging purposes.
   */
  public static void printDeletedReactions()
  {
    JTextArea area = new JTextArea("Reactions that were deleted:\n"+deletedReactions);
    JScrollPane pane = new JScrollPane(area);
    pane.setPreferredSize(new Dimension(640, 480));
    JOptionPane.showMessageDialog(null, pane);
  }

  /**
   * 
   * @param SRGType String array that contains the three reaction Types {Product, Reactant, Modifier}
   * @param pReaction
   * @param rList
   * @param layout
   */
  private static void createSRG(String[] SRGType, PluginReaction pReaction, Set<SBase> rList, Layout layout)
  {
    SpeciesReference specRef = null;
    SpeciesReferenceRole role = null;
    SpeciesReferenceGlyph srGlyph = null;
    PluginSpeciesAlias pSpeciesAlias = null;
    int loopType = 0;
    for (int i = 0; i < SRGType.length; i++) {
      if (SRGType[i].equals("Reactant")) {
        role = SpeciesReferenceRole.SUBSTRATE;
        loopType = pReaction.getNumReactants();
      }
      else if (SRGType[i].equals("Product")) {
        role = SpeciesReferenceRole.PRODUCT;
        loopType = pReaction.getNumProducts();
      }
      else if (SRGType[i].equals("Modifier")) {
        role = SpeciesReferenceRole.MODIFIER;
        loopType = pReaction.getNumModifiers();
      }

      //create SpeciesReferenceGlyphs
      for (int j = 0; j < loopType; j++) {
        Reaction r = layout.getModel().getReaction(pReaction.getId());
        if (SRGType[i].equals("Reactant")) {
          srGlyph = new SpeciesReferenceGlyph("srGlyphReactant_" + pReaction.getId() + "_" + pReaction.getReactant(j).getSpecies());
          pSpeciesAlias = pReaction.getReactant(j).getSpeciesInstance().getSpeciesAlias(0);
          specRef = r.getReactant(j);
        }
        else if (SRGType[i].equals("Product")) {
          srGlyph = new SpeciesReferenceGlyph("srGlyphProduct_" + pReaction.getId() + "_" + pReaction.getProduct(j).getSpecies());
          pSpeciesAlias = pReaction.getProduct(j).getSpeciesInstance().getSpeciesAlias(0);
          specRef = r.getProduct(j);
        }
        else if (SRGType[i].equals("Modifier")) {
          srGlyph = new SpeciesReferenceGlyph("srGlyphModifier_" + pReaction.getId() + "_" + pReaction.getModifier(j).getSpecies());
          pSpeciesAlias = pReaction.getModifier(j).getSpeciesInstance().getSpeciesAlias(0);
          ModifierSpeciesReference modSpecRef = r.getModifier(j);
          for (SpeciesReferenceGlyph ref: layout.getReactionGlyph("rGlyph_"+pReaction.getId()).getListOfSpeciesReferenceGlyphs()) {
            if (ref.getId().equals(srGlyph.getId())) {
              srGlyph.setId(srGlyph.getId()+"_2");
            }
          }
          if (!modSpecRef.isSetId()) {
            modSpecRef.setId(r.getId() + "_Modifier_"+ modSpecRef.getSpecies());
          }
          for (ModifierSpeciesReference ref2: layout.getModel().getModifierSpeciesReferences()) {
            if (ref2.getId().equals(modSpecRef.getId())) {
              modSpecRef.setId(modSpecRef.getId()+"_"+(int)(Math.random()*50)+1);
            }
          }
          srGlyph.setSpeciesReference(modSpecRef.getId());
        }

        srGlyph.setRole(role);
        srGlyph.setSpeciesGlyph("sGlyph_" + pSpeciesAlias.getAliasID());
        rList.add(srGlyph);

        layout.getReactionGlyph("rGlyph_" + pReaction.getId()).addSpeciesReferenceGlyph(srGlyph);
        if (!SRGType[i].equals("Modifier")) {
          if (specRef == null)
          {
            JTextArea area = new JTextArea(pReaction.getId()+"\t"+SRGType[i]);
            JScrollPane pane = new JScrollPane(area);
            pane.setPreferredSize(new Dimension(640, 480));
            JOptionPane.showMessageDialog(null, pane);
          }
          if (specRef!=null && !specRef.isSetId()) {
            specRef.setId(r.getId() + "_"+SRGType[i]+"_"+ specRef.getSpecies());
          }
          srGlyph.setSpeciesReference(specRef.getId());
        }
      }
    }
  }
}