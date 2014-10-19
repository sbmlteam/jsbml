/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014  jointly by the following organizations:
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

import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
import jp.sbi.celldesigner.plugin.DataObject.PluginRealLineInformationDataObjOfReactionLink;
import jp.sbi.celldesigner.plugin.util.PluginSystemOutUtils;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
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
 * @version $Rev$
 * @since 1.0
 * @date Jun 19, 2014
 */
public class LayoutConverter {

  final static double depth  =  1d;
  final static double z  =  0d;

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
   * @param pSpecies the PluginSpeciesAlias
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
      ReactionGlyph rGlyph = layout.createReactionGlyph("rGlyph_"+pReaction.getId());
      rGlyph.setReference(pReaction.getId());
      reactionList.add(rGlyph);

      TextGlyph tGlyph = layout.createTextGlyph("tGlyph_"+pReaction.getId());
      tGlyph.createBoundingBox(20, 10, depth, pReaction.getLabelX()-2, pReaction.getLabelY()-2, z);
      tGlyph.setOriginOfText(pReaction.getId());
      tGlyph.setGraphicalObject(rGlyph);
      reactionList.add(tGlyph);

      rGlyph.putUserObject(LINK_TO_CELLDESIGNER, pReaction);
      tGlyph.putUserObject(LINK_TO_CELLDESIGNER, pReaction);

      //create SpeciesReferenceGlyphs for reactants
      for (int i = 0;i<pReaction.getNumReactants();i++)
      {
        SpeciesReferenceGlyph srGlyph = new SpeciesReferenceGlyph("srGlyphReactant_"+pReaction.getId()+"_"+
            pReaction.getReactant(i).getSpeciesInstance().getId());
        srGlyph.setRole(SpeciesReferenceRole.SUBSTRATE);

        PluginSpeciesAlias pSpeciesAlias = pReaction.getReactant(i).getSpeciesInstance().getSpeciesAlias(0);
        srGlyph.setSpeciesGlyph("sGlyph_"+pSpeciesAlias.getAliasID());
        reactionList.add(srGlyph);
        srGlyph.setReference(pReaction.getId());

        layout.getReactionGlyph("rGlyph_"+pReaction.getId()).addSpeciesReferenceGlyph(srGlyph);
      }

      //create SpeciesReferenceGlyphs for products
      for (int i = 0;i<pReaction.getNumProducts();i++)
      {
        SpeciesReferenceGlyph srGlyph = new SpeciesReferenceGlyph("srGlyphProduct_" + pReaction.getId() + "_" +
            pReaction.getProduct(i).getSpeciesInstance().getId());
        srGlyph.setRole(SpeciesReferenceRole.PRODUCT);

        PluginSpeciesAlias pSpeciesAlias = pReaction.getProduct(i).getSpeciesInstance().getSpeciesAlias(0);
        srGlyph.setSpeciesGlyph("sGlyph_"+pSpeciesAlias.getAliasID());
        reactionList.add(srGlyph);
        srGlyph.setReference(pReaction.getId());

        layout.getReactionGlyph("rGlyph_"+pReaction.getId()).addSpeciesReferenceGlyph(srGlyph);
      }

      //create SpeciesReferenceGlyphs for modifiers
      for (int i = 0;i<pReaction.getNumModifiers();i++)
      {
        SpeciesReferenceGlyph srGlyph = new SpeciesReferenceGlyph("srGlyphModifier_" + pReaction.getId() + "_" +
            pReaction.getModifier(i).getSpeciesInstance().getId());

        //a few published models make the same species a modifier twice,
        //causing an ID conflict for srGlyph. We need to work around this.
        for (SpeciesReferenceGlyph ref: layout.
            getReactionGlyph("rGlyph_"+pReaction.getId()).getListOfSpeciesReferenceGlyphs())
        {
          if (ref.getId().equals(srGlyph.getId()))
          {
            srGlyph.setId(srGlyph.getId()+"_2");
          }
        }
        srGlyph.setRole(SpeciesReferenceRole.MODIFIER);

        PluginSpeciesAlias pSpeciesAlias = pReaction.getModifier(i).getSpeciesInstance().getSpeciesAlias(0);
        srGlyph.setSpeciesGlyph("sGlyph_"+pSpeciesAlias.getAliasID());
        reactionList.add(srGlyph);
        srGlyph.setReference(pReaction.getId());

        layout.getReactionGlyph("rGlyph_"+pReaction.getId()).addSpeciesReferenceGlyph(srGlyph);
      }

