package org.sbml.jsbml.examples.render;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.director.SimpleLayoutAlgorithm;


public class RingLayoutAlgorithm extends SimpleLayoutAlgorithm {

  /**
   * SId for the default compartment. In trying to avoid accidentally matching a
   * previously present id, random-case is employed (since SIds are
   * case-sensitive), as that would generally be inappropriate for a SBML-file.
   * 
   * The default compartment will collect all speciesGlyphs that are not assigned
   * to any compartment
   */
  private static final String DEFAULT_COMPARTMENT = "___dEfaUlTCoMpartmEnT";
  
  private final static double SPECIES_HEIGHT = 20;
  private final static double SPECIES_WIDTH = 40;
  
  /** How many species are there? -> affects radius and angles */
  private int speciesCount = 0;
  private double interspeciesRadians = 2 * Math.PI;  
  
  private HashMap<String, List<SpeciesGlyph>> compartmentMembers;
  private HashMap<String, CompartmentGlyph> unlaidoutCompartments;
  private Set<TextGlyph> textGlyphs;
  
  /**
   * 
   * @param width the width of the surrounding layout
   * @param height the height of the surrounding layout
   */
  public RingLayoutAlgorithm() {
    // This implementation will draw a ring for all the unlaid-out glyphs, while
    // ignoring the laid-out ones (up to the user to lay them out in a fit way)
    compartmentMembers = new HashMap<String, List<SpeciesGlyph>>();
    compartmentMembers.put(DEFAULT_COMPARTMENT, new ArrayList<SpeciesGlyph>());
    // The default-compartment is virtual (for sorting the corresponding
    // Species), it will NOT be added to the file
    unlaidoutCompartments = new HashMap<String, CompartmentGlyph>();
    textGlyphs = new HashSet<TextGlyph>();
  }


  @Override
  public Dimensions createLayoutDimension() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Dimensions createCompartmentGlyphDimension(
    CompartmentGlyph previousCompartmentGlyph) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Point createCompartmentGlyphPosition(
    CompartmentGlyph previousCompartmentGlyph) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Dimensions createSpeciesGlyphDimension() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Curve createCurve(ReactionGlyph reactionGlyph,
    SpeciesReferenceGlyph speciesReferenceGlyph) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Dimensions createTextGlyphDimension(TextGlyph textGlyph) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public Dimensions createSpeciesReferenceGlyphDimension(
    ReactionGlyph reactionGlyph, SpeciesReferenceGlyph speciesReferenceGlyph) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public BoundingBox createGlyphBoundingBox(GraphicalObject glyph,
    SpeciesReferenceGlyph specRefGlyph) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public void addLayoutedGlyph(GraphicalObject glyph) {
    // this will not count in the speciesCount.
    setOfLayoutedGlyphs.add(glyph);
  }


  @Override
  public void addUnlayoutedGlyph(GraphicalObject glyph) {
    setOfUnlayoutedGlyphs.add(glyph);
    if(glyph instanceof SpeciesGlyph) {
      Species species = (Species) ((SpeciesGlyph) glyph).getSpeciesInstance();
      /**
       * species is optional (cf. layout specification page 15). If no species
       * is set, assign this speciesgyph to the default-compartment, otherwise,
       * retrieve the species's compartment (required attribute)
       */
      if (species != null) {
        assert species.isSetCompartment();
        String compartment = species.getCompartment();
        if (!compartmentMembers.containsKey(compartment)) {
          compartmentMembers.put(compartment, new ArrayList<SpeciesGlyph>());
        }
        compartmentMembers.get(compartment).add((SpeciesGlyph) glyph);
      } else {
        compartmentMembers.get(DEFAULT_COMPARTMENT).add((SpeciesGlyph) glyph);
      }
      
      speciesCount++;
    } else if (glyph instanceof TextGlyph) {
      textGlyphs.add((TextGlyph) glyph);
    } else if (glyph instanceof CompartmentGlyph
      && ((CompartmentGlyph) glyph).isSetCompartment()) {
      unlaidoutCompartments.put(((CompartmentGlyph) glyph).getCompartment(),
        (CompartmentGlyph) glyph);
    }
  }


