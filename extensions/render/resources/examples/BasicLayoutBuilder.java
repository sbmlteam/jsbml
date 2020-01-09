package examples;

import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.CubicBezier;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.director.LayoutBuilder;

// TODO: is this intended usage?
public class BasicLayoutBuilder implements LayoutBuilder<String> {

  private StringBuffer product;
  private boolean ready;
  
  public BasicLayoutBuilder() {
    product = new StringBuffer();
    ready = false;
  }

  @Override
  public void builderStart(Layout layout) {
    addLine("\\documentclass{article}");
    addLine("\\usepackage{tikz}");
    addLine("\\begin{document}");
    addLine("\\begin{tikzpicture}");
    
    product.append("\\draw[dotted] (0pt,0pt) rectangle (");
    product.append(layout.getDimensions().getWidth());
    product.append("pt, ");
    product.append(layout.getDimensions().getHeight());
    addLine("pt); % Canvas");
  }

  @Override
  public void buildCompartment(CompartmentGlyph compartmentGlyph) {
    // Can assume: compartmentGlyph is laid-out
    product.append("\\draw[blue] ");
    product.append(boundingBoxToRectangle(compartmentGlyph.getBoundingBox()));
    product.append("; % Compartment: ");
    addLine(compartmentGlyph.getId());
  }

  @Override
  public void buildConnectingArc(SpeciesReferenceGlyph srg, ReactionGlyph rg,
    double curveWidth) {
    // TODO: use tikz library arrows for more (and switch)
    String drawMode = srg.getRole().equals(SpeciesReferenceRole.PRODUCT) ? "->" : ""; 
    
    for(CurveSegment cs : srg.getCurve().getListOfCurveSegments()) {
      cs.getStart();
      cs.getEnd();
      addLine(String.format("\\draw[%s] (%spt, %spt) -- (%spt, %spt);",
        drawMode, cs.getStart().getX(), cs.getStart().getY(),
        cs.getEnd().getX(), cs.getEnd().getY()));
    }
    // addLine(String.format("(%spt, %spt) -- (%spt, %spt);", ));
  }

  @Override
  public void buildCubicBezier(CubicBezier cubicBezier, double lineWidth) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void buildEntityPoolNode(SpeciesGlyph speciesGlyph,
    boolean cloneMarker) {
    // First the rectangular species node:
    product.append("\\draw[line width=2pt] ");
    product.append(boundingBoxToRectangle(speciesGlyph.getBoundingBox()));
    product.append("; % Species: ");
    addLine(speciesGlyph.getId());
    
    // Then the speciesName as node:
    product.append("\\node[] (");
    product.append(speciesGlyph.getId());
    product.append(") at (");
    product.append(speciesGlyph.getBoundingBox().getPosition().x()
      + (speciesGlyph.getBoundingBox().getDimensions().getWidth() / 2));
    product.append("pt, ");
    product.append(speciesGlyph.getBoundingBox().getPosition().y()
      + (speciesGlyph.getBoundingBox().getDimensions().getHeight() / 2));
    product.append("pt) {"); // TODO: Could change colour here based on cloneMarker
    product.append(speciesGlyph.getSpecies()); // TODO: lookup species's name in the layout?
    addLine("};");
  }

  @Override
  public void buildProcessNode(ReactionGlyph reactionGlyph,
    double rotationAngle, double curveWidth) {
    // TODO Auto-generated method stub
    product.append("\\draw[] ");
    product.append(boundingBoxToRectangle(reactionGlyph.getBoundingBox()));
    product.append("; % Reaction: ");
    addLine(reactionGlyph.getId());
  }

  @Override
  public void buildTextGlyph(TextGlyph textGlyph) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void builderEnd() {
    addLine("\\end{tikzpicture}");
    addLine("\\end{document}");
    addLine("");
    ready = true;
  }

  @Override
  public String getProduct() {
    // TODO: should this check for isProductReady?
    return product.toString();
  }

  @Override
  public boolean isProductReady() {
    return ready;
  }
  
  
  /**
   * Helper method to add a String as a new line (newline is added behind given
   * string, like {@link System.out.println}) to the product
   * 
   * @param line
   *        to be added
   */
  private void addLine(String line) {
    product.append(line);
    product.append(System.getProperty("line.separator")); 
  }
  
  /**
   * Helper method to format a {@link BoundingBox} in a LaTeX-tikz-draw way
   * @param bbox
   * @return
   */
  private String boundingBoxToRectangle(BoundingBox bbox) {
    return String.format("(%spt, %spt) rectangle ++(%spt, %spt)",
      bbox.getPosition().x(), bbox.getPosition().y(),
      bbox.getDimensions().getWidth(), bbox.getDimensions().getHeight());
  }
}
