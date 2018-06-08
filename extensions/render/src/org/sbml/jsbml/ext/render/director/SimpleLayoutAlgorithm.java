/*
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2018 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render.director;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.AbstractReferenceGlyph;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * {@link SimpleLayoutAlgorithm} is a partial implementation of the
 * {@link LayoutAlgorithm} interface which collects functions which are
 * independent of the output format of the {@link LayoutDirector}.
 *
 * @author Andreas Dr&auml;ger
 * @since 1.4
 */
public abstract class SimpleLayoutAlgorithm implements LayoutAlgorithm {
  
  /**
   * The default width for a {@link ReactionGlyph}.
   */
  private static final double REACTIONGLYPH_DEPTH = 0d;
  
  /**
   * The default height for a {@link ReactionGlyph}.
   */
  private static final double REACTIONGLYPH_HEIGHT = 10d;
  
  /**
   * The default depth for a {@link ReactionGlyph}.
   */
  private static final double REACTIONGLYPH_WIDTH = 20d;
  
  /**
   * The default value for z coordinates.
   */
  private static final double DEFAULT_Z_COORD = 0d;
  
  /**
   * SBML level
   */
  protected int level;
  
  /**
   * SBML version
   */
  protected int version;
  
  /**
   * Enumeration types (enums) for relative positions. Contains:
   * RIGHT, LEFT, ABOVE, BELOW and UNDEFINED
   *
   * @author Mirjam Gutekunst
   * @version $Rev: 1402 $
   */
  public static enum RelativePosition {
    /**
     *
     */
    ABOVE,
    
    /**
     *
     */
    BELOW,
    
    /**
     *
     */
    LEFT,
    
    /**
     *
     */
    RIGHT,
    
    /**
     *
     */
    UNDEFINED;
  }
  
  /**
   * The {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SimpleLayoutAlgorithm.class.toString());
  
  /**
   * The {@link Layout} object of the SBML {@link Model}.
   */
  protected Layout layout;
  
  /**
   * Set to hold all layouted glyphs
   */
  protected Set<GraphicalObject> setOfLayoutedGlyphs;
  
  /**
   * Set to hold all unlayouted glyphs
   */
  protected Set<GraphicalObject> setOfUnlayoutedGlyphs;
  
