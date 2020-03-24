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
import org.sbml.jsbml.ext.layout.CubicBezier;
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
import org.sbml.jsbml.ext.render.director.Geometry;

/**
 * Implementation of a simplistic algorithm to produce a circular layout of the
 * species-glyphs, with the reactions potentially crossing in the middle.<br>
 * 
 * To understand them in detail, draw out any geometric arrangements which are
 * implemented here. Basic trigonometry and linear algebra should suffice.
 * 
 * @author David Emanuel Vetter
 */
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
  private final static double COMPARTMENT_MARGIN = 5;
  
  /** How many species are there? -> affects radius and angles */
  private int speciesCount = 0;
  private double interspeciesRadians = 2 * Math.PI;  
  
  /** Fields to confer information between methods */
  private int compartmentStart = 0;
  private int compartmentEnd = 0;
  private double ringRadius = 0d;
  
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
  
  public RingLayoutAlgorithm() {
    /**
     *  This implementation will draw a ring for all the unlaid-out glyphs, while
     *  ignoring the laid-out ones (up to the user to lay them out in a fit way)
     */
    compartmentMembers = new HashMap<String, List<SpeciesGlyph>>();
    compartmentMembers.put(DEFAULT_COMPARTMENT, new ArrayList<SpeciesGlyph>());
    /**
     * The default-compartment is virtual (for sorting the corresponding
     * Species), it will NOT be added to the file
     */
    unlaidoutCompartments = new HashMap<String, CompartmentGlyph>();
    unlaidoutSpeciesReferenceGlyphs = new HashMap<String, List<SpeciesReferenceGlyph>>();
    textGlyphs = new HashSet<TextGlyph>();
    reactionGlyphs = new HashSet<ReactionGlyph>();
    
    laidoutEdges = new HashSet<Pair<SpeciesReferenceGlyph, ReactionGlyph>>();
  }


  @Override
  public Dimensions createLayoutDimension() {
    // Abundant, square space:
    double sideLength = speciesCount * SPECIES_WIDTH * 2;
    return new Dimensions(sideLength, sideLength, 100d, getLayout().getLevel(),
      getLayout().getVersion());
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
    /** Main method: Side effects are main purpose. */
    
    /**
     * Note: LayoutDirector will only set SBO-terms/roles AFTER this has been
     * called, thus, have to retrieve SBO/role robustly (for
     * SpeciesReferenceGlyphs)
     */
    if(!getLayout().isSetDimensions()) {
      getLayout().setDimensions(createLayoutDimension());
    }
    
    double totalWidth = getLayout().getDimensions().getWidth();
    double totalHeight = getLayout().getDimensions().getHeight();
    
    interspeciesRadians = 2d * Math.PI / speciesCount;
    /**
     *  Circumference is linear in number of species. Arbitrary (and not
     *  particularly robust) formula used here
     */
    ringRadius = Math.min(totalWidth, totalHeight) * speciesCount / 60d;  
    
    /**
     * I) Arrange (unlaidout) species and compartments in a circle
     */
    layoutSpeciesAndCompartments(totalWidth, totalHeight);
    
    /**
     * II) Layout reactionGlyphs:
     *     - RGs are placed ~ center of mass of their SRGs
     *     - SRGs need be classified by SBO/role
     *     - RG's curve is laid-out according to SRG-positions and roles
     *     - SRG-curves connect to RG's curve (for [side]product/substrate),
     *       or to RG's side (think of a cross, where one of the bars is virtual)  
     */
    layoutReactionGlyphs(totalWidth, totalHeight);
    
    /**
     * III) Place Textglyphs at their origins of text (if available)
     */
    layoutTextGlyphs();
    
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
    
    /**
     * Since we are working in a ring, the startIndex and endIndex may specify a
     * circle-arc passing through the right-/left-/top-/bottom-most point of the
     * ring (e.g. start may be 12 o'clock, end may be 8 o'clock -- then, the
     * Compartment should include both 3 o'clock and 6 o'clock, which is
     * implemented by min/maxX/Y and the interval-checks here
     */
    double minX = Math.min(Math.cos(from), Math.cos(to));
    double maxX = Math.max(Math.cos(from), Math.cos(to));
    double minY = Math.min(-Math.sin(from), -Math.sin(to));
    double maxY = Math.max(-Math.sin(from), -Math.sin(to));
    
    /**
     *  due to the way the angles are defined (90° - i*interspeciesRadians), 'from'
     *  is numerically larger than 'to'
     */
    if(to <= Math.PI / 2d && Math.PI / 2d <= from)
      minY = -1d;
    if(to <= 0 && 0 <= from)
      maxX = 1d;
    if(to <= -Math.PI / 2d && -Math.PI / 2d <= from)
      maxY = 1d;
    if(to <= -Math.PI && Math.PI <= from)
      minX = -1d;
    
    result.createDimensions((ringRadius * (maxX - minX)) + SPECIES_WIDTH + 2*COMPARTMENT_MARGIN,
      (ringRadius * (maxY - minY)) + SPECIES_HEIGHT + 2*COMPARTMENT_MARGIN, 1);
    result.createPosition(
      -COMPARTMENT_MARGIN + (ringRadius * minX) + getLayout().getDimensions().getWidth() / 2d,
      -COMPARTMENT_MARGIN + (ringRadius * minY) + getLayout().getDimensions().getHeight() / 2d,
      -(endIndex - startIndex)); // ~ sort by size: largest Compartments into the back
    
    return result;
  }


  
  /**
   * Finds the closer of the two candidates to a reference-point (in terms of
   * euclidean distance).<br>
   * <b>Side-effect!</b> all points will have a set z-value after calling this
   * method (if z was not set before, it will be 0 after calling this)
   * 
   * @param candidate1
   * @param candidate2
   * @param referent
   * @return either candidate1 or candidate2 (not cloned)
   */
  private Point closest(Point candidate1, Point candidate2, Point referent) {
    forceZtoNumber(candidate1);
    forceZtoNumber(candidate2);
    forceZtoNumber(referent);
    if (Geometry.euclideanDistance(candidate1,
      referent) < Geometry.euclideanDistance(candidate2, referent)) {
      return candidate1;
    } else {
      return candidate2;
    }
  }
  
  /**
   * Forces given point's z-value to be != NaN (default-value: 0) 
   * @param p
   */
  private void forceZtoNumber(Point p) {
    if(!p.isSetZ()) {
      p.setZ(0d);
    }
  }
  
  /**
   * Checks whether given {@link SpeciesReferenceGlyph} specifies a product or sideproduct
   * @param srg
   * @return
   */
  private boolean isProductReference(SpeciesReferenceGlyph srg) {
    if (srg.isSetSpeciesReferenceRole()) {
      return srg.getRole().equals(SpeciesReferenceRole.PRODUCT)
        || srg.getRole().equals(SpeciesReferenceRole.SIDEPRODUCT);
    }
    return false;
  }
  
  /**
   * Checks whether given {@link SpeciesReferenceGlyph} specifies a substrate or sidesubstrate
   * @param srg
   * @return
   */
  private boolean isSubstrateReference(SpeciesReferenceGlyph srg) {
    if (srg.isSetSpeciesReferenceRole()) {
      return srg.getRole().equals(SpeciesReferenceRole.SUBSTRATE)
        || srg.getRole().equals(SpeciesReferenceRole.SIDESUBSTRATE);
    }
    return false;
  }
  
  
  /**
   * Helper-method of {@link RingLayoutAlgorithm#completeGlyphs()}, to structure
   * the code. Places SpeciesGlyphs in a circle and compartmentGlyphs
   * accordingly
   * 
   * @param totalWidth
   *        of the layout
   * @param totalHeight
   *        of the layout
   */
  private void layoutSpeciesAndCompartments(double totalWidth, double totalHeight) {
    int speciesIndex = 0;
    for (String compartment : compartmentMembers.keySet()) {
      compartmentStart = speciesIndex;
      compartmentEnd = speciesIndex;
      for (SpeciesGlyph species : compartmentMembers.get(compartment)) {
        double tilt = Math.PI / 2d;
        tilt -= speciesIndex * interspeciesRadians;
        BoundingBox bbox = new BoundingBox();
        bbox.createDimensions(SPECIES_WIDTH, SPECIES_HEIGHT, 1);
        bbox.createPosition(ringRadius * Math.cos(tilt) + totalWidth / 2d,
          -ringRadius * Math.sin(tilt) + totalHeight / 2d, 0);
        species.setBoundingBox(bbox);
        speciesIndex++;
        compartmentEnd = speciesIndex;
      }
      
      /**
       * Note that the endIndex is actually one too large and would, if
       * uncorrected, lead to overlapping compartments (in terms of Species
       * contained)
       */
      compartmentEnd--;
      
      /** draw the respective compartment (if it is not yet laid out) */
      if (unlaidoutCompartments.containsKey(compartment)) {
        BoundingBox bbox =
          boundCompartment(compartmentStart, compartmentEnd, ringRadius);
        unlaidoutCompartments.get(compartment).setBoundingBox(bbox);
      }
    }
  }
  
  
  /**
   * Helper-method of {@link RingLayoutAlgorithm#completeGlyphs()}, to structure
   * the code. Places reactionGlyphs in between their substrates/products
   * (except for exchange-reactions, which are to be placed outside the
   * circle&#185;),
   * and lays out speciesReferenceGlyphs. <br>
   * The method has two semantic parts:
   * <ol>
   * <li>Computing the needed points/anchors/ports for laying out the
   * glyphs</li>
   * <li>Actually laying out the glyphs, based on this information</li>
   * </ol>
   * Requires, that all {@link SpeciesGlyph}s be laid out already (i.e. call
   * after {@link RingLayoutAlgorithm#layoutSpeciesAndCompartments})
   * <br>
   * &#185<i>Note that the GlyphCreator will take care of exchange reactions by
   * adding source/sink-nodes, which is generally recommendable</i>
   * 
   * @param totalWidth
   *        of the layout
   * @param totalHeight
   *        of the layout
   */
  private void layoutReactionGlyphs(double totalWidth, double totalHeight) {
    for (ReactionGlyph rg : reactionGlyphs) {
      /**
       * 1.: Precomputations -- find the relevant points for laying out the
       * glyphs
       */
      /** initially, center the rg in the circle */
      rg.createBoundingBox(REACTION_GLYPH_SIZE, REACTION_GLYPH_SIZE, 0,
        (totalWidth - REACTION_GLYPH_SIZE) / 2d,
        (totalHeight - REACTION_GLYPH_SIZE) / 2d, 0);
     
      /**
       * Finding centers of mass of 
       * a) all involved species
       * b) only the substrates
       * c) only the products
       * 
       * Deviating from super.calculateAverageSpeciesPosition(...), here, use
       * not the position (top-left corner) of Species, but their port to the
       * reactionglyph
       */
      Point centerOfSRGs = new Point(0, 0, 0);
      Point substratePort = new Point(0, 0, 0);
      Point productPort = new Point(0, 0, 0);
      int srgCount = rg.getSpeciesReferenceGlyphCount();
      int substrateCount = 0;
      int productCount = 0;
      for (int i = 0; i < srgCount; i++) {
        /**
         * Before this method was called, we laid out all SpeciesGlyphs -- can
         * thus now use their positions
         */
        SpeciesReferenceGlyph srg = rg.getSpeciesReferenceGlyph(i);
        /**
         * Use the speciesPort w.r.t. a reactionGlyph in the circle's center
         * instead of the center/top-left-corner of the SpeciesGlyph
         */
        Point srgPort = calculateSpeciesGlyphDockingPosition(
          calculateCenter(srg.getSpeciesGlyphInstance()), rg,
          srg.isSetSpeciesReferenceRole() ? srg.getRole()
            : SpeciesReferenceRole.UNDEFINED,
          srg.getSpeciesGlyphInstance());
        
        centerOfSRGs = Geometry.weightedSum(1, centerOfSRGs, 1d / (double) srgCount, srgPort);
        
        /** Set the role to product/substrate for products/substrates. */
        if (!srg.isSetSpeciesReferenceRole() && srg.isSetSpeciesGlyph()
          && srg.getSpeciesGlyphInstance().isSetSpecies()) {
          Species species = (Species) srg.getSpeciesGlyphInstance().getSpeciesInstance();
          
          if (((Reaction) rg.getReactionInstance()).hasProduct(species)) {
            srg.setRole(SpeciesReferenceRole.PRODUCT);
          } else if (((Reaction) rg.getReactionInstance()).hasReactant(species)) {
            srg.setRole(SpeciesReferenceRole.SUBSTRATE);
          }
        }
        
        /** Count into product or substrate count and CoM */
        if (isProductReference(srg)) {
          productPort = Geometry.weightedSum(1, productPort, 1, srgPort);
          productCount++;
        } else if (isSubstrateReference(srg)) {
          substratePort = Geometry.weightedSum(1, substratePort, 1, srgPort);
          substrateCount++;
        }
      }
      
      if (productCount > 0) {
        productPort.setX(productPort.getX() / (double) productCount);
        productPort.setY(productPort.getY() / (double) productCount);
      } else {
        /** reaction w/o products: place by substrates */
        centerOfSRGs = Geometry.weightedSum(2d, substratePort, -1d,
          new Point(totalWidth / 2, totalHeight / 2, 0));
        productPort = centerOfSRGs.clone();
      }
      
      if (substrateCount > 0) {
        substratePort.setX(substratePort.getX() / (double) substrateCount);
        substratePort.setY(substratePort.getY() / (double) substrateCount);
      } else {
        /** reaction w/o substrates: place by products */
        centerOfSRGs = Geometry.weightedSum(2d, productPort, -1d,
          new Point(totalWidth / 2, totalHeight / 2, 0));
        substratePort = centerOfSRGs.clone();
      }
      
      // Here: overwrite the old placeholder-boundingbox      
      rg.createBoundingBox(REACTION_GLYPH_SIZE, REACTION_GLYPH_SIZE, 0,
        centerOfSRGs.getX() - REACTION_GLYPH_SIZE / 2d,
        centerOfSRGs.getY() - REACTION_GLYPH_SIZE / 2d, 0);

      
      
      /**
       * The center of substrate and product-port might not yet be the center of
       * the reactionGlyph: Shift it (parallel)
       */
      double offCenterX = 0.5d * (productPort.getX() + substratePort.getX());
      double offCenterY = 0.5d * (productPort.getY() + substratePort.getY());
      productPort.setX(productPort.getX() + centerOfSRGs.getX() - offCenterX);
      productPort.setY(productPort.getY() + centerOfSRGs.getY() - offCenterY);
      substratePort.setX(
        substratePort.getX() + centerOfSRGs.getX() - offCenterX);
      substratePort.setY(
        substratePort.getY() + centerOfSRGs.getY() - offCenterY);
      // Further: Want a normalised port-distance
      double scale =
        Math.sqrt(Math.pow(productPort.getX() - centerOfSRGs.getX(), 2)
          + Math.pow(productPort.getY() - centerOfSRGs.getY(), 2));
      scale = REACTION_GLYPH_SIZE / scale;
      productPort.setX(
        centerOfSRGs.getX() * (1d - scale) + productPort.getX() * scale);
      productPort.setY(
        centerOfSRGs.getY() * (1d - scale) + productPort.getY() * scale);
      
      /**
       * since the center of substrate and product-port was shifted onto the
       * center of the RG, need not recompute the scale
       */
      substratePort.setX(
        centerOfSRGs.getX() * (1d - scale) + substratePort.getX() * scale);
      substratePort.setY(
        centerOfSRGs.getY() * (1d - scale) + substratePort.getY() * scale);
      
      /**
       * 2.: Actually lay out the glyphs
       * 
       * a) Build the curve of the reactionGlyph itself
       */
      Curve mainCurve = rg.createCurve();
      LineSegment whiskers =
        new LineSegment(layout.getLevel(), layout.getVersion());
      whiskers.setEnd(productPort.clone());
      whiskers.setStart(substratePort.clone());
      mainCurve.addCurveSegment(whiskers);
      
      /**
       * b) Build the curves to the species
       */
      layoutSpeciesReferenceGlyphs(rg, centerOfSRGs, substratePort,
        productPort);
    }
  }

  
  /**
   * Lays out all hitherto unlaidout {@link SpeciesReferenceGlyph}s of a
   * {@link ReactionGlyph} (i.e. adds Curves to them)
   * 
   * @param rg
   *        The {@link ReactionGlyph}, whose yet unlaidout
   *        {@link SpeciesReferenceGlyph}s are to be laid out
   * @param centerOfSRGs
   *        center of the SRGs of rg (same as reactionGlyph's center)
   * @param substratePort
   *        the {@link Point} to which substrate-arcs should connect
   * @param productPort
   *        the {@link Point} to which product-arcs should connect
   */
  private void layoutSpeciesReferenceGlyphs(ReactionGlyph rg,
    Point centerOfSRGs, Point substratePort, Point productPort) {
    
    if (unlaidoutSpeciesReferenceGlyphs.containsKey(rg.getId())) {
      for (SpeciesReferenceGlyph srg : unlaidoutSpeciesReferenceGlyphs.get(
        rg.getId())) {
        /**
         * Compute two additional ports that stand orthogonal to the
         * substratePort-productPort-line: These are the ports for modifiers
         */
        Point orthoPort1 = new Point(
          centerOfSRGs.getX()
            - 0.5 * (productPort.getY() - substratePort.getY()),
          centerOfSRGs.getY()
            + 0.5 * (productPort.getX() - substratePort.getX()));
        Point orthoPort2 = new Point(
          centerOfSRGs.getX()
            + 0.5 * (productPort.getY() - substratePort.getY()),
          centerOfSRGs.getY()
            - 0.5 * (productPort.getX() - substratePort.getX()));
        
        Curve curve = srg.createCurve();
        CubicBezier connection = new CubicBezier(layout.getLevel(), layout.getVersion());
        
        /** Specifies the 'force' with which the CubicBezier leaves the reactionGlyph */
        double force = 1.3d;
        
        Point basePoint1, basePoint2, start, end;
        
        Point speciesPort = calculateSpeciesGlyphDockingPosition(
          calculateCenter(srg.getSpeciesGlyphInstance()), rg,
          srg.isSetSpeciesReferenceRole() ? srg.getRole()
            : SpeciesReferenceRole.UNDEFINED,
          srg.getSpeciesGlyphInstance());
        Point speciesControlPoint = Geometry.weightedSum(1 + force, speciesPort,
          -force, calculateCenter(srg.getSpeciesGlyphInstance()));
        
        end = speciesPort;
        basePoint2 = speciesControlPoint;
        if(isProductReference(srg) || isSubstrateReference(srg)) {
          if (isSubstrateReference(srg)) {
            start = substratePort.clone();
          } else {
            start = productPort.clone();
          }       
          basePoint1 = Geometry.weightedSum(1 + force, start, -force,
            centerOfSRGs);
          
          /**
           * If the SpeciesGlyph is closer to the wrong port (which may happen
           * by chance), the layout hitherto may force the arc through the
           * reactionGlyph itself (ambiguous). To avoid this, check for the
           * direction in which the speciesGlyph is found, and potentially add
           * an orthogonal component to the basepoint (to try to redirect the
           * arc around the reactionGlyph)
           */
          if (Geometry.dotProduct(
            Geometry.weightedSum(1, start, -1, centerOfSRGs),
            Geometry.weightedSum(1, speciesPort, -1, centerOfSRGs)) < 0) {
            basePoint1 = Geometry.weightedSum(1, basePoint1, force,
              Geometry.weightedSum(1,
                closest(orthoPort1, orthoPort2, speciesPort), -1,
                centerOfSRGs));
          }
          
        } else {
          start = speciesPort;
          end = closest(orthoPort1, orthoPort2, speciesPort);
          basePoint1 = speciesControlPoint;
          /** Arbitrarily, Modifiers etc use twice the force */
          basePoint2 =
            Geometry.weightedSum(1 + 2*force, end, -2*force, centerOfSRGs);
        }      
        
        connection.setStart(start);
        connection.setBasePoint1(basePoint1);
        connection.setBasePoint2(basePoint2);
        connection.setEnd(end);
        curve.addCurveSegment(connection);
      }
    }
  }
  

  /**
   * Helper-method of {@link RingLayoutAlgorithm#completeGlyphs()}, to structure
   * the code. Places textglyphs according to their origin of text
   */
  private void layoutTextGlyphs() {
    for (TextGlyph tg : textGlyphs) {
      BoundingBox box = new BoundingBox(level, version);
      // Default:
      box.createDimensions(2, 2, 0);
      box.createPosition(0, 0, 0);
      NamedSBase origin = tg.getOriginOfTextInstance();
      if (tg.isSetGraphicalObject()) {
        box.setPosition(tg.getGraphicalObjectInstance().getBoundingBox()
                          .getPosition().clone());
        box.setDimensions(tg.getGraphicalObjectInstance().getBoundingBox()
                            .getDimensions().clone());
      } else if (origin != null && origin instanceof Species) {
        List<SpeciesGlyph> results =
          getLayout().findSpeciesGlyphs(origin.getId());
        if (!results.isEmpty()) {
          SpeciesGlyph sg = results.get(0); // this is a very specific instance
                                            // (arbitrary)
          if (sg.isSetBoundingBox()) {
            box.setPosition(sg.getBoundingBox().getPosition().clone());
            box.setDimensions(sg.getBoundingBox().getDimensions().clone());
          }
        }
      }
      tg.setBoundingBox(box);
    }
  }
  
  /**
   * Overwriting the inherited behavior to take the curve-attribute of a reactionGlyph 
   * into account (if set)
   * @param reactionGlyph: the glyph in question
   */
  public double calculateReactionGlyphRotationAngle(ReactionGlyph reactionGlyph) {
    if (reactionGlyph.isSetCurve()
      && reactionGlyph.getCurve().isSetListOfCurveSegments()
      && reactionGlyph.getCurve().getCurveSegmentCount() > 0) {
      // i.e. there is some curve that can be worked with
      
      /**
       * Very basic assumption (need be respected by the layout!): The curve is
       * just a single line specifying the connection-whiskers of the
       * reaction-glyph
       */
      return calculateRotationAngle(
        reactionGlyph.getCurve().getCurveSegment(0).getStart(),
        reactionGlyph.getCurve().getCurveSegment(0).getEnd());
    } else {
      /**
       * If no curve is available, just use the super-implementation (based on
       * SpeciesGlyph-positions)
       */
      return super.calculateReactionGlyphRotationAngle(reactionGlyph);
    }
  }
  
  
  /*
   * Unused methods:
   */
  
  @Override
  public Dimensions createCompartmentGlyphDimension(
    CompartmentGlyph previousCompartmentGlyph) {
    // Not used here.
    /**
     * Since the dimensions and position of the compartmentGlyphs are coupled in
     * this implementation, they are computed together in boundCompartment to avoid
     * redundant code
     */
    return boundCompartment(compartmentStart, compartmentEnd, ringRadius).getDimensions();
  }


  @Override
  public Point createCompartmentGlyphPosition(
    CompartmentGlyph previousCompartmentGlyph) {
    // Also not used.
    return boundCompartment(compartmentStart, compartmentEnd, ringRadius).getPosition();
  }

  @Override
  public Dimensions createSpeciesGlyphDimension() { return null; }

  @Override
  public Curve createCurve(ReactionGlyph reactionGlyph,
    SpeciesReferenceGlyph speciesReferenceGlyph) { return null; }

  @Override
  public Dimensions createTextGlyphDimension(TextGlyph textGlyph) { return null; }

  @Override
  public Dimensions createSpeciesReferenceGlyphDimension(
    ReactionGlyph reactionGlyph, SpeciesReferenceGlyph speciesReferenceGlyph) { return null; }

  @Override
  public BoundingBox createGlyphBoundingBox(GraphicalObject glyph,
    SpeciesReferenceGlyph specRefGlyph) { return null; }
}
