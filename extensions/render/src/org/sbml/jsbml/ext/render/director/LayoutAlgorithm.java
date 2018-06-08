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

import java.util.Set;

import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * Interface for defining an algorithm to create bounding boxes, dimensions and
 * points missing in the layout.
 * 
 * @author Mirjam Gutekunst
 * @since 1.4
 */
public interface LayoutAlgorithm {
  
  /**
   * method to create a {@link Dimensions} for the layout if it was missing in
   * the layout model
   * 
   * @return a {@link Dimensions}
   */
  public Dimensions createLayoutDimension();
  
  /**
   * method to create a {@link Dimensions} for a {@link CompartmentGlyph} if
   * it was missing in the layout model
   * 
   * @param previousCompartmentGlyph
   *			the last {@link CompartmentGlyph} that had been drawn before
   *          the {@link CompartmentGlyph} that is missing a {@link Dimensions}
   * @return a {@link Dimensions}
   */
  public Dimensions createCompartmentGlyphDimension(CompartmentGlyph previousCompartmentGlyph);
  
  /**
   * method to create a {@link Position} for a {@link CompartmentGlyph} if it
   * was missing in the layout model
   * 
   * @param previousCompartmentGlyph
   *			the last {@link CompartmentGlyph} that had been drawn before
   *          the {@link CompartmentGlyph} that is missing a {@link Position}
   * @return a {@link Position}
   */
  @Deprecated
  public Point createCompartmentGlyphPosition(CompartmentGlyph previousCompartmentGlyph);
  
  /**
   * method to create a {@link Dimensions} for a {@link SpeciesGlyph} if it
   * was missing in the layout model
   * 
   * @return a {@link Dimensions}
   */
  public Dimensions createSpeciesGlyphDimension();
  
  /**
   * method to create the {@link Curve} of a {@link SpeciesReferenceGlyph},
   * starting or ending at the given {@link ReactionGlyph}
   * 
   * @param reactionGlyph at which the {@link Curve} starts or ends
   * @param speciesReferenceGlyph for which the {@link Curve} is missing
   * @return a {@link Curve}
   */
  public Curve createCurve(ReactionGlyph reactionGlyph,
    SpeciesReferenceGlyph speciesReferenceGlyph);
  
  /**
   * method to create a {@link Dimensions} for a {@link TextGlyph} if it was
   * missing in the layout model
   * 
   * @param textGlyph for which the {@link Dimensions} is missing
   * @return a {@link Dimensions}
   */
  public Dimensions createTextGlyphDimension(TextGlyph textGlyph);
  
  /**
   * method to create a {@link Dimensions} for a {@link ReactionGlyph} if it
   * was missing in the layout model
   * 
   * @param reactionGlyph for which the {@link Dimensions} is missing
   * @return a {@link Dimensions}
   */
  public Dimensions createReactionGlyphDimension(ReactionGlyph reactionGlyph);
  
  /**
   * method to create a {@link Dimensions} for a {@link SpeciesReferenceGlyph}
   * if it was missing in the layout model
   * 
   * @param reactionGlyph from which the {@link SpeciesReferenceGlyph} is drawn
   * @param speciesReferenceGlyph for which the {@link Dimensions} is missing
   * @return a {@link Dimensions}
   */
  public Dimensions createSpeciesReferenceGlyphDimension(
    ReactionGlyph reactionGlyph,
    SpeciesReferenceGlyph speciesReferenceGlyph);
  
  /**
   * method to create a {@link BoundingBox} for every kind of {@link GraphicalObject}.
   * 
   * @param glyph
   * @param specRefGlyph
   * @return
   */
  public BoundingBox createGlyphBoundingBox(GraphicalObject glyph, SpeciesReferenceGlyph specRefGlyph);
  
  /**
   * @param layout the {@link Layout} to be set for this LayoutAlgorithm
   */
  public void setLayout(Layout layout);
  
  /**
   * @return the {@link Layout} set for this LayoutAlgorithm;
   */
  public Layout getLayout();
  
  /**
   * @return true if a {@link Layout} is set for this LayoutAlgorithm, else false
   */
  public boolean isSetLayout();
  
  /**
   * method to calculate the rotation angle of the lines from the reaction box,
   * in degrees
   * 
   * @param reactionGlyph for which the rotation angle shall be calculated
   * @return the rotation angle in degrees
   */
  public double calculateReactionGlyphRotationAngle(ReactionGlyph reactionGlyph);
  
  /**
   * Add a fully layouted glyph (i.e. glyph with boundingbox, dimensions and
   * position) to the input of the algorithm.
   */
  public void addLayoutedGlyph(GraphicalObject glyph);
  
  /**
   * Add a glyph with missing layout information (i.e. no dimensions or no
   * position specified) to the input of the algorithm.
   */
  public void addUnlayoutedGlyph(GraphicalObject glyph);
  
  /**
   * Add a layouted edge to the input of the algorithm.
   * 
   * @param srg species node of the edge
   * @param rg process node of the edge
   */
  public void addLayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg);
  
  /**
   * Add an unlayouted edge to the input of the algorithm.
   * 
   * @param srg species node of the edge
   * @param rg process node of the edge
   */
  public void addUnlayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg);
  
  /**
   * Return the input set of unlayouted glyphs with completed layout
   * information.
   */
  public Set<GraphicalObject> completeGlyphs();
  
}