  /**
   *
   */
  public SimpleLayoutAlgorithm() {
    setOfLayoutedGlyphs = new HashSet<GraphicalObject>();
    setOfUnlayoutedGlyphs = new HashSet<GraphicalObject>();
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.LayoutAlgorithm#getLayout()
   */
  @Override
  public Layout getLayout() {
    return layout;
  }
  
  /*
   * (non-Javadoc)
   *
   * @see org.sbml.jsbml.ext.render.director.LayoutAlgorithm#isSetLayout()
   */
  @Override
  public boolean isSetLayout() {
    return (layout != null);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.LayoutAlgorithm#setModel(Model model)
   */
  @Override
  public void setLayout(Layout layout) {
    this.layout = layout;
    level = layout.getLevel();
    version = layout.getVersion();
  }
  
  
  /**
   * method calculates the relative position of the second bounding box with
   * respect to the first bounding box
   * <ul>
   * <li> ABOVE: middle of the second bounding box is directly above the middle of the first bounding box </li>
   * <li> BELOW: middle of the second bounding box is directly below the middle of the first bounding box </li>
   * <li> LEFT: middle of the second bounding box is directly left of the middle of the first bounding box </li>
   * <li> RIGHT: middle of the second bounding box is directly right of the middle of the first bounding box </li>
   * </ul>
   * @param startGlyphBB
   * @param endGlyphBB
   * @return RelativePosition
   */
  protected static RelativePosition getRelativePosition(Point startGlyph, Point endGlyph) {
    double startX = 0d, startY = 0d;
    double endX = 0d, endY = 0d;
    
    Point posStart = startGlyph;
    Point posEnd = endGlyph;
    String error = "No coordinates given for the position of {0} bounding box.";
    if (posStart != null) {
      startX = posStart.getX();
      startY = posStart.getY();
    } else {
      logger.warning(MessageFormat.format(error, "start"));
    }
    if (posEnd != null) {
      endX = posEnd.getX();
      endY = posEnd.getY();
    } else {
      logger.warning(MessageFormat.format(error, "end"));
    }
    
    if (endX < startX) { // the start point is right, above or below the end point
      if ((endY > startY) || (endY == startY)) {
        if (((startX - endX) >= (endY - startY)) || (endY == startY)) {
          // there is a triangle spanned by the start point, the end-point
          // and the point P where X- and Y-parallels throw the start and end point crosses
          // (in case of endY != startY).
          // if the leg between P and end point is shorter than the other leg (between P and start point)
          // the end point is left of the start point
          return RelativePosition.LEFT;
        } else {
          // if the length of the two legs is equal or the leg between P and end point is longer
          // the end point is above the start point
          return RelativePosition.BELOW;
        }
      } else { //endY < startY
        if ((startX - endX) >= (startY - endY)) {
          return RelativePosition.LEFT;
        } else {
          return RelativePosition.ABOVE;
        }
      }
    } else if (endX > startX) { // the start point is left, above or below the end point
      if ((endY > startY) || (endY == startY)) {
        if (((endX - startX) >= (endY - startY)) || (endY == startY)) {
          // there is a triangle spanned by the start point, the end-point
          // and the point P where X- and Y-parallels throw the start and end point crosses
          // (in case of endY != startY).
          // if the leg between P and end point is shorter than the other leg (between P and start point)
          // the end point is right of the start point, and so the start point is left of it.
          return RelativePosition.RIGHT;
        } else {
          // if the length of the two legs is equal or the leg between P and end point is longer
          // the end point is above the start point, so the start point is below the end point.
          return RelativePosition.BELOW;
        }
      } else { //endY < startY
        if ((endX - startX) >= (startY - endY)) {
          return RelativePosition.RIGHT;
        } else {
          return RelativePosition.ABOVE;
        }
      }
    } else { // endX == startX
      // the start point is either above or below the end point
      if (endY > startY) {
        return RelativePosition.BELOW;
      } else if (endY < startY) {
        return RelativePosition.ABOVE;
      } else { // then endX == startX && endY == startY
        logger.warning(MessageFormat.format(
          "Could not compute relative position from {0} to {1}.",
          startGlyph,
          endGlyph));
        return RelativePosition.UNDEFINED;
      }
    }
  }
  
  /**
   * This method calculates the average position of a curve at the direction of the reaction glyph,
   * if the role of a species reference glyph is PRODUCT, the start point of the curve is at the
   * reaction glyph, else the start point is at the species and the end point at the reaction glyph.
   *
   * @param specRefRole
   * @param specRefGlyphList
   * @return a Position
   */
  protected Point calculateAverageCurvePosition(SpeciesReferenceRole specRefRole,
    List<SpeciesReferenceGlyph> specRefGlyphList) {
    double x = 0;
    double y = 0;
    double z = 0;
    
    double count = 0;
    
    for (SpeciesReferenceGlyph specRefGlyph : specRefGlyphList) {
      if (specRefGlyph.isSetCurve()) {
        Curve curve = specRefGlyph.getCurve();
        if (curve.isSetListOfCurveSegments()) {
          for (CurveSegment curveSegment : curve.getListOfCurveSegments()) {
            LineSegment ls = (LineSegment) curveSegment;
            if (specRefGlyph.isSetSpeciesReferenceRole()
                && specRefGlyph.getSpeciesReferenceRole().equals(specRefRole)) {
              if (specRefRole.equals(SpeciesReferenceRole.PRODUCT) || specRefRole.equals(SpeciesReferenceRole.SIDEPRODUCT)) {
                //the start point is the reaction glyph
                if (ls.isSetStart()) {
                  Point startPoint = ls.getStart();
                  x = x + startPoint.getX();
                  y = y + startPoint.getY();
                  z = z + startPoint.getZ();
                  count++;
                }
              } else {
                //SpeciesReference is a reactant, so the start is the SpeciesReference and the end is the reaction glyph
                if (ls.isSetEnd()) {
                  Point endPoint = ls.getEnd();
                  x = x + endPoint.getX();
                  y = y + endPoint.getY();
                  z = z + endPoint.getZ();
                  count++;
                }
              }
            }
          }
        }
      }
    }
    
    if (count != 0) {
      x = x / count;
      y = y / count;
      z = z / count;
    } else {
      //the coordinates are 0 too, so do nothing
    }
    
    return new Point(x, y, z, layout.getLevel(), layout.getVersion());
  }
  
  /**
   * method calculates the average position of the species glyphs belonging to
   * the given list of species reference glyphs, the role of the species
   * reference glyph must be of the given type
   *
   * @param specRefRole the species reference glyphs of the given list must have
   * @param specRefGlyphList
   * @return a Position
   */
  protected Point calculateAverageSpeciesPosition(SpeciesReferenceRole specRefRole,
    List<SpeciesReferenceGlyph> specRefGlyphList) {
    double x = 0d;
    double y = 0d;
    double z = 0d;
    
    int count = 0;
    
    for (SpeciesReferenceGlyph specRefGlyph : specRefGlyphList) {
      if (specRefGlyph.isSetSpeciesGlyph()) {
        SpeciesGlyph speciesGlyph = specRefGlyph.getSpeciesGlyphInstance();
        if (speciesGlyph.isSetBoundingBox() && speciesGlyph.getBoundingBox().isSetPosition()) {
          Point position = speciesGlyph.getBoundingBox().getPosition();
          if (specRefGlyph.isSetSpeciesReferenceRole()
              && specRefGlyph.getSpeciesReferenceRole().equals(specRefRole)) {
            x += position.getX();
            y += position.getY();
            z += position.getZ();
            count++;
          }
        }
      }
    }
    
    if (count != 0) {
      x = x / count;
      y = y / count;
      z = z / count;
    } else {
      //the coordinates are 0 too, so do nothing
    }
    
    return new Point(x, y, z, layout.getLevel(), layout.getVersion());
  }
  
  /**
   * Compute the coordinates of the central point of a {@link GraphicalObject}.
   *
   * @param graphicalObject the {@link GraphicalObject} for which to calculate the center
   * @return the center {@link Point} of the graphical object
   */
  protected Point calculateCenter(GraphicalObject graphicalObject) {
    return calculateCenter(graphicalObject, layout.getLevel(), layout.getVersion());
  }
  
  /**
   * 
   * @param graphicalObject
   * @param level
   * @param version
   * @return
   */
  public static Point calculateCenter(GraphicalObject graphicalObject, int level, int version) {
    Point center = new Point(0d, 0d, 0d, level, version);
    
    if (graphicalObject.isSetBoundingBox() &&
        graphicalObject.getBoundingBox().isSetPosition()) {
      Point position = graphicalObject.getBoundingBox().getPosition();
      Dimensions dimensions = graphicalObject.getBoundingBox().getDimensions();
      center.setX(position.getX() + (dimensions.getWidth() / 2d));
      center.setY(position.getY() + (dimensions.getHeight() / 2d));
      center.setZ(position.getZ() + (dimensions.getDepth() / 2d));
    }
    
    return center;
  }
  
  /**
   * c
   * @param reacGlyph
   * @param rotationAngle
   * @param specRef
   * @return
   */
  protected Point calculateReactionGlyphDockingPoint(ReactionGlyph reacGlyph, double rotationAngle, SpeciesReferenceGlyph specRef) {
    Point dockingPointToSubstrate = new Point(layout.getLevel(), layout.getVersion());
    Point dockingPointToProduct = new Point(layout.getLevel(), layout.getVersion());
    Point dockingPointOtherLeft = new Point(layout.getLevel(), layout.getVersion());
    Point dockingPointOtherRight = new Point(layout.getLevel(), layout.getVersion());
    
    Point reactionCenterPosition = calculateCenter(reacGlyph);
    correctDimensions(reacGlyph);
    
    RelativePosition modifierPosition = getRelativePosition(reacGlyph.getBoundingBox().getPosition(), specRef.getSpeciesGlyphInstance().getBoundingBox().getPosition());
    
    double rotationAngle_new = correctRotationAngle(rotationAngle);
    
    // the legs of the triangle build by the reaction glyph and it's arms
    BoundingBox bbox = reacGlyph.getBoundingBox();
    double c = bbox.getDimensions().getWidth() / 2d;
    double h = bbox.getDimensions().getHeight() / 2d;
    double b = Math.abs(Math.cos(Math.toRadians(rotationAngle_new)) * c);
    double a = Math.abs(Math.sin(Math.toRadians(rotationAngle_new)) * c);
    double otherA = Math.abs(Math.sin(Math.toRadians(90 - rotationAngle_new))) * h;
    double otherB = Math.abs(Math.cos(Math.toRadians(90 - rotationAngle_new))) * h;
    
    //TODO: make it shorter
    
    if ((rotationAngle >= 0) && (rotationAngle < 90)) {
      // b is the width and a the height
      dockingPointToSubstrate.setX(reactionCenterPosition.getX() - b);
      dockingPointToSubstrate.setY(reactionCenterPosition.getY() - a);
      dockingPointToSubstrate.setZ(reactionCenterPosition.getZ());
      
      dockingPointToProduct.setX(reactionCenterPosition.getX() + b);
      dockingPointToProduct.setY(reactionCenterPosition.getY() + a);
      dockingPointToProduct.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherLeft.setX(reactionCenterPosition.getX() - otherA);
      dockingPointOtherLeft.setY(reactionCenterPosition.getY() + otherB);
      dockingPointOtherLeft.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherRight.setX(reactionCenterPosition.getX() + otherA);
      dockingPointOtherRight.setY(reactionCenterPosition.getY() - otherB);
      dockingPointOtherRight.setZ(reactionCenterPosition.getZ());
      
    } else if ((rotationAngle >= 90) && (rotationAngle < 180)) {
      // a is the width and b the height
      dockingPointToSubstrate.setX(reactionCenterPosition.getX() + a);
      dockingPointToSubstrate.setY(reactionCenterPosition.getY() - b);
      dockingPointToSubstrate.setZ(reactionCenterPosition.getZ());
      
      dockingPointToProduct.setX(reactionCenterPosition.getX() - a);
      dockingPointToProduct.setY(reactionCenterPosition.getY() + b);
      dockingPointToProduct.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherLeft.setX(reactionCenterPosition.getX() - otherA);
      dockingPointOtherLeft.setY(reactionCenterPosition.getY() + otherB);
      dockingPointOtherLeft.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherRight.setX(reactionCenterPosition.getX() + otherA);
      dockingPointOtherRight.setY(reactionCenterPosition.getY() - otherB);
      dockingPointOtherRight.setZ(reactionCenterPosition.getZ());
      
    } else if ((rotationAngle >= 180) && (rotationAngle < 270)) {
      // b is the width and a the height
      dockingPointToSubstrate.setX(reactionCenterPosition.getX() + b);
      dockingPointToSubstrate.setY(reactionCenterPosition.getY() + a);
      dockingPointToSubstrate.setZ(reactionCenterPosition.getZ());
      
      dockingPointToProduct.setX(reactionCenterPosition.getX() - b);
      dockingPointToProduct.setY(reactionCenterPosition.getY() - a);
      dockingPointToProduct.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherLeft.setX(reactionCenterPosition.getX() - otherB);
      dockingPointOtherLeft.setY(reactionCenterPosition.getY() - otherA);
      dockingPointOtherLeft.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherRight.setX(reactionCenterPosition.getX() + otherB);
      dockingPointOtherRight.setY(reactionCenterPosition.getY() + otherA);
      dockingPointOtherRight.setZ(reactionCenterPosition.getZ());
      
    } else {
      // a is the width and b the height
      dockingPointToSubstrate.setX(reactionCenterPosition.getX() - a);
      dockingPointToSubstrate.setY(reactionCenterPosition.getY() + b);
      dockingPointToSubstrate.setZ(reactionCenterPosition.getZ());
      
      dockingPointToProduct.setX(reactionCenterPosition.getX() + a);
      dockingPointToProduct.setY(reactionCenterPosition.getY() - b);
      dockingPointToProduct.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherLeft.setX(reactionCenterPosition.getX() + otherA);
      dockingPointOtherLeft.setY(reactionCenterPosition.getY() - otherB);
      dockingPointOtherLeft.setZ(reactionCenterPosition.getZ());
      
      dockingPointOtherRight.setX(reactionCenterPosition.getX() - otherA);
      dockingPointOtherRight.setY(reactionCenterPosition.getY() + otherB);
      dockingPointOtherRight.setZ(reactionCenterPosition.getZ());
    }
    
    /*
     * Check which docking position is needed to be returned
     */
    SpeciesReferenceRole specRefRole = specRef.getSpeciesReferenceRole();
    
    if (specRefRole != null) {
      if (specRefRole.equals(SpeciesReferenceRole.SUBSTRATE)
          || specRefRole.equals(SpeciesReferenceRole.SIDESUBSTRATE)) {
        return dockingPointToSubstrate;
      } else if (specRefRole.equals(SpeciesReferenceRole.PRODUCT)
          || specRefRole.equals(SpeciesReferenceRole.SIDEPRODUCT)) {
        return dockingPointToProduct;
      }
    }
    
    // Species docks at the ReactionGlyph directly
    if (modifierPosition.equals(RelativePosition.LEFT)) {
      return dockingPointOtherLeft;
    } else {
      return dockingPointOtherRight;
    }
  }
  
  /**
   * Normalize a given rotation angle, i.e., express the angle in the range from
   * 0&deg; to 90&deg; (excluding 90&deg;).
   *
   * @param rotationAngle in degrees
   * @return the normalized rotation angle in degrees
   */
  protected double correctRotationAngle(double rotationAngle) {
    double correctedAngle = rotationAngle % 90;
    return correctedAngle < 0 ? correctedAngle + 90d : correctedAngle;
  }
  
  /**
   * @param reactionGlyph
   * @param specRefGlyph
   * @return
   */
  protected Point createSpeciesReferenceGlyphPosition(ReactionGlyph reactionGlyph, SpeciesReferenceGlyph specRefGlyph) {
    
    double x = 0;
    double y = 0;
    double z = 0;
    
    Curve curve = specRefGlyph.getCurve();
    if (curve == null) {
      curve = createCurve(reactionGlyph, specRefGlyph);
    }
    
    if (curve.isSetListOfCurveSegments()) {
      for (CurveSegment curveSegment : curve.getListOfCurveSegments()) {
        LineSegment ls = (LineSegment) curveSegment;
        Point startPoint = ls.getStart();
        Point endPoint = ls.getEnd();
        double startX = startPoint.getX();
        double startY = startPoint.getY();
        double startZ = startPoint.getZ();
        double endX = endPoint.getX();
        double endY = endPoint.getY();
        double endZ = endPoint.getZ();
        
        if (curveSegment instanceof CubicBezier) {
          CubicBezier cb = (CubicBezier) curveSegment;
          
          Point basePoint1 = cb.getBasePoint1();
          Point basePoint2 = cb.getBasePoint2();
          
          double minX = Math.min(startX, endX);
          minX = Math.min(minX, basePoint1.getX());
          minX = Math.min(minX, basePoint2.getX());
          x = minX;
          
          double minY = Math.min(startY, endY);
          minY = Math.min(minY, basePoint1.getY());
          minY = Math.min(minY, basePoint2.getY());
          y = minY;
          
          double minZ = Math.min(startZ, endZ);
          minZ = Math.min(minZ, basePoint1.getZ());
          minZ = Math.min(minZ, basePoint2.getZ());
          z = minZ;
          
        } else {
          
          if (startX == endX) {
            // line is vertical,
            // bounding box starts at the given x, minus half of the width of the bounding box
            double width = 5; // default width of vertical boundingBox is 10pt
            if (specRefGlyph.isSetBoundingBox() && specRefGlyph.getBoundingBox().isSetDimensions()) {
              width = specRefGlyph.getBoundingBox().getDimensions().getWidth() / 2d;
            }
            x = startX - width;
          } else {
            x = Math.min(startX, endX);
          }
          
          if (startY == endY) {
            // line is horizontal,
            // bounding box starts at the given y, minus half of the height of the bounding box
            double height = 5; // default height of horizontal bounding box is 10pt
            if (specRefGlyph.isSetBoundingBox() && specRefGlyph.getBoundingBox().isSetDimensions()) {
              height = specRefGlyph.getBoundingBox().getDimensions().getHeight() / 2d;
            }
            y = startY - height;
          } else {
            y = Math.min(startY, endY);
          }
          
          if (startZ == endZ) {
            double depth = 5;
            if (specRefGlyph.isSetBoundingBox() && specRefGlyph.getBoundingBox().isSetDimensions()) {
              depth = specRefGlyph.getBoundingBox().getDimensions().getDepth() / 2d;
            }
            z = startZ - depth;
          } else {
            z = Math.min(startZ, endZ);
          }
        }
      }
    }
    return new Point(x, y, z, layout.getLevel(), layout.getVersion());
  }
  
  /**
   * @param reactionGlyph
   * @return
   */
  protected Point createReactionGlyphPositionNew(ReactionGlyph reactionGlyph) {
    List<SpeciesReferenceGlyph> speciesReferenceGlyphList = null;
    if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
      speciesReferenceGlyphList = reactionGlyph.getListOfSpeciesReferenceGlyphs();
    }
    // TODO: Use a filter here!
    SpeciesGlyph product = null; //findSpeciesGlyphByRole(speciesReferenceGlyphList, SpeciesReferenceRole.PRODUCT);
    SpeciesGlyph substrate = null; //findSpeciesGlyphByRole(speciesReferenceGlyphList, SpeciesReferenceRole.SUBSTRATE);
    
    Point substrateEnd = null;
    Point productEnd = null;
    if ((product == null) || (substrate == null)) {
      if (speciesReferenceGlyphList != null) {
        for (SpeciesReferenceGlyph specRef : speciesReferenceGlyphList) {
          if (LayoutDirector.isSubstrate(specRef)) {
            substrate = specRef.getSpeciesGlyphInstance();
            if (specRef.isSetCurve() && (specRef.getCurve().getCurveSegmentCount() > 0) && specRef.getCurve().getCurveSegment(specRef.getCurve().getCurveSegmentCount() - 1).isSetEnd()) {
              substrateEnd = specRef.getCurve().getCurveSegment(specRef.getCurve().getCurveSegmentCount() - 1).getStart();
            }
            
          } else if (LayoutDirector.isProduct(specRef)) {
            product = specRef.getSpeciesGlyphInstance();
            if (specRef.isSetCurve() && (specRef.getCurve().getCurveSegmentCount() > 0) && specRef.getCurve().getCurveSegment(0).isSetStart()) {
              productEnd = specRef.getCurve().getCurveSegment(0).getStart();
            }
          }
        }
      }
      if ((product == null) || (substrate == null)) {
        logger.warning("Cannot find product or substrate in list of species reference glyphs for reaction glyph "
            + reactionGlyph.getId());
        // TODO fall back to another positioning mechanism
        return new Point(0, 0, 0, level, version);
      }
    }
    
    Point substrateCenter = substrateEnd != null ? substrateEnd : calculateCenter(substrate);
    Point productCenter = productEnd != null ? productEnd : calculateCenter(product);
    Dimensions rgDimensions = reactionGlyph.getBoundingBox().getDimensions();
    
    Point center = calculateCenterOfPoints(substrateCenter, productCenter);
    Point upperLeft = new Point(center.getX() - (rgDimensions.getWidth()/2),
      center.getY() - (rgDimensions.getHeight()/2),
      center.getZ() - (rgDimensions.getDepth()/2),
      level, version);
    logger.fine("substrate center is " + substrateCenter.toString());
    logger.fine("product center is " + productCenter.toString());
    logger.fine("center is " + center.toString());
    logger.fine("upper left is " + upperLeft.toString());
    return upperLeft;
  }
  
  /**
   * Calculate the center of two {@link Point}s.
   *
   * @param p1 the first {@link Point}
   * @param p2 the second {@link Point}
   * @return the center {@link Point} of the two points
   */
  private Point calculateCenterOfPoints(Point p1, Point p2) {
    Point p = new Point(level, version);
    p.setX((p1.getX() + p2.getX()) / 2);
    p.setY((p1.getY() + p2.getY()) / 2);
    p.setZ((p1.getZ() + p2.getZ()) / 2);
    return p;
  }
  
  /*
   * TODO describe calculation
   *
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.LayoutAlgorithm#createReactionGlyphPositon(ReactionGlyph reactionGlyph)
   */
  protected Point createReactionGlyphPosition(ReactionGlyph reactionGlyph) {
    double x = 0d;
    double y = 0d;
    double z = 0d;
    
    List<SpeciesReferenceGlyph> speciesReferenceGlyphList = null;
    if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
      speciesReferenceGlyphList = reactionGlyph.getListOfSpeciesReferenceGlyphs();
    }
    Dimensions reacGlyphDimension;
    
    if (reactionGlyph.isSetBoundingBox() && reactionGlyph.getBoundingBox().isSetDimensions()) {
      reacGlyphDimension = reactionGlyph.getBoundingBox().getDimensions();
    } else {
      reacGlyphDimension = createReactionGlyphDimension(reactionGlyph);
    }
    
    SpeciesGlyph product = findSpeciesGlyphByRole(speciesReferenceGlyphList, SpeciesReferenceRole.PRODUCT);
    SpeciesGlyph substrate = findSpeciesGlyphByRole(speciesReferenceGlyphList, SpeciesReferenceRole.SUBSTRATE);
    if ((product == null) || (substrate == null)) {
      if (speciesReferenceGlyphList != null) {
        for (SpeciesReferenceGlyph specRef : speciesReferenceGlyphList) {
          if (LayoutDirector.isSubstrate(specRef)) {
            substrate = specRef.getSpeciesGlyphInstance();
          } else if (LayoutDirector.isProduct(specRef)) {
            product = specRef.getSpeciesGlyphInstance();
          }
        }
      }
      if ((product == null) || (substrate == null)) {
        logger.warning("Cannot find product or substrate in list of species reference glyphs for reaction glyph "
            + reactionGlyph.getId());
        // TODO fall back to another positioning mechanism
        return new Point(x, y, z, level, version);
      }
    }
    
    Point substratePointOfMiddle = calculateCenter(substrate);
    Point productPointOfMiddle = calculateCenter(product);
    
    // the computation of the position is equal for every relativePosition (left,right,above,below and undefined)
    x = (((Math.max(productPointOfMiddle.getX(), substratePointOfMiddle.getX())
        - Math.min(productPointOfMiddle.getX(), substratePointOfMiddle.getX()))
        / 2d) + Math.min(productPointOfMiddle.getX(), substratePointOfMiddle.getX())) - (reacGlyphDimension.getWidth() / 2d);
    y = (((Math.max(productPointOfMiddle.getY(), substratePointOfMiddle.getY()) - Math.min(productPointOfMiddle.getY(), substratePointOfMiddle.getY()))
        / 2d) + Math.min(productPointOfMiddle.getY(), substratePointOfMiddle.getY())) - (reacGlyphDimension.getHeight() / 2d);
    z = (((Math.max(productPointOfMiddle.getZ(), substratePointOfMiddle.getZ()) - Math.min(productPointOfMiddle.getZ(), substratePointOfMiddle.getZ()))
        / 2d) + Math.min(productPointOfMiddle.getZ(), substratePointOfMiddle.getZ())) - (reacGlyphDimension.getDepth() / 2d);
    
    return new Point(x, y, z, level, version);
  }
  