      //starting the reaction position algorithm
      PluginRealLineInformationDataObjOfReactionLink inputInfo = pReaction.getAllMyPostionInfomations();
      debugReactionPosition(pReaction, inputInfo);
      if (inputInfo!= null && inputInfo.getPointsInLine() != null) {
        ListOf<SpeciesReferenceGlyph> list = layout.getReactionGlyph("rGlyph_"+pReaction.getId()).getListOfSpeciesReferenceGlyphs();
        if (inputInfo.getPointsInLine().size()==1)
        {
          Vector mainReactionAxis = (Vector)inputInfo.getPointsInLine().get(0);
          mainReactionAxis.set(0, new Point2D.Double(3d, 4d));
          inputInfo.setPointsInLine(mainReactionAxis);

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

            if(isPointOnLineSegment(firstPoint, nextPoint, pReaction))
            {
              rGlyphSegment.setStart(new Point(pReaction.getConnectionPointX(), pReaction.getConnectionPointY(),z));
              rGlyphSegment.setEnd(new Point(pReaction.getConnectionPointX(), pReaction.getConnectionPointY(),z));
              rGlyphCurve.addCurveSegment(rGlyphSegment);
              layout.getReactionGlyph("rGlyph_"+pReaction.getId()).setCurve(rGlyphCurve);

              reactantSegment = new LineSegment();
              reactantSegment.setStart(new Point(firstPoint.x, firstPoint.y, z));
              reactantSegment.setEnd(new Point(new Point(pReaction.getConnectionPointX(), pReaction.getConnectionPointY(), z)));
              reactantCurve.addCurveSegment(reactantSegment);
              SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_"+pReaction.getId()+"_"+pReaction.getReactant(0).getSpeciesInstance().getId());
              srGlyph.setCurve(reactantCurve);

              productSegment = new LineSegment();
              productSegment.setStart(new Point(new Point(pReaction.getConnectionPointX(), pReaction.getConnectionPointY(), z)));
              productSegment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
              productCurve.addCurveSegment(productSegment);
            }
            else if (!layout.getReactionGlyph("rGlyph_"+pReaction.getId()).isSetCurve())
            {
              reactantSegment = new LineSegment();
              reactantSegment.setStart(new Point(firstPoint.x, firstPoint.y, z));
              reactantSegment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
              reactantCurve.addCurveSegment(reactantSegment);
            }
            else if (layout.getReactionGlyph("rGlyph_"+pReaction.getId()).isSetCurve())
            {
              productSegment = new LineSegment();
              productSegment.setStart(new Point(firstPoint.x, firstPoint.y, z));
              productSegment.setEnd(new Point(nextPoint.x, nextPoint.y, z));
              productCurve.addCurveSegment(productSegment);
            }
          }
          SpeciesReferenceGlyph srGlyph = list.get("srGlyphProduct_"+pReaction.getId()+"_"+pReaction.getProduct(0).getSpeciesInstance().getId());
          srGlyph.setCurve(productCurve);
        }

