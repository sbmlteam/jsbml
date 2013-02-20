/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2013 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package de.zbit.sbml.layout;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.NamedSBaseGlyph;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.Position;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * @author Andreas Dr&auml;ger
 * @date 08:43:48
 * @since 1.1
 * @version $Rev$
 */
public abstract class SimpleLayoutAlgorithm implements LayoutAlgorithm {

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
	 * @version $Rev: 189 $
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
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(SimpleLayoutAlgorithm.class.toString());

	/**
	 * 
	 */
	public SimpleLayoutAlgorithm() {
		this.setOfLayoutedGlyphs = new HashSet<GraphicalObject>();
		this.setOfUnlayoutedGlyphs = new HashSet<GraphicalObject>();
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
		String error = "No coordinates given for the position of {0} bounding box ";
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
				if ((startX - endX) >= (endY - startY) || (endY == startY)) {
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
			if (endY > startY || endY == startY) {
				if ((endX - startX) >= (endY - startY) || endY == startY) {
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
			} else if (endY < startY){
				return RelativePosition.ABOVE;
			} else { // then endX == startX && endY == startY
				logger.warning("could not compute relative position for points " + startGlyph + " and " + endGlyph);
				return RelativePosition.UNDEFINED;
			}
		}
	}

	/**
	 * The layout object
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
	 * This method calculates the average position of a curve at the direction of the reaction glyph,
	 * if the role of a species reference glyph is PRODUCT, the start point of the curve is at the
	 * reaction glyph, else the start point is at the species and the end point at the reaction glyph.
	 * 
	 * @param specRefRole
	 * @param specRefGlyphList
	 * @return a Position
	 */
	protected Position calculateAverageCurvePosition(SpeciesReferenceRole specRefRole,
			List<SpeciesReferenceGlyph> specRefGlyphList) {
		double x = 0;
		double y = 0;
		double z = 0;

		double count = 0;

		for (SpeciesReferenceGlyph specRefGlyph : specRefGlyphList) {
			if (specRefGlyph.isSetCurve()) {
				Curve curve = specRefGlyph.getCurve();
				for (CurveSegment curveSegment : curve.getListOfCurveSegments()) {
					if (specRefGlyph.isSetSpeciesReferenceRole()
							&& specRefGlyph.getSpeciesReferenceRole().equals(specRefRole)) {
						if (specRefRole.equals(SpeciesReferenceRole.PRODUCT) || specRefRole.equals(SpeciesReferenceRole.SIDEPRODUCT)) {
							//the start point is the reaction glyph
							if (curveSegment.isSetStart()) {
								Point startPoint = curveSegment.getStart();
								x = x + startPoint.getX();
								y = y + startPoint.getY();
								z = z + startPoint.getZ();
								count++;
							}
						} else {
							//SpeciesReference is a reactant, so the start is the SpeciesReference and the end is the reaction glyph
							if (curveSegment.isSetEnd()) {
								Point endPoint = curveSegment.getEnd();
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

		if (count != 0) {
			x = x / count;
			y = y / count;
			z = z / count;
		} else {
			//the coordinates are 0 too, so do nothing
		}

		return new Position(new Point(x, y, z, layout.getLevel(), layout.getVersion()));
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
	protected Position calculateAverageSpeciesPosition(SpeciesReferenceRole specRefRole, 
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

		return new Position(new Point(x, y, z, layout.getLevel(), layout.getVersion()));
	}

	/**
	 * Compute the Coordinates of the central point of a {@link SpeciesReferenceGlyph}.
	 * @param specRefRole
	 * @param speciesRefGlyphList
	 * @return the central point
	 */
	protected Point calculateCenter(GraphicalObject glyph) {

		Point middle = new Point(layout.getLevel(), layout.getVersion());
		double x = 0;
		double y = 0;
		double z = 0;

		if (glyph.isSetBoundingBox() && glyph.getBoundingBox().isSetPosition()) {
			Point position = glyph.getBoundingBox().getPosition();
			Dimensions dimensions = glyph.getBoundingBox().getDimensions();
			x = position.getX() + (dimensions.getWidth() / 2d);
			y = position.getY() + (dimensions.getHeight() / 2d);
			z = position.getZ() + (dimensions.getDepth() / 2d);
		}
		// set the new coordinates
		middle.setX(x);
		middle.setY(y);
		middle.setZ(z);

		return middle;
	}

	/**
	 * method calculates the straight length of the {@link Curve} with Pythagoras' theorem,
	 * c = squareroot(a<sup>2</sup> + b<sup>2</sup>), 
	 * supposes rectangular triangle between start point and end point of the {@link Curve}
	 * 
	 * @param curve for which the straight length shall be calculated
	 * @return the calculated length
	 */
	public double calculateLength(Point startPoint, Point endPoint) {
		double a = 0;
		double b = 0;

		double startX = startPoint.getX();
		double startY = startPoint.getY();
		double endX = endPoint.getX();
		double endY = endPoint.getY();

		if (startX <= endX) {
			if (startY >= endY) {
				a = endX - startX; //width
				b = startY - endY; //height
			} else {
				a = endY - startY; //height
				b = endX - startX; //width
			}
		} else {
			if (startY >= endY) {
				a = startY - endY; //height
				b = startX - endX; //width

			} else {
				a = startX - endX; //width
				b = endY - startY; //height
			}
		}
		// return the hypotenuse
		return Math.hypot(a, b);
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
		double c = reacGlyph.getBoundingBox().getDimensions().getWidth()/2d;
		double h = reacGlyph.getBoundingBox().getDimensions().getHeight()/2d;
		double b = Math.abs(Math.cos(Math.toRadians(rotationAngle_new)) * c);
		double a = Math.abs(Math.sin(Math.toRadians(rotationAngle_new)) * c);
		double otherA = Math.abs(Math.sin(Math.toRadians(90 - rotationAngle_new))) * h;
		double otherB = Math.abs(Math.cos(Math.toRadians(90 - rotationAngle_new))) * h;

		//TODO: make it shorter

		if (rotationAngle >= 0 && rotationAngle < 90) {
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

		} else if (rotationAngle >= 90 && rotationAngle <180) {
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

		} else if (rotationAngle >= 180 && rotationAngle <270) {
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

		if (specRefRole.equals(SpeciesReferenceRole.SUBSTRATE) ||
				specRefRole.equals(SpeciesReferenceRole.SIDESUBSTRATE)) {
			return dockingPointToSubstrate;
		} else if (specRefRole.equals(SpeciesReferenceRole.PRODUCT) ||
				specRefRole.equals(SpeciesReferenceRole.SIDEPRODUCT)){ 
			return dockingPointToProduct;
		} else { // Species docks at the ReactionGlyph directly
			if (modifierPosition.equals(RelativePosition.LEFT)) {
				return dockingPointOtherLeft;
			} else {
				return dockingPointOtherRight;
			}
		}
	}

	/**
	 * 
	 */
	protected double correctRotationAngle(double rotationAngle) {
		double correctedAngle = rotationAngle % 90;
		return correctedAngle < 0 ? correctedAngle + 90d : correctedAngle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zbit.sbml.layout.LayoutAlgorithm#createSpeciesReferenceGlyphPosition(
	 * 						ReactionGlyph reactionGlyph, SpeciesReferenceGlyph speciesReferenceGlyph)
	 */
	protected Position createSpeciesReferenceGlyphPosition(ReactionGlyph reactionGlyph, SpeciesReferenceGlyph specRefGlyph) {

		double x = 0;
		double y = 0;
		double z = 0;

		Curve curve = specRefGlyph.getCurve();
		if (curve == null) {
			curve = createCurve(reactionGlyph, specRefGlyph);
		}

		for (CurveSegment curveSegment : curve.getListOfCurveSegments()) {
			Point startPoint = curveSegment.getStart();
			Point endPoint = curveSegment.getEnd();
			double startX = startPoint.getX();
			double startY = startPoint.getY();
			double startZ = startPoint.getZ();
			double endX = endPoint.getX();
			double endY = endPoint.getY();
			double endZ = endPoint.getZ();

			if (curveSegment.isSetBasePoint1() && curveSegment.isSetBasePoint2()) {

				Point basePoint1 = curveSegment.getBasePoint1();
				Point basePoint2 = curveSegment.getBasePoint2();

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

		return new Position(new Point(x, y, z, layout.getLevel(), layout.getVersion()));
	}

	/**
	 * @param reactionGlyph
	 * @return
	 */
	protected Point createReactionGlyphPositionNew(ReactionGlyph reactionGlyph) {
		List<SpeciesReferenceGlyph> speciesReferenceGlyphList = reactionGlyph.getListOfSpeciesReferenceGlyphs();
		SpeciesGlyph product = getProductOrSubstrateSpeciesGlyph(speciesReferenceGlyphList, SpeciesReferenceRole.PRODUCT);
		SpeciesGlyph substrate = getProductOrSubstrateSpeciesGlyph(speciesReferenceGlyphList, SpeciesReferenceRole.SUBSTRATE);

		if ((product == null) || (substrate == null)) {
			for(SpeciesReferenceGlyph specRef : speciesReferenceGlyphList) {
				if (LayoutDirector.isSubstrate(specRef)) {
					substrate = specRef.getSpeciesGlyphInstance();
				} else if (LayoutDirector.isProduct(specRef)) {
					product = specRef.getSpeciesGlyphInstance();
				}
			}
			if ((product == null) || (substrate == null)) {
				logger.warning("Cannot find product or substrate in list of species reference glyphs for reaction glyph "
						+ reactionGlyph.getId());
				// TODO fall back to another positioning mechanism
				return new Position(new Point(0, 0, 0, level, version));
			}
		}

		Point substrateCenter = calculateCenter(substrate);
		Point productCenter = calculateCenter(product);
		Dimensions rgDimensions = reactionGlyph.getBoundingBox().getDimensions();

		Point center = calculateCenterOfPoints(substrateCenter, productCenter);
		Point upperLeft = new Point(center.getX() - rgDimensions.getWidth()/2,
				center.getY() - rgDimensions.getHeight()/2,
				center.getZ() - rgDimensions.getDepth()/2,
				level, version);
		logger.fine("substrate center is " + substrateCenter.toString());
		logger.fine("product center is " + productCenter.toString());
		logger.fine("center is " + center.toString());
		logger.fine("upper left is " + upperLeft.toString());
		return upperLeft;
	}

	/**
	 * @param substrateCenter
	 * @param productCenter
	 * @return
	 */
	private Point calculateCenterOfPoints(Point p1, Point p2) {
		Point p = new Point(level, version);
		p.setX((p1.getX() + p2.getX()) / 2);
		p.setY((p1.getY() + p2.getY()) / 2);
		p.setZ((p1.getZ() + p2.getZ()) / 2);
		return p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zbit.sbml.layout.LayoutAlgorithm#createReactionGlyphPositon(ReactionGlyph reactionGlyph)
	 */
	protected Position createReactionGlyphPosition(ReactionGlyph reactionGlyph) {
		double x = 0;
		double y = 0;
		double z = 0;

		List<SpeciesReferenceGlyph> speciesReferenceGlyphList = reactionGlyph.getListOfSpeciesReferenceGlyphs();
		Dimensions reacGlyphDimension;

		if (reactionGlyph.isSetBoundingBox() && reactionGlyph.getBoundingBox().isSetDimensions()) {
			reacGlyphDimension = reactionGlyph.getBoundingBox().getDimensions();
		} else {
			reacGlyphDimension = createReactionGlyphDimension(reactionGlyph);
		}

		SpeciesGlyph product = getProductOrSubstrateSpeciesGlyph(speciesReferenceGlyphList, SpeciesReferenceRole.PRODUCT);
		SpeciesGlyph substrate = getProductOrSubstrateSpeciesGlyph(speciesReferenceGlyphList, SpeciesReferenceRole.SUBSTRATE);
		if (product == null || substrate==null) {
			for(SpeciesReferenceGlyph specRef : speciesReferenceGlyphList) {
				if (LayoutDirector.isSubstrate(specRef)) {
					substrate = specRef.getSpeciesGlyphInstance();
				} else if (LayoutDirector.isProduct(specRef)) {
					product = specRef.getSpeciesGlyphInstance();
				}
			}
			if (product == null || substrate==null) {
				logger.warning("Cannot find product or substrate in list of species reference glyphs for reaction glyph "
						+ reactionGlyph.getId());
				// TODO fall back to another positioning mechanism
				return new Position(new Point(x, y, z, level, version));
			}
		}

		Point substratePointOfMiddle = calculateCenter(substrate);
		Point productPointOfMiddle = calculateCenter(product);

		// the computation of the position is equal for every relativePosition (left,right,above,below and undefined)
		x = ((Math.max(productPointOfMiddle.getX(), substratePointOfMiddle.getX())
				- Math.min(productPointOfMiddle.getX(), substratePointOfMiddle.getX())) 
				/ 2d) + Math.min(productPointOfMiddle.getX(), substratePointOfMiddle.getX()) - (reacGlyphDimension.getWidth() / 2d);
		y = ((Math.max(productPointOfMiddle.getY(), substratePointOfMiddle.getY()) - Math.min(productPointOfMiddle.getY(), substratePointOfMiddle.getY()))
				/ 2d) + Math.min(productPointOfMiddle.getY(), substratePointOfMiddle.getY()) - (reacGlyphDimension.getHeight() / 2d);
		z = ((Math.max(productPointOfMiddle.getZ(), substratePointOfMiddle.getZ()) - Math.min(productPointOfMiddle.getZ(), substratePointOfMiddle.getZ()))
				/ 2d) + Math.min(productPointOfMiddle.getZ(), substratePointOfMiddle.getZ()) - (reacGlyphDimension.getDepth() / 2d);

		return new Position(new Point(x, y, z, level, version));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zbit.sbml.layout.LayoutAlgorithm#createReactionGlyphDimension(ReactionGlyph reactionGlyph)
	 */
	public Dimensions createReactionGlyphDimension(ReactionGlyph reactionGlyph) {

		double width = 20;
		double height = 10;
		double depth = 0;

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


		ListOf<SpeciesReferenceGlyph> curveList = new ListOf<SpeciesReferenceGlyph>();
		ListOf<SpeciesReferenceGlyph> speciesGlyphList = new ListOf<SpeciesReferenceGlyph>();

		ListOf<SpeciesReferenceGlyph> speciesReferenceGlyphList = reactionGlyph.getListOfSpeciesReferenceGlyphs();
		for (SpeciesReferenceGlyph specRefGlyph : speciesReferenceGlyphList) {
			if (specRefGlyph.isSetCurve()) {
				curveList.add(specRefGlyph);
			} else {
				if (specRefGlyph.isSetSpeciesGlyph()) {
					speciesGlyphList.add(specRefGlyph);
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

	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.LayoutAlgorithm#calculateReactionGlyphRotationAngle(org.sbml.jsbml.ext.layout.ReactionGlyph)
	 */
	public double calculateReactionGlyphRotationAngle(ReactionGlyph reactionGlyph) {
		double rotationAngle = 0;

		if (reactionGlyph.isSetListOfSpeciesReferencesGlyph()) {
			ListOf<SpeciesReferenceGlyph> speciesRefGlyphList = reactionGlyph.getListOfSpeciesReferenceGlyphs();

			// get the substrate and the product of this reaction
			SpeciesGlyph substrate = getProductOrSubstrateSpeciesGlyph(speciesRefGlyphList, SpeciesReferenceRole.SUBSTRATE);
			SpeciesGlyph product = getProductOrSubstrateSpeciesGlyph(speciesRefGlyphList, SpeciesReferenceRole.PRODUCT);

			if ((substrate == null) || (product == null)) {
				for(SpeciesReferenceGlyph specRef : speciesRefGlyphList) {
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
			if (reactionGlyph.isSetUserObjects() && reactionGlyph.getUserObject("SPECIAL_ROTATION_NEEDED")!=null) {
				for(SpeciesReferenceGlyph specRefGlyph : speciesRefGlyphList) {
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
						if(curve.isSetListOfCurveSegments()) {
							firstCentralPoint = curve.getListOfCurveSegments().getLast().getEnd();
						}
					}
					
					// Second point: the start point from the first curve segment of the product
					if (specRefGlyph.isSetSpeciesReferenceRole()
							&& specRefGlyph.getSpeciesReferenceRole().equals(SpeciesReferenceRole.PRODUCT)) {
						if(curve.isSetListOfCurveSegments()) {
							secondCentralPoint = curve.getListOfCurveSegments().getFirst().getStart();
						}
					}
				}
			}
			if(firstCentralPoint.getX() == secondCentralPoint.getX() && firstCentralPoint.getY() == secondCentralPoint.getY()) {
				secondCentralPoint = calculateCenter(reactionGlyph);
				logger.warning(MessageFormat.format("The two points for computing rotation are equal for reaction glyph {0}",
				reactionGlyph.getId()));
			} else {
				rotationAngle = calculateRotationAngle(firstCentralPoint, secondCentralPoint);
			}
		}
		return rotationAngle;
	}


	/**
	 * method to calculate the rotation angle of the {@link Curve} of the given {@link SpeciesReferenceGlyph},
	 * with Pythagoras' theorem, rotation angle alpha = arcossin(a / c),
	 * supposes rectangular triangle between the start point and the end point of the {@link Curve},
	 * in degrees
	 * 
	 * @param speciesReferenceGlyph for which the rotation angle shall be calculated
	 * @return the rotation angle in degrees
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
	 * Attention: We changed the dockingPoints, now it's not only left, right, above and below, so
	 * please use {@link SimpleLayoutAlgorithm#calculateSpeciesGlyphDockingPosition} instead.
	 * <br>
	 * <br>
	 * Computes the position of the {@link SpeciesGlyph} of interest and gives back the docking {@link Position} for the curve.
	 * @param positionOfInterest 
	 * @param relativeSpeciesGlyphPosition
	 * @param specGlyph
	 * @return Position
	 */
	@Deprecated
	protected Position calculateOldSpeciesGlyphDockingPosition(Point middleOfSpecies, RelativePosition relativeSpeciesGlyphPosition,
			SpeciesGlyph specGlyph) {

		Position dockingPosition = null;

		// the coordinates of the species glyph
		double x = middleOfSpecies.getX();
		double y = middleOfSpecies.getY();
		double z = middleOfSpecies.getZ();
		double width = specGlyph.getBoundingBox().getDimensions().getWidth();
		double height = specGlyph.getBoundingBox().getDimensions().getHeight();

		/*
		 *  create the different points for the different cases
		 * ATTENTION: dockingLeft means, that the curve will dock left, 
		 * so the SpeciesGlyph of interest is at the right of the reference object
		 */
		Point dockingLeft = new Point(x - (width/2d), y, z, level, version);
		Point dockingRight = new Point(x + (width/2d), y, z, level, version);
		Point dockingAbove = new Point(x, y - (height/2d), z, level, version);
		Point dockingBelow = new Point(x, y + (height/2d), z, level, version);

		// check the cases of relative position
		if (relativeSpeciesGlyphPosition.equals(RelativePosition.ABOVE)) {
			dockingPosition = new Position(dockingBelow);
		} else if (relativeSpeciesGlyphPosition.equals(RelativePosition.BELOW)) {
			dockingPosition = new Position(dockingAbove);
		} else if (relativeSpeciesGlyphPosition.equals(RelativePosition.LEFT)) {
			dockingPosition = new Position(dockingRight);
		} else if (relativeSpeciesGlyphPosition.equals(RelativePosition.RIGHT)) {
			dockingPosition = new Position(dockingLeft);
		}

		// return the correct docking position for this species glyph
		return dockingPosition;
	}




	/**
	 * Computes the position of the {@link SpeciesGlyph} of interest and gives back the docking {@link Point} for the curve.
	 * @param middleOfSpecies
	 * @param reactionGlyph
	 * @param relativeSpeciesGlyphPosition
	 * @param specGlyph
	 * @return dockingPoint
	 */
	protected Point calculateSpeciesGlyphDockingPosition(Point middleOfSpecies,
			ReactionGlyph reactionGlyph, SpeciesReferenceRole specRefRole, SpeciesGlyph specGlyph) {
		Point dockingPoint = null;

		// the coordinates of the species glyph
		double x = middleOfSpecies.getX();
		double y = middleOfSpecies.getY();
		double z = middleOfSpecies.getZ();
		int sboTerm = -1;
		double width = specGlyph.getBoundingBox().getDimensions().getWidth();
		double height = specGlyph.getBoundingBox().getDimensions().getHeight();
		if (specGlyph.isSetSpecies()) {
			sboTerm = specGlyph.getSpeciesInstance().getSBOTerm();
		} else if (specGlyph.isSetSBOTerm()) {
			sboTerm = specGlyph.getSBOTerm();
		}

		// computing angle
		double t = 0;
		if(specRefRole.equals(SpeciesReferenceRole.PRODUCT) ||
				specRefRole.equals(SpeciesReferenceRole.SIDEPRODUCT)) {
			t = calculateRotationAngle(calculateCenter(reactionGlyph), middleOfSpecies);
		} else {
			t = calculateRotationAngle(middleOfSpecies, calculateCenter(reactionGlyph));
		}

		if(SBO.isChildOf(sboTerm, SBO.getUnknownMolecule()) ||
				sboTerm == -1) {
			// species is an ellipse
			dockingPoint = calculateDockingForEllipseSpecies(x, y, z, width, height, calculateRotationAngle(middleOfSpecies, calculateCenter(reactionGlyph)));
		} else if (SBO.isChildOf(sboTerm, SBO.getSimpleMolecule()) || 
				SBO.isChildOf(sboTerm, SBO.getEmptySet())) {
			//species is round
			double c = height/2d;
			dockingPoint = calculateDockingForRoundSpecies(x, y, z, c, t, specRefRole);
		} else {
			// species is not round or an ellipse
			dockingPoint = calculateDockingForQuadraticSpecies(middleOfSpecies, specGlyph, calculateCenter(reactionGlyph));
		}
		return new Position(dockingPoint);
	}


	/**
	 * Method computes the docking points for ellipses like computing the cut
	 * of an ellipse and a line. In the end the computed docking point is returned.
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param angle
	 * @return
	 */
	private Point calculateDockingForEllipseSpecies(double x, double y,
			double z, double width, double height, double angle) {
		
		double xCoordinate = 0;
		double yCoordinate = 0;
		double t = correctRotationAngle(angle);
		double tant = (Math.tan(Math.toDegrees(t)) * (width/2d)) / (height/2d);
		double new_width = ((width/2d) * Math.cos(tant));
		double new_height = ((height/2d) * Math.sin(tant));
		
		if (angle >= 0 && angle < 90) {
			xCoordinate = x + new_width;
			yCoordinate= y - new_height;
		} else if (angle >= 90 && angle <180){
			xCoordinate = x - new_width;
			yCoordinate = y - new_height;
		} else if (angle >= 180 && angle <270) {
			xCoordinate = x - new_width;
			yCoordinate = y + new_height;
		} else {
			xCoordinate = x + new_width;
			yCoordinate = y + new_height;
		}
		
		return new Point(xCoordinate, yCoordinate, z, level, version);
	}

	/**
	 * Calculates the docking position/ point for round species
	 * @param x
	 * @param y
	 * @param z
	 * @param c
	 * @param rotationAngle
	 * @param specRole 
	 * @return
	 */
	private Point calculateDockingForRoundSpecies(double x, double y, double z, double c,
			double rotationAngle, SpeciesReferenceRole specRole) {
		double xCoordinate = 0;
		double yCoordinate = 0;

		double rotationAngleCorrected = correctRotationAngle(rotationAngle);
		if (specRole.equals(SpeciesReferenceRole.PRODUCT) ||
				specRole.equals(SpeciesReferenceRole.SIDEPRODUCT)) {
			c = -c ;
		}

		double a = c * Math.abs(Math.sin(Math.toRadians(rotationAngleCorrected)));
		double b = c * Math.abs(Math.cos(Math.toRadians(rotationAngleCorrected)));

		if (rotationAngle >= 0 && rotationAngle < 90) {
			xCoordinate = x + b;
			yCoordinate = y + a;
		} else if (rotationAngle >= 90 && rotationAngle <180) {
			xCoordinate = x - a;
			yCoordinate = y + b;
		} else if (rotationAngle >= 180 && rotationAngle <270) {
			xCoordinate = x - b;
			yCoordinate = y - a;
		} else {
			xCoordinate = x + a;
			yCoordinate = y - b;
		}
		return new Point(xCoordinate, yCoordinate, z, level, version);
	}


	/**
	 * Calculates the docking position for quadratic species with the equation of Thales.
	 * @param specGlyph 
	 * @param middleOfSpecies 
	 * @param middleOfReaction
	 * @return dockingPoint
	 */
	private Point calculateDockingForQuadraticSpecies(Point middleOfSpecies, 
			SpeciesGlyph specGlyph, Point middleOfReaction) {

		Point dockingPoint = new Point(level, version);

		double reacX = middleOfReaction.getX();
		double reacY = middleOfReaction.getY();
		double specX = middleOfSpecies.getX();
		double specY = middleOfSpecies.getY();
		double width = specGlyph.getBoundingBox().getDimensions().getWidth();
		double height = specGlyph.getBoundingBox().getDimensions().getHeight();

		if((Math.abs(specY - reacY) <= (height/2)) && specX != reacX) {
			//the reactionGlyph is left or right of the species
			double b = (width/2); 
			double a = (Math.abs(specY - reacY) * b) / Math.abs(specX - reacX);

			if(specX < reacX) {
				// reac is right
				dockingPoint.setX(specX + b);
			} else {
				// reac is left
				dockingPoint.setX(specX - b);
			}
			if (specY == reacY || specY < reacY) {
				dockingPoint.setY(specY + a); 
			} else {
				dockingPoint.setY(specY - a); 
			}
		} else {
			//the reactionGlyph is above or below the species
			double a = (height/2);
			double b = (Math.abs(specX - reacX) * a) / Math.abs(specX - reacX);
			if (specX == reacX) {
				b= 0;
			}

			if(specY < reacY) {
				// spec is above
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
		dockingPoint.setZ(0.0);
		return dockingPoint;
	}

	/**
	 * Creates a {@link BoundingBox} with the level and version of this layout.
	 * @return BoundingBox
	 */
	protected BoundingBox createBoundingBoxWithLevelAndVersion() {
		BoundingBox boundingBox = new BoundingBox();
		boundingBox.setLevel(layout.getLevel());
		boundingBox.setVersion(layout.getVersion());
		return boundingBox;
	}

	/**
	 * 
	 * @param go
	 * @return
	 */
	public Layout findLayout(GraphicalObject go) {
		SBase parent = null;
		do {
			parent = go.getParent();
		} while ((parent != null) && !(parent instanceof Layout));
		return (Layout) parent;
	}

	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.LayoutAlgorithm#getLayout()
	 */
	public Layout getLayout() {
		return this.layout;
	}

	/**
	 * 
	 * @param specRefGlyphList
	 * @param role
	 * @return SpeciesGlyph
	 */
	protected SpeciesGlyph getProductOrSubstrateSpeciesGlyph(
			List<SpeciesReferenceGlyph> specRefGlyphList,
			SpeciesReferenceRole role) {
		SpeciesGlyph specGlyph = null;

		for (SpeciesReferenceGlyph specRefGlyph : specRefGlyphList) {
			if (specRefGlyph.isSetSpeciesGlyph()) {
				SpeciesGlyph speciesGlyph = specRefGlyph.getSpeciesGlyphInstance();
				if (speciesGlyph.isSetBoundingBox() && speciesGlyph.getBoundingBox().isSetPosition()) {
					if (specRefGlyph.isSetSpeciesReferenceRole()
							&& specRefGlyph.getSpeciesReferenceRole().equals(role)) {
						specGlyph = speciesGlyph;
					}
				}
			}
		}
		return specGlyph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zbit.sbml.layout.LayoutAlgorithm#isSetLayout()
	 */
	public boolean isSetLayout() {
		return (layout != null);
	}


	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.LayoutAlgorithm#setModel(Model model)
	 */
	public void setLayout(Layout layout) {
		this.layout = layout;
		this.level = layout.getLevel();
		this.version = layout.getVersion();
	}

	/**
	 * This method gets a layouted glyph and checks if the dimensions
	 * fit to the glyph. If not this method corrects the layout informations.
	 * @param glyph
	 */
	public void correctDimensions(GraphicalObject glyph) {
		BoundingBox bb = glyph.getBoundingBox();
		Dimensions dimension = bb.getDimensions();
		double width = dimension.getWidth();
		double height = dimension.getHeight();
		if (glyph instanceof NamedSBaseGlyph) {
			if (glyph instanceof ReactionGlyph) {
				if (width < height || width != height*2d) {
					// Compute the minimum because the bounding box of the reaction glyph is always wider than higher
					double max = Math.max(width, height);
					dimension.setWidth(max);
					dimension.setHeight(max/2d);
				}
			} else if (glyph instanceof CompartmentGlyph) {
				//do nothing?
			} else if (glyph instanceof TextGlyph) {
				//do nothing?
			} else {
				NamedSBaseGlyph nsbGlyph = (NamedSBaseGlyph) glyph; 
				int sboTerm = -1;
				if (nsbGlyph.isSetNamedSBase()) {
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