  /*
   * TODO describe calculation
   *
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.LayoutAlgorithm#createReactionGlyphDimension(org.sbml.jsbml.ext.layout.ReactionGlyph)
   */
  @Override
  public Dimensions createReactionGlyphDimension(ReactionGlyph reactionGlyph) {
    
    double width = REACTIONGLYPH_WIDTH;
    double height = REACTIONGLYPH_HEIGHT;
    double depth = REACTIONGLYPH_DEPTH;
    
    if (layout.isSetListOfReactionGlyphs()) {
      for (ReactionGlyph reacGlyph : layout.getListOfReactionGlyphs()) {
        if (reacGlyph.isSetBoundingBox()) {
          if (reacGlyph.getBoundingBox().isSetDimensions()) {
            double rWidth = reacGlyph.getBoundingBox().getDimensions().getWidth();
            double rHeight = reacGlyph.getBoundingBox().getDimensions().getHeight();
            // Compute the minimum/maximum because the bounding box of the reaction glyph is always wider than higher
            width = Math.max(rHeight, rWidth);
            height = Math.min(rHeight, rWidth);
          }
        }
      }
    }
    
    List<SpeciesReferenceGlyph> curveList = new LinkedList<SpeciesReferenceGlyph>();
    List<SpeciesReferenceGlyph> speciesGlyphList = new LinkedList<SpeciesReferenceGlyph>();
    
    if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
      for (SpeciesReferenceGlyph specRefGlyph : reactionGlyph.getListOfSpeciesReferenceGlyphs()) {
        if (specRefGlyph.isSetCurve()) {
          curveList.add(specRefGlyph);
        } else {
          if (specRefGlyph.isSetSpeciesGlyph()) {
            speciesGlyphList.add(specRefGlyph);
          }
        }
      }
    }
    
