package examples.render;

import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.Macromolecule;


public class RenderMacromolecule extends Macromolecule<LocalStyle> {
  private String stroke, fill, clone;
  private double borderRadius;
  
  /**
   * 
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param clone
   * @param borderRadius the 'radius' of the rounded corners (absolute measure)
   */
  public RenderMacromolecule(double strokeWidth, String stroke, String fill, String clone, double borderRadius) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
    this.borderRadius = borderRadius;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup macromolecule = new RenderGroup();
    
    if(hasCloneMarker()) {
      // TODO -> use clone here.
    }
    Polygon macromoleculePoly = macromolecule.createPolygon();
    RenderLayoutBuilder.addRenderPoint(macromoleculePoly, 0, height - borderRadius);
    RenderLayoutBuilder.addRenderPoint(macromoleculePoly, 0, borderRadius);
    // Top left corner:
    RenderLayoutBuilder.addRenderCubicBezier(macromoleculePoly, 
      0, borderRadius / 2, borderRadius / 2, 0, borderRadius, 0);
    
    RenderLayoutBuilder.addRenderPoint(macromoleculePoly, width - borderRadius, 0);
    // Top right corner:
    RenderLayoutBuilder.addRenderCubicBezier(macromoleculePoly, 
      width - (borderRadius / 2), 0, width, borderRadius / 2, width, borderRadius);
    
    RenderLayoutBuilder.addRenderPoint(macromoleculePoly, width, height - borderRadius);
    // Bottom right corner:
    RenderLayoutBuilder.addRenderCubicBezier(macromoleculePoly, 
      width, height - (borderRadius/2), width - (borderRadius/2), height, width - borderRadius, height);
    
    RenderLayoutBuilder.addRenderPoint(macromoleculePoly, borderRadius, height);
    // Bottom left corner:
    RenderLayoutBuilder.addRenderCubicBezier(macromoleculePoly, 
      borderRadius / 2, height, 0, height - (borderRadius/2), 0, height - borderRadius);
    
    macromoleculePoly.setStroke(stroke);
    macromoleculePoly.setStrokeWidth(getLineWidth());
    macromoleculePoly.setFill(fill);
    
    return new LocalStyle(macromolecule);
  }
}
