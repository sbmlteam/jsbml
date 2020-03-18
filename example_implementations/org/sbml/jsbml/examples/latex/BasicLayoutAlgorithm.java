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
package org.sbml.jsbml.examples.latex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.Dimensions;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.director.SimpleLayoutAlgorithm;
import org.sbml.jsbml.util.Pair;

/**
 * Example-DOCUMENTATION TODO
 * (also improve algorithm or use/adapt the one from SBML2LaTeX)
 * @author David Vetter
 *
 */
public class BasicLayoutAlgorithm extends SimpleLayoutAlgorithm {

  private Set<Pair<SpeciesReferenceGlyph, ReactionGlyph>> laidoutEdges, unlaidoutEdges;
  
  public BasicLayoutAlgorithm() {
    super();
    laidoutEdges = new HashSet<Pair<SpeciesReferenceGlyph, ReactionGlyph>>();
    unlaidoutEdges = new HashSet<Pair<SpeciesReferenceGlyph, ReactionGlyph>>();
  }
  
  @Override
  public Dimensions createLayoutDimension() {
    double minX = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double minY = minX;
    double maxY = maxX;
    for (GraphicalObject gl : setOfLayoutedGlyphs) {
      minX = Math.min(minX, gl.getBoundingBox().getPosition().getX());
      minY = Math.min(minY, gl.getBoundingBox().getPosition().getY());
      maxX = Math.max(maxX, gl.getBoundingBox().getPosition().getX()
        + gl.getBoundingBox().getDimensions().getWidth());
      maxY = Math.max(maxY, gl.getBoundingBox().getPosition().getY()
        + gl.getBoundingBox().getDimensions().getHeight());
    }
    return new Dimensions(maxX - minX, maxY - minY, 0, level, version);
  }


  @Override
  public Dimensions createCompartmentGlyphDimension(
    CompartmentGlyph previousCompartmentGlyph) {
    // Just go with dummy values
    return new Dimensions(300, 300, 0, level, version);
  }


  @Override
  public Point createCompartmentGlyphPosition(
    CompartmentGlyph previousCompartmentGlyph) {
    // Just go with dummy values
    return new Point(0, 0, 0);
  }


  @Override
  public Dimensions createSpeciesGlyphDimension() {
    // What is the purpose of this method?
    return new Dimensions(40, 20, 0, level, version);
  }


  @Override
  public Curve createCurve(ReactionGlyph reactionGlyph,
    SpeciesReferenceGlyph speciesReferenceGlyph) {
    // TODO Does this suffice?
    Curve result = new Curve(level, version);
    SpeciesGlyph sg = speciesReferenceGlyph.getSpeciesGlyphInstance();
    Point speciesPosition = sg.getBoundingBox().getPosition().clone();
    Dimensions speciesDimensions = sg.getBoundingBox().getDimensions().clone();
    speciesPosition.setX(speciesDimensions.getWidth() + speciesPosition.getX());
    speciesPosition.setY(speciesDimensions.getHeight() + speciesPosition.getY());
    Point speciesDockingPosition = calculateSpeciesGlyphDockingPosition(
      speciesPosition, reactionGlyph, speciesReferenceGlyph.getRole(),
      speciesReferenceGlyph.getSpeciesGlyphInstance());
    
    Point reactionDockingPosition = calculateReactionGlyphDockingPoint(
      reactionGlyph, calculateReactionGlyphRotationAngle(reactionGlyph),
      speciesReferenceGlyph); 
    LineSegment line = new LineSegment(level, version);
    
    // cf. Layout-documentation page 17
    boolean startAtSpecies = false;
    switch(speciesReferenceGlyph.getRole()) {
      case PRODUCT:
      case SIDEPRODUCT:
      case SUBSTRATE:
      case SIDESUBSTRATE:
      case UNDEFINED:
        startAtSpecies = false;
        break;
      default:
        startAtSpecies = true;
    }
    line.setStart(startAtSpecies ? speciesDockingPosition : reactionDockingPosition);
    line.setEnd(startAtSpecies ? reactionDockingPosition : speciesDockingPosition);
    result.addCurveSegment(line);
    return result;
  }


  @Override
  public Dimensions createTextGlyphDimension(TextGlyph textGlyph) {
    if(textGlyph.isSetBoundingBox() && textGlyph.getBoundingBox().isSetDimensions()) {
      return textGlyph.getBoundingBox().getDimensions();
    } else {
      return new Dimensions(1, 1, 0, level, version);
    }
  }


  @Override
  public Dimensions createSpeciesReferenceGlyphDimension(
    ReactionGlyph reactionGlyph, SpeciesReferenceGlyph speciesReferenceGlyph) {
    if(reactionGlyph.getBoundingBox().isSetDimensions()) {
      return reactionGlyph.getBoundingBox().getDimensions();
    }
    return new Dimensions(1, 1, 0, level, version);
  }


  @Override
  public BoundingBox createGlyphBoundingBox(GraphicalObject glyph,
    SpeciesReferenceGlyph specRefGlyph) {
    BoundingBox result = new BoundingBox(level, version);
    result.setDimensions(new Dimensions(1, 1, 0, level, version));
    return result;
  }


