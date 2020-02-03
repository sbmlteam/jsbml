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
    
    Polygon background = macromolecule.createPolygon();
    RenderLayoutBuilder.addRenderPoint(background, 0, height - borderRadius);
    RenderLayoutBuilder.addRenderPoint(background, 0, borderRadius);
    // Top left corner:
    RenderLayoutBuilder.addRenderCubicBezier(background, 
      0, borderRadius / 2, borderRadius / 2, 0, borderRadius, 0);
    
    RenderLayoutBuilder.addRenderPoint(background, width - borderRadius, 0);
    // Top right corner:
    RenderLayoutBuilder.addRenderCubicBezier(background, 
      width - (borderRadius / 2), 0, width, borderRadius / 2, width, borderRadius);
    
    RenderLayoutBuilder.addRenderPoint(background, width, height - borderRadius);
    // Bottom right corner:
    RenderLayoutBuilder.addRenderCubicBezier(background, 
      width, height - (borderRadius/2), width - (borderRadius/2), height, width - borderRadius, height);
    
    RenderLayoutBuilder.addRenderPoint(background, borderRadius, height);
    // Bottom left corner:
    RenderLayoutBuilder.addRenderCubicBezier(background, 
      borderRadius / 2, height, 0, height - (borderRadius/2), 0, height - borderRadius);
    
    background.setStroke(stroke);
    background.setStrokeWidth(0);
    background.setFill(fill);
    
    if(hasCloneMarker()) {
      Polygon cloneMarker = macromolecule.createPolygon();
      RenderLayoutBuilder.addRenderPoint(cloneMarker, 0, height - borderRadius);
      RenderLayoutBuilder.addRenderPoint(cloneMarker, 0, 0.7*height);
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width, 0.7*height);      
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width, height - borderRadius);
      // Bottom right corner:
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        width, height - (borderRadius/2), width - (borderRadius/2), height, width - borderRadius, height);
      
      RenderLayoutBuilder.addRenderPoint(cloneMarker, borderRadius, height);
      // Bottom left corner:
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        borderRadius / 2, height, 0, height - (borderRadius/2), 0, height - borderRadius);
      
      cloneMarker.setStroke(clone);
      cloneMarker.setStrokeWidth(0);
      cloneMarker.setFill(clone);
    }
    
    Polygon macromoleculePoly = background.clone();
    macromoleculePoly.setStroke(stroke);
    macromoleculePoly.setStrokeWidth(getLineWidth());
    macromoleculePoly.unsetFill();
    macromolecule.addElement(macromoleculePoly);
    
    return new LocalStyle(macromolecule);
  }
}