        else if (inputInfo.getPointsInLine().size()>1)
        {
          List<Point2D.Double> allReactionPoints = new Vector<Point2D.Double>();
          for (int i=0; i<inputInfo.getPointsInLine().size(); i++)
          {
            Vector vector = (Vector)inputInfo.getPointsInLine().get(i);
            for (Object pointDouble: vector)
            {
              if (pointDouble instanceof Point2D.Double) {
                allReactionPoints.add((Point2D.Double) pointDouble);
              }
            }
          }
          Point2D.Double reactionMidpoint = inputInfo.getMidPointInLine(); //centerpoint of reaction

          if (pReaction.getNumReactants()>pReaction.getNumProducts())
          {
            if (((Vector)inputInfo.getPointsInLine().get(0)).size()==2)
            {
              LineSegment reactantSegment = new LineSegment();
              LineSegment productSegment = new LineSegment();
              LineSegment rGlyphSegment = new LineSegment();
              reactantSegment.setStart(new Point(allReactionPoints.get(1).x, allReactionPoints.get(1).y, z));
              reactantSegment.setEnd(new Point(reactionMidpoint.x, reactionMidpoint.y, z));
              productSegment.setStart(new Point(reactionMidpoint.x, reactionMidpoint.y, z));
              productSegment.setEnd(new Point(allReactionPoints.get(5).x, allReactionPoints.get(5).y, z));
              //first reactant
              SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_"+pReaction.getId()+"_"+pReaction.getReactant(0).getSpeciesInstance().getId());
              Curve curve = new Curve();
              curve.addCurveSegment(reactantSegment);
              srGlyph.setCurve(curve);
              //first product
              srGlyph = list.get("srGlyphProduct_"+pReaction.getId()+"_"+pReaction.getProduct(0).getSpeciesInstance().getId());
              curve = new Curve();
              curve.addCurveSegment(productSegment);
              srGlyph.setCurve(curve);
              //second reactant
              LineSegment reactantSegment2 = new LineSegment();
              reactantSegment2.setStart(new Point(allReactionPoints.get(3).x, allReactionPoints.get(3).y, z));
              reactantSegment2.setEnd(new Point(reactionMidpoint.x, reactionMidpoint.y, z));
              srGlyph = list.get("srGlyphReactant_"+pReaction.getId()+"_"+pReaction.getReactant(1).getSpeciesInstance().getId());
              curve = new Curve();
              curve.addCurveSegment(reactantSegment2);
              srGlyph.setCurve(curve);
              //curve will now describe ReactionGlyph
              curve = new Curve();
              rGlyphSegment.setStart(new Point((allReactionPoints.get(4).x+allReactionPoints.get(5).x)/2-6,
                (allReactionPoints.get(4).y+allReactionPoints.get(5).y)/2));
              rGlyphSegment.setEnd(new Point((allReactionPoints.get(4).x+allReactionPoints.get(5).x)/2+6,
                (allReactionPoints.get(4).y+allReactionPoints.get(5).y)/2));
              curve.addCurveSegment(rGlyphSegment);
              layout.getReactionGlyph("rGlyph_"+pReaction.getId()).setCurve(curve);
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
            Vector reactionLinkMembers = (Vector) rtnLinesInfo.getPointsInLine().get(0);
            //if there is more than one reactant and we have not set a reactant, we know we are working with the reactants.
            if (pReaction.getNumReactants()>1 && !list.get("srGlyphReactant_"+pReaction.getId()+"_"
                +pReaction.getReactant(pReaction.getNumReactants()-1).getSpeciesInstance().getId()).isSetCurve())
            {
              if (reactionLinkMembers.size()==2)
              {
                Point2D.Double reactantBegin = (Point2D.Double) reactionLinkMembers.get(0);
                Point2D.Double reactantEnd = (Point2D.Double) reactionLinkMembers.get(1);
                Point2D.Double[] coords = pReaction.getCoordinatesOfConnectionPoint();
                if (rtnLinesInfo.getTypeOfLine().equals("Curve"))
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
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_"+pReaction.getId()+"_"
                      +pReaction.getReactant(j).getSpeciesInstance().getId());
                  if (!srGlyph.isSetCurve())
                  {
                    Curve curve = new Curve();
                    if (rtnLinesInfo.getTypeOfLine().equals("Curve"))
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
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphReactant_"+pReaction.getId()+"_"
                      +pReaction.getReactant(j).getSpeciesInstance().getId());
                  if (!srGlyph.isSetCurve())
                  {
                    srGlyph.setCurve(curve);
                    break;
                  }
                }
              }
            }
            else if (!list.get("srGlyphProduct_"+pReaction.getId()+"_"
                +pReaction.getProduct(pReaction.getNumProducts()-1).getSpeciesInstance().getId()).isSetCurve() &&
                (pReaction.getNumReactants()>=1 || pReaction.getNumProducts()>1))
            {
              if (reactionLinkMembers.size()==2)
              {
                Point2D.Double productBegin = (Point2D.Double) reactionLinkMembers.get(0);
                Point2D.Double productEnd = (Point2D.Double) reactionLinkMembers.get(1);
                CubicBezier cbProductSegment = new CubicBezier();

                Point2D.Double[] coords = pReaction.getCoordinatesOfConnectionPoint();
                if (rtnLinesInfo!=null && rtnLinesInfo.getTypeOfLine()!=null &&
                    rtnLinesInfo.getTypeOfLine().equals("Curve"))
                {
                  cbProductSegment.setStart(new Point(productBegin.x, productBegin.y, z));
                  cbProductSegment.setEnd(new Point(productEnd.x, productEnd.y, z));
                  cbProductSegment.setBasePoint1(new Point(productBegin.x + twoThirds*(coords[1].x-productBegin.x),
                    productBegin.y + twoThirds*(coords[1].y-productBegin.y), 0d));
                  cbProductSegment.setBasePoint2(new Point(productEnd.x + twoThirds*(coords[1].x-productEnd.x),
                    productEnd.y + twoThirds*(coords[1].y-productBegin.y), 0d));
                }
                reactantSegment.setStart(new Point(productBegin.x, productBegin.y, z));
                reactantSegment.setEnd(new Point(productEnd.x, productEnd.y, z));
                //adding next product
                for (int j = 1; j < pReaction.getNumProducts(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphProduct_"+pReaction.getId()+"_"
                      +pReaction.getProduct(j).getSpeciesInstance().getId());
                  if (!srGlyph.isSetCurve())
                  {
                    Curve curve = new Curve();
                    if (rtnLinesInfo!=null && rtnLinesInfo.getTypeOfLine()!=null)
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
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphProduct_"+pReaction.getId()+"_"
                      +pReaction.getProduct(j).getSpeciesInstance().getId());
                  if (!srGlyph.isSetCurve())
                  {
                    srGlyph.setCurve(curve);
                    break;
                  }
                }
              }
            }
            else if (pReaction.getNumModifiers()>0 && !list.get("srGlyphModifier_"+pReaction.getId()+"_"
                +pReaction.getModifier(pReaction.getNumModifiers()-1).getSpeciesInstance().getId()).isSetCurve())
            {
              if (reactionLinkMembers.size()==2)
              {
                Point2D.Double modifierBegin = (Point2D.Double) reactionLinkMembers.get(0);
                Point2D.Double modifierEnd = (Point2D.Double) reactionLinkMembers.get(1);

                reactantSegment.setStart(new Point(modifierBegin.x, modifierBegin.y, z));
                reactantSegment.setEnd(new Point(modifierEnd.x, modifierEnd.y, z));
                //adding modifier
                for (int j = 0; j < pReaction.getNumModifiers(); j++)
                {
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphModifier_"+pReaction.getId()+"_"
                      +pReaction.getModifier(j).getSpeciesInstance().getId());
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
                  SpeciesReferenceGlyph srGlyph = list.get("srGlyphModifier_"+pReaction.getId()+"_"
                      +pReaction.getModifier(j).getSpeciesInstance().getId());
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
  private static void debugReactionPosition(PluginReaction pReaction,
    PluginRealLineInformationDataObjOfReactionLink inputInfo)
  {
    PrintStream oldOut = System.out;
    JTextArea textArea = new JTextArea();
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintStream newOut = new PrintStream(baos);

      System.setOut(newOut);
      PluginSystemOutUtils.printDebugInfoOfRealLineInformationOfReactionLink(inputInfo, 0);
      textArea.setText(pReaction.getId()+"\n"+baos.toString());
    } finally {
      System.setOut(oldOut);
    }
    JOptionPane.showMessageDialog(null, new JScrollPane(textArea));
  }

  /**
   * Check if the point is on the line segment.
   * Thanks to: http://www.sunshine2k.de/coding/java/PointOnLine/PointOnLine.html and http://www.sunshine2k.de/
   */
  private static boolean isPointOnLineSegment(Point2D.Double firstPoint, Point2D.Double nextPoint, PluginReaction pReaction)
  {
    if (!( (firstPoint.x <= pReaction.getConnectionPointX() && pReaction.getConnectionPointX() <= nextPoint.x) || (nextPoint.x <= pReaction.getConnectionPointX() && pReaction.getConnectionPointX() <= firstPoint.x) ))
    {
      // test point not in x-range
      return false;
    }
    if (!( (firstPoint.y <= pReaction.getConnectionPointY() && pReaction.getConnectionPointY() <= nextPoint.y) || (nextPoint.y <= pReaction.getConnectionPointY() && pReaction.getConnectionPointY() <= firstPoint.y) ))
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
    double epsilon = 0.003 * (dx1 * dx1 + dy1 * dy1);
    return epsilon;
  }

  /**
   * Thanks to: http://www.sunshine2k.de/
   * @param a
   * @param b
   * @param pReaction
   * @return
   */
  private static double perpDotProduct(Point2D.Double a, Point2D.Double b, PluginReaction pReaction)
  {
    return (a.x - pReaction.getConnectionPointX()) * (b.y - pReaction.getConnectionPointY()) - (a.y - pReaction.getConnectionPointY()) * (b.x - pReaction.getConnectionPointX());
  }
}