  @Override
  public void addLayoutedGlyph(GraphicalObject glyph) {
    setOfLayoutedGlyphs.add(glyph);
  }


  @Override
  public void addUnlayoutedGlyph(GraphicalObject glyph) {
    setOfUnlayoutedGlyphs.add(glyph);
  }


  @Override
  public void addLayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg) {
    // TODO ? get/set up the curve here and add it to graphical-Object-sets? 
    laidoutEdges.add(new Pair<SpeciesReferenceGlyph, ReactionGlyph>(srg, rg));
  }


  @Override
  public void addUnlayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg) {
    // TODO ? get/set up the curve here and add it to graphical-Object-sets?
    unlaidoutEdges.add(new Pair<SpeciesReferenceGlyph, ReactionGlyph>(srg, rg));
  }


  @Override
  public Set<GraphicalObject> completeGlyphs() {
    int species = 0;
    int reactions = 0;
    // This is the central method.
    // Side-effects seem to be the main goal!
    Set<TextGlyph> textGlyphs = new HashSet<TextGlyph>();
    Set<GraphicalObject> result = new HashSet<GraphicalObject>(); // is this even used?
    for(GraphicalObject go : setOfUnlayoutedGlyphs) {
      // Determine what to do with the g.o. -> compute it
      if(go instanceof CompartmentGlyph) {
        BoundingBox box = ((CompartmentGlyph) go).createBoundingBox(
          createCompartmentGlyphDimension((CompartmentGlyph) go));
        box.setPosition(createCompartmentGlyphPosition((CompartmentGlyph) go));
        ((CompartmentGlyph) go).setBoundingBox(box);
      } else if(go instanceof SpeciesGlyph) {
        completeSpeciesGlyph((SpeciesGlyph) go, new Point(species*50, 0, 0));
        species++;
      } else if(go instanceof ReactionGlyph){
        BoundingBox box = new BoundingBox(level, version);
        box.setDimensions(new Dimensions(10, 10, 0, level, version));
        box.setPosition(new Point(50 + reactions*50, 40, 0));
        go.setBoundingBox(box);
        reactions++;
      } else if (go instanceof TextGlyph) {
        textGlyphs.add((TextGlyph)go);
      }
      addLayoutedGlyph(go); // ? not needed?
      result.add(go);
    }
    for(TextGlyph tg : textGlyphs) {
      BoundingBox box = new BoundingBox(level, version);
      box.setDimensions(createTextGlyphDimension(tg));
      // Default:
      box.setPosition(new Point(10 * species, 5*reactions, 0));
      
      NamedSBase origin = tg.getOriginOfTextInstance(); 
      if(tg.isSetGraphicalObject()) {
        box.setPosition(tg.getGraphicalObjectInstance().getBoundingBox().getPosition().clone());
        box.setDimensions(tg.getGraphicalObjectInstance().getBoundingBox().getDimensions().clone());
      } else if(origin != null && origin instanceof Species) {
        List<SpeciesGlyph> results = getLayout().findSpeciesGlyphs(origin.getId());
        if(!results.isEmpty()) {
          SpeciesGlyph sg = results.get(0);
          if(sg.isSetBoundingBox()) {
            box.setPosition(sg.getBoundingBox().getPosition().clone());
            box.setDimensions(sg.getBoundingBox().getDimensions().clone());
          }
        }
      }
      tg.setBoundingBox(box);
    }
    
    for(Pair<SpeciesReferenceGlyph, ReactionGlyph> pair : unlaidoutEdges) {
      // TODO: Do sth here? Yes: build the curve here
      Curve curve = createCurve(pair.getValue(), pair.getKey());
      pair.getKey().setCurve(curve);
    }
    
    return result;
  }
  
  private void completeSpeciesGlyph(SpeciesGlyph sg, Point placement) {
    BoundingBox box = sg.createBoundingBox(createSpeciesGlyphDimension());
    box.setPosition(placement);
    sg.setBoundingBox(box);    
  }
  
  /**
   * Overwriting the inherited behaviour to take the curve-attribute of a reactionGlyph 
   * into account (if set)
   * @param reactionGlyph: the glyph in question
   */
  public double calculateReactionGlyphRotationAngle(ReactionGlyph reactionGlyph) {
    if (reactionGlyph.isSetCurve()
      && reactionGlyph.getCurve().isSetListOfCurveSegments()
      && reactionGlyph.getCurve().getCurveSegmentCount() > 0) {
      // i.e. there is some curve that can be worked with
      
      // Very basic assumption (need be respected by the layout!): The curve is
      // just a single line specifying the connection-whiskers of the
      // reaction-glyph
      return calculateRotationAngle(
        reactionGlyph.getCurve().getCurveSegment(0).getStart(),
        reactionGlyph.getCurve().getCurveSegment(0).getEnd());
    } else {
      // If no curve is available, just use the super-implementation (based on
      // SpeciesGlph-positions)
      return super.calculateReactionGlyphRotationAngle(reactionGlyph);
    }
  }
}