    Point substratePosition;
    Point productPosition;
    
    if (curveList.size() >= speciesGlyphList.size()) {
      // position of the curve at the point towards the reaction glyph
      substratePosition = calculateAverageCurvePosition(SpeciesReferenceRole.SUBSTRATE, curveList);
      productPosition = calculateAverageCurvePosition(SpeciesReferenceRole.PRODUCT, curveList);
      RelativePosition relativePosition = getRelativePosition(substratePosition, productPosition);
      
      if (relativePosition.equals(RelativePosition.ABOVE)) {
        height = substratePosition.getY() - productPosition.getY();
      }
      if (relativePosition.equals(RelativePosition.BELOW)) {
        height = productPosition.getY() - substratePosition.getY();
      }
      if (relativePosition.equals(RelativePosition.LEFT)) {
        width = substratePosition.getX() - productPosition.getX();
      }
      if (relativePosition.equals(RelativePosition.RIGHT)) {
        width = productPosition.getX() - substratePosition.getX();
      }
    }
    
    return new Dimensions(width, height, depth, level, version);
  }
  
  /* TODO describe calculation
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.director.LayoutAlgorithm#calculateReactionGlyphRotationAngle(org.sbml.jsbml.ext.layout.ReactionGlyph)
   */
  @Override
  public double calculateReactionGlyphRotationAngle(ReactionGlyph reactionGlyph) {
    double rotationAngle = 0d;
    
    if (reactionGlyph.isSetListOfSpeciesReferenceGlyphs()) {
      ListOf<SpeciesReferenceGlyph> speciesRefGlyphList = reactionGlyph.getListOfSpeciesReferenceGlyphs();
      
      // TODO: This needs to be extended to also take multiple reactants and products into account!
      // get the substrate and the product of this reaction
      SpeciesGlyph substrate = findSpeciesGlyphByRole(speciesRefGlyphList, SpeciesReferenceRole.SUBSTRATE);
      SpeciesGlyph product = findSpeciesGlyphByRole(speciesRefGlyphList, SpeciesReferenceRole.PRODUCT);
      
      if ((substrate == null) || (product == null)) {
        for (SpeciesReferenceGlyph specRef : speciesRefGlyphList) {
          if (LayoutDirector.isSubstrate(specRef)) {
            substrate = specRef.getSpeciesGlyphInstance();
          } else if (LayoutDirector.isProduct(specRef)) {
            product = specRef.getSpeciesGlyphInstance();
          }
        }
        if ((substrate == null) || (product == null)) {
          logger.warning(MessageFormat.format(
            "Cannot find product or substrate in list of species reference glyphs for reaction glyph {0}",
            reactionGlyph.getId()));
          return rotationAngle;
        }
      }
      
      /*
       * compute the central points:
       * If you change the first Point (firstCentralPoint) into substrate central point
       * you get a false angle when the user wants a specific position
       * for the reaction
       */
      Point firstCentralPoint = calculateCenter(substrate);
      Point secondCentralPoint = calculateCenter(product);
      
      /*
       * if there are base points set, the rotation has to be assimilated to the base points
       * and not to the centers of substrate and product.
       */
      if (reactionGlyph.isSetUserObjects() && (reactionGlyph.getUserObject("SPECIAL_ROTATION_NEEDED")!=null)) {
        for (SpeciesReferenceGlyph specRefGlyph : speciesRefGlyphList) {
          Curve curve = null;
          if (specRefGlyph.isSetCurve()) {
            curve = specRefGlyph.getCurve();
          } else if (reactionGlyph.isSetCurve()) {
            // TODO check case
            curve = reactionGlyph.getCurve();
          } else {
            break;
          }
          
          // First point: the end point from the last curve segment of the substrate
          if (specRefGlyph.isSetSpeciesReferenceRole()
              && specRefGlyph.getSpeciesReferenceRole().equals(SpeciesReferenceRole.SUBSTRATE)) {
            if (curve.isSetListOfCurveSegments()) {
              LineSegment ls = (LineSegment) curve.getListOfCurveSegments().getLast();
              firstCentralPoint = ls.getEnd();
            }
          }
          
          // Second point: the start point from the first curve segment of the product
          if (specRefGlyph.isSetSpeciesReferenceRole()
              && specRefGlyph.getSpeciesReferenceRole().equals(SpeciesReferenceRole.PRODUCT)) {
            if (curve.isSetListOfCurveSegments()) {
              LineSegment ls = (LineSegment) curve.getListOfCurveSegments().getFirst();
              secondCentralPoint = ls.getStart();
            }
          }
        }
      }
      if ((firstCentralPoint.getX() == secondCentralPoint.getX()) && (firstCentralPoint.getY() == secondCentralPoint.getY())) {
        secondCentralPoint = calculateCenter(reactionGlyph);
        logger.fine(MessageFormat.format("The two points for computing rotation angle are identical for {0} with id ''{1}'': ({2,number}; {3,number})",
          reactionGlyph.getElementName(), reactionGlyph.getId(), firstCentralPoint.getX(), firstCentralPoint.getY()));
      } else {
        rotationAngle = calculateRotationAngle(firstCentralPoint, secondCentralPoint);
      }
    }
    return rotationAngle;
  }
  
  
  /**
   * Calculate the rotation angle for a line determined by two {@link Point}s.
   *
   * @param startPoint the first {@link Point}
   * @param endPoint the second point {@link Point}
   * @return the rotation angle in degrees of a line through the two points
   */
  public static double calculateRotationAngle(Point startPoint, Point endPoint) {
    double rotationAngle = 0;
    
    double x = endPoint.getX() - startPoint.getX();
    double y = endPoint.getY() - startPoint.getY();
    rotationAngle = Math.toDegrees(Math.atan(y/x));
    
    if (endPoint.getX() < startPoint.getX()) {
      rotationAngle += 180;
    } else if (endPoint.getY() < startPoint.getY()) {
      rotationAngle += 360;
    }
    
    logger.fine(MessageFormat.format("start: {0} end: {1} deltaX: {2} deltaY: {3} rotation: {4}",
      startPoint, endPoint, x, y, rotationAngle));
    return rotationAngle;
  }
  
  /**
   * Compute the docking {@link Point} at the species depending on the
   * {@link RelativePosition} of the {@link SpeciesGlyph}.
   *
   * This method is deprecated because we decided to allow docking at any
   * point rather than restricting the docking to the four orientations left,
   * right, above and below. Use
   * {@link #calculateSpeciesGlyphDockingPosition(Point, ReactionGlyph, SpeciesReferenceRole, SpeciesGlyph)}
   * instead.
   *
   * @param middleOfSpecies center {@link Point} of the species
   * @param relativeSpeciesGlyphPosition the {@link RelativePosition} of the species
   * @param specGlyph the {@link SpeciesGlyph}
   * @return the docking {@link Point}
   */
  @Deprecated
  protected Point calculateOldSpeciesGlyphDockingPosition(
    Point middleOfSpecies,
    RelativePosition relativeSpeciesGlyphPosition,
    SpeciesGlyph specGlyph) {
    
    Point dockingPosition = null;
    
    // the coordinates of the species glyph
    double x = middleOfSpecies.getX();
    double y = middleOfSpecies.getY();
    double z = middleOfSpecies.getZ();
    double width = specGlyph.getBoundingBox().getDimensions().getWidth();
    double height = specGlyph.getBoundingBox().getDimensions().getHeight();
    
    // create the different points for the four different cases
    // Note: dockingLeft means, that the curve will dock left, so the
    // SpeciesGlyph of interest is to the right of the reference object
    Point dockingLeft = new Point(x - (width/2d), y, z, level, version);
    Point dockingRight = new Point(x + (width/2d), y, z, level, version);
    Point dockingAbove = new Point(x, y - (height/2d), z, level, version);
    Point dockingBelow = new Point(x, y + (height/2d), z, level, version);
    
    // check the cases of relative position
    if (relativeSpeciesGlyphPosition.equals(RelativePosition.ABOVE)) {
      dockingPosition = new Point(dockingBelow);
    } else if (relativeSpeciesGlyphPosition.equals(RelativePosition.BELOW)) {
      dockingPosition = new Point(dockingAbove);
    } else if (relativeSpeciesGlyphPosition.equals(RelativePosition.LEFT)) {
      dockingPosition = new Point(dockingRight);
    } else if (relativeSpeciesGlyphPosition.equals(RelativePosition.RIGHT)) {
      dockingPosition = new Point(dockingLeft);
    }
    
    return dockingPosition;
  }
  
  /**
   * Compute the docking {@link Point} at the species for the curve defined by
   * the {@link ReactionGlyph} and the {@link SpeciesGlyph}.
   *
   * @param centerOfSpecies
   *            the center {@link Point} of the {@link SpeciesGlyph}
   * @param reactionGlyph
   *            the {@link ReactionGlyph} of the arc
   * @param speciesReferenceRole
   *            the {@link SpeciesReferenceRole} of the involved
   *            {@link SpeciesReferenceGlyph}
   * @param speciesGlyph
   *            the {@link SpeciesGlyph} of the arc
   * @return the docking {@link Point}
   */
  protected Point calculateSpeciesGlyphDockingPosition(Point centerOfSpecies,
    ReactionGlyph reactionGlyph,
    SpeciesReferenceRole speciesReferenceRole,
    SpeciesGlyph speciesGlyph) {
    Point dockingPoint = null;
    
    // the coordinates of the species glyph
    double x = centerOfSpecies.getX();
    double y = centerOfSpecies.getY();
    double z = centerOfSpecies.getZ();
    
    Dimensions dimensions = speciesGlyph.getBoundingBox().getDimensions();
    double width = dimensions.getWidth();
    double height = dimensions.getHeight();
    
    int sboTerm = -1;
    if (speciesGlyph.isSetSBOTerm()) {
      sboTerm = speciesGlyph.getSBOTerm();
    } else if (speciesGlyph.isSetSpecies()) {
      sboTerm = speciesGlyph.getSpeciesInstance().getSBOTerm();
    }
    
    // computing angle
    double t = 0;
    if (speciesReferenceRole.equals(SpeciesReferenceRole.PRODUCT) ||
        speciesReferenceRole.equals(SpeciesReferenceRole.SIDEPRODUCT)) {
      t = calculateRotationAngle(calculateCenter(reactionGlyph), centerOfSpecies);
    } else {
      t = calculateRotationAngle(centerOfSpecies, calculateCenter(reactionGlyph));
    }
    
    if (SBO.isChildOf(sboTerm, SBO.getUnknownMolecule()) || (sboTerm == -1)) {
      // species is an ellipse
      dockingPoint = calculateDockingForEllipseSpecies(x, y, z,
        width,
        height,
        calculateRotationAngle(centerOfSpecies, calculateCenter(reactionGlyph)));
    } else if (SBO.isChildOf(sboTerm, SBO.getSimpleMolecule())
        || SBO.isChildOf(sboTerm, SBO.getEmptySet())) {
      // species is round
      double c = height / 2d;
      dockingPoint = calculateDockingForRoundSpecies(x, y, z, c, t,
        speciesReferenceRole);
    } else {
      // species is not round or an ellipse
      dockingPoint = calculateDockingForQuadraticSpecies(centerOfSpecies,
        speciesGlyph, calculateCenter(reactionGlyph));
    }
    
    return dockingPoint;
  }
  
  /**
   * Calculate the docking point for an ellipse by calculating the cut of an
   * ellipse with a line.
   *
   * @param x the x coordinate of the center of the ellipse
   * @param y the y coordinate of the center of the ellipse
   * @param z the z coordinate of the center of the ellipse
   * @param width the width of the ellipse
   * @param height the height of the ellipse
   * @param angle the rotation of the ellipse in degrees
   * @return the docking {@link Point} for the given ellipse
   */
  private Point calculateDockingForEllipseSpecies(double x, double y,
    double z, double width, double height, double angle) {
    
    double xCoordinate = 0;
    double yCoordinate = 0;
    double t = correctRotationAngle(angle);
    double tant = (Math.tan(Math.toDegrees(t)) * (width/2d)) / (height/2d);
    double new_width = ((width/2d) * Math.cos(tant));
    double new_height = ((height/2d) * Math.sin(tant));
    
    if ((angle >= 0) && (angle < 90)) {
      xCoordinate = x + new_width;
      yCoordinate= y - new_height;
    } else if ((angle >= 90) && (angle <180)) {
      xCoordinate = x - new_width;
      yCoordinate = y - new_height;
    } else if ((angle >= 180) && (angle <270)) {
      xCoordinate = x - new_width;
      yCoordinate = y + new_height;
    } else {
      xCoordinate = x + new_width;
      yCoordinate = y + new_height;
    }
    
    return new Point(xCoordinate, yCoordinate, z, level, version);
  }
  
  /**
   * Calculate the docking point for a round species.
   *
   * @param x
   *            the x coordinate of the center of the circle
   * @param y
   *            the y coordinate of the center of the circle
   * @param z
   *            the z coordinate of the center of the circle
   * @param c
   *            the radius of the circle
   * @param rotationAngle
   *            the rotation of the circle
   * @param speciesReferenceRole
   *            the {@link SpeciesReferenceRole} for which to calculate the
   *            docking point
   * @return the docking {@link Point} for the given circle and
   *         {@link SpeciesReferenceRole}
   */
  private Point calculateDockingForRoundSpecies(double x, double y, double z, double c,
    double rotationAngle, SpeciesReferenceRole speciesReferenceRole) {
    double xCoordinate = 0;
    double yCoordinate = 0;
    
    double rotationAngleCorrected = correctRotationAngle(rotationAngle);
    if (speciesReferenceRole.equals(SpeciesReferenceRole.PRODUCT) ||
        speciesReferenceRole.equals(SpeciesReferenceRole.SIDEPRODUCT)) {
      c = -c ;
    }
    
    double a = c * Math.abs(Math.sin(Math.toRadians(rotationAngleCorrected)));
    double b = c * Math.abs(Math.cos(Math.toRadians(rotationAngleCorrected)));
    
    if ((rotationAngle >= 0) && (rotationAngle < 90)) {
      xCoordinate = x + b;
      yCoordinate = y + a;
    } else if ((rotationAngle >= 90) && (rotationAngle <180)) {
      xCoordinate = x - a;
      yCoordinate = y + b;
    } else if ((rotationAngle >= 180) && (rotationAngle <270)) {
      xCoordinate = x - b;
      yCoordinate = y - a;
    } else {
      xCoordinate = x + a;
      yCoordinate = y - b;
    }
    return new Point(xCoordinate, yCoordinate, z, level, version);
  }
  
  
  /**
   * Calculate the docking position for quadratic species with the equation of
   * Thales.
   *
   * @param centerOfSpecies
   *            the center {@link Point} of the species
   * @param speciesGlyph
   *            the {@link SpeciesGlyph}
   * @param centerOfReaction
   *            the center {@link Point} of the reaction
   * @return the docking {@link Point} for the given quadratic object
   */
  private Point calculateDockingForQuadraticSpecies(Point centerOfSpecies,
    SpeciesGlyph speciesGlyph, Point centerOfReaction) {
    
    Point dockingPoint = new Point(level, version);
    dockingPoint.setZ(DEFAULT_Z_COORD);
    
    double reacX = centerOfReaction.getX();
    double reacY = centerOfReaction.getY();
    double specX = centerOfSpecies.getX();
    double specY = centerOfSpecies.getY();
    Dimensions dimensions = speciesGlyph.getBoundingBox().getDimensions();
    double width = dimensions.getWidth();
    double height = dimensions.getHeight();
    
    if ((Math.abs(specY - reacY) <= (height/2)) && (specX != reacX)) {
      // the reactionGlyph is left or right of the species
      double b = (width/2);
      double a = (Math.abs(specY - reacY) * b) / Math.abs(specX - reacX);
      
      if (specX < reacX) {
        // reaction is right
        dockingPoint.setX(specX + b);
      } else {
        // reaction is left
        dockingPoint.setX(specX - b);
      }
      if ((specY == reacY) || (specY < reacY)) {
        dockingPoint.setY(specY + a);
      } else {
        dockingPoint.setY(specY - a);
      }
    } else {
      // the reactionGlyph is above or below the species
      double a = (height/2);
      double b = (Math.abs(specX - reacX) * a) / Math.abs(specX - reacX);
      if (specX == reacX) {
        b= 0;
      }
      
      if (specY < reacY) {
        // species is above
        dockingPoint.setY(specY + a);
      } else {
        dockingPoint.setY(specY - a);
      }
      if (specX < reacX) {
        dockingPoint.setX(specX + b);
      } else {
        dockingPoint.setX(specX - b);
      }
    }
    return dockingPoint;
  }
  
  /**
   * Creates a {@link BoundingBox} with the level and version of this layout.
   *
   * @return a {@link BoundingBox} with the level and version of the layout
   */
  protected BoundingBox createBoundingBoxWithLevelAndVersion() {
    return new BoundingBox(layout.getLevel(), layout.getVersion());
  }
  
  /**
   * Find the {@link Layout} to which the given {@link GraphicalObject}
   * belongs.
   *
   * @param graphicalObject
   *            {@link GraphicalObject} for which to find the layout
   * @return the layout which contains the {@link GraphicalObject}
   */
  public Layout findLayout(GraphicalObject graphicalObject) {
    SBase parent = null;
    do {
      parent = graphicalObject.getParent();
    } while ((parent != null) && !(parent instanceof Layout));
    return (Layout) parent;
  }
  
  /**
   * Return the first {@link SpeciesGlyph} from a list of
   * {@link SpeciesReferenceGlyph}s which has the given
   * {@link SpeciesReferenceRole}. Return null if no such {@link SpeciesGlyph}
   * is found.
   *
   * @param speciesReferenceGlyphList
   *            {@link List} containing the {@link SpeciesReferenceGlyph}s
   * @param speciesReferenceRole
   *            the {@link SpeciesReferenceRole} for which to look
   * @return the first {@link SpeciesGlyph} which has the given
   *         {@link SpeciesReferenceRole}
   */
  protected SpeciesGlyph findSpeciesGlyphByRole(
    List<SpeciesReferenceGlyph> speciesReferenceGlyphList,
    SpeciesReferenceRole speciesReferenceRole) {
    SpeciesGlyph specGlyph = null;
    if (speciesReferenceGlyphList != null) {
      for (SpeciesReferenceGlyph specRefGlyph : speciesReferenceGlyphList) {
        if (specRefGlyph.isSetSpeciesGlyph()) {
          SpeciesGlyph speciesGlyph = specRefGlyph.getSpeciesGlyphInstance();
          // TODO: speciesGlyph could be null
          if (speciesGlyph.isSetBoundingBox() && speciesGlyph.getBoundingBox().isSetPosition()) {
            if (specRefGlyph.isSetSpeciesReferenceRole()
                && specRefGlyph.getSpeciesReferenceRole().equals(speciesReferenceRole)) {
              specGlyph = speciesGlyph;
            }
          }
        }
      }
    }
    return specGlyph;
  }
  
  /**
   * Correct the dimensions of a {@link GraphicalObject} by changing its
   * {@link BoundingBox}. According to the SBGN specification, some objects
   * have to be represented as a circle or square and not as an ellipse or
   * rectangle. fit to the glyph. If not this method corrects the layout
   * informations.
   *
   * @param glyph
   *            the {@link GraphicalObject} for which to correct the
   *            dimensions
   */
  public void correctDimensions(GraphicalObject glyph) {
    BoundingBox bb = glyph.getBoundingBox();
    Dimensions dimension = bb.getDimensions();
    double width = dimension.getWidth();
    double height = dimension.getHeight();
    if (glyph instanceof AbstractReferenceGlyph) {
      if (glyph instanceof ReactionGlyph) {
        // TODO: Why does this have to be done here? If it's not properly set, then it shouldn't be corrected this far downstream
        //        if ((width <= height) || (width != (height * 2d))) {
        //          // Compute the minimum because the bounding box of the reaction glyph is always wider than higher
        //          double max = Math.max(width, height);
        //          dimension.setWidth(max);
        //          dimension.setHeight(max/2d);
        //        }
      } else if (glyph instanceof CompartmentGlyph) {
        // do nothing
      } else if (glyph instanceof TextGlyph) {
        // do nothing
      } else {
        AbstractReferenceGlyph nsbGlyph = (AbstractReferenceGlyph) glyph;
        int sboTerm = -1;
        if (nsbGlyph.isSetReference()) {
          sboTerm = nsbGlyph.getNamedSBaseInstance().getSBOTerm();
        }
        if (SBO.isChildOf(sboTerm, SBO.getSimpleMolecule()) ||
            SBO.isChildOf(sboTerm, SBO.getIon()) ||
            SBO.isChildOf(sboTerm, SBO.getEmptySet())) {
          // the glyph is a circle: height==width
          if (width != height) {
            bb.createDimensions(height, height, 0);
          }
        } else  {
          // height must be bigger or equal the width
          if (width < height) {
            // change the both values
            dimension.setWidth(height);
            dimension.setHeight(width);
          }
        }
      }
    }
  }
}
