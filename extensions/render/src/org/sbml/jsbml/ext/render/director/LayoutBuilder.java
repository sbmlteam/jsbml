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

import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * LayoutBuilder provides methods to build graphical representations for
 * all glyph types.
 * 
 * @author Mirjam Gutekunst
 * @since 1.4
 * @param <P> Type of the product.
 */
public interface LayoutBuilder<P> {
    
  /**
   * method for preparations that have to be done at the beginning of the
   * builder, e.g., the commands at the beginning of a LaTeX document
   * 
   * @param layout
   */
  public void builderStart(Layout layout);
  
  /**
   * method for the drawing of a compartment with the size and position of the
   * {@link BoundingBox} of the given {@link CompartmentGlyph}
   * 
   * @param compartmentGlyph
   */
  public void buildCompartment(CompartmentGlyph compartmentGlyph);
  
  /**
   * method for the drawing of a connecting arc or line with the size and
   * position specified by the given SpeciesReferenceGlyph
   * 
   * @param srg species reference glyph that defines the connecting arc
   * @param rg associated reaction glyph
   * @param curveWidth line width of the arc
   */
  public void buildConnectingArc(SpeciesReferenceGlyph srg, ReactionGlyph rg, double curveWidth);
  
  /**
   * method to draw an arc between the two base points of the given {@link CubicBezier}.
   * @param cubicBezier
   * @param lineWidth
   */
  public void buildCubicBezier(CubicBezier cubicBezier, double lineWidth);
  
  /**
   * method for the drawing of an entity pool node with the given size and
   * position of the {@link BoundingBox} of the given {@link SpeciesGlyph}
   * 
   * @param speciesGlyph of the entity pool node
   * @param cloneMarker true if a clone marker is set for this speciesGlyph
   */
  public void buildEntityPoolNode(SpeciesGlyph speciesGlyph, boolean cloneMarker);
  
  /**
   * method for the drawing of a process node with the size and position of
   * the {@link BoundingBox} of the given {@link ReactionGlyph}
   * 
   * @param rotationAngle by which the lines from the reaction box are rotated
   * @param reactionGlyph the glyph for the process node to be drawn
   * @param curveWidth
   */
  public void buildProcessNode(ReactionGlyph reactionGlyph, double rotationAngle, double curveWidth);
  
  
  /**
   * method for drawing/writing the given {@link TextGlyph}
   * 
   * @param textGlyph
   */
  public void buildTextGlyph(TextGlyph textGlyph);
  
  /**
   * method for actions that have to be done at the end of the builder e.g.
   * the commands at the end of a LaTeX document
   */
  public void builderEnd();
  
  /**
   * @return the resulting product of the builder, an object of the type P
   */
  public P getProduct();
  
  /**
   * method to check if the builder has produced a complete product
   * 
   * @return {@code true}, if product is not empty, else {@code false}
   */
  public boolean isProductReady();
  
}
