package org.sbml.jsbml.examples.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Reaction;
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
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.director.SimpleLayoutAlgorithm;
import org.sbml.jsbml.util.Pair;


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
  private final static double REACTION_GLYPH_SIZE = 10; 
  
  /** How many species are there? -> affects radius and angles */
  private int speciesCount = 0;
  private double interspeciesRadians = 2 * Math.PI;  
  
  /**
   * While the SimpleLayoutAlgorithm provides sets to hold all laid-out and
   * unlaid-out Glyphs, at some point, they need be told apart by their class
   * anyways, so here they are just kept separate
   */
  private HashMap<String, List<SpeciesGlyph>> compartmentMembers;
  private HashMap<String, CompartmentGlyph> unlaidoutCompartments;
  private Set<TextGlyph> textGlyphs;
  private Set<ReactionGlyph> reactionGlyphs;
  private Set<Pair<SpeciesReferenceGlyph, ReactionGlyph>> laidoutEdges;
  /** Collect the unlaid-out SRGs for each reactionGlyph (id) */
  private HashMap<String, List<SpeciesReferenceGlyph>> unlaidoutSpeciesReferenceGlyphs;
  
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
    unlaidoutSpeciesReferenceGlyphs = new HashMap<String, List<SpeciesReferenceGlyph>>();
    textGlyphs = new HashSet<TextGlyph>();
    reactionGlyphs = new HashSet<ReactionGlyph>();
    
    laidoutEdges = new HashSet<Pair<SpeciesReferenceGlyph, ReactionGlyph>>();
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
    // TODO Unused? boundCompartment needs more information. Put into private fields?
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
    } else if (glyph instanceof ReactionGlyph) {
      reactionGlyphs.add((ReactionGlyph) glyph);
    }
  }


  @Override
  public void addLayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg) {
    // Here, we are uninterested in laid-out edges
    laidoutEdges.add(new Pair<SpeciesReferenceGlyph, ReactionGlyph>(srg, rg));
  }


  @Override
  public void addUnlayoutedEdge(SpeciesReferenceGlyph srg, ReactionGlyph rg) {
    if(!unlaidoutSpeciesReferenceGlyphs.containsKey(rg.getId())) {
      unlaidoutSpeciesReferenceGlyphs.put(rg.getId(), new ArrayList<SpeciesReferenceGlyph>());
    }
    
    unlaidoutSpeciesReferenceGlyphs.get(rg.getId()).add(srg);
  }


  @Override
  public Set<GraphicalObject> completeGlyphs() {
    // TODO: this needs refactoring! Currently sketching algorithm
    // Main method: Side effects are main purpose.
    
    // Note: LayoutDirector will only set SBO-terms/roles AFTER this has been
    // called, thus, have to retrieve SBO/role robustly (for
    // SpeciesReferenceGlyphs)
    
    double totalWidth = getLayout().getDimensions().getWidth();
    double totalHeight = getLayout().getDimensions().getHeight();
    
    interspeciesRadians = 2d * Math.PI / speciesCount;
    // Circumference linear in number of species. Arbitrary (and not
    // particularly robust) formula used here
    double radius = Math.min(totalWidth, totalHeight) * speciesCount / 60d;  
    
    /**
     * I) Arrange (unlaidout) species and compartments in a circle
     */
    int speciesIndex = 0;
    for(String compartment : compartmentMembers.keySet()) {
      int compartmentStart = speciesIndex;
      int compartmentEnd = speciesIndex;
      
      for(SpeciesGlyph species : compartmentMembers.get(compartment)) {
        double tilt = Math.PI / 2d;
        tilt -= speciesIndex * interspeciesRadians;
        
        BoundingBox bbox = new BoundingBox();
        bbox.createDimensions(SPECIES_WIDTH, SPECIES_HEIGHT, 1);
        bbox.createPosition(radius * Math.cos(tilt) + totalWidth / 2d,
          -radius * Math.sin(tilt) + totalHeight / 2d, 0);
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
    
    /**
     * II) Layout reactionGlyphs:
     *     - RGs are placed ~ center of mass of their SRGs
     *     - SRGs need be classified by SBO/role
     *     - RG's curve is laid-out according to SRG-positions and roles
     *     - SRG-curves connect to RG's curve (for [side]product/substrate),
     *       or to RG's side (think of a cross, where one of the bars is virtual)  
     */
    for(ReactionGlyph rg : reactionGlyphs) {
      System.out.println("Reaction: " + rg.getId());
      Point centerOfSRGs = new Point(0, 0, 0);
      Point substratePort = new Point(0, 0, 0);
      Point productPort = new Point(0, 0, 0);
      
      int srgCount = rg.getSpeciesReferenceGlyphCount();
      int substrateCount = 0;
      int productCount = 0;
      for(int i = 0; i < srgCount; i++) {
        // Above, we have laid out all SpeciesGlyphs -- can thus now use their
        // positions
        SpeciesReferenceGlyph srg = rg.getSpeciesReferenceGlyph(i);
        Point srgPosition = srg.getSpeciesGlyphInstance().getBoundingBox().getPosition();
        centerOfSRGs.setX(centerOfSRGs.getX() + (srgPosition.getX() / (double) srgCount));
        centerOfSRGs.setY(centerOfSRGs.getY() + (srgPosition.getY() / (double) srgCount));
        
        // Set the role to product/substrate for products/substrates.
        if (!srg.isSetSpeciesReferenceRole() && srg.isSetSpeciesGlyph()
          && srg.getSpeciesGlyphInstance().isSetSpecies()) {
          Species species = (Species) srg.getSpeciesGlyphInstance().getSpeciesInstance();
          if (((Reaction) rg.getReactionInstance()).hasProduct(species)) {
            srg.setRole(SpeciesReferenceRole.PRODUCT);
          } else if (((Reaction) rg.getReactionInstance()).hasReactant(species)) {
            srg.setRole(SpeciesReferenceRole.SUBSTRATE);
          }
        }
        
        if (srg.isSetSpeciesReferenceRole()) {
          switch (srg.getRole()) {
          case PRODUCT:
          case SIDEPRODUCT:
            productPort.setX(
              productPort.getX() + srg.getSpeciesGlyphInstance().getBoundingBox().getPosition().getX());
            productPort.setY(
              productPort.getY() + srg.getSpeciesGlyphInstance().getBoundingBox().getPosition().getY());
            productCount++;
            break;
          case SUBSTRATE:
          case SIDESUBSTRATE:
            substratePort.setX(
              substratePort.getX() + srg.getSpeciesGlyphInstance().getBoundingBox().getPosition().getX());
            substratePort.setY(
              substratePort.getY() + srg.getSpeciesGlyphInstance().getBoundingBox().getPosition().getY());
            substrateCount++;
            break;
          default:
            break;
          }
        }
      }
      // TODO: might add nonlinearity
      rg.createBoundingBox(REACTION_GLYPH_SIZE, REACTION_GLYPH_SIZE, 0,
        centerOfSRGs.getX() - REACTION_GLYPH_SIZE / 2d,
        centerOfSRGs.getY() - REACTION_GLYPH_SIZE / 2d, 0);
      
      if(productCount > 0) {
        productPort.setX(productPort.getX() / (double) productCount);
        productPort.setY(productPort.getY() / (double) productCount);
      }
      if(substrateCount > 0) {
        substratePort.setX(substratePort.getX() / (double) substrateCount);
        substratePort.setY(substratePort.getY() / (double) substrateCount);
      }
      
      // The center of substrate and product-port might not yet be the center of
      // the reactionGlyph: Shift it (parallel)
      double offCenterX = 0.5d * (productPort.getX() + substratePort.getX());
      double offCenterY = 0.5d * (productPort.getY() + substratePort.getY());
      productPort.setX(productPort.getX() + centerOfSRGs.getX() - offCenterX);
      productPort.setY(productPort.getY() + centerOfSRGs.getY() - offCenterY);
      substratePort.setX(substratePort.getX() + centerOfSRGs.getX() - offCenterX);
      substratePort.setY(substratePort.getY() + centerOfSRGs.getY() - offCenterY);
      
      // Further: Want a normalised port-distance
      double scale =
        Math.sqrt(Math.pow(productPort.getX() - centerOfSRGs.getX(), 2)
          + Math.pow(productPort.getY() - centerOfSRGs.getY(), 2));
      scale = REACTION_GLYPH_SIZE / scale;
      productPort.setX(centerOfSRGs.getX() * (1d - scale) + productPort.getX() * scale);
      productPort.setY(centerOfSRGs.getY() * (1d - scale) + productPort.getY() * scale);
      // since the center of substrate and product-port was shifted onto the
      // center of the RG, need not recompute the scale
      substratePort.setX(centerOfSRGs.getX() * (1d - scale) + substratePort.getX() * scale);
      substratePort.setY(centerOfSRGs.getY() * (1d - scale) + substratePort.getY() * scale);
      
      Curve mainCurve = rg.createCurve();
      LineSegment whiskers = new LineSegment(layout.getLevel(), layout.getVersion());
      whiskers.setEnd(productPort.clone());
      whiskers.setStart(substratePort.clone());
      mainCurve.addCurveSegment(whiskers);
      
      if(unlaidoutSpeciesReferenceGlyphs.containsKey(rg.getId())) {
        for (SpeciesReferenceGlyph srg : unlaidoutSpeciesReferenceGlyphs.get(rg.getId())) {
          Curve curve = srg.createCurve();
          // TODO: a) CubicBeziers
          // TODO: b) Better endpoints at SRGs and for modifiers
          LineSegment connection = new LineSegment(layout.getLevel(), layout.getVersion());
          if (srg.isSetSpeciesReferenceRole()) {
            switch (srg.getRole()) {
            case PRODUCT:
            case SIDEPRODUCT:
              connection.setStart(productPort.clone());
              connection.setEnd(cloneSpeciesPosition(srg));
              break;
            case SUBSTRATE:
            case SIDESUBSTRATE:
              connection.setStart(substratePort.clone());
              connection.setEnd(cloneSpeciesPosition(srg));
              break;
            default:
              connection.setStart(cloneSpeciesPosition(srg));
              connection.setEnd(centerOfSRGs.clone());
            }
          } else {
            // TODO: redundant code, make better case-distinction
            connection.setStart(cloneSpeciesPosition(srg));
            connection.setEnd(centerOfSRGs.clone());
          }
          curve.addCurveSegment(connection);
        }
      }
    }
    
    /**
     * III) Place Textglyphs at their origins of text (if available)
     */
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
          SpeciesGlyph sg = results.get(0); // this is a very specific instance (arbitrary)
          if(sg.isSetBoundingBox()) {
            box.setPosition(sg.getBoundingBox().getPosition().clone());
            box.setDimensions(sg.getBoundingBox().getDimensions().clone());
          }
        }
      }
      tg.setBoundingBox(box);
    }
    
    // The return value is never used.
    return null;
  }
  
  
  /**
   * Creates a boundingBox for a compartment containing the species placed at
   * startIndex, startIndex+1, ..., endIndex (inclusive end)
   * 
   * @param startIndex
   *        index of first species in the compartment (0 is at 12 o'clock)
   * @param endIndex
   *        index of last species in the compartment
   * @param ringRadius
   *        the radius of the ring in which the species are laid out
   * @return a boundingbox for the compartment, enclosing all the species in the
   *         compartment
   */
  private BoundingBox boundCompartment(int startIndex, int endIndex, double ringRadius) {
    BoundingBox result = new BoundingBox(level, version);
    double from = (Math.PI / 2d) - (startIndex * interspeciesRadians);
    double to = (Math.PI / 2d) - (endIndex * interspeciesRadians);
    
    double minX = Math.min(Math.cos(from), Math.cos(to));
    double maxX = Math.max(Math.cos(from), Math.cos(to));
    double minY = Math.min(-Math.sin(from), -Math.sin(to));
    double maxY = Math.max(-Math.sin(from), -Math.sin(to));
    
    // due to the way the angles are defined (90° - i*interspeciesRadians), 'from'
    // is numerically larger than 'to'
    if(to <= Math.PI / 2d && Math.PI / 2d <= from)
      minY = -1d;
    if(to <= 0 && 0 <= from)
      maxX = 1d;
    if(to <= -Math.PI / 2d && -Math.PI / 2d <= from)
      maxY = 1d;
    if(to <= -Math.PI && Math.PI <= from)
      minX = -1d;
    
    result.createDimensions((ringRadius * (maxX - minX)) + SPECIES_WIDTH,
      (ringRadius * (maxY - minY)) + SPECIES_HEIGHT, 1);
    result.createPosition(
      (ringRadius * minX) + getLayout().getDimensions().getWidth() / 2d,
      (ringRadius * minY) + getLayout().getDimensions().getHeight() / 2d,
      -(endIndex - startIndex)); // ~ sort by size: largest Compartments into the back
    
    return result;
  }
  
  public Point cloneSpeciesPosition(SpeciesReferenceGlyph srg) {
    Point result = srg.getSpeciesGlyphInstance().getBoundingBox().getPosition().clone();
    result.setLevel(layout.getLevel());
    result.setVersion(layout.getVersion());
    return result;
  }
}
