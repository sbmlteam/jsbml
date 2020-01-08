package examples;

import java.util.HashSet;
import java.util.Set;

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
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.director.SimpleLayoutAlgorithm;
import org.sbml.jsbml.util.Pair;


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
    return new Dimensions(2, 2, 0, level, version);
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
    return new Dimensions(1, 1, 0, level, version);
  }


  @Override
  public Curve createCurve(ReactionGlyph reactionGlyph,
    SpeciesReferenceGlyph speciesReferenceGlyph) {
    // TODO Does this suffice?
    Curve result = new Curve(level, version);
    Point speciesPosition = speciesReferenceGlyph.getBoundingBox().getPosition();
    Dimensions speciesDimensions = speciesReferenceGlyph.getBoundingBox().getDimensions();
    speciesPosition.setX(speciesDimensions.getWidth() + speciesPosition.getX());
    speciesPosition.setY(speciesDimensions.getHeight() + speciesPosition.getY());
    Point speciesDockingPosition = calculateSpeciesGlyphDockingPosition(
      speciesPosition, reactionGlyph, speciesReferenceGlyph.getRole(),
      speciesReferenceGlyph.getSpeciesGlyphInstance());
    
    Point reactionDockingPosition = calculateReactionGlyphDockingPoint(
      reactionGlyph, calculateReactionGlyphRotationAngle(reactionGlyph),
      speciesReferenceGlyph); 
    LineSegment line = new LineSegment(level, version);
    line.setStart(speciesDockingPosition);
    line.setEnd(reactionDockingPosition);
    result.addCurveSegment(line);
    return result;
  }


  @Override
  public Dimensions createTextGlyphDimension(TextGlyph textGlyph) {
    if(textGlyph.getBoundingBox().isSetDimensions()) {
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
    // This is the central method.
    Set<GraphicalObject> result = new HashSet<GraphicalObject>(); // is this even used?
    for(GraphicalObject go : setOfUnlayoutedGlyphs) {
      // Determine what to do with the g.o. -> compute it
      if(go instanceof CompartmentGlyph) {
        BoundingBox box = ((CompartmentGlyph) go).createBoundingBox(
          createCompartmentGlyphDimension((CompartmentGlyph) go));
        box.setPosition(createCompartmentGlyphPosition((CompartmentGlyph) go));
        ((CompartmentGlyph) go).setBoundingBox(box);
      } else {
        BoundingBox box = new BoundingBox(level, version);
        box.createDimensions();
        box.createPosition();
        go.setBoundingBox(box);
      }
      // addLayoutedGlyph(go); // ? not needed?
      result.add(go);
    }
    for(Pair<SpeciesReferenceGlyph, ReactionGlyph> pair : unlaidoutEdges) {
      // TODO: Do sth here?
    }
    
    return result;
  }
}