  @Override
  public void addLayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg) {
    // TODO Auto-generated method stub
  }


  @Override
  public void addUnlayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg) {
    // TODO Auto-generated method stub
  }


  @Override
  public Set<GraphicalObject> completeGlyphs() {
    double totalWidth = getLayout().getDimensions().getWidth();
    double totalHeight = getLayout().getDimensions().getHeight();
    
    // Main method: Side effects are main purpose.
    
    interspeciesRadians = 2 * Math.PI / speciesCount;
    // TODO: use function of totalWidth/Height instead of 60 
    double radius = Math.min(totalWidth, totalHeight) * speciesCount / 60; // Circumference linear in number of species. 
    
    int speciesIndex = 0;
    for(String compartment : compartmentMembers.keySet()) {
      int compartmentStart = speciesIndex;
      int compartmentEnd = speciesIndex;
      
      for(SpeciesGlyph species : compartmentMembers.get(compartment)) {
        double tilt = Math.PI / 2;
        tilt -= speciesIndex * interspeciesRadians;
        
        BoundingBox bbox = new BoundingBox();
        bbox.createDimensions(SPECIES_WIDTH, SPECIES_HEIGHT, 1);
        bbox.createPosition(radius * Math.cos(tilt) + totalWidth / 2,
          -radius * Math.sin(tilt) + totalHeight / 2, 0);
        species.setBoundingBox(bbox);
        speciesIndex++;
        compartmentEnd = speciesIndex;
      }
      // Note that the endIndex is actually one too large and would, if
      // uncorrected, lead to overlapping compartments
      compartmentEnd--;
      
      // draw the respective compartment (if it is not yet laid out)
      if(unlaidoutCompartments.containsKey(compartment)) {
        BoundingBox bbox = boundCompartment(compartmentStart, compartmentEnd, radius);
        unlaidoutCompartments.get(compartment).setBoundingBox(bbox);
      }      
    }
    
    for(TextGlyph tg : textGlyphs) {
      BoundingBox box = new BoundingBox(level, version);
      // Default:
      box.createDimensions(2, 2, 0);
      box.createPosition(0, 0, 0);
      
      NamedSBase origin = tg.getOriginOfTextInstance(); 
      if(tg.isSetGraphicalObject()) {
        box.setPosition(tg.getGraphicalObjectInstance().getBoundingBox().getPosition().clone());
        box.setDimensions(tg.getGraphicalObjectInstance().getBoundingBox().getDimensions().clone());
      } else if(origin != null && origin instanceof Species) {
        List<SpeciesGlyph> results = getLayout().findSpeciesGlyphs(origin.getId());
        if(!results.isEmpty()) {
          SpeciesGlyph sg = results.get(0); // this is a very specific instance
          if(sg.isSetBoundingBox()) {
            box.setPosition(sg.getBoundingBox().getPosition().clone());
            box.setDimensions(sg.getBoundingBox().getDimensions().clone());
          }
        }
      }
      tg.setBoundingBox(box);
    }
    
    return null;
  }
  
  private BoundingBox boundCompartment(int startIndex, int endIndex, double ringRadius) {
    System.out.println("From " + startIndex + " to " + endIndex);
    BoundingBox result = new BoundingBox(level, version);
    double from = (Math.PI / 2) - (startIndex * interspeciesRadians);
    double to = (Math.PI / 2) - (endIndex * interspeciesRadians);
    
    double minX = Math.min(Math.cos(from), Math.cos(to));
    double maxX = Math.max(Math.cos(from), Math.cos(to));
    double minY = Math.min(-Math.sin(from), -Math.sin(to));
    double maxY = Math.max(-Math.sin(from), -Math.sin(to));
    
    // due to the way the angles are defined (90° - i*interspeciesRadians), from
    // is numerically larger than to
    if(to <= Math.PI / 2 && Math.PI / 2 <= from)
      minY = -1;
    if(to <= 0 && 0 <= from)
      maxX = 1;
    if(to <= -Math.PI / 2 && -Math.PI / 2 <= from)
      maxY = 1;
    if(to <= -Math.PI && Math.PI <= from)
      minX = -1;
    
    result.createDimensions((ringRadius * (maxX - minX)) + SPECIES_WIDTH,
      (ringRadius * (maxY - minY)) + SPECIES_HEIGHT, 1);
    result.createPosition(
      (ringRadius * minX) + getLayout().getDimensions().getWidth() / 2,
      (ringRadius * minY) + getLayout().getDimensions().getHeight() / 2,
      -(endIndex - startIndex)); // ~ sort by size: largest Compartments into the back
    
    return result;
  }
}
